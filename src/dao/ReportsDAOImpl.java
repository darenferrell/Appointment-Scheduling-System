package dao;

import controller.ReportsMonthTypeController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Reports;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static helper.JDBC.connection;

/**
 * This is the ReportsDAOImpl class. This class implements the methods defined in the ReportsDAO interface.
 */
public class ReportsDAOImpl implements ReportsDAO {

    ObservableList<Reports> allReports = FXCollections.observableArrayList();

    /**
     * This is the getAllReports method. This method allows the user to get any and all reports and then adds them to an
     * observable list called 'allReports'.
     *
     * @return
     */
    @Override
    public ObservableList<Reports> getAllReports() {
        try {
            String sql = "SELECT monthname(start), type, count(*) as cnt FROM appointments GROUP BY monthname(start), type";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet result = ps.executeQuery();

            while (result.next()) {
                String month = result.getString("monthname(start)");
                String type = result.getString("type");
                int count = result.getInt("cnt");
                Reports r = new Reports(month, type, count);
                allReports.add(r);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
        return allReports;
    }
}

