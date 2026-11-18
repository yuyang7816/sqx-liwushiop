<script>
/**
 * 主 Layout · yanglao-admin-v1 Shell
 */
import { defineComponent, reactive, toRef, computed, ref } from 'vue';
import Navbar from './components/navbar.vue';
import Menu from './components/menu.vue';
import TagList from './components/tagList.vue';
import { useRouter, useRoute } from 'vue-router';
import { userDataStore } from '@/store/user';
import img_1 from '@/assets/logo.png';
import keepAliveRouter from '@/components/keepAliveRouter.vue';
import DefinScrollbar from '@/components/definScrollbar.vue';
import { publicDataStore } from '@/store/public';

export default defineComponent({
    name: 'MainLayout',
    components: {
        Menu,
        TagList,
        keepAliveRouter,
        Navbar,
        DefinScrollbar,
    },
    setup() {
        const publicData = publicDataStore();
        const userData = userDataStore();
        const router = useRouter();
        const route = useRoute();
        const sidebarCollapsed = ref(false);

        const dataContainer = reactive({
            name: import.meta.env.VITE_APP_name,
            fullScreen: toRef(publicData, 'fullScreen'),
            img: { img_1 },
            layoutName: 'main',
            userInfo: toRef(userData, 'userInfo'),
            tagsMap: toRef(userData, 'tagsMap'),
            userMenuList: toRef(userData, 'userMenuList'),
            iframeList: toRef(userData, 'iframeList'),
        });

        const iframePathMap = computed(() =>
            dataContainer.iframeList
                .filter((item) => item.layoutName == dataContainer.layoutName)
                .reduce((c, i) => {
                    c[i.path] = i;
                    return c;
                }, {}),
        );

        const cacheTagList = computed(() => {
            const tagList = dataContainer.tagsMap[dataContainer.layoutName || ''] || [];
            return tagList.filter((item) => item.isCache).map((item) => item.path);
        });

        const currentPageTitle = computed(() => route.meta?.title || '管理系统');

        function toPath(path) {
            router.push(path);
        }

        function toggleCollapse() {
            sidebarCollapsed.value = !sidebarCollapsed.value;
        }

        return {
            dataContainer,
            routeIncetance: route,
            toPath,
            cacheTagList,
            iframePathMap,
            sidebarCollapsed,
            toggleCollapse,
            currentPageTitle,
        };
    },
});
</script>

<template>
    <div
        :class="{
            'site-wrapper': true,
            'site-wrapper--collapsed': sidebarCollapsed,
            'site-wrapper--fullscreen': dataContainer.fullScreen,
        }"
    >
        <header class="site-navbar">
            <div class="site-navbar__left">
                <div class="site-logo" @click="toPath({ path: '/' })">
                    <el-image class="site-logo__img" :src="dataContainer.img.img_1" fit="cover" />
                    <span class="site-logo__title">{{ dataContainer.name }}</span>
                </div>
                <button type="button" class="site-navbar__collapse" @click="toggleCollapse">
                    <el-icon>
                        <Fold v-if="!sidebarCollapsed" />
                        <Expand v-else />
                    </el-icon>
                </button>
            </div>
            <div class="site-navbar__center">
                <span class="nf-page-badge">当前页面</span>
                <span class="site-navbar__page-title">{{ currentPageTitle }}</span>
            </div>
            <Navbar />
        </header>

        <div class="site-body">
            <aside class="site-sidebar site-sidebar--nf">
                <div class="site-sidebar__menu">
                    <DefinScrollbar height="100%" :showUpBt="false">
                        <Menu
                            :dataList="dataContainer.userMenuList"
                            :collapse="sidebarCollapsed"
                        />
                    </DefinScrollbar>
                </div>
            </aside>

            <main class="site-content">
                <div class="site-tags">
                    <TagList
                        :activePath="routeIncetance.path"
                        :layoutName="dataContainer.layoutName"
                    />
                </div>
                <div class="site-main">
                    <keepAliveRouter :cacheList="cacheTagList" />
                    <div
                        :style="{
                            'z-index': iframePathMap[routeIncetance.path] ? 1 : -1,
                            opacity: iframePathMap[routeIncetance.path] ? 1 : 0,
                        }"
                        class="iframe-view"
                    >
                        <iframe
                            v-for="item in dataContainer.iframeList.filter((item) => {
                                return item.layoutName == dataContainer.layoutName;
                            })"
                            :key="item.key"
                            :style="{
                                'z-index': item.path == routeIncetance.path ? 1 : -1,
                                opacity: item.path == routeIncetance.path ? 1 : 0,
                            }"
                            :src="item.src"
                            width="100%"
                            height="100%"
                            frameborder="0"
                            allowfullscreen
                        />
                    </div>
                </div>
            </main>
        </div>
    </div>
</template>

<style scoped lang="scss">
.iframe-view {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    left: 0;
    z-index: 1;
    pointer-events: none;

    iframe {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        pointer-events: auto;
    }
}
</style>
