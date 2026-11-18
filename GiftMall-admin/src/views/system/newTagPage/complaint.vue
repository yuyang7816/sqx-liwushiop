<template>
    <div class="complaint-page">
        <div class="nf-page-hero complaint-page__hero">
            <span class="nf-page-hero__badge">投诉中心</span>
            <h2 class="nf-page-hero__title">用户与群组投诉</h2>
            <p class="nf-page-hero__desc">查看用户投诉与群组投诉记录，支持按昵称、内容与状态筛选及审核处理</p>
        </div>

        <div class="nf-filter-card complaint-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>{{ dataContainer.activeName === 'first' ? '筛选条件' : '快捷操作' }}</span>
            </div>
            <div class="nf-filter-card__body complaint-page__filter-body">
                <template v-if="dataContainer.activeName === 'first'">
                    <div class="complaint-page__filter-item">
                        <span class="complaint-page__filter-label">用户昵称：</span>
                        <el-input v-model="dataContainer.userNameT" placeholder="请输入用户昵称"
                            @keydown.enter.native="handleSearch" class="complaint-page__filter-input" />
                    </div>
                    <div class="complaint-page__filter-item">
                        <span class="complaint-page__filter-label">内容：</span>
                        <el-input v-model="dataContainer.contentT" placeholder="请输入内容"
                            @keydown.enter.native="handleSearch" class="complaint-page__filter-input" />
                    </div>
                    <div class="complaint-page__filter-item">
                        <span class="complaint-page__filter-label">状态：</span>
                        <el-select v-model="dataContainer.typeT" placeholder="请选择状态"
                            class="complaint-page__filter-input" @change="handleSearch">
                            <el-option v-for="(item, index) in dataContainer.inviteVerifyData" :key="index"
                                :label="item.label" :value="item.value" />
                        </el-select>
                    </div>
                    <div class="complaint-page__filter-actions">
                        <el-button type="primary" @click="handleSearch()">刷新</el-button>
                        <el-button @click="clearSearch()">重置</el-button>
                    </div>
                </template>
                <template v-else>
                    <div class="complaint-page__filter-actions">
                        <el-button type="primary" @click="handleSearch()">刷新</el-button>
                        <el-button type="primary" @click="addChangeU()">添加</el-button>
                    </div>
                </template>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="complaint-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="用户投诉" name="first">
                <div class="nf-table-card complaint-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="100" fixed="left"></el-table-column>
                        <el-table-column prop="nickName" label="用户昵称" width="150" fixed="left">
                            <template #default="scope">
                                <span class="complaint-page__link" @click="updates(scope.row)">{{
                                    scope.row.nickName ? scope.row.nickName : '未绑定' }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="title" label="标题">
                        </el-table-column>
                        <el-table-column prop="picture" label="截图">
                            <template #default="scope">
                                <div v-if="scope.row.picture == null || scope.row.picture == ''">
                                    暂无图片
                                </div>
                                <div v-else-if="scope.row.picture.includes(',')" class="complaint-page__pictures">
                                    <div v-for="item in scope.row.picture.split(',')" :key="item"
                                        class="complaint-page__picture-item">
                                        <el-popover placement="top-start" title="" trigger="hover">
                                            <img class="complaint-page__thumb" :src="item" alt="" slot="reference">
                                            <img class="complaint-page__preview" :src="item" alt="">
                                        </el-popover>
                                    </div>
                                </div>
                                <div v-else>
                                    <el-popover placement="top-start" title="" trigger="hover">
                                        <img class="complaint-page__thumb" :src="scope.row.picture" alt=""
                                            slot="reference">
                                        <img class="complaint-page__preview" :src="scope.row.picture" alt="">
                                    </el-popover>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="content" label="投诉内容">
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="160">
                        </el-table-column>
                        <el-table-column prop="feedback" label="处理说明" width="100"></el-table-column>
                        <el-table-column prop="status" label="状态" width="100" fixed="right">
                            <template #default="scope">
                                <span v-if="scope.row.status == 1">等待处理</span>
                                <span v-if="scope.row.status == 2">封禁账号</span>
                                <span v-if="scope.row.status == 3">已驳回</span>
                                <span v-if="scope.row.status == 4">通过但不做处理</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="100" fixed="right">
                            <template #default="scope">
                                <el-button size="small" type="primary" @click="addChangeU(scope.row)"
                                    class="complaint-page__action-btn"
                                    v-if="scope.row.status == 1">审核</el-button>
                                <el-button size="small" type="danger" @click="handleDelete(scope.row)"
                                    class="complaint-page__action-btn">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="complaint-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="群组投诉" name="second">
                <div class="nf-table-card complaint-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="100"></el-table-column>
                        <el-table-column prop="name" label="名称"></el-table-column>
                        <el-table-column prop="imageUrl" label="图片">
                            <template #default="scope">
                                <img :src="scope.row.imageUrl" class="complaint-page__avatar" />
                            </template>
                        </el-table-column>
                        <el-table-column prop="url" label="链接" width="100"></el-table-column>
                        <el-table-column prop="description" label="描述" width="100"></el-table-column>
                        <el-table-column prop="feedback" label="处理说明" width="100"></el-table-column>
                        <el-table-column prop="status" label="状态" width="100">
                            <template #default="scope">
                                <span v-if="scope.row.status == 1">等待处理</span>
                                <span v-if="scope.row.status == 2">封禁账号</span>
                                <span v-if="scope.row.status == 3">已驳回</span>
                                <span v-if="scope.row.status == 4">通过但不做处理</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                        <el-table-column label="操作" width="100">
                            <template #default="scope">
                                <el-button size="small" type="primary" @click="addChangeU(scope.row)"
                                    class="complaint-page__action-btn">修改</el-button>
                                <el-button size="small" type="danger" @click="handleDelete(scope.row)"
                                    class="complaint-page__action-btn">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="complaint-page__pagination">
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
            <div class="complaint-page__form-row">
                <span class="complaint-page__form-label">审核结果：</span>
                <el-radio-group v-model="dataContainer.state" class="ml-4">
                    <el-radio :label="2">封禁账号</el-radio>
                    <el-radio :label="3">驳回</el-radio>
                    <el-radio :label="4">4通过但不做处理</el-radio>
                </el-radio-group>
            </div>
            <div class="complaint-page__form-row complaint-page__form-row--textarea">
                <span class="complaint-page__form-label">审核说明：</span>
                <el-input class="complaint-page__form-input" v-model="dataContainer.description" type="textarea"
                    rows="4" placeholder="请输入审核说明"></el-input>
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
import { userDataStore } from '@/store/user';
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
        let userData = userDataStore();
        const dataContainer = reactive({
            userInfo: toRef(userData, 'userInfo'),
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
            targetId: '',
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
                    label: '等待处理',
                    value: 1
                },
                {
                    label: '封禁账号',
                    value: 2
                },
                {
                    label: '已驳回',
                    value: 3
                },
                {
                    label: '通过但不做处理',
                    value: 4
                }
            ]


        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '用户投诉') {
                dataContainer.classify = 1
                dataSelect();
            }
            if (event.srcElement.innerText == '群组投诉') {
                dataContainer.classify = 2
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
            http.get('admin/complaint/page', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    userName: dataContainer.userNameT,
                    targetType: dataContainer.classify,
                    targetUserName: dataContainer.contentT,
                    targetChatRoomsName: '',
                    status: dataContainer.typeT,
                }
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
            dataContainer.titles = '审核'
            dataContainer.id = row.id
            dataContainer.targetId = row.targetId
            dataContainer.description = ''
            dataContainer.state = ''

            dataContainer.dialogFormVisibleU = true


        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.imageUrl = file.data.path
        }
        console.log()
        function StairNoticeToU() {

            http.post('admin/complaint/updateComplaint', {
                id: dataContainer.id,
                targetId: dataContainer.targetId,
                feedback: dataContainer.description,
                status: dataContainer.state,
                handlerId: dataContainer.userInfo.userId,
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
        return {
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
            clearSearch,

        };
    },
});
</script>



