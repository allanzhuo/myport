<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<h2>Excel上传导出：poi方式</h2>
<form action="/upload" enctype="multipart/form-data" method="post">
<input type="file" name="myfile" id="myfile"> 
<input type="submit" value="导入"> 
<button><a href="/exportExcel">导出</a></button>
${msg}
</form>
<h2>单文字图片水印</h2>
<form action="/textmark" enctype="multipart/form-data" method="post">
<input type="file" name="myfile" id="myfile"> 
<input type="submit" value="上传"> 
</form>
<h2>单图片水印</h2>
<form action="/imagemark" enctype="multipart/form-data" method="post">
<input type="file" name="myfile" id="myfile"> 
<input type="submit" value="上传"> 
</form>
<h2>多文字图片水印</h2>
<form action="/moreText" enctype="multipart/form-data" method="post">
<input type="file" name="myfile" id="myfile"> 
<input type="submit" value="上传"> 
</form>
<h2>多图片水印</h2>
<form action="/moreImage" enctype="multipart/form-data" method="post">
<input type="file" name="myfile" id="myfile"> 
<input type="submit" value="上传"> 
</form>
</body>
</html>
