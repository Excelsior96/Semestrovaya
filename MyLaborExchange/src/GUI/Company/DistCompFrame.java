package GUI.Company;

import Entities.Company;
import Repository.CompanyRepo;


import javax.swing.*;

/**
 * Created by Daniel Shchepetov on 13.12.2015.
 */
public class DistCompFrame {
    private static JFrame frame;
    private Company comp;

    public DistCompFrame(Company comp) {


        frame = new JFrame();
        frame.setBounds(30, 30, 1200, 700);


        String[] labels = {"ID", "Название","Адрес", "Телефон", "Количество вакансий", "В архиве"};

        String[][] rows = CompanyRepo.getDistTable(CompanyRepo.getConflicts(comp.getName()));
        final JTable table = new JTable(rows, labels);
        table.setEnabled(false);
        table.setFillsViewportHeight(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JScrollPane pane = new JScrollPane(table);
        frame.add(pane);

        frame.setVisible(true);
    }


    public Company getComp() {
        return comp;
    }

    public void setComp(Company comp) {
        this.comp = comp;
    }
}

