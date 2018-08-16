/**   
* @Title ：JcodeAutoApplication.java 
* @Package ：com.good 
* @Description ： TODO
* @author ：PeterQi
* @date ： 2018年8月13日 下午1:15:35 
* @version ： 1.0   
*/
package com.good;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/** 
* @ClassName ：JcodeAutoApplication 
* @Description ： TODO
* @author ：PeterQi  
* @date ：2018年8月13日 下午1:15:35 
*  
*/
@SpringBootApplication
@ComponentScan("com")
@MapperScan("com")
public class JcodeAutoApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(JcodeAutoApplication.class, args);
	}

}
