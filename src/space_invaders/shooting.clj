(ns space-invaders.shooting)

(defrecord Projectile [x y])

(defn set-player-cooldown [player]
  (assoc player :cooldown 5))

(defn create-new-projectile [player]
   (Projectile. (:x player) (:y player)))

(defn ready-to-shoot [player]
  (and (:shooting? player)
       (= (:cooldown player) 0)))

(defn shoot [game]
  (assoc game
    :player (set-player-cooldown (:player game))
    :projectiles (conj (:projectiles game) (create-new-projectile (:player game)))))

(defn handle-shooting [game]
  (if (ready-to-shoot (:player game))
    (shoot game)
    game))

