<#import "blocks/template.ftlh" as t>
<@t.template user>

        <#if user.getId()??>
            <h3 style="text-align: right">Ваш баланс: </h3>
            <#if user.getDollars()??>user.getDollars()
            <#else>
                0
            </#if>
        </#if>
    <h1 style="text-align: center">Поиск товаров</h1><hr>
    <form method="get" action="/">
        <div class="form-row">
            <div class="form-group col-md-2">
                <label for="searchCity">Город</label>
                <select id="searchCity" class="form-control">
                    <option value="">Город не выбран</option>
                    <option value="Москва">Москва</option>
                    <option value="Красноярск">Красноярск</option>
                    <option value="Казань">Казань</option>
                </select>
            </div>
            <div class="form-group col-md-10">
                <label for="searchWord">Поиск по объявлениям</label>
                <input type="text" name="searchWord" <#if searchWord??>value="${searchWord}"</#if> class="form-control"
                       id="searchWord">
            </div>
            <button style="width: 100%" type="submit" class="btn btn-dark">Поиск</button>
            <#if user.isAdmin()>
                <a class="btn btn-dark mb-3" style="text-align: center; width: 100%"
                   href="/product/franchise">
                    Добавить товары партнёра
                </a><br>
            </#if>
        </div>
    </form>
    <hr>
    <div class="card-columns">
        <#list products as product>
            <#if product.isFranchise>
                <a href="${product.link}" class="product-link">
                    <div class="card text-white bg-dark my-3 product">
                        <#if product.isFranchise>
                            <img src="/static/images/avatar.png"
                                 class="card-img-top">
                        </#if>
                        <#if !product.isFranchise>
                            <img src="/images/${product.previewImageId}"
                                 class="card-img-top">
                        </#if>

                        <div class="card-body">
                            <h5 class="card-title">${product.title}</h5>
                            <p class="card-text">${product.price} ₽</p>
                        </div>
                        <div class="card-footer">
                            <#if product.city??>
                                ${product.city}
                            </#if>
                        </div>
                    </div>
                </a>
            <#else>
                <a href="/product/${product.id}" class="product-link">
                    <div class="card text-white bg-dark my-3 product">
                        <#if product.isFranchise>
                            <img src="/static/images/avatar.png"
                                 class="card-img-top">
                        </#if>
                        <#if !product.isFranchise>
                            <img src="/images/${product.previewImageId}"
                                 class="card-img-top">
                        </#if>

                        <div class="card-body">
                            <h5 class="card-title">${product.title}</h5>
                            <p class="card-text">${product.price} ₽</p>
                        </div>
                        <div class="card-footer">
                            <#if product.city??>
                                ${product.city}
                            </#if>
                        </div>
                    </div>
                </a>
            </#if>
        <#else>
            <h1 style="text-align: center">Не найдено</h1>
        </#list>
    </div>
</@t.template>