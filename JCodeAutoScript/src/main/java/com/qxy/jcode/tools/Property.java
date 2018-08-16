/**   
* @Title ：Property.java 
* @Package ：com.qxy.jcode.tools 
* @Description ： TODO
* @author ：PeterQi
* @date ： 2018年8月3日 下午5:01:16 
* @version ： 1.0   
*/
package com.qxy.jcode.tools;

/** 
* @ClassName ：Property 
* @Description ：实体对应的属性类
* @author ：PeterQi  
* @date ：2018年8月3日 下午5:01:16 
*  
*/
public class Property {
	// 属性数据类型
	private String javaType;
	// 属性名称
	private String propertyName;
	// 属性描述
	private String propertyDescription;
	// 属性类型
	private PropertyType propertyType;
	// 属性对应数据库字段名
	private String columnName;
	// 属性对应数据库字段类型
	private String columnType;
	
	
	/**
	 * @return the columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @param columnName the columnName to set
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
 

	public String getJavaType() {
		return javaType;
	}
	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getPropertyDescription() {
		return propertyDescription;
	}
	public void setPropertyDescription(String propertyDescription) {
		this.propertyDescription = propertyDescription;
	}
	public PropertyType getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(PropertyType propertyType) {
		this.propertyType = propertyType;
	}
	/**
	 * @return the columnType
	 */
	public String getColumnType() {
		return columnType;
	}
	/**
	 * @param columnType the columnType to set
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	
}
