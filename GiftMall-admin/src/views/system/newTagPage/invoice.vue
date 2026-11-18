<template>
    <div class="invoice-page">
        <div class="nf-page-hero invoice-page__hero">
            <span class="nf-page-hero__badge">发票管理</span>
            <h2 class="nf-page-hero__title">发票列表</h2>
            <p class="nf-page-hero__desc">查看与管理用户发票申请，支持按状态、订单号与电话筛选及开票状态切换</p>
        </div>

        <div class="nf-filter-card invoice-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body invoice-page__filter-body">
                <div class="invoice-page__filter-item">
                    <span class="invoice-page__filter-label">状态：</span>
                    <el-select v-model="dataContainer.classify" placeholder="请选择状态"
                        class="invoice-page__filter-input" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.statesnum" :key="index" :label="item.label"
                            :value="item.value" />
                    </el-select>
                </div>
                <div class="invoice-page__filter-item">
                    <span class="invoice-page__filter-label">订单号：</span>
                    <el-input v-model="dataContainer.nickNameT" placeholder="请输入订单号"
                        @keydown.enter.native="handleSearch" class="invoice-page__filter-input" />
                </div>
                <div class="invoice-page__filter-item">
                    <span class="invoice-page__filter-label">电话：</span>
                    <el-input v-model="dataContainer.phoneT" placeholder="请输入电话"
                        @keydown.enter.native="handleSearch" class="invoice-page__filter-input" />
                </div>
                <div class="invoice-page__filter-actions">
                    <el-button type="primary" @click="handleSearch()">刷新</el-button>
                    <el-button @click="clearChangeU()">重置</el-button>
                </div>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="invoice-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="发票列表" name="first">
                <div class="nf-table-card invoice-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column type="selection" fixed="left">
                        </el-table-column>
                        <el-table-column prop="invoiceId" label="编号" width="80" fixed="left"></el-table-column>
                        <el-table-column prop="nickName" label="用户名" fixed="left">
                            <template #default="scope">
                                <span class="invoice-page__link" @click="updates(scope.row.userId)">{{
                                    scope.row.nickName }}</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="userEmail" label="接收邮箱">
                        </el-table-column>
                        <el-table-column prop="userPhone" label="接收手机号">
                        </el-table-column>
                        <el-table-column prop="indentNumber" label="订单号" width="170">
                            <template #default="scope">
                                <div v-if="scope.row.indentNumber">
                                    <div v-for="(item, index) in scope.row.indentNumber.split(',')" :key="index">{{ item }} </div>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="invoiceType" label="发票类型">
                            <template #default="scope">
                                <span v-if="scope.row.invoiceType == 1">公司</span>
                                <span v-if="scope.row.invoiceType == 2">个人</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="taxPayerNumber" label="税号" width="180">
                        </el-table-column>
                        <el-table-column prop="invoiceMoney" label="发票金额">
                        </el-table-column>
                        <el-table-column prop="bankAddress" label="注册地址">
                        </el-table-column>
                        <el-table-column prop="bankDeposit" label="开户行">
                        </el-table-column>
                        <el-table-column prop="bankNumber" label="银行卡号">
                        </el-table-column>
                        <el-table-column prop="bankPhone" label="注册电话">
                        </el-table-column>
                        <el-table-column prop="invoiceHeader" label="发票抬头" width="180">
                        </el-table-column>
                        <el-table-column prop="invoiceTitle" label="开票内容" width="180">
                        </el-table-column>
                        <el-table-column prop="status" label="发票状态" fixed="right">
                            <template #default="scope">
                                <span v-if="scope.row.status == 1" class="invoice-page__status--pending">待开票</span>
                                <span v-if="scope.row.status == 2" class="invoice-page__status--done">已开票</span>
                                <el-switch v-model="scope.row.status" :disabled="!isAuth('invoice:update')"
                                    @change="change3(scope.row)" :active-value="2" class="ml-2" :inactive-value="1"
                                    active-color="#13ce66" inactive-color="#ff4949">
                                </el-switch>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="170">
                        </el-table-column>
                        <el-table-column label="操作" width="160" fixed="right">
                            <template #default="scope">
                                <el-button size="small" type="primary" @click="addChangeU(scope.row)"
                                    class="invoice-page__action-btn">修改</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="invoice-page__pagination">
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
            <div class="invoice-page__form-row">
                <span class="invoice-page__form-label">订单号：</span>
                <el-input class="invoice-page__form-input" disabled v-model="dataContainer.indentNumber" type="text"
                    placeholder="请输入订单号"></el-input>
            </div>
            <div class="invoice-page__form-row">
                <span class="invoice-page__form-label">发票类型：</span>
                <el-radio-group v-model="dataContainer.invoiceType" class="ml-4">
                    <el-radio :label="1">公司</el-radio>
                    <el-radio :label="2">个人</el-radio>
                </el-radio-group>
            </div>
            <div class="invoice-page__form-row">
                <span class="invoice-page__form-label">开票内容：</span>
                <el-input class="invoice-page__form-input" v-model="dataContainer.invoiceContent" type="text"
                    placeholder="请输入发票信息"></el-input>
            </div>
            <div class="invoice-page__form-row">
                <span class="invoice-page__form-label">发票抬头：</span>
                <el-input class="invoice-page__form-input" v-model="dataContainer.invoiceHeader" type="text"
                    placeholder="请输入发票抬头"></el-input>
            </div>
            <div class="invoice-page__form-row">
                <span class="invoice-page__form-label">纳税人识别号：</span>
                <el-input class="invoice-page__form-input" v-model="dataContainer.taxPayerNumber" type="text"
                    placeholder="请输入纳税人识别号"></el-input>
            </div>
            <div class="invoice-page__form-row">
                <span class="invoice-page__form-label">用户地址：</span>
                <el-input class="invoice-page__form-input" v-model="dataContainer.bankAddress" type="text"
                    placeholder="请输入用户地址"></el-input>
            </div>
            <div class="invoice-page__form-row">
                <span class="invoice-page__form-label">注册电话：</span>
                <el-input class="invoice-page__form-input" v-model="dataContainer.bankPhone" type="text"
                    placeholder="请输入注册电话"></el-input>
            </div>
            <div class="invoice-page__form-row">
                <span class="invoice-page__form-label">申请人开户行：</span>
                <el-input class="invoice-page__form-input" v-model="dataContainer.bankDeposit" type="text"
                    placeholder="请输入申请人开户行"></el-input>
            </div>
            <div class="invoice-page__form-row">
                <span class="invoice-page__form-label">银行卡号：</span>
                <el-input class="invoice-page__form-input" v-model="dataContainer.bankNumber" type="text"
                    placeholder="请输入银行卡号"></el-input>
            </div>
            <div class="invoice-page__form-row">
                <span class="invoice-page__form-label">发票金额：</span>
                <el-input class="invoice-page__form-input" v-model="dataContainer.invoiceMoney" type="text"
                    placeholder="请输入发票金额"></el-input>
            </div>
            <div class="invoice-page__form-row">
                <span class="invoice-page__form-label">接收邮件：</span>
                <el-input class="invoice-page__form-input" v-model="dataContainer.userEmail" type="text"
                    placeholder="请输入接收邮件"></el-input>
            </div>
            <div class="invoice-page__form-row">
                <span class="invoice-page__form-label">接收手机号：</span>
                <el-input class="invoice-page__form-input" v-model="dataContainer.userPhone" type="text"
                    placeholder="请输入接收手机号"></el-input>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dataContainer.dialogFormVisibleU = false">取消</el-button>
                    <el-button type="primary" @click="StairNoticeToU">确认</el-button>
                </span>
            </template>
        </el-dialog>

        <!-- 审核 弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleSh" :title="dataContainer.titles" width="80%" center>
            <div class="invoice-page__form-row">
                <span class="invoice-page__form-label">审核结果：</span>
                <el-radio-group v-model="dataContainer.checkCertification" class="ml-4">
                    <el-radio :label="1">通过</el-radio>
                    <el-radio :label="2">拒绝</el-radio>
                </el-radio-group>
            </div>
            <div class="invoice-page__form-row invoice-page__form-row--textarea">
                <span class="invoice-page__form-label">审核说明：</span>
                <el-input class="invoice-page__form-input" v-model="dataContainer.checkCertificationMessage" type="textarea"
                    rows="4" placeholder="请输入审核说明"></el-input>
            </div>
            <template #footer>
                <span class="dialog-footer">
                    <el-button @click="dataContainer.dialogFormVisibleSh = false">取消</el-button>
                    <el-button type="primary" @click="shenheNoticeToU">确认</el-button>
                </span>
            </template>
        </el-dialog>
    </div>
