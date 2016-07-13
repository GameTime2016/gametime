// 账号信息服务
app.factory('AccountService', function ($q, $http, AuthService, SYS_PARAM) {
  return{
    account : {
        user_account:'',
        user_phone:'',
        user_email:'',
        user_name:'',
        isAccountLoaded: false
      },
    initAccount : function() {
      this.account.user_account = "";
      this.account.user_phone = "";
      this.account.user_email = "";
      this.account.user_name = "";
      this.account.isAccountLoaded = false;
    },
    setAccount : function(user_account, user_phone, user_email, user_name, isAccountLoaded) {
      this.account.user_account = user_account;
      this.account.user_phone = user_phone;
      this.account.user_email = user_email;
      this.account.user_name = user_name; 
      this.account.isAccountLoaded = true; 
    },
    destroyAccount : function() {
      this.initAccount(); 
    },
    resetAccount : function() {
      this.account.user_account = "";
      this.account.user_phone = "";
      this.account.user_email = "";
      this.account.user_name = ""; 
    },
    login : function (credentials) {
      var deferred = $q.defer();
      var parent = this;
      var req = {
                 method: 'POST',
                 url: SYS_PARAM.baseURL + 'GI_Login.aspx?',
                 data: {WebKey: SYS_PARAM.webKey, 
                        Account: credentials.username, 
                        Password: credentials.password,
                        ChkCode: credentials.identifying_code}
               };
      $http(req).then(function (res) {
                var result = {
                  isSuccess: false,
                  errorDescr:'',
                  codeUrl:''
                };
                if(res.data.resultCod == 100){
                  parent.setAccount(res.data.Account, res.data.Phone, res.data.Email, res.data.UserName);
                  AuthService.setLogged();
                  result.isSuccess = true;
                  deferred.resolve(result);
                }
                else if(res.data.resultCod == 50){
                  AuthService.getIdentifyingCode().then(function(res){
                    if(res != false){
                      result.isSuccess = false;
                      result.codeUrl = res;
                      result.errorDescr = "账号或密码错误";
                      
                    }
                    deferred.resolve(result);
                  });                  
                }
                else if(res.data.resultCod == 40){
                  AuthService.getIdentifyingCode().then(function(res){
                    if(res != false){
                      result.isSuccess = false;
                      result.codeUrl = res;
                      result.errorDescr = "验证码错误";
                      
                    }
                    deferred.resolve(result);
                  });    
                }
                else{
                  //账号或密码错误
                  result.isSuccess = false;
                  result.errorDescr = "账号或密码错误";
                  deferred.resolve(result);
                } 
              },function(res){
                  // 500
              });
      return deferred.promise;
    },
    httpAccountInfo : function () {
    var parent = this;
    var req = {
               method: 'POST',
               url: SYS_PARAM.baseURL + 'GI_GetLoginInf.aspx',
               data: {WebKey: SYS_PARAM.webKey}
             };
      return $http(req).then(function (res) {
                //用户
                if(res.data.resultCod == 100){
                  parent.setAccount(res.data.Account, res.data.Phone, res.data.Email, res.data.UserName);              
                }
                else{
                  //游客
                  parent.setAccount('', '', '', '');
                } 
                return parent.account;
              },function(res){//500
              });

    },
    getAccountInfo: function(useLoaded) {
      if(useLoaded&&this.account.isAccountLoaded)
        return this.account;
      else
        return this.httpAccountInfo();
    },
    logout : function () {
      var parent = this;
      var req = {
                 method: 'POST',
                 url: SYS_PARAM.baseURL + 'GI_LogOut.aspx',
                 data: {WebKey: SYS_PARAM.webKey}
               };
      return $http(req).then(function (res) {
                //成功
                if(res.data.resultCod == 100){
                  parent.resetAccount();
                  AuthService.setUnlogged();
                  return true;
                }
                //失败
                else{
                  parent.resetAccount();
                  AuthService.setUnlogged();
                  return false;
                } 
              },function(res){

              });
    },
    register : function (credentials) {
      var parent = this;
      var req = {
               method: 'POST',
               url: SYS_PARAM.baseURL + 'GI_RegisterUser.aspx',
               data: {
                  WebKey:SYS_PARAM.webKey,
                  Account: credentials.input_account, 
                  Password: credentials.input_password,
                  Phone: credentials.input_phone,
                  Email: credentials.input_email,
                  SMSCode: credentials.input_SMS
                }
             };
      return $http(req).then(function (res) {
                //注册成功
                if(res.data.resultCod == 100){
                  parent.setAccount(res.data.Account, res.data.Phone, res.data.Email, res.data.UserName);
                  AuthService.setLogged();
                }
                //注册失败
                else{
                } 
                return res.data;
              },function(res){

              });

    },
    modifyPassword : function(credentials){
      var parent = this;
      var req = {
               method: 'POST',
               url: SYS_PARAM.baseURL + 'GI_UpdUserPsw.aspx',
               data: {
                  WebKey:SYS_PARAM.webKey,
                  OldPassword: credentials.input_password, 
                  NewPassword: credentials.input_new_password
                }
             };
      return $http(req).then(function (res) {
                //修改成功
                if(res.data.resultCod == 100){
                  console.log('success');
                }
                //修改失败
                else{
                  console.log('fail');
                } 
                return res.data;
              },function(res){

              });
    },
    addCollection : function(ID) {
      var req = {
               method: 'POST',
               url: SYS_PARAM.baseURL + 'GI_AddCollectors.aspx',
               data: {
                  WebKey:SYS_PARAM.webKey,
                  Code: ID, 
                }
             };
      return $http(req).then(function (res) {
                var result = {
                  isSuccess: false,
                  resultDescr:''
                };
                if(res.data.resultCod == 100) {
                  result.isSuccess = true;
                  result.resultDescr = '收藏成功';
                }
                else if (res.data.resultCod == 10) {
                  result.isSuccess = true;
                  result.resultDescr = '游戏已收藏过';
                }
                else if (res.data.resultCod == 530) {
                  result.isSuccess = false;
                  result.resultDescr = '请先登录';
                }
                else {
                  result.isSuccess = false;
                  result.resultDescr = '未知错误';
                }
                return result;
              },function(res){

              });
    },
    deleteCollection : function(ID) {
      var req = {
               method: 'POST',
               url: SYS_PARAM.baseURL + 'GI_DelCollectors.aspx',
               data: {
                  WebKey:SYS_PARAM.webKey,
                  Code: ID, 
                }
             };
      return $http(req).then(function (res) {
                var result = {
                  isSuccess: false,
                  resultDescr:''
                };
                if(res.data.resultCod == 100) {
                  result.isSuccess = true;
                  result.resultDescr = '删除成功';
                }
                else {
                  result.isSuccess = false;
                  result.resultDescr = '删除失败';
                }
                return result;
              },function(res){

              });
    },
    getCollections : function() {
      var req = {
               method: 'POST',
               url: SYS_PARAM.baseURL + 'GI_GetCollectors.aspx',
               data: {
                  WebKey:SYS_PARAM.webKey
                }
             };
      return $http(req).then(function (res) {
                if(res.data.resultCod == 100) {
                  return res.data.Collectors;
                }
                else {
                  return [];
                }
              },function(res){

              });    
    }
 }
});

