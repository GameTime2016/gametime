//欢迎页控制器
function WelcomeCtrl($scope,$rootScope,$cookieStore,ROUTER_EVENTS) {
	$scope.step1 = false;
	$scope.delay = false;
	$scope.isImageLoaded = false;

	var height = angular.element(document.getElementById('btn-height')).prop('offsetWidth');
	$scope.size = height*0.5 + 'px';
	$scope.border = height*0.08 + 'px';

	$scope.wrapper_height = document.body.clientWidth *0.3 +'px';

	$scope.start = function() {
		if($scope.isImageLoaded){
			$scope.color='ani-black';
			$scope.step1=true;
			$scope.delay='delay';
			$scope.index='lower';
		}
	}
	// 欢迎页面cookie
	if($cookieStore.get("welcome_page_viewed_recently") == 'true'){
		$rootScope.$broadcast(ROUTER_EVENTS.welecomeViewed);
	}
	else{
		var expireDate = new Date();
  		expireDate.setDate(expireDate.getDate() + 1);
		$cookieStore.put("welcome_page_viewed_recently",'true',{'expires': expireDate});
	}
}
WelcomeCtrl.$inject = ['$scope','$rootScope','$cookieStore','ROUTER_EVENTS'];
// 首页控制器
function HomeCtrl($scope, ProductService, hotGameList) {
	$scope.selectByPlatform = function(ID) {
		ProductService.setUniquePlatform(ID,true);
	}
	$scope.hotGameList = hotGameList.slice(0,6);
}
HomeCtrl.$inject = ['$scope', 'ProductService', 'hotGameList'];

// 产品浏览控制器
function ProductsCtrl($scope, ProductService, productsList, typeList, platformList) {
	//$scope.productsList = productsList;
	$scope.sortByPlatform = platformList;
	$scope.sortByType = typeList;
	$scope.noPerPage = 12;
	$scope.totalItems = productsList.length;
	$scope.currentPage = 1;
	$scope.isNoItems = false;
	$scope.isAllType = false;
	$scope.isAllPlatform = false;

	if(ProductService.selectedType == -1)
		$scope.isAllType = true;

	if(ProductService.selectedPlatform == -1)
		$scope.isAllPlatform = true;

	if(productsList.length == 0){
		$scope.isNoItems = true;
	}

	$scope.selectByType = function (ID) {
		ProductService.setUniqueType(ID,false);
	}

	$scope.selectByPlatform = function (ID) {
		ProductService.setUniquePlatform(ID,false);	
	}

	$scope.range = function(currentPage) {
		var min = (currentPage-1)*$scope.noPerPage;
		var max = currentPage*$scope.noPerPage-1;
		var input = [];

		if(max >= $scope.totalItems-1)
			max = $scope.totalItems-1;
	    for (var i = min; i <= max; i ++) {
	        input.push(productsList[i]);
	    }
    	return input;
	};
}
ProductsCtrl.$inject = ['$scope','ProductService','productsList','typeList','platformList'];

// 登录控制器
function LoginCtrl($scope, $rootScope, $stateParams, AUTH_EVENTS, AuthService, AccountService, requireIdentifyingCode) {
	$scope.credentials = {
	    username: '',
	    password: ''
	};
	$scope.invalid_info = false;
	$scope.requireCode = false;
	$scope.notify = $stateParams.notify;
	
	if(requireIdentifyingCode != false) {
		$scope.requireCode = true;
		$scope.codeUrl = requireIdentifyingCode;	
	}

	$scope.LogIn = function(credentials) {		
		//检验内容
		if(!$scope.form_login.$dirty){
			$scope.form_login.account.$dirty = true;
			$scope.form_login.password.$dirty =true;
		}
		else{
			AccountService.login($scope.credentials).then(function(res){
				//登录成功
				if(res.isSuccess == true){
					$rootScope.$broadcast(AUTH_EVENTS.loginSuccess, 
						{
						pre: $stateParams.pre,
						productID: $stateParams.productID
					});
				}
				//登录失败
				else{
					$scope.invalid_info = true;
					$scope.error_conntent = res.errorDescr;
					$rootScope.$broadcast(AUTH_EVENTS.loginFailed);
					if(res.codeUrl != ''){
						$scope.requireCode = true;
						$scope.codeUrl = res.codeUrl;
					}
				}
			},function(res){		
			});
		}			
	}
	$scope.changeCode = function() {
		AuthService.getIdentifyingCode().then(function(res){
			if(res != false)
				$scope.codeUrl = res;
		});
	}
}
LoginCtrl.$inject = ['$scope', '$rootScope','$stateParams','AUTH_EVENTS','AuthService','AccountService','requireIdentifyingCode'];

