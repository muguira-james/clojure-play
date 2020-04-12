(ns http.trawler
  (:gen-class)
  (:import (org.jsoup Jsoup)
           (org.jsoup.select Elements)
           ))



(defn get-page-at-address [address]
  (let [conn (Jsoup/connect address)
        soup (.get conn)]
    soup))

(defn extract-link-data [link]
  (let [linktext (.text link)
        address (.attr link "abs:href")]
    {:linktext linktext :address address}))

(defn extract-links [soup]
  (let [links (.select soup "a")]
    (mapv extract-link-data links)))

(defn extract-h1-data [link]
  (let [linktext (.text link)
        heading (.attr link "abs:href")]
    {:linktext linktext :heading heading}))

(defn extract-h1-data2 [soup]
  (let [txt (.select soup "h1")]
    txt))

(defn fetch [address]
  (let [soup (get-page-at-address address)
        links (extract-links soup)
        text (.text soup)]
    {:soup soup :links links :text text}))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
