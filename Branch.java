
package clockevolution;

/**
 *
 * @author stevenr4
 *
 * Created by Steven Rogers
 *
 */

import java.util.Random;

/*
 * A branch is a list of 'bends' (integers) that
 * determine the direction of the bend
 * 0: up
 * 1: right
 * 2: down
 * 3: left
 *
 * For example, a branch with length 6 could be like this:
 * [2,1,2,1,1,0]
 *
 * And that would look like this:
 *
 *  A
 *  ## #
 *   ###
 *
 * Where A is the anchor and the # is the rest of the branch
 *
 */
public class Branch {

    //This is the list of integers (0-3)
    private int bend[];
    //The length of the whole branch
    private int length;
    //How random the mutations are for this branch
    private int randomness;

    //We create a new random generator to handle the mutations
    private Random rand = new Random();

    //Without any inputs, we create a branch with default variables
    Branch(){
        length = 1000;
        bend = new int[length];
        randomness = 5;
        fillBend();
    }

    //However, it is recommended that a LENGTH is given to this branch
    Branch(int setLength){
        length = setLength;
        bend = new int[length];
        randomness = 5;
        fillBend();
    }

    //If we are given an existing branch, we create a new one with the SAME variables
    Branch(Branch b){

        length = b.getLength();
        bend = new int[length];
        randomness = b.getRandomness();
        for(int i = 0; i < length; i++){
            bend[i] = b.getOneBend(i);
        }
    }

    //These two functions are for setting and getting the randomness
    public void setRandomness(int newRandom){
        randomness = newRandom;
    }
    public int getRandomness(){
        return randomness;
    }

    //This is to fill the BEND array with RANDOM bends
    private void fillBend(){
        for(int i = 0; i < length; i++){
            bend[i] = rand.nextInt(3);
        }
    }

    //Set a specific bend
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

    //Set the length
    public void setLength(int newLength){
        length = newLength;
        //We need to declare the length again
        bend = new int[length];
        //And populate it so we're not reading any bad data
        fillBend();
    }


    public int getLength(){
        return length;
    }
    public int[] getBend(){
        return bend;
    }

    //Gets a specific bend in the array GIVEN the index of that bend
    public int getOneBend(int location){
       if((location < 0) || (location > length)){
            System.err.println("Tried to read an invalid location");
        }else{
            return bend[location];
        }
       return 0;
    }

    //This function mutates the bend on the level of the randonmess given to it
    public void mutate(){
        //Declare the location, the new bend, and the amount of bends
        int location;
        int newBend;
        int amount;

        //We set the amount of bends to a number between 0 and the randomness
        amount = rand.nextInt(randomness);

        //For the amount of bends we will do to this mutation....
        for(int i = 0; i < amount; i++){
            //Get a location on the branch to bend
            location = rand.nextInt(length);
            //Get a direction to bend
            newBend = rand.nextInt(15);
            //Set that location to the new bend
            bend[location] = newBend;
        }
            
    }

    //This gets the end point of the bend
    public int[] getEndingPoints(){ //OUTPUT = [X_POSITION, Y_POSITION]

        //We create a storage variable to store the X and Y positions of the end of the branch
        int storage[] = new int[2];

        //We declare the x and y positions
        int xPos, yPos;

        //Here, we set the x and Y to the center of the screen
        xPos = 400;                                         ////////////WARNING, NOT DYNAMIC. FIX THIS
        yPos = 300;

        //For every bend in the string
        for(int i = 0; i < length; i++){

            //Go in the direction of that bend
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

        //Set the storage array
        storage[0] = xPos;
        storage[1] = yPos;

        //Return the array
        return storage;
    }
}
