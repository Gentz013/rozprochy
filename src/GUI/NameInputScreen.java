package GUI;

import logic.GameClient;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Jan on 4/24/2015.
 */
public class NameInputScreen extends JFrame {

    public NameInputScreen(String message) {

        JFrame inputFrame = new JFrame();
        inputFrame.setTitle(message);
        inputFrame.setSize(300, 100);
        inputFrame.setResizable(false);

        JPanel inputPanel = new JPanel();

        JTextField jTextField = new JTextField(20);
        JButton inputFrameButton = new JButton("OK");

        inputFrameButton.addActionListener(e1 -> {
            String nickname = jTextField.getText();
            inputFrame.dispose();

            try {
                new GameClient(nickname);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        inputPanel.add(jTextField);
        inputPanel.add(inputFrameButton);
        inputFrame.add(inputPanel);
        inputFrame.setVisible(true);
    }
}
