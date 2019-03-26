/**
 * Created by Administrator on 2017/4/25.
 */
const odn = Shinez.getQueryString("odno");
const secCode=Shinez.getQueryString("code");
window.onload = () => {
    setTimeout(() => {
        Shinez.get("/order/" + odn, (ret) => {
            if (ret.status == 0) {
                const od = ret.data.order;
                $("[name=school]").html(od.userSchool);
                $("[name=createTime]").html(Shinez.FormateDate(new Date(od.createTime), "yyyy-MM-dd HH:mm"));
                let appendStr = "";
                od
                    .orderDetails
                    .forEach((v, k) => {
                        appendStr += "<li class='hewenqi-li' ><div class='card-list1'><div class='list-con'><div class" +
                                "='goods-code'>" + v.goodsNo + "</div><div class='list-right'><p class='list-tit'>" + v.goodsName + " </p><div class='list-priceall'><div class='list-price'><span class='price-num'>" + v.realPay + "</span></div><div class='list-btn'>×" + v.goodsCount + "</div><div></div></div></div></li>";
                    });
                $("#hewenqi").html(appendStr);
                $("[name=totalCount]").html(od.totalCount);
                $("[name=discountFee]").html(od.discountFee);
                $("[name=userFullName]").html(od.userFullName);
                $("[name=userNo]").html(od.userNo);
                let status = "";
                switch (od.orderStatus) {
                    case 1:
                        status = "未付款";
                        break;
                    case 2:
                        {
                            status = "未提货";
                            $(".look-btn2").removeClass("none");
                            break;
                        }
                    case 3:
                        status = "已完成";
                        break;
                    case 4:
                        status = "已撤销";
                        break;
                    case 5:
                        status = "待退款";
                        break;
                    case 6:
                        status = "已退款";
                        break;
                }

                $("[name=orderStatus]").html(status);

            }
            if (ret.status == -1) {
                Shinez.showAlert(ret.info);
            }
            if (ret.status == 61400) 
                Shinez.showAlert(ret.info);
            }
        );
    }, 500);

    $(".look-btn2")[0].onclick = () => {
        if (confirm("确认订单[" + odn + "]提货？")) {
            Shinez.put("/order/" + odn,{code:secCode}, (ret) => {
                if (ret.status == 0) {
                    alert("提货成功");
                    $("[name=orderStatus]").html("已提货");
                    $(".look-btn2").addClass("none");
                }
                if (ret.status == -1) 
                    Shinez.showAlert(ret.info);
                }
            );
        }
    }
};