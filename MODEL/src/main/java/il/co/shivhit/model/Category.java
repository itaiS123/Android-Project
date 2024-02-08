package il.co.shivhit.model;

public class Category {
    private String id;
    private String nameOfCategory;
    public Category() {}

    public Category(String id, String nameOfCategory) {
        this.id = id;
        this.nameOfCategory = nameOfCategory;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameOfCategory() {
        return nameOfCategory;
    }

    public void setNameOfCategory(String nameOfCategory) {
        this.nameOfCategory = nameOfCategory;
    }
}
