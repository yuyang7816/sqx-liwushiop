<script>
/**
 * 密码修改页面
 */
import { defineComponent, ref, reactive } from 'vue';
import SvgIcon from '@/components/svgIcon/index.vue';
import { verifiedData } from '@/common/verifiedTools';
import { messageError, messageSuccess } from '@/action/messagePrompt.js';
import { toTrim } from '@/common/otherTools';
import { throttleFn } from '@/common/debounceAndThrottle';

export default defineComponent({
    components: {
        SvgIcon,
    },
    setup() {
        const FormElRef = ref(null); //组件实例
        const dataContainer = reactive({
            loading: false,
            form: {},
            rules: {
                oldPassword: [{ required: true, message: '请输入密码', trigger: 'blur' }],
                password: [{ required: true, message: '请输入新密码', trigger: 'blur' }],
                password_: [
                    { required: true, message: '请确认密码', trigger: 'blur' },
                    {
                        validator: (rule, value, callBack) => {
                            if (value !== dataContainer.form.password) {
                                callBack(new Error('两次密码不一致'));
                            }
                            callBack();
                        },
                        trigger: 'blur',
                    },
                ],
            },
        });
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
            });
        }, 700);
        /**
         * 数据验证
         * 外部可调用
         *  */
        /** 验证信息 */
        function validBase(data) {
            const failData = verifiedData(data, {
                oldPassword: {
                    label: 'oldPassword:原密码',
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
                password: {
                    label: 'password:密码',
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
    <div class="mine-password-page">
        <div class="nf-page-hero mine-password-page__hero">
            <span class="nf-page-hero__badge">个人中心</span>
            <h2 class="nf-page-hero__title">修改密码</h2>
            <p class="nf-page-hero__desc">输入原密码与新密码完成修改，两次新密码须保持一致</p>
        </div>

        <div class="nf-table-card mine-password-page__form">
            <el-form
                :model="dataContainer.form"
                ref="FormElRef"
                :inline="false"
                :rules="dataContainer.rules"
                label-width="120px"
                class="mine-password-page__form-el"
            >
                <el-row :gutter="0">
                    <el-col :span="24">
                        <el-form-item label="原密码" prop="oldPassword">
                            <el-input
                                show-password
                                @input="
                                    () => {
                                        dataContainer.form.oldPassword = toTrim(
                                            dataContainer.form.oldPassword,
                                        );
                                    }
                                "
                                :clearable="true"
                                v-model="dataContainer.form.oldPassword"
                            >
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="新密码" prop="password">
                            <el-input
                                show-password
                                @input="
                                    () => {
                                        dataContainer.form.password = toTrim(
                                            dataContainer.form.password,
                                        );
                                    }
                                "
                                :clearable="true"
                                v-model="dataContainer.form.password"
                            >
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="24">
                        <el-form-item label="确认密码" prop="password_">
                            <el-input
                                show-password
                                @input="
                                    () => {
                                        dataContainer.form.password_ = toTrim(
                                            dataContainer.form.password_,
                                        );
                                    }
                                "
                                v-model="dataContainer.form.password_"
                                :clearable="true"
                            >
                            </el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div class="mine-password-page__actions">
                <el-button type="primary" @click="handleSubmit">提交</el-button>
            </div>
        </div>
    </div>
</template>

<style lang="scss" scoped>
.mine-password-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.mine-password-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.mine-password-page__form {
    max-width: 640px;
    margin-bottom: 0;
}

.mine-password-page__form-el {
    width: 100%;
}

.mine-password-page__actions {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-md);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}
</style>
