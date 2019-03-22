
var $path = 'http://localhost:8081/O.OBusinessPlan';
var resourcePath = $path+'/api/resource/resources.do';
var $token = $.cookie('token');
var $user = $.cookie('name');

//请求的方法封装
function $http(path,params,type,execute,error){
    
    $.ajax({
        url:path,
        type:type,
        data:params,
        dataType:'JSON',
        success:execute,
        error:error,
        headers:{'X-user':$user,'X-token':$token}
    })
     
};

function get(path,params,execute,error){
	$http(path,params,'GET',execute,error);
}

$(document).ready(function(){

       get(resourcePath,null,function (data){
       if( data.status == 200 ){      
    	   $app.leftside.items = data.data;
       }else{
           console.log(data.message);
       }
   });

});
