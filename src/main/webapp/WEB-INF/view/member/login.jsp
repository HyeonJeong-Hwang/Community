<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<c:url value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	$().ready(function(){
		
		<c:if test="${sessionScope.status eq 'emptyId'}">
			$("#errorId").show();
		</c:if>
		<c:if test="${sessionScope.status eq 'emptyPassword'}">
			$("#errorPassword").show();
		</c:if>
		
		$("#loginBtn").click(function(){
			if($("#email").val() == ""){
				//alert("아이디를 입력하세영");
				$("#errorId").slideDown(300);
				$("#email").focus();
				return false;
			}
			else{
				$("#errorId").slideUp(300);
			}
			
			if($("#password").val() == ""){
				$("#errorPassword").slideDown(300);
				$("#password").focus();
				return false;
			}
			else{
				$("#errorPassword").slideUp(300);
			}
			
			$("#loginForm").attr({
				"action" : "<c:url value="/login"/>",
				"method" : "post"
			}).submit();
		});
	});
</script>
</head>

<body>
	<jsp:include page="/WEB-INF/view/template/menu.jsp" />
	<form id="loginForm">
		<c:if test="${sessionScope.status eq 'fail'}">
			<div id="invalidIdAndPassword">
				<div>아이디 또는 비밀번호가 틀렸습니다.</div>
				<div>다시 확인해주세요.</div>
			</div>
		</c:if>
		<div>
			<input type="email" id="email" name="email" placeholder="email" />
		</div>
		<div id="errorId" style="display:none">아이디를 입력하세요!</div>
		<div>
			<input type="password" id="password" name="password" placeholder="password" />
		</div>
		<div id="errorPassword" style="display:none">패스워드를 입력하세요!</div>
		<div>
			<input type="button" id="loginBtn" value="Login" />
		</div>
	
	</form>
	
	<div>
		<a href="<c:url value="/regist"/>">아직 회원이 아니신가요?</a>
	</div>

</body>
</html>