// 注册控制器
function RegisterCtrl($scope, $uibModal, $rootScope, AuthService ,AccountService, AUTH_EVENTS) {
	$scope.credentials = {
	    input_account: '',
	    input_password: '',
	    input_phone: '',
	    input_email: ''
	  };
	$scope.isGettingSMS = false;
	$scope.isSMSerror = false;

	//协议模态框
	$scope.openAgreement = function (size) {
	    var modalInstance = $uibModal.open({
	      templateUrl: './pages/register_agreement.html',
	      controller: 'ModalAgreementCtrl'
	    });
	}

	//倒计时方程
	var setCountdown = function(second) {
		$scope.isGettingSMS = true;
		$scope.count_donw = second;	
		var SMS_countdown = setInterval(function() {   
								$scope.count_donw--;                        	                                
                                if($scope.count_donw == 0){
                                	$scope.isGettingSMS = false;
                                	clearInterval(SMS_countdown);
                                	$scope.count_donw = second;
                                }                             
                                $scope.$digest();
                            }, 1000);
	}
	// 获取sms
	$scope.getSMS = function(){	
		AuthService.getSMS($scope.credentials).then(function(res){
			if(res.isSuccess == true) {
				$scope.isSMSerror = false;
				setCountdown(60);
			}
			else {
				$scope.isSMSerror = true;
				$scope.SMS_error_descr = res.errorDescr;
			}
		});
	}
	// 注册
	$scope.register = function() {
		var exist_account = $scope.credentials.input_account;
		var exist_phone = $scope.credentials.input_phone;
		var exist_email = $scope.credentials.input_email;
		AccountService.register($scope.credentials).then(
			function(res){				
				switch(res.resultCod){
					case 100://注册成功  
						$rootScope.$broadcast(AUTH_EVENTS.registerSuccess);
						break;
					case 1://账号重复
						$scope.exist_account = exist_account;
						break;
					case 2://手机重复
						$scope.exist_phone = exist_phone;
						break;
					case 3://邮箱重复
						$scope.exist_email = exist_email;
						break;
					case 4://验证码错误
						$scope.isSMSerror = true;
						$scope.SMS_error_descr = '验证码错误'
						break;
					default:
						break;
				};
			},function(res){				
		});
			
	}
}
RegisterCtrl.$inject = ['$scope','$uibModal','$rootScope','AuthService','AccountService','AUTH_EVENTS'];

