
package clockevolution;

/**
 *
 * @author stevenr4
 *
 * Created by Steven Rogers
 *
 */

/*
 * An evolution group consists of:
 * 1 Parent BRANCH
 * 10 Children BRANCHes
 */
public class evolutionGroup {

    //The branch of the parent
    private Branch parent;
    //An array of the children branches
    private Branch children[];
    //The static variable of how many children there are
    private final int amountOfChildren = 10;
    //How random the mutations are
    private final int randomness = 10;
    //The target (most fit) evolution point
    private int xTarget;
    private int yTarget;
    //The length of the branches
    private int length;

    //The initialize function is given just a length
    evolutionGroup( int l){

        //The length is set through the initialization
        length = l;

        //The parent becomes a default branch as long as the input
        parent = new Branch(length);

        //Set the randomness of the parent's mutations
        parent.setRandomness(randomness);

        //We need a target for the branch to reach to
        xTarget = 0;
        yTarget = 0;

        //Now we declare the amount of children
        children = new Branch[amountOfChildren];

        //For each child, create a branch
        for(int i = 0; i < amountOfChildren; i++){
            children[i] = new Branch();
            children[i].setRandomness(randomness);
        }
    }

    //This function sets the target for the children to evolve to
    public void setTarget(int x, int y){
        xTarget = x;
        yTarget = y;
    }

    //This function evolves the children, width and height are limits
    public void evolve(int width, int height){

        //Have the parent give offspring
        getChildrenFromParent();

        //From the children, get the one that is MOST FIT to survive and that's our new parent
        parent = new Branch(children[findClosestChild(width, height)]);
        
    }


    //This function gives the index of the child closest to the target (WIDTH AND HEIGHT ARE KILL LIMITS)
    public int findClosestChild(int width, int height){

        //We set the closest one to the 0th index.
        int closest = 0;
        //The closest distance is OVER 9000!!!
        int closestDistance = 90000;
        //The current distance isn't
        int distance;

        //For each child...
        for(int i = 0; i < amountOfChildren; i++){

            //We first check if it's colliding with anything
            boolean collide = false;//WILL BE USED IN A LATER RELEASE

            //Create variables to store the positions
            int xPos, yPos;

            //Set their root position to the middle of the screen
            xPos = width/2;
            yPos = height/2;

            //For each bend in the child...
            for(int l = 0; l < children[i].getLength(); l++){

                //Move the x and y up the root to their end
                switch(children[i].getOneBend(l)){
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

            //Now that we have the end point of the children branch, check the distance to the target
            distance = (int)Math.sqrt(Math.pow((xTarget - xPos),2) + Math.pow((yTarget - yPos),2));

            //If this one is closer than the previous closest...
            if((distance < closestDistance) && (!collide)){
                //Set the index to this child
                closest = i;
                //Set the new closest distance
                closestDistance = distance;
            }else{
                //Sorry son, you're not the closest
            }
        }

        //This is the INDEX of the closest child
        return closest;
    }
    
    //This function gets the children that the parent produces
    private void getChildrenFromParent(){

        //We add the parent to the 0th element of the children
        children[0] = new Branch(parent);

        //For the rest of the children...
        for(int i = 1; i < amountOfChildren; i++){

            //Make the child a duplicate of the parent
            children[i] = new Branch(parent);
            //Mutate that child
            children[i].mutate();
        }
    }

    //This function returns the children branches
    public Branch[] getChildren(){

        return children;
    }

    //This returns the parent
    public Branch getParent(){
        
        return parent;
    }
}
