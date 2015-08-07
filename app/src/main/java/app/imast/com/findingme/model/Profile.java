package app.imast.com.findingme.model;

/**
 * Created by aoki on 07/08/2015.
 */
public class Profile {

    private int id;
    private String name;
    private String lastname;
    private String adress;
    private String sex;
    private int user_id;
    private int district_id;

    public Profile(int id, String name, String lastname, String adress, String sex, int user_id, int district_id) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.adress = adress;
        this.sex = sex;
        this.user_id = user_id;
        this.district_id = district_id;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }
}
