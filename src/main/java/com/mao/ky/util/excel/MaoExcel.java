package com.mao.ky.util.excel;

import com.mao.ky.util.SU;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Excel构造器
 * @author : create by zongx at 2020/12/23 18:36
 */
public class MaoExcel {

    private static final String EXCEL_2007 = "xlsx";

    public static MaoExcel getInstance() {
        return new MaoExcel();
    }

    /**
     * 数据写入到Excel中
     * 并将数据写入到HttpServletResponse中，以文件流形式返回
     * @param response HttpServletResponse
     * @param title 工作簿名称
     * @param list 数据列表
     * @param src 数据类
     */
    public <T> void writeExcel(HttpServletResponse response, String title, List<T> list, Class<T> src) {
        //获取可用作Excel数据转换的类成员属性
        List<Field> fields = getFields(src);
        //创建Excel工作簿
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(title);
        //创建标题
        AtomicInteger ai = new AtomicInteger();
        Row row = sheet.createRow(ai.getAndIncrement());
        AtomicInteger aj = new AtomicInteger();
        fields.forEach(field -> {
            ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
            Cell cell = row.createCell(aj.getAndIncrement(), CellType.STRING);
            //设置文字和样式
            CellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            Font font = workbook.createFont();
            font.setBold(true);
            cellStyle.setFont(font);
            cell.setCellStyle(cellStyle);
            //设置值
            cell.setCellValue(annotation.title());
        });
        //写入数据
        if (null != list && list.size() > 0) {
            list.forEach(data -> {
                Row dataRow = sheet.createRow(ai.getAndIncrement());
                AtomicInteger ajj = new AtomicInteger();
                fields.forEach(field -> {
                    Object value;
                    try {
                        value = field.get(data);
                    } catch (IllegalAccessException e) {
                        value = "";
                    }
                    Cell dataCell = dataRow.createCell(ajj.getAndIncrement());
                    dataCell.setCellValue(null == value ? "" : value.toString());
                });
            });
        }
        //设置单元默认宽度，20单位
        sheet.setDefaultColumnWidth(20);
        //冻结窗口
        workbook.getSheet(title).createFreezePane(0, 1, 0, 1);
        //写入Response
        String fileName = SU.randomStr(40) + "." + EXCEL_2007;
        writeStream(response, workbook, fileName);
    }

    /**
     * Excel写入Response
     */
    private void writeStream(HttpServletResponse response, Workbook workbook, String fileName) {
        try {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(fileName, "utf-8"));
            response.flushBuffer();
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取可用作Excel数据转换的类成员属性
     */
    private <T> List<Field> getFields(Class<T> src) {
        return Arrays
                //获取类成员属性，以流的方式处理
                .stream(src.getDeclaredFields())
                //过滤没有注解的成员属性，过滤没有注明在Excel位置的成员属性
                .filter(field -> {
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (null != annotation && annotation.col() > 0) {
                        field.setAccessible(true);
                        return true;
                    }
                    return false;
                })
                //根据col排序
                .sorted(Comparator.comparing(field -> {
                    int col = 0;
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (null != annotation) {
                        col = annotation.col();
                    }
                    return col;
                }))
                //转化为List集合
                .collect(Collectors.toList());
    }

}
