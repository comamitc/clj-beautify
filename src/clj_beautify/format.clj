(ns clj-beautify.format
  (:require   [clojure.pprint :refer [write code-dispatch simple-dispatch]]
              [rewrite-clj.parser :as p]
              [rewrite-clj.node :as n]))

;; Map containing the dispatch modes for various beautifying
(def dispatch-mode
  {"clj"     code-dispatch
   "edn"     simple-dispatch})

(defn is-token-sandwich [triplet]
  (let [first-token (-> triplet first first)
        second-token (-> triplet second first)
        result (and (not= first-token :newline) (= second-token :whitespace))]
    result))

(defn is-missing-whitespace [triplet]
  (let [first-token (-> triplet first first)
        second-token (-> triplet second first)
        result (and (not (contains? #{:newline :whitespace} first-token)) (not (contains? #{:newline :whitespace} second-token)))]
    result))

(defn drop-whitespace-from-ast [ast]

  (let [descriptor (first ast)
        tokens (rest ast)
        dropped (drop-while #(contains? #{:newline :whitespace} (first %1)) tokens)
        reversed-tokens (reverse dropped)
        dropped (reverse (drop-while #(contains? #{:newline :whitespace} (first %1)) reversed-tokens))
        my-range (dec (count dropped))
        token-sandwiches (reduce (fn [accum x] (assoc accum (inc x) (is-token-sandwich (subvec (vec dropped) x (+ x 2))))) {} (range my-range))

        missing-whitespaces (reduce (fn [accum x] (assoc accum (inc x) (is-missing-whitespace (subvec (vec dropped) x (+ x 2))))) {} (range my-range))

        eaten (vec (for [x (range (count dropped)) :let [token (nth dropped x)]] (if (get token-sandwiches x) [(first token) " "] token)))

        bloated (reduce (fn [accum x]
                          (let [token (nth eaten x)]
                            (if (get missing-whitespaces x)
                              (conj accum [:whitespace " "] token)
                              (conj accum token)))) [] (range (count eaten)))


        recursed-tokens (map (fn [token]
                               (if (contains? #{:set :seq :vector :list :map} (first token))
                                 (drop-whitespace-from-ast token)
                                 token))
                             bloated)
        new-ast (vec (cons descriptor recursed-tokens))]
    new-ast))

(defn- format-literal
  "Takes a literal and formats it using the built in clojure.pprint/write
  function. Returns a formatted literal."
  [literal format-type]
  (write literal
         :pretty true
         :stream nil
         :dispatch (get dispatch-mode format-type)))

(defn code-format [code format-type]
  (let [ast (p/parse-string-all code)
        new-ast (drop-whitespace-from-ast ast)
        formatted (format-literal ast format-type)]
    (n/string formatted)))