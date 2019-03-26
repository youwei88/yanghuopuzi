/**
 * Created by shinez on 2017/4/19.
 */
const Loreal={
    EVENT_TYPE_ONLINE :2,
    EVENT_TYPE_OFFLINE: 1,
};
(()=>{
    if(Shinez){
        const openid = Shinez.getCookie("openid");
        if(openid==null){
            console.warn("unable to get openid info from cookie");
            location.href=Shinez.redirect("/core/oauth2?apiRoot="+custom.apiRoot+"&re="+encodeURIComponent(location.href)+"&mpid="+custom.mpid);
        }else {
            let userStr = sessionStorage.getItem("user");
            if (userStr == null || userStr == "") {
                //获取用户信息
                Shinez.xhr("GET", "/user/my", null, false, (ret) => {
                    console.log(ret);
                    if (ret.status == 0) {
                        //保存
                        userStr = JSON.stringify(ret.data.user);
                        sessionStorage.setItem("user", userStr);
                    }
                    if(ret.status==custom.LOGIN_STATUS_LOST){
                        Shinez.delCookie("openid");
                        sessionStorage.clear();
                        location.href=Shinez.redirect("/core/oauth2?re="+location.href+"&mpid="+custom.mpid);
                    }
                    if (ret.status == custom.UN_REGISTER) {
                        //去注册页面
                        location.href = "会员注册绑定.html";
                        return;
                    }
                    if (ret.status == -1) {
                        Shinez.showAlert(ret.info);
                    }
                }, "json");
            }
        }
    }else {
        console.error("Object Shinez is not loaded by this project,so you can't use this js")
    }
})();
