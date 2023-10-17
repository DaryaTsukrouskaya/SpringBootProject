<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Корзина</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
            <a class="navbar-brand">Оформление заказа</a>
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
                        <a class="nav-link" href="/profile">Профиль</a>
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
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="cart" value="${cart}"/>
<c:forEach items="${cart.getProducts()}" var="product">
    <div class="card mb-3 w-50 mx-auto" style="max-width: 680px;margin:10px;">
        <div class="row g-0">
            <div class="col-md-4">
                <img src="${contextPath}/images/${product.getImagePath()}" class="img-fluid rounded-start"
                     alt="Card image">
            </div>
            <div class="col-md-8">
                <div class="card-body">
                    <h5 class="card-title">${product.getName()}</h5>
                    <p class="card-text">${product.getDescription()}</p>
                    <p class="card-text">Цена: <fmt:formatNumber value="${product.getPrice()}" type="currency"/></p>
                </div>
            </div>
        </div>
    </div>
</c:forEach>
<form method="post" action="/cart/createOrder">
    <div class="form-group form-group w-50 mx-auto">
        <label for="inputAddress" text-align="justify">Ведите адрес для заказа</label>
        <input type="text" class="form-control" id="inputAddress" placeholder="Город,улица,дом,квартира"
               name="address">
        <span>${state}</span><br>
    </div>
    <div class="float-end">
        <p class="w-50 mx-auto align-items-center">
            <span>Итоговая сумма заказа: </span>
            <span class="lead fw-normal"> <fmt:formatNumber value="${cart.getTotalPrice()}" type="currency"/></span>
        </p>
    </div>
    <div class="text-center">
        <button class="btn btn-dark btn-lg" type="submit" style="margin: 10px">Оформить заказ</button>
    </div>
</form>
</body>
</html>

