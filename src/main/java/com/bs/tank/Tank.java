package com.bs.tank;



import java.awt.*;
import java.util.Random;

public class Tank {
    private int x,y;
    public static int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static int HEIGHT = ResourceMgr.goodTankU.getHeight();
    private Dir dir = Dir.DOWN;
    private static final int SPEED = 2;
    private boolean moving = true;
    private TankFrame tf = null;
    private Random random = new Random();
    private Group group = Group.BAD;
    private boolean living = true;
    Rectangle rect = new Rectangle();

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public Tank(int x, int y, Dir dir,Group group,TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;

        rect.x = this.x;
        rect.y = this.y;
        rect.height = HEIGHT;
        rect.width = WIDTH;
    }

    public boolean paint(Graphics g)
    {
//        if (!living) return;
        switch (dir)
        {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL,x,y,null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD,x,y,null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR,x,y,null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU,x,y,null);
                break;
        }
        return move();
    }

    public void fire()
    {
        int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
        int by = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
        tf.bullets.add(new Bullet(bX,by,this.dir,this.group,this.tf));
        //if(this.group == Group.GOOD) new Thread(()-> new Audio("audio/tank_fire.wav").play()).start();
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    private boolean move()
    {
//        if (!moving) return ;
        switch (dir)
        {
            case DOWN:
                y+=SPEED;
                break;
            case RIGHT:
                x+=SPEED;
                break;
            case UP:
                y-=SPEED;
                break;
            case LEFT:
                x-=SPEED;
                break;
            default:
                break;
        }

        rect.x = this.x;
        rect.y = this.y;
        if (x < 0 || y <0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT)
        {
            living = false;
            return false;
        }
        if (this.group == Group.BAD && random.nextInt(100) > 95)
        {
            fire();
            randomDir();
        }
        boundsCheck();
        return true;

    }

    private void boundsCheck() {
        if (this.x < 2) this.x = 2;
        if (this.y < 28) this.y = 28;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH  - 2) x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT -2) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;
    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void die()
    {
        this.living = false;
    }
}
