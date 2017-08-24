<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<html>
<head>
    <script src="js/jquery-1.9.1.min.js" type="text/javascript"></script>
    <script src="js/ajaxfileupload.js" type="text/javascript"></script>
    <script type="text/javascript">
        $(function () {
            $(":button").click(function () {
                if ($("#file").val().length > 0) {
                    ajaxFileUpload();
                }
                else {
                    alert("请选择图片");
                }
            })
        })
        function ajaxFileUpload() {           
        
            $.ajaxFileUpload
            (
                {
                    url: 'UploadImg.action', //用于文件上传的服务器端请求地址
                    secureuri: false, //一般设置为false
                    fileElementId:'file', //文件上传空间的id属性  <input type="file" id="file" name="file" />
                    dataType: 'json', //返回值类型 一般设置为json
                    success: function (data, status)  //服务器成功响应处理函数
                    {
                        $.each(data.root,function(idx,item){      
                          alert("imgurl:"+item.imgurl); 
						});          
                       
                        
                        $("#img1").attr("src", data);
                        if (typeof (data.error) != 'undefined') {
                            if (data.error != '') {
                                alert(data.error);
                            } else {
                                alert(data.msg);
                            }
                        }
                    },
                    error: function (data, status, e)//服务器响应失败处理函数
                    {
                        alert(e);
                    }
                }
            )
            return false;
        }
    </script>
</head>
<body>    
    <p><input type="file" id="file" name="file" />
    <input type="button" value="上传" /></p>
    <p><img id="img1" alt="上传成功啦" src="" /></p>
</body>
</html>