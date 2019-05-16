package com.bs.wms.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {
    /** 设置文字水印
     * @param sourceImg 源图片路径
     * @param targetImg 保存的图片路径
     * @param watermark 水印内容
     * @param color 水印颜色
     * @param font 水印字体
     * @throws IOException
     */
    public static void addWatermark(String sourceImg, String targetImg, String watermark, Color color, Font font) throws IOException {
        File srcImgFile = new File(sourceImg);
        Image srcImg = ImageIO.read(srcImgFile);
        int srcImgWidth = srcImg.getWidth(null);
        int srcImgHeight = srcImg.getHeight(null);
        BufferedImage buffImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();
        g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
        g.setColor(color);
        g.setFont(font);
        g.rotate(Math.toRadians(-40), (double) buffImg.getWidth() / 2, (double) buffImg.getHeight() / 2);
        //设置水印的坐标.
        /*int x = srcImgWidth - (g.getFontMetrics(g.getFont()).charsWidth(watermark.toCharArray(), 0, watermark.length())+20);
        int y = srcImgHeight - 25;*/
        int x = -srcImgWidth / 2;
        int y = -srcImgHeight / 2;
        int markWidth = 30 * getTextLength (watermark);// 字体长度
        int markHeight = 30;// 字体高度
        // 循环添加水印
        while (x < srcImgWidth * 1.5) {
            y = -srcImgHeight / 2;
            while (y < srcImgHeight * 1.5) {
                g.drawString(watermark, x, y);
                y += markHeight + 80;
            }
            x += markWidth + 80;
        }
//        g.drawString(watermark, x, y);  //加水印
        g.dispose();
        // 输出图片
        FileOutputStream outImgStream = new FileOutputStream(targetImg);
        ImageIO.write(buffImg, "jpg", outImgStream);
        System.out.println("添加水印完成");
        outImgStream.flush();
        outImgStream.close();
    }

    /**
     * 获取文本长度。汉字为1:1，英文和数字为2:1
     */
    private static int getTextLength (String text) {
        int length = text.length ();
        for (int i = 0; i < text.length (); i++) {
            String s = String.valueOf (text.charAt (i));
            if (s.getBytes ().length > 1) {
                length++;
            }
        }
        length = length % 2 == 0 ? length / 2 : length / 2 + 1;
        return length;
    }

    public static void main(String[] args) throws IOException {
        Font font = new Font(null, Font.PLAIN, 30);//字体
        String sourceImg = "E:\\img\\old.jpg"; //源图片地址
        String targetImg = "E:\\img\\new.jpg"; //新存储的地址
        String watermark = "长江时代网厅申请";//水印内容
        Color color = Color.BLACK;
        addWatermark(sourceImg, targetImg, watermark, color, font);
    }
}
