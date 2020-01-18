(ns stocklook.core
  (:require [incanter.core :as i]
             [incanter.io :as io]
             [incanter.stats :as istat]
             [incanter.charts :as plt]
             [clj-time.core :as clj-time]
             [clj-time.format :as time-format]
             [stocklook.fetch :as fetch]
             [stocklook.jmath :as jm]
             [clojure.edn :as edn]))
;;
;;
;; read data in from a file, called daily.csv, in the root of the project
;;
;; or use get-price-file symbol
;;
(def df (io/read-dataset "daily.csv" :header true))
;;
;; examples of how to use incanter slicing
;;
(def y (i/sel df :cols 4))  ;; y == close
(def x (range 100))         ;; x == 1 - 100
(i/$ [:timestamp :close] df)

;;
;; create and manage a portfolio
;;
(def portfolio (atom []))
;;
;; read the portfolio names and symbols from a filename
;;
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
;;
;; pretty print all the symbols and company names
;;
(defn print-all-names [p]
  (if (not (empty? p))
    (let [
          name (get (first p) :name)
          sym (get (first p) :symbol)]
      (println (str "symbol - " sym "\tName: " name))
      (recur (rest p)))))

;;
;; create a chart: price and moving average overlay
;;
;; inputs: list of closing prices, number of periods for the moving-average
;;
(defn create-chart [closing-col periods]
   (i/view
     (plt/add-lines
      (plt/time-series-plot
       (range 100)
       closing-col)
       
      (range 100)
      (jm/moving-average closing-col periods))))
;;
;; pop the symbol off the portfolio entry
;;
(defn get-symbol-from-portfolio [col]
  (get (first col) :symbol))
;;
;; pop company name off portfolio entry
;;
(defn get-company-name-from-portfolio [p]
  (get (first p) :name))
;;
;; loop creating charts for all portfolio symbols
;;
(defn chart-all-symbols [col] 
  (if (not (empty? col))
    (let [
          sym (get-symbol-from-portfolio col)
          ds (fetch/get-price-data sym)
          close-col (fetch/get-closing-col ds)]
      (do
        (println sym)
        (i/view
         (plt/add-lines
          (plt/time-series-plot (range 100) close-col :title sym)
          (range 100)
          (jm/moving-average close-col 4))))
      (recur (rest col)))))

;;
;; loop creating time series chart for all portfolio symbols
;;
(defn chart-all-symbols1 [col] 
  (if (not (empty? col))
    (let [
          sym (get-symbol-from-portfolio col)
          ds (fetch/get-price-data sym)
          close-col (fetch/get-closing-col ds)]
      (do
        (println sym)
        (i/view
         
          (plt/time-series-plot (range 100) close-col :title sym))
          
         
        (recur (rest col))))))
;;
;; given a symbol, e.g. "AXP" plot the resulting dataset
;;   this plots a default of 100 values
;;
(defn plot-symbol
  "gets the closing price from alpha-vantage and plots it"
  [ sym ]
  (let [
        close-column (fetch/get-closing-price-daily-full sym)
        x-vals (range (count close-column))]
    (do
      (println (str "symbol -> " sym))
      (println (str "number of prices " (count close-column)))
      (i/view
       (plt/time-series-plot x-vals close-column :title sym))
      )))
;;
;; plot all the data alpha vantage has (20 yrs if possible)
;;
(defn plot-symbol-full
  "gets the closing price from alpha-vantage and plots it"
  [ sym ]
  (let [
        close-column (fetch/get-closing-price-daily-full sym)
        x-vals (range (count close-column))]
    (do
      (println (str "symbol -> " sym))
      (println (str "number of prices " (count close-column)))
      (i/view
       (plt/time-series-plot x-vals close-column :title sym))
      )))
;;
;; ask alpha-vantage for a simple-moving-average over 20 yrs
;;
(defn plot-sma [symbol]
  (let [
        ds (i/sel (fetch/av-simple-moving-average symbol "daily" 20 "close") :cols 1)
        x-vals (range (count ds))]
    (do
      (println (str "length --> " (count ds)))
      (println (str "x length --> " x-vals))
      (i/view (plt/time-series-plot x-vals ds :title symbol))
  )))
;;
;; main func
;;
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
