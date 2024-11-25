//////////////////
//////////////////
$(document).ready(function () {
    // 通过 AJAX 请求获取分类数据
    $.ajax({
        url: '/management/categories',
        method: 'GET',
        success: function (event) {
            const videoCategory = $("#videoCategory");
            // 清空现有选项
            videoCategory.empty();

            const objs = event.data;
            objs.forEach(obj => {
                const option = $("<option>", {
                    value: obj.categoryId,
                    text: obj.categoryName
                });
                if (parseInt(obj.categoryId) === parseInt($("#categoryId").val())) {
                    option.prop('selected', true);
                }
                videoCategory.append(option);
            });
        },
        error: function (response, ajaxOptions, thrownError) {
            console.error('Error fetching categories:', thrownError);
            Error.displayError(response, ajaxOptions, thrownError);
        }
    });

    $('#price').blur(function () {
        const value = $(this).val().trim();
        if (value !== '') {
            // 尝试将输入转换为数字
            const price = parseFloat(value);
            if (!isNaN(price)) {
                // 格式化为两位小数
                $(this).val(price.toFixed(2));
            }
        }
    });

});

$("#updateVideoForm").validate({
    errorElement: 'span', //default input error message container
    errorClass: 'help-block', // default input error message class
    focusInvalid: false, // do not focus the last invalid input
    ignore: "", // validate all fields including form hidden input
    rules: {
        price: {
            required: true,
            number: true, // 只能输入数字
            rangelength: [1, 50] // 长度范围
        },
        videoTitle: {
            required: true,
            maxlength: 50
        },
        videoDesc: {
            maxlength: 200
        }
    },
    messages: {
        price: {
            required: "请输入有效的数字.",
            number: "请输入有效的数字.",
            rangelength: "价格长度请控制在1-50位."
        },
        videoTitle: {
            required: "视频名称不能为空.",
            maxlength: "视频名称请不要超过50字."
        },
        videoDesc: {
            maxlength: "视频简介请不要超过200字."
        }

    },
    invalidHandler: function (event, validator) { //display error alert on form submit
        $('.alert-danger', $('#updateVideoForm')).show();
    },

    highlight: function (element) { // hightlight error inputs
        $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
    },
    success: function (label) {
        label.closest('.form-group').removeClass('has-error');
        label.remove();
    },
    errorPlacement: function (error, element) {
        error.insertAfter(element.closest('#input-error').find('.form-control')); // 插入错误消息到输入框后面
    },
    submitHandler: function (form) {
        submitVideo();
    }
});

var submitVideo = function () {
    //
    $("#updateVideoForm").ajaxSubmit({
        url: $("#hdnContextPath").val() + '/management/updateVideo',
        type: 'POST',
        success: function (data) {
            if (data.status === 200 && data.msg === 'OK') {
                showMessage("视频信息编辑成功");
                $("#editVideoModal").modal('hide');
                const jqGrid = $("#videoList");
                jqGrid.jqGrid().trigger("reloadGrid");
            }

        },
        error: function (response, ajaxOptions, thrownError) {
            console.log(response);
            $("#editVideoModal").modal('hide');
            Error.displayError(response, ajaxOptions, thrownError);
        }
    });

    // function showErrorMessage(message) {
    //     // 创建一个新的 popup，并将样式直接写在 div 元素中
    //     const errorMessageDiv = $('<div style="position: fixed; top: 80px; left: 50%; transform: translateX(-50%); ' +
    //         'background-color: #f8d7da; border: 1px solid #f5c6cb; border-radius: 20px; padding: 1px 100px; ' +
    //         'z-index: 1000; display: none; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); ' +
    //         'color: #721c24; font-size: 1.5rem; font-weight: 300; line-height: 1; text-align: center; margin-bottom: 1rem; ' +
    //         'max-width: 100%; word-wrap: break-word; height: 50px;">' +
    //         '<p>' + message + '</p>' +
    //         '</div>');
    //
    //     // 将新创建的 popup 插入到 body 中
    //     $('body').append(errorMessageDiv);
    //     // 显示 popup
    //     errorMessageDiv.fadeIn(500);
    //     // 设置一个定时器，在几秒钟后让 popup 淡出并最终移除
    //     setTimeout(function () {
    //         errorMessageDiv.fadeOut(500, function () {
    //             $(this).remove();
    //         });
    //     }, 3000); // 5000毫秒后执行淡出操作
    // }

    function showMessage(message) {
        // 创建一个新的 popup，并将样式直接写在 div 元素中
        const messageDiv = $('<div style="position: fixed; top: 80px; left: 50%; transform: translateX(-50%); ' +
            'background-color: #d4edda; border: 1px solid #c3e6cb; border-radius: 20px; padding: 1px 100px; ' +
            'z-index: 1000; display: none; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); ' +
            'color: #155724; font-size: 1.5rem; font-weight: 300; line-height: 1; text-align: center; margin-bottom: 1rem; ' +
            'max-width: 100%; word-wrap: break-word; height: 50px;">' +
            '<p>' + message + '</p>' +
            '</div>');

        // 将新创建的 popup 插入到 body 中
        $('body').append(messageDiv);
        // 显示 popup
        messageDiv.fadeIn(500);
        // 设置一个定时器，在几秒钟后让 popup 淡出并最终移除
        setTimeout(function () {
            messageDiv.fadeOut(500, function () {
                $(this).remove();
            });
        }, 3000); // 5000毫秒后执行淡出操作
    }


};