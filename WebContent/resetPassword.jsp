<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1" import="controller.CheckSession"%>

<%
	String pageName = "resetPassword.jsp";
	String pageFolder = "";
%>

<!DOCTYPE html>
<html>
<head>
<jsp:include page="/partials/head.jsp" />
</head>

<body>
	<div class="page-wrapper">

		<jsp:include page="/partials/header.jsp">
			<jsp:param name="pageName" value="<%=pageName%>" />
			<jsp:param name="pageFolder" value="<%=pageFolder%>" />
		</jsp:include>

		<div class="sidebar-page-container basePage signUpPage">
			<div class="auto-container">
				<div class="row clearfix">
					<div class="content-side col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<div class="content">
							<div class="news-block-seven">
								<div
									class="col-lg-6 col-md-6 col-sm-12 col-xs-12 signUp-container">
									<div class="panel">
										<h2 class="text-center">Inserisci la tua E-mail</h2>
										<p></p>
									</div>
									<form id="recuperaPassword">
										<input type="email" class="form-control" id="email"
											placeholder="Email" minlength="1" required>
										<p></p>
										<div
											class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
											<button type="submit" class="btn btn-primary btn-submit">Invia</button>
										</div>
									</form>
									<div class="clearfix"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<jsp:include page="/partials/footer.jsp" />
	</div>
	<!--End pagewrapper-->

	<jsp:include page="/partials/includes.jsp" />
	<script
		src="<%=request.getContextPath()%>/js/pages_uvp/scripts_resetPassword.js"></script>

</body>
</html>
