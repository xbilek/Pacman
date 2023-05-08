/**
 * Obashuuje třídu KeyObject, reprezentující objekt klíče
 * @author Štěpán Bílek (xbilek25)
 * @author Jakub Kořínek (xkorin17)
 */
package game;

import tool.common.CommonField;
import tool.common.CommonMaze;
import tool.common.CommonMazeObject;

import tool.view.FieldView;

import javax.swing.*;

/**
 * Třída KeyObject, reprezentující objekt klíče
 */
public class KeyObject implements CommonMazeObject {
    private int row;
    private int col;
    public CommonMaze maze;

    /**
     * Konstruktor pro vytvořeni instance.
     * @param col sloupec, kde se klíč nachází
     * @param row řádek, kde se klíč nachází
     * @param maze bludiště, kde se klíč nachází
     */
    public KeyObject(int row, int col, CommonMaze maze) {
        this.row = row;
        this.col = col;
        this.maze = maze;
    }

    @Override
    public boolean canMove(CommonField.Direction direction){
        return true;
    }

    @Override
    public boolean move(CommonField.Direction direction) {
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
     * Metoda, vrací informaci o tom, že je objekt klíč
     * @return informace o tom, že je objekt klíč
     */
    @Override
    public boolean isKey() {return true;}
    @Override
    public CommonField getField() {
        return this.maze.getField(this.row, this.col);
    }

    @Override
    public int getLives() {
        return 0;
    }

    @Override
    public void damage(int d) {
    }
    @Override
    public void setFrame(JFrame frame){
        return;
    }
}
