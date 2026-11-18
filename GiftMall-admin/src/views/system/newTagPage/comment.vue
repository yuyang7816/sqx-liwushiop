<template>
    <div class="comment-page">
        <div class="nf-page-hero comment-page__hero">
            <span class="nf-page-hero__badge">聊天会话</span>
            <h2 class="nf-page-hero__title">用户会话列表</h2>
            <p class="nf-page-hero__desc">查看用户间聊天会话，支持按昵称与内容筛选，点击操作列查看聊天记录</p>
        </div>

        <div class="nf-filter-card comment-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body comment-page__filter-body">
                <div class="comment-page__filter-item">
                    <span class="comment-page__filter-label">用户昵称：</span>
                    <el-input v-model="dataContainer.userNameT" placeholder="请输入用户昵称"
                        @keydown.enter.native="handleSearch" class="comment-page__filter-input" />
                </div>
                <div class="comment-page__filter-item">
                    <span class="comment-page__filter-label">内容：</span>
                    <el-input v-model="dataContainer.contentT" placeholder="请输入内容"
                        @keydown.enter.native="handleSearch" class="comment-page__filter-input" />
                </div>
                <div class="comment-page__filter-actions">
                    <el-button type="primary" @click="handleSearch()">刷新</el-button>
                    <el-button @click="clearSearch()">重置</el-button>
                </div>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="comment-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="聊天会话" name="first">
                <div class="nf-table-card comment-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="100">
                            <template #default="scope">
                                {{ scope.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column prop="senderUserName" label="用户昵称">
                            <template #default="scope">
                                <div v-if="scope.row.senderUserName" class="comment-page__link"
                                    @click="updates(scope.row.senderUserId)">
                                    {{ scope.row.senderUserName ? scope.row.senderUserName : '未绑定' }}
                                </div>
                                <div v-if="scope.row.receiverUserName" class="comment-page__link"
                                    @click="updates(scope.row.receiverUserId)">
                                    {{ scope.row.receiverUserName ? scope.row.receiverUserName : '未绑定' }}
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="senderAvatar" label="用户图头">
                            <template #default="scope">
                                <img v-if="scope.row.senderAvatar" :src="scope.row.senderAvatar"
                                    class="comment-page__avatar" />
                                <img v-if="scope.row.receiverAvatar" :src="scope.row.receiverAvatar"
                                    class="comment-page__avatar" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="messageCount" label="信息数量" width="180"></el-table-column>
                        <el-table-column prop="lastMessageTime" label="创建时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="120">
                            <template #default="scope">
                                <el-button size="small" type="primary" @click="ltChange(scope.row)">聊天记录</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="comment-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>

        <!-- 聊天记录弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleLt" title="聊天记录" width="80%" center>
            <div class="comment-page__dialog-filter">
                <div class="comment-page__filter-item">
                    <span class="comment-page__filter-label">用户昵称：</span>
                    <el-input v-model="dataContainer.userNameC" placeholder="请输入用户昵称"
                        @keydown.enter.native="handleSearchLt" class="comment-page__filter-input" />
                </div>
                <div class="comment-page__filter-item">
                    <span class="comment-page__filter-label">聊天内容：</span>
                    <el-input v-model="dataContainer.contentC" placeholder="请输入聊天内容"
                        @keydown.enter.native="handleSearchLt" class="comment-page__filter-input" />
                </div>
                <div class="comment-page__filter-actions">
                    <el-button type="primary" @click="handleSearchLt()">查询</el-button>
                    <el-button @click="clearSearchLt()">重置</el-button>
                </div>
            </div>
            <el-table :data="dataContainer.tableDataLt.records" v-loading="dataContainer.loadingCy">
                <el-table-column prop="id" label="编号" width="100"></el-table-column>
                <el-table-column prop="senderUserName" label="用户昵称">
                    <template #default="scope">
                        <span v-if="scope.row.senderUserName" class="comment-page__link"
                            @click="updates(scope.row.senderUserId)">
                            {{ scope.row.senderUserName ? scope.row.senderUserName : '未绑定' }}
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="senderAvatar" label="用户图头">
                    <template #default="scope">
                        <img v-if="scope.row.senderAvatar" :src="scope.row.senderAvatar"
                            class="comment-page__avatar" />
                    </template>
                </el-table-column>
                <el-table-column prop="type" label="内容类型">
                    <template #default="scope">
                        <span v-if="scope.row.type == 1">普通消息</span>
                        <span v-if="scope.row.type == 2">表情</span>
                        <span v-if="scope.row.type == 3">图片</span>
                        <span v-if="scope.row.type == 4">视频</span>
                    </template>
                </el-table-column>
                <el-table-column prop="content" label="聊天内容">
                    <template #default="scope">
                        <span v-if="scope.row.type == 1">{{ scope.row.content }}</span>
                        <span v-if="scope.row.type == 2">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                <template #reference>
                                    <img class="comment-page__thumb" :src="scope.row.content" alt="">
                                </template>
                                <template #default style="width: 420px;">
                                    <img class="comment-page__preview" :src="scope.row.content" alt="">
                                </template>
                            </el-popover>
                        </span>
                        <span v-if="scope.row.type == 3">
                            <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                <template #reference>
                                    <img class="comment-page__thumb" :src="scope.row.content" alt="">
                                </template>
                                <template #default style="width: 420px;">
                                    <img class="comment-page__preview" :src="scope.row.content" alt="">
                                </template>
                            </el-popover>
                        </span>
                        <span v-if="scope.row.type == 4">
                            <video class="comment-page__video" :src="scope.row.content" type="audio/wav"
                                controls="controls"></video>
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
            </el-table>
            <div class="comment-page__pagination">
                <el-pagination @size-change="handleSizeChangeLt" @current-change="handleCurrentChangeLt"
                    :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                    :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                    :total="dataContainer.tableDataLt.total">
                </el-pagination>
            </div>
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
            userNameT: '',
            contentT: '',
            typeT: '',
            inviteVerifyData: [
                {
                    label: '全部',
                    value: ''
                },
                {
                    label: '普通消息',
                    value: 1
                },
                {
                    label: '表情',
                    value: 2
                },
                {
                    label: '图片',
                    value: 3
                },
                {
                    label: '视频',
                    value: 4
                }
            ],
            pageC: 1,
            limitC: 10,
            userNameC: '',
            phoneC: '',
            typeC: '',
            contentC: '',
            receiverUserId: '',
            senderUserId: '',
            dialogFormVisibleLt: false,
            loadingCy: false,
            tableDataLt: {},


        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '聊天会话') {
                dataContainer.classify = 1
                dataSelect();
            }

        }
        /**
         * 搜索
         */
        function handleSearch() {
            dataContainer.page = 1
            dataSelect();
        }
        function clearSearch() {
            dataContainer.page = 1
            dataContainer.userNameT = ''
            dataContainer.contentT = ''
            dataContainer.typeT = ''
            dataSelect();
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.post('admin/history/chatHistory', {
                // params: {
                page: dataContainer.page,
                limit: dataContainer.limit,
                // targetType: dataContainer.classify,
                userName: dataContainer.userNameT,
                // type:dataContainer.typeT,
                // content: dataContainer.contentT,
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
        /** 聊天记录 弹框 */
        function ltChange(row) {
            dataContainer.receiverUserId = row.receiverUserId
            dataContainer.senderUserId = row.senderUserId
            dataContainer.userNameC = ''
            dataContainer.phoneC = ''
            dataContainer.typeC = ''
            dataContainer.contentC = ''
            dataContainer.pageC = 1

            dataSelectLt();
            dataContainer.dialogFormVisibleLt = true
        }
        /** 聊天记录 数据 */
        function dataSelectLt() {
            dataContainer.loadingCy = true
            http.post('admin/history/userChatHistory', {
                // params: {
                page: dataContainer.pageC,
                limit: dataContainer.limitC,
                receiverUserId: dataContainer.receiverUserId,
                senderUserId: dataContainer.senderUserId,
                userName: dataContainer.userNameC,
                // phone: dataContainer.phoneC,
                // targetType:2,
                // type:dataContainer.typeC,
                content: dataContainer.contentC,
                // }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loadingCy = false;
                dataContainer.tableDataLt = {}
                let returnData = res.data
                dataContainer.tableDataLt = returnData
            })
        }
        function handleSearchLt() {
            dataSelectLt();
        }
        function clearSearchLt() {
            dataContainer.pageC = 1
            dataContainer.userNameC = ''
            dataContainer.phoneC = ''
            dataContainer.typeC = ''
            dataContainer.contentC = ''
            dataSelectLt();
        }
        function handleSizeChangeLt(val) {
            dataContainer.limitC = val
            dataSelectLt()
        }
        function handleCurrentChangeLt(val) {
            dataContainer.pageC = val
            dataSelectLt()
        }
        /** 跳转用户 */
        function updates(userId, querys) {
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
        function isAuth(res) {
            return userApi.isAuth(res)
        }


        return {
            isAuth,
            dataContainer,
            addChangeU,
            handleClick,
            handleSearch,
            clearSearch,
            dataSelect,
            handleSizeChange,
            handleCurrentChange,
            changeBtn,
            handleAvatarSuccess1,
            StairNoticeToU,
            handleDelete,
            ltChange,
            dataSelectLt,
            handleSearchLt,
            clearSearchLt,
            handleSizeChangeLt,
            handleCurrentChangeLt,
            updates,

        };
    },
});
</script>



<style lang="scss" scoped>
.comment-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.comment-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.comment-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.comment-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.comment-page__filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.comment-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.comment-page__filter-input {
    width: 150px;
}

.comment-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.comment-page__tabs {
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

.comment-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.comment-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.comment-page__avatar {
    width: 60px;
    height: 60px;
    margin: 3px;
    object-fit: cover;
    border-radius: 6px;
}

.comment-page__thumb {
    width: 50px;
    height: 50px;
    margin: 3px;
    object-fit: cover;
    border-radius: 4px;
}

.comment-page__preview {
    width: 400px;
    height: auto;
}

.comment-page__video {
    width: 180px;
    height: 100px;
}

.comment-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.comment-page__dialog-filter {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
    margin-bottom: var(--nf-space-sm);
    padding-bottom: var(--nf-space-sm);
    border-bottom: 1px solid var(--nf-border);
}
</style>
