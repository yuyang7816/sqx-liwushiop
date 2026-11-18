<template>
    <el-dialog :title="configData.title + configData.afterTitle" v-model="configData.open" width="800px"
        :close-on-click-modal="false" append-to-body destroy-on-close @close="handleClose" class="edit-data-dialog">
        <div class="dialog-container">
            <el-form :model="dataContainer.form" ref="FormElRef" :inline="true" :rules="dataContainer.rules"
                label-width="100px">
                <el-row :gutter="0">
                    <el-col :span="18" :xs="18">
                        <el-form-item label="用户名" prop="username">
                            <el-input v-model="dataContainer.form.username" placeholder="请输入" :disabled="configData.isShow"
                                clearable />
                        </el-form-item>
                    </el-col>
                    <el-col :span="18" :xs="18">
                        <el-form-item label="密码" prop="password">
                            <el-input v-model="dataContainer.form.password" placeholder="请输入"
                                :disabled="configData.isShow" clearable />
                        </el-form-item>
                    </el-col>
                    <el-col :span="18" :xs="18">
                        <el-form-item label="确认密码" prop="comfirmPassword">
                            <el-input v-model="dataContainer.form.comfirmPassword" placeholder="请输入"
                                :disabled="configData.isShow" clearable />
                        </el-form-item>
                    </el-col>
                    <el-col :span="18" :xs="18">
                        <el-form-item label="邮箱" prop="email">
                            <el-input v-model="dataContainer.form.email" placeholder="请输入" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="18" :xs="18">
                        <el-form-item label="手机号" prop="mobile">
                            <el-input v-model="dataContainer.form.mobile" placeholder="请输入" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="18" :xs="18">
                        <el-form-item label="角色标识" prop="sign">
                            <el-checkbox-group v-model="dataContainer.form.roleIdList">
                                <el-checkbox v-for="role in dataContainer.roleList" :key="role.roleId" :label="role.roleId">{{ role.roleName }}</el-checkbox>
                            </el-checkbox-group>
                        </el-form-item>
                    </el-col>
                    
                    <el-col :span="18" :xs="18">
                        <el-form-item label="状态" prop="status">
                            <el-radio-group v-model="dataContainer.form.status" class="ml-4">
                                <el-radio :label="1" size="large">启用</el-radio>
                                <el-radio :label="0" size="large">禁用</el-radio>
                            </el-radio-group>
                            <!-- <el-switch v-model="dataContainer.form.status" :disabled="configData.isShow"
                                active-text="启用" inactive-text="禁用" /> -->
                        </el-form-item>
                    </el-col>
                    
                </el-row>
            </el-form>
        </div>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="
                    () => {
                        dataContainer.closeType = 'cancel';
                        configData.open = false;
                    }
                ">
                    取消
                </el-button>
                <el-button v-if="!configData.isShow" type="primary" @click="handleSubmit">
                    提交
                </el-button>
            </div>
        </template>
    </el-dialog>
</template>

<script>
/**
 * 数据编辑对话框
 * 使用外部调用的方式向内部传递数据
 * 使用promise的形式向外部通知状态
 */
import { defineComponent, ref, getCurrentInstance, reactive, nextTick } from 'vue';
import { mergeObjProperty } from '@/common/otherTools';
import { getDictItem, initDataByConfig } from '@/common/otherTools';
import { verifiedData } from '@/common/verifiedTools';
import { messageError } from '@/action/messagePrompt.js';
import http from '@/http/request.js';
//配置信息，初始化时使用
const configMap = {
    open: {
        default: false,
    },
    title: {
        default: '用户数据',
    },
    afterTitle: {
        default: '',
    },
    isShow: {
        //是否只是展示
        default: false,
    },
};

