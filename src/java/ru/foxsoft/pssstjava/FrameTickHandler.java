package ru.foxsoft.pssstjava;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameTickHandler implements ActionListener {
    private GameFrame frame;

    FrameTickHandler(GameFrame frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.nextFrame();
    }
}
