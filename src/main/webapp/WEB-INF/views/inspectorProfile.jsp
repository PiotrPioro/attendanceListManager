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

    <title>Inspektor Kontrakty</title>

    <!-- Custom fonts for this template-->
    <link href="<c:url value="/vendor/fontawesome-free/css/all.min.css"/>" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="<c:url value="/css/sb-admin-2.css"/>" rel="stylesheet">

</head>

<body id="page-top">

<!-- Page Wrapper -->
<div id="wrapper">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="">
            <div class="sidebar-brand-text mx-3">Witaj ${inspector.firstName}</div>
        </a>

        <!-- Divider -->
        <hr class="sidebar-divider my-0">

        <!-- Nav Item - Dashboard -->
        <li class="nav-item active">
            <a class="nav-link" href="/attendanceListManager/logout">
                <i class="fas fa-fw fa-tachometer-alt"></i>
                <span>Wyloguj</span></a>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider">

        <!-- Heading -->
        <div class="sidebar-heading">
            Twoje konto
        </div>

        <!-- Nav Item - Edit -->
        <li class="nav-item">
            <a class="nav-link" href="/inspector/editInspector">
                <span>Edytuj profil</span>
            </a>
        </li>

        <!-- Nav Item - Password -->
        <li class="nav-item">
            <a class="nav-link" href="/inspector/editPassword">
                <span>Zmie?? has??o</span>
            </a>
        </li>

        <!-- Nav Item - Show -->
        <li class="nav-item">
            <a class="nav-link" href="/inspector/showInspector">
                <span>Szczeg????y profilu</span>
            </a>
        </li>

        <!-- Nav Item - Delete -->
        <li class="nav-item">
            <a class="nav-link" href="/inspector/deleteInspector">
                <span>Usu?? profil</span></a>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider">

        <c:if test="${admin != null}">
        <!-- Heading -->
        <div class="sidebar-heading">
            Widok administratora
        </div>

        <!-- Nav Item - addContract -->
        <li class="nav-item">
            <a class="nav-link" href="/contractAdmin/contractAdminHome">
                <span>Strona g????wna</span>
            </a>
        </li>

        <!-- Divider -->
        <hr class="sidebar-divider">
        </c:if>

        <!-- Sidebar Toggler (Sidebar) -->
        <div class="text-center d-none d-md-inline">
            <button class="rounded-circle border-0" id="sidebarToggle"></button>
        </div>

    </ul>
    <!-- End of Sidebar -->

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">

        <!-- Main Content -->
        <div id="content">

            <!-- Topbar -->
            <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                <!-- Sidebar Toggle (Topbar) -->
                <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                    <i class="fa fa-bars"></i>
                </button>

                <!-- Topbar Search -->
                <form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
                    <div class="input-group">
                        <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
                        <div class="input-group-append">
                            <button class="btn btn-primary" type="button">
                                <i class="fas fa-search fa-sm"></i>
                            </button>
                        </div>
                    </div>
                </form>

                <!-- Topbar Navbar -->
                <ul class="navbar-nav ml-auto">

                    <!-- Nav Item - Search Dropdown (Visible Only XS) -->
                    <li class="nav-item dropdown no-arrow d-sm-none">
                        <a class="nav-link dropdown-toggle" href="#" id="searchDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <i class="fas fa-search fa-fw"></i>
                        </a>
                        <!-- Dropdown - Messages -->
                        <div class="dropdown-menu dropdown-menu-right p-3 shadow animated--grow-in" aria-labelledby="searchDropdown">
                            <form class="form-inline mr-auto w-100 navbar-search">
                                <div class="input-group">
                                    <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..." aria-label="Search" aria-describedby="basic-addon2">
                                    <div class="input-group-append">
                                        <button class="btn btn-primary" type="button">
                                            <i class="fas fa-search fa-sm"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </li>
                </ul>

            </nav>
            <!-- End of Topbar -->

            <!-- Begin Page Content -->
            <div class="container-fluid">

                <!-- Page Heading -->
                <div class="d-sm-flex align-items-center justify-content-between mb-4">
                    <h1 class="h3 mb-0 text-gray-800">Kalendarz</h1>
                </div>

                <!-- Content Row -->
                <div class="row">
                    <table cellspacing="0" cellpadding="0" border="1" style="width: 100%;">

                        <h5><a href="/calendar/minusInspectorMonth?monthValue=${monthValue}&year=${year}"><-   </a> ${month.name} ${year}<a href="/calendar/plusInspectorMonth?monthValue=${monthValue}&year=${year}">    -></a></h5>
                        <thead>
                        <tr>
                            <th>Poniedzia??ek</th>
                            <th>Wtorek</th>
                            <th>??roda</th>
                            <th>Czwartek</th>
                            <th>Pi??tek</th>
                            <th>Sobota</th>
                            <th>Niedziela</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <c:forEach items="${monthList}" var="dayOfMonth">

                            <c:if test="${dayOfMonth.weekDay == 2}">
                            <c:if test="${dayOfMonth.monthDay != 1}">
                        </tr>
                        <tr>
                            </c:if>
                            </c:if>
                            <c:choose>
                                <c:when test="${currentDate.dayOfMonth == dayOfMonth.monthDay && currentDate.monthValue == monthValue && currentDate.year == year}">
                                    <td>${dayOfMonth.monthDay}*</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${dayOfMonth.monthDay}</td>
                                </c:otherwise>
                            </c:choose>
                            </c:forEach>
                        </tr>
                        </tbody>
                    </table>

                    <h1 class="h3 mb-0 text-gray-800">
                        <br>Lista twoich kontrakt??w<br>
                        <c:if test="${message != null}">
                            ${message}
                        </c:if>
                    </h1>

                    <table cellspacing="0" cellpadding="0" border="1" style="width: 100%;">
                        <thead>
                            <tr>
                                <th>Nazwa</th>
                                <th>Stanowisko</th>
                                <th>Typ listy obecno??ci</th>
                                <th>Ilo???? dni ${month.nameVariation}</th>
                                <th>${month.nameVariation} zosta??o dni</th>
                                <th>Edytuj list?? obecno??ci</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${contractMap}" var="contract">
                                <tr>
                                    <td>${contract.key.name}</td>
                                    <td>${contract.value.position}</td>
                                    <td>${contract.value.attendanceListType}</td>
                                    <td>
                                        <c:forEach var="daysAmountList" items="${contract.value.listDaysAmount}">
                                            <c:if test="${daysAmountList.monthNumber == monthValue && daysAmountList.year == year}">
                                                ${daysAmountList.amountOfDaysInMonth}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach var="daysAmountList" items="${contract.value.listDaysAmount}">
                                            <c:if test="${daysAmountList.monthNumber == monthValue && daysAmountList.year == year}">
                                                ${daysAmountList.amountOfDaysInMonth - daysAmountList.attendanceList.size()}
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                    <td>
                                        <c:forEach var="daysAmountList" items="${contract.value.listDaysAmount}">
                                            <c:if test="${daysAmountList.monthNumber == monthValue && daysAmountList.year == year}">
                                                <a href="/listAttendance/view?insp=${inspector.id}&conDet=${contract.value.id}&con=${contract.key.id}&dayAmountId=${daysAmountList.id}&monthValue=${monthValue}&year=${year}" class="btn btn-danger rounded-0 text-light m-1">Uzupe??nij list?? obecno??ci</a>
                                            </c:if>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

            </div>
            <!-- /.container-fluid -->

        </div>
        <!-- End of Main Content -->

        <!-- Footer -->
        <footer class="sticky-footer bg-white">
            <div class="container my-auto">
                <div class="copyright text-center my-auto">
                    <span>Copyright &copy; Your Website 2019</span>
                </div>
            </div>
        </footer>
        <!-- End of Footer -->

    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->

<!-- Scroll to Top Button-->
<a class="scroll-to-top rounded" href="#page-top">
    <i class="fas fa-angle-up"></i>
</a>



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