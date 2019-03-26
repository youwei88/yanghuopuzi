/**
 * Created by shinez on 2017/2/6.
 */
"use strict";
let Shinez = {};

const custom = {
    mpid: 8,
    UN_REGISTER: 50,//用户未注册
    LOGIN_STATUS_LOST: 420,//未能获取用户授权信息
    domain:"wx.loreal.wmeimob.com",
    apiRoot:"api",
    version:""
};
(function () {

    function getsec(str) {
        if (str == null)return;
        let str1 = str.substring(1, str.length) * 1;
        let str2 = str.substring(0, 1);
        if (str2 == "s") {
            return str1 * 1000;
        }
        else if (str2 == "h") {
            return str1 * 60 * 60 * 1000;
        }
        else if (str2 == "d") {
            return str1 * 24 * 60 * 60 * 1000;
        }
    }

    if (jQuery) {
        $(function () {
            $("body")
                .append('<div id="m-tip" ' +
                    'style="display:none;' +
                    'background-color: rgba(0, 0, 0, 0.70);' +
                    'font-size: 13px;' +
                    'padding: 4px 9px;' +
                    'position: fixed;' +
                    'top: 78.8%;' +
                    'left: 50%;text-align: center;' +
                    ' -ms-transform: translate(-50%,-50%);' +
                    ' -moz-transform: translate(-50%,-50%); ' +
                    '-o-transform: translate(-50%,-50%);' +
                    'transform: translate(-50%,-50%);' +
                    'z-index: 99999;color: white;">tip</div>');
        });


        Shinez = {
            test: () => {
                console.log("hello shinez")
            },
            deepClone: function (initalObj, finalObj) {
                let obj = finalObj || {};
                for (let i in initalObj) {
                    let prop = initalObj[i];
                    // 避免相互引用对象导致死循环，如initalObj.a = initalObj的情况
                    if (prop === obj) {
                        continue;
                    }
                    if (typeof prop === 'object') {
                        obj[i] = (prop.constructor === Array) ? [] : Object.create(prop);
                    } else {
                        obj[i] = prop;
                    }
                }
                return obj;
            },
            loading: () => {
                $("#loadingToast").css("display", "block")
            },
            loadingComplete: () => {
                $("#loadingToast").css("display", "none")
            },
            showConfirm: (msg, callback) => {
                if (confirm(msg))
                    callback();
            },

            //增
            post: (url, data, successCallback) => {
                if (typeof(data) == "function") {
                    successCallback = data;
                    data = {};
                }
                return Shinez.xhr("POST", url, data, true, successCallback, "json");
            },
            //查
            get: (url, data, successCallback) => {
                if (typeof(data) == "function") {
                    successCallback = data;
                    data = {};
                }
                return Shinez.xhr("GET", url, data, true, successCallback, "json");
            },
            //删
            del: (url, data, successCallback) => {
                if (typeof(data) == "function") {
                    successCallback = data;
                    data = {};
                }
                return Shinez.xhr("DELETE", url, data, true, successCallback, "json");
            },
            //update
            put: (url, data, successCallback) => {
                if (typeof(data) == "function") {
                    successCallback = data;
                    data = {};
                }
                return Shinez.xhr("PUT", url, data, true, successCallback, "json");
            },
            xhr: function (type, url, data, async, successCallback, dataType) {
                return $.ajax({
                    url: location.origin+"/"+custom.apiRoot+url,
                    type: type,
                    async: async,
                    data: data,
                    dataType: dataType,
                    success: successCallback,
                    beforeSend: function (XMLHttpRequest) {
                        Shinez.loading();
                        $("button").attr("disabled", "disabled");
                        $("input").attr("disabled", "disabled");
                        XMLHttpRequest.setRequestHeader("token", Shinez.getCookie("token"));
                        $("*[type=submit]").attr("disabled", "disabled");
                    },
                    complete: function (ret) {
                        Shinez.loadingComplete();
                        if (ret.getResponseHeader("token") != null) {
                            Shinez.setCookie("token", ret.getResponseHeader("token"));
                        }
                        $("*[type=submit]").removeAttr("disabled");
                        $("input").removeAttr("disabled");
                        $("button").removeAttr("disabled");
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        console.log(XMLHttpRequest.responseText);
                    }
                });
            },
            redirect:(url)=>{
                return location.origin+"/"+custom.apiRoot+url;
            },
            FormateDate: function (now, mask) {
                const d = now;
                const zeroize = function (value, length) {
                    if (!length) length = 2;
                    value = String(value)
                    let i, zeros;
                    for (i = 0, zeros = ''; i < (length - value.length); i++) {
                        zeros += '0';
                    }
                    return zeros + value;
                };

                return mask.replace(/"[^"]*"|'[^']*'|\b(?:d{1,4}|m{1,4}|yy(?:yy)?|([hHMstT])\1?|[lLZ])\b/g, function ($0) {
                    switch ($0) {
                        case 'd':
                            return d.getDate();
                        case 'dd':
                            return zeroize(d.getDate());
                        case 'ddd':
                            return ['Sun', 'Mon', 'Tue', 'Wed', 'Thr', 'Fri', 'Sat'][d.getDay()];
                        case 'dddd':
                            return ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'][d.getDay()];
                        case 'M':
                            return d.getMonth() + 1;
                        case 'MM':
                            return zeroize(d.getMonth() + 1);
                        case 'MMM':
                            return ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'][d.getMonth()];
                        case 'MMMM':
                            return ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'][d.getMonth()];
                        case 'yy':
                            return String(d.getFullYear()).substr(2);
                        case 'yyyy':
                            return d.getFullYear();
                        case 'h':
                            return d.getHours() % 12 || 12;
                        case 'hh':
                            return zeroize(d.getHours() % 12 || 12);
                        case 'H':
                            return d.getHours();
                        case 'HH':
                            return zeroize(d.getHours());
                        case 'm':
                            return d.getMinutes();
                        case 'mm':
                            return zeroize(d.getMinutes());
                        case 's':
                            return d.getSeconds();
                        case 'ss':
                            return zeroize(d.getSeconds());
                        case 'l':
                            return zeroize(d.getMilliseconds(), 3);
                        case 'L':
                            var m = d.getMilliseconds();
                            if (m > 99) m = Math.round(m / 10);
                            return zeroize(m);
                        case 'tt':
                            return d.getHours() < 12 ? 'am' : 'pm';
                        case 'TT':
                            return d.getHours() < 12 ? 'AM' : 'PM';
                        case 'Z':
                            return d.toUTCString().match(/[A-Z]+$/);
                        // Return quoted strings with the surrounding quotes removed
                        default:
                            return $0.substr(1, $0.length - 2);
                    }
                });
            },

            dateDiff: function (hisTime, nowTime) {
                if (!arguments.length) return '';
                let arg = arguments,
                    now = arg[1] ? arg[1] : new Date().getTime(),
                    diffValue = now - arg[0],
                    result = '',
                    minute = 1000 * 60,
                    hour = minute * 60,
                    day = hour * 24,
                    halfamonth = day * 15,
                    month = day * 30,
                    year = month * 12,
                    _year = diffValue / year,
                    _month = diffValue / month,
                    _week = diffValue / (7 * day),
                    _day = diffValue / day,
                    _hour = diffValue / hour,
                    _min = diffValue / minute;

                // if (_year >= 1) result = parseInt(_year) + "年前";
                // else if (_month >= 1) result = parseInt(_month) + "个月前";
                // else if (_week >= 1) result = parseInt(_week) + "周前";
                if (_year >= 1 || _month >= 1 || _week >= 1) result = FormatDate(new Date(hisTime), 'yyyy-MM-dd HH:mm:ss');
                else if (_day >= 1) result = parseInt(_day) + "天前";
                else if (_hour >= 1) result = parseInt(_hour) + "个小时前";
                else if (_min >= 1) result = parseInt(_min) + "分钟前";
                else result = "刚刚";
                return result;
            },
            showAlert: (msg, callback, callbackTimeout) => {
                $("#m-tip").fadeIn(200).html(msg);
                setTimeout(() => {
                    $("#m-tip").fadeOut(1000);
                    if (callback != null) callback();
                }, callbackTimeout == null ? 1500 : callbackTimeout);

            },

            setCookie: (name, value, time) => {
                let strsec = getsec(time);
                let exp = new Date();
                exp.setTime(exp.getTime() + strsec * 1);
                document.cookie = name + "=" + escape(value) + (time == null ? "" : ";expires=" + exp.toGMTString());
            },
            getCookie: (name) => {
                let arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
                if (arr = document.cookie.match(reg))
                    return unescape(arr[2]);
                else
                    return null;
            },
            getQueryString: (name) => {
                let arr, reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
                let qs = location.search.substring(1, location.search.length);
                if (arr = qs.match(reg))
                    return unescape(arr[2]);
                else
                    return null;
            },
//删除cookies
            delCookie: (name) => {
                let exp = new Date();
                exp.setTime(exp.getTime() - 1);
                let cval = Shinez.getCookie(name);
                if (cval != null)
                    document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
            },
        };
    } else {
        console.error("unable to find jquery!");
    }

})();