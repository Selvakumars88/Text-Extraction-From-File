package com.example.service;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.exception.TikaException;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;
import org.apache.tika.parser.*;
import com.spire.doc.Document;
import com.spire.doc.documents.ImageType;
import net.sourceforge.tess4j.TesseractException;
@org.springframework.stereotype.Service
public class ExtractService {
		private final Service service;
		private static final Logger log=Logger.getLogger(ExtractService.class.getName());
		public ExtractService() {
			this.service = new Service();
		}
	   
	public ExtractService(Service service) {
			super();
			this.service = service;
		}
	public String ExtractTextPdf(String filePath,String tessPath)  {
		AutoDetectParser parser=new AutoDetectParser();
		
		BodyContentHandler handler=new BodyContentHandler(-1);
		
		Metadata data=new Metadata();
		
		ParseContext context=new ParseContext();
	
	String texts="";
	
	try(FileInputStream fis=new FileInputStream(new File(filePath))){
		
		StringBuilder sb=new StringBuilder();
		parser.parse(fis, handler, data, context);
		texts=handler.toString();
		if(!texts.isEmpty()&& texts!=null) {
			log.info("The file text is exist, OCR not required");
			sb.append(texts).append("\n");
			return sb.toString();
		}
		else
		return service.ocrParse(filePath, tessPath) ;
			}catch(IOException | TikaException e) {
				e.printStackTrace();
			} catch (TesseractException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	return "Text is  not available ";
	
	}
	public String extractToDocx(String fPath,String tPath) throws TesseractException {
		Document doc=new Document();
		doc.loadFromFile(fPath);
		StringBuilder sb=new StringBuilder();
		
		String text=doc.getText();
		if(text.isEmpty()) {
			log.info("The file not empty, text exists");
			sb.append(text).append("\n");
			return sb.toString();
		}
		BufferedImage[] images=doc.saveToImages(ImageType.Bitmap);
		
		StringBuilder ocrText=new StringBuilder();
		for(int i=0;i<images.length;i++) {
			BufferedImage image=images[i];
			
			BufferedImage newImg=new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
			newImg.getGraphics().drawImage(image,0,0,null);
			
			if(newImg!=null) {
				try {
					File temp=File.createTempFile("Image-%d", ".jpg");
					ImageIO.write(newImg, "JPEG", temp);
					log.info("Image as saved at "+temp.getAbsolutePath());
					String ocrResult=service.ocrParse(temp.getAbsolutePath(), tPath);
					if(ocrResult!=null && !ocrResult.trim().isEmpty()) {
						ocrText.append(ocrResult).append("/n");
					}
					temp.delete();
				} catch (IOException e) {
					e.printStackTrace();
					log.warning("Excepion occure at : "+e.getMessage());
				}
			}
		}
		return ocrText!=null?ocrText.toString():"nothing tobe there in the ocrResult";
	}
}
