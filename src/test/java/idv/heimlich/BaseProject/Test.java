package idv.heimlich.BaseProject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idv.heimlich.Interceptor.common.db.IDBSession;
import idv.heimlich.Interceptor.common.db.IDBSessionFactory;
import idv.heimlich.Interceptor.common.db.impl.DBSessionFactoryImpl;

public class Test {
	
	private static Logger LOGGER = LoggerFactory.getLogger(Test.class);
	
	public static void main(String[] args) {
		LOGGER.debug("Test Start");
		IDBSessionFactory sessionFactory = new DBSessionFactoryImpl();
		IDBSession session = sessionFactory.getSession("");
		
	}

}
