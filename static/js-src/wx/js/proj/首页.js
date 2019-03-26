/**
 * Created by Administrator on 2017/4/25.
 */

//渲染user
$(() => {
    const userStr = sessionStorage.getItem("user");
    $("[name=userNo2]").css("float", "right");
    const user = JSON.parse(userStr);
    $.each(user, (k, v) => {
        $("[name=" + k + "]").html(v);
    });

    Shinez.showAlert("正在获取活动信息...",null,300);
    setTimeout(()=>{
        Shinez.get("/event", (ret) => {
            if (ret.status == 0) {
                const onlineEvent = ret.data.onlineEvent;
                const offlineEvent = ret.data.offlineEvent;
                loadEvent(onlineEvent, "online-event");
                loadEvent(offlineEvent, "offline-event");
            }
        });
    },450);

});

function loadEvent(eventObject, className) {
    if (eventObject == null) {
        $("." + className).find("[name=startTime]").html("");
        $("." + className).find("[name=eventStatus]").html("未开始");
        $("." + className).find(".act-ing").addClass("ending");
        return;
    }
    $("." + className).find("[name=startTime]").html(Shinez.FormateDate(new Date(eventObject.startTime), "MM月dd日 HH:mm") + " 开始");

    //noinspection JSValidateTypes
    if(eventObject.eventStatus=="正在进行")
    {
        $("." + className).find("[name=eventStatus]").html("火热进行中...");
        $("." + className).find(".act-ing").removeClass("ending");
    }else{
        $("." + className).find("[name=eventStatus]").html(eventObject.eventStatus);
        $("." + className).find(".act-ing").addClass("ending");
    }

    $("."+className).find(".intro")[0].onclick=()=>{location.href="活动简介.html?id="+eventObject.id+"&type="+eventObject.eventType;}
    $("."+className).find(".main")[0].onclick=()=>{
        sessionStorage.setItem("etype",eventObject.eventType);
        sessionStorage.setItem("eid",eventObject.id);
        location.href="商品列表.html";
    }
}