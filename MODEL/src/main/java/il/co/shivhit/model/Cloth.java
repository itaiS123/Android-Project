package il.co.shivhit.model;

public class Cloth {
    private String categoryId;
    private String colorId;
    private String image;
    public Cloth() {}

    public Cloth(String categoryId, String colorId, String image) {
        this.categoryId = categoryId;
        this.colorId = colorId;
        this.image = image;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getColorId() {
        return colorId;
    }

    public void setColorId(String colorId) {
        this.colorId = colorId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
