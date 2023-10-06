<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${product.getName()}</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="product" value="${product}"/>
<header>
    <c:set var="contextPath" value="${pageContext.request.contextPath}"/>
    <c:set var="product" value="${product}"/>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand">${product.getName()}</a>
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
                    <li class="nav-item">
                        <a class="nav-link" href="/cart">Корзина</a>
                    </li>
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
<div class="container-fluid">
    <div class="row g-0">
        <div class="col-md-3">
            <img src="${contextPath}/images/${product.getImagePath()}" class="img-fluid rounded-start"
                 alt="Card image">
        </div>
        <div class="col-md-8">
            <div class="card-body">
                <h3 class="card-title">${product.getName()}</h3><br>
                <p class="card-text">${product.getDescription()}</p><br>
                <h4 class="card-text"><small class="text-body-secondary">
                    Цена: <fmt:formatNumber value="${product.getPrice()}"
                                            type="currency"/>
                </small></h4>
                <br>
                <br>
                <a href="/cart/add?product_id=${product.getId()}">
                    <button type="button" class="btn btn-dark btn-lg">Купить</button>
                </a>

            </div>
        </div>
    </div>
</div>
</body>
</html>
