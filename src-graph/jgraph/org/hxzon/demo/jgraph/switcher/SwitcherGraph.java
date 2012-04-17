package org.hxzon.demo.jgraph.switcher;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.mxgraph.model.mxCell;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;

public class SwitcherGraph extends JFrame {

    private static final long serialVersionUID = -2707712944901661771L;
    private PacketLink _packetLink;
    private List<PacketLink> _packetLinks;
    private List<PacketLink> _packetLinks1;
    private List<PacketLink> _packetLinks2;
    private Switcher _switcher;
    private Switcher _centerSwitcher;
    private List<Switcher> _remoteSwitchers;

    public SwitcherGraph() {
        super("Switcher");
        fakeData();
        buildGraph();
        //com.mxgraph.view.mxGraph
        final mxGraph graph = new mxGraph();
        //cell
        graph.setCellsMovable(true);
        graph.setCellsEditable(false);
        graph.setCellsResizable(false);
        graph.setCellsBendable(false);
        //edge
        graph.setEdgeLabelsMovable(false);
        graph.setConnectableEdges(false);
        graph.setAllowDanglingEdges(false);
        graph.setDisconnectOnMove(false);
        graph.setAllowNegativeCoordinates(false);

        Object root = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try {
            int x = 100, y = 100;
            Object startLinkCell = graph.insertVertex(root, null, _packetLink.getDesc(), x, y, 20, 20);
            x += 100;
            Object startSwitcherCell = graph.insertVertex(root, null, _switcher.getDesc(), x, y, 20, 20);
            graph.insertEdge(root, null, "--", startLinkCell, startSwitcherCell);
            x += 100;
            Object centerSwitcherCell = graph.insertVertex(root, null, _centerSwitcher.getDesc(), x, y, 20, 20);
            graph.insertEdge(root, null, "--", startSwitcherCell, centerSwitcherCell);
            x += 100;
            for (Switcher switcher : _remoteSwitchers) {
                Object switcherCell = graph.insertVertex(root, null, switcher.getDesc(), x, y, 20, 20);
                graph.insertEdge(root, null, "--", centerSwitcherCell, switcherCell);
                y += 100;
            }
            y = 100;
            for (PacketLink packetLink : _packetLinks1) {
                graph.insertVertex(root, null, packetLink.getDesc(), x, y, 20, 20);
                y += 100;
            }
            for (PacketLink packetLink : _packetLinks2) {
                graph.insertVertex(root, null, packetLink.getDesc(), x, y, 20, 20);
                y += 100;
            }
        } finally {
            graph.getModel().endUpdate();
        }
        //com.mxgraph.swing.mxGraphComponent
        final mxGraphComponent graphComponent = new mxGraphComponent(graph);
        getContentPane().add(graphComponent);
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

            public void mouseReleased(MouseEvent e) {
                Object cell = graphComponent.getCellAt(e.getX(), e.getY());

                if (cell != null) {
                    System.out.println("cell=" + graph.getLabel(cell));
                }
            }
        });
    }

    private void buildGraph() {
        _packetLinks1 = new ArrayList<PacketLink>();
        _packetLinks2 = new ArrayList<PacketLink>();
        _remoteSwitchers=new ArrayList<Switcher>();
        SwitcherPort switcherPort = _packetLink.getPort();
        _switcher = switcherPort.getSwitcher();
        for (PacketLink packetLink : _packetLinks) {
            Switcher switcher = packetLink.getPort().getSwitcher();
            if (switcher == _switcher) {
                _packetLinks1.add(packetLink);
            } else {
                _packetLinks2.add(packetLink);
                if (!_remoteSwitchers.contains(switcher)) {
                    _remoteSwitchers.add(switcher);
                }
            }
        }
    }

    private void fakeData() {
        _centerSwitcher = new Switcher("中心交换机");
        List<SwitcherPort> ports = new ArrayList<SwitcherPort>();
        for (int i = 0; i < 5; i++) {
            SwitcherPort port = new SwitcherPort(i);
            Switcher switcher = new Switcher(i + "号交换机");
            switcher.setParentPort(port);
            port.setSwitcher(_centerSwitcher);
            _centerSwitcher.addPort(port);
            for (int j = 0; j < 10; j++) {
                SwitcherPort port2 = new SwitcherPort(j);
                switcher.addPort(port2);
                port2.setSwitcher(switcher);
                ports.add(port2);
            }
        }
        int size = ports.size();
        Random random = new Random();
        {
            _packetLink = new PacketLink("packet link");
            SwitcherPort port = ports.get(random.nextInt(size));
            _packetLink.setPort(port);
        }
        _packetLinks = new ArrayList<PacketLink>();
        for (int i = 0; i < 20; i++) {
            PacketLink packetLink = new PacketLink(i + "订阅者");
            SwitcherPort port = ports.get(random.nextInt(size));
            packetLink.setPort(port);
            _packetLinks.add(packetLink);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                SwitcherGraph frame = new SwitcherGraph();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 320);
                frame.setVisible(true);
            }
        });
    }
}