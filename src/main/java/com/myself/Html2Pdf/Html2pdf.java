package com.myself.Html2Pdf;

import com.lowagie.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Html2pdf {


    public static void main(String[] args) {
        try {
            convertHtmlToPdf("","E:/123.pdf");
        }catch (Exception e){
            System.out.println(e);
        }
    }

    public static  boolean convertHtmlToPdf(String inputFile, String outputFile)
            throws Exception {

        OutputStream os = new FileOutputStream(outputFile);
        ITextRenderer renderer = new ITextRenderer();
        String url = new File(inputFile).toURI().toURL().toString();

        renderer.setDocument(url);

        // 解决中文支持问题
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont("simsun.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        //解决图片的相对路径问题
        renderer.getSharedContext().setBaseURL("");
        renderer.layout();
        renderer.createPDF(os);

        os.flush();
        os.close();
        return true;
    }
}
