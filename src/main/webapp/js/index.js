const $indexMethods = {
   getUserInfo : function (params,success,error){
	   get($path+'/api/admin/admin_main.do',params,success,error);
   },
   cancelLogined : function (params,success,error){
	   get($path+'/api/admin/'+params+'/signout.do',null,success,error);
   },
   getMessage : function (type,params,success,error){
	   get($path+'/api/acceptMessage/'+type+'/list.do',params,success,error);
   }
}

Vue.component('side-menu',{
	props:['menu'],
	data:function(){
		return {
           hiding : true
		};
	},
	template:'<li :key="menu.node.id" >'+
	         '<a v-on:click="menuStatus()">{{menu.node.name}}</a>'+
			 '<dl v-if="menu.list.length>0" v-bind:class="{hide:hiding}">'+
			 '<dd v-for="m in menu.list" :key="m.node.id">'+
			 //'<a>{{m.node.name}}</a>'+
			 '<slot v-bind:m="m"></slot>'+
			 '</dd>'+
			 '</dl>'+
			 '</li>',
	methods:{
        menuStatus : function (){
			this.hiding = !this.hiding;
		}
	}
});

const home = {
	template :'#hometemplate'
}

// const routes = [ 
// 	{path:'/',component:home},
// 	{path:'/authority',component:authority}
//   ]

// 你还可以传别的配置参数, 不过先这么简单着吧。
const router = new VueRouter({
	//routes // （缩写）相当于 routes: routes
	routes : [ 
		{
			name:'home',
			path:'/',
			component:home
		},
		{
			name:'authority',
			path:'/authority',
			component:authority
		}
	]
})

const $app = new Vue({
	router,
	el:'#app',
	data:{
		header:{
			title : [{name : '回复我的',messageType : 1},{name : '任务通知',messageType : 2}],
			message : {
				list : [],
				messageType : null
			},
			notity:{}
		},
		leftside:{
			items:{}
		},
		loginInfo : null
	},
	components:{
		'message-item' : {
			props:['message'],
			template:'<div class="li-div"><p v-if="message.length == 0">没有任何消息</p>'+
	    	         '<ul> <li v-for="item in message" > 来自 ：{{item}}</li> </ul></div>'		
		}
	},
	methods:{
		signout : function (){
		   $indexMethods.cancelLogined($user,function (response){
			 if( response.status == 200 ){
				 window.location.href = $path;
			 }
		   });
	    },
	    getMessageForType : function (){
	    	$indexMethods.getMessage();
	    },
	    showMessage : function (messageType){
	    	console.log(messageType);
	    	$app.header.message.messageType = messageType;
	    	$indexMethods.getMessage(messageType,{pageNum:1,pageSize:6},function (response){
				 var data = response.data;				 
				 if( data.length > 0 ){
					 $app.header.message.list = data;					 
				 }
			});
	    },
	    hideMessage : function (){
	    	$app.header.message.messageType = null;
		},
		packup : function (){
			console.log('测试packup方法');
			$(".layui-side").animate({width:'toggle'},350);
			$('.layui-body').toggleClass("slide-body");
		}
	},
	created : function (){
		$indexMethods.getUserInfo({accountname : $user},function (response){
			if( response.status == 200 ){
				console.log(response.data);
			   $app.loginInfo = response.data;
			}
		});
		$indexMethods.getMessage(4,{pageNum:1,pageSize:1},function (response){
			 var data = response.data;
			 if( data.length > 0 ){
				 $app.header.notity = data[0];
			 }
		});
	},
	destroyed : function (){}
});



