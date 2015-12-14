package Repository;

import Entities.Vacancy;
import Exceptions.VacancyException;
import Utilities.DBService;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Daniel Shchepetov on 12.12.2015.
 */
public class VacancyRepo {

    public static void deleteAll(int uid) {
        String insert = "DELETE FROM Vacancy WHERE c_id = ? AND archive = 0";
        try {

            Connection con = DBService.connect();

            PreparedStatement st = con.prepareStatement(insert);
            st.setInt(1, uid);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void deleteById(String id) throws VacancyException {
        String insert = "DELETE FROM Vacancy WHERE id = ? AND archive = 0";
        try {
            checkID(Integer.parseInt(id));
            Connection con = DBService.connect();
            PreparedStatement st = con.prepareStatement(insert);
            st.setInt(1, Integer.parseInt(id));
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public static ArrayList<Vacancy> getByFindId(String id) {
        ArrayList<Vacancy> list = new ArrayList<Vacancy>();
        Connection con = DBService.connect();
        String insert = "SELECT v.id, p.name, v.c_id, c.name,v.payment, v.cond, v.req, h.home\n" +
                " FROM Vacancy AS v, Company AS c, Pos AS p, homeV AS h \n" +
                "WHERE v.archive=0 AND v.p_id = p.id AND v.home = h.id AND v.c_id = c.id AND p.id = \n" +
                "(SELECT p_id FROM Find WHERE id=?);";
        try {
            CallableStatement st = con.prepareCall(insert);
            st.setInt(1, Integer.parseInt(id));
            ResultSet set = st.executeQuery();
            while (set.next()) {
                list.add(new Vacancy(
                        set.getInt(1),
                        set.getString(2),
                        set.getInt(3),
                        set.getString(4),
                        set.getInt(5),
                        set.getString(6),
                        set.getString(7),
                        set.getString(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public static String getAd(int id) {

        String insert = "SELECT name, ";
        Connection con = DBService.connect();

        return null;
    }


    public static String[][] getTable(ArrayList<Vacancy> list) {
        String[][] data = new String[list.size()][4];
        int i = 0;
        for (Vacancy vac : list) {
            data[i][0] = String.valueOf(vac.getId());
            data[i][1] = vac.getPos();
            data[i][2] = String.valueOf(vac.getC_id());
            data[i][3] = vac.getCompany();
            i++;
        }
        return data;
    }

    public static String[][] getCompTable(ArrayList<Vacancy> list) {
        String[][] data = new String[list.size()][8];
        int i = 0;
        for (Vacancy vac : list) {
            data[i][0] = String.valueOf(vac.getId());
            data[i][1] = vac.getPos();
            data[i][2] = String.valueOf(vac.getC_id());
            data[i][3] = vac.getCompany();
            data[i][4] = String.valueOf(vac.getPayment());
            data[i][5] = vac.getCond();
            data[i][6] = vac.getReq();
            data[i][7] = vac.getHome();
            i++;
        }
        return data;
    }

    public static void intValidator(String age) throws VacancyException {
        final String PATTERN = "^[0-9][0-9]*$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(age);
        if (!matcher.matches()) {
            throw new VacancyException("Поле заполнено некорректно или не заполнено. Используйте существующие числовые значения");
        }

    }

    public static void employ(int vid, int uid, int fid) {
        String insert = "UPDATE Find \n" +
                "SET archive=? WHERE id = ?;" +
                " DELETE FROM Find WHERE u_id = ? AND archive IS NULL\n" +
                "UPDATE Unemployed\n" +
                "SET archive = 1 WHERE id = ?;\n" +
                "UPDATE Vacancy \n" +
                "SET archive = 1 WHERE id = ?;";


        PreparedStatement p;
        try {
            p = DBService.connect().prepareStatement(insert);

            p.setInt(1, vid);
            p.setInt(2, fid);
            p.setInt(3, uid);
            p.setInt(4, uid);
            p.setInt(5, vid);
            p.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addVac(Vacancy vac) throws VacancyException {

        check(vac);
        String insert = "{CALL addVac(?,?,?,?,?,?)}";
        Connection con = DBService.connect();

        try {
            CallableStatement st = con.prepareCall(insert);
            st.setString(1, vac.getPos());
            st.setInt(2, vac.getC_id());
            st.setInt(3, vac.getPayment());
            st.setString(4, vac.getCond());
            st.setString(5, vac.getReq());
            st.setString(6, vac.getHome());
            st.execute();
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }


    private static void check(Vacancy vac) throws VacancyException {

        if (vac == null) {
            throw new NullPointerException("Некорректная вакансия");
        }
        if (vac.getPos() == null || "".equals(vac.getPos())) {
            throw new VacancyException("Поле Должность не заполнено");
        }
        if (vac.getPayment() < 0) {
            throw new VacancyException("Поле Зарплата не заполнено или заполнено некорректно");
        }
        if (vac.getCond() == null || "".equals(vac.getCond())) {
            throw new VacancyException("Поле Условия не заполнено");
        }
        if (vac.getReq() == null || "".equals(vac.getReq())) {
            throw new VacancyException("Поле Требования не заполнено");
        }
    }

    public static ArrayList<Vacancy> getAllById(int id) {
        ArrayList<Vacancy> list = new ArrayList<Vacancy>();
        Connection con = DBService.connect();
        String insert = "SELECT v.id, p.name, v.c_id, c.name FROM Vacancy AS v, Pos AS p, Company AS c" +
                " WHERE v.c_id = ? AND v.p_id = p.id AND c.id = v.c_id AND v.archive=0 ";
        try {
            CallableStatement st = con.prepareCall(insert);
            st.setInt(1, id);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                list.add(new Vacancy(
                        set.getInt(1),
                        set.getString(2),
                        set.getInt(3),
                        set.getString(4)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static ArrayList<Vacancy> getAllAd(int id) {
        ArrayList<Vacancy> list = new ArrayList<Vacancy>();
        Connection con = DBService.connect();
        String insert = "SELECT p.name, v.payment, v.cond, v.req, h.home, c.name, c.phone, c.adres \n" +
                "FROM Vacancy AS v, Pos AS p, Company AS c, HomeV AS h\n" +
                "WHERE v.c_id = ? AND v.p_id = p.id AND c.id = v.c_id AND v.archive=0 AND v.home = h.id  ";
        try {
            PreparedStatement st = con.prepareStatement(insert);
            st.setInt(1, id);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                list.add(new Vacancy(
                        set.getString(1),
                        set.getInt(2),
                        set.getString(3),
                        set.getString(4),
                        set.getString(5),
                        set.getString(6),
                        set.getString(7),
                        set.getString(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public static ArrayList<Vacancy> getAdById(int id, int cid) {
        ArrayList<Vacancy> list = new ArrayList<Vacancy>();
        Connection con = DBService.connect();
        String insert = "SELECT p.name, v.payment, v.cond, v.req, h.home, c.name, c.phone, c.adres \n" +
                "FROM Vacancy AS v, Pos AS p, Company AS c, HomeV AS h\n" +
                "WHERE v.c_id = ? AND v.p_id = p.id AND c.id = v.c_id AND v.archive = 0 AND v.home = h.id AND v.id = ?  ";
        try {
            CallableStatement st = con.prepareCall(insert);
            st.setInt(1, cid);
            st.setInt(2, id);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                list.add(new Vacancy(
                        set.getString(1),
                        set.getInt(2),
                        set.getString(3),
                        set.getString(4),
                        set.getString(5),
                        set.getString(6),
                        set.getString(7),
                        set.getString(8)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void checkID(int id) throws VacancyException {
        try {
            String insert = "SELECT id FROM Vacancy WHERE id = ? AND archive = 0";
            Connection con = DBService.connect();
            PreparedStatement st = con.prepareStatement(insert);
            st.setInt(1, id);
            st.execute();
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

            } else {
                throw new VacancyException("Не найдена вакансия с таким ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void checkAllID(int id) throws VacancyException {
        try {
            String insert = "SELECT id FROM Vacancy WHERE c_id = ? AND archive = 0";
            Connection con = DBService.connect();
            PreparedStatement st = con.prepareStatement(insert);
            st.setInt(1, id);
            st.execute();
            ResultSet rs = st.executeQuery();
            if (rs.next()) {

            } else {
                throw new VacancyException("Вакансии не обнаружены");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
