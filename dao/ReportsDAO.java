package dao;

import javafx.collections.ObservableList;
import model.Reports;

/**
 * This is the ReportsDAO interface. This interface defines how and what the ReportsDAOImpl class implements.
 */
public interface ReportsDAO {

    /**
     * This is the getAllReports method. This method allows the user to get any and all reports and then adds them to an
     * observable list called 'allReports'.
     * @return
     */
        public ObservableList<Reports> getAllReports();
    }

