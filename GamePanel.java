import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 1) {
                    g2.setColor(Color.WHITE);
                }
                else{
                    g2.setColor(Color.DARK_GRAY);
                }
                g2.fillRect(i * 50, j * 50, 50, 50);
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (Main.board[j][i] == 'P') {
                    g2.setColor(Color.BLACK);
                    g2.fillRect(i * 50 + 12, j * 50 + 12, 26, 26);
                }
                if (Main.board[j][i] == 'R') {
                    g2.setColor(Color.GREEN);
                    g2.fillRect(i * 50 + 12, j * 50 + 12, 26, 26);
                }
                if (Main.board[j][i] == 'B') {
                    g2.setColor(Color.BLUE);
                    g2.fillRect(i * 50 + 12, j * 50 + 12, 26, 26);
                }
                if (Main.board[j][i] == 'N') {
                    g2.setColor(Color.MAGENTA);
                    g2.fillRect(i * 50 + 12, j * 50 + 12, 26, 26);
                }
                if (Main.board[j][i] == 'Q') {
                    g2.setColor(Color.RED);
                    g2.fillRect(i * 50 + 12, j * 50 + 12, 26, 26);
                }
                if (Main.board[j][i] == 'K') {
                    g2.setColor(Color.PINK);
                    g2.fillRect(i * 50 + 12, j * 50 + 12, 26, 26);
                }

                if (Main.board[j][i] == 'p') {
                    g2.setColor(Color.BLACK);
                    g2.fillOval(i * 50 + 12, j * 50 + 12, 26, 26);
                }
                if (Main.board[j][i] == 'r') {
                    g2.setColor(Color.GREEN);
                    g2.fillOval(i * 50 + 12, j * 50 + 12, 26, 26);
                }
                if (Main.board[j][i] == 'b') {
                    g2.setColor(Color.BLUE);
                    g2.fillOval(i * 50 + 12, j * 50 + 12, 26, 26);
                }
                if (Main.board[j][i] == 'n') {
                    g2.setColor(Color.MAGENTA);
                    g2.fillOval(i * 50 + 12, j * 50 + 12, 26, 26);
                }
                if (Main.board[j][i] == 'q') {
                    g2.setColor(Color.RED);
                    g2.fillOval(i * 50 + 12, j * 50 + 12, 26, 26);
                }
                if (Main.board[j][i] == 'k') {
                    g2.setColor(Color.PINK);
                    g2.fillOval(i * 50 + 12, j * 50 + 12, 26, 26);
                }
            }
        }
        
        g2.dispose();
    }
}
