(ns cognious.state)

(def state (atom '()))

(defn add-record [state new-record]
	(conj state new-record))

(defn load-stored-state [& args] (load-file "resources/private/storage.clj")) 