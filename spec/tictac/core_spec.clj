(ns tictac.core-spec
  (:use [speclj.core] [tictac.core])
  )

(describe "board api"
          (it "can detect legal and illegal moves"
              (should (legal-move? (rand 9) (empty-board)))
              (should-not (legal-move? 3
                                       (place :x 3 (empty-board) )))
              (should-not (legal-move? 100 (empty-board)))
               )
          (it "can have a winner"
              (should (not (winner? (empty-board))))
              (should= :X (winner? (make-game [0 1 2] [3 5 7])))
              (should= :O (winner? (make-game [3 6 7] [4 8 0])))
              )
          (it "can be a tie game"
              (should (not (tie? (empty-board))))
              (should (tie? (make-game [0 1 4 5 6 ] [2 3 7 8])))
              ))



