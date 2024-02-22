package il.co.shivhit.model;

public class Color extends BaseEntity {
    private String name;

    public Color() {}
    public Color(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
