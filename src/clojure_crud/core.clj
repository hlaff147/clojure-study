(ns clojure-crud.core
  (:require [ring.adapter.jetty :as jetty]
            [reitit.ring :as ring]
            [reitit.coercion.spec]
            [reitit.swagger :as swagger]
            [reitit.swagger-ui :as swagger-ui]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [reitit.ring.coercion :as coercion]
            [muuntaja.core :as m]
            [integrant.core :as ig]
            [clojure-crud.controller.item-controller :as controller]))

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
             :responses {200 {:body vector?}}
             :handler controller/get-items}
       :post {:summary "Create new item"
              :parameters {:body map?}
              :responses {201 {:body map?}}
              :handler controller/create-item}}]

     ["/:id"
      {:get {:summary "Get item by id"
             :parameters {:path {:id string?}}
             :responses {200 {:body map?}
                         404 {:body map?}}
             :handler controller/get-item}
       :put {:summary "Update item"
             :parameters {:path {:id string?}
                          :body map?}
             :responses {200 {:body map?}
                         404 {:body map?}}
             :handler controller/update-item}
       :delete {:summary "Delete item"
                :parameters {:path {:id string?}}
                :responses {204 {:body nil?}
                            404 {:body map?}}
                :handler controller/delete-item}}]]]
   {:data {:coercion reitit.coercion.spec/coercion
           :muuntaja (m/create)
           :middleware [muuntaja/format-negotiate-middleware
                        muuntaja/format-response-middleware
                        muuntaja/format-request-middleware
                        coercion/coerce-request-middleware
                        coercion/coerce-response-middleware]}}))

(def app
  (ring/ring-handler
   router
   (ring/routes
    (swagger-ui/create-swagger-ui-handler {:path "/"})
    (ring/create-default-handler))))

(defmethod ig/init-key :server [_ {:keys [handler port]}]
  (println (str "Server running on http://localhost:" port))
  (jetty/run-jetty handler {:port port :join? false}))

(defmethod ig/halt-key! :server [_ server]
  (.stop server))

(def system
  {:server {:handler app :port 3000}}) 

(defn -main []
  (ig/init system))