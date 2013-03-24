package net.ped.shared;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.commons.io.IOUtils;
import org.apache.fop.svg.PDFTranscoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BatikSvgToPdf implements SvgToPdf {

	protected static final Logger LOG = LoggerFactory.getLogger(SvgToPdf.class);

	@Override
	public boolean convert(File svg, File output) {
		//String svg_URI_input = "file://" + svg.getAbsolutePath();
		String svg_URI_input = null;
		try {
			svg_URI_input = new File(svg.getAbsolutePath()).toURI().toURL().toString();
		} catch (MalformedURLException e1) {
			LOG.debug("svg_URI_input :" + svg_URI_input); 
			e1.printStackTrace();
		}
		//List<String> ls = null;
		/*try {
			ls = IOUtils.readLines(new FileInputStream(svg));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		/*StringBuilder svgBuilder = new StringBuilder();
		if (ls != null) {
			for (String is : ls)
				svgBuilder.append(is);
		}*/
		TranscoderInput input_svg_image = new TranscoderInput(svg_URI_input);        
		OutputStream pdf_ostream = null;
		try {
			pdf_ostream = new FileOutputStream(output);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		try {
			TranscoderOutput output_pdf_file = new TranscoderOutput(pdf_ostream);               
			Transcoder transcoder = new PDFTranscoder();
	
			LOG.info("----------- BEFORE transcoder");
			LOG.debug("svg_URI_input: " + svg_URI_input);
			transcoder.transcode(input_svg_image, output_pdf_file);
			LOG.info("----------- AFTER transcoder");
			pdf_ostream.flush();
			IOUtils.closeQuietly(pdf_ostream);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}
}