<style lang="scss" scoped>
.complaint-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.complaint-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.complaint-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.complaint-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.complaint-page__filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.complaint-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.complaint-page__filter-input {
    width: 150px;
}

.complaint-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.complaint-page__tabs {
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

.complaint-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.complaint-page__action-btn {
    margin: 4px 0;
}

.complaint-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.complaint-page__pictures {
    display: flex;
    flex-wrap: wrap;
}

.complaint-page__picture-item {
    margin: 2px;
}

.complaint-page__thumb {
    width: 50px;
    height: 50px;
}

.complaint-page__preview {
    width: 200px;
    height: 200px;
}

.complaint-page__avatar {
    width: 60px;
    height: 60px;
}

.complaint-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.complaint-page__form-row {
    margin-bottom: var(--nf-space-sm);
    display: flex;
    align-items: flex-start;
}

.complaint-page__form-row--textarea {
    align-items: flex-start;
}

.complaint-page__form-label {
    width: 200px;
    flex-shrink: 0;
    display: inline-block;
    text-align: right;
    color: var(--nf-text-primary);
    font-size: 14px;
    line-height: 32px;
}

.complaint-page__form-row--textarea .complaint-page__form-label {
    line-height: 1.5;
    padding-top: 6px;
}

.complaint-page__form-input {
    width: 50%;
}
</style>
