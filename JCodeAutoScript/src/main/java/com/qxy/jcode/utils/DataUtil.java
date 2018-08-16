/**   
* @Title ：DataUtil.java 
* @Package ：com.qxy.jcode.utils 
* @Description ： TODO
* @author ：PeterQi
* @date ： 2018年8月10日 上午12:31:01 
* @version ： 1.0   
*/
package com.qxy.jcode.utils;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.qxy.jcode.tools.DbConnectionTool;
import com.qxy.jcode.tools.Property;
import com.qxy.jcode.tools.PropertyType;

/** 
* @ClassName ：DataUtil 
* @Description ： TODO
* @author ：PeterQi  
* @date ：2018年8月10日 上午12:31:01 
*  
*/
public class DataUtil {
	
	private static Logger logger = Logger.getLogger(DataUtil.class);
	public static HashMap<String,Object> TABLE = new HashMap<String,Object>();

/**
 * 
* @Title：convertTableToProperty 
* @Description ：TODO
* @date ：2018年8月10日 下午10:46:54 
* @param ： 
* @return ： HashMap<String,Object> 
* @throws 
 */
    public static HashMap<String,Object> convertTableToProperty(String tableName, String nameNoSuffix){  
        //创建sqlmap.xml文件模板所需参数集合
    	
    	String columSql = "select * from "+tableName;  
    	List<Property> propertys = new ArrayList<Property>();
    	List<String> pkNames = new ArrayList<String>();
    	Connection connection = null;
    	PreparedStatement statement = null;
    	ResultSetMetaData metadata = null;
        try {  
        	connection = DbConnectionTool.geConnection();	
        	//装载表的备注信息
        	setTableComment(tableName,connection);
        	//装载主键列名
        	setPkNames(tableName, pkNames, connection);
           
        	statement = connection.prepareStatement(columSql);  
        	//获取字段备注
        	HashMap<String,String> comments = setColumnComment(tableName, connection);
        	
        	//获取数据库的元数据   
            metadata = statement.getMetaData();  
            
            //数据库的字段个数  
            int len = metadata.getColumnCount();  
            setBeanProperty(metadata, len, propertys,comments);  
            
            TABLE.put(nameNoSuffix, propertys);
            TABLE.put("tableName", tableName);
            TABLE.put("pkNames", pkNames);
        } catch (Exception e) {  
        	logger.error("创建数据库字段映射异常",e);
        }finally{
        	try {
        		statement.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        return TABLE;
    }
    
    public static void setTableComment(String tableName,Connection connection){
    	String tableComSql = "select TABLE_COMMENT from INFORMATION_SCHEMA.Tables where table_name='"+ tableName+"' ;";
    	PreparedStatement statement = null;
    	ResultSet rs = null;
		try {
			statement = connection.prepareStatement(tableComSql);
			rs = statement.executeQuery();
	    	while(rs.next()){
	    		TABLE.put("tableComment", rs.getString("TABLE_COMMENT"));
	    	}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
    	
    }
    
    /**
    * @Title：setColumnComment 
    * @Description ：设置字段注释
    * @date ：2018年8月13日 下午4:46:05 
    * @param ：@param tableName
    * @param ：@param statement
    * @param ：@return 
    * @return ：HashMap<String,String> 
    * @throws 
     */
    private static HashMap<String,String> setColumnComment(String tableName,Connection connection){
    	HashMap<String,String> comments = new HashMap<String,String>();
    	String commentSql = "show full columns from " + tableName;
    	PreparedStatement statement = null;
        ResultSet rs = null;
		try {
			statement = connection.prepareStatement(commentSql);
			rs = statement.executeQuery();
			while(rs.next()){
	        	comments.put(rs.getString("Field"), rs.getString("Comment"));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
        return comments;
    }
    
/** 
* @Title：setPkNames 
* @Description ：TODO
* @date ：2018年8月12日 上午10:56:52 
* @param ：@param tableName
* @param ：@param pkNames
* @param ：@param connection
* @param ：@throws SQLException 
* @return ：void 
* @throws 
*/
private static void setPkNames(String tableName, List<String> pkNames, Connection connection) {
	ResultSet rs = null;
	try {
		rs = connection.getMetaData().getPrimaryKeys(connection.getCatalog(), null, tableName);
		while(rs.next()){
			String pkName = rs.getString("COLUMN_NAME");
			pkNames.add(pkName);
		}
	} catch (SQLException e) {
		logger.error("查询主键列名异常！",e);
	}finally{
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
/** 
 * @param comments 
* @Title：setBeanProperty 
* @Description ：TODO
* @date ：2018年8月11日 下午8:43:57 
* @param ：@param metadata
* @param ：@param len
* @param ：@throws SQLException 
* @return ：void 
* @throws 
*/
private static void setBeanProperty(ResultSetMetaData metadata, int len, List<Property> propertys, HashMap<String, String> comments) throws SQLException {
	
	Property property = null;
	//数据库字段名称  
	String colnames[] = new String[len+1];  
	//数据库字段类型    
	PropertyType colTypes[] = new PropertyType[len+1];  
	for(int i= 1;i<=len;i++){  
		property = new Property();
		
		//获取字段名称
	    colnames[i] = metadata.getColumnName(i);   
	    property.setColumnName(colnames[i]);
	    //获取字段备注
	    property.setPropertyDescription(comments.get(colnames[i]));
	    
	    ////获取字段类型
	    String columnTypeName = metadata.getColumnTypeName(i);
	    property.setColumnType(columnTypeName);
	    //转换java属性名
	    String propertyName = fieldToProperty(colnames[i]);
	    property.setPropertyName(propertyName);
	    //转换java类型
	    colTypes[i] = sqlType2JavaType(columnTypeName);    
	    property.setJavaType(colTypes[i].name());
	    property.setPropertyType(colTypes[i]);
	    
		propertys.add(property);
	    System.out.println(String.format("属性名  %s,属性类型  %s,列名  %s,列类型  %s,列注释 %s", propertyName,colTypes[i].name(),
	    							colnames[i],columnTypeName,comments.get(colnames[i])));
	}
}  
    /**
     * 
    * @Title：sqlType2JavaType 
    * @Description ：mysql的字段类型转化为java的类型
    * @date ：2018年8月11日 下午8:46:08 
    * @param ：@param sqlType
    * @param ：@return 
    * @return ：String 
    * @throws 
     */
    private static PropertyType sqlType2JavaType(String sqlType) {    
          
        if(sqlType.equalsIgnoreCase("bit")){    
            return PropertyType.Boolean;    
        }else if(sqlType.equalsIgnoreCase("tinyint")|| sqlType.equalsIgnoreCase("blob") ){    
            return PropertyType.Byte;    
        }else if(sqlType.equalsIgnoreCase("smallint")){    
            return PropertyType.Short;    
        }else if(sqlType.equalsIgnoreCase("int") || sqlType.equalsIgnoreCase("integer") ){    
            return PropertyType.Integer;    
        }else if(sqlType.equalsIgnoreCase("bigint")){    
            return PropertyType.Long;    
        }else if(sqlType.equalsIgnoreCase("float")){    
            return PropertyType.Float;    
        }else if(sqlType.equalsIgnoreCase("decimal") ){ 
        	return PropertyType.BigDecimal;
        }else if(sqlType.equalsIgnoreCase("double")){    
            return PropertyType.Double;    
        }else if(sqlType.equalsIgnoreCase("varchar") || sqlType.equalsIgnoreCase("char")     
                 || sqlType.equalsIgnoreCase("text") || sqlType.equalsIgnoreCase("tinytext")){    
            return PropertyType.String;    
        }else if(sqlType.equalsIgnoreCase("datetime") ||sqlType.equalsIgnoreCase("date")){    
            return PropertyType.Date;    
        }else if(sqlType.equalsIgnoreCase("timestamp")||sqlType.equalsIgnoreCase("time")){    
            return PropertyType.Date;    
        }    
            
        return null;    
    } 

    /**
     * 
    * @Title：fieldToProperty 
    * @Description ：表字段转换为属性
    * @date ：2018年8月11日 下午8:23:20 
    * @param ：@param field
    * @param ：@return 
    * @return ：String 
    * @throws 
     */
    private static String fieldToProperty(String field) {  
        if (null == field) {  
            return "";  
        }  
        //确保转换为小写
        field = field.toLowerCase();
        char[] chars = field.toCharArray();  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < chars.length; i++) {  
            char c = chars[i];  
            if (c == '_') {  
                int j = i + 1;  
                if (j < chars.length) {  
                    sb.append(CommonUtil.upperFirstCase(String.valueOf(chars[j])));  
                    i++;  
                }  
            } else {  
                sb.append(c);  
            }  
        }
        
        return sb.toString();  
    }  
    /**
     * 
    * @Title：parseGenerateXml 
    * @Description ：解析geennerate.xml读取表名和实体名前缀，生成过代码的要注释掉，否则重新执行会覆盖掉原来的文件
    * @date ：2018年8月14日 上午11:29:20 
    * @param ：@return 
    * @return ：HashMap<String,String> 
    * @throws 
     */
    @SuppressWarnings("rawtypes")
	public static HashMap<String,String> parseGenerateXml(){
    	//key为实体名 value为表名
    	HashMap<String,String> beanList = new HashMap<String,String>();
    	SAXReader reader = new SAXReader();
    	try {
            Document document = reader.read(new File("src/main/resources/generate.xml"));
            Element generates = document.getRootElement();
            Iterator it = generates.elementIterator();
           
            while (it.hasNext()) {
                Element generate = (Element) it.next();
                // 获取table , beanname
                String tableName = generate.attributeValue("tableName");
                String beanNameNoSuf = generate.attributeValue("beanNameNoSuf");
                beanList.put(beanNameNoSuf, tableName);
            }   
            }catch (DocumentException e) {
                logger.error("generate.xml解析异常,",e);
            }
    	return beanList;
    }

}
