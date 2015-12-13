package GUI.Company;

import Entities.Company;
import Exceptions.CompanyException;
import Repository.CompanyRepo;
import Utilities.GUIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Daniel Shchepetov on 13.12.2015.
 */
public class AllCompFrame {
    private static JFrame frame;

    public AllCompFrame() {
        frame = new JFrame();
        frame.setBounds(30, 30, 1200, 700);

        frame.setLayout(new BorderLayout());
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        String[] labels = {"ID", "Название", "Адрес", "Телефон", "Кол-во вакансий"};
        ArrayList<Company> list = CompanyRepo.getAll();
        String[][] rows = CompanyRepo.getTable(list);

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

        JButton  button = new JButton("Показать полный профиль");
       ActionListener listener = new ActionListener() {
          @Override
        public void actionPerformed(ActionEvent e) {
           String id = field.getText();
        try { //Validation
        CompanyRepo.intValidator(id);
          Company comp = CompanyRepo.getById(Integer.parseInt(id));
         if (comp != null) {
            new CompProfFrame(comp);

                        field.setText("");

        } else {
        JOptionPane.showMessageDialog(panel, "Ничего не найдено");
          }
        } catch (CompanyException ex) {
        JOptionPane.showMessageDialog(panel, "Ничего не найдено");
           ex.printStackTrace();
        }


          }


        };
        button.addActionListener(listener);
        panel.add(button, GUIService.setLabelConstraints());

        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }


    public static JFrame getFrame() {
        return frame;
    }
}
