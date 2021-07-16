package com.jbent.peoplecentral.postgres;

import java.io.Serializable;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import org.postgresql.jdbc2.AbstractJdbc2Array;
import org.postgresql.util.PGobject;

public class PGArray implements Serializable,Array {
    
     /**
	 * 
	 */
	private static final long serialVersionUID = 8514583711922316909L;
	private PGobject[] array;    

     public PGArray(PGobject[] array) {
         this.array=array;
     }  
     
     public void free() throws SQLException {
          // TODO Auto-generated method stub

     }

     public Object getArray() throws SQLException {
          // TODO Auto-generated method stub
          return array;
     }

     public Object getArray(Map<String, Class<?>> map) throws SQLException {
          // TODO Auto-generated method stub
          return null;
     }

     public Object getArray(long index, int count) throws SQLException {
          // TODO Auto-generated method stub
          return null;
     }

     public Object getArray(long index, int count, Map<String, Class<?>> map)
               throws SQLException {
          // TODO Auto-generated method stub
          return null;
     }

     public int getBaseType() throws SQLException {
//          array[0].getType();
//    	 return Types.OTHER;
    	 return 0;
     }

     public String getBaseTypeName() throws SQLException {
    	 if(array != null && array.length > 0){
    		 return array[0].getType();
    	 }else{
    		 return null;
    	 }
//          switch (type) {
//          case Types.INTEGER: return "int4";
//          case Types.BIGINT: return "int8";
//          case Types.DECIMAL: return "numeric";
//          case Types.VARCHAR: return "text";
//          //case Types.LONGNVARCHAR: return "text";
//          case Types.LONGVARCHAR: return "text";
//         
//          }
//          return null;
     }

     public ResultSet getResultSet() throws SQLException {
          // TODO Auto-generated method stub
          return null;
     }

     public ResultSet getResultSet(Map<String, Class<?>> map)
               throws SQLException {
          // TODO Auto-generated method stub
          return null;
     }

     public ResultSet getResultSet(long index, int count) throws SQLException {
          // TODO Auto-generated method stub
          return null;
     }

     public ResultSet getResultSet(long index, int count,
               Map<String, Class<?>> map) throws SQLException {
          // TODO Auto-generated method stub
          return null;
     }

     @Override
     public String toString() {
          boolean first=true;
          StringBuilder ret= new StringBuilder("{");
          for (PGobject o:array) {
               if (!first) {
                    ret.append(",");
               }
               first=false;
               if(!(o instanceof PGEscapedForArrayObject)){
            	   AbstractJdbc2Array.escapeArrayElement(ret, o.getValue());
               }else{
            	   ret.append(((PGEscapedForArrayObject)o).getArraySafeValue());
               }
          }
          ret.append("}");
          return ret.toString();
     }
    
    

}
