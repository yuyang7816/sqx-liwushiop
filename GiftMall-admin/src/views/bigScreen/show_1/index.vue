<script>
/**
 * 大屏主页面
 * 采用缩放的形式进行适配，搭配rem的话很方便实用
 *  */
import {
    defineComponent,
    ref,
    getCurrentInstance,
    reactive,
    toRef,
    computed,
    onMounted,
    onActivated,
    watch,
} from 'vue';
import AutoScalContainer from '@/components/autoScalContainer.vue';
import ViewHead from './components/viewHead.vue';
import img_1 from './assets/bg.png';
import img_2 from './assets/1-1-bg.png';
import Box_1 from './components/box_1.vue';
import Box_2 from './components/box_2.vue';
import Box_3 from './components/box_3.vue';
import Box_4 from './components/box_4.vue';
import Box_5 from './components/box_5.vue';
import Box_6 from './components/box_6.vue';
import { useRoute } from 'vue-router';

export default defineComponent({
    name: 'BigScreenView',
    components: {
        AutoScalContainer,
        ViewHead,
        Box_1,
        Box_2,
        Box_3,
        Box_4,
        Box_5,
        Box_6,
    },
    setup() {
        let route = useRoute();
        const dataContainer = reactive({
            loading: false,
            img: {
                img_1,
                img_2,
            },
            fit: 'contain',
        });
        watch(
            route,
            () => {
                let queryParams = route.query || {};
                let fitMap = {
                    cover: 'cover',
                    contain: 'contain',
                };
                dataContainer.fit = fitMap[queryParams.fit] || 'contain';
            },
            {
                immediate: true,
            },
        );
        return {
            dataContainer,
        };
    },
});
</script>

<template>
    <div class="big-screen-view">
        <AutoScalContainer :height="1080" :width="1920" :fit="dataContainer.fit">
            <div
                class="big-screen-view-container"
                :style="{
                    '--bg-img-1': `url(${dataContainer.img.img_1})`,
                    '--bg-img-2': `url(${dataContainer.img.img_2})`,
                }"
            >
                <div class="head">
                    <ViewHead title="数据可视化大屏展示">
                        <template #left>
                            <span class="bs-head-badge">可视化</span>
                        </template>
                        <template #right>
                            <span class="bs-head-desc">AutoScal 自适应缩放 · Brand 主题 #ef276a</span>
                        </template>
                    </ViewHead>
                </div>
                <div class="content">
                    <div class="top">
                        <Box_1></Box_1>
                    </div>
                    <div class="content">
                        <div class="left">
                            <div class="box">
                                <Box_2></Box_2>
                            </div>
                            <div class="box">
                                <Box_3></Box_3>
                            </div>
                        </div>
                        <div class="right">
                            <div class="box">
                                <Box_4></Box_4>
                            </div>
                            <div class="box">
                                <Box_5></Box_5>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="centre-box">
                    <div class="v-height"></div>
                    <div class="container">
                        <Box_6></Box_6>
                    </div>
                </div>
            </div>
        </AutoScalContainer>
    </div>
</template>

