<template>
    <div class="materials-list-page">
        <div class="nf-page-hero materials-list-page__hero">
            <span class="nf-page-hero__badge">帮助中心</span>
            <h2 class="nf-page-hero__title">帮助文档管理</h2>
            <p class="nf-page-hero__desc">管理帮助分类与文章内容，支持分类维护与文章增删改</p>
        </div>

        <div class="nf-filter-card materials-list-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>{{ dataContainer.activeName === 'first' ? '快捷操作' : '筛选条件' }}</span>
            </div>
            <div class="nf-filter-card__body materials-list-page__filter-body">
                <template v-if="dataContainer.activeName === 'first'">
                    <div class="materials-list-page__filter-actions">
                        <el-button type="primary" @click="handleSearch()">刷新</el-button>
                        <el-button type="primary" @click="addChangeU()"
                            :disabled="!isAuth('materialsList:add')">添加</el-button>
                    </div>
                </template>
                <template v-else>
                    <div class="materials-list-page__filter-item">
                        <span class="materials-list-page__filter-label">分类：</span>
                        <el-select v-model="dataContainer.helpClassifyIdT" placeholder="请选择分类"
                            class="materials-list-page__filter-input" @change="handleSearch()">
                            <el-option v-for="(item, index) in dataContainer.tableData.records" :key="index"
                                :label="item.helpClassifyName" :value="item.helpClassifyId" />
                        </el-select>
                    </div>
                    <div class="materials-list-page__filter-actions">
                        <el-button type="primary" @click="handleSearchW()">刷新</el-button>
                        <el-button type="primary" @click="addChangeW()"
                            :disabled="!isAuth('materialsList:add')">添加</el-button>
                    </div>
                </template>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="materials-list-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="分类管理" name="first">
                <div class="nf-table-card materials-list-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="helpClassifyId" label="编号" width="100"></el-table-column>
                        <el-table-column prop="helpClassifyName" label="名称"></el-table-column>
                        <el-table-column prop="sort" label="排序" width="100"></el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="100">
                            <template #default="scope">
                                <el-button size="small" type="primary" @click="addChangeU(scope.row)"
                                    :disabled="!isAuth('materialsList:update')"
                                    class="materials-list-page__action-btn">修改</el-button>
                                <el-button size="small" type="danger" @click="handleDelete(scope.row)"
                                    :disabled="!isAuth('materialsList:delete')"
                                    class="materials-list-page__action-btn">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="materials-list-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="文章管理" name="second">
                <div class="nf-table-card materials-list-page__table">
                    <el-table :data="dataContainer.tableDataW.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="helpWordId" label="编号" width="100"></el-table-column>
                        <el-table-column prop="helpWordTitle" label="标题"></el-table-column>
                        <el-table-column prop="helpWordContent" label="内容"></el-table-column>
                        <el-table-column prop="sort" label="排序" width="100"></el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="100">
                            <template #default="scope">
                                <el-button size="small" type="primary" @click="addChangeW(scope.row)"
                                    :disabled="!isAuth('materialsList:update')"
                                    class="materials-list-page__action-btn">修改</el-button>
                                <el-button size="small" type="danger" @click="handleDeleteW(scope.row)"
                                    :disabled="!isAuth('materialsList:delete')"
                                    class="materials-list-page__action-btn">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="materials-list-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableDataW.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>

        <!-- 添加/修改弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleU" :title="dataContainer.titles" width="80%" center>
            <div class="materials-list-page__form-row">
                <span class="materials-list-page__form-label">名称：</span>
                <el-input class="materials-list-page__form-input" v-model="dataContainer.helpClassifyName" type="text"
                    placeholder="请输入名称"></el-input>
            </div>
            <div class="materials-list-page__form-row">
                <span class="materials-list-page__form-label">排序：</span>
                <el-input class="materials-list-page__form-input" v-model="dataContainer.sort" type="text"
                    placeholder="请输入排序"></el-input>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dataContainer.dialogFormVisibleU = false">取消</el-button>
                    <el-button type="primary" @click="StairNoticeToU">确认</el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 添加/修改 文章弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleW" :title="dataContainer.titles" width="80%" center>
            <div class="materials-list-page__form-row">
                <span class="materials-list-page__form-label">标题：</span>
                <el-input class="materials-list-page__form-input" v-model="dataContainer.helpWordTitle" type="text"
                    placeholder="请输入标题"></el-input>
            </div>
            <div class="materials-list-page__form-row">
                <span class="materials-list-page__form-label">内容：</span>
                <el-input class="materials-list-page__form-input" v-model="dataContainer.helpWordContent" type="text"
                    placeholder="请输入内容"></el-input>
            </div>
            <div class="materials-list-page__form-row">
                <span class="materials-list-page__form-label">排序：</span>
                <el-input class="materials-list-page__form-input" v-model="dataContainer.sort" type="text"
                    placeholder="请输入排序"></el-input>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dataContainer.dialogFormVisibleW = false">取消</el-button>
                    <el-button type="primary" @click="StairNoticeToW">确认</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script>
