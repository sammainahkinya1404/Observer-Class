////////////////////////////
// File: ServerParkingGui.java
// Author: R Judd, modified by M I Schwartz
// This file creates a dynamic client for the ParkingOfficeServer using the inter-process
// communications protocol implemented.
////////////////////////////
package edu.du.ict4315.parking.clients;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ServerParkingGui extends JFrame {

  private final JTextArea textArea;

  public ServerParkingGui(List<ClientCommand> commands) {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    setPreferredSize(new Dimension(800, 400));
    getContentPane().setLayout(new GridLayout()); // the default GridLayout is a 1x1 grid
    getContentPane().add(mainPanel);


    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
    mainPanel.add(buttonPanel);
    textArea = new JTextArea();
    mainPanel.add(textArea);
    
    for (ClientCommand cmd : commands) {
      JButton button = new JButton(cmd.name());
      button.addActionListener(new CommandListener(textArea, cmd));
      buttonPanel.add(button);
    }

    JButton quitButton = new JButton("Quit");
    buttonPanel.add(quitButton);

    quitButton.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        System.exit(0);
      }

    });

    pack();
  }

  static Map<String, String> dialog(List<String> labels) {
    JPanel input = new JPanel(new GridBagLayout());
    GridBagConstraints config = new GridBagConstraints();

    List<JTextField> fields = new ArrayList<>();
    for (int i = 0; i < labels.size(); ++i) {
      JTextField field = new JTextField(20);
      fields.add(field);
      config.gridx = 0;
      config.gridy = i;
      input.add(new JLabel(labels.get(i)), config);
      config.gridx = 1;
      input.add(field, config);
    }
    int result = JOptionPane.showConfirmDialog(
        null, input, "Please Enter the Values", JOptionPane.OK_CANCEL_OPTION);
    Map<String, String> values = new LinkedHashMap<>();
    if (result == JOptionPane.OK_OPTION) {
      for (int i = 0; i < labels.size(); ++i) {
        values.put(labels.get(i), fields.get(i).getText());
      }
    }
    return values;
  }

  public static void main(String args[]) {
    EventQueue.invokeLater(new Runnable() {
      @Override
      public void run() {
        // TODO: NOT WORKING YET
        Map<String, ClientCommand> cmds = ServerClient.displayCommands();
        ArrayList<ClientCommand> commands = new ArrayList<>();
        for (var s: cmds.entrySet()) {
          commands.add(s.getValue());
        }
        new ServerParkingGui(commands).setVisible(true);
        // new ServerParkingGui(new ArrayList<>(ServerClient.displayCommands().values())).setVisible(true);
      }
    });
  }

  private static class CommandListener implements ActionListener {

    private final ClientCommand command;
    private final JTextArea textArea;

    CommandListener(JTextArea textArea, ClientCommand command) {
      this.textArea = textArea;
      this.command = command;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      Map<String, String> values = dialog(command.fieldNames());
      String output = "";
      for (Map.Entry<String, String> entry : values.entrySet()) {
        output += String.format("%s=%s%n", entry.getKey(), entry.getValue());
      }
      textArea.setText(output);
      if (values.size() > 0) {
        String response = command.execute(values);
        textArea.setText(String.format("%s%n%s", textArea.getText(), response));
      }
    }

  }
}
