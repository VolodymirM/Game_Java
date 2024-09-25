package entities;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import javax.imageio.ImageIO;

import utilz.LoadSave;

import java.io.IOException;
import java.awt.Graphics;

import static utilz.Constants.PlayerConstants.*;

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 20;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean left = false, right = false, up = false, down = false;
    private float playerSpeed = 2.0f;
    int width, height;

    public Player(float x, float y, int width, int height) {
        super(x, y);
        this.width = width;
        this.height = height;
        loadAnimations();
    }

    public void update() {
        updatePosition();
        updateAnimationTick();
        setAnimation();
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
    
    public void render(Graphics g) {
        int[] xBackwards = {4, 3, 2, 1, 0};

        if (playerAction == IDLE) {
            if (aniIndex < 6)
                g.drawImage(animations[playerAction][aniIndex], (int) x, (int) y, width, height, null);
            else if (aniIndex < 11)
                g.drawImage(animations[playerAction][xBackwards[aniIndex - 6]], (int) x, (int) y, width, height, null);
            else
                g.drawImage(animations[playerAction + 1][aniIndex - 11], (int) x, (int) y, width, height, null);
            
        }
        
        if (playerAction == RUNNING)
            if (aniIndex + 2 < animations[playerAction + 1].length)
                g.drawImage(animations[playerAction + 1][aniIndex + 2], (int) x, (int) y, width, height, null);
    }
    
    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[3][8];
        for (int j = 0; j < animations.length; ++j)
            for (int i = 0; i < animations[j].length; ++i)
                animations[j][i] = img.getSubimage(i * 50, j * 50, 50, 50);
        
    }
    
    private void setAnimation() {
        
        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;
    }
    
    private void updatePosition() {

        moving = false;

        if (left && !right) {
            x -= playerSpeed;
            moving = true;
        } else if (right && !left) {
            x += playerSpeed;
            moving = true;
        }

        if (up && !down) {
            y -= playerSpeed;
            moving = true;
        } else if (down && !up) {
            y += playerSpeed;
            moving = true;
        }
    }

    public void resetDirBooleans () {
        left = false;
        right = false;
        up = false;
        down = false;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

}
