(ns cognious.test.analytics-test
  (:use midje.sweet
        cognious.analytics))

(defn state [] [
		{:user {:id "tillda"} :experiment {:name "Glog" :params {} :timestamp 90}}		
		{:user {:id "jirka"} :experiment {:name "Abc" :params {} :timestamp 90}}
		{:user {:id "jirka"} :experiment {:name "Def" :params {:foo "bar"} :timestamp 110}}
		{:user {:id "jirka"} :experiment {:name "Def" :params {} :timestamp 120}}
	])

(fact
	"we can list users' experiments"
	(count (get-experiments (state) "jirka")) => 3
	(count (get-experiments (state) "jirka" "Def")) => 2
	(count (get-experiments (state) "jirka" "" {:foo "bar"})) => 1)

(fact
	"can parse dot-notation params"
	(get-params {"param.foo" "foovalue" "param.bar" "barvalue" "a" 1 "b" 2}) => {:foo "foovalue" :bar "barvalue"}
)


(fact
	"can parse dot-notation params from Compojure"
	(get-params {:param.foo "foovalue" :param.bar "barvalue" "a" 1 "b" 2}) => {:foo "foovalue" :bar "barvalue"}
)

(fact
	"can quitely convert to param name"
	(dotname->paramname "param.bar") => :bar 
	(dotname->paramname :param.bar) => :bar 
)

(fact
  "can filter users by timestamp"
  (count (get-experiments (state) "jirka" "" {} {:from 100 :to 120})) => 2)

(fact
	"get-time-params parses timestamps to ingegers or nils"
	(get-time-params {:a "foo" :b 123 :from "444" :to "556"}) => {:from 444 :to 556}
	)


