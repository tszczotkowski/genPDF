package pl.szczotkowski.tomasz.pdf.gen;

import java.io.IOException;

public class GenPDF {

	public static void main(String[] args) throws IOException {

		String pathPdf = "C:\\genPDF\\src\\";
		String selectedPdf = pathPdf + "document.pdf";
		String resultSRC = "C:\\genPDF\\result\\";
		String pathResultDoc = resultSRC + "finalPDF.pdf";
		String pathWatermark = "C:\\genPDF\\src\\Watermark.pdf";

		try {
			Operations.CreateFinalDocPdf(pathResultDoc);
		} catch (Exception e) {
			System.out.println("Problem z utworzeniem pdfa");
		}

		Operations.splittpdf(selectedPdf, pathResultDoc);
		Operations.addWatermark(pathResultDoc, pathWatermark);
		Operations.rasterization(pathResultDoc);
	}
}
