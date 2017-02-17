import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;

public class Main {
	public static void main(String[] args) throws IOException {
		PDDocument doc = PDDocument.load(new FileInputStream(new File("/home/johncyr/Documents/efs/forms/BuyersGuide/BuyersGuide_noform.pdf")));
		
		PDPage page = new PDPage();
		System.out.println(doc.getPages().getCount());
		page = doc.getPages().get(0);
		
		PDPageContentStream content = new PDPageContentStream(doc, page, AppendMode.APPEND, true, true);
		
		// AS IS
		content.setLineWidth(3);
		content.addRect((float)69.85, (float)532.60, (float)33.15, 36); 
		content.setNonStrokingColor(Color.TRANSLUCENT);
		content.stroke();
		
		// WARRANTY
		content.setLineWidth(3);
		content.addRect((float)69.85, (float)440.25, (float)33.15, 36); 
		content.setNonStrokingColor(Color.TRANSLUCENT);
		content.stroke();
		
		// FULL
		content.setLineWidth(1);
		content.addRect((float)50.5, (float)397.87, (float)28.8, 28); 
		content.setNonStrokingColor(Color.TRANSLUCENT);
		content.stroke();
		
		// LIMITED WARRANTY
		content.setLineWidth(1);
		content.addRect((float)123.84, (float)397.87, (float)28.8, 28); 
		content.setNonStrokingColor(Color.TRANSLUCENT);
		content.stroke();
		
		content.setLineWidth(1);
		content.addRect((float)51.5, (float)113.75, (float)28.8, 28); 
		content.setNonStrokingColor(Color.TRANSLUCENT);
		content.stroke();
		
		content.close();
		
		doc.save("/home/johncyr/Documents/efs/forms/BuyersGuide/results.pdf");
		doc.close();
	}
}
