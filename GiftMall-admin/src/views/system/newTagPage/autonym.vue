<template>
    <div class="page-container">
        <el-tabs v-model="dataContainer.activeName" class="demo-tabs" @tab-click="handleClick">
            <el-tab-pane label="认证列表" name="first">
                <div style="text-align: left;margin: 5px;">
                    <div style="display: inline-block;margin: 5px;">
                        <span>状态：</span>
                        <el-select v-model="dataContainer.classify" class="m-2" placeholder="请选择状态"
                            style="width: 150px;" @change="handleSearch">
                            <el-option v-for="(item, index) in dataContainer.statesnum" :key="index" :label="item.label"
                                :value="item.value" />
                        </el-select>
                    </div>
                    <div style="display: inline-block;margin: 5px;">
                        <span>昵称：</span>
                        <el-input v-model="dataContainer.nickNameT" placeholder="请输入昵称"
                            @keydown.enter.native="handleSearch" style="width: 150px;" />
                    </div>
                    <div style="display: inline-block;margin: 5px;">
                        <span>电话:</span>
                        <el-input v-model="dataContainer.phoneT" placeholder="请输入电话"
                            @keydown.enter.native="handleSearch" style="width: 150px;" />
                    </div>
                    <el-button size="small" type="primary" plain @click="handleSearch()">刷新</el-button>
                    <el-button size="small" type="primary" @click="clearChangeU()">重置</el-button>
                </div>
                <el-table :data="dataContainer.tableData.list" v-loading="dataContainer.loading" style="width: 100%">
                    <el-table-column prop="userId" label="编号" width="80" fixed="left">
                        <!-- <template #default="scope">
					<span>{{scope.$index+1}}</span>
				</template> -->
                    </el-table-column>
                    <el-table-column prop="nickName" label="昵称" fixed="left">
                        <template #default="scope">
                            <span style="color: var(--nf-brand);cursor: pointer;" @click="updates(scope.row.userId)">{{
                                scope.row.userName ? scope.row.userName :
                                    '未绑定' }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="realName" label="真实姓名" fixed="left">
                        <template #default="scope">
                            <span>{{ scope.row.realName ? scope.row.realName : '未绑定' }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="birthMention" label="出生日期" width="125">
                        <template #default="scope">
                            <span>{{ scope.row.birthMention ? scope.row.birthMention : '未绑定' }}</span>
                        </template>
                    </el-table-column>

                    <el-table-column prop="drivingYear" label="驾龄(年)">
                        <template #default="scope">
                            <span>{{ scope.row.drivingYear ? scope.row.drivingYear : '未绑定' }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="phone" label="电话" width="150">
                    </el-table-column>
                    <!-- <el-table-column prop="identityCardNumber" label="身份证号" width="165">
			</el-table-column> -->
                    <el-table-column prop="identityCardFront" label="身份证正面">
                        <template #default="scope">
                            <div v-if="scope.row.identityCardFront == null || scope.row.identityCardFront == ''">
                                暂无图片
                            </div>
                            <div v-else-if="scope.row.identityCardFront.includes(',')"
                                style="display:flex;flex-wrap: wrap;">
                                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                                    v-for="(item, index) in scope.row.identityCardFront.split(',')" :key="index">
                                    <template #reference>
                                        <img style="width: 50px; height: 50px;margin: 3px;" :src="item" alt="">
                                    </template>
                                    <template #default style="width: 420px;">
                                        <img style="width: 400px; height: auto;" :src="item" alt="">
                                    </template>
                                </el-popover>
                            </div>
                            <div v-else>
                                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                    <template #reference>
                                        <img style="width: 50px; height: 50px;margin: 3px;"
                                            :src="scope.row.identityCardFront" alt="">
                                    </template>
                                    <template #default style="width: 420px;">
                                        <img style="width: 400px; height: auto;" :src="scope.row.identityCardFront"
                                            alt="">
                                    </template>
                                </el-popover>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="identityCardVerso" label="身份证反面">
                        <template #default="scope">
                            <div v-if="scope.row.identityCardVerso == null || scope.row.identityCardVerso == ''">
                                暂无图片
                            </div>
                            <div v-else-if="scope.row.identityCardVerso.includes(',')"
                                style="display:flex;flex-wrap: wrap;">
                                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                                    v-for="(item, index) in scope.row.identityCardVerso.split(',')" :key="index">
                                    <template #reference>
                                        <img style="width: 50px; height: 50px;margin: 3px;" :src="item" alt="">
                                    </template>
                                    <template #default style="width: 420px;">
                                        <img style="width: 400px; height: auto;" :src="item" alt="">
                                    </template>
                                </el-popover>
                            </div>
                            <div v-else>
                                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                    <template #reference>
                                        <img style="width: 50px; height: 50px;margin: 3px;"
                                            :src="scope.row.identityCardVerso" alt="">
                                    </template>
                                    <template #default style="width: 420px;">
                                        <img style="width: 400px; height: auto;" :src="scope.row.identityCardVerso"
                                            alt="">
                                    </template>
                                </el-popover>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="drivingLicence" label="师傅驾驶证图片">
                        <template #default="scope">
                            <div v-if="scope.row.drivingLicence == null || scope.row.drivingLicence == ''">
                                暂无图片
                            </div>
                            <div v-else-if="scope.row.drivingLicence.includes(',')"
                                style="display:flex;flex-wrap: wrap;">
                                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;"
                                    v-for="(item, index) in scope.row.drivingLicence.split(',')" :key="index">
                                    <template #reference>
                                        <img style="width: 50px; height: 50px;margin: 3px;" :src="item" alt="">
                                    </template>
                                    <template #default style="width: 420px;">
                                        <img style="width: 400px; height: auto;" :src="item" alt="">
                                    </template>
                                </el-popover>
                            </div>
                            <div v-else>
                                <el-popover placement="top-start" title="" trigger="hover" popper-style="width:420px;">
                                    <template #reference>
                                        <img style="width: 50px; height: 50px;margin: 3px;"
                                            :src="scope.row.drivingLicence" alt="">
                                    </template>
                                    <template #default style="width: 420px;">
                                        <img style="width: 400px; height: auto;" :src="scope.row.drivingLicence" alt="">
                                    </template>
                                </el-popover>
                            </div>
                        </template>
                    </el-table-column>
                    <el-table-column prop="checkCertificationMessage" label="省市区" width="160">
                        <template #default="scope">
                            <span>{{ scope.row.province }}/{{ scope.row.city }}/{{ scope.row.district }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="checkCertificationMessage" label="审核内容" width="160">
                    </el-table-column>
                    <el-table-column prop="auditTime" label="审核时间" width="170">
                    </el-table-column>
                    <el-table-column prop="createTime" label="提交时间" width="170">
                    </el-table-column>
                    <el-table-column fixed='right' prop="status" label="状态" width="160">
                        <template #default="scope">
                            <span v-if="scope.row.checkCertification == '0'">待审核</span>
                            <span v-if="scope.row.checkCertification == '1'">审核成功</span>
                            <span v-if="scope.row.checkCertification == '2'">已拒绝</span>
                        </template>
                    </el-table-column>
                    <el-table-column label="操作" width="160" fixed="right">
                        <template #default="scope">
                            <el-button size="small" type="primary" @click="shenheChangeU(scope.row)"
                                :disabled="!isAuth('autonym:tongguo')" style="margin: 5px;"
                                v-if="scope.row.checkCertification == '0'" plain>审核</el-button>
                            <el-button size="small" type="primary" @click="addChangeU(scope.row)" style="margin: 5px;"
                                :disabled="!isAuth('autonym:update')">修改</el-button>
                            <el-button size="small" type="danger" @click="handleDelete(scope.row)" style="margin: 5px;"
                                :disabled="!isAuth('autonym:delete')">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div style="text-align: center;margin-top: 10px;">
                    <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                        :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.tableData.totalCount">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <!-- 添加/修改弹框 -->
            <el-dialog v-model="dataContainer.dialogFormVisibleU" :title="dataContainer.titles" width="80%" center>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">昵称：</span>
                    <el-input style="width:50%;" v-model="dataContainer.userName" type="text"
                        placeholder="请输入昵称"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">真实姓名：</span>
                    <el-input style="width:50%;" v-model="dataContainer.realName" type="text"
                        placeholder="请输入真实姓名"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">驾龄(年)：</span>
                    <el-input style="width:50%;" v-model="dataContainer.drivingYear" type="text"
                        placeholder="请输入驾龄"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">出生日期：</span>
                    <el-date-picker style="width:50%;" v-model="dataContainer.birthMention" type="date"
                        placeholder="请输入出生日期" format="yyyy 年 MM 月 dd 日" value-format="yyyy-MM-dd">
                    </el-date-picker>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">电话：</span>
                    <el-input style="width:50%;" v-model="dataContainer.phone" type="text"
                        placeholder="请输入电话"></el-input>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">省市区：</span>
                    <el-cascader style="width:50%" :options="dataContainer.options" ref="cascaderAddr"
                        v-model="dataContainer.storeAddress" :placeholder="dataContainer.storeAddre"
                        @change="handleChange5" :value="dataContainer.storeAddre">
                        <template #default="{ data }">
                            <span>{{ data.label }}</span>
                        </template>
                    </el-cascader>

                </div>
                <!-- <div style="margin-bottom: 10px;">
				<span style="width: 200px;display: inline-block;text-align: right;">身份证号：</span>
				<el-input style="width:50%;" v-model="dataContainer.identityCardNumber" type="text" placeholder="请输入身份证号"></el-input>
			</div> -->
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">身份证正面图：</span>
                    <div style="display: inline-block;">
                        <el-upload class="avatar-uploader" v-model="dataContainer.identityCardFront"
                            style=" width:148px;height:148px;border: 1px dashed #c0ccda;border-radius: 6px;text-align: center;line-height: 148px;color: #efefef;"
                            action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                            :on-success="handleAvatarSuccess1">
                            <img v-if="dataContainer.identityCardFront" :src="dataContainer.identityCardFront"
                                class="avatar" style="width: 148px;height: 148px;" />
                            <SvgIcon v-else style="width:23px;height:23px;color: #efefef;" name="svg:plus.svg">
                            </SvgIcon>
                        </el-upload>
                    </div>
                </div>
                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">身份证反面图：</span>
                    <div style="display: inline-block;">
                        <el-upload class="avatar-uploader" v-model="dataContainer.identityCardVerso"
                            style=" width:148px;height:148px;border: 1px dashed #c0ccda;border-radius: 6px;text-align: center;line-height: 148px;color: #efefef;"
                            action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                            :on-success="handleAvatarSuccess2">
                            <img v-if="dataContainer.identityCardVerso" :src="dataContainer.identityCardVerso"
                                class="avatar" style="width: 148px;height: 148px;" />
                            <SvgIcon v-else style="width:23px;height:23px;color: #efefef;" name="svg:plus.svg">
                            </SvgIcon>
                        </el-upload>
                    </div>
                </div>

                <div style="margin-bottom: 10px;">
                    <span style="width: 200px;display: inline-block;text-align: right;">师傅驾驶证图片：</span>
                    <div
                        style="display: inline-block;width:148px;height:148px;border: 1px dashed #c0ccda;border-radius: 6px;text-align: center;line-height: 148px;">
                        <el-upload class="avatar-uploader" v-model="dataContainer.drivingLicence"
                            style=" width:148px;height:148px;border: 1px dashed #c0ccda;border-radius: 6px;text-align: center;line-height: 148px;color: #efefef;"
                            action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                            :on-success="handleAvatarSuccess3">
                            <img v-if="dataContainer.drivingLicence" :src="dataContainer.drivingLicence" class="avatar"
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
                        <el-button @click="dataContainer.dialogFormVisibleSh = false"
                            style="margin: 10px;">取消</el-button>
                        <el-button type="primary" @click="shenheNoticeToU" style="margin: 10px;">确认</el-button>
                    </span>
                </template>
            </el-dialog>
        </el-tabs>
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
export default defineComponent({
    components: {
        SvgIcon
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



        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '认证列表') {
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
            dataContainer.classify = -1
            dataContainer.nickNameT = ''
            dataContainer.phoneT = ''
            dataContainer.page = 1
            dataSelect();
        }
        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.get('adminerrand/findAllCertification', {
                params: {
                    page: dataContainer.page,
                    limit: dataContainer.limit,
                    checkCertification: dataContainer.classify,
                    phone: dataContainer.phoneT,
                    nickName: dataContainer.nickNameT,
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
            // dataContainer.id = row.id
            // dataContainer.name = row.name
            // dataContainer.imageUrl = row.imageUrl
            // dataContainer.url = row.url
            // dataContainer.sort = row.sort
            // dataContainer.description = row.description
            // dataContainer.state = row.state

            dataContainer.phone = row.phone
            dataContainer.userName = row.nickName
            // dataContainer.identityCardNumber = row.identityCardNumber
            dataContainer.identityCardFront = row.identityCardFront
            dataContainer.identityCardVerso = row.identityCardVerso
            dataContainer.userId = row.userId
            dataContainer.realName = row.realName
            dataContainer.birthMention = row.birthMention
            dataContainer.drivingLicence = row.drivingLicence
            dataContainer.drivingYear = row.drivingYear
            if (row.province) {
                dataContainer.province = row.province
                dataContainer.city = row.city
                dataContainer.district = row.district
                dataContainer.storeAddress = []
                dataContainer.storeAddre = dataContainer.province + '/' + dataContainer.city + '/' + dataContainer.district
            } else {
                dataContainer.province = ''
                dataContainer.city = ''
                dataContainer.district = ''
                dataContainer.storeAddress = []
                dataContainer.storeAddre = '请选择省市区'
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

            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'adminerrand/updateCertification'
            } else {
                dataContainer.routeUrl = 'adminerrand/updateCertification'
            }
            http.post(dataContainer.routeUrl, {
                userId: dataContainer.userId,
                phone: dataContainer.phone,
                userName: dataContainer.userName,
                // identityCardNumber: dataContainer.identityCardNumber,
                identityCardFront: dataContainer.identityCardFront,
                identityCardVerso: dataContainer.identityCardVerso,
                realName: dataContainer.realName,
                birthMention: dataContainer.birthMention,
                drivingLicence: dataContainer.drivingLicence,
                drivingYear: dataContainer.drivingYear,
                province: dataContainer.province,
                city: dataContainer.city,
                district: dataContainer.district,
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


        };
    },
});
</script>



<style lang="scss" scoped>
.new-tag-page-view {
    display: flex;
    flex-direction: column;
    width: 100%;
    justify-content: center;
    align-items: center;

    >.container {
        // background-color: white;
        width: 100%;
        max-width: 1200px;
        height: fit-content;
        border-radius: 12px;
        padding: 60px 15px;
        box-sizing: border-box;
        display: flex;
        flex-direction: column;
        align-items: center;
        background-image: url(https://cn.bing.com/th?id=OHR.MtPrevostDuncan_ZH-CN2333619635_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp);
        background-size: cover;
        background-repeat: no-repeat;
        box-shadow: 1px 1px 3px 0 rgba(0, 0, 0, 0.319);
        background-position: center top;

        >.title {
            background: -webkit-linear-gradient(120deg, var(--nf-brand-lite2) 30%, var(--nf-brand));
            -webkit-background-clip: text;
            background-clip: text;
            -webkit-text-fill-color: transparent;
            font-size: 50px;
            margin-bottom: 55px;
            letter-spacing: 5px;
            font-weight: bold;
        }

        >.search {
            display: flex;
            flex-wrap: wrap;
            justify-content: center;
            align-items: center;
            width: 100%;
            margin-bottom: 30px;

            :deep(.el-input) {
                width: 100%;
                max-width: 700px;
                height: 50px;
                border: 2px solid rgb(3, 3, 3);
                box-shadow: 1px 1px 3px 0 rgba(0, 0, 0, 0.319);
                border-radius: 12px;
                overflow: hidden;
                font-size: 15px;
                padding: 0 15px;
                backdrop-filter: blur(12px);
                background-color: #ffffff3d;

                .el-input__wrapper {
                    box-shadow: none !important;
                    padding: 0 !important;

                    input {
                        color: rgb(255, 255, 255);
                    }
                }

                .el-input__suffix {
                    .el-icon {
                        font-size: 20px !important;
                        color: #3c3c3c !important;
                    }
                }
            }
        }

        >.item-list {
            display: flex;
            flex-direction: row;
            justify-content: center;
            flex-wrap: wrap;

            >.item {
                padding: 30px 30px;

                >.container {
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    justify-content: center;
                    position: relative;
                    cursor: pointer;
                    color: white;

                    &:hover {
                        >.icon {
                            transform: scale(1.1);
                            box-shadow: 3px 3px rgba(0, 0, 0, 0.1);
                        }

                        >.name {
                            color: #5240ff;
                            font-weight: bold;
                        }
                    }

                    >.icon {
                        width: 60px;
                        height: 60px;
                        display: flex;
                        flex-direction: row;
                        align-items: center;
                        justify-content: center;
                        background-color: rgb(240, 240, 240);
                        border-radius: 50%;
                        transition: all 0.2s;
                        border: 2px solid rgb(3, 3, 3);
                        box-sizing: border-box;
                        box-shadow: 1px 1px 1px 0 rgba(0, 0, 0, 0.653);
                        color: rgb(56, 56, 56);
                    }

                    >.name {
                        transition: all 0.2s;
                        width: 150px;
                        text-align: center;
                        position: absolute;
                        top: calc(100% + 10px);
                    }
                }
            }
        }
    }
}
</style>
