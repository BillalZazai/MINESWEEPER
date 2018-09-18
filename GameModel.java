import java.util.Random;

/**
 * The class <b>GameModel</b> holds the model, the state of the systems. 
 * It stores the following information:
 * - the state of all the ``dots'' on the board (mined or not, clicked
 * or not, number of neighbooring mines...)
 * - the size of the board
 * - the number of steps since the last reset
 *
 * The model provides all of this informations to the other classes trough 
 *  appropriate Getters. 
 * The controller can also update the model through Setters.
 * Finally, the model is also in charge of initializing the game
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */
public class GameModel {
    private Random random=new Random ();
    private DotInfo model [][] ;
    private int width;
    private int height;
    private int numberOfMines;
    private int numberOfSteps=0;
    private int generator;//idk how many random numbers to generate but lets start with 10  
    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param width
     *            the width of the board
     * 
     * @param heigth
     *            the heigth of the board
     * 
     * @param numberOfMines
     *            the number of mines to hide in the board
     */
    public GameModel(int width, int height, int numberOfMines) {
        this.width=width;
        this.height=height;
        this.numberOfMines=numberOfMines;
        model =new DotInfo [height][width];
        reset();
    }
    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */
    public void reset(){
        numberOfSteps=0;
        for (int i =0; i <height;i++){
            for (int j=0; j<width;j++){
                model[i][j]=new DotInfo(i,j);
            }
        }
        int generatorY;
        int generatorX;
        for (int i=0; i<numberOfMines; i++){
            generatorY=random.nextInt (height);
            generatorX=random.nextInt (width);  
            model[generatorY][generatorX].setMined();
        }
    }   
    /**
     * Getter method for the heigth of the game
     * 
     * @return the value of the attribute heigthOfGame
     */   
    public int getHeigth(){
        return height;
    }
    /**
     * Getter method for the width of the game
     * 
     * @return the value of the attribute widthOfGame
     */   
    public int getWidth(){
        return width;
    }
    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isMined(int i, int j){
        return model[i][j].isMined ();
    }
    /**
     * returns true if the dot  at location (i,j) has 
     * been clicked, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean hasBeenClicked(int i, int j){
        return model[i][j].hasBeenClicked();
    }
    /**
     * returns true if the dot  at location (i,j) has zero mined 
     * neighboor, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isBlank(int i, int j){
        if (getNeighbooringMines(i,j)==0){
            return true;
        }
        return false;
    }
    /**
     * returns true if the dot is covered, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isCovered(int i, int j){
        return model[i][j].isCovered ();
    }

    /**
     * returns the number of neighbooring mines os the dot  
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighbooring mines at location (i,j)
     */   
    public int getNeighbooringMines(int i, int j){
        int count=0;

        if (i==0){
            if(j==0){
                count+= (model[i+1][j].isMined()) ? 1 : 0;
                count+= (model[i][j+1].isMined()) ? 1 : 0;
                count+= (model[i+1][j+1].isMined()) ? 1 : 0;
            }
            else if (j==width-1){
                count+= (model[i+1][j].isMined()) ? 1 : 0;
                count+= (model[i+1][j-1].isMined()) ? 1 : 0;
                count+= (model[i][j-1].isMined()) ? 1 : 0;
            }
            else{
                count+= (model[i+1][j].isMined()) ? 1 : 0;
                count+= (model[i+1][j+1].isMined()) ? 1 : 0;
                count+= (model[i+1][j-1].isMined()) ? 1 : 0;
                count+= (model[i][j-1].isMined()) ? 1 : 0;
                count+= (model[i][j+1].isMined()) ? 1 : 0;
            }
        }
        else if (i==height-1){
            if(j==0){
                count+= (model[i-1][j].isMined()) ? 1 : 0;
                count+= (model[i-1][j+1].isMined()) ? 1 : 0;
                count+= (model[i][j+1].isMined()) ? 1 : 0;
            }
            else if (j==width-1){
                count+= (model[i-1][j].isMined()) ? 1 : 0;
                count+= (model[i-1][j-1].isMined()) ? 1 : 0;
                count+= (model[i][j-1].isMined()) ? 1 : 0;
            }
            else{
                count+= (model[i-1][j].isMined()) ? 1 : 0;
                count+= (model[i-1][j-1].isMined()) ? 1 : 0;
                count+= (model[i-1][j+1].isMined()) ? 1 : 0;
                count+= (model[i][j-1].isMined()) ? 1 : 0;
                count+= (model[i][j+1].isMined()) ? 1 : 0;
            }
        }
        else{
            if(j==0){
                count+= (model[i+1][j].isMined()) ? 1 : 0;
                count+= (model[i-1][j].isMined()) ? 1 : 0;
                count+= (model[i][j+1].isMined()) ? 1 : 0;
                count+= (model[i+1][j+1].isMined()) ? 1 : 0;
                count+= (model[i-1][j+1].isMined()) ? 1 : 0;
            }
            else if(j==width-1){
                count+= (model[i+1][j].isMined()) ? 1 : 0;
                count+= (model[i-1][j].isMined()) ? 1 : 0;
                count+= (model[i][j-1].isMined()) ? 1 : 0;
                count+= (model[i+1][j-1].isMined()) ? 1 : 0;
                count+= (model[i-1][j-1].isMined()) ? 1 : 0;
            }
            else{
                count+= (model[i+1][j].isMined()) ? 1 : 0;
                count+= (model[i-1][j].isMined()) ? 1 : 0;
                count+= (model[i][j+1].isMined()) ? 1 : 0;
                count+= (model[i][j-1].isMined()) ? 1 : 0;
                count+= (model[i+1][j+1].isMined()) ? 1 : 0;
                count+= (model[i-1][j+1].isMined()) ? 1 : 0;
                count+= (model[i+1][j-1].isMined()) ? 1 : 0;
                count+= (model[i-1][j-1].isMined()) ? 1 : 0;
            }
        }
        model[i][j].setNeighbooringMines (count);
        return count;

    }
    /**
     * Sets the status of the dot at location (i,j) to uncovered
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void uncover(int i, int j){
        model[i][j].uncover ();
    }
    /**
     * Sets the status of the dot at location (i,j) to clicked
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void click(int i, int j){
        model[i][j].click ();
    }
     /**
     * Uncover all remaining covered dot
     */   
    public void uncoverAll(){
        for (int i=0; i<height; i++){
            for (int j=0;j<width;j++){
                if (model[i][j].isCovered ()==true){
                    model[i][j].uncover ();
                }
            }
        }
    }
    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
        return numberOfSteps;
    }
    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
      * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */   
    public DotInfo get(int i, int j) {
        return model [i][j];
    }


   /**
     * The metod <b>step</b> updates the number of steps. It must be called 
     * once the model has been updated after the payer selected a new square.
     */
     public void step(){
        numberOfSteps++;
     }
 
   /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        boolean finished = true;
        for (int i=0; i<height; i++){
            for (int j=0; j<width; j++){
                if (!model[i][j].isMined() && model[i][j].isCovered()){
                    //System.out.println("cell"+" "+i+" "+j+"is covered. ");
                    finished=false;
                    break;
                }
            }
        }
        return finished;
    }
    /*This methods will return to us the amount of tiles that have been clicked and are not mined*/

    /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
        String string="";
        for (int i=0; i<height; i++){
            for (int j=0;j<width;j++){
                if (model[i][j].isMined ()==true){
                    string += ".T. ";
                }
                else{
                    string +=".F. ";
                }
            }
        }
        return string;
    }
}
