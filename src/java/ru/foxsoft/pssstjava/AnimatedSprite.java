package ru.foxsoft.pssstjava;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

abstract class AnimatedSprite {
    private List<BufferedImage> framesLeft;
    private List<BufferedImage> framesRight;
    private int facing = 1;
    private int frameCount = 0;
    private int leftFramesTotal;
    private int rightFramesTotal;
    private int cnt = 0;

    private Random r = new Random();

    AnimatedSprite(String[] sl, String[] sr) {
        framesLeft = new ArrayList<>();
        for (String s : sl) {
            try {
                framesLeft.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream(s)));
            } catch (IOException x) { x.printStackTrace(); }
        }
        leftFramesTotal = framesLeft.size();
        framesRight = new ArrayList<>();
        for (String s : sr) {
            try {
                framesRight.add(ImageIO.read(getClass().getClassLoader().getResourceAsStream(s)));
            } catch (IOException x) { x.printStackTrace(); }
        }
        rightFramesTotal = framesRight.size();
    }
    void left() {
        if (facing > 0) resetFrame();
        facing = -1;
    }
    void right() {
        if (facing < 0) resetFrame();
        facing = 1;
    }
    void randomFacing() {
        facing = r.nextInt(2) * 2 - 2;
        resetFrame();
    }

    void resetFrame() {
        frameCount = 0;
    }
    void tick() {
        cnt = (cnt + 1) % Constants.frameSkip;
        if (cnt == 0) {
            if (facing < 0) {
                frameCount = (frameCount + 1) % leftFramesTotal;
            }
            if (facing > 0) {
                frameCount = (frameCount + 1) % rightFramesTotal;
            }
        }
    }

    BufferedImage getFrame() {
        if (facing < 0) return framesLeft.get(frameCount);
        else if (facing > 0) return framesRight.get(frameCount);
        else return new BufferedImage(0,0,0);
    }

}

class OwlSprite extends AnimatedSprite {
    OwlSprite() {
        super(new String[] {"Owl1.bmp", "Owl2.bmp"}, new String[] {"Owl3.bmp", "Owl4.bmp"});
        right();
    }
}
class OwlSprite_dying extends AnimatedSprite {
    OwlSprite_dying() {
        super(new String[] {"LOwlDie0.bmp", "LOwlDie1.bmp", "LOwlDie2.bmp", "LOwlDie3.bmp"},
                new String[] {"ROwlDie0.bmp", "ROwlDie1.bmp", "ROwlDie2.bmp", "ROwlDie3.bmp"});
    }
}
class CrowSprite extends AnimatedSprite {
    CrowSprite() {
        super(new String[] {"LCrow0.bmp", "LCrow1.bmp"},
                new String[] {"RCrow0.bmp", "RCrow1.bmp"});
    }
}
class CrowSprite_dying extends AnimatedSprite {
    CrowSprite_dying() {
        super(new String[] {"LCrowDie0.bmp", "LCrowDie1.bmp", "LCrowDie2.bmp", "LCrowDie3.bmp"},
                new String[] {"RCrowDie0.bmp", "RCrowDie1.bmp", "RCrowDie2.bmp", "RCrowDie3.bmp"});
    }
}
class FlySprite extends AnimatedSprite {
    FlySprite() {
        super(new String[] {"LFly0.bmp", "LFly1.bmp"},
                new String[] {"RFly0.bmp", "RFly1.bmp"});
    }
}
class FlySprite_dying extends AnimatedSprite {
    FlySprite_dying() {
        super(new String[] {"HitFly1.bmp", "HitFly2.bmp", "HitFly3.bmp", "HitFly4.bmp", "HitFly5.bmp"},
                new String[] {"HitFly6.bmp", "HitFly7.bmp", "HitFly8.bmp", "HitFly9.bmp", "HitFly10.bmp"});
    }
}
class WormSprite extends AnimatedSprite {
    WormSprite() {
        super(new String[] {"LWorm0.bmp", "LWorm1.bmp"},
                new String[] {"RWorm0.bmp", "RWorm1.bmp"});
    }
}

class WormSprite_dying extends AnimatedSprite {
    WormSprite_dying() {
        super(new String[] {"HitWorm1.bmp", "HitWorm2.bmp", "HitWorm3.bmp", "HitWorm4.bmp", "HitWorm5.bmp"},
                new String[] {"HitWorm6.bmp", "HitWorm7.bmp", "HitWorm8.bmp", "HitWorm9.bmp", "HitWorm10.bmp"});
    }
}
