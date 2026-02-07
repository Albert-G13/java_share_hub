<#import "spring.ftl" as spring>
<#macro layout>
    <!doctype html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <#if _csrf??>
            <meta name="_csrf" content="${_csrf.token}"/>
            <meta name="_csrf_header" content="${_csrf.headerName}"/>
        </#if>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <title>JavaShareHub</title>
    </head>
    <body class="d-flex flex-column min-vh-100 bg-light">

    <nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4 shadow-sm">
        <div class="container">
            <a class="navbar-brand fw-bold" href="/">JavaShareHub</a>

            <div class="d-flex align-items-center">
                <#if isAuth?? && isAuth>
                    <span class="text-light me-3 small">${username}</span>
                    <a class="btn btn-outline-light btn-sm me-3" href="/profile">Мой профиль</a>
                    <form action="/auth/logout" method="post" class="m-0 d-inline">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}">
                        </#if>
                        <button class="btn btn-danger btn-sm" type="submit">Выйти</button>
                    </form>
                <#else>
                    <a class="btn btn-success btn-sm me-2" href="/auth/login">Войти</a>
                    <a class="btn btn-primary btn-sm" href="/auth/register">Регистрация</a>
                </#if>
            </div>
        </div>
    </nav>

    <main class="container py-4">
        <#nested spring>
    </main>

    <footer class="footer py-3 mt-auto bg-dark border-top">
        <div class="container text-center">
            <span class="text-white">© 2026 JavaShareHub — Твой надежный файлообменник</span>
        </div>
    </footer>
    </body>
    </html>
</#macro>