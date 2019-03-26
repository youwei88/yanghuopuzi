/**
 * Created by Administrator on 2017/4/25.
 */
let schoolList;
$().ready(function () {
    $("[name=userNo2]").parent("div").css("margin-top", ".12rem");
    $("[name=schoolDist]").parent("div").css("margin-top", ".12rem");
    setTimeout(() => {
        Shinez.get("/schoolyard", function (ret) {
            if (ret.status == 0) {
                schoolList = ret.data.list;
                $.each(schoolList, (k, v) => {
                    "use strict";
                    $("[name=schoolId]").append("<option value='"+v.id+"'>"+v.school+"</option>");
                });
            }
        });


        $(".look-btn")[0].onclick = () => {
            $(".look-btn")[0].disabled = true;
            $.each(schoolList, (k, v) => {
                if (v.id == $("[name=schoolId]").val()) {
                    $("[name=school]").val(v.school);
                    return false;
                }
            });

            //jquery1.2版本问题 无法序列化input[type=number]类型
            Shinez.post("/user", $("form").serialize(), (ret) => {
                $(".look-btn")[0].disabled = false;
                if (ret.status == 0) {
                    $(".look-btn")[0].disabled = true;
                    const userStr = JSON.stringify(ret.data.user);
                    sessionStorage.setItem("user", userStr);
                    Shinez.showAlert("注册成功");

                    setTimeout(() => {
                        location.href = "注册完成.html"
                    }, 1000);

                }
                if (ret.status == -1) {
                    Shinez.showAlert(ret.info);
                }
            });
        };
    }, 500);
})

