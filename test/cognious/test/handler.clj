 (ns cognious.test.handler
   (:use midje.sweet
         ring.mock.request  
         cognious.state
         cheshire.core
         cognious.handler))

(def test-state [
	{:user {:id "tillda"} :experiment {:name "Glog" :params {} :timestamp 90}}		
	{:user {:id "jirka"} :experiment {:name "Abc" :params {} :timestamp 90}}
	{:user {:id "jirka"} :experiment {:name "Def" :params {:foo "bar"} :timestamp 110}}
	{:user {:id "jirka"} :experiment {:name "Def" :params {} :timestamp 120}}
])

(fact
	(dosync
	    (swap! state (fn [old-state] '("test")))
	    (let [response (app (request :get "/state"))]
	      (:body response))) => "[\"test\"]")

(fact
	(dosync
	    (swap! state (fn [old-state] test-state))
	    (let [response (app (request :get "/query/jirka/Def"))]
	      (count (parse-string (:body response)))))  => 2)

(fact
	(dosync
	    (swap! state (fn [old-state] test-state))
	    (let [response (app (request :get "/query/jirka/Def?from=115&to=120"))]
	      (count (parse-string (:body response)))))  => 1)


(fact
	(dosync
	    (swap! state (fn [old-state] test-state))
	    (let [response (app (request :get "/query/jirka/Def?params.foo=bar"))]
	      (count (parse-string (:body response)))))  => 1)
