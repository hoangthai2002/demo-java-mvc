<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Sơn Spec" />
                <meta name="author" content="Sơn Spec" />
                <title>Create User - Sơn Spec</title>
                <link href="/css/styles.css" rel="stylesheet" />

                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    }); 
                </script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Manage Users</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Dashboard</a></li>
                                    <li class="breadcrumb-item"><a href="/admin/user">User</a></li>
                                    <li class="breadcrumb-item active">User Create</li>
                                </ol>
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3>Create a user</h3>
                                            <hr />
                                            <form:form method="post" action="/admin/user/create"
                                                modelAttribute="newUser" class="row" enctype="multipart/form-data">
                                                <c:set var="ErrorE">
                                                    <form:errors path="email" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="ErrorP">
                                                    <form:errors path="password" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="ErrorPh">
                                                    <form:errors path="phone" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="ErrorF">
                                                    <form:errors path="fullName" cssClass="invalid-feedback" />
                                                </c:set>
                                                <c:set var="ErrorA">
                                                    <form:errors path="address" cssClass="invalid-feedback" />
                                                </c:set>



                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Email</label>
                                                    <form:input type="email"
                                                        class="form-control  ${not empty ErrorE ? 'is-invalid ' : ''}"
                                                        path="email" />
                                                    ${ErrorE}
                                                </div>

                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Password</label>
                                                    <form:input type="password"
                                                        class="form-control ${not empty ErrorP ? 'is-invalid': ''}"
                                                        path="password" />
                                                    ${ErrorP}
                                                </div>

                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Phone number</label>
                                                    <form:input type="text" class="form-control ${not empty ErrorPh ? 'is-invalid' : ''
                                                        }" path="phone" />
                                                    ${ErrorPh}
                                                </div>


                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Full Name</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty ErrorF ? 'is-invalid': ''}"
                                                        path="fullName" />
                                                    ${ErrorF}
                                                </div>

                                                <div class="mb-3 col-12">
                                                    <label class="form-label">Address</label>
                                                    <form:input type="text"
                                                        class="form-control ${not empty ErrorA ? 'is-invalid': ''}"
                                                        path="address" />
                                                    ${ErrorA}
                                                </div>


                                                <!--//////////////////////////////////////////////////////-->
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label class="form-label">Role</label>
                                                    <form:select class="form-select" path="role.name">
                                                        <form:option value="ADMIN">ADMIN</form:option>
                                                        <form:option value="USER">USER</form:option>
                                                    </form:select>
                                                </div>
                                                <div class="mb-3 col-12 col-md-6">
                                                    <label for="avatarFile" class="form-label">Avatar</label>
                                                    <input class="form-control" type="file" id="avatarFile"
                                                        accept=".png, .jpg, .jpeg" name="File" />
                                                </div>
                                                <div class="col-12 mb-3  col-md-6">
                                                    <img style="max-height: 250px; display: none;" alt="avatar preview"
                                                        id="avatarPreview" />
                                                </div>
                                                <div class="col-12 mb-5">
                                                    <button type="submit" class="btn btn-success">Create</button>
                                                    <a href="/admin/user" class="btn btn-success">Back</a>
                                                </div>
                                            </form:form>

                                        </div>

                                    </div>
                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>

            </body>

            </html>