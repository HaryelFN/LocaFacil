package br.com.syntech.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import br.com.syntech.model.Aluguel;

public class GeradorPDF {

	public void gerar(Aluguel aluguel) throws DocumentException {
		
		String DEST = "C:\\Users\\Public\\Documents\\Recibo"+aluguel.getId()+".pdf";

		String id = String.valueOf(aluguel.getId());
		String valor = Convert.floatToStr((aluguel.getMulta() + aluguel.getJuros() + aluguel.getValor()));
		String nomeLocatario = aluguel.getContrato().getLocatario().getNome().toUpperCase();
		String valorExtenso = "( " + Convert.toNumberExtense((aluguel.getMulta() + aluguel.getJuros() + aluguel.getValor())).toUpperCase() + " )";
		String endImovel = aluguel.getContrato().getImovel().getEndereco();
		String endImovelComplemento = aluguel.getContrato().getImovel().getBairro() + ", "
				+ aluguel.getContrato().getImovel().getCidade() + ", " + aluguel.getContrato().getImovel().getUf()
				+ " / CEP: " + aluguel.getContrato().getImovel().getCep();

		String dataAtua = Convert.CalendarToStr(Calendar.getInstance());
		String nomeLocador = aluguel.getContrato().getLocador().getNome();
		String cpfLocador = aluguel.getContrato().getLocador().getCpf();
		String endLocador = aluguel.getContrato().getImovel().getBairro() + ", "
				+ aluguel.getContrato().getLocador().getCidade() + ", " + aluguel.getContrato().getLocador().getUf()
				+ " / CEP: " + aluguel.getContrato().getLocador().getCep();

		Document doc = new Document();

		try {

			doc.setPageSize(PageSize.A5.rotate());
			PdfWriter pdfWriter = PdfWriter.getInstance(doc, new FileOutputStream(DEST));
			doc.open();

			addTextData(pdfWriter, id, 180, 378, 16);

			addTextData(pdfWriter, valor, 360, 378, 16);

			addTextData(pdfWriter, nomeLocatario, 130, 320, 13);

			addTextData(pdfWriter, valorExtenso, 105, 283, 13);

			addTextData(pdfWriter, endImovel, 130, 245, 13);

			addTextData(pdfWriter, endImovelComplemento, 40, 225, 13);

			addTextData(pdfWriter, dataAtua, 510, 110, 11);

			addTextData(pdfWriter, nomeLocador, 78, 70, 10);

			addTextData(pdfWriter, cpfLocador, 348, 72, 10);

			addTextData(pdfWriter, endLocador, 90, 32, 11);

			doc.close();
			
			Desktop.getDesktop().open(new File(DEST));

		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		} catch (IOException ioe) {
			System.err.println(ioe.getMessage());
		} finally {
			doc.close();
		}
	}

	void addTextData(PdfWriter pdfWriter, String text, int x, int y, int size) {

		PdfContentByte cb = pdfWriter.getDirectContent();
		BaseFont bf;

		try {
			bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
			cb.saveState();
			cb.beginText();
			cb.moveText(x, y);
			cb.setFontAndSize(bf, size);
			cb.showText(text);
			cb.endText();
			cb.restoreState();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		pdfWriter.setPageEvent(new PDFBackground());
	}

}
