package org.hxzon.demo.jgraph.switcher;

public class PacketLink {

    private String desc;
    private SwitcherPort port;

    public PacketLink(String desc) {
        setDesc(desc);
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public SwitcherPort getPort() {
        return port;
    }

    public void setPort(SwitcherPort port) {
        this.port = port;
    }

}
