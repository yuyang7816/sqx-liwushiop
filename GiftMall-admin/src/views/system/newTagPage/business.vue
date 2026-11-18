<template>
    <div class="business-page">
        <div class="nf-page-hero business-page__hero">
            <span class="nf-page-hero__badge">投诉中心</span>
            <h2 class="nf-page-hero__title">投诉与申诉管理</h2>
            <p class="nf-page-hero__desc">查看师傅投诉与申诉记录，管理投诉分类及审核处理</p>
        </div>

        <div class="nf-filter-card business-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>快捷操作</span>
            </div>
            <div class="nf-filter-card__body business-page__filter-body">
                <el-button type="primary" @click="onRefresh">刷新</el-button>
                <el-button v-if="dataContainer.activeName === 'second'" type="primary"
                    @click="addChangeU()">添加</el-button>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="business-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="投诉列表" name="first">
                <div class="nf-table-card business-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="complaintId" label="编号" width="100" fixed="left"></el-table-column>
                        <el-table-column prop="indentNumber" label="订单号" width="200" align="center">
                        </el-table-column>
                        <el-table-column prop="nickName" label="被投诉的师傅" width="180" fixed="left">
                            <template #default="scope">
                                <span class="business-page__link" @click="updates(scope.row.userId)">
                                    {{ scope.row.nickName ? scope.row.nickName : '未绑定' }}
                                </span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="illegal" label="投诉类型" width="150">
                        </el-table-column>
                        <el-table-column prop="wrongExplain" label="违规说明" width="300"></el-table-column>
                        <el-table-column prop="resultHanding" label="处理结果" width="120" align="center">
                            <template #default="scope">
                                <span v-if="scope.row.complaintState == '5'">待审核</span>
                                <span v-else-if="scope.row.complaintState == '6'">投诉未通过</span>
                                <span v-else>投诉通过</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="complaintTime" label="投诉时间" width="100"></el-table-column>
                        <el-table-column prop="appealMessage" label="申诉理由" width="200" align="center">
                        </el-table-column>
                        <el-table-column prop="appealTime" label="申诉时间" width="100"></el-table-column>
                        <el-table-column prop="auditMessage" label="申诉结果" width="200" align="center">
                        </el-table-column>
                        <el-table-column prop="complaintState" label="状态" width="100" fixed="right">
                            <template #default="scope">
                                <span v-if="scope.row.complaintState == '1'">可申诉</span>
                                <span v-if="scope.row.complaintState == '2'">申诉中</span>
                                <span v-if="scope.row.complaintState == '3'">申诉未通过</span>
                                <span v-if="scope.row.complaintState == '4'">申诉通过</span>
                                <span v-if="scope.row.complaintState == '5'">待审核</span>
                                <span v-if="scope.row.complaintState == '6'">投诉未通过</span>
                            </template>
                        </el-table-column>
                        <el-table-column label="操作" width="100" fixed="right">
                            <template #default="scope">
                                <el-button size="small" type="warning" plain
                                    v-if="scope.row.complaintState == '5'" @click="refuseClick(scope.row)"
                                    :disabled="!isAuth('business:jujue')" class="business-page__action-btn">拒绝投诉
                                </el-button>
                                <el-button size="small" type="warning" v-else @click="refuseClick(scope.row)"
                                    :disabled="!isAuth('business:jujue')" class="business-page__action-btn">拒绝申诉
                                </el-button>
                                <el-button size="small" type="primary" plain
                                    v-if="scope.row.complaintState == '5'" @click="passClicks1(scope.row)"
                                    :disabled="!isAuth('business:tongguo')" class="business-page__action-btn">通过投诉
                                </el-button>
                                <el-button size="small" type="primary" v-else @click="passClicks(scope.row)"
                                    :disabled="!isAuth('business:tongguo')" class="business-page__action-btn">通过申诉
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="business-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
            <el-tab-pane label="投诉分类" name="second">
                <div class="nf-table-card business-page__table">
                    <el-table :data="dataContainer.tableDataClass.list" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="100"></el-table-column>
                        <el-table-column prop="illegal" label="名称"></el-table-column>
                        <el-table-column prop="forfeitMoney" label="投诉扣除金额"></el-table-column>
                        <el-table-column label="操作" width="100">
                            <template #default="scope">
                                <el-button size="small" type="primary" @click="addChangeU(scope.row)"
                                    class="business-page__action-btn">修改</el-button>
                                <el-button size="small" type="danger" @click="handleDelete(scope.row)"
                                    class="business-page__action-btn">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="business-page__pagination">
                        <el-pagination @size-change="handleSizeChangeCl" @current-change="handleCurrentChangeCl"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableDataClass.totalCount">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>
        </el-tabs>

        <!-- 添加/修改弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleU" :title="dataContainer.titles" width="80%" center>
            <div class="business-page__form-row">
                <span class="business-page__form-label">名称：</span>
                <el-input class="business-page__form-input" v-model="dataContainer.illegal" type="text"
                    placeholder="请输入名称"></el-input>
            </div>
            <div class="business-page__form-row">
                <span class="business-page__form-label">投诉扣除金额：</span>
                <el-input class="business-page__form-input" v-model="dataContainer.forfeitMoney" type="text"
                    placeholder="请输入投诉扣除金额"></el-input>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dataContainer.dialogFormVisibleU = false">取消</el-button>
                    <el-button type="primary" @click="StairNoticeToU">确认</el-button>
                </span>
            </template>
        </el-dialog>
        <!-- 任务拒绝弹框 -->
        <el-dialog title="审核拒绝" v-model="dataContainer.dialogFormVisible" center>
            <div class="business-page__form-row business-page__form-row--textarea">
                <span class="business-page__form-label">拒绝理由：</span>
                <el-input class="business-page__form-input" v-model="dataContainer.content" type="textarea" :rows="4"
                    placeholder="请输入拒绝理由">
                </el-input>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dataContainer.dialogFormVisible = false">取消</el-button>
                    <el-button type="primary" @click="refuseto()">确认</el-button>
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
            tableDataClass: {},
            page: 1,
            limit: 10,
            classify: 1,
            dialogFormVisibleU: false,
            titles: '添加',
            id: 0,
            illegal: "",
            forfeitMoney: 0,

            state: 0,
            routeUrl: '',
            active: 1,
            inactive: 2,
            dialogFormVisible: false,
            content: '',
            helpTakeId: '',
            indentNumbers: '',
            complaintId: '',


        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '投诉列表') {
                dataSelect();
            }
            if (event.srcElement.innerText == '投诉分类') {
                dataSelectClass();
            }
        }
        /**
         * 搜索
         */
        function handleSearch() {
            dataContainer.page = 1
            dataSelect();
        }
        function onRefresh() {
            if (dataContainer.activeName === 'second') {
                handleSearchCl();
            } else {
                handleSearch();
            }
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.get('adminerrand/findAllAppeal', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    complaintState: '',
                    indentNumber: '',
                    illegalId: '',
                    cityId: '',
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
        function handleSizeChangeCl(val) {
            dataContainer.limit = val
            dataSelectClass()
        }
        function handleCurrentChangeCl(val) {
            dataContainer.page = val
            dataSelectClass()
        }
        function handleSearchCl() {
            dataContainer.page = 1
            dataSelectClass();
        }
        // 投诉分类
        function dataSelectClass() {
            dataContainer.loading = true
            http.get('/adminerrand/selectIllegalTypePage', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                dataContainer.tableDataClass = {}
                let returnData = res.data
                dataContainer.tableDataClass = returnData
            })
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
                dataContainer.illegal = row.illegal
                dataContainer.imageUrl = row.imageUrl
                dataContainer.url = row.url
                dataContainer.forfeitMoney = row.forfeitMoney
                dataContainer.description = row.description
                dataContainer.state = row.state
            } else {
                dataContainer.titles = '添加'
                dataContainer.id = ''
                dataContainer.illegal = ''
                dataContainer.imageUrl = ''
                dataContainer.url = ''
                dataContainer.forfeitMoney = ''
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
                dataContainer.routeUrl = 'adminerrand/insertIllegalType'
            } else {
                dataContainer.routeUrl = 'adminerrand/updateIllegalType'
            }
            http.post(dataContainer.routeUrl, {
                id: dataContainer.id,
                illegal: dataContainer.illegal,
                forfeitMoney: dataContainer.forfeitMoney,
            }, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
                        type: 'success',
                        onClose: () => {
                            dataContainer.dialogFormVisibleU = false
                            dataSelectClass()
                        }
                    })
                } else {
                    ElMessage({
                        message: res.msg,
                        type: 'warning',
                        onClose: () => {
                            dataSelectClass()
                        }
                    })
                }
            })
        }
        /** 删除 */
        function handleDelete(data) {
            http.get('adminerrand/deleteIllegalType', {
                params: {
                    id: data.id
                }
            }, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    messageSuccess('删除成功');
                    dataSelectClass();
                } else {
                    messageError(res.msg);
                }
            })
        }
        // 注销
        function deleteuser(row) {

            http.post('user/deleteUserByUserId/' + row.userId, {}, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    messageSuccess('操作成功');
                    dataSelect();
                } else {
                    messageError(res.msg);
                }
            })

        }
        //拒绝
        function refuseClick(rows) {
            console.log(rows.indentNumber, rows.complaintState, '1111111111')
            if (rows.complaintState == '2') {
                dataContainer.status = 3
            }
            if (rows.complaintState == '5') {
                dataContainer.status = 6
            }

            dataContainer.helpTakeId = rows.riderUserId
            dataContainer.content = ''
            dataContainer.indentNumbers = rows.indentNumber
            dataContainer.complaintId = rows.complaintId
            dataContainer.dialogFormVisible = true
        }
        function refuseto() {
            if (dataContainer.content == '') {
                ElNotification({
                    title: '提示',
                    message: '请输入拒绝理由',
                    duration: 0,
                    type: 'warning'
                })
                return
            }

            if (dataContainer.status == 3) {
                http.post('indent/updateComplaintState', {
                    riderUserId: dataContainer.helpTakeId,
                    indentNumber: dataContainer.indentNumbers,
                    complaintState: dataContainer.status,
                    auditMessage: dataContainer.content,
                    complaintId: dataContainer.complaintId,
                }, {}).then((res) => {
                    console.log(res)
                    if (res.code == 0) {
                        ElMessage({
                            message: '操作成功',
                            type: 'success',
                            onClose: () => {
                                dataContainer.dialogFormVisible = false
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
            if (dataContainer.status == 6) {
                http.get('indent/updateCustomerComplaint', {
                    params: {
                        complaintState: dataContainer.status,
                        complaintId: dataContainer.complaintId,
                        auditMessage: '驳回投诉',

                    }
                }, {
                }).then((res) => {
                    console.log(res)
                    if (res.code == 0) {
                        ElMessage({
                            message: '操作成功',
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

        }
        //单条投诉通过
        function passClicks1(row) {
            console.log(row.indentNumber, row, '22222222')
            dataContainer.status = 1
            dataContainer.content = '同意'
            http.get('indent/updateCustomerComplaint', {
                params: {
                    complaintState: dataContainer.status,
                    complaintId: dataContainer.complaintId,
                    auditMessage: '审核通过',

                }
            }, {
            }).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
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
        //单条任务通过
        function passClicks(row) {
            console.log(row.indentNumber, row, '22222222')
            dataContainer.status = 4
            dataContainer.content = '同意'
            http.post('indent/updateComplaintState', {
                riderUserId: row.helpTakeId,
                indentNumber: row.indentNumbers,
                complaintState: dataContainer.status,
                auditMessage: '审核通过',
                complaintId: row.complaintId,
            }, {}).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
                        type: 'success',
                        onClose: () => {
                            dataContainer.dialogFormVisible = false
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
        /** 跳转用户详情 */
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
            onRefresh,
            dataSelect,
            handleSizeChange,
            handleCurrentChange,
            changeBtn,
            handleAvatarSuccess1,
            StairNoticeToU,
            handleDelete,
            deleteuser,
            refuseClick,
            refuseto,
            passClicks1,
            passClicks,
            dataSelectClass,
            handleSizeChangeCl,
            handleCurrentChangeCl,
            updates,



        };
    },
});
</script>



<style lang="scss" scoped>
.business-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.business-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.business-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.business-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.business-page__tabs {
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

.business-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.business-page__action-btn {
    margin: 4px 0;
}

.business-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.business-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.business-page__form-row {
    margin-bottom: var(--nf-space-sm);
    display: flex;
    align-items: flex-start;
}

.business-page__form-row--textarea {
    align-items: flex-start;
}

.business-page__form-label {
    width: 200px;
    flex-shrink: 0;
    display: inline-block;
    text-align: right;
    color: var(--nf-text-primary);
    font-size: 14px;
    line-height: 32px;
}

.business-page__form-row--textarea .business-page__form-label {
    line-height: 1.5;
    padding-top: 6px;
}

.business-page__form-input {
    width: 50%;
}
</style>
