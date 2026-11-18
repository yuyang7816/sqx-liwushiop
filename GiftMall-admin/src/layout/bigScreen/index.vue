<script>
/**
 * 大屏展示 layout
 * 负责相应事件的统一分发处理
 */
import {
    ref,
    defineComponent,
    h,
    reactive,
    watch,
    toRef,
    computed,
    onMounted,
    onUnmounted,
} from 'vue';
import { useRouter, useRoute } from 'vue-router';
import keepAliveRouter from '@/components/keepAliveRouter.vue';
import { userDataStore } from '@/store/user';

export default defineComponent({
    name: 'ScreenLayout',
    components: {
        keepAliveRouter,
    },
    props: {},
    setup() {
        let userData = userDataStore();
        const router = useRouter();
        const route = useRoute();
        const dataContainer = reactive({
            layoutName: 'big-screen',
            tagsMap: toRef(userData, 'tagsMap'),
        });
        /**
         * 需要缓存的页面列表
         * 根据标签列表来的，需要改的话只需要处理标签列表
         *  */
        const cacheTagList = computed(() => {
            let tagList = dataContainer.tagsMap[dataContainer.layoutName || ''] || [];
            return tagList
                .filter((item) => {
                    return item.isCache;
                })
                .map((item) => {
                    /** 缓存组件是根据path命名来缓存的 */
                    return item.path;
                });
        });
        return {
            dataContainer,
            cacheTagList,
        };
    },
});
</script>

<template>
    <div class="big-screen-layout">
        <div class="big-screen-layout__viewport">
            <keepAliveRouter :cacheList="cacheTagList"></keepAliveRouter>
        </div>
    </div>
</template>

<style scoped lang="scss">
.big-screen-layout {
    --bs-brand: var(--nf-brand, #ef276a);
    --bs-brand-deep: var(--nf-brand-deep, #d31051);
    --bs-brand-lite: var(--nf-brand-lite, #fde5ed);
    --bs-chrome-bg: #140810;
    --bs-chrome-bg-soft: rgba(211, 16, 81, 0.12);
    width: 100vw;
    height: 100vh;
    overflow: hidden;
    background:
        radial-gradient(ellipse 80% 60% at 50% 0%, var(--bs-chrome-bg-soft) 0%, transparent 70%),
        linear-gradient(180deg, #1a0810 0%, var(--bs-chrome-bg) 48%, #0d0408 100%);

    &__viewport {
        width: 100%;
        height: 100%;
        overflow: hidden;
        position: relative;
    }
}
</style>
