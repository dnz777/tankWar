package com.dainianzu.tank.abstractfactory;

import com.dainianzu.tank.Audio;
import com.dainianzu.tank.ResourceMgr;
import com.dainianzu.tank.TankFrame;

import java.awt.*;

public class RectExplode extends BaseExplode{
    public static int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static int HEIGHT = ResourceMgr.explodes[0].getHeight();

    private int x,y;
    //private boolean living = true;
    TankFrame tankFrame = null;

    private int step = 0;

    public RectExplode(int x, int y, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.tankFrame = tankFrame;

        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }
    @Override
    public void paint(Graphics graphics){
        //graphics.drawImage(ResourceMgr.explodes[step++],x,y,null);
        Color color = graphics.getColor();
        graphics.setColor(Color.red);
        graphics.fillRect(x,y,20*step,20*step);
        step++;

        if(step >= 5){
            tankFrame.explodes.remove(this);
        }
        graphics.setColor(color);
    }
}
