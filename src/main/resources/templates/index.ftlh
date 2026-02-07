<#import "./layout.ftlh" as main>
<@main.layout>
    <div class="container mt-5">
        <h2 class="mb-4">Публичные файлы</h2>

        <div class="card mb-4 shadow-sm">
            <div class="card-body">
                <form action="/" method="get" class="row g-3 align-items-end">
                    <#if _csrf??>
                        <input type="hidden" name="${(_csrf.parameterName)!'csrf-param-name'}" value="${(_csrf.token)!'csrf-token'}">
                    </#if>
                    <div class="col-md-4">
                        <label class="form-label text-muted small">Категория</label>
                        <select name="categoryId" class="form-select">
                            <option value="">Все категории</option>
                            <#list categories as cat>
                                <option value="${cat.id}" ${(selectedCategory?? && selectedCategory?string == cat.id?string)?string('selected', '')}>
                                    ${cat.category}
                                </option>
                            </#list>
                        </select>
                    </div>
                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary w-100">Фильтровать</button>
                    </div>
                    <div class="col-md-2">
                        <a href="/" class="btn btn-outline-secondary w-100">Сбросить</a>
                    </div>
                </form>
            </div>
        </div>

        <div class="row row-cols-1 row-cols-md-3 g-4">
            <#list files as file>
                <div class="col">
                    <div class="card h-100 border-0 shadow-sm">
                        <div class="card-body">
                            <div class="d-flex align-items-center mb-3">
                                <div class="bg-light p-3 rounded-3 me-3">
                                    📁
                                </div>
                                <h6 class="card-title mb-0 text-truncate" title="${file.name}">${file.name}</h6>
                            </div>
                        </div>
                        <div class="card-footer bg-white border-0 pb-3">
                            <a href="/files/download-public?id=${file.id}" class="btn btn-light w-100 text-primary fw-bold">
                                Скачать
                            </a>
                        </div>
                    </div>
                </div>
            <#else>
                <div class="col-12 text-center py-5">
                    <h5 class="text-muted">Ничего не найдено</h5>
                </div>
            </#list>
        </div>
    </div>
</@main.layout>