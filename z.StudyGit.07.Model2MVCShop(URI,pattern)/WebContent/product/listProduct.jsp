<%@ page contentType="text/html; charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
<%
	<%@page import="java.util.ArrayList"%>
	<%@page import="java.util.HashMap"%>
	<%@page import="com.model2.mvc.common.SearchVO"%>
	<%@page import="com.model2.mvc.service.product.vo.ProductVO"%>

	HashMap<String,Object> map=(HashMap<String,Object>)request.getAttribute("map");
	SearchVO searchVO=(SearchVO)request.getAttribute("searchVO");
	String role = (String)request.getAttribute("menu");
	int total=0;
	ArrayList<ProductVO> list=null;
	if(map != null){
		total=((Integer)map.get("count")).intValue();
		list=(ArrayList<ProductVO>)map.get("list");
	}
	
	int currentPage=searchVO.getPage();
	
	int totalPage=0;
	if(total > 0) {
		totalPage= total / searchVO.getPageUnit() ;
		if(total%searchVO.getPageUnit() >0)
			totalPage += 1;
	}
	//페이지 몇개들어가는지
	//int count = 0;
	int count = (Integer)request.getAttribute("count");
	System.out.println("count :"+count);
	int pagesize = 3;
	int countPagesize = 0;

	
	if(totalPage%pagesize == 0){
		countPagesize = totalPage/pagesize;
	}else
		countPagesize = (totalPage/pagesize)+1;
	
%>
--%>
<html>
<head>
<title>상품목록조회</title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">

function fncGetUserList(currentPage) {
	document.getElementById("currentPage").value = currentPage;
   	document.detailForm.submit();		
}

</script>
</head>

<body bgcolor="#ffffff" text="#000000">

<div style="width:98%; margin-left:10px;">

<form name="detailForm" action="/listProduct.do?menu=${param.menu}" method="post">


<table width="100%" height="37" border="0" cellpadding="0"	cellspacing="0">
	<tr>
		<td width="15" height="37">
			<img src="/images/ct_ttl_img01.gif" width="15" height="37"/>
		</td>
		<td background="/images/ct_ttl_img02.gif" width="100%" style="padding-left:10px;">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="93%" class="ct_ttl01">상품 리스트</td>
				</tr>
			</table>
		</td>
		<td width="12" height="37">
			<img src="/images/ct_ttl_img03.gif" width="12" height="37">
		</td>
	</tr>
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td align="right">
			<select name="searchCondition" class="ct_input_g" style="width:80px">
				<option value="0" ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>상품번호</option>
				<option value="1" ${ search.searchCondition==1 ? "selected" : "" }>상품명</option>
				<option value="2" ${ search.searchCondition==2 ? "selected" : "" }>상품가격</option>
				<option value="3" ${ search.searchCondition==3 ? "selected" : "" }>가격높은순</option>
				<option value="4" ${ search.searchCondition==4 ? "selected" : "" }>가격낮은순</option>
			</select>
			<input 	type="text" name="searchKeyword" value="${! empty search.searchKeyword ? search.searchKeyword : ""}"  class="ct_input_g" 
							style="width:200px; height:20px" >
		</td>
		<td align="right" width="70">
			<table border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="17" height="23">
						<img src="/images/ct_btnbg01.gif" width="17" height="23"/>
					</td>
					<td background="/images/ct_btnbg02.gif" class="ct_btn01" style="padding-top:3px;">
						<a href="javascript:fncGetUserList('1');">검색</a>
					</td>
					<td width="14" height="23">
						<img src="/images/ct_btnbg03.gif" width="14" height="23"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>


<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		<td colspan="11" >
		전체  ${resultPage.totalCount } 건수, 현재 ${resultPage.currentPage}  페이지
		</tr>
	<tr>
		<td class="ct_list_b" width="100">No</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">상품명</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b" width="150">가격</td>
		<td class="ct_line02"></td>
		<td class="ct_list_b">등록일</td>	
		<td class="ct_line02"></td>
		<td class="ct_list_b">현재상태</td>	
	</tr>
	<tr>
		<td colspan="11" bgcolor="808285" height="1"></td>
	</tr>
		
	
	
	<c:set var="i" value="0"/>
	<c:forEach var="vo" items="${list }">
		<c:set var="i" value="${i+1}"/>
	
		
		<tr class="ct_list_pop">
			<td align="center">${ i }</td>
			<td></td>
			<td align="left">
				<a href="/getProduct.do?prodNo=${vo.prodNo }&menu=${param.menu }&tranCode=${vo.proTranCode}">${vo.prodName }</a></td>
			
			<td></td>
			<td align="left">${vo.price }</td>
			<td></td>
			<td align="left">${vo.regDate }</td>
			<td></td>
			<td align="left">
				
			<c:choose>
				<c:when test="${param.menu == 'manage'}">
					<c:choose>
						<c:when test="${empty vo.proTranCode}">
							판매중
						</c:when>
						<c:when test="${vo.proTranCode == '1'}">
							구매완료 ,<a href="/updateTranCodeByProd.do?prodNo=${vo.prodNo }&tranCode=2">배송하기</a>
						</c:when>		
						<c:when test="${vo.proTranCode == '2'}">
							배송중
						</c:when>		
						<c:when test="${vo.proTranCode == '3'}">
							판매완료 ,배송완료
						</c:when>		
					</c:choose>
					
				</c:when>
				
				<c:when test="${param.menu == 'search'}">
					<c:choose>
						<c:when test="${empty vo.proTranCode}">
							판매중
						</c:when>
						<c:when test="${vo.proTranCode == '1'}">
							재고없음
						</c:when>		
						<c:when test="${vo.proTranCode == '2'}">
							재고없음
						</c:when>		
						<c:when test="${vo.proTranCode == '3'}">
							재고없음
						</c:when>		
					</c:choose>				
				</c:when>		
			</c:choose>
	
	
				
			</td>
			
			<!--아마도 현재상태 판매중 들어가는곳 
			<td align="left">
			
				판매중
			
			</td>	-->
		</tr>
		<tr>
			<td colspan="11" bgcolor="D6D7D6" height="1"></td>
		</tr>	
	</c:forEach>
	
</table>

<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:10px;">
	<tr>
		
		<td align="center">
			 <input type="hidden" id="currentPage" name="currentPage" value=""/>
		<%--
		<%	
			//pagesize  3
			//countPagesize  4
			//for(int i=1;i<=totalPage;i++){
			%>	
				<%if(count != 0){ %>
					<a href="/listProduct.do?menu=${param.menu}&count=<%=count-1%>">앞</a>
					<%} %>
			
			<%if(count*pagesize+pagesize > totalPage){ 
				for(int i=count*pagesize+1;i<=totalPage;i++){%>
					<a href="/listProduct.do?menu=${param.menu}&page=<%=i%>&count=<%=count%>"><%=i %></a>
			<%}}else{ %>
				
			
			<% for(int i=count*pagesize+1;i<=count*pagesize+pagesize;i++){%>
			<a href="/listProduct.do?menu=${param.menu}&page=<%=i%>&count=<%=count%>"><%=i %></a>
				
		
		<%}}%>
				<%if(count !=countPagesize-1){ %>
					<a href="/listProduct.do?menu=${param.menu}&count=<%=count+1%>">뒤</a>
				<%} %>
		 --%>
		 <jsp:include page="../common/pageNavigator.jsp"/>
    	</td>
	</tr>
</table>
<!--  페이지 Navigator 끝 -->

</form>

</div>
</body>
</html>
