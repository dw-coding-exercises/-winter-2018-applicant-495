(ns my-exercise.search
  (:require [hiccup.page :refer [html5]]
            [my-exercise.ocd-id :as ocd]
            [my-exercise.elections :as elections]))

(defn header [_]
  [:head
   [:meta {:charset "UTF-8"}]
   [:meta {:name "viewport"
           :content "width=device-width, initial-scale=1.0, maximum-scale=1.0"}]
   [:title "My next election(s)"]
   [:link {:rel "stylesheet" :href "default.css"}]])

(defn request-output [request]
  [:div {:class "raw-request-output"}
   [:li (str (:params request))]
   [:li (doall (ocd/address->ocd-ids (:params request)))]
   ;; FYI still carrying around the anti-forgery token at this point
   [:li (doall (elections/election-data (ocd/address->ocd-ids (:params request))))]])

(defn page [request]
  (html5
    (header request)
    (request-output request)))
