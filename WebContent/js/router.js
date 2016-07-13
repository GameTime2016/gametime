// 路由管理
app.config(function ($stateProvider, USER_ROLES) {
  $stateProvider
        .state('welcome', {
            url: '/',
            resolve: {
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(true);
                },
                authInfo: function(AuthService){
                  return AuthService.getPermission(true);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },
            views: {
              "header": {
                templateUrl: './pages/header_tidy.html',
                controller: HeaderCtrl               
              },
              "content": {
                templateUrl: './pages/welcome.html',
                controller: WelcomeCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('home', {
            url: '/home',
            resolve: {
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(true);
                },
                authInfo: function(AuthService){
                  return AuthService.getPermission(true);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                },
                hotGameList: function(ProductService){
                  return ProductService.getHotProductList(6);
                }              
            },
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl               
              },
              "content": {
                templateUrl: './pages/home.html',
                controller: HomeCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('login', {
            url: '/login?pre&productID',
            resolve: {
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(true);
                },
                authInfo: function(AuthService){
                  return AuthService.getPermission(true);
                },
                requireIdentifyingCode: function(AuthService){
                  return AuthService.getIdentifyingCode();
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },
            params: {pre: '',productID:'',notify:''},
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/login.html',
                controller: LoginCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('register', {
            url: '/register',
            resolve: {
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(true);
                },
                authInfo: function(AuthService){
                  return AuthService.getPermission(true);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl            
              },
              "content": {
                templateUrl: './pages/register.html',
                controller: RegisterCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('products', {
            url: '/products',
            resolve: {
                accountInfo: function(AccountService) {
                  return AccountService.getAccountInfo(true);
                },
                authInfo: function(AuthService) {
                  return AuthService.getPermission(true);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                },
                productsList: function(ProductService,platformList,typeList) {
                  return ProductService.getProductList();
                }
            },
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl               
              },
              "content": {
                templateUrl: './pages/products.html',
                controller: ProductsCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('product-detail', {
            url: '/product-detail/:productID',
            resolve: {
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(true);
                },
                authInfo: function(AuthService){
                  return AuthService.getPermission(true);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                },
                productDetail: function(ProductService,$stateParams,$state){
                  return ProductService.getProductDetail($stateParams.productID).catch(function(res){
                    //游戏不存在
                    $state.go('home',{},{'reload':true});
                  });
                },
                similiarProducts: function(ProductService){
                  return ProductService.getSimiliarProductList(1,6);
                }
            },
            views: {
              "header": {
                templateUrl: './pages/header_tidy.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/product_detail.html',
                controller: ProductDetailCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('account', {
            url: '/account',
            resolve: {                
                authInfo: function(AuthService){
                  return AuthService.getPermission(false);
                },
                permission: function (authInfo, AuthService, USER_ROLES, $state) {
                  return AuthService.permissionCheck(authInfo,[USER_ROLES.member]).catch(
                    function(res){
                      $state.go('login',{'pre':'account','notify':'请先登录'},{'reload':true});
                  });
                },
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(false);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/account.html',
                controller: AccountCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('bill', {
            url: '/bill',
            resolve: {                
                authInfo: function(AuthService){
                  return AuthService.getPermission(false);
                },
                permission: function (authInfo, AuthService, USER_ROLES, $state) {
                  return AuthService.permissionCheck(authInfo,[USER_ROLES.member]).catch(
                    function(res){
                      $state.go('login',{'pre':'bill','notify':'请先登录'},{'reload':true});
                  });
                },
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(false);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/bill.html',
                controller: BillCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('order', {
            url: '/order',
            resolve: {                
                authInfo: function(AuthService){
                  return AuthService.getPermission(false);
                },
                permission: function (authInfo, AuthService, USER_ROLES, $state) {
                  return AuthService.permissionCheck(authInfo,[USER_ROLES.member]).catch(
                    function(res){
                      $state.go('login',{'pre':'order','notify':'请先登录'},{'reload':true});
                  });
                },
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(false);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/order.html',
                controller: OrderCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('collection', {
            url: '/collection',
            resolve: {                
                authInfo: function(AuthService){
                  return AuthService.getPermission(false);
                },
                permission: function (authInfo, AuthService, USER_ROLES, $state) {
                  return AuthService.permissionCheck(authInfo,[USER_ROLES.member]).catch(
                    function(res){
                      $state.go('login',{'pre':'collection','notify':'请先登录'},{'reload':true});
                  });
                },
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(false);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                },
                collections: function(AccountService){
                  return AccountService.getCollections();
                }
            },
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/collection.html',
                controller: CollectionCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('modify-password', {
            url: '/modify-password',
            resolve: {                
                authInfo: function(AuthService){
                  return AuthService.getPermission(false);
                },
                permission: function (authInfo, AuthService, USER_ROLES, $state) {
                  return AuthService.permissionCheck(authInfo,[USER_ROLES.member]).catch(
                    function(res){
                      $state.go('login',{'pre':'modify-password','notify':'请先登录'},{'reload':true});
                  });
                },
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(false);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/modify_password.html',
                controller: ModifyPasswordCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
        .state('modify-phone', {
            url: '/modify-phone',
              resolve: {
                authInfo: function(AuthService){
                  return AuthService.getPermission(false);
                },
                permission: function (authInfo, AuthService, USER_ROLES, $state) {
                  return AuthService.permissionCheck(authInfo,[USER_ROLES.member]).catch(
                    function(res){
                      $state.go('login',{'pre':'modify-phone','notify':'请先登录'},{'reload':true});
                  });
                },
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(false);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },            
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/modify_phone.html',
                controller: ModifyPhoneCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
        .state('modify-email', {
            url: '/modify-email',
              resolve: {
                authInfo: function(AuthService){
                  return AuthService.getPermission(false);
                },
                permission: function (authInfo, AuthService, USER_ROLES, $state) {
                  return AuthService.permissionCheck(authInfo,[USER_ROLES.member]).catch(
                    function(res){
                      $state.go('login',{'pre':'modify-email','notify':'请先登录'},{'reload':true});
                  });
                },
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(false);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },            
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/modify_email.html',
                controller: ModifyEmailCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('shopping-cart', {
            url: '/shopping-cart',
            resolve: {
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(false);
                },
                permission: function (authInfo, AuthService, USER_ROLES, $state) {
                  return AuthService.permissionCheck(authInfo,[USER_ROLES.member]).catch(
                    function(res){
                      $state.go('login',{'pre':'shopping-cart','notify':'请先登录'},{'reload':true});
                  });
                },
                authInfo: function(AuthService){
                  return AuthService.getPermission(false);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },            
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/shopping_cart.html',
                controller: ShoppingCartCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('retrieve-password', {
            url: '/retrieve-password',
            resolve: {
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(true);
                },
                authInfo: function(AuthService){
                  return AuthService.getPermission(true);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },            
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/retrieve_password.html',
                controller: RetrievePasswordCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('bind-email', {
            url: '/bind-email',
              resolve: {
                authInfo: function(AuthService){
                  return AuthService.getPermission(false);
                },
                permission: function (authInfo, AuthService, USER_ROLES, $state) {
                  return AuthService.permissionCheck(authInfo,[USER_ROLES.member]).catch(
                    function(res){
                      $state.go('login',{'pre':'bind-email','notify':'请先登录'},{'reload':true});
                  });
                },
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(false);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },            
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/bind_email.html',
                controller: BindEmailCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('address', {
            url: '/address',
              resolve: {
                authInfo: function(AuthService){
                  return AuthService.getPermission(false);
                },
                permission: function (authInfo, AuthService, USER_ROLES, $state) {
                  return AuthService.permissionCheck(authInfo,[USER_ROLES.member]).catch(
                    function(res){
                      $state.go('login',{'pre':'address','notify':'请先登录'},{'reload':true});
                  });
                },
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(false);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },            
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/address.html',
                controller: AddressCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('community', {
            url: '/community',
              resolve: {
                authInfo: function(AuthService){

                  return AuthService.getPermission(false);
                },
                permission: function (authInfo, AuthService, USER_ROLES, $state) {
                  return AuthService.permissionCheck(authInfo,[USER_ROLES.member]).catch(
                    function(res){
                      $state.go('login',{'pre':'community','notify':'请先登录'},{'reload':true});
                  });
                },
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(false);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },            
            views: {
              "header": {
                templateUrl: './pages/header_community.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/community.html',
                controller: CommunityCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('trophy', {
            url: '/trophy',
              resolve: {
                authInfo: function(AuthService){

                  return AuthService.getPermission(false);
                },
                permission: function (authInfo, AuthService, USER_ROLES, $state) {

                  return AuthService.permissionCheck(authInfo,[USER_ROLES.member]).catch(
                    function(res){
                      $state.go('login',{'pre':'trophy','notify':'请先登录'},{'reload':true});
                  });
                },
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(false);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },            
            views: {
              "header": {
                templateUrl: './pages/header.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/trophy.html',
                controller: TrophyCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('activity', {
            url: '/activity',
              resolve: {
                authInfo: function(AuthService){

                  return AuthService.getPermission(false);
                },
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(false);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },            
            views: {
              "header": {
                templateUrl: './pages/header_community.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/activity.html',
                controller: ActivityCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('item-confirm', {
            url: '/item-confirm/:productID',
              resolve: {
                authInfo: function(AuthService){
                  return AuthService.getPermission(true);
                },
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(true);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                },
                productDetail: function(ProductService,$stateParams,$state){
                  return ProductService.getProductDetail($stateParams.productID).catch(function(res){
                    //商品不存在
                    $state.go('home',{},{'reload':true});
                  });
                },
                similiarProducts: function(ProductService){
                  return ProductService.getSimiliarProductList(1,6);
                }
            },            
            views: {
              "header": {
                templateUrl: './pages/header_tidy.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/item_confirm.html',
                controller: ItemConfirmCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('add-item', {
            url: '/add-item/:itemID',
              resolve: {
                authInfo: function(AuthService){
                  return AuthService.getPermission(false);
                },
                permission: function (authInfo, AuthService, USER_ROLES, $state) {
                  return AuthService.permissionCheck(authInfo,[USER_ROLES.member]).then(
                    function(res){
                      alert(1);
                    },
                    function(res){
                      $state.go('login',{'pre':'home','notify':'请先登录'},{'reload':true});
                  });
                },
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(false);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },            
            views: {
              "header": {
                templateUrl: './pages/header_tidy.html',
                controller: HeaderCtrl
              },
              "content": {
                templateUrl: './pages/add_item.html',
                controller: AddItemCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          })
    .state('about-us', {
            url: '/about-us',
            resolve: {
                accountInfo: function(AccountService){
                  return AccountService.getAccountInfo(true);
                },
                authInfo: function(AuthService){
                  return AuthService.getPermission(true);
                },
                typeList: function(ProductService){
                  return ProductService.getTypes(true);
                },
                platformList: function(ProductService){
                  return ProductService.getPlatforms(true);
                }
            },
            views: {
              "header": {
                templateUrl: './pages/header_tidy.html',
                controller: HeaderCtrl               
              },
              "content": {
                templateUrl: './pages/about_us.html',
                controller: aboutUsCtrl
              },
              "footer": {
                templateUrl: './pages/footer.html',
                controller: FooterCtrl
              }
            }
          }) 
});