// Board.java
package tetris;

import java.util.Arrays;

/**
 CS108 Tetris Board.
 Represents a Tetris board -- essentially a 2-d grid
 of booleans. Supports tetris pieces and row clearing.
 Has an "undo" feature that allows clients to add and remove pieces efficiently.
 Does not do any drawing or have any idea of pixels. Instead,
 just represents the abstract 2-d board.
*/
public class Board	{
	// Some ivars are stubbed out for you:
	private int width;
	private int height;
	private boolean[][] grid;
	private boolean DEBUG = true;
	boolean committed;
	private int[] widths;
	private int[] heights;
	private int maxHeight;
	
	// backup data
	private boolean[][] backupGrid;
	private int[] backupWidths;
	private int[] backupHeights;
	private int backupMaxHeight;
	
	
	// Here a few trivial methods are provided:
	
	/**
	 Creates an empty board of the given width and height
	 measured in blocks.
	*/
	public Board(int width, int height) {
		this.width = width;
		this.height = height;
		this.grid = new boolean[width][height];
		committed = true;
		
		// YOUR CODE HERE
		for (boolean[] row: grid) {
			 Arrays.fill(row, false);
		}	   
		this.widths = new int[this.height];
		this.heights = new int[this.width];
		Arrays.fill(this.widths, 0);
		Arrays.fill(this.heights, 0);
		this.maxHeight = 0;
		
		// print out test
		//System.out.println(this.toString());
		System.out.println("initialized this.widths[] >>>");
		System.out.println(printArray(this.widths));
		
		// initialize backup data
		this.backupWidths = new int[this.height];
		this.backupHeights = new int[this.width];
		this.backupGrid = new boolean[width][height];
		backup();
	}
	
	/**
	 Backup date before change the board status, use for undo()
	*/
	private void backup() {
		System.arraycopy(this.widths, 0, this.backupWidths, 0, this.height);
		System.arraycopy(this.heights, 0, this.backupHeights, 0, this.width);
		this.backupMaxHeight = this.maxHeight;
		for(int i = 0; i < this.width; i++) {
			System.arraycopy(this.grid[i], 0, this.backupGrid[i], 0, this.height);
		}
	}
	
	
	/**
	 Returns the width of the board in blocks.
	*/
	public int getWidth() {
		return width;
	}
	
	
	/**
	 Returns the height of the board in blocks.
	*/
	public int getHeight() {
		return height;
	}
	
	
	/**
	 Returns the max column height present in the board.
	 For an empty board this is 0.
	*/
	public int getMaxHeight() {	 
//		int maxHeight = 0;
//		for(int i = 0; i < this.width; i++) {
//			if(this.heights[i] > maxHeight) {
//				maxHeight = this.heights[i];
//			}
//		}
		return maxHeight; // YOUR CODE HERE
	}
	
	
	/**
	 Checks the board for internal consistency -- used
	 for debugging.
	*/
	public void sanityCheck() {
		if (DEBUG) {
			// YOUR CODE HERE
			try{
				int[] sanCheckWidths = new int[this.height];
				int[] sanCheckHeights = new int[this.width];
				int sanCheckMaxHeight = 0;

				Arrays.fill(sanCheckWidths, 0);
				Arrays.fill(sanCheckHeights, 0);

				for(int i = 0; i < this.width; i++) {
					for(int j = 0; j < this.height; j++) {
						if(this.getGrid(i, j)) {
							sanCheckWidths[j]++;
							sanCheckHeights[i] = j + 1;
							if(sanCheckMaxHeight < sanCheckHeights[i]) {
								sanCheckMaxHeight = sanCheckHeights[i];
							}
						}
					}
				}
				// compare to the class private variable
				if(sanCheckMaxHeight != this.maxHeight) {
					System.out.println("maxHeight wrong >>>");
					System.out.println("current = " + String.valueOf(sanCheckMaxHeight) + "this.maxHeight = " + String.valueOf(this.maxHeight));
					throw new RuntimeException("maxHeight is wrong!");
				}
				if(!Arrays.equals(sanCheckWidths, this.widths)) {
					System.out.println("widths[] wrong >>>");
					System.out.println("current widths[] >>>");
					System.out.println(printArray(sanCheckWidths));
					System.out.println("this.widths[] >>>");
					System.out.println(printArray(this.widths));
					throw new RuntimeException("widths[] is wrong!");
				}
				if(!Arrays.equals(sanCheckHeights, this.heights)) {
					System.out.println("heights[] wrong >>>");
					System.out.println("current heights[] >>>");
					System.out.println(printArray(sanCheckHeights));
					System.out.println("this.heights[] >>>");
					System.out.println(printArray(this.heights));
					throw new RuntimeException("heights[] is wrong!");
				}
			} catch(Exception except) {
				except.printStackTrace(); 
			}
		}
	}
	
