/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clockevolution;

/**
 *
 * @author spex
 */
public class evolutionGroup {

    private Branch parent;

    private Branch children[];
    private final int amountOfChildren = 10;
    private final int randomness = 10;
    private int xTarget;
    private int yTarget;
    private int length;


    evolutionGroup( int l){

        length = l;

        parent = new Branch(length);

        parent.setRandomness(randomness);

        xTarget = 0;
        yTarget = 0;

        children = new Branch[amountOfChildren];
        for(int i = 0; i < amountOfChildren; i++){
            children[i] = new Branch();
            children[i].setRandomness(randomness);
        }
    }

    public void setTarget(int x, int y){

        
        xTarget = x;
        yTarget = y;
    }

    public void evolve(int width, int height){

        getChildrenFromParent();

        parent = new Branch(children[findClosestChild(width, height)]);
        
    }


    public int findClosestChild(int width, int height){

        int closest = 0;
        int closestDistance = 90000;
        int distance;

        for(int i = 0; i < amountOfChildren; i++){

            boolean collide = false;

            int xPos, yPos;

            xPos = width/2;
            yPos = height/2;

            for(int l = 0; l < children[i].getLength(); l++){

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


            distance = (int)Math.sqrt(Math.pow((xTarget - xPos),2) + Math.pow((yTarget - yPos),2));

            if((distance < closestDistance) && (!collide)){
                closest = i;
                closestDistance = distance;
            }else{
            }
        }

        return closest;
    }
    
    
    private void getChildrenFromParent(){

        children[0] = new Branch(parent);

        for(int i = 1; i < amountOfChildren; i++){

            children[i] = new Branch(parent);
            children[i].mutate();
        }

    }

    public Branch[] getChildren(){

        return children;
    }

    public Branch getParent(){
        
        return parent;
    }
}
