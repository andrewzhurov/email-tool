(ns email-tool.specs
  (:require [clojure.string :as str]
            [clojure.spec :as s]
            [clojure.spec.gen :as gen]))


(s/def ::ns-keyword (s/and keyword? namespace))
(s/def ::regex (s/with-gen #(= java.util.regex.Pattern (type %))
                           #(gen/fmap re-pattern (gen/string))))
