 //点击复选框变更是否可选所有地区运费模板
 function checkAllImg(){
	 if($("#checkAllImgID").attr("src")=="/yzfs-admin/static/image/zhong.png"){
		  $("#checkAllImgID").attr({"src":"/yzfs-admin/static/image/kong.png"});
		  $("#firstWeight0").attr({"disabled":"true"});
		  $("#firstPrice0").attr({"disabled":"true"});
		  $("#secondWeight0").attr({"disabled":"true"});
		  $("#secondPrice0").attr({"disabled":"true"});
		  $("#isUsed").val(1);
	 }else{
		  $("#checkAllImgID").attr({"src":"/yzfs-admin/static/image/zhong.png"});
		  $("#firstWeight0").removeAttr("disabled");
		  $("#firstPrice0").removeAttr("disabled");
		  $("#secondWeight0").removeAttr("disabled");
		  $("#secondPrice0").removeAttr("disabled");
		  $("#isUsed").val(0);
	} 
   }
 
 
var newTR="<tr >"+
		    "<td>选择可配送区域 <a data-toggle='modal' href='#freeDialog'> <i class='fa fa-edit'></i></a> <i class='fa fa-trash-o delImage'></i><br/>" +
		    "<span style='color:red;'>请选择可配送区域</span>" +
		    "<input type='hidden'/>" +
		    "<input type='hidden'/>" +
		    "</td>"+
		    "<td><input type='text' id='firstWeight0' name='firstWeight0'/></td>"+
		    "<td><input type='text' id='firstPrice0' name='firstPrice0'/></td>"+
		    "<td><input type='text' id='secondWeight0' name='secondWeight0'/></td>"+
		    "<td><input type='text' id='secondPrice0' name='secondPrice0'/></td>"+
		    "</tr>";
//新增运费模板



 function registerFreeTREvent() {
     var freelength=$(".freeTbody tr").length;
     $(".freeTbody").find("tr").last().after(newTR);
     $(".freeTbody").find("tr").last().find("td input[type=text]").eq(0).attr("id", "firstWeight"+freelength);
     $(".freeTbody").find("tr").last().find("td input[type=text]").eq(0).attr("name", "firstWeight"+freelength);
     $(".freeTbody").find("tr").last().find("td input[type=text]").eq(1).attr("id", "firstPrice"+freelength);
     $(".freeTbody").find("tr").last().find("td input[type=text]").eq(1).attr("name", "firstPrice"+freelength);
     $(".freeTbody").find("tr").last().find("td input[type=text]").eq(2).attr("id", "secondWeight"+freelength);
     $(".freeTbody").find("tr").last().find("td input[type=text]").eq(2).attr("name", "secondWeight"+freelength);
     $(".freeTbody").find("tr").last().find("td input[type=text]").eq(3).attr("id", "secondPrice"+freelength);
     $(".freeTbody").find("tr").last().find("td input[type=text]").eq(3).attr("name", "secondPrice"+freelength);
     $(".freeTbody").find("tr").last().addClass("freeTR"+freelength);
     $(".freeTbody").find("tr").last().find("span").addClass("errorTitle"+freelength);
     $(".freeTbody").find("tr").last().find("input[type=hidden]").eq(0).attr({"id":"dataSelectedCID"+freelength});
     $(".freeTbody").find("tr").last().find("input[type=hidden]").eq(0).attr({"name":"dataSelectedCID"+freelength});
     $(".freeTbody").find("tr").last().find("input[type=hidden]").eq(1).attr({"id":"dataSelectedName"+freelength});
     $(".freeTbody").find("tr").last().find("input[type=hidden]").eq(1).attr({"name":"dataSelectedName"+freelength});
     $(".freeTbody").find("tr").last().find("td img").eq(0).attr("onclick", "editFree('"+freelength+"')");
     $(".freeTbody").find("tr").last().find("td img").eq(1).attr("onclick", "delFree('"+freelength+"')");
 }


