(ns cognious.analytics)
(use 'clojure.data)

(defn is-param? [name]
  (.startsWith (name 0) "param."))

(defn get-params [params-kv]
  (apply merge (map (fn [item] {(keyword (subs (item 0) 6)) (item 1)}) (filter is-param? params-kv))))

(defn get-time-params [params]
	(apply merge (map #((println %) {(% 0) (Integer. (% 1))}) (select-keys params [:from :to]))))

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