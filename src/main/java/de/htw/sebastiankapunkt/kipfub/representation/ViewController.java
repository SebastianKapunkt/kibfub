package de.htw.sebastiankapunkt.kipfub.representation;

import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import de.htw.sebastiankapunkt.kipfub.pathfinding.Node;
import javafx.application.Application;

import java.util.LinkedList;

public class ViewController {

    private App app;

    public ViewController() {
        new Thread(() -> Application.launch(App.class)).start();
        app = App.waitForStart();
        app.drawGrid();
    }

    public void drawScaledField(ScaledField field) {
        if (app != null) {
            app.drawScaledField(field);
        }
    }

    public ScaledField applyColorChange(ScaledField scaledField) {
        if (app != null) {
            app.applyChange(scaledField);
        }
        return scaledField;
    }

    public void drawPath(LinkedList<Node> nodes) {
        System.out.print(nodes.size());
        System.out.print("I AM OUT");
        System.out.print("I AM OUT");
        System.out.print("I AM OUT");
        System.out.print("I AM OUT");
        System.out.print("I AM OUT");
        app.drawPath(nodes);
    }

    public void drawNode(Node node) {
        app.drawNode(node);
    }
}
