/**
 * Modul reprezentuje vzhled klíče v poli.
 *
 * @author Jakub Kořínek
 * @author Štěpán Bílek
 */

package tool.view;

import tool.common.CommonMazeObject;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class KeyView implements ComponentView {
    private final CommonMazeObject model;
    private final FieldView parent;

    public KeyView(FieldView parent, CommonMazeObject m) {
        this.model = m;
        this.parent = parent;
    }

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
            image = ImageIO.read(new File("data/img/key.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Vykreslení obrázku klíče na plátno
        g2.drawImage(image, (int) x, (int) y, (int) diameter, (int) diameter, null);
//        Ellipse2D.Double ellipse = new Ellipse2D.Double(x, y, diameter, diameter);
//        g2.setColor(Color.green);
//        g2.fill(ellipse);
    }
}