// 产品详细信息控制器
function ProductDetailCtrl($scope, $stateParams, productDetail, AccountService, similiarProducts) {
	console.log("productDetail",productDetail);
	$scope.isPlay = false;
	$scope.isMute = false;
	$scope.isCollected = false;
	$scope.isPopover = false;
	$scope.resultDescr = '1';
	$scope.isEnlarged = false;
	$scope.game_code = productDetail.List[0].Code;
	$scope.game_name = productDetail.Name;
	$scope.game_picture = productDetail.Picture;
	$scope.game_desc = productDetail.Desc;
	$scope.game_rate = productDetail.List[0].Rate;
	$scope.game_poster = productDetail.Poster[0];
	$scope.game_issue = productDetail.Issue;
	$scope.game_company = productDetail.Company;
	$scope.game_category = productDetail.Category;
	$scope.game_player = productDetail.Players;
	$scope.game_platform = productDetail.List[0].Platform;
	$scope.game_language = productDetail.List[0].Language;
	$scope.game_videooutput = productDetail.List[0].Video;
	$scope.game_vibrate = productDetail.List[0].Vibrate;
	$scope.game_remote = productDetail.List[0].Remote;
	$scope.game_storage = productDetail.List[0].Storage;
	$scope.game_video = '';
	$scope.similiarGames = similiarProducts;
	var currentPicIndex = 0;
	var isVideoLoaded = false;

	var height1 = angular.element(document.getElementById('span1')).prop('offsetWidth');
	$scope.span1_fontsize = height1*0.09*0.8 + 'px';
	var height2 = angular.element(document.getElementById('span2')).prop('offsetWidth');
	$scope.span2_fontsize = height2*0.36*0.92 + 'px';

	$scope.srcs = [ {group:[{index:0,href:"./source/image/fade1.jpg"},
					{index:1,href:"./source/image/fade2.jpg"},
					{index:2,href:"./source/image/fade3.jpg"},
					{index:3,href:"./source/image/fade4.jpg"}]},
					{group:[{index:4,href:"./source/image/fade4.jpg"},
					{index:5,href:"./source/image/fade3.jpg"},
					{index:6,href:"./source/image/fade2.jpg"},
					{index:7,href:"./source/image/fade1.jpg"}]}];
	var hrefs = [ './source/image/fade1.jpg',
					'./source/image/fade2.jpg',
					'./source/image/fade3.jpg',
					'./source/image/fade4.jpg',
					'./source/image/fade4.jpg',
					'./source/image/fade3.jpg',
					'./source/image/fade2.jpg',
					'./source/image/fade1.jpg'];
	var pic_number = hrefs.length;

	// pic viewer控制
	$scope.openViewer = function(index) {
		$scope.isEnlarged = true;
		currentPicIndex = index;
		$scope.enlarged_href = hrefs[currentPicIndex];	
	}
	$scope.closeViewer = function() {
		if($scope.isEnlarged==true)
			$scope.isEnlarged=false
	}
	$scope.getMyCtrlScope = function() {
         return $scope;   
    }
    $scope.next = function() {
    	currentPicIndex = (currentPicIndex + 1)%pic_number;
    	$scope.enlarged_href = hrefs[currentPicIndex];
    }
    $scope.previous = function() {
    	currentPicIndex = (currentPicIndex - 1 + pic_number)%pic_number;
    	$scope.enlarged_href = hrefs[currentPicIndex];    	
    }
    // video控制
	$scope.play = function(){
		if(isVideoLoaded == false) {
			$scope.game_video = './source/video/test.mp4';
			isVideoLoaded = true;
		}
		var elm = document.getElementById("game-video");
		if(elm.paused){
        	elm.play();
        	$scope.isPlay = true;
        }
       	else{
        	elm.pause();
        	$scope.isPlay = false;
        }
	}
	$scope.volume = function() {
		var elm = document.getElementById("game-video");
		
		if(elm.muted){
        	elm.muted = false;
        	$scope.isMute = false;
        }
       	else{
        	elm.muted = true;
        	$scope.isMute = true;
        }
	}
	// 收藏功能
	$scope.collect = function() {
		AccountService.addCollection($stateParams.productID).then(function(res){
			if(res.isSuccess == true)
				$scope.isCollected = true;	
			$scope.resultDescr = res.resultDescr;
			$scope.isPopover = true;
			var fadeOut = setTimeout(function(){
										$scope.isPopover = false;
										$scope.$digest();
										clearTimeout(fadeOut)}
									,2000);
		});
	}
}
ProductDetailCtrl.$inject = ['$scope','$stateParams','productDetail','AccountService','similiarProducts'];

//账户控制器
function AccountCtrl($scope,accountInfo,permission) {
	$scope.account = accountInfo;
}
AccountCtrl.$inject = ['$scope','accountInfo','permission'];
//地址控制器
function AddressCtrl($scope,permission) {
}
AddressCtrl.$inject = ['$scope','accountInfo','permission'];
//账单控制器
function BillCtrl($scope) {
	
}
BillCtrl.$inject = ['$scope'];

//订单控制器
function OrderCtrl($scope) {
	
}
OrderCtrl.$inject = ['$scope'];

