package com.dainianzu.tank;

public class DefaultFireStrategy implements FireStrategy{
    @Override
    public void fire(Tank tank) {
        int bX = tank.getX() + (Tank.WIDTH / 2) - (Bullet.WIDTH / 2);
        int bY = tank.getY() + (Tank.HEIGHT / 2) - (Bullet.HEIGHT / 2);
        tank.tankFram.gameFactory.createBullet(bX,bY,tank.dir,tank.group,tank.tankFram);
        if (tank.group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
