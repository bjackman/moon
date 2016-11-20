(defproject moon "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :license {:name "GNU GPL v3"
            :url "http://www.gnu.org/licenses/gpl.html"
            :distribution :manual}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [clj-time "0.12.2"]]
  :main ^:skip-aot moon.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
