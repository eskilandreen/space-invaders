(ns space-invaders.collisions)

(defn contains [x y top bottom left right]
  (and (< left x right ) (< top y bottom)))

(defn alien-contains-projectile [projectile alien alien-swarm]
  (let [x (:x projectile)
        y (:y projectile)
        top (+ (* 24 (:y alien)) (:y alien-swarm))
        bottom (+ top 20)
        left (+ (* 24 (:x alien)) (:x alien-swarm))
        right (+ left 20)]
    (contains x y top bottom left right)))

(defn handle-collisions [game]
  (let [alien-swarm (:alien-swarm game)
        aliens (:aliens alien-swarm)
        projectiles (:projectiles game)
        combinations (for [p projectiles a aliens] [p a])
        hit-filter (fn [[p a]] (alien-contains-projectile p a alien-swarm))
        hits (filter hit-filter combinations)
        destroyed-projectiles (set (map (fn [[p a]] p) hits))
        destroyed-aliens (set (map (fn [[p a]] a) hits))
        remaining-projectiles (filter (complement destroyed-projectiles) projectiles)
        remaining-aliens (filter (complement destroyed-aliens) aliens)
        game (assoc game :projectiles (doall remaining-projectiles))
        game (assoc-in game [:alien-swarm :aliens] (doall remaining-aliens))]
    game))

