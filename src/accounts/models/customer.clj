(ns accounts.models.customer
  (:require [schema.core :as s]
            [common-labsoft.time :as time]
            [common-labsoft.schema :as schema]))

(def customer-skeleton {:customer/id         {:schema s/Uuid :id true}
                        :customer/user-id    {:schema s/Uuid :required true}
                        :customer/name       {:schema s/Str :required true}
                        :customer/email      {:schema s/Str :required true}
                        :customer/picture    {:schema s/Str :required false}
                        :customer/created-at {:schema time/LocalDateTime :required true}})
(s/defschema Customer (schema/skel->schema customer-skeleton))
