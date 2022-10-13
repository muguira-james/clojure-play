(defproject luxplore "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 ;; Our Http library for client/server
                 [http-kit "2.3.0"]
                 ;; Ring defaults - for query params etc
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-mock "0.4.0"]
                 [ring-cors "0.1.13"]
                 ;; Clojure data.JSON library
                 [org.clojure/data.json "0.2.6"]
                 [org.apache.lucene/lucene-core "9.3.0"]]
  :main ^:skip-aot luxplore.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
