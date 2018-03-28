  (ns accounts.service-test
  (:require [midje.sweet :refer :all]
            [accounts.service :as ser]
            [common-labsoft.test-helpers :as th]))

(th/with-service [ser/start! ser/stop!] [system service]
  (th/with-token {:token/scopes #{"customer"} :token/sub "3B684A44-3994-49A3-8112-29C4651D78BD"}
    (fact "Http Test"
      (th/request! service :get "/api/customers/3B684A44-3994-49A3-8112-29C4651D78BD") => {:error "NotFound"})))
