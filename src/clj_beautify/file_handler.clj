(ns clj-beautify.file-handler
  (:require [clojure.java.io :refer [file]]))

(defn read-file
  "Reads an entire file as a string"
  [filename]
  (slurp filename))

(defn write-file
  "Writes a newly formatted file"
  [filename contents]
  (spit filename contents))

(defn match-clj
  [f]
  (let [filename (.getCanonicalPath f)]
    (when (re-find #"\.clj" filename) filename)))

(defn exists?
  [f]
  (.exists f))

(defn list-files
  [pth]
  (let [f (file pth)]
    (when-not (exists? f)
      (throw (Exception. (str "File not found! " pth))))
   (loop [files (file-seq f)
          results ()]
          (if (empty? files)
            ;; then
            (remove nil? results)
            ;; else
            (let [tmp (conj results (match-clj (first files)))]
              (recur (rest files) tmp))))))