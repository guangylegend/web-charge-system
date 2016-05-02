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
	public Integer remainedMoney;	//	Money = 1 <==> 0.01 RMB
	public Integer priviligeLevel;	//	Level 0 for super manager, 1 for manager, 2,3,4.... for users
	public Boolean activeOrNot; //	0 or 1
	
	/**
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
	/**
	 * @return Values
	 */
	public String getValueStatement() {
		String res = "";
		Field[] fileds = this.getClass().getFields();
		
		for ( Field field : fileds ) {
			try{
				if ( field.get( this ) != null  ) {
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
}
