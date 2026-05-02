import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.IOException;

public class GamePanel extends JPanel {

    BufferedImage[] images = new BufferedImage[14];
    public static final int pieceSize = 80;
    public static final int tileSize = 100;

    public void getImages() {
        try {
            images[0] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece00.png"));
            images[1] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece01.png"));
            images[2] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece02.png"));
            images[3] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece03.png"));
            images[4] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece04.png"));
            images[5] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece05.png"));
            images[6] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece06.png"));
            images[7] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece07.png"));
            images[8] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece08.png"));
            images[9] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece09.png"));
            images[10] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece10.png"));
            images[11] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece11.png"));
            images[12] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece12.png"));
            images[13] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece13.png"));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    g2.setColor(Color.WHITE);
                    if ((i == Main.lastMoves[0] && j == Main.lastMoves[1]) ||(i == Main.lastMoves[2] && j == Main.lastMoves[3])) {
                        if (Main.isMate == 2) {
                            g2.setColor(new Color(255, 60, 60));
                        }
                        else if (Main.isMate == 1) {
                            g2.setColor(new Color(155, 155, 155));
                        }
                        else {
                            g2.setColor(new Color(207, 185, 151));
                        }
                    }
                }
                else{
                    g2.setColor(new Color(80, 118, 94));
                    if ((i == Main.lastMoves[0] && j == Main.lastMoves[1]) ||(i == Main.lastMoves[2] && j == Main.lastMoves[3])) {
                        if (Main.isMate == 2) {
                            g2.setColor(new Color(255, 0, 0));
                        }
                        else if (Main.isMate == 1) {
                            g2.setColor(new Color(40, 40, 40));
                        }
                        else {
                            g2.setColor(new Color(157, 135, 101));
                        }
                    }
                }
                g2.fillRect(i * tileSize, j * tileSize, tileSize, tileSize);
            }
        }

        if (Main.checkTile[0] != -1) {
            g2.setColor(new Color(255, 160, 160));
            g2.fillRect(Main.checkTile[0] * tileSize, Main.checkTile[1] * tileSize, tileSize, tileSize);
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Main.board[j][i] == 'P') {
                    g2.drawImage(images[1], i * tileSize + (tileSize - pieceSize) / 2, j * tileSize + (tileSize - pieceSize) / 2, pieceSize, pieceSize, null);
                }
                if (Main.board[j][i] == 'R') {
                    g2.drawImage(images[3], i * tileSize + (tileSize - pieceSize) / 2, j * tileSize + (tileSize - pieceSize) / 2, pieceSize, pieceSize, null);
                }
                if (Main.board[j][i] == 'B') {
                    g2.drawImage(images[5], i * tileSize + (tileSize - pieceSize) / 2, j * tileSize + (tileSize - pieceSize) / 2, pieceSize, pieceSize, null);
                }
                if (Main.board[j][i] == 'N') {
                    g2.drawImage(images[7], i * tileSize + (tileSize - pieceSize) / 2, j * tileSize + (tileSize - pieceSize) / 2, pieceSize, pieceSize, null);
                }
                if (Main.board[j][i] == 'Q') {
                    g2.drawImage(images[9], i * tileSize + (tileSize - pieceSize) / 2, j * tileSize + (tileSize - pieceSize) / 2, pieceSize, pieceSize, null);
                }
                if (Main.board[j][i] == 'K') {
                    g2.drawImage(images[11], i * tileSize + (tileSize - pieceSize) / 2, j * tileSize + (tileSize - pieceSize) / 2, pieceSize, pieceSize, null);
                }
                if (Main.board[j][i] == 'C') {
                    g2.drawImage(images[13], i * tileSize + (tileSize - pieceSize) / 2, j * tileSize + (tileSize - pieceSize) / 2, pieceSize, pieceSize, null);
                }
                if (Main.board[j][i] == 'p') {
                    g2.drawImage(images[0], i * tileSize + (tileSize - pieceSize) / 2, j * tileSize + (tileSize - pieceSize) / 2, pieceSize, pieceSize, null);
                }
                if (Main.board[j][i] == 'r') {
                    g2.drawImage(images[2], i * tileSize + (tileSize - pieceSize) / 2, j * tileSize + (tileSize - pieceSize) / 2, pieceSize, pieceSize, null);
                }
                if (Main.board[j][i] == 'b') {
                    g2.drawImage(images[4], i * tileSize + (tileSize - pieceSize) / 2, j * tileSize + (tileSize - pieceSize) / 2, pieceSize, pieceSize, null);
                }
                if (Main.board[j][i] == 'n') {
                    g2.drawImage(images[6], i * tileSize + (tileSize - pieceSize) / 2, j * tileSize + (tileSize - pieceSize) / 2, pieceSize, pieceSize, null);
                }
                if (Main.board[j][i] == 'q') {
                    g2.drawImage(images[8], i * tileSize + (tileSize - pieceSize) / 2, j * tileSize + (tileSize - pieceSize) / 2, pieceSize, pieceSize, null);
                }
                if (Main.board[j][i] == 'k') {
                    g2.drawImage(images[10], i * tileSize + (tileSize - pieceSize) / 2, j * tileSize + (tileSize - pieceSize) / 2, pieceSize, pieceSize, null);
                }
                if (Main.board[j][i] == 'c') {
                    g2.drawImage(images[12], i * tileSize + (tileSize - pieceSize) / 2, j * tileSize + (tileSize - pieceSize) / 2, pieceSize, pieceSize, null);
                }
            }
        }
        
        if (Main.printMoves) {
            for (int[] move : Main.moves) {
                g2.setColor(Color.ORANGE);
                if (Main.board[move[1]][move[0]] != ' ') {
                    g2.setColor(Color.RED);
                }
                g2.fillOval(move[0] * tileSize + (tileSize - 20) / 2, move[1] * tileSize + (tileSize - 20) / 2, 20, 20);
            }
        }

        g2.dispose();
    }
}
