(defproject clojure-crud "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [ring/ring-jetty-adapter "1.9.6"]
                 [metosin/reitit "0.7.0"]  ; Fixed version (no alpha)
                 [metosin/muuntaja "0.6.8"]
                 [integrant "0.8.0"]
                 [org.clojure/spec.alpha "0.2.194"]]
  :main clojure-crud.core)