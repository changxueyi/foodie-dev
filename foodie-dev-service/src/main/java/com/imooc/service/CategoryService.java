package com.imooc.service;

import com.imooc.pojo.Category;
import com.imooc.pojo.Vo.CategoryVO;

import java.util.List;

public interface CategoryService {
    /**
     * 查询所有的一级分类
     * @return
     */
    public List<Category> queryAllRootLevelCat();

    //查询二级、三级目录,根据一级目录
    public List<CategoryVO> getSubCarList(Integer rootCatId);

    /**
     * 查询首页每个一级目录下的6个最新商品数据
     * @param rootCatId
     * @return
     */
    public List getSixNewItemsLazy(Integer rootCatId);
}
