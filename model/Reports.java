package model;

/**
 * This is the Reports model class. This class specifies the parameters to be used in all Reports classes/documents.
 */
public class Reports {

        private String month;

        private String type;

        private int count;

    /**
     * This is the Reports constructor
     * @param month
     * @param type
     * @param count
     */
        public Reports(String month, String type, int count) {
            this.month = month;
            this.type = type;
            this.count = count;
        }

    /**
     *
     * @return month
     */
    public String getMonth() {
            return month;
        }

    /**
     * Specifies which month to set
     * @param month
     */
    public void setMonth(String month) {
            this.month = month;
        }

    /**
     *
     * @return type
     */
    public String getType() {
            return type;
        }

    /**
     * Specifies which type to set
     * @param type
     */
    public void setType(String type) {
            this.type = type;
        }

    /**
     *
     * @return count
     */
        public int getCount() {
            return count;
        }

    /**
     * Specifies which count to set
     * @param count
     */
        public void setCount(int count) {
            this.count = count;
        }

    /**
     * This is the Reports toString method. This method defines default syntax and converts hash code to string.
     * @return
     */
        @Override
        public String toString() {
            return ("Report: " + month + " " + type + " " + Integer.toString(count));
        }
    }

