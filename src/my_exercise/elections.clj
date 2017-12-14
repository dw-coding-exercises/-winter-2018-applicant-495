(ns my-exercise.elections
  (:require [clojure.edn :as edn]))

(defn ocd-id-request-election
  "Fire off a request to the elections API with the given OCD-ID"
  [ocd-id]
  (edn/read-string (slurp (str "https://api.turbovote.org/elections/upcoming?district-divisions=" ocd-id))))

(defn extract-single-election-data
  "Extracts relevant election data for a *single* election from a provided EDN response from elections API"
  [edn-single-election]
  (let [extraction-fields [:date :description :website]]
    (select-keys edn-single-election extraction-fields)))

(defn extract-election-basic-data
  "Extracts information for all elections from a provided EDN response from election API"
  [edn-response]
  (map extract-single-election-data edn-response))

(defn election-data
  [ocd-ids]
  (->> ocd-ids
    (map ocd-id-request-election)
    (map extract-election-basic-data)
    (flatten)))

;; TODO: need some simple tests around extraction of data (specifically if we're missing data)
;; TODO: extract additional voting information (how to vote, how to register, etc)
