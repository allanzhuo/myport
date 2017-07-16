package com.allan.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.formula.functions.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.allan.pojo.Student;
import com.allan.service.StudentService;
/**
 * 
 * @author 小卖铺的老爷爷
 *
 */
@Controller
public class StudentController {
	@Autowired
	private StudentService studentService;
	/**
	 * 批量导入表单数据
	 * 
	 * @param request
	 * @param myfile
	 * @return
	 */
	
	@RequestMapping(value="/importExcel",method=RequestMethod.POST)
	public String importExcel(@RequestParam("myfile") MultipartFile myfile) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			Integer num = studentService.importExcel(myfile);
		} catch (Exception e) {
			modelAndView.addObject("msg", e.getMessage());
			return "index";
		}
		modelAndView.addObject("msg", "数据导入成功");
		
		return "index";
	}
	
	@RequestMapping(value="/exportExcel",method=RequestMethod.GET)
	public void exportExcel(HttpServletResponse response) {	
		try {
			studentService.exportExcel(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	


}
