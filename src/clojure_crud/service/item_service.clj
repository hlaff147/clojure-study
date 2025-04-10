(ns clojure-crud.service.item-service
  (:require [clojure-crud.repository.item-repository :as repository]))

(defn create-item [item]
  (try
    (let [item (repository/create-item item)]
      {:status 201 :body item})
    (catch clojure.lang.ExceptionInfo e
      (let [data (ex-data e)]
        {:status 400 :body {:error "Invalid item" :details data}})))
)

(defn get-items []
  {:status 200 :body (vec (repository/get-items))})

(defn get-item [id]
  (let [item (repository/get-item id)]
    (if item
      {:status 200 :body item}
      {:status 404 :body {:error "Not found"}})))

(defn update-item [id item]
  (if (repository/item-exists? id)
    (let [item (repository/update-item id item)]
      {:status 200 :body item})
    {:status 404 :body {:error "Not found"}}))

(defn delete-item [id]
  (if (repository/item-exists? id)
    (do (repository/delete-item id)
        {:status 204 :body nil})
    {:status 404 :body {:error "Not found"}}))