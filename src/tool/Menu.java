/**
 * Třída popisující hlavní okno aplikace.
 *
 * @author Jakub Kořínek
 * @author Štěpán Bílek
 */

package tool;

import game.MazeConfigure;
import tool.common.CommonMaze;
import tool.common.CommonMazeObject;
import tool.MazePresenter;
import tool.MazeReplayer;
import tool.view.FieldView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 * Třída popisující hlavní okno aplikace.
 */
public class Menu implements ActionListener {
    private JFrame frame;
    private boolean file_chosen;
    private String file;

    private CommonMaze maze;

    public void Menu(){
        this.file_chosen = false;
        this.file = null;
    }
    public void open() {
        try {
            SwingUtilities.invokeAndWait(this::initializeInterface);
        } catch (InvocationTargetException | InterruptedException var2) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, (String)null, var2);
        }

    }

    /**
     * Funkce slouží pro uložení cesty k mapě, kterou chceme načíst.
     *
     * @return cesta k mapě
     */
    public String get_file(){
        return this.file;
    }

    /**
     * Funkce Inicializuje rozhrani menu.
     */
    private void initializeInterface() {
        this.frame = new JFrame("Pacman Menu");
        this.frame.setDefaultCloseOperation(3);
        this.frame.setPreferredSize(new Dimension(300, 300));
        this.frame.setResizable(false);

        JButton button = new JButton("Start game");
        button.setBounds(50, 10, 180, 50);
        button.addActionListener(this);
        this.frame.add(button);

        JButton button2 = new JButton("Load map");
        button2.setBounds(50, 80, 180, 50);
        button2.addActionListener(this);
        this.frame.add(button2);

        JButton button3 = new JButton("Replay game");
        button3.setBounds(50, 150, 180, 50);
        button3.addActionListener(this);
        this.frame.add(button3);

        this.frame.setSize(300, 250);
        this.frame.setLayout(null);
        this.frame.setVisible(true);
    }

    /**
     * Funkce zpracuje stisknutí tlačítek.
     *
     * @param e Událost ke zpracování.
     */
    @Override
    public void actionPerformed(ActionEvent e){
        switch (e.getActionCommand()){
            case "Start game":
                if (this.file_chosen) {
                    try {
                        File save = new File("data/save.txt");
                        if (!save.createNewFile())
                            save.delete();
                    } catch (IOException ex) {
                        System.out.println("An Error occurred.");
                        ex.printStackTrace();
                        return;
                    }
                    MazeConfigure cfg = new MazeConfigure();
                    try {
                        File f = new File(this.file);

                        Scanner scanner = new Scanner(f);

                        int rows = Integer.parseInt(scanner.next());
                        int cols = Integer.parseInt(scanner.next());
                        // vypuštění zbytku
                        scanner.nextLine();

                        cfg.startReading(rows, cols);
                        while (scanner.hasNextLine()) {
                            String data = scanner.nextLine();
                            cfg.processLine(data);
                        }
                        cfg.stopReading();

                        scanner.close();
                    } catch (FileNotFoundException ex) {
                        System.out.println("An Error occurred.");
                        ex.printStackTrace();
                        return;
                    }

                    maze = cfg.createMaze();
                    CommonMazeObject pac = maze.getPacman();

                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            MazePresenter presenter = new MazePresenter(maze, pac);
                            presenter.open();
                        }
                    });
                    t.start();
                }
                else{
                    showMessageDialog(null, "Please choose maze map.");
                }
                break;
            case "Load map":
                FileDialog fd = new FileDialog(this.frame, "Choose a file", FileDialog.LOAD);
                fd.setFile("*.txt");
                fd.setVisible(true);
                this.file = fd.getDirectory() + fd.getFile();
                if (fd.getFile() != null)
                    this.file_chosen = true;
                break;
            case "Replay game":
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MazeReplayer replayer = new MazeReplayer();
                        replayer.open();
                    }
                });
                t.start();
                break;
        }
    }
}
