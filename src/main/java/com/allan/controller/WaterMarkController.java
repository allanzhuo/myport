package com.allan.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.allan.service.WaterMarkService;

@Controller
public class WaterMarkController {
	@Autowired
	private WaterMarkService uploadService;
	/**
	 *上传图片
	 * 
	 * @param request
	 * @param myfile
	 * @return
	 */

	@RequestMapping(value="/upload",method=RequestMethod.POST)
	public String uploadImage(@RequestParam("myfile") MultipartFile myFile) {
		String imageFileName = myFile.getOriginalFilename(); 
		String uploadImage = uploadService.uploadImage(myFile, imageFileName);

		return "success";
	}
	
	/**
	 *单文字水印
	 * 
	 * @param request
	 * @param myfile
	 * @return
	 */

	@RequestMapping(value="/textmark",method=RequestMethod.POST)
	public String textWaterMark(@RequestParam("myfile") MultipartFile myFile) {
		String imageFileName = myFile.getOriginalFilename(); 
		String uploadImage = uploadService.textWaterMark(myFile, imageFileName);

		return "success";
	}
	
	/**
	 *单图片水印
	 * 
	 * @param request
	 * @param myfile
	 * @return
	 */

	@RequestMapping(value="/imagemark",method=RequestMethod.POST)
	public String imageWaterMark(@RequestParam("myfile") MultipartFile myFile,HttpServletRequest request) {
		String imageFileName = myFile.getOriginalFilename(); 
		String uploadImage = uploadService.imageWaterMark(myFile, imageFileName, request);

		return "success";
	}
	
	/**
	 *多文字水印
	 * 
	 * @param request
	 * @param myfile
	 * @return
	 */

	@RequestMapping(value="/moreText",method=RequestMethod.POST)
	public String moreTextWaterMark(@RequestParam("myfile") MultipartFile myFile) {
		String imageFileName = myFile.getOriginalFilename(); 
		String uploadImage = uploadService.moreTextWaterMark(myFile, imageFileName);

		return "success";
	}
	
	/**
	 *多图片水印
	 * 
	 * @param request
	 * @param myfile
	 * @return
	 */

	@RequestMapping(value="/moreImage",method=RequestMethod.POST)
	public String moreImageWaterMark(@RequestParam("myfile") MultipartFile myFile,HttpServletRequest request) {
		String imageFileName = myFile.getOriginalFilename(); 
		String uploadImage = uploadService.moreImageWaterMark(myFile, imageFileName, request);

		return "success";
	}
}
