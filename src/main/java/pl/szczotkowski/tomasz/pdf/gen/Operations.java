package pl.szczotkowski.tomasz.pdf.gen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.multipdf.Overlay;

public class Operations {

	public static void CreateDocPdf(String pathOperationDoc, String pathResultDoc) throws IOException {

		PDDocument doc = new PDDocument();

		doc.save(pathOperationDoc);

		doc.close();

		File oDoc = new File(pathOperationDoc);
		boolean waitForOperationDoc = true;
		while (waitForOperationDoc) {

			if (oDoc.exists()) {
				waitForOperationDoc = false;
			}
		}

		PDDocument finalDoc = new PDDocument();

		finalDoc.save(pathResultDoc);

		finalDoc.close();

		File fDoc = new File(pathOperationDoc);
		boolean waitForFinalDoc = true;
		while (waitForFinalDoc) {

			if (fDoc.exists()) {
				waitForFinalDoc = false;
			}
		}

		System.out.println("CreateFinalDocPdf end...");
	}

	public static void AddWatermark(String pathOperationDoc, String pathWatermark) throws IOException {

		PDDocument finalPDF = PDDocument.load(new File(pathOperationDoc));

		HashMap<Integer, String> overlayGuide = new HashMap<Integer, String>();
		for (int i = 0; i < finalPDF.getNumberOfPages(); i++) {
			overlayGuide.put(i + 1, pathWatermark);
		}
		Overlay overlay = new Overlay();
		overlay.setInputPDF(finalPDF);
		overlay.setOverlayPosition(Overlay.Position.BACKGROUND);
		overlay.overlay(overlayGuide);

		finalPDF.save(pathOperationDoc);
		overlay.close();
		finalPDF.close();

		System.out.println("addWatermark end...");
	}

	public static void Splittpdf(String pathPdf, String pathOperationDoc, String[] args) throws IOException {
		File file = new File(pathPdf);
		PDDocument document = PDDocument.load(file);
		File fileOperationDoc = new File(pathOperationDoc);
		PDDocument operationDoc = PDDocument.load(fileOperationDoc);
		
		
		// jeżeli tabela argumentów nie będzie posiadał argumentów, w postaci stron,
		// program przetwarza wszystkie strony
		
		if (args.length == 3) {
			for (int page = 0; page < document.getNumberOfPages(); page++) {
				operationDoc.addPage(document.getPage(page));
			}
		} else {
			Integer[] pages = new Integer[args.length - 3];
			int id = 2;
			for (int i = 0; i < pages.length; i++) {
				id++;
				pages[i] = Integer.parseInt(args[id]);
				System.out.println(pages[i]);
				
			}
			for (int page = 0; page < pages.length; page++) {

				operationDoc.addPage(document.getPage(pages[page]));
			}

		}

	
		operationDoc.save(pathOperationDoc);
		System.out.println("splittpdf end...");

		operationDoc.close();
		document.close();
	}

	public static void PdfToImage(String pathOperationDoc, String pathResultDoc, String resultSRC) throws IOException {
		int dpi = 2;
		File operationfile = new File(pathOperationDoc);
		PDDocument operationDocument = PDDocument.load(operationfile);
		PDFRenderer renderer = new PDFRenderer(operationDocument);

		File finalFilePDF = new File(pathResultDoc);
		PDDocument finalPDF = PDDocument.load(finalFilePDF);

		for (int i = 0; i < operationDocument.getNumberOfPages(); i++) {

			PDPage page;
			BufferedImage image = renderer.renderImage(i, dpi);
			String path = resultSRC + "page" + i + ".jpg";
			File nowImage = new File(path);
			ImageIO.write(image, "jpg", nowImage);

			/*
			 * if sprawdza wielkosć strony jeżeli przekracze poniże wartości jezeli poni�sze
			 * wartości są przekroczone wstawiea w pdf strone A3
			 * 
			 * Je�eli jest poniżej wartości wstawia strone A4
			 *
			 */

			if (image.getHeight() > 1684 && image.getWidth() > 1190) {

				PDPage blankPage = new PDPage(PDRectangle.A3);
				finalPDF.addPage(blankPage);
				finalPDF.save(pathResultDoc);
				page = finalPDF.getPage(i);

			} else {

				PDPage blankPage = new PDPage(PDRectangle.A4);
				finalPDF.addPage(blankPage);
				finalPDF.save(pathResultDoc);
				page = finalPDF.getPage(i);

			}

			PDImageXObject pdImage = PDImageXObject.createFromFile(path, finalPDF);
			PDPageContentStream contentStream = new PDPageContentStream(finalPDF, page);

			/*
			 * Rysuje grafike w PDFie wcyiagnięta z zdjecia powyżej. zmienna DPI określa
			 * jakość dokumentu, wielkość musi zosta� podzielona prz DPI żeby miesićły się na
			 * stronach A3 i A4
			 */
			contentStream.drawImage(pdImage, 0, 0, (pdImage.getWidth() / dpi), (pdImage.getHeight() / dpi));
			contentStream.close();

			finalPDF.save(pathResultDoc);
			nowImage.delete();

		}

		operationDocument.close();
		operationfile.delete();
		System.out.println("end...");

	}

	public static void DeleteAnnotations(String pathOperationDoc) throws IOException {
		File file = new File(pathOperationDoc);
		PDDocument document = PDDocument.load(file);

		// iteruje po całym dokumencie usuwając annotacje z pliku
		for (int page = 0; page < document.getNumberOfPages(); page++) {
			document.getPage(page).setAnnotations(null);
		}

		document.save(file);
		document.close();

		System.out.println("DeleteAnnotatnions");

	}

}
