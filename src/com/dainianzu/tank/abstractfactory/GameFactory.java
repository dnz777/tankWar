package com.dainianzu.tank.abstractfactory;

import com.dainianzu.tank.*;

public abstract class GameFactory {
    public abstract BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tankFrame);
    public abstract BaseExplode createExplode(int x, int y, TankFrame tankFrame);
    public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tankFrame);
}
