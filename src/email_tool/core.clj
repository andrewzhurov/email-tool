(ns email-tool.core
  (:require [email-tool.ast :as ast]
            [email-tool.tokens :as tok]))

(def full "I get proper rendering as well.\n\nSent from a magnificent torch of pixels\n\nOn Dec 16, 2011, at 12:47 PM, Corey Donohoe\n<reply@reply.github.com>\nwrote:\n\n> Was this caching related or fixed already?  I get proper rendering here.\n>\n> ![](https://img.skitch.com/20111216-m9munqjsy112yqap5cjee5wr6c.jpg)\n>\n> ---\n> Reply to this email directly or view it on GitHub:\n> https://github.com/github/github/issues/2278#issuecomment-3182418")

(defn email->clj [string]
  (->> string
       tok/tokenize
       ast/astnize))

