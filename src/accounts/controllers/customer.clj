(ns accounts.controllers.customer
  (:require [schema.core :as s]
            [accounts.models.customer :as models.customer]
            [accounts.db.datomic.customer :as datomic.customer]
            [common-labsoft.protocols.datomic :as protocols.datomic]))

(s/defn new-customer-account! :- models.customer/Customer
  [customer :- models.customer/Customer, datomic :- protocols.datomic/IDatomic]
  (datomic.customer/insert! customer datomic))

(s/defn get-customer! :- models.customer/Customer
  [customer-id :- s/Uuid, datomic :- protocols.datomic/IDatomic]
  (datomic.customer/lookup! customer-id datomic))

(s/defn customer-by-user-id! :- models.customer/Customer
  [user-id :- s/Uuid, datomic :- protocols.datomic/IDatomic]
  (datomic.customer/find-by-user-id! user-id datomic))
