package ru.foxsoft.pssstjava;

import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.event.WindowListener;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.util.ArrayList;

class GameFrame extends JFrame {
    Timer fpsTimer;
    Timer bulletTimer;
    SettingsWin parent;
    private GamePanel contentPane;
    private boolean win = false;
    private boolean lose = false;

    GameFrame(SettingsWin parent) {
        this.parent = parent;
        this.contentPane = new GamePanel(parent);
        setTitle("Играет: " + parent.player);
        setSize(Constants.gameframeW,Constants.gameframeH+30);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.addWindowListener(new Closer(this));
        add(contentPane);
        setResizable(false);
        setVisible(true);
        initTimers();
        start();
    }

    private void initTimers() {
        fpsTimer = new Timer(Constants.fpsDelay, new FrameTickHandler(this));
        bulletTimer = new Timer(Constants.bullet, new BulletActionHandler(this));
    }

    private void start() {
        contentPane.setBackground(Color.BLACK);
        fpsTimer.start();
    }

    boolean notEndGame() {
        return !win && !lose;
    }

    void nextFrame() {
        contentPane.actorsMove();
        repaint();
    }

    GamePanel getPane() {
        return contentPane;
    }
}

class Closer implements WindowListener {
    private GameFrame parent;

    Closer(GameFrame parent) {
        this.parent = parent;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        parent.fpsTimer.stop();
    }

    @Override
    public void windowClosed(WindowEvent e) {
         parent.parent.setVisible(true);
    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

class GamePanel extends JPanel implements KeyListener {
    private java.util.List<Actor> actors;
    private Actor player;
    private SettingsWin settingsWin;

    GamePanel(SettingsWin parent) {
        super(true);
        this.settingsWin = parent;
        switch (settingsWin.character) {
            case "Совёнок":
                player = new Actor(
                        new OwlSprite(),
                        new OwlSprite_dying(),
                        Constants.gameframeW / 2,
                        Constants.gameframeH / 2,
                        0.0,
                        0.0,
                        1);
                break;
            case "Воронёнок":
                player = new Actor(
                        new CrowSprite(),
                        new CrowSprite_dying(),
                        Constants.gameframeW / 2,
                        Constants.gameframeH / 2,
                        0.0,
                        0.0,
                        1);
                break;
        }
        actors = new ArrayList<>();
        actors.add(player);
        for (int i = 0; i<settingsWin.fliesN; i++) {
            actors.add(new FlyActor());
        }
        for (int i = 0; i<settingsWin.wormsN; i++) {
            actors.add(new WormActor());
        }
        super.setFocusable(true);
        addKeyListener(this);
        super.setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        setForeground(Color.BLACK);
        g.fillRect(0,0,Constants.gameframeW,Constants.gameframeH);
        for (Actor a: actors) {
            g.drawImage(a.getImage(),a.getX(),a.getY(),null);
        }
    }

    void actorsMove() {
        for (Actor a : actors) {
            a.move();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                player.setVx(10.0);
                break;
            case KeyEvent.VK_LEFT:
                player.setVx(-10.0);
                break;
            case KeyEvent.VK_UP:
                player.setVy(-10.0);
                break;
            case KeyEvent.VK_DOWN:
                player.setVy(10.0);
                break;

        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_RIGHT:
                player.setVx(0.0);
                break;
            case KeyEvent.VK_LEFT:
                player.setVx(0.0);
                break;
            case KeyEvent.VK_UP:
                player.setVy(0.0);
                break;
            case KeyEvent.VK_DOWN:
                player.setVy(0.0);
                break;
        }
    }
}
