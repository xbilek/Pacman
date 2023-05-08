/**
 * Rozhraní reprezentuje objekty, které mohou notifikovat observers o změnách.
 *
 * @author Jakub Kořínek
 * @author Štěpán Bílek
 */

package tool.common;

public interface Observable {
    /**
     * Registrace nového observeru.
     * @param var1 observer
     */
    void addObserver(Observer var1);

    /**
     * Odregistrace observeru.
     * @param var1 observer
     */
    void removeObserver(Observer var1);

    /**
     * Informuje registrované observery o změně.
     */
    void notifyObservers();

    /**
     * Rozhraní repezentuje registrovatelné objekty.
     */
    public interface Observer {
        /**
         * Zpracovává informaci o změne v objektu Observable.
         * @param var1 Změnený objekt.
         */
        void update(Observable var1);
    }
}
