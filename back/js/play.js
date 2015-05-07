function changePlayPanel(n) {
	if (n == 1) {
		$("#playPanel").text('窄屏');
		$("#vright").hide();
		$("#vleft").width("980px");
		$("#player").width("980px");
		$("#playPanel")
				.html(
						"<a href=\"javascript:void(0);\" onclick=\"javascript:changePlayPanel(0);\" class=\"playPanel\" id=\"witer\">窄屏</a>");
	} else {
		$("#playPanel").text('宽屏');
		$("#vright").show();
		$("#playPanel").attr("href", "javascript:changePlayPanel(1);");
		$("#vleft").width("720px");
		$("#player").width("720px");
		$("#playPanel")
				.html(
						"<a href=\"javascript:void(0);\" onclick=\"javascript:changePlayPanel(1);\" class=\"playPanel\" id=\"witer\">宽屏</a>");
	}
}
