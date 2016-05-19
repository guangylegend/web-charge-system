package mysqlConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class generalDBAPI<T extends Common.SqlAble> {
	String whereClause = null;
	String orderBy = null;
	String update;
	Integer top = null;
	Class<T> cls ;
	T clsInstance;
	
	public generalDBAPI<T> clear() {
		whereClause = null;
		orderBy = null;
		update = null;
		top = null;
		return this;
	}
	
	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public int executeUpdate( T item  ) throws SQLException {
		int r;
		Connection con = DbConnector.conectionToDB();
		Statement stmt = con.createStatement();
		String s = "UPDATE " + item.getTableName() + " SET "
				+ item.getSetStatement();
		if ( whereClause != null )
			s += " WHERE " + whereClause;
		System.err.println(s);
		r = stmt.executeUpdate(s);
		con.close();
		return r;
	}
	public ArrayList<T> executeSelect() throws SQLException {
		ArrayList<T> res = new ArrayList<T>();
		
		Connection con = DbConnector.conectionToDB();
		Statement stmt = con.createStatement();
		String s = "SELECT * FROM " + clsInstance.getTableName() ;
		
		if ( whereClause != null )
			s += " WHERE " + whereClause;
		if ( orderBy != null)
			s += " ORDER BY " + orderBy ;
		if ( top != null )
			s += " LIMIT " + top;
		ResultSet r = stmt.executeQuery(s);
		while (r.next()) {
			clsInstance.fetchFromResultSet(r);
			res.add((T) clsInstance.Clone());
		}
		con.close();
		return res;
	}
	
	/**
	 * Insert item into database.
	 * @param item
	 * @return
	 * @throws SQLException 
	 */
	public int executeInsert( T item ) throws SQLException {
		int r;
		Connection con = DbConnector.conectionToDB();
		Statement stmt = con.createStatement();
		String s = "INSERT INTO " + item.getTableName()
				+ item.getColumnNameStatement()
				+ " VALUES " + item.getValueStatement();
		r = stmt.executeUpdate(s);
		con.close();
		return r;
	}
	
	/**
	 * 
	 * @param t t row(s) will be select when calling executeSelect() after set
	 * @return
	 */
	public generalDBAPI<T> setTop( int t ) {
		top = t;
		return this;
	}
	/**
	 * .setOrderBy("customer_id")
	 * @param condition
	 * @return
	 */
	public generalDBAPI<T> setOrderBy( String condition ) {
		orderBy = new String(condition);
		return this;
	}
	/**
	 * Set where clause to string in parameter. Call this interface before call whereAdd(), whereOr() 
	 * @param condition
	 * @return
	 */
	public generalDBAPI<T> setWhere(String condition) {
		whereClause = new String(condition);
		return this;
	}
	/**
	 * Append an statement with " AND ".
	 * @param condition
	 * @return
	 */
	public generalDBAPI<T> setWhereAnd(String condition) {
		if ( whereClause == null )
			return null;
		whereClause += " AND " + condition; 
		return this;
	}
	/**
	 * Append an statement with " OR ".
	 * @param condition
	 * @return
	 */
	public generalDBAPI<T> setWhereOr(String condition) {
		if ( whereClause == null )
			return null;
		whereClause += " OR " + condition; 
		return this;
	}
	public generalDBAPI(Class<T> cl) throws ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		this.cls = cl;
		try {
			clsInstance = cls.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
