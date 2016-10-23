package bot;


/**
 * Created by sylvain on 16-10-03.
 */
public class MiniMaxSolver implements MoveSolver {

    private Field field;
    private int[][] board;
    private int height;
    private int width;
    private int difficulty;
    private int[][] bestMove = {
            {-1, -1}
    };


    public MiniMaxSolver(Field field) {
        this.field = field;
        difficulty = HARD;
    }

    @Override
    public int nextMove(int player, int level) {

        board = field.getBoard();
        height = field.getNrRows();
        width = field.getNrColumns();
        minimax(0, -1000000, 1000000);
        return bestMove[0][1];

    }

    /**
     * @return
     */
/*
    public int[][] getWinningMoves() {
        int[][] winningMoves = new int[4][2];
        //Check for vertical win
        for (int c = 0; c < 7; c++) {
            for (int r = 5; r >= 3; r--) {
                if (board[r][c] == 1 && board[r - 1][c] == 1 && board[r - 2][c] == 1 && board[r - 3][c] == 1) {
                    winningMoves[0][0] = r;
                    winningMoves[0][1] = c;
                    winningMoves[1][0] = r - 1;
                    winningMoves[1][1] = c;
                    winningMoves[2][0] = r - 2;
                    winningMoves[2][1] = c;
                    winningMoves[3][0] = r - 3;
                    winningMoves[3][1] = c;
                    return winningMoves;
                } else if (board[r][c] == 2 && board[r - 1][c] == 2 && board[r - 2][c] == 2 && board[r - 3][c] == 2) {
                    winningMoves[0][0] = r;
                    winningMoves[0][1] = c;
                    winningMoves[1][0] = r - 1;
                    winningMoves[1][1] = c;
                    winningMoves[2][0] = r - 2;
                    winningMoves[2][1] = c;
                    winningMoves[3][0] = r - 3;
                    winningMoves[3][1] = c;
                    return winningMoves;
                }
            }
        }

        //check for horizontal win
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c <= 3; c++) {
                if (board[r][c] == 1 && board[r][c + 1] == 1 && board[r][c + 2] == 1 && board[r][c + 3] == 1) {
                    winningMoves[0][0] = r;
                    winningMoves[0][1] = c;
                    winningMoves[1][0] = r;
                    winningMoves[1][1] = c + 1;
                    winningMoves[2][0] = r;
                    winningMoves[2][1] = c + 2;
                    winningMoves[3][0] = r;
                    winningMoves[3][1] = c + 3;
                    return winningMoves;
                } else if (board[r][c] == 2 && board[r][c + 1] == 2 && board[r][c + 2] == 2 && board[r][c + 3] == 2) {
                    winningMoves[0][0] = r;
                    winningMoves[0][1] = c;
                    winningMoves[1][0] = r;
                    winningMoves[1][1] = c + 1;
                    winningMoves[2][0] = r;
                    winningMoves[2][1] = c + 2;
                    winningMoves[3][0] = r;
                    winningMoves[3][1] = c + 3;
                    return winningMoves;
                }
            }
        }

        //check for diagonal win
        for (int r = 0; r <= 2; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c] == 1 && board[r + 1][c + 1] == 1 && board[r + 2][c + 2] == 1 && board[r + 3][c + 3] == 1) {
                    winningMoves[0][0] = r;
                    winningMoves[0][1] = c;
                    winningMoves[1][0] = r + 1;
                    winningMoves[1][1] = c + 1;
                    winningMoves[2][0] = r + 2;
                    winningMoves[2][1] = c + 2;
                    winningMoves[3][0] = r + 3;
                    winningMoves[3][1] = c + 3;
                    return winningMoves;
                } else if (board[r][c] == 2 && board[r + 1][c + 1] == 2 && board[r + 2][c + 2] == 2 && board[r + 3][c + 3] == 2) {
                    winningMoves[0][0] = r;
                    winningMoves[0][1] = c;
                    winningMoves[1][0] = r + 1;
                    winningMoves[1][1] = c + 1;
                    winningMoves[2][0] = r + 2;
                    winningMoves[2][1] = c + 2;
                    winningMoves[3][0] = r + 3;
                    winningMoves[3][1] = c + 3;
                    return winningMoves;
                }
            }
        }

        for (int r = 0; r <= 2; r++) {
            for (int c = 6; c >= 3; c--) {
                if (board[r][c] == 1 && board[r + 1][c - 1] == 1 && board[r + 2][c - 2] == 1 && board[r + 3][c - 3] == 1) {
                    winningMoves[0][0] = r;
                    winningMoves[0][1] = c;
                    winningMoves[1][0] = r + 1;
                    winningMoves[1][1] = c - 1;
                    winningMoves[2][0] = r + 2;
                    winningMoves[2][1] = c - 2;
                    winningMoves[3][0] = r + 3;
                    winningMoves[3][1] = c - 3;
                    return winningMoves;
                } else if (board[r][c] == 2 && board[r + 1][c - 1] == 2 && board[r + 2][c - 2] == 2 && board[r + 3][c - 3] == 2) {
                    winningMoves[0][0] = r;
                    winningMoves[0][1] = c;
                    winningMoves[1][0] = r + 1;
                    winningMoves[1][1] = c - 1;
                    winningMoves[2][0] = r + 2;
                    winningMoves[2][1] = c - 2;
                    winningMoves[3][0] = r + 3;
                    winningMoves[3][1] = c - 3;
                    return winningMoves;
                }
            }
        }
        return winningMoves;
    }
*/
    public int minimax(int depth, int alpha, int beta) {
        return (maxMove(depth, alpha, beta));
    }

