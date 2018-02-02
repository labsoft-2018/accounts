(ns accounts.wire.customer
  (:require [schema.core :as s]
            [common-labsoft.schema :as schema]))

(def new-customer-skeleton {:customer/user-id {:schema s/Uuid :required true}
                            :customer/name    {:schema s/Str :required true}
                            :customer/email   {:schema s/Str :required true}
                            :customer/picture {:schema s/Str :required false}})

(s/defschema NewCustomer (schema/skel->schema new-customer-skeleton))