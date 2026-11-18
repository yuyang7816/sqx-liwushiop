<template>
    <div class="menu-page">
        <div class="nf-page-hero menu-page__hero">
            <span class="nf-page-hero__badge">系统管理</span>
            <h2 class="nf-page-hero__title">菜单管理</h2>
            <p class="nf-page-hero__desc">管理系统菜单、目录与按钮权限，支持树形展示与增删改</p>
        </div>

        <div v-show="dataContainer.showSearch" class="nf-filter-card menu-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body menu-page__filter-body">
                <div class="menu-page__filter-item">
                    <span class="menu-page__filter-label">名称</span>
                    <el-input v-model="dataContainer.form.name" placeholder="请输入" clearable
                        @clear="handleQuery" @keyup.enter="handleQuery" class="menu-page__filter-input" />
                </div>
                <div class="menu-page__filter-actions">
                    <el-button type="primary" @click="handleQuery">查询</el-button>
                    <el-button type="primary" @click="handleAdd" :disabled="!isAuth('sys:menu:save')">新增</el-button>
                </div>
            </div>
        </div>

        <div class="nf-table-card menu-page__table">
            <el-table ref="ElTableRef" v-loading="dataContainer.loading" :data="dataContainer.list"
                @cell-dblclick="handleCopyVale" border row-key="menuId"
                :tree-props="{ children: 'children' }" height="100%">
                <el-table-column prop="name" header-align="center" min-width="150" label="名称">
                </el-table-column>
                <el-table-column prop="parentName" header-align="center" align="center" width="120" label="上级菜单">
                </el-table-column>
                <el-table-column header-align="center" align="center" label="图标">
                    <template #default="scope">
                        <SvgIcon :style="'width:23px;height:23px;'" :name="scope.row.icon"></SvgIcon>
                    </template>
                </el-table-column>
                <el-table-column prop="type" header-align="center" align="center" label="类型">
                    <template #default="scope">
                        <el-tag v-if="scope.row.type === 0" size="small">目录</el-tag>
                        <el-tag v-else-if="scope.row.type === 1" size="small" type="success">菜单</el-tag>
                        <el-tag v-else-if="scope.row.type === 2" size="small" type="info">按钮</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="orderNum" header-align="center" align="center" label="排序号">
                </el-table-column>
                <el-table-column prop="url" header-align="center" align="center" width="150" :show-overflow-tooltip="true"
                    label="菜单URL">
                </el-table-column>
                <el-table-column prop="perms" header-align="center" align="center" width="150" :show-overflow-tooltip="true"
                    label="授权标识">
                </el-table-column>
                <el-table-column label="操作" width="200" fixed="right" class-name="small-padding fixed-width">
                    <template #default="scope">
                        <el-button size="small" type="primary" class="menu-page__action-btn"
                            @click="handleEdit(scope.row, { isShow: false, afterTitle: ' - 编辑' })"
                            :disabled="!isAuth('sys:menu:update')">
                            编辑
                        </el-button>
                        <el-button size="small" type="danger" class="menu-page__action-btn"
                            @click="handleDelete(scope.row)" :disabled="!isAuth('sys:menu:delete')">
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <EditDataDialog @closeEditDialog="closeEditDialog" ref="EditDataDialogRef"></EditDataDialog>
    </div>
</template>

<script>
/**
 * 页面例子
 */
