package GUI.Pos;

import Utilities.GUIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Daniel Shchepetov on 17.12.2015.
 */
public class PosPanel {

    final static Font TOPFONT = new Font("Arial", Font.BOLD, 22);

    PosPanel(){
    JPanel panel = new JPanel();
    panel.setLayout(new GridBagLayout());
    panel.setBackground(Color.LIGHT_GRAY);

    JLabel label = new JLabel("Добавить новую должность");
    label.setFont(TOPFONT);
    panel.add(label, GUIService.setTopLabelConstraints());

    JLabel name = new JLabel("Название(с большой буквы): ");
    panel.add(name, GUIService.setLabelConstraints());
    final JTextField nameF = new JTextField(50);
    panel.add(nameF, GUIService.setTextFieldConstraints());
        JButton but = new JButton("Добавить");
        ActionListener list = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
         //Validation

            }
        };
        but.addActionListener(list);
}}
