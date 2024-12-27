package com.example.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.UserPojo.UserResueme;
import com.example.service.ExtractService;
import com.example.service.Service;

import Apache.tika.examples.ApacheTicaDemo.ApacheTicaDemoApplication1;

@RestController
public class UserController {
	@Autowired
	private final UserResueme user;
	private final  Service ser;
	private  final ExtractService exService;
	private static final Logger log=Logger.getLogger(UserController.class.getName());
	
	public UserController(UserResueme user, Service ser, ExtractService exService) {
		super();
		this.user = user;
		this.ser = ser;
		this.exService = exService;
	}
	@GetMapping("/index")
	public ModelAndView getString() {
		log.info("welcome to getMapping");
		ModelAndView mav=new ModelAndView("index");
		return mav;
	}
	@PostMapping("/")
	public ModelAndView handleFile(@RequestParam("file") MultipartFile file, Model mod) {
	    if (file.isEmpty()) {
	        throw new IllegalArgumentException("File is empty");
	    }

	    Path tempDir = null;
	    try {
	        // Create temporary directory
	        tempDir = Files.createTempDirectory("uploaded_files");
	        log.info("directory created at "+tempDir);

	        // Save file to temporary path
	        Path tempFilePath = tempDir.resolve(file.getOriginalFilename());
	        file.transferTo(tempFilePath.toFile());
	        log.info("File saved at "+tempFilePath);

	        // Process the file
	        String tpath=System.getenv("tessdata");
	        if(tpath==null) {
	        	String os=System.getProperty("os.name").toLowerCase();
	        	if(os.contains("win"))
	        	tpath="C:\\Program Files\\Tesseract-OCR\\tessdata";
	        	else
	        	tpath = "/usr/share/tesseract-ocr/4.00/tessdata";
	        }
	        ApacheTicaDemoApplication1.processFile(exService, tempFilePath.toString(), tpath, ser);

	        
	        mod.addAttribute("data",user.getData());
	        mod.addAttribute("name", user.getName());
	        mod.addAttribute("phone", user.getPhone());
	        mod.addAttribute("email", user.getEmail());
	        mod.addAttribute("qualification",user.getQualification());
	        mod.addAttribute("skills", user.getSkills());
	        mod.addAttribute("summary", user.getSummary());
	        mod.addAttribute("url",user.getLinkdnUrl());
	        log.info("User Details="+user);

	        // Delete the uploaded file
	        Files.deleteIfExists(tempFilePath);
	        Files.deleteIfExists(tempDir);

	    } catch (Exception e) {
	        e.printStackTrace(); // Replace with logger
	        log.warning("Error "+e.getMessage());
	    } finally {
	        if (tempDir != null && Files.exists(tempDir)) {
	            try {
	                Files.walk(tempDir)
	                        .sorted(Comparator.reverseOrder())
	                        .forEach(path -> {
	                            try {
	                                Files.deleteIfExists(path);
	                            } catch (IOException ex) {
	                            	log.warning("Wanrnig :"+ex.getMessage());
	                                ex.printStackTrace(); // Replace with logger
	                            }
	                        });
	            } catch (IOException ex) {
	                ex.printStackTrace(); // Replace with logger
	                log.warning("error :"+ex.getMessage());
	            }
	        }
	        log.info("Temporary files and directory cleaned up");
	    }
	    ModelAndView mav=new ModelAndView("index");
	    return mav;
	}


}
