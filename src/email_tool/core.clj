(ns email-tool.core
  (:require [email-tool.parts :as part]
            [email-tool.parse :refer [parse]]
            [clojure.spec :as s]))

(def test-text "I get proper rendering as well.\n\nSent from a magnificent torch of pixels\n\nOn Dec 16, 2011, at 12:47 PM, Corey Donohoe\n<reply@reply.github.com>\nwrote:\n\n> Was this caching related or fixed already?  I get proper rendering here.\n>\n> ![](https://img.skitch.com/20111216-m9munqjsy112yqap5cjee5wr6c.jpg)\n>\n> ---\n> Reply to this email directly or view it on GitHub:\n> https://github.com/github/github/issues/2278#issuecomment-3182418")

(def test-rule {:regex #".*"
                :result-handler (fn [parts info]
                                  (merge struct {:text-lines [info]}))
                :conflict-fn into})

(s/fdef email->clj
        :args (s/cat :mail string?
                     :parts :email-tool.parts/parts)
        :ret map?)
(defn email->clj [string & parts]
  (let [final-parts (distinct (flatten parts)) #_(concat part/parts-highest (flatten parts) part/parts-lowest)]
    (println final-parts)
    (-> (parse string final-parts)
        )))
