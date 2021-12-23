<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Edytowanie kontraktu</title>

    <!-- Custom fonts for this template-->
    <link href="<c:url value="/vendor/fontawesome-free/css/all.min.css"/>" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="<c:url value="/css/sb-admin-2.css"/>" rel="stylesheet">

</head>

<body class="bg-gradient-primary">

<div class="container">

    <div class="card o-hidden border-0 shadow-lg my-5">
        <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
                <%--<div class="col-lg-5 d-none d-lg-block bg-register-image"></div>--%>
                <div class="col-lg-7">
                    <div class="p-5">
                        <div class="text-center">
                            <h1 class="h4 text-gray-900 mb-4">Edytuj kontrakt</h1>
                        </div>
                        <form:form method="post" modelAttribute="contract">

                            <form:hidden path="id"/>

                            <input type="hidden" name="contractDetailsList" value="${contractDetailsList}">

                            Nazwa kontraktu:<form:input path="name"/><br>
                            <form:errors path="name"/><br>

                            Rodzaj usługi:<form:input path="typeOfService"/><br>
                            <form:errors path="typeOfService"/><br>

                            Opis usługi:<form:input path="description"/><br>
                            <form:errors path="description"/><br>

                            Wybierz administratora kontraktu:<form:select path="contractAdministrator" itemValue="id" itemLabel="fullName" items="${inspectors}" multiple="true"/><br>
                            <form:errors path="contractAdministrator"/><br>

                            <form:hidden path="inspectorList" itemValue="id" items="${inspectors}" multiple="true"/><br>

                            <form:hidden path="contractDetails" itemValue="id" items="${contractDetailsList}" multiple="true"/><br>

                            <input type="submit" value="Zapisz"><br>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<!-- Bootstrap core JavaScript-->
<script src="<c:url value="/vendor/jquery/jquery.min.js"/>"></script>
<script src="<c:url value="/vendor/bootstrap/js/bootstrap.bundle.min.js"/>"></script>

<!-- Core plugin JavaScript-->
<script src="<c:url value="/vendor/jquery-easing/jquery.easing.min.js"/>"></script>

<!-- Custom scripts for all pages-->
<script src="<c:url value="/js/sb-admin-2.min.js"/>"></script>

<!-- Page level plugins -->
<script src="<c:url value="/vendor/chart.js/Chart.min.js"/>"></script>

<!-- Page level custom scripts -->
<script src="<c:url value="/js/demo/chart-area-demo.js"/>"></script>
<script src="<c:url value="/js/demo/chart-pie-demo.js"/>"></script>

</body>

</html>