package tool.common;

import java.util.List;

public interface CommonMaze {
    CommonField getField(int var1, int var2);

    int numRows();

    int numCols();

    List<CommonMazeObject> ghosts();

    CommonMazeObject getPacman();

    CommonMazeObject getTarget();

    CommonMazeObject getKey();

    void saveMaze();

    boolean gameDone();

    void setDone();
}
