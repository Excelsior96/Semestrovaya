package GUI.Vacancy;

import Exceptions.VacancyException;
import GUI.Company.AllCompFrame;
import GUI.Company.CompProfFrame;
import Repository.VacancyRepo;
import Utilities.GUIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Daniel Shchepetov on 13.12.2015.
 */
public class VacDelDial {
    public VacDelDial(final int id) {
        final JDialog dial = new JDialog();
        dial.setBounds(200, 200, 350, 200);
        dial.setLayout(new GridBagLayout());

        JLabel jLabel = new JLabel("Вы действительно хотите удалить все вакансии?");

        JButton button = new JButton("Да");
        JButton but = new JButton("Нет");

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                VacancyRepo.deleteAll(id);
                VacCompFrame.getFrame().dispose();
                dial.dispose();
                JOptionPane.showMessageDialog(dial, "Все вакансии были удалены");

                AllCompFrame.getFrame().dispose();
                new AllCompFrame();



            }
        };
        button.addActionListener(listener);

        ActionListener lis = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dial.dispose();
            }
        };

        but.addActionListener(lis);
        dial.add(jLabel, GUIService.setTopLabelConstraints());
        dial.add(button, GUIService.setLabelConstraints());
        dial.add(but, GUIService.setLabelConstraints());
        dial.setVisible(true);
    }

}