import {
    defineComponent,
    onBeforeUnmount,
    ref,
    reactive,
    getCurrentInstance,
    onActivated,
} from 'vue';
import { useRouter } from 'vue-router';
import { copyValue, deepCopyObj } from '@/common/otherTools';
import DictTags from '@/components/dictTags.vue';
import { debounceFn } from '@/common/debounceAndThrottle';
import { responseData } from './common/data.js';
import { messageSuccess, confirm } from '@/action/messagePrompt.js';
import EditDataDialog from './components/editDataDialog.vue';
import SvgIcon from '@/components/svgIcon/index.vue';
import { hasPermi } from '@/action/powerTools';
import http from '@/http/request.js';
import userApi from '@/http/user.js';
import { Filter } from '@element-plus/icons-vue';
export default defineComponent({
    components: {
        DictTags,
        EditDataDialog,
        SvgIcon,
        Filter,
    },
    setup() {
        const EditDataDialogRef = ref(null); //组件实例
        const router = useRouter();
        const dataContainer = reactive({
            loading: false,
            showSearch: true,
            form: {},
            params: {
                //基础参数
            },
            list: [],
        });
        const closeEditDialog = () => {
            getDataList()
        };
        /** 获取数据列表 */
        const getDataList = debounceFn(function () {
            dataContainer.loading = true;
            http.get('sys/menu/list', {
                params: {}
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                dataContainer.list = userApi.treeDataTranslate(res, 'menuId')
            })




            // dataContainer.loading = true;
            // Promise.resolve(responseData)
            //     .then((res) => {
            //         let list = deepCopyObj(res.rows || []);
            //         dataContainer.list = list;
            //     })
            //     .catch(() => {
            //         return;
            //     })
            //     .finally(() => {
            //         dataContainer.loading = false;
            //     });
        }, 300);
        getDataList();
        /** 双击单元格，复制单元格内容 */
        function handleCopyVale(_, __, ___, event) {
            copyValue(event.target.innerText);
            messageSuccess('复制成功，内容为：' + event.target.innerText);
        }
        /** 排序触发事件 */
        function handleSortChange(column, prop, order) {
            dataContainer.form.orderByColumn = column.prop;
            dataContainer.form.isAsc = column.order;
            getDataList();
        }
        /** 搜索按钮操作 */
        function handleQuery() {
            dataContainer.params.pageNum = 1;
            getDataList();
        }
        /** 重置按钮操作 */
        function resetQuery() {
            dataContainer.form = {};
            handleQuery();
        }
        /** 导出数据 */
        function handleExport() { }
        /** 新增按钮操作 */
        function handleAdd(params) {
            if (!EditDataDialogRef.value) return;
            EditDataDialogRef.value
                .initData(
                    true,
                    {
                        ...params,
                    },
                    {
                        afterTitle: ' - 添加',
                    },
                )
                .then((data) => { })
                .catch(() => { });
        }
        /** 详情按钮操作 */
        function handleDetails(row, querys) {
            if (!EditDataDialogRef.value) return;
            EditDataDialogRef.value
                .initData(
                    true,
                    {
                        ...row,
                    },
                    {
                        ...querys,
                    },
                )
                .then(() => { })
                .catch(() => { });
        }
        /** 编辑按钮操作 */
        function handleEdit(row, querys) {
            if (!EditDataDialogRef.value) return;
            EditDataDialogRef.value
                .initData(
                    true,
                    {
                        ...row,
                    },
                    {
                        ...querys,
                    },
                )
                .then((data) => { })
                .catch(() => { });
        }
        /** 删除 */
        function handleDelete(data) {
            confirm('确认删除该数据？', '提示')
                .then(() => {
                    http.post('sys/menu/delete/' + data.menuId, {}, {
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    }).then((res) => {
                        console.log(res)
                        if (res.code == 0) {
                            messageSuccess('删除成功');
                            getDataList();
                        } else {
                            messageError(res.msg);
                        }
                    })




                    // dataContainer.loading = true;
                    // deleteSalesLeadApi(rows.map(item=>item.id).join(',')).then(() => {
                    //     getDataList();
                    // }).catch(()=>{
                    //     return;
                    // }).finally(()=>{
                    //     dataContainer.loading = false;
                    // });
                    // messageSuccess('删除成功');
                })
                .catch(() => { });
        }
        function isAuth(res) {
            return userApi.isAuth(res)
        }

        return {
            isAuth,
            dataContainer,
            getDataList,
            handleCopyVale,
            handleSortChange,
            handleQuery,
            resetQuery,
            handleExport,
            handleAdd,
            handleDetails,
            handleEdit,
            handleDelete,
            EditDataDialogRef,
            hasPermi,
            closeEditDialog
        };
    },
});
</script>

<style lang="scss" scoped>
.menu-page {
    display: flex;
    flex-direction: column;
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
    overflow-x: hidden;
}

.menu-page__hero {
    margin-bottom: var(--nf-space-sm);
    flex-shrink: 0;
}

.menu-page__filter {
    margin-bottom: var(--nf-space-sm);
    flex-shrink: 0;
}

.menu-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.menu-page__filter-item {
    display: flex;
    align-items: center;
    gap: var(--nf-space-xs);
}

.menu-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-secondary);
    white-space: nowrap;
}

.menu-page__filter-input {
    width: 200px;
}

.menu-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.menu-page__table {
    flex: 1;
    display: flex;
    flex-direction: column;
    min-height: 0;
    margin-bottom: 0;
}

.menu-page__action-btn {
    margin: 4px 0;
}
</style>
