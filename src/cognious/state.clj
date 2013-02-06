(ns cognious.state)

(def state (agent '()))

(defn add-record [state new-record]
	(conj state new-record))