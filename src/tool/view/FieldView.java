/**
 * Modul reprezentuje vzhled cesty a zdi v poli.
 *
 * @author Jakub Kořínek
 * @author Štěpán Bílek
 */

package tool.view;

import tool.common.CommonField;
import tool.common.CommonMazeObject;
import tool.common.Observable;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Vizuální reprezentace políčka.
 */
public class FieldView extends JPanel implements Observable.Observer {
    private final CommonField model;
    private final List<ComponentView> objects;
    private int changedModel = 0;

    public FieldView(CommonField model) {
        this.model = model;
        this.objects = new ArrayList();
        this.privUpdate();
        model.addObserver(this);
    }

//    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        this.objects.forEach((v) -> {
//            v.paintComponent(g);
//        });
//
//    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < objects.size(); i++) {
            objects.get(i).paintComponent(g);
        }
    }


    private void privUpdate() {
        ComponentView v;
        if (this.model.canMove()) {
            this.setBackground(Color.white);
            if (!this.model.isEmpty()) {
                CommonMazeObject o = this.model.get();
                if (o.isPacman()) {
                    v = new PacmanView(this, this.model.get());
                }
                else if (o.isTarget()) {
                    v = new TargetView(this, this.model.get());
                }
                else if (o.isKey()){
                    v = new KeyView(this, this.model.get());
                }
                else
                    v = new GhostView(this, this.model.get());
                this.objects.add(v);
            } else {
                this.objects.clear();
            }
        } else {
            this.setBackground(Color.lightGray);
        }

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    public final void update(Observable field) {
        ++this.changedModel;
        this.privUpdate();
    }

    public int numberUpdates() {
        return this.changedModel;
    }

    public void clearChanged() {
        this.changedModel = 0;
    }

    public CommonField getField() {
        return this.model;
    }
}
