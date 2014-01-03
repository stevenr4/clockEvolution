/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clockevolution;

/**
 *
 * @author spex
 */

import java.util.Random;

public class Branch {

    private int bend[];
    private int length;
    private int randomness;

    private Random rand = new Random();

    Branch(){
        length = 1000;
        bend = new int[length];
        randomness = 5;
        fillBend();
    }

    Branch(int setLength){
        length = setLength;
        bend = new int[length];
        randomness = 5;
        fillBend();
    }

    Branch(Branch b){

        length = b.getLength();
        bend = new int[length];
        randomness = b.getRandomness();
        for(int i = 0; i < length; i++){
            bend[i] = b.getOneBend(i);
        }
    }


    public void setRandomness(int newRandom){
        randomness = newRandom;
    }

    public int getRandomness(){
        return randomness;
    }

    private void fillBend(){
        for(int i = 0; i < length; i++){
            bend[i] = rand.nextInt(3);
        }
    }

    public void setBend(int location, int newBend){
        if((newBend < 0) || (newBend > 3)){
            System.err.println("Tried to set an invalid number");
        }else if((location < 0) || (location > length)){
            System.err.println("Tried to set a number to an invalid spot");
        }else{
            bend[location] = newBend;
        }
        return;
    }

    public void setLength(int newLength){
        length = newLength;
        bend = new int[length];
    }

    public int getLength(){
        return length;
    }

    public int[] getBend(){
        return bend;
    }

    public int getOneBend(int location){
       if((location < 0) || (location > length)){
            System.err.println("Tried to read an invalid location");
        }else{
            return bend[location];
        }
       return 0;
    }

    public void mutate(){
        int location;
        int newBend;
        int amount;

        amount = rand.nextInt(randomness);

        for(int i = 0; i < amount; i++){
            location = rand.nextInt(length);
            newBend = rand.nextInt(15);
            bend[location] = newBend;
        }
            
    }

    public int[] getEndingPoints(){

        int storage[] = new int[2];

        int xPos, yPos;

        xPos = 400;
        yPos = 300;

        int direction = 0;

        for(int i = 0; i < length; i++){
            
//
//
//            switch(bend[i]){
//                case 0:
//                    direction--;
//                    break;
//                case 2:
//                    direction++;
//                    break;
//            }
//
//            if(direction >= 4){
//                direction -= 4;
//            }else if(direction < 0){
//                direction += 4;
//            }
//
//            switch(direction){
//                case 0:
//                    yPos--;
//                    break;
//                case 1:
//                    xPos++;
//                    break;
//                case 2:
//                    yPos++;
//                    break;
//                case 3:
//                    xPos--;
//                    break;
//            }
            
            switch(bend[i]){
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

        }

        storage[0] = xPos;
        storage[1] = yPos;
        return storage;
    }
}
