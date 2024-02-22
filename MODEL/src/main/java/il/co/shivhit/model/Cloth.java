package il.co.shivhit.model;

public class Cloth extends BaseEntity{
    private String image;
    public Cloth() {}

    public Cloth(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