	/**
	 Given a piece and an x, returns the y
	 value where the piece would come to rest
	 if it were dropped straight down at that x.
	 
	 <p>
	 Implementation: use the skirt and the col heights
	 to compute this fast -- O(skirt length).
	*/
	public int dropHeight(Piece piece, int x) {
		int y = 0;
		for(int i = 0; i < piece.getWidth(); i++) {
			int dropY = this.heights[x + i] - piece.getSkirt()[i];
			if(dropY > y) {
				y = dropY;
			}
		}
		return y; // YOUR CODE HERE
	}
	
	
	/**
	 Returns the height of the given column --
	 i.e. the y value of the highest block + 1.
	 The height is 0 if the column contains no blocks.
	*/
	public int getColumnHeight(int x) {
		return this.heights[x]; // YOUR CODE HERE
	}
	
	
	/**
	 Returns the number of filled blocks in
	 the given row.
	*/
	public int getRowWidth(int y) {
		 return this.widths[y]; // YOUR CODE HERE
	}
	
	
	/**
	 Returns true if the given block is filled in the board.
	 Blocks outside of the valid width/height area
	 always return true.
	*/
	public boolean getGrid(int x, int y) {
		return this.grid[x][y]; // YOUR CODE HERE
	}
	
	
	public static final int PLACE_OK = 0;
	public static final int PLACE_ROW_FILLED = 1;
	public static final int PLACE_OUT_BOUNDS = 2;
	public static final int PLACE_BAD = 3;
	
	/**
	 Attempts to add the body of a piece to the board.
	 Copies the piece blocks into the board grid.
	 Returns PLACE_OK for a regular placement, or PLACE_ROW_FILLED
	 for a regular placement that causes at least one row to be filled.
	 
	 <p>Error cases:
	 A placement may fail in two ways. First, if part of the piece may falls out
	 of bounds of the board, PLACE_OUT_BOUNDS is returned.
	 Or the placement may collide with existing blocks in the grid
	 in which case PLACE_BAD is returned.
	 In both error cases, the board may be left in an invalid
	 state. The client can use undo(), to recover the valid, pre-place state.
	*/
	public int place(Piece piece, int x, int y) {
		// flag !committed problem
		if (!committed) throw new RuntimeException("place commit problem");
		// set commited to false!!!!!!!!
		this.committed = false;
		int result = PLACE_OK;
		// backup board data
		backup();
		
		//System.out.println("before place()");
		//System.out.println(this.toString());
		// YOUR CODE HERE 
		for(TPoint tp : piece.getBody()) {
			int coordX = tp.x + x;
			int coordY = tp.y + y;
			if(!inBounds(coordX, coordY)) {
				//System.out.println("return PLACE_OUT_BOUNDS");
				return PLACE_OUT_BOUNDS;
			}
			if(this.grid[coordX][coordY] == true) {
				//System.out.println("return PLACE_BAD");
				return PLACE_BAD;
			}
			this.widths[coordY]++;
			if(this.heights[coordX] < coordY + 1) {
				this.heights[coordX] = coordY + 1;
				if(this.maxHeight < this.heights[coordX]) {
					this.maxHeight = this.heights[coordX];
				}
			}
			if(this.widths[coordY] == this.width) {
				//System.out.println("return PLACE_ROW_FILLED");
				if(result != PLACE_OUT_BOUNDS && result != PLACE_BAD) {
					result = PLACE_ROW_FILLED; //  what about 2 rows filled?
				}
			}
			// fill the grid with the piece
			this.grid[coordX][coordY] = true;
		}
		// print board
		//System.out.println("after place(), before return");
		//System.out.println(this.toString());
		//System.out.println("return: " + result);
		
		// sanity check if there is no error
		if(result != PLACE_OUT_BOUNDS && result != PLACE_BAD) {
			sanityCheck();
		}
		return result;
	}
	
