(ns csvtest.core
  (:require [incanter.core :as i]
             [incanter.io :as io]
             [incanter.stats :as istat]
             [incanter.charts :as plt]
             [clj-time.core :as clj-time]
             [clj-time.format :as time-format]
             [csvtest.fetch :as fetch]
             [csvtest.jmath :as jm]
             [clojure.edn :as edn]))
;;
;;


;;
;;
;;
;; read data in from a file in the root of the project
;;
;; or use get-price-file symbol
;;
(def df (io/read-dataset "daily.csv" :header true))
;;
;; visualize moody's
;;
(def y (i/sel df :cols 4))  ;; y == close
(def x (range 100))         ;; x == 1 - 100

;;
(i/$ [:timestamp :close] df)

(def portfolio (atom []))

(defn read-portfolio [filename]
  (reset! portfolio
          (edn/read-string (slurp filename))))
;;
;; loop through the portfolio and print the symbols
;;
(defn print-all-symbols [col] 
                (if (not (empty? col))
                  (let [
                        sym (get (first col) :symbol)]
                        (println sym)
                        (recur (rest col)))))


(defn create-chart [symbol periods]
   (i/view
     (plt/add-lines
      (plt/time-series-plot
       (range 100)
       (jm/moving-average (i/sel df :cols 4) 3))
      (range 100)
      (i/sel df :cols 4))))


(defn chart-all-symbols [col] 
                (if (not (empty? col))
                  (let [
                        sym (get (first col) :symbol)]
                    (do
                      (println sym)
                      (create-chart sym 3))
                                   
                    (recur (rest col)))))



(defn -main
  "read the file daily.csv, create a chart and print the file contents"
  [& args]
  (do

    (read-portfolio "resources/fins.edn")
    (chart-all-symbols @portfolio)
    (print-all-symbols @portfolio)))
