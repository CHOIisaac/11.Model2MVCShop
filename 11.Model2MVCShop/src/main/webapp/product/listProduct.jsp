<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- 
<%@page import="com.model2.mvc.common.util.CommonUtil"%>
<%@page import="com.model2.mvc.common.Page"%>
<%@page import="com.model2.mvc.service.domain.Product"%>
<%@page import="com.model2.mvc.common.Search"%>
<%@page import="java.util.List"%>

<%
List<Product> list= (List<Product>)request.getAttribute("list");
Page resultPage=(Page)request.getAttribute("resultPage");

Search search = (Search)request.getAttribute("search");
//==> null 을 ""(nullString)으로 변경
String searchCondition = CommonUtil.null2str(search.getSearchCondition());
String searchKeyword = CommonUtil.null2str(search.getSearchKeyword());

String menu = request.getParameter("menu");
%>
--%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
	
	<!-- 참조 : http://getbootstrap.com/css/   참조 -->
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	
	<!--  ///////////////////////// Bootstrap, jQuery CDN ////////////////////////// -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" >
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" ></script>
	
	
	<!-- Bootstrap Dropdown Hover CSS -->
   <link href="/css/animate.min.css" rel="stylesheet">
   <link href="/css/bootstrap-dropdownhover.min.css" rel="stylesheet">
    <!-- Bootstrap Dropdown Hover JS -->
   <script src="/javascript/bootstrap-dropdownhover.min.js"></script>
   
   
   <!-- jQuery UI toolTip 사용 CSS-->
  <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <!-- jQuery UI toolTip 사용 JS-->
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	
	<!--  ///////////////////////// CSS ////////////////////////// -->
	<style>
	  body {
            padding-top : 50px;
        }
    </style>
    
     <!--  ///////////////////////// JavaScript ////////////////////////// -->
	<script type="text/javascript">

function fncGetUserList(currentPage) {
	//document.getElementById("currentPage").value = currentPage;
	$("#currentPage").val(currentPage)
   //	document.detailForm.submit();	
	$("form").attr("method" , "POST").attr("action" , "/product/listProduct").submit();
}

$(function() {
	$( "button.btn.btn-default" ).on("click" , function() {
		//Debug..
		//alert(  $( "td.ct_btn01:contains('검색')" ).html() );
		fncGetUserList(1);
	});
});

<c:if test="${param.menu=='search'}">
$(function() {
	
	//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
	$( "td:nth-child(2)" ).on("click" , function() {
		 var prodNo = $(this).data("param");
	        //Debug..
	        //alert(  $( this ).text().trim() );
	    	  
	           //self.location ="/product/updateProductView?prodNo="+prodNo+"${param.menu}";
	           self.location="/purchase/addPurchase?prodNo="+$(this).attr("prodNo");
	    	  
	});
				
	//==> userId LINK Event End User 에게 보일수 있도록 
	$( "td:nth-child(2)" ).css("color" , "red");
	
});	

</c:if>

<c:if test="${param.menu=='manage'}">
$(function() {
	
	//==> DOM Object GET 3가지 방법 ==> 1. $(tagName) : 2.(#id) : 3.$(.className)
	$( "td:nth-child(2)" ).on("click" , function() {
		 var prodNo = $(this).data("param");
	        //Debug..
	        //alert(  $( this ).text().trim() );
	    	  
	           //self.location ="/product/updateProductView?prodNo="+prodNo+"${param.menu}";
	           self.location ="/product/getProductView?prodNo="+$(this).attr("prodNo");
	    	  
	});
				
	//==> userId LINK Event End User 에게 보일수 있도록 
	$( "td:nth-child(2)" ).css("color" , "red");
	
});	
</c:if>

