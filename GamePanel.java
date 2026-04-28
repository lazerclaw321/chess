import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.IOException;

public class GamePanel extends JPanel {

    BufferedImage[] images = new BufferedImage[12];

    public void getImages() {
        try {
            images[0] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece_00.png"));
            images[1] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece_01.png"));
            images[2] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece_02.png"));
            images[3] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece_03.png"));
            images[4] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece_04.png"));
            images[5] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece_05.png"));
            images[6] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece_06.png"));
            images[7] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece_07.png"));
            images[8] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece_08.png"));
            images[9] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece_09.png"));
            images[10] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece_10.png"));
            images[11] = ImageIO.read(getClass().getResourceAsStream("chesspieces/chesspiece_11.png"));
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
                }
                else{
                    g2.setColor(new Color(30, 68, 44));
                }
                g2.fillRect(i * 84, j * 84, 84, 84);
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Main.board[j][i] == 'P') {
                    g2.drawImage(images[1], i * 84 + 10, j * 84 + 10, 64, 64, null);
                }
                if (Main.board[j][i] == 'R') {
                    g2.drawImage(images[3], i * 84 + 10, j * 84 + 10, 64, 64, null);
                }
                if (Main.board[j][i] == 'B') {
                    g2.drawImage(images[5], i * 84 + 10, j * 84 + 10, 64, 64, null);
                }
                if (Main.board[j][i] == 'N') {
                    g2.drawImage(images[7], i * 84 + 10, j * 84 + 10, 64, 64, null);
                }
                if (Main.board[j][i] == 'Q') {
                    g2.drawImage(images[9], i * 84 + 10, j * 84 + 10, 64, 64, null);
                }
                if (Main.board[j][i] == 'K') {
                    g2.drawImage(images[11], i * 84 + 10, j * 84 + 10, 64, 64, null);
                }
                if (Main.board[j][i] == 'p') {
                    g2.drawImage(images[0], i * 84 + 10, j * 84 + 10, 64, 64, null);
                }
                if (Main.board[j][i] == 'r') {
                    g2.drawImage(images[2], i * 84 + 10, j * 84 + 10, 64, 64, null);
                }
                if (Main.board[j][i] == 'b') {
                    g2.drawImage(images[4], i * 84 + 10, j * 84 + 10, 64, 64, null);
                }
                if (Main.board[j][i] == 'n') {
                    g2.drawImage(images[6], i * 84 + 10, j * 84 + 10, 64, 64, null);
                }
                if (Main.board[j][i] == 'q') {
                    g2.drawImage(images[8], i * 84 + 10, j * 84 + 10, 64, 64, null);
                }
                if (Main.board[j][i] == 'k') {
                    g2.drawImage(images[10], i * 84 + 10, j * 84 + 10, 64, 64, null);
                }
            }
        }
        
        if (Main.printMoves) {
            for (int[] move : Main.moves) {
                g2.setColor(Color.ORANGE);
                if (Main.board[move[1]][move[0]] != ' ') {
                    g2.setColor(Color.RED);
                }
                g2.fillOval(move[0] * 84 + 32, move[1] * 84 + 32, 20, 20);
            }
        }

        g2.dispose();
    }
}
