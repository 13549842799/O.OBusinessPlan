const basePath = 'http://localhost:8081/O.OBusinessPlan';
//为了统一路径名，能够被不能位置的html文件所引用，不能使用相对路径

document.write("<script src='https://cdn.jsdelivr.net/npm/vue/dist/vue.js'></script>");
document.write("<script type='text/javascript' src='"+basePath+"/layui-v2.4.3/layui/layui.js' ></script>");
document.write("<script type='text/javascript' src='"+basePath+"/js/jquery-3.2.1.js'></script>");
document.write(" <link rel='stylesheet' type='text/css' href='"+basePath+"/layui-v2.4.3/layui/css/layui.css'>");
document.write("<script type='text/javascript' src='"+basePath+"/js/jquery.cookie.js'></script>");
document.write("<script type='text/javascript' src='"+basePath+"/js/baseJs.js'></script>");