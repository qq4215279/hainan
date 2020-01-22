	//========================================================================================================
	// String 函数　START
	//========================================================================================================
	/**
	 * Pad a string to a certain length with another string.
	 *
	 * @param {Number} size The resulting padded string length.
	 * @param {String} [str=" "] String to use as padding.
	 * @returns {String} The padded string.
	 */
	if (!String.prototype.leftPad) {
		String.prototype.leftPad = function (n,c) {var i; var a = this.split(''); for (i = 0; i < n - this.length; i++) {a.unshift (c);}; return a.join('');};
	};

	/**
	 * Pad a string to a certain length with another string.
	 *
	 * @param {Number} size The resulting padded string length.
	 * @param {String} [str=" "] String to use as padding.
	 * @returns {String} The padded string.
	 */
	if (!String.prototype.rightPad) {
		String.prototype.rightPad = function (n,c) {var i; var a = this.split(''); for (i = 0; i < n - this.length; i++) {a.push (c);}; return a.join('');};
	};

	String.prototype.trim = function() {
		return this.replace(/(^\s*)|(\s*$)/g, "");
	};

	String.prototype.trimL = function() {
		return this.replace(/(^\s*)/g, "");
	};

	String.prototype.trimR = function() {
		return this.replace(/(\s*$)/g, "");
	};
//	// 删除所有的空格
//	String.prototype.trimAll = function() {
//		return this.replace(/\s/g,"");
//	}

	// 左切
	String.prototype.left = function(n)
	{
	    return this.slice(0,n);
	};

	// 右切
	String.prototype.right = function(n)
	{
	    return this.slice(this.length-n);
	};

	String.prototype.mid = function(m, n)
	{
	    return this.substr(m, n);
	};

	// 生成重复的文字
	String.prototype.repeat=function(num)
	{
	    var tmpArr=[];
	    for(var i=0;i<num;i++)    tmpArr.push(this);
	    return tmpArr.join("");
	};

	// 空白检测
	String.prototype.isEmpty = function(){
		return this.toString() === "" ? true : false;
	};

	String.prototype.isNotEmpty = function(){
		return this.toString() === "" ? false : true;
	};

	// 空白检测（空格和全角空格）
	String.prototype.isBlank = function(){
		return /^\s*$/.test(this);
	};

	// 空白检测（空格和全角空格）
	String.prototype.isNotBlank = function(){
		return !/^\s*$/.test(this);
	};

	// 删除指定文字
	String.prototype.remove = function(current){
		return this.replace(new RegExp(current,"gm"), "");
	};

	// 替换
	String.prototype.replaceAll = function(current, target){
		return this.replace(new RegExp(current,"gm"), target);
	};

	String.prototype.endsWith = function(str) {
		if (str == null || str == "" || this.length == 0 || str.length > this.length)
			return false;
		if (this.substring(this.length - str.length) == str)
			return true;
		else
			return false;
		return true;
	};

	String.prototype.startsWith = function(str) {
		if (str == null || str == "" || this.length == 0 || str.length > this.length)
			return false;
		if (this.substr(0, str.length) == str)
			return true;
		else
			return false;
		return true;
	};

	/**
	 * 相等
	 */
	String.prototype.equals = function(str){
		if (str == null) {
			return (this == null);
		}
		if (this == null) {
			return (str == null);
		}
		return this.toString() === str.toString();
	};

	/**
	 * 字符串
	 */
	String.prototype.append = function(str){
		return 	this + str;
	};
	
	/**
	 * 默认字符串
	 */
	String.prototype.defaultString = function(){
		return ((this == null || this == undefined) ? "" : this);
	};
	
	/**
	 * 首字母大写
	 */
	String.prototype.firstUpperCase = function () {
		return this.toString()[0].toUpperCase() + this.toString().slice(1);
	}


