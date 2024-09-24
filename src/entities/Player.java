package entities;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.Graphics;

import static utilz.Constants.PlayerConstants.*;
import static utilz.Constants.Directions.*;

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 20;
    private int playerAction = IDLE;
    private int playerDir = -1;
    private boolean moving = false;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update() {
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }

    public void render(Graphics g) {
        int[] xBackwards = {4, 3, 2, 1, 0};

        if (playerAction == IDLE) {
            if (aniIndex < 6)
                g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, 160, 160, null);
            else if (aniIndex < 11)
                g.drawImage(animations[playerAction][xBackwards[aniIndex - 6]], (int) x, (int) y, 160, 160, null);
            else
                g.drawImage(animations[playerAction + 1][aniIndex - 11], (int) x, (int) y, 160, 160, null);
            
        }
        
        if (playerAction == RUNNING)
            if (aniIndex + 2 < animations[playerAction + 1].length)
                g.drawImage(animations[playerAction + 1][aniIndex + 2], (int) x, (int) y, 160, 160, null);
    }

    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/res/player_sprites_frog.png");
        
        try {
            BufferedImage img = ImageIO.read(is);

            animations = new BufferedImage[3][8];
            for (int j = 0; j < animations.length; ++j) {
                for (int i = 0; i < animations[j].length; ++i)
                    animations[j][i] = img.getSubimage(i * 50, j * 50, 50, 50);
            }

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
                    x -= 5;
                    break;
                case UP:
                    y -= 5;
                    break;
                case RIGHT:
                    x += 5;
                    break;
                case DOWN:
                    y += 5;
                    break;

            }
        }
    }

}
