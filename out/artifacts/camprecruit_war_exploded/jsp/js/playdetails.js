function changebtn(num, obj) {
    if (num == 1) {
        if (obj.className == "pd_play_start") {
            obj.className = "pd_play_pause";
        } else if (obj.className == "pd_play_pause") {
            obj.className = "pd_play_start";
        }
    } else if (num == 2) {
        if (obj.className == "pd_play_big") {
            obj.className = "pd_play_small";
        } else if (obj.className == "pd_play_small") {
            obj.className = "pd_play_big";
        }
    } else if (num == 3) {
        if (obj.className == "pd_play_start2") {
            obj.className = "pd_play_pause2";
        } else if (obj.className == "pd_play_pause2") {
            obj.className = "pd_play_start2";
        }
    }
}


/// <summary>
/// 获得元素的绝对坐标
/// </summary>
/// <param name="element">HTML元素</param>
function absolutePoint(element) {
    var result = { x: element.offsetLeft, y: element.offsetTop };
    element = element.offsetParent;
    while (element) {
        result.x += element.offsetLeft;
        result.y += element.offsetTop;
        element = element.offsetParent;
    }
    return result;
}

function changediv(par) {
    var div1 = document.getElementById("ordereg");
    var div2 = document.getElementById("orderdh");
    var div3 = document.getElementById("ordergs");
    var div4 = document.getElementById("orderyx");
    var divul1 = document.getElementById("orderuleg");
    var divul2 = document.getElementById("orderuldh");
    var divul3 = document.getElementById("orderulgs");
    var divul4 = document.getElementById("orderulyx");
    if (par == "1") {
        div1.className = "jy_order_nomarl";
        div2.className = "jy_order_eg";
        div3.className = "jy_order_eg";
        div4.className = "jy_order_eg";
        divul1.className = "jy_order_list";
        divul2.className = "jy_order_list jk_hidden";
        divul3.className = "jy_order_list jk_hidden";
        divul4.className = "jy_order_list jk_hidden";
    } else if (par == "2") {
        div1.className = "jy_order_eg";
        div2.className = "jy_order_nomarl";
        div3.className = "jy_order_eg";
        div4.className = "jy_order_eg";
        divul1.className = "jy_order_list jk_hidden";
        divul2.className = "jy_order_list";
        divul3.className = "jy_order_list jk_hidden";
        divul4.className = "jy_order_list jk_hidden";
    } else if (par == "3") {
        div1.className = "jy_order_eg";
        div2.className = "jy_order_eg";
        div3.className = "jy_order_nomarl";
        div4.className = "jy_order_eg";
        divul1.className = "jy_order_list jk_hidden";
        divul2.className = "jy_order_list jk_hidden";
        divul3.className = "jy_order_list";
        divul4.className = "jy_order_list jk_hidden";
    } else if (par == "4") {
        div1.className = "jy_order_eg";
        div2.className = "jy_order_eg";
        div3.className = "jy_order_eg";
        div4.className = "jy_order_nomarl";
        divul1.className = "jy_order_list jk_hidden";
        divul2.className = "jy_order_list jk_hidden";
        divul3.className = "jy_order_list jk_hidden";
        divul4.className = "jy_order_list";
    }
}

