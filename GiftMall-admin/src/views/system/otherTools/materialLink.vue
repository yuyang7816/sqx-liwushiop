<template>
    <div class="material-link-page">
        <div class="nf-page-hero material-link-page__hero">
            <span class="nf-page-hero__badge">指南管理</span>
            <h2 class="nf-page-hero__title">培训指南</h2>
            <p class="nf-page-hero__desc">管理用户端与师傅端培训指南内容，支持新增、修改与删除</p>
        </div>

        <div class="nf-filter-card material-link-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>快捷操作</span>
            </div>
            <div class="nf-filter-card__body material-link-page__filter-body">
                <el-button type="primary" @click="handleSearch()">刷新</el-button>
                <el-button type="primary" @click="addChangeU()"
                    :disabled="!isAuth('materialLink:add')">添加</el-button>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="material-link-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="用户端" name="first">
                <div class="nf-table-card material-link-page__table">
                    <el-table :data="dataContainer.tableData.list" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="trainingId" label="编号" width="100"></el-table-column>
                        <el-table-column prop="title" label="标题" width="250"></el-table-column>
                        <el-table-column prop="message" label="内容"></el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="160">
                            <template #default="scope">
                                <el-button size="small" type="primary" class="material-link-page__action-btn"
                                    @click="addChangeU(scope.row)"
                                    :disabled="!isAuth('materialLink:update')">修改</el-button>
                                <el-button size="small" type="danger" class="material-link-page__action-btn"
                                    @click="handleDelete(scope.row)"
                                    :disabled="!isAuth('materialLink:delete')"
                                    v-if="scope.row.trainingId != 8">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="material-link-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.totalCount">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="师傅端" name="second">
                <div class="nf-table-card material-link-page__table">
                    <el-table :data="dataContainer.tableData.list" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="trainingId" label="编号" width="100"></el-table-column>
                        <el-table-column prop="title" label="标题" width="250"></el-table-column>
                        <el-table-column prop="message" label="内容"></el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="160">
                            <template #default="scope">
                                <el-button size="small" type="primary" class="material-link-page__action-btn"
                                    @click="addChangeU(scope.row)"
                                    :disabled="!isAuth('materialLink:update')">修改</el-button>
                                <el-button size="small" type="danger" class="material-link-page__action-btn"
                                    @click="handleDelete(scope.row)"
                                    :disabled="!isAuth('materialLink:delete')"
                                    v-if="scope.row.trainingId != 8">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="material-link-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.totalCount">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>

        <!-- 添加/修改弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleU" :title="dataContainer.titles" width="80%" center>
            <div class="material-link-page__form-row">
                <span class="material-link-page__form-label">标题：</span>
                <el-input class="material-link-page__form-input" v-model="dataContainer.title" type="text"
                    placeholder="请输入标题"></el-input>
            </div>
            <div class="material-link-page__form-row">
                <span class="material-link-page__form-label">内容：</span>
                <el-input class="material-link-page__form-input" v-model="dataContainer.message" type="text"
                    placeholder="请输入内容"></el-input>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dataContainer.dialogFormVisibleU = false">取消</el-button>
                    <el-button type="primary" @click="StairNoticeToU">确认</el-button>
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
import { messageSuccess, messageError } from '@/action/messagePrompt';
import { findTag, deleteTags } from '@/action/tagListTools';
import http from '@/http/request.js';
import userApi from '@/http/user.js';
import { Filter } from '@element-plus/icons-vue';
export default defineComponent({
    components: {
        SvgIcon,
        Filter,
    },
    setup() {
        const router = useRouter();
        const route = useRoute();
        const url = ''
        const dataContainer = reactive({
            loading: false,
            activeName: 'first',
            tableData: {},
            page: 1,
            limit: 10,
            classify: 1,
            dialogFormVisibleU: false,
            titles: '添加',
            id: 0,
            name: "",
            imageUrl: "",
            url: "",
            sort: 0,
            description: "",
            state: 0,
            routeUrl: '',
            active: 1,
            inactive: 2,

            deleteFlag: 0,
            trainingId: '',
            title: '',
            type: '',
            message: '',


        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '用户端') {
                dataContainer.classify = 1
                dataSelect();
            }
            if (event.srcElement.innerText == '师傅端') {
                dataContainer.classify = 2
                dataSelect();
            }
        }
        /**
         * 搜索
         */
        function handleSearch() {
            dataSelect();
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.get('adminerrand/adminTrainingCenter', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    type: dataContainer.classify,
                }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                dataContainer.tableData = []
                let returnData = res.data.data
                dataContainer.tableData = returnData
            })
        }
        dataSelect()
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

                id: row.id,
                state: row.state,
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
         * 添加、修改弹框
         */
        function addChangeU(row) {
            if (row) {
                dataContainer.titles = '修改'
                dataContainer.deleteFlag = row.deleteFlag
                dataContainer.trainingId = row.trainingId
                dataContainer.title = row.title
                dataContainer.type = dataContainer.type
                dataContainer.message = row.message
            } else {
                dataContainer.titles = '添加'
                dataContainer.deleteFlag = 0
                dataContainer.trainingId = ''
                dataContainer.title = ''
                dataContainer.type = dataContainer.classify
                dataContainer.message = ''

            }
            dataContainer.dialogFormVisibleU = true


        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.imageUrl = file.data.path
        }
        function StairNoticeToU() {

            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'adminerrand/addAdminTrainingCenter'
            } else {
                dataContainer.routeUrl = 'adminerrand/updateAdminTrainingCenter'
            }
            http.post(dataContainer.routeUrl, {
                deleteFlag: dataContainer.deleteFlag,
                trainingId: dataContainer.trainingId,
                title: dataContainer.title,
                type: dataContainer.type,
                message: dataContainer.message,
            }, {}).then((res) => {
                console.log(res)
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
        /** 删除 */
        function handleDelete(data) {
            ElMessageBox.confirm(`确定删除数据?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                http.post('adminerrand/updateAdminTrainingCenter', {
                    trainingId: data.trainingId,
                    deleteFlag: 1,
                }, {}).then((res) => {
                    console.log(res)
                    if (res.code == 0) {
                        messageSuccess('删除成功');
                        dataSelect();
                    } else {
                        messageError(res.msg);
                    }
                })
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

        };
    },
});
</script>



<style lang="scss" scoped>
.material-link-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.material-link-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.material-link-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.material-link-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.material-link-page__tabs {
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

.material-link-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.material-link-page__action-btn {
    margin: 4px 0;
}

.material-link-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.material-link-page__form-row {
    margin-bottom: var(--nf-space-sm);
}

.material-link-page__form-label {
    width: 200px;
    display: inline-block;
    text-align: right;
    color: var(--nf-text-primary);
    font-size: 14px;
}

.material-link-page__form-input {
    width: 50%;
}
</style>
