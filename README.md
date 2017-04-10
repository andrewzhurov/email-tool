# email-tool

Clojure library designed for you to feel comfortable with emails.

A parser for your messages, don't use regex every time you need to strip some info, parse it once and feel comfy the rest.


Since there is no strict specifications on email, we can't cover all the cases of custom syntax, that's why you're in need of ability to supply your custom parse rules.

With that in mind the lib was designed, it basicly provides you the flow of parsing, but doesn't dictate `what to parse` and `how to store` the data.
"Gimme your parse rules, get the structure"

`what` will handle your regex, and piece of info stripped by it will be passed to your func to handle - `how`

Simple as that: 
- text of email split to pieces by your regexes 
- each piece got digested by according func


func takes an info piece, returns new structure.
But one more arg, so sweet recursive structures could happen: parse rules.

Anyway it's all speced, so feel free to look.


*Word of caution: I'm not very strong on the topic, encourage you to check out other, more mature solutions, first.*


## Usage

install locally
`$ lein install`
```
(require 'email-tool.core :as etool)

(etool/email->clj email-string <your_rules>)
```

## TODO

- fix result-handler func spec
- make optional parts arg to result-handler