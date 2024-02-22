package il.co.shivhit.model;

public class Outfit extends BaseEntity {
    private Cloths cloths;
    private String nameOfOutfit;
    private String description;
    public Outfit() {}

    public Outfit(Cloths cloths, String nameOfOutfit, String description) {
        this.cloths = cloths;
        this.nameOfOutfit = nameOfOutfit;
        this.description = description;
    }
    public Cloths getCloths() {
        return cloths;
    }

    public void setCloths(Cloths cloths) {
        this.cloths = cloths;
    }

    public String getNameOfOutfit() {
        return nameOfOutfit;
    }

    public void setNameOfOutfit(String nameOfOutfit) {
        this.nameOfOutfit = nameOfOutfit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
