import java.awt.event.*;
import java.awt.*;

public class UserMouse implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        Point position = MouseInfo.getPointerInfo().getLocation();
        Point frameLocation = Main.panel.getLocationOnScreen();
        System.out.println((position.x + " " + frameLocation.x) + " " + (position.y + " " + frameLocation.y));
        int tileX = (int) Math.floor(((double) position.x - frameLocation.x) / 84);
        int tileY = (int) Math.floor(((double) position.y - frameLocation.y) / 84);
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
                if (Main.ply % 2 == 0) {
                    Main.panel.repaint();
                }
            }
        }
        else {
            if (Main.board[tileY][tileX] != ' ' && Character.isLowerCase(Main.board[tileY][tileX])) {
                Main.selectedX = tileX;
                Main.selectedY = tileY;
                Main.printMoves = true;
                Main.moves = Main.getLegalMoves(Main.selectedX, Main.selectedY, true);
                if (Main.ply % 2 == 0) {
                    Main.panel.repaint();
                }
            }
        }
        
        System.out.println(tileX + " " + tileY);
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
