package Repository;

import Entities.Unemployed;
import Exceptions.UnemployedException;
import Utilities.DBService;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Daniel Shchepetov on 07.12.2015.
 */
public class UnemployedRepo {
    public static void addFull(Unemployed unemp) throws UnemployedException {

        check(unemp);

        String insert = "{CALL addFull(?,?,?,?,?,?,?,?,?,?,?,?)}";
        Connection con = DBService.connect();

        try {
            CallableStatement st = con.prepareCall(insert);
            st.setString(1, unemp.getName());
            st.setInt(2, unemp.getAge());
            st.setString(3, unemp.getSp());
            st.setString(4, unemp.getProf());
            st.setString(5, unemp.getStud());
            st.setString(6, unemp.getLastWork());
            st.setString(7, unemp.getLastPos());
            st.setString(8, unemp.getDismiss());
            st.setString(9, unemp.getHome());
            st.setString(10, unemp.getAddress());
            st.setString(11, unemp.getPhone());
            st.setString(12, unemp.getSex());
            st.execute();
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    public static ArrayList<Unemployed> getAll() {
        ArrayList<Unemployed> list = new ArrayList<Unemployed>();
        Connection con = DBService.connect();
        String insert = "{CALL getAll}";
        try {
            CallableStatement st = con.prepareCall(insert);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                list.add(new Unemployed(
                        set.getInt(1),
                        set.getString(2),
                        set.getInt(3),
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


    public static ArrayList<Unemployed> getAllArchive() {
        ArrayList<Unemployed> list = new ArrayList<Unemployed>();
        Connection con = DBService.connect();
        String insert = "SELECT u.id, u.fio, u.age, u.sex, u.adres, u.phone, u.prof, st.stud \n" +
                "FROM Unemployed AS u, Stud AS st\n" +
                "WHERE st.id = u.stud AND u.archive = 1";
        try {
            CallableStatement st = con.prepareCall(insert);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                list.add(new Unemployed(
                        set.getInt(1),
                        set.getString(2),
                        set.getInt(3),
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


    public static String[][] getTable(ArrayList<Unemployed> list) {


        String[][] data = new String[list.size()][8];
        int i = 0;
        for (Unemployed unemp : list) {
            data[i][0] = String.valueOf(unemp.getId());
            data[i][1] = unemp.getName();
            data[i][2] = String.valueOf(unemp.getAge());
            data[i][3] = unemp.getSex();
            data[i][4] = unemp.getAddress();
            data[i][5] = unemp.getPhone();
            data[i][6] = unemp.getStud();
            data[i][7] = unemp.getProf();
            i++;
        }
        return data;
    }

    public static String[][] getDistTable(ArrayList<Unemployed> list) {


        String[][] data = new String[list.size()][9];
        int i = 0;
        for (Unemployed unemp : list) {
            data[i][0] = String.valueOf(unemp.getId());
            data[i][1] = unemp.getName();
            data[i][2] = String.valueOf(unemp.getAge());
            data[i][3] = unemp.getSex();
            data[i][4] = unemp.getAddress();
            data[i][5] = unemp.getPhone();
            data[i][6] = unemp.getStud();
            data[i][7] = unemp.getProf();
            data[i][8] = unemp.getArchiveN();
            i++;
        }
        return data;
    }

    public static Unemployed getById(int id) throws UnemployedException {

        String insert = "{CALL getById(?)}";
        Connection con = DBService.connect();
        Unemployed unemp = null;
        try {
            CallableStatement st = con.prepareCall(insert);
            st.setString(1, String.valueOf(id));

            ResultSet set = st.executeQuery();

            while (set.next()) {
                unemp = new Unemployed(id,
                        set.getString(1),
                        set.getInt(2),
                        set.getString(3),
                        set.getString(4),
                        set.getString(5),
                        set.getString(6),
                        set.getString(7),
                        set.getString(8),
                        set.getString(9),
                        set.getString(10),
                        set.getString(11),
                        set.getInt(12),
                        set.getString(13)
                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unemp;
    }


    public static Unemployed getArcById(int id) throws UnemployedException {

        String insert = "{CALL getArcById(?)}";
        Connection con = DBService.connect();
        Unemployed unemp = null;
        try {
            CallableStatement st = con.prepareCall(insert);
            st.setInt(1, id);
            st.execute();
            ResultSet set = st.executeQuery();

            while (set.next()) {
                unemp = new Unemployed(id,
                        set.getString(1),
                        set.getInt(2),
                        set.getString(3),
                        set.getString(4),
                        set.getString(5),
                        set.getString(6),
                        set.getString(7),
                        set.getString(8),
                        set.getString(9),
                        set.getString(10),
                        set.getString(11),
                        set.getInt(12),
                        set.getString(13),
                        set.getString(14),
                        set.getString(15)
                );
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return unemp;
    }


    public static ArrayList<Unemployed> getByParam(String param, String value) {

        ArrayList<Unemployed> list = new ArrayList<Unemployed>();
        Connection con = DBService.connect();
        String insert = "{CALL getUnempByParam(?,?)}";
        try {
            CallableStatement st = con.prepareCall(insert);
            st.setString(1, param);
            st.setString(2, value);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                list.add(new Unemployed(
                        set.getInt(1),
                        set.getString(2),
                        set.getInt(3),
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

    public static ArrayList<Unemployed> getConflicts(String name, int age, String prof) {

        ArrayList<Unemployed> list = new ArrayList<Unemployed>();
        Connection con = DBService.connect();
        String insert = "{CALL getConflicts(?,?,?)}";
        try {
            CallableStatement st = con.prepareCall(insert);
            st.setString(1, name);
            st.setInt(2, age);
            st.setString(3, prof);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                String archive = "Нет";
                if (set.getByte(9) == 1) {
                    archive = "Да";
                }
                list.add(new Unemployed(
                        set.getInt(1),
                        set.getString(2),
                        set.getInt(3),
                        set.getString(4),
                        set.getString(5),
                        set.getString(6),
                        set.getString(7),
                        set.getString(8),
                        archive
                ));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void deleteById(int id) {
        String insert = "IF (SELECT archive FROM Unemployed WHERE id = ?) = 1 BEGIN DELETE FROM Vacancy WHERE id =" +
                " (SELECT archive FROM FIND WHERE u_id  = ?) END;" +
                "DELETE FROM Unemployed WHERE id = ?; ";

        try {
            PreparedStatement p = DBService.connect().prepareStatement(insert);
            p.setInt(1, id);
            p.setInt(2, id);
            p.setInt(3, id);
            p.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void check(Unemployed unemp) throws UnemployedException {

        if (unemp == null) {
            throw new NullPointerException("Не бывает безработных без данных");
        }
        if (unemp.getName() == null || "".equals(unemp.getName())) {
            throw new UnemployedException("Поле ФИО не заполнено");
        }
        if (unemp.getAge() < 1910) {
            throw new UnemployedException("Поле Год рождения не заполнено или заполнено некорректно(Не раньше 1910 года рождения)");
        }
        if (unemp.getAddress() == null || "".equals(unemp.getAddress())) {
            throw new UnemployedException("Поле Адрес не заполнено");
        }
        if (unemp.getPhone() == null || "".equals(unemp.getPhone())) {
            throw new UnemployedException("Поле Телефон не заполнено");
        }

        if (unemp.getProf() == null || "".equals(unemp.getProf())) {
            throw new UnemployedException("Поле Профессия не заполнено");
        }


    }

    public static void intValidator(String age) throws UnemployedException {
        final String PATTERN = "^[0-9][0-9]*$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(age);
        if (!matcher.matches()) {
            throw new UnemployedException("Поле заполнено некорректно или не заполнено. Используйте существующие числовые значения");
        }

    }

    public static void deleteArchive(int id) {
        String insert = "DELETE FROM Unemployed WHERE archive=1 AND id IN (SELECT u_id FROM Find WHERE archive IN" +
                " (SELECT id FROM Vacancy WHERE c_id=?)); UPDATE Vacancy SET archive = 0 WHERE c_id=?";
        try {
            PreparedStatement st = DBService.connect().prepareStatement(insert);
            st.setInt(1, id);
            st.setInt(2, id);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean unempDist(String name, int age, String prof) {
        String insert = "SELECT * FROM Unemployed WHERE fio = ? AND age = ? AND prof = ?";

        try {
            PreparedStatement p = DBService.connect().prepareStatement(insert);
            p.setString(1, name);
            p.setInt(2, age);
            p.setString(3, prof);
            p.execute();
            ResultSet set = p.executeQuery();
            if (!set.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void retrieve(int id) {

        String insert = "UPDATE Unemployed SET archive = 0 WHERE id = ?;\n" +
                "UPDATE Vacancy SET archive = 0 WHERE id =\n" +
                "(SELECT archive FROM Find WHERE u_id = ? AND archive IS  NOT NULL);\n" +
                "UPDATE Find SET archive = NULL WHERE u_id = ?;\n" +
                "DELETE FROM Find WHERE u_id = ?";

        try {
            PreparedStatement p = DBService.connect().prepareStatement(insert);
            p.setInt(1, id);
            p.setInt(2, id);
            p.setInt(3, id);
            p.setInt(4, id);
            p.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}