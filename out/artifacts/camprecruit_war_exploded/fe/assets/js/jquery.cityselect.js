/*-------------------------------
 Ajax 三级省市联动
 日期：2015-9-14

 settings 参数说明
 -----
 data:省市数据
 prov:默认省份
 city:默认城市
 dist:默认地区（县）
 nodata:无数据状态
 required:必选项
 ------------------------------ */

(function ($) {
    $.fn.citySelect = function (settings) {
        if (this.length < 1) {
            return;
        }

        // 默认值
        settings = $.extend({
            data: '',
            prov: null,
            city: null,
            dist: null,
            nodata: null,
            required: true
        }, settings)

        var box_obj = this;
        var prov_obj = box_obj.find(".prov");
        var city_obj = box_obj.find(".city");
        var dist_obj = box_obj.find(".dist");
        var prov_val = settings.prov;
        var city_val = settings.city;
        var dist_val = settings.dist;
        var select_prehtml = "<option value=''>请选择</option>";
        var city_json;

        // 赋值市级函数
        var cityStart = function () {
            var prov_id = prov_obj.get(0).selectedIndex;
            if (!settings.required) {
                prov_id--;
            }
            ;
            city_obj.empty().attr("disabled", true);
            dist_obj.empty().attr("disabled", true);

            if (prov_id < 0 || typeof(city_json.citylist[prov_id].c) == "undefined") {
                if (settings.nodata == "none") {
                    city_obj.css("display", "none");
                    dist_obj.css("display", "none");
                } else if (settings.nodata == "hidden") {
                    city_obj.css("visibility", "hidden");
                    dist_obj.css("visibility", "hidden");
                }
                return;
            }

            // 遍历赋值市级下拉列表
            temp_html = select_prehtml;
            $.each(city_json.citylist[prov_id].c, function (i, city) {
                temp_html += "<option value='" + city.v + "'>" + city.n + "</option>";
            });
            city_obj.append(temp_html).attr("disabled", false).css({"display": "", "visibility": ""});
        };

        var init = function () {
            // 遍历赋值省份下拉列表
            temp_html = '';
            $.each(city_json.citylist, function (i, prov) {
                temp_html += "<option value='" + prov.p_v + "'>" + prov.p + "</option>";
            });
            prov_obj.append(temp_html);

            // 若有传入省份与市级的值，则选中。（setTimeout为兼容IE6而设置）
            setTimeout(function () {
                if (settings.prov != null) {
                    prov_obj.val(settings.prov);
                    cityStart();
                    setTimeout(function () {
                        if (settings.city != null) {
                            city_obj.val(settings.city);
                        }
                    }, 1);
                }
            }, 1);

            // 选择省份时发生事件
            prov_obj.bind("change", function () {
                cityStart();
            });
        };

        // 设置省市json数据
        city_json = settings.data;
        init();
    };
})(jQuery);