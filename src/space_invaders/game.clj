(ns space-invaders.game
  (:use space-invaders.player)
  (:use space-invaders.aliens)
  (:use space-invaders.projectiles)
  (:use space-invaders.shooting)
  (:use space-invaders.collisions))

(defrecord Game   [player alien-swarm projectiles])

(defn create-game []
  (Game.
      (create-player)
      (create-alien-swarm 100 100 5 10)
      '()))

(defn move-player-left [game]
  (assoc-in game [:player :speed] -3))

(defn move-player-right [game]
  (assoc-in game [:player :speed] 3))

(defn stop-player-movement [game]
  (assoc-in game [:player :speed] 0))

(defn set-player-shooting [game shooting]
  (assoc-in game [:player :shooting?] shooting))

(defn update-game [game]
  (-> game
    update-player
    update-projectiles
    update-aliens
    handle-shooting
    handle-collisions))
