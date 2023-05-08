/**
 * Modul reprezentující samotnou hru.
 *
 * @author Jakub Kořínek
 * @author Štěpán Bílek
 */

package tool;

import tool.common.CommonField;
import tool.common.CommonMaze;
import tool.common.CommonMazeObject;
import tool.view.FieldView;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

import game.NewKeyListener;

/**
 * Třída reprezentující hrací pole a hraní.
 */
public class MazePresenter {
    private final CommonMaze maze;
    private CommonMazeObject pacmanObj;
    private JFrame frame;
    public MazePresenter(CommonMaze maze, CommonMazeObject pacmanObj) {
        this.maze = maze;
        this.pacmanObj = pacmanObj;
        startGhosts();
    }

    /**
     * Funkce spustí odpovídající počet vláken pro každého ducha.
     * Probíhá náhodný pohyb.
     */
    private void startGhosts(){
        CommonMazeObject pac = maze.getPacman();

        for (int i = 0; i < maze.ghosts().size(); i++){
            final int ghostNum = i;
            maze.ghosts().get(i).setFrame(this.frame);
            Thread t = new Thread(new Runnable() {
                public void run() {
                    CommonMazeObject obj = maze.ghosts().get(ghostNum);
                    while(!maze.gameDone()){
                        sleep(500);
                        Random rand = new Random();
                        int randomNum = rand.nextInt(4);
                        switch (randomNum){
                            case 0:
                                obj.move(CommonField.Direction.R);
                                break;
                            case 1:
                                obj.move(CommonField.Direction.D);
                                break;
                            case 2:
                                obj.move(CommonField.Direction.U);
                                break;
                            case 3:
                                obj.move(CommonField.Direction.L);
                                break;
                        }
                    }
                }
            });
            t.start();

        }
    }

    public void open() {
        try {
            SwingUtilities.invokeAndWait(this::initializeInterface);
        } catch (InvocationTargetException | InterruptedException var2) {
            Logger.getLogger(MazePresenter.class.getName()).log(Level.SEVERE, (String)null, var2);
        }

    }

    /**
     * Inicializace rozhraní hry.
     */
    private void initializeInterface() {
        this.frame = new JFrame("Pacman");
        this.pacmanObj.setFrame(frame);
        frame.setDefaultCloseOperation(3);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 400);
        frame.setPreferredSize(new Dimension(350, 400));
        frame.setResizable(true);
        int rows = this.maze.numRows();
        int cols = this.maze.numCols();
        GridLayout layout = new GridLayout(rows, cols);
        JPanel content = new JPanel(layout);

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                FieldView field = new FieldView(this.maze.getField(i, j));
                content.add(field);
            }
        }

        if (pacmanObj != null){
            NewKeyListener keyListener = new NewKeyListener(pacmanObj);
            frame.addKeyListener(keyListener);
        }

        frame.getContentPane().add(content, "Center");

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Uspání vlákna.
     * @param ms Doba uspání.
     */
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            System.out.println("ERROR");
        }
    }
}