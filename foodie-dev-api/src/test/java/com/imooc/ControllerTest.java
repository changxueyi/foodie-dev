package com.imooc;

import com.imooc.mapper.CategoryMapper;
import com.imooc.pojo.Category;
import com.imooc.pojo.Stu;
import com.imooc.service.CategoryService;
import com.imooc.service.StuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.mapper.entity.Example;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

/**
 * @ClassName com.imooc.Mytest
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/3/29 14:38
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {
    @Autowired
    DataSource dataSource;
    @Autowired
    private StuService stuService;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryService categoryService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());//对象方式初始化Log对象

    @Test
    public void contextLoads() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
        System.out.println("恭喜你，运行成功了");
    }

    @Test
    public void test01() {
        Stu stu = stuService.getStuInfo(11);
        System.out.println(stu.toString());
    }

    @Test
    public void test02() {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", 1);

        List<Category> result = categoryMapper.selectByExample(example);
        System.out.println(result.toString());
    }

    @Test
    public void test03() {
        Example example = new Example(Category.class);
        example.orderBy("id");
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type",1);
        List<Category> result  =  categoryMapper.selectByExample(example);
        System.out.println(result.toString());

    }

    @Test
    public void test04(){
        List list = categoryService.getSixNewItemsLazy(7);
        System.out.println(list.toString());
    }

}