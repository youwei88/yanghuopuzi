/**
 * Created by Administrator on 2017/4/25.
 */


window.onload = function() {
    Shinez.showAlert("请稍后...",null,500);
    Shinez
        .get("/order/jssdk", function (ret) {
            if (ret.status == 0) {
                const map = ret.data;
                const appid = map.appId;
                const timestamp = map.timestamp;
                const nonceStr = map.noncestr;
                const signature = map.signature;
                wx.config({
                    debug: false,
                    appId: appid,
                    timestamp: timestamp,
                    nonceStr: nonceStr,
                    signature: signature,
                    jsApiList: ['hideAllNonBaseMenuItem', "chooseWXPay"]
                });
                wx.ready(function () {
                    wx.hideAllNonBaseMenuItem();
                    document.getElementsByClassName("tab-o0")[0].onclick = () => {
                        Shinez.get("/order", {
                            noComp: 1,
                            eventType: etype
                        }, (ret) => {
                            if (ret.status == 0) {
                                let str = loadData(ret.data.orders);
                                $("#container").html(str);
                                registerEvent(ret.data.domain);
                            }
                            if(ret.status==61400){
                                Shinez.showAlert(ret.info);
                            }
                        });
                    };
                    document.getElementsByClassName("tab-o1")[0].onclick = () => {
                        Shinez.get("/order", {
                            eventType: etype
                        }, (ret) => {
                            if (ret.status == 0) {
                                let str = loadData(ret.data.orders);
                                $("#container").html(str);
                                registerEvent(ret.data.domain);
                            }
                            if(ret.status==61400){
                                Shinez.showAlert(ret.info);
                            }
                        });
                    };
                    setTimeout(() => $(".tab-o0").trigger("click"), 200);
                });
            }
            if(ret.status==61400){
                Shinez.showAlert(ret.info);
            }
        });
    const userStr = sessionStorage.getItem("user");
    const user = JSON.parse(userStr);
    const etype = sessionStorage.getItem("etype");
    $.each(user, (k, v) => {
        $("[name=" + k + "]").html(v);
    });

};

function registerEvent(domain) {
    // 弹出提货码弹窗
    $(".account-btn2")
        .each(function () {
            $(this)
                .click(function (e) {
                    const odno = e
                        .target
                        .getAttribute("data-id");
                    const code = e
                        .target
                        .getAttribute("data-code");
                    $("#qr-div-" + odno).html("");
                    const qrcode = new QRCode("qr-div-" + odno, {
                        text: 'http://' + domain + '/view/confirm-pick-up.html?odno=' + odno + '&code=' + code + '&tid=' + new Date().getTime(),
                        width: 128,
                        height: 128,
                        colorDark: "#000000",
                        colorLight: "#ffffff",
                        correctLevel: QRCode.CorrectLevel.H
                    });
                    $("#qr-div-" + odno).css("display", "none");
                    $("#qr-div-" + odno)
                        .find("img")
                        .addClass("code");
                    setTimeout(() => {
                        $("#qr-div-" + odno).css("display", "")
                    },1);

                    $(".modal-modal[data-id=" + odno + "]").removeClass("none");
                    $(".modal-alert[data-id=" + odno + "]").removeClass("none");
                })
        });
    $(".close").on("click", (e) => {
        $(".modal-modal").addClass("none");
        $(".modal-alert").addClass("none");
        $(".account-tab1hover").trigger("click");
    });

    $(".cancel-btn").on("click", (e) => {
        const id = e
            .target
            .getAttribute("data-id");
        if (confirm("真要取消该订单吗？")) {
            Shinez.del("/order/" + id, (ret) => {
                if (ret.status == 0) {
                    Shinez.showAlert("订单已取消");
                    setTimeout(() => $(".tab-o0").trigger("click"), 400);
                }

            })
        }
    });

    $(".pay-btn").on("click", (e) => {
        const id = e
            .target
            .getAttribute("data-id");
        Shinez.showAlert("请稍后...");
        Shinez.put("/order", {
            orderNo: id
        }, (ret) => {
            if (ret.status == -1) {
                Shinez.showAlert(ret.info);
            }
            if (ret.status == 0) {
                const map = ret.data;
                wx.chooseWXPay({
                    timestamp: map.timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
                    nonceStr: map.nonceStr, // 支付签名随机串，不长于 32 位
                    package: map.package, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
                    signType: 'MD5', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
                    paySign: map.paySign, // 支付签名
                    success: function (res) {
                        if (res.errMsg == "chooseWXPay:ok") {
                            //支付成功

                            location.href = "支付成功.html";
                        } else {
                            alert("暂时无法支付，请稍后再试。"+res.errMsg);
                        }
                    },
                    cancel: function (res) {
                        //支付取消
                        alert("取消支付")
                    },
                    error: function (e) {
                        // alert(e);
                        console.log(e);
                    }
                });
            }
        });
    });
}

