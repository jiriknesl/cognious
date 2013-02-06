(ns cognious.test.state-test
  (:use midje.sweet
        cognious.state
        ))

(fact
	"add-record changes state accordingly"
	(.indexOf (add-record [] ...new-record...) ...new-record...) =not=> -1)