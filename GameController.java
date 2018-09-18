import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.*;


/**
 * The class <b>GameController</b> is the controller of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */


public class GameController implements ActionListener {

    GameModel gameModel;
    GameView gameView;
    int height;
    int width;
    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int height, int numberOfMines) {
        this.height=height;
        this.width=width;
        gameModel = new GameModel (width, height, numberOfMines);
        gameView =new GameView (gameModel,this);


    }


    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals ("Reset")){
            reset ();
        }
        if (e.getActionCommand().equals ("quit")){
            System.exit(0);
        }
        if(e.getSource() instanceof DotButton){
            DotButton b =(DotButton) e.getSource();
            play (b.getColumn(), b.getRow());
        }

    gameView.update();
    }

    /**
     * resets the game
     */
    public void reset(){
        gameModel.reset();
    }

    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares. 
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param width
     *            the selected column
     * @param heigth
     *            the selected line
     */
    public void play(int width, int heigth){
        if (gameModel.hasBeenClicked (heigth, width)==false){
                gameModel.click (heigth, width);
                gameModel.step();
                gameModel.uncover (heigth, width);
                if (gameModel.isMined (heigth,width)==true){
                    gameModel.uncoverAll ();
                }   
                else if (gameModel.getNeighbooringMines(heigth,width)==0){
                    clearZone (gameModel.get(heigth,width));
                }
                 
        }
        if ((gameModel.isFinished())!=false){
            gameModel.uncoverAll ();
                
 /************************************************************/           
        }

        
    }

   /**
     * <b>clearZone</b> is the method that computes which new dots should be ``uncovered'' 
     * when a new square with no mine in its neighborood has been selected
     * @param initialDot
     *      the DotInfo object corresponding to the selected DotButton that
     * had zero neighbouring mines
     */
   


    private void clearZone(DotInfo initialDot) {
        GenericArrayStack<DotInfo> dotStack = new GenericArrayStack (gameModel.getHeigth()*gameModel.getWidth());
        dotStack.push (initialDot);
        while (!(dotStack.isEmpty())){
            DotInfo d=dotStack.pop();
            for (int i =0;i<3;i++){
                for (int j=0;j<3;j++){
                    int x=d.getX ()-1+i;
                    int y=d.getY ()-1+j;
                    if (((x>-1) && (x<gameModel.getHeigth()))&& ((y>-1) && (y<gameModel.getWidth()))){
                        DotInfo n= gameModel.get (x,y);
                        if (n.isCovered ()==true){
                            n.uncover();
                            if (gameModel.getNeighbooringMines(x,y)==0){
                                dotStack.push (n);
                            }
                        }
                    }

                }
            }
        }
    }



}
