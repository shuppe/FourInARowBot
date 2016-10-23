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
        minimax(0, Integer.MIN_VALUE, Integer.MAX_VALUE);
        return bestMove[0][1];

    }

    /**
     *
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
        int max = Integer.MIN_VALUE;
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
                board[lMoves[move][0]][lMoves[move][1]] = 2; // board temporaire avec un essai dans la colonne appropriée
                int temp = minMove(depth + 1, alpha, beta);  // On regarde le min
                board[lMoves[move][0]][lMoves[move][1]] = 0; // board replacé

                // si la valeur de
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
        int min = Integer.MAX_VALUE;
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
        int scoreSwitch = 1;
        int player = 1;

        if (difficulty == MEDIUM || difficulty == HARD) {
            /*
             Analyse du board pour voir les points verticaux
            */
            for (int col = 0; col < 7; col++) {
                int row = 5;
                int discCount = 0;
                while (row >= 0 && board[row][col] == 0) {
                    row--;
                }
                if (row >= 0) {
                    player = board[row][col];
                    if (player == 1)
                        scoreSwitch = -1;
                    else
                        scoreSwitch = 1;
                    discCount++;
                    while (--row >= 0 && board[row][col] == player) {
                        discCount++;
                    }
                    if (discCount == 3)
                        discCount = 4; // pour que le multiplicateur donne 1000
                    whoWon = whoWon + scoreSwitch * discCount * 125;
                }
            }

            for (player = 1; player <= 2; player++) {

                if (player == 1)
                    scoreSwitch = -1;
                else
                    scoreSwitch = 1;

                /*
                 Analyse du board pour voir les points horizontaux
                */
                for (int col = 0; col <= 3; col++) {
                    for (int row = 0; row < 6; row++) {
                        int discCount = 0;
                        for (int discCol = 0; discCol <= 3 && (board[row][col + discCol] == player || board[row][col + discCol] == 0); discCol++) {
                            discCount += board[row][col + discCol] / player;
                        }
                        if (discCount == 3)
                            discCount = 4; // pour que le multiplicateur donne 1000
                        whoWon = whoWon + scoreSwitch * discCount * 125;
                    }
                }


            /*
             Analyse du board pour voir les points diagonaux vers la droite
            */
                for (int col = 0; col <= 3; col++) {
                    for (int row = 0; row <= 2; row++) {
                        int discCount = 0;
                        for (int discCol = 0; discCol <= 3 && (board[row + discCol][col + discCol] == player || board[row + discCol][col + discCol] == 0); discCol++) {
                            discCount += board[row + discCol][col + discCol] / player;
                        }
                        if (discCount == 3)
                            discCount = 4; // pour que le multiplicateur donne 1000
                        whoWon = whoWon + scoreSwitch * discCount * 125;
                    }
                }

            /*
             Analyse du board pour voir les points diagonaux vers la gauche
            */
                for (int col = 6; col >= 3; col--) {
                    for (int row = 0; row <= 2; row++) {
                        int discCount = 0;
                        for (int discCol = 0; discCol <= 3 && (board[row + discCol][col - discCol] == player || board[row + discCol][col - discCol] == 0); discCol++) {
                            discCount += board[row + discCol][col - discCol] / player;
                        }
                        if (discCount == 3)
                            discCount = 4; // pour que le multiplicateur donne 1000
                        whoWon = whoWon + scoreSwitch * discCount * 125;
                    }
                }
            }

        }
        //System.err.printf("analysis: whoWon value %d\n", whoWon);
        return whoWon;
    }
/*
    //for checking nrOfTokens (win situation: nrOfTokens = 4)
    private boolean checkDiagonally1(int col, int row, int nrOfTokens)
    {
        for (int j = 0; j < nrOfTokens; j++)
        {
            int adjacentSameTokens = 0;
            for (int i = 0; i < nrOfTokens; i++)
            {
                if ((col + i - j) >= 0 && (col + i - j) < nbHorzCells
                        && (row + i - j) >= 1 && (row + i - j) < nbVertCells
                        && getPlayerOfTokenAt(col + i - j, row + i - j) == getPlayerOfTokenAt(col, row))
                {
                    adjacentSameTokens++;
                }
            }
            if (adjacentSameTokens >= nrOfTokens)
                return true;
        }
        return false;
    }

    private boolean checkDiagonally2(int col, int row, int nrOfTokens)
    {
        for (int j = 0; j < nrOfTokens; j++)
        {
            int adjacentSameTokens = 0;
            for (int i = 0; i < nrOfTokens; i++)
            {
                if ((col - i + j) >= 0 && (col - i + j) < nbHorzCells
                        && (row + i - j) >= 1 && (row + i - j) < nbVertCells
                        && getPlayerOfTokenAt(col - i + j, row + i - j) == getPlayerOfTokenAt(col, row))
                {
                    adjacentSameTokens++;
                }
            }
            if (adjacentSameTokens >= nrOfTokens)
                return true;
        }
        return false;
    }

    private boolean checkHorizontally(int col, int row, int nrOfTokens)
    {
        int adjacentSameTokens = 1;
        int i = 1;
        while (col - i >= 0 && getPlayerOfTokenAt(col - i, row) == getPlayerOfTokenAt(col, row))
        {
            adjacentSameTokens++;
            i++;
        }
        i = 1;
        while (col + i < nbHorzCells && getPlayerOfTokenAt(col + i, row) == getPlayerOfTokenAt(col, row))
        {
            adjacentSameTokens++;
            i++;
        }
        return (adjacentSameTokens >= nrOfTokens);
    }

    private boolean checkVertically(int col, int row, int nrOfTokens)
    {
        int adjacentSameTokens = 1;
        int i = 1;
        while (row + i < nbVertCells && getPlayerOfTokenAt(col, row + i) == getPlayerOfTokenAt(col, row))
        {
            adjacentSameTokens++;
            i++;
        }
        return (adjacentSameTokens >= nrOfTokens);
    }
*/

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
