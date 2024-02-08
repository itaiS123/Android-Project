package il.co.shivhit.model;

import android.graphics.Color;

import java.util.List;

public class Colors {
    private List<Color> colors;
    public Colors() {}

    public Colors(List<Color> colors) {
        this.colors = colors;
    }

    public List<Color> getColors() {
        return colors;
    }

    public void setColors(List<Color> colors) {
        this.colors = colors;
    }
}
