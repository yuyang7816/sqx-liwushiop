<template>
    <div class="show-list-page">
        <div class="nf-page-hero show-list-page__hero">
            <span class="nf-page-hero__badge">用户管理</span>
            <h2 class="nf-page-hero__title">用户列表</h2>
            <p class="nf-page-hero__desc">查看与管理平台注册用户，支持按昵称、手机号筛选及状态切换</p>
        </div>

        <div v-show="dataContainer.showSearch" class="nf-filter-card show-list-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body show-list-page__filter-body">
                <div class="show-list-page__filter-item">
                    <span class="show-list-page__filter-label">用户昵称</span>
                    <el-input v-model="dataContainer.form.username" placeholder="请输入" clearable
                        @clear="handleQuery" @keyup.enter="handleQuery" class="show-list-page__filter-input" />
                </div>
                <div class="show-list-page__filter-item">
                    <span class="show-list-page__filter-label">手机号码</span>
                    <el-input v-model="dataContainer.form.phone" placeholder="请输入" clearable
                        @clear="handleQuery" @keyup.enter="handleQuery" class="show-list-page__filter-input" />
                </div>
                <div class="show-list-page__filter-actions">
                    <el-button type="primary" @click="handleQuery">
                        <SvgIcon :style="'width:15px;height:15px;margin-right:5px;'"
                            name="svg:search-bt.svg"></SvgIcon>
                        查询
                    </el-button>
                    <el-button @click="resetQuery">
                        <SvgIcon :style="'width:15px;height:15px;margin-right:5px;'"
                            name="svg:redo.svg"></SvgIcon>
                        重置
                    </el-button>
                </div>
            </div>
        </div>

        <div class="nf-table-card show-list-page__table">
            <el-table v-loading="dataContainer.loading" :data="dataContainer.dataList.records"
                @cell-dblclick="handleCopyVale" @sort-change="handleSortChange" height="100%">
                <el-table-column prop="userId" align="center" label="编号" width="60" fixed="left" />
                <el-table-column label="用户昵称" align="center" min-width="170" prop="nickName" fixed="left">
                    <template #default="scope">
                        {{ scope.row.userName ? scope.row.userName : scope.row.nickName }}
                    </template>
                </el-table-column>
                <el-table-column prop="sex" label="性别">
                    <template #default="scope">
                        <span v-if="scope.row.sex == 1">男</span>
                        <span v-else-if="scope.row.sex == 2">女</span>
                        <span v-else>未设置</span>
                    </template>
                </el-table-column>
                <el-table-column label="图像">
                    <template #default="scope">
                        <img v-if="scope.row.avatar == null" src="../../../assets/logo.png" alt="" width="40"
                            height="40">
                        <img v-else :src="scope.row.avatar" alt="" width="40" height="40">
                    </template>
                </el-table-column>
                <el-table-column prop="phone" label="手机号">
                </el-table-column>
                <el-table-column prop="userMoney" label="钱包" width="80"></el-table-column>
                <el-table-column prop="platform" label="渠道来源"></el-table-column>
                <el-table-column prop="invitationCode" label="邀请码"></el-table-column>
                <el-table-column prop="commissionRate" label="佣金比例" width="150">
                </el-table-column>
                <el-table-column prop="aliWithdrawalAccount" label="支付宝账号" width="150">
                </el-table-column>
                <el-table-column prop="wxWithdrawalAccount" label="收款二维码" width="150">
                    <template #default="scope">
                        <div v-if="scope.row.wxWithdrawalAccount == null || scope.row.wxWithdrawalAccount == ''">
                            暂无收款二维码
                        </div>
                        <div v-else style="display:flex;flex-wrap: wrap;">
                            <div style="margin: 2px;">
                                <el-popover placement="top-start" title="" trigger="hover"
                                    popper-style="width:420px;">
                                    <template #reference>
                                        <img style="width: 50px; height: 50px;margin: 3px;"
                                            :src="scope.row.wxWithdrawalAccount" alt="">
                                    </template>
                                    <template #default style="width: 420px;">
                                        <img style="width: 400px; height: auto;"
                                            :src="scope.row.wxWithdrawalAccount" alt="">
                                    </template>
                                </el-popover>
                            </div>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="createTime" label="创建时间" width="160"></el-table-column>
                <el-table-column prop="status" label="状态" fixed="right">
                    <template #default="scope">
                        <el-switch v-model="scope.row.status" class="ml-2"
                            style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                            @change="changeBtn(scope.row)" :active-value="1" :inactive-value="2"
                            :disabled="!isAuth('userList:update')" />
                    </template>
                </el-table-column>
                <el-table-column label="操作" width="200" fixed="right" class-name="small-padding fixed-width">
                    <template #default="scope">
                        <el-button size="small" type="primary" class="show-list-page__action-btn"
                            @click="handleDetails(scope.row)">
                            用户详情
                        </el-button>
                        <el-button size="small" type="danger" class="show-list-page__action-btn"
                            @click="handleDelete(scope.row)" :disabled="!isAuth('userList:delete')">
                            删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div class="show-list-page__pagination">
                <el-pagination v-show="true" :total="dataContainer.dataList.total" :background="true"
                    :current-page="dataContainer.form.page" :page-size="dataContainer.form.limit"
                    layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 30, 50]"
                    @size-change="handleSizeChange" @current-change="handleCurrentChange" />
            </div>
        </div>
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
import SvgIcon from '@/components/svgIcon/index.vue';
import { hasPermi } from '@/action/powerTools';
import http from '@/http/request.js';
import userApi from '@/http/user.js';
import { Filter } from '@element-plus/icons-vue';

