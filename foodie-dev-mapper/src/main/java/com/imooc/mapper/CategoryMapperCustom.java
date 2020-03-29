package com.imooc.mapper;

import com.imooc.pojo.Vo.CategoryVO;
import com.imooc.pojo.Vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom {
     List<CategoryVO> getSubCatList(Integer rootCatId);

     public List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String, Object> map);
}