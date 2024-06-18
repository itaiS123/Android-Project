package il.co.shivhit.model;

import java.io.Serializable;

public class Cloth extends BaseEntity implements Serializable {
    private String category;
    private String color;
    private String image;
    private String userIdfs;

    // בנאי 1
    public Cloth() {}

    // בנאי 2
    public Cloth(String category, String color, String image, String userIdfs) {
        this.category = category;
        this.color = color;
        this.image = image;
        this.userIdfs = userIdfs;
    }

    // פעולה לקבלת קטגוריה
    public String getCategory() {
        return category;
    }
    // פעולה לשינוי הקטגוריה לפי ערך מוכנס
    public void setCategory(String category) {
        this.category = category;
    }
    // פעולה לקבלת צבע
    public String getColor() {
        return color;
    }
    // פעולה לשינוי הצבע לפי ערך מוכנס
    public void setColor(String color) {
        this.color = color;
    }

    // פעולה לקבלת תמונה
    public String getImage() {
        return image;
    }
    // פעולה לשינוי התמונה לפי ערך מוכנס
    public void setImage(String image) {
        this.image = image;
    }

    public String getUserIdfs() {
        return userIdfs;
    }

    public void setUserIdfs(String userIdfs) {
        this.userIdfs = userIdfs;
    }
}
