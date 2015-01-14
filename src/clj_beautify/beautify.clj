(ns clj-beautify.beautify
  (:require [clojure.tools.reader :as r]
            [clojure.tools.reader.reader-types :as rt]
            [clojure.pprint :refer [write code-dispatch simple-dispatch]]))

;; Map containing the dispatch modes for various beautifying
(def dispatch-mode
  {"clj"     code-dispatch
   "edn"     simple-dispatch})

(defn- read-all
  [input]
  (let [eof (Object.)]
    (take-while #(not= % eof) (repeatedly #(r/read input false eof)))))

(defn str-to-literal!
  "Takes valid clojure as input and transforms it to a clojure literal for
  formating."
  [string]
  (let [test (read-all (rt/string-push-back-reader string))]
    (println test)
    test))

(defn- format-literal!
  "Takes a literal and formats it using the built in clojure.pprint/write
  function. Returns a formatted literal."
  [literal format-type]
  (write literal
         :pretty true
         :stream nil
         :dispatch (get dispatch-mode format-type)))

(defn- literal-to-str!
  "Takes valid formatter clojure literal as input and transforms it to a string
  for return to webapp UI"
  [literal]
  (str literal))

(defn format-clj!
  "Clojure formatting function that takes a unformatted-input and format type
  and transforms it into a formatted output using clojure.pprint/write."
  [input format-type]
  (let [in-lit    (str-to-literal! input)
        formatted (format-literal! in-lit format-type)]
        (println in-lit)
        (literal-to-str! formatted)))
