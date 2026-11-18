<script>
/**
 * 顶栏用户区 · user-pill + el-dropdown
 */
import { defineComponent, reactive, toRef } from 'vue';
import { useRouter } from 'vue-router';
import userAvatar from '@/components/userAvatar.vue';
import { userDataStore } from '@/store/user';
import { confirm } from '@/action/messagePrompt';

export default defineComponent({
    name: 'Navbar',
    components: { userAvatar },
    setup() {
        const userData = userDataStore();
        const router = useRouter();
        const dataContainer = reactive({
            userInfo: toRef(userData, 'userInfo'),
        });

        function toPath(path) {
            router.push(path);
        }

        function handleLogout() {
            confirm('退出登录？', '确定退出？', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                showClose: false,
            })
                .then(() => {
                    userData.setUserInfo({});
                    router.push('/login');
                })
                .catch(() => {});
        }

        return { dataContainer, toPath, handleLogout };
    },
});
</script>

<template>
    <div class="site-navbar__right">
        <el-dropdown trigger="click" @command="(cmd) => cmd === 'logout' ? handleLogout() : toPath(cmd)">
            <div class="user-pill">
                <div class="user-pill__avatar">
                    <userAvatar :userInfo="dataContainer.userInfo" />
                </div>
                <div class="user-pill__info">
                    <span class="user-pill__name">{{ dataContainer.userInfo.username || '管理员' }}</span>
                    <span class="user-pill__role">系统用户</span>
                </div>
                <el-icon class="user-pill__arrow"><ArrowDown /></el-icon>
            </div>
            <template #dropdown>
                <el-dropdown-menu>
                    <el-dropdown-item command="/main/mine/info-update">修改基本信息</el-dropdown-item>
                    <el-dropdown-item command="/main/mine/info-password">修改密码</el-dropdown-item>
                    <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </template>
        </el-dropdown>
    </div>
</template>

<style scoped lang="scss">
.site-navbar__right {
    margin-left: auto;
}

.user-pill__avatar {
    :deep(.user-avatar) {
        width: 32px;
        height: 32px;
        --user-name-size: 14px;
        .name {
            color: var(--nf-brand);
            background: var(--nf-brand-lite);
        }
    }
}
</style>
