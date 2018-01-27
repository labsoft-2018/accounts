(ns accounts.models.carrier
  (:require [schema.core :as s]
            [common-labsoft.schema :as schema]))

(def vehicle-types #{:carrier.vehicle/motorcycle :carrier.vehicle/car :carrier.vehicle/bicycle})
(s/defschema Vehicle (apply s/enum vehicle-types))

(def carrier-skeleton {:carrier/id         {:schema s/Uuid :id true}
                       :carrier/user-id    {:schema s/Uuid :required true :unique true}
                       :carrier/name       {:schema s/Str :required true}
                       :carrier/email      {:schema s/Str :required true}
                       :carrier/vehicle    {:schema Vehicle :required true}
                       :carrier/created-at {:schema s/Str :required true}})
(s/defschema Carrier (schema/skel->schema carrier-skeleton))