/**
 * 页面例子
 */
import { defineComponent, reactive, toRef } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import SvgIcon from '@/components/svgIcon/index.vue';
import { userDataStore } from '@/store/user';
import { toTree, unfoldTreeList } from '@/common/treeTools';
import DefinScrollbar from '@/components/definScrollbar.vue';
import { messageSuccess, messageError } from '@/action/messagePrompt';
import { findTag, deleteTags } from '@/action/tagListTools';
import http from '@/http/request.js';
import userApi from '@/http/user.js';
import { Filter } from '@element-plus/icons-vue';
export default defineComponent({
    components: {
        SvgIcon,
        DefinScrollbar,
        Filter,
    },
    setup() {
        let userData = userDataStore();
        const router = useRouter();
        const route = useRoute();
        const url = ''
        const dataContainer = reactive({
            loading: false,
            activeName: 'first',
            tableData: {},
            tableDataW: {},
            page: 1,
            limit: 10,
            classify: 1,
            dialogFormVisibleU: false,
            titles: '添加',
            helpClassifyId: 0,
            helpClassifyName: "",
            sort: 0,
            parentId: 0,
            helpClassifyIdT: '',
            dialogFormVisibleW: false,
            helpWordId: '',
            helpWordTitle: '',
            helpWordContent: '',


        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '分类管理') {
                dataSelect();
            }
            if (event.srcElement.innerText == '文章管理') {
                dataContainer.helpClassifyIdT = dataContainer.tableData.records[0].helpClassifyId
                dataSelectW();
            }
        }
        /**
         * 搜索 分类
         */
        function handleSearch() {
            dataContainer.page = 1
            dataSelect();
        }
        /** 获取分类列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.post('admin/helpClassify/page', {

                page: dataContainer.page,
                limit: dataContainer.limit,
                helpClassifyName: '',
                parentId: 0,
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                let returnData = res.data
                dataContainer.tableData = returnData
            })
        }
        function handleSearchW() {
            dataContainer.page = 1
            dataSelectW();
        }
        /** 获取分类列表数据 */
        function dataSelectW() {
            dataContainer.loading = true
            http.post('admin/helpWord/page', {

                page: dataContainer.page,
                limit: dataContainer.limit,
                helpClassifyId: dataContainer.helpClassifyIdT,
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                let returnData = res.data
                dataContainer.tableDataW = returnData
            })
        }
        dataSelect();
        /**  分页事件*/
        function handleSizeChange(val) {
            dataContainer.limit = val
            dataSelect()
        }
        function handleCurrentChange(val) {
            dataContainer.page = val
            dataSelect()
        }
        function changeBtn(row) {
            http.post('admin/base/banner/updateState', {
                params: {
                    id: row.id,
                    state: row.state,
                }
            }, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                        message: '修改成功',
                        type: 'success',
                        onClose: () => {
                            dataSelect()
                        }
                    })
                } else {
                    ElMessage({
                        message: res.msg,
                        type: 'warning',
                        onClose: () => {
                            dataSelect()
                        }
                    })
                }
            })
        }
        /**
         * 添加、修改 分类弹框 
         */
        function addChangeU(row) {
            if (row) {
                dataContainer.titles = '修改'
                dataContainer.helpClassifyId = row.helpClassifyId
                dataContainer.helpClassifyName = row.helpClassifyName
                dataContainer.parentId = row.parentId
                dataContainer.sort = row.sort
            } else {
                dataContainer.titles = '添加'
                dataContainer.helpClassifyId = ''
                dataContainer.helpClassifyName = ''
                dataContainer.parentId = 0
                dataContainer.sort = ''
            }
            dataContainer.dialogFormVisibleU = true


        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.imageUrl = file.data.path
        }
        function StairNoticeToU() {

            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'admin/helpClassify/save'
            } else {
                dataContainer.routeUrl = 'admin/helpClassify/update'
            }
            http.post(dataContainer.routeUrl, {
                helpClassifyId: dataContainer.helpClassifyId,
                helpClassifyName: dataContainer.helpClassifyName,
                parentId: dataContainer.parentId,
                sort: dataContainer.sort,
            }, {}).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
                        type: 'success',
                        onClose: () => {
                            dataContainer.dialogFormVisibleU = false
                            dataSelect()
                        }
                    })
                } else {
                    ElMessage({
                        message: res.msg,
                        type: 'warning',
                        onClose: () => {
                            dataSelect()
                        }
                    })
                }
            })
        }
        /** 删除分类 */
        function handleDelete(data) {

            http.post('admin/helpClassify/delete', {
                helpClassifyId: data.helpClassifyId
            }, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    messageSuccess('删除成功');
                    dataSelect();
                } else {
                    messageError(res.msg);
                }
            })
        }
        /**
         * 添加、修改 文章弹框 
         */
        function addChangeW(row) {
            if (row) {
                dataContainer.titles = '修改'
                dataContainer.helpClassifyId = row.helpClassifyId
                dataContainer.helpWordId = row.helpWordId
                dataContainer.helpWordTitle = row.helpWordTitle
                dataContainer.helpWordContent = row.helpWordContent

                dataContainer.sort = row.sort
            } else {
                dataContainer.titles = '添加'
                dataContainer.helpWordId = ''
                dataContainer.helpClassifyId = dataContainer.helpClassifyIdT
                dataContainer.helpWordTitle = ''
                dataContainer.helpWordContent = ''
                dataContainer.sort = ''
            }
            dataContainer.dialogFormVisibleW = true


        }
        function StairNoticeToW() {

            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'admin/helpWord/save'
            } else {
                dataContainer.routeUrl = 'admin/helpWord/update'
            }
            http.post(dataContainer.routeUrl, {
                helpWordId: dataContainer.helpWordId,
                helpClassifyId: dataContainer.helpClassifyId,
                helpWordTitle: dataContainer.helpWordTitle,
                helpWordContent: dataContainer.helpWordContent,
                sort: dataContainer.sort,
            }, {}).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
                        type: 'success',
                        onClose: () => {
                            dataContainer.dialogFormVisibleW = false
                            dataSelectW()
                        }
                    })
                } else {
                    ElMessage({
                        message: res.msg,
                        type: 'warning',
                        onClose: () => {
                        }
                    })
                }
            })
        }
        /** 删除文章 */
        function handleDeleteW(data) {

            http.post('admin/helpWord/delete', {
                helpWordId: data.helpWordId
            }, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    messageSuccess('删除成功');
                    dataSelectW();
                } else {
                    messageError(res.msg);
                }
            })
        }
        function isAuth(res) {
            return userApi.isAuth(res)
        }


        return {
            isAuth,
            dataContainer,
            addChangeU,
            handleClick,
            handleSearch,
            dataSelect,
            handleSizeChange,
            handleCurrentChange,
            changeBtn,
            handleAvatarSuccess1,
            StairNoticeToU,
            handleDelete,
            dataSelectW,
            handleSearchW,
            addChangeW,
            StairNoticeToW,
            handleDeleteW,


        };
    },
});
</script>



<style lang="scss" scoped>
.materials-list-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.materials-list-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.materials-list-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.materials-list-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.materials-list-page__filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.materials-list-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.materials-list-page__filter-input {
    width: 200px;
}

.materials-list-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.materials-list-page__tabs {
    margin-bottom: 0;

    :deep(.el-tabs__header) {
        margin-bottom: 0;
    }

    :deep(.el-tabs__item.is-active) {
        color: var(--nf-brand);
    }

    :deep(.el-tabs__active-bar) {
        background-color: var(--nf-brand);
    }
}

.materials-list-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.materials-list-page__action-btn {
    margin: 4px 0;
}

.materials-list-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.materials-list-page__form-row {
    margin-bottom: var(--nf-space-sm);
    display: flex;
    align-items: center;
}

.materials-list-page__form-label {
    width: 200px;
    flex-shrink: 0;
    display: inline-block;
    text-align: right;
    color: var(--nf-text-primary);
    font-size: 14px;
    line-height: 32px;
}

.materials-list-page__form-input {
    width: 50%;
}
</style>
