/**
 * Obashuuje třídu GhostObject, reprezentující objekt ducha
 * @author Štěpán Bílek (xbilek25)
 * @author Jakub Kořínek (xkorin17)
 */
package game;

import tool.common.CommonField;
import tool.common.CommonMazeObject;
import tool.view.FieldView;
import tool.view.GhostView;
import tool.common.CommonMaze;

import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Třída GhostObject, reprezentující objekt ducha
 */
public class GhostObject implements CommonMazeObject {

    /**
     * Attribut representující řadu, kde se duch vyskytuje
     */
    private int row;
    /**
     * Attribut representující sloupec, kde se duch vyskytuje
     */
    private int col;
    /**
     * Attribut representující bludiště, kde se duch vyskytuje
     */
    public CommonMaze maze;

    private JFrame frame;
    private int hits;
    /**
     * Konstruktor pro vytvořeni instance.
     * @param col sloupec, kde se duch nachází
     * @param row řádek, kde se duch nachází
     * @param maze bludiště, kde se duch nachází
     */
    public GhostObject(int row, int col, CommonMaze maze) {
        this.row = row;
        this.col = col;
        this.maze = maze;
    }
    /**
     * Metoda, vrací informaci o tom, zda se může duch pohnout daným směrem.
     * @param direction směr, do kterého se chce duch pohnout
     */
    @Override
    public boolean canMove(CommonField.Direction direction) {
        // v cíli je jiný duch
        if (this.maze.getField(this.row, this.col).nextField(direction).contains(this))
            return false;
        return this.maze.getField(this.row, this.col).nextField(direction).canMove();
    }

    /**
     * Metoda, přesouvající objekt daným směrem
     * @param direction směr, do kterého se objekt pohne
     */
    @Override
    public boolean move(CommonField.Direction direction) {
        CommonField targetField = this.maze.getField(this.row, this.col).nextField(direction);
        CommonField currentField = this.maze.getField(this.row, this.col);
        if (this.canMove(direction)){
            //duch je na políčku současně s pacmanem
            if (currentField.get().isPacman()){
                targetField.put(this);
                currentField.remove(this);
                this.row = this.row + direction.deltaRow();
                this.col = this.col + direction.deltaCol();
                FieldView fieldViewTarget = new FieldView(targetField);
                FieldView fieldViewCurrent = new FieldView(currentField);
                GhostView ghostViewTarget = new GhostView(fieldViewTarget, this);
                targetField.addObserver(fieldViewTarget);
                currentField.removeObserver(fieldViewCurrent);
                targetField.notifyObservers();
                currentField.notifyObservers();
            }
            //duch je na políčku sám a v cíli nikdo není
            else if (this.maze.getField(this.row, this.col).nextField(direction).isEmpty() ||
                    this.maze.getField(this.row, this.col).nextField(direction).get().isKey()) {
                targetField.put(this);
                currentField.remove(this);
                this.row = this.row + direction.deltaRow();
                this.col = this.col + direction.deltaCol();
                FieldView fieldViewTarget = new FieldView(targetField);
                FieldView fieldViewCurrent = new FieldView(currentField);
                GhostView ghostViewTarget = new GhostView(fieldViewTarget, this);
                targetField.addObserver(fieldViewTarget);
                currentField.removeObserver(fieldViewCurrent);
                targetField.notifyObservers();
                currentField.notifyObservers();
            }
            //duch je na políčku sám a v cíli je pacman
            else {
                CommonMazeObject target = this.maze.getField(this.row, this.col).nextField(direction).get();
                if (target.isPacman()) {
                    targetField.put(this);
                    currentField.remove(this);
                    this.row = this.row + direction.deltaRow();
                    this.col = this.col + direction.deltaCol();
                    target.damage(1);
                    this.damage(-1);
                    if (this.maze.getPacman().getLives() == 0){
                        this.maze.setDone();
                        showMessageDialog(null, "DEFEAT");
                        this.frame.dispose();
                    }
                    FieldView fieldViewCurrent = new FieldView(currentField);
                    currentField.removeObserver(fieldViewCurrent);
                    FieldView fieldViewTarget = new FieldView(targetField);
                    targetField.addObserver(fieldViewTarget);
                    targetField.notifyObservers();
                    currentField.notifyObservers();
                }
            }
            return true;
        }
        return false;
    }


    @Override
    public boolean isPacman() {
        return false;
    }


    @Override
    public boolean isTarget(){
        return false;
    }

    /**
     * Metoda, vrací pole, na kterém se duch nachází
     * @return pole, na kterém se duch nachází
     */
    @Override
    public CommonField getField() {
        return this.maze.getField(this.row, this.col);
    }

    /**
     * Metoda, počet případů, kdy duch zasáhl pacmana
     * @return počet případů, kdy duch zasáhl pacmana
     */
    @Override
    public int getLives() {
        return this.hits;
    }

    /**
     * Metoda, ubere život Pacmanovi
     */
    @Override
    public void damage(int d) {
        this.hits -= d;
    }
    /**
     * Metoda, která nastaví okno, ve kterém hra probíhá
     * @param frame okno, ve kterém hra probíhá
     */
    @Override
    public void setFrame(JFrame frame){
        this.frame = frame;
    }
}
