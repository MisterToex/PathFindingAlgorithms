package org.toex.dev.app;

import org.toex.dev.app.handler.Handler;
import org.toex.dev.data_structures.graph.Graph;
import org.toex.dev.app.gfx.utils.Camera;
import org.toex.dev.app.gfx.elements.Display;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Application implements Runnable {
    private Thread thread;
    private boolean running = false;

    private Handler handler;
    private Display display;
    private Camera camera;
    private Graph graph;


    public Application() {

    }
    private void init() {
        handler = new Handler(this);
        if(graph == null)
            openGraph(null);

        camera = new Camera(handler);
        display = new Display(handler);
        camera.setMargins(32, 32, 32, 32);
        camera.center();
    }

    public void openGraph(Component parent) {
        final JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(parent);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            graph = Graph.createGraphfromXML(fc.getSelectedFile().getPath(), handler);
        } else if(returnVal == JFileChooser.CANCEL_OPTION) {
            System.exit(0);
        }
    }

    public void run() {
        init();
        long now;
        long last = System.nanoTime();;
        double delta = 0;
        double tps = 2;

        while (running){
            now = System.nanoTime();
            delta += (now - last) * tps / 1000000000;
            last = now;
            if(delta >= 1) {
                System.out.println("tps update with delta of: " + delta);
                update();
                display.getGraphPanel().repaint();
                delta -= 1;
            }
        }
        stop();
    }

    public void update() {
    }

    public void render(Graphics2D g2d){
        graph.render(g2d);
    }

    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Display getDisplay() {
        return display;
    }

    public Camera getCamera() {
        return camera;
    }

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph){
        this.graph = graph;
    }

}
