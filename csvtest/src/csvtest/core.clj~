(ns csvtest.core
  (:gen-class))

(require '[incanter.core :as i])
(require '[incanter.io :as io])
(require '[incanter.stats :as istat])
(require '[incanter.charts :as plt])

;;
(def TIME-SERIES-DAILY 'TIME_SERIES_DAILY)
(def data-type 'csv)
(def apikey 'foo)
;;
;; create a URL
;;
(defn alpha-vantage-url
  "return the url for alpha-vanntage"
  [symbol]
  (str "https://www.alphavantage.co/query?function="
       TIME-SERIES-DAILY "&symbol=" symbol "&datatype=" data-type "&apikey=" apikey))
;;
;; get price data from a file
;;
(defn get-price-file
  "get price data from a file: daily.csv, in the root of the project"
  [file-name]
  (io/read-dataset file-name :header true))
;;
;; get price data from alpha-vantage
;;
(defn get-price-data
  "get data for a symbol"
  [symbol]
  (io/read-dataset (alpha-vantage-url symbol) :header true))
;;
;;
(defn moving-average [lst window-size]
  (loop [ sm []
         lsti (seq lst)
         win-size window-size
         ]
    (if (empty? lsti)
      sm
      (recur (conj sm (istat/mean (first (partition win-size 1 lsti)))) (rest lsti) win-size))))
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
(i/view
 (plt/add-lines
  (plt/time-series-plot (range 100) (moving-average (i/sel df :cols 4) 3))
  (range 100)
  (i/sel df :cols 4)))
;;
;; select a column
(i/$ :open df)
;;
;; select 2 columns
(i/$ [:timestamp :close] df)



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println df))
