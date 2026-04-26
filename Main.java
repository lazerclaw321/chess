import java.awt.*;
import java.util.*;
import javax.swing.*;

public class Main {
    static int ply = 0;
    static boolean whiteShortCastlingRights = true;
    static boolean whiteLongCastlingRights = true;
    static boolean blackShortCastlingRights = true;
    static boolean blackLongCastlingRights = true;
    static int enPassantRow = -1;
    static boolean midGame = false;
    static double scorePosGlobal;
    static int movesCalculated = 0;

    static int[][] psqPawn = new int[8][8];
    static int[][] psqKnight = new int[8][8];
    static int[][] psqBishop = new int[8][8];
    static int[][] psqQueen = new int[8][8];
    static int[][] psqRook = new int[8][8];
    static int[][] psqKing = new int[8][8];

    public static char[][] board = new char[8][8];
    public static boolean printMoves = false;
    public static int selectedX, selectedY;

    static {
        board[0] = new char[]{'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'};
        board[1] = new char[]{'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'};
        board[2] = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        board[3] = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        board[4] = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        board[5] = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        board[6] = new char[]{'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'};
        board[7] = new char[]{'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'};

        psqPawn[0] = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
        psqPawn[1] = new int[]{5, 0, -10, -20, -20, -10, 0, 5};
        psqPawn[2] = new int[]{5, 1, -10, 0, 0, -10, 1, 5};
        psqPawn[3] = new int[]{0, 0, 0, 19, 21, 0, 0, 0};
        psqPawn[4] = new int[]{5, 5, 5, 25, 25, 5, 5, 5};
        psqPawn[5] = new int[]{10, 20, 25, 30, 30, 25, 20, 10};
        psqPawn[6] = new int[]{50, 50, 50, 60, 60, 50, 50, 50};
        psqPawn[7] = new int[]{800, 800, 800, 800, 800, 800, 800, 800};

        psqKnight[0] = new int[]{-50, -25, -20, -20, -20, -20, -25, -50};
        psqKnight[1] = new int[]{-25, -10, -1, 5, 5, -1, -10, -25};
        psqKnight[2] = new int[]{-50, -1, 14, 20, 20, 14, -1, -50};
        psqKnight[3] = new int[]{-20, 5, 20, 25, 25, 20, 5, -20};
        psqKnight[4] = new int[]{-20, 5, 20, 25, 25, 20, 5, -20};
        psqKnight[5] = new int[]{-50, -1, 14, 20, 20, 14, -1, -50};
        psqKnight[6] = new int[]{-25, -10, -1, 5, 5, -1, -10, -25};
        psqKnight[7] = new int[]{-50, -25, -20, -20, -20, -20, -25, -50};

        psqBishop[0] = new int[]{-20, -10, -10, -10, -10, -10, -10, -20};
        psqBishop[1] = new int[]{-10, -5, 0, 5, 5, 0, -5, -10};
        psqBishop[2] = new int[]{-10, 0, 10, 15, 15, 10, 0, -10};
        psqBishop[3] = new int[]{-10, 5, 15, 20, 20, 15, 5, -10};
        psqBishop[4] = new int[]{-10, 5, 15, 20, 20, 15, 5, -10};
        psqBishop[5] = new int[]{-10, 0, 10, 15, 15, 10, 0, -10};
        psqBishop[6] = new int[]{-10, -5, 0, 5, 5, 0, -5, -10};
        psqBishop[7] = new int[]{-20, -10, -10, -10, -10, -10, -10, -20};

        psqQueen[0] = new int[]{-20, -10, -10, -10, -10, -10, -10, -20};
        psqQueen[1] = new int[]{-10, -5, 0, 5, 5, 0, -5, -10};
        psqQueen[2] = new int[]{-10, 0, 10, 15, 15, 10, 0, -10};
        psqQueen[3] = new int[]{-10, 5, 15, 20, 20, 15, 5, -10};
        psqQueen[4] = new int[]{-10, 5, 15, 20, 20, 15, 5, -10};
        psqQueen[5] = new int[]{-10, 0, 10, 15, 15, 10, 0, -10};
        psqQueen[6] = new int[]{-10, -5, 0, 5, 5, 0, -5, -10};
        psqQueen[7] = new int[]{-20, -10, -10, -10, -10, -10, -10, -20};

        psqRook[0] = new int[]{10, 10, 10, 10, 10, 10, 10, 10};
        psqRook[1] = new int[]{11, 11, 11, 11, 11, 11, 11, 11};
        psqRook[2] = new int[]{-1, -1, -1, -1, -1, -1, -1, -1};
        psqRook[3] = new int[]{-1, -1, -1, -1, -1, -1, -1, -1};
        psqRook[4] = new int[]{-1, -1, -1, -1, -1, -1, -1, -1};
        psqRook[5] = new int[]{-1, -1, -1, -1, -1, -1, -1, -1};
        psqRook[6] = new int[]{-1, -1, -1, -1, -1, -1, -1, -1};
        psqRook[7] = new int[]{0, 0, 0, 12, 12, 5, 0, 0};

        psqKing[0] = new int[]{-5, -5, -5, -5, -5, -5, -5, -5};
        psqKing[1] = new int[]{-5, -5, -5, -5, -5, -5, -5, -5};
        psqKing[2] = new int[]{-5, -5, -5, -5, -5, -5, -5, -5};
        psqKing[3] = new int[]{-5, -5, -5, -5, -5, -5, -5, -5};
        psqKing[4] = new int[]{-5, -5, -5, -5, -5, -5, -5, -5};
        psqKing[5] = new int[]{-5, -5, -5, -5, -5, -5, -5, -5};
        psqKing[6] = new int[]{-5, -5, -5, -5, -5, -5, -5, -5};
        psqKing[7] = new int[]{-5, 10, 10, 0, 0, 10, 10, -5};
    }

