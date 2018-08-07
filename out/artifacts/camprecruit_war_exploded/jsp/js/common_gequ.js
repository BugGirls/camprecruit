function setPlaySize() {
    $("." + initData.playmaindiv).width() < initData.play_card_width ? ($("#" + initData.playcontentdiv + ", ." + initData.playmaindiv + ", #" + initData.playerid + "").css({
        width: initData.play_card_width
    }), $(".flash_c .k1 a").css({
        backgroundPosition: "34px -300px"
    }).html("\u6807\u5c4f")) : ($("#" + initData.playcontentdiv + ", ." + initData.playmaindiv + ", #" + initData.playerid + "").css({
        width: initData.player_content_width
    }), $(".flash_c .k1 a").css({
        backgroundPosition: "34px 10px"
    }).html("\u5bbd\u5c4f")),
    $(".flash_c .k1 a").unbind("click").bind("click", setPlaySize)
}
function selectTags(e, t, n, r, s) {
    var o = document.getElementById(s).getElementsByTagName("li"),
    u = o.length;
    for (i = 0; i < u; i++) o[i].className = "";
    t.parentNode.className = n;
    for (i = 0; j = document.getElementById(r + i); i++) j.style.display = "none";
    document.getElementById(e).style.display = "block"
}
function hideDetail() {
    var e = document.getElementById("hutia"),
    t = e.innerHTML,
    n = document.createElement("span"),
    r = document.createElement("a");
    n.innerHTML = t.substring(0, 50),
    r.innerHTML = t.length > 50 ? "...\u8be6\u7ec6": "",
    r.href = "javascript:void(0);",
    r.onclick = function() {
        r.innerHTML == "...\u8be6\u7ec6" ? (r.innerHTML = "\u9690\u85cf", n.innerHTML = t) : (r.innerHTML = "...\u8be6\u7ec6", n.innerHTML = t.substring(0, 50))
    },
    e.innerHTML = "",
    e.appendChild(n),
    e.appendChild(r)
}
$(function() {
    $("#" + initData.playerid).mouseenter(function(e) {
        showRightControl(.6, .2)
    }).mouseleave(function(e) {
        hiddenRightControl(1, .2)
    }),
    $(".flash_c").mousemove(function(e) {
        initData.ifShowRightControl = !0,
        showRightControl(0, .2)
    }).mouseenter(function(e) {
        initData.ifShowRightControl = !0,
        showRightControl(0, .2)
    }).mouseleave(function(e) {
        initData.ifShowRightControl = !1,
        hiddenRightControl(1, 0)
    }),
    $(".play_main a,.play a").bind("click",
    function() {
        $(this).blur()
    }),
    $(".flash_c .k1 a").bind("click", setPlaySize),
    $(".zhuan a").bind("click", showLink),
    $(".feng_w").blur(function() {
        $(this).fadeOut("fast")
    })
}),
Date.prototype.format = function(e) {
    var t = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        S: this.getMilliseconds()
    };
    /(y+)/.test(e) && (e = e.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length)));
    for (var n in t)(new RegExp("(" + n + ")")).test(e) && (e = e.replace(RegExp.$1, RegExp.$1.length == 1 ? t[n] : ("00" + t[n]).substr(("" + t[n]).length)));
    return e
},
$(document).ready(function() {
    doPlayRecord(1, "", "");
    var e = $(".play_save"),
    t = $(".save_layer");
    e.click(function() {
        showRecord(),
        t.show()
    }),
    $("#J_video_record").delegate("#J_close", "click",
    function() {
        $("#J_video_record").hide()
    }),
    $("#J_video_record").delegate("#J_del_one", "click",
    function() {
        doPlayRecord(1, $(this).parent().find(".look").attr("title"), $(this).parent().find(".look").attr("href")),
        showRecord()
    })
}),
$(function() {
    CheckLoginAjax(),
    showRating(0)
})