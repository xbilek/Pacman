package tool.view;

import tool.common.CommonMazeObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GhostView implements ComponentView {
    private final CommonMazeObject model;
    private final FieldView parent;

    public GhostView(FieldView parent, CommonMazeObject m) {
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
            // Načtení obrázku ducha z disku
            image = ImageIO.read(new File("data/img/ghost.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Vykreslení obrázku ducha na plátno
        g2.drawImage(image, (int) x, (int) y, (int) diameter, (int) diameter, null);

        // počítadlo hitů
        g2.setColor(Color.black);
        g2.setFont(new Font("Serif", 1, 20));
        g2.drawString("" + this.model.getLives() + "", (int)(x + diameter) / 2, (int)(y + diameter + 10.0) / 2 + 5);
    }
}
