<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>Кабинет</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand">Кабинет</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse"
                    data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false"
                    aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/home">Главная</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/cart">Корзина</a>
                    </li>
                    <sec:authorize access="isAuthenticated()">
                        <li class="nav-item">
                            <a class="nav-link" href="/logout">Выйти</a>
                        </li>
                    </sec:authorize>
                    <sec:authorize access="!isAuthenticated()">
                        <li class="nav-item">
                            <a class="nav-link" href="/login">Войти</a>
                        </li>
                    </sec:authorize>
                </ul>
            </div>
            <form method="post" action="/search">
                <div class="input-group">
                    <input type="search" id="keyWords" class="form-control" name="keyWords" placeholder="Поиск"
                           minlength="3"/>
                    <button type="submit" class="btn btn-primary">
                        <i class="fa fa-search"></i>
                    </button>
                    </a>
                </div>
            </form>
        </div>
    </nav>
</header>
<section>
    <c:set var="user" value="${user}"/>
    <div class="col">
        <div class="card mb-3">
            <div class="card-body">
                <div class="row">
                    <div class="col-sm-3">
                        <h5 class="mb-0">Личные данные</h5>
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Имя</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        ${user.getName()}
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Фамилия</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        ${user.getSurname()}
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Дата рождения</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        ${user.getBirthDate()}
                    </div>
                </div>
                <hr>
                <div class="row">
                    <div class="col-sm-3">
                        <h6 class="mb-0">Email</h6>
                    </div>
                    <div class="col-sm-9 text-secondary">
                        ${user.getEmail()}
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<div class="dropdown">
    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false" style="margin: 15px">
        Размер страницы
    </button>
    <div class="dropdown-menu dropdown-menu-left" aria-labelledby="dropdownMenu">
        <a class="dropdown-item" href="/profile/changeSize/1">1</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="/profile/changeSize/2">2</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="/profile/changeSize/3">3</a>
    </div>
</div>
<section>
    <h5 class="mb-0" style="padding: 20px">История заказов</h5>
    <c:forEach items="${userOrders}" var="order">
        <span style="padding: 20px">Номер заказа-${order.getId()} / Дата заказа-${order.getOrderDate()}</span>
        <div class="col d-flex justify-content-start">
            <c:forEach items="${order.getProducts()}" var="product">
                <div class="card mb-3" style="max-width: 540px;margin: 20px">
                    <div class="row g-0">
                        <div class="col-md-4">
                            <img src="${contextPath}/images/${product.getImagePath()}"
                                 class="img-fluid rounded-start" alt="Card image">
                        </div>
                        <div class="col-md-8">
                            <div class="card-body">
                                <h5 class="card-title">${product.getName()}</h5>
                                <p class="card-text">${product.getDescription()}</p>
                                <p class="card-text">Цена:
                                        <fmt:formatNumber value="${product.getPrice()}"
                                                          type="currency"/>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
        </a>
        </div>
    </c:forEach>
    <nav>
        <ul class="pagination justify-content-center" style="margin: 15px">
            <li class="page-item"><a class="page-link"
                                     href="/profile/pagination/${paginationParams.getPageNumber()-1}">Назад</a>
            </li>
            <li class="page-item"><a class="page-link"
                                     href="/profile/pagination/0">1</a>
            </li>
            <li class="page-item"><a class="page-link"
                                     href="/profile/pagination/1">2</a>
            </li>
            <li class="page-item"><a class="page-link"
                                     href="/profile/pagination/2">3</a></li>
            <li class="page-item"><a class="page-link"
                                     href="/profile/pagination/${paginationParams.getPageNumber()+1}">Вперед</a>
            </li>
        </ul>
    </nav>
</section>
</body>
</html>