    public int maxMove(int depth, int alpha, int beta) {
        int max = -500025;
        int m = testForWinner();
        if (m != 0) {
            return m;
        }
        if (depth >= 6 || isDraw()) {
            return analysis();
        }

        int[][] lMoves = findAllLegalMoves();

        for (int move = 0; move < 7; move++) {
            if (lMoves[move][0] == -1 || lMoves[move][1] == -1) {
                continue;
            } else {
                board[lMoves[move][0]][lMoves[move][1]] = 2;
                int temp = minMove(depth + 1, alpha, beta);
                board[lMoves[move][0]][lMoves[move][1]] = 0;
                if (temp > max) {
                    max = temp;
                    if (depth == 0) {
                        bestMove[0][0] = lMoves[move][0];
                        bestMove[0][1] = lMoves[move][1];
                    }
                }
                if (temp > alpha) {
                    alpha = temp;
                }
                if (alpha >= beta) {
                    return alpha;
                }
            }
        }
        return max;
    }

    public int minMove(int depth, int alpha, int beta) {
        int min = 500025;
        int m = testForWinner();
        if (m != 0) {
            return m;
        }
        if (depth >= 6 || isDraw()) {
            return analysis();
        }

        int[][] lMoves = findAllLegalMoves();

        for (int move = 0; move < 7; move++) {
            if (lMoves[move][0] == -1 || lMoves[move][1] == -1) {
                continue;
            } else {
                board[lMoves[move][0]][lMoves[move][1]] = 1;
                int temp = maxMove(depth + 1, alpha, beta);
                board[lMoves[move][0]][lMoves[move][1]] = 0;
                if (temp < min) {
                    min = temp;
                    if (depth == 0) {
                        bestMove[0][0] = lMoves[move][0];
                        bestMove[0][1] = lMoves[move][1];
                    }
                }
                if (temp < beta) {
                    beta = temp;
                }
                if (alpha >= beta) {
                    return beta;
                }
            }
        }
        return min;
    }

