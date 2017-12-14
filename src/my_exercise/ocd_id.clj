(ns my-exercise.ocd-id
  (:require [clojure.string :as s]))

(def default-ocd-id "ocd-division/country:us")

(defn ocd-state
  "Create a state-level ocd-id"
  [prefix state]
  (if (and (some? prefix)
           (some? state)
           (= (count state) 2))
    (str prefix "/state:" (s/lower-case state))))

(defn ocd-place [prefix place]
  "Create a place-level ocd-id"
  (if (and (some? prefix)
           (some? place)
           (not (empty? place)))
    (str prefix "/place:" (s/lower-case place))))

(defn address->ocd-ids
  "Take the raw request params and try to build ocd id's out of it"
  [params]
  (let [state-prefix (ocd-state default-ocd-id (:state params))]
    (filter some?
      [default-ocd-id
       state-prefix
       (ocd-place state-prefix (:city params))])))

;; TODO: use google's geocoding api to get county
