package com.mao.ky.util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * PDF构造器
 * 构造器提供PDF文件创建，并通过HTTP方式返回文件流
 * 该构造器为固定构造器
 * PDF内容需自己创建。
 * 可创建的内容：
 * 一级标题
 * 二级标题
 * 提示信息
 * 表格
 * 文本段落
 * 详情见代码。
 * 内容构建器对已定义的内容分类的参数已定死，不能修改。（颜色，对齐方式除外）
 * 如果需要更复杂的内容格式，请自行代码解决
 * @author : create by zongx at 2020/12/23 10:25
 */
public class MaoPdf {

    //基础字体名称：宋体
    private static final String FONT_NAME = "STSong-Light";
    //基础字体编码
    private static final String FONT_ENCODING = "UniGB-UCS2-H";

    private BaseFont baseFont;

    public static MaoPdf getInstance() {
        return new MaoPdf();
    }

    public MaoPdf() {
        try {
            baseFont = BaseFont.createFont(MaoPdf.FONT_NAME, MaoPdf.FONT_ENCODING, BaseFont.NOT_EMBEDDED);
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * PDF文件创建 并输出到HttpServletResponse中
     * @param response HttpServletResponse
     * @param info PDF基本信息
     */
    public void exportPdf(HttpServletResponse response, PdfInfo info, List<Element> list) throws IOException, DocumentException {
        //PDF文档
        Document document = new Document(PageSize.A4);
        //PDF书写器
        PdfWriter pdfWriter = PdfWriter.getInstance(document, response.getOutputStream());
        //添加文档信息
        pdfBase(document, pdfWriter, info);
        //添加数据
        for (Element element : list) {
            document.add(element);
        }
        //response设置
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode(info.getName(),"utf-8"));
        response.flushBuffer();
        //关闭文档
        document.close();
    }

    /**
     * PDF文件创建 并输出到本地磁盘文件中
     * @param path 文件路径
     * @param info pdf信息
     * @param list 数据列表
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void exportPdf(String path, PdfInfo info, List<Element> list) throws IOException, DocumentException {
        //PDF文档
        Document document = new Document(PageSize.A4);
        File file = new File(path);
        if (!file.exists())
            file.createNewFile();
        //PDF书写器
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(file));
        //添加文档信息
        pdfBase(document, pdfWriter, info);
        //添加数据
        for (Element element : list) {
            document.add(element);
        }
        //关闭文档
        document.close();
    }

    /**
     * 添加一级标题
     */
    public Element makeTitle(String title, BaseColor color, int align) {
        Paragraph paragraph = new Paragraph(title, baseFont(30F, 1, color));
        baseParagraph(paragraph, 20F, 20F, 15F, 15F, 0, 32F, align);
        return paragraph;
    }
    public Element makeTitle(String title, int align) {
        return makeTitle(title, null, align);
    }
    public Element makeTitle(String title, BaseColor color) {
        return makeTitle(title, color, 1);
    }
    public Element makeTitle(String title) {
        return makeTitle(title,null, 1);
    }

    /**
     * 添加二级标题
     */
    public Element makeSubTitle(String title, BaseColor color, int align) {
        Paragraph paragraph = new Paragraph(title, baseFont(22F, 1, color));
        baseParagraph(paragraph, 15F, 15F, 15F, 15F, 0, 28F, align);
        return paragraph;
    }
    public Element makeSubTitle(String title, int align) {
        return makeSubTitle(title, null, align);
    }
    public Element makeSubTitle(String title, BaseColor color) {
        return makeSubTitle(title, color, 1);
    }
    public Element makeSubTitle(String title) {
        return makeSubTitle(title,null, 1);
    }

    /**
     * 添加提示字段
     */
    public Element makeTip(String tip, BaseColor color, int align) {
        Paragraph paragraph = new Paragraph(tip, baseFont(13F, 0, color));
        baseParagraph(paragraph, 10F, 10F, 10F, 10F, 0, 16F, align);
        return paragraph;
    }
    public Element makeTip(String tip, int align) {
        return makeTip(tip,BaseColor.GRAY, align);
    }
    public Element makeTip(String tip, BaseColor color) {
        return makeTip(tip,color, 1);
    }
    public Element makeTip(String tip) {
        return makeTip(tip,BaseColor.GRAY, 1);
    }

    /**
     * 添加段落
     */
    public Element makeParagraph(String content, BaseColor color, boolean indent, int style) {
        Paragraph paragraph = new Paragraph(content, baseFont(14F, style, color));
        float indentCount = indent ? 24F : 0F;
        baseParagraph(paragraph, 10F, 10F, 10F, 10F, indentCount, 18F, 0);
        return paragraph;
    }
    public Element makeParagraph(String content, int style) {
        return makeParagraph(content, null, true, style);
    }
    public Element makeParagraph(String content, boolean indent) {
        return makeParagraph(content, null, indent, 0);
    }
    public Element makeParagraph(String content, BaseColor color) {
        return makeParagraph(content, color, true, 0);
    }
    public Element makeParagraph(String content) {
        return makeParagraph(content, null, true, 0);
    }

    /**
     * 添加小块
     */
    public Element makePhrase(String content, BaseColor color) {
        Phrase phrase = new Phrase(content, baseFont(14, 0, color));
        phrase.setLeading(18F);
        return phrase;
    }
    public Element makePhrase(String content) {
        return makePhrase(content, null);
    }

    /**
     * 段落基本样式
     */
    private void baseParagraph(Paragraph paragraph,
                      float top, float bottom, float left, float right,
                      float indent, float lead, int align) {
        //设置上下间距
        paragraph.setSpacingBefore(top);
        paragraph.setSpacingAfter(bottom);
        //设置左右间距
        paragraph.setIndentationLeft(left);
        paragraph.setIndentationRight(right);
        //设置首航缩进
        paragraph.setFirstLineIndent(indent);
        //设置行间距
        paragraph.setLeading(lead);
        //设置对齐方式 0：左对齐，1：居中，2：右对齐
        paragraph.setAlignment(align);
    }

    /**
     * 添加表格
     * 添加表格需要与注解PdfColumn一起使用
     * @see PdfColumn Pdf单元格字段注解
     * 注解中注明标题、序号、单元格百分比。
     */
    public <T> Element makeTable(List<T> list, Class<T> src) {
        //获取数字字段
        List<Field> fields = getFields(src);
        //创建表格
        PdfPTable table = new PdfPTable(fields.size());
        //跨页处理
        table.setSplitLate(false);
        table.setSplitRows(true);
        //设置表格宽度比例为100%
        table.setWidthPercentage(100);
        float totalWidth = PageSize.A4.getWidth() - 20F;
        //求每一个单元格的宽度
        float[] cellWidth = new float[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            PdfColumn annotation = field.getAnnotation(PdfColumn.class);
            cellWidth[i] = totalWidth * annotation.percent() / 100;
        }
        //设置宽度组（设置方法会抛出异常）
        try {
            table.setTotalWidth(cellWidth);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        //锁住宽度
        table.setLockedWidth(true);
        //设置上下间距
        table.setSpacingBefore(20F);
        table.setSpacingAfter(20F);
        //设置边框
        table.getDefaultCell().setBorder(1);
        //创建每个单元格
        addCell(table, list, fields);

        return table;
    }

    /**
     * 向表格中添加单元格
     */
    private <T> void addCell(PdfPTable table, List<T> list, List<Field> fields) {
        Font titleFont = baseFont(12F, 1, null);
        Font dataFont = baseFont(12F, 0, null);
        //添加标题
        fields.forEach(field -> {
            PdfColumn annotation = field.getAnnotation(PdfColumn.class);
            PdfPCell cell = new PdfPCell(new Paragraph(annotation.title(),titleFont));
            titleCellStyle(cell);
            table.addCell(cell);
        });
        //添加数据
        if (null != list && list.size() > 0) {
            list.forEach(data -> fields.forEach(field -> {
                Object value;
                try {
                    value = field.get(data);
                } catch (IllegalAccessException e) {
                    value = "";
                }
                Paragraph paragraph = new Paragraph(null == value ? "" : value.toString(), dataFont);
                PdfPCell cell = new PdfPCell(paragraph);
                dataCellStyle(cell);
                table.addCell(cell);
            }));
        }
    }

    /**
     * 标题单元格样式：
     * 边框、浅灰底、居中
     */
    private void titleCellStyle(PdfPCell cell) {
        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setBackgroundColor(new BaseColor(220, 220, 220));
        cell.setMinimumHeight(20F);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    }

    /**
     * 数据单元格样式：
     * 边框、白底、居左2F
     */
    private void dataCellStyle(PdfPCell cell) {
        cell.setBorder(Rectangle.BOX);
        cell.setBorderColor(BaseColor.BLACK);
        cell.setMinimumHeight(20F);
        cell.setPaddingLeft(2F);
    }

    /**
     * 获取统计数据的字段
     */
    private <T> List<Field> getFields(Class<T> src) {
        return Arrays
                //获取类成员属性，以流的方式处理
                .stream(src.getDeclaredFields())
                //过滤没有注解的成员属性，过滤没有注明在Excel位置的成员属性
                .filter(field -> {
                    PdfColumn annotation = field.getAnnotation(PdfColumn.class);
                    if (null != annotation && annotation.col() > 0 && annotation.percent() > 0) {
                        field.setAccessible(true);
                        return true;
                    }
                    return false;
                })
                //根据col排序
                .sorted(Comparator.comparing(field -> {
                    int col = 0;
                    PdfColumn annotation = field.getAnnotation(PdfColumn.class);
                    if (null != annotation) {
                        col = annotation.col();
                    }
                    return col;
                }))
                //转化为List集合
                .collect(Collectors.toList());
    }

    /**
     * 字体基本样式
     */
    private Font baseFont(float size, int style, BaseColor color) {
        //font-style 字体样式 0：正常，1：加粗，2：斜体
        Font font = new Font(this.baseFont, size, style);
        if (null != color) {
            font.setColor(color);
        }
        return font;
    }

    /**
     * 设置文档基本信息
     */
    private void pdfBase(Document document, PdfWriter writer, PdfInfo info) {
        //水印
        if (canWrite(info.getWatermark())) {
            writer.setPageEvent(new WaterMark(info.getWatermark()));
        }
        //页眉页脚
        if (canWrite(info.getHeader())) {
            writer.setPageEvent(new HeaderFooter(this.baseFont, info.getHeader()));
        }
        //打开文档
        document.open();
        //标题（文件信息）
        if (canWrite(info.getTitle())) {
            document.addTitle(info.getTitle());
        }
        //作者（文件信息）
        if (canWrite(info.getAuthor())) {
            document.addAuthor(info.getAuthor());
        }
        //摘要（文件信息）
        if (canWrite(info.getSubject())) {
            document.addSubject(info.getSubject());
        }
        //关键词（文件信息）
        if (canWrite(info.getKeyword())) {
            document.addKeywords(info.getKeyword());
        }
        //创建者（文件信息）
        if (canWrite(info.getCreator())) {
            document.addCreator(info.getCreator());
        }
    }

    private boolean canWrite(String str) {
        return null != str && str.length() > 0;
    }

}
