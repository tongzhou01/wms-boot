$(function () {
    // 初始化Table
    var oTable = new TableInit();
    oTable.Init();
});


var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#tb_spec').bootstrapTable({
            url: '/spec',         //请求后台的URL（*）
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
            // height: 800,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            columns: [{
                checkbox: true
            }, {
                field: 'name',
                title: '规格名称'
            }, {
                field: 'unit',
                title: '单位属性'
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
    result += "<a href='javascript:;' class='btn btn-xs green' onclick=\"showData('" + id + "')\" title='查看'><span class='glyphicon glyphicon-search'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs blue' onclick=\"editData('" + id + "')\" title='编辑'><span class='glyphicon glyphicon-pencil'></span></a>";
    result += "<a href='javascript:;' class='btn btn-xs red' onclick=\"delData('" + id + "')\" title='删除'><span class='glyphicon glyphicon-remove'></span></a>";
    return result;
}

function getData(id) {
    $.ajax({
        type: "GET",
        url: "/spec/" + id,
        success: function (result) {
            if (result.code == 0) {
                var data = result.data;
                console.log(data);
                $('#name').val(data.name);
                $('#unit').val(data.unit);
                $('#id').val(data.id);
            } else {
                dialogErrorMsg("查询产品规格详情异常");
            }
        }
    });
}

function addData() {
    // window.location.href = "add";
    $('#specModal').modal('show');
    $('#myModalLabel').text('新增');
    $('#submit').show().off("click").on('click', saveSpec);
}

function showData(id) {
    $('#specModal').modal('show');
    $('#myModalLabel').text('详情');
    getData(id);
    $('#submit').hide();
}

function editData(id) {
    $('#specModal').modal('show');
    $('#myModalLabel').text('修改');
    getData(id);
    $('#submit').show().off("click").on('click', updateSpec);
}

function delData(id) {
    $.ajax({
        type: "DELETE",// 删除请求
        url: "/spec/" + id,
        success: function (result) {
            if (result.code == 0) {
                dialogSuccessMsg("删除成功");
                $('#tb_spec').bootstrapTable('refresh');
            }
        },
        error: function () {
            dialogErrorMsg("删除产品规格异常");
        }
    });
}

function updateSpec() {
    var $specForm = $('#specForm');
    var specForm = $specForm.serializeObject();
    if (doValidate($specForm)) {
        $.ajax({
            contentType: "application/json",
            type: "PUT",// 更新请求
            url: "/spec",
            data: JSON.stringify(specForm),
            dataType: "json",//预期服务器返回的数据类型
            success: function (result) {
                if (result.code == 0) {
                    dialogSuccessMsg("保存成功");
                    $('#specModal').modal('hide');
                    $('#tb_spec').bootstrapTable('refresh');
                }
            },
            error: function () {
                dialogErrorMsg("更新产品规格信息异常");
                $('#specModal').modal('hide');
            }
        });
    }
}

function saveSpec() {
    var $specForm = $('#specForm');
    var specForm = $specForm.serializeObject();
    if (doValidate($specForm)) {
        $.ajax({
            contentType: "application/json",
            type: "POST",// 新增请求
            url: "/spec",
            data: JSON.stringify(specForm),
            dataType: "json",//预期服务器返回的数据类型
            success: function (result) {
                if (result.code == 0) {
                    dialogSuccessMsg("保存成功");
                    $('#specModal').modal('hide');
                    $('#tb_spec').bootstrapTable('refresh');
                }
            },
            error: function () {
                dialogErrorMsg("保存产品规格信息异常");
                $('#specModal').modal('hide');
            }
        });
    }
}

//清除弹窗原数据
$("#specModal").on("hide.bs.modal", function () {
    document.getElementById("specForm").reset();
    resetValidate($('#specForm'));
})

$('#specForm').bootstrapValidator({
    message: 'This value is not valid',
    feedbackIcons: {
        valid: 'glyphicon glyphicon-ok',
        invalid: 'glyphicon glyphicon-remove',
        validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
        name: {
            validators: {
                notEmpty: {
                    message: '规格名称不能为空'
                }
            }
        },
        unit: {
            validators: {
                notEmpty: {
                    message: '单位不能为空'
                }
            }
        }
    }
});

