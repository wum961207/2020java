package com.lemon.easyApiTest.Utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.lemon.easyApiTest.pojo.CaseInfo;
import com.lemon.easyApiTest.pojo.WriteBackData;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ExcelUtils {
    //批量存储集合
    public static List<WriteBackData> wbdlist = new ArrayList<>();

    /**
     * 读取Excel数据并封装到指定对象中
     * @param sheetIndex  开始sheet索引
     * @param sheetNum    sheet个数
     * @param clazz       EXCEL映射字节对象
     * @param path        Excel所在路径
     * @return
     */
    public static List read(int sheetIndex,int sheetNum,Class clazz,String path) {
        List<CaseInfo> caseInfos = null;
        try {
            //1.excel文件流
            FileInputStream fis = new FileInputStream(path);
            System.out.println(path);
            //2.easyapi导入参数
            ImportParams params = new ImportParams();
            params.setStartSheetIndex(sheetIndex);
            params.setSheetNum(sheetNum);//设置读取sheet的数量
            //3.导入importExcel(excel文件流，映射关系字节码对象，导入参数)
            caseInfos = ExcelImportUtil.importExcel(fis, clazz, params);
            //4.关流
            fis.close();
            return caseInfos;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return caseInfos;
    }

    /**
     * 读取config文件
     */
    public static String readConfig() throws Exception{
        Properties properties = new Properties();
        // 使用InPutStream流读取properties文件
        FileInputStream fis = new FileInputStream("G:\\JavaTest\\code\\java20_maven1\\src\\test\\resources\\config.properties");
        properties.load(fis);
        // 获取key对应的value值
        String path = properties.getProperty("path");
        fis.close();
        return path;
    }

    /**
     * 批量回写
     */
    public static void batchWrite() throws Exception{
        //回写的逻辑：变量wbdlist集合，去除sheetIndex和sheetNum，cellNum，content
        FileInputStream fis = new FileInputStream(readConfig());
        //获取所有sheet
        Workbook sheets = WorkbookFactory.create(fis);
        //循环遍历对应的sheet对象
        for(WriteBackData wbd : wbdlist){
            int sheetIndex = wbd.getSheetIndex();
            int rowNum = wbd.getRowNum();
            int cellNum = wbd.getCellNum();
            String content = wbd.getContent();
            //获取对应的sheet对象
            Sheet sheet = sheets.getSheetAt(sheetIndex);
            //获取对应的row对象
            Row row = sheet.getRow(rowNum);
            //获取对应cell对象
            Cell cell = row.getCell(cellNum);
            //回写内容
            System.out.println(wbd.toString());
            cell.setCellValue(content);
        }
        //回写到Excel文件中
        FileOutputStream fos = new FileOutputStream(readConfig());
        sheets.write(fos);
        fis.close();
        fos.close();
    }
}
