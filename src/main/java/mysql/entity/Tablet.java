package mysql.entity;

public class Tablet {

    private Integer id;
    private Brand brand;
    private Integer diagonal;
    private Integer ram;
    private Os os;
    private Integer memory;

    public Tablet() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBrand() {
        return brand.getId();
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Integer getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(Integer diagonal) {
        this.diagonal = diagonal;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getOs() {
        return os.getId();
    }

    public void setOs(Os os) {
        this.os = os;
    }

    public Integer getMemory() {
        return memory;
    }

    public void setMemory(Integer memory) {
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "Планшет {" +
                "id=" + id +
                ", Производитель=" + brand.getName() +
                ", Диагональ=" + diagonal +
                ", ОЗУ=" + ram +
                ", Операционная система=" + os.getName() +
                ", Память=" + memory +
                "}\n";
    }

    public String[] convert() {
        return new String[]{"tablet",brand.getName(), diagonal.toString(), ram.toString(), os.getName(), memory.toString()};
    }
}
