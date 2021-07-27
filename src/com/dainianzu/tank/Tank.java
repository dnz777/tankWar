package com.dainianzu.tank;

import com.dainianzu.tank.abstractfactory.BaseTank;

import java.awt.*;
import java.util.Random;

public class Tank extends BaseTank {
    //坦克位置（坐标）
    private int x, y;
    //坦克方向
    Dir dir = Dir.DOWN;
    //坦克移动速度
    private static final int SPEED = 2;
    //坦克移动状态
    private boolean moving = true;
    //坦克是否存活
    private boolean living = true;
    //阵营(放到父类中)
    //Group group = Group.BAD;

    //加强炮弹
    boolean addFire = false;

    FireStrategy fireStrategy;

    //随机数
    private Random random = new Random();

    //矩形(放到父类中)
    //public Rectangle rectangle = new Rectangle();

    public static int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static int HEIGHT = ResourceMgr.goodTankU.getHeight();

    TankFrame tankFram = null;

    public Tank(int x, int y, Dir dir, boolean moving, Group group, TankFrame tankFrame) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tankFram = tankFrame;
        this.moving = moving;

        rectangle.x = this.x;
        rectangle.y = this.y;
        rectangle.width = WIDTH;
        rectangle.height = HEIGHT;

        if (group == Group.GOOD && this.addFire) {
            String goodFSName = (String) PropertyMgr.get("goodFS");
            try {
                fireStrategy = (FireStrategy) Class.forName(goodFSName).getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            fireStrategy = new DefaultFireStrategy();
        }
    }

    //画出坦克
    public void paint(Graphics graphics) {
        if (living != true) {
            tankFram.tanks.remove(this);
        }

        switch (dir) {
            case LEFT:
                graphics.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case UP:
                graphics.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case RIGHT:
                graphics.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case DOWN:
                graphics.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
        }
//        Color color = graphics.getColor();
//        graphics.setColor(Color.YELLOW);
//        graphics.fillRect(x,y,50,50);
//        graphics.setColor(color);
        move();
    }

    private void move() {
        if (!moving) return;
        switch (dir) {
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
        rectangle.x = this.x;
        rectangle.y = this.y;

        if (this.group == Group.BAD && random.nextInt(100) > 95) this.fire();
        if (this.group == Group.BAD && random.nextInt(100) > 95) randomDir();

        boundCheck();

    }

    private void boundCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 28) y = 28;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;
    }

    public void fire() {
        //fireStrategy.fire(this);
        int bX = this.getX() + (Tank.WIDTH / 2) - (Bullet.WIDTH / 2);
        int bY = this.getY() + (Tank.HEIGHT / 2) - (Bullet.HEIGHT / 2);
        tankFram.gameFactory.createBullet(bX,bY,dir,group,tankFram);
        if (group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }

    public void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void die() {
        this.living = false;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public void setAddFire(boolean addFire) {
        this.addFire = addFire;
    }
}
