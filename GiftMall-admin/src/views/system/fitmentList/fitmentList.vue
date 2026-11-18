<template>
    <div class="fitment-list-page">
        <div class="nf-page-hero fitment-list-page__hero">
            <span class="nf-page-hero__badge">升级配置</span>
            <h2 class="nf-page-hero__title">应用升级管理</h2>
            <p class="nf-page-hero__desc">管理客户端版本升级配置，支持查看与刷新升级信息</p>
        </div>

        <div class="nf-filter-card fitment-list-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>快捷操作</span>
            </div>
            <div class="nf-filter-card__body fitment-list-page__filter-body">
                <div class="fitment-list-page__filter-actions">
                    <el-button type="primary" @click="handleSearch()">刷新</el-button>
                </div>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="fitment-list-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="升级配置" name="first">
                <div class="nf-table-card fitment-list-page__table">
                    <div class="fitment-list-page__placeholder">
                        111
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
/**
 * 页面例子
 */
import {
    defineComponent,
    reactive,
} from 'vue';
import { useRouter, useRoute } from 'vue-router';
import SvgIcon from '@/components/svgIcon/index.vue';
import { Delete, Filter } from '@element-plus/icons-vue';
import http from '@/http/request.js';

export default defineComponent({
    components: {
        SvgIcon,
        Delete,
        Filter,
    },
    setup() {
        const router = useRouter();
        const route = useRoute();
        const dataContainer = reactive({
            activeName: 'first',
        });

        function initData() {
            let params = route.params;
            if (!params.sign) return;
        }
        initData();

        function prev() {
            router.back();
        }

        function handleClick() {
            dataSelect();
        }

        function handleSearch() {
            dataSelect();
        }

        /** 获取用户详情数据 */
        function dataSelect() {
            let userId = route.query.userId;
            http.get('adminerrand/findUserMessage', {
                params: {
                    userId: userId,
                },
            }, {
            }).then((res) => {
                console.log('============', res);
            });
        }
        console.log('========1111====', route.query.userId);
        dataSelect();

        return {
            dataContainer,
            initData,
            prev,
            handleClick,
            handleSearch,
            dataSelect,
        };
    },
});
</script>

<style lang="scss" scoped>
.fitment-list-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.fitment-list-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.fitment-list-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.fitment-list-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.fitment-list-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.fitment-list-page__tabs {
    margin-bottom: 0;

    :deep(.el-tabs__header) {
        margin-bottom: 0;
    }

    :deep(.el-tabs__item.is-active) {
        color: var(--nf-brand);
    }

    :deep(.el-tabs__active-bar) {
        background-color: var(--nf-brand);
    }
}

.fitment-list-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.fitment-list-page__placeholder {
    padding: var(--nf-space-md);
    font-size: 14px;
    color: var(--nf-text-secondary);
    line-height: 1.5;
}
</style>
