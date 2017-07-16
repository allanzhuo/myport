package com.allan.service;



import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface WaterMarkService {

	public String uploadImage(MultipartFile myFile,String imageFileName);
	public String textWaterMark(MultipartFile myFile,String imageFileName);
	public String imageWaterMark(MultipartFile myFile,String imageFileName,HttpServletRequest request);
	public String moreTextWaterMark(MultipartFile myFile,String imageFileName);
	public String moreImageWaterMark(MultipartFile myFile,String imageFileName,HttpServletRequest request);
		
}
