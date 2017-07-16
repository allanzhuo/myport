package com.allan.service.impl;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.allan.mapper.StudentMapper;
import com.allan.pojo.Student;
import com.allan.service.StudentService;
/**
 * 
 * @author 小卖铺的老爷爷
 *
 */
@Service
public class StudentServiceImpl implements StudentService{
	private final static String XLS = "xls";  
	private final static String XLSX = "xlsx"; 
	@Autowired
	private StudentMapper studentMapper;
	/**
	 * 导入Excel，兼容xls和xlsx
	 */
	@SuppressWarnings("resource")
	public Integer importExcel(MultipartFile myFile) throws Exception {
		//		1、用HSSFWorkbook打开或者创建“Excel文件对象”
		//
		//		2、用HSSFWorkbook对象返回或者创建Sheet对象
		//
		//		3、用Sheet对象返回行对象，用行对象得到Cell对象
		//
		//		4、对Cell对象读写。
		//获得文件名  
		Workbook workbook = null ;
		String fileName = myFile.getOriginalFilename(); 
		if(fileName.endsWith(XLS)){  
			//2003  
			workbook = new HSSFWorkbook(myFile.getInputStream());  
		}else if(fileName.endsWith(XLSX)){  
			//2007  
			workbook = new XSSFWorkbook(myFile.getInputStream());  
		}else{
			throw new Exception("文件不是Excel文件");
		}

		Sheet sheet = workbook.getSheet("Sheet1");
		int rows = sheet.getLastRowNum();// 指的行数，一共有多少行+
		if(rows==0){
			throw new Exception("请填写数据");
		}
		for (int i = 1; i <= rows+1; i++) {
			// 读取左上端单元格
			Row row = sheet.getRow(i);
			// 行不为空
			if (row != null) {
				// **读取cell**
				Student student = new Student();
				//姓名
				String name = getCellValue(row.getCell(0));
				student.setName(name);
				//班级
				String classes = getCellValue(row.getCell(1));
				student.setClasses(classes);
				//分数
				String scoreString = getCellValue(row.getCell(2));
				if (!StringUtils.isEmpty(scoreString)) {
					Integer score = Integer.parseInt(scoreString);
					student.setScore(score);
				}
				//考试时间
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
				String dateString = getCellValue(row.getCell(3));  
				if (!StringUtils.isEmpty(dateString)) {
					Date date=sdf.parse(dateString);  
					student.setTime(date);
				}
				studentMapper.insert(student);
			}
		}
		return rows-1;
	}

	/**
	 * 获得Cell内容
	 * 
	 * @param cell
	 * @return
	 */
	public String getCellValue(Cell cell) {
		String value = "";
		if (cell != null) {
			// 以下是判断数据的类型
			switch (cell.getCellType()) {
			case HSSFCell.CELL_TYPE_NUMERIC: // 数字
				value = cell.getNumericCellValue() + "";
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					Date date = cell.getDateCellValue();
					if (date != null) {
						value = new SimpleDateFormat("yyyy-MM-dd").format(date);
					} else {
						value = "";
					}
				} else {
					value = new DecimalFormat("0").format(cell.getNumericCellValue());
				}
				break;
			case HSSFCell.CELL_TYPE_STRING: // 字符串
				value = cell.getStringCellValue();
				break;
			case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
				value = cell.getBooleanCellValue() + "";
				break;
			case HSSFCell.CELL_TYPE_FORMULA: // 公式
				value = cell.getCellFormula() + "";
				break;
			case HSSFCell.CELL_TYPE_BLANK: // 空值
				value = "";
				break;
			case HSSFCell.CELL_TYPE_ERROR: // 故障
				value = "非法字符";
				break;
			default:
				value = "未知类型";
				break;
			}
		}
		return value.trim();
	}
	/**
	 * 导出excel文件
	 */
	public void exportExcel(HttpServletResponse response) throws Exception {
		// 第一步，创建一个webbook，对应一个Excel文件  
		HSSFWorkbook wb = new HSSFWorkbook();  
		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
		HSSFSheet sheet = wb.createSheet("Sheet1");  
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short  
		HSSFRow row = sheet.createRow(0);  
		// 第四步，创建单元格，并设置值表头 设置表头居中  
		HSSFCellStyle style = wb.createCellStyle();  
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  

		HSSFCell cell = row.createCell(0);
		cell.setCellValue("姓名");  
		cell.setCellStyle(style);  
		cell = row.createCell(1);  
		cell.setCellValue("班级");  
		cell.setCellStyle(style);  
		cell = row.createCell(2);  
		cell.setCellValue("分数");  
		cell.setCellStyle(style);  
		cell = row.createCell(3);  
		cell.setCellValue("时间");  
		cell.setCellStyle(style);  

		// 第五步，写入实体数据 实际应用中这些数据从数据库得到，  
		List<Student> list = studentMapper.selectAll();  

		for (int i = 0; i < list.size(); i++){  
			row = sheet.createRow(i + 1);  
			Student stu = list.get(i);  
			// 第四步，创建单元格，并设置值  
			row.createCell(0).setCellValue(stu.getName());  
			row.createCell(1).setCellValue(stu.getClasses());  
			row.createCell(2).setCellValue(stu.getScore());  
			cell = row.createCell(3);  
			cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(stu.getTime()));  
		}  		
		//第六步,输出Excel文件
	    OutputStream output=response.getOutputStream();
	    response.reset();
	    long filename = System.currentTimeMillis();
	    SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
	    String fileName = df.format(new Date());// new Date()为获取当前系统时间
	    response.setHeader("Content-disposition", "attachment; filename="+fileName+".xls");
	    response.setContentType("application/msexcel");        
	    wb.write(output);
	    output.close();
	}  

}


