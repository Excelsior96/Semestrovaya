package GUI.Unemployed;

import Entities.Unemployed;
import Repository.UnemployedRepo;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Created by Daniel Shchepetov on 11.12.2015.
 */
public class SearchUnemp {
    private static JFrame frame;


    public SearchUnemp(ArrayList<Unemployed> list) {


        frame = new JFrame();
        frame.setBounds(30, 30, 1200, 700);


        String[] labels = {"ID", "ФИО", "Год рождения", "Пол", "Адрес", "Телефон", "Профессия", "Образование", "В архиве"};

        String[][] rows = UnemployedRepo.getDistTable(list);
        final JTable table = new JTable(rows, labels);
        table.setEnabled(false);
        table.setFillsViewportHeight(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JScrollPane pane = new JScrollPane(table);
        frame.add(pane);

        frame.setVisible(true);
    }
}

