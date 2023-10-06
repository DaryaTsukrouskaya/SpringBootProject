<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>${category.getName()}</title>
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
            <a class="navbar-brand">${category.getName()}</a>
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
<div class="dropdown">
    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenu" data-toggle="dropdown"
            aria-haspopup="true" aria-expanded="false" style="margin: 15px">
        Размер страницы
    </button>
    <div class="dropdown-menu dropdown-menu-right" aria-labelledby="dropdownMenu">
        <a class="dropdown-item" href="/category/changeSize/${category.getId()}/1">1</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="/category/changeSize/${category.getId()}/2">2</a>
        <div class="dropdown-divider"></div>
        <a class="dropdown-item" href="/category/changeSize/${category.getId()}/3">3</a>
    </div>
</div>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<div class="row">
    <c:forEach items="${category.getProducts()}" var="product">
        <div class="col d-flex justify-content-center">
            <a href="/product/${product.getId()}" style="text-decoration:none;color:inherit">
                <div class="card" style="width: 19rem; margin: 20px">
                    <img class="card-img-top"
                         src="${contextPath}/images/${product.getImagePath()}" alt="Card image">
                    <div class="card-body" style="text-align: center">
                        <h5 class="card-title"> ${product.getName()}</h5>
                        <p class="card-text">${product.getDescription()}</p>
                        <p class="card-text">Цена: <fmt:formatNumber value="${product.getPrice()}"
                                                                     type="currency"/><br>
                    </div>
                </div>
            </a>
        </div>
    </c:forEach>
</div>
<nav>
    <ul class="pagination justify-content-center" style="margin: 15px">
        <li class="page-item"><a class="page-link"
                                 href="/category/pagination/${category.getId()}/${paginationParams.getPageNumber()-1}">Назад</a>
        </li>
        <li class="page-item"><a class="page-link"
                                 href="/category/pagination/${category.getId()}/0">1</a>
        </li>
        <li class="page-item"><a class="page-link"
                                 href="/category/pagination/${category.getId()}/1">2</a>
        </li>
        <li class="page-item"><a class="page-link"
                                 href="/category/pagination/${category.getId()}/2">3</a></li>
        <li class="page-item"><a class="page-link"
                                 href="/category/pagination/${category.getId()}/${paginationParams.getPageNumber()+1}">Вперед</a>
        </li>
    </ul>
</nav>
<br>
<br>
<form method="POST" action="/category/loadFromFile/${category.getId()}" enctype="multipart/form-data"
      class="file-import">
    <label for="file-upload" class="custom-file-upload"
           style="padding: 15px;margin: 0px 0px 15px 15px;border: 1px solid #ccc">
        <input id="file-upload" name="file" type="file" class="title" accept=".csv">
        <button type="submit" class="btn btn-dark">Импортировать продукты категории</button>
    </label>
</form>
<form method="POST" action="/category/loadCsvFile/${category.getId()}">
    <button type="submit" class="btn btn-dark" style="margin: 15px">Экспортировать продукты категории</button>
</form>
</body>
</html>
