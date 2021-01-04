package com.mao.ky.util.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 * 页眉页脚控件
 * @author : create by zongx at 2020/12/23 10:58
 */
class HeaderFooter extends PdfPageEventHelper {

    private String name;
    // 总页数
    PdfTemplate totalPage;
    Font font;

    public HeaderFooter(BaseFont baseFont, String name) {
        this.font = new Font(baseFont, 8, Font.NORMAL);
        this.name = name;
    }

    // 打开文档时，创建一个总页数的模版
    public void onOpenDocument(PdfWriter writer, Document document) {
        PdfContentByte cb =writer.getDirectContent();
        totalPage = cb.createTemplate(30, 16);
    }

    // 一页加载完成触发，写入页眉和页脚
    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable table = new PdfPTable(3);
        try {
            table.setTotalWidth(PageSize.A4.getWidth() - 100);
            table.setWidths(new int[] { 24, 24, 3});
            table.setLockedWidth(true);
            table.getDefaultCell().setFixedHeight(-10);
            table.getDefaultCell().setBorder(Rectangle.BOTTOM);

            // 可以直接使用addCell(str)，不过不能指定字体，中文无法显示
            table.addCell(new Paragraph(this.name, this.font));
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            //设置当前多少页
            table.addCell(new Paragraph(String.valueOf(writer.getPageNumber()), this.font));
            // 总页数
            PdfPCell cell = new PdfPCell(Image.getInstance(totalPage));
            cell.setBorder(Rectangle.BOTTOM);
            table.addCell(cell);
            // 将页眉写到document中，位置可以指定，指定到下面就是页脚
            table.writeSelectedRows(0, -1, 50,PageSize.A4.getHeight() - 20, writer.getDirectContent());
        } catch (Exception de) {
            throw new ExceptionConverter(de);
        }
    }

    // 全部完成后，将总页数的pdf模版写到指定位置
    public void onCloseDocument(PdfWriter writer, Document document) {
        //设置总共多少页
        String text = String.valueOf(writer.getPageNumber());
        ColumnText.showTextAligned(totalPage, Element.ALIGN_LEFT, new Paragraph(text,this.font), 2, 2, 0);
    }

}
