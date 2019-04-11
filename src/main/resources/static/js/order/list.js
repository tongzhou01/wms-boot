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
            // height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
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
                field: 'createTime',
                title: '创建时间'
            }, {
                field: 'id',
                title: '操作',
                // width: 150,
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
            pageNum: (params.offset / params.limit) + 1,  //页码
        };
        return temp;
    };
    return oTableInit;
};

//操作栏的格式化
function actionFormatter(value, row, index) {
    var id = value;
    var result = "";
    result += "<a href='javascript:;' class='btn btn-xs ' onclick=\"showItem('" + id + "')\" title='商品列表'><span class='glyphicon glyphicon-list-alt'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs ' onclick=\"exportExcel('" + id + "')\" title='导出Excel'><span class='glyphicon glyphicon-download-alt'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs ' onclick=\"openMailModal('" + id + "')\" title='发送邮件'><span class='glyphicon glyphicon-envelope'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs ' onclick=\"showData('" + id + "')\" title='预览'><span class='glyphicon glyphicon-search'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs ' onclick=\"printOrder('" + id + "')\" title='打印'><span class='glyphicon glyphicon-print'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs ' onclick=\"editData('" + id + "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
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
                $('#orderPhone').val(data.orderPhone);
                $('#orderAddress').val(data.orderAddress);
                $('#invoicePerson').val(data.invoicePerson);
                $('#remake').val(data.remake);
                $('#id').val(data.id);
            } else {
                dialogErrorMsg("查询订单详情异常");
            }
        }
    });
}

function openMailModal(id) {
    $('#orderId').val(id);
    $('#sendMailModal').modal('show');
}

function addData() {
    // window.location.href = "add";
    $('#orderInfoModal').modal('show');
    $('#myModalLabel').text('新增');
    $('#submit').show().off("click").on('click', saveOrder);
}

function addData2() {
    window.location.href = "add";
}

function sendMail() {
    var $sendMailForm = $('#sendMailForm');
    var sendMailForm = $sendMailForm.serializeObject();
    $.ajax({
        contentType:"application/json",
        type: "POST",// 更新请求
        url: "/order/mail",
        data: JSON.stringify(sendMailForm),
        dataType: "json",//预期服务器返回的数据类型
        success: function (result) {
            if (result.code == 0) {
                dialogSuccessMsg("发送成功");
                $('#sendMailModal').modal('hidden');
            }
        },
        error : function() {
            dialogErrorMsg("发送异常");
        }
    });
}

/*function showData(id) {
    $('#orderInfoModal').modal('show');
    $('#myModalLabel').text('详情');
    getData(id);
    $('#submit').hide();
}*/
function showData(id) {
    window.open("/order/print/" + id);
}
function printOrder(id) {
    window.open("/order/print/" + id).print();
}

function editData(id) {
    $('#orderInfoModal').modal('show');
    $('#myModalLabel').text('修改');
    getData(id);
    $('#submit').show().off("click").on('click', updateOrder);
}

// 下载文件
function exportExcel(id) {
    window.open("/order/excel/" + id);
}

function delData(id) {
    $.ajax({
        type: "DELETE",// 更新请求
        url: "/order/" + id,
        success: function (result) {
            if (result.code == 0) {
                dialogSuccessMsg("删除成功");
                $('#tb_order').bootstrapTable('refresh');
            }
        },
        error : function() {
            dialogErrorMsg("删除订单异常");
        }
    });
}

function showItem(id) {
    window.location.href = "/view/item/list?orderId=" + id;
}


function updateOrder() {
    var $orderForm = $('#orderForm');
    var orderForm = $orderForm.serializeObject();
    var data = {"orderInfo":orderForm};
    if (doValidate($orderForm)) {
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
                    $('#tb_order').bootstrapTable('refresh');
                }
            },
            error : function() {
                dialogErrorMsg("更新订单信息异常");
                $('#orderInfoModal').modal('hide');
            }
        });
    }
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
                $('#tb_order').bootstrapTable('refresh');
                document.getElementById("orderForm").reset();
            }
        },
        error : function() {
            dialogErrorMsg("保存订单信息异常");
            $('#orderInfoModal').modal('hide');
        }
    });
}

$(function () {
    $('#orderForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            orderName: {
                validators: {
                    notEmpty: {
                        message: '订单名称不能为空'
                    }
                }
            },
            companyName: {
                validators: {
                    notEmpty: {
                        message: '公司名称不能为空'
                    }
                }
            },
            customerName: {
                validators: {
                    notEmpty: {
                        message: '顾客姓名不能为空'
                    }
                }
            },
            totalAmount: {
                validators: {
                    notEmpty: {
                        message: '总金额不能为空'
                    }
                }
            },
            deliveryPerson: {
                validators: {
                    notEmpty: {
                        message: '送货人不能为空'
                    }
                }
            },
            deliveryAddress: {
                validators: {
                    notEmpty: {
                        message: '送货地址不能为空'
                    }
                }
            },
            deliveryDate: {
                trigger: 'change',
                validators: {
                    notEmpty: {
                        message: '送货日期不能为空'
                    }
                }
            },
            /*signPerson: {
                validators: {
                    notEmpty: {
                        message: '签收人不能为空'
                    }
                }
            },*/
            invoicePerson: {
                validators: {
                    notEmpty: {
                        message: '开票人不能为空'
                    }
                }
            },
            orderPhone: {
                validators: {
                    notEmpty: {
                        message: '订货电话不能为空'
                    }
                }
            },
            orderAddress: {
                validators: {
                    notEmpty: {
                        message: '定货地址不能为空'
                    }
                }
            }
        }
    });
});

$(function () {
    $('#sendMailForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            receiverAddress: {
                validators: {
                    notEmpty: {
                        message: '邮箱地址不能为空'
                    }
                }
            },
        }
    });
})

//清除弹窗原数据
$("#orderInfoModal").on("hidden.bs.modal", function() {
    document.getElementById("orderForm").reset();
});

//清除验证
$("#orderInfoModal").on("hide.bs.modal", function() {
    resetValidate($('#orderForm'));
});

//清除弹窗原数据
$("#sendMailModal").on("hidden.bs.modal", function() {
    document.getElementById("sendMailForm").reset();
});

//清除验证
$("#sendMailModal").on("hide.bs.modal", function() {
    resetValidate($('#sendMailForm'));
});

$('#deliveryDate').datetimepicker({
    minView: "month",
    language: 'zh-CN',
    format: 'yyyy-mm-dd',
    autoclose: true,
    todayBtn: true,
    pickerPosition: "bottom-left"
});

