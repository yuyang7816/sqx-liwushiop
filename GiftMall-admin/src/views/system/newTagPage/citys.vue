<template>
    <div class="citys-page">
        <div class="nf-page-hero citys-page__hero">
            <span class="nf-page-hero__badge">城市管理</span>
            <h2 class="nf-page-hero__title">城市与区域配置</h2>
            <p class="nf-page-hero__desc">管理省/直辖市、市、区县级城市信息及计价规则</p>
        </div>

        <div class="nf-filter-card citys-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body citys-page__filter-body">
                <div class="citys-page__filter-item">
                    <span class="citys-page__filter-label">省/直辖市名称</span>
                    <el-input v-model="dataContainer.phoneT" placeholder="请输入省/直辖市名称"
                        @keydown.enter.native="handleSearch" class="citys-page__filter-input" clearable />
                </div>
                <el-button type="primary" @click="handleSearch()">刷新</el-button>
                <el-button type="primary" @click="clearChangeU()">重置</el-button>
                <el-button type="primary" @click="addChangeU()" :disabled="!isAuth('citys:add')">添加</el-button>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="citys-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="城市列表" name="first">
                <div class="nf-table-card citys-page__table">
                <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading" style="width: 100%"
                    lazy :load="load" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }" ref="ElTableRef"
                    :default-expand-all="dataContainer.shows" v-if="dataContainer.refreshTable" row-key="id"
                    height="100%">
                    <el-table-column fixed prop="id" label="编号" width="150">
                    </el-table-column>
                    <el-table-column prop="cityName" label="名称" width="200">
                    </el-table-column>
                    <el-table-column prop="baseKilometres" label="基础公里数">
                    </el-table-column>
                    <el-table-column prop="baseMoney" label="基础价格">
                    </el-table-column>
                    <el-table-column prop="unitOverMoney" label="额外每公里价格(单位:元)">
                    </el-table-column>
                    <el-table-column prop="nightMoney" label="每公里费用(22:00~23:59)">
                    </el-table-column>
                    <el-table-column prop="earlyMoney" label="每公里费用(00:00~06:59)">
                    </el-table-column>
                    <el-table-column prop="minuteMoney" label="每分钟时长费(单位:元)">
                    </el-table-column>
                    <el-table-column prop="pingRate" label="平台抽成比例(0.1表示10%)">
                    </el-table-column>
                    <el-table-column prop="riderRate" label="师傅默认佣金比例">
                    </el-table-column>
                    <el-table-column prop="waitMinuteMoney" label="等待每分钟价格">
                    </el-table-column>
                    <el-table-column prop="freeWaitTime" label="免费等待时长(分钟)">
                    </el-table-column>
                    <el-table-column prop="sort" label="排序">
                    </el-table-column>
                    <el-table-column prop="isEnable" label="是否启用" fixed="right">
                        <template #default="scope">
                            <!-- <el-switch v-if="scope.row.isEnable" v-model="scope.row.isEnable" @change="change3(scope.row.isEnable,scope.row)"
                            :active-value="openValue" :inactive-value="closeValue" active-color="#13ce66" inactive-color="#ff4949">
                        </el-switch> -->
                            <el-switch v-model="scope.row.isEnable" class="ml-2" :active-value="dataContainer.active"
                                :inactive-value="dataContainer.inactive" @change="change3(scope.row)"
                                style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949" />
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="160" fixed="right">
                        <template #default="scope">
                            <el-button size="small" type="primary" @click="addChangeU(scope.row)"
                                :disabled="!isAuth('citys:update')" class="citys-page__action-btn">修改</el-button>
                            <el-button size="small" type="danger" @click="handleDelete(scope.row)"
                                :disabled="!isAuth('citys:delete')" class="citys-page__action-btn">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                </div>
            </el-tab-pane>
        </el-tabs>

        <!-- 添加/修改弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisibleU" :title="dataContainer.titles" width="80%" center>
                <div v-if="dataContainer.titles == '添加'">
                    <div style="margin-bottom: 10px;">
                        <span style="width: 200px;display: inline-block;text-align: right;">等级：</span>
                        <el-radio-group v-model="dataContainer.radio" class="ml-4">
                            <el-radio :label="0">省/直辖市</el-radio>
                            <el-radio :label="1">市</el-radio>
                            <el-radio :label="2">区/县</el-radio>
                        </el-radio-group>
                    </div>
                    <div style="margin-bottom: 10px;" v-if="dataContainer.radio == 1 || dataContainer.radio == 2">
                        <span style="width: 200px;display: inline-block;text-align: right;">省/直辖市：</span>
                        <el-select v-model="dataContainer.provinceId" class="m-2" placeholder="请选择省/直辖市"
                            style="width: 50%;" @change="select">
                            <el-option v-for="(item, index) in dataContainer.provinceList" :key="index"
                                :label="item.cityName" :value="item.id" />
                        </el-select>
                    </div>
                    <div style="margin-bottom: 10px;" v-if="dataContainer.radio == 2">
                        <span style="width: 200px;display: inline-block;text-align: right;">市：</span>
                        <el-select v-model="dataContainer.cityId" class="m-2" placeholder="请选择市" style="width: 50%;">
                            <el-option v-for="(item, index) in dataContainer.cityList" :key="index"
                                :label="item.cityName" :value="item.id" />
                        </el-select>
                    </div>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">名称：</span>
                    <el-input style="width:50%;" v-model="dataContainer.cityName" type="text"
                        placeholder="请输入名称"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">排序：</span>
                    <el-input style="width:50%;" v-model="dataContainer.sort" type="text"
                        placeholder="请输入排序"></el-input>
                </div>
                <div style="margin-bottom: 10px;" v-if="dataContainer.parentId != 0">
                    <span style="width: 200px;display: inline-block;text-align: right;">基础公里数：</span>
                    <el-input style="width:50%;" v-model="dataContainer.baseKilometres" type="text"
                        placeholder="请输入基础公里数"></el-input>
                </div>
                <div style="margin-bottom: 10px;" v-if="dataContainer.parentId != 0">
                    <span style="width: 200px;display: inline-block;text-align: right;">基础价格：</span>
                    <el-input style="width:50%;" v-model="dataContainer.baseMoney" type="text"
                        placeholder="请输入基础价格"></el-input>
                </div>
                <div style="margin-bottom: 10px;" v-if="dataContainer.parentId != 0">
                    <span style="width: 200px;display: inline-block;text-align: right;">额外每公里价格(单位:元)：</span>
                    <el-input style="width:50%;" v-model="dataContainer.unitOverMoney" type="text"
                        placeholder="请输入额外每公里价格(单位:元)"></el-input>
                </div>
                <div style="margin-bottom: 10px;" v-if="dataContainer.parentId != 0">
                    <span style="width: 200px;display: inline-block;text-align: right;">每公里费用(22:00~23:59)：</span>
                    <el-input style="width:50%;" v-model="dataContainer.nightMoney" type="text"
                        placeholder="请输入每公里费用(22:00~23:59)"></el-input>
                </div>
                <div style="margin-bottom: 10px;" v-if="dataContainer.parentId != 0">
                    <span style="width: 200px;display: inline-block;text-align: right;">每公里费用(00:00~06:59)：</span>
                    <el-input style="width:50%;" v-model="dataContainer.earlyMoney" type="text"
                        placeholder="请输入每公里费用(00:00~06:59)"></el-input>
                </div>
                <div style="margin-bottom: 10px;" v-if="dataContainer.parentId != 0">
                    <span style="width: 200px;display: inline-block;text-align: right;">每分钟时长费(单位:元)：</span>
                    <el-input style="width:50%;" v-model="dataContainer.minuteMoney" type="text"
                        placeholder="请输入每分钟时长费(单位:元)"></el-input>
                </div>
                <div style="margin-bottom: 10px;" v-if="dataContainer.parentId != 0">
                    <span style="width: 200px;display: inline-block;text-align: right;">平台抽成比例(0.1表示10%)：</span>
                    <el-input style="width:50%;" v-model="dataContainer.pingRate" type="text"
                        placeholder="请输入平台抽成比例(0.1表示10%)"></el-input>
                </div>
                <div style="margin-bottom: 10px;" v-if="dataContainer.parentId != 0">
                    <span style="width: 200px;display: inline-block;text-align: right;">师傅默认佣金比例：</span>
                    <el-input style="width:50%;" v-model="dataContainer.riderRate" type="text"
                        placeholder="请输入师傅默认佣金比例"></el-input>
                </div>
                <div style="margin-bottom: 10px;" v-if="dataContainer.parentId != 0">
                    <span style="width: 200px;display: inline-block;text-align: right;">等待每分钟价格：</span>
                    <el-input style="width:50%;" v-model="dataContainer.waitMinuteMoney" type="text"
                        placeholder="请输入等待每分钟价格"></el-input>
                </div>
                <div style="margin-bottom: 10px;" v-if="dataContainer.parentId != 0">
                    <span style="width: 200px;display: inline-block;text-align: right;">免费等待时长(分钟)：</span>
                    <el-input style="width:50%;" v-model="dataContainer.freeWaitTime" type="text"
                        placeholder="请输入免费等待时长(分钟)"></el-input>
                </div>
                <div style="margin-bottom: 10px;" v-if="dataContainer.parentId != 0">
                    <span style="width: 200px;display: inline-block;text-align: right;">同步师傅分佣比例：</span>
                    <el-radio-group v-model="dataContainer.isCover" class="ml-4">
                        <el-radio :label="1">是</el-radio>
                        <el-radio :label="0">否</el-radio>
                    </el-radio-group>
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
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">审核结果：</span>
                    <el-radio-group v-model="dataContainer.checkCertification" class="ml-4">
                        <el-radio :label="1">通过</el-radio>
                        <el-radio :label="2">拒绝</el-radio>
                    </el-radio-group>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">审核说明：</span>
                    <el-input style="width:50%;" v-model="dataContainer.checkCertificationMessage" type="textarea"
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
        const ElTableRef = ref(null); //组件实例
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

            id: '',
            sort: '',
            isEnable: '',
            radio: '',
            cityName: '',
            provinceId: '',
            cityId: '',
            parentId: 0,
            agentUserId: '',
            baseKilometres: '',
            baseMoney: '',
            unitOverMoney: '',
            nightMoney: '',
            earlyMoney: '',
            minuteMoney: '',
            pingRate: '',
            riderRate: '',
            waitMinuteMoney: '',
            freeWaitTime: '',
            isCover: 0,

            userId: '',
            phone: '',
            userName: '',
            identityCardNumber: '',
            identityCardFront: '',
            identityCardVerso: '',
            selfIdentityCard: '',
            drivingLicence: '',
            realName: '',
            drivingYear: '',
            birthMention: '',
            userIds: '',
            province: '', //省
            city: '', //市
            district: '', //区
            options: regionData,
            storeAddress: [],
            storeAddre: '请选择城市',
            provinceList: [],
            cityList: [],
            shows: false,
            refreshTable: true,



        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '城市列表') {
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
            dataContainer.refreshTable = false
            http.get('admin/cityInfo/getCityInfoList', {
                params: {
                    parentId: 0,
                    cityName: dataContainer.phoneT,

                }
            }, {
            }).then((res) => {
                dataContainer.refreshTable = true;
                dataContainer.loading = false;
                if (res.data.records.length > 0) {
                    for (var i in res.data.records) {
                        if (res.data.records[i].hasChildren == 2) {
                            res.data.records[i].children = []
                            res.data.records[i].hasChildren = true
                        } else {
                            res.data.records[i].hasChildren = false
                        }
                    }
                }
                dataContainer.tableData = {}
                dataContainer.shows = false
                let returnData = res.data
                dataContainer.tableData = returnData
                dataContainer.provinceList = returnData.records
            })
        }
        function load(tree, treeNode, resolve) {
            console.log(tree, treeNode, '-----', resolve)
            if (tree != undefined) {
                dataContainer.loading = true
                http.get('admin/cityInfo/getCityInfoList', {
                    params: {
                        parentId: tree.id,
                        cityName: dataContainer.phoneT,

                    }
                }, {
                }).then((res) => {
                    console.log(res)
                    dataContainer.loading = false;
                    if (res.data.records.length > 0) {
                        for (var i in res.data.records) {
                            if (res.data.records[i].hasChildren == 2) {
                                res.data.records[i].children = []
                                res.data.records[i].hasChildren = true
                            } else {
                                res.data.records[i].hasChildren = false
                            }
                        }
                    }
                    let returnData = res.data;
                    resolve(returnData.records);
                    console.log('----', returnData.records)
                })
            }



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
                console.log('-------------', dataContainer.options)
                dataContainer.titles = '修改'
                dataContainer.id = row.id
                dataContainer.sort = row.sort
                dataContainer.isEnable = row.isEnable
                dataContainer.radio = row.radio
                dataContainer.cityName = row.cityName
                dataContainer.provinceId = row.provinceId
                dataContainer.cityId = row.cityId
                dataContainer.parentId = row.parentId
                dataContainer.agentUserId = row.agentUserId
                dataContainer.baseKilometres = row.baseKilometres
                dataContainer.baseMoney = row.baseMoney
                dataContainer.unitOverMoney = row.unitOverMoney
                dataContainer.nightMoney = row.nightMoney
                dataContainer.earlyMoney = row.earlyMoney
                dataContainer.minuteMoney = row.minuteMoney
                dataContainer.pingRate = row.pingRate
                dataContainer.riderRate = row.riderRate
                dataContainer.waitMinuteMoney = row.waitMinuteMoney
                dataContainer.freeWaitTime = row.freeWaitTime
                dataContainer.isCover = row.isCover
            } else {
                dataContainer.titles = '添加'
                dataContainer.id = ''
                dataContainer.sort = ''
                dataContainer.isEnable = 1
                dataContainer.radio = 0
                dataContainer.cityName = ''
                dataContainer.provinceId = ''
                dataContainer.cityId = ''
                dataContainer.parentId = 0
                dataContainer.agentUserId = ''
                dataContainer.baseKilometres = ''
                dataContainer.baseMoney = ''
                dataContainer.unitOverMoney = ''
                dataContainer.nightMoney = ''
                dataContainer.earlyMoney = ''
                dataContainer.minuteMoney = ''
                dataContainer.pingRate = ''
                dataContainer.riderRate = ''
                dataContainer.waitMinuteMoney = ''
                dataContainer.freeWaitTime = ''
                dataContainer.isCover = 0




            }


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
            if (dataContainer.radio == 1) {

                dataContainer.parentId = dataContainer.provinceId

            }
            if (dataContainer.radio == 2) {

                dataContainer.parentId = dataContainer.cityId
            }

            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'admin/cityInfo/saveCityInfo'
            } else {
                dataContainer.routeUrl = 'admin/cityInfo/saveCityInfo'
            }
            http.post(dataContainer.routeUrl, {
                isEnable: dataContainer.isEnable,
                sort: dataContainer.sort,
                cityName: dataContainer.cityName,
                id: dataContainer.id,
                parentId: dataContainer.parentId,
                agentUserId: dataContainer.agentUserId,
                baseKilometres: dataContainer.baseKilometres,
                baseMoney: dataContainer.baseMoney,
                unitOverMoney: dataContainer.unitOverMoney,
                nightMoney: dataContainer.nightMoney,
                earlyMoney: dataContainer.earlyMoney,
                minuteMoney: dataContainer.minuteMoney,
                pingRate: dataContainer.pingRate,
                riderRate: dataContainer.riderRate,
                waitMinuteMoney: dataContainer.waitMinuteMoney,
                freeWaitTime: dataContainer.freeWaitTime,
                isCover: dataContainer.isCover,
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
                            load()
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
            ElMessageBox.confirm(`确定删除此条信息?`, '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                http.post('admin/cityInfo/deleteCityInfoById', {
                    id: data.id
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

            http.get('admin/cityInfo/updateEnable', {
                id: row.id,
            }, {
                // headers: {
                //     'Content-Type': 'application/x-www-form-urlencoded'
                // }
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
        /** 展开/折叠行 */
        function handleExpand() {
            if (!ElTableRef.value) return;
            dataContainer.defaultExpandAll = !dataContainer.defaultExpandAll;
            function setupState(list) {
                list.forEach((item) => {
                    ElTableRef.value.toggleRowExpansion(item);
                    if (item.children) {
                        setupState(item.children);
                    }
                });
            }
            setupState(dataContainer.list);
        }
        function select() {
            http.get('admin/cityInfo/getCityInfoList', {
                params: {
                    parentId: dataContainer.provinceId,
                    cityName: '',

                }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.loading = false;
                if (res.data.records.length > 0) {
                    for (var i in res.data.records) {
                        if (res.data.records[i].hasChildren == 2) {
                            res.data.records[i].children = []
                            res.data.records[i].hasChildren = true
                        } else {
                            res.data.records[i].hasChildren = false
                        }
                    }
                }
                dataContainer.cityList = {}
                let returnData = res.data
                dataContainer.cityList = returnData.records
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
            load,
            handleExpand,
            select,


        };
    },
});
</script>



<style lang="scss" scoped>
.citys-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.citys-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.citys-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.citys-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.citys-page__filter-item {
    display: flex;
    align-items: center;
    gap: var(--nf-space-xs);
}

.citys-page__filter-label {
    font-size: 14px;
    color: var(--nf-text-secondary);
    white-space: nowrap;
}

.citys-page__filter-input {
    width: 180px;
}

.citys-page__tabs {
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

.citys-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.citys-page__action-btn {
    margin: 4px 0;
}
</style>
