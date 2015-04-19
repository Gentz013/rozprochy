import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Jan on 4/19/2015.
 */
public class GamePanel extends JPanel implements MouseListener {

    int[][] board = new int[3][3];
    int turn = 1;
    Graphics2D globalg2d;

    public GamePanel(){
        addMouseListener(this);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        Point click_location = e.getPoint();

        int x_coord = (int) click_location.getX()/100;
        int y_coord = (int) click_location.getY()/100;

        board[x_coord][y_coord] = turn;
        turn*=(-1);

        repaint();


        System.out.println("(" + click_location.getX() + ", " + click_location.getY() + ")");
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
                if(board[i][j] > 0){
                    //paint circle
                    g2d.setColor(Color.GREEN);
                    g2d.fillOval(100 * i+20, 100 * j+20, 60, 60);

                } else if (board[i][j] < 0){
                    //paint cross
                    g2d.setColor(Color.RED);
                    g2d.setStroke(new BasicStroke(10));
                    g2d.drawLine(100 * i+20, 100 * j+20, 100 * i + 100-20, 100 * j + 100-20);
                    g2d.drawLine(100 * i+20, 100 * j+100-20, 100 * i + 100-20, 100 * j+20);
                }
            }
        }

        g2d.setBackground(Color.BLACK);
    }


}
