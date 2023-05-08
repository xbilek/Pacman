package game;

import tool.common.CommonField;
import tool.common.CommonMazeObject;
import tool.common.CommonMaze;

import javax.swing.*;


import static javax.swing.JOptionPane.showMessageDialog;

public class PacmanObject implements CommonMazeObject {
    private int row;
    private int col;
    private CommonMaze maze;
    private JFrame frame;
    private boolean needsKey = false;

    private boolean removeKey = false;

    private int lives;

    public PacmanObject(int row, int col, CommonMaze maze) {
        this.row = row;
        this.col = col;
        this.maze = maze;
        this.lives = 3;
    }

    @Override
    public boolean canMove(CommonField.Direction direction) {
        return this.maze.getField(this.row, this.col).nextField(direction).canMove();
    }

    @Override
    public boolean move(CommonField.Direction direction) {
        if (this.canMove(direction)){
            CommonField targetField = this.maze.getField(this.row, this.col).nextField(direction);
            CommonField currentField = this.maze.getField(this.row, this.col);
            if (targetField.get() == maze.getTarget()) {
                if (this.canWin()) {
                    this.maze.setDone();
                    showMessageDialog(null, "VICTORY");
                    this.frame.dispose();
                }
            }
            if (targetField.get() == maze.getKey() && targetField.get() != null){
                this.needsKey = false;
                if (this.maze.getKey() != null);
                    targetField.remove(this.maze.getKey());

            }
            else if (targetField.get() != null && !targetField.get().isTarget()) {
                this.damage(1);
                targetField.get().damage(-1);
                if (this.getLives() == 0) {
                    this.maze.setDone();
                    showMessageDialog(null, "DEFEAT");
                    this.frame.dispose();
                }
            }
            targetField.put(this);
            currentField.remove(this);
            //v cílí je duch
            this.row = this.row + direction.deltaRow();
            this.col = this.col + direction.deltaCol();
//            FieldView fieldViewTarget = new FieldView(targetField);
//            FieldView fieldViewCurrent = new FieldView(currentField);
//            GhostView ghostViewTarget = new GhostView(fieldViewTarget, this);
//            targetField.addObserver(fieldViewTarget);
//            currentField.removeObserver(fieldViewCurrent);
//            targetField.notifyObservers();
//            currentField.notifyObservers();
        }
        this.maze.saveMaze();
        return false;
    }

    @Override
    public boolean isPacman() {
        return true;
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
        return this.lives;
    }


    public void dirToMove(char key) {
        Runnable moveRunnable = new Runnable() {
            public void run() {
                switch (key) {
                    case 'd':
                        while(NewKeyListener.currDir == 'd' && !maze.gameDone()) {
                            move(CommonField.Direction.R);
                            sleep(250);;
                        }
                        break;
                    case 's':
                        while(NewKeyListener.currDir == 's' && !maze.gameDone()) {
                            move(CommonField.Direction.D);
                            sleep(250);
                        }
                        break;
                    case 'a':
                        while(NewKeyListener.currDir == 'a' && !maze.gameDone()) {
                            move(CommonField.Direction.L);
                            sleep(250);
                        }
                        break;
                    case 'w':
                        while(NewKeyListener.currDir == 'w' && !maze.gameDone()) {
                            move(CommonField.Direction.U);
                            sleep(250);
                        }
                        break;
                }
            }
            private void sleep(int ms) {
                try {
                    Thread.sleep(ms);
                } catch (InterruptedException ex) {
                    System.out.println("ERROR");
                }
            }
        };
        Thread t = new Thread(moveRunnable);
        t.start();

    }



    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            System.out.println("ERROR");
        }
    }


    public void damage(int d){
        if (this.lives != 0)
            this.lives -= d;
    }

    public void detectKey(){
        this.needsKey = true;
    }
    public boolean canWin(){
        return !this.needsKey;
    }

    @Override
    public void setFrame(JFrame frame){
        this.frame = frame;
    }


}