//删除新增的运费模板列
function delFree(rowid){ 
	$(".freeTR"+rowid).remove();
	var freelength=$(".freeTbody tr").length;
	if(freelength==1){
		return;
	}
	for(var i=1;i<=freelength;i++){
		$(".freeTbody").children('tr').eq(i).attr({"class":"freeTR"+i});
		$(".freeTR"+i).find("td input[type=text]").eq(0).attr("id", "firstWeight"+i);
		$(".freeTR"+i).find("td input[type=text]").eq(0).attr("name", "firstWeight"+i);
		$(".freeTR"+i).find("td input[type=text]").eq(1).attr("id", "firstPrice"+i);
		$(".freeTR"+i).find("td input[type=text]").eq(1).attr("name", "firstPrice"+i);
		$(".freeTR"+i).find("td input[type=text]").eq(2).attr("id", "secondWeight"+i);
		$(".freeTR"+i).find("td input[type=text]").eq(2).attr("name", "secondWeight"+i);
		$(".freeTR"+i).find("td input[type=text]").eq(3).attr("id", "secondPrice"+i);
		$(".freeTR"+i).find("td input[type=text]").eq(3).attr("name", "secondPrice"+i);
		$(".freeTR"+i).find("td a").eq(0).attr("onclick", "editFree('"+i+"')");
		$(".freeTR"+i).find("td img").eq(1).attr("onclick", "delFree('"+i+"')");
	}
}




//异步查询运费列表
function batchfreeList(){
	 $.ajax({
		 url:contextPath+"/admin/free/batchfreeList",
		 type:"get",
		 dataType:"html",
 		 beforeSend: function () {  
			onDialog();
		 },
		 success:function(response){
			 $(".freeTbody").append(response);
		 },
         complete: function () {  
 			offDialog();
 	     },
		 error:function(data){
			 ohSnap("系统异常",'black');
		 }
	 });
	
}

//查询省份列表
// function initProvinceList(){
// 	 $.ajax({
// 		 url:contextPath+"/admin/free/pList",
// 		 data:{lv:1},
// 		 type:"post",
// 		 success:function(data){
// 			 $.each(data.list,function(key,val){
// 				 if(key==0){
// 					 $("#provinceId").append("<option selected value='"+val.id+"'>"+val.name+"</option>");
// 					 $("#provinceId").append("<input type='hidden' id='"+val.id+"' value='"+val.name+"'/>");
// 					 $(".current_place").text(val.name);
// 					 initCityList(val.id);
// 				 }else{
// 					 $("#provinceId").append("<option value='"+val.id+"'>"+val.name+"</option>");
// 					 $("#provinceId").append("<input type='hidden' id='"+val.id+"' value='"+val.name+"'/>");
// 				 }
// 			 })
//
// 		 },
// 		 error:function(data){
// 			 ohSnap("系统异常",'black');
// 		 }
// 	 });
//
// }

//省份点击事件
$("#provinceId").change(function(){
	var pid=$("#provinceId").val();
	initCityList(pid);
})


