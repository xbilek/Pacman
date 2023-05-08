/**
 * MazeReplayer slouží k přehrání odehrané hry.
 *
 * @author Jakub Kořínek
 * @author Štěpán Bílek
 */

package tool;

import game.MazeConfigure;
import tool.common.CommonMaze;
import tool.common.CommonMazeObject;
import tool.view.FieldView;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import game.NewKeyListener;
import game.PathField;

/**
 * MazeReplayer slouží k přehrání odehrané hry.
 */
public class MazeReplayer {
    private CommonMaze maze;
    private CommonMazeObject pacmanObj;
    private JFrame frame;

    private int rows;
    private int cols;

    private JPanel content;

    public MazeReplayer(){
        try {

            File f = new File("data/save.txt");

            Scanner scanner = new Scanner(f);
            rows = Integer.parseInt(scanner.next());
            cols = Integer.parseInt(scanner.next());
        } catch (FileNotFoundException ex) {
            System.out.println("An Error occurred.");
            ex.printStackTrace();
            return;
        }
    }

    /**
     * Funkce spustí nové vlákno a přehraje hru.
     */
    public void startReplay(){
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {

                    File f = new File("data/save.txt");

                    Scanner scanner = new Scanner(f);

                    rows = Integer.parseInt(scanner.next());
                    cols = Integer.parseInt(scanner.next());
                    scanner.nextLine();
                    while (scanner.hasNextLine()) {
                        // načtení řádků bludiště
                        MazeConfigure cfg = new MazeConfigure();

                        cfg.startReading(rows, cols);
                        while (scanner.hasNextLine()) {
                            String data = scanner.nextLine();
                            //System.out.println(data);
                            if (data == "") {
                                break;
                            }
                            cfg.processLine(data);
                        }
                        cfg.stopReading();
                        if (scanner.hasNextLine())
                            scanner.nextLine();
                        // zpracování bludiště pomocí funkce zpracujBludiste(maze)
                        maze = cfg.createMaze();
                        frame.getContentPane().removeAll();
                        GridLayout layout = new GridLayout(rows + 2, cols + 2);
                        content = new JPanel(layout);
                        for (int i = 0; i < rows + 2; i++) {
                            for (int j = 0; j < cols + 2; j++) {
                                FieldView field = new FieldView(maze.getField(i, j));
                                content.add(field);

                            }
                        }

                        frame.getContentPane().add(content, "Center");
                        content.revalidate();
                        sleep(50);


                    }
                    scanner.close();

                } catch (FileNotFoundException ex) {
                    System.out.println("An Error occurred.");
                    ex.printStackTrace();
                    return;
                }


            }
        });
        t.start();



    }
    public void open() {
        try {
            SwingUtilities.invokeAndWait(this::initializeInterface);
        } catch (InvocationTargetException | InterruptedException var2) {
            Logger.getLogger(MazePresenter.class.getName()).log(Level.SEVERE, (String)null, var2);
        }


    }

    /**
     * Funkce inicializuje rozhraní přehrávače.
     */
    private void initializeInterface() {

        this.frame = new JFrame("Replay");
        // this.pacmanObj.setFrame(frame);
        frame.setDefaultCloseOperation(3);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setPreferredSize(new Dimension(500, 500));
        frame.setResizable(true);

        setRowsCols();

        frame.pack();
        frame.setVisible(true);

        startReplay();
    }

    /**
     * Funkce zjistí počet sloupců a řádků pro přehrávač.
     */
    private void setRowsCols(){
        try {

            File f = new File("data/save.txt");

            Scanner scanner = new Scanner(f);
            rows = Integer.parseInt(scanner.next());
            cols = Integer.parseInt(scanner.next());
        } catch (FileNotFoundException ex) {
            System.out.println("An Error occurred.");
            ex.printStackTrace();
            return;
        }
    }

    /**
     * Uspání vlákna.
     * @param ms doba uspání.
     */
    public static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            System.out.println("ERROR");
        }
    }
}