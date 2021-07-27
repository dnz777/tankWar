package com.dainianzu.tank;

public class FourDirFireStrategy implements FireStrategy{
    
    @Override
    public void fire(Tank tank) {
        int bX = tank.getX() + (Tank.WIDTH / 2) - (Bullet.WIDTH / 2);
        int bY = tank.getY() + (Tank.HEIGHT / 2) - (Bullet.HEIGHT / 2);
        
        Dir[] dirs = Dir.values();
        for (Dir dir : dirs) {
            tank.tankFram.gameFactory.createBullet(bX,bY,dir,tank.group,tank.tankFram);
        }
        if (tank.group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
