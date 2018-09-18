import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * The class <b>GameView</b> provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>DotButton</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.
 *
 * @author Guy-Vincent Jourdan, University of Ottawa
 */

public class GameView extends JFrame {
    int NUMBER_OF_ROWS;
    int NUMBER_OF_COLUMNS;
    DotButton [][] board;
    GameModel gameModel;
    GameController gameController;
    JLabel label;

    /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */
    /*ATTENTION: DONT FORGET TO CHANGE THE HEIGHT AND WIDTH TO gameModel.getHeight and Width */
    public GameView(GameModel gameModel, GameController gameController) {
        super ("|*MineSweeper it *|");
        this.gameModel=gameModel;
        this.gameController=gameController;
        /*Building of Frame*/
        NUMBER_OF_ROWS=gameModel.getHeigth ();
        NUMBER_OF_COLUMNS=gameModel.getWidth ();
        setPreferredSize(new Dimension(500, 400));

        setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
        /******Panel for the tiles *****/
        JPanel panel =new JPanel ();
        panel.setBackground (Color.WHITE);
        panel.setLayout (new GridLayout (NUMBER_OF_ROWS,NUMBER_OF_COLUMNS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 20));
        board =new DotButton [NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
        for (int i =0; i<NUMBER_OF_ROWS;i++){
            for (int j=0; j<NUMBER_OF_COLUMNS; j++){
                board [i][j]= new DotButton (j,i,11);
                board [i][j].addActionListener(gameController);
                panel.add (board[i][j]);
            }
        }
        /*******************************/
        JPanel buttom=new JPanel ();
        buttom.setBackground (Color.WHITE);
        label=new JLabel ("Steps: "+gameModel.getNumberOfSteps());
        JButton reset, quit;
        reset= new JButton ("Reset");
        reset.addActionListener(gameController);
        quit=new JButton ("quit");
        quit.addActionListener (gameController);
        buttom.add (label);
        buttom.add (reset);
        buttom.add (quit);
        add (panel, BorderLayout.CENTER);
        add (buttom, BorderLayout.SOUTH);
        pack ();
        setVisible (true);


    }

    /**
     * update the status of the board's DotButton instances based 
     * on the current game model, then redraws the view
     */

    public void update(){
        for (int i =0; i<NUMBER_OF_ROWS;i++){
            for (int j=0; j<NUMBER_OF_COLUMNS;j++){
                board [i][j].setIconNumber (getIcon(i,j));
                label.setText ("Steps: "+gameModel.getNumberOfSteps());
            }
        }

    }

    /**
     * returns the icon value that must be used for a given dot 
     * in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */   
    private int getIcon(int i, int j){
        int iconNum=11;
        if (gameModel.isCovered (i,j)==false){
            if (gameModel.isMined (i,j)==true&&gameModel.hasBeenClicked(i,j)==true){
                iconNum=10;
                return iconNum;
            }
            if (gameModel.isMined (i,j)==true&&gameModel.hasBeenClicked(i,j)==false){
                iconNum=9;
                return iconNum;
            }
            if (gameModel.isMined (i,j)==false){
                iconNum=gameModel.getNeighbooringMines (i,j);
                return iconNum;
            }
        }
        return iconNum;

    }


}
