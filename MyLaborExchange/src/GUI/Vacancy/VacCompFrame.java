package GUI.Vacancy;

import Entities.Company;
import Entities.Find;
import Entities.Vacancy;
import Exceptions.VacancyException;
import GUI.Company.AllCompFrame;
import GUI.Company.CompProfFrame;
import Repository.FindRepo;
import Repository.VacancyRepo;
import Utilities.GUIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by Daniel Shchepetov on 13.12.2015.
 */
public class VacCompFrame {
    private static JFrame frame;

    public VacCompFrame(Company comp) {
        frame = new JFrame();
        frame.setBounds(30, 30, 1200, 700);

        frame.setLayout(new BorderLayout());
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);

        String[] labels = {"ID", "Название", "ID компании", "Название компании"};
        ArrayList<Vacancy> list = VacancyRepo.getAllById(comp.getId());
        String[][] rows = VacancyRepo.getTable(list);

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

        JButton button = new JButton("Найти заявки");
        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = field.getText();
                try { //Validation
                    VacancyRepo.intValidator(id);
                    ArrayList<Find> list = FindRepo.getByVacId(id);
                    if (list.size() != 0) {
                        frame.dispose();
                        new VacFindFrame(list);


                    } else {
                        JOptionPane.showMessageDialog(panel, "Ничего не найдено");
                    }
                } catch (VacancyException ex) {
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
                    VacancyRepo.intValidator(id);
                    VacancyRepo.deleteById(id);
                    JOptionPane.showMessageDialog(panel, "Выполнено");
                    frame.dispose();
                    //refreshing
                    AllCompFrame.getFrame().dispose();
                    new AllCompFrame();
                    new VacCompFrame(comp);
                    CompProfFrame.getFrame().dispose();
                    new CompProfFrame(comp);
                } catch (VacancyException ex) {
                    JOptionPane.showMessageDialog(panel, ex.getMessage());
                    ex.printStackTrace();
                }


            }

        };
        JButton b = new JButton("Удалить все");
        ActionListener l = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VacDelDial(comp.getId());
                frame.dispose();


            }


        };

        JButton bu = new JButton("Сгенерировать объявление");
        ActionListener li = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = field.getText();
                try { //Validation
                    VacancyRepo.intValidator(id);
                   ArrayList list = VacancyRepo.getAdById(Integer.parseInt(id), comp.getId());
                     new AdFrame(list);

                } catch (VacancyException e1) {
                    JOptionPane.showMessageDialog(panel, e1.getMessage());
                    e1.printStackTrace();
                }


            }};



            JButton butt = new JButton("Сгенерировать все объявления");
            ActionListener liste = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        ArrayList<Vacancy> list = VacancyRepo.getAllAd(comp.getId());
                        new AdFrame(list);
                }
            };

            butt.addActionListener(liste);
            button.addActionListener(listener);
            but.addActionListener(lis);
            b.addActionListener(l);
            bu.addActionListener(li);
            panel.add(button,GUIService.setTextFieldConstraints());
            panel.add(but,GUIService.setTextFieldConstraints());
            panel.add(b,GUIService.setTextFieldConstraints());
            panel.add(bu,GUIService.setTextFieldConstraints());
            panel.add(butt,GUIService.setTextFieldConstraints());

            frame.add(panel,BorderLayout.CENTER);
            frame.setVisible(true);
        }


    public static JFrame getFrame() {
        return frame;
    }
}



