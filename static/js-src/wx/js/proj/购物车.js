/**
 * Created by Administrator on 2017/4/25.
 */

const selectGoodsStr = sessionStorage.getItem("selectGoods");
let selectGoods = [];
let userCart = [];
let handleFlag = false;
let handleFlagOnce = false;
const eid = sessionStorage.getItem("eid");
if (selectGoodsStr != null && selectGoodsStr != "") {
    selectGoods = JSON.parse(selectGoodsStr);

    window.onload = () => {
        document.getElementsByClassName("user-cart")[0].innerHTML = appendGoods(selectGoods);
        document.getElementsByClassName("user-cart-copy")[0].innerHTML = "";
        const etype = sessionStorage.getItem("etype");
        document.getElementsByTagName("title")[0].innerHTML = etype == 1
            ? "线下义卖"
            : "线上义卖";
        Shinez.get("/goods/jssdk", function (ret) {
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
                    //初始计算价格
                    setTimeout(()=>{
                        price()
                    },100);
                    
                    registerEvent("");
                    document.getElementsByClassName("pay-btn")[0].onclick = () => {
                        doPay()
                    };
                    handleFlag=true;
                });
            }
        });

    }
}

let doPayFlag = false;
function doPay() {
    if (!doPayFlag) 
        return;
    doPayFlag = false;
    $(".pay-btn").css("background", "#aaaaaa");
    const totalFee = price(1);
    if (totalFee <= 0) 
        return;
    console.log("付款：￥" + totalFee);

    Shinez.showAlert("请稍后...");
    Shinez.post("/order", {
        data: JSON.stringify(userCart),
        totalFee: totalFee
    }, (ret) => {

        console.log(ret);
        if (ret.status == -1) {
            doPayFlag = true;
            $(".pay-btn").css("background", "#e50265");
            Shinez.showAlert(ret.info);
        }
        if (ret.status == 0) {
            userCart = {};
            sessionStorage.removeItem("selectGoods");
            sessionStorage.removeItem("selectId");
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
                        alert(res.errMsg);
                    }
                },
                cancel: function (res) {
                    //支付取消
                    alert("取消支付");
                    location.href = "订单.html";
                },
                error: function (e) {
                    // alert(e);
                    console.log(e);
                }
            });
        }
        if(ret.status==61400){
            Shinez.showAlert(ret.info);
             doPayFlag = true;
            $(".pay-btn").css("background", "#e50265");
        }
    });
}
//事件注册
function registerEvent(cartCopyFlag) {

    // register删除按点击事件
    $(cartCopyFlag + " .delete-right-btn")
        .on('touchstart', function () {
            var that = $(this);
            var li = that.parents("li");
            li.fadeOut(500, function () {
                li.remove();
                price(cartCopyFlag);
                nonegoods();
            });
            //删除购物车数组
            const delId = li.attr("data-id");
            let index = -1;
            userCart.forEach((v, k) => {
                if (v.id == delId) {
                    index = k;
                    return false;
                }
            });
            userCart.splice(index, 1);
            //删除购物车数组END

        });

    //register减按钮点击事件
    $(".sub").on('touchstart', function (e) {
        if (handleFlag && handleFlagOnce) {
            handleFlag = false;
            var numCon = $(this).siblings(cartCopyFlag + " .list-num");
            var count = parseInt(numCon.html());
            if (count > 1) {
                numCon.html(count - 1);
                price(cartCopyFlag);
                let id = $(e.target)
                    .parents("li")
                    .attr("data-id");
                userCart.forEach((v, k) => {
                    if (v.id == id) {
                        if (v.goodsCount > 1) 
                            v.goodsCount--;
                        return false;
                    }
                });
                console.table(userCart);
            }
            setTimeout(() => {
                handleFlag = true
            }, 750);
        }
    });

    //register加按钮点击事件
    $(".add").on('touchstart', function (e) {
        if (handleFlag && handleFlagOnce) {
            handleFlag = false;
            var left = parseInt($(this).siblings("input[class=j-input-left]").val());
            var limit = parseInt($(this).siblings("input[class=j-input-limit]").val());
            var numCon = $(this).siblings(cartCopyFlag + " .list-num");

            var count = parseInt(numCon.html());
            if (count < left && count < limit) {
                numCon.html(count + 1);
                price(cartCopyFlag);
                const id = $(e.target)
                    .parents("li")
                    .attr("data-id");
                userCart.forEach((v, k) => {
                    if (v.id == id) {
                        v.goodsCount++;
                        return false;
                    }
                });

                console.table(userCart);
            }
            setTimeout(() => {
                handleFlag = true
            }, 750);
        }
    });
    $(".pay-btn").css("background", "#e50265");
}

