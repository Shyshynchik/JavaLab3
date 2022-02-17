package mysql.entity;

public class Cpu {
    private Integer id;
    private String name;

    public Cpu(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Cpu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
