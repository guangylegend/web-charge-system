package mysqlConnector;
/*
 * Define table class
 */

import java.util.ArrayList;
import java.util.HashSet;

public class Table {
	String tableName;
	
	
	class Schema {
		String columnName , type;
		boolean isPrimaryKey = false, isIndex = false;
		boolean isUnique = false;
		boolean autoInc = false;
		
		Schema(String _columnName, String _type) {
			this.columnName = new String(_columnName);
			this.type = new String(_type);
		}
	};
	
	ArrayList<Schema> schema= new ArrayList<Schema>();
	
	Table(String name) {
		tableName = new String(name);
	}
	void setName(String name) {
		tableName = new String(name);
	}
	void addSchema( String name, String type) {
		Schema sch = new Schema(name,type);
		sch.isPrimaryKey = false;
		sch.isIndex = false;
		schema.add( sch );
	}

	void addSchema( String name, String type, boolean isPrimaryKey, boolean isIndex ) {
		Schema sch = new Schema(name,type);
		sch.isPrimaryKey = isPrimaryKey;
		sch.isIndex = isIndex;
		schema.add( sch );
	}
	
	void addSchema( String name, String type, boolean isPrimaryKey, boolean isIndex, boolean isUnique ) {
		Schema sch = new Schema(name,type);
		sch.isPrimaryKey = isPrimaryKey;
		sch.isIndex = isIndex;
		sch.isUnique = isUnique;
		schema.add( sch );
	}
	void addSchemaWithAutoIncrease(String name, String type) {
		Schema sch = new Schema(name,type);
		sch.autoInc = true;
		schema.add(sch);
	}
	
	String getCreatTableStatement() {
		String res = "CREATE TABLE ";
		ArrayList<String> indexList = new ArrayList<String>();
		ArrayList<String> UniqueList = new ArrayList<String>();
		
		res += this.tableName + " ";
		res += "(" ;
		for ( int i = 0 ; i < schema.size() ; ++i ) {
			res += schema.get(i).columnName + " " + schema.get(i).type;
			if ( schema.get(i).autoInc)
				res += " NOT NULL AUTO_INCREMENT PRIMARY KEY";
			if( schema.get(i).isPrimaryKey )
				res += " PRIMARY KEY";
			else if (schema.get(i).isIndex)
				indexList.add(schema.get(i).columnName);
			
			if ( schema.get(i).isUnique)
				UniqueList.add(schema.get(i).columnName);
			res += ",";
		}
		
		for ( int i = 0 ; i < indexList.size() ; ++i )
			res += "INDEX("+indexList.get(i)+"),";
		for ( int i = 0 ; i < UniqueList.size() ; ++i )
			res += "UNIQUE KEY("+UniqueList.get(i)+"),";
		
		if (res.endsWith(","))
			res = res.substring(0, res.length()-1);
		res += ")" ;
		
		return res;		
	}
}
