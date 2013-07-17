(ns space-invaders.aliens)

(defrecord Alien  [x y alive?])
(defrecord AlienBox [x y rows cols aliens speed direction time-to-turn direction-cycle])

(defn decrease-time-to-turn [alien-swarm]
  (assoc alien-swarm :time-to-turn
         (dec
           (:time-to-turn alien-swarm))))

(defn next-direction [alien-swarm]
  (let [direction-cycle (:direction-cycle alien-swarm)
        [direction speed time-to-turn] (first direction-cycle)]
    (assoc alien-swarm
           :direction direction
           :speed speed
           :time-to-turn time-to-turn
           :direction-cycle (rest direction-cycle))))

(defn move-aliens [alien-swarm]
  (let [direction (:direction alien-swarm)
        speed (:speed alien-swarm)
        x (:x alien-swarm)
        y (:y alien-swarm)]
    (case direction
      :right (assoc alien-swarm :x (+ x speed))
      :left (assoc alien-swarm :x (- x speed))
      :down (assoc alien-swarm :y (+ y speed)))))

(defn update-aliens-direction [alien-swarm]
  (let [time-to-turn (:time-to-turn alien-swarm)]
    (if (or
          (nil? time-to-turn)
          (zero? time-to-turn))
      (next-direction alien-swarm)
      alien-swarm)))

(defn update-aliens [game]
  (assoc game :alien-swarm
    (-> (:alien-swarm game)
      update-aliens-direction
      decrease-time-to-turn
      move-aliens)))

(defn create-alien-swarm [pos-x pos-y rows cols]
  (let [aliens (for [x (range cols)
                     y (range rows)] (Alien. x y true))]
    (AlienBox.
      pos-x
      pos-y
      rows
      cols
      aliens
      nil
      nil
      nil
      (cycle ['(:right 2 100)
              '(:down 2 20)
              '(:left 2 100)
              '(:down 2 20)]))))
