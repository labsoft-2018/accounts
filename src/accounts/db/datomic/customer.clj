(ns accounts.db.datomic.customer
  (:require [schema.core :as s]
            [accounts.models.customer :as models.customer]
            [common-labsoft.protocols.datomic :as protocols.datomic]
            [common-labsoft.datomic.api :as datomic]
            [common-labsoft.time :as time]))

(s/defn insert! :- models.customer/Customer
  [customer :- models.customer/Customer, datomic :- protocols.datomic/IDatomic]
  (let [prepared-customer (assoc customer :customer/created-at (time/now))]
    (datomic/insert! :customer/id prepared-customer datomic)))

(s/defn lookup! :- models.customer/Customer
  [customer-id :- s/Uuid, datomic :- protocols.datomic/IDatomic]
  (datomic/lookup! :customer/id customer-id (datomic/db datomic)))

(s/defn find-by-user-id! :- models.customer/Customer
  [user-id :- s/Uuid, datomic :- protocols.datomic/IDatomic]
  (datomic/lookup! :customer/user-id user-id (datomic/db datomic)))
