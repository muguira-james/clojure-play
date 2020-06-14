(defproject clodb "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/clojure-contrib "1.2.0"]   ;; for clojure.contrib.sql
                 [org.clojure/java.jdbc "0.7.11"]         ;; jdbc 
                 [mysql/mysql-connector-java "5.1.38"]
                 [org.clojars.scsibug/feedparser-clj "0.4.0"]
                 ]
  :main ^:skip-aot clodb.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})



