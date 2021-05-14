var seckill = {
    URL: {},
    validate: {
        phoneValidation: function (phone) {
            return phone && phone.length === 11 && !isNaN(phone);
        }
    },
    detail: {
        init: function (params) {
            // 1. 手机号验证与登录
            // 2. 计时交互
            var killPhone = $.cookie('killPhone')
            var startTime = params['startTime']
            var endTime = params['endTime']
            var seckillId = params['seckillId']

            // 验证手机号
            if (seckill.validate.phoneValidation(killPhone)) {
                // 如果没有登录就为false, 此时需要绑定phone
                var killphoneModal = $('#killphoneModal')
                killphoneModal.modal({
                    // 显示弹出层, 默认为fade
                    show: true,
                    // 拖拽事件
                    backdrop: 'static',
                    // 关闭键盘事件
                    keyboard: false,
                })
                // 绑定点击事件
                $('#killPhoneBtn').click(function () {
                    var inputPhone = $('#killPhoneKey').val()
                    if (seckill.validate.phoneValidation(inputPhone)) {
                        // phone写入cookie
                        $.cookie('killPhone', inputPhone, {expires: 7, path: '/seckill'})
                        // 刷新页面
                        window.location.reload()
                    } else {
                        // 给老子填啊
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号不对啊</label>').show(300)
                    }
                })
            }
        }
    },
}