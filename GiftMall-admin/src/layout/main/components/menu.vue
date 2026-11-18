<script>
/**
 * 左侧菜单组件 · 白底侧栏
 */
import { defineComponent, ref, reactive, onMounted, toRef } from 'vue';
import { useRoute } from 'vue-router';
import MenuItem from './menuItem.vue';

export default defineComponent({
    name: 'Menu',
    components: { MenuItem },
    props: {
        dataList: {
            type: Array,
            default: () => [],
        },
        collapse: {
            type: Boolean,
            default: false,
        },
    },
    setup(props) {
        const route = useRoute();
        const ElMenuRef = ref(null);
        const dataContainer = reactive({
            dataList: toRef(props, 'dataList'),
        });

        onMounted(() => {
            if (!ElMenuRef.value) return;
            const el = ElMenuRef.value.$el;
            const hasActiveSubEl = el.querySelector('.el-sub-menu .is-sub-defin-active');
            if (!hasActiveSubEl) return;
            ElMenuRef.value.open(route.path);
        });

        return { dataContainer, route, ElMenuRef };
    },
});
</script>

<template>
    <el-menu
        class="menu-container nf-sidebar-menu"
        ref="ElMenuRef"
        :collapse="collapse"
        :default-active="route.fullPath"
        :router="false"
        :collapse-transition="false"
    >
        <MenuItem
            v-for="item in dataContainer.dataList"
            :key="item.path || item.url || item.name"
            :dataInfo="item"
        />
    </el-menu>
</template>

<style scoped lang="scss">
.menu-container {
    width: 100%;
    border: none !important;
    padding: var(--nf-space-xs) var(--nf-space-xs) var(--nf-space-sm);
    box-sizing: border-box;
    --el-menu-active-color: var(--nf-brand) !important;
    --el-menu-text-color: var(--nf-text-regular) !important;
    --el-menu-hover-bg-color: var(--nf-sidebar-bg-alt) !important;
    --el-menu-bg-color: transparent !important;
    --el-menu-item-height: 44px !important;
    --el-menu-sub-item-height: 44px !important;
    --el-menu-base-level-padding: 12px !important;
    --el-menu-level-padding: 12px !important;
    overflow-x: hidden;

    :deep(.menu-item-container) {
        .el-sub-menu__icon-arrow {
            margin-top: 0 !important;
            top: initial !important;
            right: 12px !important;
        }

        .el-menu {
            padding: 0;
        }

        .el-sub-menu__title,
        .el-menu-item {
            min-height: 44px !important;
            height: fit-content !important;
            line-height: 1.4 !important;
            border-radius: var(--nf-radius-sm);
            margin-bottom: 2px;
        }

        .el-sub-menu.is-sub-defin-active > .el-sub-menu__title {
            color: var(--nf-brand) !important;
        }

        .el-menu-item .svg-icon,
        .el-sub-menu__title .svg-icon {
            margin-right: 8px;
            flex-shrink: 0;
            color: inherit;
        }

        .el-menu-item span,
        .el-sub-menu__title span.menu-label {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }
    }
}
</style>
