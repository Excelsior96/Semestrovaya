package Repository;

import Exceptions.PosException;
import Utilities.DBService;
import javafx.geometry.Pos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Daniel Shchepetov on 17.12.2015.
 */
public class PosRepo {


    public static void addNewPos(String str) throws PosException {
        check(str);
        try {
            PreparedStatement st = DBService.connect().prepareStatement("INSERT INTO Pos VALUES (?)");
            st.setString(1, str);
            st.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private static void check(String pos) throws PosException {

        if (pos == null || "".equals(pos)) {
            throw new PosException("Поле не заполнено");
        }
        try {
            PreparedStatement st = DBService.connect().prepareStatement("SELECT * FROM Pos WHERE name=?");
            st.setString(1,pos);
            st.execute();
            ResultSet rs = st.executeQuery();
            if(rs.next()){
               throw  new PosException("Данная должность уже зарегистрирована");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
