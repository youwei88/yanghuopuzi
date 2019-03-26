/**
 * Created by Administrator on 2017/4/25.
 */
//渲染user
$(() => {
    let id;
    const userStr = sessionStorage.getItem("user");
    const user = JSON.parse(userStr);
    $.each(user, (k, v) => {
        $("[name=" + k + "]").html(v);
        if(k=="id")
            id=v;
    });

    $(".look-btn")[0].onclick = () => {
        const newTel = document.getElementsByName("tel")[0].value;
        if (newTel == "" || newTel.length != 11) {
            Shinez.showAlert("手机号格式有误");
            return;
        }
        if(id==null){
            Shinez.showAlert("用户未登录");
            return;
        }
        Shinez.showConfirm("要将您的手机号码修改为" + newTel + "?", () => {
            Shinez.put("/user",{tel:newTel,id:id},(ret)=>{
                if(ret.status==0){
                    user.tel=newTel;
                    sessionStorage.setItem("user",JSON.stringify(user));
                    location.href="修改成功.html";
                    return;
                }
                if(ret.status==-1){
                    Shinez.showAlert(ret.info);
                }
            });
        })
    };

});