    public static char makeMove(int x, int y, int x2, int y2, boolean realMove) {
        char lastCaptured = (char) board[y2][x2];
        if (realMove && board[y][x] == 'p' && lastCaptured == ' ' && x2 == enPassantRow && y2 == 2) {
            lastCaptured = board[3][x2];
            board[3][x2] = ' ';
        }
        if (realMove && board[y][x] == 'P' && lastCaptured == ' ' && x2 == enPassantRow && y2 == 5) {
            lastCaptured = board[5][x2];
            board[3][x2] = ' ';
        }
        if (realMove){
            if (board[y][x] == 'P' && y2 == 3) {
                enPassantRow = x;
            }
            else if (board[y][x] == 'p' && y2 == 4) {
                enPassantRow = x;
            }
            else {
                enPassantRow = -1;
            }
        }
        
        if (realMove && board[y][x] == 'K') {
            blackShortCastlingRights = false;
            blackLongCastlingRights = false;
        }
        if (realMove && board[y][x] == 'R' && x == 1) {
            blackLongCastlingRights = false;
        }
        if (realMove && board[y][x] == 'R' && x == 7) {
            blackLongCastlingRights = false;
        }
        if (realMove && board[y][x] == 'k') {
            whiteShortCastlingRights = false;
            whiteLongCastlingRights = false;
        }
        if (realMove && board[y][x] == 'r' && x == 1) {
            whiteLongCastlingRights = false;
        }
        if (realMove && board[y][x] == 'r' && x == 7) {
            whiteLongCastlingRights = false;
        }
        if (board[y][x] == 'K' && x2 == 6 && x == 4 && y == 0) {
            board[y][x] = ' ';
            board[y][7] = ' ';
            board[y][6] = 'K';
            board[y][5] = 'R';
        }
        else if (board[y][x] == 'k' && x2 == 6 && x == 4 && y == 7) {
            board[y][x] = ' ';
            board[y][7] = ' ';
            board[y][6] = 'k';
            board[y][5] = 'r';
        }
        else if (board[y][x] == 'K' && x2 == 2 && x == 4 && y == 0) {
            board[y][x] = ' ';
            board[y][0] = ' ';
            board[y][2] = 'K';
            board[y][3] = 'R';
        }
        else if (board[y][x] == 'k' && x2 == 2 && x == 4 && y == 7) {
            board[y][x] = ' ';
            board[y][0] = ' ';
            board[y][2] = 'k';
            board[y][3] = 'r';
        }
        else {
            board[y2][x2] = board[y][x];
            board[y][x] = ' ';
        }
        return lastCaptured;
    }

