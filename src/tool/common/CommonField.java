/**
 * Rozhraní pro políčko pole.
 *
 * @author Jakub Kořínek
 * @author Štěpán Bílek
 */

package tool.common;

public interface CommonField extends Observable {
    /**
     * Funkce pro zjištění sousedního políčka tohoto pole.
     * @param var1 Zadaný směr.
     * @return Vrací sousední pole ve směru var1.
     */
    CommonField nextField(Direction var1);

    /**
     * Funkce zjistí, jestli je v poli nějaký objekt.
     * @return Vrací true, když pole je prázdné, jinak false,
     */
    boolean isEmpty();

    /**
     * Funkce slouží pro uložení objektu v daném poli.
     * @return Vrátí objekt nacházející se na poli.
     */
    CommonMazeObject get();

    /**
     * Říká o poli, jestli je to zeď nebo ne.
     * @return V případě zdi vrátí false, jinak true.
     */
    boolean canMove();

    /**
     * Funkce zjišťuje, jestli pole obsahuje konkrétní instanci.
     * @param var1 Instance objektu pole.
     * @return Pokud se instance skutečně nachází na poli, vrátí true, jinak false.
     */
    boolean contains(CommonMazeObject var1);

    /**
     * Funkce vloží do pole objekt.
     * @param object Instance požadované třídy.
     * @return Vrátí true při úspěšném uložení, jinak false.
     */
    boolean put(CommonMazeObject object);

    /**
     * Funkce smaže objekt z pole.
     * @param object Instance třídy, kterou chceme oddělat.
     * @return Vrátí true při úspěšném smazání, jinak false.
     */
    boolean remove(CommonMazeObject object);

    /**
     * Funkce zjišťuje přítomnost Pacmana.
     * @return Vrátí true, pokud je v poli pacman.
     */
    boolean hasPacman();

    /**
     * Funkce zjišťuje přítomnost cíle.
     * @return Vrátí true, pokud je v poli cíl.
     */
    boolean hasTarget();

    /**
     * Funkce zjišťuje přítomnost klíče.
     * @return Vrátí true, pokud je v poli klíč.
     */
    boolean hasKey();

    /**
     * Třídá sloužící pro výčet možných směrů.
     */
    public static enum Direction {
        L(0, -1),
        U(-1, 0),
        R(0, 1),
        D(1, 0);

        private final int r;
        private final int c;

        private Direction(int dr, int dc) {
            this.r = dr;
            this.c = dc;
        }

        /**
         * Funkce slouží pro jednoduchý přístup k x-ové souřadnici objektu a její úprava podle směru.
         * @return Vrátí změnu x-ové souřadnice podle směru.
         */
        public int deltaRow() {
            return this.r;
        }

        /**
         * Funkce slouží pro jednoduchý přístup k y-ové souřadnici objektu a její úprava podle směru.
         * @return Vrátí změnu y-ové souřadnice podle směru.
         */
        public int deltaCol() {
            return this.c;
        }


    }
}
