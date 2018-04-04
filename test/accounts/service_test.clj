(ns accounts.service-test
  (:require [midje.sweet :refer :all]
            [accounts.service :as ser]
            [common-labsoft.test-helpers :as th]
            [common-labsoft.time :as time]))

(th/with-service [ser/start! ser/stop!] [system service]
                 (th/with-token {:token/scopes #{"customer"} :token/sub "3B684A44-3994-49A3-8112-29C4651D78BD"}
                                (fact "Http Test"
                                      (th/request! service :get "/api/customers/3B684A44-3994-49A3-8112-29C4651D78BD") => {:error "NotFound"}))
                 (th/with-token {:token/scopes #{"auth"}}
                                (let [created-customer (th/request! service :post "/api/customers" {:user-id "700dc33d-7911-4f75-9b91-29d50f37f682"
                                                                                                    :name    "Rodrigo"
                                                                                                    :email   "rodrigo@mail.com"})]
                                  (fact "Create a customer" created-customer => #(not (nil? (:id %))))
                                  (fact "Get a customer" (th/request! service :get (str "/api/customers/" (:id created-customer))) => created-customer))

                                (let [created-carrier (th/request! service :post "/api/carriers" {:user-id "700dc33d-7911-4f75-9b91-29d50f37f682"
                                                                                                  :name    "Rodrigo"
                                                                                                  :email   "rodrigo@mail.com"
                                                                                                  :vehicle :carrier.vehicle/bicycle})]
                                  (fact "Create a carrier" created-carrier => #(not (nil? (:id %))))
                                  (fact "Get a carrier" (th/request! service :get (str "/api/carriers/" (:id created-carrier))) => created-carrier))))