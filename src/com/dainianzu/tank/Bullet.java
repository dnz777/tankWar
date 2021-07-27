package com.dainianzu.tank;

import com.dainianzu.tank.abstractfactory.BaseBullet;
import com.dainianzu.tank.abstractfactory.BaseTank;

import java.awt.*;

public class Bullet extends BaseBullet {
    private static final int SPEED = 6;
    public static int WIDTH = ResourceMgr.bulletD.getWidth();
    public static int HEIGHT = ResourceMgr.bulletD.getHeight();
    private int x,y;
    private Dir dir;
    private boolean living = true;
    private Group group = Group.BAD;
    private TankFrame tankFrame = null;
    private Rectangle rectangle = new Rectangle();

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Bullet(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFrame = tankFrame;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;

        tankFrame.bullets.add(this);
    }

    //画出子弹
    public void paint(Graphics graphics){
        if (!living){
            tankFrame.bullets.remove(this);
        }
        switch (dir){
            case LEFT:
                graphics.drawImage(ResourceMgr.bulletL,x,y,null);
                break;
            case UP:
                graphics.drawImage(ResourceMgr.bulletU,x,y,null);
                break;
            case RIGHT:
                graphics.drawImage(ResourceMgr.bulletR,x,y,null);
                break;
            case DOWN:
                graphics.drawImage(ResourceMgr.bulletD,x,y,null);
                break;
        }
//        Color color = graphics.getColor();
//        graphics.setColor(Color.red);
//        graphics.fillOval(x,y,WIDTH,HETGH);
//        graphics.setColor(color);

        move();
    }


    private void move(){
        switch (dir){
            case LEFT:
                x -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
        }

        //更新rectangle
        rectangle.x = this.x;
        rectangle.y = this.y;

        if(x < 0 || y< 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;
    }

    public void die(){
        this.living = false;
    }

    //子弹与坦克碰撞
    public void collideWith(BaseTank tank) {
        if (this.group == tank.getGroup()) return;
        //子弹的矩形
        //Rectangle rectangle1 = new Rectangle(this.x,this.y,WIDTH,HEIGHT);
        //坦克的矩形
        //Rectangle rectangle2 = new Rectangle(tank.getX(),tank.getY(),Tank.WIDTH,Tank.HEIGHT);

        if (rectangle.intersects(tank.rectangle)){
            tank.die();
            this.die();
            int eX = tank.getX() + (Tank.WIDTH/2) - (Explode.WIDTH/2);
            int eY = tank.getY() + (Tank.HEIGHT/2) -(Explode.HEIGHT/2);
            tankFrame.explodes.add(tankFrame.gameFactory.createExplode(eX,eY,tankFrame));
        }

    }
}
