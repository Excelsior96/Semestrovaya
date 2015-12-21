package Entities;

/**
 * Created by Daniel Shchepetov on 13.12.2015.
 */
public class Company {
    private int id;
    private String name;
    private String adres;
    private String phone;
    private int col;
    private String archive;

    public Company(String name, String adres, String phone) {
        this.name = name;
        this.adres = adres;
        this.phone = phone;
    }

    public Company(int id, String name, String adres, String phone, int col) {
        this.name = name;
        this.adres = adres;
        this.phone = phone;
        this.id = id;
        this.col = col;
    }

    public Company(int id, String name, String adres, String phone, int col, String archive) {
        this.name = name;
        this.adres = adres;
        this.phone = phone;
        this.id = id;
        this.col = col;
        this.archive = archive;
    }

    public Company(int id, String nam, String addr, String ph) {
        this.id = id;
        this.name = nam;
        this.adres = addr;
        this.phone = ph;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public String getArchive() {
        return archive;
    }

    public void setArchive(String archive) {
        this.archive = archive;
    }
}
