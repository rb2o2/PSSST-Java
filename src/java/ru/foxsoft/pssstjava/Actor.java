package ru.foxsoft.pssstjava;

import java.awt.Image;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

class Actor {
    Map<String, AnimatedSprite> sprites;
    protected int x;
    int y;
    double vx;
    double vy;
    private int facing;
    static final String STATUS_OK = "OK";
    static final String DYING = "DYING";
    String status = STATUS_OK;

    Actor(AnimatedSprite ok, AnimatedSprite dying, int initx, int inity,
          double initvx, double initvy, int initfacing) {
        sprites = new HashMap<>();
        sprites.put(STATUS_OK, ok);
        sprites.put(DYING, dying);
        x = initx;
        y = inity;
        vx = initvx;
        vy = initvy;
        facing = initfacing;
    }
    Actor() {}

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    String getStatus() {
        return status;
    }

    Image getImage() {
        return sprites.get(status).getFrame();
    }

    void move() {
        if (Math.abs(vx) + Math.abs(vy) > 0.01) sprites.get(status).tick();
        x=(int)((x + vx) > (Constants.gameframeW - sprites.get(status).getFrame().getWidth())?x:
                ((x+vx) < 0?x:(x + vx)));
        y=(int)((y + vy) > (Constants.gameframeH - sprites.get(status).getFrame().getHeight())?y:
                ((y + vy) < 0?y: (y+vy)));
    }

    void setVx(double vx) {
        if (vx < 0.0) {
            sprites.get(status).left();
        } else if (vx > 0.0) sprites.get(status).right();
        else if (Math.abs(vx)< 0.01) sprites.get(status).resetFrame();
        this.vx = vx;
    }
    void setVy(double vy) {
        this.vy = vy;
    }
}

class FlyActor extends Actor {
    private int amp;
    private double phase;
    private double omega;
    private int y0;
    FlyActor() {
        Random r = new Random();
        amp = r.nextInt(50) + 20;
        phase = r.nextDouble() * 100;
        omega = r.nextDouble()* 0.5+ 0.05;
        sprites = new HashMap<>();
        sprites.put(STATUS_OK, new FlySprite());
        sprites.put(DYING, new FlySprite_dying());
        int xt = 0, yt = 0;
        AnimatedSprite sOK = sprites.get(STATUS_OK);
        while (((xt>Constants.gameframeW / 2 - 30) && (xt<Constants.gameframeW/2 + 30) &&
                (yt > Constants.gameframeH / 2 - 30) && (yt < Constants.gameframeH / 2 + 30) ||
                ((yt + amp > Constants.gameframeH - sOK.getFrame().getHeight()) || (yt - amp < 0)))) {
            xt = r.nextInt(Constants.gameframeW - sOK.getFrame().getWidth());
            yt = r.nextInt(Constants.gameframeH - sOK.getFrame().getHeight());
        }
        x = xt;
        y0 = y = yt;
        double vxt = 0.0;
        while (Math.abs(vxt) < 2.5)
            vxt = r.nextDouble() * 25 - 12.5;
        vx = vxt;
        if (vx < 0.0)
            sprites.get(status).left();
        else
            sprites.get(status).right();
    }

    @Override
    void move() {
        if (Math.abs(vx) + Math.abs(vy) > 0.5) sprites.get(status).tick();
        if ((x + vx) > Constants.gameframeW - sprites.get(status).getFrame().getWidth()) {
            vx = - vx;
            sprites.get(status).left();
        }
        if (x + vx < 0) {
            vx = -vx;
            sprites.get(status).right();
        }
        if (y + vy < 0) {
            vy = - vy;
            y = 0;
        } if (y + vy > Constants.gameframeH - sprites.get(status).getFrame().getHeight()) {
            vy = - vy;
            y = Constants.gameframeH - sprites.get(status).getFrame().getHeight();
        }
        phase += omega;
        vy = amp * Math.sin(phase);
        y = (int)(y0 + amp * Math.sin(phase * omega));
        x = (int)(x + vx);
    }
}

class WormActor extends Actor {
    WormActor() {
        super(new WormSprite(), new WormSprite_dying(), 0,0,0.0,0.0,-1);
        Random r = new Random();
        int xt = Constants.gameframeW/2, yt = Constants.gameframeH/2;
        AnimatedSprite sOK = sprites.get(STATUS_OK);
        while (((xt>Constants.gameframeW / 2 - 30) && (xt<Constants.gameframeW/2 + 30) &&
                (yt > Constants.gameframeH / 2 - 30) && (yt < Constants.gameframeH / 2 + 30) ||
                ((yt > Constants.gameframeH) || (yt < 0)))) {
            xt = r.nextInt(Constants.gameframeW - sOK.getFrame().getWidth());
            yt = r.nextInt(Constants.gameframeH - sOK.getFrame().getHeight());
        }
        x = xt;
        y = yt;

        vx = 0.0;
        while (Math.abs(vx) < 1.5) {
            vx = r.nextDouble() * 10 - 5.0;
        }
        if (vx < 0.0) sprites.get(status).left();
        if (vx > 0.0) sprites.get(status).right();
        vy = 0.0;
    }

    @Override
    void move() {
        if (Math.abs(vx) + Math.abs(vy) > 0.01) sprites.get(status).tick();
        if ((x + vx) > Constants.gameframeW - sprites.get(status).getFrame().getWidth()) {
            vx = -vx;
            sprites.get(status).left();
        }
        if (x + vx < 0) {
            vx = -vx;
            sprites.get(status).right();
        }

        y = (int)(y + vy);
        x = (int)(x + vx);
    }
}