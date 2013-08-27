(ns space-invaders.collisions
  (:require [clojure.set :as s]))

(defn contains [x y top bottom left right]
  (and (< left x right ) (< top y bottom)))

(defn alien-contains-projectile? [projectile alien alien-swarm]
  (let [x (:x projectile)
        y (:y projectile)
        top (+ (* 24 (:y alien)) (:y alien-swarm))
        bottom (+ top 20)
        left (+ (* 24 (:x alien)) (:x alien-swarm))
        right (+ left 20)]
    (contains x y top bottom left right)))

(defn- alien-hits [game]
  (->> (for [p (:projectiles game)
             a (:aliens game)
             :when (alien-contains-projectile? p a (:alien-swarm game))] 
         [p a])
       (reduce (fn [hits [p a]] (s/union hits #{p a})) #{})))

(defn handle-collisions [game]
  (let [hits (alien-hits game)
        missed? #(complement (contains? hits %))]
    (-> game
        (assoc :projectiles (filter missed? (:projectiles game)))
        (assoc-in [:alien-swarm :aliens] (filter missed? (:aliens game))))))

