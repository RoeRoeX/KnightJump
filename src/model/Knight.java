package model;

import control.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Knight extends GameFigure {

    private int width = 157;
    private int height = 108;
    private Image ArmorStand;
    private Image ArmorRun1;
    private Image ArmorRun2;
    private Image ArmorJump;
    private Image ArmorAttack1;
    private Image ArmorAttack2;
    private Image NakeyStand;
    private Image NakeyRun1;
    private Image NakeyRun2;
    private Image NakeyJump;
    private Image NakeyAttack1;
    private Image NakeyAttack2;
    private Image hit;
    private int frames = 0;
    private int runCycle = 0;
    private int attackFrames = 0;
    private int attackCycle = 0;
    private int damageFrames= 0;

    public static final int Right = 0;
    public static final int Left = 1;
    private final int UNIT_MOVE = 375;
    private final int UNIT_JUMP = 250;

    public Knight (){
        super(360,477);
    }

    @Override
    public void render(Graphics2D g2) {

        // fixes graphical jitter
        int x = (int)Math.floor(location.x);
        int y = (int)Math.floor(location.y);

        if (moving && !jumping && !falling && !attacking) {
            switch (super.facing) {
                case Right:
                    if (runCycle == 0) {
                        if(hitCount == 0){
                        g2.drawImage(ArmorRun1, x, y, width, height, null); }
                        else {g2.drawImage(NakeyRun1, x, y, width, height, null);}

                    } else if (runCycle == 1) {
                        if(hitCount == 0){
                        g2.drawImage(ArmorRun2, x, y, width, height, null);}
                        else {g2.drawImage(NakeyRun2, x, y, width, height, null);}

                    }
                    break;
                case Left:
                    if (runCycle == 0) {
                        if(hitCount == 0){
                        g2.drawImage(ArmorRun1, x + 150, y, -width, height, null);}
                        else {g2.drawImage(NakeyRun1, x + 150, y, -width, height, null);}

                    } else if (runCycle == 1) {
                        if(hitCount == 0){
                        g2.drawImage(ArmorRun2, x + 150, y, -width, height, null);}
                        else {g2.drawImage(NakeyRun2, x + 150, y, -width, height, null);}

                    }
                    break;
            }
        }
        else if (jumping || falling) {
            switch (super.facing) {
                case Right:
                    if (hitCount == 0){
                    g2.drawImage(ArmorJump, x, y, width, height, null); }
                    else {g2.drawImage(NakeyJump, x, y, width, height, null);}
                    break;
                case Left:
                    if (hitCount == 0) {
                    g2.drawImage(ArmorJump, x + 150, y, -width, height, null); }
                    else {g2.drawImage(NakeyJump, x + 150, y, -width, height, null);}
                    break;
            }
        }
        else if (attacking) {
            switch (super.facing) {
                case Right:
                    if(attackCycle == 0) {
                        if(hitCount == 0){
                        g2.drawImage(ArmorAttack1, x, y, width, height, null); }
                        else {g2.drawImage(NakeyAttack1, x, y, width, height, null); }
                    }
                    else if (attackCycle == 1) {
                        if(hitCount == 0){
                        g2.drawImage(ArmorAttack2, x, y, width, height, null);}
                        else {g2.drawImage(NakeyAttack2, x, y, width, height, null);}
                    }
                    break;
                case Left:
                    if(attackCycle == 0) {
                        if (hitCount == 0){
                        g2.drawImage(ArmorAttack1, x + 150, y, -width, height, null);}
                        else {g2.drawImage(NakeyAttack1, x + 150, y, -width, height, null);}
                    }
                    else if (attackCycle == 1) {
                        if (hitCount == 0){
                        g2.drawImage(ArmorAttack2, x + 150, y, -width, height, null);}
                        else {g2.drawImage(NakeyAttack2, x + 150, y, -width, height, null);}
                    }
                    break;
            }
        }
        if (damage) {
            g2.drawImage(hit, x, y, width, height, null);
        }
        else if (!moving && !jumping && !attacking && !falling) {
            switch (super.facing) {
                case Right:
                    if(hitCount== 0){
                    g2.drawImage(ArmorStand, x, y, width, height, null);}
                    else {g2.drawImage(NakeyStand, x, y, width, height, null);}
                    break;
                case Left:
                    if (hitCount == 0){
                    g2.drawImage(ArmorStand, x + 150, y, -width, height, null);}
                    else {g2.drawImage(NakeyStand, x + 150, y, -width, height, null);}
                    break;
            }
        }
    }

    @Override
    public void update(float dt) {

        if (moving) {
            switch (facing) {
                case Right:
                    if (super.location.x < 850) {
                            super.location.x += UNIT_MOVE * dt;
                        break;
                    }
                case Left:
                    if (super.location.x >= 0) {
                            super.location.x -= UNIT_MOVE * dt;

                        break;
                    }
            }
        }
        if (moving && !jumping && !falling) {
            if (frames == 5) {
                if (runCycle == 0) runCycle = 1;
                else runCycle = 0;
                frames = 0;
            } else frames++;
        }
        if (jumping) {
            if (super.location.y >= 377 && !falling) {
                super.location.y -= UNIT_JUMP * dt;
            }
            else {
                falling = true;
                jumping = false;
            }
        }
        if (falling) {
            if (super.location.y <= 477) {
                super.location.y += UNIT_JUMP * dt;
            } else {
                falling = false;
            }
        }
        if (attacking) {
            attackFrames++;
            if (attackFrames == 5) {
                attackCycle = 1;
            }
            else if (attackFrames == 10 ) {
                attacking = false;
                attackCycle = 0;
                attackFrames = 0;
            }
        }
        else{
            switch (facing) {
                case Left:
                    if (super.location.x > 150) super.location.x -= WORLD_PACE * dt;
                    break;
                case Right:
                    if (super.location.x > 0) super.location.x -= WORLD_PACE * dt;
                    break;
            }
        }
        if (damage) {
            location.x -= 30 * dt;
            damageFrames++;
            if (damageFrames == 5) {
                damageFrames = 0;
                damage = false;
            }
        }
        if (hitCount > 1) {
            Main.gameOver = true;
            Main.running = false;
        }
    }

    @Override
    public int getCollisionRadius() {
        return 108/2;
    }

    @Override
    public void load() {
        try {
            File imageFile = new File("images/ArmorStand.png");
            ArmorStand = ImageIO.read(imageFile);
            imageFile = new File("images/ArmorRun1.png");
            ArmorRun1 = ImageIO.read(imageFile);
            imageFile = new File("images/ArmorRun2.png");
            ArmorRun2 = ImageIO.read(imageFile);
            imageFile = new File("images/ArmorJump.png");
            ArmorJump = ImageIO.read(imageFile);
            imageFile = new File("images/ArmorAttack1.png");
            ArmorAttack1 = ImageIO.read(imageFile);
            imageFile = new File("images/ArmorAttack2.png");
            ArmorAttack2 = ImageIO.read(imageFile);
            imageFile = new File("images/damage.png");
            hit = ImageIO.read(imageFile);
            imageFile = new File("images/NakeyStand.png");
            NakeyStand = ImageIO.read(imageFile);
            imageFile = new File("images/NakeyRun1.png");
            NakeyRun1 = ImageIO.read(imageFile);
            imageFile = new File("images/NakeyRun2.png");
            NakeyRun2 = ImageIO.read(imageFile);
            imageFile = new File("images/NakeyJump.png");
            NakeyJump = ImageIO.read(imageFile);
            imageFile = new File("images/NakeyAttack1.png");
            NakeyAttack1 = ImageIO.read(imageFile);
            imageFile = new File("images/NakeyAttack2.png");
            NakeyAttack2 = ImageIO.read(imageFile);
        } catch (IOException e){
            System.out.println("Image error");
        }
    }
}
