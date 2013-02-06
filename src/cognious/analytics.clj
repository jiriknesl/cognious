(ns cognious.analytics)
(use 'clojure.data)

(defn starts-with-param? [name]
	(println name)
  (.startsWith (name 0) "param."))

(defn is-param? [name]
	(case (keyword? input)
		true (starts-with-param (subs (str input) 1))
		false (starts-with-param input)
	)
)

(defn dotname->paramname [input]
	(case (keyword? input)
		true (keyword (subs (str input) 7))
		false (keyword (subs (str input) 6))
	)
)

(defn get-params [params-kv]
  (merge {} (apply merge (map (fn [item] {(dotname->paramname (item 0)) (item 1)}) (filter is-param? params-kv)))))

(defn get-time-params [params]
	(merge {} (apply merge (map (fn [row] {(row 0) (Integer. (row 1))}) (select-keys params [:from :to])))))

(defn get-experiments 
	([state user-id] (get-experiments state user-id "" {}))
	([state user-id experiment-name] (get-experiments state user-id experiment-name {}))
	([state user-id experiment-name params] (get-experiments state user-id experiment-name params {}))
	([state user-id experiment-name params time-params] (filter (fn [row] 
		(and
			(= user-id ((row :user) :id))
			(or (= experiment-name "") (= experiment-name ((row :experiment) :name)))
			(= (nth (diff ((row :experiment) :params) params) 1) nil)		 
			(or (= (time-params :from) nil) (>= (-> row :experiment :timestamp) (time-params :from)))
			(or (= (time-params :to) nil) (<= (-> row :experiment :timestamp) (time-params :to)))
		)) state)
	)
)