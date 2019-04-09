/**
 * 新增订单时添加商品节点至table
 */
function addItemToTable() {
    var $itemModalForm = $('#itemModalForm');
    if (doValidate($itemModalForm)) {
        var itemName = $('#itemName').val();
        var reserveNumber = $('#reserveNumber').val();
        var deliveryNumber = $('#deliveryNumber').val();
        var unitPrice = $('#unitPrice').val();
        var amount = unitPrice * deliveryNumber;
        var length = $("#itemTable").find("tr").length;
        $("#itemTable").find("tbody").append('<tr><th scope="row">' + length + '</th>'
            + addTd('itemName', itemName, length)
            + addTd('reserveNumber', reserveNumber, length)
            + addTd('deliveryNumber', deliveryNumber, length)
            + addTd('unitPrice', unitPrice, length)
            + addTd('amount', amount, length)
            + '<td><a href="javascript:;" class="btn btn-xs red" onclick="delTd(this)" title="删除"><span class="glyphicon glyphicon-remove"></span></td></tr>'
        );
        $('#orderItemModal').modal('hide');
    }
}

function delTd(obj) {
    $(obj).parent().parent().remove();
}

function addTd(name, val, length) {
    return '<td><input type="hidden" name="' + name + '" value="' + val + '"/>' + val + '</td>';
}

/**
 * 添加商品modal
 */
function addItemList() {
    $('#orderItemModal').modal('show');
}

//清除弹窗原数据
$("#orderItemModal").on("hide.bs.modal", function () {
    $('#itemName').val('');
    $('#reserveNumber').val('');
    $('#deliveryNumber').val('');
    $('#unitPrice').val('');
    $('#amount').val('');
})

function saveOrder() {
    var $orderForm = $('#orderForm');
    var $orderItemForm = $('#orderItemForm');
    var orderJson = $orderForm.serializeObject();
    var orderItemJson = jsonConvert($orderItemForm.serializeObject());
    var data = {"orderInfo": orderJson, "orderItems": orderItemJson};
    if (doValidate($orderForm)) {
        $.ajax({
            contentType:"application/json",
            type: "POST",// 新增请求
            url: "/order" ,
            data: JSON.stringify(data),
            dataType: "json",//预期服务器返回的数据类型
            success: function (result) {
                if (result.code == 0) {
                    // location.reload();
                    dialogSuccessMsg("保存成功");
                }
            },
            error : function() {
                dialogErrorMsg("保存订单信息异常");
            }
        });
    }
}

function jsonConvert(jsonData) {
    var vCount = 0;
    // 计算json内部的数组最大长度
    for(var item in jsonData){
        var tmp = $.isArray(jsonData[item]) ? jsonData[item].length : 1;
        vCount = (tmp > vCount) ? tmp : vCount;
    }
    if(vCount > 1) {
        var jsonData2 = new Array();
        for(var i = 0; i < vCount; i++){
            var jsonObj = {};
            for(var item in jsonData) {
                jsonObj[item] = jsonData[item][i];
            }
            jsonData2.push(jsonObj);
        }
        return jsonData2;
    }else{
        return jsonData;
    }
}

$('#deliveryDate').datetimepicker({
    minView: "month",
    language: 'zh-CN',
    format: 'yyyy-mm-dd',
    autoclose: true,
    todayBtn: true,
    pickerPosition: "bottom-left"
});

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
                trigger:'change',
                validators: {
                    notEmpty: {
                        message: '送货日期不能为空'
                    }
                }
            },
            signPerson: {
                validators: {
                    notEmpty: {
                        message: '签收人不能为空'
                    }
                }
            },
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

    $('#itemModalForm').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            itemName: {
                validators: {
                    notEmpty: {
                        message: '商品名称不能为空'
                    }
                }
            },
            reserveNumber: {
                validators: {
                    notEmpty: {
                        message: '预定数量不能为空'
                    }
                }
            },
            deliveryNumber: {
                validators: {
                    notEmpty: {
                        message: '配送数量不能为空'
                    }
                }
            },
            unitPrice: {
                validators: {
                    notEmpty: {
                        message: '单价不能为空'
                    }
                }
            }
        }
    });
});