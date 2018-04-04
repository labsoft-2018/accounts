(ns accounts.adapters.customer
  (:require [accounts.models.customer :as models.customer]
            [schema.core :as s]
            [common-labsoft.time :as time]
            [common-labsoft.misc :as misc]))


(s/defn internalize :- models.customer/Customer
  [request :- models.customer/CustomerRequest]
  (misc/assoc-if {:customer/user-id     (:user-id request)
                  :customer/name        (:name request)
                  :customer/email       (:email request)
                  :customer/created-at  (time/now)}
                 :customer/picture (:picture request)))

(s/defn externalize :- models.customer/CustomerRequest
  [customer :- models.customer/Customer]
  (misc/assoc-if {:id         (:customer/id customer)
                  :name       (:customer/name customer)
                  :email      (:customer/email customer)
                  :user-id    (:customer/user-id customer)
                  :created-at (:customer/created-at customer)}
                 :picture (:customer/picture customer)))