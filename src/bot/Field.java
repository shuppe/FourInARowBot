// Copyright 2015 theaigames.com (developers@theaigames.com)

//    Licensed under the Apache License, Version 2.0 (the "License");
//    you may not use this file except in compliance with the License.
//    You may obtain a copy of the License at

//        http://www.apache.org/licenses/LICENSE-2.0

//    Unless required by applicable law or agreed to in writing, software
//    distributed under the License is distributed on an "AS IS" BASIS,
//    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//    See the License for the specific language governing permissions and
//    limitations under the License.
//	
//    For the full copyright and license information, please view the LICENSE
//    file that was distributed with this source code.

package bot;

/**
 * Field class
 * <p>
 * Field class that contains the field status data and various helper functions.
 *
 * @author Jim van Eeden <jim@starapple.nl>, Joost de Meij <joost@starapple.nl>
 */

public class Field {
    public int mLastColumn = 0;
    private int[][] mBoard;
    private int mCols = 0, mRows = 0;
    private String mLastError = "";
    private long moveList = 0;
    private final static int EMPTY_CELL = 0;

    public Field(int columns, int rows) {
        mBoard = new int[columns][rows];
        mCols = columns;
        mRows = rows;
        clearBoard();
    }

    public int[][] getBoard() {
        int[][] board = new int[mRows][mCols];
        for (int x = 0; x < mRows; x++) {
            for (int y = 0; y < mCols; y++) {
                board[x][y] = mBoard[y][x];
            }
        }

        return board;
    }

    public long getMoveList() {
        return moveList;
    }

    public void setMoveList(long moveList) {
        this.moveList = moveList;
    }

    private void addMove(int move) {
        if (this.moveList == 0)
            this.moveList = move;
        else
            this.moveList = this.moveList * 10 + move;
    }

    /**
     * Sets the number of columns (this clears the board)
     *
     * @param cols : int cols
     */
    public void setColumns(int cols) {
        mCols = cols;
        mBoard = new int[mCols][mRows];
    }

    /**
     * Sets the number of rows (this clears the board)
     *
     * @param rows : int rows
     */
    public void setRows(int rows) {
        mRows = rows;
        mBoard = new int[mCols][mRows];
    }

    /**
     * Clear the board
     */
    public void clearBoard() {
        for (int x = 0; x < mCols; x++) {
            for (int y = 0; y < mRows; y++) {
                mBoard[x][y] = EMPTY_CELL;
            }
        }
    }

    /**
     * Adds a disc to the board
     *
     * @param column : Column where to slide disc
     * @param disc   args : player's disc to place
     * @return : true if disc fits, otherwise false
     */
    public Boolean addDisc(int column, int disc) {
        mLastError = "";
        if (column < mCols) {
            for (int y = mRows - 1; y >= 0; y--) { // From bottom column up
                if (mBoard[column][y] == EMPTY_CELL) {
                    mBoard[column][y] = disc;
                    mLastColumn = column;
                    addMove(column);
                    return true;
                }
            }
            mLastError = "Column is full.";
        } else {
            mLastError = "Move out of bounds.";
        }
        return false;
    }

    /**
     * Initialise field from comma separated String
     *
     * @param s :
     */
    public void parseFromString(String s) {
        s = s.replace(';', ',');
        String[] r = s.split(",");
        int counter = 0;
        for (int y = 0; y < mRows; y++) {
            for (int x = 0; x < mCols; x++) {
                mBoard[x][y] = Integer.parseInt(r[counter]);
                counter++;
            }
        }
    }

    /**
     * Returns the current piece on a given column and row
     *
     * @param column
     * @param row
     * @return
     */
    public int getDisc(int column, int row) {
        return mBoard[column][row];
    }

    /**
     * Returns whether a slot is open at given column
     *
     * @param column : int column
     * @return : Boolean
     */
    public Boolean isValidMove(int column) {
        return (mBoard[column][0] == EMPTY_CELL);
    }

    /**
     * Returns reason why addDisc returns false
     *
     * @return : reason why addDisc returns false
     */
    public String getLastError() {
        return mLastError;
    }

    @Override
    /**
     * Creates comma separated String with every cell.
     * @param args :
     * @return : String
     */
    public String toString() {
        String r = "";
        int counter = 0;
        for (int y = 0; y < mRows; y++) {
            for (int x = 0; x < mCols; x++) {
                if (counter > 0) {
                    r += ",";
                }
                r += mBoard[x][y];
                counter++;
            }
        }
        return r;
    }

    /**
     * Checks whether the field is full
     *
     * @return : Returns true when field is full, otherwise returns false.
     */
    public boolean isFull() {
        for (int col = 0; col < mCols; col++)
            if (mBoard[col][0] == EMPTY_CELL)	// Just check last row for empty cells
                return false; 					// At least one cell is not filled

        return true;							// No empty cell
    }

    /**
     * Checks whether the given column is full
     *
     * @return : Returns true when given column is full, otherwise returns false.
     */
    public boolean isColumnFull(int column) {
        return (mBoard[column][0] != EMPTY_CELL);
    }

    /**
     * @return : Returns the number of columns in the field.
     */
    public int getNrColumns() {
        return mCols;
    }

    /**
     * @return : Returns the number of rows in the field.
     */
    public int getNrRows() {
        return mRows;
    }
}
