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
;; col == @portfolio
;;
(defn print-all-symbols [col] 
                (if (not (empty? col))
                  (let [
                        sym (get (first col) :symbol)]
                        (println sym)
                        (recur (rest col)))))

(defn print-all-names [p]
  (if (not (empty? p))
    (let [
          name (get (first p) :name)
          sym (get (first p) :symbol)]
      (println (str "symbol - " sym "\tName: " name))
      (recur (rest p)))))

(defn get-closing-col [ds]
  (i/sel ds :cols 4))

(defn get-closing-price [symbol]
  (get-closing-col (fetch/get-price-data symbol)))



(defn create-chart [closing-col periods]
   (i/view
     (plt/add-lines
      (plt/time-series-plot
       (range 100)
       closing-col)
       
      (range 100)
      (jm/moving-average closing-col periods))))

(defn get-symbol-from-portfolio [col]
  (get (first col) :symbol))

(defn get-company-name-from-portfolio [p]
  (get (first p) :name))

(defn chart-all-symbols [col] 
  (if (not (empty? col))
    (let [
          sym (get-symbol-from-portfolio col)
          ds (fetch/get-price-data sym)
          close-col (get-closing-col ds)]
      (do
        (println sym)
        (i/view
         (plt/add-lines
          (plt/time-series-plot (range 100) close-col :title sym)
          (range 100)
          (jm/moving-average close-col 4))))
      (recur (rest col)))))


(defn chart-all-symbols1 [col] 
  (if (not (empty? col))
    (let [
          sym (get-symbol-from-portfolio col)
          ds (fetch/get-price-data sym)
          close-col (get-closing-col ds)]
      (do
        (println sym)
        (i/view
         
          (plt/time-series-plot (range 100) close-col :title sym))
          
         
        (recur (rest col))))))




(defn -main
  "read the file daily.csv, create a chart and print the file contents"
  [& args]
  (do

    (read-portfolio "resources/fins.edn")
    ;;(chart-all-symbols @portfolio)
    (print-all-names @portfolio)
))


;;(defn create-chart-1 [symbol periods]
;;   (i/view
;;     (plt/add-lines
;;      (plt/time-series-plot
;;       (range 100)
;;       (jm/moving-average (i/sel df :cols 4) periods))
;;      (range 100)
;;      (i/sel df :cols 4))))
