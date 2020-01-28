(ns api-example.core-test
  (:require [clojure.test :refer :all]
            [api-example.core :as core]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

(deftest example-based-test-1
  (testing "I want to explain how an example based test works"
    (let [data [{:id 1 :values 20 :letters "ok"}
                {:id 2 :values 22 :letters "foi"}]]
      (is (= (core/sum-all-values data) 42)))))


;;; Property Based Tests [generative testing]
;;; 1. We need to find a property to test:  [hypothesis 1] - The sum of the numbers [if all positive] cannot be smaller than 0
;;; 2. We need to make a model to generate examples for us.
(def gen-input-data (gen/not-empty (gen/vector (gen/hash-map
                                                :id gen/small-integer
                                                :values gen/pos-int
                                                :letters gen/string))))

(comment
  (gen/sample gen-input-data 2)
  ;; => ([{:id 1, :values 1, :letters ""}] [{:id -1, :values 1, :letters ""} {:id 0, :values 2, :letters ""}])
  )

;;; 3. We need to wire the property and test our hypothesis with the `for/all` property
(defspec property-based-test-1 300
  (prop/for-all [data gen-input-data]
                (let [result (core/sum-all-values data)]
                  (> result 0))))

;;; 4. This property will run for 300 randomly generated examples. Can you catch the bug here? But your example-based one, just passed!
;;; 5. After running all these iterations, our hypothesis still holds? Yes? Jump to the next test =P
