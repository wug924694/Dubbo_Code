import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class Test01 {

    public static void main(String[] args) throws IOException, TemplateException {
        //创建模板
        Configuration configuration = new Configuration(Configuration.getVersion());
        //设置模板目录
        configuration.setDirectoryForTemplateLoading(new File("E:\\develop\\ideaCode\\Dubbo_Code\\FreemarkerDemo\\src\\main\\resources"));
        //设置编码格式
        configuration.setDefaultEncoding("utf-8");
        //获取模板对象
        Template template = configuration.getTemplate("test.ftl");
        //创建数据
        Map map = new HashMap();
        map.put("name","小王");
        map.put("message","我是大王");
        map.put("success",true);

        List list = new ArrayList();
        Map m1 = new HashMap();
        m1.put("name","张三");
        m1.put("grades",100);

        Map m2 = new HashMap();
        m2.put("name","李四");
        m2.put("grades",80);

        Map m3 = new HashMap();
        m3.put("name","王五");
        m3.put("grades",95);

        list.add(m1);
        list.add(m2);
        list.add(m3);

        map.put("list",list);

        map.put("today",new Date());

        map.put("point",12313132);

        //创建输出流对象
        Writer writer = new FileWriter("e:\\test.html");
        //输出
        template.process(map, writer);
        //关闭流
        writer.close();
    }
}
