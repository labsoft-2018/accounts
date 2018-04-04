(ns accounts.controllers.customer
  (:require [schema.core :as s]
            [accounts.models.customer :as models.customer]
            [accounts.adapters.customer :as adapters.customer]
            [accounts.db.datomic.customer :as datomic.customer]
            [common-labsoft.protocols.datomic :as protocols.datomic]))

(s/defn new-customer-account! :- models.customer/Customer
  [customer :- models.customer/CustomerRequest, datomic :- protocols.datomic/IDatomic]
  (adapters.customer/externalize (datomic.customer/insert! (adapters.customer/internalize customer) datomic)))

(s/defn get-customer! :- models.customer/Customer
  [customer-id :- s/Uuid, datomic :- protocols.datomic/IDatomic]
  (adapters.customer/externalize (datomic.customer/lookup! customer-id datomic)))

(s/defn customer-by-user-id! :- models.customer/Customer
  [user-id :- s/Uuid, datomic :- protocols.datomic/IDatomic]
  (adapters.customer/externalize (datomic.customer/find-by-user-id! user-id datomic)))
