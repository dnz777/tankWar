package com.dainianzu.tank.abstractfactory;

import com.dainianzu.tank.Group;

import java.awt.*;

public abstract class BaseTank {
    public Group group = Group.BAD;
    public Rectangle rectangle = new Rectangle();

    public abstract void paint(Graphics graphics);

    public Group getGroup() {
        return group;
    }

    public abstract void die();
    public abstract  int getX();
    public abstract int getY();

}
