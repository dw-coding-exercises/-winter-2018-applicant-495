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
   [:h2 "Current Elections"]
   (for [election (elections/election-data (ocd/address->ocd-ids (:params request)))]
     [:div
       [:h3 (:description election)]
       [:p "Voting Deadline: " (:date election)]
       [:a {:href (:website election)} "Election Website"]])])
       ; TODO: add additional information about each election here (registration, id requirements, etc)

(defn page [request]
  (html5
    (header request)
    (request-output request)))
