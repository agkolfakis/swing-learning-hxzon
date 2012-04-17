package org.hxzon.demo.jgraph.switcher;

import java.util.ArrayList;
import java.util.List;

public class Switcher {
    private String desc;
    private List<SwitcherPort> ports;
    private SwitcherPort parentPort;

    public Switcher(String desc) {
        setDesc(desc);
    }

    public void addPort(SwitcherPort port) {
        if (ports == null) {
            ports = new ArrayList<SwitcherPort>();
        }
        ports.add(port);
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<SwitcherPort> getPorts() {
        return ports;
    }

    public void setPorts(List<SwitcherPort> ports) {
        this.ports = ports;
    }

    public SwitcherPort getParentPort() {
        return parentPort;
    }

    public void setParentPort(SwitcherPort parentPort) {
        this.parentPort = parentPort;
    }

}
