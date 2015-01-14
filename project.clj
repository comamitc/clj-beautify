(defproject clj-beautify "0.1.0-SNAPSHOT"
  :description "A clojure formatter written in Clojure"
  :url "http://example.com/FIXME"
  :license {:name "The MIT License (MIT)"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [rewrite-clj "0.4.1"]]
  :main ^:skip-aot clj-beautify.core
  :source-paths ["src" "../tools.reader/src/main/clojure"]
  :aot [clojure.tools.reader.impl.ExceptionInfo]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
