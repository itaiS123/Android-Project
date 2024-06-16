package il.co.shivhit.model;

import java.io.Serializable;

public class Outfit extends BaseEntity implements Serializable {
    private Cloths cloths;
    private String nameOfOutfit;
    private String description;
    private String userIdfs;

    // בנאי 1
    public Outfit() {}
    // בנאי2
    public Outfit(Cloths cloths, String nameOfOutfit, String description) {
        this.cloths = cloths;
        this.nameOfOutfit = nameOfOutfit;
        this.description = description;
    }
    // פעולה לקבלת רשימת בגדים
    public Cloths getCloths() {
        return cloths;
    }

    // פעולה לשינוי התלבושות לפי ערך מוכנס
    public void setCloths(Cloths cloths) {
        this.cloths = cloths;
    }
    // פעולה לקבלת שם התלבושת
    public String getNameOfOutfit() {
        return nameOfOutfit;
    }

    // פעולה לשינוי שם התלבושת לפי ערך מוכנס
    public void setNameOfOutfit(String nameOfOutfit) {
        this.nameOfOutfit = nameOfOutfit;
    }
    // פעולה לקבלת תיאור התלבושת
    public String getDescription() {
        return description;
    }

    // פעולה לשינוי התיאור לפי ערך מוכנס
    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserIdfs() {
        return userIdfs;
    }

    public void setUserIdfs(String userIdfs) {
        this.userIdfs = userIdfs;
    }
}
