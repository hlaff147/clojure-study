(ns clojure-crud.service.item-service-test
  (:require [clojure.test :refer :all]
            [clojure-crud.service.item-service :as service]
            [clojure-crud.repository.item-repository :as repository]))

(def mock-item {:id "123" :name "Test Item" :price 99.99})

(deftest create-item-test
  (testing "Successfully creates an item"
    (with-redefs [repository/create-item (fn [item] (assoc item :id "123"))]
      (let [result (service/create-item mock-item)]
        (is (= 201 (:status result)))
        (is (= "123" (get-in result [:body :id]))))))

  (testing "Handles invalid item"
    (with-redefs [repository/create-item (fn [_]
                                           (throw (ex-info "Invalid item" {:error "validation failed"})))]
      (let [result (service/create-item {})]
        (is (= 400 (:status result)))
        (is (= "Invalid item" (get-in result [:body :error])))))))

(deftest get-items-test
  (testing "Returns all items"
    (with-redefs [repository/get-items (fn [] [mock-item])]
      (let [result (service/get-items)]
        (is (= 200 (:status result)))
        (is (vector? (:body result)))
        (is (= [mock-item] (:body result)))))))

(deftest get-item-test
  (testing "Returns item when found"
    (with-redefs [repository/get-item (fn [_] mock-item)]
      (let [result (service/get-item "123")]
        (is (= 200 (:status result)))
        (is (= mock-item (:body result))))))

  (testing "Returns 404 when item not found"
    (with-redefs [repository/get-item (fn [_] nil)]
      (let [result (service/get-item "456")]
        (is (= 404 (:status result)))
        (is (= "Not found" (get-in result [:body :error])))))))

(deftest update-item-test
  (testing "Updates item when it exists"
    (with-redefs [repository/item-exists? (fn [_] true)
                  repository/update-item (fn [_ item] item)]
      (let [result (service/update-item "123" mock-item)]
        (is (= 200 (:status result)))
        (is (= mock-item (:body result))))))

  (testing "Returns 404 when item doesn't exist"
    (with-redefs [repository/item-exists? (fn [_] false)]
      (let [result (service/update-item "456" mock-item)]
        (is (= 404 (:status result)))
        (is (= "Not found" (get-in result [:body :error])))))))

(deftest delete-item-test
  (testing "Deletes item when it exists"
    (with-redefs [repository/item-exists? (fn [_] true)
                  repository/delete-item (fn [_] nil)]
      (let [result (service/delete-item "123")]
        (is (= 204 (:status result)))
        (is (nil? (:body result))))))

  (testing "Returns 404 when item doesn't exist"
    (with-redefs [repository/item-exists? (fn [_] false)]
      (let [result (service/delete-item "456")]
        (is (= 404 (:status result)))
        (is (= "Not found" (get-in result [:body :error])))))))