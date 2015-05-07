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
	$("#searchForm").attr("action", './video/videoListAction!list');
	$("#searchForm").submit();
	
	// 清空
	$("#searchName").attr("value", '');
	$("#searchForm").attr("action", '');
}

function searchComic(){
	$("#searchForm").attr("action", './comic/comicListAction!list');
	$("#searchForm").submit();
	
	// 清空
	$("#searchName").attr("value", '');
	$("#searchForm").attr("action", '');
}

function onSearch(keyword) {
	var encode = base64Encode('中国我爱你');
	alert("encode-->" + encode);
	
	var decode = base64Decode(encode);
	alert("decode-->" + decode);
	
//	//可由下面的代码编码为string  
//	var str = '';  
//	var len =  decode.length;
//	for(var i = 0 ; i < len ;++i){  
//	      str += String.fromCharCode(decode[i]);  
//	}  
//	alert("str-->" + str);
	
	if (keyword == null || keyword == '' || keyword == undefined) {
		return;
	}

	$("#searchName").attr("value", keyword);
	searchVideo();
}

//加密方法。没有过滤首尾空格，即没有trim.  
//加密可以加密N次，对应解密N次就可以获取明文  
function encodeBase64(mingwen,times){  
  var code="";  
  var num=1;  
  if(typeof times=='undefined'||times==null||times==""){  
     num=1;  
  }else{  
     var vt=times+"";  
     num=parseInt(vt);  
  }  

  if(typeof mingwen=='undefined'||mingwen==null||mingwen==""){  

  }else{  
      $.base64.utf8encode = true;  
      code=mingwen;  
      for(var i=0;i<num;i++){  
         code=$.base64.btoa(code);  
      }  
  }  
  return code;  
}  


//解密方法。没有过滤首尾空格，即没有trim  
//加密可以加密N次，对应解密N次就可以获取明文  
function decodeBase64(mi,times){  
  var mingwen="";  
  var num=1;  
  if(typeof times=='undefined'||times==null||times==""){  
     num=1;  
  }else{  
     var vt=times+"";  
     num=parseInt(vt);  
  }  


  if(typeof mi=='undefined'||mi==null||mi==""){  

  }else{  
     $.base64.utf8encode = true;  
     mingwen=mi;  
     for(var i=0;i<num;i++){  
         mingwen=$.base64.atob(mingwen);  
     }  
  }  
  return mingwen;  
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
