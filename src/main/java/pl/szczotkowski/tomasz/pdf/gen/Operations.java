package pl.szczotkowski.tomasz.pdf.gen;

import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.color.PDOutputIntent;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;
import org.apache.pdfbox.multipdf.Overlay;

public class Operations {

	public static void CreateFinalDocPdf(String pathResultDoc) throws IOException {

		/*
		 * Create new file pdf in which save element from selected document 
		 */
		PDDocument doc = new PDDocument();

		doc.save(pathResultDoc);

		doc.close();

		File fdoc = new File(pathResultDoc);
		
		// this loop waiting for a document creation 
		boolean waitForFinalDoc = true;
		while (waitForFinalDoc) {

			if (fdoc.exists()) {
				waitForFinalDoc = false;
			}
		}

		System.out.println("CreateFinalDocPdf end...");
	}

	public static void addWatermark(String pathResultDoc, String pathWatermark) throws IOException {

		/*
		 * This metod adding Watermark to all document PDF whitch selected in generator 
		 */
		PDDocument finalPDF = PDDocument.load(new File(pathResultDoc));
		
		
		HashMap<Integer, String> overlayGuide = new HashMap<Integer, String>();
		for (int i = 0; i < finalPDF.getNumberOfPages(); i++) {
			overlayGuide.put(i + 1, pathWatermark);
		}
		Overlay overlay = new Overlay();
		overlay.setInputPDF(finalPDF);
		overlay.setOverlayPosition(Overlay.Position.BACKGROUND);
		overlay.overlay(overlayGuide);

		finalPDF.save(pathResultDoc);
		System.out.println("addWatermark end...");

		finalPDF.close();
	}

	public static void splittpdf(String selectedPdf, String pathResultDoc) throws IOException {
		
		/*
		 * This is concept to selected element from all document, and create new document. 
		 */
		
		File file = new File(selectedPdf);
		PDDocument document = PDDocument.load(file);
		File finalDoc = new File(pathResultDoc);
		PDDocument finalPDF = PDDocument.load(finalDoc);
		finalPDF.addPage(document.getPage(14));
		finalPDF.addPage(document.getPage(2));
		finalPDF.addPage(document.getPage(6));
		finalPDF.addPage(document.getPage(6));
		finalPDF.addPage(document.getPage(10));

		finalPDF.save(pathResultDoc);
		document.close();
		System.out.println("splittpdf end...");

		document.close();
	}

	public static void rasterization(String pathResultDoc) throws IOException {
		
		/*
		 * This metod rasterization all document becouse we needed protect element in pdf. 
		 */
//		File pathResultFile = new File(pathResultDoc);
//		PDDocument finalPDF = PDDocument.load(new File(pathResultFile));
//		PDFPrintable printable = new PDFPrintable(finalPDF, null, false, 150);
//		PrinterJob job = PrinterJob.getPrinterJob();
//		job.setPrintable(printable);
		

		
		
		System.out.println("end...");
		

	}

}
