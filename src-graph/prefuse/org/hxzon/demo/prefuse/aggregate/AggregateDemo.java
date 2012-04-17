/**
 * Copyright (c) 2004-2006 Regents of the University of California.
 * See "license-prefuse.txt" for licensing terms.
 */
package org.hxzon.demo.prefuse.aggregate;

import java.util.Iterator;

import javax.swing.JFrame;

import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.activity.Activity;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.data.Node;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.PolygonRenderer;
import prefuse.render.Renderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.visual.AggregateItem;
import prefuse.visual.AggregateTable;
import prefuse.visual.VisualGraph;
import prefuse.visual.VisualItem;

/**
 * Demo application showcasing the use of AggregateItems to
 * visualize groupings of nodes with in a graph visualization.
 * 
 * This class uses the AggregateLayout class to compute bounding
 * polygons for each aggregate and the AggregateDragControl to
 * enable drags of both nodes and node aggregates.
 * 
 * @author <a href="http://jheer.org">jeffrey heer</a>
 */
public class AggregateDemo {

    public static final String GroupName_graph = "graph";
    public static final String GroupName_node = "graph.nodes";
    public static final String GroupName_edge = "graph.edges";
    public static final String GroupName_aggregate = "aggregates";

    public static Display aggregateDemo() {
        Visualization visualization = new Visualization();
        // initialize display and data
        Display display = new Display(visualization);
        initDataGroups(visualization);

        // set up the renderers
        // draw the nodes as basic shapes
        Renderer nodeRenderer = new ShapeRenderer(20);
        // draw aggregates as polygons with curved edges
        Renderer polyRenderer = new PolygonRenderer(Constants.POLY_TYPE_CURVE);
        ((PolygonRenderer) polyRenderer).setCurveSlack(0.15f);

        DefaultRendererFactory rendererFactory = new DefaultRendererFactory();
        rendererFactory.setDefaultRenderer(nodeRenderer);
        rendererFactory.add("ingroup('aggregates')", polyRenderer);
        visualization.setRendererFactory(rendererFactory);

        // set up the visual operators
        // first set up all the color actions
        ColorAction nodeStrokeColor = new ColorAction(GroupName_node, VisualItem.STROKECOLOR);
        nodeStrokeColor.setDefaultColor(ColorLib.gray(100));
        nodeStrokeColor.add("_hover", ColorLib.gray(50));

        ColorAction nodeFillColor = new ColorAction(GroupName_node, VisualItem.FILLCOLOR);
        nodeFillColor.setDefaultColor(ColorLib.gray(255));
        nodeFillColor.add("_hover", ColorLib.gray(200));

        ColorAction edgeStrokeColor = new ColorAction(GroupName_edge, VisualItem.STROKECOLOR);
        edgeStrokeColor.setDefaultColor(ColorLib.gray(100));

        ColorAction aggrStrokeColor = new ColorAction(GroupName_aggregate, VisualItem.STROKECOLOR);
        aggrStrokeColor.setDefaultColor(ColorLib.gray(200));
        aggrStrokeColor.add("_hover", ColorLib.rgb(255, 100, 100));

        int[] palette = new int[] { ColorLib.rgba(255, 200, 200, 150), ColorLib.rgba(200, 255, 200, 150), ColorLib.rgba(200, 200, 255, 150) };
        ColorAction aggrFillColor = new DataColorAction(GroupName_aggregate, "id", Constants.NOMINAL, VisualItem.FILLCOLOR, palette);

        // bundle the color actions
        ActionList colors = new ActionList();
        colors.add(nodeStrokeColor);
        colors.add(nodeFillColor);
        colors.add(edgeStrokeColor);
        colors.add(aggrStrokeColor);
        colors.add(aggrFillColor);

        // now create the main layout routine
        ActionList layout = new ActionList(Activity.INFINITY);
        layout.add(colors);
        layout.add(new ForceDirectedLayout(GroupName_graph, true));
        layout.add(new AggregateLayout(GroupName_aggregate));
        layout.add(new RepaintAction());
        visualization.putAction("layout", layout);

        // set up the display
        display.setSize(500, 500);
        display.pan(250, 250);
        display.setHighQuality(true);
        display.addControlListener(new AggregateDragControl());
        display.addControlListener(new ZoomControl());
        display.addControlListener(new PanControl());

        // set things running
        visualization.run("layout");
        return display;
    }

    private static void initDataGroups(Visualization visualization) {
        // create sample graph
        // 9 nodes broken up into 3 interconnected cliques
        Graph graph = new Graph();
        for (int i = 0; i < 3; ++i) {
            Node node1 = graph.addNode();
            Node node2 = graph.addNode();
            Node node3 = graph.addNode();
            graph.addEdge(node1, node2);
            graph.addEdge(node1, node3);
            graph.addEdge(node2, node3);
        }
        graph.addEdge(0, 3);
        graph.addEdge(3, 6);
        graph.addEdge(6, 0);

        // add visual data groups
        VisualGraph visualGraph = visualization.addGraph(GroupName_graph, graph);
        visualization.setInteractive(GroupName_edge, null, false);
        visualization.setValue(GroupName_node, null, VisualItem.SHAPE, new Integer(Constants.SHAPE_ELLIPSE));

        AggregateTable aggregateTable = visualization.addAggregates(GroupName_aggregate);
        aggregateTable.addColumn(VisualItem.POLYGON, float[].class);
        aggregateTable.addColumn("id", int.class);

        // add nodes to aggregates
        // create an aggregate for each 3-clique of nodes
        Iterator nodes = visualGraph.nodes();
        for (int i = 0; i < 3; ++i) {
            AggregateItem aggregateItem = (AggregateItem) aggregateTable.addItem();
            aggregateItem.setInt("id", i);
            for (int j = 0; j < 3; ++j) {
                aggregateItem.addItem((VisualItem) nodes.next());
            }
        }
    }

    public static void main(String[] argv) {
        JFrame frame = new JFrame("prefuse  |  aggregated");
        frame.getContentPane().add(aggregateDemo());
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

} // end of class AggregateDemo