    public static void unmakeMove(int x, int y, int x2, int y2, boolean special, char lastCaptured) {
        if (board[y2][x2] == 'K' && x2 == 6 && x == 4 && y == 0) {
            board[y][x] = 'K';
            board[y][7] = 'R';
            board[y][6] = ' ';
            board[y][5] = ' ';
        }
        else if (board[y2][x2] == 'k' && x2 == 6 && x == 4 && y == 7) {
            board[y][x] = 'k';
            board[y][7] = 'r';
            board[y][6] = ' ';
            board[y][5] = ' ';
        }
        else if (board[y2][x2] == 'K' && x2 == 2 && x == 4 && y == 0) {
            board[y][x] = 'K';
            board[y][0] = 'R';
            board[y][2] = ' ';
            board[y][3] = ' ';
        }
        else if (board[y2][x2] == 'k' && x2 == 2 && x == 4 && y == 7) {
            board[y][x] = 'k';
            board[y][0] = 'r';
            board[y][2] = ' ';
            board[y][3] = ' ';
        }
        else {
            board[y][x] = board[y2][x2];
            board[y2][x2] = lastCaptured;
        }
    }

    public static int pointValue(char piece, int x, int y, boolean newBotChanges) {
        if (piece == 'p') {
            return 100 + psqPawn[7 - y][x];
        }
        if (piece == 'P') {
            return 100 + psqPawn[y][x];
        }
        if (piece == 'n') {
            return 320 + psqKnight[7 - y][x];
        }
        if (piece == 'N') {
            return 320 + psqKnight[y][x];
        }
        if (piece == 'b') {
            return 330 + psqBishop[7 - y][x];
        }
        if (piece == 'B') {
            return 330 + psqBishop[y][x];
        }
        if (piece == 'r') {
            return 500 + psqRook[y][x];
        }
        if (piece == 'R') {
            return 500 + psqRook[7 - y][x];
        }
        if (piece == 'q' || piece == 'Q') {
            return 900 + psqQueen[y][x];
        }
        if (piece == 'k') {
            return 20000 + psqKing[y][x];
        }
        if (piece == 'K') {
            return 20000 - psqKing[7 - y][x];
        }
        return 0;
    }

    public static boolean isValidMove(int x, int y, boolean whiteToMove, boolean protection) {
        if (x >= 0 && x < 8 && y >= 0 && y < 8) {
            if (board[y][x] == ' ' || (Character.isLowerCase(board[y][x]) != whiteToMove) || protection) {
                return true;
            }
        }
        return false;
    }
 
