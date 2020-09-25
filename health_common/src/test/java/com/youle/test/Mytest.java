package com.youle.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Mytest {
   // @Test
    public void test01() throws Exception {
        //加载指定文件
        XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(new File("D:\\a.xlsx")));
        //读取excel文件的第一个标签页
        XSSFSheet sheet = xwb.getSheetAt(0);
        //遍历标签页 获取每一行数据
        for (Row cells : sheet) {
            //遍历行获取每行数据
            for (Cell cell : cells) {
                System.out.println(cell.getCellType());
                if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
                    cell.setCellType(cell.CELL_TYPE_STRING);
                    System.out.println(cell.getStringCellValue());
                }else {
                    System.out.println(cell.getStringCellValue());
                }
            }
        }
        xwb.close();
    }

    //使用POI读取Excel文件中的数据
   // @Test
    public void test2() throws Exception {
        //加载指定的文件  创建一个Excel对象(工作薄)
        XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(new File("D:\\hello.xlsx")));
        //读取Excel文件中的第一个sheet标签页
        XSSFSheet sheet = xwb.getSheetAt(0);
        //获取到当前工作表的最后一个行号  需要注意 行号从0开始  而且获取的行是有数据的行
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 0; i <= lastRowNum; i++) {
            XSSFRow row = sheet.getRow(i);
            //获取当前行的最后一个单元格索引   索引从1开始  实际计算从0开始
            short lastCellNum = row.getLastCellNum();
            for (int j = 0; j < lastCellNum; j++) {
                //根据每一个单元格索引获取每个单元格对象
                XSSFCell cell = row.getCell(j);
                if(cell.getCellType() == cell.CELL_TYPE_NUMERIC){
                    cell.setCellType(cell.CELL_TYPE_STRING);
                    System.out.println(cell.getStringCellValue());
                    //System.out.println(cell.getNumericCellValue()+"***");
                }else{
                    System.out.println(cell.getStringCellValue());
                }
            }
        }
        //关闭资源
        xwb.close();
    }

    //使用POI向Excel表格中写数据
   // @Test
    public void test3() throws Exception {
        //在内存中创建一个Excel文件(工作薄)
        XSSFWorkbook excel = new XSSFWorkbook();
        //创建一个sheet标签页对象
        XSSFSheet sheet = excel.createSheet("优乐");
        //在工作表中创建行对象
        XSSFRow title = sheet.createRow(0);
        //在行中创建单元格对象
        title.createCell(0).setCellValue("姓名");
        title.createCell(1).setCellValue("地址");
        title.createCell(2).setCellValue("年龄");
        XSSFRow dataRow = sheet.createRow(1);
        dataRow.createCell(0).setCellValue("小明");
        dataRow.createCell(1).setCellValue("成都");
        dataRow.createCell(2).setCellValue("20");

        //创建一个输出流  通过输出流将内存中Excel文件写到磁盘中
        FileOutputStream fos = new FileOutputStream(new File("d:\\world.xlsx"));
        excel.write(fos);
        //释放资源
        fos.close();
        excel.close();
    }

}
