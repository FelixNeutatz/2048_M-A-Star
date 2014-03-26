2048_M-A-Star
=============

This project provides an algorithm to solve the popular game 2048: http://gabrielecirulli.github.io/2048/

The idea is based on the A* search algorithm. There are implemented 3 cost functions:

1. sum of all tiles in the playing field
2. number of all unassigned tiles in the playing field
3. average value of an occupied tile

Up to now the third cost function seems to be most promising.

By increasing the depth (level) of the A* search tree the classification can be improved, but it needs much more time, too.


I have found a bug which made the game simpler than the real game. So it will take more time to make the algorithm smarter.


To check the result of my algorithm you can run https://github.com/FelixNeutatz/2048_M-A-Star/blob/master/src/AStar.java
which solves a random sample game.


You can also play the game by running https://github.com/FelixNeutatz/2048_M-A-Star/blob/master/src/Game2048.java
