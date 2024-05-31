package il.co.shivhit.model;

import java.io.Serializable;

public class BaseEntity implements Serializable {
    protected String idfs;
    public String getIdfs() {
        return idfs;
    }
    public void setIdfs(String idfs) {
        this.idfs = idfs;
    }
}
