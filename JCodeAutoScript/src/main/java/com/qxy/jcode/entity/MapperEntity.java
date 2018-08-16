/**   
* @Title ：Entity.java 
* @Package ：com.qxy.jcode.tools 
* @Description ： TODO
* @author ：PeterQi
* @date ： 2018年8月3日 下午5:05:48 
* @version ： 1.0   
*/
package com.qxy.jcode.entity;

import com.qxy.jcode.tools.Constant;

/**
 * 
 * @ClassName ：Entity 
 * @Description ： 映射接口类
 * @author ：PeterQi 
 * @date ：2018年8月3日 下午5:05:48    
 */

public class MapperEntity extends Entity {

	public MapperEntity(String nameNoSuffix) {
		super.setNameNoSuffix(nameNoSuffix);
		super.setParentPackage(Constant.OUT_DIR_JAVA_BASE);
		//super.setParentPackage("com.good.testJcode");
		super.setSubPackage(Constant.OUT_DIR_JAVA_MAPPER);
		super.setNameSuffix(Constant.OUT_FILE_SUFFIX_MAPPER);
	}

}