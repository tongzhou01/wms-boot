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
    );
    $('#orderItemModal').modal('hide');
}

function addTd(name, val, length) {
    return '<td><input type="hidden" name="orderItems[' + (length - 1) + '].' + name + '" value="' + val + '"/>' + val + '</td>';
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
    var orderForm = $('#orderForm').serializeObject();
    // var orderItemFormArray = $('#orderItemForm').serializeArray();
    // console.log(orderItemFormArray);
    var jsonArray = [];
    var nameArray1 = [];
    /*$.each(orderItemFormArray, function () {
        var nameArray = this.name.split(".");//orderItems[0].itemName
        if (nameArray1.indexOf(nameArray[0]) == -1) {
            nameArray1.push(nameArray[0]);
        }
    })
    $.each(orderItemFormArray, function () {
        var nameArray = this.name.split(".");//orderItems[0].itemName
        o[nameArray[1]] = this.value || '';// o = {"itemName":"1"}
        $.each(nameArray1, function () {
            var o = {};

        })
        if (o[this.name]) {
            console.log(o[this.name].push);
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    })*/
    // var orderItemForm = $('#orderItemForm').serializeObject();
    var data = {"orderInfo": orderForm, "orderItems": []};
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