/**
 * Created by Administrator on 2017/4/25.
 */
$(()=>{
    Shinez.showAlert("正在读取数据...");
    setTimeout(()=>{
        Shinez.get("/notice",(data)=>{
            $(".textarea").html(data.content);
        });
    },500);
});