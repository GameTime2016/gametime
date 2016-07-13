//两次密码匹配
app.directive('sameWith', function() {
  return {
    require: 'ngModel',
    scope: {

      reference: '=sameWith'

    },
    link: function(scope, elm, attrs, ctrl) {
      ctrl.$validators.sameWith = function(viewValue, $scope) {
        if(viewValue == scope.reference){
          ctrl.$setValidity('noMatch', true);
          if(!ctrl.$error.required){
            ctrl.$valid = true;
            ctrl.$invalid = false;
          }
          return true;
        }
        else{
          ctrl.$setValidity('noMatch', false);
          ctrl.$valid = false;
          ctrl.$invalid = true;
          return false;
        }
      }

      scope.$watch("reference", function(value) {
        ctrl.$validate();
      });
    }
  }
});

//密码与账号不能相同
app.directive('differentWith', function() {
  return {
    require: 'ngModel',
    scope: {

      reference: '=differentWith'

    },
    link: function(scope, elm, attrs, ctrl) {
      ctrl.$validators.differentWith = function(viewValue, $scope) {
        if(viewValue != scope.reference){
          ctrl.$setValidity('match', true);
          if(!ctrl.$error.pattern&&!ctrl.$error.required){
            ctrl.$valid = true;
            ctrl.$invalid = false;
          }
          return true;
        }
        else{
          ctrl.$setValidity('match', false);
          ctrl.$valid = false;
          ctrl.$invalid = true;
          return false;
        }
      };

      scope.$watch("reference", function(value) {
        ctrl.$validate();
      });
    }
  }
});

// resize指令
app.directive('resize', function ($window) {
    return {
        link: link
     };    
    function link(scope, element, attrs){
      var elm = document.getElementById("video-panel");
      scope.video_panel_height = elm.offsetHeight + 'px';
       
      angular.element($window).bind('resize', function(){
        var elm = document.getElementById("video-panel");
        scope.video_panel_height = elm.offsetHeight + 'px'; 
        scope.$digest();
      });     
    }
});
// 视频结束事件
app.directive('videoEnded', function () {
    return function (scope, $element) {
      $element[0].addEventListener("ended", function () {
        console.log('yeah'); // never calls
        scope.isPlay = false;
        scope.$digest();
      });
    }
});
//图片加载完成事件指令
app.directive('imageonload', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind('load', function() {
                scope.isImageLoaded = true;
            });
            // element.bind('error', function(){
            //     alert('image could not be loaded');
            // });
        }
    };
});