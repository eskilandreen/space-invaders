(ns space-invaders.projectiles)

(defn move-projectile [p]
  (assoc p :y (- (:y p) 5)))

(defn update-projectiles [game]
  (assoc game :projectiles (map move-projectile (:projectiles game))))
