package GUI;

import logic.IServerManager;
import logic.Player;
import logic.PlayerRejectedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

/**
 * Created by Jan on 4/21/2015.
 */
public class HelloScreen extends JFrame implements ActionListener{

    private ButtonGroup gameModeButtonGroup;
    private JRadioButton singlePlayerGameModeButton;
    private JRadioButton multiPlayerGameModeButton;
    private Player player;
    private IServerManager serverManager;


    public HelloScreen(Player player, IServerManager serverManager){
        this.player=player;
        this.serverManager = serverManager;

        singlePlayerGameModeButton = new JRadioButton("Singleplayer mode");
        singlePlayerGameModeButton.setSelected(true);

        multiPlayerGameModeButton = new JRadioButton("Multiplayer mode");
        multiPlayerGameModeButton.setSelected(false);

        gameModeButtonGroup = new ButtonGroup();
        gameModeButtonGroup.add(singlePlayerGameModeButton);
        gameModeButtonGroup.add(multiPlayerGameModeButton);

        JLabel messageLabel = new JLabel("Please select your gamemode");
        JPanel labelPanel = new JPanel();

        JButton okButton = new JButton("OK");
        okButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(messageLabel);
        buttonPanel.add(singlePlayerGameModeButton);
        buttonPanel.add(multiPlayerGameModeButton);
        buttonPanel.add(okButton);
        buttonPanel.setVisible(true);

        setSize(300, 150);
        setTitle("Hello " + player.getNickname() + " please select your gamemode");
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
            try {
                serverManager.chooseGame(player, 1);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            } catch (PlayerRejectedException e1) {
                e1.printStackTrace();
            }
        } else {
            //multi player mode
            dispose();
            try {
                serverManager.chooseGame(player, -1);
            } catch (RemoteException e1) {
                e1.printStackTrace();
            } catch (PlayerRejectedException e1) {
                e1.printStackTrace();
            }
        }
    }
}
