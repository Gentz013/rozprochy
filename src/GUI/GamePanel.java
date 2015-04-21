package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;

import logic.BoardData;


/**
 * Created by Jan on 4/19/2015.
 */
public class GamePanel extends JPanel implements MouseListener {

    BoardData board = new BoardData();

    public GamePanel(){
        addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(board.isPlayerTurn());

        if(board.ifGameWon() == 0) {

            if(board.isPlayerTurn()) {
                System.out.println(board.isPlayerTurn());

                Point click_location = e.getPoint();

                int x_coord = (int) click_location.getX() / 100;
                int y_coord = (int) click_location.getY() / 100;

                board.setBoardValue(x_coord, y_coord);
            }


            //System.out.println("(" + click_location.getX() + ", " + click_location.getY() + ")");
        }

        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void paintComponent(Graphics g){


        if(!board.isPlayerTurn()){
            board.makeAIMove();
        }

        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.fillRect(0, 0, 300, 300);

        g2d.setColor(Color.WHITE);
        g2d.drawLine(100, 0, 100, 300);
        g2d.drawLine(200, 0, 200, 300);

        g2d.drawLine(0, 100, 300, 100);
        g2d.drawLine(0, 200, 300, 200);


        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(board.getBoardValue(i,j) > 0){
                    //paint circle
                    g2d.setColor(Color.GREEN);
                    g2d.fillOval(100 * i+20, 100 * j+20, 60, 60);

                } else if (board.getBoardValue(i,j) < 0){
                    //paint cross
                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(10));
                    g2d.drawLine(100 * i+20, 100 * j+20, 100 * i + 100-20, 100 * j + 100-20);
                    g2d.drawLine(100 * i+20, 100 * j+100-20, 100 * i + 100-20, 100 * j+20);
                }
            }
        }

        int gameStatus = board.ifGameWon();

        g2d.setColor(Color.RED);
        g2d.setStroke(new BasicStroke(15));
        if(gameStatus != 0){
            System.out.println(gameStatus);
            switch(gameStatus) {
                case 1:
                    g2d.drawLine(0, 50, 300, 50);
                    break;
                case 2:
                    g2d.drawLine(0, 150, 300, 150);
                    break;
                case 3:
                    g2d.drawLine(0, 250, 300, 250);
                    break;
                case 4:
                    g2d.drawLine(50, 0, 50, 300);
                    break;
                case 5:
                    g2d.drawLine(150, 0, 150, 300);
                    break;
                case 6:
                    g2d.drawLine(250, 0, 250, 300);
                    break;
                case 7:
                    g2d.drawLine(0, 0, 300, 300);
                    break;
                case 8:
                    g2d.drawLine(0, 300, 300, 0);
                    break;
            }
        }

        g2d.setBackground(Color.BLACK);
    }
}
