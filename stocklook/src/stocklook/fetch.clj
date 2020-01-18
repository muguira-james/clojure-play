(ns stocklook.fetch)

;;
;; helper functions
;;
(require '[incanter.core :as i])
(require '[incanter.io :as io])

;;
(def TIME-SERIES-DAILY 'TIME_SERIES_DAILY)
(def FULL-SIZE 'full)
(def data-type 'csv)
(def apikey 'foo)
;; (def date-format (time-format/formatter "YYY-mm-dd"))
;;
;; create a URL, default to 100 points
;;
(defn alpha-vantage-url
  "return the url for alpha-vanntage"
  [symbol]
  (str "https://www.alphavantage.co/query?function="
       TIME-SERIES-DAILY "&symbol=" symbol "&datatype=" data-type "&apikey=" apikey))
;;
;; parameter inputs: e.g. TIME-SERIES-DAILY
(defn alpha-vantage-prm
  "uses parameters to create the URL"
  [ function symbol  ]
  (str "https://www.alphavantage.co/query?function="
       function
       "&symbol=" symbol
       "&datatype=" data-type "&apikey=" apikey))
;;
;; parameter inputs: get all data alpha vanntage has
;;
(defn alpha-vantage-prm-full
  "uses parameters to create the URL"
  [ function symbol ]
  (do
    (let [ zz (str "https://www.alphavantage.co/query?function="
         function
         "&symbol=" symbol
         "&outputsize=" FULL-SIZE
         "&datatype=" data-type
         "&apikey=" apikey)]
      (println zz)
      zz)))
      
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
;; get daily price data from alpha-vantage
;;
(defn get-price-daily
  "gets the daily info for a stock: open hi low close volumn"
  [ symbol ]
  (io/read-dataset (alpha-vantage-url TIME-SERIES-DAILY symbol) :header true))

;;
;; get 20 yrs of price data from AV
;;
(defn get-price-daily-full
  "gets the daily info for a stock: open hi low close volumn"
  [ symbol ]
  (io/read-dataset (alpha-vantage-prm-full TIME-SERIES-DAILY symbol) :header true))
;;
;; pop the "close" column off the dataset
;;
(defn get-closing-col [ds]
  (i/sel ds :cols 4))
;;
;; fetch the price dataset from alpha vantage
;;  return the close column
;;
(defn get-closing-price [symbol]
  (get-closing-col (get-price-data symbol)))
;;
(defn get-closing-price-daily [symbol]
  (get-closing-col (get-price-daily symbol)))
;;
(defn get-closing-price-daily-full [symbol]
  (get-closing-col (get-price-daily-full symbol)))


;;
;; create a URL for a SMA
;;
(defn alpha-vantage-SMA
  "return the url for alpha-vanntage"
  [symbol time-interval time-period series-type ]
  (str "https://www.alphavantage.co/query?function=SMA"
       "&symbol=" symbol
       "&interval=" time-interval
       "&time_period=" time-period
       "&series_type=" series-type
       "&datatype=" data-type
       "&apikey=" apikey))
;;
;; get a simple moving average from alpha vantage
(defn av-simple-moving-average
  "retrieve a SMA from alpha vantage"
  [ symbol time-interval time-period series-type]
  (let [
        url (alpha-vantage-SMA symbol time-interval time-period series-type)
        ds (io/read-dataset url :header true)]
    (do
      (println url)
      ds)))
