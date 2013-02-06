(ns cognious.routes.home
  (:use compojure.core hiccup.element)
  (:require [cognious.views.layout :as layout]
            [cognious.util :as util]
            [noir.response :as response]
            [cognious.state :as state]
            [cognious.analytics :as analytics]))

(defn home-page [] 
  (layout/common
    (util/md->html "/md/docs.md")))

(defn about-page []
  (layout/common
   "this is the story of cognious... work in progress"))

(defn query-page [params]
  (analytics/get-experiments @state/state (params :user) (params :experimentName) {} (analytics/get-time-params params)))

(defroutes home-routes 
  (GET "/" [] (home-page))
  (GET "/about" [] (response/json {:ano "ne"}))
  (GET "/state" [] (response/json @state/state))
  (GET "/query/:user/:experimentName" {params :params} (response/json (query-page params))))