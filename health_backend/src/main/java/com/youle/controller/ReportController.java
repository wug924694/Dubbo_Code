package com.youle.controller;

        import com.alibaba.dubbo.config.annotation.Reference;
        import com.youle.constant.MessageConstant;
        import com.youle.entiy.Result;
        import com.youle.service.ISetmealService;
        import com.youle.service.MemberService;
        import com.youle.service.ReportService;
        import net.sf.jasperreports.engine.JasperCompileManager;
        import net.sf.jasperreports.engine.JasperExportManager;
        import net.sf.jasperreports.engine.JasperFillManager;
        import net.sf.jasperreports.engine.JasperPrint;
        import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
        import org.apache.poi.xssf.usermodel.XSSFRow;
        import org.apache.poi.xssf.usermodel.XSSFSheet;
        import org.apache.poi.xssf.usermodel.XSSFWorkbook;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

        import javax.servlet.ServletOutputStream;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.File;
        import java.math.BigDecimal;
        import java.text.SimpleDateFormat;
        import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;
    @Reference
    private ISetmealService setmealService;
    @Reference
    private ReportService reportService;

    //每月会员数条形图
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        try {
            Map<String, Object> map = new HashMap<>();
            List<String> months = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            //获得当前日期之前12个月的日期
            calendar.add(Calendar.MONTH, -12);
            for (int i = 0; i < 12; i++) {
                calendar.add(Calendar.MONTH, 1);
                months.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
            }
            map.put("months", months);
            List<Integer> memberCount = memberService.findMemberCountByMonth(months);
            map.put("memberCount", memberCount);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }


    //套餐占比饼形图
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        try {
            Map<String, Object> map = new HashMap<>();
            List<Map<String, Object>> setMealCount = setmealService.findSetMealCount();
            map.put("setmealCount", setMealCount);
            List<String> setMealNames = new ArrayList<>();
            for (Map<String, Object> m : setMealCount) {
                String name = (String) m.get("name");
                setMealNames.add(name);
            }
            map.put("setmealNames", setMealNames);
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    //运营数据的统计
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Map<String, Object> result = reportService.getBusinessReportData();
            //取出返回结果 插入到报表中
            String reportData = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer todayVisitfindMemberCountBeforeDatesNumber = (Integer) result.get("todayVisitfindMemberCountBeforeDatesNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");
            //动态的获得该文件所在的磁盘路径，     file.separator自动适应操作系统分隔符
            String filePath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
            XSSFWorkbook excel = new XSSFWorkbook(new File(filePath));
            //读取第一个工作表
            XSSFSheet sheet = excel.getSheetAt(0);
            //日期 获取第三行
            XSSFRow row = sheet.getRow(2);
            //获取第六格
            row.getCell(5).setCellValue(reportData);
            //新增会员
            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);
            row.getCell(7).setCellValue(totalMember);

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);
            row.getCell(7).setCellValue(thisMonthNewMember);

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);
            row.getCell(7).setCellValue(todayVisitfindMemberCountBeforeDatesNumber);

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);
            row.getCell(7).setCellValue(thisWeekVisitsNumber);

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            row.getCell(7).setCellValue(thisMonthVisitsNumber);

            int rowNum = 12;

            for (Map map : hotSetmeal) {
                row = sheet.getRow(rowNum++);

                BigDecimal proportion = (BigDecimal) map.get("proportion");

                row.getCell(4).setCellValue((String) map.get("name"));
                row.getCell(5).setCellValue((long) map.get("setmeal_count"));
                row.getCell(6).setCellValue(proportion.doubleValue());
            }

            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-execl");//execl文件类型
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");//指定以附件下载
            excel.write(out);
            out.flush();
            out.close();
            excel.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    @RequestMapping("/exportBusinessReport4PDF")
    public Result exportBusinessReport4PDF(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Map<String, Object> result = reportService.getBusinessReportData();
            //取出返回结果  准备数据插入到报表中
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");
            //基于提供的Excel模板在内存中创建一个Excel表格对象
            String jrxmlPath = request.getSession().getServletContext().getRealPath("template")+ File.separator+"health_business3.jrxml";
            String jasperPath = request.getSession().getServletContext().getRealPath("template") +File.separator + "health_business3.jasper";

            //编译模板
            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
            //填充数据---使用JavaBean数据源方式填充
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperPath,result,new JRBeanCollectionDataSource(hotSetmeal));

            //使用输出流进行表格下载 基于浏览器作为客户端下载
            ServletOutputStream os = response.getOutputStream();
            //设置mime类型
            response.setContentType("application/pdf");
            response.setHeader("content-Disposition","attachment;filename=report.pdf");
            //输出文件
            JasperExportManager.exportReportToPdfStream(jasperPrint,os);

            os.flush();
            os.close();

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
}
