package device;

import java.io.Serializable;

public interface Device extends Serializable {

    void setBrand(String brand);
    void setDiagonal(String diagonal);
    void setRam(String ram);

    String getBrand();
    String getDiagonal();
    String getRam();

    String getType();
    void setData(String[] strings);
}
