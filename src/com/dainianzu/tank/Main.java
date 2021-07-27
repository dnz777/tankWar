package com.dainianzu.tank;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tankFrame = new TankFrame();

        int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));

        //初始化敌人坦克
        for (int i = 0; i < initTankCount; i++) {
            tankFrame.tanks.add(tankFrame.gameFactory.createTank(50 + i*80,200,Dir.DOWN,Group.BAD,tankFrame));
        }
        //游戏背景音乐
        //new Thread(()-> new Audio("audio/war1.wav").loop()).start();

        while (true){
            Thread.sleep(25);
            tankFrame.repaint();
        }

    }
}