    public static Vector<int[]> movePiece(int x, int y, boolean whiteToMove, boolean protection) {
        Vector<int[]> moves = new Vector<int[]>();

        if (whiteToMove && x == 4 && y == 7 && whiteLongCastlingRights && board[7][4] == 'k' && board[7][3] == ' ' && board[7][2] == ' ' && board[7][1] == ' ' && board[7][0] == 'r') {
            moves.add(new int[] {x - 2, y});
        }
        if (!whiteToMove && x == 4 && y == 0 && blackLongCastlingRights && board[0][4] == 'K' && board[0][3] == ' ' && board[0][2] == ' ' && board[0][1] == ' ' && board[0][0] == 'R') {
            moves.add(new int[] {x - 2, y});
        }
        if (whiteToMove && x == 4 && y == 7 && whiteShortCastlingRights && board[7][4] == 'k' && board[7][5] == ' ' && board[7][6] == ' ' && board[7][7] == 'r') {
            moves.add(new int[] {x + 2, y});
        }
        if (!whiteToMove && x == 4 && y == 0 && blackShortCastlingRights && board[0][4] == 'k' && board[0][5] == ' ' && board[0][6] == ' ' && board[0][7] == 'r') {
            moves.add(new int[] {x + 2, y});
        }

        if (board[y][x] == 'n' || board[y][x] == 'N') {
            if (isValidMove(x - 2, y - 1,  whiteToMove, protection)) {moves.add(new int[]{x - 2, y - 1});}
            if (isValidMove(x + 2, y - 1,  whiteToMove, protection)) {moves.add(new int[]{x + 2, y - 1});}
            if (isValidMove(x - 2, y + 1,  whiteToMove, protection)) {moves.add(new int[]{x - 2, y + 1});}
            if (isValidMove(x + 2, y + 1,  whiteToMove, protection)) {moves.add(new int[]{x + 2, y + 1});}
            if (isValidMove(x - 1, y - 2,  whiteToMove, protection)) {moves.add(new int[]{x - 1, y - 2});}
            if (isValidMove(x + 1, y - 2,  whiteToMove, protection)) {moves.add(new int[]{x + 1, y - 2});}
            if (isValidMove(x - 1, y + 2,  whiteToMove, protection)) {moves.add(new int[]{x - 1, y + 2});}
            if (isValidMove(x + 1, y + 2,  whiteToMove, protection)) {moves.add(new int[]{x + 1, y + 2});}
        }
        if (board[y][x] == 'k' || board[y][x] == 'K') {
            if (isValidMove(x + 1, y + 1,  whiteToMove, protection)) {moves.add(new int[]{x + 1, y + 1});}
            if (isValidMove(x - 1, y + 1,  whiteToMove, protection)) {moves.add(new int[]{x - 1, y + 1});}
            if (isValidMove(x + 1, y - 1,  whiteToMove, protection)) {moves.add(new int[]{x + 1, y - 1});}
            if (isValidMove(x - 1, y - 1,  whiteToMove, protection)) {moves.add(new int[]{x - 1, y - 1});}
            if (isValidMove(x, y + 1,  whiteToMove, protection)) {moves.add(new int[]{x, y + 1});}
            if (isValidMove(x, y - 1,  whiteToMove, protection)) {moves.add(new int[]{x, y - 1});}
            if (isValidMove(x + 1, y,  whiteToMove, protection)) {moves.add(new int[]{x + 1, y});}
            if (isValidMove(x - 1, y,  whiteToMove, protection)) {moves.add(new int[]{x - 1, y});}
        }        
        if (board[y][x] == 'R' || board[y][x] == 'r' || board[y][x] == 'q' || board[y][x] == 'Q') {
            for (int i = y - 1; i >= 0; i--) {
                if (board[i][x] == ' ' || (Character.isLowerCase(board[i][x]) != whiteToMove) || protection) {
                    moves.add(new int[]{x, i});
                }
                if (board[i][x] != ' ') {
                    break;
                }
            }
            for (int i = y + 1; i <= 7; i++) {
                if (board[i][x] == ' ' || (Character.isLowerCase(board[i][x]) != whiteToMove) || protection) {
                    moves.add(new int[]{x, i});
                }
                if (board[i][x] != ' ') {
                    break;
                }
            }
            for (int i = x - 1; i >= 0; i--) {
                if (board[y][i] == ' ' || (Character.isLowerCase(board[y][i]) != whiteToMove || protection)) {
                    moves.add(new int[]{i, y});
                }
                if (board[y][i] != ' ') {
                    break;
                }
            }
            for (int i = x + 1; i <= 7; i++) {
                if (board[y][i] == ' ' || (Character.isLowerCase(board[y][i]) != whiteToMove || protection)) {
                    moves.add(new int[]{i, y});
                }
                if (board[y][i] != ' ') {
                    break;
                }
            }
        }

        if (board[y][x] == 'b' || board[y][x] == 'B' || board[y][x] == 'q' || board[y][x] == 'Q') {
            for (int i = 1; i <= Math.min(x, y); i++) {
                if (board[y - i][x - i] == ' ' || (Character.isLowerCase(board[y - i][x - i]) != whiteToMove) || protection) {
                    moves.add(new int[]{x - i, y - i});
                }
                if (board[y - i][x - i] != ' ') {
                    break;
                }
            }
            for (int i = 1; i + Math.max(x, y) < 8; i++) {
                if (board[y + i][x + i] == ' ' || (Character.isLowerCase(board[y + i][x + i]) != whiteToMove) || protection) {
                    moves.add(new int[]{x + i, y + i});
                }
                if (board[y + i][x + i] != ' ') {
                    break;
                }
            }
            for (int i = 1; i + Math.max(7 - x, y) < 8; i++) {
               
                if (board[y + i][x - i] == ' ' || (Character.isLowerCase(board[y + i][x - i]) != whiteToMove) || protection) {
                    moves.add(new int[]{x - i, y + i});
                }
                if (board[y + i][x - i] != ' ') {
                    break;
                }
            }
            for (int i = 1; i + Math.max(x, 7 - y) < 8; i++) {
                if (board[y - i][x + i] == ' ' || (Character.isLowerCase(board[y - i][x + i]) != whiteToMove) || protection) {
                    moves.add(new int[]{x + i, y - i});
                }
                if (board[y - i][x + i] != ' ') {
                    break;
                }
            }
        }

        if (board[y][x] == 'p' || board[y][x] == 'P') {
            if (whiteToMove) {
                if (y > 0 && board[y - 1][x] == ' ') {
                    moves.add(new int[]{x, y - 1});
                    if (y == 6 && board[y - 2][x] == ' ') {
                        moves.add(new int[]{x, y - 2});
                    }
                }
                if (y > 0 && x > 0 && ((board[y - 1][x - 1] != ' ' && Character.isLowerCase(board[y - 1][x - 1]) != whiteToMove) || (x - 1 == enPassantRow || board[y][x - 1] == 'P'))) {
                    moves.add(new int[]{x - 1, y - 1});
                }
                if (y > 0 && x < 7 && (board[y - 1][x + 1] != ' ' && Character.isLowerCase(board[y - 1][x + 1]) != whiteToMove || (x + 1 == enPassantRow || board[y][x + 1] == 'P'))) {
                    moves.add(new int[]{x + 1, y - 1});
                }
            }
            else {
                if (y < 7 && board[y + 1][x] == ' ') {
                    moves.add(new int[]{x, y + 1});
                    if (y == 1 && board[y + 2][x] == ' ') {
                        moves.add(new int[]{x, y + 2});
                    }
                }
                if (y < 7 && x > 0 && ((board[y + 1][x - 1] != ' ' && Character.isLowerCase(board[y + 1][x - 1]) != whiteToMove) || (x - 1 == enPassantRow || board[y][x - 1] == 'p'))) {
                    moves.add(new int[]{x - 1, y + 1});
                }
                if (y < 7 && x < 7 && (board[y + 1][x + 1] != ' ' && Character.isLowerCase(board[y + 1][x + 1]) != whiteToMove || (x + 1 == enPassantRow || board[y][x + 1] == 'P'))) {
                    moves.add(new int[]{x + 1, y + 1});
                }
            }
        }
        return moves;
    }  

