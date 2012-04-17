package org.hxzon.demo.jgraph.switcher;

public class SwitcherPort {
    private int portIndex;
    private Switcher switcher;

    public SwitcherPort(int portIndex) {
        setPortIndex(portIndex);
    }

    public int getPortIndex() {
        return portIndex;
    }

    public void setPortIndex(int portIndex) {
        this.portIndex = portIndex;
    }

    public Switcher getSwitcher() {
        return switcher;
    }

    public void setSwitcher(Switcher switcher) {
        this.switcher = switcher;
    }

}
