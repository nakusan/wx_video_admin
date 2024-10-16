// 删除视频
var deleteVideo = function (videoId) {
    var flag = window.confirm("是否删除该视频？");
    if (!flag) {
        return;
    }

    $.ajax({
        url: $("#hdnContextPath").val() + "/management/deleteVideo?videoId=" + videoId,
        type: "POST",
        async: false,
        success: function (data) {
            if (data.status === 200 && data.msg === "OK") {
                alert("操作成功");
                var jqGrid = $("#videoList");
                jqGrid.jqGrid().trigger("reloadGrid");
            } else {
                console.log(JSON.stringify(data));
            }
        }
    })
};

var updateVideo = function (videoId) {
    // 构建更新页面的 URL
    var updateUrl = $("#hdnContextPath").val() + "/management/showUpdateVideo?videoId=" + videoId;
    $("#updateVideoShow").attr("href", updateUrl);
    $("#updateVideoShow").click();
};

var toggleStatus = function (videoId, targetStatus) {
    $.ajax({
        url: $("#hdnContextPath").val() + "/management/toggleStatus?videoId=" + videoId + "&videoStatus=" + targetStatus,
        type: 'POST',
        async: false,
        success: function(data) {
            if (data.status === 200 && data.msg === "OK") {
                // 刷新表格数据
                var jqGrid = $("#videoList");
                jqGrid.jqGrid().trigger("reloadGrid");
            } else {
                console.log(JSON.stringify(data));
            }
        }
    });
}

function playVideo(videoUrl) {
    var videoPlayer = $('#videoPlayer');
    var videoSource = $('#videoSource');

    videoSource.attr('src', videoUrl);
    videoPlayer[0].load();

    $('#videoModal').modal({
        backdrop: 'static',
        keyboard: false
    });

    $('#videoModal').on('hidden.bs.modal', function () {
        videoPlayer[0].pause();
        videoPlayer[0].currentTime = 0;
    });

    $('#videoModal').modal('show');
}

// 定义视频列表对象
var VideoList = function () {
    // 举报列表
	var handleUsersReportsList = function() {
		// 上下文对象路径
		var hdnContextPath = $("#hdnContextPath").val();
		var apiServer = $("#apiServer").val();
		var jqGrid = $("#videoList");  
        jqGrid.jqGrid({  
            caption: "视频列表",
            url: hdnContextPath + "/management/videoList",
            mtype: "post",  
            styleUI: 'Bootstrap',//设置jqgrid的全局样式为bootstrap样式  
            datatype: "json",  
            colNames: ['ID', '封面', '标题', '分类', '内容', '时长', '价格', '购买数', '状态', '创建时间', '操作'],
            colModel: [  
                { name: 'videoId', index: 'videoId', width: 20, sortable: false, hidden: true },
                { name: 'thumbUrl', index: 'thumbUrl', width: 11, sortable: false, hidden: true,
                    formatter:function(cellvalue, options, rowObject) {
                        var src = apiServer + cellvalue;
                        return "<img src='" + src + "' alt='当前视频封面' style='width: 100%; height: auto; max-width: 100px; max-height: 100px; object-fit: contain;' />";
                    }
                },
                { name: 'videoTitle', index: 'videoTitle', width: 43, sortable: false },
                { name: 'categoryName', index: 'categoryName', width: 12, sortable: false },
                { name: 'videoPath', index: 'videoPath', width: 8, sortable: false,
                    formatter:function(cellvalue, options, rowObject) {
                        var src = apiServer + cellvalue;
                        // var display = "<a href='" + src + "' target='_blank'>点我播放</a>";
                        var display = "<a href='javascript:void(0);' onclick='playVideo(\"" + src + "\")'>点我播放</a>";
                        return display;
                    }
                },
                { name: 'duration', index: 'duration', width: 10, sortable: false },
                { name: 'price', index: 'price', width: 6, sortable: false },
                { name: 'salesCounts', index: 'salesCounts', width: 6, sortable: false },
                { name: 'status', index: 'status', width: 6, sortable: false, hidden: false,
                	formatter:function(cellvalue, options, rowObject) {
                        var color = cellvalue === 2 ? 'green' : 'red';
                        var text = cellvalue === 2 ? '正常' : '禁播';
                        return '<span style="color: ' + color + ';">' + text + '</span>';
			    	}
			    },
                { name: 'createTime', index: 'createTime', width: 18, sortable: false, hidden: false,
                	formatter:function(cellvalue, options, rowObject) {
			    		return Common.formatTime(cellvalue,'yyyy-MM-dd HH:mm:ss');
			    	}
			    },
                { name: '', index: '', width: 25,
                    formatter: function (cellvalue, option, rowObject) {
                        var html = '';
                        if (rowObject.status === 2) {
                            html += '<button class="btn btn-danger" onclick=toggleStatus("' + rowObject.videoId + '",0) style="padding: 1px 3px 1px 3px;">禁止</button>&nbsp;&nbsp;';
                        } else {
                            html += '<button class="btn btn-success" onclick=toggleStatus("' + rowObject.videoId + '",1) style="padding: 1px 3px 1px 3px;">发布</button>&nbsp;&nbsp;';
                        }
                        html += '<button class="btn btn-outline blue-sharp" id="" onclick=updateVideo("' + rowObject.videoId + '") style="padding: 1px 3px 1px 3px;">编辑</button>&nbsp;&nbsp;';
                        html += '<button class="btn btn-outline yellow-gold" id="" onclick=deleteVideo("' + rowObject.videoId + '") style="padding: 1px 3px 1px 3px;">删除</button>';
                        return html;
                    }
                }
            ],  
            viewrecords: true,  		// 定义是否要显示总记录数
            rowNum: 10,					// 在grid上显示记录条数，这个参数是要被传递到后台
            rownumbers: true,  			// 如果为ture则会在表格左边新增一列，显示行顺序号，从1开始递增。此列名为'rn'
            autowidth: true,  			// 如果为ture时，则当表格在首次被创建时会根据父元素比例重新调整表格宽度。如果父元素宽度改变，为了使表格宽度能够自动调整则需要实现函数：setGridWidth
            height: 500,				// 表格高度，可以是数字，像素值或者百分比
            rownumWidth: 36, 			// 如果rownumbers为true，则可以设置行号 的宽度
            pager: "#videoListPager",		// 分页控件的id  
            subGrid: false				// 是否启用子表格
        }).navGrid('#videoListPager', {
            edit: false,
            add: false,
            del: false,
            search: false
        });

        // 随着窗口的变化，设置jqgrid的宽度  
        $(window).bind('resize', function () {  
            var width = $('.videoList_wrapper').width() * 0.99;  
            jqGrid.setGridWidth(width);  
        });  
        
        // 不显示水平滚动条
        // jqGrid.closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
    };
    
    return {
        // 初始化各个函数及对象
        init: function () {
        	handleUsersReportsList();
        }
    };
}();


jQuery(document).ready(function() {
	VideoList.init();
});