package il.co.shivhit.model;

import java.io.Serializable;

public class Color extends BaseEntity implements Serializable {
    private String name;

    // בנאי 1
    public Color() {}
    // בנאי 2
    public Color(String name) {
        this.name = name;
    }
    // פעולה לקבלת צבע
    public String getName() {
        return name;
    }
    // פעולה לשינוי הצבע לפי ערך מוכנס
    public void setName(String name) {
        this.name = name;
    }
}
