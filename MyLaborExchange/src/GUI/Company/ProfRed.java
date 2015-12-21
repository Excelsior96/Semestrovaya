package GUI.Company;

import Entities.Company;
import Exceptions.CompanyException;
import Repository.CompanyRepo;
import Utilities.GUIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Daniel Shchepetov on 21.12.2015.
 */
public class ProfRed {

    private static JFrame frame;
    private final static Font TOPFONT = new Font("Arial", Font.BOLD, 22);

    public ProfRed(final Company comp) {
        frame = new JFrame();
        frame.setBounds(20, 20, 500, 500);
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        JLabel label = new JLabel("Редактировать");
        label.setFont(TOPFONT);

        final ButtonGroup bg = new ButtonGroup();

        panel.add(label, GUIService.setTopLabelConstraints());
        final JLabel name = new JLabel("Название: ");
        final JLabel address = new JLabel("Адрес: ");
        final JLabel phone = new JLabel("Телефон: ");

        JTextField nameF = new JTextField(comp.getName());
        JTextField addressF = new JTextField(comp.getAdres());
        JTextField phoneF = new JTextField(comp.getPhone());

        panel.add(name, GUIService.setLabelConstraints());
        panel.add(nameF, GUIService.setTextFieldConstraints());
        panel.add(address, GUIService.setLabelConstraints());
        panel.add(addressF, GUIService.setTextFieldConstraints());
        panel.add(phone, GUIService.setLabelConstraints());
        panel.add(phoneF, GUIService.setTextFieldConstraints());


        JButton button = new JButton("Принять изменения");
        ActionListener list = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nam = nameF.getText();
                String addr = addressF.getText();
                String ph = phoneF.getText();
                String message;
                message = "Выполнено!";

                try {  //Validations and uniqueness
                    Company com = new Company(comp.getId(), nam,addr, ph);


                    CompanyRepo.update(com);
                    JOptionPane.showMessageDialog(panel, message);
                    nameF.setText("");
                    addressF.setText("");
                    phoneF.setText("");

                    frame.dispose();
                    AllCompFrame.getFrame().dispose();
                    new AllCompFrame();
                    CompProfFrame.getFrame().dispose();

                    new CompProfFrame(com.getId());
                } catch (CompanyException ex) {
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
