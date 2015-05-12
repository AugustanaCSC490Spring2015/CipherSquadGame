# CipherSquadGame
Welcome to the Rat Race Game presented by CypherSquad

-This is a maze based game that presents the user with a movable mouse character. The object of the game is for the user 
  to drag the mouse character through the maze and collect as many powerups as possible in the shortest time possible. At the 
  end of every level your score is recorded (if it one of the top ten) and then you continue onto the next level.
  
The main contributors to this project have been:

  - Ethan Halsall   -   Designed the original maze generation / maze data structure
                    -   Created structural design of game source code (packages, inheritance, etc.)
                    -   Created maze data structure to line to bitmap code for displaying the maze
                    -   Created and designed Mouse class and Mouse subclasses
                    -   Bounded mouse to move only within space in the maze
                    -   Added rotation to mouse image such that it is pointing in its direction of motion
                    -   Created levelUp concept and code
                    -   Restructured power up code to reduce backwards dependencies and increase functionality
                    -   Fixed image scaling aspect ratios / UI reorganization
                    -   Points subtracted when timer increments / gives player something to race against
                    -   Added code to release all resources to prevent memory leaks
                    -   Added images to views
                    -   Designed Icon
  - Matthew Leja    -   Designed and implemented the powerups and such
  - Jamie Christian -   Created the first iteration of high scores
                    -   Created the timer and populated it to the activity bar
                    -   Retrieval and population of player score to the activity bar
                    -   Removal of option to choose maze generation algorithm from UI and code
                    -   Read when the mouse has reached the end of a maze so Ethan could later implement the automatic level-up
  - Danielle Bryant -   Reorganized code and added the directions activity
  - Jonathon Meyer  -   Added a settings popup and modified High scores to hold initials