//
//        function Format(datetime, fmt) {
//        		if (!datetime) {
//        			return datetime;
//        		}
//            if (parseInt(datetime) == datetime) {
//                if (datetime.length == 10) {
//                    datetime = parseInt(datetime) * 1000;
//                } else if (datetime.length == 14) {
//                    datetime = parseInt(datetime);
//                }
//            }
//            datetime = new Date(datetime);
//            var o = {
//                "M+": datetime.getMonth() + 1,                 //月份   
//                "d+": datetime.getDate(),                    //日   
//                "h+": datetime.getHours(),                   //小时   
//                "m+": datetime.getMinutes(),                 //分   
//                "s+": datetime.getSeconds(),                 //秒   
//                "q+": Math.floor((datetime.getMonth() + 3) / 3), //季度   
//                "S": datetime.getMilliseconds()             //毫秒   
//            };
//            if (/(y+)/.test(fmt))
//                fmt = fmt.replace(RegExp.$1, (datetime.getFullYear() + "").substr(4 - RegExp.$1.length));
//            for (var k in o)
//                if (new RegExp("(" + k + ")").test(fmt))
//                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
//            return fmt;
//        }
function dateFormat(myStr, fmt){
	
	return myStr.replace(
	    /^(\d{4})(\d\d)(\d\d)(\d\d)(\d\d)(\d\d)$/,
	    '$1-$2-$3 $4:$5'
	);
	

	//return Date.parseExact(date, "YYYYMMDDHHMMSS");
	/*	if (date == undefined || date == null) {
            return "";
        }
		
		if (date.isBlank()) {
            return "";
        }

        if (date.length == 6) {
        	var yyyy = date.left(4);
            var mm = date.right(2);
            return yyyy + "/" + mm;
        }

        if (date.length == 8) {
        	var yyyy = date.left(4);
            var mm = date.mid(4, 2);
            var dd = date.right(2);

            return yyyy + "/" + mm + "/" + dd;
        }

        if (date.length == 14) {

        	var yyyy = date.left(4);
            var mm = date.mid(4, 2);
            var dd = date.mid(6, 2);
            var h =  date.mid(8, 2);
            var m =  date.mid(10, 2);
            var s =  date.mid(12, 2);

            return yyyy + "-" + mm + "-" + dd + " " + h + ":" + m + ":" + s;
        }

        return date;*/
	}

function dateDisplay(myStr){
	return dateFormat(myStr, "yyyy-mm-dd hh:mi");
}

function frDate2time(str) {
	if (!str || str.isBlank()) {
		return str;
	}
	return str.replaceAll("-", "") + "000000";
}

function toDate2time(str) {
	if (!str || str.isBlank()) {
		return str;
	}
	return str.replaceAll("-", "") + "235959";
}

/**
 * 获取URL参数
 * @param name 参数名
 * @returns 参数值
 */
function GetQueryString(name){
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

/**
 * 获得字符串实际长度，中文2，英文1
 */
var GetLength = function (str) {
    ///<param name="str">要获得长度的字符串</param>
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) realLength += 1;
        else realLength += 2;
    }
    return realLength;
};

/** 
 * js截取字符串，中英文
 * @param str：需要截取的字符串 
 * @param len: 需要截取的长度 
 */
function cutstr(str, len) {
    var str_length = 0;
    var str_len = 0;
    str_cut = new String();
    str_len = str.length;
    for (var i = 0; i < str_len; i++) {
        a = str.charAt(i);
        str_length++;
        if (escape(a).length > 4) {
            // 中文字符的长度经编码之后大于4  
            str_length++;
        }
        str_cut = str_cut.concat(a);
        if (str_length >= len) {
            str_cut = str_cut.concat("...");
            return str_cut;
        }
    }
    // 如果给定字符串小于指定长度，则返回源字符串；  
    if (str_length < len) {
        return str;
    }
}

//只修改指定ul下的li列表
function cyUlAvtivty(obj) {
	//改变所有li样式，寻找id为jquery_genr的ul元素的所有li元素
    $(obj).parent().parent().children().children().removeClass("caoy_ul_activity");
    //改变当前li样式
    $(obj).addClass("caoy_ul_activity");
}

function errorFocus() {
	$(".form-group.has-feedback.has-error .form-control").get(0).focus();
}
