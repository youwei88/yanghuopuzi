  window.addEventListener('load', function() {
    var initX; //触摸位置
    var moveX; //滑动时的位置
    var X = 0; //移动距离
    var objX = 0; //目标对象位置
    $(".delete-right").each(function(){

   
    $(this)[0].addEventListener('touchstart', function(event) {
      //event.preventDefault();
      var obj = event.target;
      obj=$(obj).eq(0).parents(".list-li")[0];
       // console.log(obj);
        initX = event.targetTouches[0].pageX;
        objX = (obj.style.WebkitTransform.replace(/translateX\(/g, "").replace(/px\)/g, "")) * 1; 
      if (objX == 0) {
        $(this)[0].addEventListener('touchmove', function(event) {
         // event.preventDefault();
          var obj = event.target;
          obj=$(obj).eq(0).parents(".list-li")[0];
          if (obj.className == "list-li") {
            moveX = event.targetTouches[0].pageX;
            X = moveX - initX;
            if (X >= 0) {
              obj.style.WebkitTransform = "translateX(" + 0 + "px)";
            } else if (X < 0) {
              var l = Math.abs(X);
              obj.style.WebkitTransform = "translateX(" + -l + "px)";
              if (l > 50) {
                l = 50;
                obj.style.WebkitTransform = "translateX(" + -l + "px)";
              }
            }
          }
        });
      } else if (objX < 0) {
        $(this)[0].addEventListener('touchmove', function(event) {
          //event.preventDefault();
         var obj = event.target;
         obj=$(obj).eq(0).parents(".list-li")[0];
          if (obj.className == "list-li") {
            moveX = event.targetTouches[0].pageX;
            X = moveX - initX;
            if (X >= 0) {
              var r = -50 + Math.abs(X);
              obj.style.WebkitTransform = "translateX(" + r + "px)";
              if (r > 0) {
                r = 0;
                obj.style.WebkitTransform = "translateX(" + r + "px)";
              }
            } else { //向左滑动
              obj.style.WebkitTransform = "translateX(" + -50 + "px)";
            }
          }
        });
      }

    })
    $(this)[0].addEventListener('touchend', function(event) {
      //event.preventDefault();
      var obj = event.target;
      obj=$(obj).eq(0).parents(".list-li")[0];
      if (obj.className == "list-li") {
        objX = (obj.style.WebkitTransform.replace(/translateX\(/g, "").replace(/px\)/g, "")) * 1;
        if (objX > -25) {
          obj.style.WebkitTransform = "translateX(" + 0 + "px)";
          objX = 0;
        } else {
          obj.style.WebkitTransform = "translateX(" + -50 + "px)";
          objX = -50;
        }
      }
    })
   })

  })