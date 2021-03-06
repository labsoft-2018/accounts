(ns accounts.controllers.carrier
  (:require [schema.core :as s]
            [accounts.models.carrier :as models.carrier]
            [accounts.adapters.carrier :as adapters.carrier]
            [accounts.db.datomic.carrier :as datomic.carrier]
            [common-labsoft.protocols.datomic :as protocols.datomic]))

(s/defn new-carrier-account! :- models.carrier/Carrier
  [carrier :- models.carrier/Carrier, datomic :- protocols.datomic/IDatomic]
  (adapters.carrier/externalize (datomic.carrier/insert! (adapters.carrier/internalize carrier) datomic)))

(s/defn get-carrier! :- models.carrier/Carrier
  [carrier-id :- s/Uuid, datomic :- protocols.datomic/IDatomic]
  (adapters.carrier/externalize (datomic.carrier/lookup! carrier-id datomic)))

(s/defn find-by-user-id! :- models.carrier/Carrier
  [user-id :- s/Uuid, datomic :- protocols.datomic/IDatomic]
  (adapters.carrier/externalize (datomic.carrier/carrier-by-user-id! user-id datomic)))
