$(function () {
    // 初始化Table
    var oTable = new TableInit();
    oTable.Init();
});


var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_order').bootstrapTable({
            url: '/order',         //请求后台的URL（*）
            method: 'get',                      //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
            search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
            strictSearch: true,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            clickToSelect: true,                //是否启用点击选中行
            height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle: true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                checkbox: true
            }, {
                field: 'orderName',
                title: '订单名称'
            }, {
                field: 'orderNo',
                title: '订单号'
            }, {
                field: 'deliveryAddress',
                title: '送货地址'
            }, {
                field: 'deliveryDate',
                title: '送货日期'
            }, {
                field: 'totalAmount',
                title: '总金额'
            }, {
                field: 'id',
                title: '操作',
                width: 120,
                align: 'center',
                valign: 'middle',
                formatter: actionFormatter
            },]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {   //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
            pageSize: params.limit,   //页面大小
            pageNum: params.offset,  //页码
        };
        return temp;
    };
    return oTableInit;
};

//操作栏的格式化
function actionFormatter(value, row, index) {
    var id = value;
    var result = "";
    result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"showData('" + id + "')\" title='查看'><span class='glyphicon glyphicon-search'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"editData('" + id + "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"delData('" + id + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
    return result;
}

function getData(id) {
    $.ajax({
        type : "GET",
        url : "/order/" + id,
        success : function(result) {
            if (result.code == 0) {
                var data = result.data;
                console.log(data);
                $('#orderName').val(data.orderName);
                $('#companyName').val(data.companyName);
                $('#customerName').val(data.customerName);
                $('#deliveryPerson').val(data.deliveryPerson);
                $('#deliveryAddress').val(data.deliveryAddress);
                $('#deliveryDate').val(data.deliveryDate);
                $('#totalAmount').val(data.totalAmount);
                $('#signPerson').val(data.signPerson);
                $('#invoicePerson').val(data.invoicePerson);
                $('#remake').val(data.remake);
                $('#id').val(data.id);
            } else {
                dialogErrorMsg("查询订单详情异常");
            }
        }
    });
}
function addData() {
    $('#orderInfoModal').modal('show');
    $('#submit').show().off("click").on('click', saveOrder);
}
function showData(id) {
    $('#orderInfoModal').modal('show');
    getData(id);
    $('#submit').hide();
}

function editData(id) {
    $('#orderInfoModal').modal('show');
    getData(id);
    $('#submit').show().off("click").on('click', updateOrder);
}

function delData(id) {
    $.ajax({
        type: "DELETE",// 更新请求
        url: "/order/" + id,
        success: function (result) {
            if (result.code == 0) {
                dialogSuccessMsg("删除成功");
                location.reload();
            }
        },
        error : function() {
            dialogErrorMsg("删除订单异常");
        }
    });
}

function updateOrder() {
    var orderForm = $('#orderForm').serializeObject();
    var data = {"orderInfo":orderForm};
    console.log("orderForm=" + orderForm);
    $.ajax({
        contentType:"application/json",
        type: "PUT",// 更新请求
        url: "/order" ,
        data: JSON.stringify(data),
        dataType: "json",//预期服务器返回的数据类型
        success: function (result) {
            if (result.code == 0) {
                dialogSuccessMsg("保存成功");
                $('#orderInfoModal').modal('hide');
                location.reload();
            }
        },
        error : function() {
            dialogErrorMsg("保存订单信息异常");
            $('#orderInfoModal').modal('hide');
        }
    });
}

function saveOrder() {
    var orderForm = $('#orderForm').serializeObject();
    var data = {"orderInfo":orderForm};
    $.ajax({
        contentType:"application/json",
        type: "POST",// 新增请求
        url: "/order" ,
        data: JSON.stringify(data),
        dataType: "json",//预期服务器返回的数据类型
        success: function (result) {
            if (result.code == 0) {
                dialogSuccessMsg("保存成功");
                $('#orderInfoModal').modal('hide');
                location.reload();
            }
        },
        error : function() {
            dialogErrorMsg("保存订单信息异常");
            $('#orderInfoModal').modal('hide');
        }
    });
}

//清除弹窗原数据
$("#orderInfoModal").on("hidden.bs.modal", function() {
    document.getElementById("orderForm").reset();
});


