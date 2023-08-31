<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Регистрация</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="script.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-8 offset-md-4">
            <h2>Регистрация</h2>
            <p>Заполните форму для регистрации</p>
            <form id="registrationForm" method="post" action="/register" onsubmit="return validateForm()">
                <input type="hidden" name="command" value="register"/>
                <div class="form-group">
                    <label for="name">Имя: </label>
                    <input type="text" class="form-control w-25" id="name" placeholder="Имя"
                           name="name"
                           required>
                    <div class="invalid-feedback">Введите имя!</div>
                </div>
                <div class="form-group">
                    <label for="surname">Фамилия:</label>
                    <input type="text" class="form-control w-25" id="surname" placeholder="Фамилия"
                           name="surname"
                           required>
                    <div class="invalid-feedback">Введите фамилию!</div>
                </div>
                <div class="form-group">
                    <label>Дата рождения:</label>
                    <input type="text" class="form-control w-25" name="birthDate" id="birthDate"
                           placeholder="Дата рождения" onfocus="(this.type='date')">
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <input type="text" class="form-control w-25" id="email" placeholder="Email"
                           name="email"
                           required oninput="validateEmail()">
                    <div class="invalid-feedback">Введите email!</div>
                    <span id="validationErr" style="display: none">*Неверный формат email адреса!</span>
                </div>
                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input type="text" class="form-control w-25" id="password" placeholder="Введите пароль"
                           name="password"
                           required>
                    <div class="invalid-feedback">Введите пароль!</div>
                </div>
                <div class="form-group">
                    <label for="repeatPassword">Повторите пароль:</label>
                    <input type="text" class="form-control w-25" id="repeatPassword" placeholder="Повторите пароль"
                           name="repeatPassword"
                           required oninput="validateRepeatPass()">
                    <span id="matchingError" style="display: none">*Пароли не совпадают!</span>
                    <div class="invalid-feedback">Повторите пароль!</div>
                </div>
                <a href="/register">
                    <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
                </a>
                <br>
                <span>${state}</span>
            </form>
        </div>
    </div>
</div>
</body>
</html>
