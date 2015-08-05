package app.imast.com.findingme.model;

/**
 * Created by XKenokiX on 04/08/2015.
 */
public class PetType {

    private int id;
    private String name;

    public PetType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
