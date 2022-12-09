package Utls;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfReader  {
	
	
	
	public static String verifyPdf(String path) throws Exception 
	{
		File Pdffile = new File(path); 
		
		PDDocument doc = PDDocument.load(Pdffile);
		PDFTextStripper textfile = new PDFTextStripper();
		String TextRead= textfile.getText(doc);

		System.out.println("File is fetched. ");
		
		return TextRead;
	}
	
	
	//Part A – Charter of Aged Care Rights

}
