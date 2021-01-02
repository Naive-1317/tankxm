package com.bs.tank;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();
        int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));
        //初始化敌方坦克
        for (int i = 0 ; i < 5 ; i++)
        {
            tankFrame.tanks.add(new Tank(50 +i*80,200,Dir.DOWN,Group.BAD,tankFrame));
        }

        new Thread(()->new Audio("audio/war1.wav").loop()).start();

        while (true)
        {
            Thread.sleep(25);
            //调用update,paint方法，
            tankFrame.repaint();
        }


    }
}
