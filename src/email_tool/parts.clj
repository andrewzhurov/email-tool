(ns email-tool.parts
  (:require [clojure.string :as str]
            [clojure.spec :as s]
            [clojure.spec.gen :as gen]))


(s/def ::type :email-tool.specs/ns-keyword)
(s/def ::result-handler (s/fspec :args (s/cat :parts (s/coll-of (s/+ map?))
                                              :result (s/or :no-groups string?
                                                            :groups (s/coll-of (s/or :str string?
                                                                               :nil nil?))))

                                 :ret (s/or :to-merge map?
                                            :no-merge nil?)))
(s/def ::conflict-fn (s/fspec :args (s/cat :f any? :s any?)
                              :ret any?))

(s/def ::part (s/keys :req-un [:email-tool.specs/regex
                               ::result-handler]
                      :opt [::type
                            ::conflict-fn]))
(s/def ::parts (s/coll-of ::part))

;; precedence from top to bottom, custom go middle
(def parts-highest
  [{:type ::quoted-message
    :regex #"(On[\w \d\n,:<@.>]*wrote:)\n*(>+(.*\n*)+)+"
    :result-handler (fn [_ [full reply-metadata quoted]]
                      {:quoted-message {:full full
                                        :reply-metadata reply-metadata
                                        :quoted quoted}})}
   ])
(def parts-lowest
  (list
   {:type ::text
    :regex #".+"
    :result-handler (fn [_ text] {:text text})
    :conflict-fn (partial str/join "\n")}
   ))

(def signatures [{:type ::signature
                  :regex #"Sent from .+"
                  :result-handler (fn [_ res] {:signature res})
                  :conflict-fn nil}
                 ]) 
   
