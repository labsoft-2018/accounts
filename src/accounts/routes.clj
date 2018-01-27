(ns accounts.routes
  (:require [common-labsoft.pedestal.interceptors.auth :as int-auth]
            [common-labsoft.pedestal.interceptors.error :as int-err]
            [common-labsoft.pedestal.interceptors.adapt :as int-adapt]
            [common-labsoft.pedestal.interceptors.schema :as int-schema]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [io.pedestal.http.body-params :as body-params]
            [schema.core :as s]))

(defn hello-world
  [request]
  {:status 200
   :body   {:res "Hello, World!"}})

(defroutes routes
  [[["/" ^:interceptors [int-err/catch!
                         (body-params/body-params)
                         int-adapt/coerce-body
                         int-adapt/content-neg-intc
                         int-auth/auth
                         int-schema/coerce-output]
     ["/api"

      ["/users/:id" ^:interceptors [(int-adapt/path->uuid :id :customer-id)]

       ["/customer" ^:interceptors [(int-auth/scopes-or? #{"auth"} (partial int-auth/auth-owner? :id '[[?entity :customer/user-id ?resource-id]
                                                                                                       [?entity :customer/id ?owner-id]]))]
        {:get [:customer-by-user-id hello-world]}]

       ["/carrier" ^:interceptors [(int-auth/scopes-or? #{"auth"} (partial int-auth/auth-owner? :id '[[?entity :carrier/user-id ?resource-id]
                                                                                                      [?entity :carrier/id ?owner-id]]))]
        {:get [:carrier-by-user-id hello-world]}]]

      ["/customers"
       {:post [:new-customer ^:interceptors [(int-auth/allow-scopes? "auth")]
               hello-world]}

       ["/:id" ^:interceptors [(int-adapt/path->uuid :id :customer-id)
                               (int-auth/scopes-or? #{"auth"} (partial int-auth/auth-identity? :id))]
        {:get [:one-customer hello-world]}]]

      ["/carriers"
       {:post [:new-carrier ^:interceptors [(int-auth/allow-scopes? "auth")]
               hello-world]}

       ["/:id" ^:interceptors [(int-adapt/path->uuid :id :carrier-id)
                               (int-auth/scopes-or? #{"auth"} (partial int-auth/auth-identity? :id))]
        {:get [:one-carrier hello-world]}]]]]]])
