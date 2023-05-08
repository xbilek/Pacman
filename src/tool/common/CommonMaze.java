/**
 * Rozhraní pro hrací bludiště.
 *
 * @author Jakub Kořínek
 * @author Štěpán Bílek
 */

package tool.common;

import java.util.List;

/**
 * Rozhraní pro hrací bludiště.
 */
public interface CommonMaze {
    /**
     * Funkce pro získání konkrétního pole bludiště.
     * @param var1 x-ová souřadnice políčka.
     * @param var2 y-ová souřadnice políčka.
     * @return Vrátí pole podle souřadnic.
     */
    CommonField getField(int var1, int var2);

    /**
     * @return Vrátí počet řádků bludiště.
     */
    int numRows();

    /**
     * @return Vrátí počet sloupců pole.
     */
    int numCols();

    /**
     * Funkce získá seznam duchů bludiště.
     * @return Seznam duchů bludiště.
     */
    List<CommonMazeObject> ghosts();

    /**
     * Funkce pro nalezení instance Pacmana.
     * @return Pacman.
     */
    CommonMazeObject getPacman();

    /**
     * Funkce pro nalezení instance cíle.
     * @return Cíl.
     */
    CommonMazeObject getTarget();

    /**
     * Funkce pro nalezení instance klíče.
     * @return Klíč.
     */
    CommonMazeObject getKey();

    /**
     * Funkce slouží pro logování průběhu hry.
     */
    void saveMaze();

    /**
     * Zjistí stav hry.
     * @return Vrátí true, pokud je hra u konce, jinak false.
     */
    boolean gameDone();

    /**
     * Nastaví příznak konce hry.
     */
    void setDone();
}
