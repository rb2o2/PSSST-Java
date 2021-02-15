package ru.foxsoft.pssstjava;

import javax.swing.*;

public class Application extends JFrame {

    private void init() {}

    private void showSettingsWindow() {
        new SettingsWin();
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.init();
        app.showSettingsWindow();
    }
}
