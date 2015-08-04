package app.imast.com.findingme.model;

/**
 * Created by aoki on 04/08/2015.
 */
public class Pet {

    private String name;
    private String photo;
    private String info;

    public Pet(String name, String photo, String info) {
        this.name = name;
        this.photo = photo;
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
