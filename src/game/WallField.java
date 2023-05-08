package game;

import tool.common.CommonField;
import tool.common.CommonMazeObject;

public class WallField implements CommonField{
    private int col;
    private int row;
    public WallField(int row, int col){
        this.col = col;
        this.row = row;
    }
    @Override
    public CommonField nextField(Direction direction) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public CommonMazeObject get() {
        return null;
    }

    @Override
    public boolean canMove() {
        return false;
    }

    @Override
    public boolean contains(CommonMazeObject commonMazeObject) {
        return false;
    }

    @Override
    public boolean put(CommonMazeObject object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean remove(CommonMazeObject object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addObserver(Observer observer) {

    }

    @Override
    public void removeObserver(Observer observer) {

    }

    @Override
    public void notifyObservers() {

    }
    @Override
    public boolean hasPacman(){
        return false;
    }

    @Override
    public boolean hasKey(){
        return false;
    }

    @Override
    public boolean hasTarget(){
        return false;
    }

}
