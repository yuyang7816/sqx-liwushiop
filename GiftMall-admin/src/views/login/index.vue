<template>
    <div class="login-page">
        <div class="login-page__bg">
            <span class="login-page__orb login-page__orb--1"></span>
            <span class="login-page__orb login-page__orb--2"></span>
            <span class="login-page__orb login-page__orb--3"></span>
        </div>
        <div class="login-page__card">
            <div class="login-page__brand">
                <div class="login-page__brand-pattern" aria-hidden="true"></div>
                <div class="login-page__brand-content">
                    <span class="login-page__brand-badge">管理后台</span>
                    <el-carousel :interval="7000" arrow="never" indicator-position="outside">
                        <el-carousel-item>
                            <div class="login-page__brand-inner">
                                <el-image class="login-page__brand-img" :src="dataContainer.img.img_7" fit="contain" />
                                <p class="login-page__brand-desc">省钱兄礼物商城 · 管理后台</p>
                            </div>
                        </el-carousel-item>
                    </el-carousel>
                </div>
            </div>
            <div class="login-page__form-wrap">
                <div class="login-page__form">
                    <div class="login-page__header">
                        <h1 class="login-page__title">欢迎登录</h1>
                        <p class="login-page__subtitle">省钱兄礼物商城系统</p>
                    </div>

                    <div class="login-page__field">
                        <label class="login-page__label">账号</label>
                        <el-input
                            clearable
                            :prefix-icon="User"
                            @input="
                                () => {
                                    toTrim(dataContainer.form, 'username');
                                    palindrome(dataContainer.form, 'username');
                                }
                            "
                            placeholder="请输入账号"
                            @keyup.enter="onLogin"
                            v-model="dataContainer.form.username"
                        />
                    </div>
                    <div class="login-page__field">
                        <label class="login-page__label">密码</label>
                        <el-input
                            type="password"
                            clearable
                            :prefix-icon="Lock"
                            @keyup.enter="onLogin"
                            show-password
                            placeholder="请输入密码"
                            @input="
                                () => {
                                    toTrim(dataContainer.form, 'password');
                                }
                            "
                            v-model="dataContainer.form.password"
                        />
                    </div>
                    <div class="login-page__field login-page__field--code">
                        <label class="login-page__label">验证码</label>
                        <div class="login-page__code-row">
                            <el-input
                                v-model="dataContainer.form.captcha"
                                :prefix-icon="PictureRounded"
                                placeholder="请输入验证码"
                                clearable
                                @keyup.enter="onLogin"
                            />
                            <el-image
                                class="login-page__captcha"
                                :src="dataContainer.img.img_3"
                                fit="fill"
                                title="点击刷新验证码"
                                @click="captchaImgS"
                            />
                        </div>
                    </div>
                    <div class="login-page__actions">
                        <el-button
                            class="nf-login-submit login-bt"
                            type="primary"
                            v-if="!dataContainer.form.idU"
                            :loading="dataContainer.loading"
                            @click="onLogin"
                        >
                            登 录
                        </el-button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
/**
 * 登录页面
 */
import axios from 'axios';
import { defineComponent, reactive, getCurrentInstance } from 'vue';
import publicApi from '@/http/public.js';
import userApi from '@/http/user.js';
import http from '@/http/request.js';
import { messageSuccess, messageError } from '@/action/messagePrompt';
import { useRoute, useRouter } from 'vue-router';
import { throttleFn_1 as throttleFn } from '@/common/debounceAndThrottle';
import { verifiedData } from '@/common/verifiedTools';
import { Select, ArrowRightBold, SemiSelect, User, Lock, PictureRounded } from '@element-plus/icons-vue';
import { userDataStore } from '@/store/user';
import { getTypeOf } from '@/common/otherTools';
import img_2 from '@/assets/login-imgs/login-bg.svg';
import img_3 from '@/assets/login-imgs/code.svg';
import img_4 from '@/assets/login-imgs/login-bg-1.svg';
import img_5 from '@/assets/login-imgs/login-bg-2.svg';
import img_6 from '@/assets/login-imgs/login-bg-3.svg';
import img_7 from '@/assets/login-imgs/login-bg-4.png';

