import java.time.Instant;
import java.util.*;
import javax.swing.*;
import java.util.concurrent.TimeUnit;

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
    static long startingTime;
    static final long minThinkingTime = 5000;
    static final long maxThinkingTime = 10000;
    static final long maxQsearchTime = 15000;
    static int totalPoints = 0;

    static int[] midgamePstPawn = new int[64];
    static int[] midgamePstKnight = new int[64];
    static int[] midgamePstBishop = new int[64];
    static int[] midgamePstQueen = new int[64];
    static int[] midgamePstRook = new int[64];
    static int[] midgamePstKing = new int[64];

    static int[] endgamePstPawn = new int[64];
    static int[] endgamePstKnight = new int[64];
    static int[] endgamePstBishop = new int[64];
    static int[] endgamePstQueen = new int[64];
    static int[] endgamePstRook = new int[64];
    static int[] endgamePstKing = new int[64];

    public static char[][] board = new char[8][8];
    public static boolean printMoves = false;
    public static ArrayList<int[]> moves;
    public static int selectedX = -1, selectedY = -1;
    public static int moveX = -1, moveY = -1;
    public static GamePanel panel = new GamePanel();
    public static int[] lastMoves = new int[] {-1, -1, -1, -1};
    public static int isMate = 0;
    public static int[] checkTile = new int[] {-1, -1};

    public static void initializePst() {
        midgamePstPawn = new int[]  {
            800,   800,   800,   800,   800,   800,  800,   800,
            98, 134,  61,  95,  68, 126, 34, -11,
            -6,   7,  26,  31,  65,  56, 25, -20,
            -14,  13,   6,  21,  23,  12, 17, -23,
            -27,  -2,  -5,  12,  17,   6, 10, -25,
            -26,  -4,  -4, -10,   3,   3, 33, -12,
            -35,  -1, -20, -23, -15,  24, 38, -22,
            0,   0,   0,   0,   0,   0,  0,   0
        };
        endgamePstPawn = new int[] {
            800,   800,   800,   800,   800,   800,  800,   800,
            178, 173, 158, 134, 147, 132, 165, 187,
            94, 100,  85,  67,  56,  53,  82,  84,
            32,  24,  13,   5,  -2,   4,  17,  17,
            13,   9,  -3,  -7,  -7,  -8,   3,  -1,
            4,   7,  -6,   1,   0,  -5,  -1,  -8,
            13,   8,   8,  10,  13,   0,   2,  -7,
            0,   0,   0,   0,   0,   0,   0,   0,
        };
        midgamePstKnight = new int[] {
            -167, -89, -34, -49,  61, -97, -15, -107,
            -73, -41,  72,  36,  23,  62,   7,  -17,
            -47,  60,  37,  65,  84, 129,  73,   44,
            -9,  17,  19,  53,  37,  69,  18,   22,
            -13,   4,  16,  13,  28,  19,  21,   -8,
            -23,  -9,  12,  10,  19,  17,  25,  -16,
            -29, -53, -12,  -3,  -1,  18, -14,  -19,
            -105, -21, -58, -33, -17, -28, -19,  -23,
        };
        endgamePstKnight = new int[]  {
            -58, -38, -13, -28, -31, -27, -63, -99,
            -25,  -8, -25,  -2,  -9, -25, -24, -52,
            -24, -20,  10,   9,  -1,  -9, -19, -41,
            -17,   3,  22,  22,  22,  11,   8, -18,
            -18,  -6,  16,  25,  16,  17,   4, -18,
            -23,  -3,  -1,  15,  10,  -3, -20, -22,
            -42, -20, -10,  -5,  -2, -20, -23, -44,
            -29, -51, -23, -15, -22, -18, -50, -64,
        };
        midgamePstBishop = new int[] {
            -29,   4, -82, -37, -25, -42,   7,  -8,
            -26,  16, -18, -13,  30,  59,  18, -47,
            -16,  37,  43,  40,  35,  50,  37,  -2,
            -4,   5,  19,  50,  37,  37,   7,  -2,
            -6,  13,  13,  26,  34,  12,  10,   4,
            0,  15,  15,  15,  14,  27,  18,  10,
            4,  15,  16,   0,   7,  21,  33,   1,
            -33,  -3, -14, -21, -13, -12, -39, -21,
        };
        endgamePstBishop = new int[] {
            -14, -21, -11,  -8, -7,  -9, -17, -24,
            -8,  -4,   7, -12, -3, -13,  -4, -14,
            2,  -8,   0,  -1, -2,   6,   0,   4,
            -3,   9,  12,   9, 14,  10,   3,   2,
            -6,   3,  13,  19,  7,  10,  -3,  -9,
            -12,  -3,   8,  10, 13,   3,  -7, -15,
            -14, -18,  -7,  -1,  4,  -9, -15, -27,
            -23,  -9, -23,  -5, -9, -16,  -5, -17,
        };
        midgamePstRook = new int[] {
            32,  42,  32,  51, 63,  9,  31,  43,
            27,  32,  58,  62, 80, 67,  26,  44,
            -5,  19,  26,  36, 17, 45,  61,  16,
            -24, -11,   7,  26, 24, 35,  -8, -20,
            -36, -26, -12,  -1,  9, -7,   6, -23,
            -45, -25, -16, -17,  3,  0,  -5, -33,
            -44, -16, -20,  -9, -1, 11,  -6, -71,
            -19, -13,   1,  17, 16,  7, -37, -26,
        };
        endgamePstRook = new int[] {
            13, 10, 18, 15, 12,  12,   8,   5,
            11, 13, 13, 11, -3,   3,   8,   3,
            7,  7,  7,  5,  4,  -3,  -5,  -3,
            4,  3, 13,  1,  2,   1,  -1,   2,
            3,  5,  8,  4, -5,  -6,  -8, -11,
            -4,  0, -5, -1, -7, -12,  -8, -16,
            -6, -6,  0,  2, -9,  -9, -11,  -3,
            -9,  2,  3, -1, -5, -13,   4, -20,
        };
        midgamePstQueen = new int[] {
            -28,   0,  29,  12,  59,  44,  43,  45,
            -24, -39,  -5,   1, -16,  57,  28,  54,
            -13, -17,   7,   8,  29,  56,  47,  57,
            -27, -27, -16, -16,  -1,  17,  -2,   1,
            -9, -26,  -9, -10,  -2,  -4,   3,  -3,
            -14,   2, -11,  -2,  -5,   2,  14,   5,
            -35,  -8,  11,   2,   8,  15,  -3,   1,
            -1, -18,  -9,  10, -15, -25, -31, -50,
        };
        endgamePstQueen = new int[] {
            -9,  22,  22,  27,  27,  19,  10,  20,
            -17,  20,  32,  41,  58,  25,  30,   0,
            -20,   6,   9,  49,  47,  35,  19,   9,
            3,  22,  24,  45,  57,  40,  57,  36,
            -18,  28,  19,  47,  31,  34,  39,  23,
            -16, -27,  15,   6,   9,  17,  10,   5,
            -22, -23, -30, -16, -16, -23, -36, -32,
            -33, -28, -22, -43,  -5, -32, -20, -41,
        };
        midgamePstKing = new int[] {
            -65,  23,  16, -15, -56, -34,   2,  13,
            29,  -1, -20,  -7,  -8,  -4, -38, -29,
            -9,  24,   2, -16, -20,   6,  22, -22,
            -17, -20, -12, -27, -30, -25, -14, -36,
            -49,  -1, -27, -39, -46, -44, -33, -51,
            -14, -14, -22, -46, -44, -30, -15, -27,
            1,   7,  -8, -64, -43, -16,   9,   8,
            -15,  36,  12, -54,   8, -28,  24,  14,
        };
        endgamePstKing = new int[] {
            -74, -35, -18, -18, -11,  15,   4, -17,
            -12,  17,  14,  17,  17,  38,  23,  11,
            10,  17,  23,  15,  20,  45,  44,  13,
            -8,  22,  24,  27,  26,  33,  26,   3,
            -18,  -4,  21,  24,  27,  23,   9, -11,
            -19,  -3,  11,  21,  23,  16,   7,  -9,
            -27, -11,   4,  13,  14,   4,  -5, -17,
            -53, -34, -21, -11, -28, -14, -24, -43
        };
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

    public static int pointValue(char piece, int x, int y) {
        if (totalPoints > 43000) {
            if (piece == 'p') {
                return 100 + midgamePstPawn[8*y+x];
            }
            if (piece == 'P') {
                return 100 + midgamePstPawn[(8*(7-y)+x)];
            }
            if (piece == 'n') {
                return 320 + midgamePstKnight[8*y+x];
            }
            if (piece == 'N') {
                return 320 + midgamePstKnight[(8*(7-y)+x)];
            }
            if (piece == 'b') {
                return 330 + midgamePstBishop[8*y+x];
            }
            if (piece == 'B') {
                return 330 + midgamePstBishop[(8*(7-y)+x)];
            }
            if (piece == 'r') {
                return 500 + midgamePstRook[8*y+x];
            }
            if (piece == 'R') {
                return 500 + midgamePstRook[(8*(7-y)+x)];
            }
            if (piece == 'q') {
                return 900 + midgamePstQueen[8*y+x];
            }
            if (piece == 'Q') {
                return 900 + midgamePstQueen[(8*(7-y)+x)];
            }
            if (piece == 'k') {
                return 20000 + midgamePstKing[8*y+x];
            }
            if (piece == 'K') {
                return 20000 + midgamePstKing[(8*(7-y)+x)];
            }
            if (piece == 'c') {
                return 200 + midgamePstKnight[8*y+x];
            }
            if (piece == 'C') {
                return 200 + midgamePstKnight[(8*(7-y)+x)];
            }
        }
        else {
            if (piece == 'p') {
                return 100 + endgamePstPawn[8*y+x];
            }
            if (piece == 'P') {
                return 100 + endgamePstPawn[(8*(7-y)+x)];
            }
            if (piece == 'n') {
                return 320 + endgamePstKnight[8*y+x];
            }
            if (piece == 'N') {
                return 320 + endgamePstKnight[(8*(7-y)+x)];
            }
            if (piece == 'b') {
                return 330 + endgamePstBishop[8*y+x];
            }
            if (piece == 'B') {
                return 330 + endgamePstBishop[(8*(7-y)+x)];
            }
            if (piece == 'r') {
                return 500 + endgamePstRook[8*y+x];
            }
            if (piece == 'R') {
                return 500 + endgamePstRook[(8*(7-y)+x)];
            }
            if (piece == 'q') {
                return 900 + endgamePstQueen[8*y+x];
            }
            if (piece == 'Q') {
                return 900 + endgamePstQueen[(8*(7-y)+x)];
            }
            if (piece == 'k') {
                return 20000 + endgamePstKing[8*y+x];
            }
            if (piece == 'K') {
                return 20000 + endgamePstKing[(8*(7-y)+x)];
            }
            if (piece == 'c') {
                return 200 + endgamePstKnight[8*y+x];
            }
            if (piece == 'C') {
                return 200 + endgamePstKnight[(8*(7-y)+x)];
            }
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
 
    public static ArrayList<int[]> movePiece(int x, int y, boolean whiteToMove, boolean protection) {
        ArrayList<int[]> moves = new ArrayList<int[]>();

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
        if (board[y][x] == 'c' || board[y][x] == 'C') {
            if (isValidMove(x - 3, y - 1,  whiteToMove, protection)) {moves.add(new int[]{x - 3, y - 1});}
            if (isValidMove(x + 3, y - 1,  whiteToMove, protection)) {moves.add(new int[]{x + 3, y - 1});}
            if (isValidMove(x - 3, y + 1,  whiteToMove, protection)) {moves.add(new int[]{x - 3, y + 1});}
            if (isValidMove(x + 3, y + 1,  whiteToMove, protection)) {moves.add(new int[]{x + 3, y + 1});}
            if (isValidMove(x - 1, y - 3,  whiteToMove, protection)) {moves.add(new int[]{x - 1, y - 3});}
            if (isValidMove(x + 1, y - 3,  whiteToMove, protection)) {moves.add(new int[]{x + 1, y - 3});}
            if (isValidMove(x - 1, y + 3,  whiteToMove, protection)) {moves.add(new int[]{x - 1, y + 3});}
            if (isValidMove(x + 1, y + 3,  whiteToMove, protection)) {moves.add(new int[]{x + 1, y + 3});}
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
                if (y > 0 && x > 0 && ((board[y - 1][x - 1] != ' ' && Character.isLowerCase(board[y - 1][x - 1]) != whiteToMove) || (x - 1 == enPassantRow && board[y][x - 1] == 'P'))) {
                    moves.add(new int[]{x - 1, y - 1});
                }
                if (y > 0 && x < 7 && (board[y - 1][x + 1] != ' ' && Character.isLowerCase(board[y - 1][x + 1]) != whiteToMove || (x + 1 == enPassantRow && board[y][x + 1] == 'P'))) {
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
                if (y < 7 && x > 0 && ((board[y + 1][x - 1] != ' ' && Character.isLowerCase(board[y + 1][x - 1]) != whiteToMove) || (x - 1 == enPassantRow && board[y][x - 1] == 'p'))) {
                    moves.add(new int[]{x - 1, y + 1});
                }
                if (y < 7 && x < 7 && (board[y + 1][x + 1] != ' ' && Character.isLowerCase(board[y + 1][x + 1]) != whiteToMove || (x + 1 == enPassantRow && board[y][x + 1] == 'p'))) {
                    moves.add(new int[]{x + 1, y + 1});
                }
            }
        }
        return moves;
    }  

    public static ArrayList<int[]> getLegalMoves(int x, int y, boolean whiteToMove) {
        ArrayList<int[]> moves = movePiece(x, y, whiteToMove, false);
        ArrayList<int[]> legalMoves = new ArrayList<int[]>();

        if (!inCheck(whiteToMove)) {
            for (int[] move : moves) {
                if (move[0] == 3 && move[1] == 7 && whiteToMove && x == 4 && y == 7 && whiteLongCastlingRights && board[7][4] == 'k' && board[7][3] == ' ' && board[7][2] == ' ' && board[7][1] == ' ' && board[7][0] == 'r') {
                    char lastCaptured = makeMove(x, y, x-2, y, false);
                    if (!inCheck(whiteToMove)) {
                        legalMoves.add(new int[] {x - 2, y});
                    }
                    unmakeMove(x, y, x-2, y, false, lastCaptured);
                }
                if (move[0] == 3 && move[1] == 0 && !whiteToMove && x == 4 && y == 0 && blackLongCastlingRights && board[0][4] == 'K' && board[0][3] == ' ' && board[0][2] == ' ' && board[0][1] == ' ' && board[0][0] == 'R') {
                    char lastCaptured = makeMove(x, y, x-2, y, false);
                    if (!inCheck(whiteToMove)) {
                        legalMoves.add(new int[] {x - 2, y});
                    }
                    unmakeMove(x, y, x-2, y, false, lastCaptured);
                }
                if (move[0] == 5 && move[1] == 7 && whiteToMove && x == 4 && y == 7 && whiteShortCastlingRights && board[7][4] == 'k' && board[7][5] == ' ' && board[7][6] == ' ' && board[7][7] == 'r') {
                    char lastCaptured = makeMove(x, y, x+2, y, false);
                    if (!inCheck(whiteToMove)) {
                        legalMoves.add(new int[] {x + 2, y});
                    }
                    unmakeMove(x, y, x+2, y, false, lastCaptured);
                }
                if (move[0] == 5 && move[1] == 0 && !whiteToMove && x == 4 && y == 0 && blackShortCastlingRights && board[0][4] == 'k' && board[0][5] == ' ' && board[0][6] == ' ' && board[0][7] == 'r') {
                    char lastCaptured = makeMove(x, y, x+2, y, false);
                    if (!inCheck(whiteToMove)) {
                        legalMoves.add(new int[] {x + 2, y});
                    }
                    unmakeMove(x, y, x+2, y, false, lastCaptured);
                }
            }
        }
        for (int[] move : moves) {
            char lastCaptured = makeMove(x, y, move[0], move[1], false);
            if (!inCheck(whiteToMove)) {
                legalMoves.add(move);
            }
            unmakeMove(x, y, move[0], move[1], false, lastCaptured);
        }
        return legalMoves;
    }

    public static int[] fetchKingPosition(boolean whiteToMove) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[j][i] == 'K' && !whiteToMove) {
                    return new int[] {i, j};
                }
                if (board[j][i] == 'k' && whiteToMove) {
                    return new int[] {i, j};
                }
            }
        }
        return new int[] {-1, -1};
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
                    ArrayList<int[]> moves = movePiece(i, j,  !whiteToMove, false);
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
   
    public static ArrayList<int[]> fetchMoves(boolean whiteToMove, boolean truncate, boolean protection, boolean capture) {
        ArrayList<int[]> allMoves = new ArrayList<int[]>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[j][i] != ' ' && Character.isLowerCase(board[j][i]) == whiteToMove) {
                    ArrayList<int[]> moves = getLegalMoves(i, j,  whiteToMove);
                    for (int[] move : moves) {
                        char lastCaptured = makeMove(i, j, move[0], move[1], false);
                        if (!capture || lastCaptured != ' ') {
                            allMoves.add(new int[]{(int) board[move[1]][move[0]], i, j, move[0], move[1], scorePosition(whiteToMove, false)});
                        }
                        unmakeMove(i, j, move[0], move[1], false, lastCaptured);
                    }
                }
            }
        }
        Collections.sort(allMoves, (a, b) -> b[5] - a[5]);
        return allMoves;
    }
   
    public static int scorePosition(boolean whiteToMove, boolean total) {
        int score = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char piece = board[j][i];
                if (piece == ' ') continue;                
                if (Character.isLowerCase(piece) == whiteToMove) {
                    score += pointValue(piece, i, j);
                }
                else {
                    if (total) {
                        score += pointValue(piece, i, j);
                    }
                    else {
                        score -= pointValue(piece, i, j);
                    }
                }
            }
        }
        return score;
    }
   
    public static int quiescenceSearch(boolean whiteToMove, int a, int b, int depth, boolean newBotChanges) {
        int alpha = a, beta = b;
        int eval = scorePosition(whiteToMove, false);
        movesCalculated++;
        if (eval >= beta) {
            return beta;
        }
        if (Instant.now().toEpochMilli() - startingTime < maxQsearchTime) {
            alpha = Math.max(alpha, eval);
            ArrayList<int[]> captures = fetchMoves( whiteToMove, false, false, true);
            for (int[] move : captures) {
                char lastCaptured = makeMove(move[1], move[2], move[3], move[4], false);
                eval = -quiescenceSearch( !whiteToMove, -beta, -alpha, depth - 1, newBotChanges);
                unmakeMove(move[1], move[2], move[3], move[4], false, lastCaptured);
                if (eval >= beta) {
                    return beta;
                }
                alpha = Math.max(alpha, eval);
            }
        }
        return alpha;
    }
   
    public static int search(boolean whiteToMove, int depth, int alpha, int beta, boolean newBotChanges) {
        movesCalculated++;
        if (depth == 0 || Instant.now().toEpochMilli() - startingTime > maxThinkingTime) {
            return quiescenceSearch(whiteToMove, alpha, beta, depth, newBotChanges);
        }
        ArrayList<int[]> allMoves = fetchMoves(whiteToMove, false, false, false);
        if (inCheck(whiteToMove)) {
            if (allMoves.size() == 0) {
                return -99999999 - depth; 
            }
        }
        if (allMoves.size() == 0) {
            return 0;
        }
        for (int[] move : allMoves) {
            char lastCaptured = makeMove(move[1], move[2], move[3], move[4], false);
            int eval = -search(!whiteToMove, depth - 1, -beta, -alpha, newBotChanges);
            unmakeMove(move[1], move[2], move[3], move[4], false, lastCaptured);
            if (eval >= beta) {
                return beta;
            }
            alpha = Math.max(alpha, eval);
        }
        return alpha;
    }    

    public static int[] botMoves(boolean whiteToMove, boolean newBotChanges) {
        ArrayList<int[]> allMoves = fetchMoves(whiteToMove, false, false, false);
        int depth = 1;
        movesCalculated++;
        int maxScore = -999999;
        if (allMoves.size() == 0) {
            return new int[] {};
        }
        int[] optimalMove = new int[5];  
        while (Instant.now().toEpochMilli() - startingTime < minThinkingTime) {
            for (int pos = 0; pos < allMoves.size(); pos++) {
                if (Instant.now().toEpochMilli() - startingTime < minThinkingTime) {
                    int[] move = allMoves.get(pos);
                    char lastCaptured = makeMove(move[1], move[2], move[3], move[4], false);
                    int evaluation = -search(!whiteToMove, depth - 1, -9999999, 9999999, newBotChanges);
                    
                    if (evaluation >= maxScore || (optimalMove[0] == move[0] && optimalMove[1] == move[1] && optimalMove[2] == move[2] && optimalMove[3] == move[3] && optimalMove[4] == move[4])) {
                        maxScore = evaluation;
                        optimalMove = move;
                    }
                    unmakeMove(move[1], move[2], move[3], move[4], false, lastCaptured);
                    System.out.println(Arrays.toString(move) + " " + evaluation);
                }
            }
            depth++;
            System.out.println(depth);
        }
        System.out.println(Arrays.toString(optimalMove) + " " + maxScore + " " + movesCalculated);
        System.out.println(Instant.now().toEpochMilli() - startingTime);
        movesCalculated = 0;
        return optimalMove;
    }    

    public static void main(String[] args) {
        initializePst();
        JFrame frame = new JFrame("Chess Bot");
        frame.setSize(GamePanel.tileSize * 8 + 13, GamePanel.tileSize * 8 + 38);
        frame.setResizable(false);
        panel.getImages();
        frame.getContentPane().add(panel);
        frame.setVisible(true);
        frame.addMouseListener(new UserMouse());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        board[0] = new char[]{'R', 'N', 'B', 'Q', 'K', 'B', 'N', 'R'};
        board[1] = new char[]{'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'};
        board[2] = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        board[3] = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        board[4] = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        board[5] = new char[]{' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        board[6] = new char[]{'p', 'p', 'p', 'p', 'p', 'p', 'p', 'p'};
        board[7] = new char[]{'r', 'n', 'b', 'q', 'k', 'b', 'n', 'r'};
        getLegalMoves(0, 0, true);

        boolean whiteToMove = true;
        //gameloop
        while (true) {
            while (moveX == -1 && moveY == -1) {
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ply++;
            char piece = board[selectedY][selectedX];
            printMoves = false;
            int[] position = new int[]{moveX, moveY};
            for (int[] move : moves) {
                if (move[0] == position[0] && move[1] == position[1]) {
                    makeMove(selectedX, selectedY, moveX, moveY, true);
                    if (inCheck(!whiteToMove)) {
                        checkTile = fetchKingPosition(!whiteToMove);
                    }
                    else {
                        checkTile = new int[] {-1, -1};
                    }
                    if (fetchMoves(!whiteToMove, false, false, false).size() == 0) {
                        if (inCheck(!whiteToMove)) {
                            isMate = 2;
                        }              
                        else {
                            isMate = 1;
                        }  
                    }
                    lastMoves = new int[] {selectedX, selectedY, moveX, moveY};
                    panel.repaint();
                    totalPoints = scorePosition(whiteToMove, true);
                    System.out.println(totalPoints);
                    whiteToMove = !whiteToMove;
                    if (moveY == 0 && piece == 'p') {
                        board[moveY][moveX] = 'q';
                    }
                    if (moveY == 7 && piece == 'P') {
                        board[moveY][moveX] = 'Q';
                    }
                    selectedX = -1;
                    selectedY = -1;
                    moveX = -1;
                    moveY = -1;
                    for (char[] row : board) {
                        System.out.println(Arrays.toString(row));
                    }
                }
            }
            if (inCheck(whiteToMove)) {
                System.out.println("check");
            }
            //bot moves
            startingTime = Instant.now().toEpochMilli();
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int[] target = botMoves(whiteToMove, true);
            makeMove(target[1], target[2], target[3], target[4], true);
            if (inCheck(!whiteToMove)) {
                checkTile = fetchKingPosition(!whiteToMove);
            }
            else {
                checkTile = new int[] {-1, -1};
            }
            lastMoves = new int[] {target[1], target[2], target[3], target[4]};
            if (fetchMoves(!whiteToMove, false, false, false).size() == 0) {
                if (inCheck(!whiteToMove)) {
                    isMate = 2;
                }              
                else {
                    isMate = 1;
                }  
            }
            panel.repaint();
            totalPoints = scorePosition(whiteToMove, true);
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