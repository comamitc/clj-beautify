(defproject clj-beautify "0.1.2"
  :description "A clojure formatter written in Clojure"
  :url "https://github.com/comamitc/clj-beautify"
  :license {:name "The MIT License (MIT)"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot clj-beautify.core
  :source-paths ["src" "./contrib/tools.reader/src/main/clojure"]
  :aot [clojure.tools.reader.impl.ExceptionInfo]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
