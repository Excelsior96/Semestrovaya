package GUI.Unemployed;

import Entities.Unemployed;
import Exceptions.UnemployedException;
import Repository.CompanyRepo;
import Repository.UnemployedRepo;
import Utilities.GUIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Daniel Shchepetov on 17.12.2015.
 */
public class ArchiveComp {
    private static JFrame frame;

    public ArchiveComp(int id) {
        frame = new JFrame();
        frame.setBounds(30, 30, 1200, 700);

        frame.setLayout(new BorderLayout());
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        ArrayList<Unemployed> list = CompanyRepo.hasUnemp(id);
        String[] labels = {"ID", "ФИО", "Год рождения", "Пол", "Адрес", "Телефон", "Профессия", "Образование"};
        String[][] rows = UnemployedRepo.getTable(list);

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

        JButton button = new JButton("Показать полный профиль");
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = field.getText();
                try { //Validation
                    UnemployedRepo.intValidator(id);
                    Unemployed unemp = UnemployedRepo.getArcById(Integer.parseInt(id));
                    if (unemp != null) {
                        new UnemplProfArcFrame(unemp);

                        field.setText("");

                    } else {
                        JOptionPane.showMessageDialog(panel, "Ничего не найдено");
                    }
                } catch (UnemployedException ex) {
                    JOptionPane.showMessageDialog(panel, "Ничего не найдено");
                    ex.printStackTrace();
                }


            }


        };
        button.addActionListener(listener);

        JButton butt = new JButton("Очистить");
        ActionListener listen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
          UnemployedRepo.deleteArchive(id);
                frame.dispose();
                JOptionPane.showMessageDialog(frame, "Выполнено!");


            }
        };
butt.addActionListener(listen);
        panel.add(button, GUIService.setTextFieldConstraints());
panel.add(butt, GUIService.setTextFieldConstraints());
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    public static JFrame getFrame() {
        return frame;
    }
}