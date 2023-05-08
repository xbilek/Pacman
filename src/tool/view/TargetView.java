/**
 * Modul reprezentuje vzhled cíle v poli.
 *
 * @author Jakub Kořínek
 * @author Štěpán Bílek
 */

package tool.view;

import tool.common.CommonMazeObject;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TargetView implements ComponentView {
    private CommonMazeObject model;
    private FieldView parent;

    public TargetView(FieldView parent, CommonMazeObject m) {
        this.model = m;
        this.parent = parent;
    }

    /**
     * Funkce vykreslí cíl do mapy.
     * @param g Vykreslovač.
     */
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        Rectangle bounds = this.parent.getBounds();
        double w = bounds.getWidth();
        double h = bounds.getHeight();
        Math.max(h, w);
        double diameter = Math.min(h, w) - 10.0;
        double x = (w - diameter) / 2.0;
        double y = (h - diameter) / 2.0;
        BufferedImage image = null;

        try {
            // Načtení obrázku klíče z disku
            image = ImageIO.read(new File("data/img/target.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Vykreslení obrázku klíče na plátno
        g2.drawImage(image, (int) x, (int) y, (int) diameter, (int) diameter, null);
        //g2.setColor(Color.black);
        //g2.setFont(new Font("Serif", 1, 20));
        //g2.drawString("(" + this.model.getLives() + ")", (int)(x + diameter) / 2, (int)(y + diameter + 10.0) / 2 + 5);
    }
}