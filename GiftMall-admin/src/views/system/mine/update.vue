<script>
/**
 * 头像修改页面
 */
import { defineComponent, ref, reactive } from 'vue';
import SvgIcon from '@/components/svgIcon/index.vue';
import { verifiedData } from '@/common/verifiedTools';
import { messageError, messageSuccess } from '@/action/messagePrompt.js';
import { toTrim } from '@/common/otherTools';
import { throttleFn } from '@/common/debounceAndThrottle';
import { userDataStore } from '@/store/user';

export default defineComponent({
    components: {
        SvgIcon,
    },
    setup() {
        let userData = userDataStore();
        const FormElRef = ref(null); //组件实例
        const dataContainer = reactive({
            loading: false,
            form: {},
            rules: {
                nickName: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
                avatar: [{ required: true, message: '请输入头像', trigger: 'blur' }],
            },
        });
        /** 初始化数据 */
        function initData() {
            Object.assign(dataContainer.form, userData.userInfo);
        }
        initData();
        /** 提交数据 */
        /** 保存操作 */
        const handleSubmit = throttleFn(function () {
            if (!FormElRef.value || dataContainer.loading) return;
            FormElRef.value.validate((valid, e) => {
                if (!valid) {
                    const message = e[Object.keys(e)[0]][0].message;
                    messageError(message);
                    return;
                }
                const verifiedData = validBase(dataContainer.form);
                if (verifiedData) {
                    messageError('参数错误！' + verifiedData[0].label);
                    return;
                }
                // 可以提交数据了
                messageSuccess('数据提交了');
                userData.setUserInfo(Object.assign({}, userData.userInfo, dataContainer.form));
            });
        }, 700);
        /**
         * 数据验证
         * 外部可调用
         *  */
        /** 验证信息 */
        function validBase(data) {
            const failData = verifiedData(data, {
                nickName: {
                    label: 'nickName:昵称',
                    validate(value, option) {
                        if (!value && value !== 0) {
                            option.label = option.label + ' 不能为空';
                            return false;
                        }
                        if (Object.prototype.toString.call(value) !== '[object String]') {
                            option.label = option.label + ' 必须是一个字符串';
                            return false;
                        }
                        if (value.length > 300) {
                            option.label = option.label + ' 字符长度长度超出300';
                            return false;
                        }
                        return true;
                    },
                },
                avatar: {
                    label: 'avatar:头像',
                    validate(value, option) {
                        if (!value && value !== 0) {
                            option.label = option.label + ' 不能为空';
                            return false;
                        }
                        if (Object.prototype.toString.call(value) !== '[object String]') {
                            option.label = option.label + ' 必须是一个字符串';
                            return false;
                        }
                        if (value.length > 300) {
                            option.label = option.label + ' 字符长度长度超出300';
                            return false;
                        }
                        return true;
                    },
                },
            });
            return failData;
        }
        return {
            dataContainer,
            FormElRef,
            handleSubmit,
            toTrim,
        };
    },
});
</script>

<template>
    <div class="mine-update-page">
        <div class="nf-page-hero mine-update-page__hero">
            <span class="nf-page-hero__badge">个人中心</span>
            <h2 class="nf-page-hero__title">修改个人信息</h2>
            <p class="nf-page-hero__desc">更新昵称与头像链接，修改后点击提交保存</p>
        </div>

        <div class="nf-table-card mine-update-page__form">
            <div class="mine-update-page__avatar-wrap">
                <el-image class="mine-update-page__avatar" :src="dataContainer.form.avatar" fit="cover" />
            </div>
            <el-form
                :model="dataContainer.form"
                ref="FormElRef"
                :inline="false"
                :rules="dataContainer.rules"
                label-width="120px"
                class="mine-update-page__form-el"
            >
                <el-row :gutter="0">
                    <el-col :span="24">
                        <el-form-item label="昵称" prop="nickName">
                            <el-input
                                @input="
                                    () => {
                                        dataContainer.form.nickName = toTrim(
                                            dataContainer.form.nickName,
                                        );
                                    }
                                "
                                :clearable="true"
                                v-model="dataContainer.form.nickName"
                            >
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="头像" prop="avatar">
                            <el-input
                                @input="
                                    () => {
                                        dataContainer.form.avatar = toTrim(
                                            dataContainer.form.avatar,
                                        );
                                    }
                                "
                                :clearable="true"
                                v-model="dataContainer.form.avatar"
                            >
                            </el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div class="mine-update-page__actions">
                <el-button type="primary" @click="handleSubmit">提交</el-button>
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.mine-update-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.mine-update-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.mine-update-page__form {
    max-width: 640px;
    margin-bottom: 0;
}

.mine-update-page__avatar-wrap {
    display: flex;
    justify-content: center;
    margin-bottom: var(--nf-space-md);
}

.mine-update-page__avatar {
    width: 120px;
    height: 120px;
    border-radius: 50%;
    border: 2px solid var(--nf-border);
}

.mine-update-page__form-el {
    width: 100%;
}

.mine-update-page__actions {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-md);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}
</style>
