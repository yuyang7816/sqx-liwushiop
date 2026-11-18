<template>
    <el-dialog :title="configData.title + configData.afterTitle" v-model="configData.open" width="800px"
        :close-on-click-modal="false" append-to-body destroy-on-close @close="handleClose" class="edit-data-dialog">
        <div class="dialog-container">
            <el-form :model="dataContainer.form" ref="FormElRef" :inline="true" :rules="dataContainer.rules"
                label-width="150px">
                <el-form-item label="类型" prop="type">
                    <el-radio-group v-model="dataContainer.form.type">
                        <el-radio v-for="(type, index) in dataContainer.typeList" :label="index" :key="index">{{ type
                            }}</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="菜单名称" prop="name">
                    <el-input v-model="dataContainer.form.name" style="width: 200px;" placeholder="请输入菜单名称" clearable />
                </el-form-item>
                <el-form-item label="上级菜单" prop="parentName">
                    <el-tree-select v-model="dataContainer.form.parentName" ref="menuListTree" check-strictly
                        :data="dataContainer.menuList" :default-expand-all="true" :render-after-expand="false"
                        :props="dataContainer.menuListTreeProps" style="width: 200px;" @node-click="menuListTreeCurrentChangeHandle"/>
                </el-form-item>
                <el-form-item label="菜单路由" prop="url" v-if="dataContainer.form.type === 1">
                    <el-input style="width: 200px" v-model="dataContainer.form.url" clearable  />
                </el-form-item>
                <el-form-item label="授权标识" prop="perms" v-if="dataContainer.form.type !== 0">
                    <el-input style="width: 200px" v-model="dataContainer.form.perms" clearable />
                </el-form-item>
                <el-form-item label="排序" prop="sort" v-if="dataContainer.form.type !== 2">
                    <el-input-number style="width: 200px" v-model="dataContainer.form.orderNum" :min="1" :max="999" clearable />
                </el-form-item>
                <el-form-item label="菜单图标"  prop="icon" v-if="dataContainer.form.type !== 2">
                 
                        
                            <el-popover ref="iconListPopover" placement="bottom-start" trigger="click" popper-style="width: 730px;height: 320px;"
                                popper-class="mod-menu__icon-popover" :visible="dataContainer.visible">
                                <DefinScrollbar  :showUpBt="true" style="width: 700px;height: 300px;">
                                    <div class="page-container icon-list-view">
                                        <div class="container">
                                            <div
                                                class="item"
                                                v-for="(item, index) in dataContainer.iconList"
                                                :key="index" 
                                            >
                                                <SvgIcon :style="'width:23px;height:23px;'" :name="item.name"  @click="ClickBtn(item)"></SvgIcon>
                                                
                                            </div>
                                            
                                        </div>
                                    </div>
                                </DefinScrollbar>
                                <template #reference>
                                     <el-input v-model="dataContainer.form.icon" v-popover:iconListPopover :readonly="true"
                                placeholder="菜单图标名称" class="icon-list__input" @click="dataContainer.visible = !dataContainer.visible"></el-input>
                                </template>
                            </el-popover>
                        <!-- <el-col :span="2" class="icon-list__tips">
                            <el-tooltip placement="top" effect="light">
                                <div slot="content">全站推荐使用SVG Sprite, 详细请参考:<a
                                        href="//github.com/daxiongYang/sz-fast-vue/blob/master/src/icons/index.js"
                                        target="_blank">icons/index.js</a>描述</div>
                                <i class="el-icon-warning"></i>
                            </el-tooltip>
                        </el-col> -->
                </el-form-item>
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
                <el-button v-if="!configData.isShow" type="primary" :loading="dataContainer.loading"
                    @click="handleSubmit">
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
// import SvgIcon from '@/components/svgIcon/index.vue';
// import IconSlect from '@/components/iconSlect.vue';
import http from '@/http/request.js';
import userApi from '@/http/user.js';

import SvgIcon from '@/components/svgIcon/index.vue';
import { iconList } from '@/components/svgIcon/common.js';
import DefinScrollbar from '@/components/definScrollbar.vue';
import { copyValue } from '@/common/otherTools';
import { messageSuccess } from '@/action/messagePrompt';

