function navtiveCallback(type, responseObj) {
    if (type == 1) {//刷新界面
        // doRefresh
        alert("这是刷新");
    } else if (type == 2) {//分享

        if (responseObj == '1') {
            alert('分享成功');
        }
        // $(".loading").hide();
    } else if (type == 3) {//设置webView标题
        // setTitle
        alert("设置标题撒");
    } else if (type == 4) {//是不是会员
        // isMember
        alert("是不是会员");
    } else if (type == 5) {//弹窗
        // showMsg
        alert("弹框喽");
    } else if (type == 6) {//是否登录
        if(responseObj){
            app.isLoginCallback();
        }
    } else if (type == 7) {//token
        app.token = responseObj;
        app.getTokenCallback(responseObj);
    }
}


var app = {
    token: null,
    getAgent: function () {
        return navigator.userAgent;
    },
    isAndroid: function () {
        var u = this.getAgent();
        return u.indexOf('Android') > -1 || u.indexOf('Adr') > -1; //android终端
    },
    isIos: function () {
        return !!this.getAgent().match(/\(i[^;]+;( U;)? CPU.+Mac OS X/); //ios终端
    },
    isLogin() {
        if (this.isAndroid()) {
            if (window.hngh && window.hngh.isLogin(false)) {
                navtiveCallback(6);
                return true;
            }
            return false;

        }
        if (this.isIos()) {
            if (window.webkit && window.webkit.messageHandlers && window.webkit.messageHandlers.isLogin) {
                window.webkit.messageHandlers.isLogin.postMessage(false);
            }

        }
        return false;
    },
    getToken: function (getTokenBackFunc) {
        if (getTokenBackFunc) {
            this.getTokenCallback = getTokenBackFunc;
        }
        if (this.token) {
            return this.token;
        }
        if (this.isAndroid()) {
            if (window.hngh) {
                var token = window.hngh.getLoginToken();
                if (token) {
                    this.token = token;
                    if (this.getTokenCallback) {
                        this.getTokenCallback(token);
                    }
                }
            }
        }
        if (this.isIos()) {
            if (window.webkit && window.webkit.messageHandlers && window.webkit.messageHandlers.getLoginToken) {
                window.webkit.messageHandlers.getLoginToken.postMessage(null);
            }
        }
    },
    isLoginCallback:null,
    getTokenCallback: null,
    setTokenCallback: function (func) {
        this.getTokenCallback = func;
    }
};