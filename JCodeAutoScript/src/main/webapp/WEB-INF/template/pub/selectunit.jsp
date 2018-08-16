<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<style>
<!--
#comm_selectUnit .btn {
	border-radius: 5px 5px 5px 5px;
}
-->
</style>
<div class="modal fade" id="comm_selectUnit">
	<div class="modal-dialog" style="width: 900px;">
		<div class="modal-content">
			<div class="panel panel-default collapsed">
				<div class="panel-heading">
					<h4 class="panel-title">机构选择</h4>
					<div class="panel-options">
						<a href="#" data-toggle="panel">
							<span class="collapse-icon">&ndash;</span>
							<span class="expand-icon">+</span>
						</a>
					</div>
				</div>
				<input type='hidden' id='unitInputId'>
				<div class="panel-body">
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label for="com_unitId" class="control-label">组织编号</label> <input
									type="text" class="form-control" id="com_unitId">
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label for="com_unitName" class="control-label">组织名称</label> <input
									type="text" class="form-control" id="com_unitName">
							</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-12">
							<div class="form-group pull-right">
								<button type="button" class="btn btn-success"
									id="unitFilter_button">过滤</button>
							</div>
						</div>
					</div>
				</div>
				<div class="panel-body-" style="width: 100%;">
					<table class="table table-bordered table-striped" id="unitTable"
						style="width: 100%;">
						<thead>
							<tr>
								<th>组织编号</th>
								<th>组织名称</th>
							</tr>
						</thead>
						<tbody class="middle-align">
						</tbody>
					</table>
				<div class="modal-footer">  
					<button type="button" id="selectUnitAll" class="btn btn-blue"
						style="display: none;">全选</button>
					<button type="button" id="selectUnit" class="btn btn-success">确定</button>
					<button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
				</div>
				</div>
			</div>
		</div>
	</div>
</div>
	
<script>
	//global cache.
	var selectedCols = [];
	var selectedNames = [];
	var selectMode = 'radio';
	var unitTable;
	$(function() {
		/* WebUtils.bindSelect('s_unitStatus', 'valid_type'); */
		//初始化列表
		var initparams3 = {
			"ajax" : {
				"url" : "${_CONTEXT_PATH}/sys/unitcommon/listUnit",
				"type" : "POST",
				"data" : function(d) {
					var orders = d.order;
					for (var i = 0; i < orders.length; i++) {
						d["norder[" + i + "][column]"] = d.columns[orders[i]['column']].data;
						d["norder[" + i + "][dir]"] = orders[i]['dir'];
					}
					//d.sys_type = "${param.sys_type}";
					d.s_unitId = $('#com_unitId').val();
					d.s_unitName = $('#com_unitName').val();
					d.s_unitStatus = "";
				}
			},
			"columns" : [ {
				"data" : "unitId"
			}, {
				"data" : "unitName"
			} ],

		}
// 		var unitTable = $("#unitTable").DataTable(initparams3);
		//表格行选择
		$('#unitTable tbody').on('click', 'tr', function() {
			//unitTable.$('tr.selected').removeClass('selected');
			if(selectMode == 'radio'){
				unitTable.$('tr.selected').toggleClass('selected');
				selectedCols = [];
				selectedNames = [];
				selectedCols.push($(this).find("td:first").text());
				selectedNames.push($(this).find("td:nth-child(2)").text());
			}else{
				if ($(this).hasClass("selected")) {
					//delete
					var i = selectedCols.indexOf($(this).find("td:first").text());
					if (i != -1) {
						selectedCols.splice(i, 1);
						selectedNames.splice(i, 1);
					}
				} else {
					selectedCols.push($(this).find("td:first").text());
					selectedNames.push($(this).find("td:nth-child(2)").text());
				}
			}
// 			$(this).toggleClass('selected');
		});

		//根据监听选择已选的行
		$('#unitTable').on('draw.dt', function() {
			$('#unitTable tbody tr').each(function() {
				var i = selectedCols.indexOf($(this).find("td:first").text());
				if (i != -1) {
					$(this).toggleClass('selected');
				}
			});
		});

		$('#unitFilter_button').click(function() {
			
		    //$("#unitTable").DataTable(initparams3);
			unitTable.draw();
// 			unitTable.destroy();
			$("#comm_selectUnit .panel-default").toggleClass('collapsed');
		});

		$('#comm_selectUnit').on('show.bs.modal', function(event) {
			//设置详细对话框内容
			var button = $(event.relatedTarget);
			var modal = $(this);
			modal.find('#unitInputId').val(button.attr('id'));
			selectMode = button.attr('selectMode');
			if(button.attr('selectedIds') != undefined
					&& button.attr('selectedNames') != undefined){
				selectedCols = button.attr('selectedIds').split(",");
				var selectedNames = button.attr('selectedNames').split(",");
			}
			
			$('#s_unitId').val('');
			$('#s_unitName').val('');
			$("#comm_selectUnit .panel-default").addClass('collapsed');
		});
		
		$('#comm_selectUnit').on('shown.bs.modal', function(event) {
		
			if (!unitTable) {
				var params3 = $.extend({},WebUtils.settings, initparams3);
				unitTable = $("#unitTable").DataTable(params3);
			    WebUtils.initColumnHider(unitTable);
			} else {
				unitTable.draw();
			}
		});

		$('#comm_selectUnit').on('hidden.bs.modal', function(event) {
			if ($("#detail").attr('aria-hidden') == 'false') {
				$('#comm_selectUnit').parent().addClass("modal-open");
			}
		});

		$('#selectUnit').on('click', function() {
			var strs = [];
			var unitNames = "";
			var unitIds = "";
			$.each(selectedCols,function(i, d) {
				if (i != selectedCols.length - 1) {
					unitIds += d + ",";
				} else {
					unitIds += d;
				}
			});
			$.each(selectedNames,function(i, d) {
				if (i != selectedNames.length - 1) {
					unitNames += d + ",";
				} else {
					unitNames += d;
				}
			});
			strs.push(unitNames);
			strs.push(unitIds);
			var outParams = eval('(' + $("#unitInputId").val() + ')');
			var k = 0;
			for ( var outParamKey in outParams) {
				/* var outParamVal = outParams[outParamKey];
				outParamVal = filterStr(outParamVal);
				alert(selectUnit[outParamVal]);
				console.log(selectUnit);
				$("#" + outParamKey).val(selectUnit[outParamVal]); */
				if (k < 2) {
					$("#" + outParamKey).val(strs[k]);
				}
				k++;
			}
			$('#comm_selectUnit').modal('hide');
		});
	});
	function filterStr(s) {
		var retStr = '';
		if (s != '') {
			retStr = (s + "").replace(new RegExp("<", "g"), "&lt;").replace(
					new RegExp(">", "g"), "&gt;");
		}
		return retStr;
	}
</script>