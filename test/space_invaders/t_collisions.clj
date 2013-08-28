(ns space-invaders.t_collisions
  (:use midje.sweet
        space-invaders.collisions))

(defn- create-swarm [aliens]
  {:aliens aliens :x 0 :y 0})

(facts
  "alien-hits"
  (fact "no aliens are hit with projectiles -> empty"
        (alien-hits {:alien-swarm (create-swarm [{:x 0 :y 0}]) 
                     :projectiles [{:x 0 :y 90}]}) 
        => #{})
  (fact "aliens hit with projectiles -> #{alien projectile}"
        (let [coord {:x 0 :y 0}]
          (alien-hits {:alien-swarm (create-swarm [coord])
                       :projectiles [coord]})
          => #{coord})))

(facts
  "handle-collisions"
  (fact "the hit alien is removed"
        (let [hit-alien {:x 0 :y 0}
              missed-alien {:x 30 :y 30}
              game+ (handle-collisions {:alien-swarm (create-swarm [hit-alien
                                                                    missed-alien])
                                        :projectiles [hit-alien]})]
          (-> game+ :alien-swarm :aliens) => [missed-alien]
          (-> game+ :projectiles) => [])))