//配置信息，初始化时使用
const configMap = {
    open: {
        default: false,
    },
    title: {
        default: '按钮权限数据',
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
    components: {
        // SvgIcon,
        // IconSlect,
         SvgIcon,
        DefinScrollbar,
    },
    setup(props, { emit }) {

        const configData = reactive({});
        const FormElRef = ref(null); //组件实例
        const menuListPopover = ref(null);
        const menuListTree = ref(null)
        const parentName = ref([])
        
        const dataContainer = reactive({
             iconList: iconList,
            loading: false,
            closeType: 'close', //关闭时的类型，是由确认取消按钮关闭的还是其他地方关闭的 confirm cancel
            resolve: undefined, //返给外部promise的回调
            reject: undefined,
            form: {
                
                menuId: '',
                type: '',
                name: '',
                parentId: 0,
                url: '',
                perms: '',
                orderNum: 0,
                icon: '',
                parentName: '222222222',
            },
            rules: {
                name: [{ required: true, message: '请输入名称', trigger: 'blur' }],
                parentName: [{ required: true, message: '上级菜单不能为空', trigger: 'change' }],
            },
            menuList: [],
            type: 1,
            typeList: ['目录', '菜单', '按钮'],
            
            children: 'children',
            menuListTreeProps: {
                label: 'name',
                children: 'children'
            },
            visible:false,
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
            http.post(`sys/menu/${!dataContainer.form.menuId ? 'save' : 'update'}`, {
                'name':dataContainer.form.name,
                'parentId':dataContainer.form.parentId,
                'menuId':dataContainer.form.menuId || undefined,
                'type':dataContainer.form.type,
                'url':dataContainer.form.url,
                'perms':dataContainer.form.perms,
                'orderNum':dataContainer.form.orderNum,
                'icon':dataContainer.form.icon,
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
                }else{
                    messageError(res.msg);
                }
            })


            // /** 使用组件自带方法验证数据 */
            // if (!FormElRef.value) return;
            // FormElRef.value.validate((valid, e) => {
            //     if (e) {
            //         /** 打印报错信息 */
            //         let msg = e[Object.keys(e)[0]][0].message;
            //         messageError(msg);
            //     }
            //     if (!valid) return;
            //     dataContainer.loading = true;
            //     setTimeout(() => {
            //         /** 没有后台，所以直接将数据向外部抛出 */
            //         otherDataContainer.castParams = dataContainer.form;
            //         dataContainer.closeType = 'confirm';
            //         configData.open = false;
            //         dataContainer.loading = false;
            //     }, 100);
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
        /** 菜单树选中 */
        function menuListTreeCurrentChangeHandle(value) {
            console.log(value.menuId,'------------',value.name)
            dataContainer.form.parentId = value.menuId
            dataContainer.form.parentName = value.name
            parentName = []
            parentName.push(value.name) 
        }
        /** 图标选中 */
        function iconActiveHandle(iconName) {
            dataContainer.form.icon = iconName
        }
        /** 获取菜单全部权限 */
        function selectList() {
            http.get('sys/menu/select', {
                params: {}
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.menuList = userApi.treeDataTranslate(res.data.menuList, 'menuId')
                menuListTree.value.getCheckedKeys(false)
                // menuListTreeSetCurrentNode()
                if(dataContainer.form.menuId){
                    selectInfo(dataContainer.form.menuId)
                }
            })
        }
         /** 获取菜单 信息*/
        function selectInfo(id) {
            http.get('sys/menu/info/'+id, {
                    params: {}
                }, {
                }).then((data) => {
                    if(data.code==0){
                        dataContainer.form.menuId = data.data.menu.menuId
                        dataContainer.form.type = data.data.menu.type
                        dataContainer.form.name = data.data.menu.name
                        dataContainer.form.parentId = data.data.menu.parentId
                        dataContainer.form.url = data.data.menu.url
                        dataContainer.form.perms = data.data.menu.perms
                        dataContainer.form.orderNum = data.data.menu.orderNum
                        dataContainer.form.icon = data.data.menu.icon
                        menuListTreeSetCurrentNode();
                    }
                })
        }
        // 菜单树设置当前选中节点
        function  menuListTreeSetCurrentNode() {
             menuListTree.value.setCurrentKey(dataContainer.form.parentId)
           for(var i in dataContainer.menuList){
            if(dataContainer.form.parentId==dataContainer.menuList[i].menuId){
                dataContainer.form.parentName = dataContainer.menuList[i].name
            }
           }

           
            // dataContainer.form.parentName = (menuListTree.value.getCurrentNode() || {})['name']
            // console.log('======',menuListTree.value.setCurrentKey(dataContainer.form.parentId))
            
        }
         /** copy事件 */
        function ClickBtn(item) {
            console.log(item,'----')
            dataContainer.form.icon = item.name
            dataContainer.visible = !dataContainer.visible
//             let msg = `<SvgIcon
// :style="'width:23px;height:23px;'"
// name="${item.name}"></SvgIcon>`;
//             copyValue(msg);
//             messageSuccess('复制成功');
        }
        
        return {
            configData,
            initData,
            dataContainer,
            handleClose,
            getDataInfo,
            handleSubmit,
            FormElRef,
            menuListPopover,
            validData,
            menuListTreeCurrentChangeHandle,
            iconActiveHandle,
            selectList,
            menuListTree,
            selectInfo,
            menuListTreeSetCurrentNode,
            ClickBtn,
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


.icon-list__input {
    >.el-input__inner {
        cursor: pointer;
    }
}



.icon-list__tips {
    font-size: 18px;
    text-align: center;
    color: #e6a23c;
    cursor: pointer;
}
.icon-list-view {
    display: flex;
    flex-direction: column;
    padding: var(--page-gap);
    box-sizing: border-box;
    color: var(--text-color-2);
    > .container {
        padding: 15px;
        box-sizing: border-box;
        background-color: rgba(0, 0, 0, 0.43);
        border-radius: 5px;
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(100px, 1fr));
        grid-gap: 0 0;
        > .item {
            width: 100%;
            display: flex;
            flex-direction: column;
            justify-content: flex-start;
            align-items: center;
            cursor: pointer;
            transition: all 0.2s;
            border-radius: 5px;
            padding: 15px;
            box-sizing: border-box;
            &:hover {
                background-color: rgb(0, 0, 0);
            }
            > .name {
                font-size: 15px;
                margin-top: 10px;
                text-align: center;
            }
        }
    }
}
.el-popover.el-popper{
    width: 730px !important;
}
</style>
