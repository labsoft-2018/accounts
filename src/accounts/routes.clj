(ns accounts.routes
  (:require [common-labsoft.pedestal.interceptors.auth :as int-auth]
            [common-labsoft.pedestal.interceptors.error :as int-err]
            [common-labsoft.pedestal.interceptors.adapt :as int-adapt]
            [common-labsoft.pedestal.interceptors.schema :as int-schema]
            [accounts.controllers.carrier :as controllers.carrier]
            [accounts.controllers.customer :as controllers.customer]
            [accounts.wire.carrier :as wire.carrier]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [io.pedestal.http.body-params :as body-params]
            [schema.core :as s]
            [accounts.models.carrier :as models.carrier]))

(defn new-customer
  [{{:keys [datomic]} :components customer :data}]
  {:status 200
   :body   (controllers.customer/new-customer-account! customer datomic)})

(defn new-carrier
  [{{:keys [datomic]} :components carrier :data}]
  (clojure.pprint/pprint carrier)
  {:status 200
   :body   (controllers.carrier/new-carrier-account! carrier datomic)})

(defn one-carrier
  [{{:keys [datomic]} :components customer-id :customer-id}]
  {:status 200
   :body   (controllers.carrier/get-carrier! customer-id datomic)})

(defn one-customer
  [{{:keys [datomic]} :components carrier-id :carrier-id}]
  {:status 200
   :body   (controllers.customer/get-customer! carrier-id datomic)})

(defn customer-by-user-id
  [{{:keys [datomic]} :components user-id :user-id}]
  {:status 200
   :body   (controllers.customer/customer-by-user-id! user-id datomic)})

(defn carrier-by-user-id
  [{{:keys [datomic]} :components user-id :user-id}]
  {:status 200
   :body   (controllers.carrier/find-by-user-id! user-id datomic)})

(defroutes routes
  [[["/" ^:interceptors [int-err/catch!
                         (body-params/body-params)
                         int-adapt/coerce-body
                         int-adapt/content-neg-intc
                         int-auth/auth
                         int-schema/coerce-output]
     ["/api"

      ["/users/:id" ^:interceptors [(int-adapt/path->uuid :id :user-id)]

       ["/customer" ^:interceptors [(int-auth/scopes-or? #{"auth"} (partial int-auth/auth-owner? :id '[[?entity :customer/user-id ?resource-id]
                                                                                                       [?entity :customer/id ?owner-id]]))]
        {:get [:customer-by-user-id customer-by-user-id]}]

       ["/carrier" ^:interceptors [(int-auth/scopes-or? #{"auth"} (partial int-auth/auth-owner? :id '[[?entity :carrier/user-id ?resource-id]
                                                                                                      [?entity :carrier/id ?owner-id]]))]
        {:get [:carrier-by-user-id carrier-by-user-id]}]]

      ["/customers"
       {:post [:new-customer ^:interceptors [(int-auth/allow-scopes? "auth")]
               new-customer]}

       ["/:id" ^:interceptors [(int-adapt/path->uuid :id :customer-id)
                               (int-auth/scopes-or? #{"auth"} (partial int-auth/auth-identity? :id))]
        {:get [:one-customer one-customer]}]]

      ["/carriers"
       {:post [:new-carrier ^:interceptors [(int-auth/allow-scopes? "auth")
                                            (int-schema/coerce wire.carrier/NewCarrier)]
               new-carrier]}

       ["/:id" ^:interceptors [(int-adapt/path->uuid :id :carrier-id)
                               (int-auth/scopes-or? #{"auth"} (partial int-auth/auth-identity? :id))]
        {:get [:one-carrier one-carrier]}]]]]]])