export default defineComponent({
    components: {
        DictTags,
        SvgIcon,
        Filter,
    },
    setup() {
        const router = useRouter();
        const dataContainer = reactive({
            loading: false,
            showSearch: true,
            checked__: false,
            form: {
                page: 1,
                limit: 10,
                username: '',
                phone: '',
                userType: '',
                status: '',
            },
            params: {
                pageNum: 1,
                pageSize: 10,
            },
            config: {
                total: 0,
            },
            dataList: {},
            currentRows: [],
            optionList: [
                { value: true, label: '不可选择', elTagType: 'danger' },
                { value: false, label: '可选择', elTagType: 'primary' },
            ],
        });
        /** 获取数据列表 */
        const getDataList = debounceFn(function () {
            if (dataContainer.loading) return;
            dataContainer.loading = true;
            setTimeout(() => {
                http.post('admin/user/list', {
                    page: dataContainer.form.page,
                    limit: dataContainer.form.limit,
                    userType: '',
                    username: dataContainer.form.username,
                    phone: dataContainer.form.phone,
                    userType: '',
                    status: '',
                }, {
                }).then((res) => {
                    console.log(res)
                    dataContainer.loading = false;
                    if (res.code == 0) {
                        dataContainer.dataList = res.data
                        dataContainer.config.total = res.data.totalCount;
                    } else {

                    }
                })
            }, 800);
        }, 70);
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
            dataContainer.form.username = ''
            dataContainer.form.phone = ''
            dataContainer.params.pageNum = 1;
            handleQuery();
        }
        /** 导出数据 */
        function handleExport() {
            let str = '保存字符串的例子！！';
            let strData = new Blob([str], { type: 'text/plain;charset=utf-8' });
            messageSuccess('导出成功！');
        }
        /** 新增按钮操作 */
        function handleAdd() {
            router.push({
                name: 'show-list-add',
            });
        }
        /** 详情按钮操作 */
        function handleDetails(row, querys) {
            console.log(row, 'router---', router.replace, '----------querys', querys)
            router.push({
                name: 'userDetail',
                query: {
                    userId: row.userId,
                },
                params: {
                    userId: row.userId,
                    sign: row.userId || new Date().getTime(),
                    querys,
                },
            });
        }
        /** 编辑按钮操作 */
        function handleEdit(row, querys) {
            router.push({
                name: 'show-list-update',
                params: {
                    sign: row.id || new Date().getTime(),
                },
                querys,
            });
        }
        /** 删除 */
        function handleDelete(rows) {
            confirm('确认删除所选用户？', '提示！')
                .then(() => {
                    http.get('admin/cancelAccount/remove', {
                        params: {
                            userId: rows.userId
                        }
                    }, {
                    }).then((res) => {
                        if (res.code == 0) {
                            messageSuccess('删除成功');
                            getDataList();
                        } else {

                        }
                    })
                })
                .catch(() => { });
        }
        /** 批次确认多选操作 */
        function changeAllCheck(value) {
            if (!value) {
                dataContainer.list.forEach((item) => {
                    item.checked__ = false;
                });
            } else {
                dataContainer.list.forEach((item) => {
                    if (isDisabled(item)) {
                        item.checked__ = false;
                        return;
                    }
                    item.checked__ = true;
                });
            }
        }
        /** 计算批量操作按钮的显示隐藏 */
        function isDisabled(row) {
            return row.disabled;
        }
        function handleSelectionChange(selection) {
            dataContainer.currentRows = selection || [];
        }
        function changeBtn(val) {
            var datas = {
                userId: val.userId,
                status: val.status
            }
            http.post('admin/user/update', datas, {
            }).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    messageSuccess('操作成功');
                    getDataList();
                } else {
                    messageError(res.msg);
                    getDataList();
                }
            })
        }
        function handleSizeChange(val) {
            dataContainer.form.limit = val
            getDataList();
        }
        function handleCurrentChange(val) {
            dataContainer.form.page = val
            getDataList();
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
            hasPermi,
            changeAllCheck,
            isDisabled,
            handleSelectionChange,
            changeBtn,
            handleSizeChange,
            handleCurrentChange,
        };
    },
});
</script>

<style lang="scss" scoped>
.show-list-page {
    display: flex;
    flex-direction: column;
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
    overflow-x: hidden;
}

.show-list-page__hero {
    margin-bottom: var(--nf-space-sm);
    flex-shrink: 0;
}

.show-list-page__filter {
    margin-bottom: var(--nf-space-sm);
    flex-shrink: 0;
}

.show-list-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.show-list-page__filter-item {
    display: flex;
    align-items: center;
    gap: var(--nf-space-xs);
}

.show-list-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-secondary);
    white-space: nowrap;
}

.show-list-page__filter-input {
    width: 200px;
}

.show-list-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.show-list-page__table {
    flex: 1;
    display: flex;
    flex-direction: column;
    min-height: 0;
    margin-bottom: 0;
}

.show-list-page__action-btn {
    margin: 4px 0;
}

.show-list-page__pagination {
    display: flex;
    justify-content: flex-end;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}
</style>
