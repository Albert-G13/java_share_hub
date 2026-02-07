<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-4">
            <div class="card shadow-sm">
                <div class="card-header bg-dark text-white text-center">
                    <h4 class="mb-0">Вход в систему</h4>
                </div>
                <div class="card-body">
                    <form action="/auth/login" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}">
                        </#if>

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="username" class="form-control" required autofocus>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Пароль</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>

                        <#if RequestParameters.error??>
                            <div class="alert alert-danger py-2 small">Неверный email или пароль</div>
                        </#if>

                        <button type="submit" class="btn btn-primary w-100">Войти</button>
                    </form>
                </div>
                <div class="card-footer text-center py-3">
                    <span class="text-muted small">Впервые у нас?</span>
                    <a href="/auth/register" class="small text-decoration-none">Зарегистрироваться</a>
                </div>
            </div>
        </div>
    </div>
</@main.layout>