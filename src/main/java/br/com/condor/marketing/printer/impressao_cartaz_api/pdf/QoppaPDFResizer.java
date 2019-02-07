package br.com.condor.marketing.printer.impressao_cartaz_api.pdf;

import java.awt.geom.Rectangle2D;
import java.io.IOException;

import com.qoppa.pdf.PDFException;
import com.qoppa.pdf.ResizePageOptions;
import com.qoppa.pdfProcess.PDFDocument;
import com.qoppa.pdfProcess.PDFPage;

/**
 * Foi decidido que a lib não será comprada.
 * 
 * @author Carlos Eduardo Alves de Godoi
 *
 */
@Deprecated
public class QoppaPDFResizer {
	private static final String USER_HOME_DIR = System.getenv("USERPROFILE");
	
	private static final String OUT_PATH = String.join("\\", USER_HOME_DIR, "Desktop");
	
	private static final String A3_SOURCE_FILE = "Cartaz_A3.pdf";
	private static final String A5_SOURCE_FILE = "Cartaz_A5.pdf";
	
	private static final String OUT_FILE_RESIZED_TO_A5 = "A3_Resized_To_A5.pdf";
	private static final String OUT_FILE_RESIZED_TO_A3 = "A5_Resized_To_A3.pdf";
	
	static void resizeA5ToA3() throws PDFException, IOException {
		PDFDocument pdfDoc = new PDFDocument(String.join("\\", OUT_PATH, A5_SOURCE_FILE), null);
		
		PDFPage page = pdfDoc.getPage(0);
		
		ResizePageOptions options = new ResizePageOptions();
		
		// Get the page size A3 (height 297mm x width 420mm) in points
		double height = 420 / 25.4 * 72.0; // The height is smaller than width because the source file is an A5 one
		double width = 297 / 25.4 * 72.0;
		
		options.setMediaBox(new Rectangle2D.Double(0, 0, width, height));
		options.setAutoScale(true);
		options.setCenter(true);
		options.setFitToMedia(true);
		
		page.resizePage(options);
		
		pdfDoc.saveDocument(String.join("\\", OUT_PATH, OUT_FILE_RESIZED_TO_A3));
	}
	
	static void resizeA3ToA5() throws PDFException, IOException {
		PDFDocument pdfDoc = new PDFDocument(String.join("\\", OUT_PATH, A3_SOURCE_FILE), null);
		
		PDFPage page = pdfDoc.getPage(0);
		
		ResizePageOptions options = new ResizePageOptions();
		
		// Get the page size A5 (width 148mm x height 210mm) in points
		double width = 148 / 25.4 * 72.0;
		double height = 210 / 25.4 * 72.0;
		
		options.setMediaBox(new Rectangle2D.Double(0, 0, width, height));
		options.setAutoScale(true);
		options.setCenter(true);
		options.setFitToMedia(true);
		
		page.resizePage(options);
		
		pdfDoc.saveDocument(String.join("\\", OUT_PATH, OUT_FILE_RESIZED_TO_A5));
	}
	
	
	
	// Área de Testes
	public static void main(String[] args) throws PDFException, IOException {
		resizeA3ToA5();
		
		resizeA5ToA3();
		
		System.out.println("Verify the generated .pdf file(s) in your Desktop");
	}
	
}
