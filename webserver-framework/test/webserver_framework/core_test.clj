(ns webserver-framework.core-test
  (:require [clojure.test :refer :all]
            [webserver-framework.core :refer :all]
            [ring.mock.request :as mock]
            [clojure.data.json :as json]
            ))

(deftest main-route-test
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= 200 (:status response))))))


(deftest test-route
  (testing "the test route"
    (let [res (app (mock/request :get "/test"))
          answer ((json/read-str (:body res)) "foo")]
      (is (= answer "james")))))


(deftest post-test-route
  (testing "a simple post"
    (let [res (app (mock/request :post "/ptest"))
          value ((json/read-str (:body res)) "james")]
      (is (= value "muguira")))))

