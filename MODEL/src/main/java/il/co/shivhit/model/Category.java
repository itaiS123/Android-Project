package il.co.shivhit.model;

import java.io.Serializable;

public class Category extends BaseEntity implements Serializable {
    private String nameOfCategory;

    //  בנאי 1
    public Category() {}

    // בנאי 2
    public Category(String nameOfCategory) {
        this.nameOfCategory = nameOfCategory;
    }

    // קבלת שם הקטגוריה
    public String getNameOfCategory() {
        return nameOfCategory;
    }

    // שינוי שם הקטגוריה לפי בחירה
    public void setNameOfCategory(String nameOfCategory) {
        this.nameOfCategory = nameOfCategory;
    }
}
