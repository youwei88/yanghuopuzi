/**
 * Created by Administrator on 2017/4/25.
 */
//渲染user
$(() => {
    const userStr = sessionStorage.getItem("user");
    const user = JSON.parse(userStr);
    $.each(user, (k, v) => {
        $("[name=" + k + "]").html(v);
    });
});