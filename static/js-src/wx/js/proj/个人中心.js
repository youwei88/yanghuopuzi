/**
 * Created by Administrator on 2017/4/25.
 */
//渲染user
$(() => {
    const userStr = sessionStorage.getItem("user");
    const user = JSON.parse(userStr);
    $("img[name=headimgurl]").attr("src", user.headimgurl);
    $.each(user, (k, v) => {
        $("[name=" + k + "]").html(v);
    });

    $(".online-b")[0].onclick = () => {
        sessionStorage.setItem("etype", Loreal.EVENT_TYPE_ONLINE);
        location.href = "订单.html";
    };
    $(".offline-b")[0].onclick = () => {
        sessionStorage.setItem("etype", Loreal.EVENT_TYPE_OFFLINE);
        location.href = "订单.html";
    };

    setTimeout(() => {
        Shinez.get("/order/msg-count", (ret) => {
            if (ret.status == 0) {
                const online = ret.data.online_count;
                const offline = ret.data.offline_count;
                if (online == 0) 
                    $("[name=online]").remove();
                else {
                    $("[name=online]").html(online).addClass("radius").css("display","");
                }
                if (offline == 0) 
                    $("[name=offline]").remove();
                else {
                    $("[name=offline]").html(offline).addClass("radius").css("display","");
                }
            }
        });
    }, 1000);
});