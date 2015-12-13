package GUI.Find;

import Entities.Find;
import Entities.Unemployed;
import Entities.Vacancy;
import Exceptions.FindException;
import Repository.FindRepo;
import Repository.VacancyRepo;
import Utilities.GUIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Daniel Shchepetov on 12.12.2015.
 */
public class FindUnempFrame {

    private static JFrame frame;

    public FindUnempFrame(Unemployed unemp) {
        frame = new JFrame();
        frame.setBounds(30, 30, 1200, 700);

        frame.setLayout(new BorderLayout());
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        String[] labels = {"ID", "Название", "ID безработного", "ФИО"};
        ArrayList<Find> list = FindRepo.getAllById(unemp.getId());
        String[][] rows = FindRepo.getTable(list);

        JTable table = new JTable(rows, labels);
        table.setEnabled(false);
        table.setFillsViewportHeight(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JScrollPane pane = new JScrollPane(table);
        frame.add(pane, BorderLayout.NORTH);

        JLabel label = new JLabel("Введите ID:");
        panel.add(label, GUIService.setTopLabelConstraints());


        final JTextField field = new JTextField();
        panel.add(field, GUIService.setTextFieldConstraints());

        JButton button = new JButton("Найти вакансии");
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = field.getText();
                try { //Validation
                    FindRepo.intValidator(id);
                    ArrayList<Vacancy> list = VacancyRepo.getByFindId(id);
                    if (list.size() != 0) {
                        frame.dispose();
                        new FindVacFrame(list, unemp.getId(), id);


                    } else {
                        JOptionPane.showMessageDialog(panel, "Ничего не найдено");
                    }
                } catch (FindException ex) {
                    JOptionPane.showMessageDialog(panel, "Ничего не найдено");
                    ex.printStackTrace();
                }

            }

        };

        JButton but = new JButton("Удалить по ID");
        ActionListener lis = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = field.getText();
                try { //Validation
                    FindRepo.intValidator(id);
                    FindRepo.deleteById(id);
                    JOptionPane.showMessageDialog(panel, "Выполнено");
                    frame.dispose();
                    new FindUnempFrame(unemp);
                } catch (FindException ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage());
                    ex.printStackTrace();
                }

            }

        };
        JButton b = new JButton("Удалить все заявки");
        ActionListener l = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new FindDelDial(unemp.getId());

            }


        };
        button.addActionListener(listener);
        but.addActionListener(lis);
        b.addActionListener(l);
        panel.add(button, GUIService.setTextFieldConstraints());
        panel.add(but, GUIService.setTextFieldConstraints());
        panel.add(b, GUIService.setTextFieldConstraints());

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    public static JFrame getFrame() {
        return frame;
    }
}
