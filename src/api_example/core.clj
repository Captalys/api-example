(ns api-example.core
  (:require [clojure.spec.alpha :as s]
            [compojure.api.exception :as ex]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :as http-response :refer :all]
            [schema.core :as schema]))

(s/def :person/name string?)
(s/def :person/age int?)
(s/def :person/country keyword?)

(s/def :person/spec (s/keys :req [:person/name
                            :person/age
                            :person/country]))

(schema/defschema person-spec
  {:person/name schema/Str
   :person/age schema/Int
   :person/country schema/Keyword})

(schema/defschema numbers-spec
  {:x schema/Int
   :y schema/Int})


(defmulti validation-handler (fn [approach f e type] approach))

(defmethod validation-handler :schema
  [_ f e type]
  (let [schema-data (map str (keys (:schema (ex-data e))))
        errors (:errors (ex-data e))]
    (f {:message "You probably sent a data different from the specification model expected"
        :expected-schema schema-data
        :found-errors errors
        :type type})))

(defmethod validation-handler :spec
  [_ f e type]
  (let [schema-data (->> (ex-data e)
                         :spec
                         :form
                         last
                         (map #(hash-map % (s/describe %)))
                         (into {}))
        errors (:pred (first (:clojure.spec.alpha/problems (:problems (ex-data e)))))]
    (f {:message "You probably sent a data different from the specification model expected"
        :expected-schema schema-data
        :found-errors errors
        :type type})))


(defn custom-validation-handler [f type]
  (fn [^Exception e data request]
    (let [data (ex-data e)
          approach (if (:schema data) :schema :spec)]
      ;; overcomplication to my example: I have some APIs using the `schema.spec` model and other using `clojure.spec`
      ;; I only wanted to provide a code example for both of them, therefore the dispatch defmulti below.
      (validation-handler approach f e type))))

(defn division-error-handler [f type]
  (fn [^Exception e data request]
    (f {:message "Dividing two numbers and you got it wrong!!...!!"
        :extra-message "Shame!"
        :type type})))

(def app
  (api
   {:swagger
    {:ui "/" :spec "/swagger.json"}
    :exceptions
    {:handlers
     {::ex/request-validation (custom-validation-handler http-response/internal-server-error "Request Validation Error")
      java.lang.ArithmeticException (division-error-handler http-response/internal-server-error "You suck")}}}

   (POST "/people-schema" []
     :summary "default approach"
     :body [people person-spec]
     (ok {:ping "pong-schema"}))

   (context "/people-spec" []
     (resource
      {:coercion :spec
       :post {:summary "data-driven approach"
              :parameters {:body-params :person/spec}
              :handler (fn [{people :body-params}]
                         (ok {:ping "pong-spec"}))}}))

   (GET "/wrong-addition" []
     :summary "You can add these two numbers. Try it!"
     :query-params [x :- schema/Int
                    y :- schema/Int]
     (ok (/ x 0)))))
