(ns my-exercise.ocd-id-test
  (:require [clojure.test :refer :all]
            [my-exercise.ocd-id :refer :all]))

(deftest address->ocd-ids-test
  (testing "empty input results in entire U.S."
    (is (=
           ["ocd-division/country:us"]
           (address->ocd-ids {}))))
  (testing "blank input results entire U.S."
    (is (=
           ["ocd-division/country:us"]
           (address->ocd-ids {:street ""
                              :street-2 ""
                              :city ""
                              :state ""
                              :zip ""}))))
  (testing "alabama state results in entire U.S. plus alabama"
    (is (=
           ["ocd-division/country:us"
            "ocd-division/country:us/state:al"]
           (address->ocd-ids {:street ""
                              :street-2 ""
                              :city ""
                              :state "AL"
                              :zip ""}))))
  (testing "place works as well"
    (is (=
           ["ocd-division/country:us"
            "ocd-division/country:us/state:al"
            "ocd-division/country:us/state:al/place:birmingham"]
           (address->ocd-ids {:street ""
                              :street-2 ""
                              :city "Birmingham"
                              :state "AL"
                              :zip ""})))))

(deftest ocd-state-test
  (testing "empty state -> nil"
    (is (nil? (ocd-state "" ""))))
  (testing "state builds valid ocd-id"
    (is (=
           (str default-ocd-id "/state:co")
           (ocd-state default-ocd-id "CO")))))

(deftest ocd-place-test
  (testing "empty place -> nil"
    (is (nil? (ocd-place "" ""))))
  (testing "place builds valid ocd-id"
    (is (=
           "prefix/place:denver"
           (ocd-place "prefix" "Denver")))))

(deftest ocd-county-test
  (testing "empty count -> nil"
    (is (nil? (ocd-county "" ""))))
  (testing "county builds valid ocd-id"
    (is (=
          "prefix/county:arapahoe"
          (ocd-county "prefix" "Arapahoe")))))
