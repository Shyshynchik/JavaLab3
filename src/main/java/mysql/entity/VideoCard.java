package mysql.entity;

public class VideoCard {

    private Integer id;
    private String name;

    public VideoCard(){

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
        return "Video card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
