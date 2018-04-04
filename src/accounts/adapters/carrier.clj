(ns accounts.adapters.carrier
  (:require [schema.core :as s]
            [accounts.models.carrier :as models.carrier]
            [clj-time.core :as time]
            [common-labsoft.misc :as misc]))

(s/defn internalize :- models.carrier/Carrier
  [request :- models.carrier/CarrierRequest]
  (misc/assoc-if {:carrier/user-id    (:user-id request)
                  :carrier/name       (:name request)
                  :carrier/email      (:email request)
                  :carrier/vehicle    (:vehicle request)
                  :carrier/created-at (time/now)}
                 :carrier/picture (:picture request)))

(s/defn externalize :- models.carrier/CarrierRequest
  [carrier :- models.carrier/Carrier]
  (misc/assoc-if {:id         (:carrier/id carrier)
                  :name       (:carrier/name carrier)
                  :email      (:carrier/email carrier)
                  :user-id    (:carrier/user-id carrier)
                  :vehicle    (:carrier/vehicle carrier)
                  :created-at (:carrier/created-at carrier)}
                 :picture (:carrier/picture carrier)))