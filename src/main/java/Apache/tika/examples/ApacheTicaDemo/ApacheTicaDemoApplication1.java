package Apache.tika.examples.ApacheTicaDemo;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.UserPojo.UserResueme;
import com.example.service.ExtractService;
import com.example.service.Service;

public class ApacheTicaDemoApplication1 {
	@Autowired
    static UserResueme resume=new UserResueme();
	static  Service ser=new Service();
    static ExtractService service=new ExtractService();
    public static void main(String[] args) {
        try {
        	
            
            
          
            String fpath = "C:\\Users\\AMIN\\Downloads\\Selvakumar S_Resume.docx";
            String tpath = "C:\\Program Files\\Tesseract-OCR\\tessdata";
            
            
            
            processFile(service, fpath, tpath,ser);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void processFile(ExtractService service, String fpath, String tpath,Service ser) throws Exception {
        String test;
        if (fpath.endsWith(".pdf")) {
            test = service.ExtractTextPdf(fpath, tpath);
        }
        else if(fpath.endsWith(".docx")) {
        	test=service.extractToDocx(fpath, tpath);
        }
        else if(fpath.endsWith(".png")||fpath.endsWith(".PNG")||fpath.endsWith(".jpj")) {
        	test=ser.ocrParse(fpath, tpath);
        }
         else {
            throw new IllegalArgumentException("Unsupported file format: " + fpath);
        }
        displayExtractedDetails(ser, test);
    }

    private static void displayExtractedDetails(Service service, String test) {
    	System.out.println("Texts:\n"+test);
        System.out.println("\n--- Extracted Details ---");
        System.out.printf("Name       : %s%n", service.extractName(test));
        System.out.printf("Email      : %s%n", service.extractEmail(test));
        System.out.printf("Phone      : %s%n", service.extractPhone(test));
        System.out.printf("URL        : %s%n", service.extractUrl(test));
        System.out.println("Skills     :");
        System.out.println(service.extractSkills(test));
        System.out.printf("Qualification: %s%n", service.extractQualification(test));
        System.out.println("Summary    :");
        System.out.println(service.extractSummary(test));
    }
}
