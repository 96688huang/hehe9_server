/* Build by ued.5173.com 
 Date:2015-04-01 13:25:32 
 Version:2.00 */
(function(a) {
	function f(d) {
		return d && d.constructor === Number ? d + "px" : d
	}
	a.fn.bgiframe = "undefined" == typeof a("body").css("maxHeight") ? function(
			d) {
		d = a.extend({
			top : "auto",
			left : "auto",
			width : "auto",
			height : "auto",
			opacity : true,
			src : "javascript:false;"
		}, d);
		var c = '<iframe class="bgiframe"frameborder="0"tabindex="-1"src="'
				+ d.src
				+ '"style="display:block;position:absolute;z-index:-1;'
				+ (d.opacity !== false ? "filter:Alpha(Opacity='0');" : "")
				+ "top:"
				+ (d.top == "auto" ? "expression(((parseInt(this.parentNode.currentStyle.borderTopWidth)||0)*-1)+'px')"
						: f(d.top))
				+ ";left:"
				+ (d.left == "auto" ? "expression(((parseInt(this.parentNode.currentStyle.borderLeftWidth)||0)*-1)+'px')"
						: f(d.left))
				+ ";width:"
				+ (d.width == "auto" ? "expression(this.parentNode.offsetWidth+'px')"
						: f(d.width))
				+ ";height:"
				+ (d.height == "auto" ? "expression(this.parentNode.offsetHeight+'px')"
						: f(d.height)) + ';"/>';
		return this.each(function() {
			a(this).children("iframe.bgiframe").length === 0
					&& this.insertBefore(document.createElement(c),
							this.firstChild)
		})
	}
			: function() {
				return this
			};
	a.fn.bgIframe = a.fn.bgiframe
})(jQuery);
(function(a, f, d) {
	function c(h) {
		return h
	}
	function b(h) {
		return decodeURIComponent(h.replace(g, " "))
	}
	var g = /\+/g, e = a.cookie = function(h, j, k) {
		if (j !== d) {
			k = a.extend({}, e.defaults, k);
			if (j === null)
				k.expires = -1;
			if (typeof k.expires === "number") {
				var l = k.expires, m = k.expires = new Date;
				m.setDate(m.getDate() + l)
			}
			j = e.json ? JSON.stringify(j) : String(j);
			return f.cookie = [ encodeURIComponent(h), "=",
					e.raw ? j : encodeURIComponent(j),
					k.expires ? "; expires=" + k.expires.toUTCString() : "",
					k.path ? "; path=" + k.path : "",
					k.domain ? "; domain=" + k.domain : "",
					k.secure ? "; secure" : "" ].join("")
		}
		j = e.raw ? c : b;
		k = f.cookie.split("; ");
		l = 0;
		for (m = k.length; l < m; l++) {
			var p = k[l].split("=");
			if (j(p.shift()) === h) {
				h = j(p.join("="));
				return e.json ? JSON.parse(h) : h
			}
		}
		return null
	};
	e.defaults = {};
	a.removeCookie = function(h, j) {
		if (a.cookie(h) !== null) {
			a.cookie(h, null, j);
			return true
		}
		return false
	}
})(jQuery, document);
$(function() {
	function a(k) {
		var l;
		return (l = document.cookie.match(new RegExp("(^| )" + k
				+ "=([^;]*)(;|$)"))) ? unescape(l[2]) : null
	}
	function f(k, l) {
		var m = new Date;
		m.setTime(m.getTime() + 2592E6);
		document.cookie = k + "=" + escape(l) + ";expires=" + m.toGMTString()
				+ ";path=/;domain=5173.com"
	}
	function d(k) {
		var l;
		if (a("_BFD"))
			a("_BFD");
		else {
			l = Math.floor(Math.random() * k + 1);
			f("_BFD", l)
		}
		return Math.floor(Math.random() * k + 1)
	}
	function c() {
		$("#loginList")
				.prepend(
						'<li id="changeV2014">\u8fd4\u56de\u65e7\u7248\u9996\u9875</li>');
		$("#changeV2014").live("click", function() {
			document.location.href = "http://www.5173.com"
		})
	}
	function b() {
		$("#loginList")
				.prepend(
						'<li id="changeV2014">\u4f7f\u75282014\u5e74\u65b0\u7248\u9996\u9875</li>');
		$("#changeV2014").live("click", function() {
			document.location.href = "http://www.5173.com/default"
		})
	}
	var g = true;
	if ($("#topRight .black_arrow").css("transition"))
		g = false;
	$("#topRight > li").bind("mouseenter", function() {
		var k = $(this).find("div.sub_box");
		if (k.length) {
			$(this).css("zIndex", "1002").addClass("current");
			g || $(this).find(".black_arrow").addClass("arrow_up");
			k.show()
		}
	}).bind("mouseleave", function() {
		var k = $(this).find("div.sub_box");
		if (k.length) {
			$(this).css("zIndex", "").removeClass("current");
			g || $(this).find(".black_arrow").removeClass("arrow_up");
			k.hide()
		}
	});
	var e = function(k, l, m, p) {
		k = {
			url : k,
			dataType : "jsonp",
			scriptCharset : "GBK",
			jsonp : "jsoncallback",
			success : m,
			complete : p
		};
		if (l) {
			k.cache = true;
			k.jsonpCallback = l
		}
		$.ajax(k)
	}, h = false, j = false;
	if (window.location.href == "http://www.5173.com/"
			|| window.location.href == "http://www.5173.com/default")
		if (window.location.href.indexOf("default") == "-1") {
			h = true;
			b()
		} else {
			j = true;
			c()
		}
	e(
			"http://user.5173.com/Ajax/FrameLoginHandle.ashx",
			null,
			function(k) {
				if (k.name) {
					var l = k.id, m = '<li><b class="f_333">'
							+ k.name
							+ '</b></li><li id="msgList"><a href="http://message.5173.com/MyInfo/SiteMessageList.aspx?TagType=0" class="msg">\u7ad9\u5185\u4fe1<span id="msgNum">'
							+ k.msg
							+ '</span><em class="line">|</em></a><div class="msg_box" id="msgBox"></div></li><li><a href="#" class="login_out" id="loginOut">\u9000\u51fa</a></li>';
					$("#loginList").html(m);
					h && b();
					j && c();
					k.msg !== "0" && $("#msgNum").addClass("highlight");
					e("http://fcd.5173.com/shoppingcart/getcount.aspx", null,
							function(w) {
								$("#markCart b.num").text(w.count)
							});
					$("#loginOut")
							.click(
									function(w) {
										var F = window.location.href
												|| "http://www.5173.com", n;
										try {
											if (KeyUndecode)
												n = "&Undecode=" + KeyUndecode
										} catch (q) {
											n = ""
										}
										window.location.href = "http://passport.5173.com/Sso/Logout?returnUrl="
												+ escape(F) + n;
										w.preventDefault()
									});
					window._BFD = window._BFD || {};
					_BFD.BFD_USER = {
						user_id : l,
						user_cookie : d(99999999)
					};
					if (_BFD.BFD_INFO)
						_BFD.BFD_INFO.user_id = l;
					window.isSignIn = true
				} else {
					window._BFD = window._BFD || {};
					_BFD.BFD_USER = {
						user_id : "",
						user_cookie : d(99999999)
					};
					if (_BFD.BFD_INFO)
						_BFD.BFD_INFO.user_id = "";
					window.isSignIn = false
				}
				if (_BFD.BFD_INFO) {
					_BFD.client_id = "Ctest_5173";
					_BFD.script = document.createElement("script");
					_BFD.script.type = "text/javascript";
					_BFD.script.async = true;
					_BFD.script.charset = "utf-8";
					_BFD.script.src = ("https:" == document.location.protocol ? "https://ssl-static1"
							: "http://static1")
							+ ".bfdcdn.com/service/5173/5173.js";
					document.getElementsByTagName("head")[0]
							.appendChild(_BFD.script)
				}
				window.loginData = k;
				if (typeof window.loginCallback === "function") {
					window.loginCallback(k);
					try {
						window.loginData = window.loginCallback = null;
						delete window.loginCallback;
						delete window.loginData
					} catch (p) {
					}
				}
			})
});
(function(a) {
	a.fn.switchable = function(f) {
		return this
				.each(function() {
					var d = a.extend({
						auto : true,
						delay : 3E3,
						duration : 600,
						index : true,
						nav : true,
						nextText : "Next",
						prevText : "Previous",
						effects : "default",
						imglazyload : false,
						callback : null,
						init : null
					}, f), c = a(this), b = c.find("div.switchable_container"), g = b
							.children("ul"), e = g.children("li"), h = e.length, j = {}, k = "", l = "", m, p, w, F, n, q;
					if (!(h < 2)) {
						a.easing.easeOutCirc = function(x, t, C, u, A) {
							return u * Math.sqrt(1 - (t = t / A - 1) * t) + C
						};
						j["default"] = function(x, t) {
							var C = e.filter(".current");
							if (x !== undefined) {
								t = x;
								x = m.filter(".current").attr("data-index");
								if (t === x)
									return
							} else {
								x = parseInt(C.attr("data-index"));
								t = !t ? x === h ? 0 : x + 1 : x === 0 ? h
										: x - 1
							}
							C.fadeOut().removeClass("current");
							e.eq(t).fadeIn().addClass("current").trigger(
									"loadImg").next().trigger("loadImg");
							d.callback
									&& d.callback.apply(e.eq(t)[0],
											[ t, h + 1 ]);
							m
									&& m.eq(x).removeClass("current").end().eq(
											t).addClass("current")
						};
						a
								.each(
										{
											horizontal : [ "left", "width" ],
											vertical : [ "top", "height" ]
										},
										function(x, t) {
											var C = t[0], u = t[1];
											j[x] = function(A, E) {
												var J = e.filter(".current"), M = parseFloat(g
														.css(u)), L = parseFloat(g
														.css(C)), K = e
														.data("size"), G = a.noop, N = 1, Q = {}, P, R, T;
												if (A !== undefined) {
													T = A;
													A = m.filter(".current")
															.attr("data-index");
													if (T > A) {
														E = false;
														N = T - A
													} else if (T < A) {
														E = true;
														N = A - T
													} else
														return
												} else {
													A = parseInt(J
															.attr("data-index"));
													T = !E ? A === h ? 0
															: A + 1
															: A === 0 ? h
																	: A - 1
												}
												N = K * N;
												if (E) {
													L += N;
													if (A === 0) {
														P = e.last();
														R = "-" + M + "px";
														G = function() {
															a(this)
																	.css(
																			C,
																			"-"
																					+ (M - K)
																					+ "px");
															P.css("position",
																	"").css(C,
																	"")
														}
													}
												} else {
													L -= N;
													if (A === h) {
														P = e.first();
														R = M + "px";
														G = function() {
															a(this).css(C,
																	"0px");
															P.css("position",
																	"").css(C,
																	"")
														}
													}
												}
												P
														&& P.css("position",
																"relative")
																.css(C, R);
												Q[C] = L + "px";
												g.animate(Q, d.duration,
														"easeOutCirc", G);
												J.removeClass("current");
												e.eq(T).addClass("current")
														.trigger("loadImg")
														.next().trigger(
																"loadImg");
												d.callback
														&& d.callback.apply(e
																.eq(T)[0], [ T,
																h + 1 ]);
												m
														&& m
																.eq(A)
																.removeClass(
																		"current")
																.end()
																.eq(T)
																.addClass(
																		"current")
											}
										});
						q = j[d.effects];
						var r = function() {
							if (d.auto)
								w = setInterval(q, d.delay)
						}, s = function() {
							if (w) {
								clearInterval(w);
								w = null
							}
						}, y = function(x) {
							var t = ~this.className.indexOf("prev") ? true
									: false;
							s();
							d.effects !== "default" ? g.stop(true, true) : e
									.filter(":animated ").stop(true, true);
							q(undefined, t);
							r();
							x.preventDefault()
						}, B = function(x) {
							x = a(x).attr("data-index");
							s();
							d.effects !== "default" ? g.stop(true, true) : e
									.filter(":animated ").stop(true, true);
							q(x, false);
							r()
						}, H = function() {
							a(this).find("img").each(function() {
								var x = a(this);
								this.src = x.attr("data-lazysrc");
								x.removeAttr("data-lazysrc")
							})
						};
						(function(x) {
							var t = e.outerWidth(), C = e.outerHeight(), u, A, E;
							e.each(function(J) {
								J === 0 && a(this).addClass("current");
								a(this).attr("data-index", J)
							});
							if (x === "horizontal") {
								u = t;
								A = "width";
								E = "left"
							} else if (x === "vertical") {
								u = C;
								A = "height";
								E = "none"
							}
							if (x !== "default") {
								e.data("size", u);
								b.css({
									overflow : "hidden",
									position : "relative",
									width : t + "px",
									height : C + "px"
								});
								g.css(A, u * h + "px").css({
									position : "absolute",
									top : "0px",
									left : "0px"
								});
								e.css("float", E)
							} else
								e.each(function(J) {
									if (J !== 0)
										this.style.display = "none"
								});
							if (d.index) {
								for (n = 0; n < h; n++)
									k += '<a href="javascript:;" target="_self"'
											+ (n === 0 ? ' class="current"'
													: "")
											+ ' data-index="'
											+ n
											+ '">' + (n + 1) + "</a>";
								m = a(
										'<div class="switchable_index">' + k
												+ "</div>").appendTo(c).find(
										"a").hover(function() {
									var J = this;
									F = setTimeout(function() {
										B(J)
									}, 100)
								}, function() {
									clearTimeout(F);
									F = null
								})
							}
							if (d.nav) {
								l = '<a href="#" class="switchable_prev" style="display:none">'
										+ d.prevText
										+ '</a><a href="#" class="switchable_next" style="display:none">'
										+ d.nextText + "</a>";
								p = a(l).appendTo(c).click(y);
								c.hover(function() {
									p.show()
								}, function() {
									p.hide()
								})
							} else
								a("a.switchable_prev,a.switchable_next", c)
										.click(y);
							d.auto && e.hover(s, r);
							a.browser.msie
									&& a.browser.version === "6.0"
									&& document
											.execCommand(
													"BackgroundImageCache",
													false, true);
							d.imglazyload && e.slice(2).one("loadImg", H);
							d.init && d.init.call(e.first()[0], h);
							h--;
							r()
						})(d.effects)
					}
				})
	}
})(jQuery);
$.browser.msie && $.browser.version === "6.0"
		&& document.execCommand("BackgroundImageCache", false, true);
