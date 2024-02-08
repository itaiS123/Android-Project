package il.co.shivhit.model;

import java.util.List;

public class Outfits {
    private List<Outfit> outfits;
    public Outfits() {}

    public Outfits(List<Outfit> outfits) {
        this.outfits = outfits;
    }

    public List<Outfit> getOutfits() {
        return outfits;
    }

    public void setOutfits(List<Outfit> outfits) {
        this.outfits = outfits;
    }
}