<style lang="scss" scoped>
.big-screen-view {
    --bs-brand: var(--nf-brand, #ef276a);
    --bs-brand-deep: var(--nf-brand-deep, #d31051);
    --bs-brand-lite: var(--nf-brand-lite, #fde5ed);
    --bs-accent-glow: rgba(239, 39, 106, 0.28);
    --bs-panel-tint: rgba(211, 16, 81, 0.16);
    width: 100%;
    height: 100%;
    overflow: hidden;
    background:
        radial-gradient(circle at 50% 0%, var(--bs-accent-glow) 0%, transparent 55%),
        linear-gradient(180deg, #1a0810 0%, #120810 100%);

    .big-screen-view-container {
        width: 1920px;
        height: 1080px;
        background-color: #120810;
        display: flex;
        flex-direction: column;
        background-image: var(--bg-img-1);
        background-repeat: no-repeat;
        background-size: 100% 100%;
        background-position: center;
        position: relative;
        box-shadow:
            inset 0 0 0 1px rgba(239, 39, 106, 0.18),
            0 0 48px rgba(211, 16, 81, 0.22);

        &::after {
            content: '';
            position: absolute;
            left: 0;
            right: 0;
            bottom: 0;
            height: 3px;
            background: linear-gradient(
                90deg,
                transparent 0%,
                var(--bs-brand) 18%,
                var(--bs-brand-deep) 50%,
                var(--bs-brand) 82%,
                transparent 100%
            );
            pointer-events: none;
            z-index: 3;
        }

        > .head {
            height: 91px;
            position: relative;
            z-index: 2;

            :deep(.centre) {
                text-shadow: 0 0 18px rgba(239, 39, 106, 0.35);
            }
        }

        .bs-head-badge {
            display: inline-flex;
            align-items: center;
            height: 28px;
            padding: 0 12px;
            border-radius: 6px;
            font-size: 13px;
            font-weight: 600;
            letter-spacing: 0.5px;
            color: var(--bs-brand);
            background: rgba(253, 229, 237, 0.12);
            border: 1px solid rgba(239, 39, 106, 0.35);
            box-shadow: 0 0 12px rgba(239, 39, 106, 0.18);
        }

        .bs-head-desc {
            font-size: 13px;
            font-weight: 500;
            color: rgba(255, 255, 255, 0.72);
            letter-spacing: 0.3px;
        }

        :deep(.box-cp-container > .container) {
            background-color: var(--bs-panel-tint) !important;
            border: 1px solid rgba(239, 39, 106, 0.22);
            box-shadow: inset 0 0 24px rgba(211, 16, 81, 0.12);
        }

        :deep(.box-cp-container a),
        :deep(.box-cp-container .router-link-active) {
            background-color: var(--bs-brand) !important;
            border: 1px solid rgba(253, 229, 237, 0.35);
            transition: background-color 0.2s ease, box-shadow 0.2s ease;

            &:hover {
                background-color: var(--bs-brand-deep) !important;
                box-shadow: 0 0 10px rgba(239, 39, 106, 0.35);
            }
        }

        > .content {
            display: flex;
            flex-direction: column;
            flex: 1 1 0;
            width: 100%;
            height: 0;
            position: relative;
            z-index: 2;
            pointer-events: none;
            > .top {
                width: 100%;
                height: 199px;
                pointer-events: initial;
            }
            > .content {
                display: flex;
                flex-direction: row;
                justify-content: space-between;
                flex: 1 1 0;
                width: 100%;
                height: 0;
                padding: 0 15px 15px 15px;
                box-sizing: border-box;
                > .left,
                > .right {
                    display: flex;
                    flex-direction: column;
                    > .box {
                        width: 100%;
                        flex: 1 1 0;
                        height: 0;
                        background-image: var(--bg-img-2);
                        background-repeat: no-repeat;
                        background-size: 100% 100%;
                        background-position: center;
                        margin: 0 0 15px 0;
                        pointer-events: initial;
                        border-radius: 8px;
                        overflow: hidden;
                        box-shadow:
                            inset 0 0 0 1px rgba(239, 39, 106, 0.18),
                            0 2px 8px rgba(211, 16, 81, 0.12);
                        &:last-child {
                            margin: 0;
                        }
                    }
                }
                > .left {
                    height: 100%;
                    width: 550px;
                }
                > .right {
                    height: 100%;
                    width: 550px;
                }
            }
        }
        > .centre-box {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            z-index: 1;
            display: flex;
            flex-direction: column;
            > .v-height {
                width: 100%;
                height: 270px;
            }
            > .container {
                flex: 1 1 0;
                height: 0;
                width: 100%;
            }
        }
    }
}
</style>
