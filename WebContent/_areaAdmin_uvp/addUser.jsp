<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1" import="controller.CheckSession"%>

<%
	String pageName = "addUser.jsp";
	String pageFolder = "_areaAdmin_uvp";
	CheckSession ck = new CheckSession(pageFolder, pageName, request.getSession());
	if (!ck.isAllowed()) {
		response.sendRedirect(request.getContextPath() + ck.getUrlRedirect());
	}
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
								<form id="signUp">

									<div
										class="col-lg-6 col-md-6 col-sm-12 col-xs-12 signUp-container">
										<div class="panel">
											<h2 class="text-center">Dati utente</h2>
										</div>

										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<input type="text" class="form-control" id="name"
												placeholder="Nome" maxlength="20" required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<input type="text" class="form-control" id="surname"
												placeholder="Cognome" maxlength="20" required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<input readonly="readonly"
												onfocus="this.removeAttribute('readonly');" type="email"
												class="form-control" id="email" placeholder="Email" required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<input type="text" class="form-control" id="office"
												placeholder="Ufficio" maxlength="10" required>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<label class="radio-inline"><input type="radio"
												class="sex" name="sex" value="M" required>M</label> <label
												class="radio-inline"><input type="radio" class="sex"
												name="sex" value="F" required>F</label>
										</div>
										<div class="clearfix"></div>
										<div class="form-group">
											<div class="panel">
												<h2 class="text-center">Dati tirocinio</h2>
											</div>
										</div>
										<div class="form-group col-lg-6 col-md-6 col-sm-12 col-xs-12">
											<input type="text" class="form-control" id="name"
												placeholder="Nome" maxlength="20" required>
										</div>

										<div
											class="form-group col-lg-12 col-md-12 col-sm-12 col-xs-12">
											<button type="submit" class="btn btn-primary btn-submit">Registrati</button>

										</div>

										<div class="clearfix"></div>
									</div>
								</form>
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
	<script src="<%=request.getContextPath()%>/js/pages_uvp/scripts_addUser.js"></script>
</body>
</html>