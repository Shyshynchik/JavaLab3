package mysql.entity;

public class Os{

    protected Integer id;
    protected String name;

    public Os(){

    }

    public Os(String name) {
        this.name = name;
    }

    public Os(Integer id, String name) {
        this.id = id;
        this.name = name;
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
        return "Os{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
