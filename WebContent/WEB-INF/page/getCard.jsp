<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>领取卡券</title>
        <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm.min.css">
        <link rel="stylesheet" href="//g.alicdn.com/msui/sm/0.6.2/css/sm-extend.min.css">
    </head>

    <body>
        <div class="content">
            <div class="content-block">
                <p><a href="#" id="getCardBtn" class="button button-round" style="width: 70%;margin: 0 auto;height: 100px;line-height: 100px;font-size: 33px;">领取 </a></p>
            </div>
        </div>
    </body>

    <script src="https://cdn.bootcss.com/jquery/3.1.0/jquery.min.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.2.0.js"></script>
    <script>
        $(function() {
            $("#getCardBtn").click(function () {
                $.ajax({
                    type: "GET",
                    url: "/member/activity/js_put_card",
                    dataType: "json",
                    data: {
                        cardId: '${cardId}'
                    },
                    success: function (data) {
                        wx.config({
                            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                            appId: data.appId, // 必填，公众号的唯一标识
                            timestamp: data.timestamp, // 必填，生成签名的时间戳
                            nonceStr: data.nonceStr, // 必填，生成签名的随机串
                            signature: data.signature1,// 必填，签名
                            jsApiList: ['addCard'] // 必填，需要使用的JS接口列表
                        });

                        wx.addCard({
                            cardList: [{
                                cardId: data.cardId,
                                cardExt: '{"code":"","openid":"","timestamp":"' + data.timestamp + '","nonce_str":"' + data.nonceStr + '","signature":"' + data.signature2 + '"}'
                            }], // 需要添加的卡券列表
                            success: function (res) {
                                console.log(res)
                            }
                        })
                    }
                })
            })
        })
    </script>

</html>