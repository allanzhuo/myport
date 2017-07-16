<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<body>
<h2>Excel导入导出：poi方式</h2>
<form action="/importExcel" enctype="multipart/form-data" method="post">
<input type="file" name="myfile" id="myfile"> 
<input type="submit" value="提交"> <br>
${msg}

</form>
</body>
</html>
