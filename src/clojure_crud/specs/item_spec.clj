(ns clojure-crud.specs.item-spec
  (:require [clojure.spec.alpha :as s]))

(s/def ::id string?)
(s/def ::name string?)
(s/def ::price number?)
(s/def ::item (s/keys :req-un [::id ::name ::price]))