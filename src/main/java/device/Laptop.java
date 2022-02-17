package device;

import device.impl.DeviceImpl;

public abstract class Laptop extends DeviceImpl {

    protected String cpu;
    protected String videoCard;

    public abstract String getCpu();
    public abstract String getVideoCard();

    public abstract void setCpu(String cpu);
    public abstract void setVideoCard(String videoCard);
}
