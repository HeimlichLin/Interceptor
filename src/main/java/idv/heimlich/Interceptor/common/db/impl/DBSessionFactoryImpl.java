package idv.heimlich.Interceptor.common.db.impl;

import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idv.heimlich.Interceptor.common.db.DBSessionManager;
import idv.heimlich.Interceptor.common.db.IDBSession;
import idv.heimlich.Interceptor.common.db.IDBSessionFactory;
import idv.heimlich.Interceptor.common.db.code.DBConfig;

public class DBSessionFactoryImpl implements IDBSessionFactory {
	
	private static Logger LOGGER = LoggerFactory.getLogger(DBSessionFactoryImpl.class);

	@Override
	public IDBSession getSession(String conn) {
		final String connid = StringUtils.defaultIfEmpty(conn, DBSessionManager.PROP_DEFAULT_CONN_ID);
		Objects.requireNonNull(connid, "無此定義coonid" + conn);
		DBConfig dbConfig = DBConfig.valueOf(connid);
//		LOGGER.debug("use connid:" + connid);
		return dbConfig.getXdaoSession();
	}

}
