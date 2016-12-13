Classes:

Bag.java
Bag.java reads a file with all the different letters and spits out a random character when getNextTile() is called. It then removes that character from the list.

Board.java
Board.java manages the Board GUI. It does not store any state. It is in charge of drawing all the different tiles on the board as well as the multiplier tiles.

BoardBuffer.java
BoardBuffer manages the boardstate. It detects whether the configuration of tiles on the board is legal, adds and removes letters from the internal state of the baord 
(as opposed to the board GUI, as in Board.java), and detects the current word being made, among a few other things.

BoardState.java
BoardState is just a Tile[][] that stores the currentBoardState and also initializes a new Board at the beginning of every game.

Dictionary.java
Dictionary is in charge of opening the Dictionary file at the beginning of every game as well as adding new words to the dictionary.

Game.java
Game.java is where everything is put together and initialized.

InfoPanel.java
InfoPanel is just a JPanel with a specified dimension.

Inventory.java
Inventory stores both GUI and Game State, storing which tiles are in a person's inventory at any given time. It also functions a player instance, keeping track of score and name.

Mode.java
Mode is the enum for which mode the game is in right now-- DEFAULT or PLACETILE.

Square.java
Square is the unit that is drawn on the board. It should be a JPanel(maybe) but it isn't, for some weird stupid reason.

Tests.java
JUnit tests

Tile.java
Tile.java is the game-state version of Square.java. It should honestly be merged with Square.java, however by the time I realized that, I was halfway into writing the program and I had no time to.
