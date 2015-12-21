package GUI.Company;

import Entities.Company;
import Repository.CompanyRepo;
import Utilities.GUIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Daniel Shchepetov on 21.12.2015.
 */
public class SearchComp {

    JFrame frame = new JFrame();

    public SearchComp() {
        frame = new JFrame();
        frame.setBounds(70, 70, 500, 500);

        frame.setLayout(new BorderLayout());
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);


        JLabel label = new JLabel("Введите название");
        JTextField field = new JTextField();
        JButton but = new JButton("Поиск");
        ActionListener list = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = field.getText();
                if (name != "") {

                    ArrayList<Company> list = CompanyRepo.search(name);
                    if (list.size() != 0) {
                        frame.dispose();
                        new SearchCompList(list);

                    } else {
                        JOptionPane.showMessageDialog(panel, "Ничего не найдено");
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