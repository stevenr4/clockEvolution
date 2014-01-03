/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clockevolution;

/**
 *
 * @author spex
 */


import javax.swing.JFrame;
import java.awt.DisplayMode;
import java.awt.event.MouseEvent;
import java.awt.*;
import java.awt.Image.*;
import java.awt.event.*;
import java.lang.Math;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class Main extends JFrame implements MouseListener, MouseMotionListener {

    final int myWIDTH = 640;
    final int myHEIGHT = 480;

    DisplayMode dm = new DisplayMode(myWIDTH,myHEIGHT,16,DisplayMode.REFRESH_RATE_UNKNOWN);

    boolean done;

    Screen s;
    Printer p;

    int mouseX;
    int mouseY;
    boolean mouseHeld;

    Calendar c;


    evolutionGroup secondHand,minuteHand,hourHand;


    public static void main(String[] args) {
        // TODO code application logic here
        Main m = new Main();
        m.init();
        m.run();


    }

    public void init(){
        mouseX = 0;
        mouseY = 0;
        mouseHeld = false;
        
        done = false;


        s = new Screen();

        p = new Printer(myWIDTH, myHEIGHT);

        secondHand = new evolutionGroup((myHEIGHT * 3)/2);
        minuteHand = new evolutionGroup((myHEIGHT));
        hourHand = new evolutionGroup((myHEIGHT/2));
        

        this.addMouseListener(this); 
        this.addMouseMotionListener(this); 

        c = new GregorianCalendar();
    }

    public void run(){
        s.setFullScreen(dm, this);

        System.out.println("0* = " + Math.cos(Math.toRadians(0)));
        System.out.println("30* = " + Math.cos(Math.toRadians(30)));
        System.out.println("45* = " + Math.cos(Math.toRadians(45)));
        System.out.println("60* = " + Math.cos(Math.toRadians(60)));
        System.out.println("90* = " + Math.cos(Math.toRadians(90)));


        do{

            c = new GregorianCalendar();

            secondHand.evolve(myWIDTH,myHEIGHT);
            minuteHand.evolve(myWIDTH,myHEIGHT);
            hourHand.evolve(myWIDTH,myHEIGHT);

            if(mouseHeld){
                secondHand.setTarget(mouseX, mouseY);
                minuteHand.setTarget(mouseX, mouseY);
                hourHand.setTarget(mouseX, mouseY);
            }else{
                secondHand.setTarget(Main.getXFromAngle(c.get(Calendar.SECOND) * 6, myHEIGHT) + myWIDTH/2,
                        Main.getYFromAngle(c.get(Calendar.SECOND) * 6, myHEIGHT) + myHEIGHT/2);

                minuteHand.setTarget(Main.getXFromAngle(c.get(Calendar.MINUTE) * 6, myHEIGHT) + myWIDTH/2,
                        Main.getYFromAngle(c.get(Calendar.MINUTE) * 6, myHEIGHT) + myHEIGHT/2);

                hourHand.setTarget(Main.getXFromAngle(c.get(Calendar.HOUR) * 30, myHEIGHT) + myWIDTH/2,
                        Main.getYFromAngle(c.get(Calendar.HOUR) * 30, myHEIGHT) + myHEIGHT/2);
            }


            p.cls();

            p.drawChildren(hourHand.getChildren(), Color.GREEN);
            p.drawChildren(minuteHand.getChildren(), Color.BLUE);
            p.drawChildren(secondHand.getChildren(), new Color(255,24,25));

            p.printData(c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND),
                    getXFromAngle(c.get(Calendar.SECOND) * 6, myHEIGHT) + "," + getYFromAngle(c.get(Calendar.SECOND) * 6, myHEIGHT) + " : " +
                    (c.get(Calendar.SECOND) * 6));
            
            p.drawClock(c.get(Calendar.SECOND),c.get(Calendar.MINUTE),c.get(Calendar.HOUR));



            repaint();

            try{
                Thread.sleep(3);
            }catch(Exception ex){}

        }while(done == false);

        s.restoreScreen();
    }

    public void paint(Graphics g){
        p.paint(g);
    }


    static public int getXFromAngle(double angle, int height){

        angle = angle - 90;


        if(angle < 0){
            angle += 360;
        }else if(angle > 360){
            angle -= 360;
        }
        
        int result;
        result = (int)((height / 2) * Math.cos(Math.toRadians(angle)));
        
        return result;
    }

    static public int getYFromAngle(double angle, int height){

        angle = angle - 90;


        if(angle < 0){
            angle += 360;
        }else if(angle > 360){
            angle -= 360;
        }

        int result;
        result = (int)((height / 2) * Math.sin(Math.toRadians(angle)));

        return result;
    }


    public void mouseExited(java.awt.event.MouseEvent e){}
    public void mouseEntered(java.awt.event.MouseEvent e){}
    public void mouseClicked(java.awt.event.MouseEvent e){
    }
    public void mouseReleased(java.awt.event.MouseEvent e){
        mouseHeld = false;
    }
    public void mousePressed(java.awt.event.MouseEvent e){
        if(MouseEvent.BUTTON3 == e.getButton()){
            done = true;
        }else{
            mouseHeld = true;
        }
    }
    public void mouseMoved(java.awt.event.MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();

    }
    public void mouseDragged(java.awt.event.MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
    }
}

