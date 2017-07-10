package de.htw.sebastiankapunkt.kipfub.representation;

import de.htw.sebastiankapunkt.kipfub.model.ScaledField;
import javafx.application.Application;

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
}