(function(a) {
	a.famsg = a.famsg || {};
	var f = [], d, c;
	a
			.extend(
					a.famsg,
					{
						loadQueue : function(b) {
							f.push(b);
							f[0] !== "runing" && a.famsg.loadDequeue()
						},
						loadDequeue : function() {
							var b = f.shift();
							if (b === "runing")
								b = f.shift();
							if (b) {
								f.unshift("runing");
								b()
							}
						},
						fullscreen : function(b) {
							var g = {
								load : 2E3,
								wait : 3E3,
								title : "",
								onStart : function() {
								},
								onEnd : function() {
								}
							};
							if (b != null)
								g = a.extend(g, b);
							b = "fullscreen_" + (new Date).getTime();
							var e = a(
									"<div id='" + b
											+ "' class='fullscreenmsg'></div>")
									.hide();
							a("body").prepend(e);
							b = a(
									"<a href='javascript:void(0);' target='_self' class='close'>X \u5173\u95ed</a>")
									.click(function() {
										e.slideUp("slow")
									});
							e
									.append(b)
									.append(
											a("<a class='adimg' href='"
													+ g.link
													+ "' target='_blank'><img src='"
													+ g.img + "' alt='"
													+ g.title + "' title='"
													+ g.title + "' ></a>"));
							setTimeout(function() {
								g.onStart()
							}, g.load);
							setTimeout(function() {
								e.slideDown("slow", function() {
								})
							}, g.load);
							setTimeout(function() {
								e.slideUp("slow", function() {
									g.onEnd()
								})
							}, g.load + g.wait)
						},
						popupmsg : function(b, g) {
							var e = {
								width : "300px",
								height : "180px",
								load : 5E3,
								stop : 4E3,
								speed : 1500
							};
							if (g != null)
								e = a.extend(e, g);
							g = a.famsg.getWinHeight();
							a(b).css("top", g).css("width", e.width).hide();
							a(
									"<a class='close' href='javascript:void 0'>\u00d7</a>")
									.appendTo(a(b)).click(function() {
										a(b).hide()
									});
							a.browser.msie
									&& a.browser.version == "6.0"
									&& a("body")
											.css(
													{
														"background-image" : "url(about:blank)",
														"background-attachment" : "fixed"
													});
							setTimeout(
									function() {
										a(b).data("lastscoll",
												a.famsg.getWinHeight()).show();
										if (a.browser.msie)
											var h = 0, j = setInterval(
													function() {
														var k = parseInt(a.famsg
																.getWinHeight())
																- 5 * h;
														k = Math
																.max(
																		k,
																		parseInt(a.famsg
																				.getWinHeight())
																				- parseInt(e.height));
														h++;
														if (k <= parseInt(a.famsg
																.getWinHeight())
																- parseInt(e.height)) {
															clearInterval(j);
															j = null;
															k = parseInt(document.documentElement.clientHeight)
																	- parseInt(e.height);
															a(b)
																	.attr(
																			"style",
																			" top:expression(eval(document.documentElement.scrollTop + "
																					+ k
																					+ ")); ")
														} else {
															var l = parseInt(a.famsg
																	.getWinHeight()), m = parseInt(a(
																	b)
																	.data(
																			"lastscoll"));
															m = l - m;
															if (m != 0) {
																k += m;
																a(b)
																		.data(
																				"lastscoll",
																				l)
															}
															a(b).css("top", k)
																	.show()
														}
													}, 20);
										else
											a(b)
													.animate(
															{
																top : parseInt(a.famsg
																		.getWinHeight())
																		- parseInt(e.height)
															},
															{
																duration : e.speed,
																step : function() {
																	var k = parseInt(a.famsg
																			.getWinHeight());
																	parseInt(a(
																			b)
																			.data(
																					"lastscoll"));
																	a(b)
																			.data(
																					"lastscoll",
																					k)
																},
																complete : function() {
																	a(b)
																			.css(
																					"top",
																					a.famsg
																							.getWinHeight()
																							- e.height)
																}
															})
									}, e.load);
							setTimeout(
									function() {
										var h = a.famsg.getWinHeight();
										a(b).data("lastscoll", h).show();
										if (a(b).css("display") != "none")
											if (a.browser.msie)
												var j = 0, k = setInterval(
														function() {
															var l = parseInt(a.famsg
																	.getWinHeight()
																	- parseInt(e.height))
																	+ 5 * j;
															l = Math
																	.min(
																			l,
																			parseInt(a.famsg
																					.getWinHeight()));
															j++;
															if (l >= parseInt(a.famsg
																	.getWinHeight())) {
																clearInterval(k);
																k = null;
																a(b).hide()
															} else {
																var m = parseInt(a.famsg
																		.getWinHeight()), p = parseInt(a(
																		b)
																		.data(
																				"lastscoll"));
																p = m - p;
																if (p != 0) {
																	l += p;
																	a(b)
																			.data(
																					"lastscoll",
																					m)
																}
																a(b).css("top",
																		l)
																		.show()
															}
														}, 20);
											else
												a(b)
														.animate(
																{
																	top : parseInt(a(
																			b)
																			.css(
																					"top"))
																			+ parseInt(e.height)
																},
																{
																	duration : e.speed,
																	step : function() {
																		var l = parseInt(a.famsg
																				.getWinHeight());
																		parseInt(a(
																				b)
																				.data(
																						"lastscoll"));
																		a(b)
																				.data(
																						"lastscoll",
																						l)
																	},
																	complete : function() {
																		a(b)
																				.css(
																						"top",
																						a.famsg
																								.getWinHeight()
																								- e.height)
																	}
																})
									}, e.load + e.stop)
						},
						getWinHeight : function() {
							return Math.max(document.documentElement.scrollTop,
									document.body.scrollTop)
									+ document.documentElement.clientHeight
						},
						scrollertxt : function(b, g) {
							var e = {
								boxwidth : "auto",
								wait : 8E3,
								speed : 2E3,
								data : ""
							};
							if (g != null)
								defopt = a.extend(e, g);
							g = "scollertxt" + (new Date).getTime();
							var h = "";
							a(e.data).each(
									function() {
										h += "<li style='width:" + e.boxwidth
												+ "px'><a href='" + this.url
												+ "' target='_blank'>"
												+ this.title + "</a></li>"
									});
							var j = a(b);
							b = a("<ul style='width:" + e.boxwidth * 3
									+ "px' id='" + g + "' >" + h + "</ul>");
							var k = a(
									"<div style='overflow:hidden;width:"
											+ e.boxwidth + "px'></div>")
									.append(b);
							j.append(k);
							k = a("<a href='#' class='scollleft' data='" + g
									+ "' id='btn_l_" + g + "' ></a>");
							var l = a("<a href='#' class='scollright' data='"
									+ g + "'></a>");
							j.append(k).append(l);
							b.data("state", "0");
							a(e.data).length > 1
									&& b
											.attr("timer", setInterval(
													"$.famsg.scrollerLeft('"
															+ g + "',"
															+ e.speed + ",-"
															+ e.boxwidth + ")",
													e.wait));
							j
									.find("li")
									.mouseover(
											function() {
												clearInterval(a(this).parent()
														.attr("timer"))
											})
									.mouseout(
											function() {
												a(this).parent().find("li")
														.css("margin-left",
																"0px");
												a(this)
														.parent()
														.attr(
																"timer",
																setInterval(
																		"$.famsg.scrollerLeft('"
																				+ a(
																						this)
																						.parent()
																						.attr(
																								"id")
																				+ "',"
																				+ e.speed
																				+ ",-"
																				+ e.boxwidth
																				+ ")",
																		e.wait))
											});
							k.click(
									function() {
										var m = null;
										m = a(this).attr("data");
										if (a("#" + m).data("state") == "0") {
											a("#" + m).find("li").css(
													"margin-left", "0px");
											clearInterval(a("#" + m).attr(
													"timer"));
											a("#" + m).attr("timer", null);
											a.famsg.scrollerLeft(m, 500, "-"
													+ e.boxwidth)
										}
									}).mouseover(function() {
								var m = a(this).attr("data");
								clearInterval(a("#" + m).attr("timer"))
							}).mouseout(
									function() {
										var m = a(this).attr("data");
										a("#" + m).find("li").css(
												"margin-left", "0px");
										a("#" + m).attr(
												"timer",
												setInterval(
														"$.famsg.scrollerLeft('"
																+ m + "',"
																+ e.speed
																+ ",-"
																+ e.boxwidth
																+ ")", e.wait))
									});
							l.click(
									function() {
										var m = null;
										m = a(this).attr("data");
										if (a("#" + m).data("state") == "0") {
											a("#" + m).find("li").css(
													"margin-left", "0px");
											clearInterval(a("#" + m).attr(
													"timer"));
											a("#" + m).attr("timer", null);
											a.famsg.scrollerRight(m, 500, "-"
													+ e.boxwidth)
										}
									}).mouseover(function() {
								var m = a(this).attr("data");
								clearInterval(a("#" + m).attr("timer"))
							}).mouseout(
									function() {
										var m = a(this).attr("data");
										a("#" + m).find("li").css(
												"margin-left", "0px");
										a("#" + m).attr(
												"timer",
												setInterval(
														"$.famsg.scrollerLeft('"
																+ m + "',"
																+ e.speed
																+ ",-"
																+ e.boxwidth
																+ ")", e.wait))
									})
						},
						scrollerLeft : function(b, g, e) {
							var h = a("#" + b), j = null;
							j = h.find("li:first");
							if (h.data("state") == "0") {
								h.data("state", "1");
								j.animate({
									marginLeft : e
								}, g, function() {
									j.css("margin-left", "0px");
									h.append(j);
									h.find("li").css("margin-left", "0px");
									h.data("state", "0")
								})
							}
						},
						scrollerRight : function(b, g, e) {
							var h = a("#" + b), j = h.find("li:last");
							j.css("margin-left", e);
							h.prepend(j);
							if (h.data("state") == "0") {
								h.data("state", "1");
								j.animate({
									marginLeft : "0px"
								}, g, function() {
									h.find("li").css("margin-left", "0px");
									h.data("state", "0")
								})
							}
						},
						lunbo : function(b, g) {
							var e = {
								width : 520,
								height : 260,
								interval : 3E3,
								type : 1
							};
							if (g != null)
								e = a.extend(e, g);
							g = e.data;
							for (var h = g.length, j = (e.width - h) / h, k = a("<ul class='pic-list'></ul>"), l = a("<ul class='num'></ul>"), m = 0; m < h; m++) {
								var p = g[m], w = p.link != "#" ? a("<li index='"
										+ m
										+ "'><a href='"
										+ p.link
										+ "' target='_blank'><img src='"
										+ p.img
										+ "' name='lunboimg' style='width:"
										+ e.width
										+ "px; height:"
										+ e.height
										+ "px;' /></a></li>")
										: a("<li index='"
												+ m
												+ "'><img src='"
												+ p.img
												+ "' name='lunboimg' style='width:"
												+ e.width + "px; height:"
												+ e.height + "px;' /></li>");
								p = e.type == 1 && p.link != "#" ? a("<li index='"
										+ m
										+ "' style='width:"
										+ j
										+ "px;'><a href='"
										+ p.link
										+ "' target='_blank'>"
										+ p.title
										+ "</a><span></span></li>")
										: a("<li index='"
												+ m
												+ "' style='width:"
												+ j
												+ "px;'><a href='javascript:void(0);' >"
												+ p.title
												+ "</a><span></span></li>");
								k.append(w);
								l.append(p)
							}
							var F;
							F = b.indexOf("#") == 0 ? a(b) : a("#" + b);
							F.append(a(
									"<div class='adlunbo_style_" + e.type
											+ "' style='width:" + e.width
											+ "px; height:" + e.height
											+ "px;'></div>").append(l)
									.append(k));
							l.find("li").eq(0).addClass("current");
							F.data("curr", 0);
							F.data("auto", 1);
							var n = function(q) {
								var r = l.find("li").eq(q);
								if (!a(r).hasClass("current")) {
									F.data("curr", q);
									k.find("li").hide();
									k.find("li").eq(q).fadeIn();
									l.find("li").show().removeClass("current");
									a(r).addClass("current")
								}
							};
							l.find("li").mouseover(function() {
								F.data("auto", "0");
								var q = a(this).attr("index");
								n(q)
							}).mouseout(function() {
								F.data("auto", "1")
							});
							k.find("li").mouseover(function() {
								F.data("auto", "0")
							}).mouseout(function() {
								F.data("auto", "1")
							});
							setInterval(function() {
								if (parseInt(F.data("auto")) == 1) {
									var q = F.data("curr");
									q = parseInt(q) + 1;
									if (q >= h)
										q = 0;
									n(q)
								}
							}, e.interval)
						},
						loadKjImg : function(b) {
							var g = b.val().match(/src="([\s\S]*?)"/i)[1], e = b[0].parentNode, h = document.write, j = document
									.createElement("script"), k = document.head
									|| document.getElementsByTagName("head")[0]
									|| document.documentElement;
							document.write = function(l) {
								e.innerHTML = l
							};
							j.type = "text/javascript";
							j.src = g;
							j.onerror = j.onload = j.onreadystatechange = function(
									l) {
								l = l || window.event;
								if (!j.readyState
										|| /loaded|complete/.test(j.readyState)
										|| l === "error") {
									j.onerror = j.onload = j.onreadystatechange = null;
									document.write = h;
									k.removeChild(j);
									k = j = b = e = null;
									a.famsg.loadDequeue()
								}
							};
							k.insertBefore(j, k.firstChild)
						},
						lunbolazy : function(b, g) {
							var e = {
								interval : 4E3
							};
							if (g != null)
								e = a.extend(e, g);
							var h = a(b), j = h.find("li").length;
							c = a("<ul class='num'></ul>");
							for (b = 0; b < j; b++) {
								g = "";
								if (b == 0)
									g = " class='current' ";
								d = a("<li style='width: 129px;' index='"
										+ b
										+ "' "
										+ g
										+ "><a  href='javascript:;' target='_self'>"
										+ (b + 1) + "</a><span></span></li>");
								d.mouseover(function() {
									c.find("li").removeClass("current");
									a(this).addClass("current");
									h.find("li").hide();
									var l = h.find("li").eq(
											a(this).attr("index")), m = a(l)
											.find("textarea");
									m.length && function(p) {
										a.famsg.loadQueue(function() {
											a.famsg.loadKjImg(p)
										})
									}(m);
									a(l).fadeIn();
									h.data("auto", "0");
									h.data("curr", a(this).attr("index"))
								});
								c.append(d)
							}
							h.before(c);
							h.data("auto", 1);
							h.data("curr", 0);
							h.find("li").mouseover(function() {
								h.data("auto", "0")
							}).mouseout(function() {
								h.data("auto", "1")
							});
							setInterval(function() {
								if (parseInt(h.data("auto")) == 1) {
									var l = h.data("curr");
									l = parseInt(l) + 1;
									if (l >= j)
										l = 0;
									k(l)
								}
							}, e.interval);
							var k = function(l) {
								var m = c.find("li").eq(l);
								if (!a(m).hasClass("current")) {
									c.find("li").removeClass("current");
									a(m).addClass("current");
									h.data("curr", l);
									h.find("li").hide();
									l = h.find("li").eq(l);
									var p = a(l).find("textarea");
									p.length && a.famsg.loadQueue(function() {
										a.famsg.loadKjImg(p)
									});
									a(l).fadeIn()
								}
							}
						},
						floatdiv : function(b, g) {
							var e = {
								offset : 480,
								top : 100
							};
							if (g != null)
								e = a.extend(e, g);
							g = e.offset;
							if (g < 0)
								g -= a(b).width();
							if (a.browser.msie && a.browser.version == "6.0") {
								a("body").css({
									"background-image" : "url(about:blank)",
									"background-attachment" : "fixed"
								});
								a(b)
										.attr(
												"style",
												"position:absolute;  left:50%; top:expression(eval(document.documentElement.scrollTop + "
														+ e.top
														+ ")); margin-left:"
														+ g + "px;")
							} else
								a(b).css({
									position : "fixed",
									left : "50%",
									top : e.top + "px",
									"margin-left" : g + "px"
								})
						},
						showFlotHistory : function(b) {
							var g = {
								signupurl : "https://passport.5173.com/User/Register",
								loginurl : "https://passport.5173.com/?returnUrl=http://www.5173.com",
								msgurl : "http://message.5173.com/MyInfo/SiteMessageList.aspx?TagType=0",
								sound : 0,
								msgcount : 0,
								signup : 0
							};
							if (b != null)
								g = a.extend(g, b);
							b = a("<div id='browsehistory' class='browse_history'></div>");
							var e = [];
							e.push("<div class='login_state'>");
							g.signup == 0 ? e.push("<ul><li><a href='"
									+ g.signupurl
									+ "'>\u6ce8\u518c</a></li> <li><a href='"
									+ g.loginurl
									+ "'>\u767b\u5f55</a></li> </ul>")
									: e
											.push(" <a href='"
													+ g.msgurl
													+ "' class='messages_info'>\u672a\u8bfb\u7ad9\u5185\u4fe1<span class='no'>"
													+ g.msgcount
													+ "</span></a>");
							e.push(" </div>");
							if ((g = g.visited) && g.length > 0) {
								e
										.push("<div class='late_browse'><h4>\u6700\u8fd1\u6d4f\u89c8</h4><ul>");
								for (var h = 0; h < g.length; h++) {
									var j = g[h];
									e.push("<li><span class='name'><a href='"
											+ j.title_a + "' title='" + j.title
											+ "'>" + j.title + "</a></span>");
									e.push("  <span class='info'><a href='"
											+ j.type_a + "' title='" + j.type
											+ "'>" + j.type + "</a></span>");
									e.push("  <span class='price'><a href='"
											+ j.price_a + "' title='"
											+ unescape(j.price_til) + "'>"
											+ j.price + "</a></span></li>")
								}
								e.push("</ul><b class='bottom'></b></div>")
							}
							e
									.push("<div class='quick_buy'><h4>\u5feb\u901f\u8d2d\u4e70</h4><ul>");
							e
									.push("<li class='card'><a onclick=\"__utmTrackEvent('%e9%a6%96%e9%a1%b5%e7%82%b9%e5%87%bb','%e5%bf%ab%e9%80%9f%e8%b4%ad%e4%b9%b0','%e7%82%b9%e5%8d%a1');\"    href='http://dkjy.5173.com/BuyIndex.aspx' target='_blank'>\u70b9\u5361</a></li>");
							e
									.push("<li class='phone'><a  onclick=\"__utmTrackEvent('%e9%a6%96%e9%a1%b5%e7%82%b9%e5%87%bb','%e5%bf%ab%e9%80%9f%e8%b4%ad%e4%b9%b0','%e6%89%8b%e6%9c%ba');\"  href='http://chong.5173.com/' target='_blank'>\u624b\u673a</a></li>");
							e
									.push("<li class='qq'><a onclick=\"__utmTrackEvent('%e9%a6%96%e9%a1%b5%e7%82%b9%e5%87%bb','%e5%bf%ab%e9%80%9f%e8%b4%ad%e4%b9%b0','QQ');\"  href='http://trading.5173.com/search/0725c34d0d424b8898465c86a28f6bac.shtml?ga=&gs=' target='_blank'>QQ\u589e\u503c</a></li>");
							e
									.push("<li class='gamegold'><a href='http://dkhg.5173.com/' target='_blank' onclick=\"__utmTrackEvent('%E9%A6%96%E9%A1%B5%E7%82%B9%E5%87%BB','%E5%BF%AB%E9%80%9F%E8%B4%AD%E4%B9%B0','%E5%8D%A1%E6%8D%A2%E5%B8%81');\">\u5361\u6362\u5e01</a></li>");
							e
									.push("<li class='xy'><a onclick=\"__utmTrackEvent('%e9%a6%96%e9%a1%b5%e7%82%b9%e5%87%bb','%e5%bf%ab%e9%80%9f%e8%b4%ad%e4%b9%b0','%e8%bf%85%e6%b8%b8');\"  href='http://tool.5173.com/xunyou/index.aspx' target='_blank'>\u52a0\u901f\u5668</a></li>");
							e
									.push("<li class='jdyou'><a href='http://tool.5173.com/jdyou/index.aspx' target='_blank'>\u7f51\u6e38\u5de5\u5177</a></li>");
							e
									.push("<li class='qp_trad'><a href='http://zzjy.5173.com/' target='_blank'>\u68cb\u724c\u6e38\u620f</a></li>");
							e.push("</ul><b class='bottom'></b></div>");
							var k = a(" <a href='javascript:void(0)' class='browse_gotop' style='display:none;'></a>");
							b.html(e.join("")).append(k).appendTo(a("body"));
							a.famsg.floatdiv(b, {
								top : 100,
								offset : 480
							});
							k.click(function() {
								a(window).scrollTop(0)
							});
							a(window).scroll(function() {
								a(window).scrollTop() > 0 ? k.show() : k.hide()
							})
						},
						popright : function(b) {
							var g = {
								delay : 5E3,
								zindex : 1E3
							};
							if (b != null)
								g = a.extend(g, b);
							a.browser.msie
									&& a.browser.version == "6.0"
									&& a("body")
											.css(
													{
														"background-image" : "url(about:blank)",
														"background-attachment" : "fixed"
													});
							var e = a("<div  class='popr-large' style='z-index:"
									+ g.zindex + "'></div>"), h = a("<div  class='popr-small' style='z-index:"
									+ (g.zindex + 1) + "'></div>");
							b = a("<a href='"
									+ g.big.link
									+ "' class='imgborder' target='_blank'><img src='"
									+ g.big.img + "' /></a>");
							var j = a(
									"<a href='javascript:void(0);' target='_self' class='close'>\u00d7</a>")
									.click(function() {
										e.hide();
										h.show()
									});
							e.append(b).append(j).appendTo(a("body"));
							b = a("<a href='"
									+ g.small.link
									+ "' class='imgborder'  target='_blank'><img src='"
									+ g.small.img + "' /></a>");
							var k = a(
									"<a href='javascript:void(0);' target='_self' class='close'>\u5173\u95ed</a>")
									.click(function() {
										h.hide()
									}), l = a(
									"<a href='javascript:void(0);' target='_self' class='replay'>\u91cd\u64ad</a>")
									.click(function() {
										h.hide();
										e.show()
									});
							h.append(b).append(k).append(l).appendTo(a("body"));
							if (a.browser.msie && a.browser.version == "6.0") {
								b = e.offset().top;
								e.attr("style",
										" top:expression(eval(document.documentElement.scrollTop + "
												+ b + ")); ");
								b = h.offset().top;
								h.attr("style",
										" top:expression(eval(document.documentElement.scrollTop + "
												+ b + ")); ")
							}
							h.hide();
							setTimeout(function() {
								j.click()
							}, g.delay)
						}
					})
})(jQuery);
(function(a) {
	a.fn.fedPlaceHoder = function(f) {
		var d = a(this), c = d.attr("placeholder");
		d.data("focusw", c);
		if (d.attr("type") == "password") {
			var b = a("<input type='text' value='" + c
					+ "' class='fed_input' />");
			d.val("").after(b).hide();
			b.focus().addClass(f);
			b.focus(function() {
				d.removeAttr("placeholder").show().focus();
				d.val() == c && d.val("");
				a(this).hide()
			}).click(function() {
				d.removeAttr("placeholder").show().focus();
				d.val() == c && d.val("");
				a(this).hide()
			}).change(function() {
				d.val(b.val())
			});
			d.focusout(function() {
				if (d.val() == "" || d.val() == c) {
					a(this).hide();
					b.show()
				}
				d.attr("placeholder", d.data("focusw"))
			})
		} else {
			if (d.val() == "" || d.val() == c)
				d.val(c).addClass(f);
			d.focus(function() {
				d.val() == c && d.val("");
				d.removeClass(f).removeAttr("placeholder")
			}).blur(function() {
				d.val() == "" && d.val(c).addClass(f);
				d.attr("placeholder", d.data("focusw"))
			})
		}
		return this
	};
	a.fn.fedLenListen = function(f) {
		var d = a(this);
		d.bind("keyup change mousedown blur", function() {
			var c = d.val();
			if (c == d.attr("placeholder"))
				c = "";
			c = c.replace(/\s/g, "x").replace(/[^x00-xff]/g, "xx");
			c = Math.ceil(c.length / 2);
			f(c)
		});
		return this
	};
	a.futil = a.futil || {};
	a
			.extend(
					a.futil,
					{
						fixed : function(f, d) {
							var c = {
								position : 4,
								xspace : 20,
								yspace : 50
							};
							if (d != null)
								c = a.extend(c, d);
							c.def == false && a(f).hide();
							if (a.browser.msie && a.browser.version == "6.0") {
								a("body").css({
									"background-image" : "url(about:blank)",
									"background-attachment" : "fixed"
								});
								var b;
								switch (parseInt(c.position)) {
								case 1:
									b = function() {
										a(f)
												.attr(
														"style",
														"position:absolute;  left:"
																+ c.xspace
																+ "px; top:expression(eval(document.documentElement.scrollTop + "
																+ c.yspace
																+ ")); ")
									};
									break;
								case 2:
									b = function() {
										a(f)
												.attr(
														"style",
														"position:absolute;  right:"
																+ c.xspace
																+ "px; top:expression(eval(document.documentElement.scrollTop + "
																+ c.yspace
																+ ")); ")
									};
									break;
								case 3:
									b = function() {
										var g = a(window).height() - c.yspace
												- a(f).height();
										a(f)
												.attr(
														"style",
														"position:absolute;  left:"
																+ c.xspace
																+ "px; top:expression(eval(document.documentElement.scrollTop + "
																+ g + ")); ")
									};
									break;
								case 4:
									b = function() {
										var g = a(window).height() - c.yspace
												- a(f).height();
										a(f)
												.attr(
														"style",
														"position:absolute;  right:"
																+ c.xspace
																+ "px; top:expression(eval(document.documentElement.scrollTop + "
																+ g + ")); ")
									};
									break
								}
								b();
								a(window).resize(function() {
									b()
								})
							} else
								switch (parseInt(c.position)) {
								case 1:
									a(f).css({
										position : "fixed",
										left : c.xspace + "px",
										top : c.yspace + "px"
									});
									break;
								case 2:
									a(f).css({
										position : "fixed",
										right : c.xspace + "px",
										top : c.yspace + "px"
									});
									break;
								case 3:
									a(f).css({
										position : "fixed",
										left : c.xspace + "px",
										bottom : c.yspace + "px"
									});
									break;
								case 4:
									a(f).css({
										position : "fixed",
										right : c.xspace + "px",
										bottom : c.yspace + "px"
									});
									break;
								default:
									a(f).css({
										position : "fixed",
										right : c.xspace + "px",
										bottom : c.yspace + "px"
									});
									break
								}
						},
						appendGoTop : function() {
							var f = a('<div class="popup_right" ><div id="upTop" class="up_top"><a href="javascript:void(0);">\u8fd4\u56de\u9876\u90e8</a></div></div>');
							a("body").append(f.hide());
							a.futil.fixed(f, {
								position : "4",
								xspace : 50,
								yspace : 50
							});
							f.hide();
							f.click(function() {
								a(window).scrollTop(0)
							});
							a(window).scroll(function() {
								a(window).scrollTop() > 0 ? f.show() : f.hide()
							})
						},
						bindStar : function(f) {
							f = f.split(",");
							for (var d = 0; d < f.length; d++) {
								var c = f[d], b = a(c).val() || 0, g = a(c)
										.parent().next().find("span");
								if (!g.attr("value") || !g.attr("msg")) {
									console
											.error("Input -> "
													+ c
													+ " \u9700\u8981\u540c\u65f6\u6dfb\u52a0value,msg\u5c5e\u6027\uff01");
									break
								}
								var e = function(h, j) {
									var k = a(h).parent().find("span");
									a(k).each(
											function(l) {
												l < j ? a(k).eq(l).addClass(
														"starcurrent") : a(k)
														.eq(l).removeClass(
																"starcurrent")
											})
								};
								e(g, b);
								a(g)
										.mouseover(
												function() {
													var h = a(this).attr(
															"value");
													e(this, h);
													var j = a(this).attr("msg")
															.split("-");
													if (j.length > 1) {
														a("#fed_star_pecent")
																.empty()
																.html(
																		h
																				+ "\u5206&emsp;"
																				+ j[0]);
														a("#fed_star_msg")
																.empty().html(
																		j[1])
													} else {
														a("#fed_star_pecent")
																.empty()
																.html(
																		h
																				+ "\u5206");
														a("#fed_star_msg")
																.empty().html(
																		j[0])
													}
													a("#fed_star_tips")
															.fedFollow(this, {
																left : -20,
																top : 10
															})
												});
								a(g).mouseout(
										function() {
											a("#fed_star_tips").hide();
											var h = a(this).parent().prev()
													.find("input").val();
											e(this, h)
										});
								a(g).click(
										function() {
											var h = a(this).attr("value");
											a(this).parent().prev().find(
													"input").val(h)
										})
							}
						},
						arrayToJson : function(f) {
							var d = [];
							if (typeof f == "string")
								return '"'
										+ f.replace(/([\'\"\\])/g, "\\$1")
												.replace(/(\n)/g, "\\n")
												.replace(/(\r)/g, "\\r")
												.replace(/(\t)/g, "\\t") + '"';
							if (typeof f == "object") {
								if (f.sort) {
									for (c = 0; c < f.length; c++)
										d.push(arrayToJson(f[c]));
									d = "[" + d.join() + "]"
								} else {
									for ( var c in f)
										d.push(c + ":" + arrayToJson(f[c]));
									document.all
											&& !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/
													.test(f.toString)
											&& d.push("toString:"
													+ f.toString.toString());
									d = "{" + d.join() + "}"
								}
								return d
							}
							return f.toString()
						},
						jsonToString : function(f) {
							var d = [], c = function(g) {
								if (typeof g == "object" && g != null)
									return json2str(g);
								return /^(string|number)$/.test(typeof g) ? '"'
										+ g + '"' : g
							};
							for ( var b in f)
								d.push('"' + b + '":' + c(f[b]));
							return "{" + d.join(",") + "}"
						}
					})
})(jQuery);
(function(a) {
	a.fed = a.fed || {};
	a
			.extend(
					a.fed,
					{
						roll : function(f, d) {
							var c = {
								data : [],
								num : 4,
								step : 1
							};
							if (d != null)
								c = a.extend(c, d);
							var b = a("<ul class='fed-roll-ul clearfix'></ul>");
							a(c.data).each(
									function(e) {
										b
												.append("<li style='width:"
														+ c.width
														+ "px; margin-right:"
														+ c.space + "px;'>"
														+ c.data[e] + "</li>")
									});
							var g = parseInt(c.width) + parseInt(c.space);
							b.css("width", g * c.data.length + 50 + "px");
							d = "";
							if (c.data.length <= c.num)
								d = "class='disable'";
							$toRightBtn = a("<a href='javascript:void(0);' class='fed-roll-toright'><span "
									+ d + "></span></a>");
							$toLeftBtn = a("<a href='javascript:void(0);' class='fed-roll-toleft'><span class='disable'></span></a>");
							rollwidth = g * parseInt(c.num);
							$frame = a(
									"<div class='fed-roll-frame' style='width:"
											+ rollwidth + "px' ></div>")
									.append(b);
							$box = a("<div  class='fed-roll' ></div>").append(
									$toLeftBtn).append($frame).append(
									$toRightBtn);
							a(f).append($box);
							a(f).find("img").attr("name", "scollimg");
							a(f).data("currIndex", 0);
							$toRightBtn
									.click(function() {
										var e = a(f).data("currIndex");
										if (e < c.data.length - c.num) {
											e += c.step;
											a(f).data("currIndex", e);
											a(f + " .fed-roll-toleft > span")
													.removeClass("disable");
											b
													.animate(
															{
																left : "-="
																		+ g
																		* c.step
																		+ "px"
															},
															"slow",
															function() {
																e >= c.data.length
																		- c.num
																		&& a(
																				f
																						+ "  .fed-roll-toright > span")
																				.addClass(
																						"disable")
															})
										}
									});
							$toLeftBtn
									.click(function() {
										var e = a(f).data("currIndex");
										if (e > 0) {
											e -= c.step;
											a(f).data("currIndex", e);
											a(f + " .fed-roll-toright > span")
													.removeClass("disable");
											b
													.animate(
															{
																left : "+="
																		+ g
																		* c.step
																		+ "px"
															},
															"slow",
															function() {
																e <= 0
																		&& a(
																				f
																						+ "  .fed-roll-toleft > span")
																				.addClass(
																						"disable")
															})
										}
									})
						}
					})
})(jQuery);
$.fn.fedautocomplete = function() {
};
$.fn.fedfocustip = function(a) {
	var f = this;
	this.val() == "" && f.val(a);
	f.click(function() {
		f.val() == a && f.val("")
	}).blur(function() {
		f.val() == "" && f.val(a)
	});
	return this
};
(function(a) {
	a.fed = a.fed || {};
	a.fed.util = a.fed.util || {};
	a
			.extend(
					a.fed.util,
					{
						autocomplete : function(f, d, c) {
							var b = a(f), g = {
								delay : 300
							};
							if (d != null)
								g = a.extend(g, d);
							b.data("currIndex", -1);
							b.attr("autocomplete", "off");
							b.sugg = function() {
								var e = b.val();
								if (a.trim(e).length > 0) {
									var h = "&keyword=" + escape(e) + "&game="
											+ a("#hide_game").val() + "&area="
											+ a("#hide_area").val()
											+ "&server="
											+ a("#hide_server").val()
											+ "&type=" + a("#hide_type").val();
									a
											.ajax({
												url : "http://trading.5173.com/browse/KeywordSearch.aspx?action=search&count=10"
														+ h,
												type : "GET",
												dataType : "jsonp",
												success : function(j) {
													if (j.length > 0) {
														j = {
															keywords : a
																	.trim(e),
															data : j
														};
														b.showSugg(j);
														b.data("currIndex",
																"-1")
													} else {
														j = b.attr("id")
																+ "_sug";
														a("#" + j).hide()
													}
												},
												jsonp : "jsoncallback",
												scriptCharset : "GBK",
												jsonpCallback : "suggest",
												cache : true
											})
								} else {
									h = b.attr("id") + "_sug";
									a("#" + h).hide()
								}
							};
							b.showSugg = function(e) {
								var h = b.attr("id") + "_sug", j = a("#" + h);
								if (!j || j.length <= 0) {
									j = a("<dl id='" + h
											+ "' class='FED_suggest' ></dl>");
									b.after(j)
								} else
									j.empty();
								h = e.data;
								if (h.length > 0) {
									e = e.keywords.split(" ");
									var k = a.parseJSON(a
											.cookie("searchbar_cookie")), l = [], m = "";
									for ( var p in k)
										~k[p][0].indexOf(e) && l.push(k[p]);
									if (l.length > 0) {
										l = l.reverse();
										l.length > 3 && (l.length = 3);
										a(j)
												.append(
														"<dt>\u60a8\u6700\u8fd1\u641c\u7d22\u7684\u662f:</dt>");
										for (p = 0; p < l.length; p++) {
											for (k = 0; k < e.length; k++)
												if (e[k].length > 0)
													m = l[p][0].replace(e[k],
															"<b>" + e[k]
																	+ "</b>");
											a(
													'<dd class="his"><a href="http://s.5173.com/'
															+ l[p][1]
															+ '&uh=1">' + m
															+ "</a></dd>")
													.appendTo(a(j))
										}
										a(j).append('<dd class="line"></dd>');
										a(j).find(".his").each(function() {
											a(this).mouseover(function() {
												b.val(a(this).text());
												a("#hide_game").val()
											})
										})
									}
									for (l = 0; l < h.length; l++) {
										m = h[l];
										p = m.title;
										for (k = 0; k < e.length; k++)
											if (e[k].length > 0)
												p = p.replace(e[k], "<b>"
														+ e[k] + "</b>");
										m = a("<dd >" + p + "</dd>").data(
												"item", m);
										m.mouseenter(function() {
											b.data("currItem", a(this).data(
													"item"));
											b.val(a(this).data("item").title)
										});
										m.click(function() {
											c(a(this).data("item"))
										});
										j.append(m)
									}
									a(j).find("dd").bind(
											"mouseover",
											function() {
												a(j).find("dd").removeClass(
														"current");
												a(this).addClass("current")
											});
									a(j).show();
									return j
								} else
									return null
							};
							b.keyup(function(e) {
								var h = b.attr("id") + "_sug";
								h = a("#" + h);
								switch (e.keyCode) {
								case 38:
									if (h != null && h.length > 0) {
										e = h.find("dd");
										h = e.length;
										var j = b.data("currIndex");
										e.removeClass("current");
										h = Math.max(parseInt(j) - 1, 0);
										e = a(e[parseInt(h)]);
										e.addClass("current");
										b.val(e.data("item").title);
										b.data("currIndex", h);
										b.data("currItem", e.data("item"))
									}
									break;
								case 40:
									if (h != null && h.length > 0) {
										e = h.find("dd");
										h = e.length;
										j = b.data("currIndex");
										e.removeClass("current");
										h = Math.min(parseInt(j) + 1, h - 1);
										e = a(e[parseInt(h)]);
										e.addClass("current");
										b.val(e.data("item").title);
										b.data("currIndex", h);
										b.data("currItem", e.data("item"))
									}
									break;
								case 27:
									h != null && h.remove();
									break;
								default:
									(e = b.data("timer")) && clearTimeout(e);
									e = setTimeout(function() {
										b.sugg()
									}, g.delay);
									b.data("timer", e);
									break
								}
							});
							a(document).click(
									function(e) {
										var h = b.attr("id") + "_sug";
										h = a("#" + h);
										e.target != b && e.target != h
												&& e.target != a("#myform")
												&& h.hide()
									})
						}
					})
})(jQuery);
(function(a) {
	a.fed = a.fed || {};
	a.extend(a.fed, {
		tabs : function(f, d) {
			var c = {
				event : "click",
				currindex : 0,
				onSuccess : function() {
				},
				lazy : 100,
				effect : 1
			};
			c = a.extend(c, d);
			var b = a(f).children("div[name='tabhead']").find("ul").find("li");
			if (b.length == 0)
				b = a(f + " > ul > li");
			var g = a(f).children("div[name='tabpanel']");
			if (g.length == 0)
				g = a(f + " > div[name!='tabhead']");
			b.eq(c.currindex).addClass("current");
			g.hide().eq(c.currindex).show();
			b.data("curr", c.currindex);
			d = b.eq(c.currindex).find("a");
			var e = d.attr("url"), h = d.attr("id") ? d.attr("id") : d
					.attr("href");
			e && a.trim(a(h).html()) == "" && a.get(e, {}, function(l) {
				a(h).html(l);
				c.onSuccess(h)
			});
			b.bind(c.event, function() {
				var l = a(this), m = function() {
					b.removeClass("current");
					l.addClass("current");
					var p = l.find("a").attr("id") ? l.find("a").attr("id") : l
							.find("a").attr("href");
					if (a.browser.msie)
						p = "#" + p.split("#")[1];
					g.hide();
					a(p).show();
					var w = l.find("a").attr("url");
					if (w && a.trim(a(p).html()) == "") {
						a(p).html("loding...");
						a.get(w, {}, function(F) {
							a(p).html(F);
							c.onSuccess(p)
						})
					} else
						c.onSuccess(p)
				};
				if (c.event == "click")
					m();
				else
					c.lazy && c.lazy > 0 ? b.data("tabtimer", setTimeout(
							function() {
								m()
							}, c.lazy)) : m();
				return false
			});
			c.event == "mouseover" && b.bind("mouseout", function() {
				clearTimeout(b.data("tabtimer"))
			});
			b.bind("click", function() {
				if (a(this).find("a").attr("href").substr(0, 1) == "#")
					return false
			});
			if (c.interval && c.interval > 0) {
				a(f).data("auto", "1");
				a(f).hover(function() {
					a(f).data("auto", "0")
				}, function() {
					a(f).data("auto", "1")
				});
				var j = b.length, k = function(l) {
					b.removeClass("current");
					b.eq(l).addClass("current");
					c.effect == 1 ? g.hide().eq(l).fadeIn() : g.hide().eq(l)
							.show();
					b.data("curr", l);
					l = b.eq(l).find("a");
					l = l.attr("id") ? l.attr("id") : l.attr("href");
					c.onSuccess(l)
				};
				setInterval(function() {
					if (parseInt(a(f).data("auto")) == 1 && b.length > 1) {
						var l = b.data("curr");
						l = parseInt(l) + 1;
						if (l >= j)
							l = 0;
						k(l)
					}
				}, c.interval)
			}
		}
	})
})(jQuery);
(function(a) {
	a
			.extend({
				LAYER : {
					openID : "",
					position : "",
					mt : "",
					parentIsLoad : true,
					init : function(f) {
						if (f == undefined)
							f = "UED_box";
						if (this.parentIsLoad) {
							a('<div id="' + f + '"></div>').appendTo("body");
							a(
									'<div class="UED_SHUCOVER_V1 UED_hide" id="UED_SHUCOVER_V1"><iframe class="UED_SHUCOVER_IFRAME_V1" id="UED_SHUCOVER_IFRAME_V1" src="about:blank"></iframe></div>')
									.appendTo("body");
							this.parentIsLoad = false
						}
					},
					show : function(f) {
						var d = {
							overlay : {
								color : "#000",
								opacity : 0.5
							},
							position : "fixed",
							mt : "200px",
							layerContainer : "UED_LAYER_PARENT_FRAME_V1"
						};
						d = a.extend(d, f);
						if (!document.getElementById(d.id)) {
							alert("\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd\ufffd: \u04b3\ufffd\ufffd\ufffd\ufffd\u00fb\ufffd\u0437\ufffd\ufffd\ufffdid="
									+ d.id);
							return false
						}
						this.init(d.layerContainer);
						this.position = d.position;
						this.mt = d.mt;
						this.openID = f.id;
						this.setpos(a("#" + this.openID));
						this.is6FIX("100%");
						a("#" + this.openID).prependTo(
								a("#" + d.layerContainer));
						a("#" + this.openID).show();
						a("#UED_SHUCOVER_V1").css({
							"background-color" : d.overlay.color,
							opacity : d.overlay.opacity
						}).show()
					},
					setpos : function(f) {
						f.addClass("UED_LAYER_PARENT_V1");
						var d = f.height(), c = f.width();
						f.css({
							"margin-left" : c / 2 * -1 + "px",
							"margin-top" : d / 2 * -1 + "px"
						});
						if (d > (document.body.clientHeight == 0 ? document.body.clientHeight
								: document.documentElement.clientHeight)
								|| this.position === "absolute")
							f.css({
								top : this.mt,
								position : "absolute",
								marginTop : "0"
							})
					},
					close : function() {
						a("#" + this.openID).hide();
						a("#" + this.openID).removeClass("UED_LAYER_PARENT_V1");
						a("#UED_SHUCOVER_V1").hide();
						this.is6FIX("auto")
					},
					is6FIX : function(f) {
						if (a.browser.msie && a.browser.version == "6.0") {
							a("html").css({
								height : f
							});
							a("body").css({
								height : f
							})
						}
					}
				}
			})
})(jQuery);
(function(a) {
	/^(1.3|1.2|1.1|1.0)/.test(a.prototype.jquery)
			&& alert("\u60a8\u4f7f\u7528\u7684JQ\u7248\u672c"
					+ a.prototype.jquery
					+ "\u8fc7\u4f4e\uff0c\u8bf7\u4f7f\u7528JQ1.4\u53ca\u4ee5\u4e0a\u7248\u672c");
	a.fn.initTipBox = function(c) {
		if (!c) {
			c = a(this).attr("id") + "_tip";
			var b = a("#" + c);
			if (b.length == 0) {
				b = a('<div class="fed_show_msg"  id="' + c + '" ></div>');
				a(this).parent().after(b)
			}
		}
		this.attr("tipId", c)
	};
	a.fn.showTip = function(c, b) {
		b = b || "";
		a("#" + a(this).attr("tipId")).html(
				'<span class="fed_formtips_' + c + '" ><s class="ico_' + c
						+ '_1"></s>' + b + "</span>");
		c == "error" ? a(this).data("valid", false) : a(this).data("valid",
				true)
	};
	a.fn.resetTip = function(c, b) {
		c || (c = this.attr("tipId"));
		c || (c = a(this).attr("id") + "_tip");
		if (c) {
			c = a("#" + c);
			if (c.length > 0) {
				b ? c.html(b) : c.html("");
				a(this).data("valid", true)
			}
		}
	};
	a.fn.checkAll = function() {
		var c = true, b = a(this).find("input"), g = a(this).find("select"), e = [];
		b.each(function(h) {
			$input = a(b[h]);
			e.push($input)
		});
		g.each(function(h) {
			$select = a(g[h]);
			e.push($select)
		});
		a(e).each(function(h) {
			$singleDom = a(e[h]);
			try {
				var j = $singleDom.attr("type");
				if (j != "submit" && j != "button") {
					$singleDom.blur();
					if ($singleDom.data("valid") == false)
						c = false
				}
			} catch (k) {
				c = false
			}
		});
		return c
	};
	a.validate = a.validate || {};
	a
			.extend(
					a.validate,
					{
						require : function(c, b) {
							if (a.trim(b).length == 0)
								throw "\u8bf7\u8f93\u5165" + c + "\u3002";
						},
						minlength : function(c, b, g) {
							if (a.trim(b).length < g.value)
								throw c
										+ "\u5b57\u7b26\u6700\u5c0f\u957f\u5ea6\u4e3a"
										+ g.value + "\u3002";
						},
						maxlength : function(c, b, g) {
							if (a.trim(b).length > g.value)
								throw c
										+ "\u5b57\u7b26\u6700\u5927\u957f\u5ea6\u4e3a"
										+ g.value + "\u3002";
						},
						maxunicodelength : function(c, b, g) {
							b = a.trim(b).replace(/\s/g, "x").replace(
									/[^x00-xff]/g, "xx");
							if (Math.ceil(b.length / 2) > g.value)
								throw c
										+ "\u5b57\u7b26\u6700\u5927\u957f\u5ea6\u4e3a"
										+ g.value + "\u3002";
						},
						rangelength : function(c, b, g) {
							var e = g.value[0];
							g = g.value[1];
							if (a.trim(b).length < e || a.trim(b).length > g)
								throw c
										+ "\u5b57\u7b26\u957f\u5ea6\u8303\u56f4\u4e3a"
										+ e + "-" + g + "\u3002";
						},
						min : function(c, b, g) {
							if ((new RegExp(d.num1)).test(b) == false)
								throw c
										+ "\u8bf7\u8f93\u5165\u6570\u5b57\u683c\u5f0f\uff01";
							if (parseInt(b) < g.value)
								throw c + "\u6700\u5c0f\u503c\u4e3a" + g.value;
						},
						max : function(c, b, g) {
							if ((new RegExp(d.num1)).test(b) == false)
								throw c
										+ "\u8bf7\u8f93\u5165\u6570\u5b57\u683c\u5f0f\uff01";
							if (parseInt(b) > g.value)
								throw c + "\u6700\u5927\u503c\u4e3a" + g.value
										+ "\u3002";
						},
						range : function(c, b, g) {
							if ((new RegExp(d.num1)).test(b) == false)
								throw c
										+ "\u8bf7\u8f93\u5165\u6570\u5b57\u683c\u5f0f\uff01";
							g = g.value;
							if (parseInt(b) < parseInt(g[0])
									|| parseInt(b) > parseInt(g[1]))
								throw c
										+ "\u7684\u8f93\u5165\u8303\u56f4\u5728"
										+ g[0] + "-" + g[1]
										+ "\u4e4b\u95f4\u3002";
						},
						intege : function(c, b) {
							if ((new RegExp(d.intege1)).test(b) == false)
								throw c
										+ "\u8bf7\u8f93\u5165\u5927\u4e8e0\u7684\u6b63\u6574\u6570\uff01";
						},
						number : function(c, b) {
							if ((new RegExp(d.num1)).test(b) == false)
								throw c
										+ "\u8bf7\u8f93\u5165\u6570\u5b57\u683c\u5f0f\uff01";
						},
						email : function(c, b) {
							if ((new RegExp(d.email)).test(b) == false)
								throw c
										+ "\u8bf7\u8f93\u5165\u6b63\u786e\u683c\u5f0f\u7684\u90ae\u7bb1\uff08\u5305\u542b@\u540e\u7f00\uff09\uff01";
						},
						phone : function(c, b) {
							if ((new RegExp(d.tel)).test(b) == false)
								throw c
										+ "\u8bf7\u8f93\u5165\u6b63\u786e\u683c\u5f0f\u7684\u7535\u8bdd\u53f7\u7801\uff01";
						},
						mobile : function(c, b) {
							if ((new RegExp(d.mobile)).test(b) == false)
								throw c
										+ "\u8bf7\u8f93\u5165\u6b63\u786e\u683c\u5f0f\u7684\u624b\u673a\u53f7\u7801\uff01";
						},
						date : function(c, b) {
							if ((new RegExp(d.date)).test(b) == false)
								throw c
										+ "\u8bf7\u8f93\u5165\u6b63\u786e\u683c\u5f0f\u7684\u65e5\u671f\uff01";
						},
						idcard : function(c, b) {
							c = a.trim(b);
							if (c.length == 15) {
								c = new RegExp(d.idcard15);
								if (c.test(b) == false)
									throw "\u8bf7\u8f93\u5165\u6b63\u786e\u683c\u5f0f\u7684\u8eab\u4efd\u8bc1\u53f7\uff01";
							} else if (c.length == 18) {
								c = new RegExp(d.idcard18);
								if (c.test(b) == false)
									throw "\u8bf7\u8f93\u5165\u6b63\u786e\u683c\u5f0f\u7684\u8eab\u4efd\u8bc1\u53f7\uff01";
							} else
								throw "\u8bf7\u8f93\u5165\u6b63\u786e\u683c\u5f0f\u7684\u8eab\u4efd\u8bc1\u53f7\uff01";
						},
						money : function(c, b) {
							if ((new RegExp(d.money)).test(b) == false)
								throw c
										+ "\u8bf7\u8f93\u5165\u6b63\u786e\u683c\u5f0f\u7684\u91d1\u989d\uff01";
						},
						sameto : function(c, b, g) {
							if (b != a(g.value).val())
								throw "\u4e24\u6b21\u8f93\u5165\u4e0d\u4e00\u81f4\uff01";
						},
						regex : function(c, b, g) {
							if ((new RegExp(g.value)).test(b) == false)
								throw c
										+ "\u8f93\u5165\u683c\u5f0f\u4e0d\u6b63\u786e\uff01";
						},
						character : function(c, b, g) {
							var e = [], h = {
								number : false,
								upper : false,
								lower : false,
								chinese : false,
								specialchar : false,
								space : false,
								underline : false
							};
							h = a.extend(h, g.value);
							h.number && e.push("[0-9]");
							h.upper && e.push("[A-Z]");
							h.lower && e.push("[a-z]");
							h.chinese
									&& e
											.push("[\\u4E00-\\u9FA5\\uF900-\\uFA2D]");
							h.space && e.push(" ");
							h.underline && e.push("_");
							h.specialchar && e.push(".");
							g = "^(" + e.join("|") + ")*$";
							if ((new RegExp(g)).test(b) == false)
								throw c
										+ "\u8f93\u5165\u683c\u5f0f\u4e0d\u6b63\u786e\uff01";
						},
						firstchar : function(c, b, g) {
							if (b.length == 0)
								throw "\u8bf7\u8f93\u5165" + c + "\u3002";
							b = b[0];
							var e = [], h = {
								number : false,
								upper : false,
								lower : false,
								chinese : false
							};
							h = a.extend(h, g.value);
							h.number && e.push("[0-9]");
							h.upper && e.push("[A-Z]");
							h.lower && e.push("[a-z]");
							h.chinese
									&& e
											.push("[\\u4E00-\\u9FA5\\uF900-\\uFA2D]");
							g = "^(" + e.join("|") + ")*$";
							if ((new RegExp(g)).test(b) == false)
								throw c
										+ "\u9996\u5b57\u7b26\u8f93\u5165\u683c\u5f0f\u4e0d\u6b63\u786e\uff01";
						},
						checkboxrequire : function(c, b) {
							var g = 0;
							c.each(function() {
								a(this).attr("checked") == true && g++
							});
							if (g < 1)
								throw "\u60a8\u81f3\u5c11\u9700\u8981\u9009\u62e91\u4e2a"
										+ b + "\u3002";
						},
						checkboxminlength : function(c, b, g, e) {
							var h = 0;
							c.each(function() {
								a(this).attr("checked") == true && h++
							});
							if (h < e.value)
								throw "\u60a8\u81f3\u5c11\u9700\u8981\u9009\u62e9"
										+ e.value + "\u4e2a" + b + "\u3002";
						},
						checkboxmaxlength : function(c, b, g, e) {
							var h = 0;
							a(c).each(function() {
								a(this).attr("checked") == true && h++
							});
							if (h > e.value)
								throw "\u60a8\u81f3\u591a\u53ea\u80fd\u9009\u62e9"
										+ e.value + "\u4e2a" + b + "\u3002";
						},
						radiorequire : function(c, b) {
							var g = 0;
							a(c).each(function() {
								a(this).attr("checked") == true && g++
							});
							if (g < 1)
								throw "\u8bf7\u9009\u62e9" + b + "\u3002";
						},
						selectrequire : function(c, b, g) {
							if (g == "")
								throw "\u8bf7\u9009\u62e9" + b + "\u3002";
						}
					});
	var f = [];
	f.require = a.validate.require;
	f.minlength = a.validate.minlength;
	f.maxlength = a.validate.maxlength;
	f.maxunicodelength = a.validate.maxunicodelength;
	f.rangelength = a.validate.rangelength;
	f.min = a.validate.min;
	f.max = a.validate.max;
	f.range = a.validate.range;
	f.intege = a.validate.intege;
	f.number = a.validate.number;
	f.email = a.validate.email;
	f.phone = a.validate.phone;
	f.mobile = a.validate.mobile;
	f.date = a.validate.date;
	f.idcard = a.validate.idcard;
	f.money = a.validate.money;
	f.sameto = a.validate.sameto;
	f.regex = a.validate.regex;
	f.character = a.validate.character;
	f.firstchar = a.validate.firstchar;
	f.checkboxrequire = a.validate.checkboxrequire;
	f.checkboxminlength = a.validate.checkboxminlength;
	f.checkboxmaxlength = a.validate.checkboxmaxlength;
	f.radiorequire = a.validate.radiorequire;
	f.selectrequire = a.validate.selectrequire;
	a.fn.fedValidate = function(c) {
		var b = {
			name : "",
			rules : null,
			deftip : true,
			defmsg : {
				tipmsg : null,
				error : null,
				success : null
			},
			ajax : null,
			fun : null
		};
		if (c != null)
			b = a.extend(b, c);
		a(this).initTipBox(b.tip);
		var g = a(this), e = b.rules;
		e
				|| alert("\u8bf7\u8bbe\u5b9a" + b.name
						+ "\u7684rules\u89c4\u5219\uff01");
		c = false;
		for (var h = 0; h < e.length; h++) {
			var j = e[h];
			if (j.name == "require") {
				c = j.value;
				break
			}
		}
		a(this).data("isRequrie", c);
		if (a(this).attr("type") == "checkbox"
				|| a(this).attr("type") == "radio") {
			var k = a(this), l = a(this).attr("type");
			a(this).bind(
					"blur click",
					function() {
						try {
							var m = a(this).val();
							if (a(this).data("isRequrie") == false)
								g.resetTip();
							else {
								for (var p = 0; p < e.length; p++) {
									var w = e[p];
									try {
										f[l + w.name]
												&& f[l + w.name](k, b.name, m,
														w)
									} catch (F) {
										if (w.msg)
											throw w.msg;
										else if (b.defmsg.error)
											throw b.defmsg.error;
										else
											throw F;
									}
								}
								b.defmsg.success ? g.showTip("success",
										b.defmsg.success) : g
										.showTip("success")
							}
						} catch (n) {
							g.showTip("error", n);
							return true
						}
					})
		} else if (a(this).attr("type") == "select-one")
			a(this).bind(
					"blur change",
					function() {
						try {
							var m = a(this).find("option:selected").val();
							if (a(this).data("isRequrie") == false)
								g.resetTip();
							else {
								for (var p = 0; p < e.length; p++) {
									var w = e[p];
									try {
										f["select" + w.name]
												&& f["select" + w.name](k,
														b.name, m, w)
									} catch (F) {
										if (w.msg)
											throw w.msg;
										else if (b.defmsg.error)
											throw b.defmsg.error;
										else
											throw F;
									}
								}
								b.defmsg.success ? g.showTip("success",
										b.defmsg.success) : g
										.showTip("success")
							}
						} catch (n) {
							g.showTip("error", n);
							return true
						}
					});
		else {
			a(this).data("isRequrie") == true
					&& a(this).focusin(
							function() {
								if (b.deftip == true)
									b.defmsg.tipmsg ? g.showTip("info",
											b.defmsg.tipmsg) : g.showTip(
											"info", "\u8bf7\u8f93\u5165"
													+ b.name + "\u3002")
							});
			a(this).bind(
					"blur change",
					function() {
						try {
							var m = a(this).val();
							if (a(this).data("isRequrie") == false && m == "")
								g.resetTip();
							else {
								for (var p = 0; p < e.length; p++) {
									var w = e[p];
									try {
										f[w.name] && f.require
												&& f[w.name](b.name, m, w)
									} catch (F) {
										if (w.msg)
											throw w.msg;
										else if (b.defmsg.error)
											throw b.defmsg.error;
										else
											throw F;
									}
								}
								b.fun && b.fun();
								if (b.ajax) {
									a(this).attr("name");
									a.get(b.ajax.url, {
										paramName : m
									}, function(q) {
										q = a.parseJSON(q);
										if (q.state == 1)
											g.showTip("success", q.msg);
										else
											q.state == 0
													&& g
															.showTip("error",
																	q.msg)
									})
								}
								b.defmsg.success ? g.showTip("success",
										b.defmsg.success) : g
										.showTip("success")
							}
						} catch (n) {
							g.showTip("error", n);
							return false
						}
					})
		}
		return this
	};
	var d = {
		intege : "^-?[1-9]\\d*$",
		intege1 : "^[1-9]\\d*$",
		intege2 : "^-[1-9]\\d*$",
		num : "^([+-]?)\\d*\\.?\\d+$",
		num1 : "^[1-9]\\d*|0$",
		num2 : "^-[1-9]\\d*|0$",
		decmal : "^([+-]?)\\d*\\.\\d+$",
		decmal1 : "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*$",
		decmal2 : "^-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*)$",
		decmal3 : "^-?([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0)$",
		decmal4 : "^[1-9]\\d*.\\d*|0.\\d*[1-9]\\d*|0?.0+|0$",
		decmal5 : "^(-([1-9]\\d*.\\d*|0.\\d*[1-9]\\d*))|0?.0+|0$",
		email : "^([a-zA-Z0-9]+[_|_|.|-]*)+@([a-zA-Z0-9]+[_|_|.]?)*[a-zA-Z0-9]+.[a-zA-Z]{2,3}$",
		color : "^[a-fA-F0-9]{6}$",
		url : "^http[s]?:\\/\\/([\\w-]+\\.)+[\\w-]+([\\w-./?%&=]*)?$",
		chinese : "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$",
		ascii : "^[\\x00-\\xFF]+$",
		zipcode : "^\\d{6}$",
		mobile : "^(13|15|18|14)[0-9]{9}$",
		ip4 : "^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$",
		notempty : "^\\S+$",
		picture : "(.*)\\.(jpg|bmp|gif|ico|pcx|jpeg|tif|png|raw|tga)$",
		rar : "(.*)\\.(rar|zip|7zip|tgz)$",
		date : "^\\d{4}(\\-|\\/|.)\\d{1,2}\\1\\d{1,2}$",
		qq : "^[1-9]*[1-9][0-9]*$",
		tel : "^(([0\\+]\\d{2,3}-)?(0\\d{2,3})-)?(\\d{7,8})(-(\\d{3,}))?$",
		username : "^\\w+$",
		letter : "^[A-Za-z]+$",
		letter_u : "^[A-Z]+$",
		letter_l : "^[a-z]+$",
		idcard : "^[1-9]([0-9]{14}|[0-9]{17})$",
		idcard15 : "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$",
		idcard18 : "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X)$",
		money : "^[0-9]+([.]\\d{1,2})?$"
	}
})(jQuery);
(function(a) {
	a.fn.fedFollow = function(f, d) {
		var c = {
			left : 0,
			top : 0
		};
		if (d != null)
			c = a.extend(c, d);
		var b = a(f), g = a(this), e = function() {
			var h = b.offset(), j = b.height();
			g.show().css({
				position : "absolute",
				left : h.left + c.left,
				top : h.top + j + c.top
			})
		};
		e();
		a(window).resize(function() {
			g.css("display") != "none" && window.setTimeout(function() {
				e()
			}, 100)
		});
		return this
	}
})(jQuery);
(function(a) {
	var f = window.FED_DATAHOST || "fcd.5173.com";
	a.fedData = a.fedData || {};
	a.fedData.gameData = null;
	a
			.extend(
					a.fedData,
					{
						getAllGame : function(d) {
							a.fedData.preLoadGame(d)
						},
						getGameByIndex : function(d, c) {
							a.fedData.preLoadGame(function(b) {
								c(b[d].list)
							})
						},
						getType : function(d, c) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/ajax.axd?methodName=GETBIZCATEGORYSV31&cache=20&gameType=offertypes&gameid="
													+ d, "calltype",
											function(b) {
												c(b)
											})
						},
						getArea : function(d, c) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/ajax.axd?methodName=gamesV31&cache=20&gameType=GameAreas&tradingType=other&id="
													+ d, "callarea",
											function(b) {
												c(b)
											})
						},
						getServer : function(d, c) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/ajax.axd?methodName=gamesV31&cache=20&gameType=GameServers&tradingType=other&id="
													+ d, "callserver",
											function(b) {
												c(b)
											})
						},
						getRace : function(d, c) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/ajax.axd?methodName=gamesV31&cache=20&gameType=GameRaces&id="
													+ d, "callrace",
											function(b) {
												c(b)
											})
						},
						getFastSearch : function(d, c) {
							d = "http://"
									+ f
									+ "/ajax.axd?methodName=GETSEARCHEDGAMES&cache=20&keyword="
									+ escape(d) + "&count=18";
							a.fedData.loadData(d, "callfastsearch",
									function(b) {
										c(b)
									})
						},
						preLoadGame : function(d) {
							a.fedData.gameData == null ? a.fedData
									.loadData(
											"http://"
													+ f
													+ "/ajax.axd?methodName=GETALLGAMESDATAv31&cache=20&IsGameID=false",
											"callgame", function(c) {
												a.fedData.gameData = c;
												d(c)
											})
									: d(a.fedData.gameData)
						},
						preLoadGameV2 : function(d) {
							if (a.fedData.gameData == null)
								a.fedData
										.loadData(
												"http://"
														+ f
														+ "/commondata/Category.aspx?type=game1&cache=200",
												"callgame2", function(c) {
													a.fedData.gameData = c;
													d && d(c)
												});
							else
								d && d(a.fedData.gameData)
						},
						getAllGameV2 : function(d) {
							a.fedData.preLoadGameV2(d)
						},
						getGameByIndexV2 : function(d, c) {
							a.fedData.preLoadGameV2(function(b) {
								c(b[d])
							})
						},
						getOperatorV2 : function(d, c) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/commondata/Category.aspx?type=operator&cache=&id="
													+ d, "calloperator",
											function(b) {
												c(b)
											})
						},
						getOperatorAreaV2 : function(d, c, b) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/commondata/Category.aspx?type=operatorArea&cache=&id="
													+ c + "&gameid=" + d,
											"calloperatorarea", function(g) {
												b(g)
											})
						},
						getAreaV2 : function(d, c) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/commondata/Category.aspx?type=area&cache=&id="
													+ d, "callarea",
											function(b) {
												c(b)
											})
						},
						getServerV2 : function(d, c) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/commondata/Category.aspx?type=server&cache=&id="
													+ d, "callserver",
											function(b) {
												c(b)
											})
						},
						getTypeV2 : function(d, c) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/commondata/Category.aspx?type=cate&cache=&id="
													+ d, "calltype",
											function(b) {
												c(b)
											})
						},
						getRaceV2 : function(d, c) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/commondata/Category.aspx?type=race&cache=&id="
													+ d, "callrace",
											function(b) {
												c(b)
											})
						},
						getHistory : function(d) {
							a.fedData.loadDataNoCache("http://" + f
									+ "/ajax.axd?methodName=GETHISTORYDATA",
									function(c) {
										d(c)
									})
						},
						getPlatV2 : function(d, c) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/commondata/Category.aspx?type=plats&cache=&id="
													+ d, "callplatform",
											function(b) {
												c(b)
											})
						},
						getPlatAreaV2 : function(d, c, b) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/commondata/Category.aspx?type=area&cache=&id="
													+ d + "&platId=" + c,
											"callplatarea", function(g) {
												b(g)
											})
						},
						loadData : function(d, c, b) {
							a.ajax({
								type : "GET",
								url : d,
								dataType : "jsonp",
								jsonp : "jsoncallback",
								scriptCharset : "GBK",
								jsonpCallback : c,
								cache : true,
								success : function(g) {
									b(g)
								}
							})
						},
						loadDataNoCache : function(d, c) {
							a.ajax({
								type : "GET",
								url : d,
								dataType : "jsonp",
								jsonp : "jsoncallback",
								scriptCharset : "GBK",
								success : function(b) {
									c(b)
								}
							})
						},
						getMobile : function(d, c) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/BMCenter/getjsondata.aspx?type=dkjy&methodName=GETGSON4BM4SELECTINIT&mobile="
													+ d + "&datasource=sjk",
											"callMobile", function(b) {
												c(b)
											})
						},
						getMobilePrice : function(d, c) {
							var b = {
								phoneNo : "",
								gameid : "",
								mianzhiid : ""
							};
							b = a.extend(b, d);
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/BMCenter/getjsondata.aspx?type=dkjy&methodName=GETJSON4BM4GETPRICE&strvalue="
													+ b.phoneNo
													+ "&gameid="
													+ b.gameid
													+ "&mianzhiid="
													+ b.mianzhiid
													+ "&gameareaid=undefined&datasource=sjk",
											"callMobilePrice", function(g) {
												c(g)
											})
						},
						getISP : function(d) {
							var c = /^(130|131|132|145|155|156|186|185)[0-9]{8}$/, b = /^(133|153|189|180)[0-9]{8}$/;
							return /^(139|138|137|136|135|134|159|158|152|151|150|157|188|187|147|182|183)[0-9]{8}$/
									.test(d) ? 0 : c.test(d) ? 1
									: b.test(d) ? 2 : -1
						},
						getQQProduct : function(d) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/BMCenter/getjsondata.aspx?type=dkjy&methodName=GETGSON4BM4SELECTINIT&datasource=qq",
											"callQQProduct", function(c) {
												d(c)
											})
						},
						getQQPrice : function(d, c) {
							var b = {
								gameid : "",
								mianzhiid : ""
							};
							b = a.extend(b, d);
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/BMCenter/getjsondata.aspx?type=dkjy&methodName=GETJSON4BM4GETPRICE&gameid="
													+ b.gameid
													+ "&gameareaid=%20&mianzhiid="
													+ b.mianzhiid
													+ "&datasource=qq45173",
											"callQQPrice", function(g) {
												c(g)
											})
						},
						getChessGameTpye : function(d, c) {
							a.fedData
									.loadData(
											"http://"
													+ f
													+ "/commondata/Category.aspx?type=chesegame&cache=&id="
													+ d, "callchesegame",
											function(b) {
												b && c(b)
											})
						}
					})
})(jQuery);
(function(a) {
	var f = [];
	f.getPlat = a.fedData.getPlatV2;
	f.getPlatArea = a.fedData.getPlatAreaV2;
	f.getOperator = a.fedData.getOperatorV2;
	f.getOperatorArea = a.fedData.getOperatorAreaV2;
	f.getArea = a.fedData.getAreaV2;
	f.getServer = a.fedData.getServerV2;
	f.getType = a.fedData.getTypeV2;
	f.getRace = a.fedData.getRaceV2;
	f.getChessGameTpye = a.fedData.getChessGameTpye;
	var d = [ {
		api : "getGameByIndex",
		title : "\u6e38\u620f",
		deftext : "\u6e38\u620f\u540d\u79f0",
		name : "game",
		arrowLeft : "50px",
		dataParam : "",
		sort : 0,
		request : "game1"
	}, {
		api : "getArea",
		title : "\u533a",
		deftext : "\u6e38\u620f\u533a",
		name : "area",
		arrowLeft : "140px",
		dataParam : "game",
		sort : 1,
		request : "area"
	}, {
		api : "getServer",
		title : "\u670d",
		deftext : "\u6e38\u620f\u670d\u52a1\u5668",
		name : "server",
		arrowLeft : "230px",
		dataParam : "area",
		sort : 2,
		request : "server"
	}, {
		api : "getType",
		title : "\u5206\u7c7b",
		deftext : "\u5168\u90e8\u5206\u7c7b",
		name : "type",
		arrowLeft : "320px",
		dataParam : "game",
		sort : 3,
		request : "type"
	} ], c = [ {
		api : "getGameByIndex",
		title : "\u6e38\u620f",
		deftext : "\u6e38\u620f\u540d\u79f0",
		name : "game",
		arrowLeft : "50px",
		dataParam : "",
		sort : 0,
		request : "game1"
	}, {
		api : "getOperator",
		title : "\u8fd0\u8425\u5546",
		deftext : "\u6e38\u620f\u8fd0\u8425\u5546",
		name : "operator",
		arrowLeft : "140px",
		dataParam : "game",
		sort : 1,
		request : "operator"
	}, {
		api : "getOperatorArea",
		title : "\u533a",
		deftext : "\u6e38\u620f\u533a",
		name : "area",
		arrowLeft : "230px",
		dataParam : "game",
		sort : 2,
		request : "area"
	}, {
		api : "getServer",
		title : "\u670d",
		deftext : "\u6e38\u620f\u670d\u52a1\u5668",
		name : "server",
		arrowLeft : "320px",
		dataParam : "area",
		sort : 3,
		request : "server"
	}, {
		api : "getType",
		title : "\u5206\u7c7b",
		deftext : "\u5168\u90e8\u5206\u7c7b",
		name : "type",
		arrowLeft : "410px",
		dataParam : "game",
		sort : 4,
		request : "type"
	} ], b = [ {
		api : "getGameByIndex",
		title : "\u6e38\u620f",
		deftext : "\u6e38\u620f\u540d\u79f0",
		name : "game",
		arrowLeft : "50px",
		dataParam : "",
		sort : 0,
		request : "game1"
	}, {
		api : "getPlat",
		title : "\u5e73\u53f0",
		deftext : "\u6e38\u620f\u5e73\u53f0",
		name : "plat",
		arrowLeft : "140px",
		dataParam : "game",
		sort : 1,
		request : "plat"
	}, {
		api : "getPlatArea",
		title : "\u533a",
		deftext : "\u6e38\u620f\u533a",
		name : "area",
		arrowLeft : "230px",
		dataParam : "game",
		sort : 2,
		request : "area"
	}, {
		api : "getServer",
		title : "\u670d",
		deftext : "\u6e38\u620f\u670d\u52a1\u5668",
		name : "server",
		arrowLeft : "320px",
		dataParam : "area",
		sort : 3,
		request : "server"
	}, {
		api : "getType",
		title : "\u5206\u7c7b",
		deftext : "\u5168\u90e8\u5206\u7c7b",
		name : "type",
		arrowLeft : "410px",
		dataParam : "game",
		sort : 4,
		request : "type"
	} ], g = [ {
		api : "getGameByIndex",
		title : "\u6e38\u620f",
		deftext : "\u6e38\u620f\u540d\u79f0",
		name : "game",
		arrowLeft : "50px",
		dataParam : "",
		sort : 0,
		request : "game1"
	}, {
		api : "getArea",
		title : "\u533a",
		deftext : "\u6e38\u620f\u533a",
		name : "area",
		arrowLeft : "140px",
		dataParam : "game",
		sort : 1,
		request : "area"
	}, {
		api : "getServer",
		title : "\u670d",
		deftext : "\u6e38\u620f\u670d\u52a1\u5668",
		name : "server",
		arrowLeft : "230px",
		dataParam : "area",
		sort : 2,
		request : "server"
	}, {
		api : "getChessGameTpye",
		title : "\u5206\u7c7b",
		deftext : "\u5168\u90e8\u5206\u7c7b",
		name : "type",
		arrowLeft : "320px",
		dataParam : "game",
		sort : 3,
		request : "type"
	} ], e = 4, h = d, j = 0, k = [], l = 0, m = 1, p = 1;
	if (a("#gameSelectV2").hasClass("game_select_main"))
		p = 0;
	var w = !!window.__utmTrackEvent
			&& document.location.host === "www.5173.com", F = a("#gs_operator")
			.outerWidth();
	a.searchbarv2 = a.searchbarv2 || {};
	a
			.extend(
					a.searchbarv2,
					{
						bindform : function() {
							a.fed.tabs("#gameSelectBox", {
								event : "click",
								onSuccess : function(n) {
									if (n == "#simpleSearch") {
										l = 1;
										a.searchbarv2.hideGameTab()
									} else
										l = 0
								}
							});
							a.fedData.preLoadGameV2();
							a("#gs_game").click(function(n) {
								n.preventDefault();
								a.searchbarv2.showGameTab()
							});
							a.searchbarv2.itemClickEvent();
							a("#gsSearchBox").fedPlaceHoder("holderfont");
							a("#simpleSearchInput").fedPlaceHoder("holderfont");
							a.fed.util
									.autocomplete(
											"#gsSearchBox",
											{
												url : "http://trading.5173.com/browse/KeywordSearch.aspx?action=search&count=10",
												delay : 300
											}, function(n) {
												document.location.href = n.link
											});
							a.fed.util
									.autocomplete(
											"#simpleSearchInput",
											{
												url : "http://trading.5173.com/browse/KeywordSearch.aspx?action=search&count=10",
												delay : 300
											}, function(n) {
												document.location.href = n.link
											});
							a("#gsSearchBtn")
									.click(
											function() {
												var n = "http://s.5173.com/search/"
														+ a("#hide_game").val()
														+ ".shtml?uh=1";
												if (l == 0) {
													n = "http://s.5173.com/search/"
															+ a("#hide_game")
																	.val()
															+ ".shtml?uh=1";
													~window.location.href
															.indexOf("s.5173.com/list")
															&& (n = "http://s.5173.com/list/search/"
																	+ a(
																			"#hide_game")
																			.val()
																	+ ".shtml?uh=1");
													if (a.trim(a("#gs_game")
															.html()) == "\u6e38\u620f\u540d\u79f0"
															&& a("#gsSearchBox")
																	.val() == a(
																	"#gsSearchBox")
																	.attr(
																			"placeholder")) {
														alert("\u8bf7\u5148\u9009\u62e9\u6e38\u620f\u8fdb\u884c\u641c\u7d22");
														return false
													}
													if (a.trim(a("#gs_game")
															.html()) == "\u6e38\u620f\u540d\u79f0")
														n = "http://trading.5173.com/browse/KeywordSearch.aspx";
													a("#gsSearchBox").val() == a(
															"#gsSearchBox")
															.attr("placeholder") ? a(
															"#gsKeyword").val(
															"")
															: a("#gsKeyword")
																	.val(
																			escape(a(
																					"#gsSearchBox")
																					.val()));
													if (w)
														if (a("#gs_game")
																.text() !== "\u6e38\u620f\u540d\u79f0") {
															var q = encodeURIComponent("\u9996\u9875-"
																	+ a(
																			"#gs_game")
																			.text()
																	+ "-"
																	+ a(
																			"#gs_plat")
																			.text()
																	+ "-"
																	+ a(
																			"#gs_operator")
																			.text()
																	+ "-"
																	+ a(
																			"#gs_area")
																			.text()
																	+ "-"
																	+ a(
																			"#gs_server")
																			.text()
																	+ "-"
																	+ a(
																			"#gs_type")
																			.text()
																	+ "-"
																	+ a(
																			"#gsSearchBox")
																			.val());
															q = q
																	.replace(
																			/\(([^\)]+)\)/g,
																			"$1");
															__utmTrackEvent(
																	encodeURIComponent("\u7ad9\u5185\u641c\u7d22"),
																	encodeURIComponent("\u76f4\u63a5\u641c\u7d22"),
																	q)
														}
													if (m == 4) {
														n = "http://s.5173.com/search/"
																+ a(
																		"#hide_game")
																		.val()
																+ ".shtml";
														a(
																"#gsMenu>.hide1, #gsMenu>.hide")
																.append(
																		'<input type="hidden" id="game_tpye" name="type" value="chesegame">')
													}
												} else {
													n = "http://trading.5173.com/browse/KeywordSearch.aspx";
													if (a("#simpleSearchInput")
															.val() == a(
															"#simpleSearchInput")
															.attr("placeholder")) {
														a("#gsKeyword").val("");
														alert("\u8bf7\u8f93\u5165\u5173\u952e\u5b57\u8fdb\u884c\u641c\u7d22");
														return false
													} else
														a("#gsKeyword")
																.val(
																		escape(a(
																				"#simpleSearchInput")
																				.val()));
													a(
															"#hide_operator,#hide_area,#hide_server,#hide_type")
															.val("");
													w
															&& __utmTrackEvent(
																	encodeURIComponent("\u7ad9\u5185\u641c\u7d22"),
																	encodeURIComponent("\u8054\u60f3\u641c\u7d22"),
																	encodeURIComponent(a(
																			"#simpleSearchInput")
																			.val()))
												}
												a("#gsForm").attr({
													action : n,
													target : "_self"
												}).submit()
											});
							a.fedData.getHistory(function(n) {
								k = n.searchbar
							})
						},
						itemClickEvent : function() {
							thisApiArray = m === 4 ? g : m === 3 ? b : c;
							for (var n = 1; n < thisApiArray.length; n++) {
								var q = thisApiArray[n];
								a("#gs_" + q.name)
										.data("name", q.name)
										.data("deftext", q.deftext)
										.click(
												function() {
													for (var r = 0; r < h.length; r++)
														if (a(this)
																.data("name") == h[r].name) {
															var s = a(
																	"#hide_"
																			+ h[r].dataParam)
																	.val();
															a.searchbarv2
																	.renderPanelByIndex(
																			r,
																			s);
															a.searchbarv2
																	.moveArrow(r);
															j = r
														}
												})
							}
						},
						hideGameTab : function() {
							a("#gsBox").hide();
							a("#searchbar_arrow").hide()
						},
						showGameTab : function() {
							var n = a("#gsBox").empty().show(), q = a("<div class='gs_box_inner'></div>"), r = a("<div class='gs_head clearfix'></div>"), s = a("<dl class='gs_name' style='display: block;'>");
							s
									.append(a("<dt>\u5386\u53f2\u9009\u62e9\uff1a</dt>"));
							for (var y = 0; y < k.length; y++) {
								var B = k[y];
								B = a(
										"<a href='javascript:void(0);' gameid='"
												+ B.gameid + "'>" + B.gamename
												+ "</a>").click(function(E) {
									E.preventDefault();
									E = a(this).attr("gameid");
									var J = a(this).html();
									a.searchbarv2.selectGame(E, J)
								});
								s.append(a("<dd></dd>").append(B))
							}
							y = a("<a  target='_self' class='close_btn' title='\u5173\u95ed\u7a97\u53e3' href='javascript:;'>\u5173\u95ed\u7a97\u53e3</a>");
							y
									.click(function(E) {
										E.preventDefault();
										a("#gsBox").empty().hide();
										a.searchbarv2.hideArrow();
										if (w) {
											E = h[j].title;
											__utmTrackEvent(
													encodeURIComponent("\u9996\u9875\u70b9\u51fb"),
													encodeURIComponent("\u56db\u7ea7\u8054\u52a8"),
													encodeURIComponent("\u5173\u95ed")
															+ encodeURIComponent(E)
															+ encodeURIComponent("\u9009\u62e9\u5c42"))
										}
									});
							B = a("<a target='_blank'  class='gs_add_name' href='http://trading.5173.com/UpInfo/UpGameAreaServer.aspx'>\u627e\u4e0d\u5230\u6e38\u620f\u3001\u533a\u670d\uff1f</a>");
							r.append(s).append(y).append(B);
							q.append(r);
							r = a("<div id='gsFsearch' class='gs_f_search'></div>");
							s = a("<a href='#' class='gs_back_btn' id='gsBackBtn'>\u8fd4\u56de</a>");
							var H = a("<input type='text' placeholder='\u8bf7\u8f93\u5165\u6c49\u5b57\u6216\u62fc\u97f3' class='gs_f_search_box'>");
							H.fedPlaceHoder("gs_f_search_box_default");
							var x = a("<a class='gs_f_search_btn' href='javascript:void(0);'>\u5feb\u901f\u641c\u7d22</a>");
							r.append(s).append(H).append(x);
							q.append(r);
							H.keydown(function(E) {
								if (E.keyCode === 13) {
									E.preventDefault();
									x.trigger("click")
								}
							});
							x
									.click(function(E) {
										E.preventDefault();
										if (H.val() != H.attr("placeholder")) {
											var J = H.val().replace(
													/<[\/]*script[\s\S]*?>/ig,
													"");
											a("#gsList").hide();
											a("#gsSort").hide();
											a("#gsNav").hide();
											var M = a("#gsFastSearch").empty()
													.show();
											a("#gsNav").find("li").find("a")
													.removeClass("current");
											a("#fastletter").show().find("a")
													.addClass("current");
											a.fedData
													.getFastSearch(
															escape(J),
															function(L) {
																a("#gsFsearch")
																		.addClass(
																				"gs_result_f_search");
																if (L.length == 0) {
																	a(
																			"#gsFastSearch")
																			.addClass(
																					"gs_list_padding");
																	M
																			.append("<li class='gs_result_ico'><s class='ico_warning_5'></s></li><li class='gs_search_result'>\u5f88\u62b1\u6b49\uff0c\u6ca1\u6709\u641c\u7d22\u5230\u201c <span class='f_f60'>"
																					+ J
																					+ "</span> \u201d\u76f8\u5173\u6e38\u620f\u3002</li><li class='gs_none_result'><b>\u53cb\u60c5\u63d0\u793a\uff1a</b></li><li class='gs_none_result'>1. \u5728\u8f93\u5165\u6846\u5185\u6b63\u786e\u8f93\u5165\u6e38\u620f\u540d\u79f0\u4e2d\u4efb\u610f\u6c49\u5b57\u3001\u4efb\u610f\u4e24\u5b57\u62fc\u97f3\u5168\u62fc\u6216\u8005\u5b57\u6bcd\uff1b</li><li class='gs_none_result'>2. \u901a\u8fc7\u6e38\u620f\u540d\u79f0\u62fc\u97f3\u7d22\u5f15\u641c\u7d22\u3002</li><li class='gs_add_name'>\u627e\u4e0d\u5230\u60f3\u8981\u7684\u6e38\u620f\u3001\u533a\u670d\uff0c\u60a8\u53ef\u4ee5<a target='_blank' href='http://trading.5173.com/UpInfo/UpGameAreaServer.aspx'>\u70b9\u6b64\u6dfb\u52a0</a></li>")
																} else {
																	a(
																			"#gsFastSearch")
																			.removeClass(
																					"gs_list_padding");
																	M
																			.append("<li class='gs_search_result'>\u60a8\u641c\u7d22\u4e86\u201c <span class='f_f60'>"
																					+ J
																					+ "</span> \u201d\uff0c\u8981\u641c\u7d22\u7684\u662f\u4e0d\u662f\uff1a</li>");
																	for (var K = 0; K < L.length; K++) {
																		var G = L[K], N = a("<li><a  href='javascript:void(0);' >"
																				+ G.gamename
																				+ "</a></li>");
																		N
																				.data(
																						"gameid",
																						G.gameid);
																		N
																				.data(
																						"gamename",
																						G.gamename);
																		N
																				.click(function(
																						Q) {
																					Q
																							.preventDefault();
																					a.searchbarv2
																							.selectGame(
																									a(
																											this)
																											.data(
																													"gameid"),
																									a(
																											this)
																											.data(
																													"gamename"))
																				});
																		M
																				.append(N)
																	}
																	L = a("<li class='gs_nofind'><span>\u6ca1\u6709\u627e\u5230\u60a8\u60f3\u8981\u7684\uff1f\u60a8\u8fd8\u53ef\u4ee5<a href='javascript:void(0);' id='searchAgain'>\u8fd4\u56de\u91cd\u65b0\u641c\u7d22</a></span></li>");
																	L
																			.find(
																					"a")
																			.click(
																					function() {
																						a(
																								"#gs_game")
																								.click()
																					});
																	M.append(L)
																}
															});
											w
													&& __utmTrackEvent(
															encodeURIComponent("\u9996\u9875\u70b9\u51fb"),
															encodeURIComponent("\u56db\u7ea7\u8054\u52a8"),
															encodeURIComponent("\u5feb\u901f\u641c\u7d22-")
																	+ encodeURIComponent("\u5feb\u901f\u641c\u7d22-"
																			+ J))
										}
									});
							s.click(function() {
								a("#gs_game").click();
								return false
							});
							a("#searchAgain").click(function() {
							});
							var t = a("<ul  id='gsSort' class='gs_sort'></ul>");
							q.append(t);
							var C = a("<ul id='gsNav' class='gs_nav'></ul>");
							C.append("<li class='first_line'></li>");
							r = a(
									"<li id='fastletter' class='w_70'><a href='javascript:void(0);'>\u641c\u7d22\u7ed3\u679c</a></li>")
									.hide();
							r.click(function(E) {
								E.preventDefault();
								a("#gsList").hide();
								a("#gsSort").hide();
								a("#gsFastSearch").show();
								a("#gsNav").find("li").find("a").removeClass(
										"current");
								a("#fastletter").show().find("a").addClass(
										"current")
							});
							s = a(
									"<li class='w_70'><a  class='current' href='javascript:void(0);'>\u70ed\u95e8\u6e38\u620f</a></li>")
									.data("letter", "HOT");
							s.click(function(E) {
								E.preventDefault();
								A("HOT");
								C.find("li").find("A").removeClass("current");
								a(this).find("a").addClass("current")
							});
							C.append(r).append(s);
							r = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
							a.each(r, function(E, J) {
								E = a(
										"<li><a href='javascript:void(0);'>"
												+ J + "</a></li>").data(
										"letter", J);
								E.click(function(M) {
									M.preventDefault();
									M = a(this).data("letter");
									A(M);
									C.find("li").find("A").removeClass(
											"current");
									a(this).find("a").addClass("current")
								});
								C.append(E)
							});
							C.append("<li class='last_line'></li>");
							q.append(C);
							var u = a("<ul id='gsList' class='gs_list gs_name'></ul>");
							q.append(u);
							r = a("<ul id='gsFastSearch' class='gs_list gs_name'></ul>");
							q.append(r);
							var A = function(E) {
								u.show();
								t.show();
								a("#gsFastSearch").hide();
								t.empty();
								$gsSort_LiAll = a(
										"<li id='sortall' ><a href='javascript:void(0);'>\u5168\u90e8</a></li>")
										.data("gameTypeIndex", 0);
								t.append($gsSort_LiAll);
								var J = null;
								a.fedData
										.getGameByIndexV2(
												"Types",
												function(L) {
													var K = false, G = {}, N = a
															.grep(
																	L,
																	function(Q,
																			P) {
																		if (Q.alias !== "other")
																			return Q;
																		else
																			G = L[P]
																	});
													N.push(G);
													a
															.each(
																	N,
																	function(Q,
																			P) {
																		var R = a(
																				"<li id='"
																						+ P.alias
																						+ "'><a href='javascript:void(0);'>"
																						+ P.name
																						+ "</a></li>")
																				.data(
																						"gameTypeIndex",
																						Q + 1);
																		if (Q + 1 == m) {
																			R
																					.addClass("current");
																			J = P;
																			K = true
																		}
																		t
																				.append(R)
																	});
													K == false
															&& $gsSort_LiAll
																	.addClass("current");
													a.searchbarv2
															.showRookieHelper(t
																	.find("li")
																	.last())
												});
								var M = "";
								t
										.find("li[id!='searchbar_rookie_tips']")
										.click(
												function(L) {
													L.preventDefault();
													m = a(this).data(
															"gameTypeIndex");
													t.find("li").removeClass(
															"current");
													a(this).addClass("current");
													M = a(this).attr("id");
													if (M == "sortall")
														u.find("li").show();
													else {
														u.find(
																"li[lang!='"
																		+ M
																		+ "']")
																.hide();
														u.find(
																"li[lang='" + M
																		+ "']")
																.show()
													}
													L = a(this).closest("li")
															.find("a").html();
													w
															&& __utmTrackEvent(
																	encodeURIComponent("\u7ad9\u5185\u641c\u7d22"),
																	encodeURIComponent("\u76f4\u63a5\u641c\u7d22"),
																	encodeURIComponent("\u6e38\u620f\u7c7b\u522b\u7edf\u8ba1-")
																			+ encodeURIComponent("\u6e38\u620f\u7c7b\u522b\u7edf\u8ba1-"
																					+ L))
												});
								u.empty();
								a.fedData
										.preLoadGameV2(function(L) {
											if (typeof (L !== "undefined")) {
												typeof L.TOPGAME !== "undefined"
														&& a
																.each(
																		L.TOPGAME[E],
																		function(
																				K,
																				G) {
																			K = a(
																					"<li id='"
																							+ G.id
																							+ "' lang='"
																							+ G.type
																							+ "'><a class='hot' title='"
																							+ G.name
																							+ "' href='javascript:void(0);'>"
																							+ G.name
																							+ "</a></li>")
																					.data(
																							{
																								name : G.name,
																								href : G.href
																							});
																			u
																					.append(K)
																		});
												a
														.each(
																L[E],
																function(K, G) {
																	K = a(
																			"<li id='"
																					+ G.id
																					+ "' lang='"
																					+ G.type
																					+ "'><a title='"
																					+ G.name
																					+ "' href='javascript:void(0);'>"
																					+ G.name
																					+ "</a></li>")
																			.data(
																					{
																						name : G.name,
																						href : G.href
																					});
																	u.append(K)
																});
												if (J == null)
													u.find("li").show();
												else {
													!M && (M = J.alias);
													u.find("li").hide();
													u
															.find(
																	"li[lang="
																			+ M
																			+ "]")
															.show()
												}
												u
														.find("li")
														.click(
																function(K) {
																	K
																			.preventDefault();
																	a(
																			"#gsSearchBox")
																			.val(
																					"");
																	K = a(this)
																			.attr(
																					"id");
																	var G = a(
																			this)
																			.data(
																					"name"), N = a(
																			this)
																			.attr(
																					"lang");
																	a(this)
																			.data(
																					"href");
																	a.searchbarv2
																			.selectGame(
																					K,
																					G,
																					N);
																	a.searchbarv2
																			.initGameType(K)
																})
											}
										})
							};
							A("HOT");
							n.append(q);
							n.bgiframe();
							a.searchbarv2.moveArrow(-1)
						},
						selectGame : function(n, q, r) {
							a.searchbarv2.resetBar(0);
							a("#gs_game").html(q);
							a("#hide_game").val(n);
							if (r === "chessgame") {
								a.searchbarv2.changeBarToFour();
								j = 3;
								h = g;
								a.searchbarv2.renderPanelByIndex(j, n);
								a.searchbarv2.moveArrow(j);
								a("#gs_area , #gs_server").css({
									color : "#ccc",
									cursor : "not-allowed"
								}).unbind("click")
							} else {
								a("#gs_area , #gs_server").css({
									color : "#333",
									cursor : "pointer"
								});
								a.searchbarv2.itemClickEvent();
								a("#gsSort .current").attr("id") == "mobilegame" ? a.fedData
										.getPlatV2(
												n,
												function(s) {
													if (s) {
														j = 1;
														h = b;
														a.searchbarv2
																.changeBarToSix();
														a.searchbarv2
																.renderPanel(
																		h[j], s)
													} else {
														j = 1;
														h = d;
														a.searchbarv2
																.changeBarToFour();
														a.searchbarv2
																.renderPanelByIndex(
																		j, n)
													}
													a.searchbarv2.moveArrow(j)
												})
										: a.fedData
												.getOperatorV2(
														n,
														function(s) {
															if (s.length > 0) {
																j = 1;
																h = c;
																a.searchbarv2
																		.changeBarToFive();
																a.searchbarv2
																		.renderPanel(
																				h[j],
																				s)
															} else {
																j = 1;
																h = d;
																a.searchbarv2
																		.changeBarToFour();
																a.searchbarv2
																		.renderPanelByIndex(
																				j,
																				n)
															}
															a.searchbarv2
																	.moveArrow(j)
														})
							}
						},
						renderPanelByIndex : function(n, q) {
							if (n < h.length) {
								var r = h[n];
								if (r.api == "getOperatorArea") {
									var s = a("#hide_game").val();
									q = a("#hide_operator").val();
									if (s == "") {
										a.searchbarv2.moveArrow(n);
										a.searchbarv2
												.renderPanelByMsg(
														r,
														"\u8bf7\u5148\u9009\u62e9\u6e38\u620f...",
														n)
									} else if (q == "") {
										a.searchbarv2.moveArrow(n);
										a.searchbarv2
												.renderPanelByMsg(
														r,
														"\u8bf7\u5148\u9009\u62e9\u6e38\u620f\u8fd0\u8425\u5546...",
														n)
									} else
										f[r.api](s, q, function(y) {
											a.searchbarv2.moveArrow(n);
											a.searchbarv2.renderPanel(r, y, n)
										})
								} else if (r.api == "getPlatArea") {
									s = a("#hide_game").val();
									q = a("#hide_plat").val();
									if (s == "") {
										a.searchbarv2.moveArrow(n);
										a.searchbarv2
												.renderPanelByMsg(
														r,
														"\u8bf7\u5148\u9009\u62e9\u6e38\u620f...",
														n)
									} else if (q == "") {
										a.searchbarv2.moveArrow(n);
										a.searchbarv2
												.renderPanelByMsg(
														r,
														"\u8bf7\u5148\u9009\u62e9\u6e38\u620f\u5e73\u53f0...",
														n)
									} else
										f[r.api](s, q, function(y) {
											a.searchbarv2.moveArrow(n);
											a.searchbarv2.renderPanel(r, y, n)
										})
								} else {
									s = a("#hide_game").val();
									if (s == "") {
										a.searchbarv2.moveArrow(n);
										a.searchbarv2
												.renderPanelByMsg(
														r,
														"\u8bf7\u5148\u9009\u62e9\u6e38\u620f...",
														n)
									} else if (r.name == "server")
										if (a("#hide_area").val() == "") {
											a.searchbarv2.moveArrow(n);
											a.searchbarv2
													.renderPanelByMsg(
															r,
															"\u8bf7\u5148\u9009\u62e9\u6e38\u620f\u533a...",
															n)
										} else
											f[r.api]
													(
															q,
															function(y) {
																if (y.length > 0) {
																	a.searchbarv2
																			.moveArrow(n);
																	a.searchbarv2
																			.renderPanel(
																					r,
																					y)
																} else {
																	j = h.length - 1;
																	a.searchbarv2
																			.moveArrow(j);
																	a.searchbarv2
																			.renderPanelByIndex(
																					j,
																					s)
																}
															});
									else
										f[r.api](q, function(y) {
											a.searchbarv2.moveArrow(n);
											a.searchbarv2.renderPanel(r, y)
										})
								}
							} else
								a("#gsBox").empty().hide()
						},
						renderPanelByMsg : function(n, q, r) {
							var s = a("#gsBox").empty().show(), y = a("<div class='gs_box_inner'></div>"), B = a("<div class='gs_head clearfix'></div>"), H = a("<dl class='gs_name' style='display: block;'>");
							H.append(a("<dt>\u8bf7\u9009\u62e9" + n.title
									+ "\uff1a</dt>"));
							n = a(
									"<a href='javascript:void(0);'>\u5168\u90e8"
											+ n.title + "</a>").data("name",
									n.name).data("sort", n.sort);
							n.click(function(t) {
								t.preventDefault();
								a("#hide_" + a(this).data("name")).val(-1);
								a("#gs_" + a(this).data("name")).html(
										a(this).html());
								a.searchbarv2.hideGameTab();
								t = a(this).data("sort");
								j = a(this).data("sort");
								var C = h.length - 1;
								if (a(this).data("name") == "type") {
									var u = h[C];
									a("#hide_" + u.name).val(-1);
									a("#gs_" + u.name).html(
											"\u5168\u90e8" + u.title);
									a.searchbarv2.hideGameTab()
								} else
									for (var A = 0; A < h.length; A++) {
										j += 1;
										if (A > t && A < C) {
											u = h[A];
											a("#hide_" + u.name).val(-1);
											a("#gs_" + u.name).html(
													"\u5168\u90e8" + u.title)
										} else {
											u = a("#hide_game").val();
											a.searchbarv2.moveArrow(C);
											a.searchbarv2.renderPanelByIndex(C,
													u)
										}
									}
							});
							H.append(a("<dd></dd>").append(n));
							n = a("<a  target='_self' class='close_btn' title='\u5173\u95ed\u7a97\u53e3' href='javascript:;'>\u5173\u95ed\u7a97\u53e3</a>");
							n
									.click(function(t) {
										t.preventDefault();
										a("#gsBox").empty().hide();
										a.searchbarv2.hideArrow();
										if (w) {
											t = h[j].title;
											__utmTrackEvent(
													encodeURIComponent("\u9996\u9875\u70b9\u51fb"),
													encodeURIComponent("\u56db\u7ea7\u8054\u52a8"),
													encodeURIComponent("\u5173\u95ed")
															+ encodeURIComponent(t)
															+ encodeURIComponent("\u9009\u62e9\u5c42"))
										}
									});
							var x = a("<a target='_blank'  class='gs_add_name' href='http://trading.5173.com/UpInfo/UpGameAreaServer.aspx'>\u627e\u4e0d\u5230\u6e38\u620f\u3001\u533a\u670d\uff1f</a>");
							B.append(H).append(n).append(x);
							y.append(B);
							B = a("<ul id='gsList' class='gs_list gs_name'></ul>");
							y.append(B);
							B.append("<li class='gs_tip'>" + q + "</li>");
							s.append(y);
							s.bgiframe();
							a.searchbarv2.moveArrow(r)
						},
						renderPanel : function(n, q) {
							var r = a("#gsBox").empty().show(), s = a("<div class='gs_box_inner'></div>"), y = a("<div class='gs_head clearfix'></div>"), B = a("<dl class='gs_name' style='display: block;'>");
							B.append(a("<dt>\u8bf7\u9009\u62e9" + n.title
									+ "\uff1a</dt>"));
							var H = a(
									"<a href='javascript:void(0);'>\u5168\u90e8"
											+ n.title + "</a>").data("name",
									n.name).data("sort", n.sort);
							H.click(function(C) {
								C.preventDefault();
								a("#hide_" + a(this).data("name")).val(-1);
								a("#gs_" + a(this).data("name")).html(
										a(this).html());
								a.searchbarv2.hideGameTab();
								C = a(this).data("sort");
								j = a(this).data("sort");
								var u = h.length - 1;
								if (a(this).data("name") == "type") {
									var A = h[u];
									a("#hide_" + A.name).val(-1);
									a("#gs_" + A.name).html(
											"\u5168\u90e8" + A.title);
									a.searchbarv2.hideGameTab()
								} else
									for (var E = 0; E < h.length; E++) {
										j += 1;
										if (E > C && E < u) {
											A = h[E];
											a("#hide_" + A.name).val(-1);
											a("#gs_" + A.name).html(
													"\u5168\u90e8" + A.title)
										} else {
											A = a("#hide_game").val();
											a.searchbarv2.renderPanelByIndex(u,
													A)
										}
									}
							});
							B.append(a("<dd></dd>").append(H));
							H = a("<a  target='_self' class='close_btn' title='\u5173\u95ed\u7a97\u53e3' href='javascript:;'>\u5173\u95ed\u7a97\u53e3</a>");
							H
									.click(function(C) {
										C.preventDefault();
										a("#gsBox").empty().hide();
										a.searchbarv2.hideArrow();
										if (w) {
											C = h[j].title;
											__utmTrackEvent(
													encodeURIComponent("\u9996\u9875\u70b9\u51fb"),
													encodeURIComponent("\u56db\u7ea7\u8054\u52a8"),
													encodeURIComponent("\u5173\u95ed")
															+ encodeURIComponent(C)
															+ encodeURIComponent("\u9009\u62e9\u5c42"))
										}
									});
							var x = a("<a target='_blank'  class='gs_add_name' href='http://trading.5173.com/UpInfo/UpGameAreaServer.aspx'>\u627e\u4e0d\u5230\u6e38\u620f\u3001\u533a\u670d\uff1f</a>");
							y.append(B).append(H).append(x);
							s.append(y);
							var t = a("<ul id='gsList' class='gs_list gs_name'></ul>");
							s.append(t);
							a
									.each(
											q,
											function(C, u) {
												C = a(
														"<li id='"
																+ u.id
																+ "' lang='"
																+ u.type
																+ "' ><a href='javascript:void(0);'>"
																+ u.name
																+ "</a></li>")
														.data({
															id : u.id,
															name : u.name,
															href : u.href
														});
												t.append(C);
												C
														.click(function(A) {
															A.preventDefault();
															a(this)
																	.attr(
																			"lang");
															a(this)
																	.data(
																			"href");
															a("#gs_" + n.name)
																	.html(
																			a(
																					this)
																					.data(
																							"name"));
															a("#hide_" + n.name)
																	.val(
																			a(
																					this)
																					.attr(
																							"id"));
															j += 1;
															if (j < h.length) {
																if (j == h.length - 1) {
																	A = a(
																			"#hide_game")
																			.val();
																	a.searchbarv2
																			.renderPanelByIndex(
																					j,
																					A)
																} else
																	a.searchbarv2
																			.renderPanelByIndex(
																					j,
																					a(
																							this)
																							.data(
																									"id"));
																a.searchbarv2
																		.moveArrow(j)
															} else
																a.searchbarv2
																		.hideGameTab()
														})
											});
							r.append(s);
							r.bgiframe()
						},
						changeBarToFour : function() {
							if (e != 4) {
								e = 4;
								h = d;
								a("#gs_operator").hide();
								a("#gs_plat").hide();
								a("#gsSearchBox")
										.css(
												"width",
												parseInt(a("#gsSearchBox").css(
														"width"))
														+ F)
							}
						},
						changeBarToFive : function() {
							h = c;
							a("#gs_operator").show();
							a("#gs_plat").hide();
							if (e != 5) {
								e = 5;
								a("#gsSearchBox")
										.css(
												"width",
												parseInt(a("#gsSearchBox").css(
														"width"))
														- F)
							}
						},
						changeBarToSix : function() {
							h = b;
							a("#gs_operator").hide();
							a("#gs_plat").show();
							if (e != 5) {
								e = 5;
								a("#gsSearchBox")
										.css(
												"width",
												parseInt(a("#gsSearchBox").css(
														"width"))
														- F)
							}
						},
						initBarToFour : function() {
							e = 4;
							h = d;
							a("#gs_operator").hide()
						},
						initBarToFive : function() {
							e = 5;
							h = c;
							a("#gs_operator").show();
							a("#gsSearchBox").css(
									"width",
									parseInt(a("#gsSearchBox").css("width"))
											- F)
						},
						hideArrow : function() {
							a("#searchbar_arrow").hide()
						},
						moveArrow : function(n) {
							var q = a("#searchbar_arrow").show();
							if (q.length == 0)
								q = a(
										"<s id='searchbar_arrow' class='game_select_arrow' style='left: 50px;'></s>")
										.appendTo(a(".game_select")).show();
							if (n > -1)
								h && n >= h.length ? q.hide() : q.animate({
									left : h[n].arrowLeft
								}, 200, function() {
								});
							else
								q.animate({
									left : "50px"
								}, 200, function() {
								})
						},
						resetBar : function(n) {
							for (var q = 0; q < h.length; q++) {
								var r = h[q];
								if (q > n) {
									a("#gs_" + r.name).html(r.deftext);
									a("#hide_" + r.name).val("")
								}
							}
						},
						showRookieHelper : function(n) {
							if (!a.cookie("searchbar_rookie_close")) {
								var q = a("<li id='searchbar_rookie_tips' style='display:none'></li>"), r = a("<div class='fed-toolstip'></div>");
								r
										.html("<div class='fed-tipsarr fed-arrleft'><span class='arr-1'></span><span class='arr-2'></span></div><div class='fed-tipcon'> <p>\u65b0\u589e\u6e38\u620f\u7c7b\u578b\u7b5b\u9009\uff0c\u9009\u62e9\u9875\u6e38\u3001\u624b\u6e38\u66f4\u65b9\u4fbf\uff01</p></div>");
								var s = a("<a href='javascript:void(0);' id='searchbar_rookie_closebtn' class='fed-tipsclose'>\u00d7</a>");
								r.append(s);
								q.append(r);
								n.after(q);
								s.click(function(y) {
									y.preventDefault();
									q.hide();
									a.cookie("searchbar_rookie_close",
											"closed", {
												expires : 365
											})
								})
							}
						},
						initBar : function(n) {
							a(
									'<li id="gs_plat"  class="gs_plat arrow" title="\u9009\u62e9\u6e38\u620f\u5e73\u53f0" style="display:none;">\u5e73\u53f0</li>')
									.insertAfter(a("#gs_game"));
							a(
									'<input type="hidden" id="hide_plat" name="gp" value="" />')
									.insertAfter(a("#hide_game"));
							for (var q = false, r = 0; r < c.length; r++) {
								var s = c[r];
								if (n[s.name]) {
									a("#gs_" + s.name).html(n[s.name].name);
									a("#hide_" + s.name).val(n[s.name].id);
									if (s.name == "operator") {
										a.searchbarv2.initBarToFive();
										q = true
									}
								}
							}
							q == false && a("#hide_operator").val("");
							n.keyword && a("#gsSearchBox").val(n.keyword);
							if (n.gametype !== "")
								switch (n.gametype) {
								case "netgame":
									m = 1;
									break;
								case "webgame":
									m = 2;
									break;
								case "mobilegame":
									m = 3;
									break;
								case "chesegame":
									m = 4;
									h = g;
									a("#gs_area , #gs_server").css({
										color : "#ccc",
										cursor : "not-allowed"
									}).unbind("click");
									break;
								case "other":
									m = 5;
									break
								}
						},
						initGameType : function(n) {
							if (typeof defaultGameType != "undefined") {
								var q = defaultGameType, r;
								a.fedData.getTypeV2(n, function(s) {
									a.each(s, function(y, B) {
										if (B.name == q) {
											r = B.id;
											a("#hide_type").val(r)
										}
									})
								})
							}
						},
						resetbar : function() {
						}
					})
})(jQuery);
$(function() {
	if (typeof serachBarData === "undefined")
		serachBarData = {
			game : {
				id : "",
				name : "\u6e38\u620f\u540d\u79f0"
			},
			area : {
				id : "",
				name : "\u6e38\u620f\u533a"
			},
			server : {
				id : "",
				name : "\u6e38\u620f\u670d\u52a1\u5668"
			},
			type : {
				id : "",
				name : "\u5168\u90e8\u5206\u7c7b"
			},
			keyword : "",
			gametype : ""
		};
	$.searchbarv2.initBar(serachBarData);
	$.searchbarv2.bindform()
});
$(function() {
	var a = function(o, v) {
		var D = document.createElement("input"), I = !!("placeholder" in D);
		D = null;
		$(o).each(
				function() {
					this.value = "";
					var z = $(this).attr("placeholder"), U = $(this).css(
							"color"), O = z && !I, S = this.value;
					S && S !== z && $(this).css({
						color : v
					});
					if (O && !S)
						this.value = z;
					$(this).focus(function() {
						if (O && this.value === z)
							this.value = "";
						$(this).css({
							color : v
						})
					}).blur(function() {
						if (!this.value) {
							if (O)
								this.value = z;
							$(this).css({
								color : U
							})
						}
					})
				})
	};
	if ($("#gameSelect").length) {
		$("#gameSelect");
		var f = $("#gsMenu"), d = $("#gsBox"), c = $("#gsForm"), b = $("#gsSearchBox"), g = $("#gsSearchBtn"), e = $("#gsUpGameInfo"), h = f
				.find("input:hidden"), j = $("#simpleSearchInput"), k = f
				.find("li.gs_name"), l = f.find("li.gs_area"), m = f
				.find("li.gs_server"), p = f.find("li.gs_type"), w = f.offset().top
				+ f.outerHeight(), F = /\s*gs_list\s*/, n = $.browser.msie
				&& $.browser.version === "6.0", q = !!window.__utmTrackEvent
				&& document.location.host === "www.5173.com", r = false, s = false, y = window.FED_DATAHOST
				|| "fcd.5173.com", B = $.fedData, H, x, t, C, u, A, E, J, M, L, K, G, N;
		h.val("-1");
		(function() {
			var o = "", v = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("");
			o += '<div class="gs_box_inner"><div class="gs_head clearfix" id="gsHead"><dl class="gs_name" id="gsHistory"><dt>\u5386\u53f2\u9009\u62e9\uff1a</dt></dl><dl class="gs_area hide"><dt><strong>\u8bf7\u9009\u62e9\u6e38\u620f\u533a</strong></dt><dd><a href="#" gameid="-1">\u5168\u90e8\u533a</a></dd></dl><dl class="gs_server hide"><dt><strong>\u8bf7\u9009\u62e9\u6e38\u620f\u670d</strong></dt><dd><a href="#" gameid="-1">\u5168\u90e8\u670d</a></dd></dl><dl class="gs_type hide"><dt><strong>\u8bf7\u9009\u62e9\u5206\u7c7b</strong></dt><dd><a href="#" gameid="-1">\u5168\u90e8\u5206\u7c7b</a></dd></dl><a href="javascript:;" title="\u5173\u95ed\u7a97\u53e3" class="close_btn" id="closeBtn" target="_self">\u5173\u95ed\u7a97\u53e3</a><a href="http://trading.5173.com/UpInfo/UpGameAreaServer.aspx" class="gs_add_name" id="gsUpGameInfo" target="_blank">\u627e\u4e0d\u5230\u6e38\u620f\u3001\u533a\u670d\uff1f</a></div>';
			o += '<div class="gs_f_search" id="gsFsearch"><input type="text" class="gs_f_search_box" id="gsFastSearchBox" placeholder="\u8bf7\u8f93\u5165\u6c49\u5b57\u6216\u62fc\u97f3" /><a href="#" class="gs_f_search_btn" id="gsFastSearchBtn">\u5feb\u901f\u641c\u7d22</a></div>';
			o += '<ul class="gs_nav" id="gsNav"><li class="first_line"></li><li class="w_70 hide" id="gsResultMenu"><a href="#">\u641c\u7d22\u7ed3\u679c</a></li><li class="w_70"><a href="#" class="current" id="gsHotGame">\u70ed\u95e8\u6e38\u620f</a></li>';
			$.each(v, function(D, I) {
				o += '<li><a href="#">' + I + "</a></li>"
			});
			o += '<li class="last_line"></li></ul><ul class="gs_list" id="gsList"><li class="gs_tip"><img src="http://img01.5173cdn.com/fed/build/1.00/images/loader.gif" alt="" />\u6b63\u5728\u52a0\u8f7d\u6e38\u620f\u6570\u636e\uff0c\u8bf7\u7a0d\u5019...</li></ul></div>';
			d.html(o);
			x = $("#gsList");
			t = $("#gsNav");
			C = $("#gsHead");
			u = $("#gsFsearch");
			E = $("#gsFastSearchBox");
			J = $("#gsFastSearchBtn");
			A = $("#gsHistory");
			M = $("#gsResultMenu");
			L = M.find("a");
			if (n)
				$.fn.bgiframe !== undefined ? d.children().bgiframe()
						: $
								.getScript(
										"http://img01.5173cdn.com/fed/build/1.00/js/jquery.bgiframe.js",
										function() {
											d.children(".gs_box_inner")
													.bgiframe()
										})
		})();
		a(b, "#333");
		a(E, "#333");
		a(j, "#333");
		$.ajax({
			type : "GET",
			url : "http://" + y + "/ajax.axd?methodName=GETHISTORYDATA",
			dataType : "jsonp",
			scriptCharset : "GBK",
			jsonp : "jsoncallback",
			success : function(o) {
				if (o.searchbar) {
					var v = A.html();
					$.each(o.searchbar, function(D, I) {
						v += '<dd><a href="#" gameid="' + I.gameid + '">'
								+ I.gamename + "</a></dd>"
					});
					A.html(v)
				}
			}
		});
		var Q;
		$.fed.tabs("#gameSelectBox", {
			event : "click",
			onSuccess : function(o) {
				P();
				if (o === "#simpleSearch") {
					Q = G;
					G = window.cacheGameId = null
				} else
					G = window.cacheGameId = Q
			}
		});
		var P = function() {
			d.hide();
			H.hide()
		};
		$("#closeBtn").click(
				function() {
					P();
					if (q) {
						var o = x[0].className.replace(F, "");
						o = Z[o];
						__utmTrackEvent("%E9%A6%96%E9%A1%B5%E7%82%B9%E5%87%BB",
								"%E5%9B%9B%E7%BA%A7%E8%81%94%E5%8A%A8",
								"%E5%85%B3%E9%97%AD"
										+ encodeURIComponent(o[o.length - 1])
										+ "%E9%80%89%E6%8B%A9%E5%B1%82")
					}
				});
		H = $('<s class="game_select_arrow"></s>').appendTo(document.body).css(
				"top", w + 3 + "px");
		var R = d.find("s.arrow").eq(0).outerWidth();
		a = Math.floor(k.offset().left + (k.outerWidth() - R) / 2);
		w = Math.floor(l.offset().left + (l.outerWidth() - R) / 2);
		var T = Math.floor(m.offset().left + (m.outerWidth() - R) / 2);
		R = Math.floor(p.offset().left + (p.outerWidth() - R) / 2);
		var Z = {
			gs_name : [ a, "gs_area", B.getArea, "\u6e38\u620f" ],
			gs_area : [ w, "gs_server", B.getServer, "\u533a" ],
			gs_server : [ T, "gs_type", B.getType, "\u670d" ],
			gs_type : [ R, "\u7c7b\u76ee" ]
		};
		$.easing.easeInOutExpo = function(o, v, D, I, z) {
			if (v == 0)
				return D;
			if (v == z)
				return D + I;
			if ((v /= z / 2) < 1)
				return I / 2 * Math.pow(2, 10 * (v - 1)) + D;
			return I / 2 * (-Math.pow(2, -10 * --v) + 2) + D
		};
		var ha = function(o) {
			o = Z[o][0];
			H.css("display", "block");
			parseInt(H.css("left")) !== o && H.animate({
				left : o + "px"
			}, 150, "easeInOutExpo")
		}, aa = function(o) {
			o = o || "gs_name";
			C.find("dl:visible").hide().end().find("." + o).show();
			x[0].className = "gs_list " + o;
			ha(o)
		}, da = function(o, v) {
			var D, I, z;
			v = v || $("#gsHotGame");
			r = true;
			D = v.text();
			D = D === "\u70ed\u95e8\u6e38\u620f" ? "HOTG"
					: D === "\u641c\u7d22\u7ed3\u679c" ? "FASTSEARCH" : D;
			I = "GAMEHTML_" + D;
			z = v.data(I);
			if (z === undefined) {
				z = "";
				$
						.each(
								o,
								function(U, O) {
									if (O.name === D) {
										$
												.each(
														O.list,
														function(S, V) {
															z += '<li><a href="#" gameid="'
																	+ V.id
																	+ '"'
																	+ (D !== "HOTG"
																			&& V.hot === "1" ? 'class="hot"'
																			: "")
																	+ ">"
																	+ V.name
																	+ "</a></li>"
														});
										return false
									}
								});
				z = z === "" ? '<li class="gs_tip">\u8be5\u5b57\u6bcd\u4e0b\u6682\u65e0\u6e38\u620f</li>'
						: z;
				v.data(I, z)
			}
			x.html(z)
		}, ea = function(o) {
			o.nextUntil().filter(".arrow").each(
					function() {
						$(this).data("GAMEHTML")
								&& $(this).data("GAMEID") !== G
								&& $(this).removeData("GAMEHTML").removeData(
										"GAMEID")
					})
		}, ba = function(o, v, D) {
			var I = f.find("li." + o);
			o !== "gs_name" && I.data("TEXT") === undefined
					&& I.data("TEXT", I.text()).data("TITLE", I.attr("title"));
			I.text(v).attr("title", v);
			if (D)
				s ? I.nextUntil().filter(".arrow").each(
						function() {
							var z;
							z = $(this).hasClass("gs_area") ? N.area
									&& N.area.normalText
									|| "\u6e38\u620f\u533a" : $(this).hasClass(
									"gs_server") ? N.server
									&& N.server.normalText
									|| "\u6e38\u620f\u670d\u52a1\u5668"
									: N.type && N.type.normalText
											|| "\u5168\u90e8\u5206\u7c7b";
							$(this).text(z).attr("title", z)
						}) : I.nextUntil().filter(".arrow").each(function() {
					var z = $(this).data("TEXT");
					z && $(this).text(z).attr("title", $(this).data("TITLE"))
				})
		}, Y = function(o, v, D, I) {
			var z = "", U = f.find("li." + D), O = U.data("GAMEID"), S, V;
			if (I === undefined)
				I = false;
			S = function() {
				if (z = p.data("GAMEHTML")) {
					clearTimeout(V);
					x.html(z)
				} else
					V = setTimeout(S, 200)
			};
			if (O === v) {
				z = U.data("GAMEHTML");
				x.html(z);
				aa(D)
			} else
				o(v, function(W) {
					if (W) {
						$.each(W, function(X, fa) {
							z += '<li><a href="#" gameid="' + fa.id + '">'
									+ fa.name + "</a></li>"
						});
						if (D !== "gs_type" || I) {
							x.html(z);
							aa(D)
						}
						U.data("GAMEID", v).data("GAMEHTML", z)
					} else {
						ea(k);
						z = p.data("GAMEHTML");
						if (z === undefined)
							V = setTimeout(S, 200);
						else
							x.html(z);
						aa("gs_type")
					}
				});
			ea(U)
		}, ga = function(o) {
			if (!$(o.target).parents("li").hasClass("gs_add_name")) {
				var v = $(o.target), D = x[0].className.replace(F, ""), I = f
						.find("input." + D), z = v.attr("gameid"), U = v
						.attr("areaid"), O = v.text(), S = z, V = true, W, X;
				if (!v.parent().hasClass(".gs_add_name")) {
					W = Z[D][1];
					X = Z[D][2];
					if (U) {
						z = U;
						W = "gs_server";
						X = B.getServer
					}
					if (D === "gs_server") {
						z = G;
						e.hide()
					} else
						e.is(":hidden") && e.show();
					if (z === "-1") {
						if (D === "gs_area") {
							ba("gs_server", "\u5168\u90e8\u670d", true);
							V = false
						}
						W = "gs_type";
						X = B.getType;
						z = G;
						ea(l)
					}
					D === "gs_type" ? P() : Y(X, z, W);
					if (D === "gs_name") {
						G = window.cacheGameId = S;
						p.data("GAMEID") !== S && Y(B.getType, S, "gs_type");
						t.hide();
						u.hide()
					}
					if (U) {
						ba("gs_name", v.attr("gamename"), true);
						ba("gs_area", v.attr("areaname"), false)
					} else
						ba(D, O, V);
					if (I.length)
						I.val(S);
					else {
						I = f.find("input." + W);
						I.val("-1")
					}
					I.nextUntil().each(function() {
						if (this.value)
							this.value = "-1"
					});
					o.preventDefault()
				}
			}
		};
		B.getAllGame(function(o) {
			window.allGameData = K = o;
			r || da(K, t.find("a.current"))
		});
		t.delegate("a", "click", function(o) {
			t.find("a.current").removeClass("current");
			$(this).addClass("current");
			K && da(K, $(this));
			o.preventDefault()
		});
		t.delegate("a", "focus", function() {
			$(this).blur()
		});
		f
				.find("li.arrow")
				.click(
						function() {
							var o = this.className.match(/(gs_\w+)\b/)[0], v = t
									.find("a.current"), D;
							if (o === "gs_name") {
								u.show();
								t.show();
								K && da(K, v)
							} else {
								v = $(this).data("GAMEHTML");
								if (v === undefined)
									switch (o) {
									case "gs_area":
										if (G)
											Y(B.getArea, G, "gs_area");
										else
											v = '<li class="gs_tip">\u8bf7\u5148\u9009\u62e9\u6e38\u620f...</li>';
										break;
									case "gs_server":
										D = h.filter(".gs_area").val();
										if (D !== "-1")
											Y(B.getServer, D, "gs_server");
										else
											v = '<li class="gs_tip">\u8bf7\u5148\u9009\u62e9\u6e38\u620f\u533a...</li>';
										break;
									case "gs_type":
										if (G)
											Y(B.getType, G, "gs_type", true);
										else
											v = '<li class="gs_tip">\u8bf7\u5148\u9009\u62e9\u6e38\u620f...</li>';
										break
									}
								u.hide();
								t.hide();
								x.html(v)
							}
							C.find("dl").hide().end().find("dl." + o).show();
							aa(o);
							d.show()
						});
		x.delegate("a", "click", function(o) {
			ga(o);
			g.focus()
		});
		C.find("dl").delegate("a", "click", ga);
		var ca = function(o, v) {
			var D = $.trim(v.val()), I = D !== ""
					&& D !== v.attr("placeholder"), z;
			o = o || v.data("currItem");
			if (G)
				url = "http://s.5173.com/search/" + G + ".shtml";
			else {
				if (o && o.title === D) {
					window.location.href = o.link;
					q
							&& __utmTrackEvent(
									"%E7%AB%99%E5%86%85%E6%90%9C%E7%B4%A2",
									"%E8%81%94%E6%83%B3%E6%90%9C%E7%B4%A2",
									encodeURIComponent(D));
					return
				}
				if (I)
					url = "http://trading.5173.com/browse/KeywordSearch.aspx";
				else {
					alert(v[0].id === "gsSearchBox" ? "\u8bf7\u5148\u9009\u62e9\u6e38\u620f\u8fdb\u884c\u641c\u7d22"
							: "\u8bf7\u8f93\u5165\u5173\u952e\u5b57\u8fdb\u884c\u641c\u7d22");
					return
				}
			}
			z = z === undefined ? escape(D) : z;
			if (I)
				$("#gsKeyword").length ? $("#gsKeyword").val(z) : h.parent()
						.append(
								'<input type="hidden" name="keyword" value="'
										+ z + '" id="gsKeyword" />');
			h.each(function() {
				if (this.value === "-1")
					this.value = ""
			});
			c.attr({
				action : url,
				target : "_self"
			}).submit();
			if (q)
				if (k.text() !== "\u6e38\u620f\u540d\u79f0") {
					o = encodeURIComponent("\u9996\u9875-" + k.text() + "-"
							+ l.text() + "-" + m.text() + "-" + p.text()
							+ (I ? "-" + D : ""));
					o = o.replace(/\(([^\)]+)\)/g, "$1");
					__utmTrackEvent("%E7%AB%99%E5%86%85%E6%90%9C%E7%B4%A2",
							"%E7%9B%B4%E6%8E%A5%E6%90%9C%E7%B4%A2", o)
				}
		};
		b.keydown(function(o) {
			if (o.keyCode === 13) {
				o.preventDefault();
				ca(null, b)
			}
		}).focus(P);
		g.click(function(o) {
			o.preventDefault();
			ca(null, b.is(":visible") ? b : j)
		});
		$.fed.util
				.autocomplete(
						"#gsSearchBox",
						{
							url : "http://trading.5173.com/browse/KeywordSearch.aspx?action=search&count=10",
							delay : 300
						}, function(o) {
							ca(o, b)
						});
		$.fed.util
				.autocomplete(
						"#simpleSearchInput",
						{
							url : "http://trading.5173.com/browse/KeywordSearch.aspx?action=search&count=10",
							delay : 300
						}, function(o) {
							ca(o, j)
						});
		E.keydown(function(o) {
			o.keyCode === 13 && J.trigger("click")
		});
		J
				.click(function(o) {
					var v = E.val().replace(/<[\/]*script[\s\S]*?>/ig, ""), D = escape(v);
					if (!(!v || v === "\u8bf7\u8f93\u5165\u6c49\u5b57\u6216\u62fc\u97f3")) {
						$
								.ajax({
									type : "GET",
									url : "http://"
											+ y
											+ "/ajax.axd?methodName=GETSEARCHEDGAMES&cache=600&keyword="
											+ D + "&count=10",
									dataType : "jsonp",
									scriptCharset : "GBK",
									jsonp : "jsoncallback",
									jsonpCallback : "callfastsearch",
									cache : true,
									success : function(I) {
										var z = "";
										if (I.length) {
											z = '<li class="gs_search_result">\u60a8\u641c\u7d22\u4e86\u201c <span class="f_f60">'
													+ v
													+ "</span> \u201d\uff0c\u8981\u641c\u7d22\u7684\u662f\u4e0d\u662f\uff1a</li>";
											$
													.each(
															I,
															function(U, O) {
																z += '<li><a href="#" gameid="'
																		+ O.gameid
																		+ (O.areaid ? '" gamename="'
																				+ O.gamename
																				+ '" areaid="'
																				+ O.areaid
																				+ '" areaname="'
																				+ O.areaname
																				+ '">'
																				+ O.gamename
																				+ "/"
																				+ O.areaname
																				: '">'
																						+ O.gamename)
																		+ "</a></li>"
															})
										} else
											z = '<li class="gs_search_result">\u5f88\u62b1\u6b49\uff0c\u6ca1\u6709\u641c\u7d22\u5230\u201c <span class="f_f60">'
													+ v
													+ '</span> \u201d\u76f8\u5173\u6e38\u620f\u3002</li><li class="gs_none_result">\u53cb\u60c5\u63d0\u793a\uff1a</li><li class="gs_none_result">1. \u5728\u8f93\u5165\u6846\u5185\u6b63\u786e\u8f93\u5165\u6e38\u620f\u540d\u79f0\u4e2d\u4efb\u610f\u6c49\u5b57\u3001\u4efb\u610f\u4e24\u5b57\u62fc\u97f3\u5168\u62fc\u6216\u8005\u5b57\u6bcd\uff1b</li><li class="gs_none_result">2. \u901a\u8fc7\u6e38\u620f\u540d\u79f0\u62fc\u97f3\u7d22\u5f15\u641c\u7d22\u3002</li><li class="gs_add_name"><b>\u627e\u4e0d\u5230\u60f3\u8981\u7684\u6e38\u620f\u3001\u533a\u670d\uff0c\u60a8\u53ef\u4ee5<a href="http://trading.5173.com/UpInfo/UpGameAreaServer.aspx" target="_blank">\u70b9\u6b64\u6dfb\u52a0</a></b></li>';
										t.find("a.current").removeClass(
												"current");
										M.show();
										L.addClass("current").data(
												"GAMEHTML_FASTSEARCH", z);
										x.html(z)
									}
								});
						q
								&& __utmTrackEvent(
										"%E9%A6%96%E9%A1%B5%E7%82%B9%E5%87%BB",
										"%E5%9B%9B%E7%BA%A7%E8%81%94%E5%8A%A8",
										"%E5%BF%AB%E9%80%9F%E6%90%9C%E7%B4%A2-"
												+ encodeURIComponent("\u5feb\u901f\u641c\u7d22-"
														+ v))
					}
					o.preventDefault()
				});
		e.click(function() {
			var o = G || "", v = f.find("li.hide .gs_area").val();
			v = v === "-1" ? "" : v;
			$(this).attr(
					"href",
					"http://trading.5173.com/UpInfo/UpGameAreaServer.aspx?gm="
							+ o + "&ga=" + v)
		});
		$.initGameSelect = function(o) {
			s = true;
			N = o;
			if (o.game && o.game.name) {
				k.text(o.game.name).attr("title", o.game.name);
				G = window.cacheGameId = o.game.id
			}
			if (o.area && o.area.name) {
				l.text(o.area.name).attr("title", o.area.name);
				h.filter(".gs_area").val(o.area.id)
			}
			if (o.server && o.server.name) {
				m.text(o.server.name).attr("title", o.server.name);
				h.filter(".gs_server").val(o.server.id)
			}
			if (o.type && o.type.name) {
				p.text(o.type.name).attr("title", o.type.name);
				h.filter(".gs_type").val(o.type.id)
			}
			o.keyword && b.val(o.keyword)
		}
	}
});
(function(a) {
	a.fed = a.fed || {};
	a
			.extend(
					a.fed,
					{
						footer : function() {
							a("#footer1000").length > 0 ? a("#footer1000")
									.html(
											"<ul class='footer_nav'>\t<li><a href='http://www.5173.com/About' target='_blank' rel='nofollow'>\u5173\u4e8e\u6211\u4eec</a>|</li>\t<li><a href='http://www.5173.com/About/Partners' target='_blank' rel='nofollow'>\u5408\u4f5c\u4f19\u4f34</a>|</li>\t<li><a href='http://www.5173.com/About/Cooperation' target='_blank' rel='nofollow'>\u5408\u4f5c\u8054\u7cfb</a>|</li>\t<li><a href='http://www.5173.com/About/Protection' target='_blank' rel='nofollow'>\u9690\u79c1\u4fdd\u62a4</a>|</li>\t<li><a href='http://sc.5173.com/' target='_blank' rel='nofollow'>\u6295\u8bc9\u5efa\u8bae</a>|</li>\t<li><a href='http://www.5173.com/About/Copyright' target='_blank' rel='nofollow'>\u514d\u8d23\u58f0\u660e</a>|</li>\t<li><a href='http://www.5173.com/about/job' target='_blank' rel='nofollow'>\u8bda\u8058\u82f1\u624d</a></li></ul><p class='copyright'>Copyright \u00a9 2002-2015 5173.com \u7248\u6743\u6240\u6709<a href='http://www.miitbeian.gov.cn/' target='_blank' rel='nofollow'>ICP\u8bc1\uff1a\u6d59B2-20090127 \uff08\u91d1\u534e\u6bd4\u5947\u7f51\u7edc\u6280\u672f\u6709\u9650\u516c\u53f8\uff09</a><a href='http://images001.5173cdn.com/html/license/license.html' target='_blank' rel='nofollow'>\u3010\u7f51\u7edc\u6587\u5316\u7ecf\u8425\u8bb8\u53ef\u8bc1\u3011\u6d59\u7f51\u6587[2013]0619-067\u53f7</a></p><ul class='honor_list'>\t<li class='item1'><a href='http://help.5173.com/winner100/winner100.html' target='_blank' rel='nofollow'>\u4e9a\u6d32\u4e2d\u5c0f\u79c1\u8425\u4f01\u4e1a100\u5f3a</a></li>\t<li class='item2'><a href='http://www.ectrustprc.org.cn/seal/splash/2000003.htm'  target='_blank' rel='nofollow'>\u4e2d\u56fd\u7535\u5b50\u5546\u52a1\u8bda\u4fe1\u5355\u4f4d</a></li>\t<li class='item4'><a href='http://help.5173.com/cxlm/01.html' target='_blank' rel='nofollow'>\u53cd\u76d7\u53f7\u7eff\u8272\u8054\u76df</a></li>   <li class='item8'><a href='https://search.szfw.org/cert/l/CX20141230006228006321' target='_blank' rel='nofollow'>\u8bda\u4fe1\u7f51\u7ad9</a></li></ul><ul class='honor_list honor_list_2' style='padding-left:234px;'>\t<li class='item6'><a href='http://www.miitbeian.gov.cn/state/outPortal/loginPortal.action'  target='_blank' rel='nofollow'>\u5de5\u4fe1\u90e8\u57df\u540d\u5907\u6848\u7ba1\u7406\u7cfb\u7edf </a></li>\t<li class='item3'><a href='http://www.315online.com.cn/member/315090077.html' target='_blank' rel='nofollow'>\u7f51\u4e0a\u4ea4\u6613\u4fdd\u969c\u4e2d\u5fc3</a></li>\t<li class='item7'><a href='http://www.idinfo.cn/SignHandle?userID=3307030000019094' target='_blank' rel='nofollow'>\u4f01\u4e1a\u6cd5\u4eba\u8425\u4e1a\u6267\u7167</a></li>\t<li class='item9'><a href='http://www.51315.cn/' target='_blank' rel='nofollow'>\u8bda\u4fe1\u5728\u7ebf</a></li></ul>")
									: a("body")
											.append("<div></div>")
											.append(
													a(
															"<div class='footer'></div>")
															.html(
																	"<ul class='footer_nav'>\t<li><a href='http://www.5173.com/About' target='_blank' rel='nofollow'>\u5173\u4e8e\u6211\u4eec</a>|</li>\t<li><a href='http://www.5173.com/About/Partners' target='_blank' rel='nofollow'>\u5408\u4f5c\u4f19\u4f34</a>|</li>\t<li><a href='http://www.5173.com/About/Cooperation' target='_blank' rel='nofollow'>\u5408\u4f5c\u8054\u7cfb</a>|</li>\t<li><a href='http://www.5173.com/About/Protection' target='_blank' rel='nofollow'>\u9690\u79c1\u4fdd\u62a4</a>|</li>\t<li><a href='http://sc.5173.com/' target='_blank' rel='nofollow'>\u6295\u8bc9\u5efa\u8bae</a>|</li>\t<li><a href='http://www.5173.com/About/Copyright' target='_blank' rel='nofollow'>\u514d\u8d23\u58f0\u660e</a>|</li>\t<li><a href='http://www.5173.com/about/job' target='_blank' rel='nofollow'>\u8bda\u8058\u82f1\u624d</a></li></ul><p class='copyright'>Copyright \u00a9 2002-2015 5173.com \u7248\u6743\u6240\u6709<a href='http://www.miitbeian.gov.cn/' target='_blank' rel='nofollow'>ICP\u8bc1\uff1a\u6d59B2-20090127 \uff08\u91d1\u534e\u6bd4\u5947\u7f51\u7edc\u6280\u672f\u6709\u9650\u516c\u53f8\uff09</a><a href='http://images001.5173cdn.com/html/license/license.html' target='_blank' rel='nofollow'>\u3010\u7f51\u7edc\u6587\u5316\u7ecf\u8425\u8bb8\u53ef\u8bc1\u3011\u6d59\u7f51\u6587[2013]0619-067\u53f7</a></p><ul class='honor_list'>\t<li class='item1'><a href='http://help.5173.com/winner100/winner100.html' target='_blank' rel='nofollow'>\u4e9a\u6d32\u4e2d\u5c0f\u79c1\u8425\u4f01\u4e1a100\u5f3a</a></li>\t<li class='item2'><a href='http://www.ectrustprc.org.cn/seal/splash/2000003.htm'  target='_blank' rel='nofollow'>\u4e2d\u56fd\u7535\u5b50\u5546\u52a1\u8bda\u4fe1\u5355\u4f4d</a></li>\t<li class='item4'><a href='http://help.5173.com/cxlm/01.html' target='_blank' rel='nofollow'>\u53cd\u76d7\u53f7\u7eff\u8272\u8054\u76df</a></li>   <li class='item8'><a href='https://search.szfw.org/cert/l/CX20141230006228006321' target='_blank' rel='nofollow'>\u8bda\u4fe1\u7f51\u7ad9</a></li></ul><ul class='honor_list honor_list_2' style='padding-left:234px;'>\t<li class='item6'><a href='http://www.miitbeian.gov.cn/state/outPortal/loginPortal.action'  target='_blank' rel='nofollow'>\u5de5\u4fe1\u90e8\u57df\u540d\u5907\u6848\u7ba1\u7406\u7cfb\u7edf </a></li>\t<li class='item3'><a href='http://www.315online.com.cn/member/315090077.html' target='_blank' rel='nofollow'>\u7f51\u4e0a\u4ea4\u6613\u4fdd\u969c\u4e2d\u5fc3</a></li>\t<li class='item7'><a href='http://www.idinfo.cn/SignHandle?userID=3307030000019094' target='_blank' rel='nofollow'>\u4f01\u4e1a\u6cd5\u4eba\u8425\u4e1a\u6267\u7167</a></li>\t<li class='item9'><a href='http://www.51315.cn/' target='_blank' rel='nofollow'>\u8bda\u4fe1\u5728\u7ebf</a></li></ul>"))
						}
					})
})(jQuery);
$(function() {
	try {
		FED_AUTOFOOTER && $.fed.footer()
	} catch (a) {
		$.fed.footer()
	}
	window.status = "\u6211\u4eec\u53d1\u6398\u4e86\u865a\u62df\u7269\u54c1\u4ea4\u6613\u884c\u4e1a\uff0c\u5e76\u4e00\u76f4\u5f15\u9886\u5b83\u524d\u8fdb\uff01\uff01"
});
(function(a) {
	a.fn.showMsg = function(f, d) {
		var c = {
			type : "info",
			msg : "",
			msgId : ""
		};
		if (f != null)
			c = a.extend(c, f);
		if (c.msgId) {
			f = a(c.msgId);
			c.type != "" && c.type != "info"
					&& c.msg.indexOf("fed_formtips") < 0 ? f
					.html('<span class="fed_formtips_' + c.type
							+ '" ><s class="ico_' + c.type + '_1"></s>' + c.msg
							+ "</span>") : f.html(c.msg)
		} else {
			var b = a(this).attr("id") + "_msg";
			f = a("#" + b);
			if (f.length == 0) {
				f = a('<div class="fed_show_msg"  id="' + b + '" ></div>');
				a(this).parent().after(f)
			}
			f.html('<span class="fed_formtips_' + c.type + '" ><s class="ico_'
					+ c.type + '_1"></s>' + c.msg + "</span>")
		}
		c.type == "error" ? a(this).data("valid", false) : a(this).data(
				"valid", true);
		d && d();
		return this
	};
	a.fn.setValid = function(f) {
		a(this).data("valid", f);
		return this
	};
	a.fn.resetMsg = function(f, d) {
		var c = {
			msgId : ""
		};
		if (f != null)
			c = a.extend(c, f);
		if (c.msgId)
			f = a(c.msgId);
		else {
			f = a(this).attr("id") + "_msg";
			f = a("#" + f)
		}
		f.html("");
		d && d();
		return this
	}
})(jQuery);
(function(a) {
	a
			.extend({
				fedDownScroll : function(f) {
					var d = a.extend({
						element : null,
						startIndex : 0,
						speed : 0,
						duration : 600
					}, f), c = a(d.element), b = c.children("li").length, g = d.startIndex, e = b
							- g, h = 0, j, k;
					b -= 1;
					var l = function() {
						j = c.children("li").eq(g);
						j.css({
							display : "none",
							opacity : "0"
						});
						c.prepend(j);
						j.slideDown(600).animate({
							opacity : "1"
						}, 600, function() {
							a(this).css({
								opacity : ""
							})
						});
						if (h < 2)
							if (g === b) {
								h += 1;
								g = h < 2 ? e : b
							} else
								g += 1;
						else
							g = b
					};
					k = setInterval(l, d.speed);
					c.hover(function() {
						if (k) {
							clearInterval(k);
							k = null
						}
					}, function() {
						k = setInterval(l, d.speed)
					})
				}
			})
})(jQuery);
(function(a) {
	a.fn.imglazyload = function(f) {
		var d = a.extend({
			attr : "lazy-src",
			container : window,
			event : "scroll",
			fadeIn : false,
			threshold : 0,
			vertical : true,
			loadScript : false,
			callback : null
		}, f);
		f = d.event;
		var c = d.vertical, b = a(d.container), g = d.threshold, e = d.loadScript, h = d.callback, j = a.famsg.loadQueue, k = a.famsg.loadKjImg, l = a
				.makeArray(a(this)), m = c ? "top" : "left", p = c ? "scrollTop"
				: "scrollLeft", w = c ? b.height() : b.width(), F = b[p](), n = w
				+ F, q = {
			init : function(y) {
				return y >= F && y <= n + g
			},
			scroll : function(y) {
				var B = b[p]();
				return y >= B && y <= w + B + g
			},
			resize : function(y) {
				var B = b[p](), H = c ? b.height() : b.width();
				return y >= B && y <= H + B + g
			}
		}, r = function(y, B) {
			var H = 0, x = false, t, C, u, A;
			if (B) {
				if (B !== "scroll" && B !== "resize")
					x = true
			} else
				B = "init";
			for (; H < l.length; H++) {
				t = false;
				C = l[H];
				u = a(C);
				if (!e && !h) {
					A = u.attr(d.attr);
					if (!A)
						continue
				}
				t = u.data("imglazyload_offset");
				if (t === undefined) {
					t = e || h ? u.parent().offset()[m] : u.offset()[m];
					u.data("imglazyload_offset", t)
				}
				if (t = x || q[B](t)) {
					if (e)
						(function(E) {
							j(function() {
								k(E)
							})
						})(u);
					else if (h)
						h.call(u);
					else {
						C.src = A;
						d.fadeIn && u.hide().fadeIn();
						u.removeAttr("lazy-src")
					}
					u.removeData("imglazyload_offset");
					l.splice(H--, 1)
				}
			}
			if (!l.length) {
				y ? y.unbind(B, s) : b.unbind(d.event, s);
				a(window).unbind("resize", s);
				l = null
			}
			C = u = null
		}, s = function(y) {
			r(a(this), y.type)
		};
		b = f === "scroll" ? b : a(this);
		b.bind(f, s);
		a(window).bind("resize", s);
		r();
		return this
	}
})(jQuery);
(function(a) {
	a.fshare = a.fshare || {};
	a
			.extend(
					a.fshare,
					{
						bind : function(f, d) {
							for (var c = d.list, b = a("<ul></ul>"), g = 0, e = c.length; g < e; g++) {
								var h = c[g], j = "";
								if (d.showtext)
									j = h.text;
								j = a("<li></li>")
										.append(
												a("<a href='javascript:void(0);' class='site_"
														+ h.site
														+ "'  title='"
														+ h.text
														+ "'>"
														+ j
														+ "</a>"));
								d.showtext && j.hover(function() {
									a(this).addClass("listcurrent")
								}, function() {
									a(this).removeClass("listcurrent")
								});
								b.append(j);
								j.bind("click", {
									config : h
								}, function(k) {
									var l = k.data.config;
									k = a.extend(l, k.data.config.data);
									if (l.extend)
										k = a.extend(k, l.extend);
									l.track != null && l.track != "undefind"
											&& l.track();
									switch (k.site) {
									case "renren":
										a.fshare.renren(k);
										break;
									case "pengyou":
										a.fshare.pengyou(k);
										break;
									case "qzone":
										a.fshare.qzone(k);
										break;
									case "qweibo":
										a.fshare.qweibo(k);
										break;
									case "weibo":
										a.fshare.weibo(k);
										break;
									case "kaixin":
										a.fshare.kaixin(k);
										break;
									case "sohu":
										a.fshare.sohu(k);
										break
									}
								})
							}
							a(f).append(b)
						},
						weibo : function(f) {
							var d = {
								site : "weibo",
								url : document.location.href,
								title : document.title,
								content : document.title,
								pic : "",
								appkey : "4034240183",
								width : 615,
								height : 505
							};
							d = a.extend(d, f);
							window.open(
									"http://service.weibo.com/share/share.php?url="
											+ encodeURIComponent(d.url)
											+ "&appkey=" + d.appkey + "&title="
											+ encodeURIComponent(d.title)
											+ "&pic="
											+ encodeURIComponent(d.pic)
											+ "&ralateUid=2492379130",
									"_blank", "width=" + d.width + ",height="
											+ d.height);
							return false
						},
						qweibo : function(f) {
							var d = {
								site : "qweibo",
								url : document.location.href,
								title : document.title,
								content : document.title,
								pic : "",
								appkey : "appkey",
								width : 700,
								height : 680
							};
							d = a.extend(d, f);
							var c = encodeURI(d.title), b = encodeURIComponent(d.url), g = encodeURI(d.appkey), e = encodeURI(d.pic), h = "http://www.5173.com", j = "http://v.t.qq.com/share/share.php?url="
									+ b
									+ "&appkey="
									+ g
									+ "&site="
									+ h
									+ "&pic=" + e + "&title=" + c;
							window
									.open(
											j,
											"",
											"width="
													+ d.width
													+ ", height="
													+ d.height
													+ ", top=0, left=0, toolbar=no, menubar=no, scrollbars=no, location=yes, resizable=no, status=no")
						},
						qzone : function(f) {
							var d = {
								site : "qzone",
								url : document.location.href,
								title : document.title,
								content : document.title,
								pic : ""
							};
							d = a.extend(d, f);
							window
									.open("http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?title="
											+ encodeURIComponent(d.title)
											+ "&url="
											+ encodeURIComponent(d.url));
							return false
						},
						pengyou : function(f) {
							var d = {
								site : "pengyou",
								url : document.location.href,
								title : document.title,
								content : document.title,
								pic : ""
							};
							d = a.extend(d, f);
							window
									.open("http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?to=pengyou&title="
											+ encodeURIComponent(d.title)
											+ "&url="
											+ encodeURIComponent(d.url));
							return false
						},
						renren : function(f) {
							var d = {
								site : "renren",
								url : document.location.href,
								title : document.title,
								content : document.title,
								pic : ""
							};
							d = a.extend(d, f);
							(function(c, b, g) {
								function e() {
									if (!window
											.open(
													[ h, k ].join(""),
													"xnshare",
													[
															"toolbar=0,status=0,resizable=1,width=626,height=436,left=",
															(c.width - 626) / 2,
															",top=",
															(c.height - 436) / 2 ]
															.join("")))
										j.href = [ h, k ].join("")
								}
								if (!/renren\.com/.test(b.location)) {
									var h = "http://share.renren.com/share/buttonshare?link=", j = d.url;
									b = d.title;
									var k = [ g(j), "&title=", g(b) ].join("");
									/Firefox/.test(navigator.userAgent) ? setTimeout(
											e, 0)
											: e()
								}
							})(screen, document, encodeURIComponent)
						},
						kaixin : function(f) {
							var d = {
								site : "kaixin",
								url : document.location.href,
								title : document.title,
								content : document.title,
								pic : ""
							};
							d = a.extend(d, f);
							f = d.content;
							(function(c, b) {
								var g = document.createElement("form");
								g.id = "KXtempRepasteForm";
								g.method = "post";
								g.action = c;
								g.target = "_blank";
								c = document.createElement("input");
								c.type = "hidden";
								c.name = "rcontent";
								c.value = b;
								g.appendChild(c);
								document.body.appendChild(g);
								g.submit();
								document.body.removeChild(g)
							})(
									"http://www.kaixin001.com/repaste/bshare.php?rtitle="
											+ encodeURIComponent(d.title)
											+ "&rurl="
											+ encodeURIComponent(d.url), f)
						},
						sohu : function(f) {
							var d = {
								site : "sohu",
								url : document.location.href,
								title : document.title,
								content : document.title,
								pic : ""
							};
							d = a.extend(d, f);
							(function(c, b, g, e, h, j, k, l, m) {
								function p() {
									if (!window
											.open(
													[ w, j ].join(""),
													"mb",
													[
															"toolbar=0,status=0,resizable=1,width=660,height=470,left=",
															(c.width - 660) / 2,
															",top=",
															(c.height - 470) / 2 ]
															.join("")))
										F.href = [ w, j ].join("")
								}
								var w = "http://t.sohu.com/third/post.jsp?", F = l
										|| b.location;
								j = [ "&appkey=B8tHBZJdGhYTMPYHb5tS&url=",
										g(F), "&title=", g(k || b.title),
										"&content=", m || "gb2312", "&pic=",
										g(j || "") ].join("");
								/Firefox/.test(navigator.userAgent) ? setTimeout(
										p, 0)
										: p()
							})(screen, document, encodeURIComponent, "", "",
									"", d.content, d.url, "utf-8")
						}
					})
})(jQuery);
function getFilterUrl(a, f) {
	var d = "", c = a.split("?");
	a = c[0];
	if (c[1]) {
		c = c[1].split("&");
		for (var b = 0; b < c.length; b++) {
			for (var g = true, e = 0; e < f.length; e++)
				if (c[b].indexOf(f[e]) > -1)
					g = false;
			if (g == true) {
				if (d != "")
					d += "&";
				d += c[b]
			}
		}
	}
	return d == "" ? a : a + "?" + d
};
(function(a) {
	a.fed = a.fed || {};
	var f = function(d) {
		this.$wrap = a(d);
		this.outCon = this.index = 0;
		this.$wrap
				.append('\t<div class="fed-ig-wrap"><a class="fed-ig-leftCtl"></a><a class="fed-ig-rightCtl""></a></div><div class="fed-ig-list"></div>');
		this.$imgCon = this.$wrap.find(".fed-ig-wrap");
		this.$liCon = this.$wrap.find(".fed-ig-list");
		this.$lefCtrl = this.$wrap.find(".fed-ig-leftCtl");
		this.$rightCtrl = this.$wrap.find(".fed-ig-rightCtl");
		return this
	};
	f.prototype.init = function(d) {
		var c = {
			imgsTitle : {},
			imgData : [],
			hasAccros : true,
			firstOne : 0
		};
		a.extend(c, d);
		if (!c.imgData.length) {
			a(c.wrap).hide();
			a(c.imgList).hide()
		}
		this.createDom(c);
		this.control();
		this.transform();
		this.preLoad("." + this.$liCon.attr("class"), function() {
		});
		this.setHeight()
	};
	f.prototype.setHeight = function() {
		var d = a(".fed-ig-wrap").find("img").eq(0).css("height");
		a(".fed-ig-wrap").css("height", d)
	};
	f.prototype.createDom = function(d) {
		var c = this.$imgCon, b = this.$liCon, g = d.imgData, e = b.parent()
				.width(), h = "<ul>", j = d.hasAccros ? '<div class="fed-tipsarr fed-arrtop"><span class="arr-1">\u25c6</span><span class="arr-2">\u25c6</span></div>'
				: "", k = "", l = 0;
		if (b.length) {
			for (var m = 0, p = g.length; m < p; m++) {
				k = m === d.firstOne ? 'class="current"'
						: m === g.length - 1 ? 'class="last"' : "";
				h += "<li " + k + ">" + j + '<a href="' + g[m].url
						+ '" title="' + (g[m].titles ? g[m].titles : "")
						+ '"><img src="' + (g[m].small ? g[m].small : "")
						+ '"/></a></li>'
			}
			h += "</ul>";
			b.html(h);
			m = 0;
			for (p = b.find("li").length; m < p; m++) {
				d = b.find("li").get(m);
				h = d.currentStyle ? parseInt(d.currentStyle.marginRight)
						: parseInt(window.getComputedStyle(d, false).marginRight);
				l += parseInt(d.offsetWidth) + h;
				if (l > parseInt(e) && this.outCon === 0)
					this.outCon = m
			}
			if (l > parseInt(e)) {
				b.css("height", parseInt(b.find("li").get(0).offsetHeight) + 10
						+ "px");
				b.children().css("width", l + "px")
			}
		}
		if (c.length) {
			for (m = 0; m < g.length; m++)
				if (m == 0)
					c.append('<a class="picinfo" target="' + g[m].target
							+ '" style="" rel="pics"  href="' + g[m].url
							+ '"><img src="' + g[m].bigger + '" alt="'
							+ g[m].alt + '"></a>');
				else
					m == 1 ? c.append('<a class="picinfo" target="'
							+ g[m].target
							+ '" style="display:none;" rel="pics" href="'
							+ g[m].url + '"><img src="' + g[m].bigger
							+ '" alt="' + g[m].alt + '"></a>')
							: c
									.append('<a class="picinfo" target="'
											+ g[m].target
											+ '" style="display:none;" rel="pics" href="'
											+ g[m].url
											+ '"><img data-lazysrc="'
											+ g[m].bigger
											+ '" src="http://img01.5173cdn.com/fed/build/1.00/images/placeholder.png" /></a>');
			c = c.height() / 2 - this.$lefCtrl.height() / 2;
			this.$lefCtrl.css("top", c);
			this.$rightCtrl.css("top", c)
		}
		this.index = b.find("li.current").index()
	};
	f.prototype.preLoad = function(d, c) {
		d = a(d).find("img[bigsrc]");
		for (var b = [], g = 0, e = d.length; g < e; g++)
			b.push(d[g].getAttribute("bigsrc"));
		var h = function() {
			var j = new Image;
			j.onload = function() {
				b.splice(0, 1);
				c && c.call(this);
				b.length && h()
			};
			if (b[0])
				j.src = b[0]
		};
		h()
	};
	f.prototype.transform = function() {
		var d, c, b, g, e = a("a[rel=pics] img").eq(0), h = a("a[rel=pics] img")
				.eq(0).attr("src"), j = new Image;
		j.src = h;
		j.onload = function() {
			d = j.width;
			c = j.height;
			b = e.parent().width();
			g = e.parent().height();
			e.attr({
				oWidth : d,
				oHeight : c
			});
			var k = 0, l = 0;
			if (d > 0 && c > 0) {
				if (d <= b && c <= g)
					if (d > b) {
						k = b;
						l = c * b / d
					} else {
						k = d;
						l = c
					}
				else if (d / c > b / g) {
					k = b;
					l = c / d * d
				} else {
					k = d * g / c;
					l = g
				}
				e.css({
					width : k,
					height : l
				})
			}
		}
	};
	f.prototype.control = function() {
		var d = this, c = d.$lefCtrl, b = d.$rightCtrl, g = d.$liCon, e = g
				.children(), h = e.children().length, j = "."
				+ this.$lefCtrl.attr("class"), k = "."
				+ this.$rightCtrl.attr("class"), l = "."
				+ this.$liCon.attr("class"), m = "."
				+ this.$imgCon.attr("class"), p = function() {
			if (h > 1) {
				c.is(":visible") || c.show();
				b.is(":visible") || b.show();
				if (d.index === 0)
					c.hide();
				else
					d.index === h - 1 && b.hide()
			} else {
				c.hide();
				b.hide()
			}
		}, w = function(n, q) {
			var r = new Image;
			r.onload = function() {
				var s = r.width, y = r.height, B = n.parent().width(), H = n
						.parent().height();
				n.attr({
					oWidth : s,
					oHeight : y
				});
				var x = 0, t = 0;
				if (s > 0 && y > 0) {
					if (s <= B && y <= H) {
						x = s;
						t = y
					} else if (s / y > B / H) {
						x = B;
						t = y / s * s
					} else {
						x = s * H / y;
						t = H
					}
					n.css({
						width : x,
						height : t
					})
				}
				n.parent().parent().css("height", t)
			};
			r.src = q
		}, F = function() {
			var n = e.find("li"), q = Math.floor(d.outCon / 2), r = q, s = n
					.get(0).currentStyle ? parseInt(n.get(0).currentStyle.marginRight)
					: parseInt(window.getComputedStyle(n.get(0), false).marginRight), y = parseInt(n
					.get(0).offsetWidth)
					+ s;
			return function(B) {
				var H = 0;
				if (Object.prototype.toString.apply(B).toLowerCase() === "[object number]")
					d.index = B;
				d.$imgCon.find("a:has(img)").siblings().hide();
				d.$imgCon.find("a:has(img)").eq(d.index).fadeIn(300);
				n.siblings().removeClass("current").eq(d.index).addClass(
						"current");
				if (d.outCon !== 0) {
					if (d.index > q || g.scrollLeft() > 0) {
						H = (d.index - q) * y;
						g.scrollLeft(H)
					}
					r = d.index
				}
			}
		}();
		this.$wrap.delegate(k, "click", function(n) {
			n.preventDefault();
			d.index++;
			n = d.index;
			a("a[rel=pics] img");
			var q = a("a[rel=pics] img").eq(n), r = a("a[rel=pics] img").eq(n)
					.attr("data-lazysrc")
					|| a("a[rel=pics] img").eq(n).attr("src");
			hsrcvalue = a("a[rel=pics] img").eq(n).attr("data-lazysrc");
			a("a[rel=pics] img")[n].src = r;
			w(q, r);
			F();
			p()
		});
		this.$wrap.delegate(j, "click", function(n) {
			n.preventDefault();
			d.index--;
			n = d.index;
			a("a[rel=pics] img");
			var q = a("a[rel=pics] img").eq(n), r = a("a[rel=pics] img").eq(n)
					.attr("data-lazysrc")
					|| a("a[rel=pics] img").eq(n).attr("src");
			hsrcvalue = a("a[rel=pics] img").eq(n).attr("data-lazysrc");
			a("a[rel=pics] img")[n].src = r;
			w(q, r);
			F();
			p()
		});
		this.$wrap.delegate(l + " li", "click", function(n) {
			n.preventDefault();
			n = a(this).index();
			a("a[rel=pics] img");
			var q = a("a[rel=pics] img").eq(n);
			d.index = n;
			a("a[rel=pics] img").eq(n).attr("data-lazysrc");
			var r = a("a[rel=pics] img").eq(n).attr("data-lazysrc")
					|| a("a[rel=pics] img").eq(n).attr("src");
			a("a[rel=pics] img")[n].src = r;
			w(q, r);
			a("a[rel=pics]").hide();
			a("a[rel=pics]").eq(n).fadeIn(300).css("display", "block");
			e.find("li").siblings().removeClass("current").eq(n).addClass(
					"current")
		});
		this.$wrap
				.delegate(
						m,
						"hover",
						function(n) {
							if (n.type === "mouseenter") {
								d.$liCon.find("li.current").index() !== d.$liCon
										.find("li").length - 1
										&& a(this)
												.children("a.fed-ig-rightCtl")
												.show();
								d.$liCon.find("li.current").index() !== 0
										&& a(this).children("a.fed-ig-leftCtl")
												.show()
							} else
								a(this)
										.children(
												"a.fed-ig-leftCtl:visible,a.fed-ig-rightCtl:visible")
										.hide()
						})
	};
	a.extend(a.fed, {
		imagegallery : function(d, c) {
			(new f(d)).init(c)
		}
	})
})(jQuery);
(function(a) {
	a.fed = a.fed || {};
	a
			.extend(
					a.fed,
					{
						showFP : function(f) {
							var d, c = {
								type : 1
							};
							if (f != null)
								c = a.extend(c, f);
							f = '\t\u8bf7\u60a8\u63d0\u9ad8\u8b66\u60d5\uff0c\u5982\u6709\u7591\u95ee\u53ef\u8054\u7cfb\u9632\u9a97\u5ba2\u670d&nbsp;<a href="###" onclick="NTKF.im_openInPageChat(\'bq_1005_1413961920197\')" class="v_align v_align_qq v_xiaoneng"><img border="0" src="http://images001.5173cdn.com/images/my5173/v4/bg/icon-xiaoneng_default.png" alt="\u70b9\u51fb\u8fd9\u91cc\u7ed9\u6211\u53d1\u6d88\u606f" title="\u70b9\u51fb\u8fd9\u91cc\u7ed9\u6211\u53d1\u6d88\u606f" class="v_align"></a>\u3002<a href="http://safe.5173.com/SafeCenter/dynamic/kefuyanzhen.html" target="_blank" class="f_blue">\u9a8c\u8bc1\u771f\u5047\u5ba2\u670d</a> <a href="http://bar.5173.com/showtopic-803486.aspx" target="_blank" class="f_blue">\u67e5\u770b\u66f4\u591a\u9a97\u672f</a><p><span class="btn_nor" id="fed_fp_btnno_'
									+ c.type
									+ '">\uff083\u79d2\uff09\u6211\u5df2\u9605\u8bfb\u4ee5\u4e0a\u4fe1\u606f</span><a class="btn_cur" id="fed_fp_btnyes_'
									+ c.type
									+ '" href="#">\u6211\u5df2\u9605\u8bfb\u4ee5\u4e0a\u4fe1\u606f</a></p>';
							if (c.type == 1 || c.type == 2)
								d = '<div id="fppop" class="UED_hide"><div class="fp_bg"><span class="fp_img"></span><h1>5173\u9632\u9a97\u63d0\u9192!</h1><h2>\u60a8\u76ee\u524d\u5f88\u53ef\u80fd\u5df2\u7ecf\u88ab\u9a97\uff0c\u8bf7\u5bf9\u6bd4\u4ee5\u4e0b3\u79cd\u8bc8\u9a97\u65b9\u5f0f\u4e0e\u60a8\u662f\u5426\u76f8\u7b26\uff1a</h2><div class="w_500"><ul class="fp_infor"><li>1\uff0e\u9a97\u5b50\u501f\u53e3\u8d2d\u4e70\u70b9\u5361\u53ef\u4ee5\u6362\u53d6\u6e38\u620f\u5e01\u88c5\u5907\uff0c\u7a0d\u540e\u8ba9\u60a8\u63d0\u4f9b\u70b9\u5361\u5361\u53f7\u548c\u5bc6\u7801\u3002</li><li>2\uff0e\u5047\u5ba2\u670d\u63d0\u51fa\u51fa\u552e\u5546\u54c1\u9700\u8981\u4ea4\u7eb3\u62bc\u91d1\u3001\u4fdd\u8bc1\u91d1\u3001\u4fdd\u9669\u91d1\u3001\u6fc0\u6d3b\u8d39\u3001\u4fe1\u7528\u70b9\u7b49\u3002</li><li class="bor_none">3\uff0e\u5176\u4ed6\u8ba9\u60a8\u505a\u517c\u804c\u3001\u5237\u4fe1\u8a89\u3001\u505a\u4efb\u52a1\u8fd4\u5229\u7b49\u5e38\u7528\u9a97\u672f\u3002</li></ul>'
										+ f + "</div></div></div>";
							a("body").append(d);
							a.LAYER.show({
								id : "fppop"
							});
							var b = [], g = [];
							b = 3;
							g = setInterval(
									function() {
										b -= 1;
										a("#fed_fp_btnno_" + c.type)
												.html(
														"\uff08"
																+ b
																+ "\u79d2\uff09\u6211\u5df2\u9605\u8bfb\u4ee5\u4e0a\u4fe1\u606f");
										if (b <= 0) {
											a("#fed_fp_btnno_" + c.type).hide();
											a(".btn_cur").css("display",
													"inline-block");
											clearInterval(g)
										}
									}, 1E3);
							a("#fed_fp_btnyes_" + c.type).click(function() {
								a.LAYER.close({
									id : "fppop"
								});
								a("#fppop").remove()
							});
							a(".v_xiaoneng").click(function() {
								a.LAYER.close({
									id : "fppop"
								});
								a("#fppop").remove()
							})
						}
					})
})(jQuery);
(function(a) {
	a.loadKJ = function() {
		var f = [], d = document.head
				|| document.getElementsByTagName("head")[0]
				|| document.documentElement, c = function(e) {
			f.push(e);
			f[0] !== "runing" && b()
		}, b = function() {
			var e = f.shift();
			if (e === "runing")
				e = f.shift();
			if (e) {
				f.unshift("runing");
				e()
			}
		}, g = function(e) {
			var h = e.val().match(/src="([\s\S]*?)"/i)[1], j = e[0].parentNode, k = document.write, l = document
					.createElement("script");
			document.write = function(m) {
				j.innerHTML = m
			};
			l.src = h;
			l.async = "async";
			l.onerror = l.onload = l.onreadystatechange = function() {
				if (/loaded|complete|undefined/.test(l.readyState)) {
					l.onerror = l.onload = l.onreadystatechange = null;
					document.write = k;
					d.removeChild(l);
					l = e = j = null;
					b()
				}
			};
			d.insertBefore(l, d.firstChild)
		};
		return function(e) {
			c(function() {
				g(e)
			})
		}
	}()
})(jQuery);
$(function() {
	$("#headerSwitchable li").one("loadScript", function() {
		$.loadKJ($(this).find("textarea.fragment_box"))
	});
	$("#headerSwitchable").switchable({
		delay : 5E3,
		effects : "horizontal",
		index : false,
		nav : false,
		callback : function() {
			$(this).next().trigger("loadScript")
		},
		init : function() {
			$(this).trigger("loadScript").next().trigger("loadScript")
		}
	})
});
(function(a) {
	var f = function() {
		var c = document.domain.split(".").reverse();
		return c[1] + "." + c[0]
	}, d = {
		close : function() {
			a(this).hide();
			var c = a(this).attr("id");
			a.cookie(d.make(c), "s", {
				path : "/",
				domain : f(),
				expires : 1E3
			});
			typeof arguments[0] === "function" && arguments[0]()
		},
		show : function() {
			var c = {
				left : 0,
				top : 0,
				zindex : 1
			};
			for (i = 0; i < arguments.length; i++)
				switch (typeof arguments[i]) {
				case "string":
					var b = a(arguments[i]);
					break;
				case "object":
					if (arguments[i] != null)
						c = a.extend(c, arguments[i]);
					break;
				case "function":
					var g = arguments[i];
					break
				}
			var e = a(this), h = e.attr("id");
			h = d.make(h);
			if (a.cookie(h) != "s") {
				e.show();
				var j = function() {
					var k = b.offset(), l = b.height();
					e.show().css({
						position : "absolute",
						left : k.left + c.left,
						top : k.top + l + c.top,
						"z-index" : c.zindex
					})
				};
				j();
				a(window).resize(function() {
					e.css("display") != "none" && window.setTimeout(function() {
						j()
					}, 100)
				});
				g && g()
			}
		},
		make : function(c) {
			return "guide_" + c
		}
	};
	a.fn.fedGuide = function(c) {
		if (d[c])
			return d[c].apply(this, Array.prototype.slice.call(arguments, 1));
		else if (typeof c === "object" || !c)
			return d.init.apply(this, arguments);
		else
			a.error("\u53ea\u80fd\u4f7f\u7528 show, close \u65b9\u6cd5");
		return this
	}
})(jQuery);
(function() {
	try {
		var a = {};
		a.referrer = document.referrer;
		a.r = Math.random();
		if (!a.referrer)
			try {
				if (window.opener)
					a.referrer = window.opener.location.href
			} catch (f) {
			}
		if (document) {
			a.url = document.URL;
			if (a.url) {
				var d = a.url, c = d.indexOf("://");
				if (c > 0)
					d = d.substr(c + 3, d.length - c - 3);
				var b = d.indexOf("?");
				if (b > 0)
					d = d.substr(0, b);
				var g = d.indexOf("/");
				if (g > 0)
					d = d.substr(0, g);
				a.domain = d
			}
			a.title = document.title
		}
		if (window && window.screen) {
			a.height = window.screen.height || 0;
			a.width = window.screen.width || 0;
			a.colorDepth = window.screen.colorDepth || 0
		}
		if (navigator) {
			a.language = navigator.language || "";
			a.agent = navigator.userAgent || ""
		}
		d = "";
		for ( var e in a) {
			if (d != "")
				d += "&";
			d += e + "=" + encodeURIComponent(a[e])
		}
		(new Image(1, 1)).src = ("https:" == document.location.protocol ? "https://"
				: "http://")
				+ "a.5173.com/__tra.gif?" + d;
		if (navigator.userAgent.indexOf("Chrome") != -1) {
			var h = function() {
				try {
					for (var k, l = document.cookie.split(";"), m = 0; m < l.length; m++) {
						for (var p = l[m]; p.charAt(0) == " ";)
							p = p.substring(1, p.length);
						if (p.indexOf("trace_5173") == 0) {
							k = p.substring(10, p.length);
							break
						}
					}
					if (k) {
						var w = new Date;
						w.setTime(w.getTime() + 6E4);
						var F = "trace_5173" + k + "; expires="
								+ w.toUTCString() + "; domain=5173.com; path=/";
						document.cookie = F
					}
					setTimeout(h, 4E4)
				} catch (n) {
				}
			};
			h()
		}
	} catch (j) {
	}
	return false
})();
var top_v3_json = {
	buyers : {
		name : "\u4e70\u5bb6\u4e2d\u5fc3",
		href : "http://user.5173.com/default.aspx",
		"class" : "",
		target : "_self",
		item : [
				{
					name : "\u4e70\u5bb6\u4e2a\u4eba\u4e2d\u5fc3",
					href : "http://user.5173.com/default.aspx",
					target : "_self",
					"class" : ""
				},
				{
					name : "\u6211\u8d2d\u4e70\u7684\u5546\u54c1",
					href : "http://trading.5173.com/list/viewmyorderlist.aspx",
					target : "_self",
					"class" : ""
				},
				{
					name : "\u6211\u7684\u5546\u57ce\u8ba2\u5355",
					href : "http://mall.5173.com/MyInfo/MallOrderList.aspx",
					target : "_self",
					"class" : "current"
				},
				{
					name : "\u6211\u7684\u4ee3\u7ec3\u8ba2\u5355",
					href : "http://dlc2c.5173.com/main/dlplayorderlist.aspx?ishistory=0",
					target : "_self",
					"class" : "current"
				},
				{
					name : "\u6211\u8981\u4e70",
					href : "http://trading.5173.com/",
					target : "_blank",
					"class" : ""
				},
				{
					name : "\u5145\u503c",
					href : "https://mypay.ebatong.com/charge/chargeebank.aspx?flag=1",
					target : "_blank",
					"class" : ""
				} ]
	},
	seller : {
		name : "\u5356\u5bb6\u4e2d\u5fc3",
		href : "http://user.5173.com/default.aspx",
		"class" : "",
		target : "_self",
		item : [ {
			"class" : "",
			name : "\u5356\u5bb6\u4e2a\u4eba\u4e2d\u5fc3",
			href : "http://user.5173.com/default.aspx",
			target : "_self"
		}, {
			"class" : "",
			name : "\u6211\u7684\u8ba2\u5355\u7ba1\u7406",
			href : "http://trading.5173.com/list/myorder_manage.aspx",
			target : "_self"
		}, {
			"class" : "",
			name : "\u51fa\u552e\u4e2d\u7684\u5546\u54c1",
			href : "http://trading.5173.com/list/viewmybizofferslist2.aspx",
			target : "_self"
		}, {
			"class" : "",
			name : "\u4ed3\u5e93\u4e2d\u7684\u5546\u54c1",
			href : "http://trading.5173.com/list/viewpausedbizofferslist.aspx",
			target : "_self"
		}, {
			"class" : "",
			name : "\u6700\u65b0\u6210\u4ea4\u4ef7\u683c",
			href : "http://trading.5173.com/list/ViewLastestDealList.aspx",
			target : "_self"
		}, {
			"class" : "",
			name : "\u6211\u7684\u4ee3\u7ec3\u8ba2\u5355",
			href : "http://dlc2c.5173.com/main/neworderlist.aspx?ishistory=0",
			target : "_self"
		}, {
			"class" : "",
			name : "\u6211\u8981\u5356",
			href : "http://trading.5173.com/auction/newpublish.aspx",
			target : "_blank"
		}, {
			"class" : "",
			name : "\u63d0\u73b0",
			href : "https://mypay.ebatong.com/charge/userwithdrawal.aspx",
			target : "_blank"
		} ]
	},
	recharge : {
		name : "\u5145\u503c",
		href : "https://mypay.ebatong.com/charge/chargeebank.aspx?flag=1",
		"class" : "",
		target : "_self"
	},
	withdrawals : {
		name : "\u63d0\u73b0",
		href : "https://mypay.ebatong.com/charge/userwithdrawal.aspx",
		"class" : "",
		target : "_self"
	},
	client : {
		name : "\u5ba2\u670d\u4e2d\u5fc3",
		href : "http://kf.5173.com",
		"class" : "",
		target : "_blank",
		item : [ {
			name : "\u54a8\u8be2\u4e2d\u5fc3",
			"class" : "",
			href : "http://sc.5173.com/",
			target : "_blank"
		}, {
			name : "\u5b89\u5168\u4e2d\u5fc3",
			"class" : "",
			href : "http://safe.5173.com/",
			target : "_blank"
		}, {
			name : "\u5e2e\u52a9\u4e2d\u5fc3",
			"class" : "",
			href : "http://aid.5173.com/",
			target : "_blank"
		}, {
			name : "\u6211\u8981\u54a8\u8be2",
			"class" : "current",
			href : "http://sc.5173.com/index.php?question/ask/",
			target : "_blank"
		}, {
			name : "\u6211\u8981\u6295\u8bc9",
			"class" : "current",
			href : "http://sc.5173.com/?question/complain.html",
			target : "_blank"
		} ]
	},
	navigation : {
		name : "\u7f51\u7ad9\u5bfc\u822a",
		href : "http://user.5173.com/default.aspx",
		"class" : "",
		item : [
				{
					name : "\u5546\u54c1\u7c7b",
					"class" : "",
					itemlist : [ {
						name : "\u91d1\u5e01\u4ea4\u6613",
						"class" : "",
						href : "http://yxb.5173.com",
						target : "_blank"
					}, {
						name : "\u5e10\u53f7\u4ea4\u6613",
						"class" : "",
						href : "http://gameid.5173.com",
						target : "_blank"
					}, {
						name : "\u88c5\u5907\u4ea4\u6613",
						"class" : "",
						href : "http://zb.5173.com/",
						target : "_blank"
					}, {
						name : "\u6e38\u620f\u4ee3\u7ec3",
						"class" : "",
						href : "http://dlc2c.5173.com/",
						target : "_blank"
					}, {
						name : "\u70b9\u5361\u4ea4\u6613",
						"class" : "",
						href : "http://dkjy.5173.com/",
						target : "_blank"
					}, {
						name : "\u5e10\u53f7\u79df\u8d41",
						"class" : "",
						href : "http://tool.5173.com/AccountRent/Index.aspx#",
						target : "_blank"
					}, {
						name : "\u6e38\u620f\u8bba\u575b",
						"class" : "",
						href : "http://bbs.5173.com/forum.php",
						target : "_blank"
					}, {
						name : "\u8d2d\u4e70\u5f69\u7968",
						"class" : "",
						href : "http://caipiao.5173.com/?agentId=233461/",
						target : "_blank"
					}, {
						name : "\u68cb\u724c\u6e38\u620f",
						"class" : "",
						href : "http://zzjy.5173.com/",
						target : "_blank"
					} ]
				},
				{
					name : "\u670d\u52a1\u7c7b",
					"class" : "",
					itemlist : [
							{
								name : "\u8d44\u6599\u4fee\u6539",
								"class" : "",
								href : "http://gameid.5173.com/fworder/publish.aspx?u_ref=gameid_index",
								target : "_blank"
							},
							{
								name : "\u7f51\u6e38\u52a9\u624b",
								"class" : "",
								href : "http://tool.5173.com",
								target : "_blank"
							},
							{
								name : "\u6295\u8bc9\u5efa\u8bae",
								"class" : "",
								href : "http://bar.5173.com",
								target : "_blank"
							},
							{
								name : "\u54a8\u8be2\u4e2d\u5fc3",
								"class" : "",
								href : "http://sc.5173.com",
								target : "_blank"
							},
							{
								name : "\u5b89\u5168\u4e2d\u5fc3",
								"class" : "",
								href : "http://safe.5173.com",
								target : "_blank"
							},
							{
								name : "5173\u52a9\u7406",
								"class" : "",
								href : "http://assi.5173.com",
								target : "_blank"
							},
							{
								name : "\u77ed\u4fe1\u670d\u52a1",
								"class" : "",
								href : "http://mobile.5173.com/Subscription/Index",
								target : "_blank"
							}, {
								name : "\u63a8\u5e7f\u8054\u76df",
								"class" : "",
								href : "http://cps.5173.com/",
								target : "_blank"
							} ]
				},
				{
					name : "\u5176\u4ed6",
					"class" : "",
					itemlist : [
							{
								name : "\u624b\u673a5173",
								"class" : "",
								href : "http://www.5173.com/m",
								target : "_blank"
							},
							{
								name : "\u6e38\u620f\u8d44\u8baf",
								"class" : "",
								href : "http://html.5173.com/operation/2012/10/26news.html",
								target : "_blank"
							}, {
								name : "\u9875\u6e38\u5f00\u670d",
								"class" : "",
								href : "http://kaifu.5173.com/",
								target : "_blank"
							} ]
				} ]
	},
	microblog : {
		name : "\u5b98\u65b9\u5fae\u535a",
		href : "http://user.5173.com/default.aspx",
		"class" : "",
		item : {
			xlwb : {
				name : "\u65b0\u6d6a\u5fae\u535a",
				href : "http://e.weibo.com/1804287704/profile",
				target : "_blank",
				"class" : ""
			},
			txwb : {
				name : "\u817e\u8baf\u5fae\u535a",
				href : "http://e.t.qq.com/good5173",
				target : "_blank",
				"class" : ""
			},
			wx : {
				name : "\u5173\u6ce85173\u5fae\u4fe1",
				href : "",
				target : "_blank",
				"class" : ""
			}
		}
	}
};
(function() {
	var a = {
		init : function() {
			if ($("#J_GlobalTop")[0]) {
				this.renderHtml();
				this.login()
			}
		},
		easyAjax : function(f, d, c, b) {
			f = {
				url : f,
				dataType : "jsonp",
				scriptCharset : "gb2312",
				jsonp : "jsoncallback",
				success : c,
				complete : b
			};
			if (d) {
				f.cache = true;
				f.jsonpCallback = d
			}
			$.ajax(f)
		},
		renderHtml : function() {
			if (window.location.href.indexOf("5173.com") != "-1")
				this.url = window.location.href || "http://www.5173.com";
			if (typeof top_v3_json !== "undefined") {
				var f = top_v3_json, d = "", c = "", b = "", g = "";
				try {
					$.each(f.buyers.item, function(j, k) {
						d += '<li class="' + k["class"] + '"><a target="'
								+ k.target + '"  href="' + k.href
								+ '" ref="nofollow">' + k.name + "</a></li>"
					});
					$.each(f.seller.item, function(j, k) {
						c += '<li class="' + k["class"] + '"><a target="'
								+ k.target + '"  href="' + k.href
								+ '" ref="nofollow">' + k.name + "</a></li>"
					});
					$.each(f.client.item, function(j, k) {
						b += '<li class="' + k["class"] + '"><a target="'
								+ k.target + '"  href="' + k.href
								+ '" ref="nofollow">' + k.name + "</a></li>"
					});
					$
							.each(
									f.navigation.item,
									function(j, k) {
										var l = "";
										$.each(k.itemlist, function(m, p) {
											l += '<dd><a href="' + p.href
													+ '" target="' + p.target
													+ '" ref="nofollow">'
													+ p.name + "</a></dd>"
										});
										g += '  <dl class="map_list">                            <dt>'
												+ k.name
												+ "</dt>"
												+ l
												+ "                        </dl>"
									});
					var e = '<div class="header header_index950">    <div class="top">        <div class="top_box clearfix">            <ul class="top_left" id="loginList">                <li>\u60a8\u597d\uff01</li>                <li>\u8bf7<a target="_self" href="https://passport.5173.com/?returnUrl='
							+ escape(this.url)
							+ '">\u767b\u5f55</a></li>                <li><a title="QQ\u767b\u5f55" class="qq_login f_666" href="https://passport.5173.com/Partner/LoginFrom?appNo=qq&amp;returnUrl='
							+ escape(this.url)
							+ '">QQ\u767b\u5f55</a></li>                <li><a href="https://passport.5173.com/User/Register">\u514d\u8d39\u6ce8\u518c</a></li>            </ul>            <ul id="topRight" class="top_right">                <li class="'
							+ f.buyers["class"]
							+ '">                    <a target="'
							+ f.buyers.target
							+ '" href="'
							+ f.buyers.href
							+ '" ref="nofollow">'
							+ f.buyers.name
							+ '<s class="like_arrow_down black_arrow"></s><em class="line">|</em></a>                    <div class="sub_box">                        <ul class="user_list">'
							+ d
							+ '                        </ul>                    </div>                </li>                <li class="'
							+ f.seller["class"]
							+ '">                    <a target="'
							+ f.seller.target
							+ '" href="'
							+ f.seller.href
							+ '" ref="nofollow">'
							+ f.seller.name
							+ '<s class="like_arrow_down black_arrow"></s><em class="line">|</em></a>                    <div class="sub_box">                        <ul class="user_list">'
							+ c
							+ '                        </ul>                    </div>                </li>                <li class="pay_chognzhi" id="payChognzhi">                    <a href="'
							+ f.recharge.href
							+ '" target="'
							+ f.recharge.target
							+ '" ref="nofollow">'
							+ f.recharge.name
							+ '<em class="line">|</em> </a>                </li>                <li class="pay_tixian" id="payTixian">                    <a href="'
							+ f.withdrawals.href
							+ '" target="'
							+ f.withdrawals.target
							+ '" ref="nofollow">'
							+ f.withdrawals.name
							+ '<em class="line">|</em> </a>                </li>                <li id="markCart" class="mark_cart">                    <a href="http://trading.5173.com/list/viewmyshoppingcart.aspx" ref="nofollow"><s class="icon_cart"></s>\u8d2d\u7269\u8f66&nbsp;<b class="num">0</b>&nbsp;\u4ef6<em class="line">|</em></a>                </li>                <li class="'
							+ f.client["class"]
							+ '">                    <a target="'
							+ f.client.target
							+ '" href="'
							+ f.client.href
							+ '" ref="nofollow">'
							+ f.client.name
							+ '<s class="like_arrow_down black_arrow"></s><em class="line">|</em></a>                    <div class="sub_box">                        <ul class="support_list">'
							+ b
							+ '                        </ul>                    </div>                </li>                <li class="'
							+ f.navigation["class"]
							+ '"><span>'
							+ f.navigation.name
							+ '<s class="like_arrow_down black_arrow"></s><em class="line">|</em></span>                    <div class="sub_box map_box">'
							+ g
							+ '                    </div>                </li>                <li class="'
							+ f.microblog["class"]
							+ '"><span>'
							+ f.microblog.name
							+ '<s class="like_arrow_down black_arrow"></s></span>                    <div class="sub_box sns_box">                        <ul class="sns_list">                            <li><a class="icon_sina" target="'
							+ f.microblog.item.xlwb.target
							+ '"  href="'
							+ f.microblog.item.xlwb.href
							+ '" ref="nofollow">'
							+ f.microblog.item.xlwb.name
							+ '</a></li>                            <li><a class="icon_tx" target="'
							+ f.microblog.item.txwb.target
							+ '"  href="'
							+ f.microblog.item.txwb.href
							+ '" ref="nofollow">'
							+ f.microblog.item.txwb.name
							+ '</a></li>                        </ul>                        <div class="wx_Qr_code_list">                            <div class="wx_code"></div>                            <div class="wx_txt">'
							+ f.microblog.item.wx.name
							+ "</div>                        </div>                    </div>                </li>            </ul>        </div>    </div></div>"
				} catch (h) {
					typeof window.console !== "undefined"
							&& console
									.log("\u516c\u5171\u9876\u90e8\u901a\u680f\u62a5\u9519 ===> "
											+ h.message)
				}
				$("#J_GlobalTop").append(e);
				this.dropDownEvt()
			}
		},
		dropDownEvt : function() {
			if ($("#topRight .black_arrow").css("transition"))
				var f = false;
			$("#topRight > li").bind("mouseenter", function() {
				var d = $(this).find("div.sub_box");
				if (d.length) {
					$(this).css("zIndex", "1002").addClass("current");
					f || $(this).find(".black_arrow").addClass("arrow_up");
					d.show()
				}
			}).bind("mouseleave", function() {
				var d = $(this).find("div.sub_box");
				if (d.length) {
					$(this).css("zIndex", "").removeClass("current");
					f || $(this).find(".black_arrow").removeClass("arrow_up");
					d.hide()
				}
			})
		},
		initMsg : function() {
			this
					.easyAjax(
							"http://message.5173.com/Service/GetUserSiteMsg.aspx",
							null,
							function(f) {
								if (f) {
									var d = f.length, c = $("#msgNum"), b = $("#msgList"), g = '<div class="switchable_container"><ul>', e;
									if (d > 0) {
										$
												.each(
														f,
														function(h, j) {
															g += "<li><h3>"
																	+ j.title
																	+ '</h3><p class="msg_desc"><a href="'
																	+ j.linkUrl
																	+ '">'
																	+ j.msg
																	+ '</a></p><p><a href="'
																	+ j.linkUrl
																	+ '" class="btn">'
																	+ j.linkText
																	+ "</a></p></li>"
														});
										g += '</ul></div><div class="msg_bottom"><a href="http://message.5173.com/MyInfo/SiteMessageList.aspx?TagType=0" class="more">\u67e5\u770b\u5168\u90e8</a><span id="msgPageIndex"><em class="current">1</em>/<em class="length">'
												+ d
												+ '</em></span><a href="#" class="switchable_prev">\u4e0a\u4e00\u6761</a><a href="#" class="switchable_next">\u4e0b\u4e00\u6761</a></div>';
										e = $("#msgBox").html(g);
										c.addClass("highlight");
										b.hover(function() {
											$(this).css("zIndex", "1002")
													.addClass("current");
											e.show()
										}, function() {
											$(this).css("zIndex", "")
													.removeClass("current");
											e.hide()
										});
										e.switchable({
											effects : "vertical",
											nav : false,
											index : false,
											auto : false,
											duration : 400,
											callback : function(h) {
												$("#msgPageIndex .current")
														.text(h + 1)
											}
										})
									}
								}
							})
		},
		login : function() {
			function f() {
				$("#loginList")
						.prepend(
								'<li id="changeV2014">\u8fd4\u56de\u65e7\u7248\u9996\u9875</li>');
				$("#changeV2014").live("click", function() {
					document.location.href = "http://www.5173.com"
				})
			}
			function d() {
				$("#loginList")
						.prepend(
								'<li id="changeV2014">\u4f7f\u75282014\u5e74\u65b0\u7248\u9996\u9875</li>');
				$("#changeV2014").live("click", function() {
					document.location.href = "http://www.5173.com/default"
				})
			}
			var c = this, b = false, g = false;
			if (window.location.href == "http://www.5173.com/"
					|| window.location.href == "http://www.5173.com/default")
				if (window.location.href.indexOf("default") == "-1") {
					b = true;
					d()
				} else {
					g = true;
					f()
				}
			c
					.easyAjax(
							"http://user.5173.com/Ajax/FrameLoginHandle.ashx",
							null,
							function(e) {
								if (typeof e.name !== "undefined" && e.name) {
									var h = '<li><b class="f_333">'
											+ e.name
											+ '</b></li><li id="msgList"><a href="http://message.5173.com/MyInfo/SiteMessageList.aspx?TagType=0" class="msg">\u7ad9\u5185\u4fe1<span id="msgNum">'
											+ e.msg
											+ '</span><em class="line">|</em></a><div class="msg_box" id="msgBox"></div></li><li><a href="#" class="login_out" id="loginOut">\u9000\u51fa</a></li>';
									$("#loginList").html(h);
									b && d();
									g && f();
									e.msg !== "0"
											&& $("#msgNum").addClass(
													"highlight");
									c
											.easyAjax(
													"http://fcd.5173.com/shoppingcart/getcount.aspx",
													null, function(k) {
														$("#markCart b.num")
																.text(k.count)
													});
									$("#loginOut")
											.click(
													function(k) {
														var l = window.location.href
																|| "http://www.5173.com", m;
														try {
															if (KeyUndecode)
																m = "&Undecode="
																		+ KeyUndecode
														} catch (p) {
															m = ""
														}
														window.location.href = "http://passport.5173.com/Sso/Logout?returnUrl="
																+ escape(l) + m;
														k.preventDefault()
													});
									window.isSignIn = true
								} else
									window.isSignIn = false;
								window.loginData = e;
								if (typeof window.loginCallback === "function") {
									window.loginCallback(e);
									try {
										window.loginData = window.loginCallback = null;
										delete window.loginCallback;
										delete window.loginData
									} catch (j) {
									}
								}
							})
		},
		bfd : function() {
		}
	};
	$(function() {
		a.init()
	})
})();
