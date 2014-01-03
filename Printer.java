
package clockevolution;

/**
 *
 * @author stevenr4
 *
 * Created by Steven Rogers
 *
 */

import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;


//This is just a separation of the code for the actual buffereing and printing
public class Printer {

    //This is our backbuffer
    BufferedImage buffer;
    //Our graphics
    Graphics2D g2d;

    //This is the Width and Height of the screen
    private int WIDTH = 800;        //MEANT TO BE OVERWRITTEN FROM THE MAIN FUNCTION
    private int HEIGHT = 600;       // ||        ||              ||          ||

    //This is the start function, if not given a width and height, it will use the above
    Printer(){
        //Create the buffer
        buffer = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
        //Create a graphics attached to the buffer
        g2d = buffer.createGraphics();
    }

    //This is the correct way to build up a printer giving it the width and the height
    Printer(int W, int H){
        
        //Set the width and the height
        HEIGHT = H;
        WIDTH = W;

        //Create the buffer
        buffer = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

        //Set up the graphics
        g2d = buffer.createGraphics();
    }

    //This just gets the graphics from the jframe and applys our buffer to it
    public void paint(Graphics g){
        g.drawImage(buffer, 0, 0, null);
    }

    //This function draws one branch
    public void drawBranch(Branch toDraw, Color c){
        
        //We create a DUPLICATE but not the same branch so if it changes half way through, we're not broken
        Branch draw = new Branch(toDraw);
        
        //We declare the variables for the root position
        int xPos, yPos;

        //Set it to the MIDDLE of the screen
        xPos = WIDTH/2;
        yPos = HEIGHT/2;

        //Set the color of the graphics
        g2d.setColor(c);
        
        //For the length of the branch,
        for(int i = 0; i < draw.getLength(); i++){

            //Go in that one direction one pixel
            switch(draw.getOneBend(i)){
                    case 0:
                        yPos--;
                        break;
                    case 1:
                        xPos++;
                        break;
                    case 2:
                        yPos++;
                        break;
                    case 3:
                        xPos--;
                        break;
                }
            
            //Draw a dot on the pixel
            g2d.drawLine(xPos, yPos, xPos, yPos);
        }
    }

    //Draw the children of a branch
    public void drawChildren(Branch children[], Color c){

        //Just loop through the children and do the above drawbranch function
        for(int i = 0; i < children.length; i++){
            drawBranch(children[i], c);
        }

    }

    //Print the strings in the correct locaions
    void printData(String x, String y){
        g2d.setColor(Color.WHITE);
        g2d.drawString(x, 15, 15);
        g2d.drawString(y, 15, 40);
        g2d.drawString("Right click to quit",15,75);
    }

    //Clear the screen (PAINT IT BLACK >.<)
    void cls() {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,WIDTH,HEIGHT);
    }

    //This function draws the circle and the hour hands
    public void drawClock(int second, int minute, int hour){

        //Declare the spacing on the clock
        int spacing;
        
        //Set the spacing to the thinnest of the height or width
        if(WIDTH > HEIGHT){
            spacing = (WIDTH - HEIGHT) / 2;
            g2d.drawOval(spacing, 0, HEIGHT, HEIGHT);
        }else{
            spacing = (HEIGHT - WIDTH) / 2;
            g2d.drawOval(spacing, 0, HEIGHT, HEIGHT);
        }

        //Go through the 60 positions on the clock
        for(int i = 0; i < 60; i++){
            
            //If it is on the current hour set it to green
            if(i == (hour * 5)){
                g2d.setColor(Color.GREEN);//Current hour -->
            }else if(i == minute){
                g2d.setColor(Color.BLUE); //Current minute --> Blue
            }else if(i == second){
                g2d.setColor(Color.RED);  //Current second --> Red
            }else{                        
                g2d.setColor(Color.WHITE);//Otherwise --> White
            }

            //We make 3 lines per each spot on the clock, looks pretty that way
            for(int p = -1; p < 2; p++){  ///  [-1,0,1]

                //If it's 3, 6, 9, or 12, make the lines abnormally long
                if(i % 15 == 0){
                    g2d.drawLine(Main.getXFromAngle((i * 6) + p, HEIGHT) + WIDTH/2,
                            Main.getYFromAngle((i * 6) + p, HEIGHT) + HEIGHT/2,
                            (Main.getXFromAngle(((i * 6) + p), HEIGHT) * 40) / 100 + WIDTH/2,
                            (Main.getYFromAngle(((i * 6) + p), HEIGHT) * 40) / 100 + HEIGHT/2);

                }else if(i % 5 == 0){
                    //For the hour hands, make lines long
                    g2d.drawLine(Main.getXFromAngle((i * 6) + p, HEIGHT) + WIDTH/2,
                            Main.getYFromAngle((i * 6) + p, HEIGHT) + HEIGHT/2,
                            (Main.getXFromAngle(((i * 6) + p), HEIGHT) * 70) / 100 + WIDTH/2,
                            (Main.getYFromAngle(((i * 6) + p), HEIGHT) * 70) / 100 + HEIGHT/2);
                }else{
                    //For the minute hands, make the lines short
                    g2d.drawLine(Main.getXFromAngle((i * 6) + p, HEIGHT) + WIDTH/2,
                            Main.getYFromAngle((i * 6) + p, HEIGHT) + HEIGHT/2,
                            (Main.getXFromAngle(((i * 6) + p), HEIGHT) * 95) / 100 + WIDTH/2,
                            (Main.getYFromAngle(((i * 6) + p), HEIGHT) * 95) / 100 + HEIGHT/2);
                }
            }
        }
    }
}
