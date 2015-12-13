package GUI.Vacancy;

import Entities.Vacancy;
import Repository.VacancyRepo;
import Utilities.GUIService;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Daniel Shchepetov on 13.12.2015.
 */
public class AdFrame {
    private static JFrame frame;

    public AdFrame(ArrayList<Vacancy> list) {


        frame = new JFrame();
        frame.setBounds(30, 30, 500, 500);
        frame.setLayout(new BorderLayout());


        JTextArea ar = new JTextArea();
        ar.setLineWrap(true);
        ar.setEditable(false);

        for (Vacancy vac: list){


         String advert = "Фирма "  + vac.getCompany() + " приглашает на работу по профессии " + vac.getName() + "\n" + "Зарплата: " + vac.getPayment() + "\n" + "Требования: " + vac.getReq() +
                 "\n" + "Условия: " + vac.getCond() + "\n" + "Жилье: " + vac.getHome() + "\n"
                 + "Обращаться по телефону: " +  vac.getPhone() +  " или по адресу: " + vac.getAdres() + "\n\n\n" ;
            ar.setText(ar.getText() + advert);
        }

        JScrollPane pane = new JScrollPane(ar);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



        frame.getContentPane().add(pane);
        frame.setVisible(true);

    }


    public static JFrame getFrame() {
        return frame;
    }

}