function loadData(orders) {
    let append = "";
    orders.forEach((v, k) => {
        append += " <ul class='card-list-order hewenqi card-listn' id='hewenqi'>";
        const odList = v.orderDetails;
        odList.forEach((i, j) => {
            append += "<li class='hewenqi-li'><div class='card-list1'><div class='list-con'><img src='" + (i.goodsImg == null
                ? ""
                : i.goodsImg) + "' class='card-goods1'/><div class='list-right'><p class='list-tit'>" + i.goodsName + "</p> <div class='list-priceall'><div class='list-price'>￥<span class='price-num'" +
                    ">" + i.realPay + "</span></div><div class='list-btn'> ×" + i.goodsCount + "</div></div></div></div></div> </li>";
        });
        append += "</ul><div class='account-total'><div class='totao-top'>";
        append+="<span style='float: left;color: #e50265;font-size: 14px;margin-top: 0.02rem;'>" + v.orderNo + "</span>";
        append+="<span>共<em class='account" +"-num'>" + v.totalCount + "</em>件 </span><span>合计:<i class='symbol'>￥</i><em class='account-price'>" + v.discountFee + "</em></span></div>";
       
        if (v.orderStatus == 1) {
            append += "<div class='account-btn  cancel-btn' style='background:#000000' data-id='" + v.orderNo + " '>取消订单</div> <div data-id='" + v.orderNo + "' class='account-btn  pay-btn'>支付</div></div>";
        }
        if (v.orderStatus == 3) {
             append += "<div class='account-tip' style='float: left;width: 1.7rem;margin-top: 0.15rem;color: #e50265;"
             +"font-size: 14px;margin-left: 0.1rem;text-align: left;'>核销人："+v.validUserFullName+"</div>";
            append += "<div class='account-btn '>已完成</div></div>";
        }
        if (v.orderStatus == 4) {
            append += "<div class='account-btn '>已撤销</div></div>";
        }
        if (v.orderStatus == 5) {
            append += "<div class='account-btn '>待退款</div></div>";
        }
        if (v.orderStatus == 6) {
            append += "<div class='account-btn '>已退款</div></div>";
        }
        if (v.orderStatus == 2) {
            append += "<div class='account-btn account-btn2' data-id='" + v.orderNo + "'  data-code='" + v.pickCode + "'>查看提货码</div></div><div class='modal-modal none' data-id='" + v.orderNo + "'></div><div class='modal-alert none'  data-id='" + v.orderNo + "'><div class='alert-tit' > <span name='school'>" + v.userSchool + "</span><h6 style='color: #e50265'>订单号："+v.orderNo+"</h6><img src='../image/close.png' class='close'/></div><div class='modal-con'" +
                    "><img src='../image/border.png' class='modal-border'/> <div id='qr-div-" + v.orderNo + "'style='display:none' ></div></div><div class='getgoods '> <p>提货码：" + (v.pickCode == null
                ? ""
                : v.pickCode) + "</p><p>截止时间：" + Shinez.FormateDate(new Date(v.eventPickUpTime), "yyyy-MM-dd HH:mm") + "</p><p>提货地点：" + v.eventPickUpAddress + "</p></div></div>";
        }
    });
    return append;
}


