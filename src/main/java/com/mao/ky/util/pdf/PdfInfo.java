package com.mao.ky.util.pdf;

import lombok.Getter;
import lombok.Setter;

/**
 * @author : create by zongx at 2020/12/23 11:15
 */
@Getter
@Setter
public class PdfInfo {
    private String name;            //文件名称
    private String watermark;       //水印
    private String header;          //页眉
    private String title;           //标题
    private String author;          //作者
    private String subject;         //摘要
    private String keyword;         //关键词
    private String creator;         //创建者
}
