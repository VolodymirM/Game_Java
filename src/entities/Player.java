package entities;

import java.awt.image.BufferedImage;

import main.Game;

import java.awt.Graphics;

import utilz.LoadSave;
import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;

public class Player extends Entity{

    private BufferedImage[][] animations;
    private int aniTick, aniIndex, aniSpeed = 20;
    private int playerAction = IDLE;
    private boolean moving = false;
    private boolean left, right, up, down, jump;
    private float playerSpeed = 1.5f;
    private int[][] levelData;
    private float xDrawOffset = 11 * Game.SCALE, yDrawOffset = 22 * Game.SCALE;
    
    // jumping / gravity
    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterColision = 0.5f * Game.SCALE;
    private boolean inAir = false;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitbox(x, y, 29 * Game.SCALE, 22 * Game.SCALE);

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
                g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
            else if (aniIndex < 11)
                g.drawImage(animations[playerAction][xBackwards[aniIndex - 6]], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
            else
                g.drawImage(animations[playerAction + 1][aniIndex - 11], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
            
        }
        
        if (playerAction == RUNNING)
            if (aniIndex + 2 < animations[playerAction + 1].length)
                g.drawImage(animations[playerAction + 1][aniIndex + 2], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        
        if (inAir) {
            if (airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        }

        if (playerAction == JUMP) {
            g.drawImage(animations[playerAction][aniIndex + 2], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        }

        if (playerAction == FALLING) {
            g.drawImage(animations[playerAction - 1][5], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        }

        // if (playerAction == GROUND) {
        //     g.drawImage(animations[playerAction - 2][aniIndex + 6], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
        // }

        drawHitbox(g);
    }
    
    private void loadAnimations() {
        BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);

        animations = new BufferedImage[3][8];
        for (int j = 0; j < animations.length; ++j)
            for (int i = 0; i < animations[j].length; ++i)
                animations[j][i] = img.getSubimage(i * 50, j * 50, 50, 50);
        
    }

    public void loadLevelData(int[][] levelData) {
        this.levelData = levelData;
        if (!IsEntityOnFloor(hitbox, levelData))
            inAir = true;
    }
    
    private void setAnimation() {
        int startAni = playerAction;
        
        if (moving)
            playerAction = RUNNING;
        else
            playerAction = IDLE;

        if (inAir) {
            if (airSpeed < 0)
                playerAction = JUMP;
            else
                playerAction = FALLING;
        }

        if (startAni != playerAction)
           resetAniTick();
    }

    private void resetAniTick() {
        aniTick = 0;
        aniIndex = 0;
    }
    
    private void updatePosition() {

        moving = false;

        if (jump)
            jump();

        if (!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if (left)
            xSpeed -= playerSpeed;
        if (right)
            xSpeed += playerSpeed;

        if (!inAir)
            if (!IsEntityOnFloor(hitbox, levelData))
                inAir = true;

        if (inAir) {
            if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, levelData)) {
                hitbox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            }
            else {
                hitbox.y = GetEntityYPosUnderRoodOrAboveFloor(hitbox, airSpeed);
                if (airSpeed > 0)
                    resetInAir();
                else
                    airSpeed = fallSpeedAfterColision;
                updateXPos(xSpeed);
            }
        }
        else 
            updateXPos(xSpeed);

        moving = true;
    
    }

    private void jump() {
        if (inAir)
            return;
        inAir = true;
        airSpeed = jumpSpeed;
    }

    private void resetInAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {
        if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, levelData)) {
            hitbox.x += xSpeed;
        }
        else {
            hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
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

    public boolean isJump() {
        return jump;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

}
