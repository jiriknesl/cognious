(defproject cognious "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [lib-noir "0.3.5"]
                 [compojure "1.1.3"]
                 [hiccup "1.0.2"]
                 [ring-server "0.2.5"]                 
                 [com.taoensso/timbre "1.2.0"]
                 [com.taoensso/tower "1.2.0"]
                 [cheshire "5.0.1"]
                 [markdown-clj "0.9.19"]
                 [ring-json-params "0.1.3"]
                 [midje "1.5-alpha9"]]  
  :plugins [[lein-ring "0.8.0"][lein-midje "3.0-alpha4"]]
  :ring {:handler cognious.handler/war-handler
         :init    cognious.handler/init
         :destroy cognious.handler/destroy}  
  :profiles
  {:production {:ring {:open-browser? false 
                       :stacktraces?  false 
                       :auto-reload?  false}}
   :dev {:dependencies [[ring-mock "0.1.3"]
                        [ring/ring-devel "1.1.0"]]}}
  :min-lein-version "2.0.0")