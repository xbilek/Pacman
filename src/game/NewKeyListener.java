package game;

import game.PacmanObject;
import tool.common.CommonMazeObject;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class NewKeyListener implements KeyListener {
    private CommonMazeObject obj;
    public static char prevDir;


    public static char currDir = 'd';
    public static boolean pressedFlag;

    public NewKeyListener(CommonMazeObject pacmanObj) {
        this.obj = pacmanObj;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Pass
    }
    @Override
    public void keyPressed(KeyEvent e) {
//        currDir = e.getKeyChar();
//        //pressedFlag = prevDir == e.getKeyChar();
//        ((PacmanObject)this.obj).dirToMove(e.getKeyChar());
//        prevDir = e.getKeyChar();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        currDir = e.getKeyChar();
        //pressedFlag = prevDir == e.getKeyChar();
        ((PacmanObject)this.obj).dirToMove(e.getKeyChar());
        prevDir = e.getKeyChar();
    }
}
