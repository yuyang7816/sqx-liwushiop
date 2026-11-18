<script>
/*
 * 401
 */
import { defineComponent, reactive } from 'vue';
import svg401 from '@/assets/img-list/401.svg';
import { useRoute, useRouter } from 'vue-router';

export default defineComponent({
    setup() {
        const route = useRoute();
        const dataContainer = reactive({
            svg401: svg401,
            path: '',
        });
        /** 初始化 */
        function initData() {
            let params = route.query || {};
            dataContainer.path = params.fullPath || params.path || '';
        }
        initData();
        return {
            dataContainer,
        };
    },
});
</script>

<template>
    <div class="error-401-page">
        <div class="error-401-page__inner">
            <div class="nf-page-hero error-401-page__hero">
                <span class="nf-page-hero__badge">访问受限</span>
                <h2 class="nf-page-hero__title">401 无访问权限</h2>
                <p class="nf-page-hero__desc">您没有权限访问该页面或资源，请联系管理员申请权限</p>
            </div>

            <div class="error-401-page__card">
                <div class="error-401-page__illus">
                    <img :src="dataContainer.svg401" alt="401" />
                </div>
                <div class="error-401-page__info">
                    <div class="error-401-page__path-label">所访问地址</div>
                    <div class="error-401-page__path">{{ dataContainer.path || '—' }}</div>
                    <p class="error-401-page__text">
                        对不起，您正在寻找的页面没有访问权限。尝试检查 URL
                        的错误，然后按浏览器上的刷新按钮或尝试在我们的应用程序中找到其他内容。或者向管理员申请访问权限。
                    </p>
                    <router-link class="error-401-page__btn" to="/main/index">返回首页</router-link>
                </div>
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.error-401-page {
    height: 100%;
    min-height: 100vh;
    width: 100%;
    box-sizing: border-box;
    background: var(--nf-page-bg);
    padding: var(--nf-space-md);
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    overflow-x: hidden;

    &__inner {
        width: 100%;
        max-width: 960px;
    }

    &__hero {
        margin-bottom: var(--nf-space-sm);
    }

    &__card {
        display: flex;
        flex-direction: row;
        align-items: center;
        gap: var(--nf-space-lg);
        background: var(--nf-card-bg);
        border: 1px solid var(--nf-border);
        border-radius: var(--nf-radius-md);
        box-shadow: var(--nf-shadow-card);
        padding: var(--nf-space-lg);
    }

    &__illus {
        flex: 0 0 auto;
        width: 280px;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: var(--nf-space-md);
        background: var(--nf-brand-lite);
        border-radius: var(--nf-radius-md);

        img {
            width: 100%;
            max-height: 220px;
            object-fit: contain;
        }
    }

    &__info {
        flex: 1 1 0;
        min-width: 0;
    }

    &__path-label {
        font-size: 12px;
        font-weight: 600;
        color: var(--nf-text-secondary);
        margin-bottom: var(--nf-space-xs);
    }

    &__path {
        font-size: 13px;
        line-height: 1.6;
        color: var(--nf-brand);
        word-break: break-all;
        padding: var(--nf-space-xs) var(--nf-space-sm);
        background: var(--nf-sidebar-bg-alt);
        border-radius: var(--nf-radius-sm);
        border: 1px solid var(--nf-border);
        margin-bottom: var(--nf-space-md);
    }

    &__text {
        font-size: 13px;
        line-height: 1.7;
        color: var(--nf-text-regular);
        margin: 0 0 var(--nf-space-md);
    }

    &__btn {
        display: inline-flex;
        align-items: center;
        justify-content: center;
        min-width: 120px;
        height: 36px;
        padding: 0 var(--nf-space-md);
        font-size: 14px;
        font-weight: 500;
        color: var(--nf-brand);
        background-color: transparent;
        border: 1px solid var(--nf-brand);
        border-radius: var(--nf-radius-sm);
        text-decoration: none;
        transition: all 0.2s;
        box-sizing: border-box;

        &:hover {
            color: #fff;
            background-color: var(--nf-brand);
            border-color: var(--nf-brand);
        }
    }
}

@media (max-width: 768px) {
    .error-401-page {
        padding: var(--nf-space-sm);

        &__card {
            flex-direction: column;
            padding: var(--nf-space-md);
            gap: var(--nf-space-md);
        }

        &__illus {
            width: 100%;
            max-width: 240px;

            img {
                max-height: 160px;
            }
        }
    }
}
</style>
