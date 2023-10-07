<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Поиск</title>
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
            <a class="navbar-brand">Поисковая страница</a>
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
                </ul>
            </div>
            <form method="post" action="/search/applyFilter">
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
<div class="row">
    <div class="col-lg-6 col-md-6">
        <c:forEach items="${products}" var="product">
            <div class="card mb-3" style="max-width: 540px;margin:10px;">
                <div class="row g-0">
                    <div class="col-md-4">
                        <img src="${contextPath}/images/${product.getImagePath()}" class="img-fluid rounded-start"
                             alt="Card image">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title">${product.getName()}</h5>
                            <p class="card-text">${product.getDescription()}</p>
                            <p class="card-text">Цена: <fmt:formatNumber value="${product.getPrice()}"
                                                                         type="currency"/></p>
                            <a href="/product/${product.getId()}"
                               class="btn btn-dark btn-lg">Перейти</a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <nav>
            <ul class="pagination" style="margin: 15px">
                <li class="page-item"><a class="page-link"
                                         href="/search/pagination/${paginationParams.getPageNumber()-1}">Назад</a></li>
                <li class="page-item"><a class="page-link" href="/search/pagination/0">1</a></li>
                <li class="page-item"><a class="page-link" href="/search/pagination/1">2</a></li>
                <li class="page-item"><a class="page-link" href="/search/pagination/2">3</a></li>
                <li class="page-item"><a class="page-link"
                                         href="/search/pagination/${paginationParams.getPageNumber()+1}">Вперед</a>
                </li>
            </ul>
        </nav>
    </div>
    <div class="col-lg-6 col-md-6">
        <label><h3>Фильтр</h3></label>
        <form method="post" action="/search/applyFilter">
            <select id="categoryName" name="categoryName">
                <c:forEach items="${categories}" var="category">
                    <option name="category" <c:if test="${searchParams.getCategoryName().equals(category.getName())}">selected</c:if> value="${category.getName()}">
                            ${category.getName()}
                    </option>
                </c:forEach>
            </select>
            <div>Цена</div>
            <input id="priceFrom" name="priceFrom" type="text" placeholder="Цена от">
            <input id="priceTo" name="priceTo" type="text" placeholder="Цена до">
            <button type="submit" class="btn btn-dark btn-lg">Применить</button>
        </form>
    </div>
</div>
</body>
</html>
