(ns scrape1.urls)

(require '[net.cgrand.enlive-html :as html])
(require '[org.httpkit.client :as http])
(require '[clojure.data.json :as json])

;;------------------- handle URL --------------------------
;;
;; the basic test case is cnn business
(def ^:dynamic *base-url* "https://cnn.com/BUSINESS")

;;

;;
;; options to set user-agent and ignore SSL
(def options {:user-agent "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:88.0) Gecko/20100101 Firefox/88.0"  })

;;
;; gather a dom from cnn-business
(defn get-dom []
  (html/html-snippet
   (:body @(http/get *base-url* options  ))))


;;
;; get a dom from input url
(defn get-dom-from-url [url]
  (html/html-snippet
   (:body @(http/get url options ))))

(defn get-page [url]
  @(http/get url options))

;; ----------------- extract to sequence -------------------

(defn extract-cnbc-to-seq [url]
  (html/select (get-dom-from-url url) [:a.LatestNews-headline]))

(defn extract-cnn-business-to-seq [url]
  (html/select (get-dom-from-url url) [:span.cd__headline-text]))

(defn extract-bbc-business-to-seq [url]
  (html/select (get-dom-from-url url) [:h3.gs-c-promo-heading__title]))

(defn extract-cnbc [ base ticker tab ]
  (let [url (str base ticker tab)]
    (extract-cnbc-to-seq url)))



;;
(def alphavantage-url "https://www.alphavantage.co/query?function=")
(def TIME-SERIES-DAILY 'TIME_SERIES_DAILY)
(def FULL-SIZE 'full)
(def data-type 'csv)
(def apikey 'WO3506PATLTSXPJB)
(def INCOME-STATEMENT "INCOME_STATEMENT")


;; (def date-format (time-format/formatter "YYY-mm-dd"))
;;
;; create a URL, default to 100 points
;;
(defn alpha-v-time-series-csv
  "return the url for alpha-vanntage"
  [symbol]
  (str alphavantage-url
       TIME-SERIES-DAILY "&symbol=" symbol "&datatype=" data-type "&apikey=" apikey))

(defn alpha-v-income-url [symbol]
  (str alphavantage-url INCOME-STATEMENT "&symbol=" symbol "&apikey=" apikey))

(defn get-income-stmt [tckr]
  (json/read-str (:body @(http/get (alpha-v-income-url tckr))) :key-fn keyword))
  
;; ---------------------  ratio code ---------------
(defn get-operating-income [inc-stmt]
  (Long. (:operatingIncome (first (:annualReports inc-stmt)))))

(defn get-net-income [inc-stmt]
  (Long. (:netIncome (first (:annualReports inc-stmt)))))

(defn get-total-revenue [inc-stmt]
  (Long. (:totalRevenue (first (:annualReports inc-stmt)))))

(defn netProfit-margin [net-income total-revenue]
  (float ( / net-income total-revenue)))


(defn operating-margin [operating-income total-revenue]
  (float (/ operating-income total-revenue)))



(defn ratio-driver [ticker]
  (let [inc-stmt (get-income-stmt ticker)]
    (do
      (println (str "operating margin= "
                    (operating-margin
                     (get-operating-income inc-stmt) (get-total-revenue inc-stmt))))
      (println (str "net profit = "
                    (netProfit-margin
                     (get-net-income inc-stmt) (get-total-revenue inc-stmt)))0
      ))))


;; ----  DEAD commented out code ----------------

(comment 
;;
;; parameter inputs: e.g. TIME-SERIES-DAILY
(defn alpha-vantage-prm
  "uses parameters to create the URL"
  [ function symbol  ]
  (str alphavantage-url
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


)
