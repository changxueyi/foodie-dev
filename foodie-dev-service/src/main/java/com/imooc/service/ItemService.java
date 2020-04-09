package com.imooc.service;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.Vo.CommentLevelCountsVO;
import com.imooc.pojo.Vo.ShopcartVO;
import com.imooc.utils.PagedGridResult;

import java.util.List;

/**
 * @ClassName ItemService
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/3/30 15:14
 */
public interface ItemService {
    /**
     * +
     * 根据商品id 查询商品详情
     *
     * @param id
     * @return
     */
    public Items queryItemById(String id);

    /**
     * 根据商品id 查询商品图片的列表
     *
     * @param itemId
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id 查询商品规格
     *
     * @param itemId
     * @return java.util.List<com.imooc.pojo.ItemsSpec>
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id 查询商品的参数
     *
     * @param itemId
     * @return com.imooc.pojo.ItemsParam
     */
    public ItemsParam queryItemParam(String itemId);
    /**+
     * 根据 商品id 查询商品的评价等级数量
     */
    public CommentLevelCountsVO queryCommentCounts(String itemId);
    /**
     * 根据商品id查询商品的评价等级数量
     * @param itemId  商品id
     * @param level   商品的评价等级
     * @param page    商品的页码
     * @param pageSize  每页展示的数量
     * @return com.imooc.utils.PagedGridResult
    */
    public PagedGridResult queryPagedComments(String itemId, Integer level,
                                              Integer page, Integer pageSize);
    public PagedGridResult searchItems(String keywords,String sort,Integer page,Integer pageSize);

    public List<ShopcartVO> queryItemsBySpecIds(String specIds);
    //根据商品规格id，获取具体信息
    public ItemsSpec queryItemSpecById(String specIds);
    //根据商品id 获取商品图片主图
    public String queryItemMainImgById(String itemId);

    //减少库存
    public void decreaseItemSpecStock(String specId,int buyCounts);

}