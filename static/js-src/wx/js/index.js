/*发送验证码*/
$(".getcode").click(function () {
    var $this = $(this),
        $time = 60;
    if (!$this.hasClass("gray")) {
        $this.addClass("gray").html("重新获取（60）");

        showTime();
    }
    function showTime() {
        if ($time == 0) {
            $this.removeClass("gray").html("获取验证码");
        } else {
            $this.text("重新获取(" + $time + ")");
            setTimeout(showTime, 1000);
            $time--;
        }
    }

})


// })

$(function () {

//     // 线上义卖的tab切换
//     $(".ongoingtab").click(function () {
//         $(this).addClass("tabhover").siblings(".ongoingtab").removeClass("tabhover")
//     });
//
//
//
//
// // 初始化数量
//     var L = $(".radiohover").length;
//     $(".number").html(L);
// // 初始化总价
//     var totalprice = 0;
// // 爱发声 radio
//     var pricenum = $(".total-price").html()
//     $(".radio").each(function () {
//         $(this).click(function () {
//             if ($(this).hasClass("radiohover")) {
//                 $(this).removeClass("radiohover");
//                 pricenum = parseFloat(pricenum - $(this).siblings(".price").find(".per-price").html())
//             } else {
//                 $(this).addClass("radiohover");
//                 pricenum = parseFloat(pricenum + parseFloat($(this).siblings(".price").find(".per-price").html()))
//             }
//
//             // 数量
//             var m = $(".radiohover").length;
//             $(".number").html(m);
//
//
//             // radio点击后的总价
//             $(".total-price").html(pricenum)
//         })
//     })
//
//     function totalprice1() {
//         $(".radiohover").each(function () {
//             totalprice += parseFloat($(this).siblings(".price").find(".per-price").html())
//         })
//         $(".total-price").html(totalprice)
//     }
//     totalprice1();
//
//

    // 订单页面价钱的计算

    /*1.数量*/
    var accountnum = 0;
    var accountprice = 0;
    $(".hewenqi-li").each(function (n) {
        accountnum += parseInt($(".hewenqi-li").eq(n).find(".list-btn").html().replace(/[^0-9]/ig, ""));
        accountprice += $(".hewenqi-li").eq(n).find(".list-btn").html().replace(/[^0-9]/ig, "") * parseFloat($(".hewenqi-li").eq(n).find(".price-num").html());

    });

    $(".account-price").html(accountprice)
    $(".account-num").html(accountnum);






    // 订单页面tab切换
    $(".account-tab1").each(function () {
        $(this).click(function () {
            $(this).addClass("account-tab1hover").siblings(".account-tab1").removeClass("account-tab1hover")
        })
    })



});

