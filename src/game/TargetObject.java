package game;

import tool.common.CommonField;
import tool.common.CommonMaze;
import tool.common.CommonMazeObject;

import tool.view.FieldView;
import tool.view.TargetView;

import javax.swing.*;


public class TargetObject implements CommonMazeObject {
    private int row;
    private int col;
    public CommonMaze maze;
    public TargetObject(int row, int col, CommonMaze maze) {
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
    public boolean isKey() {
        return false;
    }

    @Override
    public boolean isTarget(){
        return true;
    }

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
