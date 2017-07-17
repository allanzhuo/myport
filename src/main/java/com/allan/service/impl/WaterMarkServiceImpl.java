package com.allan.service.impl;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.allan.service.WaterMarkService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
@Service
public class WaterMarkServiceImpl implements WaterMarkService{
	//定义上传的文件夹
	private static final String UPLOAD_PATH = "E:/save";
	//定义水印文字样式
	private static final String MARK_TEXT = "小卖铺的老爷爷";
	private static final String FONT_NAME = "微软雅黑";
	private static final int FONT_STYLE = Font.BOLD;
	private static final int FONT_SIZE = 60;
	private static final Color FONT_COLOR = Color.black;


	private static final float ALPHA = 0.3F;


	//1、上传图片
	public String uploadImage(MultipartFile myFile,String imageFileName) {
		InputStream is =null;
		OutputStream os =null;
		try{
			is = myFile.getInputStream();
			os = new FileOutputStream(UPLOAD_PATH+"/"+imageFileName);
			byte[] buffer =new byte[1024];
			int len = 0;

			while ((len=is.read(buffer))>0){
				os.write(buffer);
			}

		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(is!=null){
				try {
					is.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
			if(os!=null){
				try {
					os.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
		}

		return "success";

	}
	//添加单条文字水印
	public String textWaterMark(MultipartFile myFile,String imageFileName) {
		InputStream is =null;
		OutputStream os =null;
		int X = 636;
		int Y = 700;

		try {
			Image image = ImageIO.read(myFile.getInputStream());
			//计算原始图片宽度长度
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			//创建图片缓存对象
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
			//创建java绘图工具对象
			Graphics2D graphics2d = bufferedImage.createGraphics();
			//参数主要是，原图，坐标，宽高
			graphics2d.drawImage(image, 0, 0, width, height, null);
			graphics2d.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
			graphics2d.setColor(FONT_COLOR);

			//使用绘图工具将水印绘制到图片上
			//计算文字水印宽高值
			int waterWidth = FONT_SIZE*getTextLength(MARK_TEXT);
			int waterHeight = FONT_SIZE;
			//计算水印与原图高宽差
			int widthDiff = width-waterWidth;
			int heightDiff = height-waterHeight;
			//水印坐标设置
			if (X > widthDiff) {
				X = widthDiff;
			}
			if (Y > heightDiff) {
				Y = heightDiff;
			}
			//水印透明设置
			graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
			graphics2d.drawString(MARK_TEXT, X, Y+FONT_SIZE);

			graphics2d.dispose();
			os = new FileOutputStream(UPLOAD_PATH+"/"+imageFileName);
			//创建图像编码工具类
			JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
			//使用图像编码工具类，输出缓存图像到目标文件
			en.encode(bufferedImage);
			if(is!=null){		
				is.close();
			}
			if(os!=null){
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

	//添加单图片水印
	public String imageWaterMark(MultipartFile myFile,String imageFileName,HttpServletRequest request) {
		InputStream is =null;
		OutputStream os =null;
		int X = 636;
		int Y = 763;

		try {
			Image image = ImageIO.read(myFile.getInputStream());
			//计算原始图片宽度长度
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			//创建图片缓存对象
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
			//创建java绘图工具对象
			Graphics2D graphics2d = bufferedImage.createGraphics();
			//参数主要是，原图，坐标，宽高
			graphics2d.drawImage(image, 0, 0, width, height, null);
			graphics2d.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
			graphics2d.setColor(FONT_COLOR);

			//水印图片路径
			String logoPath = "/img/logo.png";
			String realPath = request.getSession().getServletContext().getRealPath(logoPath);
			File logo = new File(realPath);
			Image imageLogo = ImageIO.read(logo);
			int widthLogo = imageLogo.getWidth(null);
			int heightLogo = imageLogo.getHeight(null);
			int widthDiff = width-widthLogo;
			int heightDiff = height-heightLogo;
			//水印坐标设置
			if (X > widthDiff) {
				X = widthDiff;
			}
			if (Y > heightDiff) {
				Y = heightDiff;
			}
			//水印透明设置
			graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
			graphics2d.drawImage(imageLogo, X, Y, null);

			graphics2d.dispose();
			os = new FileOutputStream(UPLOAD_PATH+"/"+imageFileName);
			//创建图像编码工具类
			JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
			//使用图像编码工具类，输出缓存图像到目标文件
			en.encode(bufferedImage);
			if(is!=null){		
				is.close();
			}
			if(os!=null){
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}
	//添加多条文字水印
	public String moreTextWaterMark(MultipartFile myFile,String imageFileName) {
		InputStream is =null;
		OutputStream os =null;
		int X = 636;
		int Y = 763;

		try {
			Image image = ImageIO.read(myFile.getInputStream());
			//计算原始图片宽度长度
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			//创建图片缓存对象
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
			//创建java绘图工具对象
			Graphics2D graphics2d = bufferedImage.createGraphics();
			//参数主要是，原图，坐标，宽高
			graphics2d.drawImage(image, 0, 0, width, height, null);
			graphics2d.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
			graphics2d.setColor(FONT_COLOR);

			//使用绘图工具将水印绘制到图片上
			//计算文字水印宽高值
			int waterWidth = FONT_SIZE*getTextLength(MARK_TEXT);
			int waterHeight = FONT_SIZE;

			//水印透明设置
			graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
			graphics2d.rotate(Math.toRadians(30), bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);

			int x = -width/2;
			int y = -height/2;

			while(x < width*1.5){
				y = -height/2;
				while(y < height*1.5){
					graphics2d.drawString(MARK_TEXT, x, y);
					y+=waterHeight+100;
				}
				x+=waterWidth+100;
			}
			graphics2d.dispose();

			os = new FileOutputStream(UPLOAD_PATH+"/"+imageFileName);
			//创建图像编码工具类
			JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
			//使用图像编码工具类，输出缓存图像到目标文件
			en.encode(bufferedImage);
			if(is!=null){		
				is.close();
			}
			if(os!=null){
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

	//多图片水印
	public String moreImageWaterMark(MultipartFile myFile,String imageFileName,HttpServletRequest request) {
		InputStream is =null;
		OutputStream os =null;
		int X = 636;
		int Y = 763;

		try {
			Image image = ImageIO.read(myFile.getInputStream());
			//计算原始图片宽度长度
			int width = image.getWidth(null);
			int height = image.getHeight(null);
			//创建图片缓存对象
			BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
			//创建java绘图工具对象
			Graphics2D graphics2d = bufferedImage.createGraphics();
			//参数主要是，原图，坐标，宽高
			graphics2d.drawImage(image, 0, 0, width, height, null);
			graphics2d.setFont(new Font(FONT_NAME, FONT_STYLE, FONT_SIZE));
			graphics2d.setColor(FONT_COLOR);

			//水印图片路径
			String logoPath = "/img/logo.png";
			String realPath = request.getSession().getServletContext().getRealPath(logoPath);
			File logo = new File(realPath);
			Image imageLogo = ImageIO.read(logo);
			int widthLogo = imageLogo.getWidth(null);
			int heightLogo = imageLogo.getHeight(null);
			
			//水印透明设置
			graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, ALPHA));
			
			graphics2d.rotate(Math.toRadians(30), bufferedImage.getWidth()/2, bufferedImage.getHeight()/2);
			
			int x = -width/2;
			int y = -height/2;

			while(x < width*1.5){
				y = -height/2;
				while(y < height*1.5){
					graphics2d.drawImage(imageLogo, x, y, null);
					y+=heightLogo+100;
				}
				x+=widthLogo+100;
			}
			graphics2d.dispose();
			os = new FileOutputStream(UPLOAD_PATH+"/"+imageFileName);
			//创建图像编码工具类
			JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(os);
			//使用图像编码工具类，输出缓存图像到目标文件
			en.encode(bufferedImage);
			if(is!=null){		
				is.close();
			}
			if(os!=null){
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}

	//计算水印文本长度
	//1、中文长度即文本长度 2、英文长度为文本长度二分之一
	public int getTextLength(String text){
		//水印文字长度
		int length = text.length();

		for (int i = 0; i < text.length(); i++) {
			String s =String.valueOf(text.charAt(i));
			if (s.getBytes().length>1) {
				length++;
			}
		}
		length = length%2==0?length/2:length/2+1;
		return length;
	}
}