    public static boolean inCheck(boolean whiteToMove) {
        int x = -1, y = -1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[j][i] == 'K' && !whiteToMove) {
                    x = i;
                    y = j;
                    break;
                }
                if (board[j][i] == 'k' && whiteToMove) {
                    x = i;
                    y = j;
                    break;
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[j][i] != ' ' && Character.isLowerCase(board[j][i]) == !whiteToMove) {
                    Vector<int[]> moves = movePiece(i, j,  !whiteToMove, false);
                    for (int[] move : moves) {
                        if (move[0] == x && move[1] == y) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }    
   
    public static Vector<int[]> fetchMoves(boolean whiteToMove, boolean truncate, boolean protection, boolean capture) {
        Vector<int[]> allMoves = new Vector<int[]>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[j][i] != ' ' && Character.isLowerCase(board[j][i]) == whiteToMove) {
                    Vector<int[]> moves = movePiece(i, j,  whiteToMove, protection);
                    for (int[] move : moves) {
                        char lastCaptured = makeMove(i, j, move[0], move[1], false);
                        if (!inCheck( whiteToMove)) {
                            if (!capture || lastCaptured != ' ') {
                                if (lastCaptured == ' ') {
                                    allMoves.add(new int[]{(int) board[move[1]][move[0]], i, j, move[0], move[1], scorePosition(whiteToMove, true)});
                                }
                                else {
                                    allMoves.add(new int[]{(int) board[move[1]][move[0]], i, j, move[0], move[1], pointValue(lastCaptured, move[0], move[1], true ) - pointValue(board[move[1]][move[0]], i, j, true)});
                                }
                            }
                        }
                        unmakeMove(i, j, move[0], move[1], false, lastCaptured);
                    }
                }
            }
        }
        Collections.sort(allMoves, (a, b) -> b[5] - a[5]);
        return allMoves;
    }
   
    public static int scorePosition(boolean whiteToMove, boolean newBotChanges) {
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char piece = board[j][i];
                if (piece == ' ') continue;                
                if (Character.isLowerCase(piece) == whiteToMove) {
                    score += pointValue(piece, i, j, newBotChanges);
                }
                else {
                    score -= pointValue(piece, i, j, newBotChanges);
                }
               
               
            }
        }
        return score;
    }
   
    public static int quiescenceSearch(boolean whiteToMove, int a, int b, int depth, boolean newBotChanges) {
        int alpha = a, beta = b;
        int eval = scorePosition(whiteToMove, newBotChanges);
        movesCalculated++;
        if (eval >= beta) {
            return beta;
        }
        alpha = Math.max(alpha, eval);
        Vector<int[]> captures = fetchMoves( whiteToMove, false, false, true);
        for (int[] move : captures) {
            char lastCaptured = makeMove(move[1], move[2], move[3], move[4], false);
            if (move[5] > 400) {
                unmakeMove(move[1], move[2], move[3], move[4], false, lastCaptured);
                return move[5];
            }
            eval = -quiescenceSearch( !whiteToMove, -beta, -alpha, depth - 1, newBotChanges);
            unmakeMove(move[1], move[2], move[3], move[4], false, lastCaptured);
            if (eval > beta) {
                return beta;
            }
            alpha = Math.max(alpha, eval);
        }
        return alpha;
    }
   
    public static int search(boolean whiteToMove, int depth, int a, int b, boolean newBotChanges) {
        int alpha = a, beta = b;
        movesCalculated++;
        if (depth == 0) {
            return quiescenceSearch( whiteToMove, alpha, beta, depth, newBotChanges);
        }
        Vector<int[]> allMoves = fetchMoves( whiteToMove, false, false, false);
        if (allMoves.size() == 0) {
            if (inCheck( whiteToMove)) {
                return -99999999; //checkmate
            }
            else {
                return 0; //stalemate
            }
        }
        for (int[] move : allMoves) {
            char lastCaptured = makeMove(move[1], move[2], move[3], move[4], false);
            int eval = -search( !whiteToMove, depth - 1, -beta, -alpha, newBotChanges);
            int newMoveScore = scorePosition( whiteToMove, newBotChanges);
            unmakeMove(move[1], move[2], move[3], move[4], false, lastCaptured);
            if (eval > beta) {
                return beta;
            }
            if (newMoveScore - scorePosition( whiteToMove, newBotChanges) < -300) {
                return quiescenceSearch( whiteToMove, alpha, beta, depth, newBotChanges);
            }
            alpha = Math.max(alpha, eval);
        }
        return alpha;
    }    

    public static int[] botMoves(boolean whiteToMove, boolean newBotChanges, int depth) {
        Vector<int[]> allMoves = fetchMoves(whiteToMove, false, false, false);
        movesCalculated++;
        if (inCheck(whiteToMove)) {
            System.out.println("in check");
            for (int[] move : allMoves) {
                System.out.println(Arrays.toString(move));
            }
        }
        int maxScore = -999999;
        if (allMoves.size() == 0) {
            return new int[] {};
        }
        int[] optimalMove = new int[5];        
        for (int pos = 0; pos < allMoves.size(); pos++) {
            int[] move = allMoves.get(pos);
            char lastCaptured = makeMove(move[1], move[2], move[3], move[4], false);
            int evaluation = -search(!whiteToMove, depth - 1, -9999999, 9999999, newBotChanges);
            if (evaluation >= maxScore) {
                maxScore = evaluation;
                optimalMove = move;
            }
            unmakeMove(move[1], move[2], move[3], move[4], false, lastCaptured);
            System.out.println(Arrays.toString(move) + " " + evaluation);
        }
        System.out.println(Arrays.toString(optimalMove) + " " + maxScore + " " + movesCalculated);
        movesCalculated = 0;
        return optimalMove;
    }    

    public static void main(String[] args) {

        JFrame frame = new JFrame("Chess Bot");
        frame.setSize(800, 800);
        GamePanel panel = new GamePanel();
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        
        Scanner scanner = new Scanner(System.in);        
        boolean whiteToMove = true;
        
        //gameloop
        while (true) {
            selectedX = scanner.nextInt();
            selectedY = scanner.nextInt();
            if (board[selectedY][selectedX] != ' ') {
               
                char piece = board[selectedY][selectedX];
                Vector<int[]> moves = movePiece(selectedX, selectedY,  whiteToMove, false);
                for (int[] move : moves) {
                    System.out.println("Move to: " + move[0] + ", " + move[1]);
                }
                int moveX = scanner.nextInt();
                int moveY = scanner.nextInt();
                int[] position = new int[]{moveX, moveY};
                for (int[] move : moves) {
                    if (move[0] == position[0] && move[1] == position[1]) {
                        makeMove(selectedX, selectedY, moveX, moveY, true);
                        panel.repaint();
                        if (moveY == 0 && piece == 'p') {
                            board[moveY][moveX] = 'q';
                        }
                        if (moveY == 7 && piece == 'P') {
                            board[moveY][moveX] = 'Q';
                        }
                        for (char[] row : board) {
                            System.out.println(Arrays.toString(row));
                        }
                    }
                }
                if (inCheck( whiteToMove)) {
                    System.out.println("check");
                }
                
                ply++;
                whiteToMove = !whiteToMove;
                //bot moves
                int[] target = botMoves( whiteToMove, true, 2);
                makeMove(target[1], target[2], target[3], target[4], true);
                panel.repaint();
                System.out.println(enPassantRow);
                if (target[4] == 0 && board[target[4]][target[3]] == 'p') {
                    board[target[4]][target[3]] = 'q';
                }
                if (target[4] == 7 && board[target[4]][target[3]] == 'P') {
                    board[target[4]][target[3]] = 'Q';
                }
                System.out.println("--------------------------------------------" + ply);
                for (char[] row : board) {
                    System.out.println(Arrays.toString(row));
                }
                ply++;
                whiteToMove = !whiteToMove;
                System.out.println("___________________________________________" + ply);
               /*
                target = botMoves( whiteToMove, false);
                board[target[4]][target[3]] = (char) target[0];
                board[target[2]][target[1]] = ' ';
                if (target[4] == 0 && board[target[4]][target[3]] == 'p') {
                    board[target[4]][target[3]] = 'q';
                }
                if (target[4] == 7 && board[target[4]][target[3]] == 'P') {
                    board[target[4]][target[3]] = 'Q';
                }
                for (char[] row : board) {
                    System.out.println(Arrays.toString(row));
                }
                whiteToMove = !whiteToMove;
                ply++;*/
            }
        }
    }
}