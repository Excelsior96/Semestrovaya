package GUI.Find;

import Repository.FindRepo;
import Utilities.GUIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Daniel Shchepetov on 12.12.2015.
 */
public class FindDelDial {

    public FindDelDial(final int id) {
        final JDialog dial = new JDialog();
        dial.setBounds(200, 200, 350, 200);
        dial.setLayout(new GridBagLayout());

        JLabel jLabel = new JLabel("Вы действительно хотите удалить все заявки?");

        JButton button = new JButton("Да");
        JButton but = new JButton("Нет");

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FindRepo.deleteAll(id);
                FindUnempFrame.getFrame().dispose();
                dial.dispose();
                JOptionPane.showMessageDialog(dial, "Все заявки были удалены");

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
