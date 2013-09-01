(ns space-invaders.player)

(defrecord Player [x y speed shooting? cooldown])

(defn update-player-position [player]
  (let [x (:x player)
        speed (:speed player)]
    (assoc player :x (+ x speed))))

(defn update-player-cooldown [player]
  (let [cooldown (:cooldown player)]
    (assoc player :cooldown (max (dec cooldown) 0))))

(defn update-player [game]
  (assoc game :player
    (-> (:player game)
      update-player-position
      update-player-cooldown)))

(defn create-player []
  (Player. 400 400 0 false 0))