//初始化城市列表
function initCityList(pid){
	$(".current_place").text($("#"+pid).val());
	$(".cities").html("");
	 $.ajax({
		 url:contextPath+"/admin/free/pList",
		 data:{pid:pid},
		 type:"post",
		 success:function(data){
			 var cidlength=$("#address"+pid).find(".cityDiv").length;
			 if(cidlength==data.list.length){
			    $(".cities").append("<li> <label class='checkbox'><i onclick='checkAll();' name='checkCityItemImg'    class='fa fa-check-circle checkAllImg '><span>全选</span> </label></li>");
			 }else{
			    $(".cities").append("<li> <label class='checkbox'><i  onclick='checkAll();'  name='checkCityItemImg'  class='fa fa-circle-thin checkAllImg'><span>全选</span> </label></li>");
			 }
			 $.each(data.list,function(key,val){
				 var selectedCID=$("#selectedCID"+val.id).val();
				 if(selectedCID==val.id){
					 $(".cities").append("<li> <label class='checkbox'><i   name='checkCityItemImg' onclick='checkItem("+key+");' class='fa fa-check-circle checkImg"+key+"'><input type='hidden' class='js_areas_checkitem' id='checkImg"+key+"' name='checkCname"+val.id+"'  data-name='"+val.name+"' data-myid='"+val.id+"' type='text'><span>"+val.name+"</span> </label></li>");
				 }else{
					 $(".cities").append("<li> <label class='checkbox'><i   name='checkCityItemImg' onclick='checkItem("+key+");' class='fa fa-circle-thin checkImg"+key+"'><input type='hidden' class='js_areas_checkitem' id='checkImg"+key+"'  name='checkCname"+val.id+"' data-name='"+val.name+"' data-myid='"+val.id+"' type='text'><span>"+val.name+"</span> </label></li>");
				 }
				
			 })
		 },
		 error:function(data){
			 ohSnap("系统异常",'black');
		 }
	 });
}

var index=0;
//全选或者不全选
function checkAll(){
	 var length=$(".cities").find("li").length-1;
	 if($(".checkAllImg").attr("src")=="/yzfs-admin/static/image/kong.png"){
		 $(".checkbox").find("img").attr({"src":"/yzfs-admin/static/image/zhong.png"});
		 index=length;
	 }else{
		 $(".checkbox").find("img").attr({"src":"/yzfs-admin/static/image/kong.png"});
		 index=0;
	 }
	 console.log("全"+index);
	 checkAllMoveCity(index);
	 
}

//单个选择城市

function checkItem(key){
	 var length=$(".cities").find("li").length-1;
	 if($(".checkAllImg").attr("src")=="/yzfs-admin/static/image/zhong.png"){
		 index=length;
	 }
	 console.log("单"+index);
	 if($(".checkImg"+key).attr("src")=="/yzfs-admin/static/image/kong.png"){
		 $(".checkImg"+key).attr({"src":"/yzfs-admin/static/image/zhong.png"});
		 index=index+1;
		 if(length==index){
			 $(".checkAllImg").attr({"src":"/yzfs-admin/static/image/zhong.png"}); 
		 }
	 }else{
		 $(".checkImg"+key).attr({"src":"/yzfs-admin/static/image/kong.png"});
		 index=index-1;
		 if(length!=index){
			 $(".checkAllImg").attr({"src":"/yzfs-admin/static/image/kong.png"}); 
		 }
	 }
	 //添加或者移除已选择或者已取消的城市信息
	 moveDistrict(key);
}

var newPname="<div class='allAddress'><div class='nodes-item node-last clearfix provinceDiv'>"+
				"<a href='javascript:;'><img src='/yzfs-admin/static/image/icon/subIcon.png' class='iconclass imgItemClass'/></a>"+
				"<div class='node-item  add-member pull-left'>"+
		        "<span class='pull-left m-name provinceName'>河北省</span>"+
		        "<input type='hidden' id='selectedPID' name='selectedPID'/>"+
		        "</div></div>";

var newCname="<div class='nodes-items vline clearfix cityDiv'>"+
				"<div class='nodes-items  clearfix'>"+
				"<div class='node-items  add-member pull-left' style='padding-left:40px;'>"+
				"<span class='pull-left m-name cityName' >石家庄市</span>"+
				"<input type='hidden' id='selectedCID' name='selectedCID'/>"+
				"</div></div></div>";