$(function() {
	$( "td:nth-child(6) > i" ).on("click" , function() {
        //var prodNo = $(this).data("param");
        //Debug..
        //alert(  $( this ).text().trim() );
          var prodNo = $(this).next().val();
        	$.ajax(
        			{
        				url : "/product/json/getProduct/"+prodNo ,
						method : "GET" ,
						dataType : "json" ,
						headers : {
							"Accept" : "application/json",
							"Content-Type" : "application/json"
						},
						success : function(JSONData , status) {

							//Debug...
							//alert(status);
							//Debug...
							//alert("JSONData : \n"+JSONData);
							
							var displayValue = "<h6>"
														+"상품번호 : "+JSONData.prodNo+"<br/>"
														+"상품명 : "+JSONData.prodName+"<br/>"
														+"상품이미지 : "+JSONData.fileName+"<br/>"
														+"상품상세정보 : "+JSONData.prodDetail+"<br/>"
														+"제조일자 : "+JSONData.manuDate+"<br/>"
														+"가격 : "+JSONData.price+"<br/>"
														+"등록일 : "+JSONData.regDateString+"<br/>"
														+"</h3>";
							//Debug...									
							//alert(displayValue);
							$("h6").remove();
							$( "#"+prodNo+"" ).html(displayValue);
					}
        	});
        	
        
        
  });
	  $( ".ct_list_pop td:nth-child(3)" ).css("color" , "red");
      $("h7").css("color" , "red");
      
      $(".ct_list_pop:nth-child(4n+6)" ).css("background-color" , "whitesmoke");
  });
</script>
</head>

<body>
	
	<!-- ToolBar Start /////////////////////////////////////-->
	<jsp:include page="/layout/toolbar.jsp" />
   	<!-- ToolBar End /////////////////////////////////////-->
	
	<!--  화면구성 div Start /////////////////////////////////////-->
	<div class="container">
	
		<div class="page-header text-info">
	       <h3>상품목록조회</h3>
	    </div>
	    
	    <!-- table 위쪽 검색 Start /////////////////////////////////////-->
	    <div class="row">
	    
		    <div class="col-md-6 text-left">
		    	<p class="text-primary">
		    		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
		    	</p>
		    </div>
		    
		    <div class="col-md-6 text-right">
			    <form class="form-inline" name="detailForm" >
				  <div class="form-group">
				    <select class="form-control" name="searchCondition" >
						<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
						<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품이름</option>
						<option value="2"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>상품가격</option>
					</select>
				  </div>
				  
				  <div class="form-group">
				    <label class="sr-only" for="searchKeyword">검색어</label>
				    <input type="text" class="form-control" id="searchKeyword" name="searchKeyword"  placeholder="검색어"
				    			 value="${! empty search.searchKeyword ? search.searchKeyword : '' }"  >
				  </div>
				  
				  <button type="button" class="btn btn-default">검색</button>
				  
				  <!-- PageNavigation 선택 페이지 값을 보내는 부분 -->
				  <input type="hidden" id="currentPage" name="currentPage" value=""/>
				  
				</form>
	    	</div>
	    	
		</div>
		<!-- table 위쪽 검색 Start /////////////////////////////////////-->
		
		
      <!--  table Start /////////////////////////////////////-->
      <table class="table table-hover table-striped" >
      
        <thead>
          <tr>
            <th align="center">No</th>
            <th align="left" >상품명</th>
            <th align="left">가격</th>
            <th align="left">등록일</th>
            <th align="left">현재상태</th>
            <th align="left">간략정보</th>
          </tr>
        </thead>
       
		<tbody>
		
		  <c:set var="i" value="0" />
		  <c:forEach var="product" items="${list}">
			<c:set var="i" value="${ i+1 }" />
			<tr>
			  <td align="center">${ i }</td>
			  <td align="left" prodNo="${product.prodNo}" title="Click : 상품정보 확인">${product.prodName}</td>
			  <td align="left">${product.price}</td>
			  <td align="left">${product.regDate}</td>
			  <td align="left">${product.proTranCode}</td>
			  <td align="left">
			  	<i class="glyphicon glyphicon-ok" id= "${product.prodNo}"></i>
			  	<input type="hidden" value="${product.prodNo}">
			  </td>
			</tr>
          </c:forEach>
        
        </tbody>
      
      </table>
	  <!--  table End /////////////////////////////////////-->
	  
 	</div>
 	<!--  화면구성 div End /////////////////////////////////////-->
 	
 	
 	<!-- PageNavigation Start... -->
	<jsp:include page="../common/pageNavigator_new.jsp"/>
	<!-- PageNavigation End... -->
	
</body>
</html>
