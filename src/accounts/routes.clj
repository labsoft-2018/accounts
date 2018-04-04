(ns accounts.routes
  (:require [common-labsoft.pedestal.interceptors.auth :as int-auth]
            [common-labsoft.pedestal.interceptors.error :as int-err]
            [common-labsoft.pedestal.interceptors.adapt :as int-adapt]
            [common-labsoft.pedestal.interceptors.schema :as int-schema]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [io.pedestal.http.body-params :as body-params]
            [accounts.models.carrier :as models.carrier]
            [accounts.models.customer :as models.customer]
            [accounts.db.datomic.carrier :as datomic.carrier]
            [accounts.controllers.customer :as controllers.customer]
            [accounts.controllers.carrier :as controllers.carrier]
            [accounts.adapters.customer :as adapters.customer]
            [accounts.adapters.carrier :as adapters.carrier]
            [schema.core :as s]
            [accounts.db.datomic.customer :as datomic.customer]))

(defn hello-world
  [request]
  {:status 200
   :body   {:res "Hello, World!"}})

(defn get-one-customer-route
  [{{:keys [crypto token datomic http sqs]} :components customer-id :customer-id}]
  {:status 200
   :body   (controllers.customer/get-customer! customer-id datomic)})

(defn create-customer-route
  [{{:keys [crypto token datomic http sqs]} :components customer :data}]
  {:status 200
   :body   (controllers.customer/new-customer-account! customer datomic)})

(defn get-one-carrier-route
  [{{:keys [crypto token datomic http sqs]} :components carrier-id :carrier-id}]
  {:status 200
   :body   (controllers.carrier/get-carrier! carrier-id datomic)})

(defn create-carrier-route
  [{{:keys [crypto token datomic http sqs]} :components carrier :data}]
  {:status 200
   :body   (controllers.carrier/new-carrier-account! carrier datomic)})


(defroutes routes
  [[["/" ^:interceptors [int-err/catch!
                         (body-params/body-params)
                         int-adapt/coerce-body
                         int-adapt/content-neg-intc
                         int-auth/auth
                         int-schema/coerce-output]
     ["/api"

      ["/customers"
       {:post [:new-customer ^:interceptors [(int-auth/allow-scopes? "auth")
                                             (int-schema/coerce models.customer/CustomerRequest)]
               create-customer-route]}

       ["/:id" ^:interceptors [(int-adapt/path->uuid :id :customer-id)
                               (int-auth/scopes-or? #{"auth"} (partial int-auth/auth-identity? :id))]
        {:get [:one-customer get-one-customer-route]}]]

      ["/carriers"
       {:post [:new-carrier ^:interceptors [(int-auth/allow-scopes? "auth")
                                            (int-schema/coerce models.carrier/CarrierRequest)]
               create-carrier-route]}

       ["/:id" ^:interceptors [(int-adapt/path->uuid :id :carrier-id)
                               (int-auth/scopes-or? #{"auth"} (partial int-auth/auth-identity? :id))]
        {:get [:one-carrier get-one-carrier-route]}]]]]]])
