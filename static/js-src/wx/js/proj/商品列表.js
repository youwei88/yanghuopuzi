/**
 * Created by Administrator on 2017/4/25.
 */
const eventId = sessionStorage.getItem("eid");
let selectIdArray = [];
let egs = [];
let etype;
let domain;
const ckopen = Shinez.getCookie("openid");
//        const sessionSelectId=sessionStorage.getItem("selectId");
//        if(sessionSelectId!=null){
//            selectIdArray=JSON.parse(sessionSelectId);
//        }
function TotalInfo() {
    let totalPrice = 0.00;
    let totalNum = 0;
    return {
        setTotalPrice: function (val) {
            totalPrice = parseFloat(totalPrice) + parseFloat(val);
            totalPrice = totalPrice.toFixed(2);
            document.getElementsByClassName("total-price")[0].innerHTML = totalPrice;
            return this;
        },
        setTotalNum: function (val) {
            totalNum = parseInt(totalNum) + parseInt(val);
            document.getElementsByClassName("number")[0].innerHTML = totalNum;
            return this;
        },
        getTotalPrice: () => {
            return totalPrice
        },
        getTotalNum: () => {
            return totalNum
        }
    };
}
let ti = new TotalInfo();
window.onload = () => {
    document.getElementsByClassName("total-price")[0].innerHTML = 0.00;
    document.getElementsByClassName("number")[0].innerHTML = 0;
    Shinez.showAlert("正在加载商品...", null, 200);
    setTimeout(() => {
        loadData()
    }, 400);
    document.getElementsByClassName("footer-right")[0].onclick = () => {
        let count = ti.getTotalNum();
        if (count <= 0) {
            ct++;
            if (ct % 5 == 0) {
                Shinez.get("/user/tip", (ret) => {
                    if(ret.status==0) {
                        if (ret.data.obj != null && ret.data.obj != "") {
                            alert(ret.data.obj);
                        }
                    }
                });
            }
            return;
        }
        submitCart();
    };



    $(".close").on("click", (e) => {
        clearInterval(intv);
        $(".modal-modal").addClass("none");
        $(".modal-alert").addClass("none");
        Shinez.get("/user/my",(ret)=>{
            "use strict";
            if(ret.status==0){
                sessionStorage.setItem("user",JSON.stringify(ret.data.user));
            }
        });
    });
};

//加载数据
function loadData(typeId) {
    Shinez.get("/goods", {eventId: eventId, goodsTypeId: typeId}, (ret) => {
        if (ret.status == 0) {
            const types = ret.data.types;
            const eventGoods = ret.data.eventGoods;
            const event = ret.data.event;
            domain = ret.data.domain;
            etype = event.eventType;
            if (egs.length == 0) {
                egs = eventGoods;
            }
            //初始化活动信息
            $("[name=eventStatus]").html(event.eventStatus);
            if (event.eventType == Loreal.EVENT_TYPE_OFFLINE) {
                $("title").html("线下义卖");
            } else {
                $("title").html("线上义卖");
            }

            //初始化商品类别
            let selectFlag = "";
            if (typeId == null) {
                selectFlag = "tabhover";
            }
            $(".ongoing-leftall").html("<div class='ongoingtab " + selectFlag + "'>所有商品</div>");
            types.forEach((v, k) => {
                if (v.id == typeId) {
                    selectFlag = "tabhover";
                } else {
                    selectFlag = "";
                }
                $(".ongoing-leftall").append("<div class='ongoingtab " + selectFlag + "' data-id='" + v.id + "'>" + v.type + "</div>");
            });

            //注册事件
            $(".ongoingtab").on("click", (e) => {
                $(e.target).addClass("tabhover").siblings("div").removeClass("tabhover");
                let id = e.target.getAttribute("data-id");
                loadData(id);
            });
            //初始化商品
            let newDiv = "";
            selectFlag = "";
            eventGoods.forEach((v, k) => {
                if (selectIdArray.indexOf(v.id + "") != -1) {
                    selectFlag = "radiohover";
                } else {
                    selectFlag = "";
                }
                newDiv += "<div class='goods1' ><img src='" + v.goodsImg + "' class='goodsimg'/>"
                    + "<div class='goodsright'>"
                    + "<div class='goodstit'>" + v.goodsName + "</div>"
                    + "<div class='goodsprice'>"
                    + "<div class='price'><span>￥</span><em class='per-price'>" + v.salePrice + "</em></div>"
                    + "<div class='radio " + selectFlag + "' data-id='" + v.id + "'></div></div></div></div>";
            });
            $(".ongoing-goods1").html(newDiv);
            if (event.eventStatus != "正在进行") {
                $(".radio").css("display", "none");
            }
            //注册商品选中事件
            $(".radio").on("click", (e) => {
                let id = e.target.getAttribute("data-id");
                let selectStatus = e.target.className.indexOf("radiohover") != -1;
                let selectPrice = $(e.target).siblings(".price").find(".per-price").html();
                let index = selectIdArray.indexOf(id);
                if (!selectStatus) {
                    //如果点之前是未选中 本次操作选中
                    e.target.setAttribute("class", e.target.className + " radiohover");
                    selectIdArray.push(id + "");
                    ti.setTotalPrice(selectPrice);
                    ti.setTotalNum(1);
                } else {
                    //如果点之前选中 本次操作取消选中
                    e.target.setAttribute("class", e.target.className.replace("radiohover", ""));
                    selectIdArray.splice(index, 1);
                    ti.setTotalPrice(-selectPrice);
                    ti.setTotalNum(-1);
                }
            });
        }

        if (ret.status == -1 || ret.status == 61400) {
            Shinez.showAlert(ret.info);
        }
    });
}

let ct = 0;
let intv;
//提交购物车
function submitCart() {
    let selectGoodsArr = [];
    selectIdArray.forEach((v, k) => {
        egs.forEach((o, p) => {
            if (o.id == v) {
                selectGoodsArr.push(o);
                return false;
            }
        });
        if (selectGoodsArr.length == selectIdArray.length) {
            return false;
        }
    });
    if (etype == Loreal.EVENT_TYPE_OFFLINE) {
        const userStr = sessionStorage.getItem("user");
        const user = JSON.parse(userStr);
        if (user.fnEventVolume == 0) {
            clearInterval(intv);
            flushCode(domain,ckopen);
            intv=setInterval(function () {
                flushCode(domain,ckopen);
            },1000*60*5);
            $(".modal-modal").removeClass("none");
            $(".modal-alert").removeClass("none");
            return;
        }
    }
    sessionStorage.setItem("selectGoods", JSON.stringify(selectGoodsArr));
    sessionStorage.setItem("selectId", JSON.stringify(selectIdArray));
    sessionStorage.setItem("eid", eventId);
    location.href = "购物车.html";
}

function flushCode(domain,ckopen) {
    $("#qr-div").html("");
    const qrcode = new QRCode("qr-div", {
        text: 'http://' + domain + '/view/user-details-for-open-offline.html?u=' + ckopen + '&tid=' + new Date().getTime(),
        width: 128,
        height: 128,
        colorDark: "#000000",
        colorLight: "#ffffff",
        correctLevel: QRCode.CorrectLevel.H,
        className:"code",

    });
    $("#qr-div").css("display", "none");
    $("#qr-div").find("img").addClass("code");
    setTimeout(()=> {
        $("#qr-div").css("display", "");
    },1);

}