//将选中的城市自拼接到已选地址中
function moveDistrict(i){
	 var pname=$("#"+$("#provinceId").val()).val();//省份名称
	 var isExistpName=$("#"+$("#provinceId").val()).data('name');//查看是否存在该省份名称
	 var Pid=$("#provinceId").val();
	 var cid=$("#checkImg"+i).data('myid');
	 var cname=$("#checkImg"+i).data('name');
	 if($(".checkImg"+i).attr("src")=="/yzfs-admin/static/image/zhong.png"){
		if(pname!=isExistpName){
			if($(".allAddress").length==0){ 
				$(".org-nodes").append(newPname);
				$(".allAddress").eq(0).attr({"id":"address"+Pid});
				$(".allAddress").eq(0).find(".provinceName").html(pname);
				$(".allAddress").eq(0).find(".imgItemClass").attr({"id":"treeIconClass"+Pid});
				$(".allAddress").eq(0).find(".imgItemClass").attr({"onclick":"checkTree('"+Pid+"')"});
				$(".allAddress").eq(0).find(".provinceDiv :input[type=hidden]").val(Pid);
				$(".allAddress").eq(0).find(".provinceDiv :input[type=hidden]").attr({"id":"selectedPID"+Pid});
				$("#address"+Pid).last().eq(0).find(".provinceDiv").after(newCname);
				$("#address"+Pid).find(".cityName").html(cname);
				$("#address"+Pid).eq(0).find(".cityDiv").attr({"id":"addressItem"+cid});
				$("#address"+Pid).eq(0).find(".cityDiv :input[type=hidden]").val(cid);
				$("#address"+Pid).eq(0).find(".cityDiv :input[type=hidden]").attr({"id":"selectedCID"+cid});
			}else{ 
				var pidObj=$("#selectedPID"+Pid).val();
				var cidObj=$("#selectedCID"+cid).val();
				if(Pid==pidObj){
				   if(cid!=cidObj){
				      $("#address"+Pid).find(".cityDiv").last().after(newCname);
				      $("#address"+Pid).find(".cityDiv").last().find(".cityName").html(cname);
				      $("#address"+Pid).find(".cityDiv").last().attr({"id":"addressItem"+cid});
				      $("#address"+Pid).find(".cityDiv").last().find("input[type=hidden]").val(cid);
				      $("#address"+Pid).find(".cityDiv").last().find("input[type=hidden]").attr({"id":"selectedCID"+cid});
				      $("#address"+Pid).find(".cityDiv").removeClass("hide");
				      $("#treeIconClass"+Pid).attr({"src":"/yzfs-admin/static/image/icon/subIcon.png"});
				   }
				}else{
				  $(".allAddress").last().after(newPname);
				  $(".allAddress").last().eq(0).attr({"id":"address"+Pid});
				  $(".allAddress").last().eq(0).find(".provinceName").html(pname);
				  $(".allAddress").last().eq(0).find(".imgItemClass").attr({"id":"treeIconClass"+Pid});
				  $(".allAddress").last().eq(0).find(".imgItemClass").attr({"onclick":"checkTree('"+Pid+"')"});
				  $(".allAddress").last().eq(0).find(".provinceDiv :input[type=hidden]").val(Pid);
				  $(".allAddress").last().eq(0).find(".provinceDiv :input[type=hidden]").attr({"id":"selectedPID"+Pid});
				  $("#address"+Pid).last().eq(0).find(".provinceDiv").after(newCname);
				  $("#address"+Pid).find(".cityName").last().html(cname);
				  $("#address"+Pid).last().find(".cityDiv :input[type=hidden]").val(cid);
				  $("#address"+Pid).last().find(".cityDiv :input[type=hidden]").attr({"id":"selectedCID"+cid});
				  $("#address"+Pid).find(".cityDiv").last().attr({"id":"addressItem"+cid});
				}
		  }
	 }
  }else{
	  $("#addressItem"+cid).remove();
	  if($("#address"+Pid).find(".cityDiv").length==0){
		  $("#address"+Pid).remove();
	  }
  }
}

