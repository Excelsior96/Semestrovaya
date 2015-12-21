package GUI.Company;

import Entities.Company;
import Repository.CompanyRepo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Daniel Shchepetov on 21.12.2015.
 */
public class SearchCompList {
    private JFrame frame;
    public SearchCompList(ArrayList<Company> list) {

        frame = new JFrame();
        frame.setBounds(30, 30, 1200, 700);

        frame.setLayout(new BorderLayout());
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        String[] labels = {"ID", "Название", "Адрес", "Телефон", "Кол-во свободных вакансий"};
        String[][] rows = CompanyRepo.getTable(list);

        JTable table = new JTable(rows, labels);
        table.setEnabled(false);
        table.setFillsViewportHeight(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JScrollPane pane = new JScrollPane(table);
        frame.add(pane, BorderLayout.NORTH);


        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
