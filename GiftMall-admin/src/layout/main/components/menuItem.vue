<script>
import { reactive, toRef, computed } from 'vue';
import SvgIcon from '@/components/svgIcon/index.vue';
import { useRouter, useRoute } from 'vue-router';
import linkItem from './linkItem.vue';

function normalizePath(p) {
    if (!p) return p;
    return p.startsWith('/') ? p : '/' + p;
}

export default {
    name: 'MenuItem',
    components: { SvgIcon, linkItem },
    props: {
        dataInfo: {
            type: Object,
            default: () => ({}),
        },
    },
    setup(props) {
        const router = useRouter();
        const route = useRoute();
        const dataContainer = reactive({
            dataInfo: toRef(props, 'dataInfo'),
        });

        const indexPath = computed(() => normalizePath(dataContainer.dataInfo.url));

        function handleClick(params) {
            if (params.isLink) {
                window.open(params.url);
            } else {
                router.push(params.url);
            }
        }

        return { dataContainer, handleClick, route, indexPath };
    },
};
</script>

<template>
    <div class="menu-item-container">
        <el-menu-item
            v-if="!dataContainer.dataInfo.list || dataContainer.dataInfo.list.length == 0"
            :index="indexPath"
            :class="{ 'is-active': indexPath === route.fullPath }"
            @click="handleClick(dataContainer.dataInfo)"
        >
            <SvgIcon
                :style="'width:18px;height:18px;min-width:18px;'"
                :name="dataContainer.dataInfo.icon"
            />
            <span>{{ dataContainer.dataInfo.name }}</span>
        </el-menu-item>

        <el-sub-menu v-else :index="dataContainer.dataInfo.name">
            <template #title>
                <SvgIcon
                    :style="'width:18px;height:18px;min-width:18px;'"
                    :name="dataContainer.dataInfo.icon"
                />
                <span class="menu-label">{{ dataContainer.dataInfo.name }}</span>
                <linkItem :dataInfo="dataContainer.dataInfo.url" />
            </template>
            <MenuItem
                v-for="item in dataContainer.dataInfo.list"
                :key="item.url"
                :dataInfo="item"
            />
        </el-sub-menu>
    </div>
</template>

<style scoped lang="scss">
.menu-item-container {
    width: 100%;
}
</style>
