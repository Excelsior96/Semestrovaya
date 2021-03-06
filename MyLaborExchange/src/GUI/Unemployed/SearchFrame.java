package GUI.Unemployed;

import Entities.Unemployed;
import Exceptions.UnemployedException;
import Repository.UnemployedRepo;
import Utilities.GUIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Daniel Shchepetov on 21.12.2015.
 */
public class SearchFrame {

    private static JFrame frame;

    public SearchFrame() {
        frame = new JFrame();
        frame.setBounds(70, 70, 500, 500);

        frame.setLayout(new BorderLayout());
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);


        JLabel label = new JLabel("Введите ФИО");
        JTextField field = new JTextField();
        JButton but = new JButton("Поиск");
        ActionListener list = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = field.getText();
                if (name != "") {

                    ArrayList<Unemployed> list = null;

                        list = UnemployedRepo.search(name);

                    if (list.size() != 0) {
                        frame.dispose();
                        new SearchUnemp(list);

                    } else {
                        JOptionPane.showMessageDialog(panel, "Ничего не найдено или значения некорректны");
                    }


                } else {
                    JOptionPane.showMessageDialog(panel, "Поле не заполнено");
                }

            }
        };
        but.addActionListener(list);
        panel.add(label, GUIService.setLabelConstraints());
        panel.add(field, GUIService.setTextFieldConstraints());
        panel.add(but, GUIService.setTextFieldConstraints());
        frame.add(panel);
        frame.setVisible(true);
    }
}
