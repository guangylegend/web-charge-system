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
		
		Schema(String _columnName, String _type) {
			this.columnName = _columnName;
			this.type = _type;
		}
	};
	
	ArrayList<Schema> schema= new ArrayList<Schema>();
	
	Table(String name) {
		tableName = name;
	}
	void setName(String name) {
		tableName = name;
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
	
	String getCreatTableStatement() {
		String res = "CREATE TABLE ";
		ArrayList<String> indexList = new ArrayList<String>();
		
		res += this.tableName + " ";
		res += "(" ;
		for ( int i = 0 ; i < schema.size() ; ++i ) {
			res += schema.get(i).columnName + " " + schema.get(i).type;
			if( schema.get(i).isPrimaryKey )
				res += " PRIMARY KEY";
			else if (schema.get(i).isIndex)
				indexList.add(schema.get(i).columnName);
			res += ",";
		}
		
		for ( int i = 0 ; i < indexList.size() ; ++i )
			res += "INDEX("+indexList.get(i)+"),";
		if (res.endsWith(","))
			res = res.substring(0, res.length()-1);
		res += ")" ;
		
		return res;		
	}
}
