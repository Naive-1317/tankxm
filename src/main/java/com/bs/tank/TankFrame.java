package com.bs.tank;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame extends Frame {

    Tank myTank = new Tank(200,400,Dir.DOWN,Group.GOOD,this);
    List<Bullet> bullets =  new ArrayList<Bullet>();
    List<Tank> tanks = new ArrayList<Tank>();
    List<Explode> explodes = new ArrayList<Explode>();
    Explode e = new Explode(100,100,this);
    static final int GAME_WIDTH = 800,GAME_HEIGHT = 600;
    //TankFrame构造器，建场地，监听时间
    public TankFrame(){
        setSize(GAME_WIDTH, GAME_HEIGHT);
        //不可调整大小
        setResizable(false);
        setTitle("tank war");
        //设置可见
        setVisible(true);

        this.addKeyListener(new MyKeyListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System .exit(0);
            }
        });
    }

    /*利用双缓冲解决闪烁问题（手翻书）
    * 解决方案：内存的内容复制到显存*/
    // 首先定义一张图片，这张图片定义在内存之中的
    Image offScreenImage = null;
    @Override
    public void update(Graphics g) {
        if(offScreenImage == null) {
            //创造一张图片
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        //得到图片的画笔
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        //用笔把图画一遍
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        //调用下方重写的方法，把内存中的画笔用去画坦克和炮弹了，就画到内存里了(一次性画到内存里)
        paint(gOffScreen);
        //用屏幕上的画笔，把内存里的图片画到了屏幕上，闪烁现象都删除了
        g.drawImage(offScreenImage, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("子弹的数量："+ bullets.size(),10,60);
        g.drawString("敌人的数量" + tanks.size(),10,80);
        g.drawString("爆炸的数量" + explodes.size(),10,100);
        g.setColor(c);

        myTank.paint(g);
        for (int i = 0; i < bullets.size(); i++)
        {
            bullets.get(i).paint(g);
        }

        for (int i = 0;i <tanks.size();i++)
        {
            if (!tanks.get(i).paint(g))
            {
                tanks.remove(i);
            }
        }

        for (int i = 0;i <explodes.size();i++)
        {
            explodes.get(i).paint(g);
        }

        //碰撞检测
        for(int i = 0; i<bullets.size();i++)
        {
            for (int j = 0; j<tanks.size();j++)
            {
                if(!bullets.get(i).collideWith(tanks.get(j)))
                {
                this.tanks.remove(j);
                }
            }
        }
    }

    class MyKeyListener extends KeyAdapter
    {
        boolean bL = false;
        boolean bU = false;
        boolean bR = false;
        boolean bD = false;
        @Override
        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();
            switch (key)
            {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            int key1 = e.getKeyCode();
            switch (key1)
            {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case  KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                default:
                    break;
            }
            setMainTankDir();
        }
        //坦克静止，移动
        private void setMainTankDir()
        {
            if(!bL&&!bU&&!bR&&!bD) myTank.setMoving(false);
            else {
                myTank.setMoving(true);
                if (bL) myTank.setDir(Dir.LEFT);
                if (bU) myTank.setDir(Dir.UP);
                if (bR) myTank.setDir(Dir.RIGHT);
                if (bD) myTank.setDir(Dir.DOWN);
            }
        }
    }




}
