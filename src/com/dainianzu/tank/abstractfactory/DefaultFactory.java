package com.dainianzu.tank.abstractfactory;

import com.dainianzu.tank.*;

public class DefaultFactory extends GameFactory{


    @Override
    public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        return new Tank(x,y,dir,true,group,tankFrame);
    }

    @Override
    public BaseExplode createExplode(int x, int y, TankFrame tankFrame) {
        return new Explode(x,y,tankFrame);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tankFrame) {
        return new Bullet(x,y,dir,group,tankFrame);
    }
}
