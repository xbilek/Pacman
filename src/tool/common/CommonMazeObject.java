package tool.common;

import javax.swing.*;

public interface CommonMazeObject {
    boolean canMove(CommonField.Direction var1);

    boolean move(CommonField.Direction var1);

    default boolean isPacman() {
        return false;
    }

    default boolean isTarget() {return false; }

    default boolean isKey() {return false; }
    CommonField getField();

    int getLives();

    void damage(int d);

    void setFrame(JFrame frame);
}
