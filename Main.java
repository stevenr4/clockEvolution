
package clockevolution;

/**
 *
 * @author stevenr4
 * 
 * Created by Steven Rogers
 * 
 */


import javax.swing.JFrame;
import java.awt.DisplayMode;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Calendar;
import java.util.GregorianCalendar;

//The main class of the application, it uses JFrame and the mouse listeners for input
public class Main extends JFrame implements MouseListener, MouseMotionListener {

    //The width and height of the rendered screen
    final int myWIDTH = 640;
    final int myHEIGHT = 480;

    //The display mode for the screen set to the above width and height
    DisplayMode dm = new DisplayMode(myWIDTH,myHEIGHT,16,DisplayMode.REFRESH_RATE_UNKNOWN);

    //A bool to let the application know if the user wants to quit
    boolean done;

    //The screen and a printer for the screen
    Screen s;
    Printer p;

    //The known location of the mouse, and if the button is being pushed
    int mouseX;
    int mouseY;
    boolean mouseHeld;

    //This allows us to get the local time off of the computer
    Calendar c;

    //We make an evolution group for each hand
    evolutionGroup secondHand,minuteHand,hourHand;


    //The main function that is run when the application starts up
    public static void main(String[] args) {
        // TODO code application logic here
        Main m = new Main();
        m.init();
        m.run();
    }

    //This function makes sure all of the variables are declared so we aren't taking in any Null values
    public void init(){

        //Set the default mouse coords
        mouseX = 0;
        mouseY = 0;
        mouseHeld = false;

        //We're not done, we're just getting started
        done = false;

        //We create a new screen to be able to use
        s = new Screen();

        //We create a new printer to be able to print to the screen
        p = new Printer(myWIDTH, myHEIGHT);

        //This is where we declare the length of the arms
        secondHand = new evolutionGroup((myHEIGHT * 3)/2);
        minuteHand = new evolutionGroup((myHEIGHT));
        hourHand = new evolutionGroup((myHEIGHT/2));
        
        //Now we need to add the listeners to be able to listen for the events
        this.addMouseListener(this); 
        this.addMouseMotionListener(this); 

        //A new calendar gets us the current running time and date
        c = new GregorianCalendar();
    }

