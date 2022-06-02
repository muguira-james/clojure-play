
(ns scrape1.core-test
  (:require [clojure.test :refer :all]
            [clojure.pprint :as pp]
            [scrape1.core :refer :all]))

(deftest a-test
  (testing
    "Fetch my personal website and check number key/values in results"
    (let [page-data (fetch-web-page-data "https://markwatson.com")]
      (pp/pprint page-data)
      (is (= (count page-data) 2)))))
