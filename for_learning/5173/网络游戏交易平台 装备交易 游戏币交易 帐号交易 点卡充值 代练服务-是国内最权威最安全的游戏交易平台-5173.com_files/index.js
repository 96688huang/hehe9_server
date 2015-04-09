/* Build by 336559@5173.com 
 Date:2015-03-18 10:38:14 
 Version:1.00 */
$(function() {
	var D = $(document);
	$.inputPlaceholder = function(o, n) {
		var r = document.createElement("input"), t = !!("placeholder" in r);
		r = null;
		$(o).each(
				function() {
					this.value = "";
					var s = $(this).attr("placeholder"), A = $(this).css(
							"color"), y = s && !t, k = this.value;
					k && k !== s && $(this).css({
						color : n
					});
					if (y && !k)
						this.value = s;
					$(this).focus(function() {
						if (y && this.value === s)
							this.value = "";
						$(this).css({
							color : n
						})
					}).blur(function() {
						if (!this.value) {
							if (y)
								this.value = s;
							$(this).css({
								color : A
							})
						}
					})
				})
	};
	$.analogSubmit = function(o, n) {
		var r = document.createDocumentFragment(), t = $('<form method="get" target="_blank" />');
		if (n) {
			o += n.url;
			$.each(n.extras, function(s, A) {
				if (A !== "undefined") {
					s = $('<input type="hidden" name="' + s + '" value="' + A
							+ '" />');
					r.appendChild(s[0])
				}
			});
			t[0].appendChild(r)
		}
		t.attr("action", o).appendTo($("body")).submit().remove();
		t = r = null
	};
	$.easyAjax = function(o, n, r, t) {
		o = {
			url : o,
			dataType : "jsonp",
			scriptCharset : "GBK",
			jsonp : "jsoncallback",
			contentType : "application/x-www-form-urlencoded; charset=GBK",
			success : r,
			complete : t
		};
		if (n) {
			o.cache = true;
			o.jsonpCallback = n
		}
		$.ajax(o)
	};
	$.keyMove = function(o, n, r) {
		var t = o.filter(".current"), s = t.index(), A = o.parent(), y = A[0].scrollHeight > A[0].offsetHeight, k = o.length;
		s += n;
		t.removeClass("current");
		t = o.eq(y ? s < 0 ? 0 : s === k ? k - 1 : s : s === k ? 0
				: s === -2 ? k - 1 : s);
		t.addClass("current");
		if (y) {
			o = o.outerHeight();
			A.scrollTop(n === 1 ? A.scrollTop() + o : A.scrollTop() - o)
		}
		r && r.val(t.text())
	};
	var J = function(o) {
		var n = o.parents(".like_select"), r = n.find("span.text"), t = o
				.text();
		if (r.text() !== t) {
			r.text(t);
			n.trigger("likeChange")
		}
		z(o.parent())
	}, Y = function(o) {
		var n = o.data.elem;
		switch (o.which) {
		case 38:
			$.keyMove(n, -1);
			o.preventDefault();
			break;
		case 40:
			$.keyMove(n, 1);
			o.preventDefault();
			break;
		case 13:
			J(n.filter(".current"));
			o.preventDefault();
			break
		}
	}, z = function(o) {
		o.parent().css("zIndex", "").end().hide();
		D.unbind("keydown", Y).unbind("click", C)
	}, C = function(o) {
		var n = o.data.elem, r = n.parent()[0];
		$.contains(r, o.target) || z(n)
	};
	$("div.like_selected").click(function(o) {
		var n = $(this).next(), r = $("ul.like_option:visible");
		r.length && r[0] !== n[0] && z(r);
		if (n.is(":hidden")) {
			n.find("li").length > 8 ? n.css({
				height : "180px",
				overflowY : "scroll"
			}) : n.css({
				height : "auto",
				overflowY : "auto"
			});
			n.parent().css("zIndex", "1000").end().show();
			D.bind("keydown", {
				elem : n.find("li")
			}, Y).bind("click", {
				elem : n
			}, C)
		} else
			z(n);
		o.stopPropagation()
	});
	$(".like_option").delegate(
			"li",
			"mouseover",
			function() {
				$(this).siblings(".current").removeClass("current").end()
						.addClass("current")
			}).delegate("li", "click", function() {
		J($(this))
	});
	$.fn.likeValue = function() {
		if ($(this).hasClass("like_select"))
			return $(this).find("li.current").attr("data-value");
		return ""
	};
	$.fn.likeSelected = function() {
		$(this).hasClass("like_select")
				&& $(this).find("span.text").text(
						$(this).find("li.current").text());
		return this
	}
});
$(function() {
	function D(b) {
		Z.unbind("likeChange");
		Z.bind("likeChange", function() {
			V.addClass("disabled");
			ha.addClass("disabled");
			ISPindex = W(N[0].value);
			if ($.trim(N[0].value).length !== 11 && ISPindex != -1)
				B("Phone", true);
			else {
				var a = $(this).find("li.current").html();
				if (b && ia) {
					b.priceid = va(ia, a);
					aa(b)
				}
			}
		})
	}
	function J(b, a) {
		var c = b.find(".like_option li"), e = b.find(".like_selected");
		Q.find(".like_option").attr("data-value");
		if (a) {
			e.bind("click",
					function() {
						var g = b.find(".text").attr("data-value"), h = Q.find(
								".text").attr("data-value");
						Aa.find(".like_option li.current").attr("data-value");
						var j = ra.find(".like_option li.current").attr(
								"data-value"), q = e.find(".text").attr(
								"no-loading");
						if (b.attr("id") != "rcGold2CardGameName"
								&& b.attr("id") != "rcGold2CardGameServer") {
							if (g != h && q == 1) {
								Ba({
									ajaxUrl : a + h,
									elemObj : b
								});
								e.find(".text").attr("no-loading", 0)
							}
						} else if (b.attr("id") == "rcGold2CardGameServer") {
							if (g != h && q == 1) {
								Ba({
									ajaxUrl : a + h + "&areaId=" + j,
									elemObj : b
								});
								e.find(".text").attr("no-loading", 0)
							}
						} else
							b.attr("id");
						return false
					});
			c.live("click", function() {
				var g = $(this), h = g.closest(".like_select"), j = Q.find(
						".text").attr("data-value");
				ra.find(".text").attr("data-value");
				var q = h.find(".text").attr("data-value"), v = g
						.attr("data-value");
				if (h.attr("id") == "rcGold2CardGameName") {
					Q.find(".text").attr("data-value", g.attr("data-value"));
					Y(v)
				} else
					h.attr("id") != "rcGold2CardGameArea" && q != j
							&& h.find(".text").attr("data-value", j);
				C(h);
				z();
				return false
			})
		}
	}
	function Y(b) {
		$.easyAjax(ba + "methodName=GetGameCampSelect&gameId=" + b,
				"Gold2Cardload_area", function(a) {
					if (a)
						ja.closest("li").show();
					else {
						ja.closest("li").hide();
						ja.find(".like_option li.current")
								.attr("data-value", 1)
					}
				})
	}
	function z() {
		var b, a = ra.find(".like_option li.current").attr("data-value"), c = Aa
				.find(".like_option li.current").attr("data-value"), e = ja
				.find(".like_option li.current").attr("data-value"), g = wa
				.find(".like_option li.current").attr("data-value");
		g = escape(g);
		b = Q.find(".like_option li.current").attr("data-value") ? Q.find(
				".like_option li.current").attr("data-value") : Q.find(
				".like_selected .text").attr("data-value");
		e || (e = "");
		var h = ba + "methodName=GetProduct&gameId=" + b + "&areaId=" + a
				+ "&serverId=" + c + "&campId=" + e + "&subBiz=" + g, j;
		if (b && a && c && g) {
			e
					|| (h = ba + "methodName=GetProduct&gameId=" + b
							+ "&areaId=" + a + "&serverId=" + c
							+ "&campId=&subBiz=" + g);
			$.easyAjax(h, "Gold2Cardload_1", function(q) {
				if (q) {
					xa.show();
					xa.find(".price").html(q.Value);
					j = "http://huangou.5173.com/order/buyCard.aspx?id=" + q.Id
				} else {
					xa.hide();
					j = "http://huangou.5173.com/"
				}
				Fa.attr("href", j)
			})
		} else {
			xa.hide();
			j = "http://huangou.5173.com/"
		}
		Fa.attr("href", j)
	}
	function C(b) {
		switch (b.attr("id")) {
		case "rcGold2CardGameName":
			o([ "rcGold2CardGameArea", "rcGold2CardGameServer",
					"rcGold2CardGameCamp", "rcGold2CardGamePriceSel" ]);
			break;
		case "rcGold2CardGameArea":
			o([ "rcGold2CardGameServer" ]);
			break;
		default:
		}
	}
	function o(b) {
		for (var a = 0; a < b.length; a++) {
			selcetId = $("#" + b[a]).attr("id");
			$("#" + selcetId).find(".text").html(Ha[selcetId]);
			$("#" + selcetId).find(".text").attr("data-value", "");
			$("#" + selcetId).find(".text").attr("no-loading", 1);
			$("#" + selcetId).find("ul.like_option").html("")
		}
	}
	var n = $(document), r = !!window.__utmTrackEvent, t = function(b) {
		var a = new Date, c = a.getTime();
		a.setTime(a.getTime() + 31536E5);
		document.cookie = "5173BMCenterVisitTimeAndGameID="
				+ encodeURIComponent(c + "|" + b + "|bmcenter.5173.com")
				+ "; path=/; domain=5173.com; expire=" + a.toGMTString()
	}, s = function(b) {
		var a = [], c = $.cookie("LocalPhones");
		if (c)
			a = c.split(",");
		c = false;
		for (var e = 0; e < a.length; e++)
			if (a[e] == b) {
				c = true;
				break
			}
		if (c == false) {
			a.push(b);
			a.length == 4 && a.splice(0, 1);
			c = a.join(",");
			$.cookie("LocalPhones", c);
			y(a)
		}
	}, A = function(b) {
		var a = [], c = $.cookie("LocalPhones");
		if (c)
			a = c.split(",");
		for (c = 0; c < a.length; c++)
			if (a[c] == b) {
				a.splice(c, 1);
				c = a.join(",");
				$.cookie("LocalPhones", c);
				y(a);
				break
			}
	}, y = function(b) {
		for (var a = $("#rcPhoneNumberList").find("ul.history_number").empty(), c = b.length - 1; c >= 0; c--) {
			var e = $("<li " + (c === b.length - 1 ? 'class="current"' : "")
					+ "><span>" + b[c] + "</span></li>"), g = $('<a href="#" class="remove_btn" title="\u5220\u9664\u8be5\u8bb0\u5f55" phone="'
					+ b[c] + '" target="_self"></a>');
			g.click(function() {
				var h = $(this).attr("phone");
				A(h)
			});
			a.append(e.append(g))
		}
		a.css("zIndex", 500).end().find("a.arrow_box").show().end().find(
				"input.fed_input").val(b[b.length - 1])
	}, k = function() {
		var b = $.cookie("LocalPhones");
		if (b) {
			localPhoneArray = b.split(",");
			localPhoneArray.length > 0 && y(localPhoneArray)
		}
	};
	k();
	var x = function() {
		var b = [], a = $.cookie("LocalPhones");
		if (a)
			b = a.split(",");
		a = false;
		for (var c = 0; c < b.length; c++)
			if (b[c] == phone) {
				a = true;
				break
			}
		if (a == false) {
			b.push(phone);
			b.length == 4 && b.splice(0, 1);
			a = b.join(",");
			$.cookie("LocalPhones", a, {
				expires : 3650
			});
			y(b)
		}
	};
	A = function(b) {
		var a = [], c = $.cookie("LocalPhones");
		if (c)
			a = c.split(",");
		for (c = 0; c < a.length; c++)
			if (a[c] == b) {
				a.splice(c, 1);
				c = a.join(",");
				$.cookie("LocalPhones", c, {
					expires : 3650
				});
				y(a);
				break
			}
	};
	y = function(b) {
		for (var a = $("#rcPhoneNumberList").find("ul.history_number").empty(), c = b.length - 1; c >= 0; c--) {
			var e = $("<li " + (c === b.length - 1 ? 'class="current"' : "")
					+ "><span>" + b[c] + "</span></li>"), g = $('<a href="#" class="remove_btn" title="\u5220\u9664\u8be5\u8bb0\u5f55" phone="'
					+ b[c] + '" target="_self"></a>');
			g.click(function() {
				var h = $(this).attr("phone");
				A(h)
			});
			a.append(e.append(g))
		}
		a.css("zIndex", 500).end().find("a.arrow_box").show().end().find(
				"input.fed_input").val(b[b.length - 1])
	};
	k = function() {
		var b = $.cookie("LocalPhones");
		if (b) {
			localPhoneArray = b.split(",");
			localPhoneArray.length > 0 && y(localPhoneArray)
		}
	};
	k();
	x = function(b) {
		var a = [], c = $.cookie("LocalQQs");
		if (c)
			a = c.split(",");
		c = false;
		for (var e = 0; e < a.length; e++)
			if (a[e] == b) {
				c = true;
				break
			}
		if (c == false) {
			a.push(b);
			a.length == 4 && a.splice(0, 1);
			c = a.join(",");
			$.cookie("LocalQQs", c, {
				expires : 3650
			});
			I(a)
		}
	};
	var K = function(b) {
		var a = [], c = $.cookie("LocalQQs");
		if (c)
			a = c.split(",");
		for (c = 0; c < a.length; c++)
			if (a[c] == b) {
				a.splice(c, 1);
				c = a.join(",");
				$.cookie("LocalQQs", c, {
					expires : 3650
				});
				I(a);
				break
			}
	}, I = function(b) {
		for (var a = $("#rcQQNumberList").find("ul.history_number").empty(), c = b.length - 1; c >= 0; c--) {
			var e = $("<li " + (c === b.length - 1 ? 'class="current"' : "")
					+ "><span>" + b[c] + "</span></li>"), g = $('<a href="#" class="remove_btn" title="\u5220\u9664\u8be5\u8bb0\u5f55" qq="'
					+ b[c] + '" target="_self"></a>');
			g.click(function() {
				var h = $(this).attr("qq");
				K(h)
			});
			a.append(e.append(g))
		}
		a.css("zIndex", 500).end().find("a.arrow_box").show().end().find(
				"input.fed_input").val(b[b.length - 1])
	};
	(function() {
		var b = $.cookie("LocalQQs");
		if (b) {
			localQQArray = b.split(",");
			localQQArray.length > 0 && I(localQQArray)
		}
	})();
	var la = function(b) {
		b.parents("li").find("input.fed_input").val(b.find("span").text())
				.trigger("keyup");
		ka(b.parent());
		B("Phone", false)
	}, ma = function(b) {
		var a = b.data.elem;
		switch (b.which) {
		case 38:
			$.keyMove(a, -1);
			b.preventDefault();
			break;
		case 40:
			$.keyMove(a, 1);
			b.preventDefault();
			break;
		case 13:
			la(a.filter(".current"));
			b.preventDefault();
			break
		}
	}, ka = function(b) {
		b.parent().css("zIndex", "").end().hide();
		n.unbind("keydown", ma).unbind("click", ca)
	}, ca = function(b) {
		var a = b.data.elem, c = a.parent()[0];
		$.contains(c, b.target) || ka(a)
	};
	$("ul.recharge_list a.arrow_box").click(function(b) {
		var a = $(this).next(), c = a.find("li"), e = c.length;
		if (a.is(":hidden") && e) {
			e > 8 ? a.css({
				height : "180px",
				overflowY : "scroll"
			}) : a.css({
				height : "auto",
				overflowY : "auto"
			});
			a.parent().css("zIndex", "1000").end().show();
			n.bind("keydown", {
				elem : c
			}, ma).bind("click", {
				elem : a
			}, ca)
		} else
			ka(a);
		b.preventDefault()
	});
	$(".history_number")
			.delegate(
					"li",
					"mouseover",
					function() {
						$(this).siblings().removeClass("current").end()
								.addClass("current")
					})
			.delegate("li", "click", function() {
				la($(this))
			})
			.delegate(
					"a",
					"click",
					function() {
						var b = $(this), a = b.parents(".history_number"), c = R
								+ "ajax.axd?methodName=DeleteAccount&", e;
						b.parent().remove();
						e = a.find("li").length;
						if (e < 8) {
							a.css({
								height : "auto",
								overflowY : "auto"
							});
							e || a.hide()
						}
						c += "gameid="
								+ (a.parent()[0].id === "rcPhoneNumberList" ? "319522a68d3c40958094df8482a26d98"
										: "e8d058a210e64b8aa20789852bfca469")
								+ "&account=" + b.prev().text();
						$.easyAjax(c, null, $.noop);
						return false
					});
	$.fed.tabs("#recharge", {
		event : "mouseover",
		onSuccess : function(b) {
			var a = $(b), c = a
					.find("ul.like_option:visible,ul.history_number:visible");
			a.trigger("loadData");
			c.length && c.hide().parent().css("zIndex", "");
			if (b == "#rechargeTab5") {
				Ca.stopAutoNum();
				Ca.init()
			} else
				Ca.stopAutoNum()
		}
	});
	var N = $("#rcPhoneNumber"), O = $("#rcPhoneAttribution"), Z = $("#rcPhonePriceSel"), na = $("#rcPhonePrice"), ha = $("#rcPhoneBtnNoneLogin"), V = $("#rcPhoneBtn"), sa = {}, F = {}, X = /[^\d]+/, L = "http://"
			+ (window.FED_DATAHOST || "fcd.5173.com")
			+ "/BMCenter/getjsondata.aspx?type=dkjy&", R = "http://dkjy.5173.com/", ia;
	$.inputPlaceholder(N[0], "#333");
	var W = function(b) {
		var a = /^(130|131|132|155|156|186|185|145|176)[0-9]{8}$/, c = /^(133|153|189|180|181|177)[0-9]{8}$/;
		return /^(139|138|137|136|135|134|159|158|152|151|150|157|188|187|147|182|183|178)[0-9]{8}$/
				.test(b) ? 0 : a.test(b) ? 1 : c.test(b) ? 2 : -1
	}, B = $.rcToggleError = function(b, a, c) {
		a = a ? "inherit" : "hidden";
		b = $("#rc" + b + "Tip");
		c && b.html('<s class="ico_error_1"></s>' + c);
		b.css({
			visibility : a
		})
	}, aa = function(b) {
		b.number
				&& $
						.easyAjax(
								L
										+ "methodName=GETJSON4BM4GETPRICE&cache=600&strvalue="
										+ b.number + "&gameid=" + b.gameid
										+ "&mianzhiid=" + b.priceid
										+ "&gameareaid=" + b.areaid
										+ "&datasource=" + b.datasource,
								"callphoneprice",
								function(a) {
									a = a[0];
									var c = a.ConsignmentPrice, e = c
											.indexOf("$"), g = a.BizOfferAD, h = a.ActivityBizOfferUrl, j = $("#discountPhone");
									if (h) {
										j.html(g);
										j.attr("href", h);
										j.show()
									} else {
										j.html(g);
										j.hide()
									}
									if (~e) {
										B("Phone", false);
										a = c.substring(0, e);
										c = c.substring(e + 1);
										F.url = "order/mobilestep1.aspx";
										F.extras = {
											offerid : a,
											mobile : b.number,
											save : "1",
											fast : "1"
										}
									} else {
										B(
												"Phone",
												true,
												"\u6b64\u9762\u503c\u6682\u65e0\u5546\u54c1\uff0c\u8bf7\u9009\u62e9\u5176\u4ed6\u3002");
										$("#rechargeTab1 .gg").eq(1).show();
										F.url = "BizOffer/CCCard/ViewList.aspx";
										F.extras = {
											gameid : b.gameid,
											gameareaid : a.gameareaid,
											mianzhi : b.priceid
										}
									}
									na.find("b.price").text(c);
									if (c !== "-1")
										na.show();
									else
										na.is(":visible") && na.hide();
									F.extras.u_ref = "5173_index_sidebar_bianmin_phone";
									V.removeClass("disabled");
									ha.removeClass("disabled")
								})
	}, va = function(b, a) {
		var c;
		$.each(b, function(e, g) {
			if (g.name === a) {
				c = this.id;
				return false
			}
		});
		return c
	}, da = {
		init : function(b) {
			this.renderHtml(b)
		},
		renderHtml : function(b) {
			var a = "", c = "";
			if (b.type == "phone") {
				a = "rcPhoneBtnNoneLoginTrue";
				c = "rcPhoneBtnNoneLoginFailed"
			} else if (b.type == "qq") {
				a = "rcQQBtnNoneLoginTrue";
				c = "rcQQBtnNoneLoginFailed"
			}
			if ($("#layer_rcBtnNoneLogin").length === 0) {
				var e = "<style>";
				e += "    #layer_rcBtnNoneLogin .pop_tittle .close {background: url(http://img01.5173cdn.com/fed/build/1.00/images/layer_close.gif) no-repeat; width: 45px; height: 17px; display: block; position: absolute; top: -2px; right: 10px; }";
				e += "    #layer_rcBtnNoneLogin .btnlink_b_small , #layer_rcBtnNoneLogin .btnlink_g_small{ float:left; }";
				e += "    #layer_rcBtnNoneLogin {";
				e += "        width: 340px;";
				e += "        position: absolute;";
				e += "        z-index: 99999;";
				e += "        right: 0;";
				e += "        top: 170px;";
				e += "        background: #ffffff;";
				e += "    }";
				e += "    #layer_rcBtnNoneLogin .pop_box {";
				e += "        width: 340px;";
				e += "    }";
				e += "    #layer_rcBtnNoneLogin .pop_mainbox {";
				e += "        width: 320px;";
				e += "    }";
				e += "</style>";
				e += '<div id="layer_rcBtnNoneLogin" class="">';
				e += '    <div class="pop_box">';
				e += '        <div class="pop_tittle">';
				e += "            <h3>\u53cb\u60c5\u63d0\u793a</h3>";
				e += '            <span class="close J-close"></span>';
				e += "        </div>";
				e += '        <div class="pop_mainbox">';
				e += '            <div class="side_icon">';
				e += '                <s class="ico_warning_5"></s>';
				e += "            </div>";
				e += '            <div class="right_main">';
				e += '                <p class="J-tips"></p>';
				e += '                <div class="geayline"></div>';
				e += '                <div class="btnBox mt10">';
				e += '                    <div class="J-yes btnlink_b_small ml10">';
				e += '                        <span id="' + a
						+ '" class="w70">\u5145&nbsp;&nbsp;&nbsp;\u503c</span>';
				e += "                    </div>";
				e += '                    <div class="J-close btnlink_g_small ml10">';
				e += '                        <span id="' + c
						+ '" class="w70">\u53d6&nbsp;&nbsp;&nbsp;\u6d88</span>';
				e += "                    </div>";
				e += "                </div>";
				e += "            </div>";
				e += "        </div>";
				e += "    </div>";
				e += "</div>";
				$(".content").css("position", "relative").append(e);
				$("#layer_rcBtnNoneLogin").find(".J-close").bind("click",
						function() {
							$("#layer_rcBtnNoneLogin").remove()
						});
				$("#layer_rcBtnNoneLogin")
						.find(".w70")
						.bind(
								"click",
								function() {
									var h = $(this).attr("id");
									if (h == "rcPhoneBtnNoneLoginTrue")
										_hmt
												.push([
														"_trackEvent",
														"\u9996\u9875",
														"\u70b9\u51fb\u91cf",
														"\u4fbf\u6c11\u4e2d\u5fc3",
														"\u8bdd\u8d39\u5145\u503c\u514d\u767b\u5f55\u5145\u503c" ]);
									else if (h == "rcPhoneBtnNoneLoginFailed")
										_hmt
												.push([
														"_trackEvent",
														"\u9996\u9875",
														"\u70b9\u51fb\u91cf",
														"\u4fbf\u6c11\u4e2d\u5fc3",
														"\u8bdd\u8d39\u5145\u503c\u514d\u767b\u5f55\u5145\u503c\u53d6\u6d88" ]);
									else if (h == "rcQQBtnNoneLoginTrue")
										_hmt
												.push([
														"_trackEvent",
														"\u9996\u9875",
														"\u70b9\u51fb\u91cf",
														"\u4fbf\u6c11\u4e2d\u5fc3",
														"QQ\u5145\u503c\u514d\u767b\u5f55\u5145\u503c" ]);
									else
										h == "rcQQBtnNoneLoginFailed"
												&& _hmt
														.push([
																"_trackEvent",
																"\u9996\u9875",
																"\u70b9\u51fb\u91cf",
																"\u4fbf\u6c11\u4e2d\u5fc3",
																"QQ\u5145\u503c\u514d\u767b\u5f55\u5145\u503c\u53d6\u6d88" ])
								})
			} else
				$("#layer_rcBtnNoneLogin").show();
			var g = $("#layer_rcBtnNoneLogin");
			g.find(".J-tips").html(b.tips);
			g.find(".J-yes").unbind("click").bind("click", function() {
				b.submit();
				g.remove()
			})
		}
	};
	k = function(b, a) {
		b
				.click(function(c) {
					if (!$(this).hasClass("disabled")) {
						var e = W($.trim(N[0].value));
						if ($.trim(N[0].value).length === 11 && e != -1) {
							if (F.url) {
								if (~F.url.indexOf("order"))
									F.url = a;
								t(sa.gameid);
								if ($(b).attr("id") == "rcPhoneBtnNoneLogin")
									if (F.extras.mobile)
										da
												.init({
													type : "phone",
													tips : '<p>\u60a8\u5373\u5c06\u4e3a\u624b\u673a\u53f7\u7801<span class="num orange fb">&nbsp;'
															+ F.extras.mobile
															+ "&nbsp;</span>\u8fdb\u884c\u5145\u503c\uff0c\u8bf7\u6838\u5bf9\u5145\u503c\u53f7\u7801\u662f\u5426\u6b63\u786e\uff01</p>",
													submit : function() {
														s($.trim(N[0].value));
														$.analogSubmit(R, F)
													}
												});
									else {
										s($.trim(N[0].value));
										$.analogSubmit(R, F)
									}
								else
									$.analogSubmit(R, F);
								if (r)
									__utmTrackEvent(
											encodeURIComponent("\u65b0\u7248\u9996\u9875"),
											encodeURIComponent("\u4fbf\u6c11\u4e2d\u5fc3"),
											~a.indexOf("anonymstep") ? encodeURIComponent("\u514d\u767b\u5f55\u8d2d\u4e70-PHONE")
													: encodeURIComponent("\u767b\u5f55\u8d2d\u4e70-PHONE"))
							}
						} else {
							B("Phone", true);
							if (O.is(":visible")) {
								O.hide();
								$("#rechargeTab1 .gg").eq(1).show()
							}
						}
					}
					c.preventDefault()
				})
	};
	var ta = function(b, a) {
		window.isSignIn
				&& $
						.easyAjax(
								L + b,
								null,
								function(c) {
									var e = "";
									$
											.each(
													c,
													function(g, h) {
														e += "<li "
																+ (g === 0 ? 'class="current"'
																		: "")
																+ "><span>"
																+ h.account
																+ '</span><a href="#" class="remove_btn" title="\u5220\u9664\u8be5\u8bb0\u5f55"></a></li>'
													});
									e
											&& a.find("ul.history_number")
													.html(e).end().find(
															"a.arrow_box")
													.show().end().find(
															"input.fed_input")
													.val()
								})
	};
	window.initRechargeHistory = function() {
		ta(
				"methodName=GetUserChargedAccounts&gameid=319522a68d3c40958094df8482a26d98&length=10",
				$("#rcPhoneNumberList"));
		try {
			window.initRechargeHistory = null;
			delete window.initRechargeHistory
		} catch (b) {
		}
	};
	N
			.keyup(
					function() {
						var b = $.trim(this.value), a = b.length, c;
						if (X.test(b))
							this.value = b.replace(X, "");
						else if (a === 11) {
							c = W(b);
							if (~c) {
								B("Phone", false);
								a = L
										+ "methodName=GETGSON4BM4SELECTINIT&cache=600&mobile="
										+ b + "&datasource=sjk";
								$.easyAjax(a, "callphoneparams", function(e) {
									e = e[c];
									u({
										data : e.mianzhi,
										elemA : Z,
										gameid : "id",
										gamename : "name"
									});
									var g = e.areaName, h = Z.find("span.text")
											.text();
									h = va(ia = e.mianzhi, h);
									if (g) {
										O.find("span").text(
												g
														+ " "
														+ e.gamename.substring(
																0, 2)).end()
												.show();
										$("#rechargeTab1 .gg").eq(1).hide();
										$(".query_order").hide()
									} else if (O.is(":visible")) {
										O.hide();
										$("#rechargeTab1 .gg").eq(1).show();
										$(".query_order").show()
									}
									sa = {
										number : b,
										gameid : e.gameid,
										areaid : e.gameareaid,
										priceid : h,
										datasource : "sjk"
									};
									aa(sa);
									D(sa)
								})
							}
						} else if (a > 11)
							this.value = b.substring(0, 11);
						else if (a === 0) {
							B("Phone", true);
							if (O.is(":visible")) {
								O.hide();
								$("#rechargeTab1 .gg").eq(1).show();
								$(".query_order").show()
							}
						}
					})
			.focus(
					function() {
						$.trim(this.value).length === 11 && !F.url
								&& $(this).trigger("keyup")
					})
			.blur(
					function() {
						ISPindex = W(N[0].value);
						if ($.trim(this.value).length !== 11 || ISPindex === -1) {
							B("Phone", true,
									"\u8bf7\u8f93\u5165\u6b63\u786e\u7684\u624b\u673a\u53f7\u7801\u3002");
							if (O.is(":visible")) {
								O.hide();
								$("#rechargeTab1 .gg").eq(1).show();
								$(".query_order").show()
							}
						}
					});
	k(V, "order/mobilestep1.aspx");
	k(ha, "order/mobileanonymstep1.aspx");
	var l = {
		$goldGame : $("#rcGoldSelGame"),
		$goldGameText : $("#rcGoldSelGame").find("span.text"),
		$goldGameUl : $("#rcGoldSelGame").find("ul"),
		$goldGameLi : $("#rcGoldSelGame").find("li"),
		$goldArea : $("#rcGoldSelArea"),
		$goldAreaText : $("#rcGoldSelArea").find("span.text"),
		$goldAreaUl : $("#rcGoldSelArea").find("ul"),
		$goldAreaLi : $("#rcGoldSelArea").find("li"),
		$goldServer : $("#rcGoldSelSer"),
		$goldServerText : $("#rcGoldSelSer").find("span.text"),
		$goldServerUl : $("#rcGoldSelSer").find("ul"),
		$goldServerLi : $("#rcGoldSelSer").find("li"),
		$goldPrice : $("#rcGoldPrice"),
		$goldBuy : $("#rcGoldBtn"),
		postType : "",
		postGameId : "",
		postAreaId : "",
		postServerId : "",
		postCampID : "",
		Race : [],
		$goldCamp : $("#rcGoldCamp"),
		$goldCampUl : $("#rcGoldCamp").find("ul"),
		$qhFlog : true,
		init : function() {
			this.getGame();
			this.subClick()
		},
		quickAjax : function(b) {
			b = $.extend({}, {
				type : "GET",
				url : "null",
				scriptCharset : "GB2312",
				jsonp : "jsoncallback",
				dataType : "jsonp",
				data : {},
				async : true,
				success : function() {
				},
				error : function(a, c, e) {
					if (navigator.userAgent.indexOf("MSIE") < 0) {
						console.log(a);
						console.log(c);
						console.log(e)
					}
				}
			}, b);
			$.ajax(b)
		},
		insertHtml : function(b) {
			this.quickAjax($.extend({}, {
				success : function(a) {
					var c = 0, e = "", g = b.obj.attr("id"), h = "";
					if (!(a == null || a.length < 1)) {
						if (g.indexOf("SelGame") > -1) {
							h = a[0].gamecontent;
							for (l.Race = a[0].race; c < h.length; c++)
								e += '<li rel="' + h[c].id + '" type="'
										+ h[c].type + '" mate-text="'
										+ h[c].title + '">' + h[c].name
										+ "</li>"
						} else
							for (; c < a.length; c++)
								e += '<li rel="' + a[c].id + '" mate-text="'
										+ a[c].title + '">' + a[c].name
										+ "</li>";
						b.obj.find("ul").html("").append(e)
					}
				}
			}, b))
		},
		quickSearch : function(b) {
			if (b.indexOf("Game") > -1) {
				$("#" + b).find("li").css("display", "block");
				$("#" + b).find("span.text").css("display", "none");
				var a = $("#" + b).find(".like_selected").find("input");
				a.length < 1 ? $("#" + b).find(".like_selected").prepend(
						'<input id="' + b + '_text" type="text" />') : a
						.val("").css("display", "block");
				$("#" + b)
						.delegate(
								"#" + b + "_text",
								"keyup",
								function(c) {
									var e = $("#" + b).find("li"), g = 0, h, j, q = 0;
									for (h = $.trim($(this).val())
											.toUpperCase(); g < e.length; g++) {
										j = $.trim(e.eq(g).attr("mate-text"));
										if (j.indexOf(h) > -1) {
											e.eq(g).css("display", "block");
											q++
										} else
											e.eq(g).css("display", "none")
									}
									c.stopPropagation()
								})
			}
		},
		getGame : function() {
			$("#rcGoldPrice b.f_f60").addClass("color");
			$("#rcGoldCamp span.text").html("\u8bf7\u9009\u62e9\u9635\u8425");
			$("#rcGoldBtn").removeAttr("href");
			$("#rcGoldPrice b.f_f60").html("");
			$("#rcGoldPrice label").html("\u8d2d\u4e70\u6bd4\u4f8b\uff1a");
			this.insertHtml({
				url : "http://fcd.5173.com/BMCenter/GetJsonData.aspx",
				data : {
					type : "yxb"
				},
				jsonCallBack : "jsoncall",
				cache : 2E3,
				obj : this.$goldGame
			});
			this.$goldGame
					.find(".like_selected")
					.click(
							function() {
								$("#rcGoldSelGame").find(".like_option").css(
										"zIndex", 1E3);
								$("#rcGoldPrice b.f_f60").html("");
								$("#rcGoldBtn").removeAttr("href");
								var a = $(this).next("ul.like_option").html();
								a == "" || a.indexOf("\u8bf7\u9009\u62e9") > -1 ? $(
										this)
										.next("ul.like_option")
										.html(
												"<li>\u6e38\u620f\u540d\u52a0\u8f7d\u4e2d...</li>")
										: $(this).next("ul.like_option").css(
												"display", "block");
								$("li#li-camp").css("display", "none");
								l.$goldArea.find("em").css("display", "block");
								l.$goldServer.find("em")
										.css("display", "block");
								l.$goldArea.find("ul").html("");
								l.$goldArea.find("span.text").html(
										"\u8bf7\u9009\u62e9\u6e38\u620f\u533a");
								l.$goldServer.find("ul").html("");
								l.$goldServer.find("span.text").html(
										"\u8bf7\u9009\u62e9\u6e38\u620f\u670d");
								$("li#li-camp").find("ul").html("");
								$("li#li-camp").find("span.text").html(
										"\u8bf7\u9009\u62e9\u9635\u8425");
								$("#rcGoldPrice").find("b.price").html("");
								l.$goldGame.undelegate("li", "click");
								l.$goldGame.delegate("li", "click", b);
								l.quickSearch("rcGoldSelGame");
								$(this).find("input").focus();
								$(this)
										.find("input")
										.blur(
												function() {
													$(this).css("display",
															"none");
													$(this)
															.next("span")
															.css("display",
																	"block")
															.html(
																	"\u8bf7\u9009\u62e9\u6e38\u620f");
													$(this).siblings("em").css(
															"display", "block")
												});
								$(this).find(".arrow_box").css("display",
										"none")
							});
			var b = function() {
				$("#rcGoldTip").css("visibility", "hidden");
				$("#rcGoldCamp span.text").css("color", "#333");
				l.$goldGame.find("input").css("display", "none");
				l.$goldGame.find("span.text").css("display", "inline");
				var a = $(this).attr("rel");
				l.postGameId = a;
				l.postType = $(this).attr("type");
				l.getArea(a);
				$(this).parent().siblings(".like_selected").find(".arrow_box")
						.css("display", "block");
				l.emNone($(this))
			}
		},
		getArea : function(b) {
			$("#rcGoldBtn").removeAttr("href");
			$("#rcGoldPrice b.f_f60").html("");
			$("#rcGoldPrice label").html("\u8d2d\u4e70\u6bd4\u4f8b\uff1a");
			this.insertHtml({
				url : "http://fcd.5173.com/commondata/Category.aspx",
				cache : "",
				data : {
					type : "area",
					id : b,
					jsonp : "callarea"
				},
				obj : this.$goldArea
			});
			this.$goldArea
					.find(".like_selected")
					.click(
							function() {
								$("#rcGoldPrice b.f_f60").html("");
								$("#rcGoldBtn").removeAttr("href");
								var c = $(this).next("ul.like_option").html();
								c == "" || c.indexOf("\u8bf7\u9009\u62e9") > -1 ? $(
										this)
										.next("ul.like_option")
										.html(
												"<li>\u6e38\u620f\u533a\u52a0\u8f7d\u4e2d...</li>")
										: $(this).next("ul.like_option").css(
												"display", "block");
								l.$goldServer.find("em")
										.css("display", "block");
								l.$goldServer.find("ul").html("");
								l.$goldServer.find("span.text").html(
										"\u8bf7\u9009\u62e9\u6e38\u620f\u670d");
								$("li#li-camp").find("ul").html("");
								$("li#li-camp").find("span.text").html(
										"\u8bf7\u9009\u62e9\u9635\u8425");
								$("#rcGoldPrice").find("b.price").html("");
								l.$goldArea.undelegate("li", "click");
								l.$goldArea.delegate("li", "click", a);
								l.quickSearch("rcGoldSelArea")
							});
			var a = function() {
				$("#rcGoldTip").css("visibility", "hidden");
				$("#rcGoldCamp span.text").css("color", "#333");
				l.$goldArea.find("input").css("display", "none");
				l.$goldArea.find("span.text").css("display", "inline");
				var c = $(this).attr("rel");
				l.postAreaId = c;
				l.getServer(c);
				l.emNone($(this))
			}
		},
		getServer : function(b) {
			$("#rcGoldBtn").removeAttr("href");
			$("#rcGoldPrice b.f_f60").html("");
			$("#rcGoldPrice label").html("\u8d2d\u4e70\u6bd4\u4f8b\uff1a");
			this.insertHtml({
				url : "http://fcd.5173.com/commondata/Category.aspx",
				cache : "",
				data : {
					type : "server",
					id : b,
					jsonp : "callserver"
				},
				obj : this.$goldServer
			});
			this.$goldServer
					.find(".like_selected")
					.click(
							function(c) {
								$("#rcGoldPrice b.f_f60").html("");
								$("#rcGoldBtn").removeAttr("href");
								var e = $(this).next("ul.like_option").html();
								e == "" || e.indexOf("\u8bf7\u9009\u62e9") > -1 ? $(
										this)
										.next("ul.like_option")
										.html(
												"<li>\u6e38\u620f\u670d\u52a0\u8f7d\u4e2d...</li>")
										: $(this).next("ul.like_option").css(
												"display", "block");
								$("li#li-camp").find("ul").html("");
								$("li#li-camp").find("span.text").html(
										"\u8bf7\u9009\u62e9\u9635\u8425");
								$("#rcGoldPrice").find("b.price").html("");
								l.$goldServer.undelegate("li", "click");
								l.$goldServer.delegate("li", "click", a);
								l.quickSearch("rcGoldSelSer");
								$(this).find("input").focus();
								c.stopPropagation()
							});
			var a = function() {
				$("#rcGoldTip").css("visibility", "hidden");
				$("#rcGoldCamp span.text").css("color", "#333");
				l.$goldServer.find("input").css("display", "none");
				l.$goldServer.find("span.text").css("display", "inline");
				var c = $(this).attr("rel");
				l.postServerId = c;
				var e = "", g = true;
				$.each(l.Race, function(h, j) {
					if (j.gid == l.postGameId) {
						g = false;
						e += '<li class="gold-flog-btn" rel = "' + j.id + '">'
								+ j.name + "</li>"
					}
				});
				if (g || l.postGameId == "1a0d128d66b24896bf7dcf7430083cf0") {
					l.postCampID = "";
					l.getPrice()
				} else {
					$("li#li-camp").css("display", "block");
					l.$goldCampUl.html("");
					l.$goldCampUl.append(e);
					$("li#li-camp").find("li").click(function() {
						$("#rcGoldTip").css("visibility", "hidden");
						$("#rcGoldCamp span.text").css("color", "#333");
						$("#rcGoldBtn").removeAttr("href");
						$("#rcGoldBtn").removeClass("hover");
						$("#rcGoldPrice").find("b.price").html("");
						l.postCampID = $(this).attr("rel");
						l.getPrice()
					})
				}
				l.emNone($(this))
			}
		},
		getPrice : function() {
			l.$qhFlog = true;
			var b = function(a) {
				var c, e, g, h;
				if (l.postType == "1") {
					if (a == null || a.length < 1) {
						$("#rcGoldPrice b.f_f60").html("");
						$("#rcGoldPrice")
								.find("b.price")
								.html(
										"<span>\u8be5\u5546\u54c1\u5df2\u552e\u5b8c</span>");
						$("#rcGoldBtn").removeAttr("href");
						$("#rcGoldPrice b.f_f60").html("");
						return false
					}
					g = (1 / a[0].MasterPrice).toFixed(2);
					c = g + a[0].Unit + "/\u5143";
					e = "http://mall.5173.com/EasyBuy/EasyOrder.aspx?productId="
							+ a[0].ProductId;
					g = a[0].ProductId;
					h = a[0].MasterPrice + "\u5143/" + a[0].Unit
				} else if (l.postType == "0" || l.postType == "") {
					if (a == null || a.length < 1 || a.goodsList == null
							|| a.goodsList.length < 1) {
						$("#rcGoldPrice b.f_f60").html("\u6362\u7b97");
						$("#rcGoldPrice")
								.find("b.price")
								.html(
										"<span>\u8be5\u5546\u54c1\u5df2\u552e\u5b8c</span>");
						$("#rcGoldBtn").removeAttr("href");
						$("#rcGoldPrice b.f_f60").html("");
						return false
					}
					g = (1 / a.goodsList[0].unitPrice).toFixed(2);
					c = g + a.goodsList[0].moneyName + "/\u5143";
					g = a.goodsList[0].id;
					e = typeof a.goodsList[0].gameRace == "undefined" ? ""
							: a.goodsList[0].gameRace;
					e = "http://yxbmall.5173.com/createorder.html?gameId="
							+ a.goodsList[0].gameId
							+ "&regionId="
							+ a.goodsList[0].regionId
							+ "&serverId="
							+ a.goodsList[0].serverId
							+ "&raceId="
							+ (typeof a.goodsList[0].raceId == "undefined" ? ""
									: a.goodsList[0].raceId) + "&gameName="
							+ escape(a.goodsList[0].gameName) + "&gameRegion="
							+ escape(a.goodsList[0].region) + "&gameServer="
							+ escape(a.goodsList[0].server) + "&gameRace="
							+ escape(e) + "&goodsCat="
							+ a.goodsList[0].goodsCat
							+ "&goldCount=0&refererType=1";
					h = a.goodsList[0].unitPrice + "\u5143/"
							+ a.goodsList[0].moneyName
				}
				$("#rcGoldPrice b.f_f60").html("\u6362\u7b97");
				$("#rcGoldPrice ").undelegate("b.f_f60", "click");
				$("#rcGoldPrice ").delegate(
						"b.f_f60",
						"click",
						function() {
							if (l.$qhFlog) {
								$(this).parent("span").siblings("label").html(
										"\u5355\u4ef7\uff1a");
								$("#rcGoldPrice b.price").html(h);
								l.$qhFlog = false
							} else {
								$(this).parent("span").siblings("label").html(
										"\u8d2d\u4e70\u6bd4\u4f8b\uff1a");
								$("#rcGoldPrice b.price").html(c);
								l.$qhFlog = true
							}
						});
				$("#rcGoldPrice").find("b.price").html(c);
				$("#rcGoldPrice").find('input[type="hidden"]').val(g);
				$("#rcGoldBtn").attr("href", e)
			};
			if (l.postType == "1")
				this.insertHtml({
					url : "http://sales.5173.com/FastBuy/PlayMoney.aspx",
					data : {
						jsonp : "price",
						GameID : l.postGameId,
						AreaId : l.postAreaId,
						ServerId : l.postServerId,
						CampID : l.postCampID
					},
					success : b
				});
			else if (l.postType == "0" || l.postType == "")
				this
						.insertHtml({
							url : "http://yxbmall.5173.com/gamegold-facade-frontend/services/goods/queryunitprice",
							data : {
								gameID : l.postGameId,
								regionID : l.postAreaId,
								serverID : l.postServerId,
								raceID : l.postCampID
							},
							jsonp : "callback",
							scriptCharset : "UTF-8",
							success : b
						})
		},
		subClick : function() {
			$("#rcGoldBtn")
					.click(
							function() {
								if (typeof $(this).attr("href") == "undefined"
										|| $(this).attr("href") == "")
									if ($("#rcGoldSelGame span.text").html() == "\u8bf7\u9009\u62e9\u6e38\u620f") {
										$("#rcGoldTip").find("span").remove();
										$("#rcGoldTip")
												.append(
														"<span>\u8bf7\u9009\u62e9\u6e38\u620f</span>")
												.css("visibility", "visible")
									} else if ($("#rcGoldSelArea span.text")
											.html() == "\u8bf7\u9009\u62e9\u6e38\u620f\u533a") {
										$("#rcGoldTip").find("span").remove();
										$("#rcGoldTip")
												.append(
														"<span>\u8bf7\u9009\u62e9\u6e38\u620f\u533a</span>")
												.css("visibility", "visible")
									} else if ($("#rcGoldSelSer span.text")
											.html() == "\u8bf7\u9009\u62e9\u6e38\u620f\u670d") {
										$("#rcGoldTip").find("span").remove();
										$("#rcGoldTip")
												.append(
														"<span>\u8bf7\u9009\u62e9\u6e38\u620f\u670d</span>")
												.css("visibility", "visible")
									} else
										$("#li-camp").css("display") == "block"
												&& $("#rcGoldCamp span.text")
														.html() == "\u8bf7\u9009\u62e9\u9635\u8425"
												&& $("#rcGoldCamp span.text")
														.css("color", "#f00")
							});
			$("#rechargeTab0 ul.recharge_list li").eq(0).css("zIndex", 10);
			$("#rechargeTab0 ul.recharge_list li").eq(1).css("zIndex", 9);
			$("#rechargeTab0 ul.recharge_list li").eq(2).css("zIndex", 8);
			$("#rechargeTab0 ul.recharge_list li").eq(3).css("zIndex", 7);
			$("#rechargeTab0 ul.recharge_list li").eq(4).css("zIndex", 6);
			$("#rechargeTab0 ul.recharge_list li").eq(5).css("zIndex", 5)
		},
		emNone : function(b) {
			b.html().length > 8 ? b.parent().siblings(".like_selected").find(
					"em").css("display", "none") : b.parent().siblings(
					".like_selected").find("em").css("display", "block")
		}
	};
	l.init();
	var oa = $("#rcQQNumber"), P = $("#rcQQPriceSel"), S = $("#rcQQPrice"), ua = $("#rcQQBtn"), d = $("#rcQQtype"), f = $("#rcQQBtnNoneLogin"), i = /^[1-9]\d{4,}$/, p = {}, m = {};
	m.extras = {};
	$.inputPlaceholder(oa[0], "#333");
	var w = function(b) {
		b = L + "methodName=GETJSON4BM4GETPRICE&cache=600&gameid=" + b.gameid
				+ "&gameareaid=%20&mianzhiid=" + b.priceid
				+ "&datasource=qq45173";
		ua.addClass("disabled");
		f.addClass("disabled");
		$
				.easyAjax(
						b,
						"callqqprice",
						function(a) {
							a = a[0];
							var c = a.ConsignmentPrice, e = c.indexOf("$"), g;
							g = a.BizOfferAD;
							a = a.ActivityBizOfferUrl;
							var h = $("#discountQQ");
							if (a) {
								h.html(g);
								h.attr("href", a);
								h.show()
							} else {
								h.html(g);
								h.hide()
							}
							if (~e) {
								g = c.substring(0, e);
								c = c.substring(e + 1);
								m.extras.offerid = g
							}
							m.url = "order/step1v2.aspx";
							m.extras.save = 1;
							if (c !== "-1") {
								B("QQ", false);
								S.find("b.price").text(c).end().show()
							} else {
								B(
										"QQ",
										true,
										"\u6b64\u9762\u503c\u6682\u65e0\u5546\u54c1\uff0c\u8bf7\u9009\u62e9\u5176\u4ed6\u3002");
								S.is(":visible") && S.hide()
							}
							m.extras.u_ref = "5173_index_sidebar_bianmin_qq";
							ua.removeClass("disabled");
							f.removeClass("disabled")
						})
	}, u = function(b) {
		var a = b.data;
		if (a) {
			var c = "", e = "", g = b.elemA, h = b.elemB;
			$.each(a, function(j, q) {
				var v = q.selected === 1;
				if (g)
					c += '<li data-value="' + q[b.gameid] + '"'
							+ (v ? 'class="current"' : "") + ">"
							+ q[b.gamename] + "</li>";
				h
						&& v
						&& $.each(q.mianzhi, function(T, M) {
							e += '<li data-value="'
									+ M[b.id]
									+ '"'
									+ (M.selected === 1 ? 'class="current"'
											: "") + ">" + M[b.name] + "</li>"
						})
			});
			c && g.find("ul.like_option").html(c).end().likeSelected();
			e && h.find("ul.like_option").html(e).end().likeSelected()
		}
		b = null
	};
	k = function(b, a) {
		b
				.click(function(c) {
					if (!$(this).hasClass("disabled")) {
						var e = $.trim(oa[0].value);
						if (e.length == 11)
							B(
									"QQ",
									true,
									"QQ\u53f7\u7801\u8f93\u5165\u6709\u8bef\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165\u3002");
						else if (i.test(e)) {
							if (S.is(":visible")) {
								if (!m.extras.qq)
									m.extras.qq = e;
								if (m.url) {
									if (~m.url.indexOf("order")) {
										m.url = a;
										if (~a.indexOf("anonym"))
											m.extras.fast = 1;
										else
											delete m.extras.fast
									}
									t(p.gameid);
									if ($(b).attr("id") == "rcQQBtnNoneLogin")
										if (m.extras.qq)
											da
													.init({
														type : "qq",
														tips : '<p>\u60a8\u5373\u5c06\u4e3aQQ\u53f7\u7801<span class="num orange fb">&nbsp;'
																+ m.extras.qq
																+ "&nbsp;</span>\u8fdb\u884c\u5145\u503c\uff0c\u8bf7\u6838\u5bf9\u5145\u503c\u53f7\u7801\u662f\u5426\u6b63\u786e\uff01</p>",
														submit : function() {
															x(e);
															$
																	.analogSubmit(
																			R,
																			m)
														}
													});
										else {
											x(e);
											$.analogSubmit(R, m)
										}
									else
										$.analogSubmit(R, m);
									if (r)
										__utmTrackEvent(
												encodeURIComponent("\u65b0\u7248\u9996\u9875"),
												encodeURIComponent("\u4fbf\u6c11\u4e2d\u5fc3"),
												~a.indexOf("anonymstep") ? encodeURIComponent("\u514d\u767b\u5f55\u8d2d\u4e70-QQ")
														: encodeURIComponent("\u767b\u5f55\u8d2d\u4e70-QQ"))
								}
							}
						} else
							B("QQ", true,
									"\u8bf7\u8f93\u5165\u6b63\u786e\u7684QQ\u53f7\u7801\u3002")
					}
					c.preventDefault()
				});
		return false
	};
	$("#rechargeTab2")
			.one(
					"loadData",
					function() {
						$
								.easyAjax(
										L
												+ "methodName=GETGSON4BM4SELECTINIT&cache=600&datasource=qq",
										"callqqload",
										function(b) {
											ta(
													"methodName=GetUserChargedAccounts&gameid=e8d058a210e64b8aa20789852bfca469&&length=10",
													$("#rcQQNumberList"));
											u({
												data : b,
												elemA : d,
												gameid : "gameid",
												gamename : "gamename",
												id : "id",
												name : "name"
											});
											u({
												data : b,
												elemB : P,
												gameid : "gameid",
												gamename : "gamename",
												id : "id",
												name : "name"
											});
											p = {
												gameid : b[0].gameid,
												priceid : P.likeValue()
											};
											w(p)
										})
					});
	d.bind("likeChange", function() {
		var b = $(this).likeValue();
		$.easyAjax(L
				+ "methodName=GETGSON4BM4SELECTINIT&cache=600&datasource=qq",
				"callqqload", function(a) {
					var c = "";
					$.each(a, function(e, g) {
						if (g.gameid === b) {
							for (var h = 0; h < g.mianzhi.length; h++)
								c += '<li data-value="' + g.mianzhi[h].id
										+ '">' + g.mianzhi[h].name + "</li>";
							c
									&& P.find("ul.like_option").html(c).end()
											.find("li").eq(0).addClass(
													"current").end()
											.likeSelected();
							P.find(".text").text(P.find(".current").text());
							p = {
								gameid : g.gameid,
								priceid : P.likeValue()
							};
							w(p);
							return false
						}
					})
				})
	});
	oa
			.keyup(
					function() {
						var b = this.value;
						if (X.test(b))
							this.value = b.replace(X, "");
						if (b.length >= 11) {
							this.value = b.substring(0, 11);
							B(
									"QQ",
									true,
									"QQ\u53f7\u7801\u8f93\u5165\u6709\u8bef\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165\u3002")
						} else
							B(
									"QQ",
									false,
									"QQ\u53f7\u7801\u8f93\u5165\u6709\u8bef\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165\u3002")
					})
			.blur(
					function() {
						var b = $.trim(this.value);
						if (i.test(b)) {
							S.is(":hidden") ? B(
									"QQ",
									true,
									"\u6b64\u9762\u503c\u6682\u65e0\u5546\u54c1\uff0c\u8bf7\u9009\u62e9\u5176\u4ed6\u3002")
									: B("QQ", false);
							m.extras.qq = b
						} else
							B("QQ", true,
									"\u8bf7\u8f93\u5165\u6b63\u786e\u7684QQ\u53f7\u7801\u3002")
					});
	P.bind("likeChange", function() {
		p = {
			gameid : d.find(".current").attr("data-value"),
			priceid : $(this).likeValue()
		};
		w(p)
	});
	k(ua, "order/step1v2.aspx");
	k(f, "order/anonymstep1.aspx");
	var G;
	k = $("#rcCardGameName");
	var E = $("#rcCardPriceSel"), H = $("#rcCardPriceKF"), U = $("#rcCardPriceZD"), Da = $("#rcCardBtn"), ea = $("#rcCardBtnNoneLogin"), pa = {}, ya = {}, za = {};
	ya.extras = {};
	za.extras = {};
	var Ga = function(b, a, c, e) {
		var g = b.indexOf("$");
		if (~g) {
			c = b.substring(0, g);
			b = b.substring(g + 1);
			e.url = "order/step1v2.aspx";
			e.extras.offerid = c;
			a = a;
			if (e.extras.gameid) {
				delete e.extras.gameid;
				delete e.extras.mianzhi
			}
		} else {
			e.url = "bizoffer/cccard/viewlist.aspx";
			a = c;
			e.extras.offerid && delete e.extras.offerid
		}
		$.each(a, function(h, j) {
			e.extras[h] = j
		});
		e.extras.u_ref = "u_ref=5173_index_sidebar_bianmin_dk";
		return [ e, b ]
	}, Ia = function(b, a, c, e, g, h) {
		return $
				.easyAjax(
						b,
						"callcardprice",
						function(j) {
							j = j[0];
							var q = j.ConsignmentPrice, v = j.CryptoPrice;
							j = c.parent();
							var T = e.parent(), M = {}, fa = {};
							fa = {
								gameid : a.gameid,
								mianzhi : a.priceid,
								tradetype : 3
							};
							q = Ga(q, M, fa, g);
							g = q[0];
							q = q[1];
							j.find("b.price").text(q);
							if (q !== "-1") {
								j.show();
								window.isSignIn || ea.show();
								c[0].checked = true;
								e[0].checked = false
							} else {
								j.is(":visible") && j.hide();
								window.isSignIn || ea.hide();
								c[0].checked = false;
								e[0].checked = true
							}
							if (v !== "-1") {
								fa.tradetype = 1;
								q = Ga(v, M, fa, h);
								h = q[0];
								v = q[1];
								T.find("b.price").text(v).end().show()
							} else
								T.is(":visible") && T.hide();
							j.is(":hidden") && T.is(":hidden") ? B(
									"Card",
									true,
									"\u6b64\u9762\u503c\u6682\u65e0\u5546\u54c1\uff0c\u8bf7\u9009\u62e9\u5176\u4ed6\u3002")
									: B("Card", false);
							ya = g;
							za = h;
							Da.removeClass("disabled");
							ea.removeClass("disabled")
						})
	}, Ea = function(b) {
		var a = L + "methodName=GETJSON4BM4GETPRICE&cache=600&gameid="
				+ b.gameid + "&gameareaid=%20&mianzhiid=" + b.priceid
				+ "&datasource=bmdk";
		Da.addClass("disabled");
		ea.addClass("disabled");
		Ia(a, b, H, U, ya, za, false)
	};
	$("#rechargeTab3")
			.one(
					"loadData",
					function() {
						$
								.easyAjax(
										L
												+ "methodName=GETGSON4BM4SELECTINIT&cache=600&datasource=dk",
										"callcardload", function(b) {
											Ja();
											G = b
										})
					});
	var Ja = function() {
		function b(g, h) {
			$.each(g, function(q) {
				$.each(g[q], function(v, T) {
					if (h === T.id) {
						var M = "";
						$.each(T.mianzhi, function(fa, ga) {
							M += '<li data-value="'
									+ ga.id
									+ '"'
									+ (ga.selected == 1 ? 'class="current"'
											: "") + ">" + ga.name + "</li>"
						});
						$("#rcCardPriceSel ul").html(M)
					}
				})
			});
			var j = $("#rcCardPriceSel ul li.current").html();
			$("#rcCardPriceSel .like_selected .text").html(j);
			a();
			E.find(".like_option li").click(function() {
				a()
			})
		}
		function a() {
			var g = $("#dkGameLayer .game_div li.current").attr("data-value"), h = $(
					"#rcCardPriceSel li.current").attr("data-value");
			pa = {
				gameid : g,
				priceid : h
			};
			Ea(pa)
		}
		var c = $("#dkGameLayer"), e = $(".dk_game li");
		$('<a href="#" title="\u5173\u95ed" class="closebtn"></a>').click(
				function() {
					c.hide();
					return false
				}).appendTo(c);
		$
				.ajax({
					type : "get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					jsonpCallback : "bmzxdk",
					scriptCharset : "gb2312",
					url : "http://dkjy.5173.com/ajax.axd?methodName=GETBMCENTERGAMELIST",
					cache : false,
					success : function(g) {
						function h(j) {
							$(".game_div div").html("");
							var q = "", v, T = g;
							if (j)
								for (var M = 0; M < j.length; M++) {
									v = j[M];
									if (g[v].length > 0) {
										if (v != "BMHotGame")
											q += "<i>" + v + "</i>";
										q += "<ul>";
										$
												.each(
														g[v],
														function(ga, qa) {
															q += '<li data-value = "'
																	+ qa.id
																	+ '"'
																	+ (qa.selected == 1 ? 'class="current"'
																			: "")
																	+ ' ><a title="'
																	+ qa.name
																	+ '" rel="'
																	+ qa.id
																	+ '">'
																	+ qa.name
																	+ "</a></li>"
														});
										q += "</ul>"
									}
								}
							$(".game_div div").html(q);
							var fa = $(".game_div li");
							fa
									.bind(
											"click",
											function() {
												var ga = $(this);
												setTimeout(
														function() {
															var qa = ga.text(), Ka = ga
																	.attr("data-value"), La = $("#rcCardGameName .text");
															fa
																	.removeClass("current");
															ga
																	.addClass("current");
															La.html(qa);
															c.hide();
															b(T, Ka)
														}, 500);
												return false
											})
						}
						e.eq(0).addClass("dk_on");
						e.eq(0).data("letter", [ "BMHotGame" ]);
						e.eq(0).find("a").html("\u70ed\u95e8\u6e38\u620f");
						e.eq(1).data("letter", [ "A", "B", "C", "D", "E" ]);
						e.eq(1).find("a").html("ABCDE");
						e.eq(2)
								.data(
										"letter",
										[ "F", "G", "H", "I", "J", "K", "L",
												"M", "N" ]);
						e.eq(2).find("a").html("FGHIJKLMN");
						e.eq(3)
								.data("letter",
										[ "O", "P", "Q", "R", "S", "T" ]);
						e.eq(3).find("a").html("OPQRST");
						e.eq(4)
								.data("letter",
										[ "U", "V", "W", "X", "Y", "Z" ]);
						e.eq(4).find("a").html("UVWXYZ");
						$(".dk_game li").click(function() {
							var j = $(this);
							$(".dk_game li").removeClass("dk_on");
							j.addClass("dk_on");
							j = j.data("letter");
							h(j);
							return false
						});
						$("#rcCardGameName .like_selected").click(function() {
							c.show();
							return false
						});
						h([ "BMHotGame" ]);
						$(".game_div li.current").click()
					}
				})
	};
	k.bind("likeChange", function() {
		var b = $(this).likeValue();
		$.each(G, function(a, c) {
			if (c.gameid === b) {
				u({
					data : c.mianzhi,
					elemA : E,
					gameid : "id",
					gamename : "name"
				});
				return false
			}
		});
		window.isSignIn || ea[H.is(":visible") ? "show" : "hide"]();
		pa = {
			gameid : b,
			priceid : E.likeValue()
		};
		Ea(pa)
	});
	E.bind("likeChange", function() {
		pa.priceid = $(this).likeValue();
		Ea(pa)
	});
	U.bind("change", function() {
		window.isSignIn || ea[this.checked ? "hide" : "show"]()
	});
	H.bind("change", function() {
		window.isSignIn || ea[this.checked ? "show" : "hide"]()
	});
	k = function(b, a) {
		b
				.click(function(c) {
					var e;
					if (!$(this).hasClass("disabled"))
						if (H.is(":visible") || U.is(":visible")) {
							e = U.is(":checked") ? za : ya;
							if (e.url) {
								if (~e.url.indexOf("order"))
									e.url = a;
								t(pa.gameid);
								if (r)
									__utmTrackEvent(
											encodeURIComponent("\u65b0\u7248\u9996\u9875"),
											encodeURIComponent("\u4fbf\u6c11\u4e2d\u5fc3"),
											~a.indexOf("anonymstep") ? encodeURIComponent("\u514d\u767b\u5f55\u8d2d\u4e70-DK")
													: encodeURIComponent("\u767b\u5f55\u8d2d\u4e70-DK"));
								$.analogSubmit(R, e)
							}
						}
					c.preventDefault()
				})
	};
	k(Da, "order/step1v2.aspx");
	k(ea, "order/anonymstep1.aspx");
	var Ca = {
		init : function() {
			function b() {
				var a = this;
				a.timer = null;
				a.timer = setInterval(function() {
					a.ballNum = a.randomBallNum();
					a.renderBallNum()
				}, 200)
			}
			this.bindEvt();
			this.picClass = "i-ssq";
			this.ballNum = this.randomBallNum();
			this.timer = null;
			b.call(this);
			this.ajaxFn()
		},
		stopAutoNum : function() {
			clearInterval(this.timer)
		},
		ajaxFn : function() {
			var b = this;
			b.ajax = $
					.ajax({
						url : "http://caipiao.5173.com/activity/cngsadvertising!getData.jhtml",
						dataType : "jsonp",
						jsonp : "jsonpcallback",
						scriptCharset : "UTF-8",
						cache : true,
						error : function() {
							clearInterval(b.timer)
						},
						success : function(a) {
							function c(j) {
								if (!g) {
									$("#lotterPicRt").attr("class", j.picClass);
									$("#lotteryRt_goBtn").attr("href",
											j.selectNumUrl);
									j.poolPrize ? $("#lotterPoolPrizeRt").html(
											"\u5956\u6c60\uff1a" + j.poolPrize)
											.attr("class", "f_r") : $(
											"#lotterPoolPrizeRt").html(
											"\u6bcf\u592920:30\u5f00\u5956")
											.attr("class", "f_l")
								}
								$("#lotteryRt_submitBtn").attr("href",
										j.submitUrl);
								switch (j.picClass) {
								case "i-ssq":
									$("#allBall").html(e(j.ballNum)).find(
											":last").addClass("blueBall");
									break;
								case "i-dlt":
									$("#allBall").html(e(j.ballNum)).find(
											":last").addClass("blueBall")
											.prev().addClass("blueBall");
									break;
								case "i-fc3d":
									$("#allBall").html(e(j.ballNum));
									break
								}
							}
							function e(j) {
								var q = "";
								j = j;
								j = j.replace("|", ",").replace("|", ",")
										.split(",");
								for (var v = 0; v < j.length; v++)
									q += "<li>" + j[v] + "</li>";
								return q
							}
							clearInterval(b.timer);
							var g = false;
							$("#lotterSelectRt option[value='ssq']").attr(
									"selected", true);
							h = {
								picClass : "i-ssq",
								ballNum : a.ssqBall,
								selectNumUrl : a.ssqSelectNumUrl,
								submitUrl : a.ssqMachineSelectNumUrl,
								poolPrize : a.ssqPoolPrize
							};
							c(h);
							$("#lotterAdRt01").attr("href", a.footAdWordUrlOne)
									.text(a.footAdWordOne);
							$("#lotterAdRt02").attr("href", a.footAdWordUrlTwo)
									.text(a.footAdWordTwo);
							var h = {
								picClass : "i-ssq",
								ballNum : a.ssqBall,
								selectNumUrl : a.ssqSelectNumUrl,
								submitUrl : a.ssqMachineSelectNumUrl,
								poolPrize : a.ssqPoolPrize
							};
							$("#lotterSelectRt")
									.unbind("change")
									.bind(
											"change",
											function() {
												g = false;
												switch ($(this).val()) {
												case "ssq":
													h = {
														picClass : "i-ssq",
														ballNum : a.ssqBall,
														selectNumUrl : a.ssqSelectNumUrl,
														submitUrl : a.ssqMachineSelectNumUrl,
														poolPrize : a.ssqPoolPrize
													};
													break;
												case "dlt":
													h = {
														picClass : "i-dlt",
														ballNum : a.dltBall,
														selectNumUrl : a.dltSelectNumUrl,
														submitUrl : a.dltMachineSelectNumUrl,
														poolPrize : a.dltPoolPrize
													};
													break;
												case "fc3d":
													h = {
														picClass : "i-fc3d",
														ballNum : a.fc3dBall,
														selectNumUrl : a.fc3dSelectNumUrl,
														submitUrl : a.fc3dMachineSelectNumUrl,
														poolPrize : null
													};
													break
												}
												c(h)
											});
							$("#lotteryRt_changeBtn")
									.unbind("click")
									.bind(
											"click",
											function() {
												var j = "", q = {};
												g = true;
												switch ($("#lotterPicRt").attr(
														"class")) {
												case "i-ssq":
													j = "ssq";
													break;
												case "i-dlt":
													j = "dlt";
													break;
												case "i-fc3d":
													j = "fc3d";
													break
												}
												$
														.ajax({
															url : "http://caipiao.5173.com/activity/cngsadvertising!randomOneNote.jhtml?game="
																	+ j,
															dataType : "jsonp",
															jsonp : "jsonpcallback",
															scriptCharset : "UTF-8",
															cache : true,
															success : function(
																	v) {
																switch (j) {
																case "ssq":
																	q = {
																		picClass : "i-ssq",
																		ballNum : v.ssqBall,
																		submitUrl : v.ssqMachineSelectNumUrl
																	};
																	c(q);
																	break;
																case "dlt":
																	q = {
																		picClass : "i-dlt",
																		ballNum : v.dltBall,
																		submitUrl : v.dltMachineSelectNumUrl
																	};
																	c(q);
																	break;
																case "fc3d":
																	q = {
																		picClass : "i-fc3d",
																		ballNum : v.fc3dBall,
																		submitUrl : v.fc3dMachineSelectNumUrl
																	};
																	c(q);
																	break
																}
															}
														})
											})
						}
					})
		},
		bindEvt : function() {
			var b = this;
			$("#lotterSelectRt").bind("change", function() {
				switch ($(this).val()) {
				case "ssq":
					b.picClass = "i-ssq";
					break;
				case "dlt":
					b.picClass = "i-dlt";
					break;
				case "fc3d":
					b.picClass = "i-fc3d";
					break
				}
				$("#lotterPicRt").attr("class", b.picClass)
			})
		},
		renderBallNum : function() {
			function b(a) {
				for (var c = "", e = 0; e < a.length; e++)
					c += a[e] < 10 ? "<li>0" + a[e] + "</li>" : "<li>" + a[e]
							+ "</li>";
				return c
			}
			switch (this.picClass) {
			case "i-ssq":
				$("#allBall").html(b(this.ballNum)).find(":last").addClass(
						"blueBall");
				break;
			case "i-dlt":
				$("#allBall").html(b(this.ballNum)).find(":last").addClass(
						"blueBall").prev().addClass("blueBall");
				break;
			case "i-fc3d":
				$("#allBall").html(b(this.ballNum));
				break
			}
		},
		randomBallNum : function() {
			function b(e, g) {
				switch (arguments.length) {
				case 1:
					return parseInt(Math.random() * e + 1);
				case 2:
					return parseInt(Math.random() * (g - e + 1) + e);
				default:
					return 0
				}
			}
			var a = [];
			switch (this.picClass) {
			case "i-ssq":
				for (; a.length < 6;) {
					var c = b(1, 31);
					$.inArray(c, a) == -1 && a.push(c)
				}
				for (; a.length < 7;) {
					c = b(1, 16);
					$.inArray(c, a) == -1 && a.push(c)
				}
				return a;
			case "i-dlt":
				for (; a.length < 5;) {
					c = b(1, 35);
					$.inArray(c, a) == -1 && a.push(c)
				}
				for (; a.length < 7;) {
					c = b(1, 12);
					$.inArray(c, a) == -1 && a.push(c)
				}
				return a;
			case "i-fc3d":
				for (; a.length < 3;) {
					c = b(0, 9);
					$.inArray(c, a) == -1 && a.push(c)
				}
				return a
			}
		}
	}, Q = $("#rcGold2CardGameName"), ra = $("#rcGold2CardGameArea"), Aa = $("#rcGold2CardGameServer"), ja = $("#rcGold2CardGameCamp"), wa = $("#rcGold2CardGamePriceSel"), xa = $("#rcGold2CardGamePrice"), Fa = $("#rcGold2CardSubmit"), ba = "http://huangou.5173.com/AjaxJsonp.aspx?", Ha = {
		rcGold2CardGameName : "\u8bf7\u9009\u62e9\u6e38\u620f",
		rcGold2CardGameArea : "\u8bf7\u9009\u62e9\u6e38\u620f\u533a",
		rcGold2CardGameServer : "\u8bf7\u9009\u62e9\u6e38\u620f\u670d",
		rcGold2CardGameCamp : "\u8bf7\u9009\u62e9\u6e38\u620f\u9635\u8425",
		rcGold2CardGamePriceSel : "\u8bf7\u9009\u62e9\u9762\u503c"
	}, Ba = function(b) {
		if (b)
			var a = b.ajaxUrl, c = b.elemObj;
		$.easyAjax(a, "Gold2Cardload", function(e) {
			var g = c;
			if (e) {
				var h = e.length;
				u({
					data : e,
					elemA : g,
					gameid : "Id",
					gamename : "Value"
				});
				e = g.find(".like_option li").eq(0).html();
				var j = g.find(".like_option li").eq(0).attr("data-value");
				g.find(".text").html(e);
				g.find(".text").attr("data-value", j);
				if (h > 7) {
					g.find(".like_option").height(180);
					g.find(".like_option").css("overflow", "hidden");
					g.find(".like_option").css("overflowY", "scroll")
				}
			} else {
				g.find(".like_option").html("\u7a7a");
				g.find(".text").attr("data-value", "")
			}
		})
	};
	$("#rechargeTab6").one("loadData", function() {
		var b = ba + "methodName=GetGameSelect";
		ra.find(".text").attr("data-value", "");
		ra.find(".text").attr("no-loading", 1);
		ja.find(".text").attr("data-value", "");
		ja.find(".text").attr("no-loading", 1);
		wa.find(".text").attr("data-value", "");
		wa.find(".text").attr("no-loading", 1);
		Ba({
			ajaxUrl : b,
			elemObj : Q
		});
		setTimeout(function() {
			Q.find(".like_option li").eq(0).click()
		}, 1E3);
		J(Q, b);
		J(ra, ba + "methodName=GetGameAreaSelect&gameId=");
		J(ja, ba + "methodName=GetGameCampSelect&gameId=");
		J(wa, ba + "methodName=GetSubBizCategorySelect&gameId=");
		J(Aa, ba + "methodName=GetGameServerSelect&gameId=");
		z()
	})
});
(function(D) {
	var J = !!~navigator.userAgent.toLowerCase().indexOf("msie 6");
	D.fixed = function(z, C) {
		var o = document.body, n = document.documentElement, r = z.style, t, s, A, y, k, x, K;
		if (C) {
			s = C.top;
			A = C.bottom;
			y = C.right;
			k = C.left
		} else
			k = s = "0px";
		if (k !== undefined) {
			x = "left";
			y = k
		} else {
			x = "right";
			y = y
		}
		if (s !== undefined) {
			k = "top";
			K = s
		} else {
			k = "bottom";
			K = A
		}
		r[x] = y;
		if (J) {
			if (o.currentStyle.backgroundAttachment !== "fixed") {
				o.style.backgroundImage = "url(about:blank)";
				o.style.backgroundAttachment = "fixed"
			}
			if (s !== undefined)
				C = parseInt(s);
			else if (A !== undefined) {
				t = z.offsetHeight;
				C = n.clientHeight - t - parseInt(A);
				window.onresize = function() {
					C = n.clientHeight - t - parseInt(A);
					r.setExpression("top",
							"fuckIE6=document.documentElement.scrollTop + " + C
									+ ' + "px"')
				}
			}
			r.position = "absolute";
			r.setExpression("top",
					"fuckIE6=document.documentElement.scrollTop + " + C
							+ ' + "px"')
		} else {
			r.position = "fixed";
			r[k] = K
		}
	};
	D.feedback = function(z, C, o) {
		if (window.screen.width > 1027) {
			var n = false, r, t, s;
			s = D('<div class="float_feedback" id="float_feedback" style="position:absolute"><a class="feedback_1" href="http://sc.5173.com/index.php?question/ask_skip.html" target="_blank"></a><a class="feedback_2" href="http://sc.5173.com/?question/complain.html" target="_blank"></a></div>');
			s.css(z).appendTo(document.body);
			window.location.host == "www.5173.com"
					&& D
							.easyAjax(
									"http://ams.5173.com/AMSV4/AmsBizClient?qt=ho&gi=&si=&gci=&tp=8&ct=10&gt=0",
									null,
									function(k) {
										if (k)
											if (k.ho) {
												var x = k.ho.length > 3 ? 3
														: k.ho.length;
												t = '<div class="history_browse" id="history_browse"><h5>\u6700\u8fd1\u6d4f\u89c8</h5><ul>';
												for (var K = 0; K < x; K++) {
													var I = k.ho[K], la = A(I.SumPrice / 1E4);
													t += '<li><span><a title="'
															+ I.GameName
															+ '" href="'
															+ I.TradingUrl
															+ '">'
															+ I.GameName
															+ '</a></span><a href="'
															+ I.BuyUrl
															+ '">'
															+ I.BizeOfferTypeName
															+ '</a><ins><a title="'
															+ unescape(I.BizOfferTitle)
															+ '" href="'
															+ I.BuyUrl
															+ '">\uffe5' + la
															+ "</a></ins></li>"
												}
												t += '</ul><div class="more"><a href="http://user.5173.com/Default.aspx">\u66f4\u591a</a></div></div>';
												x != 0
														&& D(t)
																.appendTo(
																		D("#float_feedback"))
																.find("li a")
																.click(
																		function() {
																			var ma = D(
																					this)
																					.attr(
																							"title");
																			__utmTrackEvent(
																					encodeURIComponent("\u65b0\u7248\u9996\u9875"),
																					encodeURIComponent("\u6700\u8fd1\u6d4f\u89c8"),
																					encodeURIComponent(ma))
																		})
											}
									});
			r = s.offset().top;
			var A = function(k) {
				var x = parseFloat(k);
				if (isNaN(x)) {
					alert("function:changeTwoDecimal->parameter error");
					return false
				}
				x = Math.round(k * 100) / 100;
				k = x.toString();
				x = k.indexOf(".");
				if (x < 0) {
					x = k.length;
					k += "."
				}
				for (; k.length <= x + 2;)
					k += "0";
				return k
			}, y = function() {
				J ? s[0].style.removeExpression("top") : s.css("position",
						"absolute");
				s.css(z)
			};
			D(window).bind("scroll", function() {
				var k = D(this).scrollTop(), x = {
					top : "0px"
				};
				if (z.left)
					x.left = z.left;
				else
					x.right = z.right;
				if (k > r && !n) {
					n = true;
					D.fixed(s[0], x)
				} else if (k < r && n) {
					n = false;
					y()
				}
			});
			s
					&& s.find("a").click(
							function() {
								C = C || "";
								o = o || "";
								__utmTrackEvent("%E6%8F%90%E5%BB%BA%E8%AE%AE",
										encodeURIComponent(C),
										encodeURIComponent(o))
							})
		}
	};
	var Y = D('<div class="float_feedback" id="floatFeedbackState" style="position:absolute"><a class="feedback_1" href="http://sc.5173.com/index.php?question/ask_skip.html" target="_blank"></a><a class="feedback_2" href="http://sc.5173.com/?question/complain.html" target="_blank"></a></div>');
	D(Y).appendTo(D("#gameSelectV2"))
})(jQuery);
$(function() {
	function D() {
		var d = location.search, f = {};
		if (d.indexOf("?") != -1) {
			strs = d.substr(1).split("&");
			for (d = 0; d < strs.length; d++)
				f[strs[d].split("=")[0]] = unescape(strs[d].split("=")[1])
		}
		return f
	}
	function J(d, f) {
		var i = new Date;
		i.setTime(i.getTime() + 2592E6);
		document.cookie = d + "=" + f + ";path=/;domain=.5173.com;expires="
				+ i.toGMTString()
	}
	function Y() {
		var d = $("#feedbackTab"), f = d.find(".feedback_list"), i = d
				.find(".bd");
		$
				.ajax({
					type : "get",
					dataType : "jsonp",
					jsonp : "jsoncallback",
					scriptCharset : "GBK",
					jsonpCallback : "suggest22",
					cache : false,
					url : "http://sc.5173.com/api/new_suggest.php?count=6&type=new",
					success : function(p) {
						f.empty();
						var m = "", w = p.button, u = "", G = [ "blue",
								"orange", "green" ];
						$
								.each(
										p.questionList,
										function(E, H) {
											if (E < 5)
												m += '<li><span class="ask"><a href="'
														+ H.url
														+ '" title="'
														+ H.title
														+ '">'
														+ H.title
														+ '</a></span><span class="answer"><a href="'
														+ H.url
														+ '" title="'
														+ H.answer
														+ '">'
														+ H.answer
														+ "</a></span></li>"
										});
						$
								.each(
										w,
										function(E, H) {
											u = "";
											u += '<a class="' + G[E]
													+ '" href="' + H.url
													+ '" target="_blank">'
													+ H.name + "</a>";
											$(u)
													.click(
															function() {
																var U = $(this)
																		.text();
																r
																		|| __utmTrackEvent(
																				encodeURIComponent("\u65b0\u7248\u9996\u9875V3"),
																				encodeURIComponent("\u4e8c\u5c4f\u54a8\u8be2\u5efa\u8bae\u6295\u8bc9"),
																				encodeURIComponent(U))
															}).appendTo(i)
										});
						f.append(m)
					}
				})
	}
	function z(d, f) {
		return (d = f.match(new RegExp("[?&]" + d + "=([^&]*)(&?)", "i"))) ? d[1]
				: d
	}
	function C() {
		if (typeof flashRightTop !== "undefined" && flashRightTop !== "") {
			$('<div class="popup_top" id="popupTop"></div>').appendTo("body");
			var d = $("#popupTop");
			d.find("a").eq(0).css("display", "block");
			$.fed.swfobject("#popupTop", {
				url : flashRightTop,
				width : 160,
				height : 160
			});
			var f = function() {
				if ($(window).width() < 1280) {
					d.css({
						position : "absolute",
						right : "50%",
						"margin-right" : "-720px",
						top : d.offset().top
					});
					$.browser.msie && $.browser.version === "6.0" ? $(window)
							.scroll(function() {
								d.css({
									top : $(this).scrollTop()
								})
							}) : $(window).scroll(function() {
						d.css({
							position : "fixed",
							top : "0px"
						})
					})
				} else {
					d.css("margin-right", "0");
					t($("#popupTop")[0], {
						top : "0px",
						right : "0px"
					})
				}
			};
			f();
			$(window).resize(function() {
				f()
			});
			d.find("span").click(function() {
				if (parseInt($(this).parent().css("width")) == 160) {
					$(this).parent().find("a").css("display", "none");
					$(this).parent().find("a").eq(1).css("display", "block");
					$(this).parent().css({
						width : "120px",
						height : "120px"
					})
				} else {
					$(this).parent().find("a").css("display", "none");
					$(this).parent().find("a").eq(0).css("display", "block");
					$(this).parent().css({
						width : "160px",
						height : "160px"
					})
				}
			})
		}
	}
	function o() {
		var d = $("#freeGoldCenter"), f = d.find("ul"), i = f.find("li"), p = $("<ul></ul>"), m = parseInt(f
				.css("height"));
		if (i.length > 6) {
			p.append(f.html()).css("top", m);
			d.append(p);
			var w = 0, u, G = function() {
				var E = $("#freeGoldCenter ul");
				w -= 1;
				E.eq(0).css("top", w);
				E.eq(1).css("top", m + w);
				if (w < -m) {
					E = E.eq(0).remove();
					d.append(E);
					w = 0
				}
			};
			u = setInterval(function() {
				G()
			}, 50);
			d.hover(function() {
				clearInterval(u)
			}, function() {
				u = setInterval(function() {
					G()
				}, 50)
			})
		}
	}
	$
			.ajax({
				type : "GET",
				url : "http://fcd.5173.com/ajax.axd?methodName=GETALLGAMESDATAv31&cache=20&IsGameID=false",
				dataType : "jsonp",
				jsonp : "jsoncallback",
				scriptCharset : "GBK",
				jsonpCallback : "callgame1",
				cache : true,
				success : function(d) {
					window.allGameData = allGameData = d
				}
			});
	var n = $.browser.msie && $.browser.version === "6.0", r = !!window.__utmTrackEvent
			&& document.location.host === "www.5173.com", t = $.fixed;
	n && document.execCommand("BackgroundImageCache", false, true);
	var s = function() {
		var d = $("#popupRight"), f = d.find("img"), i = d.find(".gg_286"), p = d
				.find(".gg_96"), m;
		if (f.length) {
			f.each(function() {
				this.src = $(this).attr("data-lazysrc");
				$(this).removeAttr("data-lazysrc")
			});
			var w = function() {
				clearTimeout(m);
				m = null;
				i.hide();
				d.css({
					width : "100px",
					height : "180px"
				});
				t(d[0], {
					bottom : "0px",
					right : "0px"
				});
				p.show().find("a.close_btn").click(function(u) {
					p.hide();
					d.css({
						width : "100px",
						height : "60px"
					});
					t(d[0], {
						bottom : "0px",
						right : "0px"
					});
					u.preventDefault()
				}).end().find("a.play_btn").click(function(u) {
					p.hide();
					d.css({
						width : "290px",
						height : "240px"
					});
					t(d[0], {
						bottom : "0px",
						right : "0px"
					});
					i.show();
					u.preventDefault()
				})
			};
			m = setTimeout(w, 6E3);
			i.show().find("a.close_btn").click(function(u) {
				w();
				u.preventDefault()
			})
		}
	};
	window.loginCallback = function(d) {
		var f = $("#fastTrack");
		if (d.name) {
			$
					.easyAjax(
							"http://fcd.5173.com/commondata/CaibeiTip.aspx",
							null,
							function(i) {
								if (i) {
									var p = '<div class="top_cb"><div class="top_cb_box"><div class="sale_tip">'
											+ i.header
											+ '</div><div class="login_status"><span class="login_span">\u60a8\u597d\uff0c'
											+ i.name
											+ '</span><a class="my_caibei" href="'
											+ i.url
											+ '">\u6211\u7684\u5f69\u8d1d\u79ef\u5206</a></div></div></div>';
									$(".header").eq(0).prepend(p);
									$("#loginList b").eq(0).html(i.name)
								}
							});
			$("#rcPhoneBtn,#rcQQBtn,#rcCardBtn").text(
					"\u786e\u5b9a\u8d2d\u4e70");
			$(
					"#rcPhoneBtnNoneLogin,#recharge .query_order,#rcQQBtnNoneLogin,#rcCardBtnNoneLogin")
					.hide();
			$("#popupRight").css({
				width : "100px",
				height : "60px"
			});
			t($("#popupRight")[0], {
				bottom : "0px",
				right : "0px"
			});
			if (d.diffip === "1") {
				$.LAYER.show({
					id : "signoutBox"
				});
				$("#signoutBox").find(".close_btn").click(function(i) {
					$.LAYER.close();
					i.preventDefault()
				})
			}
			window.initRechargeHistory()
		} else
			setTimeout(s, 5E3);
		f.find("div.loading").hide().remove()
	};
	if (window.isSignIn !== undefined && window.loginCallback) {
		window.loginCallback(window.loginData);
		try {
			window.loginData = window.loginCallback = null;
			delete window.loginCallback;
			delete window.loginData
		} catch (A) {
		}
	}
	var y = $("#topNews"), k = document.cookie, x = k.indexOf("fed_topNews="), K = k
			.indexOf(";", x), I = window.$_TipLastModified, la = $("s.game_select_arrow"), ma = function() {
		$("body")
				.prepend(
						'<div class="top" style="border: 0 none;"> <div class="top_box">\t <div id="topNews" class="top_news" style="display: block;"> \t\t<s class="icon_news f_l"></s> \t\t<p class="f_l"> \t\t<a href="http://safe.5173.com/SafeCenter/dynamic/kefuyanzhen.html" ref="nofollow">\u8b66\u60d5\uff1a5173\u70ed\u7ebf\u53ca\u4e13\u7ebf\u7535\u8bdd\u65e0\u5916\u62e8\u529f\u80fd\uff0c\u4efb\u4f55\u4ee5\u201c\u517c\u804c\u201d\uff0c\u201c\u5237\u4fe1\u8a89\u201d\uff0c\u201c\u505a\u4efb\u52a1\u201d\uff0c\u201c\u7f34\u7eb3\u4fdd\u8bc1\u91d1\u201d\u4e3a\u501f\u53e3\u7684\u5747\u4e3a\u8bc8\u9a97\u884c\u4e3a\uff0c\u8c28\u9632\u4e0a\u5f53\uff01</a>\t\t </p> \t\t<a class="close_btn f_r" id="topNews_btn" title="\u5173\u95ed\u63d0\u793a\uff0c\u4e0d\u518d\u63d0\u9192">\u5173\u95ed\u63d0\u793a\uff0c\u4e0d\u518d\u63d0\u9192</a> \t</div> </div> </div>');
		setTimeout(function() {
			la.css("top", 192 + y.outerHeight() + "px")
		}, 1E3);
		$("#payTips").fedGuide("show", "#payChognzhi", {
			left : 3,
			top : -25,
			zindex : 1E3
		})
	};
	if (!~K)
		K = k.length;
	if (~x) {
		cookieVal = k.slice(x + 11 + 1, K);
		I !== cookieVal && ma()
	} else
		ma();
	$("#topNews").find(".close_btn").click(
			function(d) {
				$(this).parent().animate({
					opacity : 0
				}, 300, function() {
					$(this).slideUp();
					aa.animate({
						"padding-top" : "0"
					});
					setTimeout(function() {
						la.css("top", "192px");
						$("#payTips").animate({
							top : "0"
						});
						$("#ggFloat").animate({
							top : "186px"
						})
					}, 100)
				});
				var f = new Date;
				f.setTime(f.getTime() + 31536E5);
				document.cookie = "fed_topNews=" + window.$_TipLastModified
						+ "; expires=" + f.toGMTString()
						+ "; path=/; domain=www.5173.com";
				d.preventDefault()
			});
	var ka;
	$("#moreServices").hover(
			function() {
				var d = this;
				ka = setTimeout(function() {
					$(d).find("h4").addClass("current").end().find(
							"ul.more_menu_list").show()
				}, 100)
			},
			function() {
				clearTimeout(ka);
				ka = null;
				$(this).find("h4").removeClass("current").end().find(
						"ul.more_menu_list").hide()
			});
	$("#mainMenuList,.goods_list").delegate("li", "mouseover", function() {
		$(this).addClass("current")
	}).delegate("li", "mouseout", function() {
		$(this).removeClass("current")
	});
	$("#ingotTop li,#newgameList li").hover(
			function() {
				$(this).siblings(".current").removeClass("current").end()
						.addClass("hover current").find("img").trigger(
								"loadImg")
			}, function() {
				$(this).removeClass("hover")
			});
	$("input.fed_input").focus(function() {
		$(this).addClass("focus")
	}).blur(function() {
		$(this).removeClass("focus")
	});
	$(".gg_219").switchable({
		auto : false,
		effects : "horizontal",
		nav : false,
		imglazyload : true
	});
	$("#guesslikeTestgg").switchable({
		effects : "horizontal",
		nav : false,
		index : false
	});
	$("#siteData").switchable({
		effects : "vertical",
		nav : false,
		index : false,
		auto : false,
		duration : 300
	});
	$("#cardInnerTab1 img,#newGameTab1 img,#newgameList img").imglazyload();
	$("#ingotTop img").first().imglazyload().end().slice(1).one("loadImg",
			function() {
				$(this).imglazyload()
			});
	$("#gg330 textarea.fragment_box").imglazyload({
		callback : function() {
			$.loadKJ(this)
		}
	});
	$.fed.tabs("#newsTab", {
		event : "mouseover"
	});
	$.fed.tabs("#spike", {
		event : "mouseover"
	});
	$.each([ "#cardOuterTab", "#cardOuterTab1", "#cardOuterTab2",
			"#cardOuterTab3", "#newGameTab" ], function(d, f) {
		$.fed.tabs(f, {
			event : "mouseover",
			onSuccess : function(i) {
				i = $(i);
				i.is(".hide") && i.trigger("lazyload")
			}
		});
		f !== "#cardOuterTab"
				&& $(f).children(".hide").one("lazyload", function() {
					$("img", this).imglazyload()
				})
	});
	$("#cardOuterTab2,#cardOuterTab3,#cardOuterTab4").one("lazyload",
			function() {
				$(this).children("div").first().find("img").imglazyload()
			});
	$
			.ajax({
				url : "http://gg.5173.com/adpolestar/door_mp/;ap=425097FF_4FF7_007C_C6CC_305F1ACADF61|30EA7785_4639_35BA_07BD_0EE87045F9C1|6CC4F1FB_1164_9E67_0284_080EE7841DC1|CE16DFE8_F27F_05B5_8FAB_370B24328ED9|DFE80D1F_30AB_AABB_3443_A8CE6CE44394|;jsoncallback=fun;ct=json;pu=5173;/?",
				dataType : "jsonp",
				beforeSend : function() {
					var d = this.url, f = d.match(/(jQuery(?:\d|_)+)/)[1];
					this.url = d.replace(/(jsoncallback)=(\w+)/,
							function(i, p) {
								return p + "=" + f
							}).replace(/&.+/, "")
				},
				success : function(d) {
					for (var f = $("#mainSlider").find("ul"), i = "", p = d.length, m = 0; m < p; m++) {
						var w = d[m];
						i += '<li><a href="'
								+ w.link
								+ '"><img '
								+ (m > 1 ? 'src="http://img01.5173cdn.com/fed/build/1.00/images/placeholder.png" data-lazysrc="'
										: 'src="') + w.src + '"width="'
								+ w.width + '" height="' + w.height + '" alt="'
								+ w.alt + '" /></a></li>'
					}
					$.each(d, function() {
					});
					f.html(i);
					$("#mainSlider").switchable({
						effects : "horizontal",
						imglazyload : true
					})
				}
			});
	$("#mainSlider .switchable_container").delegate("a", "mouseover",
			function() {
				$(this).siblings().addClass("dark")
			}).delegate("a", "mouseout", function() {
		$(this).siblings().removeClass("dark")
	});
	var ca = $("#csQQNumber"), N = $("#csValidSafeTip"), O = $("#csValidListTip"), Z = /^[1-9]\d{4,}$/, na = /[^\d]+/, ha = ca
			.attr("placeholder");
	$.inputPlaceholder(ca, "#333");
	var V = function(d) {
		O.html('<s class="ico_error_1"></s>' + d).css({
			visibility : "visible"
		})
	}, sa = function() {
		O.css({
			visibility : "hidden"
		})
	};
	ca.focus(function() {
		N.show()
	}).keyup(function() {
		var d = this.value;
		if (na.test(d))
			this.value = d.replace(na, "")
	}).blur(function() {
		var d = $.trim(this.value);
		if (!d || d === ha)
			V("\u8bf7\u8f93\u5165QQ\u53f7\u7801");
		else if (d.length < 5)
			V("QQ\u53f7\u96505\u4f4d\u4ee5\u4e0a\u6570\u5b57");
		else
			Z.test(d) ? sa() : V("QQ\u53f7\u4e0d\u80fd\u4ee50\u5f00\u5934");
		N.hide()
	});
	$("#csQQBtn").click(function(d) {
		var f = $.trim(ca[0].value);
		if (Z.test(f))
			$.analogSubmit("http://safe.5173.com/", {
				url : "SafeCenter/dynamic/kefuyanzhen.html",
				extras : {
					txtQQNumber : f,
					type : "www"
				}
			});
		else if (!f || f === ha)
			V("\u8bf7\u8f93\u5165QQ\u53f7\u7801");
		d.preventDefault()
	});
	(function() {
		var d = /^[a-zA-Z]{1}[-?\w]{5,19}$/, f = $("#csweichaiNumber"), i = $("#csValidListTip_weichai"), p = f
				.attr("placeholder");
		$.inputPlaceholder(f, "#333");
		var m = function(u) {
			i.html('<s class="ico_error_1"></s>' + u).css({
				visibility : "visible"
			})
		}, w = function() {
			i.css({
				visibility : "hidden"
			})
		};
		f
				.blur(function() {
					var u = $.trim(this.value);
					if (u == "" || u === p)
						m("\u8bf7\u8f93\u5165\u5fae\u4fe1\u53f7");
					else
						d.test(u) ? w()
								: m("\u5fae\u4fe1\u53f7\u9519\u8bef\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165")
				});
		$("#csweichaiBtn")
				.click(
						function(u) {
							var G = $.trim(f[0].value);
							if (!G || G === p)
								m("\u8bf7\u8f93\u5165\u5fae\u4fe1\u53f7");
							else
								d.test(G) ? $
										.analogSubmit(
												"http://safe.5173.com/",
												{
													url : "SafeCenter/dynamic/kefuyanzhen.html",
													extras : {
														txtwechaNumber : G,
														type : "www"
													}
												})
										: m("\u5fae\u4fe1\u53f7\u9519\u8bef\uff0c\u8bf7\u91cd\u65b0\u8f93\u5165");
							u.preventDefault()
						})
	})();
	var F = $("#cateIndexNav"), X = $("#cateIndexList");
	k = $("#cateIndexBox");
	var L, R = function(d, f) {
		var i = f.text();
		d = "GAMEHTML_" + i;
		var p = f.data(d);
		if (p === undefined) {
			p = "";
			$
					.each(
							window.allGameData,
							function(m, w) {
								if (w.name === i) {
									$
											.each(
													w.list,
													function(u, G) {
														p += '<li><a href="http://trading.5173.com/search/'
																+ G.id
																+ '.shtml"'
																+ (name !== "HOTG"
																		&& G.hot === "1" ? 'class="hot"'
																		: "")
																+ ">"
																+ G.name
																+ "</a></li>"
													});
									return false
								}
							});
			p = p === "" ? '<li class="tip">\u8be5\u5b57\u6bcd\u4e0b\u6682\u65e0\u6e38\u620f</li>'
					: p;
			f.data(d, p)
		}
		X.html(p)
	};
	F.delegate(
			"li",
			"mouseover",
			function() {
				var d = $(this);
				L = setTimeout(function() {
					F.css("borderBottomColor", "#8db6fa").find(".current")
							.removeClass("current");
					d.addClass("current");
					window.allGameData && R(allGameData, d.find("a"));
					X.show()
				}, 100)
			}).delegate("a", "click", function(d) {
		d.preventDefault()
	}).delegate("li", "mouseout", function() {
		if (L) {
			clearTimeout(L);
			L = null
		}
	});
	k.bind("mouseleave", function() {
		X.hide();
		F.css("borderBottomColor", "").find(".current").removeClass("current")
	});
	r
			&& X.delegate("a", "click", function() {
				__utmTrackEvent("%E6%96%B0%E7%89%88%E9%A6%96%E9%A1%B5",
						"%E6%89%80%E6%9C%89%E7%B1%BB%E7%9B%AE",
						"%E6%8B%BC%E9%9F%B3%E7%B4%A2%E5%BC%95-"
								+ encodeURIComponent($(this).text()))
			});
	t($("#popupRight")[0], {
		bottom : "0px",
		right : "0px"
	});
	var ia = $("#upTop");
	ia.find("a").click(function(d) {
		$(window).scrollTop(0);
		d.preventDefault()
	});
	$(window).scroll(
			function() {
				$(this).scrollTop() > 0 ? ia.css("visibility", "visible") : ia
						.css("visibility", "hidden")
			});
	var W = $("#ggFloat"), B;
	setTimeout(function() {
		W.show();
		B = setTimeout(function() {
			W.hide()
		}, 5E3)
	}, 2E3);
	W.find(".close_btn").click(function(d) {
		clearTimeout(B);
		B = null;
		W.hide();
		d.preventDefault()
	});
	$(window).scroll(function() {
		if ($(window).scrollTop() > 184) {
			aa.find(".feedback_1").css({
				visibility : "visible"
			});
			aa.css("top", "113px");
			$("#floatFeedbackState").css("display", "none")
		} else {
			aa.find(".feedback_1").css({
				visibility : "hidden"
			});
			$("#floatFeedbackState").css("display", "block")
		}
	});
	$.feedback({
		top : "113px",
		left : "50%"
	}, "\u65b0\u7248\u9996\u9875", "\u53f3\u6f02\u6846");
	var aa = $("#float_feedback");
	y.css("display") == "none" && aa.css("padding-top", "0");
	var va = function() {
		var d = true;
		if ($("#topRight .black_arrow").css("transition"))
			d = false;
		$("#topRight > li").hover(function() {
			var f = $(this).find("div.sub_box");
			if (f.length) {
				$(this).css("zIndex", "1002").addClass("current");
				d || $(this).find(".black_arrow").addClass("arrow_up");
				f.show()
			}
		}, function() {
			var f = $(this).find("div.sub_box");
			if (f.length) {
				$(this).css("zIndex", "").removeClass("current");
				d || $(this).find(".black_arrow").removeClass("arrow_up");
				f.hide()
			}
		})
	}, da = $("#guideTip"), ta = {
		step1 : $("#history_browse"),
		step2 : $("#valid"),
		step3 : $("#loginList li").eq(0)
	}, l = function(d, f, i) {
		da.css({
			top : d + "px",
			marginLeft : f + "px"
		});
		d = ta[i];
		d.css({
			position : "relative",
			zIndex : "100000"
		});
		i === "step1" && d.css("position", "absolute");
		if (i === "step1" || i === "step4") {
			$("#topRight > li").unbind("mouseenter mouseleave");
			d.addClass("current").children(".sub_box").show()
		}
		d.addClass("current")
	}, oa = function(d) {
		var f = ta[d];
		f.css({
			position : "",
			zIndex : ""
		});
		d === "step1" && f.css("marginLeft", "");
		if (d === "step1" || d === "step4") {
			va();
			f.removeClass("current").children(".sub_box").hide()
		}
	};
	da.find("a.next_btn").click(
			function(d) {
				var f = $(this).parent();
				da.find("li.current").removeClass("current").next().addClass(
						"current");
				switch (f[0].className) {
				case "step1":
					oa("step1");
					l(625, -306, "step2");
					break;
				case "step2":
					oa("step2");
					l(220, -506, "step3");
					break
				}
				r
						&& __utmTrackEvent("%E5%BC%95%E5%AF%BC",
								"%E6%96%B0%E7%89%88%E9%A6%96%E9%A1%B5",
								"%E5%85%B3%E9%97%AD-"
										+ ($(this).parents("li").index() + 1));
				d.preventDefault()
			});
	da.find("a.close_btn").click(
			function(d) {
				oa($(this).parent()[0].className);
				$.LAYER.close();
				var f = new Date;
				f.setTime(f.getTime() + 31536E5);
				document.cookie = "fed_guideTip=1; expires=" + f.toGMTString()
						+ "; path=/; domain=www.5173.com";
				r
						&& __utmTrackEvent("%E5%BC%95%E5%AF%BC",
								"%E6%96%B0%E7%89%88%E9%A6%96%E9%A1%B5",
								"%E5%85%B3%E9%97%AD-"
										+ ($(this).parents("li").index() + 1));
				d.preventDefault()
			});
	da.find("a.more_btn").click(function() {
		$(this).next().trigger("click")
	});
	~document.cookie.indexOf("fed_guideTip=")
			|| r
			&& __utmTrackEvent("%E5%BC%95%E5%AF%BC",
					"%E6%96%B0%E7%89%88%E9%A6%96%E9%A1%B5",
					"%E5%B1%95%E5%BC%80%E5%BC%95%E5%AF%BC");
	navigator.userAgent.toLowerCase().indexOf("msie 6.0");
	var P = $(".gg_130");
	k = $(".gg_130 .close_btn");
	if (P.length) {
		n && $(window).scroll(function() {
			$(this).scrollTop() > 0 ? P.css({
				position : "absolute",
				top : $(this).scrollTop() + 300
			}) : P.css({
				top : "300px"
			})
		});
		k.click(function() {
			P.hide()
		})
	}
	$("#old_link").live(
			"click",
			function() {
				r
						&& __utmTrackEvent("%E5%BC%95%E5%AF%BC",
								"%E8%BF%94%E5%9B%9E%E6%97%A7%E7%89%88");
				var d = new Date;
				d.setTime(d.getTime() + 31536E5);
				document.cookie = "_homePageNew=0; expires=" + d.toGMTString()
						+ "; path=/; domain=5173.com"
			});
	$.fed.tabs("#valid", {
		event : "mouseover",
		onSuccess : function() {
		}
	});
	var S = $("#csUrl");
	S.focus(function() {
		$(this).css({
			color : "#333333"
		})
	});
	S.blur(function() {
		$.trim(S.val())
	});
	$("#csUrlBtn")
			.click(
					function() {
						var d = S.val();
						if ($.trim(d))
							$.analogSubmit("http://safe.5173.com/", {
								url : "SafeCenter/dynamic/wangzhiyanzhen.html",
								extras : {
									txtWebUrlID : d,
									type : "www"
								}
							});
						else {
							$("#csValidListTip_Url").css({
								visibility : "visible"
							});
							$("#csValidListTip_Url")
									.html(
											"\u8bf7\u8f93\u5165\u9700\u8981\u9a8c\u8bc1\u7684\u7f51\u5740\u3002")
						}
						return false
					});
	$("#csQQBtn").click(function(d) {
		var f = $.trim(ca[0].value);
		if (!Z.test(f))
			if (!f || f === ha)
				V("\u8bf7\u8f93\u5165QQ\u53f7\u7801");
		d.preventDefault()
	});
	$("#caipiaotip-close").live("click", function() {
		$("#caipiaotip1").fed("close")
	});
	$("#topRight>li").eq(5).find(".sub_box li").eq(4).addClass("current");
	$("#topRight>li").eq(5).find(".sub_box li").eq(3).addClass("current");
	n = {};
	n = D();
	if (n.RecommendUserId != null) {
		k = [];
		k[0] = n.RecommendUserId;
		k[1] = "";
		k[2] = "";
		k[3] = "";
		J("SourceCookie", k)
	}
	Y();
	var ua = {
		init : function(d) {
			this.opt = d;
			this.tpl();
			$("#wdzs_kf2013").attr("class", "wdzskf_def").append(this.tpl.def);
			this.dosomting();
			this.scroll()
		},
		scroll : function() {
			var d = $("#wdzs_kf2013").offset().top - 28, f = $.browser.msie
					&& $.browser.version === "6.0";
			document.body.clientWidth < 1305 && $("#wdzs_kf2013").css({
				"margin-left" : "0",
				left : "0"
			});
			$(window).resize(function() {
				document.body.clientWidth < 1305 ? $("#wdzs_kf2013").css({
					"margin-left" : "0",
					left : "0"
				}) : $("#wdzs_kf2013").css({
					"margin-left" : "-640px",
					left : "50%"
				})
			});
			f ? $(window).scroll(function() {
				$(this).scrollTop() > d ? $("#wdzs_kf2013").css({
					top : $(this).scrollTop() + 35
				}) : $("#wdzs_kf2013").css({
					top : "246px"
				})
			}) : $(window).scroll(function() {
				$(this).scrollTop() > d ? $("#wdzs_kf2013").css({
					position : "fixed",
					top : "35px"
				}) : $("#wdzs_kf2013").css({
					position : "absolute",
					top : "246px"
				})
			})
		},
		dosomting : function() {
			$("#wode_kf_close").bind("click", function() {
				$("#wdzs_kf2013").hide()
			});
			$
					.ajax({
						type : "GET",
						url : "http://user.5173.com/ajax/GetUserBindSc.ashx",
						dataType : "jsonp",
						jsonp : "jsoncallback",
						scriptCharset : "GB2312",
						jsonpCallback : "getBind",
						cache : false,
						success : function(d) {
							var f = $("#wdzs_kf2013");
							if (d.BindKefu == "")
								f.attr("class", "wdzskf_def");
							else {
								$("#wdzs_kf2013").show();
								f.find(".wode_kf").show();
								f.find(".def_kf").hide();
								$
										.ajax({
											type : "GET",
											url : "http://sc.5173.com/api/online_operator.php?ac=operator_info&id="
													+ d.BindKefu,
											dataType : "jsonp",
											jsonp : "jsoncallback",
											scriptCharset : "GB2312",
											jsonpCallback : "operator",
											cache : false,
											success : function(i) {
												i = i.operator[0];
												if (i == null || i == "")
													f.attr("class",
															"wdzskf_def");
												else {
													$("#wdzs_kf2013").show();
													$("#bdBtn_wdzs_kf2013")
															.attr("class",
																	"ghKf_wdzs_kf2013")
															.text(
																	"\u66f4\u6362\u6211\u7684\u4e13\u5c5e\u5ba2\u670d \u300b");
													f.find(".wode_kf").show();
													if (i.name != "")
														$(
																".loginName_wdzs_kf2013")
																.text(
																		i.nick_name);
													else {
														f
																.find(
																		".loginName_wdzs_kf2013")
																.hide();
														f.find(".ms_online")
																.hide()
													}
													i.QQ !== ""
															&& $(
																	"#qqNum_wdzs_kf2013")
																	.parent(
																			".txBox")
																	.show()
																	.end()
																	.text(i.QQ);
													i.weixin !== ""
															&& $(
																	"#wxNum_wdzs_kf2013")
																	.parent(
																			".txBox")
																	.show()
																	.end()
																	.text(
																			i.weixin);
													i.mobile !== ""
															&& $(
																	"#sjNum_wdzs_kf2013")
																	.parent(
																			".txBox")
																	.show()
																	.end()
																	.text(
																			i.mobile);
													i.icon !== ""
															&& $(
																	'<img src="'
																			+ i.icon
																			+ '" alt="" width="94" height="94" />')
																	.load(
																			function() {
																				$(
																						"#txPic_wdzs_kf2013")
																						.append(
																								'<img src="'
																										+ i.icon
																										+ '" alt="'
																										+ i.nick_name
																										+ '" width="94" height="94"/>')
																			});
													if (i.isonjob == 1
															&& i.isbusy == 0) {
														f
																.attr("class",
																		"wdzskf_online");
														if (i.QQ !== "")
															if (i.IsNewPopQQ == undefined)
																$(
																		"#qq_wdzs_kf2013")
																		.html(
																				'<a target="_blank" href="tencent://message/?uin='
																						+ i.QQ
																						+ '&Site=&Menu=yes"></a>');
															else
																i.IsNewPopQQ == 1 ? $(
																		"#qq_wdzs_kf2013")
																		.html(
																				'<a target="_blank" href="http://sighttp.qq.com/authd?IDKEY='
																						+ i.qqidkey
																						+ '"></a>')
																		: $(
																				"#qq_wdzs_kf2013")
																				.html(
																						'<a target="_blank" href="tencent://message/?uin='
																								+ i.QQ
																								+ '&Site=&Menu=yes"></a>');
														else {
															f
																	.attr(
																			"class",
																			"wdzskf_offline");
															$(
																	"#bdBtn_wdzs_kf2013")
																	.attr(
																			"class",
																			"qtKf_wdzs_kf2013")
																	.text(
																			"\u9009\u62e9\u5176\u4ed6\u4e13\u5c5e\u5ba2\u670d")
														}
													} else {
														f
																.attr("class",
																		"wdzskf_offline");
														$("#bdBtn_wdzs_kf2013")
																.attr("class",
																		"qtKf_wdzs_kf2013")
																.text(
																		"\u9009\u62e9\u5176\u4ed6\u4e13\u5c5e\u5ba2\u670d")
													}
													var p = f.find(".superior");
													$("#btnSup_wdzs_kf2013")
															.show();
													$("#btnSup_wdzs_kf2013")
															.bind(
																	"click",
																	function() {
																		if (p
																				.css("display") == "block") {
																			p
																					.hide();
																			$(
																					this)
																					.attr(
																							"class",
																							"btnBom_superior")
																		} else {
																			p
																					.show();
																			$(
																					this)
																					.attr(
																							"class",
																							"btnTop_superior")
																		}
																	});
													if (i.superior_name !== "")
														$(
																".supName_wdzs_kf2013")
																.text(
																		i.superior_name);
													else {
														$("#btnSup_wdzs_kf2013")
																.hide();
														p.hide()
													}
													i.superior_qq !== ""
															&& $(
																	"#supQQ_wdzs_kf2013")
																	.parent(
																			".txBox")
																	.show()
																	.end()
																	.text(
																			i.superior_qq);
													i.superior_weixin !== ""
															&& $(
																	"#supWeixin_wdzs_kf2013")
																	.parent(
																			".txBox")
																	.show()
																	.end()
																	.text(
																			i.superior_weixin);
													i.superior_mobile !== ""
															&& $(
																	"#supMobile_wdzs_kf2013")
																	.parent(
																			".txBox")
																	.show()
																	.end()
																	.text(
																			i.superior_mobile)
												}
											}
										})
							}
						}
					})
		},
		tpl : function() {
			var d = this.tpl;
			d.def = "";
			d.def += '<div class="wrapBox">';
			d.def += '<div id="wode_kf_close"></div>';
			d.def += '    <div class="ms_hdBox_kf"></div>';
			d.def += '   <div class="txPic" id="txPic_wdzs_kf2013">';
			d.def += "  </div>";
			d.def += '<div class="wode_kf">';
			d.def += '  <div class="hdBox_wdzs_kf2013">';
			d.def += '    <div class="loginName_wdzs_kf2013">\u6211\u7684\u4e13\u5c5e\u5ba2\u670d</div>';
			d.def += '    <div class="defMs_hdBox_kf">\u4e00\u5bf9\u4e00\u7684\u4f18\u8d28\u670d\u52a1</div>';
			d.def += '      <div id="qq_wdzs_kf2013"></div>';
			d.def += "  </div>";
			d.def += '  <div class="offline">';
			d.def += '  <div class="ms_offline">';
			d.def += '          <p class="l01">\u60a8\u7684\u4e13\u5c5e\u5ba2\u670d</p>';
			d.def += '          <p class="l02">\u76ee\u524d\u4e0d\u5728\u7ebf</p>';
			d.def += '          <a class="l03" href="http://user.5173.com/ScOperator/bind.aspx">\u9009\u62e9\u5176\u4ed6\u4e13\u5c5e\u5ba2\u670d</a>';
			d.def += "      </div>";
			d.def += "  </div>";
			d.def += '  <div class="items_wdzs_kf2013">';
			d.def += '  <dl class="txBox">';
			d.def += '      <dt class="text_txBox">Q&nbsp;Q:</dt>';
			d.def += '      <dd id="qqNum_wdzs_kf2013">';
			d.def += "       </dd>";
			d.def += "  </dl>";
			d.def += '  <dl class="txBox">';
			d.def += '      <dt class="text_txBox">\u624b\u673a:</dt>';
			d.def += '      <dd id="sjNum_wdzs_kf2013">';
			d.def += "      </dd>";
			d.def += "  </dl>";
			d.def += '  <dl class="txBox">';
			d.def += '      <dt class="text_txBox">\u5fae\u4fe1:</dt>';
			d.def += '      <dd id="wxNum_wdzs_kf2013"></dd>';
			d.def += "  </dl>";
			d.def += "  </div>";
			d.def += '  <a class="ljKf_wdzs_kf2013"  id="bdBtn_wdzs_kf2013" href="http://user.5173.com/ScOperator/bind.aspx"></a>';
			d.def += "</div>";
			d.def += '<div id="btnSup_wdzs_kf2013" class="btnBom_superior"><span></span></div>';
			d.def += '    <div class="superior">';
			d.def += '    <dl class="nameBox_wdzs_kf2013">';
			d.def += '        <dt class="supName_wdzs_kf2013"></dt>';
			d.def += "        <dd>\u5ba2\u670d\u7ecf\u7406</dd>";
			d.def += "    </dl>";
			d.def += '    <dl class="txBox">';
			d.def += '    <dt class="text_txBox">Q&nbsp;Q:</dt>';
			d.def += '    <dd id="supQQ_wdzs_kf2013"></dd>';
			d.def += "    </dl>";
			d.def += '    <dl class="txBox">';
			d.def += '        <dt class="text_txBox">\u624b\u673a:</dt>';
			d.def += '        <dd id="supMobile_wdzs_kf2013"></dd>';
			d.def += "</dl>";
			d.def += '<dl class="txBox">';
			d.def += '        <dt class="text_txBox">\u5fae\u4fe1:</dt>';
			d.def += '        <dd id="supWeixin_wdzs_kf2013"></dd>';
			d.def += "</dl>";
			d.def += " </div>";
			d.def += "</div>"
		}
	};
	$(function() {
		$("#wdzs_kf2013").length > 0 && ua.init()
	});
	(function() {
		var d = $("#guesslikeCon ul.outter_list_new");
		$
				.easyAjax(
						"http://fcd.5173.com/CommonData/GetDataForWww.aspx?type=guesslike",
						null,
						function(f) {
							if (f) {
								for (var i = f.length, p = "", m = 0; m < i; m++) {
									p += '<li><a class="img_box" href="'
											+ f[m].ImageUrl
											+ '"><img width="120" height="80" alt="" src="'
											+ f[m].ImageSrc + '"></a>';
									p += '  <span class="outter_list_new_txt">';
									p += '      \t<a class="current" href="'
											+ f[m].Url1 + '">' + f[m].Name1
											+ '</a> | <a href="' + f[m].Url2
											+ '">' + f[m].Name2
											+ '</a> | <a href="' + f[m].Url3
											+ '">' + f[m].Name3 + "</a>";
									p += "  </span></li>"
								}
								d.html(p)
							}
						})
	})();
	(function() {
		var d = window.location.href, f = z("recommendUserId", d), i = function(
				E, H) {
			var U = new Date;
			U.setTime(U.getTime() + 2592E6);
			document.cookie = E + "=" + H + ";expires=" + U.toGMTString()
					+ "path=/;domain=5173.com"
		}, p = function() {
			$
					.ajax({
						type : "get",
						dataType : "jsonp",
						jsonp : "jsoncallback",
						scriptCharset : "gb2312",
						jsonpCallback : "getCookie",
						cache : false,
						url : "http://fcd.5173.com/UserCookies/SetUserCookies.aspx?recommendUserId="
								+ f
								+ "&sourceSiteId="
								+ m
								+ "&sourceAdId="
								+ w
								+ "&sourceChildSiteId=" + u + "&comUrl=" + G,
						success : function(E) {
							(E = E[0].user) && i("SourceCookie", E)
						}
					})
		};
		if (f) {
			var m = z("sourceSiteId", d) == null ? "" : z("sourceSiteId", d), w = z(
					"sourceAdId", d) == null ? "" : z("sourceAdId", d), u = z(
					"sourceChildSiteId", d) == null ? "" : z(
					"sourceChildSiteId", d), G = document.referer == null ? ""
					: document.referer;
			if (d = $.cookie("SourceCookie"))
				d.split(",")[0] != f && p();
			else
				p();
			f = f
		}
	})();
	C();
	o();
	$(".holiday_logo").attr("href", "http://www.18luohan.com/")
});
