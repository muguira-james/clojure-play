(ns csvtest.fetch)



(require '[incanter.core :as i])
(require '[incanter.io :as io])

;;
(def TIME-SERIES-DAILY 'TIME_SERIES_DAILY)
(def data-type 'csv)
(def apikey 'foo)
;; (def date-format (time-format/formatter "YYY-mm-dd"))
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
