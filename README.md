# Slide-Puzzle
Slide Puzzle

Sliding Number Puzzle is a classic sliding block puzzle game. Numbered blocks are randomly arranged on an
NxM board with a single free space that a numbered block can be slid into either the north, south, east, or 
west. The goal is to arrange the numbers numerically in ascending order from left to right and then top
to bottom with the empty space as the last cell in the board. 

An example of an unsolved 3x3 board may look like: 
- 3 7 8 
- 6 2 .
- 3 5 4
 
An example of a solved 3x3 board looks like:
- 1 2 3
- 4 5 6
- 7 8 .

(The '.' represents the empty space in the board)

The puzzle is solved using a Breadth First Search. 

There is a plain-text user interface (PTUI) that operates in the command line, 
which allows the user to interact with the puzzle. The game will support five
user operations: 

- Hint: When hinting, if the current state of the puzzle is solvable, the puzzle should advance to the next step in the solution with an indication that it was successful. Otherwise the puzzle should remain in the same state and indicate there is no solution.

- Load: When loading, the user will provide the path and name of a puzzle file for the game to load. If the file is readable it is guaranteed to be a valid puzzle file and the new puzzle file should be loaded and displayed, along with an indication of success. If the file cannot be read, an error message should be displayed and the previous puzzle file should remain loaded

- Select: 
Selection works in two parts.
For the first selection, the user should be able to select a cell on the board with the intention of selecting the piece at that location. If there is a piece there, there should be an indication and selection should advance to the second part. Otherwise if there is no piece there an error message should be displayed and selection has ended.
For the second selection, the user should be able to select another cell on the board with the intention of moving the previously selected piece to this location. If the move is valid, it should be made and the board should be updated and with an appropriate indication. If the move is invalid, and error message should be displayed.

- Quit: The user can quit from and end the program.

- Reset: The previously loaded file should be reloaded, causing the puzzle to return to its initial state. An indication of the reset should be informed to the user.

The project also has a graphical user interface (GUI) component to it as well, which uses the Model-View-Controller Design.
The GUI is presented to the user and displays the following information:

- The title of the window which indicicates the game name ("Slide GUI")
- A status area to display interaction messages from the model
- A playing area to display the board as a grid of buttons
- A collection of buttons that can be used for the hint, load, and reset behaviors 
