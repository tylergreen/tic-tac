(ns tictac.core)

(defn empty-board []
  (vector (repeat 9 :empty)))

(defn place [mark position board]
  (assoc board position mark))

(defn place-many [mark positions board]
  (reduce (fn [b pos] (place mark pos b)) board positions))

(defn make-game
  [xs os]
     (place-many :O os
      (place-many :X xs (empty-board))))

(defn rows [board]
  (partition 3 board))

(defn transpose [matrix]
  (apply map vector matrix))

(defn columns [board]
  (transpose (rows board)))

(defn diagonals [board]
  (map
  #(map (partial nth board) %)
  [[0 4 8]
   [2 4 6]]
  ))

(defn legal-move? [i board]
  (and (>= 8 i) (>= i 0)
       (= :empty (nth board i))))

(defn empty-board []
  (vec (repeat 9 :empty)))

(defn print-board [board]
  (doall
   (map prn (rows
             (map-indexed #(str %1 %2)
                          (replace {:empty :_} board))))))

(defn streak? [seq]
  (and
    (apply = seq)
    (not (= :empty (first seq)))
    (first seq)))

(defn winner? [board]
  (some streak?
        (mapcat #(% board) [rows columns diagonals])))

(defn tie? [board]
  (and 
   (not-any? #{:empty} board)
   (not (winner? board))))

(defn valid-input? [input]
  (some #{input} (range 10)))

(defn read-input []
  (try (read)
       (catch Exception e false)))

(defn other [player]
  (if (= player :X)
    :O
    :X ))

(defn welcome-message []
  (doall (map prn ["Welcome to 2 Player TikTak(Cloj)"
            "Enter a number 0 - 8"
            "Board layout is as follows:"
            "0 1 2"
            "3 4 5"
            "6 7 8"])))

(defn play-game []
  (welcome-message)
  (loop [board (empty-board) player :X]
    (print-board board)
    (cond (winner? board) (str "Player " (other player) " Wins")
          (tie? board) "Cat Game!"
          true (let [input (read-input)]
                 (if (legal-move? input board)
                   (recur (place player input board) (other player))
                   (do (prn "Illegal move, try again!")
                       (recur board player)))))))

                
(defn -main []
  (play-game))