</template>
<script>
/**
 * 页面例子
 */
import { ref, defineComponent, reactive, toRef } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import SvgIcon from '@/components/svgIcon/index.vue';
import { messageSuccess, messageError } from '@/action/messagePrompt';
import { findTag, deleteTags } from '@/action/tagListTools';
import http from '@/http/request.js';
import userApi from '@/http/user.js';
import { Filter } from '@element-plus/icons-vue';
import {
    // provinceAndCityData,
    regionData,
    codeToText,
    // provinceAndCityDataPlus,
    // regionDataPlus

} from 'element-china-area-data'
export default defineComponent({
    components: {
        SvgIcon,
        Filter,
    },
    setup() {
        const router = useRouter();
        const route = useRoute();
        const url = ''
        const cascaderAddr = ref(regionData);
        const getCodeToText = (codeStr) => {
            let codeArray = codeStr.split(",");
            let area = codeToText[codeArray[0]] + codeToText[codeArray[1]] + codeToText[codeArray[2]];
            return area;
        }
        const dataContainer = reactive({
            loading: false,
            activeName: 'first',
            tableData: {},
            page: 1,
            limit: 10,
            classify: '',
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
            statesnum: [{
                value: '',
                label: '全部'
            },
            {
                value: 1,
                label: '待开票'
            }, {
                value: 2,
                label: '已开票'
            }
            ],
            statusT: '',
            nickNameT: '',
            phoneT: '',
            dialogFormVisibleSh: false,
            checkCertificationMessage: '',
            checkCertification: '',


            invoiceId: '',
            userId: '',
            userType: '',//用户类型 1公司 2个人
            userName: '',
            invoiceType: '',//发票类型
            invoiceContent: '',//发票信息
            invoiceHeader: '',//发票抬头
            taxPayerNumber: '',//纳税人识别号
            bankAddress: '',//用户地址
            bankPhone: '',//注册电话
            bankDeposit: '',//申请人开户行
            bankNumber: '',//银行卡号
            invoiceMoney: '',//发票金额
            userEmail: '',//申请人电子邮件
            indentNumber: '',
            userPhone: '',



            province: '', //省
            city: '', //市
            district: '', //区
            options: regionData,
            storeAddress: [],
            storeAddre: '请选择城市',



        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '发票列表') {
                // dataContainer.classify = 1
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
        function clearChangeU() {
            dataContainer.classify = ''
            dataContainer.nickNameT = ''
            dataContainer.phoneT = ''
            dataContainer.page = 1
            dataSelect();
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.get('admin/invoice/adminGetIndentList', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    status: dataContainer.classify,
                    phone: dataContainer.phoneT,
                    indentNumber: dataContainer.nickNameT,
                    startTime: '',
                    endTime: '',
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
            console.log('-------------', dataContainer.options)
            dataContainer.titles = '修改'
            dataContainer.invoiceId = row.invoiceId
            dataContainer.userId = row.userId
            dataContainer.userType = row.userType
            dataContainer.invoiceType = row.invoiceType
            dataContainer.invoiceContent = row.invoiceTitle
            dataContainer.invoiceHeader = row.invoiceHeader
            dataContainer.taxPayerNumber = row.taxPayerNumber
            dataContainer.bankAddress = row.bankAddress
            dataContainer.bankPhone = row.bankPhone
            dataContainer.bankDeposit = row.bankDeposit
            dataContainer.bankNumber = row.bankNumber
            dataContainer.invoiceMoney = row.invoiceMoney
            dataContainer.userEmail = row.userEmail
            dataContainer.indentNumber = row.indentNumber
            dataContainer.userPhone = row.userPhone
            dataContainer.dialogFormVisibleU = true

        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.identityCardFront = file.data.path
        }
        function handleAvatarSuccess2(file, fileList) {
            dataContainer.identityCardVerso = file.data.path
        }
        function handleAvatarSuccess3(file, fileList) {
            dataContainer.drivingLicence = file.data.path
        }
        // 获取省市区
        function handleChange5(value) {
            const value0 = value[0]
            const value1 = value[1]
            const value2 = value[2]
            console.log(codeToText[value0], '====', value)


            //    value = cascaderAddr.currentLabels
            // value = this.$refs['cascaderAddr'].currentLabels
            dataContainer.province = codeToText[value0]
            dataContainer.city = codeToText[value1]
            dataContainer.district = codeToText[value2]
            if (dataContainer.city == '市辖区') {
                dataContainer.city = dataContainer.province
            }
        }
        function StairNoticeToU() {


            dataContainer.routeUrl = 'admin/invoice/updateInvoiceInfo'
            http.post(dataContainer.routeUrl, {
                invoiceId: dataContainer.invoiceId,
                userId: dataContainer.userId,
                userType: dataContainer.userType,
                invoiceType: dataContainer.invoiceType,
                invoiceTitle: dataContainer.invoiceContent,
                invoiceHeader: dataContainer.invoiceHeader,
                taxPayerNumber: dataContainer.taxPayerNumber,
                bankAddress: dataContainer.bankAddress,
                bankPhone: dataContainer.bankPhone,
                bankDeposit: dataContainer.bankDeposit,
                bankNumber: dataContainer.bankNumber,
                invoiceMoney: dataContainer.invoiceMoney,
                userEmail: dataContainer.userEmail,
                indentNumber: dataContainer.indentNumber,
                userPhone: dataContainer.userPhone,
            }, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then((res) => {
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
            ElMessageBox.confirm(`删除实名认证后，师傅将无法接单，确定删除?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                http.post('adminerrand/deleteCertification', {
                    userId: data.userId
                }, {
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded'
                    }
                }).then((res) => {
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
        // 审核 弹框
        function shenheChangeU(row) {
            dataContainer.titles = '审核'
            dataContainer.userId = row.userId
            dataContainer.checkCertificationMessage = ''
            dataContainer.checkCertification = ''
            dataContainer.dialogFormVisibleSh = true
        }
        function shenheNoticeToU() {
            if (dataContainer.checkCertification == '') {
                ElNotification({
                    title: '提示',
                    message: '请选择审核结果',
                    duration: 0,
                    type: 'warning'
                })
                return
            }
            if (dataContainer.checkCertification == 2) {
                if (dataContainer.checkCertificationMessage == '') {
                    ElNotification({
                        title: '提示',
                        message: '请输入审核理由',
                        duration: 0,
                        type: 'warning'
                    })
                    return
                }
            }

            http.post('adminerrand/checkCertification', {
                userId: dataContainer.userId,
                checkCertification: dataContainer.checkCertification,
                checkCertificationMessage: dataContainer.checkCertificationMessage,
            }, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then((res) => {
                console.log(res)
                if (res.code == 0) {
                    ElMessage({
                        message: '操作成功',
                        type: 'success',
                        onClose: () => {
                            dataContainer.dialogFormVisibleSh = false
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
        // 修改状态
        function change3(row) {
            http.post('admin/invoice/updateInvoice', {
                ids: row.invoiceId,
                status: row.status,
            }, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
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
        function isAuth(res) {
            return userApi.isAuth(res)
        }


        return {
            isAuth,
            dataContainer,
            addChangeU,
            handleClick,
            handleSearch,
            clearChangeU,
            dataSelect,
            handleSizeChange,
            handleCurrentChange,
            changeBtn,
            handleAvatarSuccess1,
            handleAvatarSuccess2,
            handleAvatarSuccess3,
            StairNoticeToU,
            handleDelete,
            updates,
            shenheChangeU,
            shenheNoticeToU,
            handleChange5,
            change3,


        };
    },
});
</script>



<style lang="scss" scoped>
.invoice-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.invoice-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.invoice-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.invoice-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.invoice-page__filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.invoice-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.invoice-page__filter-input {
    width: 150px;
}

.invoice-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.invoice-page__tabs {
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

.invoice-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.invoice-page__action-btn {
    margin: 4px 0;
}

.invoice-page__link {
    color: var(--nf-brand);
    cursor: pointer;
}

.invoice-page__status--pending {
    color: #f56c6c;
}

.invoice-page__status--done {
    color: #67c23a;
}

.invoice-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.invoice-page__form-row {
    margin-bottom: var(--nf-space-sm);
    display: flex;
    align-items: flex-start;
}

.invoice-page__form-row--textarea {
    align-items: flex-start;
}

.invoice-page__form-label {
    width: 200px;
    flex-shrink: 0;
    display: inline-block;
    text-align: right;
    color: var(--nf-text-primary);
    font-size: 14px;
    line-height: 32px;
}

.invoice-page__form-row--textarea .invoice-page__form-label {
    line-height: 1.5;
    padding-top: 6px;
}

.invoice-page__form-input {
    width: 50%;
}
</style>
