package GUI.Company;

import Entities.Company;
import Exceptions.CompanyException;
import Repository.CompanyRepo;
import Utilities.GUIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Daniel Shchepetov on 06.12.2015.
 */
public class CompaniesPanel {
    final static Font TOPFONT = new Font("Arial", Font.BOLD, 22);

    public static JPanel getPanel() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        JLabel label = new JLabel("Добавить новую фирму");
        label.setFont(TOPFONT);
        panel.add(label, GUIService.setTopLabelConstraints());

        JLabel name = new JLabel("Название: ");
        panel.add(name, GUIService.setLabelConstraints());
        final JTextField nameF = new JTextField(40);
        panel.add(nameF, GUIService.setTextFieldConstraints());

        JLabel adres = new JLabel("Адрес: ");
        panel.add(adres, GUIService.setLabelConstraints());
        final JTextField adresF = new JTextField(40);
        panel.add(adresF, GUIService.setTextFieldConstraints());

        JLabel phone = new JLabel("Телефон: ");
        panel.add(phone, GUIService.setLabelConstraints());
        final JTextField phoneF = new JTextField(30);
        panel.add(phoneF, GUIService.setTextFieldConstraints());
        JButton addUnemployedButton = new JButton("Регистрация");
        ActionListener addListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameF.getText();
                String address = adresF.getText();
                String phone = phoneF.getText();

                String message;
                message = "Регистрация прошла успешно";

                try {  //uniqueness

                    Company comp = new Company(name, address, phone);
                    if (CompanyRepo.unempDist(comp.getName())) {
                        CompanyRepo.addFull(comp);
                        JOptionPane.showMessageDialog(panel, message);
                        nameF.setText("");
                        adresF.setText("");
                        phoneF.setText("");
                    } else {
                        //if there is a match in DB, it prompts you for confirmation of adding
                        new DistDialog(comp);
                    }

                } catch (CompanyException ex) {
                    message = ex.getMessage();
                    JOptionPane.showMessageDialog(panel, message);
                    ex.printStackTrace();
                }


            }
        };
        addUnemployedButton.addActionListener(addListener);
        panel.add(addUnemployedButton, GUIService.setTextFieldConstraints());
        JButton button = new JButton("Показать все фирмы");
        ActionListener allListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AllCompFrame();

            }
        };

        button.addActionListener(allListener);
        panel.add(button, GUIService.setTextFieldConstraints());

        JButton but = new JButton("Поиск");
        ActionListener list = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SearchComp();
            }
        };
        but.addActionListener(list);
        panel.add(but, GUIService.setTextFieldConstraints());
        return panel;
    }
}
