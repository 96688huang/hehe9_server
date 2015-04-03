window.onerror=function(){return true;}
var player = player || {};
player.lighter = {
    closed: false,
    speed: 200,
    opacity: "0.96",
    height: 0,
    timer: null,
    show: true,
    Close: function() {
        if (this.height === 0) {
            this.heigth = 1;
            $("#light").height($(document).height())
        }
        var a = this;
        $("#lighter").addClass("close").text("开灯");$("#vright").hide();setWid(1);$("h1").hide();scroll(0,parseInt($('.pos').offset().top));
        $("#light").fadeTo(this.speed, a.opacity,
        function(b) {
            a.closed = true;
            $(document).bind("keydown", "esc",
            function() {
                a.Open()
            });
            $(this).dblclick(function() {
                a.Open()
            });
            $(this).mousemove(function() {
                if (a.show === false) {
                    $("#lighter").show();
                    a.show = true
                }
                a.timer && clearTimeout(a.timer);
                a.timer = setTimeout(function() {
                    $("#lighter").hide();
                    a.show = false
                },
                3000)
            })
        })
    },
    Open: function() {
        var a = this;
        $("#light").fadeOut(this.speed,
        function() {
            a.timer && clearTimeout(a.timer);
            a.closed = false;
            $(this).unbind("dblclick mousemove");
	    $("#vright").show();setWid(0);$("h1").show();
            $("#lighter").removeClass("close").text("关灯").show();
        })
    },
    bind: function() {
        var a = this;
        $("#lighter").bind("click",
        function() {
            a.closed == false ? a.Close() : a.Open()
        })
    }
};
var _w = '100%';
var _h = 480;
var _flv = _VideoSrc();
var _path = '';
function plays() {
	if(_video.indexOf("$")!=-1)
		_src = _flv+"?v=" + _video.split("$")[0];
	else
		_src = _flv+"?v=" + _video;
	setTimeout(function() {$("#hy_float").remove()},6000);
	if(_source == '') {
		if(_video.length>20)
			_path = _video;
		else {
			_path = "http://static.youku.com/v1.0.0112/v/swf/qplayer.swf?VideoIDS="+_video;
		}
		document.write('<embed src="'+_path+'" type="application/x-shockwave-flash" allowscriptaccess="always" flashvars="playMovie=true&amp;isAutoPlay=true&amp;auto=1&amp;autoPlay=true&amp;" allowfullscreen="true" wmode="opaque" width="100%" height="480"></embed>')
	}else {
		document.write('<iframe src="'+_src+'" id="naruto_v" name="naruto_v" frameborder="0" width='+_w+' height='+_h+' scrolling="no" class="vborder"></iframe>');
	}
}
function setWid(n) {
	if(n==1){$("#setwid").text('窄屏');$("#vright").hide();$("#vleft").width("980px");$("#player").width("980px");$("#setwid").html("<a href=\"javascript:void(0);\" onclick=\"javascript:setWid(0);\" class=\"setwid\" id=\"witer\">窄屏</a>");}else {$("#setwid").text('宽屏');$("#vright").show();$("#setwid").attr("href","javascript:setWid(1);");$("#vleft").width("720px");$("#player").width("720px");$("#setwid").html("<a href=\"javascript:void(0);\" onclick=\"javascript:setWid(1);\" class=\"setwid\" id=\"witer\">宽屏</a>");}
}
function _vshare() {
	document.writeln('<!-- Baidu Button BEGIN --><div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare"><a class="bds_qzone"></a><a class="bds_tsina"></a><a class="bds_tqq"></a><a class="bds_renren"></a><a class="bds_tqf"></a><a class="bds_qq"></a><a class="bds_baidu"></a><a class="bds_douban"></a><a class="bds_copy"></a><span class="bds_more">更多</span><a class="shareCount"></a></div>');
	document.writeln('<!-- Baidu Button END -->');
	document.writeln('<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=616638" ><\/script><script type="text/javascript" id="bdshell_js"><\/script>');
	document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?t=" + new Date().getHours();
}
function _VideoSrc() {
	return (_source == '') ? '/v3/js/flv.html' : '/v3/player/'+_source+'.html';
}
// hym
var hyms = new Array();
var hym = {
    data: function() {
        this.src = "";
        this.href = "";
        this.width = "";
        this.heigth = "";
        this.type = "";
        this.code = "";
        this.enable = 1;
    },
    create: function(myhym, num) {
        for (var i = 0; i < num; i++) {
            myhym[i] = new hym.data();
        }
    },
    get: function(id) {
        if (!hyms[id]) return "";
        if (!hyms[id].enable) return "";
        var type = hyms[id].type || "";
        switch (type) {
        case "html":
        case "htm":
            return hyms[id].code;
        case "iframe":
            return "<iframe scrolling=\"no\" frameborder=\"0\" width=\"" + hyms[id].width + "\" height=\"" + hyms[id].height + "\" src=\"" + hyms[id].src + "\" ></iframe>";
        case "img":
            return "<a href=\"" + hyms[id].href + "\" target=\"_blank\"><img alt=\"" + hyms[id].alt + "\" src=\"" + hyms[id].src + "\" border=\"0\" width=\"" + hyms[id].width + "\" height=\"" + hyms[id].height + "\" /></a>";
        default:
            return "";
        }
    },
    show: function(id) {
        var code = hym.get(id);
        if (code != "") {
            document.write(code);
        }
    }
};
hym.create(hyms,6);
hyms[0].type = "iframe";
hyms[0].src = "http://www.narutom.com/v2/v/p/vl.html";
hyms[0].width = 960;
hyms[0].height = 60;
hyms[1].type = "iframe";
hyms[1].src  = "http://www.narutom.com/v2/v/p/v250.html";
hyms[1].width = 250;
hyms[1].height = 250;
hyms[2].type = "html";
hyms[2].code = "<script type=\"text/javascript\">var cpro_id = \"u1309564\";<\/script><script src=\"http://cpro.baidustatic.com/cpro/ui/c.js\" type=\"text/javascript\"><\/script>";
hyms[3].type = "iframe";
hyms[3].src = "http://img.88rpg.net/html/click/1553_2003.html";
hyms[3].width = 980;
hyms[3].height = 90;
hyms[4].type = "html";
hyms[4].code  = "";
hyms[5].type = "html";
hyms[5].code  = "<iframe src=\"http://www.narutom.com/v2/v/p/loading.html\" width=\"300\" height=\"300\" frameborder=\"no\" border=\"0\" marginwidth=\"0\" marginheight=\"0\" scrolling=\"no\" allowtransparency=\"yes\"><\/iframe>";
//hyms[5].type = "html";
//hyms[5].code  = "<script type=\"text\/javascript\">var cpro_id = \"u1353427\";<\/script><script src=\"http:\/\/cpro.baidustatic.com\/cpro\/ui\/c.js\" type=\"text\/javascript\"><\/script>";