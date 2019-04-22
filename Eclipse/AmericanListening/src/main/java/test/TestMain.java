package test;

import java.io.IOException;
import java.util.logging.Level;

import com.americanlistening.core.Instance;
import com.americanlistening.core.User;

public class TestMain {

	public static void main(String[] args) {
		Instance inst = Instance.createInstance("test_instance", null);
		inst.logger.setLevel(Level.ALL);
		try {
			inst.load(null);
			inst.logger.log(Level.INFO, "loaded!");
		} catch (Exception e) {
			inst.logger.log(Level.SEVERE, "failed to load!", e);
		}
		
		User user = inst.createUser("test@test.com");
		//inst.logger.log(Level.INFO, user.toString());
		
		inst.saveAll();
		
		//throw new NullPointerException("test");
	}
}
