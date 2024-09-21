package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.InputStream;
import java.io.IOException;
import javax.swing.JPanel;
import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class GamePanel extends JPanel {

    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 20;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;

    public GamePanel() {

        mouseInputs = new MouseInputs(this);
        importImg();
        loadAnimations();
        setPanelSize();
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void loadAnimations() {
        animations = new BufferedImage[3][8];
        //int xBackwards = 4;

        for (int j = 0; j < animations.length; ++j) {
            for (int i = 0; i < animations[j].length; ++i) {
                /*if (i < 6) {
                    animations[j][i] = img.getSubimage(i * 50, 0, 50, 50);
                    continue;
                }
                if (i < 11) {
                    animations[j][i] = img.getSubimage(xBackwards * 50, 0, 50, 50);
                    --xBackwards;
                    continue;
                }
                animations[i] = img.getSubimage((i - 11) * 50, 50, 50, 50);*/
                animations[j][i] = img.getSubimage(i * 50, j * 50, 50, 50);
            }
        }
    }

    private void importImg() {
        InputStream is = getClass().getResourceAsStream("/res/player_sprites_frog.png");
        
        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    public void setDirection(int direction) {
        this.playerDir = direction;
        moving = true;
    }
    
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    private void updateAnimationTick() {
        ++aniTick;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            ++aniIndex;
            
            if (aniIndex >= GetSpriteAmount(playerAction))
                aniIndex = 0;
        }
    }
    
    private void setAnimation() {
        
        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
    }
    
    private void updatePosition() {
        
        if (moving) {
            switch (playerDir) {
                case LEFT:
                    xDelta -= 5;
                    break;
                case UP:
                    yDelta -= 5;
                    break;
                case RIGHT:
                    xDelta += 5;
                    break;
                case DOWN:
                    yDelta += 5;
                    break;

            }
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateAnimationTick();

        setAnimation();
        updatePosition();

        g.drawImage(animations[playerAction][aniIndex], (int) xDelta, (int) yDelta, 160, 160, null);
    }

}
