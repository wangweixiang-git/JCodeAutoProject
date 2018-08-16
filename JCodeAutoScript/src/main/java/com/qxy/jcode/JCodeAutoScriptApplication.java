package com.qxy.jcode;

import java.util.HashMap;

import com.qxy.jcode.generator.ControllerGeneratorClient;
import com.qxy.jcode.generator.EntityGeneratorClient;
import com.qxy.jcode.generator.MapperGeneratorClient;
import com.qxy.jcode.generator.ServiceGeneratorClient;
import com.qxy.jcode.generator.ServiceImplGeneratorClient;
import com.qxy.jcode.generator.ViewGeneratorClient;
import com.qxy.jcode.generator.XMLGeneratorClient;
import com.qxy.jcode.utils.DataUtil;


public class JCodeAutoScriptApplication {

	public static void main(String[] args) {
		//SpringApplication.run(JCodeAutoScriptApplication.class, args);
		try {
				/*HashMap<String,Object> map = DataUtil.TABLE;
				XMLGeneratorClient entity = new XMLGeneratorClient();
				for(String nameNoSuffix : map.keySet()) {
					entity.generator(nameNoSuffix);
				}*/
				EntityGeneratorClient entity = new EntityGeneratorClient();
				MapperGeneratorClient mapper = new MapperGeneratorClient();
				ServiceGeneratorClient service = new ServiceGeneratorClient();
				ServiceImplGeneratorClient serviceImpl = new ServiceImplGeneratorClient();
				ControllerGeneratorClient controller = new ControllerGeneratorClient();
				ViewGeneratorClient view = new ViewGeneratorClient();
				XMLGeneratorClient xml = new XMLGeneratorClient();
				HashMap<String,String> map = DataUtil.parseGenerateXml();
				for(String nameNoSuffix : map.keySet()){
					//装载实体属性及表字段到DataUtil.TABLE
					//目前只支持单主键的表
					DataUtil.convertTableToProperty(map.get(nameNoSuffix), nameNoSuffix);
					//entity.generator(nameNoSuffix);
					mapper.generator(nameNoSuffix);
					service.generator(nameNoSuffix);
					serviceImpl.generator(nameNoSuffix);
					controller.generator(nameNoSuffix);
					view.generator(nameNoSuffix);
					entity.generator(nameNoSuffix);
					xml.generator(nameNoSuffix);
					DataUtil.TABLE.clear();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}
}
