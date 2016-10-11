<%@page trimDirectiveWhitespaces="true"%>
<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="login.title"/></title>
</head>

<div class="login-container">
    <div class="row">
        <div class="col-md-12">
            <div class="text-center m-b-md">
                <h3><fmt:message key="please.login"/></h3>
                <small><fmt:message key="application.welcome"/></small>

            </div>
            <div class="hpanel forum-box">
                <div class="panel-body">
                    <form name="loginForm" action="<c:url value="/j_security_check"/>" method="post">
                        <div class="form-group">
                            <label class="control-label" for="username">Username</label>
                            <input type="text" placeholder="" title="Please enter you username" required="" value="" name="j_username" id="username" class="form-control">
                            <span class="help-block small">Your unique username to app</span>

                        </div>
                        <div class="form-group">
                            <label class="control-label" for="password">Password</label>
                            <input type="password" title="Please enter your password" placeholder="" required="" value="" name="j_password" id="password" class="form-control">
                            <span class="help-block small">Your strong password</span>

                        </div>

                        <fmt:message var="login" key="login"/>
                        <input type="submit" name="buttonLogin" value="${login}" class="btn btn-success btn-block" id="submit"/>
                        <span style="color:red;">
                            <c:choose>
                                <c:when test="${param.error == 1}">
                                    <fmt:message key="login.error.passwordmissedmatch"/>
                                </c:when>
                                <c:when test="${param.error == 2}">
                                    <fmt:message key="login.error.sessionexpired"/>
                                </c:when>
                            </c:choose>
                        </span>

                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 text-center">
            <fmt:message key="copyright.text"/>
        </div>
    </div>
</div>


<script type="text/javascript">
    $(document).ready(function(){
        $('#submit').click(function(){
            if($('#username').val() == '' || $('#password').val() == '') {
                alert('<fmt:message key="login.error.empty.username.or.password"/>');
                return false;
            }
            return true;
        });
    });
</script>