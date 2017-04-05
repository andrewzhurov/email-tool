(ns email-tool.ast
  (:require [clojure.string :as str]
            [email-tool.tokens :as tok]))

(def composing-types {:text-line {:composed-type :text
                                  :compose-fn (partial str/join "\n")}
                      :quoted {:composed-type :quoted-block
                               :compose-fn identity}
                      })
(defn- obtain-type [token]
  (some (fn [[k v]] (if (re-matches v token)
                        {:type k :value token} nil))
        tok/tokens))
(defn typinize
  "Typenizes all recognized tokens"
  [tokens]
  (map obtain-type tokens))
 
;; it may be used for combining simple types into complex one
;; e.g. text followed by signature to message
;; but for now we really don't need this
(defn compose
  "Composes separate entities into meaningful structure"
  [typed]
  (->> typed
       (partition-by :type)
       (map (fn [group] {:entities-type (:type (first group))
                         :entities group}))
       (map (fn [{:keys [entities-type entities]}]
              (let [{:keys [composed-type compose-fn]} (composing-types entities-type)]
                {:type composed-type
                 :value (compose-fn (map :value entities))})))
       ))

(def entity-keys [:type :state :content])
(def message-keys (conj entity-keys :signature))
(defn astnize! [entities]
  (zipmap message-keys [:message [] nil]))
(defn astnize [tokens]
  (->> tokens
       typinize
       compose
       astnize!
       ))

 




