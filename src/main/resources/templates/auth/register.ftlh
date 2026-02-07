<#import "../layout.ftlh" as main>
<@main.layout ; spring>
    <div class="row justify-content-center">
        <div class="col-md-6 col-lg-4">
            <div class="card shadow-sm">
                <div class="card-header bg-dark text-white text-center">
                    <h4 class="mb-0">Регистрация</h4>
                </div>
                <div class="card-body">
                    <form action="/auth/register" method="post">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}">
                        </#if>

                        <div class="mb-3">
                            <label class="form-label">Ваше имя</label>
                            <@spring.formInput "user.name" 'type="text" name="name" class="form-control" placeholder="Иван Иванов"'/>
                            <@spring.showErrors "<br>" "error text-danger"/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <@spring.formInput "user.email" 'type="email" name="email" class="form-control" placeholder="example@mail.com" required'/>
                            <@spring.showErrors "<br>" "error text-danger"/>
                        </div>

                        <div class="mb-3">
                            <label class="form-label">Пароль</label>
                            <@spring.formInput "user.password" 'type="password" name="password" class="form-control" placeholder="••••••••" '/>
                            <@spring.showErrors "<br>" "error text-danger"/>
                        </div>

                        <#if error??>
                            <div class="alert alert-danger py-2 small">${error}</div>
                        </#if>

                        <button type="submit" class="btn btn-primary w-100">Создать аккаунт</button>
                    </form>
                </div>
                <div class="card-footer text-center py-3">
                    <span class="text-muted small">Уже есть аккаунт?</span>
                    <a href="/auth/login" class="small text-decoration-none">Войти</a>
                </div>
            </div>
        </div>
    </div>
</@main.layout>