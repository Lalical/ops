(function(){
	var pageData = JSON.parse(localStorage.getItem("pageData")) 
	var list = document.querySelector("#pageConainer");
	var str = "";
	for (var i = 0; i < pageData.length; i++) {
		str+= '<li class="layui-nav-item" id="'+pageData[i].id+'"><a class="" href="'+pageData[i].id+'.html">'+pageData[i].name+'</a></li>'
	}
	list.innerHTML= str;
	var title = document.getElementsByTagName("title")[0].attributes[0].value;
	var currentPage = document.getElementById(title);
	currentPage.classList.add("layui-this")
})()