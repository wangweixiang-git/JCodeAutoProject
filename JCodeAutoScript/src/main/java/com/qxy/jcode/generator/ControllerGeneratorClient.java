/**   
* @Title ：EntityGeneratorClient.java 
* @Package ：com.qxy.jcode.entity 
* @Description ： TODO
* @author ：PeterQi
* @date ： 2018年8月3日 下午5:12:04 
* @version ： 1.0   
*/
package com.qxy.jcode.generator;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.good.comm.StringUtils;
import com.qxy.jcode.api.GeneratorClient;
import com.qxy.jcode.entity.BeanEntity;
import com.qxy.jcode.entity.ControllerEntity;
import com.qxy.jcode.entity.ServiceEntity;
import com.qxy.jcode.tools.Constant;
import com.qxy.jcode.tools.FileType;
import com.qxy.jcode.tools.Property;
import com.qxy.jcode.utils.CommonUtil;
import com.qxy.jcode.utils.DataUtil;
import com.qxy.jcode.utils.FileUtil;

import freemarker.template.Template;
import freemarker.template.utility.StringUtil;

/** 
* @ClassName ：EntityGeneratorClient 
* @Description ： TODO
* @author ：PeterQi  
* @date ：2018年8月3日 下午5:12:04 
*  
*/
public class ControllerGeneratorClient  implements GeneratorClient{
	
	private Template template;
	private Map<String, Object> root;
	private ControllerEntity entity;
	
	@Override
	public void generator(String nameNoSuffix) {
		try {
			// 创建、解析模板并缓存
			template = CommonUtil.getTemplate("controller.ftl");
			// 创建 数据模型
			createDataModel(nameNoSuffix);
			// 创建文件并控件台输出文件
			createFile(FileType.JAVA);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 创建数据模型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> createDataModel(String nameNoSuffix) {
		entity = new ControllerEntity(nameNoSuffix);
		String tableComment = StringUtils.trimEmpty(DataUtil.TABLE.get("tableComment"));
		//默认导出的文件名
		if(tableComment.isEmpty()){
			tableComment = "文件";
		}
		List<Property> properties = (List<Property>) DataUtil.TABLE.get(nameNoSuffix);
		entity.setProperties(properties);
		entity.setDescription(tableComment);
		root = new HashMap<String, Object>();
		root.put("entity", entity);
		root.put("Constant", CommonUtil.useStaticConstant());
		root.put("BeanEntity",new BeanEntity(nameNoSuffix));
		root.put("ServiceEntity",new ServiceEntity(nameNoSuffix));
		//root.put("serviceName",CommonUtil.lowerFirstCase(new ServiceEntity(nameNoSuffix).getName()));
		return root;
	}
	
	/*(non-Javadoc) 
	* <p>Title ：createFile</p> 
	* <p>Description ： </p> 
	* @param fileName
	* @throws IOException 
	* @see com.qxy.jcode.api.GeneratorClient#createFile(java.lang.String) 
	*/
	@Override
	public void createFile(FileType fileType) throws IOException {
		String fileName = entity.getName() + "." + fileType.name().toLowerCase();
		FileUtil.out(template, root, Constant.OUT_DIR_JAVA_CONTROLLER, fileName,fileType);
	}
	
	public static void main(String[] args) {
		try{ 
			//HashMap<String,Object> map = DataUtil.TABLE;
			ControllerGeneratorClient service = new ControllerGeneratorClient();
			//for(String nameNoSuffix : map.keySet()) {
				service.generator("TestBrhDepoAnal");
			//}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}


}