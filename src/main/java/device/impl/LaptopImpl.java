package device.impl;

import device.Laptop;

public class LaptopImpl extends Laptop{

    @Override
    public String getCpu() {
        return cpu;
    }

    @Override
    public String getVideoCard() {
        return videoCard;
    }

    @Override
    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    @Override
    public void setVideoCard(String videoCard) {
        this.videoCard = videoCard;
    }

    @Override
    public String toString() {
        return super.toString() + "," + cpu + "," + videoCard;
    }

    @Override
    public String getType() {
        return "laptop";
    }

    @Override
    public void setData(String[] strings) {
        super.setData(strings);
        cpu = strings[4];
        videoCard = strings[5];
    }
}
