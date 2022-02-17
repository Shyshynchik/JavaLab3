package device.impl;

import device.Device;

public class DeviceImpl implements Device {
    protected String brand;
    protected String diagonal;
    protected String ram;

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public void setDiagonal(String diagonal) {
        this.diagonal = diagonal;
    }

    @Override
    public void setRam(String ram) {
        this.ram = ram;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public String getDiagonal() {
        return diagonal;
    }

    @Override
    public String getRam() {
        return ram;
    }

    @Override
    public String getType() {
        return "device";
    }

    @Override
    public void setData(String[] strings) {
        brand = strings[1];
        diagonal = strings[2];
        ram = strings[3];
    }

    @Override
    public String toString() {
        return getType() + "," + brand + "," + diagonal + "," + ram;
    }
}
