(ns clj-beautify.beautify
  (:require [clojure.tools.reader :as r]
            [clojure.tools.reader.reader-types :as rt]
            [clojure.pprint :refer [write code-dispatch simple-dispatch]]))

;; Map containing the dispatch modes for various beautifying
(def dispatch-mode
  {"clj"     code-dispatch
   "edn"     simple-dispatch})

(defn- format-literal
  "Takes a literal and formats it using the built in clojure.pprint/write
  function. Returns a formatted literal."
  [literal format-type]
  (write literal
         :pretty true
         :stream nil
         :dispatch (get dispatch-mode format-type)))

(defn- read-all
  [input]
  (let [eof (Object.)]
    (take-while #(not= % eof) (repeatedly #(r/read input false eof false true)))))

(defn- str-to-literal
  "Takes valid clojure as input and transforms it to a clojure literal for
  formating."
  [string format-type]
  (read-all (rt/string-push-back-reader string)))

;; TODO: not sure of the performance reprocussions of regex string replacement
(defn- unwrap-comments
  [s]
  ;; TODO: could also be ! token
  (let [f (clojure.string/replace s (re-pattern "\"\\(comment ") ";")]
    (if (not= s f)
      ;; then
      (let [x (clojure.string/replace f (re-pattern "\\)\"(\n)?") "\n")]
        (if (.startsWith x ";")
          x
          (str x "\n\n")))
      ;; else
      (str s "\n\n"))))

(defn- literal-to-str
  "Takes valid formatter clojure literal as input and transforms it to a string
  for return to webapp UI"
  [literals]
   (clojure.string/join "" (map unwrap-comments literals)))

(defn format-clj
  "Clojure formatting function that takes a unformatted-input and format type
  and transforms it into a formatted output using clojure.pprint/write."
  [input format-type]
  (let [in-lits  (str-to-literal input format-type)
       formatted (map #(format-literal % format-type) in-lits)]
        (literal-to-str formatted)))
