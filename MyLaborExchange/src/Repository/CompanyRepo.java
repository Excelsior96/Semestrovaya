package Repository;

import Entities.Company;
import Exceptions.CompanyException;
import Utilities.DBService;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Daniel Shchepetov on 13.12.2015.
 */
public class CompanyRepo {

    public static void deleteById(int id) {
        String insert = "DELETE FROM Company WHERE id = ?";

        try {
            PreparedStatement p = DBService.connect().prepareStatement(insert);
            p.setInt(1, id);
            p.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addFull(Company comp) throws CompanyException {

        check(comp);

        String insert = "INSERT INTO Company VALUES (?,?,?,0,0)";
        Connection con = DBService.connect();

        try {
            CallableStatement st = con.prepareCall(insert);
            st.setString(1, comp.getName());
            st.setString(2, comp.getAdres());
            st.setString(3, comp.getPhone());
            st.execute();
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }


    public static Company getById(int id) throws CompanyException {

        String insert = "SELECT name,adres, phone, col FROM Company WHERE archive=0 AND id = ?";
        Connection con = DBService.connect();
        Company comp = null;
        try {
            CallableStatement st = con.prepareCall(insert);
            st.setString(1, String.valueOf(id));
            ResultSet set = st.executeQuery();

            while (set.next()) {
                comp = new Company(id,
                        set.getString(1),
                        set.getString(2),
                        set.getString(3),
                        set.getInt(4));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return comp;
    }

    public static ArrayList<Company> getConflicts(String name) {

        ArrayList<Company> list = new ArrayList<Company>();
        Connection con = DBService.connect();
        String insert = "SELECT * FROM Company WHERE name = ?";
        try {
            CallableStatement st = con.prepareCall(insert);
            st.setString(1, name);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                String archive = "Нет";
                if (set.getByte(6)==1){
                    archive = "Да";
                }
                list.add(new Company(
                        set.getInt(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4),
                        set.getInt(5),
                        archive
                ));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public static String[][] getDistTable(ArrayList<Company> list) {


        String[][] data = new String[list.size()][6];
        int i = 0;
        for (Company comp: list) {
            data[i][0] = String.valueOf(comp.getId());
            data[i][1] =comp.getName();
            data[i][2] = comp.getAdres();
            data[i][3] = comp.getPhone();
            data[i][4] = String.valueOf(comp.getCol());
            data[i][5] = comp.getArchive();
            i++;
        }
        return data;
    }

    private static void check(Company comp) throws CompanyException {

        if (comp == null) {
            throw new NullPointerException("Не бывает фирм без данных");
        }
        if (comp.getName() == null || "".equals(comp.getName())) {
            throw new CompanyException("Поле Название не заполнено");
        }
        if (comp.getAdres() == null || "".equals(comp.getAdres())) {
            throw new CompanyException("Поле Адрес не заполнено");
        }
        if (comp.getPhone() == null || "".equals(comp.getPhone())) {
            throw new CompanyException("Поле Телефон не заполнено");
        }

    }

    public static ArrayList<Company> getAll() {
        ArrayList<Company> list = new ArrayList<Company>();
        Connection con = DBService.connect();
        String insert = "SELECT id,name, adres, phone, col FROM Company";
        try {
            CallableStatement st = con.prepareCall(insert);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                list.add(new Company(
                        set.getInt(1),
                        set.getString(2),
                        set.getString(3),
                        set.getString(4),
                        set.getInt(5)));


            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static String[][] getTable(ArrayList<Company> list) {


        String[][] data = new String[list.size()][5];
        int i = 0;
        for (Company comp : list) {
            data[i][0] = String.valueOf(comp.getId());
            data[i][1] = comp.getName();
            data[i][2] = comp.getAdres();
            data[i][3] = comp.getPhone();
            data[i][4] = String.valueOf(comp.getCol());

            i++;
        }
        return data;
    }

    public static boolean unempDist(String name) {
        String insert = "SELECT * FROM Company WHERE name = ?";

        try {
            PreparedStatement p = DBService.connect().prepareStatement(insert);
            p.setString(1, name);
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

    public static void intValidator(String age) throws CompanyException {
        final String PATTERN = "^[0-9][0-9]*$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(age);
        if (!matcher.matches()) {
            throw new CompanyException("Поле заполнено некорректно или не заполнено. Используйте существующие числовые значения");
        }

    }
}