//全选移动城市区域
function checkAllMoveCity(index){
	 var length=$(".cities").find("li").length-1;
	 var pname=$("#"+$("#provinceId").val()).val();//省份名称
	 var Pid=$("#provinceId").val();
	 var pidObj=$("#selectedPID"+Pid).val();
	 if(index>0){
		 console.log(2);
		 if(Pid!=pidObj){
			if($(".allAddress").length==0){ 
				 $(".org-nodes").append(newPname);
				 $(".allAddress").eq(0).attr({"id":"address"+Pid});
				 $(".allAddress").eq(0).find(".provinceName").html(pname);
				 $(".allAddress").eq(0).find(".imgItemClass").attr({"id":"treeIconClass"+Pid});
				 $(".allAddress").eq(0).find(".imgItemClass").attr({"onclick":"checkTree('"+Pid+"')"});
				 $(".allAddress").eq(0).find(".provinceDiv :input[type=hidden]").val(Pid);
				 $(".allAddress").eq(0).find(".provinceDiv :input[type=hidden]").attr({"id":"selectedPID"+Pid});
			}else{ 
				  $(".allAddress").last().after(newPname);
				  $(".allAddress").last().eq(0).attr({"id":"address"+Pid});
				  $(".allAddress").last().eq(0).find(".provinceName").html(pname);
				  $(".allAddress").last().eq(0).find(".imgItemClass").attr({"id":"treeIconClass"+Pid});
				  $(".allAddress").last().eq(0).find(".imgItemClass").attr({"onclick":"checkTree('"+Pid+"')"});
				  $(".allAddress").last().eq(0).find(".provinceDiv :input[type=hidden]").val(Pid);
				  $(".allAddress").last().eq(0).find(".provinceDiv :input[type=hidden]").attr({"id":"selectedPID"+Pid});
		    }
		 }
		 $("#address"+Pid).find(".cityDiv").remove();
		 for(var i=0;i<length;i++){
			 var cid=$("#checkImg"+i).data('myid');
			 var cname=$("#checkImg"+i).data('name');
			
			 if(i==0){
				$("#address"+Pid).find(".provinceDiv").after(newCname);
				$("#address"+Pid).eq(0).find(".cityName").html(cname);
				$("#address"+Pid).eq(0).find(".cityDiv").attr({"id":"addressItem"+cid});
				$("#address"+Pid).eq(0).find(".cityDiv :input[type=hidden]").val(cid);
				$("#address"+Pid).eq(0).find(".cityDiv :input[type=hidden]").attr({"id":"selectedCID"+cid});
			 }else{
		        $("#address"+Pid).find(".cityDiv").last().after(newCname);
		        $("#address"+Pid).find(".cityDiv").last().find(".cityName").html(cname);
		        $("#address"+Pid).find(".cityDiv").last().attr({"id":"addressItem"+cid});
		        $("#address"+Pid).find(".cityDiv").last().find("input[type=hidden]").val(cid);
		        $("#address"+Pid).find(".cityDiv").last().find("input[type=hidden]").attr({"id":"selectedCID"+cid});
		        $("#address"+Pid).find(".cityDiv").removeClass("hide");
		        $("#treeIconClass"+Pid).attr({"src":"/yzfs-admin/static/image/icon/subIcon.png"});
			 }
		 }
	 }else{
		 $("#address"+Pid).remove();
	 }

	
}


//树形展开与关闭
function checkTree(Pid){
	if($("#treeIconClass"+Pid).attr("src")=="/yzfs-admin/static/image/icon/addIcon.png"){
		console.log("#address"+Pid)
		$("#treeIconClass"+Pid).attr({"src":"/yzfs-admin/static/image/icon/subIcon.png"});
		$("#address"+Pid).find(".cityDiv").removeClass("hide");
	}else{
		$("#treeIconClass"+Pid).attr({"src":"/yzfs-admin/static/image/icon/addIcon.png"});
		$("#address"+Pid).find(".cityDiv").addClass("hide");
	}
}

