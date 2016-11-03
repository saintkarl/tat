<%@ include file="/common/taglibs.jsp" %>
<c:url var="url" value="/admin/tip/edit.html"/>
<c:url var="backUrl" value="/admin/tip/list.html"/>
<head>
    <title><fmt:message key="edit.tip.title"/></title>
</head>

<div class="breadcrumbs ace-save-state" id="breadcrumbs">
    <ul class="breadcrumb">
        <li>
            <i class="ace-icon fa fa-home home-icon"></i>
            <a href="#"><fmt:message key="label.home"/></a>
        </li>
        <li>
            <a href="#"><fmt:message key="label.tip.title"/></a>
        </li>
        <li class="active"><fmt:message key="edit.tip.title"/></li>
    </ul><!-- /.breadcrumb -->
</div>





    <div class="page-content">
        <div class="page-header">
            <h1><fmt:message key="edit.tip.title"/> </h1>
        </div><!-- /.page-header -->
        <div class="row">
            <div class="col-xs-12">

                <form:form commandName="item" action="${formUrl}" method="post" id="itemForm" class="form-horizontal" novalidate="novalidate">

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.title"/></label>

                        <div class="col-sm-9">
                            <input type="text" class="col-xs-10 col-sm-5">
                        </div>
                    </div>


                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.description"/></label>

                        <div class="col-sm-9">
                            <textarea type="text" class="col-xs-10 col-sm-5">

                            </textarea>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-3 control-label no-padding-right"><fmt:message key="label.source"/></label>

                        <div class="col-sm-9">
                            <input type="text" class="col-xs-10 col-sm-5">
                        </div>
                    </div>


                    <h4 class="header green clearfix"><fmt:message key="label.content"/></h4>
                    <textarea class="wysiwyg-editor" id="tip-content-editor"></textarea>
                    <div class="hr hr-double dotted"></div>


                    <form:hidden path="pojo.tipId" />
                    <form:hidden path="crudaction" value="insert-update"/>
                </form:form>

            </div>
        </div>
    </div>


<!-- inline scripts related to this page -->
<script type="text/javascript">
    $(function () {
        CKEDITOR.replace('tip-content-editor');
    });
</script>

