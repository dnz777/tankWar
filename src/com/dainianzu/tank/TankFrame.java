package com.dainianzu.tank;

import com.dainianzu.tank.abstractfactory.*;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankFrame  extends Frame {
//    int x = 200;
//    int y = 200;
//    Dir dir = Dir.DOWN;
//    private static final int SPEED = 10;
    Tank myTank = new Tank(GAME_WIDTH/2 - Tank.WIDTH/2,GAME_HEIGHT - Tank.HEIGHT - 20,Dir.UP,false,Group.GOOD,this);
    public List<BaseBullet> bullets = new ArrayList<>();
    public List<BaseTank> tanks = new ArrayList<>();
    public List<BaseExplode> explodes = new ArrayList<>();
    //实例化工厂
    //public GameFactory gameFactory = new RectFactory();
    //默认工厂
    public GameFactory gameFactory = new DefaultFactory();
    //Bullet bullet = new Bullet(300,300,Dir.DOWN);
    //Explode explode = new Explode(100,100,this);
    public static final int GAME_WIDTH =1080,GAME_HEIGHT = 960;


    public TankFrame(){
        setSize(GAME_WIDTH,GAME_HEIGHT);
        setResizable(false);
        setTitle("Tank War");
        setVisible(true);
        this.addKeyListener(new MyKeyListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    Image offScreenImage = null;
    @Override
    public void update(Graphics graphics){
        if (offScreenImage == null){
            offScreenImage = this.createImage(GAME_WIDTH,GAME_HEIGHT);
        }
        Graphics goffScreen = offScreenImage.getGraphics();
        Color color = goffScreen.getColor();
        goffScreen.setColor(Color.BLACK);
        goffScreen.fillRect(0,0,GAME_WIDTH,GAME_HEIGHT);
        goffScreen.setColor(color);
        paint(goffScreen);
        graphics.drawImage(offScreenImage,0,0,null);
    }
    public void paint(Graphics graphics){
        Color color = graphics.getColor();
        graphics.setColor(Color.white);
        graphics.drawString("子弹数量：" + bullets.size(),10,60);
        graphics.drawString("敌人数量：" + tanks.size(),10,80);
        graphics.drawString("爆炸火花数量：" + explodes.size(),10,100);

        //我方坦克
        myTank.paint(graphics);
        //子弹
        for (int i = 0;i < bullets.size();i++) {
            bullets.get(i).paint(graphics);
        }
        //敌方坦克
        for (int i = 0;i < tanks.size();i++){
            tanks.get(i).paint(graphics);
        }

        for (int i = 0; i < bullets.size(); i++) {
            for( int j = 0 ;j < tanks.size();j ++){
                bullets.get(i).collideWith(tanks.get(j));
            }
        }
        for (int i = 0; i < explodes.size();i++){
            explodes.get(i).paint(graphics);
        }

    }

    class MyKeyListener extends KeyAdapter{
        boolean bL = false;
        boolean bR = false;
        boolean bU = false;
        boolean bD = false;
        @Override
        public void keyPressed(KeyEvent e) {
            //super.keyPressed(e);
            //System.out.println("keyP");
            //x += 200;
            //repaint();
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_LEFT:
                    bL = true;
                    //System.out.println("按下←");
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    //System.out.println("按下→");
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    //System.out.println("按下↑");
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    //System.out.println("按下↓");
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                    break;
                case KeyEvent.VK_X:
                    myTank.addFire=true;
                    System.out.println(myTank.addFire);
                    myTank.fire();
                    break;
                default:
                    break;

            }
            setMainTankDir();

            //new Thread(()->new Audio("audio/tank_move.wav").play()).start();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            //super.keyReleased(e);
            //System.out.println("keyR");
            int key = e.getKeyCode();
            switch (key){
                case KeyEvent.VK_LEFT:
                    bL = false;
                    //System.out.println("松开←");
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    //System.out.println("松开→");
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    //System.out.println("松开↑");
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    //System.out.println("松开↓");
                    break;
                case KeyEvent.VK_X:
                    myTank.setAddFire(false);
                    System.out.println(myTank.addFire);
                    break;

            }
            setMainTankDir();
        }
        private void setMainTankDir() {
            if (!bL && !bR && !bU && !bD) myTank.setMoving(false);
            else {
                myTank.setMoving(true);
                if(bL) myTank.setDir(Dir.LEFT);
                if(bR) myTank.setDir(Dir.RIGHT);
                if(bU) myTank.setDir(Dir.UP);
                if(bD) myTank.setDir(Dir.DOWN);
            }


        }
    }
}
