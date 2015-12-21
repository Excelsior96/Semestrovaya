package GUI.Unemployed;

import Entities.Unemployed;
import Exceptions.UnemployedException;
import Repository.UnemployedRepo;
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

    public ProfRed(final Unemployed unemp) {
        frame = new JFrame();
        frame.setBounds(20, 20, 500, 500);
        final JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.LIGHT_GRAY);
        JLabel label = new JLabel("Редактировать");
        label.setFont(TOPFONT);

        final ButtonGroup bg = new ButtonGroup();

        panel.add(label, GUIService.setTopLabelConstraints());
        final JLabel name = new JLabel("ФИО: ");
        final JLabel sex = new JLabel("Пол: ");
        final JLabel sp = new JLabel("Семейное положение: ");
        final JLabel prof = new JLabel("Профессия: ");
        final JLabel stud = new JLabel("Образование: ");
        final JLabel home = new JLabel("Жилищные условия: ");
        final JLabel address = new JLabel("Адрес: ");
        final JLabel phone = new JLabel("Телефон: ");
        final JLabel lastwork = new JLabel("Предыдущее место работы: ");
        final JLabel lastpos = new JLabel("Должность: ");
        final JLabel dismiss = new JLabel("Причина увольнения: ");


        JTextField nameF = new JTextField(unemp.getName());
        JRadioButton man = new JRadioButton("М", true);
        JRadioButton woman = new JRadioButton("Ж", false);
        man.setBackground(Color.LIGHT_GRAY);
        woman.setBackground(Color.LIGHT_GRAY);
        JComboBox spC = new JComboBox(GUIService.getSP());
        spC.setSelectedItem(unemp.getSp());
        JTextField profF = new JTextField(unemp.getProf());
        JComboBox studC = new JComboBox(GUIService.getStud());
        studC.setSelectedItem(unemp.getStud());
        JComboBox homeC = new JComboBox(GUIService.getHome());
        homeC.setSelectedItem(unemp.getHome());
        JTextField addressF = new JTextField(unemp.getAddress());
        JTextField phoneF = new JTextField(unemp.getPhone());
        JTextField lastworkF = new JTextField(unemp.getLastWork());
        JTextField lastposF = new JTextField(unemp.getLastPos());
        JTextField dismissF = new JTextField(unemp.getDismiss());

        panel.add(name, GUIService.setLabelConstraints());
        panel.add(nameF, GUIService.setTextFieldConstraints());
        panel.add(sex, GUIService.setLabelConstraints());
        bg.add(man);
        bg.add(woman);
        panel.add(man, GUIService.setRadioButtonConstraints());
        panel.add(woman, GUIService.setTextFieldConstraints());

        panel.add(sp, GUIService.setLabelConstraints());
        panel.add(spC, GUIService.setTextFieldConstraints());
        panel.add(prof, GUIService.setLabelConstraints());
        panel.add(profF, GUIService.setTextFieldConstraints());
        panel.add(stud, GUIService.setLabelConstraints());
        panel.add(studC, GUIService.setTextFieldConstraints());
        panel.add(home, GUIService.setLabelConstraints());
        panel.add(homeC, GUIService.setTextFieldConstraints());
        panel.add(address, GUIService.setLabelConstraints());
        panel.add(addressF, GUIService.setTextFieldConstraints());
        panel.add(phone, GUIService.setLabelConstraints());
        panel.add(phoneF, GUIService.setTextFieldConstraints());
        panel.add(lastwork, GUIService.setLabelConstraints());
        panel.add(lastworkF, GUIService.setTextFieldConstraints());
        panel.add(lastpos, GUIService.setLabelConstraints());
        panel.add(lastposF, GUIService.setTextFieldConstraints());
        panel.add(dismiss, GUIService.setLabelConstraints());
        panel.add(dismissF, GUIService.setTextFieldConstraints());


        JButton button = new JButton("Принять изменения");
        ActionListener list = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameF.getText();
                String sp = (String) spC.getSelectedItem();
                String sex;
                String myhome = (String) homeC.getSelectedItem();
                String address = addressF.getText();
                String phone = phoneF.getText();
                String stud = (String) studC.getSelectedItem();
                String prof = profF.getText();
                String mylastWork = lastworkF.getText();
                String mylastPos = lastposF.getText();
                String dismiss = dismissF.getText();
                if (man.isSelected()) {
                    sex = man.getText();
                } else {
                    sex = woman.getText();
                }

                String message;
                message = "Выполнено!";

                try {  //Validations and uniqueness
                    Unemployed unempl = new Unemployed(unemp.getId(), name, sex, sp, myhome, address, phone, stud, prof, mylastWork, mylastPos, dismiss);


                    UnemployedRepo.update(unempl);
                    JOptionPane.showMessageDialog(panel, message);
                    nameF.setText("");
                    addressF.setText("");
                    phoneF.setText("");
                    profF.setText("");
                    lastworkF.setText("");
                    lastposF.setText("");
                    dismissF.setText("");
                    frame.dispose();
                    AllUnempFrame.getFrame().dispose();
                    new AllUnempFrame();
                    UnemplProfFrame.getFrame().dispose();
                    unempl.setAge(unemp.getAge());
                    new UnemplProfFrame(unempl);
                } catch (UnemployedException ex) {
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
