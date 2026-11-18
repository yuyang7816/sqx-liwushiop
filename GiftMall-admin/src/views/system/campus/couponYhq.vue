<template>
    <div class="coupon-yhq-page">
        <div class="nf-page-hero coupon-yhq-page__hero">
            <span class="nf-page-hero__badge">优惠券管理</span>
            <h2 class="nf-page-hero__title">优惠券配置</h2>
            <p class="nf-page-hero__desc">管理优惠券列表，支持新增、修改、赠送与删除</p>
        </div>

        <div class="nf-filter-card coupon-yhq-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>快捷操作</span>
            </div>
            <div class="nf-filter-card__body coupon-yhq-page__filter-body">
                <el-button type="primary" @click="handleSearch()">刷新</el-button>
                <el-button type="primary" @click="addChangeU()" :disabled="!isAuth('couponYhq:add')">新增</el-button>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="coupon-yhq-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="优惠券列表" name="first">
                <div class="nf-table-card coupon-yhq-page__table">
                <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column prop="couponId" label="编号" width="80" fixed="left">
                    </el-table-column>
                    <!-- <el-table-column prop="couponType" label="优惠券类型" width="100" fixed="left">
                        <template #default="scope">
                            <span v-if="scope.row.couponType==0">全部可用</span>
                            <span v-if="scope.row.couponType==1">代泊</span>
                            <span v-if="scope.row.couponType==2">代取</span>
                            <span v-if="scope.row.couponType==3">接机</span>
                            <span v-if="scope.row.couponType==4">送机</span>
                            
                        </template>
