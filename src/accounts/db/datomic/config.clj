(ns accounts.db.datomic.config
  (:require [accounts.models.carrier :as models.carrier]
            [accounts.models.customer :as models.customer]))

(def settings {:schemas [models.customer/customer-skeleton
                         models.carrier/carrier-skeleton]
               :enums   [models.carrier/vehicle-types]})
