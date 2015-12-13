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
 * Created by Daniel Shchepetov on 13.12.2015.
 */
public class DistDialog {
    public DistDialog(final Company comp) {
        final JDialog dial = new JDialog();
        dial.setBounds(200, 200, 400, 200);
        dial.setLayout(new GridBagLayout());

        JLabel jLabel = new JLabel("Возможно, анкета уже была зарегистрирована. Продолжить?");

        JButton button = new JButton("Да");
        JButton but = new JButton("Нет");
        JButton b = new JButton("Показать найденное");
        ActionListener a = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DistCompFrame(comp);
            }
        };

        ActionListener ac = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dial.dispose();
            }
        };

        ActionListener act = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    CompanyRepo.addFull(comp);
                } catch (CompanyException e1) {
                    e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(dial, "Регистрация прошла успешно");
                dial.dispose();
            }
        };
        button.addActionListener(act);
        but.addActionListener(ac);
        b.addActionListener(a);
        dial.add(jLabel, GUIService.setTopLabelConstraints());
        dial.add(button, GUIService.setLabelConstraints());
        dial.add(but, GUIService.setLabelConstraints());
        dial.add(b, GUIService.setLabelConstraints());

        dial.setVisible(true);
    }
}
