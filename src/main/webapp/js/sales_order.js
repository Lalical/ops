layui.use(['form', "jquery", "layer", "element"], function() {
	let element = layui.element;
	let form = layui.form;
	let $ = layui.$;
	let params = {
		customerid:null,
		product:[]
	};
	getCustomer()
	$(".addBtn").click(function() {
		layer.open({
			type: 1,
			shade: 0,
			maxWidth: 600,
			btnAlign: 'l',
			ixed: false,
			title: false,
			content: $('#mylayer'),
			yes: function(index, layero) {
				layer.close()
			}
		});
		$(".confirmBtn").show();
	})
	//监听提交
	$(".confirmBtn").on('click', function() {
		console.log(params);
	
	});
	form.on('submit(formDemo)', function(data) {
		creatTags(data.field);
		params.product.push(data.field);
		params.customerid = parseInt( data.field.customer);
		layer.closeAll('page');
		clearInput();;
		return false;
	});
	function getCustomer(){
		$.ajax({
		url: "../data/customer.json",
		dataType: 'json', //服务器返回json格式数据
		type: 'get', //HTTP请求类型
		success: function(data) {
			console.log(data)
			let str = "";
			for (var i = 0; i < data.customer.length; i++) {
				str += '<option value="' + data.customer[i].id + '" customerid ="'+data.customer[i].id+'">' + data.customer[i].name + '</option>'
			}
			$("#customer").html(str)
			form.render();
		},
		error: function(xhr, type, errorThrown) {
	
		}
	});
	}
	function creatTags(item) {
		let obj = $(".order-tag ul");
		let str = '<li><span class="tag-list-item">'+item.name+item.count+'个，单价'+item.perprice+'元</span><i class="layui-icon layui-icon-close"></i></li>';
		obj.append(str)
	}
	!function deletTags(){
		$("ul").on("click",".layui-icon-close",function(event){
		 var $tar = $(event.target);
		  if ($tar[0].localName == "li") {
			var i = $tar.parent().children().index($tar);
		  } else {
			var i = $tar.parents('li').index();
		  }
			$(this).parents("li").remove();
			params.product.splice(i,1)
			console.log(params)
		})
	}();
	function clearInput(){
		$(".cancel").click();
	}
});

