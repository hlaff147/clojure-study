(ns clojure-crud.core
  (:require [ring.adapter.jetty :as jetty]
            [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.swagger :as swagger]
            [reitit.swagger-ui :as swagger-ui]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.coercion :as coercion]
            [muuntaja.core :as m]
            [integrant.core :as ig]))

;; In-memory database
(defonce items (atom {}))

;; Handlers
(defn get-items [_]
  {:status 200 :body (vals @items)})

(defn get-item [{:keys [parameters]}]
  (let [id (-> parameters :path :id)
        item (get @items id)]
    (if item
      {:status 200 :body item}
      {:status 404 :body {:error "Not found"}})))

(defn create-item [{:keys [parameters]}]
  (let [id (str (random-uuid))
        item (-> parameters :body (assoc :id id))]
    (swap! items assoc id item)
    {:status 201 :body item}))

(defn update-item [{:keys [parameters]}]
  (let [id (-> parameters :path :id)
        item (-> parameters :body (assoc :id id))]
    (if (contains? @items id)
      (do (swap! items assoc id item)
          {:status 200 :body item})
      {:status 404 :body {:error "Not found"}})))

(defn delete-item [{:keys [parameters]}]
  (let [id (-> parameters :path :id)]
    (if (contains? @items id)
      (do (swap! items dissoc id)
          {:status 204 :body nil})
      {:status 404 :body {:error "Not found"}})))

;; Router
(def router
  (ring/router
   [["/swagger.json"
     {:get {:no-doc true
            :swagger {:info {:title "Simple CRUD API"}}
            :handler (swagger/create-swagger-handler)}}]

    ["/items"
     {:swagger {:tags ["items"]}}

     [""
      {:get {:summary "Get all items"
             :handler get-items}
       :post {:summary "Create new item"
              :handler create-item}}]

     ["/:id"
      {:get {:summary "Get item by id"
             :parameters {:path {:id string?}}
             :handler get-item}
       :put {:summary "Update item"
             :parameters {:path {:id string?}
                          :body map?}
             :handler update-item}
       :delete {:summary "Delete item"
                :parameters {:path {:id string?}}
                :handler delete-item}}]]]
   {:data {:coercion reitit.coercion.spec/coercion
           :muuntaja m/instance
           :middleware [muuntaja/format-negotiate-middleware
                        muuntaja/format-response-middleware
                        muuntaja/format-request-middleware
                        coercion/coerce-request-middleware
                        coercion/coerce-response-middleware]}}))

;; App
(def app
  (ring/ring-handler
   router
   (ring/routes
    (swagger-ui/create-swagger-ui-handler {:path "/"})
    (ring/create-default-handler))))

;; System
(defmethod ig/init-key :server [_ {:keys [handler port]}]
  (println (str "Server running on http://localhost:" port))
  (jetty/run-jetty handler {:port port :join? false}))

(defmethod ig/halt-key! :server [_ server]
  (.stop server))

(def system
  {:server {:handler app :port 3000}})

;; Main
(defn -main []
  (ig/init system))