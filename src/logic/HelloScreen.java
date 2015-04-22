package logic;

import GUI.GameScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Jan on 4/21/2015.
 */
public class HelloScreen extends JFrame implements ActionListener{

    private ButtonGroup gameModeButtonGroup;
    private JRadioButton singlePlayerGameModeButton;
    private JRadioButton multiPlayerGameModeButton;


    public HelloScreen(){


        singlePlayerGameModeButton = new JRadioButton("Singleplayer mode");
        singlePlayerGameModeButton.setSelected(true);

        multiPlayerGameModeButton = new JRadioButton("Multiplayer mode");
        multiPlayerGameModeButton.setSelected(false);

        gameModeButtonGroup = new ButtonGroup();
        gameModeButtonGroup.add(singlePlayerGameModeButton);
        gameModeButtonGroup.add(multiPlayerGameModeButton);

        JLabel messageLabel = new JLabel("Please select your gamemode");
        JPanel labelPanel = new JPanel();
        //labelPanel.add(messageLabel);
        //messageLabel.setVisible(true);


        JButton okButton = new JButton("OK");
        okButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(messageLabel);
        buttonPanel.add(singlePlayerGameModeButton);
        buttonPanel.add(multiPlayerGameModeButton);
        buttonPanel.add(okButton);
        buttonPanel.setVisible(true);

        setSize(300, 150);
        setTitle("Tic Tac Toe");
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(3, 1));
        setVisible(true);

        add(buttonPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(singlePlayerGameModeButton.isSelected()){
            //single player mode
            dispose();
            new GameScreen("Tic Tac Toe");
        } else {

            //multi player mode
            dispose();
            JFrame inputFrame = new JFrame();
            inputFrame.setTitle("Please enter your nickname");
            inputFrame.setSize(300,100);
            inputFrame.setResizable(false);

            JPanel inputPanel = new JPanel();

            JTextField jTextField = new JTextField(20);
            JButton inputFrameButton = new JButton("OK");

            inputFrameButton.addActionListener(e1 -> {
                String nickname = jTextField.getText();
                System.out.println(nickname);
                inputFrame.dispose();
                new ClientServer(nickname);
            });

            inputPanel.add(jTextField);
            inputPanel.add(inputFrameButton);
            inputFrame.add(inputPanel);
            inputFrame.setVisible(true);




        }

    }
}