    public int analysis() {
        int whoWon = 0;

        if (difficulty == MEDIUM || difficulty == HARD) {
            // Horizontal one moves Player 1
            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 1 && board[row][col + 1] == 0 && board[row][col + 2] == 0 && board[row][col + 3] == 0) {
                        whoWon = whoWon - 125;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 0 && board[row][col + 1] == 1 && board[row][col + 2] == 0 && board[row][col + 3] == 0) {
                        whoWon = whoWon - 125;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 0 && board[row][col + 1] == 0 && board[row][col + 2] == 1 && board[row][col + 3] == 0) {
                        whoWon = whoWon - 125;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 0 && board[row][col + 1] == 0 && board[row][col + 2] == 0 && board[row][col + 3] == 1) {
                        whoWon = whoWon - 125;
                    }
                }
            }
            //Horizontal One Moves End Player One

            // Horizontal one moves Player Two
            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 2 && board[row][col + 1] == 0 && board[row][col + 2] == 0 && board[row][col + 3] == 0) {
                        whoWon = whoWon + 125;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 0 && board[row][col + 1] == 2 && board[row][col + 2] == 0 && board[row][col + 3] == 0) {
                        whoWon = whoWon + 125;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 0 && board[row][col + 1] == 0 && board[row][col + 2] == 2 && board[row][col + 3] == 0) {
                        whoWon = whoWon + 125;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 0 && board[row][col + 1] == 0 && board[row][col + 2] == 0 && board[row][col + 3] == 2) {
                        whoWon = whoWon + 125;
                    }
                }
            }
            //Horizontal One Moves End Player Two

            //Horizontal Two Moves Player One
            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 1 && board[row][col + 1] == 1 && board[row][col + 2] == 0 && board[row][col + 3] == 0) {
                        whoWon = whoWon - 250;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 0 && board[row][col + 1] == 1 && board[row][col + 2] == 1 && board[row][col + 3] == 0) {
                        whoWon = whoWon - 250;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 0 && board[row][col + 1] == 0 && board[row][col + 2] == 1 && board[row][col + 3] == 1) {
                        whoWon = whoWon - 250;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 1 && board[row][col + 1] == 0 && board[row][col + 2] == 1 && board[row][col + 3] == 0) {
                        whoWon = whoWon - 250;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 0 && board[row][col + 1] == 1 && board[row][col + 2] == 0 && board[row][col + 3] == 1) {
                        whoWon = whoWon - 250;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 1 && board[row][col + 1] == 0 && board[row][col + 2] == 0 && board[row][col + 3] == 1) {
                        whoWon = whoWon - 250;
                    }
                }
            }

            //Horizontal Two Moves Player One End

            //Horizontal Two Moves Player Two
            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 1 && board[row][col + 1] == 1 && board[row][col + 2] == 0 && board[row][col + 3] == 0) {
                        whoWon = whoWon + 250;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 0 && board[row][col + 1] == 2 && board[row][col + 2] == 2 && board[row][col + 3] == 0) {
                        whoWon = whoWon + 250;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 0 && board[row][col + 1] == 0 && board[row][col + 2] == 2 && board[row][col + 3] == 2) {
                        whoWon = whoWon + 250;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 2 && board[row][col + 1] == 0 && board[row][col + 2] == 2 && board[row][col + 3] == 0) {
                        whoWon = whoWon + 250;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 0 && board[row][col + 1] == 2 && board[row][col + 2] == 0 && board[row][col + 3] == 2) {
                        whoWon = whoWon + 250;
                    }
                }
            }

            for (int col = 0; col <= 3; col++) {
                for (int row = 0; row < 6; row++) {
                    if (board[row][col] == 2 && board[row][col + 1] == 0 && board[row][col + 2] == 0 && board[row][col + 3] == 2) {
                        whoWon = whoWon + 250;
                    }
                }
            }
            //Horizontal Two Moves Player Two End
            if (difficulty == HARD) {
                //Horizontal Three Moves Player One

                for (int col = 0; col <= 3; col++) {
                    for (int row = 0; row < 6; row++) {
                        if (board[row][col] == 1 && board[row][col + 1] == 1 && board[row][col + 2] == 1 && board[row][col + 3] == 0) {
                            whoWon = whoWon - 1000;
                        }
                    }
                }

                for (int col = 0; col <= 3; col++) {
                    for (int row = 0; row < 6; row++) {
                        if (board[row][col] == 0 && board[row][col + 1] == 1 && board[row][col + 2] == 1 && board[row][col + 3] == 1) {
                            whoWon = whoWon - 1000;
                        }
                    }
                }

                for (int col = 0; col <= 3; col++) {
                    for (int row = 0; row < 6; row++) {
                        if (board[row][col] == 1 && board[row][col + 1] == 0 && board[row][col + 2] == 1 && board[row][col + 3] == 1) {
                            whoWon = whoWon - 1000;
                        }
                    }
                }

                for (int col = 0; col <= 3; col++) {
                    for (int row = 0; row < 6; row++) {
                        if (board[row][col] == 1 && board[row][col + 1] == 1 && board[row][col + 2] == 0 && board[row][col + 3] == 1) {
                            whoWon = whoWon - 1000;
                        }
                    }
                }
                //Horizontal Three Moves Player One End

                //Horizontal Three Moves Player Two

                for (int col = 0; col <= 3; col++) {
                    for (int row = 0; row < 6; row++) {
                        if (board[row][col] == 2 && board[row][col + 1] == 2 && board[row][col + 2] == 2 && board[row][col + 3] == 0) {
                            whoWon = whoWon + 1000;
                        }
                    }
                }

                for (int col = 0; col <= 3; col++) {
                    for (int row = 0; row < 6; row++) {
                        if (board[row][col] == 0 && board[row][col + 1] == 2 && board[row][col + 2] == 2 && board[row][col + 3] == 2) {
                            whoWon = whoWon + 1000;
                        }
                    }
                }

                for (int col = 0; col <= 3; col++) {
                    for (int row = 0; row < 6; row++) {
                        if (board[row][col] == 2 && board[row][col + 1] == 0 && board[row][col + 2] == 2 && board[row][col + 3] == 2) {
                            whoWon = whoWon + 1000;
                        }
                    }
                }

                for (int col = 0; col <= 3; col++) {
                    for (int row = 0; row < 6; row++) {
                        if (board[row][col] == 2 && board[row][col + 1] == 2 && board[row][col + 2] == 0 && board[row][col + 3] == 2) {
                            whoWon = whoWon + 1000;
                        }
                    }
                }
            }
            //Horizontal Three Moves Player Two End
        }

        return whoWon;
    }

    public int[][] findAllLegalMoves() {
        int[][] legalMove = {
                {-1, -1},
                {-1, -1},
                {-1, -1},
                {-1, -1},
                {-1, -1},
                {-1, -1},
                {-1, -1}
        };

        for (int c = 0; c < 7; c++) {
            for (int r = 5; r >= 0; r--) {
                if (board[r][c] == 0) {
                    legalMove[c][0] = r;
                    legalMove[c][1] = c;
                    break;
                }
            }
        }
        return legalMove;
    }


    /**
     * testForWinner
     *
     *
     *
     * @return: 0 if no winner, 1 if player wins, 2 if computer wins
     */
    public int testForWinner() {

        //Check for vertical win
        for (int c = 0; c < 7; c++) {
            for (int r = 5; r >= 3; r--) {
                if (board[r][c] == 1 && board[r - 1][c] == 1 && board[r - 2][c] == 1 && board[r - 3][c] == 1) {
                    return -500000;
                } else if (board[r][c] == 2 && board[r - 1][c] == 2 && board[r - 2][c] == 2 && board[r - 3][c] == 2) {
                    return 500000;
                }
            }
        }

        //check for horizontal win
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c <= 3; c++) {
                if (board[r][c] == 1 && board[r][c + 1] == 1 && board[r][c + 2] == 1 && board[r][c + 3] == 1) {
                    return -500000;
                } else if (board[r][c] == 2 && board[r][c + 1] == 2 && board[r][c + 2] == 2 && board[r][c + 3] == 2) {
                    return 500000;
                }
            }
        }

        //check for diagonal win
        for (int r = 0; r <= 2; r++) {
            for (int c = 0; c < 4; c++) {
                if (board[r][c] == 1 && board[r + 1][c + 1] == 1 && board[r + 2][c + 2] == 1 && board[r + 3][c + 3] == 1) {
                    return -500000;
                } else if (board[r][c] == 2 && board[r + 1][c + 1] == 2 && board[r + 2][c + 2] == 2 && board[r + 3][c + 3] == 2) {
                    return 500000;
                }
            }
        }

        for (int r = 0; r <= 2; r++) {
            for (int c = 6; c >= 3; c--) {
                if (board[r][c] == 1 && board[r + 1][c - 1] == 1 && board[r + 2][c - 2] == 1 && board[r + 3][c - 3] == 1) {
                    return -500000;
                } else if (board[r][c] == 2 && board[r + 1][c - 1] == 2 && board[r + 2][c - 2] == 2 && board[r + 3][c - 3] == 2) {
                    return 500000;
                }
            }
        }

        return 0;
    }

    /**
     *
     * @return: true if game is a draw
     */
    public boolean isDraw() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 7; col++) {
                if (board[row][col] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

}
