

package clockevolution;

/**
 *
 * Credit on this page goes to a youtube tutorial I once watched (which i cannot find)
 *
 */

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.DisplayMode;
import java.awt.Window;
import javax.swing.JFrame;


//This class manages the screen
public class Screen {

    //The graphics card of the computer
    private GraphicsDevice vc;

    //Sets up the graphics environmnet
    public Screen(){
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        vc = env.getDefaultScreenDevice();

    }

    //This function sets the window to ful screen
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

    //Returns the window
    public Window getFullScreenWindow(){
        return vc.getFullScreenWindow();
    }

    //Restores the screen to how it wa before the application started
    public void restoreScreen(){
        Window w = vc.getFullScreenWindow();
        if(w != null){
            w.dispose();
        }
        vc.setFullScreenWindow(null);
    }
}