    //This is where the functionality begins
    public void run(){

        //Start off by setting this application to full screen using the previous display mode
        s.setFullScreen(dm, this);

        //This is how I learned the difference between Radians and Degrees :/
        System.out.println("0* = " + Math.cos(Math.toRadians(0)));
        System.out.println("30* = " + Math.cos(Math.toRadians(30)));
        System.out.println("45* = " + Math.cos(Math.toRadians(45)));
        System.out.println("60* = " + Math.cos(Math.toRadians(60)));
        System.out.println("90* = " + Math.cos(Math.toRadians(90)));

        //This is the main do while loop that handles the entire application
        do{

            //We get a new calendar (to get the current time again)
            c = new GregorianCalendar();

            //Tell the hands to evolve towards their target location (width and height are passed limits
            secondHand.evolve(myWIDTH,myHEIGHT);
            minuteHand.evolve(myWIDTH,myHEIGHT);
            hourHand.evolve(myWIDTH,myHEIGHT);

            //If the mouse is held down, their target location to evolve to IS the hand itself
            if(mouseHeld){
                secondHand.setTarget(mouseX, mouseY);
                minuteHand.setTarget(mouseX, mouseY);
                hourHand.setTarget(mouseX, mouseY);
            }else{
                //The mouse is not held down, we need to calculate their target coords ont he clock
                secondHand.setTarget(Main.getXFromAngle(c.get(Calendar.SECOND) * 6, myHEIGHT) + myWIDTH/2,
                        Main.getYFromAngle(c.get(Calendar.SECOND) * 6, myHEIGHT) + myHEIGHT/2);

                minuteHand.setTarget(Main.getXFromAngle(c.get(Calendar.MINUTE) * 6, myHEIGHT) + myWIDTH/2,
                        Main.getYFromAngle(c.get(Calendar.MINUTE) * 6, myHEIGHT) + myHEIGHT/2);

                hourHand.setTarget(Main.getXFromAngle(c.get(Calendar.HOUR) * 30, myHEIGHT) + myWIDTH/2,
                        Main.getYFromAngle(c.get(Calendar.HOUR) * 30, myHEIGHT) + myHEIGHT/2);
            }

            //We're about to begin drawing out the hands, so let's clear the screen
            p.cls();

            //Draw each child with its color
            p.drawChildren(hourHand.getChildren(), Color.GREEN);
            p.drawChildren(minuteHand.getChildren(), Color.BLUE);
            p.drawChildren(secondHand.getChildren(), new Color(255,24,25));

            //Print the debug data
            p.printData(c.get(Calendar.HOUR) + ":" + c.get(Calendar.MINUTE) + ":" + c.get(Calendar.SECOND),
                    getXFromAngle(c.get(Calendar.SECOND) * 6, myHEIGHT) + "," + getYFromAngle(c.get(Calendar.SECOND) * 6, myHEIGHT) + " : " +
                    (c.get(Calendar.SECOND) * 6));

            //Draw the clock face (not including the hands)
            p.drawClock(c.get(Calendar.SECOND),c.get(Calendar.MINUTE),c.get(Calendar.HOUR));


            //Now we add the buffer to the screen
            repaint();

            //Try sleeping for 3 millisecs (gives us an amazing framerate
            try{
                Thread.sleep(3);
            }catch(Exception ex){}

            //Check if we're done or not
        }while(done == false);

        //Un-do the screen
        s.restoreScreen();
    }

    //This overrides the paint function
    public void paint(Graphics g){

        p.paint(g);
    }

    //This function just gets the X location from an angle
    static public int getXFromAngle(double angle, int height){

        //My angle is offset by 90 degrees, so correct that
        angle = angle - 90;

        //Make sure the angle is 0-359
        if(angle < 0){
            angle += 360;
        }else if(angle >= 360){
            angle -= 360;
        }

        //Declare a variable to hold the result
        int result;

        //Calculate the resulting location
        result = (int)((height / 2) * Math.cos(Math.toRadians(angle)));

        //Return the result
        return result;
    }

    //This function just gets the Y location from an angle
    static public int getYFromAngle(double angle, int height){

        //My angle is offset by 90 degrees, so we correct that here
        angle = angle - 90;

        //Make sure the angle is within 0-359
        if(angle < 0){
            angle += 360;
        }else if(angle >= 360){
            angle -= 360;
        }

        //Declare a result variable
        int result;

        //Get the resulting Y location
        result = (int)((height / 2) * Math.sin(Math.toRadians(angle)));

        //Return the result
        return result;
    }

    //These are overwriting functions fromt he mouse events
    public void mouseExited(java.awt.event.MouseEvent e){}//We don't care about exited
    public void mouseEntered(java.awt.event.MouseEvent e){}//..or entered
    public void mouseClicked(java.awt.event.MouseEvent e){}//..or clicked
    public void mouseReleased(java.awt.event.MouseEvent e){//But we care if the mouse is released
        mouseHeld = false;
    }
    public void mousePressed(java.awt.event.MouseEvent e){//If the main button is clicked
        if(MouseEvent.BUTTON3 == e.getButton()){//if it's the right click, we quit
            done = true;
        }else{//If it's a left click, we're just dragging the mouse
            mouseHeld = true;
        }
    }
    public void mouseMoved(java.awt.event.MouseEvent e){//Update the movement at every possible location
        mouseX = e.getX();
        mouseY = e.getY();

    }
    public void mouseDragged(java.awt.event.MouseEvent e){
        mouseX = e.getX();
        mouseY = e.getY();
    }
}

