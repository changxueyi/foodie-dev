package com.imooc.mapper;

import com.imooc.pojo.Vo.ItemCommentVO;
import com.imooc.pojo.Vo.SearchItemsVO;
import com.imooc.utils.PagedGridResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ItemsMapperCustom
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/3/31 14:01
 */
public interface ItemsMapperCustom {
    public List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String,Object> map);

    public List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);
}