<template>
    <div class="role-page">
        <div class="nf-page-hero role-page__hero">
            <span class="nf-page-hero__badge">系统管理</span>
            <h2 class="nf-page-hero__title">角色管理</h2>
            <p class="nf-page-hero__desc">管理系统角色与权限分配，支持按名称筛选及增删改</p>
        </div>

        <div v-show="dataContainer.showSearch" class="nf-filter-card role-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body role-page__filter-body">
                <div class="role-page__filter-item">
                    <span class="role-page__filter-label">角色名称</span>
                    <el-input v-model="dataContainer.roleName" placeholder="请输入" clearable
                        @clear="handleQuery" @keyup.enter="handleQuery" class="role-page__filter-input" />
                </div>
                <div class="role-page__filter-actions">
                    <el-button type="primary" @click="handleQuery">查询</el-button>
                    <el-button @click="resetQuery">重置</el-button>
                    <el-button type="primary" @click="handleAdd" :disabled="!isAuth('sys:role:save')">新增</el-button>
                    <el-button plain type="danger" v-if="dataContainer.currDataList.length > 0"
                        :disabled="!isAuth('sys:role:delete')"
                        @click="handleDelete(dataContainer.currDataList)">
                        批量删除
                    </el-button>
                </div>
            </div>
        </div>

        <div class="nf-table-card role-page__table">
            <el-table v-loading="dataContainer.loading" :data="dataContainer.list" border
                @cell-dblclick="handleCopyVale" @sort-change="handleSortChange" height="100%">
                <el-table-column prop="roleId" align="center" show-overflow-tooltip label="编号" width="100" />
                <el-table-column label="角色名称" show-overflow-tooltip align="center" prop="roleName" />
                <el-table-column label="备注" show-overflow-tooltip align="center" prop="remark" />
                <el-table-column label="创建时间" show-overflow-tooltip align="center" prop="createTime"
                    min-width="220" />
                <el-table-column label="操作" width="200" fixed="right" class-name="small-padding fixed-width">
                    <template #default="scope">
                        <el-button size="small" type="primary" class="role-page__action-btn"
                            @click="handleEdit(scope.row, { isShow: false, afterTitle: ' - 编辑' })"
                            :disabled="!isAuth('sys:role:update')">
                            编辑
                        </el-button>
                        <el-button size="small" type="danger" class="role-page__action-btn"
                            @click="handleDelete(scope.row.roleId)" :disabled="!isAuth('sys:role:delete')">
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="role-page__pagination">
                <el-pagination v-show="true" :total="dataContainer.totalCount" :background="true"
                    :current-page="dataContainer.params.pageNum" :page-size="dataContainer.params.pageSize"
                    layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 30, 50]" :pager-count="7"
                    @size-change="sizeChangeHandle" @current-change="currentChangeHandle" />
            </div>
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
import { copyValue } from '@/common/otherTools';
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
        const closeEditDialog = () =>{
            getDataList()
        };
        const dataContainer = reactive({
            loading: false,
            showSearch: true,
            form: {},
            params: {
                //基础参数
                pageNum: 1,
                pageSize: 10,
            },
            config: {
                total: 0,
            },
            list: [],
            currDataList: [],
            roleName: '',
            totalCount:0,
        });
        /** 获取数据列表 */
        const getDataList = debounceFn(function () {
            dataContainer.loading = true;
            http.get('sys/role/list', {
                params: {
                    page: dataContainer.params.pageNum,
                    limit: dataContainer.params.pageSize,
                    roleName: dataContainer.roleName
                }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                if (res.code == 0) {

                    dataContainer.list = res.data.page.list
                    dataContainer.totalCount = res.data.page.totalCount
                }
            })



            // dataContainer.loading = true;
            // Promise.resolve(responseData)
            //     .then((res) => {
            //         // dataContainer.list = res.rows || [];
            //         // dataContainer.config.total = res.total;
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
            dataContainer.roleName = '';
            handleQuery();
        }
        /** 导出数据 */
        function handleExport() { }
        /** 新增按钮操作 */
        function handleAdd() {
            if (!EditDataDialogRef.value) return;
            EditDataDialogRef.value
                .initData(
                    true,
                    {},
                    {
                        afterTitle: ' - 添加',
                    },
                )
                .then(() => { })
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
                .then(() => { })
                .catch(() => { });
        }
        /** 删除 */
        function handleDelete(id) {
            const userIds = id ? [id] : this.dataListSelections.map(item => {
                return item
            })
            confirm('确认删除该数据？').then(() => {
                http.post('sys/role/delete', userIds, {
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
                messageSuccess('删除成功');
            })
                .catch(() => { });
        }
        /**  每页数*/
        function sizeChangeHandle(val) {
            dataContainer.params.pageSize = val
            dataContainer.params.pageNum = 1
            getDataList();
        }
        /** 当前页*/
        function currentChangeHandle(val) {
            dataContainer.params.pageNum = val
            getDataList();
        }
        function isAuth(res){
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
            sizeChangeHandle,
            currentChangeHandle,
            closeEditDialog,
        };
    },
});
</script>

<style lang="scss" scoped>
.role-page {
    display: flex;
    flex-direction: column;
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
    overflow-x: hidden;
}

.role-page__hero {
    margin-bottom: var(--nf-space-sm);
    flex-shrink: 0;
}

.role-page__filter {
    margin-bottom: var(--nf-space-sm);
    flex-shrink: 0;
}

.role-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.role-page__filter-item {
    display: flex;
    align-items: center;
    gap: var(--nf-space-xs);
}

.role-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-secondary);
    white-space: nowrap;
}

.role-page__filter-input {
    width: 200px;
}

.role-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.role-page__table {
    flex: 1;
    display: flex;
    flex-direction: column;
    min-height: 0;
    margin-bottom: 0;
}

.role-page__action-btn {
    margin: 4px 0;
}

.role-page__pagination {
    display: flex;
    justify-content: flex-end;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}
</style>
