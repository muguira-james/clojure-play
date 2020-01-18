
(defproject stocklook "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [incanter "1.9.3"]
                 [incanter/incanter-charts "1.9.3"]]
  :main ^:skip-aot stocklook.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
