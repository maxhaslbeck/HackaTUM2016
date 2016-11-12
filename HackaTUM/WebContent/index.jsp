<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Test</title>
</head>
<body>

<h1>Hello</h1>


<h3><%= (String)request.getAttribute("Message") %>
</h3>
 
<form action="Tipps" method="post">
	Tipp text: <br>
	<input type="text" name = "tipptext"> <br>
	Aufgabe (id): <br>
	<input type="text" name = "aufgabe"> <br>
	Kosten: <br>
	<input type="text" name = "cost"> <br>
	Hole (#):<br>
	<input type="text" name = "hole"> <br>
	Rank (#):<br>
	<input type="text" name = "rank"> <br>
	<input type="submit" value ="submit">
</form>
<br>
<form action="FileUploader" method="post" enctype="multipart/form-data">
	Upload bundle (zip): <br>
	<input type="file" name = "bundle"> <br>
	Upload xml: <br>
	<input type="file" name = "xml"> <br>
	Aufgabe (id): <br>
	<input type="text" name = "aufgabe"> <br>
	<input type="submit" value ="submit">
</form>


</body>
</html>