//收藏控制器
function CollectionCtrl($scope, collections, AccountService) {
	$scope.noPerPage = 24;
	$scope.totalItems = collections.length;
	$scope.currentPage = 1;
	$scope.isNoItems = false;

	if(collections.length == 0){
		$scope.isNoItems = true;
	}
	
	$scope.range = function(currentPage) {
		var min = (currentPage-1)*$scope.noPerPage;
		var max = currentPage*$scope.noPerPage-1;
		var input = [];

		if(max >= $scope.totalItems-1)
			max = $scope.totalItems-1;
	    for (var i = min; i <= max; i ++) {
	        input.push(collections[i]);
	    }
    	return input;
	};
	$scope.deleteCollection = function(gameID) {
		AccountService.deleteCollection(gameID).then(function(res){
		});
	}
}
CollectionCtrl.$inject = ['$scope','collections','AccountService'];

//修改密码控制器
function ModifyPasswordCtrl($scope, AccountService) {
	$scope.modifyPassword = function(){
		AccountService.modifyPassword($scope.credentials).then();
	}
}
ModifyPasswordCtrl.$inject = ['$scope','AccountService'];

//修改手机控制器
function ModifyPhoneCtrl($scope, accountInfo) {
	$scope.binded_phone = accountInfo.user_phone;
	$scope.isStep1 = true;
	$scope.isStep2 = false;
	$scope.isStep3 = false;
}
ModifyPhoneCtrl.$inject = ['$scope','accountInfo'];

//修改邮箱控制器
function ModifyEmailCtrl($scope, accountInfo) {
	$scope.binded_phone = accountInfo.user_phone;
	$scope.old_email = accountInfo.user_email;
	$scope.isStep1 = true;
	$scope.isStep2 = false;
	$scope.isStep3 = false;
}
ModifyEmailCtrl.$inject = ['$scope','accountInfo'];

//租赁车控制器
function RentalCartCtrl($scope ) {
	$scope.check_all = false;
	$scope.items = [
		{selected:false,name:'NBA 2K16',href:'./source/image/product4.jpg',rental:'40/周',deposit:400},
		{selected:false,name:'刺客信条:兄弟会',href:'./source/image/product1.jpg',rental:'30/周',deposit:280},
		{selected:false,name:'刺客信条:兄弟会',href:'./source/image/product1.jpg',rental:'30/周',deposit:280}
	];

	reCaculate();

	$scope.check = function(index){
		$scope.items[index].selected = !$scope.items[index].selected;
		reCaculate();
	}
	$scope.checkAll = function(){
		if($scope.check_all == true)
			for(var i=0; i<$scope.items.length; i++){
				$scope.items[i].selected = true;	
			}
		else
			for(var i=0; i<$scope.items.length; i++){
				$scope.items[i].selected = false;	
			}
			
		reCaculate();
	}
	function reCaculate(){
		var number = 0;
		var deposit = 0;
		for(var i=0; i<$scope.items.length; i++){
			var item = $scope.items[i];
			if(item.selected == true){
				deposit += item.deposit;
				number ++;
			}
		}
		$scope.item_number = number;
		$scope.total_deposit = deposit;	
	}
	
	$scope.deleteItem = function(index){
		$scope.items.splice(index,1);
		reCaculate();
	}
}
RentalCartCtrl.$inject = ['$scope'];

//购物车控制器
function ShoppingCartCtrl($scope) {
	$scope.check_all = false;
	$scope.items = [
		{selected:false,isRental:false,mode:'购买',name:'NBA 2K16',href:'./source/image/product4.jpg',number:2,price:400},
		{selected:false,isRental:false,mode:'购买',name:'刺客信条:兄弟会',href:'./source/image/product1.jpg',number:1,price:280},
		{selected:false,isRental:true,mode:'租赁',name:'刺客信条:兄弟会',href:'./source/image/product1.jpg',number:1,price:280}
	];

	reCaculate();

	$scope.check = function(index){
		$scope.items[index].selected = !$scope.items[index].selected;
		reCaculate();
	}
	$scope.checkAll = function(){
		if($scope.check_all == true)
			for(var i=0; i<$scope.items.length; i++){
				$scope.items[i].selected = true;	
			}
		else
			for(var i=0; i<$scope.items.length; i++){
				$scope.items[i].selected = false;	
			}
			
		reCaculate();
	}
	$scope.numberChange = function(){
		reCaculate();
	}
	function reCaculate(){
		var number = 0;
		var price = 0;
		for(var i=0; i<$scope.items.length; i++){
			var item = $scope.items[i];
			if(item.selected == true){
				price += item.price*item.number;
				number += item.number;
			}
		}
		$scope.item_number = number;
		$scope.total_price = price;	
	}
	
	$scope.deleteItem = function(index){
		$scope.items.splice(index,1);
		reCaculate();
	}

}
ShoppingCartCtrl.$inject = ['$scope'];

