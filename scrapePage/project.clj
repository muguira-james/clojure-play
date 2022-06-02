(defproject scrape1 "0.1.0-SNAPSHOT"
  :description "build some scraping logic"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :source-paths ["src"]
  :java-source-paths ["src-java"]

  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.jsoup/jsoup "1.14.3"]
                 [org.clojars.scsibug/feedparser-clj "0.4.0"]
                 [org.apache.opennlp/opennlp-tools "1.9.4"]]
  :main ^:skip-aot scrape1.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
