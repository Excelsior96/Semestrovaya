package GUI.Pos;

import Exceptions.PosException;
import Repository.PosRepo;
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

    public static JPanel getPanel(){
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
             String str = nameF.getText();
                try {
                    PosRepo.addNewPos(str);
                    JOptionPane.showMessageDialog(panel,"Успешно!");
                    nameF.setText("");
                } catch (PosException e1) {
                    JOptionPane.showMessageDialog(panel,e1.getMessage());
                    e1.printStackTrace();
                }
            }
        };
        but.addActionListener(list);
        panel.add(but, GUIService.setTextFieldConstraints());
        return panel;
}}