	private boolean inBounds(int x, int y) {
		return x >= 0 && x < this.width && y >= 0 && y < this.height;
	}
	
	
	/**
	 Deletes rows that are filled all the way across, moving
	 things above down. Returns the number of rows cleared.
	*/
	public int clearRows() {
		// backup data
		// backup();
		
		int rowsCleared = 0;
		// client could call or not call place() before call clearRows()
		this.committed = false;
		// YOUR CODE HERE
		int toRow = 0;
		for(int fromRow = 0; fromRow < this.maxHeight; fromRow++) {
			if(this.widths[fromRow] < this.width) {
				for(int i = 0; i < this.width; i++) {
					grid[i][toRow] = grid[i][fromRow];
				}
				this.widths[toRow] = this.widths[fromRow];
				toRow++;
			} else {
				rowsCleared++;
			}
		}
		// fill the removed rows with false grid
		for(int j = toRow; j < this.maxHeight; j++) {
			for(int i = 0; i < this.width; i++) {
				grid[i][j] = false;
			}
			this.widths[j] = 0;
		}
		// update this.heights[] and this.maxHeight ????
		for(int i = 0; i < this.width; i++) {
			for(int j = this.heights[i]; j >= 0; j--) {
				if(this.grid[i][j]) {
					//System.out.println("return true");
					this.heights[i] = j + 1;
					break;
				} else { /* System.out.println("return false"); */}
				this.heights[i] = 0;
			}
		}
		this.maxHeight = calcMaxHeight();
		
		System.out.println("after clear row");
		//System.out.println(this.toString());
		sanityCheck();
		return rowsCleared;
	}

	/**
	 Get the local maxHeight from this.heights;
	*/
	private int calcMaxHeight() {
		int max = 0;
		for(int i = 0; i < this.width; i++) {
			if(max < this.heights[i]) {
				max = this.heights[i];
			}
		}
		return max;
	}

	/**
	 Reverts the board to its state before up to one place
	 and one clearRows();
	 If the conditions for undo() are not met, such as
	 calling undo() twice in a row, then the second undo() does nothing.
	 See the overview docs.
	*/
	public void undo() {
		// YOUR CODE HERE
		System.arraycopy(this.backupWidths, 0, this.widths, 0, this.height);
		System.arraycopy(this.backupHeights, 0, this.heights, 0, this.width);
		this.maxHeight = this.backupMaxHeight;
		for(int i = 0; i < this.width; i++) {
			System.arraycopy(this.backupGrid[i], 0, this.grid[i], 0, this.height);
		}
		this.committed = true;
	}
	
	
	/**
	 Puts the board in the committed state.
	*/
	public void commit() {
		committed = true;
	}


	
	/*
	 Renders the board state as a big String, suitable for printing.
	 This is the sort of print-obj-state utility that can help see complex
	 state change over time.
	 (provided debugging utility) 
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder();
		for (int y = height-1; y>=0; y--) {
			buff.append('|');
			for (int x=0; x<width; x++) {
				if (getGrid(x,y)) buff.append('+');
				else buff.append(' ');
			}
			buff.append("|\n");
		}
		for (int x=0; x<width+2; x++) buff.append('-');
		return(buff.toString());
	}
	
	private String printArray(int[] array) {
		StringBuilder buff = new StringBuilder();
		for(int i = 0; i < array.length; i++) {
			buff.append(array[i]);
			buff.append(' ');
		}
		return (buff.toString());
	}
}


