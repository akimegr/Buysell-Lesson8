<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="/static/css/style.css">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
          integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
          crossorigin="anonymous">
</head>
<body>
<#--<div class="collapse mb-3" id="collapseExample" style="">-->
<form action="/product/franchiseCreate" method="post" >
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    <button type="submit" style="width: 100%" class="btn btn-dark">Добавить</button>
</form>
<a href="/" style="width: 100%" class="btn btn-dark">Выйти</a>
<hr>
<div class="card-columns">
    <#list products as product>
        <a href="${product.link}" class="product-link">
            <div class="card text-white bg-dark my-3 product">

                <img src="/static/images/avto.jpg"
                     style="border:2px solid black; border-radius: 20px" height="120">
                <div class="card-body">
                    <h5 class="card-title">${product.title}</h5>
                    <p class="card-text">${product.price} ₽</p>
                </div>
                <div class="card-footer">
                    ${product.city}
                </div>
            </div>
        </a>
        <form action="/product/franchiseDelete/${product.id}" method="post" >
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit" style="width: 20%" class="btn btn-dark">Удалить</button>
        </form>
        <form action="/products/franchiseAllows/${product.id}" method="post" >
            <input type="hidden" value="${_csrf.token}" name="_csrf">
            <button type="submit" style="width: 20%" class="btn btn-dark">Показать</button>
        </form>

    <#else>
        <h1 style="text-align: center">Не найдено</h1>
    </#list>
</div>
</div>
</body>
</html>