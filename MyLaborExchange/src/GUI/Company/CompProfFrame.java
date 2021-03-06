package GUI.Company;

import Entities.Company;
import Entities.Unemployed;
import Entities.Vacancy;
import Exceptions.VacancyException;
import GUI.Unemployed.Archive;
import GUI.Unemployed.ArchiveComp;
import GUI.Vacancy.VacCompFrame;
import Repository.CompanyRepo;
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
public class CompProfFrame {
    private static JFrame frame;
    private final static Font TOPFONT = new Font("Arial", Font.BOLD, 22);

    public CompProfFrame(int cid) {
        frame = new JFrame();
        frame.setBounds(20, 20, 600, 600);
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        JLabel label = new JLabel("Профиль");
        label.setFont(TOPFONT);
Company comp = CompanyRepo.getById(cid);
        panel.add(label, GUIService.setTopLabelConstraints());
        final JLabel id = new JLabel("ID: " + comp.getId());
        final JLabel name = new JLabel("Название: " + comp.getName());
        final JLabel age = new JLabel("Адрес: " + comp.getAdres());
        final JLabel sex = new JLabel("Телефон: " + comp.getPhone());
        final JLabel sp = new JLabel("Кол-во свободных вакансий: " + comp.getCol());

        panel.add(id, GUIService.setTextFieldConstraints());
        panel.add(name, GUIService.setTextFieldConstraints());
        panel.add(age, GUIService.setTextFieldConstraints());
        panel.add(sex, GUIService.setTextFieldConstraints());
        panel.add(sp, GUIService.setTextFieldConstraints());

        JButton button = new JButton("Удалить профиль");
        ActionListener list = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Unemployed> list = CompanyRepo.hasUnemp(comp.getId());
                if (list.size() != 0) {
                    new ArchiveComp(comp.getId());
                    JOptionPane.showMessageDialog(panel, "В архиве обнаружены пользователи, зарегистрированные в этой фирме. Удаление невозможно");
                } else {
                    new ProfDelDialog(comp.getId());
                }
            }

        };
        button.addActionListener(list);

        // KMP!!! I don't want to continue...

        JLabel posL = new JLabel("Выберите предлагаемую должность: ");
        panel.add(posL, GUIService.setLabelConstraints());
        final JComboBox posC = new JComboBox(GUIService.getPos());
        panel.add(posC, GUIService.setTextFieldConstraints());

        JLabel payL = new JLabel("Введите зарплату: ");
        panel.add(payL, GUIService.setLabelConstraints());
        final JTextField payF = new JTextField();
        panel.add(payF, GUIService.setTextFieldConstraints());

        JLabel condL = new JLabel("Введите условия: ");
        panel.add(condL, GUIService.setLabelConstraints());
        final JTextField condF = new JTextField();
        panel.add(condF, GUIService.setTextFieldConstraints());

        JLabel reqL = new JLabel("Введите требования: ");
        panel.add(reqL, GUIService.setLabelConstraints());
        final JTextField reqF = new JTextField();
        panel.add(reqF, GUIService.setTextFieldConstraints());

        JLabel home = new JLabel("Введите жилищные условия: ");
        panel.add(home, GUIService.setLabelConstraints());
        final JComboBox homeF = new JComboBox(GUIService.getHomeV());
        panel.add(homeF, GUIService.setTextFieldConstraints());

        final JButton but = new JButton("Разместить вакансию");

        ActionListener lis = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pos = (String) posC.getSelectedItem();
                String pay = payF.getText();
                String cond = condF.getText();
                String req = reqF.getText();
                String home = (String) homeF.getSelectedItem();

                try {
                    VacancyRepo.intValidator(pay);

                    VacancyRepo.addVac(new Vacancy(comp.getId(), pos, Integer.parseInt(pay), cond, req, home));
                    JOptionPane.showMessageDialog(panel, "Успешно!");
                    payF.setText("");
                    condF.setText("");
                    reqF.setText("");
                    AllCompFrame.getFrame().dispose();
                    new AllCompFrame();
                    CompProfFrame.getFrame().dispose();

                    new CompProfFrame(cid);
                } catch (VacancyException e1) {

                    JOptionPane.showMessageDialog(panel, e1.getMessage());

                    e1.printStackTrace();
                }


            }
        };

        but.addActionListener(lis);

        final JButton b = new JButton("Показать вакансии");
        ActionListener l = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VacCompFrame(comp, cid);
            }
        };
        b.addActionListener(l);


        JButton bu = new JButton("Редактировать профиль");

        ActionListener li =  new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProfRed(comp);
            }
        };
        bu.addActionListener(li);

        panel.add(but, GUIService.setTextFieldConstraints());
        panel.add(b, GUIService.setTextFieldConstraints());
        panel.add(button, GUIService.setTextFieldConstraints());
        panel.add(bu, GUIService.setTextFieldConstraints());


        frame.add(panel);

        frame.setVisible(true);
    }


    public static JFrame getFrame() {
        return frame;
    }
}
