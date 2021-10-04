package idv.heimlich.Interceptor.common.db;

public interface IDBSessionFactory {

	IDBSession getSession(String conn);

}
