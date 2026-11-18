<template>
    <el-dialog :title="configData.title + configData.afterTitle" v-model="configData.open" width="800px"
        :close-on-click-modal="false" append-to-body destroy-on-close @close="handleClose" class="edit-data-dialog">
        <div class="dialog-container">
            <el-form :model="dataContainer.form" ref="FormElRef" :rules="dataContainer.rules" label-width="80px">
                <el-form-item label="角色名称" prop="roleName">
                    <el-input v-model="dataContainer.form.roleName" placeholder="请输入" clearable />
                </el-form-item>
                <el-form-item label="角色描述" prop="content">
                    <el-input v-model="dataContainer.form.remark" placeholder="请输入" />
                </el-form-item>
                <el-form-item label="菜单权限" prop="menu">
                    <el-tree style="width: 100%;" ref="menuListTree" v-loading="dataContainer.loading"
                        :data="dataContainer.menuList" node-key="menuId"
                        :default-checked-keys="dataContainer.form.menuIdList" :props="dataContainer.menuListTreeProps"
                        show-checkbox default-expand-all />
                </el-form-item>
            </el-form>
        </div>
        <template #footer>
            <div class="dialog-footer">
                <el-button @click="() => { dataContainer.closeType = 'cancel'; configData.open = false; }">
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
import userApi from '@/http/user.js';
import { ElTree } from 'element-plus';
//配置信息，初始化时使用
const configMap = {
    open: {
        default: false,
    },
    title: {
        default: '角色数据',
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
    setup(props, { emit }) {
        const configData = reactive({});
        const FormElRef = ref(null); //组件实例
        const menuListTree = ref(null);
        const dataContainer = reactive({
            loading: false,
            closeType: 'close', //关闭时的类型，是由确认取消按钮关闭的还是其他地方关闭的 confirm cancel
            resolve: undefined, //返给外部promise的回调
            reject: undefined,
            form: {
                menuIdList: [],
                roleName: '',
                remark: '',
                roleId: '',
            },
            menuList: [],
            menuListTreeProps: {
                label: 'name',
                children: 'children'
            },
            rules: {
                roleName: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
            },
            tempKey: -666666,
            treeData: [
                {
                    id: 1,
                    label: 'Level one 1',
                    children: [
                        {
                            id: 3,
                            label: 'Level two 2-1',
                            children: [
                                {
                                    id: 4,
                                    label: 'Level three 3-1-1',
                                },
                                {
                                    id: 5,
                                    label: 'Level three 3-1-2',
                                    disabled: true,
                                },
                            ],
                        },
                        {
                            id: 2,
                            label: 'Level two 2-2',
                            disabled: true,
                            children: [
                                {
                                    id: 6,
                                    label: 'Level three 3-2-1',
                                },
                                {
                                    id: 7,
                                    label: 'Level three 3-2-2',
                                    disabled: true,
                                },
                            ],
                        },
                    ],
                },
            ],
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
            nextTick(() => {
                dataContainer.form = data;
                selectList();
                // getDataInfo();
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
            console.log(dataContainer.form)
            console.log(menuListTree.value.getCheckedKeys(), [menuListTree.tempKey], menuListTree.value.getHalfCheckedKeys())
            http.post(`sys/role/${!dataContainer.form.roleId ? 'save' : 'update'}`, {
                'roleName': dataContainer.form.roleName,
                'remark': dataContainer.form.remark,
                'roleId': dataContainer.form.roleId || undefined,
                'menuIdList': [].concat(menuListTree.value.getCheckedKeys(), [dataContainer.tempKey], menuListTree.value.getHalfCheckedKeys()),
            }, {
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
                    dataContainer.closeType = 'confirm';
                    configData.open = false;
                    emit('closeEditDialog')
                } else {
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
        /** 数据验证 */
        /** 外部可调用 */
        function validData(data) {
            const failData = verifiedData(data, {
                roleName: {
                    msg: '名称 不能为空',
                    validate(value, option) {
                        if (!value && value !== 0) return false;
                        return true;
                    },
                },
            });
            return failData;
        }
        /** 获取角色全部权限 */
        function selectList() {
            dataContainer.loading = true;
            http.get('sys/menu/list', {
                params: {}
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.menuList = userApi.treeDataTranslate(res, 'menuId')
                menuListTree.value.getCheckedKeys(false)
                if (dataContainer.form.roleId) {
                    selectInfo(dataContainer.form.roleId)
                }
                dataContainer.loading = false;
            })
        }
        /** 获取角色权限 信息*/
        function selectInfo(id) {
            http.get('sys/role/info/' + id, {
                params: {}
            }, {
            }).then((data) => {
                if (data.code == 0) {
                    dataContainer.form.roleName = data.data.role.roleName
                    dataContainer.form.remark = data.data.role.remark
                    dataContainer.form.roleId = data.data.role.roleId
                    const idx = data.data.role.menuIdList.indexOf(dataContainer.tempKey)
                    if (idx !== -1) {
                        data.data.role.menuIdList.splice(idx, data.data.role.menuIdList.length - idx)
                    }
                    // dataContainer.form.menuIdList = data.role.menuIdList
                    menuListTree.value.setCheckedKeys(data.data.role.menuIdList)
                    console.log(dataContainer.form.menuIdList)
                    // console.log(menuListTree.value)
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
            menuListTree,
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
