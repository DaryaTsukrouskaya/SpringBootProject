<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Авторизация</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-8 offset-md-4">
            <h2>Вход</h2>
            <p>Заполните форму для входа</p>
            <form method="post" action="/login" class="needs-validation" novalidate>
                <input type="hidden" name="command" value="sign-in"/>
                <div class="form-group">
                    <label for="email">Логин:</label>
                    <input type="text" class="form-control w-25" id="email" placeholder="Введите логин"
                           name="email"
                           required>
                    <div class="invalid-feedback">Введите логин!</div>
                </div>
                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input type="text" class="form-control w-25" id="password" placeholder="Введите пароль"
                           name="password"
                           required>
                    <div class="invalid-feedback">Введите пароль!</div>
                </div>
                <button id="loginBtn" type="submit" class="btn btn-primary w-25">Войти</button>
                <div class="form-group">
                    <span>${state}</span><br>
                    <a href="/register" class="form-control w-25">Зарегистрироваться</a>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    (function () {
        'use strict';
        window.addEventListener('load', function () {
            var forms = document.getElementsByClassName('needs-validation');
            var validation = Array.prototype.filter.call(forms, function (form) {
                form.addEventListener('submit', function (event) {
                    if (form.checkValidity() === false) {
                        event.preventDefault();
                        event.stopPropagation();
                    }
                    form.classList.add('was-validated');
                }, false);
            });
        }, false);
    })();

    document.getElementById('loginBtn').disabled = true;

    document.getElementById('email').addEventListener('keyup', e => {
        if (e.target.value === "") {
            document.getElementById('loginBtn').disabled = true;
        } else {
            document.getElementById('loginBtn').disabled = false;
        }
    });

    document.getElementById('password').addEventListener('keyup', e => {
        if (e.target.value === "") {
            document.getElementById('loginBtn').disabled = true;
        } else {
            document.getElementById('loginBtn').disabled = false;
        }
    });
</script>
</body>
</html>

