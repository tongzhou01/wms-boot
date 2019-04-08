/**
 * 新增订单时添加商品节点至table
 */
function addItemToTable() {
    var itemName = $('#itemName').val();
    var reserveNumber = $('#reserveNumber').val();
    var deliveryNumber = $('#deliveryNumber').val();
    var unitPrice = $('#unitPrice').val();
    var amount = $('#amount').val();
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
$("#orderItemModal").on("hidden.bs.modal", function () {
    $('#itemName').val('');
    $('#reserveNumber').val('');
    $('#deliveryNumber').val('');
    $('#unitPrice').val('');
    $('#amount').val('');
})

function saveOrder() {
    var orderJson = $('#orderForm').serializeObject();
    var orderItemJson = jsonConvert($('#orderItemForm').serializeObject());
    var data = {"orderInfo": orderJson, "orderItems": orderItemJson};
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

function test() {
    var json = {"orderItems[0].itemName":"1","orderItems[0].reserveNumber":"1","orderItems[0].deliveryNumber":"1","orderItems[0].unitPrice":"1","orderItems[0].amount":"1","orderItems[1].itemName":"2","orderItems[1].reserveNumber":"2","orderItems[1].deliveryNumber":"2","orderItems[1].unitPrice":"2","orderItems[1].amount":"2"};
    json["orderItems[0].amount"]
    var field = ['itemName','reserveNumber'];

}