</el-table-column> -->
                    <el-table-column prop="couponName" label="优惠券名称" fixed="left" width="160">
                    </el-table-column>
                    <el-table-column prop="couponPicture" label="优惠券图片">
                        <template #default="scope">
                            <div v-if="scope.row.couponPicture == null || scope.row.couponPicture == ''">
                                暂无图片
                            </div>
                            <div v-else>
                                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                    <template #reference>
                                        <img class="coupon-yhq-page__thumb"
                                            :src="scope.row.couponPicture" alt="">
                                    </template>
                                    <template #default style="width: 420px;">
                                        <img class="coupon-yhq-page__preview" :src="scope.row.couponPicture" alt="">
                                    </template>
                                </el-popover>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="money" label="优惠券抵扣金额" width="100">
                    </el-table-column>
                    <el-table-column prop="minMoney" label="优惠券最低消费" width="150">
                    </el-table-column>
                    <el-table-column prop="validDays" label="优惠券有天数" width="150">
                        <template #default="scope">
                            <span>{{ scope.row.validDays == 0 ? '永久' : scope.row.validDays }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="maxReceive" label="最多领取或购买数量" width="150">
                        <template #default="scope">
                            <span>{{ scope.row.maxReceive == 0 ? '不限' : scope.row.maxReceive }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="couponNum" label="优惠券数量" width="150">
                    </el-table-column>
                    <el-table-column prop="isEnable" label="是否启用" width="150">
                        <template #default="scope">
                            <span>{{ scope.row.isEnable == 1 ? '启用' : '未启用' }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createTime" label="创建时间" width="170">
                    </el-table-column>
                    <el-table-column label="操作" width="160" fixed="right">
                        <template #default="scope">
                            <el-button size="small" type="primary" @click="shenheChangeU(scope.row)"
                                :disabled="!isAuth('couponYhq:zengsong')" class="coupon-yhq-page__action-btn" plain>赠送</el-button>
                            <el-button size="small" type="primary" @click="addChangeU(scope.row)"
                                class="coupon-yhq-page__action-btn"
                                :disabled="!isAuth('couponYhq:update')">修改</el-button>
                            <el-button size="small" type="danger" @click="handleDelete(scope.row)"
                                class="coupon-yhq-page__action-btn"
                                :disabled="!isAuth('couponYhq:delete')">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="coupon-yhq-page__pagination">
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
                <!-- <div style="margin-bottom: 10px;">
				<span style="width: 200px;display: inline-block;text-align: right;">优惠券类型：</span>
                <el-radio-group v-model="dataContainer.couponType">
					<el-radio :label="0">全部可用</el-radio>
					<el-radio :label="1">代泊</el-radio>
					<el-radio :label="2">代取</el-radio>
					<el-radio :label="3">接机</el-radio>
					<el-radio :label="4">送机</el-radio>
				</el-radio-group>
			</div> -->
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">优惠券名称：</span>
                    <el-input style="width:50%;" v-model="dataContainer.couponName" type="text"
                        placeholder="请输入优惠券名称"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">优惠券抵扣金额：</span>
                    <el-input style="width:50%;" v-model="dataContainer.money" type="text"
                        placeholder="请输入优惠券抵扣金额"></el-input>
                </div>

                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">优惠券最低消费：</span>
                    <el-input style="width:50%;" v-model="dataContainer.minMoney" type="text"
                        placeholder="请输入优惠券最低消费"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">优惠券数量：</span>
                    <el-input style="width:50%;" v-model="dataContainer.couponNum" type="text"
                        placeholder="请输入优惠券数量"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">有效期天数：</span>
                    <el-input style="width:50%;" v-model="dataContainer.validDays" type="text"
                        placeholder="请输入有效期天数"></el-input>
                    <span style="color: red;">*0为永久</span>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">最多领取或购买数量：</span>
                    <el-input style="width:50%;" v-model="dataContainer.maxReceive" type="text"
                        placeholder="请输入最多领取或购买数量"></el-input>
                    <span style="color: red;">*0为不限</span>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">是否启用：</span>
                    <el-radio-group v-model="dataContainer.isEnable">
                        <el-radio :label="1">是</el-radio>
                        <el-radio :label="0">否</el-radio>
                    </el-radio-group>

                </div>

                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">优惠券图片：</span>
                    <div style="display: inline-block;">
                        <el-upload class="avatar-uploader" v-model="dataContainer.couponPicture"
                            style=" width:148px;height:148px;border: 1px dashed #c0ccda;border-radius: 6px;text-align: center;line-height: 148px;color: #efefef;"
                            action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                            :on-success="handleAvatarSuccess1">
                            <img v-if="dataContainer.couponPicture" :src="dataContainer.couponPicture" class="avatar"
                                style="width: 148px;height: 148px;" />
                            <SvgIcon v-else style="width:23px;height:23px;color: #efefef;" name="svg:plus.svg">
                            </SvgIcon>
                        </el-upload>
                    </div>
                </div>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dataContainer.dialogFormVisibleU = false"
                            style="margin: 10px;">取消</el-button>
                        <el-button type="primary" @click="StairNoticeToU" style="margin: 10px;">确认</el-button>
                    </span>
                </template>
            </el-dialog>
            <!-- 赠送优惠券 弹框 -->
            <el-dialog v-model="dataContainer.dialogFormVisibleSh" title="赠送优惠券" width="80%" center>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">选择用户：</span>
                    <el-tag :key="j" v-for="(tag, j) in dataContainer.userNameList" closable
                        :disable-transitions="false" style="margin: 5px;" @close="couponIdClose1(tag, j)">
                        {{ tag }}
                    </el-tag>
                    <el-button class="button-new-tag" size="small" @click="couponIdBtn1()">添加</el-button>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">赠送数量：</span>
                    <el-input style="width:50%;" v-model="dataContainer.checkCertificationMessage" type="text"
                        placeholder="请输入赠送数量"></el-input>
                </div>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dataContainer.dialogFormVisibleSh = false"
                            style="margin: 10px;">取消</el-button>
                        <el-button type="primary" @click="shenheNoticeToU" style="margin: 10px;">确认</el-button>
                    </span>
                </template>
            </el-dialog>
            <!-- 用户列表 弹框 -->
            <el-dialog v-model="dataContainer.dialogFormVisibleUse" title="用户列表" width="70%" center>
                <div>
                    <div style="display: inline-block;margin: 5px;">
                        <span>用户昵称：</span>
                        <el-input v-model="dataContainer.nickNameT" placeholder="请输入用户昵称"
                            @keydown.enter.native="selectU" style="width: 150px;" />
                    </div>
                    <div style="display: inline-block;margin: 5px;">
                        <span>手机号:</span>
                        <el-input v-model="dataContainer.phoneT" placeholder="请输入手机号" @keydown.enter.native="selectU"
                            style="width: 150px;" />
                    </div>
                    <el-button size="small" type="primary" plain @click="selectU()">刷新</el-button>
                    <el-button size="small" type="primary" @click="clearChangeU()">重置</el-button>
                    <el-button style="margin-left:15px;" :disabled="dataContainer.xzList.length <= 0" size="small"
                        type="warning" @click="closes2()">确定</el-button>
                </div>
                <div class="table-container">
                    <el-table ref="multipleTableRef" v-loading="dataContainer.tableDataLoading"
                        :data="dataContainer.userData.list" @selection-change="changeFun1">
                        <el-table-column type="selection" fixed>
                        </el-table-column>
                        <el-table-column prop="userId" align="center" label="编号" width="60"></el-table-column>
                        <el-table-column label="用户昵称" align="center" min-width="170" prop="nickName">
                            <template #default="scope">
                                {{ scope.row.userName ? scope.row.userName : scope.row.nickName }}
                            </template>
                        </el-table-column>
                        <el-table-column label="图像">
                            <template #default="scope">
                                <img v-if="scope.row.avatar == null" src="../../../assets/logo.png" alt="" width="40"
                                    height="40">
                                <img v-else :src="scope.row.avatar" alt="" width="40" height="40">
                            </template>
                        </el-table-column>
                        <el-table-column prop="phone" label="手机号"></el-table-column>
                        <el-table-column prop="platform" label="渠道来源"></el-table-column>
                        <el-table-column prop="invitationCode" label="邀请码"></el-table-column>
                        <el-table-column label="邀请人邀请码"></el-table-column>

                    </el-table>
                </div>
                <div class="pagination-container" style="margin: 10px;text-align: right;">
                    <el-pagination v-show="true" :total="dataContainer.userData.totalCount" :background="true"
                        :current-page="dataContainer.page1" :page-size="dataContainer.limit1"
                        layout="total, sizes, prev, pager, next, jumper" :page-sizes="[10, 20, 30, 50]"
                        @size-change="handleSizeChangeU" @current-change="handleCurrentChangeU" />
                </div>
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
import {
    // provinceAndCityData,
    regionData,
    codeToText,
    // provinceAndCityDataPlus,
    // regionDataPlus

} from 'element-china-area-data'
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
        const cascaderAddr = ref(regionData);
        const multipleTableRef = ref()
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
            classify: -1,
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
                value: -1,
                label: '全部'
            },
            {
                value: 0,
                label: '待审核'
            }, {
                value: 1,
                label: '审核成功'
            }, {
                value: 2,
                label: '拒绝'
            }
            ],
            statusT: '',
            nickNameT: '',
            phoneT: '',
            dialogFormVisibleSh: false,
            checkCertificationMessage: '',
            checkCertification: '',

            userId: '',
            phone: '',
            userName: '',

            couponId: '',
            couponName: '',
            money: '',
            minMoney: '',
            maxReceivev: '',
            couponPicture: '',
            validDays: '',
            isEnable: '',
            couponType: '',
            couponNum: '',
            userNameList: [],
            dialogFormVisibleUse: false,
            tableDataLoading: false,
            userData: {},
            inviterCode: '',
            page1: 1,
            limit1: 10,
            userIdList: [],
            userNameList: [],
            xzList: [],






        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '优惠券列表') {
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

        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.get('admin/coupon/getCouponPageList', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,

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
        function handleSizeChangeU(val) {
            dataContainer.limit1 = val
            dataSelect1()
        }
        function handleCurrentChangeU(val) {
            dataContainer.page1 = val
            dataSelect1()
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
            if (row) {

                dataContainer.titles = '修改'
                dataContainer.couponId = row.couponId
                dataContainer.couponName = row.couponName
                dataContainer.money = row.money
                dataContainer.minMoney = row.minMoney
                dataContainer.maxReceive = row.maxReceive
                dataContainer.couponPicture = row.couponPicture
                dataContainer.validDays = row.validDays
                dataContainer.isEnable = row.isEnable
                // dataContainer.couponType = row.couponType
                dataContainer.couponNum = row.couponNum
            } else {
                dataContainer.titles = '添加'
                dataContainer.couponId = ''
                dataContainer.couponName = ''
                dataContainer.money = ''
                dataContainer.minMoney = ''
                dataContainer.maxReceive = ''
                dataContainer.couponPicture = ''
                dataContainer.validDays = ''
                dataContainer.isEnable = ''
                // dataContainer.couponType = ''
                dataContainer.couponNum = ''
            }


            dataContainer.dialogFormVisibleU = true





        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.couponPicture = file.data.path
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

            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'admin/coupon/addCoupon'
            } else {
                dataContainer.routeUrl = 'admin/coupon/updateCoupon'
            }
            http.post(dataContainer.routeUrl, {
                couponId: dataContainer.couponId,
                couponName: dataContainer.couponName,
                money: dataContainer.money,
                minMoney: dataContainer.minMoney,
                maxReceivev: dataContainer.maxReceivev,
                couponPicture: dataContainer.couponPicture,
                validDays: dataContainer.validDays,
                isEnable: dataContainer.isEnable,
                // couponType: dataContainer.couponType,
                couponNum: dataContainer.couponNum,

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
            ElMessageBox.confirm(`确定删除数据?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                http.post('admin/coupon/deleteCoupon', {
                    couponId: data.couponId
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
            dataContainer.couponId = row.couponId
            dataContainer.checkCertificationMessage = ''
            dataContainer.dialogFormVisibleSh = true
        }
        function shenheNoticeToU() {
            if (dataContainer.userIdList.length == 0) {
                ElNotification({
                    title: '提示',
                    message: '请选择赠送用户',
                    duration: 3000,
                    type: 'warning'
                })
                return
            }
            if (dataContainer.checkCertificationMessage == '') {
                ElNotification({
                    title: '提示',
                    message: '请输入赠送数量',
                    duration: 3000,
                    type: 'warning'
                })
                return
            }

            http.post('admin/couponUser/giveUserCoupon', {
                userIds: dataContainer.userIdList.toString(),
                couponId: dataContainer.couponId,
                num: dataContainer.checkCertificationMessage,
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
        // 打开用户列表
        function couponIdBtn1() {
            dataContainer.page1 = 1
            dataContainer.phone = ''
            dataContainer.userName = ''
            dataContainer.inviterCode = ''
            dataSelect1()
            dataContainer.dialogFormVisibleUse = true
        }
        // 获取用户数据列表
        function dataSelect1() {
            dataContainer.tableDataLoading = true
            http.get('adminerrand/findAllUser', {
                params: {
                    page: dataContainer.page1,
                    limit: dataContainer.limit1,
                    userType: '',
                    nickName: dataContainer.nickNameT,
                    phone: dataContainer.phoneT,
                    platform: '',
                    invitationCode: '',
                    inviterCode: dataContainer.inviterCode,
                    isVip: '',
                }
            }, {
                // headers: {
                //     'Content-Type': 'application/json'
                // }
            }).then((res) => {
                console.log(res)
                dataContainer.tableDataLoading = false
                if (res.code == 0) {
                    dataContainer.userData = res.data
                } else {

                }
            })
        }
        function selectU() {
            dataContainer.page1 = 1
            dataSelect1()
        }
        function clearChangeU() {
            dataContainer.page1 = 1
            dataContainer.phoneT = ''
            dataContainer.nickNameT = ''
            dataContainer.inviterCode = ''
            dataSelect1()
        }

        // 删除用户
        function couponIdClose1(ietm, index) {
            console.log('index', index, 'ietm', ietm, dataContainer.userIdList.length)
            if (dataContainer.userIdList.length < 2) {
                ElNotification({
                    title: '提示',
                    message: '最少需要选中一个用户',
                    duration: 3000,
                    type: 'warning'
                })
                return
            } else {
                dataContainer.userIdList.splice(index, 1)
                dataContainer.userNameList.splice(index, 1)
            }
        }
        // 多选用户
        function changeFun1(val) {
            console.log('多选', val)
            dataContainer.xzList = val
        }
        // 确定选择用户
        function closes2() {
            for (var i in dataContainer.xzList) {
                dataContainer.userIdList.push(dataContainer.xzList[i].userId)
                dataContainer.userNameList.push(dataContainer.xzList[i].nickName)
            }
            dataContainer.dialogFormVisibleUse = false
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
            handleAvatarSuccess2,
            handleAvatarSuccess3,
            StairNoticeToU,
            handleDelete,
            updates,
            shenheChangeU,
            shenheNoticeToU,
            handleChange5,
            couponIdBtn1,
            dataSelect1,
            selectU,
            clearChangeU,
            closes2,
            changeFun1,
            couponIdClose1,
            handleSizeChangeU,
            handleCurrentChangeU,

        };
    },
});
</script>



<style lang="scss" scoped>
.coupon-yhq-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.coupon-yhq-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.coupon-yhq-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.coupon-yhq-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.coupon-yhq-page__tabs {
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

.coupon-yhq-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.coupon-yhq-page__action-btn {
    margin: 4px 0;
}

.coupon-yhq-page__thumb {
    width: 50px;
    height: 50px;
    margin: 3px;
}

.coupon-yhq-page__preview {
    width: 400px;
    height: auto;
}

.coupon-yhq-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}
</style>
