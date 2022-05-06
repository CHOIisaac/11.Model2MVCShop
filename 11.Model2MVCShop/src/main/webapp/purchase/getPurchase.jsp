<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--
<%@ page import="com.model2.mvc.service.domain.*" %>

<% Purchase purchase = (Purchase)request.getAttribute("productVO"); %>
<% User user = (User)session.getAttribute("user"); %>
 --%>
 
<html>
<head>
<title>Insert title here</title>
</head>

<body>
<!-- ���� ��ư ���� ��, �������Ǵ� ���ſϷ� ���� ������ -->

������ ���� ���Ű� �Ǿ����ϴ�.

<table border=1>

<!-- purchaseVO �� User buyer, Product purchaseProd ���� -->
	<tr>
		<td>��ǰ��ȣ</td>
		<td>${purchase.purchaseProd.prodNo}</td>
		<td></td>
	</tr>
	
	<!-- ������ ���̵� �����ý�, 
		1. requestScope�� ����� purchase.buyer.userId
		2. sessionScope�� ����� user.userId 
		��� ����  -->
	<tr>
		<td>�����ھ��̵�</td>
		<td>${user.userId}</td>	
		<!--<td>${requestScope.purchase.buyer.userId}</td> -->	
		<td></td>
	</tr>
	
	
<!--  -->	
	<tr>
		<td>���Ź��</td>
		<td>${purchase.paymentOption}</td>
		<td></td>
	</tr>
	<tr>
		<td>�������̸�</td>
		<td>${purchase.receiverName}</td>
		<td></td>
	</tr>
	<tr>
		<td>�����ڿ���ó</td>
		<td>${purchase.receiverPhone}</td>
		<td></td>
	</tr>
	<tr>
		<td>�������ּ�</td>
		<td>${purchase.divyAddr}</td>
		<td></td>
	</tr>
		<tr>
		<td>���ſ�û����</td>
		<td>${purchase.divyRequest}</td>
		<td></td>
	</tr>
	<tr>
		<td>����������</td>
		<td>${purchase.divyDate}</td>
		<td></td>
	</tr>
	<tr>
		<td>�ֹ���</td>
		<td>${purchase.orderDate}</td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>