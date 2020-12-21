(ns jsonfun.core
  (:require [clojure.data.json :as json]))

(def misSpec "{:missionId \"FOO2\", :description \"example foo2\"}" )

(defn readme [s]
  println s)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!")
  (println (json/write-str {:missionId "FOO1", :description "experiment 1"}))
  )

;(defn category
;  [m n]
;  (let [ scen (get m :scenario)
;        frt (nth scen n)
;        catg (get frt :category)] println catg))

(defn scenario
  [m]
  (get m :scenario))

(defn category
  [m n]
  (nth (scenario m) n))

(defn spTitle
  [m n sp]
  (get (nth (get  (nth (scenario m) n) :specEntries) sp) :title))
