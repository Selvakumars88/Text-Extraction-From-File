package com.example.service;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.UserPojo.UserResueme;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

@org.springframework.stereotype.Service
public class Service {
	@Autowired
    private final UserResueme user;

    public Service(UserResueme user) {
        this.user = user;
    }

    public Service() {
        this.user = new UserResueme();
    }

    public String ocrParse(String filePath, String tessPath) throws TesseractException {
        File file = new File(filePath);
        ITesseract tess = new Tesseract();
        tess.setDatapath(tessPath);
        //tess.setTessVariable("tessedit_create_hocr", "1");
        tess.setLanguage("eng");
        tess.setPageSegMode(1);
        tess.setOcrEngineMode(1);
        String ocrText= tess.doOCR(file); // Return OCR-processed text
        //user.setData(ocrText);
         return ocrText;
    }
    
public String extractName(String text) {
	user.setData(text);
	String name =extractWithName(text);
	user.setName(name!=null?name:"not found");
	return user.getName();
}
    public String extractEmail(String text) {
        String email = extractWithRegex(text, "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}");
        String result = email != null ? email : "Email not found";
        user.setEmail(result);
        return result;
    }

    public String extractPhone(String text) {
        String phone = extractWithRegex(text, "(\\+91[-\\s]?)?[6-9]\\d{9}");
        user.setPhone(phone != null ? phone : "Phone not found");
       return  user.getPhone();
    }

    public String extractSkills(String text) {
        String skills = extractSkillsHere(text);
        user.setSkills(skills);
        
		return user.getSkills();
              
    }
		public String extractQualification(String text) {
        String qualification = extractWithRegex(text, "(?i)\\b((?:ph\\.d|m\\.?tech|m\\.?sc|m\\.?a|mba|b\\.?tech|b\\.?e|b\\.?sc|b\\.?a|diploma|12th|10th))\\b");
        user.setQualification(qualification != null ? qualification : "Qualification not found");

        return user.getQualification();
    }

    public String extractExperience(String text) {
        String exper = extractWithRegex(text, "\\b(?:experience|work\\s+experience|years?\\s+of\\s+experience|worked\\s+as)\\s*[:\\-]?\\s*(.*?)(?=\\n|\\r|$)");
        user.setExperience(exper != null ? exper : "Fresher");
        
      return user.getExperience();
    }

    public String extractSummary(String text) {
        String summary = extracProfile(text);
        		
        user.setSummary(summary != null ? summary : "Summary not found");

       return user.getSummary();
    }

    public String extractUrl(String text) {
        String url = extractWithRegex(text, "(?:https?:\\/\\/)?(?:www\\.)?linkedin\\.com\\/in\\/[a-zA-Z0-9\\-]+");
        user.setLinkdnUrl(url != null ? url : "URL not found");
        return user.getLinkdnUrl();
    }

   

    private static String extractWithRegex(String text, String regex) {
        Pattern ptrn = Pattern.compile(regex, Pattern.MULTILINE);
        Matcher match = ptrn.matcher(text);
         if(match.find() ) {
        	 String group=match.group();
        	 return  group;
         }
         else
        	 return null;
    }
    private static String extractSkillsHere(String text) {
    	 int skillIndex=text.indexOf("SKILLS");
    	 if(skillIndex==-1) {
    		 return null;
    	 }
    	 String skills=text.substring(skillIndex + "SKILLS".length()).trim();
    	String[] arr=skills.split("\n");
    	StringBuilder sb=new StringBuilder();
    	int count=0;
    	for(String skill:arr) {
    		if(skill.trim().isEmpty()) {
    			continue;
    		}
    		sb.append(skill.trim()).append(", ");
    		count++;
    		if(count>5) {
    			break;
    		}
    	}
    	
    	return sb.toString();
    }

   private static  String extracProfile(String text) {
	   int proindex=text.indexOf("PROFILE");
	   if(proindex==-1) {
		   return null;
	   }
	   String proText=text.substring(proindex + "PROFILE".length()).trim();
	   String[] lines=proText.split("\n");
	   StringBuilder sb=new StringBuilder();
	   int lineCount=0;
	   for(String line:lines) {
		   if(line.trim().isEmpty()) {
			   continue;
		   }
		   sb.append(line.trim()).append(" ");
		   lineCount++;
		   if(lineCount==2) {
			   break;
		   }
	   }
	   return sb.toString().trim();
   }
   private String extractWithName(String text) {
	   String[] arr=text.split("\n");
	   for(String line:arr) {
		   if(!line.trim().isEmpty()) {
			   return line.trim();
		   }
	   }
	   return null;
   }
   
}
