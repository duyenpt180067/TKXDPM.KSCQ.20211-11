package subsystem.interbank;

import java.io.IOException;

import common.exception.UnrecognizedException;
import utils.API;

public class InterbankBoundary {


	public String query(String processTransactionUrl, String generateData) throws UnrecognizedException  {
		// TODO Auto-generated method stub
		String response = null;
		try {
			String url;
			response = API.post( processTransactionUrl, generateData);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new UnrecognizedException();
		}
		return response;
	}

}
