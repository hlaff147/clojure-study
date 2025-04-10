(ns clojure-crud.repository.item-repository
  (:require [clojure.spec.alpha :as s]
            [clojure-crud.specs.item-spec :as item-spec]))

(defonce items (atom {}))

(defn get-items []
  (vals @items))

(defn get-item [id]
  (get @items id))

(defn create-item [item]
  (let [id (str (random-uuid))]
    (if (s/valid? ::item-spec/item (assoc item :id id))
      (let [item (assoc item :id id)]
        (swap! items assoc id item)
        item)
      (throw (ex-info "Invalid item" (s/explain-data ::item-spec/item item))))))

(defn update-item [id item]
  (if (s/valid? ::item-spec/item (assoc item :id id))
    (let [updated-item (assoc item :id id)]
      (swap! items assoc id updated-item)
      updated-item)
    (throw (ex-info "Invalid item" (s/explain-data ::item-spec/item item)))))

(defn delete-item [id]
  (swap! items dissoc id))

(defn item-exists? [id]
  (contains? @items id))