<template>
    <div class="messagezx-page">
        <div class="nf-page-hero messagezx-page__hero">
            <span class="nf-page-hero__badge">注销管理</span>
            <h2 class="nf-page-hero__title">用户注销申请</h2>
            <p class="nf-page-hero__desc">查看用户提交的注销申请记录，支持刷新列表、跳转用户详情与审核注销</p>
        </div>

        <div class="nf-filter-card messagezx-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>快捷操作</span>
            </div>
            <div class="nf-filter-card__body messagezx-page__filter-body">
                <div class="messagezx-page__filter-actions">
                    <el-button type="primary" @click="handleSearch()">刷新</el-button>
                </div>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="messagezx-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="注销信息" name="first">
                <div class="nf-table-card messagezx-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="100"></el-table-column>
                        <el-table-column prop="sort" label="用户" width="100">
                            <template #default="scope">
                                <span class="messagezx-page__link" @click="updates(scope.row.userId)">
                                    {{ scope.row.userName ? scope.row.userName : '-' }}
                                </span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="title" label="申请手机号码"></el-table-column>
                        <el-table-column prop="content" label="内容"></el-table-column>
                        <el-table-column prop="status" label="状态" width="100">
                            <template #default="scope">
                                <span v-if="scope.row.status == 1">待审核</span>
                                <span v-if="scope.row.status == 2">已注销</span>
                                <span v-if="scope.row.status == 3">已撤销</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="100">
                            <template #default="scope">
                                <el-button size="small" type="danger" v-if="scope.row.status == 1"
                                    @click="deleteuser(scope.row)" :disabled="!isAuth('messageZx:shenhe')">注销用户</el-button>
                                <span v-if="scope.row.status == 2">用户已注销</span>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="messagezx-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>

        <!-- 添加/修改弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleU" :title="dataContainer.titles" width="80%" center>
            <div class="messagezx-page__form-row">
                <span class="messagezx-page__form-label">名称：</span>
                <el-input class="messagezx-page__form-input" v-model="dataContainer.name" type="text"
                    placeholder="请输入名称"></el-input>
            </div>
            <div class="messagezx-page__form-row">
                <span class="messagezx-page__form-label">链接：</span>
                <el-input class="messagezx-page__form-input" v-model="dataContainer.url" type="text"
                    placeholder="请输入链接"></el-input>
            </div>
            <div class="messagezx-page__form-row">
                <span class="messagezx-page__form-label">描述：</span>
                <el-input class="messagezx-page__form-input" v-model="dataContainer.description" type="text"
                    placeholder="请输入描述"></el-input>
            </div>
            <div class="messagezx-page__form-row">
                <span class="messagezx-page__form-label">排序：</span>
                <el-input class="messagezx-page__form-input" v-model="dataContainer.sort" type="text"
                    placeholder="请输入排序"></el-input>
            </div>
            <div class="messagezx-page__form-row">
                <span class="messagezx-page__form-label">图像：</span>
                <div class="messagezx-page__upload-wrap">
                    <el-upload class="avatar-uploader messagezx-page__uploader" v-model="dataContainer.imageUrl"
                        action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                        :on-success="handleAvatarSuccess1">
                        <img v-if="dataContainer.imageUrl" :src="dataContainer.imageUrl"
                            class="messagezx-page__upload-img" />
                        <SvgIcon v-else class="messagezx-page__upload-icon" name="svg:plus.svg">
                        </SvgIcon>
                    </el-upload>
                </div>
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


        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '注销信息') {
                dataContainer.classify = 1
                dataSelect();
            }
            if (event.srcElement.innerText == '首页分类') {
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
            dataContainer.loading = true   //message/selectMessageByType?page=1&limit=10&state=8&platform=
            http.post('admin/cancelAccount/page', {
                // params: {
                page: dataContainer.page,
                limit: dataContainer.limit,
                platform: '',
                // classify: dataContainer.classify,
                // state: 8,
                // }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                dataContainer.tableData = {}
                let returnData = res.data
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
                dataContainer.id = row.id
                dataContainer.name = row.name
                dataContainer.imageUrl = row.imageUrl
                dataContainer.url = row.url
                dataContainer.sort = row.sort
                dataContainer.description = row.description
                dataContainer.state = row.state
            } else {
                dataContainer.titles = '添加'
                dataContainer.id = ''
                dataContainer.name = ''
                dataContainer.imageUrl = ''
                dataContainer.url = ''
                dataContainer.sort = ''
                dataContainer.description = ''
                dataContainer.state = ''
            }
            dataContainer.dialogFormVisibleU = true


        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.imageUrl = file.data.path
        }
        function StairNoticeToU() {

            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'admin/base/banner/save'
            } else {
                dataContainer.routeUrl = 'admin/base/banner/update'
            }
            http.post(dataContainer.routeUrl, {
                id: dataContainer.id,
                name: dataContainer.name,
                imageUrl: dataContainer.imageUrl,
                classify: dataContainer.classify,
                url: dataContainer.url,
                sort: dataContainer.sort,
                description: dataContainer.description,
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
            http.post('admin/base/banner/delete', {
                id: data.id
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
        // 注销
        function deleteuser(row) {

            http.get('admin/cancelAccount/cancel?id=' + row.userId, {}, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    messageSuccess('操作成功');
                    dataSelect();
                } else {
                    messageError(res.msg);
                }
            })

        }
        function isAuth(res) {
            return userApi.isAuth(res)
        }
        /** 跳转用户详情 */
        function updates(userId, querys) {
            if (userId) {
                router.push({
                    name: 'userDetail',
                    query: {
                        userId: userId,
                    },
                    params: {
                        sign: userId || new Date().getTime(),
                        querys,
                    },
                });
            }
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
            deleteuser,
            updates,

        };
    },
});
</script>



<style lang="scss" scoped>
.messagezx-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.messagezx-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.messagezx-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.messagezx-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.messagezx-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.messagezx-page__tabs {
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

.messagezx-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.messagezx-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.messagezx-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.messagezx-page__form-row {
    margin-bottom: var(--nf-space-sm);
    display: flex;
    align-items: flex-start;
}

.messagezx-page__form-label {
    width: 200px;
    flex-shrink: 0;
    display: inline-block;
    text-align: right;
    color: var(--nf-text-primary);
    font-size: 14px;
    line-height: 32px;
}

.messagezx-page__form-input {
    width: 50%;
}

.messagezx-page__upload-wrap {
    display: inline-block;
}

.messagezx-page__uploader {
    width: 148px;
    height: 148px;
    border: 1px dashed var(--nf-border);
    border-radius: 6px;
    text-align: center;
    line-height: 148px;
    color: var(--nf-border);
}

.messagezx-page__upload-img {
    width: 148px;
    height: 148px;
}

.messagezx-page__upload-icon {
    width: 23px;
    height: 23px;
    color: var(--nf-border);
}
</style>
