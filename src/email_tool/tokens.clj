(ns email-tool.tokens
  (:require [clojure.string :as str]
            [email-tool.ast :as ast]))



(def tokens 
  (array-map  ;;:crlf #"\r\n"
              ;:crlf-to-test #"[\r\n]+"
              ;;:sig #"(\u2014|--|__|-\\w)|(^Sent from my (\\w+\\s*){1,3})"
              :signature #"^Sent from .+"
              ;;:quote-hdr #"^:etorw.*nO"
              ;;:quote-hdr-to-test #"^On.+wrote:"
              ;;:multi-quote-hdr #"(?!On.*On\\s.+?wrote:)(On\\s(.+?)wrote:)"
              :quoted #">+.*"
              :text-line #".+"
              ))

(def test-re (->> (str (:quoted tokens) "|" #".+")
                  re-pattern))

(def regex-tokens (->> tokens
                       vals
                       (str/join "|")
                       re-pattern)) 


(defn tokenize [string]
  (->> string 
       (re-seq regex-tokens)
       ))

(defn get-ready [msg]
  (-> msg
      (str/replace #"[\n\r]+" "\n")
      ))

