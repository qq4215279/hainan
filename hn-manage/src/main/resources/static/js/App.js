var App = {
    ctxPath: "",
    setCtxPath: function (ctx) {
        if (this.ctxPath == "") {
            this.ctxPath = ctx;
        }
    },
    confirm: function (tip, ensure) {//询问框
        parent.layer.confirm(tip, {
            btn: ['确定', '取消']
        }, function (index) {
            ensure();
            parent.layer.close(index);
        }, function (index) {
            parent.layer.close(index);
        });
    },
    confirmAudit: function (tip, ensure, unsure, btn1, btn2) {//审核确认框
        if (!btn1) {
            btn1 = "审核通过";
        }
        if (!btn2) {
            btn2 = "审核不通过";
        }
        layer.confirm(tip, {
            btn: [btn1, btn2, '关闭'],
            icon: 1,
            title: "审核"
        }, function (index) {
            ensure();
            layer.close(index);
        }, function (index) {
            unsure();
            layer.close(index);
        });
        if ($(".layui-layer-btn1") && $(".layui-layer-btn1").length > 0 && $(".layui-layer-btn1")[0].style) {
            $(".layui-layer-btn1")[0].style.color = "#FFFFFF";
            $(".layui-layer-btn1")[0].style.backgroundColor = "#ed5565";
            $(".layui-layer-btn1")[0].style.borderColor = "#ed5565";
        }
    },
    log: function (info) {
        console.log(info);
    },
    alert: function (info, iconIndex, callBack) {
        if (!callBack) {
            callBack = function () {

            }
        }
        parent.layer.msg(info, {
            icon: iconIndex
        }, callBack);
    },
    info: function (info, callBack) {
        this.alert(info, 0, callBack);
    },
    success: function (info, callBack) {
        this.alert(info, 1, callBack);
    },
    error: function (info, callBack) {
        this.alert(info, 2, callBack);
    },

    showLoading: function () {
        return layer.load(1, {
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
    },
    closeLoading: function () {
        layer.closeAll('loading'); //关闭加载层
    }
    ,
    closeSelfIframe: function () {
        //当你在iframe页面关闭自身时
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    }
    ,
    infoDetail: function (title, info, infw, infh) {
        var display = "";
        var width = "950";
        var height = "600";
        if (typeof info == "string") {
            display = info;
        } else {
            if (info instanceof Array) {
                for (var x in info) {
                    display = display + info[x] + "";
                }
            } else {
                display = info;
            }
        }
        if (typeof infw == "number") {
            width = infw + "";
        }
        if (typeof infh == "number") {
            height = infh + "";
        }
        parent.layer.open({
            title: title,
            type: 1,
            skin: 'layui-layer-rim', //加上边框
            area: [width + 'px', height + 'px'], //宽高
            content: '<div style="padding: 20px;">' + display + '</div>'
        });
    },
    writeObj: function (obj) {
        var description = "";
        for (var i in obj) {
            var property = obj[i];
            description += i + " = " + property + ",";
        }
        layer.alert(description, {
            skin: 'layui-layer-molv',
            closeBtn: 0
        });
    },
    showInputTree: function (inputId, inputTreeContentId, leftOffset, rightOffset) {
        var onBodyDown = function (event) {
            if (!(event.target.id == "menuBtn" || event.target.id == inputTreeContentId || $(event.target).parents("#" + inputTreeContentId).length > 0)) {
                $("#" + inputTreeContentId).fadeOut("fast");
                $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
            }
        };

        if (leftOffset == undefined && rightOffset == undefined) {
            var inputDiv = $("#" + inputId);
            var inputDivOffset = $("#" + inputId).offset();
            $("#" + inputTreeContentId).css({
                left: inputDivOffset.left + "px",
                top: inputDivOffset.top + inputDiv.outerHeight() + "px"
            }).slideDown("fast");
        } else {
            $("#" + inputTreeContentId).css({
                left: leftOffset + "px",
                top: rightOffset + "px"
            }).slideDown("fast");
        }

        $("body").bind("mousedown", onBodyDown);
    },
    baseAjax: function (url, tip) {
        var ajax = new $ax(App.ctxPath + url, function (data) {
            App.success(tip + "成功!");
        }, function (data) {
            App.error(tip + "失败!" + data.responseJSON.message + "!");
        });
        return ajax;
    },
    changeAjax: function (url) {
        return App.baseAjax(url, "修改");
    },
    zTreeCheckedNodes: function (zTreeId) {
        var zTree = $.fn.zTree.getZTreeObj(zTreeId);
        if (!zTree) {
            return "";
        }
        var nodes = zTree.getCheckedNodes();
        var ids = "";
        for (var i = 0, l = nodes.length; i < l; i++) {
            ids += "," + nodes[i].id;
        }
        return ids.substring(1);
    },
    eventParseObject: function (event) {//获取点击事件的源对象
        event = event ? event : window.event;
        var obj = event.srcElement ? event.srcElement : event.target;
        return $(obj);
    },
    sessionTimeoutRegistry: function () {
        $.ajaxSetup({
            contentType: "application/x-www-form-urlencoded;charset=utf-8",
            complete: function (XMLHttpRequest, textStatus) {
                //通过XMLHttpRequest取得响应头，sessionstatus，
                var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
                if (sessionstatus == "timeout") {
                    //如果超时就处理 ，指定要跳转的页面
                    window.location = App.ctxPath + "/global/sessionError";
                }
            }
        });
    },
    initValidator: function (formId, fields) {
        $('#' + formId).bootstrapValidator({
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: fields,
            live: 'enabled',
            message: '该字段不能为空'
        });
    }, //获取url参数
    getQueryString: function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");

        var r = window.location.search.substr(1).match(reg);

        if (r != null) return unescape(r[2]);
        return null;
    },
    /**
     * 带有loading效果的ajax方法
     * @param obj ajax参数
     */
    loadingAjax: function (obj) {
        var index = layer.load(1, {
            shade: [0.1, '#fff'] //0.1透明度的白色背景
        });
        obj.complete = function () {
            layer.close(index);
        }

        var oSuccess = obj.success;

        function success(result) {
            if (result.code != 200) {
                App.error(result.message);
            } else {
                oSuccess(result);
            }
        }

        obj.success = success;
        obj.error = error;

        function error() {
            App.error("服务器异常，请稍再试")
        }

        $.ajax(obj);
    },//打开窗口
    openIframe: function (obj) {
        var index = layer.open({
            title: obj.title,
            area: obj.area, //宽高
            content: obj.content,
            type: 2,
            fix: false, //不固定
            maxmin: true,
            scrollbar: false
        });
        return index;
    },
    //初始化图片相册 使用插件1
    initAlbum:function(pattern){
        var link = document.createElement('link');
        link.type = 'text/css';
        link.rel = 'stylesheet';
        link.href = this.ctxPath + '/static/plugin/jquerygalpop/css/jquery.galpop.css';
        var ctx=this.ctxPath;
        document.getElementsByTagName("head")[0].appendChild(link);
        $.getScript(ctx + '/static/plugin/jquerygalpop/js/jquery.galpop.js',function(){
            if(!pattern){
                pattern='a';
            }
            $(pattern).each(function(){
                var images = $(this).find('img');

                if(images&&images.length>0){

                    $(this).removeClass('galpop-info').addClass('galpop-info')
                    $(this).attr('data-galpop-group','info');
                }

            });
            $('.galpop-info').galpop();
        });

    },
    initAlbumNew:function(){
        var link = document.createElement('link');
        link.type = 'text/css';
        link.rel = 'stylesheet';
        link.href = this.ctxPath + '/static/plugin/magnify-master/dist/jquery.magnify.css';
        var ctx=this.ctxPath;
        document.getElementsByTagName("head")[0].appendChild(link);
        $.getScript(ctx+'/static/plugin/magnify-master/dist/jquery.magnify.js',function(){

            $('a').each(function(){
                var img=$(this).find('img');
                if(img){

                    var src = $(this).attr('src');
                    var title = $(this).attr('title');
                    $(this).attr('data-magnify','gallery')
                    if(title){
                        $(this).attr('data-caption',title);
                    }

                }
            });
            $('[data-magnify]').magnify({
                headToolbar: [
                    'minimize',
                    'maximize',
                    'close'
                ],
                footToolbar: [
                    'prev',
                    'next',
                    'zoomIn',
                    'zoomOut',
                    'fullscreen',
                    'actualSize',
                    'rotateLeft',
                    'rotateRight'
                ],
                modalWidth: 400,
                modalHeight: 400
            });

        });

    },
    print:function ( parent_className,fileName) {
        var ctx=this.ctxPath;
        var itemid=new Date().getTime();
        $('.'+parent_className).attr('id',itemid);
        location.href="#"+itemid;
        $.getScript(ctx+"/static/plugin/jquery-print-pdf/js/html2Canvas.js",function(){
            $.getScript(ctx+"/static/plugin/jquery-print-pdf/js/jsPdf.js",function(){
                if(!parent_className)alert("请传入要打印部分的父节点类名") ;
                var target = document.getElementsByClassName(parent_className)[0];
                target.style.background = "#FFFFFF";
                html2canvas(target, {
                    onrendered:function(canvas) {
                        var contentWidth = canvas.width;
                        var contentHeight = canvas.height;
                        //一页pdf显示html页面生成的canvas高度;
                        var pageHeight = contentWidth / 592.28 * 841.89;
                        //未生成pdf的html页面高度
                        var leftHeight = contentHeight;
                        //页面偏移

                        var position = 0;
                        //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
                        var imgWidth = 595.28;
                        var imgHeight = 592.28/contentWidth * contentHeight;
                        var pageData = canvas.toDataURL('image/jpeg', 1.0);
                        var pdf = new jsPDF('', 'pt', 'a4');
                        //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
                        //当内容未超过pdf一页显示的范围，无需分页
                        if (leftHeight < pageHeight) { pdf.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight ); }
                        else { while(leftHeight > 0) { pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
                            leftHeight -= pageHeight;
                            position -= 841.89;
                            //避免添加空白页
                            if(leftHeight > 0) { pdf.addPage(); } } }
                        if(!fileName){
                            fileName='表格';
                        }
                        pdf.save(fileName+".pdf"); } })
            });
        });



    }
}
