var $$ = function (id) {
	return document.getElementById(id);
}

var getByClass = function (oParent, sClass) {
	var aEle = oParent.getElementsByTagName("*");
	var re = new RegExp("\\b" + sClass + "\\b");
	var arr = [];

	for (var i = 0; i < aEle.length; i++) {
		if (re.test(aEle[i].className)) {
			arr.push(aEle[i]);
		}
	}
	return arr;
}

var setMainNav = function () {
	var oMainNav = $$("letterMainNav");
	var aLi = getByClass(oMainNav, "menu2_list")[0].getElementsByTagName("li");
	var aGameHover = getByClass(oMainNav, "menu2_name");
	var aHoverCont = getByClass(oMainNav, "menu2_hover_cont");

	for (var i = 0; i < aGameHover.length; i++) {
		aGameHover[i].index = i;
		aGameHover[i].onmouseover = function () {
			this.className += " "+"game_hover_current";
			for (var j = 0; j < aHoverCont.length; j++) {
				aHoverCont[j].index_j = j;
				aHoverCont[j].style.display = "none";
				aHoverCont[j].onmouseover = function () {
					this.style.display = "block";
					aGameHover[this.index_j].className += " "+"game_hover_current";
				}

				aHoverCont[j].onmouseout = function () {
					// this.style.display = "none"; //鼠标离开后, 不隐藏;
				}
			}
			if (aHoverCont[this.index]) {
				aHoverCont[this.index].style.display = "block";
			}
		}
	}

	for (var i = 0; i < aLi.length; i++) {
		aLi[i].index = i;
		aLi[i].onmouseout = function () {
			if (aHoverCont[this.index]) {
				// aHoverCont[this.index].style.display = "none"; //鼠标离开后, 不隐藏;
			}
			aGameHover[this.index].className = "menu2_name";
		}
	}
}

window.onload = function () {
	setMainNav();
}