export default defineComponent({
    name: 'LoginView',
    components: {
        Select,
        ArrowRightBold,
        SemiSelect,
        User,
        Lock,
        PictureRounded,
        getCurrentInstance,
    },
    setup() {
        const proxy = getCurrentInstance();
        const userData = userDataStore();
        const router = useRouter();
        const route = useRoute();
        const dataContainer = reactive({
            name: import.meta.env.VITE_APP_name,
            form: {
                username: '',
                password: '',
                captcha: '',
                uuid: '',
            },
            loading: false,
            captchaSvg: '',
            captchaId: '',
            loading_1: false,
            img: {
                img_2,
                img_3,
                img_4,
                img_5,
                img_6,
                img_7,
            },
        });
        /** 验证信息 */
        function validBase(data) {
            const failData = verifiedData(data, {
                username: {
                    label: '',
                    validate(value, option) {
                        let label = 'username:账号';
                        var regex = /^[A-Za-z0-9-_]+$/;
                        if (!value && value !== 0) {
                            option.label = label + ' 不能为空';
                            return false;
                        }
                        if (getTypeOf(value) !== '[object String]') {
                            option.label = label + ' 必须是一个字符串';
                            return false;
                        }
                        if (value.length > 70) {
                            option.label = label + ' 字符长度长度超出70';
                            return false;
                        }
                        let isPass = regex.test(value);
                        if (!isPass) {
                            option.label = label + ' 只能是英文，数字，下划线，中划线';
                            return false;
                        }
                        return true;
                    },
                },
                password: {
                    label: '',
                    validate(value, option) {
                        let label = 'password:密码';
                        if (!value && value !== 0) {
                            option.label = label + ' 不能为空';
                            return false;
                        }
                        if (getTypeOf(value) !== '[object String]') {
                            option.label = label + ' 必须是一个字符串';
                            return false;
                        }
                        if (value.length > 300) {
                            option.label = label + ' 字符长度长度超出300';
                            return false;
                        }
                        return true;
                    },
                },
                captcha: {
                    label: '',
                    validate(value, option) {
                        let label = 'captcha:验证码';
                        if (!value && value !== 0) {
                            option.label = label + ' 不能为空';
                            return false;
                        }
                        if (getTypeOf(value) !== '[object String]') {
                            option.label = label + ' 必须是一个字符串';
                            return false;
                        }
                        if (value.length > 7) {
                            option.label = label + ' 字符长度长度超出7';
                            return false;
                        }
                        return true;
                    },
                },
            });
            return failData;
        }
        /** 获取验证码 */
        const getCaptcha = throttleFn(function () {
            if (dataContainer.loading_1) return;
            dataContainer.loading_1 = true;
            publicApi
                .getCaptcha()
                .then((res) => {
                    const data = res.data || {};
                    dataContainer.captchaId = data.id;
                    dataContainer.captchaSvg = data.svg;
                    dataContainer.form.captcha = '';
                })
                .catch(() => {
                    messageError('验证码获取失败');
                })
                .finally(() => {
                    dataContainer.loading_1 = false;
                });
        }, 700);
        getCaptcha();
        /** 登录操作 */
        const onLogin = throttleFn(function (otherParmas) {
            let routeParams = route.query || {};

            try {
                if (dataContainer.loading) return;
                const verifiedData = validBase(dataContainer.form);
                if (verifiedData) {
                    messageError('参数错误！' + verifiedData[0].label);
                    return;
                }
                dataContainer.loading = true;
                const params = {
                    ...dataContainer.form,
                    ...otherParmas,
                };
                params.captchaId = dataContainer.captchaId;
                http.post('sys/login', dataContainer.form, {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                }).then((res) => {
                    console.log(res);
                    if (res.code == 0) {
                        localStorage.setItem('token', res.data.token);
                        userData.setUserInfo({
                            token: res.data.token || '',
                        });
                        messageSuccess('登录成功');
                        dataContainer.loading = false;
                        router.push({
                            path: '/home',
                        });
                        userStore();
                    } else {
                        dataContainer.loading = false;
                        messageError('登录失败：' + res.msg);
                        captchaImgS();
                    }
                });
            } catch {
            }
        }, 700);

        /** 去除首尾空格 */
        function toTrim(data, p) {
            let str = data[p];
            str = (str || '').replace(/^\s+|\s+$/g, '');
            data[p] = str;
        }
        /** 去除特殊符号 */
        function palindrome(data, p) {
            let str = data[p];
            str = (str || '').replace(
                /[`:~!#$%^&*() \+ =<>?"{}|, \/ ;' \\ [ \] ~！#￥%……&*（） \+ ={}|《》？：“”【】、；‘’，。、]/g,
                '',
            );
            data[p] = str;
        }

        /** 用户信息*/
        function userStore() {
            http.get('sys/user/info', {}).then((res) => {
                console.log(res);
            });
        }
        /** 获取验证码*/
        function captchaImgS() {
            const uuid = userApi.getUUID();
            dataContainer.form.uuid = uuid;
            dataContainer.img.img_3 =
                'https://lwscadmin.xianmxkj.com/sqx_fast/captcha.jpg?uuid=' + dataContainer.form.uuid;
            return;
        }
        function arrayBufferToBase64(buffer) {
            var binary = '';
            var bytes = new Uint8Array(buffer);
            var len = bytes.byteLength;
            for (var i = 0; i < len; i++) {
                binary += String.fromCharCode(bytes[i]);
            }
            return window.btoa(binary);
        }
        captchaImgS();
        return {
            dataContainer,
            onLogin,
            getCaptcha,
            toTrim,
            palindrome,
            captchaImgS,
            User,
            Lock,
            PictureRounded,
        };
    },
});
</script>

<style lang="scss" scoped>
.login-page {
    width: 100vw;
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    padding: var(--nf-space-lg);
    box-sizing: border-box;
    overflow: hidden;
}

.login-page__bg {
    position: absolute;
    inset: 0;
    background: linear-gradient(145deg, #fef0f5 0%, var(--nf-page-bg) 45%, #fde5ed 100%);
    z-index: 0;
}

.login-page__orb {
    position: absolute;
    border-radius: 50%;
    pointer-events: none;

    &--1 {
        width: 520px;
        height: 520px;
        background: radial-gradient(circle, rgba(239, 39, 106, 0.1) 0%, transparent 68%);
        top: -160px;
        right: -100px;
    }

    &--2 {
        width: 360px;
        height: 360px;
        background: radial-gradient(circle, rgba(211, 16, 81, 0.06) 0%, transparent 70%);
        bottom: -80px;
        left: -60px;
    }

    &--3 {
        width: 200px;
        height: 200px;
        background: radial-gradient(circle, rgba(239, 39, 106, 0.05) 0%, transparent 70%);
        top: 40%;
        left: 12%;
    }
}

.login-page__card {
    position: relative;
    z-index: 1;
    display: flex;
    width: 100%;
    max-width: 960px;
    min-height: 540px;
    background: #fff;
    border-radius: var(--nf-radius-lg);
    box-shadow: 0 12px 40px rgba(26, 36, 51, 0.12);
    overflow: hidden;
    border: 1px solid rgba(232, 234, 237, 0.8);
}

.login-page__brand {
    flex: 1;
    position: relative;
    background: linear-gradient(155deg, var(--nf-brand) 0%, var(--nf-brand-deep) 100%);
    display: flex;
    align-items: center;
    justify-content: center;
    padding: var(--nf-space-xl);
    overflow: hidden;
}

.login-page__brand-pattern {
    position: absolute;
    inset: 0;
    opacity: 0.12;
    background-image: radial-gradient(circle at 20% 30%, #fff 1px, transparent 1px),
        radial-gradient(circle at 70% 60%, #fff 1px, transparent 1px);
    background-size: 32px 32px, 48px 48px;
}

.login-page__brand-content {
    position: relative;
    z-index: 1;
    width: 100%;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.login-page__brand-badge {
    display: inline-block;
    padding: 4px 12px;
    margin-bottom: var(--nf-space-lg);
    font-size: 12px;
    font-weight: 600;
    letter-spacing: 1px;
    color: #fff;
    background: rgba(255, 255, 255, 0.18);
    border: 1px solid rgba(255, 255, 255, 0.35);
    border-radius: 20px;
    backdrop-filter: blur(4px);
}

.login-page__brand {
    :deep(.el-carousel) {
        width: 100%;

        .el-carousel__container {
            height: 280px;
        }

        .el-carousel__indicators {
            .el-carousel__button {
                background: rgba(255, 255, 255, 0.4);
                width: 24px;
                height: 4px;
                border-radius: 2px;
            }

            .is-active .el-carousel__button {
                background: #fff;
                width: 32px;
            }
        }
    }
}

.login-page__brand-inner {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    height: 100%;
    text-align: center;
}

.login-page__brand-img {
    width: 180px;
    height: 180px;
    margin-bottom: var(--nf-space-md);
    filter: drop-shadow(0 6px 16px rgba(0, 0, 0, 0.18));
}

.login-page__brand-desc {
    color: rgba(255, 255, 255, 0.92);
    font-size: 15px;
    margin: 0;
    letter-spacing: 0.5px;
    line-height: 1.6;
}

.login-page__form-wrap {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
    padding: var(--nf-space-xl);
    background: #fff;
}

.login-page__form {
    width: 100%;
    max-width: 340px;
}

.login-page__header {
    text-align: center;
    margin-bottom: var(--nf-space-xl);
}

.login-page__logo-row {
    display: flex;
    justify-content: center;
    margin-bottom: var(--nf-space-md);
}

.login-page__logo {
    width: 52px;
    height: 52px;
}

.login-page__title {
    font-size: 26px;
    font-weight: 700;
    color: var(--nf-text-primary);
    margin: 0 0 6px;
    letter-spacing: 0.5px;
}

.login-page__subtitle {
    font-size: 13px;
    color: var(--nf-text-secondary);
    margin: 0;
}

.login-page__label {
    display: block;
    font-size: 13px;
    font-weight: 500;
    color: var(--nf-text-primary);
    margin-bottom: 6px;
}

.login-page__field {
    margin-bottom: var(--nf-space-md);

    :deep(.el-input__wrapper) {
        border-radius: var(--nf-radius-sm);
        box-shadow: 0 0 0 1px var(--nf-border) inset;
        padding: 6px 12px;
        min-height: 42px;
        transition: box-shadow 0.2s, background-color 0.2s;

        &:hover {
            box-shadow: 0 0 0 1px var(--nf-brand) inset;
        }

        &.is-focus {
            box-shadow: 0 0 0 1px var(--nf-brand) inset, 0 0 0 3px rgba(239, 39, 106, 0.1);
        }
    }

    :deep(.el-input__prefix) {
        color: var(--nf-text-secondary);
    }

    :deep(.el-input__wrapper.is-focus .el-input__prefix) {
        color: var(--nf-brand);
    }

    &--code {
        .login-page__code-row {
            display: flex;
            align-items: center;
            gap: var(--nf-space-sm);

            :deep(.el-input) {
                flex: 1;
            }
        }
    }
}

.login-page__captcha {
    width: 108px;
    height: 42px;
    border-radius: var(--nf-radius-sm);
    border: 1px solid var(--nf-border);
    cursor: pointer;
    flex-shrink: 0;
    transition: border-color 0.2s, box-shadow 0.2s;

    &:hover {
        border-color: var(--nf-brand);
        box-shadow: 0 0 0 2px rgba(239, 39, 106, 0.1);
    }
}

.login-page__actions {
    margin-top: var(--nf-space-lg);

    :deep(.nf-login-submit) {
        width: 100%;
        height: 44px;
        border-radius: var(--nf-radius-sm);
        font-size: 15px;
        font-weight: 600;
        letter-spacing: 2px;
        transition: transform 0.15s, box-shadow 0.2s;

        &:not(.is-loading):hover {
            transform: translateY(-1px);
            box-shadow: 0 4px 12px rgba(239, 39, 106, 0.35);
        }

        &:not(.is-loading):active {
            transform: translateY(0);
        }
    }
}

@media (max-width: 768px) {
    .login-page__brand {
        display: none;
    }

    .login-page__card {
        min-height: auto;
        max-width: 420px;
    }

    .login-page__form-wrap {
        padding: var(--nf-space-lg);
    }
}
</style>
