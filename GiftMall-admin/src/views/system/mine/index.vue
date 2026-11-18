<script>
/**
 * 个人信息展示
 */
import { defineComponent, reactive, toRef } from 'vue';
import SvgIcon from '@/components/svgIcon/index.vue';
import { userDataStore } from '@/store/user';
import { Setting } from '@element-plus/icons-vue';

export default defineComponent({
    components: {
        SvgIcon,
        Setting,
    },
    setup() {
        let userData = userDataStore();
        const dataContainer = reactive({
            userInfo: toRef(userData, 'userInfo'),
        });
        return {
            dataContainer,
        };
    },
});
</script>

<template>
    <div class="mine-info-page">
        <div class="nf-page-hero mine-info-page__hero">
            <span class="nf-page-hero__badge">个人中心</span>
            <h2 class="nf-page-hero__title">个人信息</h2>
            <p class="nf-page-hero__desc">查看当前登录账号的基本资料，包括昵称、手机、邮箱与所属部门</p>
        </div>

        <div class="nf-filter-card mine-info-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Setting /></el-icon>
                <span>快捷操作</span>
            </div>
            <div class="nf-filter-card__body mine-info-page__filter-body">
                <div class="mine-info-page__filter-actions">
                    <router-link to="/main/mine/info-update">
                        <el-button type="primary">修改基本信息</el-button>
                    </router-link>
                    <router-link to="/main/mine/info-password">
                        <el-button type="primary">修改密码</el-button>
                    </router-link>
                </div>
            </div>
        </div>

        <div class="nf-table-card mine-info-page__content">
            <div class="mine-info-page__profile">
                <el-image
                    class="mine-info-page__avatar"
                    :src="dataContainer.userInfo.avatar"
                    fit="cover"
                />
                <div class="mine-info-page__fields">
                    <div class="mine-info-page__item">
                        <div class="mine-info-page__label">
                            <SvgIcon
                                :style="'width:16px;height:16px;margin-right:10px;'"
                                name="svg:user-fill.svg"
                            ></SvgIcon>
                            用户名称
                        </div>
                        <div class="mine-info-page__value">
                            {{ dataContainer.userInfo.nickName }}
                        </div>
                    </div>
                    <div class="mine-info-page__item">
                        <div class="mine-info-page__label">
                            <SvgIcon
                                :style="'width:16px;height:16px;margin-right:10px;'"
                                name="svg:laptop.svg"
                            ></SvgIcon>
                            手机号码
                        </div>
                        <div class="mine-info-page__value">
                            {{ dataContainer.userInfo.mobile }}
                        </div>
                    </div>
                    <div class="mine-info-page__item">
                        <div class="mine-info-page__label">
                            <SvgIcon
                                :style="'width:16px;height:16px;margin-right:10px;'"
                                name="svg:commentdots-fill.svg"
                            ></SvgIcon>
                            用户邮件
                        </div>
                        <div class="mine-info-page__value">
                            {{ dataContainer.userInfo.email }}
                        </div>
                    </div>
                    <div class="mine-info-page__item">
                        <div class="mine-info-page__label">
                            <SvgIcon
                                :style="'width:16px;height:16px;margin-right:10px;'"
                                name="svg:compress-alt.svg"
                            ></SvgIcon>
                            所属部门
                        </div>
                        <div class="mine-info-page__value">
                            {{ dataContainer.userInfo.role }}
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.mine-info-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.mine-info-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.mine-info-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.mine-info-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.mine-info-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);

    a {
        text-decoration: none;
    }
}

.mine-info-page__content {
    margin-bottom: 0;
}

.mine-info-page__profile {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: var(--nf-space-md);
}

.mine-info-page__avatar {
    width: 120px;
    height: 120px;
    border-radius: 50%;
    border: 2px solid var(--nf-border);
    flex-shrink: 0;
}

.mine-info-page__fields {
    width: 100%;
    max-width: 560px;
    border-radius: var(--nf-radius-md);
    background: var(--nf-sidebar-bg-alt);
    overflow: hidden;
}

.mine-info-page__item {
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;
    padding: var(--nf-space-sm) var(--nf-space-md);
    font-size: 14px;
    border-bottom: 1px solid var(--nf-border);
    color: var(--nf-text-primary);

    &:last-child {
        border-bottom: none;
    }
}

.mine-info-page__label {
    display: flex;
    flex-direction: row;
    align-items: center;
    color: var(--nf-text-secondary);
    flex-shrink: 0;
}

.mine-info-page__value {
    flex: 1;
    min-width: 0;
    display: flex;
    justify-content: flex-end;
    text-align: right;
    color: var(--nf-text-primary);
    word-break: break-all;
}
</style>
