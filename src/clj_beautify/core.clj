(ns clj-beautify.core
  (:require [clj-beautify.file-handler :as f]
            [clj-beautify.beautify :refer [format-clj]])
  (:gen-class))

(defn format-string
  "Just a function wrapper around format-clj"
  ([input mode] (format-string input mode nil))
  ([input mode settings] (format-clj input mode settings)))

(defn format-file
  "Given a file page and a valid mode (`clj`|`edn`) open and use
  `clojure.tools.reader/read-string` to transform the file to a literal so that
  if can be formatted by `clojure.pprint/write`. It then writes back to the same
  file with a formatted string."
  [filename mode]
  (let [input   (f/read-file filename)
        output  (format-clj input mode)]
    (f/write-file filename output)))

(defn -main
  "Entry point of the command line program that takes a file path (or directory)
  and mode (clj|edn). Formats all files to specified mode and rewrites the
  original files."
  [& args]
  (let [arg-cnt (count args)]
    (when (< 2 arg-cnt)
      (throw (Exception. (str "Invalid number of arguements. Expected 2 but "
                              "found " arg-cnt))))
    ;; TODO: do something with the valid args
    (doseq [file (f/list-files (nth args 1))]
      (format-file file (nth args 0)))))
