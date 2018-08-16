package com.good.testjcode.service;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.good.sys.ServiceException;
import com.good.sys.bean.Operator;
import com.good.db.IPage;
import com.good.testjcode.bean.TestBrhDepoAnalPo;;

/** 
*
* @ClassName ：TestBrhDepoAnalService 
* @Description ： 业务接口类
* @author ：PeterQi
*
*/
public interface TestBrhDepoAnalService {

	public List<TestBrhDepoAnalPo> list(Operator oper,@Param("condition") Map<String,Object> param, @Param("page") IPage page) throws ServiceException;

	public void delete(Operator oper,TestBrhDepoAnalPo[] list) throws ServiceException;

	public void update(Operator oper,TestBrhDepoAnalPo po) throws ServiceException;

	public void insert(Operator oper,TestBrhDepoAnalPo po) throws ServiceException;

}
