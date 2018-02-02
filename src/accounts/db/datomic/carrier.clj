(ns accounts.db.datomic.carrier
  (:require [schema.core :as s]
            [accounts.models.carrier :as models.carrier]
            [common-labsoft.protocols.datomic :as protocols.datomic]
            [common-labsoft.time :as time]
            [common-labsoft.datomic.api :as datomic]))

(s/defn insert! :- models.carrier/Carrier
  [carrier :- models.carrier/Carrier, datomic :- protocols.datomic/IDatomic]
  (let [prepared-carrier (assoc carrier :carrier/created-at (time/now))]
    (datomic/insert! :carrier/id prepared-carrier datomic)))

(s/defn lookup! :- models.carrier/Carrier
  [carrier-id :- s/Uuid, datomic :- protocols.datomic/IDatomic]
  (datomic/lookup! :carrier/id carrier-id (datomic/db datomic)))

(s/defn carrier-by-user-id! :- models.carrier/Carrier
  [user-id :- s/Uuid, datomic :- protocols.datomic/IDatomic]
  (datomic/lookup! :carrier/user-id user-id (datomic/db datomic)))
