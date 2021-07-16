package com.jbent.peoplecentral.postgres;


import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

public class PGArrayGeneric implements Array {
    
     /**
	 * 
	 */
	private static final long serialVersionUID = 409181549376339382L;

	private Object[] array;
    
     private int type;

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
          // TODO Auto-generated method stub
          return type;
     }

     public String getBaseTypeName() throws SQLException {
          switch (type) {
          case Types.INTEGER: return "int4";
          case Types.BIGINT: return "int8";
          case Types.DECIMAL: return "numeric";
          case Types.VARCHAR: return "text";
          //case Types.LONGNVARCHAR: return "text";
          case Types.LONGVARCHAR: return "text";
         
          }
          return null;
     }
     public void setArray(Integer type,Object[] array) {
          this.type=type;
          this.array=array;
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
          String ret="{";
          for (Object o:array) {
               if (!first) {
                    ret+=",";
               }
               first=false;
               ret+=o.toString();
          }
          ret+="}";
          return ret;
     }
    
    

}
