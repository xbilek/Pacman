/**
 * Obashuuje třídu PathField, pole
 * @author Štěpán Bílek (xbilek25)
 * @author Jakub Kořínek (xkorin17)
 */
package game;

import tool.common.CommonField;
import tool.common.CommonMazeObject;
import tool.common.Observable;
import tool.common.CommonMaze;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;


/**
 * Třída PathField, reprezentující objekt pole
 */
public class PathField implements CommonField {
    private final Set<Observable.Observer> observers = new HashSet();
    private int col;
    private int row;
    private CommonMaze maze;
    private CommonMazeObject pacman;
    private List<CommonMazeObject> ghosts;
    private CommonMazeObject target;

    private CommonMazeObject key;

    /**
     * Konstruktor pro vytvořeni instance.
     * @param col sloupec, kde se pole nachází
     * @param row řádek, kde se pole nachází
     * @param maze bludiště, kde se pole nachází
     */
    public PathField(int row, int col, CommonMaze maze){
        this.col = col;
        this.row = row;
        this.maze = maze;
        this.pacman = null;
        this.ghosts = new ArrayList<>();
        this.target = null;
        this.key = null;
    }

    /**
     * Metoda, vrací sousední pole v daném směru.
     * @param direction směr požadovaného pole
     * @return požadované pole
     */
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


    /**
     * Metoda, vrací informaci o tom, zda je pole prázdné.
     * @return informace o tom, zda je pole prázdné
     */
    @Override
    public boolean isEmpty() {
        return (this.pacman == null && this.ghosts.size() == 0 && this.target == null && this.key == null);
    }

    /**
     * Metoda, vrací objekt, který je na poli umístěn
     * @return objekt, který je na poli umístěn
     */
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

    /**
     * Metoda, vrací informaci o tom, zda se lze na pole přesunout
     * @return informace o tom, zda se lze na pole přesunout
     */
    @Override
    public boolean canMove() {
        return true;
    }

    /**
     * Metoda, vrací informaci o tom, zda pole obsahuje pacmana nebo ducha
     * @param commonMazeObject objekt, který metoda kontroluje
     * @return informace o tom, zda pole obsahuje pacmana nebo ducha
     */
    @Override
    public boolean contains(CommonMazeObject commonMazeObject) {
        if (commonMazeObject.isPacman()){
            return this.pacman != null;
        }
        return this.ghosts.size() != 0;
    }

    /**
     * Metoda, umísťuje objekt na pole
     * @param object objekt, který metoda umísťuje
     * @return informace o tom, zda umístění proběhlo úspěšně
     */
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

    /**
     * Metoda, odstarňuje objekt na pole
     * @param object objekt, který metoda odstraňuje
     * @return informace o tom, zda odstranění proběhlo úspěšně
     */
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

    /**
     * Metoda, vrací informaci o tom, zda obsahuje pacmana
     * @return informace o tom, zda obsahuje pacmana
     */
    @Override
    public boolean hasPacman(){
        return this.pacman != null;
    }

    /**
     * Metoda, vrací informaci o tom, zda obsahuje klíč
     * @return informace o tom, zda obsahuje klíč
     */
    @Override
    public boolean hasKey(){
        return this.key != null;
    }

    /**
     * Metoda, vrací informaci o tom, zda obsahuje cíl
     * @return informace o tom, zda obsahuje cíl
     */
    @Override
    public boolean hasTarget(){
        return this.target != null;
    }
}