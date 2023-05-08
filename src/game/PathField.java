package game;

import tool.common.CommonField;
import tool.common.CommonMazeObject;
import tool.common.Observable;
import tool.common.CommonMaze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;


public class PathField implements CommonField {
    private final Set<Observable.Observer> observers = new HashSet();
    private int col;
    private int row;
    private CommonMaze maze;
    private CommonMazeObject pacman;
    private List<CommonMazeObject> ghosts;
    private CommonMazeObject target;

    private CommonMazeObject key;
    public PathField(int row, int col, CommonMaze maze){
        this.col = col;
        this.row = row;
        this.maze = maze;
        this.pacman = null;
        this.ghosts = new ArrayList<>();
        this.target = null;
        this.key = null;
    }

    @Override
    public CommonField nextField(Direction direction) {
        switch(direction){
            case U: return maze.getField(this.row-1, this.col);
            case R: return maze.getField(this.row, this.col+1);
            case L: return maze.getField(this.row, this.col-1);
            case D: return maze.getField(this.row+1, this.col);
        }
        return null;
    }

    @Override
    public boolean isEmpty() {
        return (this.pacman == null && this.ghosts.size() == 0 && this.target == null && this.key == null);
    }

    @Override
    public CommonMazeObject get() {
        if (this.pacman != null)
            return this.pacman;
        if (this.ghosts.size() != 0)
            return this.ghosts.get(0);
        if (this.target != null)
            return this.target;
        if (this.key != null) {
            return this.key;
        }
        return null;
    }

    @Override
    public boolean canMove() {
        return true;
    }

    @Override
    public boolean contains(CommonMazeObject commonMazeObject) {
        if (commonMazeObject.isPacman()){
            return this.pacman != null;
        }
        return this.ghosts.size() != 0;
    }

    @Override
    public boolean put(CommonMazeObject object) {
        if (object.isPacman())
            this.pacman = object;
        else if(object.isTarget()) {
            this.target = object;
        }else if(object.isKey()){
            this.key = object;
           // this.target = object;
        }
        else
            this.ghosts.add(object);
        notifyObservers();
        return true;
    }

    @Override
    public boolean remove(CommonMazeObject object) {
        if (object.isPacman())
            this.pacman = null;
        else if(object.isKey()) {
            this.key = null;
        }
        else
            this.ghosts.remove(object);
        notifyObservers();
        return true;
    }

    @Override
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        this.observers.forEach((o) -> {
            o.update(this);
        });
    }

    @Override
    public boolean hasPacman(){
        return this.pacman != null;
    }

    @Override
    public boolean hasKey(){
        return this.key != null;
    }

    @Override
    public boolean hasTarget(){
        return this.target != null;
    }
}