function jumpTo(url) {
	if (url == null || url == '' || url == undefined) {
		return;
	}

	var form = document.getElementById("jumpForm");
	var urlItem = url.split("?");
	var params = null;
	if (urlItem.length > 1) {
		params = urlItem[1];
		var paramArr = params.split("&");
		if (paramArr.length > 0) {
			for (var i = 0; i < paramArr.length; i++) {
				var keyValue = paramArr[i];
				var keyValueArr = keyValue.split("=");
				var key = keyValueArr[0];
				var value = null;
				if (keyValueArr.length > 1) {
					value = keyValueArr[1];
				}
				var new_input = document.createElement("input");
				new_input.setAttribute("type", "text");
				new_input.setAttribute("name", key);
				new_input.setAttribute("value", value);
				new_input.setAttribute("hidden", 'true');

				form = document.getElementById("jumpForm");
				form.appendChild(new_input);

				var br = document.createElement("br");
				form.appendChild(br);
			}
		}
	}

	form.setAttribute("action", urlItem[0]);
	form.submit();
}

// 头动画
function showHeadGif() {
	var gifImgs = [];
	gifImgs[0] = "./img/gif/as_1.gif";
	gifImgs[1] = "./img/gif/as_2.gif";
	gifImgs[2] = "./img/gif/as_3.gif";
	gifImgs[3] = "./img/gif/flk_1.gif";
	gifImgs[4] = "./img/gif/j3_1.gif";
	var randomBgIndex = Math.round(Math.random() * 4);
	var img = document.getElementById('onepieceGif');
	img.setAttribute("src", gifImgs[randomBgIndex]);
}
window.onload=showHeadGif;
