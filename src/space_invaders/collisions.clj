(ns space-invaders.collisions)

(defn- alien-contains-projectile?* [x y top bottom left right]
  (and (<= left x right ) (<= top y bottom)))

(defn alien-contains-projectile? [projectile alien alien-swarm]
  (let [x (:x projectile)
        y (:y projectile)
        top (+ (* 24 (:y alien)) (:y alien-swarm))
        bottom (+ top 20)
        left (+ (* 24 (:x alien)) (:x alien-swarm))
        right (+ left 20)]
    (alien-contains-projectile?* x y top bottom left right)))

(defn alien-hits [game]
  (let [alien-swarm (:alien-swarm game)]
    (->> (for [p (:projectiles game)
               a (:aliens alien-swarm)
               :when (alien-contains-projectile? p a alien-swarm)]
           [p a])
         (reduce (fn [hits [p a]] (conj hits p a)) #{}))))

(defn handle-collisions [game]
  (let [filter-hits-fn (partial remove (alien-hits game))]
    (-> game
        (update-in [:projectiles] filter-hits-fn)
        (update-in [:alien-swarm :aliens] filter-hits-fn))))

