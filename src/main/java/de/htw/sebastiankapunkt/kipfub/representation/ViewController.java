package de.htw.sebastiankapunkt.kipfub.representation;

import javafx.application.Application;

public class ViewController {

    private App app;

    public ViewController(int myPlayerNumber) {
        if (myPlayerNumber == 1) {
            new Thread(() -> Application.launch(App.class)).start();
            app = App.waitForStart();
            app.drawGrid();
        }
    }
}
