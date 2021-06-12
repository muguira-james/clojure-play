(ns scrape1.urls)

(require '[net.cgrand.enlive-html :as html])
(require '[org.httpkit.client :as http])

;;------------------- handle URL --------------------------
;;
;; the basic test case is cnn business
(def ^:dynamic *base-url* "https://cnn.com/BUSINESS")

;;

;;
;; options to set user-agent and ignore SSL
(def options {:user-agent "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:88.0) Gecko/20100101 Firefox/88.0" :insecure true })

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
