/**
 * Created by Administrator on 2017/4/28.
 */

$(() => {
    let flag = true;
    const openid = Shinez.getQueryString("u");
    const tm = Shinez.getQueryString("tid");
    if (new Date().getTime() - tm > 1000 * 60 * 5) {
        Shinez.showAlert("二维码已失效");
        flag = false;
    }
    if (flag) {

        setTimeout(() => {
            Shinez.get("/user/" + openid, (ret) => {
                "use strict";
                if (ret.status == 0) {
                    $.each(ret.data.user, (k, v) => {
                        if (k == "createTime")
                            v = Shinez.FormateDate(new Date(v), "yyyy-MM-dd");
                        $("[name=" + k + "]").html(v);
                        if (k == "fnEventVolume") {
                            if (v == true)
                                $(".look-btn").html("已开启").css("background", "#cccccc");
                            else{
                                $(".look-btn").on("click", () => {
                                    if (confirm("确认开启？")) {
                                        Shinez.put("/user/" + openid, (r) => {
                                            if (r.status == 0) {
                                                // Shinez.showAlert("开启成功");
                                                // $(".look-btn").remove();
                                                location.href="开启成功.html";
                                                return;
                                            }
                                            if (r.status == -1) {
                                                Shinez.showAlert(r.info);
                                            };
                                        });
                                    }
                                });
                            }
                        }
                    });
                }
                if (ret.status == -1) {
                    Shinez.showAlert(ret.info);
                }
            })
        }, 400);


    }
});