//找回密码控制器
function RetrievePasswordCtrl($scope) {
	$scope.tab1 = true;
	$scope.tab2 = false;
}
RetrievePasswordCtrl.$inject = ['$scope'];

//绑定邮箱控制器
function BindEmailCtrl($scope,accountInfo) {
	$scope.email = accountInfo.user_email;
}
BindEmailCtrl.$inject = ['$scope','accountInfo'];

//社区控制器
function CommunityCtrl($scope,accountInfo) {

}
CommunityCtrl.$inject = ['$scope','accountInfo'];
//成就控制器
function TrophyCtrl($scope,accountInfo) {

}
TrophyCtrl.$inject = ['$scope','accountInfo'];

//活动控制器
function ActivityCtrl($scope) {
	$scope.activities = [
		{href:'./source/image/poster1.jpg',name:'"游戏时光"线下同城互动活动',date:'2016-4-20',place:'大连市青少年宫',number:'40'},
		{href:'./source/image/poster2.jpg',name:'"游戏时光"高校嘉年华',date:'2016-6-12',place:'上海市人民广场',number:'不限'},
		{href:'./source/image/poster3.jpg',name:'"游戏时光"FIFA16全国总决选',date:'2016-5-18',place:'北京雍和宫',number:'200'},
		{href:'./source/image/poster4.jpg',name:'"游戏时光"线下同城互动活动',date:'2017-1-1',place:'北京雍和宫',number:'200'},
		{href:'./source/image/poster5.jpg',name:'"游戏时光"线下同城互动活动',date:'2016-5-20',place:'北京雍和宫',number:'200'}
	];

}
ActivityCtrl.$inject = ['$scope'];

// 普通租赁确认页面
function ItemConfirmCtrl($scope, $uibModal, AuthService, productDetail, similiarProducts) {
	$scope.game_code = productDetail.List[0].Code;
	$scope.item_image_url = productDetail.Picture;
	$scope.item_name = productDetail.Name;
	$scope.item_company = productDetail.Company;
	$scope.item_category = productDetail.Category;
	$scope.item_player = productDetail.Players;
	$scope.item_platform = productDetail.List[0].Platform;
	$scope.item_language = productDetail.List[0].Language;
	$scope.similiarGames = similiarProducts;
	$scope.plat_ps = true;
	$scope.plat_xbox = false;
	$scope.rental_term = "来回邮寄所花费时间不计算于总租金之内";

	$scope.jumpto = function(toState) {
		var stateParams = {itemID: $scope.game_code};
		AuthService.jumpTo(toState,stateParams);
	}
	// //协议模态框
	// $scope.check = function () {
	//     var modalInstance = $uibModal.open({
	//       templateUrl: './pages/login.html',
	//     });
	// }
}
ItemConfirmCtrl.$inject = ['$scope','$uibModal','AuthService','productDetail','similiarProducts'];
//添加购物车控制器
function AddItemCtrl($scope) {

}
AddItemCtrl.$inject = ['$scope'];

