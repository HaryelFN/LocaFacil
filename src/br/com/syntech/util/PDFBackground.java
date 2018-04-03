package br.com.syntech.util;

import java.io.IOException;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFBackground extends PdfPageEventHelper {

	private String url = this.getClass().getResource("/br/com/syntech/img/template.jpg").toString();

	@Override
	public void onEndPage(PdfWriter writer, Document document) {

		Image background = null;

		try {

			background = Image.getInstance(url);

		} catch (BadElementException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// This scales the image to the page,
		// use the image's width & height if you don't want to scale.
		float width = document.getPageSize().getWidth();
		float height = document.getPageSize().getHeight();
		try {
			writer.getDirectContentUnder().addImage(background, width, 0, 0, height, 0, 0);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}