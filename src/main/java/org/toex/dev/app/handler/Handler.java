package org.toex.dev.app.handler;

import org.toex.dev.app.Application;
import org.toex.dev.data_structures.graph.Graph;
import org.toex.dev.app.gfx.utils.Camera;
import org.toex.dev.app.gfx.elements.Display;

public class Handler {

    Application app;
    public Handler(Application app) {
        this.app = app;
    }

    public Application getApp() {
        return app;
    }

    public Graph getGraph(){
        return app.getGraph();
    }

    public Display getDisplay() {
        return app.getDisplay();
    }

    public Camera getCamera() {
        return app.getCamera();
    }
}
