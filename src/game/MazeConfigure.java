package game;

import tool.common.CommonField;
import tool.common.CommonMazeObject;
import tool.common.CommonMaze;
import game.PacmanObject;
import tool.view.FieldView;
import tool.view.GhostView;
import tool.view.PacmanView;
import tool.view.TargetView;
import tool.view.KeyView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MazeConfigure implements CommonMaze{
    private int cols;
    private int rows;
    private int expected_cols;
    private int expected_rows;
    private int current_row;
    private boolean wrong_cols;
    private CommonField[][] board;
    private PacmanObject Pacman;
    private FieldView fieldView;
    private PacmanView pacmanView;

    private TargetView TargetView;
    private GhostObject Ghost;

    private TargetObject Target;

    private KeyObject Key;

    private KeyView KeyView;
    private GhostView ghostView;

    private boolean hasKey;

    private boolean gameDoneFlag = false;


    private List<CommonMazeObject> ghostsList = new ArrayList<>();
    public void startReading(int rows, int cols){
        this.cols = cols + 2;
        this.expected_cols = cols;
        this.rows = rows + 2;
        this.expected_rows = rows;
        this.current_row = 0;

        this.wrong_cols = false;

        this.board = new CommonField[this.rows][this.cols];
        //zaplnění prvního řádku zdmi
        for (int i = 0; i < this.cols; i++){
            this.board[this.current_row][i] = new WallField(this.current_row, i);
        }
        this.current_row++; //current_row == 1
    }
    public boolean processLine(String line){
        if (line.length() != this.expected_cols){
            wrong_cols = true;
            return false;
        }
        //naplnění řádku
        this.board[this.current_row][0] = new WallField(this.current_row, 0);
        String[] parsed_line = line.split("");
        for (int i = 1, line_index = 0; i < cols-1; i++, line_index++){
            switch (parsed_line[line_index]){
                case ".":
                    this.board[this.current_row][i] = new PathField(this.current_row, i, this);
                    this.fieldView = new FieldView(this.board[this.current_row][i]);
                    this.board[this.current_row][i].addObserver(this.fieldView);
                    break;
                case "X":
                    this.board[this.current_row][i] = new WallField(this.current_row, i);
                    break;
                case "S":
                    this.Pacman = new PacmanObject(this.current_row, i, this);
                    this.board[this.current_row][i] = new PathField(this.current_row, i, this);
                    this.board[this.current_row][i].put(this.Pacman);

                    this.fieldView = new FieldView(this.board[this.current_row][i]);
                    this.pacmanView = new PacmanView(this.fieldView, this.Pacman);
                    this.board[this.current_row][i].addObserver(this.fieldView);
                    if(this.hasKey)
                        this.getPacman().detectKey();

                    break;
                case "G":
                    this.Ghost = new GhostObject(this.current_row, i, this);
                    this.board[this.current_row][i] = new PathField(this.current_row, i, this);
                    this.board[this.current_row][i].put(this.Ghost);

                    this.fieldView = new FieldView(this.board[this.current_row][i]);
                    this.ghostView = new GhostView(this.fieldView, this.Ghost);
                    this.board[this.current_row][i].addObserver(this.fieldView);


                    this.ghostsList.add(this.Ghost);
                    break;
                case "T":
                    this.Target = new TargetObject(this.current_row, i, this);
                    this.board[this.current_row][i] = new PathField(this.current_row, i, this);
                    this.board[this.current_row][i].put(this.Target);

                    this.fieldView = new FieldView(this.board[this.current_row][i]);
                    this.TargetView = new TargetView(this.fieldView, this.Target);
                    this.board[this.current_row][i].addObserver(this.fieldView);
                    break;
                case"K":
                    this.Key = new KeyObject(this.current_row, i, this);
                    this.board[this.current_row][i] = new PathField(this.current_row, i, this);
                    this.board[this.current_row][i].put(this.Key);

                    this.fieldView = new FieldView(this.board[this.current_row][i]);
                    this.KeyView = new KeyView(this.fieldView, this.Key);
                    this.board[this.current_row][i].addObserver(this.fieldView);
                    if(this.getPacman() != null)
                        this.getPacman().detectKey();
                    this.hasKey = true;

                    break;

                default:
                    this.wrong_cols = true;
                    break;
            }
        }
        this.board[this.current_row][this.cols-1] = new WallField(this.current_row, this.cols-1);
        this.current_row++;
        return true;
    }
    public boolean stopReading(){
        //naplnění posledního řádku
        for (int i = 0; i < this.cols; i++){
            this.board[this.current_row][i] = new WallField(this.current_row, i);
        }
        this.current_row--;
        return true;
    }
    public CommonMaze createMaze(){
        if (this.current_row == this.expected_rows && !wrong_cols)
            return this;
        else
            return null;
    }
    @Override
    public CommonField getField(int x, int y) {
        return this.board[x][y];
    }
    @Override
    public int numRows() {
        return this.rows;
    }
    @Override
    public int numCols() {
        return this.cols;
    }
    @Override
    public List<CommonMazeObject> ghosts() {
            List<CommonMazeObject> copy = new ArrayList(this.ghostsList);
        return copy;
    }

    public boolean gameDone(){
        return gameDoneFlag;
    }

    public void setDone(){
        this.gameDoneFlag = true;
    }
    @Override
    public PacmanObject getPacman() {
        return Pacman;
    }

    @Override
    public TargetObject getTarget() {
        return Target;
    }

    public KeyObject getKey() {return Key;}

    public void saveMaze(){
        try {
            File newSave = new File("data/save.txt");
            newSave.createNewFile();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        try {
            FileWriter myWriter = new FileWriter("data/save.txt", true);
            myWriter.write(this.rows - 2 + "");
            myWriter.write(" ");
            myWriter.write(this.cols - 2 + "");
            myWriter.write("\n");

            for (current_row = 1; current_row < rows - 1; current_row++) {
                myWriter.write(this.getLine());
            }
            myWriter.write("\n");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String getLine(){
        String line = "";
        for(int i = 1; i < this.cols - 1; i++){
            if(this.getField(current_row, i).hasTarget())
                line = line + "T";
            else if(this.getField(current_row, i).hasPacman())
                line = line + "S";
            else if(this.getField(current_row, i).hasKey())
                line = line + "K";
            else if(!this.getField(current_row, i).canMove())
                line = line + "X";
            else if(this.getField(current_row, i).isEmpty())
                line = line + ".";
            else
                line = line + "G";
        }
        line = line + "\n";
        return line;
    }
}
