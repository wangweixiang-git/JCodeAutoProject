package com.good.testjcode.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.good.db.IPage;
import com.good.testjcode.bean.TestBrhDepoAnalPo;

/** 
*
* @ClassName ：TestBrhDepoAnalDao 
* @Description ： 映射接口类
* @author ：PeterQi
*
*/
public interface TestBrhDepoAnalDao {

	public int deleteByPrimaryKey(String pkId);

	public int insert(TestBrhDepoAnalPo record);

	public int insertSelective(TestBrhDepoAnalPo record);

	public int updateByPrimaryKeySelective(TestBrhDepoAnalPo record);

	public int updateByPrimaryKey(TestBrhDepoAnalPo record);

	public TestBrhDepoAnalPo selectByPrimaryKey(String pkId);

	public List<TestBrhDepoAnalPo> selectByConditionSelective(@Param("condition") Map<String,Object> param, @Param("page") IPage page);

}