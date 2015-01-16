(ns clj-beautify.core-test
  (:require [clojure.test :refer :all]
            [clj-beautify.core :refer :all]))

;; comments
;; comments again
(defn a-test
  [] ;; comments 2
  ;; comment 2.5
  (testing "FIXME, I fail." (is (= 0 1))) ;; comments 3
)

;; comments
(defn a-test [] ;; comments 2
  (testing "FIXME, I fail." (is (= 0 1))))

;; this is a edn data structure
(def foo
 {:foo "bar",
  :baz "bar",
  :feebs "babababab",
  :howmanymore "THIS MIGHT",
  :onemore "howdy"})

