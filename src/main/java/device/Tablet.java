package device;

import device.impl.DeviceImpl;

public abstract class Tablet extends DeviceImpl {

    protected String os;
    protected String memory;

    public abstract void setOs(String os);
    public abstract void setMemory(String memory);

    public abstract String getOs();
    public abstract String getMemory();
}
