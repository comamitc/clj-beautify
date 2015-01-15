(ns clj-beautify.core-test
  (:require [clojure.test :refer :all]
            [clj-beautify.core :refer :all]))

;; comments
(defn a-test []
  ;; comments 2
  (testing "FIXME, I fail." (is (= 0 1))))

;; comments
(defn a-test []
  ;; comments 2
  (testing "FIXME, I fail." (is (= 0 1))))

;; this is a edn data structure
(def foo
 (:foo "bar" :baz "bar" :feebs "babababab" :howmanymore "THIS MIGHT" :onemore "howdy"))