// 访问控制服务
app.factory('AuthService', function ($http, $q, $state, $uibModal, SYS_PARAM, USER_ROLES, USER_STATUS) {
  return {
    permissionModel: { permission: USER_ROLES.guest, isLogged: USER_STATUS.unlogged, isPermissionLoaded: false },
    getPermission : function(useLoaded){
      if(useLoaded&&this.permissionModel.isPermissionLoaded)
        return this.permissionModel;
      else
        return this.httpPermission();
    },
    httpPermission : function () {
      var parent = this;
      var req = {
                 method: 'POST',
                 url: SYS_PARAM.baseURL + 'GI_GetUserRight.aspx',
                 data: {WebKey: SYS_PARAM.webKey}
               };
      return $http(req).then(function (res) {
                //member
                if(res.data.resultCod == 100){
                  parent.setLogged();
                }
                //未登录
                else{
                  parent.setUnlogged();
                } 
                return parent.permissionModel;
              },function(res){//500
                parent.setUnlogged();
              });

    },
    setLogged : function(){
      this.permissionModel.permission = USER_ROLES.member;
      this.permissionModel.isLogged = USER_STATUS.logged;
      this.permissionModel.isPermissionLoaded = true;  
    },
    setUnlogged : function(){
      this.permissionModel.permission = USER_ROLES.guest;
      this.permissionModel.isLogged = USER_STATUS.unlogged;
      this.permissionModel.isPermissionLoaded = true;  
    },
    permissionCheck: function (authInfo, roleCollection) {
      var deferred = $q.defer();
      var parent = this;
      this.isAuthorized(authInfo,roleCollection, deferred);    
      return deferred.promise;
    },
    isAuthorized : function (authInfo, authorizedRoles, deferred) {
      if (!angular.isArray(authorizedRoles)) {
        authorizedRoles = [authorizedRoles];
      }
      var res = (authorizedRoles.indexOf(authInfo.permission) !== -1);
      if(res == true){
        deferred.resolve(true);
      }else{
        deferred.reject(true);
      }      
    },
    jumpTo : function(toState,stateParams){
          var parent = this;
          this.getPermission(false).then(function(res){
          parent.permissionCheck(res,[USER_ROLES.member]).then(
          function(res){ 
            $state.go(toState,stateParams)
          },
          function(res){ 
            var modalInstance = $uibModal.open({
                templateUrl: './pages/modal_login.html',
                controller: 'LoginModalCtrl',
                resolve: {
                    redirect: function () {
                      return toState;
                    },
                    stateParams: function() {
                      return stateParams;
                    }
                  }
              });
          }); 
        });
    },
    getIdentifyingCode : function() {
      var parent = this;
      var req = {
               method: 'POST',
               url: SYS_PARAM.baseURL + 'GI_GetLoginCode.aspx',
               data: {WebKey: SYS_PARAM.webKey}
             };
      return $http(req).then(function (res) {
                if(res.data.resultCod == 50){
                  return res.data.CodePicUrl;
                }
                else{
                  return false;
                } 
              },function(res){//500
      });
    },
    getSMS : function(credentials) {
      var parent = this;
      var req = {
               method: 'POST',
               url: SYS_PARAM.baseURL + 'GI_SendSMS.aspx',
               data: {WebKey: SYS_PARAM.webKey,
                      Account: credentials.input_account,
                      Phone: credentials.input_phone}
             };
      return $http(req).then(function (res) {
                var result = {
                  isSuccess: false,
                  errorDescr:''};

                if(res.data.resultCod == 100) {
                  result.isSuccess = true;              
                }
                else if(res.data.resultCod == 10) {
                  result.isSuccess = false;
                  result.errorDescr = '发送间隔太短'
                } 
                else if(res.data.resultCod == 20) {
                  result.isSuccess = false;
                  result.errorDescr = '超出发送次数'
                }
                else {
                  result.isSuccess = false;
                  result.errorDescr = '发送失败'
                }
                return result;
              },function(res){//500
      });
    }
  }
});
//产品浏览服务
app.factory('ProductService', function ($http, $q , SYS_PARAM){
  return {
    types: { typeList: [], isTypesLoaded: false},
    platforms: { platformList: [], isPlatformsLoaded: false},
    productsList: [],
    searchName: '',
    selectedPlatform:-1,
    selectedType:-1,
    selectedCategory:-1,
    getTypes: function(useLoaded) {
      if(useLoaded&&this.types.isTypesLoaded)
        return this.types.typeList;
      else
        return this.httpTypes();
    },
    httpTypes: function() {
      var parent = this;
      var req = {
                 method: 'POST',
                 url: SYS_PARAM.baseURL + 'GI_GetCategory.aspx',
                 data: {WebKey: SYS_PARAM.webKey}
               };
      return $http(req).then(function (res) {
                if(res.data.resultCod == 100){
                  parent.types.typeList = res.data.Category;
                  for(var i=0; i< parent.types.typeList.length; i++){
                    parent.types.typeList[i].isSelected = false; 
                  }
                  parent.types.isTypesLoaded = true;
                }
                return parent.types.typeList;
              },function(res){//500
              });
    },
    getPlatforms: function(useLoaded) {
      if(useLoaded&&this.platforms.isPlatformsLoaded){
        return this.platforms.platformList;}
      else
        return this.httpPlatforms();
    },
    httpPlatforms: function() {
      var parent = this;
      var req = {
                 method: 'POST',
                 url: SYS_PARAM.baseURL + 'GI_GetPlatform.aspx',
                 data: {WebKey: SYS_PARAM.webKey}
               };
      return $http(req).then(function (res) {
                if(res.data.resultCod == 100){
                  parent.platforms.platformList = res.data.Platform;
                  for(var i=0; i< parent.platforms.platformList.length; i++){
                    parent.platforms.platformList[i].isSelected = false; 
                  }
                  parent.platforms.isPlatformsLoaded = true;
                }
                return parent.platforms.platformList;
              },function(res){//500
              });
    },
    setAll: function() {
      this.searchName = '';
      this.selectedPlatform = -1;
      this.selectedCategory = -1;
      this.selectedType = -1;
      for(var i=0; i<this.types.typeList.length; i++) {
        this.types.typeList[i].isSelected = false;
      }
      for(var i=0; i<this.platforms.platformList.length; i++) {
        this.platforms.platformList[i].isSelected = false;
      }
    },
    setUniquePlatform: function(ID, setOthers){
      this.searchName = '';
      this.selectedPlatform = ID;
      for(var i=0; i<this.platforms.platformList.length; i++){
        if(this.platforms.platformList[i].ID == ID) {
          this.platforms.platformList[i].isSelected = true;
        }
        else{
          this.platforms.platformList[i].isSelected = false; 
        }
      }
      if(setOthers){
        this.selectedType = -1;
        for(var i=0; i<this.types.typeList.length; i++) {
          this.types.typeList[i].isSelected = false;
        }
      }
    },
    setUniqueType: function(ID, setOthers){
      this.searchName = '';
      this.selectedType = ID;
      for(var i=0; i<this.types.typeList.length; i++){
        if(this.types.typeList[i].ID == ID) {
          this.types.typeList[i].isSelected = true;
        }
        else {
          this.types.typeList[i].isSelected = false; 
        }
      }
      if(setOthers){
        this.selectedPlatform = -1;
        for(var i=0; i<this.platforms.platformList.length; i++) {
          this.platforms.platformList[i].isSelected = false;
        }
      }
    },
    // modifyTypes: function(ID){
    //   this.searchName = '';
    //   for(var i=0; i<this.types.typeList.length; i++) {
    //     if(this.types.typeList[i].ID == ID) {
    //       this.types.typeList[i].isSelected = !this.types.typeList[i].isSelected;
    //     }
    //   }
    // },
    // modifyPlatform: function(ID){
    //   this.searchName = '';
    //   for(var i=0; i<this.platforms.platformList.length; i++) {
    //     if(this.platforms.platformList[i].ID == ID) {
    //       this.platforms.platformList[i].isSelected = !this.platforms.platformList[i].isSelected;
    //     }
    //   }
    // },
    getProductList: function() {
      var name = this.searchName;
      var type = this.selectedType;
      var platform = this.selectedPlatform;

      if(name != '') {
        type = '';
        platform = '';
      }
      if(this.selectedType == -1) {
        type = '';
      }
      if(this.selectedPlatform == -1) {
        platform = '';
      }
      // if(this.searchName === ''){
      //   types+='[';
      //   for(var i=0; i<this.types.typeList.length; i++){
      //     if(this.types.typeList[i].isSelected == true){
      //       noOfSelected1 ++;
      //       types += this.types.typeList[i].ID+','
      //     }
      //   }
      //   types+=']';

      //   platforms+='[';
      //   for(var i=0; i<this.platforms.platformList.length; i++){
      //     if(this.platforms.platformList[i].isSelected == true){
      //       noOfSelected2 ++;
      //       platforms += this.platforms.platformList[i].ID+','
      //     }
      //   }
      //   platforms+=']'; 

      //   if(noOfSelected1 == 0 || noOfSelected2 == 0){
      //     return [];
      //   }  
      // }
      // else {
      //   name = this.searchName;
      //   types = '';
      // }

      var req = {
               method: 'POST',
               url: SYS_PARAM.baseURL + 'GI_GetProductsList.aspx',
               data: {WebKey: SYS_PARAM.webKey,
                      Name: name,
                      Category: type,
                      Platform: platform,
                      Hot: '',
                      Number: '',
                      Page: ''}
             };
      return $http(req).then(function (res) { 
        this.productsList = res.data.Lst;
        if(res.data.resultCod == 100) {                      
            return res.data.Lst;       
          } 
        else {      
            return [];
          }
      });
    },
    getHotProductList: function(num) {
      var number = num;
      var req = {
               method: 'POST',
               url: SYS_PARAM.baseURL + 'GI_GetProductsList.aspx',
               data: {WebKey: SYS_PARAM.webKey,
                      Name: '',
                      Category: '',
                      Platform: '',
                      Hot: 'H',
                      Number: number,
                      Page: 1}
             };
      return $http(req).then(function (res) { 
        this.productsList = res.data.Lst;
        if(res.data.resultCod == 100) {  
            return res.data.Lst;       
          } 
        else {      
            return [];
          }
      });
    },
    getSimiliarProductList: function(typeID,num) {
      var number = num;
      var type = typeID;
      var req = {
               method: 'POST',
               url: SYS_PARAM.baseURL + 'GI_GetProductsList.aspx',
               data: {WebKey: SYS_PARAM.webKey,
                      Name: '',
                      Category: type,
                      Platform: '',
                      Hot: '',
                      Number: number,
                      Page: 1}
             };
      return $http(req).then(function (res) { 
        this.productsList = res.data.Lst;
        if(res.data.resultCod == 100) {  
            return res.data.Lst;       
          } 
        else {      
            return [];
          }
      });
    },
    getProductDetail: function(ID){
      var deferred = $q.defer();
      var req = {
               method: 'POST',
               url: SYS_PARAM.baseURL + 'GI_GetProductDetail.aspx',
               data: {WebKey: SYS_PARAM.webKey,
                      Code: ID
                     }
             };  
      $http(req).then(function (res) { 
        console.log("detail",res.data);
        if(res.data.resultCod == 100) {                     
            deferred.resolve(res.data);     
          } 
        else {  
          deferred.reject("游戏不存在");
          }
      });
      return deferred.promise;
    }
  }
});
//loading
app.factory('loadingMarker', ["$rootScope", function ($rootScope) {
    var loadingMarker = {
        request: function (config) {
            // $rootScope.isLoading = true;
            // config.requestTimestamp = new Date().getTime();
            return config;
        },
        response: function (response) {
            // $rootScope.isLoading = false;
            // response.config.responseTimestamp = new Date().getTime();
            return response;
        }
    };
    return loadingMarker;
}]);

// 错误拦截
app.factory('errorMention', ['$q', '$injector', function($q, $injector) {
    var errorMention = {
        responseError: function(response) {
            // Session has expired
            console.log('error:',response.status);
            return response;
        }
    };
    return errorMention;
}]);