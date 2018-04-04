(ns accounts.models.customer
  (:require [schema.core :as s]
            [common-labsoft.time :as time]
            [common-labsoft.schema :as schema]))

(def customer-skeleton {:customer/id         {:schema s/Uuid :id true}
                        :customer/user-id    {:schema s/Uuid :required true :unique true}
                        :customer/name       {:schema s/Str :required true}
                        :customer/email      {:schema s/Str :required true :unique true}
                        :customer/picture    {:schema s/Str :required false}
                        :customer/created-at {:schema time/LocalDateTime :required true}})
(s/defschema Customer (schema/skel->schema customer-skeleton))

(def customer-request-skeleton {:id                  {:schema s/Uuid :required false}
                                :name                {:schema s/Str :required true}
                                :user-id             {:schema s/Uuid :required true}
                                :email               {:schema s/Str :required true}
                                :picture             {:schema s/Str :required false}
                                :created-at          {:schema time/LocalDateTime :required false}})

(s/defschema CustomerRequest (schema/skel->schema customer-request-skeleton))