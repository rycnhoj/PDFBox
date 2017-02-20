import java.awt.Color;
import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationWidget;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAppearanceCharacteristicsDictionary;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDTextField;

public class PdDoc {
	private PDDocument document;
	private String outputPath;
	private PDPage firstPage;
	
	PdDoc(String i, String o) throws InvalidPasswordException, IOException {
		System.out.println("Constructor");
		document = PDDocument.load(new File(i));
		firstPage = document.getPages().get(0);
		outputPath = o;
	}

	public void save() {
		System.out.println("Save");
		try {
			document.save(outputPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() throws IOException {
		System.out.println("Close");
		if (document != null) {
			document.close();
		}
	}
	
	public void createAcroForm () throws IOException {
        PDFont font = PDType1Font.HELVETICA;
        PDResources resources = new PDResources();
        resources.put(COSName.getPDFName("Helv"), font);
		
		// Create a new Acro Form (interactive layer)
		PDAcroForm acroForm = new PDAcroForm(document);
		// Add it to the document
		document.getDocumentCatalog().setAcroForm(acroForm);
        // Add and set the resources and default appearance at the form level
        acroForm.setDefaultResources(resources);
        
        // Add a form field to the form.
        PDTextField textBox = new PDTextField(acroForm);
        textBox.setPartialName("SampleField");
        
        // Acrobat sets the font size on the form level to be
        // auto sized as default. This is done by setting the font size to '0'
        String defaultAppearanceString = "/Helv 0 Tf 0 g";
        acroForm.setDefaultAppearance(defaultAppearanceString);
        
        // Acrobat sets the font size to 12 as default
        // This is done by setting the font size to '12' on the
        // field level. 
        // The text color is set to blue in this example.
        // To use black, replace "0 0 1 rg" with "0 0 0 rg" or "0 g".
        defaultAppearanceString = "/Helv 12 Tf 0 0 1 rg";
        textBox.setDefaultAppearance(defaultAppearanceString);
        
        
        // add the field to the acroform
        acroForm.getFields().add(textBox);
        
        
        // Specify the annotation associated with the field
        PDAnnotationWidget widget = textBox.getWidgets().get(0);
        PDRectangle rect = new PDRectangle(50, 750, 200, 50);
        widget.setRectangle(rect);
        widget.setPage(firstPage);
        
        // set green border and yellow background
        // if you prefer defaults, just delete this code block
        PDAppearanceCharacteristicsDictionary fieldAppearance
                = new PDAppearanceCharacteristicsDictionary(new COSDictionary());
        fieldAppearance.setBorderColour(new PDColor(new float[]{0,1,0}, PDDeviceRGB.INSTANCE));
        fieldAppearance.setBackground(new PDColor(new float[]{1,1,0}, PDDeviceRGB.INSTANCE));
        widget.setAppearanceCharacteristics(fieldAppearance);

        // make sure the annotation is visible on screen and paper
        widget.setPrinted(true);
        
        // Add the annotation to the page
        firstPage.getAnnotations().add(widget);
        
        // set the field value
        textBox.setValue("Sample field");

	}
	
	public void drawBoxesOnFirstPage() throws IOException {
		System.out.println("drawBoxesOnFirstPage");

		PDPageContentStream content = new PDPageContentStream(document, firstPage, AppendMode.APPEND, true, true);

		// AS IS
		content.setLineWidth(3);
		content.addRect((float) 69.85, (float) 532.60, (float) 33.15, 36);
		content.setNonStrokingColor(Color.TRANSLUCENT);
		content.stroke();

		// WARRANTY
		content.setLineWidth(3);
		content.addRect((float) 69.85, (float) 440.25, (float) 33.15, 36);
		content.setNonStrokingColor(Color.TRANSLUCENT);
		content.stroke();

		// FULL
		content.setLineWidth(1);
		content.addRect((float) 50.5, (float) 397.87, (float) 28.8, 28);
		content.setNonStrokingColor(Color.TRANSLUCENT);
		content.stroke();

		// LIMITED WARRANTY
		content.setLineWidth(1);
		content.addRect((float) 123.84, (float) 397.87, (float) 28.8, 28);
		content.setNonStrokingColor(Color.TRANSLUCENT);
		content.stroke();

		content.setLineWidth(1);
		content.addRect((float) 51.5, (float) 113.75, (float) 28.8, 28);
		content.setNonStrokingColor(Color.TRANSLUCENT);
		content.stroke();

		content.close();
	}
}

