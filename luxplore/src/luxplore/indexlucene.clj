(ns luxplore.indexlucene
  (:gen-class))

(require '[clojure.edn :as edn])
(require '[clojure.java.io :as io])

(import [org.apache.lucene.store FSDirectory Directory])
(import (org.apache.lucene.index
         DirectoryReader IndexReader IndexWriter IndexWriterConfig))
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
  (def directory (FSDirectory/open (.toPath (File. indexPath))))
  directory)


(defn closeIndex
  []
  (.close directory))

(defn createStandardAnalyzer
  []
  (def analyzer (StandardAnalyzer.))
  analyzer)


    
(defn openReader
  [directory]
  (def reader (DirectoryReader/open directory))
  reader)

(defn closeReader
  []
  (.close reader))


(defn createIndexConfig
  "create a config for the current open Index"
  [analyzer]
  (def wCfg (IndexWriterConfig. analyzer))
  wCfg)

(defn openIndexWriter
  "open writer on current index"
  [directory xCfg]
  (def iWriter (IndexWriter. directory wCfg))
  iWriter)

(defn numberOfIndexedDocs
  "answer number of indexed docs from the current open index"
  []
  (.numDocs reader))

(defn createIndex
  [indexPath]
  (do
    (def directory (openIndex indexPath))
    (def analyzer (createStandardAnalyzer))
    (def wCfg (createIndexConfig analyzer))
    (def iWriter (openIndexWriter directory wCfg))
    (.commit iWriter)
    (.forceMerge iWriter 100)
    (.close iWriter)
    (.close directory)
    indexPath))

(defn -main
  "comment... "
  [& args]
  (println "Hello, World!"))
