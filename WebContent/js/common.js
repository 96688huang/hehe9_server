// IE 中不支持
function jumpTo(url) {
	if (url == null || url == '' || url == undefined) {
		return;
	}

	var form = document.getElementById("jumpForm");
	var urlItem = url.split("?");
	var params = null;
	var elementArr = null;
	if (urlItem.length > 1) {
		params = urlItem[1];
		var paramArr = params.split("&");
		if (paramArr.length > 0) {
			elementArr = new Array();
			for (var i = 0; i < paramArr.length; i++) {
				var keyValue = paramArr[i];
				var keyValueArr = keyValue.split("=");
				var key = keyValueArr[0];
				var value = null;
				if (keyValueArr.length > 1) {
					value = keyValueArr[1];
				}
				
				var child = document.createElement("input");
				child.setAttribute("id", key);
				child.setAttribute("type", "text");
				child.setAttribute("name", key);
				child.setAttribute("value", value);
				child.setAttribute("hidden", 'true');
				
				elementArr[i] = child;
				form.appendChild(child);
			}
		}
	}

	form.setAttribute("action", urlItem[0]);
	form.submit();
	
	// form提交后，清空表单元素
	if(elementArr != null && elementArr.length > 0){
		for(var i = 0; i < elementArr.length; i++){
			form.removeChild(elementArr[i]);
		}
	}
}

function searchVideo(){
	$("#searchForm").attr("action", './list_videos.html');
	$("#searchForm").submit();
	
	// 清空
	$("#searchName").attr("value", '');
	$("#searchForm").attr("action", '');
}

function searchComic(){
	$("#searchForm").attr("action", './list_comics.html');
	$("#searchForm").submit();
	
	// 清空
	$("#searchName").attr("value", '');
	$("#searchForm").attr("action", '');
}

function onSearch(keyword) {
	if (keyword == null || keyword == '' || keyword == undefined) {
		return;
	}

	$("#searchName").attr("value", keyword);
	searchVideo();
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
window.setInterval(function() {
	showHeadGif()
}, 10000);

// Scroll page to the bottom
// $(document).ready(function() {
// $('html, body').animate({
// scrollTop : $(document).height()
// }, 'slow');
// });
