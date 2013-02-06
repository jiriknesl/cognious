 (ns cognious.test.handler
   (:use midje.sweet
         ring.mock.request  
         cognious.state
         cognious.handler))

  	(fact
	  	(dosync
		    (send state (fn [old-state] '("test")))
		    (let [response (app (request :get "/state"))]
		      (:body response))) => "[\"test\"]"
		    
	  	)


