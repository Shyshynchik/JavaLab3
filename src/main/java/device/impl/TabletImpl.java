package device.impl;

import device.Tablet;

public class TabletImpl extends Tablet {

    @Override
    public void setOs(String os) {
        this.os = os;
    }

    @Override
    public void setMemory(String memory) {
        this.memory = memory;
    }

    @Override
    public String getOs() {
        return os;
    }

    @Override
    public String getMemory() {
        return memory;
    }

    @Override
    public String toString() {
        return super.toString() + "," + os + "," + memory;
    }

    @Override
    public String getType() {
        return "tablet";
    }

    @Override
    public void setData(String[] strings) {
        super.setData(strings);
        os = strings[4];
        memory = strings[5];
    }
}