//编辑运费模板列
function editFree(rowid){ 
	$("#saveDistrictId").attr({"onclick":"saveDistrict('"+rowid+"')"});
	$("img[name=checkCityItemImg]").attr({"src":"/yzfs-admin/static/image/kong.png"});
	$(".org-nodes").html("");
}

//保存区域ID
function saveDistrict(rowid){
		var dataAreaCIDList='';
		var areadList='';
		$("input[name=selectedPID]").each(function (i) {
			var citList='';
            var pid = $(this).val();
            areadList+=$("#"+pid).val()+"[";
            if (isNotBlank(pid)) {
        		$("#address"+pid).find("input[name=selectedCID]").each(function (j) {
                    var cid = $(this).val();
                    if (isNotBlank(cid)) {
                    	citList += cid + ",";
                    	areadList+=$("#selectedCID"+cid).parent().find(".cityName").text()+",";
                    }
                });
        		areadList=areadList.substring(0,areadList.length-1);
        		areadList+="]、"
            }
            dataAreaCIDList+=citList;
        });
	    if(areadList==undefined || areadList==''){
			 return;
		}
		var dataAreaList=areadList.substring(0,areadList.length-1);
		$(".errorTitle"+rowid).text(dataAreaList);
		$(".errorTitle"+rowid).css({"color":"#2fa80d"});
		$("#dataSelectedCID"+rowid).val(dataAreaCIDList);
		$("#dataSelectedName"+rowid).val(dataAreaList);
}

//保存运费模板信息
$("#save").click(function(){
	$("input[type=text]").css({"border-color":""});
	var length=$(".freeTbody").find("tr").length;
	$("#tempLength").val(length);
	 if($("#checkAllImgID").attr("src")=="/yzfs-admin/static/image/zhong.png"){
		  $("#isUsed").val(1);
	 }else{
		  $("#isUsed").val(0);
	} 
	var tempForm=$("form[name=tempForm]")
	 $.ajax({
		 url:contextPath+"/admin/free/saveTemp",
		 data:tempForm.serialize(),
		 type:"post",
 		 beforeSend: function () {  
			onDialog();
		 },
		 success:function(data){
			 if(data.code==100){
				 ohSnap(data.msg,'black');
			 }else{
				 ohSnap(data.msg,'black');
				 publicOnfocus(data.sign,'');
			 }
		 },
		 complete: function () {  
 			offDialog();
 	     }, 
		 error:function(data){
			 ohSnap("系统异常",'black');
		 }
	 });
})

//刷新
function refresh(){
	window.location.href=contextPath+"/admin/free/freeList";
}

//修改配置
function configChange(id,sign,value){
	var msg="";
	if(sign==0){
		msg="确认要停用免邮费吗?";
	}else{
		msg="确认要启用免邮费吗?";
	}
	$(".sysConfigValue").find("input").val($("#"+id).val());
	$(".isUsedStaticTitle").text(msg);
	$("#isUsedComfirSave").click(function(){
		 $.ajax({
			 url:contextPath+"/admin/config/editReadListByParams",
			 data:{id:id,status:sign,value:$(".sysConfigValue").find("input").val()},
			 type:"post",
	 		 beforeSend: function () {  
				onDialog();
			 },
			 success:function(data){
				 $("#Delete_S_cancel").trigger("click");
				 ohSnap(data.msg,'black');
				 refresh();
			 },
			 complete: function () {  
	 			offDialog();
	 	     }, 
			 error:function(data){
				 ohSnap("系统异常",'black');
			 }
		 });
		
	})
	
}


//获取光标的公共方法
function publicOnfocus(id,idError){
	 $("#"+id).css({"border-color":"#ff0000"});
	 $("#"+idError).css({"color":"#ff0000"});
	 $("#"+id).focus();
}

//显示正确赋值
function publicOnRight(id,idError){
	 $("#"+id).css({"border-color":"#4aaf00"});
	 $("#"+idError).html("");
}






    

