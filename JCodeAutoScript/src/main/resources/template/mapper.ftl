package ${entity.parentPackage}.${entity.subPackage};

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.good.db.IPage;
import ${BeanEntity.parentPackage}.${BeanEntity.subPackage}.${BeanEntity.name};

/** 
*
* @ClassName ：${entity.name} 
* @Description ： 映射接口类
* @author ：PeterQi
*
*/
public interface ${entity.name} {

	public int deleteByPrimaryKey(String pkId);

	public int insert(${BeanEntity.name} record);

	public int insertSelective(${BeanEntity.name} record);

	public int updateByPrimaryKeySelective(${BeanEntity.name} record);

	public int updateByPrimaryKey(${BeanEntity.name} record);

	public ${BeanEntity.name} selectByPrimaryKey(String pkId);

	public List<${BeanEntity.name}> selectByConditionSelective(@Param("condition") Map<String,Object> param, @Param("page") IPage page);

}