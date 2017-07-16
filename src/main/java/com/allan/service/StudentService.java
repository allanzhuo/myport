package com.allan.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.allan.pojo.Student;

public interface StudentService {
	Integer importExcel(MultipartFile myFile) throws Exception;
	void exportExcel(HttpServletResponse response) throws Exception;
}
