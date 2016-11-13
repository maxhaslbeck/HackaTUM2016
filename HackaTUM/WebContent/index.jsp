<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>TUMjudge</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
  	<link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
  	<link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/tumjudge.css">
	<link rel="stylesheet" type="text/css" href="css/menu.css">
	<link rel="stylesheet" type="text/css" href="css/proofwars.css">
	<link rel="stylesheet" type="text/css" href="css/isabelle.css">
</head>
<body class="contest-1 tumjudge-isabelle">

<%@include file="navbar.jsp" %>

<div class="container proofwars">

<h1><%= request.getAttribute("xmlString") %></h1>



<h1 class="pull-center">Hello</h1>

<h3><%=(String)request.getAttribute("Message")%></h3>

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
<form action="Tipps" method="get">
	Aufgabe (id): <br>
	<input type="text" name = "aufgabe"> <br>
	<input type="submit" value ="get"> <br>
</form>

<div id="result">
</div>
</div>

</body>
</html>