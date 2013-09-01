(ns space-invaders.draw
  (:use seesaw.core
        seesaw.color
        seesaw.graphics))

(def my-style (style :foreground "#FFFFFF"
                     :stroke (stroke :width 1 :cap :round)))


(defn draw-alien [c g alien]
  (let [x (* (:x alien) 24)
        y (* (:y alien) 24)
        w 20
        h 20]
  (draw g (rect x y w h) my-style)))

(defn draw-alien-swarm [c g alien-swarm]
  (let [x-offset (:x alien-swarm)
        y-offset (:y alien-swarm)
        aliens   (:aliens alien-swarm)
        draw-one (fn [alien] (draw-alien c g alien))]
    (translate g x-offset y-offset)
    (doall
      (map draw-one
           (filter :alive? aliens)))
    (translate g (* -1 x-offset) (* -1 y-offset))))

(defn draw-player [c g player]
  (let [x (:x player)
        y (:y player)]
    (draw g (rect x y 30 30) my-style)))

(defn draw-projectile [c g projectile]
  (let [x (:x projectile)
        y (:y projectile)
        w 1
        h 5]
    (draw g (rect x y w h) my-style)))

(defn draw-projectiles [c g projectiles]
  (let [draw-one (fn [p] (draw-projectile c g p))]
    (doall (map draw-one projectiles))))

(defn draw-entities [c g player alien-swarm projectiles]
  (draw-player c g player)
  (draw-alien-swarm c g alien-swarm)
  (draw-projectiles c g projectiles))
