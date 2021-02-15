package ru.foxsoft.pssstjava;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Hashtable;

class SettingsWin extends JFrame {
    //Components to extract settings from
    private JSlider fliesS;
    private JSlider wormsS;
    private JComboBox<String> charS;
    private JTextField playerName;

    //Configuration Settings
    int fliesN;
    int wormsN;
    String character;
    String player;

    SettingsWin() {
        setResizable(false);
        setLocationByPlatform(true);
        setLocationRelativeTo(null);
        JPanel pane = new JPanel(new GridLayout(15,1,5,5));
        pane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        setSize(250, 650);
        add(pane);
        setTitle("Настройки");
        fliesS = new JSlider(JSlider.HORIZONTAL, 1,20,5);
        wormsS = new JSlider(JSlider.HORIZONTAL, 1,20,5);
        JLabel label1 = new JLabel("Количество гусениц");
        pane.add(label1);
        wormsS.setMinorTickSpacing(1);
        wormsS.setLabelTable(new Hashtable<>(new HashMap<Integer, JLabel>() {{
            put(1, new JLabel("1"));
            put(5, new JLabel("5"));
            put(10, new JLabel("10"));
            put(15, new JLabel("15"));
            put(20, new JLabel("20"));
        }}));
        wormsS.setPaintTicks(true);
        wormsS.setPaintLabels(true);
        pane.add(wormsS);
        fliesS.setMinorTickSpacing(1);
        fliesS.setLabelTable(new Hashtable<>(new HashMap<Integer, JLabel>() {{
            put(1, new JLabel("1"));
            put(5, new JLabel("5"));
            put(10, new JLabel("10"));
            put(15, new JLabel("15"));
            put(20, new JLabel("20"));
        }}));
        fliesS.setPaintTicks(true);
        fliesS.setPaintLabels(true);
        String[] characters = {"Совёнок", "Воронёнок"};
        charS = new JComboBox<>(characters);
        charS.setSelectedIndex(0);
        JLabel label2 = new JLabel("Количество мушек");
        pane.add(label2);
        pane.add(fliesS);
        JLabel label3 = new JLabel("Авторы игры:");
        JLabel label4 = new JLabel("Программист: Izzy Moreno");
        JLabel label5 = new JLabel("Художник: Dart Hydra");
        JLabel label6 = new JLabel("Введите имя игрока");
        JLabel label8 = new JLabel("Портировано на Java by Rb2o2");
        pane.add(label6);
        playerName = new JTextField("Игрок 1", 20);
        pane.add(playerName);
        JLabel label7 = new JLabel("Выберите персонажа игры");
        pane.add(label7);
        pane.add(charS);
        JButton startBut = new JButton("Начать игру");
        JButton exitBut = new JButton("Выйти из игры");
        JButton hofBut = new JButton("Таблица рекордов");
        hofBut.addActionListener(hof());
        exitBut.addActionListener(exit());
        startBut.addActionListener(start());
        pane.add(startBut);
        pane.add(hofBut);
        pane.add(exitBut);
        pane.add(label3);
        pane.add(label4);
        pane.add(label5);
        pane.add(label8);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private ActionListener hof() {
        return e -> new TableOfRecords();
    }

    private ActionListener exit() {
        JFrame self = this;
        return e -> {
            self.dispose();
            System.exit(0);
        };
    }

    private ActionListener start() {
        SettingsWin self = this;
        return e -> {
            getSettings();
            self.setVisible(false);
            new GameFrame(self);
        };
    }

    private void getSettings() {
        fliesN = fliesS.getValue();
        wormsN = wormsS.getValue();
        player = playerName.getText();
        character = (String)charS.getSelectedItem();
        System.out.println(String.format("INFO: flies=%d, worms=%d, character=%s, player=%s", fliesN, wormsN, character, player));
    }
}
