package jdbc;

import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class DBCPInit extends HttpServlet{

	@Override
	public void init() throws ServletException {
		loadJdbcDriver();
		initConnectionPool();
	}
	
	private void loadJdbcDriver(){
		try{
			//web.xml
			String driver = getInitParameter("jdbcdriver");
			Class.forName(driver);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	private void initConnectionPool(){
		try{
			String jdbcDriver = getInitParameter("jdbcUrl");
			String dbUser = getInitParameter("dbUser");
			String dbPassword = getInitParameter("dbPass");
			
			ConnectionFactory connFactory = new DriverManagerConnectionFactory(jdbcDriver, dbUser, dbPassword);
			
			PoolableConnectionFactory poolableConnFactory = new PoolableConnectionFactory(connFactory, null);
			poolableConnFactory.setValidationQuery("select 1");
			
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			//커넥션 풀에 있는 유휴 커넥션 검사 주기 설정(5분으로 설정)
			poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
			//true이면 유휴 커션이 유효한 지 검사
			poolConfig.setTestWhileIdle(true);
			//커넥션 풀이 유지할 최소 유휴 커넥션 개수
			poolConfig.setMinIdle(4);
			//풀이 관리하는 커넥션의 최대 개수를 설정(늘릴 수 있음)
			poolConfig.setMaxTotal(50);
			
			GenericObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConnFactory, poolConfig);
			poolableConnFactory.setPool(connectionPool);
			
			Class.forName("org.apache.commons.dbcp2.PoolingDriver");
			PoolingDriver driver = (PoolingDriver)DriverManager.getDriver("jdbc:apache:commons:dbcp:"); //기본이름
			
			//커넥션 풀을 등록시, 풀의 이름으로 'chapter14'를 사용
			//따라서, 사용시에는 이 이름을 사용하여 커넥션을 구하면 됨
			//예) jdbc:apache:commons:dbcp:chapter14
			String poolName = getInitParameter("poolName");
			driver.registerPool(poolName, connectionPool); //나만의 이름(주로 db이름과 같게)
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
}
