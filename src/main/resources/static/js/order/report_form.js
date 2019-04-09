var myChart = echarts.init(document.getElementById('reportForm'));

function updateView(year, month) {
    var url = '/order/count/' + year + '/' + month;
    // 异步加载数据
    $.get(url).done(function (data) {
        var leg;
        leg = data.legend;
        leg.push("总计");
        // 填入数据
        myChart.setOption({
            title: {
                text: ''
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: leg
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            toolbox: {
                feature: {
                    saveAsImage: {}
                }
            },
            xAxis: {
                name: '月份',
                type: 'category',
                boundaryGap: false,
                data: data.categories
            },
            yAxis: {
                name: '金额（元）',
                type: 'value'
            },
            series: data.data
        }, true);
    });
}


$('#yearDay').change(function (e) {
    var value = e.delegateTarget.value;
    var stringArr = value.split('-');
    updateView(stringArr[0], stringArr[1]);
});

$('#yearDay').datetimepicker({
    format: 'yyyy-mm',
    autoclose: true,
    todayBtn: true,
    startView: 'year',
    minView: 'year',
    maxView: 'decade',
    language: 'zh-CN',
});

$(function () {
    var date = new Date();
    var fullYear = date.getFullYear();
    var month = (date.getMonth() + 1);
    $('#yearDay').val(fullYear + '-' + month);
    updateView(fullYear, month);
});