export default defineComponent({
    name: 'EditDataDialog',
    components: {},
    props: {
        refreshDataList:{
            type: Object,
            default: () => {
                return {};
            },
        }
    },
    setup(props, { emit }) {
        const configData = reactive({});
        const FormElRef = ref(null); //组件实例
        const dataContainer = reactive({
            loading: false,
            closeType: 'close', //关闭时的类型，是由确认取消按钮关闭的还是其他地方关闭的 confirm cancel
            resolve: undefined, //返给外部promise的回调
            reject: undefined,
            form: {
                id: 0,
                username: '',
                password: '',
                comfirmPassword: '',
                salt: '',
                email: '',
                mobile: '',
                inviterCode:'',
                roleIdList: [],
                status: 1
            },
            roleList:[],
            rules: {
                username: [{ required: true, message: '请输入用户名称', trigger: 'blur' }],
                password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
                comfirmPassword: [{ required: true, message: '确认密码不能为空', trigger: 'blur' }],
                email: [{ required: true, message: '邮箱不能为空', trigger: 'blur' }],
                mobile: [{ required: true, message: '手机号不能为空', trigger: 'blur' },],
            },
        });
        const otherDataContainer = {
            castParams: {}, //向外部传递的参数
        };
        initDataByConfig(configData, {}, configMap);
        /**
         * 对话框关闭时的回调
         * 根据行为类型来判断调用那个回调函数
         *  */
        function handleClose() {
            if (dataContainer.closeType == 'confirm') {
                dataContainer.resolve(otherDataContainer.castParams);
            } else {
                dataContainer.reject(dataContainer.closeType, otherDataContainer.castParams);
            }
        }
        /**
         * 初始化数据（外部调用）
         * 返回一个promise，以提供直接的回调
         *  */
        function initData(show = true, data = {}, option = {}) {
            initDataByConfig(configData, option, configMap);
            dataContainer.closeType = 'close';
            dataContainer.loading = false;
            dataContainer.form = {};
            otherDataContainer.castParams = {};
            configData.open = show;
            if(data.userId){
                
                console.log('--------',)
                
                selectInfo(data.userId);
            }
            nextTick(() => {
                dataContainer.form = data;
                // console.log('获取管理员信息',dataContainer.form)
                // getDataInfo();
                selectList();
            });
            return new Promise((r, j) => {
                dataContainer.resolve = r;
                dataContainer.reject = j;
            });
        }
        /** 获取数据详细 */
        function getDataInfo() {
            dataContainer.form = {};
        }
        /** 提交数据 */
        function handleSubmit() {
             http.post(`sys/user/${!dataContainer.form.userId ? 'save' : 'update'}`, dataContainer.form, {
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                            message: '操作成功',
                            type: 'success',
                            onClose: () => {
                            }
                        })
                    otherDataContainer.castParams = {
                        name: '数据保存成功了，向外部通知222222222222222',
                    };
                    props.refreshDataList;
                    dataContainer.closeType = 'confirm';
                    configData.open = false;
                    emit('closeEditDialog')
                }else{
                    
                    messageError(res.msg);
                }
            })


            /** 使用组件自带方法验证数据 */
            // if (!FormElRef.value) return;
            // FormElRef.value.validate((valid, e) => {
            //     if (e) {
            //         /** 打印报错信息 */
            //         let msg = e[Object.keys(e)[0]][0].message;
            //         messageError(msg);
            //     }
            //     if (!valid) return;
            //     setTimeout(() => {
            //         otherDataContainer.castParams = {
            //             name: '数据保存成功了，向外部通知',
            //         };
            //         dataContainer.closeType = 'confirm';
            //         configData.open = false;
            //     }, 1000);
            // });
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
        /** 获取角色数据 */
        function selectList() {
            http.get('sys/role/select', {
                params: {}
            }, {
            }).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    dataContainer.roleList = res.data.list
                }
            })
        }
        /** 获取管理员 数据 */
        function selectInfo(id) {
            http.get('sys/user/info/'+id, {
                params: {}
            }, {
            }).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    dataContainer.form.username = res.data.user.username
                    dataContainer.form.salt = res.data.user.salt
                    dataContainer.form.email = res.data.user.email
                    dataContainer.form.mobile = res.data.user.mobile
                    dataContainer.form.inviterCode = res.data.user.inviterCode
                    dataContainer.form.roleIdList = res.data.user.roleIdList
                    dataContainer.form.status = res.data.user.status
                }
            })
        }
        return {
            configData,
            initData,
            dataContainer,
            handleClose,
            getDataInfo,
            handleSubmit,
            FormElRef,
            validData,
            selectList,
            selectInfo,
        };
    },
});
</script>

<style lang="scss" scoped>
.edit-data-dialog {
    .dialog-container {
        padding: 15px 15px 0 15px;
        box-sizing: border-box;
    }
}
</style>
