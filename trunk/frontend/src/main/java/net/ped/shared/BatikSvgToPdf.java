package net.ped.shared;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.commons.io.IOUtils;
import org.apache.fop.svg.PDFTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatikSvgToPdf implements SvgToPdf {

	protected static final Logger LOG = LoggerFactory.getLogger(SvgToPdf.class);

	@Override
	public File convert(File svg) {
		//Step -1: We read the input SVG document into Transcoder Input
		String svg_URI_input = "file:///" + svg.getAbsolutePath();
		List<String> ls = null;
		try {
			ls = IOUtils.readLines(new FileInputStream(svg));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder svgBuilder = new StringBuilder();
		if (ls != null) {
			for (String is : ls)
				svgBuilder.append(is);
		}
		TranscoderInput input_svg_image = new TranscoderInput(svg_URI_input);        
		//Step-2: Define OutputStream to PDF file and attach to TranscoderOutput
		File pdf = null;
		pdf = FileStorage.createTempFile("ticketpdf", "ticket", ".pdf");
		OutputStream pdf_ostream = null;
		try {
			pdf_ostream = new FileOutputStream(pdf);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if( pdf_ostream == null)
			return null;
		TranscoderOutput output_pdf_file = new TranscoderOutput(pdf_ostream);               
		// Step-3: Create a PDF Transcoder and define hints
		Transcoder transcoder = new PDFTranscoder();
		// Step-4: Write output to PDF format

		System.out.println("----------- BEFORE");
		try {
			transcoder.transcode(input_svg_image, output_pdf_file);
		} catch (TranscoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("----------- AFTER");
		// Step 5- close / flush Output Stream
		try {
			pdf_ostream.flush();
			pdf_ostream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pdf;

	}
}
