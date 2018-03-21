<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/button.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/static/css/input.css"/>"/>
<script type="text/javascript" src="<c:url  value="/static/js/jquery-3.3.1.min.js"/>"></script>
<script type="text/javascript">
	$().ready(function(){
		$("#email").keyup(function(){
			var value = $(this).val();
			if(value != ""){
				$(this).removeClass("invalid");
				$(this).addClass("valid");
			}
			else{
				$(this).removeClass("valid");
				$(this).addClass("invalid");
			}
		});
		
		$("#password-confirm").keyup(function(){
			var value = $(this).val();
			var password = $("#password").val();
			
			if(value != password){
				$(this).removeClass("valid");
				$(this).addClass("invalid");
				$("#password").removeClass("valid");
				$("#password").addClass("invalid");
			}
			else{
				$(this).removeClass("invalid");
				$(this).addClass("valid");
				$("#password").removeClass("invalid");
				$("#password").addClass("valid");
			}
		})
		
		$("#registBtn").click(function(){
			if($("#email").val()==""){
				alert("이메일 오디감");
				$("#email").focus();
				$("#email").addClass("invalid");
				return false;
			}
			if($("#nickname").val()==""){
				alert("닉네임 오디감");
				$("#nickname").focus();
				$("#nickname").addClass("invalid");
				return false;
			}
			if($("#password").val()==""){
				alert("비번 오디감");
				$("#nicpasswordkname").focus();
				$("#nicpasswordkname").addClass("invalid");
				return false;
			}
			
			$("#registForm").attr({
				"action" : "<c:url value="/regist"/>",
				"method" : "post"
			}).submit();
			
		});
		
		
	});
</script>
</head>
<body>

	<div id="wrapper">
		<jsp:include page="/WEB-INF/view/template/menu.jsp" />
		<form:form modelAttribute="registForm">
			<div>
				<!-- TODO 이메일 중복검사 -->
				<input type="email" id="email" name="email" placeholder="Email" value="${registForm.email}"/>
				<div><form:errors path="email" /></div>
			</div>
			<div>
				<!-- TODO 닉네임 중복검사 -->
				<input type="text" id="nickname" name="nickname" placeholder="Nickname" />
				<div><form:errors path="nickname" /></div>
			</div>
			<div>
				<input type="password" id="password" name="password" placeholder="Password" />
			</div>
			<div>
				<input type="password" id="password-confirm" placeholder="Password" />
			</div>
			<div style="text-align:center;">
				<div id="registBtn" class="button">
					회원가입
				</div>
			
			</div>
		</form:form>
	</div>

</body>
</html>