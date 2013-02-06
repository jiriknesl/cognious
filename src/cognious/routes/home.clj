(ns cognious.routes.home
  (:use compojure.core hiccup.element)
  (:require [cognious.views.layout :as layout]
            [cognious.util :as util]
            [noir.response :as response]
            [cognious.state :as state]))

(defn home-page [] 
  (layout/common
    (util/md->html "/md/docs.md")))

(defn about-page []
  (layout/common
   "this is the story of cognious... work in progress"))

(defroutes home-routes 
  (GET "/" [] (home-page))
  (GET "/about" [] (response/json {:ano "ne"}))
  (GET "/state" [] (response/json @state/state)))