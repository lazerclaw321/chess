import java.awt.event.*;
import java.awt.*;

public class UserMouse implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        if (Main.ply % 2 == 0) {
            Point position = MouseInfo.getPointerInfo().getLocation();
            Point frameLocation = Main.panel.getLocationOnScreen();
            int tileX = (int) Math.floor(((double) position.x - frameLocation.x) / GamePanel.tileSize);
            int tileY = (int) Math.floor(((double) position.y - frameLocation.y) / GamePanel.tileSize);
            System.out.println(Main.pointValue(Main.board[tileY][tileX], tileX, tileY));
            if (tileX < 7 && tileY < 7) {
                if (Main.printMoves) {
                    boolean realMove = false;
                    for (int[] move : Main.moves) {
                        if (move[0] == tileX && move[1] == tileY) {
                            Main.moveX = tileX;
                            Main.moveY = tileY;
                            realMove = true;
                            break;
                        }
                    }   
                    if (!realMove) {
                        if (Main.board[tileY][tileX] != ' ' && Character.isLowerCase(Main.board[tileY][tileX])) {
                            Main.selectedX = tileX;
                            Main.selectedY = tileY;
                            Main.printMoves = true;
                            Main.moves = Main.getLegalMoves(Main.selectedX, Main.selectedY, true);
                        }
                        else {
                            Main.selectedX = -1;
                            Main.selectedY = -1;
                            Main.printMoves = false;
                        }
                        Main.panel.repaint();
                    }
                }
                else {
                    if (Main.board[tileY][tileX] != ' ' && Character.isLowerCase(Main.board[tileY][tileX])) {
                        Main.selectedX = tileX;
                        Main.selectedY = tileY;
                        Main.printMoves = true;
                        Main.moves = Main.getLegalMoves(Main.selectedX, Main.selectedY, true);
                        Main.panel.repaint();
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    }
    
}
