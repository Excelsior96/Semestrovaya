package GUI.Vacancy;

import Entities.Company;
import Entities.Vacancy;
import Exceptions.VacancyException;
import Repository.VacancyRepo;
import Utilities.GUIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Daniel Shchepetov on 21.12.2015.
 */
public class VacRed {

    private static JFrame frame;
    private final static Font TOPFONT = new Font("Arial", Font.BOLD, 22);

    public VacRed(Company comp, int cid,final Vacancy vac) {
        frame = new JFrame();
        frame.setBounds(20, 20, 500, 500);
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        JLabel label = new JLabel("Редактировать");
        label.setFont(TOPFONT);

        final ButtonGroup bg = new ButtonGroup();

        panel.add(label, GUIService.setTopLabelConstraints());
        final JLabel name = new JLabel("Название: ");
        final JLabel pay = new JLabel("Оплата: ");
        final JLabel cond = new JLabel("Условия: ");
        final JLabel req = new JLabel("Требования: ");
        final  JLabel home  = new JLabel("Жилищные условия");


        JTextField payF = new JTextField(String.valueOf(vac.getPayment()));
        JComboBox nameC = new JComboBox(GUIService.getPos());
        nameC.setSelectedItem(vac.getPos());
        JTextField condF = new JTextField(vac.getCond());
        JComboBox homeC = new JComboBox(GUIService.getHomeV());
        homeC.setSelectedItem(vac.getHome());
        JTextField reqF = new JTextField(vac.getReq());


        panel.add(name, GUIService.setLabelConstraints());
        panel.add(nameC, GUIService.setTextFieldConstraints());
        panel.add(pay, GUIService.setLabelConstraints());
        panel.add(payF, GUIService.setTextFieldConstraints());
        panel.add(cond, GUIService.setLabelConstraints());
        panel.add(condF, GUIService.setTextFieldConstraints());
        panel.add(req, GUIService.setLabelConstraints());
        panel.add(reqF, GUIService.setTextFieldConstraints());
        panel.add(home, GUIService.setLabelConstraints());
        panel.add(homeC, GUIService.setTextFieldConstraints());

        JButton button = new JButton("Принять изменения");
        ActionListener list = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
             String nam = (String) nameC.getSelectedItem();
                String pay = payF.getText();
                String con = condF.getText();
                String r = reqF.getText();
                String ho = (String) homeC.getSelectedItem();
                String message;
                message = "Выполнено!";

                try {  //Validations and uniqueness
                   String id = String.valueOf(vac.getId());
                    VacancyRepo.intValidator(pay);
                    Vacancy v = new Vacancy(id, nam, Integer.parseInt(pay), con, r, ho);


                    VacancyRepo.update(v);
                    JOptionPane.showMessageDialog(panel, message);

                    frame.dispose();
                    VacCompFrame.getFrame().dispose();
                    new VacCompFrame(comp, cid);
                } catch (VacancyException ex) {
                    message = ex.getMessage();
                    JOptionPane.showMessageDialog(panel, message);
                    ex.printStackTrace();
                }


            }
        };

        button.addActionListener(list);
        panel.add(button, GUIService.setTextFieldConstraints());
        frame.add(panel);

        frame.setVisible(true);
    }
}