<#import "../layout.ftlh" as main>
<@main.layout>
    <div class="row">
        <#if generatedLink??>
            <div class="col-12">
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    <strong>Ссылка создана!</strong><br>
                    Ссылка: <a href="${generatedLink}" class="alert-link">${generatedLink}</a>
                    <a href="/profile" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></a>
                    <p class="mb-0 small text-muted mt-2">Ссылка одноразовая и привязана к этому файлу.</p>
                </div>
            </div>
        </#if>

        <div class="col-md-4">
            <div class="card shadow-sm mb-4">
                <div class="card-header bg-dark text-white">Загрузить файл</div>
                <div class="card-body">
                    <form action="/files/upload" method="post" enctype="multipart/form-data">
                        <#if _csrf??>
                            <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}">
                        </#if>

                        <div class="mb-3">
                            <label class="form-label">Выберите файл</label>
                            <input type="file" name="file" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Категория</label>
                            <select name="categoryId" class="form-select">
                                <#list categories as cat>
                                    <option value="${cat.id}">${cat.category}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Доступ</label>
                            <select name="status" class="form-select">
                                <option value="public">Публичный</option>
                                <option value="private">Приватный</option>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-success w-100">Загрузить</button>
                    </form>
                </div>
            </div>
        </div>

        <div class="col-md-8">
            <div class="card shadow-sm text-dark">
                <div class="card-header bg-white">
                    <h5 class="mb-0">Мои файлы</h5>
                </div>
                <div class="card-body p-0">
                    <table class="table table-hover mb-0">
                        <thead class="table-light">
                        <tr>
                            <th>Имя</th>
                            <th>Статус</th>
                            <th>Скачиваний</th>
                            <th class="text-end">Действие</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list files as file>
                            <tr>
                                <td>${file.name}</td>
                                <td>
                                    <span class="badge ${ (file.status == 'public')?string('bg-info', 'bg-warning text-dark') }">
                                        ${file.status}
                                    </span>
                                </td>
                                <td>${file.downloadCount}</td>
                                <td class="text-end">
                                    <#if file.status == 'public'>
                                        <a href="/files/download-public?id=${file.id}" class="btn btn-sm btn-primary">Скачать</a>
                                    <#else>
                                        <form action="/files/generate-link" method="post" class="d-inline">
                                            <#if _csrf??>
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                            </#if>
                                            <input type="hidden" name="fileId" value="${file.id}">
                                            <button type="submit" class="btn btn-sm btn-success">Сгенерировать ссылку</button>
                                        </form>
                                    </#if>
                                </td>
                            </tr>
                        <#else>
                            <tr><td colspan="4" class="text-center py-4 text-muted">Файлов пока нет</td></tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</@main.layout>