package GUI.Vacancy;

import Entities.Find;
import Repository.FindRepo;
import Repository.VacancyRepo;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Daniel Shchepetov on 12.12.2015.
 */
public class VacFindFrame {
    private static JFrame frame;

    public VacFindFrame(ArrayList<Find> list) {


        frame = new JFrame();
        frame.setBounds(30, 30, 1200, 700);

        frame.setLayout(new BorderLayout());
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        String[] labels = {"ID", "Желаемая должность", "ID безработного", "Имя безработного"};

        String[][] rows = FindRepo.getTable(list);

        JTable table = new JTable(rows, labels);
        table.setEnabled(false);
        table.setFillsViewportHeight(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JScrollPane pane = new JScrollPane(table);
        frame.add(pane);
        frame.setVisible(true);

    }


    public static JFrame getFrame() {
        return frame;
    }

}


