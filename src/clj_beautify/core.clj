(ns clj-beautify.core
  (:require [clj-beautify.file-handler :as f]
            [clj-beautify.beautify :refer [format-clj]])
  (:gen-class))

(defn format-file
  [filename mode]
  (let [input   (f/read-file filename)
        output  (format-clj input mode)]
    (f/write-file filename output)))

;; TODO:
(defn -main
  "Entry point of the command line program.
   (0 args) -> represents the mode to open the program in (clj or edn)
   (1 args) -> represents the file or directory to walk for formatting"
  [& args]
  (let [arg-cnt (count args)]
    (when (< 2 arg-cnt)
      (throw (Exception. (str "Invalid number of arguements. Expected 2 but "
                              "found " arg-cnt))))
    ;; TODO: do something with the valid args
    (doseq [file (f/list-files (nth args 1))]
      (format-file file (nth args 0)))))
