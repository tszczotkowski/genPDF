package pl.szczotkowski.tomasz.pdf.gen;

import java.io.IOException;

import javax.swing.JOptionPane;

public class GenPDF {

	public static void main(String[] args) throws IOException {

		
		if(args.length == 0) {
			
			JOptionPane.showMessageDialog(null, "Brak argumentów");
		}else {
		
		
		String pathPdf = args[0]; 
		String resultSRC = args[1]; 
		String pathWatermark = args[2];
		
		String pathOperationDoc = resultSRC + "\\operationPDF.pdf";
		String pathResultDoc = resultSRC + "\\FinalPDF.pdf";

		try {
			Operations.CreateDocPdf(pathOperationDoc, pathResultDoc);

			Operations.Splittpdf(pathPdf, pathOperationDoc, args);
			Operations.DeleteAnnotations(pathOperationDoc);
			Operations.AddWatermark(pathOperationDoc, pathWatermark);
			Operations.PdfToImage(pathOperationDoc, pathResultDoc, resultSRC);
		} catch (Exception e) {
			System.out.println("Błąd");
		}

		System.out.println("Zakończyłem prace...");
	}
	}
}