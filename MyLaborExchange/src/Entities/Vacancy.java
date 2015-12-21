package Entities;

/**
 * Created by Daniel Shchepetov on 12.12.2015.
 */
public class Vacancy {
    private int id;
    private int p_id;
    private int c_id;
    private int payment;
    private String cond;
    private String req;
    private String home;
    private int archive;
    private String company;
    private String pos;
    private String name;
    private String phone;
    private String adres;

    public Vacancy(int id, String pos, int c_id, String company, int payment, String cond, String req, String home) {
        this.id = id;
        this.c_id = c_id;
        this.payment = payment;
        this.cond = cond;
        this.req = req;
        this.home = home;
        this.company = company;
        this.pos = pos;
    }

    public Vacancy(int c_id, String pos, int payment, String cond, String req, String home) {

        this.c_id = c_id;
        this.payment = payment;
        this.cond = cond;
        this.req = req;
        this.home = home;
        this.pos = pos;

    }

    public Vacancy(int id, String pos, int c_id, String company) {
        this.c_id = c_id;
        this.id = id;
        this.pos = pos;
        this.company = company;
    }

    public Vacancy(String name, int pay, String cond, String req, String home, String company, String phone, String adres) {
        this.name = name;
        this.payment = pay;
        this.cond = cond;
        this.req = req;
        this.company = company;
        this.home = home;

        this.phone = phone;
        this.adres = adres;
    }

    public Vacancy(String id, String string, int anInt, String string1, String string2, String string3) {
        this.id = Integer.parseInt(id);
        this.pos = string;
        this.payment = anInt;
        this.cond = string1;
        this.req = string2;
        this.home = string3;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getPayment() {
        return payment;
    }

    public void setPayment(int payment) {
        this.payment = payment;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public String getReq() {
        return req;
    }

    public void setReq(String req) {
        this.req = req;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public int getArchive() {
        return archive;
    }

    public void setArchive(int archive) {
        this.archive = archive;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}
