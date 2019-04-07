function dialogMsg(msg, type) {
    $.toast({
        text: msg, // Text that is to be shown in the toast
        heading: 'Note', // Optional heading to be shown on the toast
        icon: 'information', // Type of toast icon success error information
        showHideTransition: 'fade', // fade, slide or plain
        allowToastClose: false, // Boolean value true or false
        hideAfter: 3000, // false to make it sticky or number representing the miliseconds as time after which toast needs to be hidden
        stack: 5, // false if there should be only one toast at a time or a number representing the maximum number of toasts to be shown at a time
        position: 'bottom-left', // bottom-left or bottom-right or bottom-center or top-left or top-right or top-center or mid-center or an object representing the left, right, top, bottom values
        textAlign: 'left',  // Text alignment i.e. left, right or center
        loader: true,  // Whether to show loader or not. True by default
        loaderBg: '#12a4b4',  // Background color of the toast loader
        beforeShow: function () {}, // will be triggered before the toast is shown
        afterShown: function () {}, // will be triggered after the toat has been shown
        beforeHide: function () {}, // will be triggered before the toast gets hidden
        afterHidden: function () {}  // will be triggered after the toast has been hidden
        // http://www.jqueryfuns.com/resource/view/2412
    });
}

function dialogSuccessMsg(msg) {
    $.toast({
        heading: '成功',
        text: msg,
        icon: 'success'
    })
}
function dialogWarningMsg(msg) {
    $.toast({
        heading: '警告',
        text: msg,
        icon: 'warning'
    })
}
function dialogErrorMsg(msg) {
    $.toast({
        heading: '异常',
        text: msg,
        icon: 'error'
    })
}
function dialogInformationMsg(msg) {
    $.toast({
        heading: '提示信息',
        text: msg,
        icon: 'info'
    })
}

$.fn.serializeObject = function()
{
    var o = {};
    var a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            console.log(o[this.name].push);
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};