package Common;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class SqlAble implements Cloneable{
	
	SqlAble() {}
	
	abstract public String getTableName();
	public Object Clone() {
		try {
			return clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public String toString() {
		String s = "";
		Field[] fileds = this.getClass().getFields();
		
		for ( Field field : fileds ) {
			try{
				if ( field.get( this ) != null  )
					s += field.getName() + ":" + field.get(this) + ","; 
			}
			catch( IllegalAccessException ex) {
				System.err.println("Impossible illegal access");
				return null;
			}
		}
		if (s.endsWith(","))
			s = s.substring(0, s.length()-1);
		return s ;
	}
	/**
	 * (col1=value1,col2=value2,...£©
	 * @return WHERE statement for sql 
	 */
	public String getSetStatement() {
		String res = "";
		Field[] fileds = this.getClass().getFields();
		
		for ( Field field : fileds ) {
			try{
				if ( field.get( this ) != null  ) {
					res += field.getName();
					res += "=";
					if ( field.get(this) instanceof String )
						res += "'" + field.get(this) + "'";
					else if ( field.get(this) instanceof Date ) {
						SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						res += "'" + timeFormat.format((Date)field.get(this)) + "'";
					}
						/*res += "'" 
								+ (((Date)field.get(this)).getYear()+1900) + "-"
								+ (((Date)field.get(this)).getMonth()+1) + "-"
								+ ((Date)field.get(this)).getDate() + " "
								+ ((Date)field.get(this)).getHours() + ":"
								+ ((Date)field.get(this)).getMinutes() + ":"
								+ ((Date)field.get(this)).getSeconds()
								+ "'";*/
					else
						res += field.get(this);
					res += " and ";
				}
			} catch ( Exception e ) {
				System.err.println("Impossible illegal access");
				return null;
			}
		}
		if ( res.endsWith(" and "))
			res = res.substring(0, res.length()-5);
		return res;
	}
	/**
	 * (col1,col2,col3,...)
	 * @return Column names
	 */
	public String getColumnNameStatement() {
		String res = "";
		Field[] fileds = this.getClass().getFields();
		
		for ( Field field : fileds ) {
			try{
				if ( field.get( this ) != null  ) {
					res += field.getName();
					res += ",";
				}
			} catch ( Exception e ) {
				System.err.println("Impossible illegal access");
				return null;
			}
		}
		if ( res.endsWith(","))
			res = res.substring(0, res.length()-1);
		return "("+res+")";
	}
	/**(value1,value2,value3,...) in sql format
	 * @return Values
	 */
	public String getValueStatement() {
		String res = "";
		Field[] fileds = this.getClass().getFields();
		
		for ( Field field : fileds ) {
			try{
				if ( field.get( this ) != null  ) {
					if ( field.get(this) instanceof String )
						res += "'" + field.get(this) + "'";
					else if ( field.get(this) instanceof Date ) {
						SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						res += "'" + timeFormat.format((Date)field.get(this)) + "'";
					}
					else
						res += field.get(this);
					res += ",";
				}
			} catch ( Exception e ) {
				System.err.println("Impossible illegal access");
				return null;
			}
		}
		if ( res.endsWith(","))
			res = res.substring(0, res.length()-1);
		return "("+res+")";
	}
	public boolean fetchFromResultSet( ResultSet res ) {
		
		Field[] fileds = this.getClass().getFields();
		for ( Field field : fileds ) {
			try{
				if ( field.getType().isAssignableFrom(Integer.class) )
					field.set(this, res.getInt(field.getName()));
				else if ( field.getType().isAssignableFrom(String.class) )
					field.set(this, res.getString(field.getName()));
				else if ( field.getType().isAssignableFrom(Date.class) ) {
					SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
					field.set(this, timeFormat.parse( res.getTimestamp(field.getName()).toString() ));
				}
				else if ( field.getType().isAssignableFrom(Long.class))
					field.set(this, res.getLong(field.getName()));
				else
					System.err.print("unhandled type of field!");
				
			} catch ( SQLException e ) {
				e.printStackTrace();	//	ignore
				//return false;
			}catch (IllegalAccessException e) {
				e.printStackTrace();	//	ignore
				//System.err.println("Impossible illegal access");
				//return false;
			} catch (ParseException e) {
				e.printStackTrace();	//	ignore
			}
		}
		return true;
	}
}
