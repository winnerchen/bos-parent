package cn.chen.bos.utils.test;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by hasee on 2017/8/1.
 */
public class PdfTest {
    public static void main(String[] args) throws Exception {
        Document document = new Document();
        FileOutputStream outputStream = new FileOutputStream(new File("f:/bb/firstPdf.pdf"));
        Paragraph paragraph = new Paragraph("My first paragraph");
        Image image = Image.getInstance("f:/bb/m.jpg");
        PdfWriter.getInstance(document, outputStream);
        document.open();
        document.add(paragraph);
        document.add(image);
        document.close();
    }
}
