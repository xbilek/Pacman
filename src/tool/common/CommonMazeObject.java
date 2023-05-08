/**
 * Rozhraní pro hratelné objekty.
 *
 * @author Jakub Kořínek
 * @author Štěpán Bílek
 */

package tool.common;

import javax.swing.*;

/**
 * Rozhraní pro hratelné objekty.
 */
public interface CommonMazeObject {
    /**
     * Funcke zjistí, jestli se objekt smí pohnout zadaným směrem.
     * @param var1 Směr.
     * @return True, pokud se objekt smí pohnout, jinak false.
     */
    boolean canMove(CommonField.Direction var1);

    /**
     * Funkce posune objekt.
     * @param var1 Směr posunu.
     * @return True, pokud byl přesun úspěšný. Jinak false.
     */
    boolean move(CommonField.Direction var1);

    /**
     * @return True, pokud je tato instance Pacman.
     */
    default boolean isPacman() {
        return false;
    }

    /**
     * @return True, pokud je tato instance cíl.
     */
    default boolean isTarget() {return false; }

    /**
     * @return True, pokud je tato instance klíč.
     */
    default boolean isKey() {return false; }

    /**
     * Funkce zjistí pole, na kterém stojí objekt.
     * @return Políčko, na kterém tato instance je.
     */
    CommonField getField();

    /**
     * Funkce slouží pro zjištění počtu životů.
     * @return Životy.
     */
    int getLives();

    /**
     * Poškodé objekt. (ubere život)
     * @param d Míra poškození.
     */
    void damage(int d);

    /**
     * Funkce uloží JFrame hry, aby s ní objekt mohl komunikovat.
     * @param frame Lišta hry.
     */
    void setFrame(JFrame frame);
}
