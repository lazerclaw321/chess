import java.awt.event.*;
import java.awt.*;

public class UserMouse implements MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        Point position = MouseInfo.getPointerInfo().getLocation();
        System.out.println(position.x + " " + position.y);
        int tileX = (int) Math.floor(((double) position.x - 10) / 50);
        int tileY = (int) Math.floor(((double) position.y - 30) / 50);
        if (Main.printMoves) {
            for (int[] move : Main.moves) {
                if (move[0] == tileX && move[1] == tileY) {
                    Main.moveX = tileX;
                    Main.moveY = tileY;
                    break;
                }
            }   
        }
        else {
            if (Main.board[tileY][tileX] != ' ') {
                Main.selectedX = tileX;
                Main.selectedY = tileY;
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
