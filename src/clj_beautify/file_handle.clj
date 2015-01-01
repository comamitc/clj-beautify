(ns clj-beautify.file-handle)

(defn read-file
  "Reads an entire file as a string"
  [filename]
  (slurp filename))

(defn write-file
  "Writes a newly formatted file"
  [filename contents]
  (spit filename contents))