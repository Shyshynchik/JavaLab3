package mysql.entity;

public class Laptop {

    private Integer id;
    private Brand brand;
    private Integer diagonal;
    private Integer ram;
    private Cpu cpu;
    private VideoCard videoCard;

    public Laptop() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Brand getBrand() {
        return brand;
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

    public Cpu getCpu() {
        return cpu;
    }

    public void setCpu(Cpu cpu) {
        this.cpu = cpu;
    }

    public VideoCard getVideoCard() {
        return videoCard;
    }

    public void setVideoCard(VideoCard videoCard) {
        this.videoCard = videoCard;
    }

    @Override
    public String toString() {
        return "Ноутбук {" +
                "id=" + id +
                ", Производитель=" + brand.getName() +
                ", Диагональ=" + diagonal +
                ", ОЗУ=" + ram +
                ", Процессор=" + cpu.getName() +
                ", Видеокарта=" + videoCard.getName() +
                "}\n";
    }

    public String[] convert() {
        return new String[]{"laptop",brand.getName(), diagonal.toString(), ram.toString(), cpu.getName(), videoCard.getName()};
    }
}
