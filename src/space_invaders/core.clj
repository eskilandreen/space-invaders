(ns space-invaders.core
  (:use space-invaders.draw
        space-invaders.game
        space-invaders.player
        seesaw.core
        seesaw.color
        seesaw.keymap)
  (:gen-class))


(def window-width 640)
(def window-height 480)

(def game (atom (create-game)))

(defn draw-game [c g game]
  (draw-entities c g
                 (:player game)
                 (:alien-swarm game)
                 (:projectiles game)))

(defn do-tick [c g]
  (swap! game update-game)
  (draw-game c g @game))

(defn on-key-pressed [e]
  (let [key-char (.getKeyChar e)]
    (case key-char
      \a (swap! game move-player-left)
      \d (swap! game move-player-right)
      \newline (swap! game set-player-shooting true)
      "ign")))

(defn on-key-released [e]
  (let [key-char (.getKeyChar e)]
    (case key-char
      \a (swap! game stop-player-movement)
      \d (swap! game stop-player-movement)
      \newline (swap! game set-player-shooting false)
      "ign")))

(defn make-frame []
  (let [cvs (canvas :id :canvas
                    :background "#000000"
                    :paint do-tick)
        f (frame :title "Invaders, possibly from space!"
                 :width window-width
                 :height window-height
                 :on-close :dispose
                 :visible? true
                 :content cvs)]
    (move! f :to [0 0])
    (listen f :key-pressed on-key-pressed)
    (listen f :key-released on-key-released)
    (timer (fn [e] (repaint! cvs)) :delay 25)))

(defn -main [& args]
  (native!)
  (make-frame))
