	$(document)
			.ready(
					function() {

						$(document)
								.on(
										'submit',
										'#signUp',
										function(e) {
											var email = $("#email").val();
											$
															.ajax({
																url : absolutePath
																		+ "/ServletStudent",
																type : "POST",
																dataType : 'JSON',
																async : false,
																data : {
																	
																	
																	"email" : email,
																	
																	
																	
																},
																success : function(
																		msg) {
																	if (!msg.result) {
																		showAlert(
																				1,
																				msg.error);
																	} else {
																		showAlert(
																				0,
																				msg.content);

																		setTimeout(
																				function() {
																					window.location.href = msg.redirect;
																				},
																				2000);
																	}
																},
																error : function(
																		msg) {
																	showAlert(1,
																			"Impossibile Recuperare i dati.");
																}
															});

													$(".preloader").hide();
												
												
												
											});

					});