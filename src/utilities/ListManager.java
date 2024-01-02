package utilities;

import dao.DivisionsDAO;
import dao.DivisionsDAOImpl;
import javafx.collections.ObservableList;
import model.Divisions;

/**
 * This is the ListManager class. This class puts together an observable list called divisionsBasedOnCountry so that when
 * a user selects a country, that only the appropriate states/provinces will be displayed as selectable.
 */
public class ListManager {

        public static ObservableList<Divisions> getFilteredDivisions(int countryID){
            DivisionsDAO divDAO = new DivisionsDAOImpl();
            return divDAO.getDivisionsBasedOnCountry(countryID);
        }
    }

