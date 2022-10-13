(ns luxplore.core
  (:gen-class))

(require '[clojure.edn :as edn])
(require '[clojure.java.io :as io])

(import [org.apache.lucene.store FSDirectory Directory])
(import (org.apache.lucene.index
         DirectoryReader IndexReader IndexWriterConfig))
(import (org.apache.lucene.document Document Field FieldType))
(import (org.apache.lucene.analysis.standard StandardAnalyzer))

(import [java.io File])

(def config '())
(def directory '())
(def reader '())
(def wCfg '())
(def analyzer '())

(defn gatherConfig [fileName]
  (def config (edn/read-string (slurp fileName))))

(defn getDefaultIndexDir
  []
  (get (first config) :defaultIndexDir))

(defn testConfig
  []
  (let [ idx (getDefaultIndexDir)]
    (println (str "item = " config))
    (println (str "index dir= " idx))))

(defn openIndex
  [indexPath]
  (def directory (FSDirectory/open (.toPath (File. indexPath)))))


(defn closeIndex
  []
  (.close directory))

(defn openReader
  [directory]
  (def reader (DirectoryReader/open directory)))

(defn closeReader
  []
  (.close reader))

(defn createStardAnalyzer
  []
  (def analyzer (StandardAnalyzer.)))

(defn createIndexConfig
  "create a config for the current open Index"
  []
  (def wCfg (IndexWriterConfig. analyzer)))

(defn openIndexWriter
  "open writer on current index"
  []
  (def iWriter (IndexWriter. directory wCfg)))

(defn numberOfIndexedDocs
  "answer number of indexed docs from the current open index"
  []
  (.numDocs reader))
  
(defn -main
  "comment... "
  [& args]
  (println "Hello, World!"))
