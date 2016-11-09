<%@ include file="/common/taglibs.jsp" %>
<c:url var="formUrl" value="/admin/tip/list.html"/>
<c:url var="addUrl" value="/admin/tip/edit.html"/>
<html>
<head>
    <title><fmt:message key="list.tip.title"/></title>
    <meta name="menu" content="users-manage"/>
    <meta name="gmenu" content="tip"/>
</head>
<body>
<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <i class="ace-icon fa fa-home home-icon"></i>
            <a href="#"><fmt:message key="label.home"/></a>
        </li>
        <li>
            <a href="#"><fmt:message key="label.tip.title"/></a>
        </li>
        <li class="active"><fmt:message key="list.tip.title"/></li>
    </ul><!-- /.breadcrumb -->
</div>

<div class="page-content">
    <c:if test="${not empty messageResponse}">
        <div class="row">
            <div class="col-xs-12">

                <div class="alert alert-${alert} alert-dismissible fade in" role="alert">
                    <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                        ${messageResponse}
                </div>
            </div>
        </div>
    </c:if>

    <div class="page-header">
        <div class="row">
            <h1 class="col-sm-12"><fmt:message key="list.tip.title"/> </h1>
        </div>
    </div><!-- /.page-header -->

    <form:form action="${formUrl}" commandName="items" role="form" id="listForm" cssClass="form-horizontal">
        <div class="row">
            <div class="col-xs-12">
                <div class="widget-box table-filter">
                    <div class="widget-header">
                        <h4 class="widget-title"><fmt:message key="label.searchfilter"/></h4>
                        <div class="widget-toolbar">
                            <a href="#" data-action="collapse">
                                <i class="ace-icon fa fa-chevron-up"></i>
                            </a>
                        </div>
                    </div>
                    <div class="widget-body">
                        <div class="widget-main">
                            <div class="form-horizontal">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><fmt:message key="label.title"/></label>
                                    <div class="col-sm-9">
                                        <form:input path="pojo.title" cssClass="form-control input-sm"/>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="widget-bottom form-actions center">
                            <button id="btnSearch" type="button" class="btn btn-sm btn-success">
                                <fmt:message key="label.apply.search"/>
                                <i class="ace-icon fa fa-arrow-right icon-on-right bigger-110"></i>
                            </button>
                        </div>
                    </div>
                </div>
                <div class="table-btn-controls">
                    <div class="pull-right tableTools-container">
                        <a href="${addUrl}" class="btn btn-success">
                            <i class="ace-icon fa fa-plus"></i>
                            <fmt:message key="label.add"/>
                        </a>
                        <button id="confirmDelete" class="btn btn-danger">
                            <i class="fa fa-trash-o"></i>
                            <fmt:message key="label.delete"/>
                        </button>
                    </div>
                </div>

                <c:if test="${items.totalItems > 0}">
                    <div class="dataTables_wrapper form-inline no-footer">
                        <display:table name="items.listResult" cellspacing="0" cellpadding="0" requestURI="${formUrl}"
                                       partialList="true" sort="external" size="${items.totalItems }" defaultsort="2"
                                       id="tableList" excludedParams="checkList"
                                       pagesize="${items.maxPageItems}" export="false"
                                       class="table table-fcv-ace table-striped table-bordered table-hover dataTable no-footer" style="margin: 3em 0 1.5em;">
                            <display:column headerClass="select-cell" class="select-cell" sortable="false"
                                            title="<label class='pos-rel'><input type='checkbox' class='ace'><span class='lbl'></span></label>">
                                <label class="pos-rel">
                                    <input name="checkList" type="checkbox" class="ace" value="${tableList.tipId}"/>
                                    <span class="lbl"></span>
                                </label>
                            </display:column>
                            <display:column headerClass="text-left" property="title" sortName="title" sortable="true"
                                            titleKey="tip.title"/>
                            <display:column headerClass="col-actions" class="col-actions" titleKey="label.action">
                                <c:url var="editUrl" value="/admin/tip/edit.html">
                                    <c:param name="pojo.tipId" value="${tableList.tipId}"/>
                                </c:url>

                                <a href="${editUrl}" class="btn btn-xs btn-info addEditRole">
                                    <i class="ace-icon fa fa-pencil bigger-120"></i>
                                </a>

                                <button onclick="warningBeforeDelete('${tableList.tipId}');"
                                        type="button" class="btn btn-xs btn-danger">
                                    <i class="ace-icon fa fa-trash-o bigger-120"></i>
                                </button>

                            </display:column>
                            <display:setProperty name="paging.banner.placement" value="bottom"/>
                            <display:setProperty name="paging.banner.item_name" value="tip"/>
                            <display:setProperty name="paging.banner.items_name" value="tips"/>
                        </display:table>
                    </div>
                </c:if>

                <input type="hidden" name="crudaction" id="crudaction"/>
            </div>
        </div>
    </form:form>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $("#btnSearch").click(function () {
            $("#itemForm").submit();
        });

        $(".addEdittip").click(function(){
            var $modal = $('#addModel');
            var tipId = $(this).attr("tipId");
            $modal.load('<c:url value="/ajax/admin/tip/edit.html"/>', {'pojo.tipId' : tipId, '_': new Date().getTime()}, function(){
                $modal.modal();
                registerSubmitPopup();
            });
        });

        function registerSubmitPopup(){

            $("#submit").click(function(){
                $("#addtipFormItem").submit();
            });
            $('#myTab a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
                //if($(e.target).attr('href') == "#home") doSomethingNow();
            })


            $('#checkAllBox').click(function () {
                if ($(this).prop('checked')) {
                    $(this).closest('table').find('input[type="checkbox"]').prop('checked', true);
                } else {
                    $(this).closest('table').find('input[type="checkbox"]').prop('checked', false);
                }
            });

            $("#addtipFormItem").submit(function(e){
                e.preventDefault();

                $("#crudaction").val("insert-update");
                var formData = new FormData($(this)[0]);
                $.ajax({
                    cache: false,
                    type: "POST",
                    dataType: 'html',
                    contentType: false,
                    processData:false,
                    data: formData,
                    url:  "<c:url value="/ajax/admin/tip/edit.html"/>",
                    success: function(res){
                        if(res.trim() == "success"){
                            document.location = "<c:url value="/admin/tip/list.html"/>";
                        }else{
                            $("#addModel").html(res);
                            registerSubmitPopup();
                        }
                    }
                });

                return false;
            });
        }

        $("#confirmDelete").click(function () {
            deleteBySelectedIds({
                title: "<fmt:message key="delete.confirm.message.title"/>",
                text: "<fmt:message key="delete.confirm.message.content"/>",
                cancelButtonText: "<fmt:message key="label.cancel"/>",
                confirmButtonText: "<fmt:message key="label.delete"/>"
            }, function () {
                $('#crudaction').val('delete');
                $("#itemForm").submit();
            });
        });
    });
</script>

</body>
</html>