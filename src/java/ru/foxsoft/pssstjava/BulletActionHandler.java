package ru.foxsoft.pssstjava;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BulletActionHandler implements ActionListener {
    private GamePanel pane;

    BulletActionHandler(GameFrame frame) {
        this.pane = frame.getPane();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //TODO implement this for bullet timing
    }
}
