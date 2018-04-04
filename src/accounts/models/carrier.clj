(ns accounts.models.carrier
  (:require [schema.core :as s]
            [common-labsoft.schema :as schema]
            [common-labsoft.time :as time]))

(def vehicle-types #{:carrier.vehicle/motorcycle :carrier.vehicle/car :carrier.vehicle/bicycle})
(s/defschema Vehicle (apply s/enum vehicle-types))

(def carrier-skeleton {:carrier/id         {:schema s/Uuid :id true}
                       :carrier/user-id    {:schema s/Uuid :required true :unique true}
                       :carrier/name       {:schema s/Str :required true}
                       :carrier/email      {:schema s/Str :required true}
                       :carrier/vehicle    {:schema Vehicle :required true}
                       :carrier/picture    {:schema s/Str :required false}
                       :carrier/created-at {:schema time/LocalDateTime :required true}})
(s/defschema Carrier (schema/skel->schema carrier-skeleton))

(def carrier-request-skeleton {:id         {:schema s/Uuid :required false}
                               :user-id    {:schema s/Uuid :required true}
                               :name       {:schema s/Str :required true}
                               :email      {:schema s/Str :required true}
                               :vehicle    {:schema Vehicle :required true}
                               :picture    {:schema s/Str :required false}
                               :created-at {:schema time/LocalDateTime :required false}})

(s/defschema CarrierRequest (schema/skel->schema carrier-request-skeleton))