package il.co.shivhit.model;

public class Category extends BaseEntity {
    private String nameOfCategory;
    public Category() {}

    public Category(String nameOfCategory) {
        this.nameOfCategory = nameOfCategory;
    }

    public String getNameOfCategory() {
        return nameOfCategory;
    }

    public void setNameOfCategory(String nameOfCategory) {
        this.nameOfCategory = nameOfCategory;
    }
}
