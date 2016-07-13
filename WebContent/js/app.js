//模块加载
var app = angular.module('mainApp', ['ui.router','ui.bootstrap','ngAnimate','ngCookies']);

// 常量设置
app.constant('SYS_PARAM', {
  webKey:'websiteKey123',
  baseURL:'http://localhost:8080/gametime_1.0.0/'
});
app.constant('ROUTER_EVENTS', {
  welecomeViewed: 'welecome-viewed'
});
app.constant('AUTH_EVENTS', {
  loginSuccess: 'auth-login-success',
  loginFailed: 'auth-login-failed',
  logoutSuccess: 'auth-logout-success',
  logoutFailed: 'auth-logout-failed',
  registerSuccess: 'auth-register-success',
  registerFailed: 'auth-register-failed',
  sessionTimeout: 'auth-session-timeout',
  notAuthenticated: 'auth-not-authenticated',
  notAuthorized: 'auth-not-authorized',
  userUpdated:'auth-user-updated',
  loginFirst:'log-in-first'
});
app.constant('USER_ROLES', {
  all: '*',
  admin: 'admin',
  editor: 'editor',
  guest: 'guest',
  member: 'member'
});
app.constant('USER_STATUS', {
  logged: true,
  unlogged: false
});
//run函数
app.run(function($rootScope, $state, AUTH_EVENTS, ROUTER_EVENTS) {

  //浏览器类型判断
  var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
  // alert(userAgent);

  //login图片预加载
  var preLoadImage = ['./source/image/bc-login.png','./source/image/4.png'];

  for(var i=0; i<preLoadImage.length; i++){
    var img = new Image(); 
    img.src = preLoadImage[i]; 
  }
  // var img = new Image(); 
  // img.src = "./source/image/bc-login.png"; 
  // var img2 = new Image(); 
  // img2.src = "./source/image/4.png"; 

  //监听全局事件
  $rootScope.$on(AUTH_EVENTS.loginSuccess, function(evt, args) {
    $state.go(args.pre,{productID:args.productID,type:args.type}); 
  });
  $rootScope.$on(AUTH_EVENTS.logoutSuccess, function(evt, next, current) {
    $state.go('login'); 
  });
  $rootScope.$on(AUTH_EVENTS.registerSuccess, function(evt, next, current) {
    $state.go('account'); 
  });
  //欢迎页近期登陆事件
  $rootScope.$on(ROUTER_EVENTS.welecomeViewed, function(evt, next, current) {
    $state.go('home'); 
  });

  $rootScope.$on('$stateChangeStart', function(event, toState, toParams, fromState, fromParams) {
    $rootScope.isLoading = true;
    if(toState.name == 'login'&&toParams.pre == '')
      toParams.pre = fromState.name;
    // if(toState.name == 'login'&&fromState.name == ''){
    //   alert(1);
    //   toParams.pre = 'home';
    // }
    if(toState.name == 'login'&&fromState.name == 'product-detail')      
      toParams.productID = fromParams.productID;
    if(toState.name == 'login'&&fromState.name == 'item-confirm')     
      toParams.productID = fromParams.productID;
    if(toState.name == 'login'&&fromState.name == 'add-item') {   
      toParams.productID = fromParams.productID;
      toParams.type = fromParams.type;
    }
  });
  $rootScope.$on('$stateChangeSuccess', function(evt, next, current) {
     $rootScope.isLoading = false;
     document.body.scrollTop = document.documentElement.scrollTop = 0;
  });
  
});

// http默认header配置并解析
app.config(function($httpProvider) {
    $httpProvider.defaults.headers.put['Content-Type'] = 'application/x-www-form-urlencoded';
    $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
 
    // Override $http service's default transformRequest
    $httpProvider.defaults.transformRequest = [function(data) {
        var param = function(obj) {
            var query = '';
            var name, value, fullSubName, subName, subValue, innerObj, i;
 
            for (name in obj) {
                value = obj[name];
 
                if (value instanceof Array) {
                    for (i = 0; i < value.length; ++i) {
                        subValue = value[i];
                        fullSubName = name + '[' + i + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += param(innerObj) + '&';
                    }
                } else if (value instanceof Object) {
                    for (subName in value) {
                        subValue = value[subName];
                        fullSubName = name + '[' + subName + ']';
                        innerObj = {};
                        innerObj[fullSubName] = subValue;
                        query += param(innerObj) + '&';
                    }
                } else if (value !== undefined && value !== null) {
                    query += encodeURIComponent(name) + '='
                            + encodeURIComponent(value) + '&';
                }
            }
 
            return query.length ? query.substr(0, query.length - 1) : query;
        };
 
        return angular.isObject(data) && String(data) !== '[object File]'
                ? param(data)
                : data;
    }];
});
app.config(function($urlRouterProvider){
    $urlRouterProvider.otherwise('/home');
})
//注入器
app.config(["$httpProvider", function ($httpProvider) {
    $httpProvider.interceptors.push('loadingMarker');
}]);

app.config(['$httpProvider', function($httpProvider) {
    $httpProvider.interceptors.push('errorMention');
}]);