// 判断购物车是否为空
function nonegoods() {
    if ($(".list-li").length == 0) {
        $(".no-goods").removeClass("none")
    }
}

//获取加购商品
function getExchange(priceNoneEx) {

    Shinez.get("/goods/exchange", {
        eventId: eid,
        fullPrice: priceNoneEx
    }, (ret) => {
        document.getElementsByClassName("user-cart-copy")[0].innerHTML = "";
        if (ret.status == 0) {
            const eg = ret.data.exchangeGoods;
            if (eg.length == 0) {
                userCart.forEach((v, k) => {
                    if (v.goodsProp == 1) {
                        userCart.splice(k, 1);
                    }
                });
            }
            let appendEx = appendGoods(eg, 1);
            document.getElementsByClassName("user-cart-copy")[0].innerHTML = appendEx;
            price(1);
            registerEvent(".user-cart-copy");
            doPayFlag = true;
            setTimeout(()=>handleFlagOnce = true,200);
            $(".pay-btn").css("background", "#e50265");
        }
        if (ret.status == 61400) 
            Shinez.showAlert(ret.info);
        }
    );
}

//加载商品函数
function appendGoods(goodsData, goodsProp) {
    let appendStr = "";
    let _g;
    goodsData.forEach((v, k) => {
        _g = {};
        _g = Shinez.deepClone(v, _g);
        delete _g.goodsPrice;
        delete _g.goodsType;
        _g.goodsCount = 1;
        delete _g.goodsImg;
        userCart.forEach((v, k) => {
            if (v.id == _g.id) {
                userCart.splice(k, 1);
            }
        });
        userCart.push(_g);
        appendStr += "<li class='list-li' data-id='" + v.id + "'><div class='card-list1'><div class='list-con'><img src='" + v.goodsImg + "' class='card-goods1'/><div class='list-right'><p class='list-tit'>" + v.goodsName + "</p><div class='list-priceall'><div class='list-price'>￥<span class='price-num'>" + v.salePrice + "</span></div><div class='list-btn'><img src='../image/sub.png' class='sub'/><div" +
                " class='list-num'>1</div><img src='../image/add.png' class='add'/><input type='h" +
                "idden' class='j-input-limit' value='" + (v.limitCount == 0
            ? "999"
            : v.limitCount) + "'><!-- 限购数量 --><input type='hidden' class='j-input-left' value='" + v.goodsCount + "'><!-- 库存数量 --></div></div><div class='quota'><span>" + (v.limitCount == 0
            ? ""
            : ((goodsProp == null
                ? "限购"
                : "可换购") + v.limitCount) + "件") + "</span><span>库存：" + v.goodsCount + "</span></div></div></div> </div><div class='delete-btn  delete-right-btn'>删除</di" +
                "v> </li>";
    });
    return appendStr;
}

//计算总价
function price(flag) {
    var price = 0;
    var pricealll = 0;
    $(".price-num").each(function () {
        price += parseFloat($(this).html()) * parseInt($(this).parents(".list-priceall").find(".list-num").html());
        pricealll = price.toFixed(2);
    });
    $(".total-price").html(pricealll);

    if (flag == null || flag == "") {
        doPayFlag = false;
        getExchange((pricealll - priceExchange()).toFixed(2), eid);
    }
    return pricealll;
}
//计算换购价
function priceExchange() {
    var price = 0;
    var pricealll = 0;
    var arr = $(".user-cart-copy").find(".price-num");
    if (arr == null || arr.length == 0) 
        return 0;
    $(".user-cart-copy")
        .find(".price-num")
        .each(function () {
            price += parseFloat($(this).html()) * parseInt($(this).parents(".list-priceall").find(".list-num").html());
            pricealll = price.toFixed(2);
        });
    return pricealll;
}