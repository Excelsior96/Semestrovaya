package GUI.Unemployed;

import Repository.UnemployedRepo;
import Utilities.GUIService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Daniel Shchepetov on 11.12.2015.
 */
public class ProfDelDialog {

    public ProfDelDialog(final int id) {
        final JDialog dial = new JDialog();
        dial.setBounds(200, 200, 350, 200);
        dial.setLayout(new GridBagLayout());

        JLabel jLabel = new JLabel("Вы действительно хотите удалить этот профиль?");
        JLabel lab = new JLabel("Все его заявки будут удалены");
        JLabel l = new JLabel("Отменить изменения будет невозможно");
        JButton button = new JButton("Да");
        JButton but = new JButton("Нет");

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UnemployedRepo.deleteById(id);
                if (UnemplProfFrame.getFrame()!=null){
                UnemplProfFrame.getFrame().dispose();}
                if (UnemplProfArcFrame.getFrame()!=null){
                    UnemplProfArcFrame.getFrame().dispose();}

                dial.dispose();

                JOptionPane.showMessageDialog(dial, "Профиль удален безвозратно...");
               if (AllUnempFrame.getFrame() !=null ) {
                   AllUnempFrame.getFrame().dispose();
                   new AllUnempFrame();
               }
                if (Archive.getFrame() !=null ) {
                    Archive.getFrame().dispose();
                    new Archive();
                }
            }
        };
        button.addActionListener(listener);

        ActionListener lis = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dial.dispose();
            }
        };

        but.addActionListener(lis);
        dial.add(jLabel, GUIService.setTopLabelConstraints());
        dial.add(lab, GUIService.setTopLabelConstraints());
        dial.add(l, GUIService.setTopLabelConstraints());
        dial.add(button, GUIService.setLabelConstraints());
        dial.add(but, GUIService.setLabelConstraints());
        dial.setVisible(true);
    }
}
