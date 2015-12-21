package Repository;

import Entities.Find;
import Entities.Unemployed;
import Exceptions.FindException;
import Utilities.DBService;

import java.sql.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Daniel Shchepetov on 11.12.2015.
 */
public class FindRepo {

    public static void addFind(Find f) throws FindException {
        String insert = "{CALL addFind(?,?)}";
        Connection con = DBService.connect();
        try {
            CallableStatement st = con.prepareCall(insert);
            st.setInt(1, f.getU_id());
            st.setString(2, f.getPos());
            st.execute();


        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Find> getAllById(int id) {
        ArrayList<Find> list = new ArrayList<Find>();
        Connection con = DBService.connect();
        String insert = "SELECT f.id, p.name, f.u_id, u.fio FROM Find AS f, Pos AS p, Unemployed AS u" +
                " WHERE f.u_id = ? AND f.p_id = p.id AND u.id = f.u_id AND f.archive IS NULL";
        try {
            CallableStatement st = con.prepareCall(insert);
            st.setInt(1, id);
            ResultSet set = st.executeQuery();
            while (set.next()) {
                list.add(new Find(
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

    public static String[][] getTable(ArrayList<Find> list) {
        String[][] data = new String[list.size()][4];
        int i = 0;
        for (Find find : list) {
            data[i][0] = String.valueOf(find.getId());
            data[i][1] = find.getPos();
            data[i][2] = String.valueOf(find.getU_id());
            data[i][3] = find.getName();
            i++;
        }
        return data;
    }


    public static void intValidator(String age) throws FindException {
        final String PATTERN = "^[^-][0-9][0-9]*$";
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(age);
        if (!matcher.matches()) {
            throw new FindException("Одно из полей заполнено некорректно или не заполнено. Используйте существующие числовые значения");
        }

    }

    public static void deleteById(String id) {
        String insert = "DELETE FROM Find WHERE id = ? AND archive IS NULL";
        Connection con = DBService.connect();
        try {
            PreparedStatement st = con.prepareStatement(insert);
            st.setInt(1, Integer.parseInt(id));
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<Find> getByVacId(String id) {
        ArrayList<Find> list = new ArrayList<Find>();
        Connection con = DBService.connect();
        String insert = "SELECT f.id, p.name, f.u_id, u.fio FROM Find AS f, Unemployed AS u, Pos AS p " +
                "WHERE f.archive IS NULL AND f.p_id = p.id AND f.u_id = u.id AND p.id = (SELECT p_id FROM Vacancy WHERE id=?)";
        try {
            CallableStatement st = con.prepareCall(insert);
            st.setInt(1, Integer.parseInt(id));
            ResultSet set = st.executeQuery();
            while (set.next()) {
                list.add(new Find(
                        set.getInt(1),
                        set.getString(2),
                        set.getInt(3),
                        set.getString(4)
                       ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }

    public static void deleteAll(int uid) {
        String insert = "DELETE FROM Find WHERE u_id = ? AND archive IS NULL";
        Connection con = DBService.connect();
        try {
            PreparedStatement st = con.prepareStatement(insert);
            st.setInt(1, uid);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}