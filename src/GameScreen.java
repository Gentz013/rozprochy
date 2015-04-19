import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Jan on 4/17/2015.
 */
public class GameScreen extends JFrame {

    int[][] game_board;


    public GameScreen(String title){
        super(title);
        this.game_board = new int[10][10];


        JPanel upperPane = new JPanel(new BorderLayout(2,1));
        JLabel scoreLabel = new JLabel("Score: ", SwingConstants.LEFT);
        JLabel shotsLabel = new JLabel("Shots: ", SwingConstants.RIGHT);
        scoreLabel.setVerticalAlignment(SwingConstants.TOP);
        shotsLabel.setVerticalAlignment(SwingConstants.TOP);
        upperPane.add(scoreLabel, BorderLayout.WEST);
        upperPane.add(shotsLabel);
        upperPane.setPreferredSize(new Dimension(300, 20));
        upperPane.setVisible(true);
        add(upperPane, BorderLayout.NORTH);

        GamePanel gamePanel = new GamePanel();
        add(gamePanel);

        setSize(300, 345);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }


}
