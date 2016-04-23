package Common;

import java.lang.reflect.Field;
import java.util.Date;

import sun.security.jca.GetInstance;

public class UserInfo {
	public Integer userId;	//	Unique identity
	public String userName; //	The real name of user
	public String loginName;	//	Account name
	public String password; //	Encrypted password
	public String email;
	public String companyAddress;
	public Date signUpTime;	//	Time when user registered
	public Date lastLogInTime;
	public Integer Money;	//	Money = 1 <==> 0.01 RMB
	public Integer priviligeLevel;	//	Level 0 for super manager, 1 for manager, 2,3,4.... for users
	public Boolean activeOrNot; //	0 or 1
	
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
	public String getWhereStatement() {
		String res = "";
		Field[] fileds = this.getClass().getFields();
		
		for ( Field field : fileds ) {
			try{
				if ( field.get( this ) != null  ) {
					res += field.getName();
					res += "=";
					if ( field.get(this) instanceof String )
						res += "'";
					res += field.get(this);
					if ( field.get(this) instanceof String )
						res += "'";
					res += ",";
				}
			} catch ( Exception e ) {
				System.err.println("Impossible illegal access");
			}
		}
		if ( res.endsWith(","))
			res = res.substring(0, res.length()-1);
		return "("+res+")";
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
					else if ( field.get(this) instanceof Date )
						res += "'" 
								+ ((Date)field.get(this)).getYear() + "-"
								+ ((Date)field.get(this)).getMonth() + "-"
								+ ((Date)field.get(this)).getDate() + " "
								+ ((Date)field.get(this)).getHours() + ":"
								+ ((Date)field.get(this)).getMinutes() + ":"
								+ ((Date)field.get(this)).getSeconds()
								+ "'";
					else
						res += field.get(this);
					res += ",";
				}
			} catch ( Exception e ) {
				System.err.println("Impossible illegal access");
			}
		}
		if ( res.endsWith(","))
			res = res.substring(0, res.length()-1);
		return "("+res+")";
	}
}
