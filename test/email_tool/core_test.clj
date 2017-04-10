(ns email-tool.core-test
  (:require [clojure.test :refer :all]
            [email-tool.core :as emailt]))

(defn obtain-email [name]
  (slurp (str "test/files/emails/" name ".txt")))


(deftest "email_1_4"
  (let [email (obtain-email "email_1_4")]
    (testing "full traverse"
      (is (= (emailt/email->clj email)
             {:type :message
              :state []
              :signature "Sent from a magnificent torch of pixels"
              :content [{:type :text
                         :state []
                         :content "I get proper rendering as well.\n"}
                        {:type :message
                         :state [:quotted]
                         :signature "Reply to this email directly or view it on GitHub:\nhttps://github.com/github/github/issues/2278#issuecomment-3182418\n"
                         :content [{:type :text
                                    :state []
                                    :content "Was this caching related or fixed already?  I get proper rendering here.\n\n![](https://img.skitch.com/20111216-m9munqjsy112yqap5cjee5wr6c.jpg)"}]}]})))
    (testing "custom parser; amount of symbols in email"
      (is (= (emailt/email->clj (clojure.string/replace email #"\n" "")
                                [{#".+" (fn [matched] {:symbol-amount (count matched)})}])
             {:type :message
              :state []
              :symbol-amount 148})))))
