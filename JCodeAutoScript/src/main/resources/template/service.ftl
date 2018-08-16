package ${entity.parentPackage}.${entity.subPackage};

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.good.sys.ServiceException;
import com.good.sys.bean.Operator;
import com.good.db.IPage;
import ${BeanEntity.parentPackage}.${BeanEntity.subPackage}.${BeanEntity.name};;

/** 
*
* @ClassName ：${entity.name} 
* @Description ： 业务接口类
* @author ：PeterQi
*
*/
public interface ${entity.name} {

	public List<${BeanEntity.name}> list(Operator oper,@Param("condition") Map<String,Object> param, @Param("page") IPage page) throws ServiceException;

	public void delete(Operator oper,${BeanEntity.name}[] list) throws ServiceException;

	public void update(Operator oper,${BeanEntity.name} po) throws ServiceException;

	public void insert(Operator oper,${BeanEntity.name} po) throws ServiceException;

}
