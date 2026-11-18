<template>
    <el-scrollbar height="100%">
        <div class="show-list-add-page">
            <div class="nf-page-hero show-list-add-page__hero">
                <span class="nf-page-hero__badge">示例页面</span>
                <h2 class="nf-page-hero__title">{{ isUpdateMode ? '编辑数据' : '新增数据' }}</h2>
                <p class="nf-page-hero__desc">
                    填写数据名称、code 与 id 后提交保存；编辑模式下可更新标签缓存状态
                </p>
            </div>

            <div class="nf-filter-card show-list-add-page__filter">
                <div class="nf-filter-card__head">
                    <span>页面信息</span>
                </div>
                <div class="nf-filter-card__body show-list-add-page__filter-body">
                    <div class="show-list-add-page__meta-row">
                        <span class="show-list-add-page__meta-label">当前加载时间：</span>
                        <span class="show-list-add-page__meta-value">{{ dataContainer.nowTime }}</span>
                    </div>
                    <div class="show-list-add-page__meta-row">
                        <span class="show-list-add-page__meta-label">当前加载时间戳：</span>
                        <span class="show-list-add-page__meta-value">{{ dataContainer.nowTime_1 }}</span>
                    </div>
                    <div class="show-list-add-page__meta-row">
                        <span class="show-list-add-page__meta-label">数据唯一标识：</span>
                        <span class="show-list-add-page__meta-value">{{ dataContainer.form.id || '—' }}</span>
                    </div>
                    <div class="show-list-add-page__meta-row show-list-add-page__meta-row--action">
                        <el-button @click="handleClick_2" type="primary">
                            更新标签信息（修改标题，切换缓存状态），不会更改目录配置，就是说重新重目录配置处创建该标签会使用目录的配置
                        </el-button>
                    </div>
                </div>
            </div>

            <div class="nf-table-card show-list-add-page__form">
                <el-form
                    :model="dataContainer.form"
                    ref="FormElRef"
                    :inline="true"
                    :rules="dataContainer.rules"
                    label-width="120px"
                    class="show-list-add-page__form-el"
                >
                    <el-row :gutter="0">
                        <el-col :span="8" :xs="6">
                            <el-form-item label="修改数据名称" prop="name">
                                <el-input
                                    v-model="dataContainer.form.name"
                                    placeholder="请输入"
                                    clearable
                                />
                            </el-form-item>
                        </el-col>
                        <el-col :span="8" :xs="6">
                            <el-form-item label="修改数据code" prop="code">
                                <el-input
                                    v-model="dataContainer.form.code"
                                    placeholder="请输入"
                                    clearable
                                />
                            </el-form-item>
                        </el-col>
                        <el-col :span="8" :xs="6">
                            <el-form-item label="修改数据id" prop="id">
                                <el-input
                                    v-model="dataContainer.form.id"
                                    placeholder="请输入"
                                    clearable
                                />
                            </el-form-item>
                        </el-col>
                        <el-col :span="8" :xs="6">
                            <el-form-item label="修改数据id" prop="id">
                                <el-input
                                    v-model="dataContainer.form.id"
                                    placeholder="请输入"
                                    clearable
                                />
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
                <div class="show-list-add-page__actions">
                    <el-button type="primary" @click="handleSubmit">提交</el-button>
                </div>
            </div>
        </div>
    </el-scrollbar>
</template>

<script>
/**
 * 页面例子
 */
import {
    defineComponent,
    ref,
    reactive,
    computed,
} from 'vue';
import { useRouter, useRoute } from 'vue-router';
import SvgIcon from '@/components/svgIcon/index.vue';
import { Delete } from '@element-plus/icons-vue';
import { verifiedData } from '@/common/verifiedTools';
import { messageError } from '@/action/messagePrompt.js';
import { findTag, updateTag } from '@/action/tagListTools';

export default defineComponent({
    components: {
        SvgIcon,
        Delete,
    },
    setup() {
        const FormElRef = ref(null); //组件实例
        const router = useRouter();
        const route = useRoute();
        const isUpdateMode = computed(() => !!route.params.sign);
        const dataContainer = reactive({
            loading: false,
            form: {},
            nowTime: new Date(),
            nowTime_1: new Date().getTime(),
            rules: {
                name: [{ required: true, message: '请输入数据', trigger: 'blur' }],
            },
        });
        /**
         * 数据初始化
         */
        function initData() {
            let params = route.params;
            if (!params.sign) return;
            dataContainer.form = {
                id: params.sign,
            };
        }
        initData();
        /** 提交数据 */
        function handleSubmit() {
            /** 使用组件自带方法验证数据 */
            if (!FormElRef.value) return;
            FormElRef.value.validate((valid, e) => {
                if (e) {
                    /** 打印报错信息 */
                    let msg = e[Object.keys(e)[0]][0].message;
                    messageError(msg);
                }
                if (!valid) return;
                /** 像后端提交 */
            });
        }
        /**
         * 数据验证
         * 外部可调用
         *  */
        function validData(data) {
            const failData = verifiedData(data, {
                name: {
                    msg: '名称 不能为空',
                    validate(value, option) {
                        if (!value && value !== 0) return false;
                        return true;
                    },
                },
            });
            return failData;
        }
        function handleClick_2() {
            let tag = findTag(route.path) || {};
            if (!tag) return;
            updateTag({
                tag: {
                    ...tag,
                    title: tag.title + '-1',
                    isCache: !tag.isCache,
                },
                layoutName: tag.layoutName,
            });
        }
        return {
            dataContainer,
            initData,
            FormElRef,
            handleSubmit,
            handleClick_2,
            isUpdateMode,
        };
    },
});
</script>

<style lang="scss" scoped>
.show-list-add-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.show-list-add-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.show-list-add-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.show-list-add-page__filter-body {
    display: flex;
    flex-direction: column;
    gap: var(--nf-space-sm);
}

.show-list-add-page__meta-row {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-xs);
    font-size: 14px;
    line-height: 1.5;
}

.show-list-add-page__meta-row--action {
    margin-top: var(--nf-space-xs);
}

.show-list-add-page__meta-label {
    flex-shrink: 0;
    color: var(--nf-text-secondary);
}

.show-list-add-page__meta-value {
    color: var(--nf-text-primary);
    word-break: break-all;
}

.show-list-add-page__form {
    margin-bottom: 0;
}

.show-list-add-page__form-el {
    width: 100%;
}

.show-list-add-page__actions {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-md);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}
</style>
