package entity.db;

import java.lang.System.Logger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import common.exception.EntityNotFoundException;
import utils.Configs;
import utils.Utils;
import view.handler.rent_info.RentedBikeInfoScreenHandler;




public class EcobikeDB {
//	private static Logger LOGGER = Utils.getLogger(EcobikeDB.class.getName());
	
	
    private static Connection connect;

    public static Connection getConnection() {
        if (connect != null) return connect;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:assets/db/ecobike.db";
            
            connect = DriverManager.getConnection(url);
//            LOGGER.info("Connect database successfully");
            System.out.println("get info ");
        } catch (Exception e) {
//            LOGGER.info(e.getMessage());
        	 System.out.println("get error ");
        }
        return connect;
    }


    public static void main(String[] args) throws SQLException, EntityNotFoundException {
    }
}
