/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clockevolution;

/**
 *
 * @author spex
 */

import java.awt.*;
import java.awt.image.*;

public class Printer {

    BufferedImage buffer;
    Graphics2D g2d;

    private int WIDTH = 800;
    private int HEIGHT = 600;

    Printer(){
        buffer = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

        g2d = buffer.createGraphics();
    }

    Printer(int W, int H){
        HEIGHT = H;
        WIDTH = W;


        buffer = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);

        g2d = buffer.createGraphics();
    }

    public void paint(Graphics g){
        g.drawImage(buffer, 0, 0, null);
    }

    public void drawBranch(Branch toDraw, Color c){
        Branch draw = new Branch(toDraw);
        int xPos, yPos;

        xPos = WIDTH/2;
        yPos = HEIGHT/2;

        g2d.setColor(c);
        
        int direction = 0;

        for(int i = 0; i < draw.getLength(); i++){

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
            
            g2d.drawLine(xPos, yPos, xPos, yPos);
        }
    }

    public void drawChildren(Branch children[], Color c){

        for(int i = 0; i < children.length; i++){
            drawBranch(children[i], c);
        }

    }

    void printData(String x, String y){
        g2d.setColor(Color.WHITE);
        g2d.drawString(x, 15, 15);
        g2d.drawString(y, 15, 40);
    }

    void cls() {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,WIDTH,HEIGHT);
    }

    public void drawClock(int second, int minute, int hour){

        int spacing;
        if(WIDTH > HEIGHT){
            spacing = (WIDTH - HEIGHT) / 2;
            g2d.drawOval(spacing, 0, HEIGHT, HEIGHT);
        }else{
            spacing = (HEIGHT - WIDTH) / 2;
            g2d.drawOval(spacing, 0, HEIGHT, HEIGHT);
        }

        for(int i = 0; i < 60; i++){
            if(i == (hour * 5)){
                g2d.setColor(Color.GREEN);
            }else if(i == minute){
                g2d.setColor(Color.BLUE);
            }else if(i == second){
                g2d.setColor(Color.RED);
            }else{
                g2d.setColor(Color.WHITE);
            }
            for(int p = -1; p < 2; p++){


                if(i % 15 == 0){
                    g2d.drawLine(Main.getXFromAngle((i * 6) + p, HEIGHT) + WIDTH/2,
                            Main.getYFromAngle((i * 6) + p, HEIGHT) + HEIGHT/2,
                            (Main.getXFromAngle(((i * 6) + p), HEIGHT) * 40) / 100 + WIDTH/2,
                            (Main.getYFromAngle(((i * 6) + p), HEIGHT) * 40) / 100 + HEIGHT/2);

                }else if(i % 5 == 0){
                    g2d.drawLine(Main.getXFromAngle((i * 6) + p, HEIGHT) + WIDTH/2,
                            Main.getYFromAngle((i * 6) + p, HEIGHT) + HEIGHT/2,
                            (Main.getXFromAngle(((i * 6) + p), HEIGHT) * 70) / 100 + WIDTH/2,
                            (Main.getYFromAngle(((i * 6) + p), HEIGHT) * 70) / 100 + HEIGHT/2);
                }else{
                    g2d.drawLine(Main.getXFromAngle((i * 6) + p, HEIGHT) + WIDTH/2,
                            Main.getYFromAngle((i * 6) + p, HEIGHT) + HEIGHT/2,
                            (Main.getXFromAngle(((i * 6) + p), HEIGHT) * 95) / 100 + WIDTH/2,
                            (Main.getYFromAngle(((i * 6) + p), HEIGHT) * 95) / 100 + HEIGHT/2);
                }
            }
        }
    }
}
