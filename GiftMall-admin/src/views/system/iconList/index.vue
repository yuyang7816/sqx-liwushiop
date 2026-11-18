<script>
/**
 * 页面例子
 * 显示所有icon
 */
import { defineComponent, reactive } from 'vue';
import SvgIcon from '@/components/svgIcon/index.vue';
import { iconList } from '@/components/svgIcon/common.js';
import { copyValue } from '@/common/otherTools';
import { messageSuccess } from '@/action/messagePrompt';
import { InfoFilled } from '@element-plus/icons-vue';

export default defineComponent({
    components: {
        SvgIcon,
        InfoFilled,
    },
    setup() {
        const dataContainer = reactive({
            iconList: iconList,
            activeName: 'first',
        });
        /** copy事件 */
        function onClick(item) {
            let msg = `<SvgIcon
:style="'width:23px;height:23px;'"
name="${item.name}"></SvgIcon>`;
            copyValue(msg);
            messageSuccess('复制成功');
        }
        return {
            dataContainer,
            onClick,
        };
    },
});
</script>

<template>
    <div class="icon-list-page">
        <div class="nf-page-hero icon-list-page__hero">
            <span class="nf-page-hero__badge">系统工具</span>
            <h2 class="nf-page-hero__title">SVG 图标列表</h2>
            <p class="nf-page-hero__desc">浏览项目内置 SVG 图标资源，点击图标可复制组件引用代码</p>
        </div>

        <div class="nf-filter-card icon-list-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><InfoFilled /></el-icon>
                <span>使用说明</span>
            </div>
            <div class="nf-filter-card__body icon-list-page__filter-body">
                <span class="icon-list-page__tip">点击任意图标即可复制 SvgIcon 组件引用代码到剪贴板，共 {{ dataContainer.iconList.length }} 个图标</span>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="icon-list-page__tabs">
            <el-tab-pane label="图标列表" name="first">
                <div class="nf-table-card icon-list-page__grid-card">
                    <div class="icon-list-page__grid">
                        <div
                            class="icon-list-page__item"
                            v-for="(item, index) in dataContainer.iconList"
                            @click="onClick(item)"
                            :key="index"
                        >
                            <SvgIcon :style="'width:23px;height:23px;'" :name="item.name"></SvgIcon>
                            <div class="icon-list-page__name">
                                {{ item.name }}
                            </div>
                        </div>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>
    </div>
</template>

<style lang="scss" scoped>
.icon-list-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.icon-list-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.icon-list-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.icon-list-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
}

.icon-list-page__tip {
    font-size: 14px;
    color: var(--nf-text-secondary);
    line-height: 1.5;
}

.icon-list-page__tabs {
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

.icon-list-page__grid-card {
    margin-top: 0;
    margin-bottom: 0;
}

.icon-list-page__grid {
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(100px, 1fr));
    gap: 0;
    border: 1px solid var(--nf-border);
    border-radius: var(--nf-radius-sm);
    overflow: hidden;
}

.icon-list-page__item {
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    align-items: center;
    cursor: pointer;
    transition: background-color 0.2s, color 0.2s;
    padding: 15px;
    box-sizing: border-box;
    border-right: 1px solid var(--nf-border);
    border-bottom: 1px solid var(--nf-border);

    &:hover {
        background-color: var(--nf-brand-lite);
        color: var(--nf-brand);
    }
}

.icon-list-page__name {
    font-size: 13px;
    margin-top: 10px;
    text-align: center;
    word-break: break-all;
    color: var(--nf-text-secondary);
    line-height: 1.3;

    .icon-list-page__item:hover & {
        color: var(--nf-brand);
    }
}
</style>
