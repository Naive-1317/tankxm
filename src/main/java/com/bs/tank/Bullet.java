package com.bs.tank;


import java.awt.*;

public class Bullet {
    //速度
    private static final int SPEED = 6;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();
    Rectangle rect = new Rectangle();
    private int x,y;
    private Dir dir;
    private boolean living = true;
    private Group group = Group.BAD;
    TankFrame tf = null;


    public Bullet(int x, int y, Dir dir,Group group,TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tf = tf;
        this.group = group;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void paint(Graphics g)
    {
        if (!living)
        {
            tf.bullets.remove(this);
        }
        switch (dir)
        {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL,x,y,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD,x,y,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR,x,y,null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU,x,y,null);
                break;
        }
        move();
    }
    //更新坐标数据
    private void move()
    {
        switch (dir)
        {
            case LEFT:
                x-=SPEED;
                break;
            case UP:
                y-=SPEED;
                break;
            case RIGHT:
                x+=SPEED;
                break;
            case DOWN:
                y+=SPEED;
                break;
        }

        rect.x = this.x;
        rect.y = this.y;

        if (x < 0 || y <0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;
    }

    public boolean collideWith(Tank tank) {
        if (this.group == tank.getGroup())
        {
            return true;
        }
//        Rectangle rect1 = new Rectangle(this.x , this.y ,WIDTH , HEIGHT);
//        Rectangle rect2 = new Rectangle(tank.getX() , tank.getY() , tank.WIDTH , tank.HEIGHT);
        if (rect.intersects(tank.rect))
        {
            tank.die();
            this.die();
            int eX = tank.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
            int eY = tank.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
            tf.explodes.add(new Explode(eX, eY, tf));
            return false;
        }
        return true;
    }

    private void die() {
        this.living = false;
    }

}
