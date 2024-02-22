package il.co.shivhit.model;

public class Cloth extends BaseEntity {
    private String category;
    private  String color;
    private String image;
    public Cloth() {}

    public Cloth(String category, String color, String image) {
        this.category = category;
        this.color = color;
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
