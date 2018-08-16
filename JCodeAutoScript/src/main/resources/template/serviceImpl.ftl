package ${entity.parentPackage}.${entity.subPackage};

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.good.comm.enu.BizType;
import com.good.comm.enu.ExecuteResult;
import com.good.comm.enu.FunctionType;
import com.good.db.IPage;
import com.good.sys.ServiceException;
import com.good.sys.UUIDGenerator;
import com.good.sys.bean.Operator;
import com.good.sys.service.AuditLogService;

import ${BeanEntity.parentPackage}.${BeanEntity.subPackage}.${BeanEntity.name};
import ${MapperEntity.parentPackage}.${MapperEntity.subPackage}.${MapperEntity.name};
import ${ServiceEntity.parentPackage}.${ServiceEntity.subPackage}.${ServiceEntity.name};
/** 
*
* @ClassName ：${entity.name} 
* @Description ： 业务实现类
* @author ：PeterQi
*
*/
@Service
public class ${entity.name} implements ${ServiceEntity.name} {

	private static Logger logger = LoggerFactory.getLogger(${entity.name}.class);
	
	@Autowired
	private ${MapperEntity.name} mapper;
	
	@Autowired
	private AuditLogService logService;
	
	@Override
	public List<${BeanEntity.name}> list(Operator oper,@Param("condition") Map<String, Object> param, IPage page) throws ServiceException {
		ExecuteResult result = ExecuteResult.UNKNOWN;
		List<${BeanEntity.name}> ret = mapper.selectByConditionSelective(param, page);
		logger.info("list service result:\n{}\n", result);
		result = ExecuteResult.SUCCESS;
		logService.addAuditLog(oper, BizType.PRD, "list${BeanEntity.name}", "查询${entity.description}","",
				FunctionType.QUERY, result);
		return ret;
	}

	@Override
	public void delete(Operator oper , ${BeanEntity.name}[] list) throws ServiceException {
		ExecuteResult result = ExecuteResult.UNKNOWN;
		StringBuffer sb = new StringBuffer();
		for (${BeanEntity.name} one : list) {
			String pkId = one.getPkId();
            mapper.deleteByPrimaryKey(pkId);
            sb.append(one.getPkId()).append(",");
        }	
		result = ExecuteResult.SUCCESS;
		logger.info("delete service result:\n{}\n", result);
		logService.addAuditLog(oper, BizType.PRD, "delete${BeanEntity.name}", "删除${entity.description}",sb.toString(),
				FunctionType.DELETE, result);
	}

	@Override
	public void update(Operator oper, ${BeanEntity.name} one) throws ServiceException {
		ExecuteResult result = ExecuteResult.UNKNOWN;
		mapper.updateByPrimaryKey(one);
		result = ExecuteResult.SUCCESS;
		logger.info("update service result:\n{}\n", result);
		logService.addAuditLog(oper, BizType.PRD, "update${BeanEntity.name}", "更新${entity.description}", one.getPkId().toString(),
				FunctionType.UPDATE, result);
	}


	@Override
	public void insert(Operator oper, ${BeanEntity.name} one) throws ServiceException {
		ExecuteResult result = ExecuteResult.UNKNOWN;
		String pkId = UUIDGenerator.getUUIDKey();
		one.setPkId(pkId);
		mapper.insert(one);
		result = ExecuteResult.SUCCESS;
		logger.info("insert service result:\n{}\n", result);
		logService.addAuditLog(oper, BizType.PRD, "insert${BeanEntity.name}", "增加${entity.description}", pkId,
				FunctionType.INSERT, result);
		
	}
	
}