var seckill = {
    URL: {
        now: function () {
            return '/seckill/time/now'
        },
        exposer: function (seckillId) {
            return '/seckill/' + seckillId + '/exposer'
        },
        execution: function (seckillId, md5) {
            return '/seckill/' + seckillId + '/' + md5 + '/execution'
        },
    },

    validate: {
        phoneValidation: function (phone) {
            return phone && phone.length === 11 && !isNaN(phone);
        }
    },

    handleSeckillKill: function (seckillId, node) {
        // seckill-box隐藏, 替换为按钮
        node.hide().html('<button class="btn btn-primary" id="seckill-btn">开始秒杀</button>')
        // 首先暴露接口
        $.post(seckill.URL.exposer(seckillId), {}, function (result) {
            if (result && result['success']) {        // 暴露秒杀接口 执行成功
                var exposer = result['data']
                if (exposer['exposed']) {             // 秒杀接口 暴露成功
                    var md5 = exposer['md5']
                    var killUrl = seckill.URL.execution(seckillId, md5)

                    $('#seckill-btn').one('click', function () {
                        // 1. 禁用按钮
                        $(this).addClass('disabled')
                        // 2. 发送秒杀请求
                        $.post(killUrl, {}, function (result) {
                            if (result && result['success']) {
                                var killResult = result['data']
                                var state = killResult['state']
                                var stateInfo = killResult['stateInfo']
                                // 3. 显示秒杀结果
                                node.html('<span class="label label-success">' + stateInfo + '</span>')
                            } else {
                                // 没有手机号的时候会到这
                                // 但其实一到详情页就会让用户输入手机号的
                                console.log('result: ' + result)
                            }
                        })
                    })
                    node.show()
                } else {
                    // 秒杀对象不存在或不在秒杀时限内---到这
                    // 但一般来说, 如果不在秒杀时限内的话, 详情页会直接提示秒杀未开启或秒杀结束
                    // 能到这, 说明用户时间与服务器时间存在误差, 导致不在服务器的秒杀时限内
                    // 秒杀接口暴露失败
                    var startTime = exposer['start']
                    var endTime = exposer['end']
                    var nowTime = exposer['now']
                    // 重新校准时间
                    seckill.countdownModel.countdownFunc(seckillId, startTime, endTime, nowTime)
                }
            } else {
            // 商品id不存在时---到这
                console.log('result: ' + result)
            }
        })
    },

    detail: {
        init: function (params) {
            // 1. 手机号验证与登录
            // 2. 计时交互
            // 验证手机号
            var killPhone = $.cookie('userPhone')
            if (!seckill.validate.phoneValidation(killPhone)) {
                // 如果没有登录就为false, 此时需要绑定phone
                var killphoneModal = $('#killPhoneModal')
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
                        $.cookie('userPhone', inputPhone, {expires: 7, path: '/seckill'})
                        // 刷新页面
                        window.location.reload()
                    } else {
                        // 给老子填啊
                        $('#killPhoneMessage').hide().html('<label class="label label-danger">手机号不对啊</label>').show(300)
                    }
                })
            }

            // 已经登录, 计时交互
            var startTime = params['startTime']
            var endTime = params['endTime']
            var seckillId = params['seckillId']
            $.get(seckill.URL.now(), {}, function (results) {
                if (results && results['success']) {
                    var nowTime = results['data']
                    seckill.countdownModel.countdownFunc(seckillId, startTime, endTime, nowTime)
                } else {
                    console.log('result: ' + results)             // TODO delete
                }
            })
        }
    },

    countdownModel: {
        countdownFunc: function (seckillId, startTime, endTime, nowTime) {
            var box = $('#seckill-box')
            // 时间判断
            if (nowTime > endTime) {
                box.html('秒杀结束!')
            } else if (nowTime < startTime) {
                // 倒计时, 计时时间绑定
                // var killTime = new Date(startTime + 1000)
                // box.countdown(killTime, function (event) {
                //     var format = event.strftime('秒杀倒计时: %D天 %H时 %M分 %S秒')
                //     box.html(format)
                // }).on('finish.countdown', function (){
                //     console.log("inner finish countdown")      // TODO delete
                //     seckill.handleSeckillKill()
                // })
                var nowDate = new Date(nowTime)
                var startDate = new Date(startTime)
                console.log('nowDate:' + nowDate)
                console.log('startDate:' + startDate)
                var str = '秒杀还未开启！' + (startDate.getDate() - nowDate.getDate()) + '天 ' +
                    (startDate.getHours() - nowDate.getHours()) + '时 ' +
                    (startDate.getMinutes() - nowDate.getMinutes()) + '分 ' +
                    (startDate.getSeconds() - nowDate.getSeconds()) + '秒'
                box.html(str)
            } else {
                // 执行秒杀过程
                seckill.handleSeckillKill(seckillId, box)
            }
        }
    }
}