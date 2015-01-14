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

(defn str-to-literal
  "Takes valid clojure as input and transforms it to a clojure literal for
  formating."
  [string format-type]
  (let [test (read-all (rt/string-push-back-reader string))]
    (println test)
    test))

(defn- unwrap-comments
  [s]
  (let [f   (clojure.string/replace s (re-pattern "^\"") ";")
        b   (clojure.string/replace f (re-pattern "\\)\"$") "")]
    (clojure.string/replace b (re-pattern "\\(comment ") "")))

(defn- literal-to-str
  "Takes valid formatter clojure literal as input and transforms it to a string
  for return to webapp UI"
  [literals]
   (clojure.string/join "\n\n" (map unwrap-comments literals)))

(defn format-clj
  "Clojure formatting function that takes a unformatted-input and format type
  and transforms it into a formatted output using clojure.pprint/write."
  [input format-type]
  (let [in-lits  (str-to-literal input format-type)
       formatted (map #(format-literal % format-type) in-lits)]
        (literal-to-str formatted)))
