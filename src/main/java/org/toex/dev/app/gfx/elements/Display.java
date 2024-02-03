package org.toex.dev.app.gfx.elements;

import org.toex.dev.app.handler.Handler;
import org.toex.dev.data_structures.graph.Graph;
import org.toex.dev.data_structures.graph.Vertex;
import org.toex.dev.data_structures.operations.pathfinding.dijkstra.Dijkstra;
import org.toex.dev.data_structures.operations.pathfinding.dijkstra.Dijkstra2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class Display extends JFrame{

    private Handler handler;
    private JPanel contentPane;
    private GraphPanel graphPanel;
    private JScrollPane scrollPane;
    private JList vertexList;
    private JMenuBar menuBar;
    private JMenu mFile;
    private JMenuItem miOpen, miExit;
    private JMenu mTest;
    private JMenuItem miRunDijkstra, miRunModifiedDijkstra;
    private JMenu mView;
    private JMenuItem miCenterGraph;
    private JMenu mAbout;
    private JMenuItem miAbout;

    public Display(Handler handler) {
        super();
        this.handler = handler;
        setMinimumSize(new Dimension(640, 480));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPane);
        setVisible(true);
        pack();
    }

    private void createUIComponents() {
        graphPanel = new GraphPanel(handler);
        initMenuBar();
        initList();
    }
    private void initMenuBar() {
        menuBar = new JMenuBar();
        mFile = new JMenu("File");
        miOpen = new JMenuItem("Open");
        Component parent = this;
        miOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFileChooser fc = new JFileChooser();
                if (e.getSource() == miOpen) {
                    int returnVal = fc.showOpenDialog(parent);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        handler.getApp().setGraph(Graph.createGraphfromXML(fc.getSelectedFile().getPath(), handler));
                    }
                }
            }
        });
        mFile.add(miOpen);
        miExit = new JMenuItem("Exit");
        mFile.add(miExit);
        menuBar.add(mFile);
        mTest = new JMenu("Test");
        miRunDijkstra = new JMenuItem("Run dijkstra algorithm");
        miRunDijkstra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputFrame inputFrame = new InputFrame("Dijkstra");
                inputFrame.addParam("Source");
                inputFrame.addParam("Target");
                inputFrame.addActionLister(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handler.getGraph().resetMarks();
                        Vertex source = handler.getGraph().getVertexByID(inputFrame.getParamValue("Source"));
                        Vertex target = handler.getGraph().getVertexByID(inputFrame.getParamValue("Target"));
                        ArrayList<Vertex> sp = Dijkstra.shortestPath(source, target);
                        System.out.println(sp);
                    }
                });
            }
        });
        mTest.add(miRunDijkstra);
        miRunModifiedDijkstra = new JMenuItem("Run modified dijkstra algorithm");
        miRunModifiedDijkstra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                InputFrame inputFrame = new InputFrame("Modified Dijkstra");
                inputFrame.addParam("Source");
                inputFrame.addParam("Target");
                inputFrame.addActionLister(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        handler.getGraph().resetMarks();
                        Vertex source = handler.getGraph().getVertexByID(inputFrame.getParamValue("Source"));
                        Vertex target = handler.getGraph().getVertexByID(inputFrame.getParamValue("Target"));
                        ArrayList<Vertex> sp = Dijkstra2.shortestPath(source, target);
                        System.out.println(sp);
                    }
                });
            }
        });
        mTest.add(miRunModifiedDijkstra);
        menuBar.add(mTest);
        mView = new JMenu("View");
        miCenterGraph = new JMenuItem("Center the Graph");
        mView.add(miCenterGraph);
        miCenterGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handler.getCamera().center();
            }
        });
        menuBar.add(mView);
        mAbout = new JMenu("About");
        miAbout = new JMenuItem("About");
        mAbout.add(miAbout);
        menuBar.add(mAbout);
        setJMenuBar(menuBar);
    }

    public void initList() {
        scrollPane = new JScrollPane();
        DefaultListModel model = new DefaultListModel<>();
        vertexList = new JList<>(model);
        Iterator<Vertex> i = handler.getGraph().getVertexGroup().iterator();
        while(i.hasNext()) {
            Vertex v = i.next();
            model.addElement(v.toString());
        }
        scrollPane.setViewportView(vertexList);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        vertexList.setLayoutOrientation(JList.VERTICAL);
    }

    public GraphPanel getGraphPanel() {
        return graphPanel;
    }
}
