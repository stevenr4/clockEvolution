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
import javax.swing.JFrame;

public class Screen {

    private GraphicsDevice vc;//Get a graphics card

    public Screen(){
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = env.getDefaultScreenDevice();//Get access to graphics card

    }

    public void setFullScreen(DisplayMode dm, JFrame window){
        window.setUndecorated(true);//No buttons on the window
        window.setResizable(false);//Cannot resize

        vc.setFullScreenWindow(window);//Put window in the full screen mode

        if(dm != null && vc.isDisplayChangeSupported()){
            try{
                vc.setDisplayMode(dm);
            }catch(Exception ex){}
        }
    }

    public Window getFullScreenWindow(){
        return vc.getFullScreenWindow();
    }

    public void restoreScreen(){
        Window w = vc.getFullScreenWindow();
        if(w != null){
            w.dispose();
        }
        vc.setFullScreenWindow(null);
    }
}
