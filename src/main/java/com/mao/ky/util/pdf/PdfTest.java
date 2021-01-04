package com.mao.ky.util.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.mao.ky.util.FileUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author : create by zongx at 2020/12/24 11:05
 */
public class PdfTest {

    //文本模板规则数据
    private static final String[] elements = new String[] {"header","author","title","sub-title","tip","paragraph"};
    private static final String specialColor = "colorful";
    private static final List<BaseColor> colors = new ArrayList<>();

    static {
        colors.add(new BaseColor(205, 102, 0));
        colors.add(new BaseColor(139, 10, 80));
        colors.add(new BaseColor(131, 139, 131));
        colors.add(new BaseColor(39, 64, 139));
        colors.add(new BaseColor(28, 28, 28));
    }

    /**
     * PDF参考
     */
    public static void main(String[] args) throws Exception {
        //文件路径
        String filePath = "C:\\Users\\zongx\\Desktop\\aa.pdf";

        //读取文件
        List<String> stringList = FileUtil.getFileString("/docs/txt/pdf.txt", FileUtil.CLASSPATH);
        if (null == stringList || stringList.size() <= 0)
            throw new Exception("文件无数据");
        //转换为一个模板名称对应一个数据列表的格式
        Map<String, List<String>> map = transPdfFile(stringList);
        if (map.isEmpty())
            throw new Exception("获取数据失败");

        //header author
        AtomicReference<String> header = new AtomicReference<>("");
        AtomicReference<String> author = new AtomicReference<>("");

        MaoPdf maoPdf = new MaoPdf();
        //文本信息
        List<Element> list = new ArrayList<>();

        map.forEach((k,v) -> {
            //分离出标题
            String model = k.substring(1, k.indexOf("]"));
            switch (model) {
                case "header":
                    header.set(k);
                    break;
                case "author":
                    author.set(k);
                    break;
                case "title":
                case "sub-title":
                case "tip":
                case "paragraph":
                    Map<String, String> style = getStyle(k);
                    addElement(maoPdf, list, style, v, model);
                    break;
            }
        });

        //PDF信息
        PdfInfo info = new PdfInfo();
        info.setTitle(header.get());
        info.setHeader(header.get());
        info.setAuthor(author.get());

        //创建PDF文件
        maoPdf.exportPdf(filePath, info, list);
    }

    private static void addElement(MaoPdf maoPdf, List<Element> list, Map<String, String> style, List<String> data, String model) {
        style.forEach((k,v) -> {
            System.out.println(k);
            System.out.println(v);
        });
        //是否随机颜色标识
        boolean colorful = false;
        //颜色
        BaseColor color;
        Random random = new Random();
        String colorStyle = style.get("color");
        if (null == colorStyle || colorStyle.length() <= 0)
            color = BaseColor.BLACK;
        else if (colorStyle.equals(specialColor)) {
            colorful = true;
            color = null;
        } else {
            String[] split = colorStyle.split(" ");
            color = new BaseColor(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }
        //对齐方式
        int align = 1;
        String alignValue = style.get("align");
        if (null != alignValue) {
            align = Integer.parseInt(alignValue);
        }
        //字体样式
        int style2 = 1;
        String styleValue = style.get("style");
        if (null != styleValue) {
            style2 = Integer.parseInt(styleValue);
        }
        //数据遍历
        for (String s : data) {
            BaseColor useColor = colorful ? colors.get(random.nextInt(colors.size())) : color;
            switch (model) {
                case "title":
                    list.add(maoPdf.makeTitle(s, useColor, align));
                    break;
                case "sub-title":
                    list.add(maoPdf.makeSubTitle(s, useColor, align));
                    break;
                case "tip":
                    list.add(maoPdf.makeTip(s, useColor, align));
                    break;
                case "paragraph":
                    list.add(maoPdf.makeParagraph(s, useColor, true, style2));
            }
        }
    }

    private static Map<String, String> getStyle(String model) {
        Map<String, String> map = new HashMap<>();
        if (model.contains("(") && model.contains(")")) {
            String styles = model.substring(model.indexOf("(") + 1, model.indexOf(")"));
            if (styles.contains(",")) {
                String[] styleList = styles.split(",");
                for (String s : styleList) {
                    addStyle(map,s);
                }
            } else {
                addStyle(map, styles);
            }
        }
        return map;
    }

    private static void addStyle(Map<String, String> map, String s) {
        if (s.contains(":")) {
            String[] split = s.split(":");
            if (split.length == 2) {
                String styleName = split[0];
                String styleValue = split[1];
                switch (styleName) {
                    case "color":
                        if (styleValue.equals(specialColor)) {
                            map.put(styleName, specialColor);
                        } else if (styleValue.contains(" ")) {
                            String[] colors = styleValue.trim().split(" ");
                            if (colors.length == 3 && isNumber(colors[0]) && isNumber(colors[1]) && isNumber(colors[2])) {
                                map.put(styleName, styleValue);
                            }
                        }
                        break;
                    case "align":
                    case "style":
                        if (isNumber(styleValue)) {
                            map.put(styleName,styleValue);
                        }
                        break;
                }
            }
        }
    }

    private static boolean isNumber(String s) {
        try {
            int i = Integer.parseInt(s);
            return i >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static Map<String, List<String>> transPdfFile(List<String> list) {
        Map<String, List<String>> map = new LinkedHashMap<>();
        int index = 0;
        String model = "";
        List<String> modelList = new ArrayList<>();
        while (index < list.size()) {
            String s = list.get(index);
            if (null != s && s.length() > 0) {
                if (isModel(s)) {
                    //模板标识行
                    if (model.length() > 0 && list.size() > 0) {
                        List<String> copyList = new ArrayList<>(modelList);
                        map.put(model, copyList);
                    }
                    model = s;
                    modelList.clear();
                } else {
                    //数据行
                    modelList.add(s);
                }
                index ++;
            }
        }
        //收尾工作
        if (model.length() > 0 && list.size() > 0) {
            List<String> copyList = new ArrayList<>(modelList);
            map.put(model, copyList);
        }
        return map;
    }

    /**
     * 判断是不是模板名称
     */
    private static boolean isModel(String model) {
        if (model.contains("[") && model.contains("]") && (model.endsWith("]") || model.endsWith(")"))) {
            String modelName = model.substring(1,model.indexOf("]"));
            for (String element : elements) {
                if (modelName.equals(element))
                    return true;
            }
        }
        return false;
    }

}
