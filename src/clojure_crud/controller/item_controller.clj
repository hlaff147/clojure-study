(ns clojure-crud.controller.item-controller
  (:require [clojure-crud.service.item-service :as service]))

(defn get-items [_]
  (service/get-items))

(defn get-item [{:keys [path-params]}]
  (let [id (:id path-params)]
    (service/get-item id)))

(defn create-item [{:keys [body-params]}]
  (service/create-item body-params))

(defn update-item [{:keys [path-params body-params]}]
  (let [id (:id path-params)]
    (service/update-item id body-params)))

(defn delete-item [{:keys [path-params]}]
  (let [id (:id path-params)]
    (service/delete-item id)))