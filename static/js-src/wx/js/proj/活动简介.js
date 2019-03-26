/**
 * Created by Administrator on 2017/4/25.
 */
const copen=Shinez.getCookie("openid");
$(() => {
    const typeId = Shinez.getQueryString("type");
    const id = Shinez.getQueryString("id");
    sessionStorage.setItem("eid",id);
    sessionStorage.setItem("etype",typeId);
    
    $(".textareabtn a").attr("href",copen==null?"首页.html":"商品列表.html");
    Shinez.showAlert("LOADING...");
    setTimeout(()=>{
    Shinez.get("/event/intro", {id: id}, (data) => {
        $(".textarea").html(data.intro);
    })},450);

});