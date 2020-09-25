package com.youle.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.youle.constant.RedisConstant;
import com.youle.dao.ISetmealDao;
import com.youle.entiy.PageResult;
import com.youle.entiy.QueryPageBean;
import com.youle.pojo.Setmeal;
import com.youle.service.ISetmealService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import redis.clients.jedis.JedisPool;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ISetmealService.class)
@Transactional
public class SetmealServiceImpl implements ISetmealService {
    @Autowired
    private ISetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    //从属性文件读取输出目录的路径
    @Value("${out_put_path}")
    private String outputpath;


    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        Integer setmealId = setmeal.getId();
        setSetmealAndCheckGroup(setmealId, checkgroupIds);
        //将图片名称存储到redis
        String imgName = setmeal.getImg();
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, imgName);

        //新增套餐hou重新生成静态页面
        generateMobileStaticHtml();
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> pages = setmealDao.findByCondition(queryString);
        return new PageResult(pages.getTotal(), pages.getResult());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(Integer setmealId) {
        return setmealDao.findCheckGroupIdsBySetmealId(setmealId);
    }

    @Override
    public void edit(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.edit(setmeal);
        Integer setmealId = setmeal.getId();
        setmealDao.deleteAssociation(setmealId);
        setSetmealAndCheckGroup(setmealId, checkgroupIds);
        //新增套餐hou重新生成静态页面
        generateMobileStaticHtml();
    }

    @Override
    public void delete(Integer id) {
        setmealDao.deleteAssociation(id);
        setmealDao.delete(id);
        //新增套餐hou重新生成静态页面
        generateMobileStaticHtml();
    }

    @Override
    public List<Map<String, Object>> findSetMealCount() {
        return setmealDao.findSetMealCount();
    }


    public void setSetmealAndCheckGroup(Integer setmealId, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length >= 0) {
            for (Integer checkgroupId : checkgroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("setmeal_id", setmealId);
                map.put("checkgroup_id", checkgroupId);
                setmealDao.addSetmealAndCheckGroup(map);
            }
        }
    }

    //生成静态页面
    public void generateMobileStaticHtml() {
        //准备模板文件中所需的数据
        List<Setmeal> setmealList = this.findAll();
        //生成套餐列表静态页面
        generateMobileSetmealListHtml(setmealList);
        //生成套餐详情静态页面（多个）
        generateMobileSetmealDetailHtml(setmealList);
    }

    //生成套餐列表静态页面
    public void generateMobileSetmealListHtml(List<Setmeal> setmealList) {
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("setmealList", setmealList);
        this.generateHtml("mobile_setmeal.ftl", "m_setmeal.html", dataMap);
    }

    //生成套餐详情静态页面（多个）
    public void generateMobileSetmealDetailHtml(List<Setmeal> setmealList) {
        for (Setmeal setmeal : setmealList) {
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("setmeal", this.findById(setmeal.getId()));
            this.generateHtml("mobile_setmeal_detail.ftl", "setmeal_detail_" + setmeal.getId() + ".html", dataMap);
        }
    }

    public void generateHtml(String templateName, String htmlPageName, Map<String, Object> dataMap) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        Writer out = null;
        try {
            // 加载模版文件
            Template template = configuration.getTemplate(templateName);
            // 生成数据
            File docFile = new File(outputpath + "\\" + htmlPageName);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            // 输出文件
            template.process(dataMap, out);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }
}
