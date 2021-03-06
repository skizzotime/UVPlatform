$(document).ready(function() {
	$(document).on('submit','#recuperaPassword',function() {
		var email = $("#email").val();
		$.ajax({
			url : absolutePath + "/resetPassword",
			type : "POST",
			dataType : 'JSON',
			async : false,
			data : 
			{
				"email" : email,
			},
			success : function(msg) {
				if (!msg.result) {
					showAlert(1,msg.error);
				} else {
					showAlert(0,msg.content);
					setTimeout(function() {
						window.location.href = msg.redirect;
					}, 1000);
				}
			},
			error : function(msg) {
				showAlert(1,"Impossibile inviare l'email");
			}
		});
	});
});