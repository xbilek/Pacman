/**
 * Obashuuje třídu NewKeyListener, zajišťující ovládání klávesnicí
 * @author Štěpán Bílek (xbilek25)
 * @author Jakub Kořínek (xkorin17)
 */
package game;

import game.PacmanObject;
import tool.common.CommonMazeObject;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

/**
 * Třída NewKeyListener, zajišťující ovládání klávesnicí
 */
public class NewKeyListener implements KeyListener {
    private CommonMazeObject obj;
    public static char prevDir;


    public static char currDir = 'd';
    public static boolean pressedFlag;

    /**
     * Konstruktor pro vytvořeni instance.
     * @param pacmanObj objekt pacmana, který je ovládán
     */
    public NewKeyListener(CommonMazeObject pacmanObj) {
        this.obj = pacmanObj;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Pass
    }
    @Override
    public void keyPressed(KeyEvent e) {
        // pass
    }

    /**
     * Metoda, volající funkci pro pohyb pacmana
     * @param e klávesa, která byla stisknuta, udává směr, kterým se bude pacman pohubovat
     */
    @Override
    public void keyTyped(KeyEvent e) {
        currDir = e.getKeyChar();
        //pressedFlag = prevDir == e.getKeyChar();
        ((PacmanObject)this.obj).dirToMove(e.getKeyChar());
        prevDir = e.getKeyChar();
    }
}