// header控制器
function HeaderCtrl($scope, $uibModal, $rootScope, AccountService, AuthService, ProductService, AUTH_EVENTS, accountInfo, authInfo, typeList, platformList) {
	$scope.username = accountInfo.user_account;
	$scope.isLogged = authInfo.isLogged;
	$scope.menu_default = true;
	$scope.sortByPlatform = platformList;
	$scope.sortByType = typeList;
	$scope.isCollapsed = true;
	$rootScope.$on(AUTH_EVENTS.logoutSuccess, function(evt, next, current) {
    	$scope.isLogged = AuthService.getPermission(true).isLogged;
  	});

	//搜索游戏
	$scope.searchGame = function() {
		ProductService.searchName = $scope.input_gamename;
	}
	$scope.selectAll = function() {
		ProductService.setAll();	
	}
	$scope.selectByPlatform = function(ID) {
		ProductService.setUniquePlatform(ID,true);	
	}
	$scope.selectByType = function(ID) {
		ProductService.setUniqueType(ID,true);
	}

	$scope.login = function () {
	    var modalInstance = $uibModal.open({
	      templateUrl: './pages/modal_login.html',
	      controller: 'LoginModalCtrl',
	      resolve: {
        		redirect: function () {
          			return '#';
        		},
        		stateParams: function() {
        			return '';
        		}
      		}
	    });
	}
	//登出
	$scope.logout = function() {
		AccountService.logout().then(
			function(res){
				//登出成功
				if(res){
					$rootScope.$broadcast(AUTH_EVENTS.logoutSuccess);					 
				}
				//登出失败
				else{
					alert(res);
					console.log('原因',res);
					$rootScope.$broadcast(AUTH_EVENTS.logoutFailed);
				}
			},function(res){
				
			});
	}
	$scope.jumpto = function(toState) {
		AuthService.jumpTo(toState,'');
	}
}
HeaderCtrl.$inject = ['$scope','$uibModal','$rootScope','AccountService','AuthService','ProductService','AUTH_EVENTS','accountInfo','authInfo','typeList','platformList'];

//footer控制器
function FooterCtrl($scope) {

}
FooterCtrl.$inject = ['$scope'];
// 关于我们控制器
function aboutUsCtrl($scope) {

}
aboutUsCtrl.$inject = ['$scope'];
//合同模态框控制器
app.controller('ModalAgreementCtrl',['$scope', '$uibModalInstance', '$http', function ($scope, $uibModalInstance, $http) {

	$http({method:'GET',
		   url:'http://121.42.43.128/gametime/source/text/register_agreement.txt'}).
		then(function (res) {
        	$scope.agreement_content = res.data;  
        },function(res){

        });

	$scope.cancel = function () {
    	$uibModalInstance.dismiss('cancel');
  	};
  	
}]);
// 加载样式控制器
app.controller('LoadingCtrl',['$rootScope', function ($rootScope) {  	
}]);

//登录模态框控制器
app.controller('LoginModalCtrl', function ($scope, $state, $uibModalInstance, AuthService, AccountService, redirect, stateParams) {
	$scope.cancel = function () {
		$uibModalInstance.dismiss('cancel');
	};
	$scope.credentials = {
	    username: '',
	    password: ''
	};
	$scope.invalid_info = false;
	$scope.requireCode = false;

	AuthService.getIdentifyingCode().then(function(res){
		if(res != false) {
			$scope.requireCode = true;
			$scope.codeUrl = res;	
		}
	});
	
	$scope.LogIn = function(credentials) {		
		//检验内容
		if(!$scope.form_login.$dirty){
			$scope.form_login.account.$dirty = true;
			$scope.form_login.password.$dirty =true;
		}
		else{
			AccountService.login($scope.credentials).then(function(res){
				//登录成功
				if(res.isSuccess == true){
					if(redirect == '#')
						$state.reload();
					else
						$state.go(redirect,stateParams);

					$uibModalInstance.dismiss('cancel');
				}
				//登录失败
				else{
					$scope.invalid_info = true;
					$scope.error_conntent = res.errorDescr;
					if(res.codeUrl != ''){
						$scope.requireCode = true;
						$scope.codeUrl = res.codeUrl;
					}
				}
			},function(res){		
			});
		}			
	}
	$scope.changeCode = function() {
		AuthService.getIdentifyingCode().then(function(res){
			if(res != false)
				$scope.codeUrl = res;
		});
	}
	$scope.register = function() {
		$uibModalInstance.dismiss('cancel');	
	}
	$scope.retrievePassword = function() {
		$uibModalInstance.dismiss('cancel');	
	}
});