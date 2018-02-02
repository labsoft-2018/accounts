(ns accounts.wire.carrier
  (:require [schema.core :as s]
            [accounts.models.carrier :as models.carrier]
            [common-labsoft.schema :as schema]))

(def new-carrier-skeleton {:carrier/user-id {:schema s/Uuid :required true}
                           :carrier/name    {:schema s/Str :required true}
                           :carrier/email   {:schema s/Str :required true}
                           :carrier/vehicle {:schema models.carrier/Vehicle :required true}
                           :carrier/picture {:schema s/Str :required false}})
(s/defschema NewCarrier (schema/skel->schema new-carrier-skeleton))

