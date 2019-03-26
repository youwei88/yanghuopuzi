window.onload=function () {
    const etype=sessionStorage.getItem("etype");
    if(etype==Loreal.EVENT_TYPE_ONLINE){
        document.getElementById("tip-online").style.display="";
    }else{
        document.getElementById("tip-offline").style.display="";
    }
    document.getElementsByClassName("look-btn")[0].onclick=function () {
        location.href="订单.html";
    }
};