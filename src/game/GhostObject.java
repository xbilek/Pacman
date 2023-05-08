package game;

import tool.common.CommonField;
import tool.common.CommonMazeObject;
import tool.view.FieldView;
import tool.view.GhostView;
import tool.common.CommonMaze;

import javax.swing.*;

import static javax.swing.JOptionPane.showMessageDialog;

public class GhostObject implements CommonMazeObject {
    private int row;
    private int col;
    public CommonMaze maze;
    private JFrame frame;
    private int hits;
    public GhostObject(int row, int col, CommonMaze maze) {
        this.row = row;
        this.col = col;
        this.maze = maze;
    }
    @Override
    public boolean canMove(CommonField.Direction direction) {
        // v cíli je jiný duch
        if (this.maze.getField(this.row, this.col).nextField(direction).contains(this))
            return false;
        return this.maze.getField(this.row, this.col).nextField(direction).canMove();
    }

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

    @Override
    public CommonField getField() {
        return this.maze.getField(this.row, this.col);
    }

    @Override
    public int getLives() {
        return this.hits;
    }

    @Override
    public void damage(int d) {
        this.hits -= d;
    }
    @Override
    public void setFrame(JFrame frame){
        this.frame = frame;
    }
}
