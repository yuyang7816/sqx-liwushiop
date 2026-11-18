<template>
    <div class="specification-page">
        <div class="nf-page-hero specification-page__hero">
            <span class="nf-page-hero__badge">商品规格</span>
            <h2 class="nf-page-hero__title">规格列表</h2>
            <p class="nf-page-hero__desc">管理商品规格信息，支持新增、修改与删除</p>
        </div>

        <div class="nf-filter-card specification-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>快捷操作</span>
            </div>
            <div class="nf-filter-card__body specification-page__filter-body">
                <el-button type="primary" @click="handleSearch()">刷新</el-button>
                <el-button type="primary" @click="addChangeU()"
                    :disabled="!isAuth('classifyAdmin:add')">新增</el-button>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="specification-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="规格列表" name="first">
                <div class="nf-table-card specification-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="80">
                        </el-table-column>
                        <el-table-column prop="ruleName" label="规格名称">
                        </el-table-column>
                        <el-table-column label="规格值">
                            <template #default="scope">
                                <div v-for="(item, index) in scope.row.giftProductRuleValueList" :key="index">
                                    <div>
                                        <span>{{ item.value }}:</span>
                                        <span>{{ item.detail }}</span>
                                    </div>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="170">
                        </el-table-column>
                        <el-table-column label="操作" width="160">
                            <template #default="scope">
                                <el-button size="small" type="primary" class="specification-page__action-btn"
                                    @click="addChangeU(scope.row)"
                                    :disabled="!isAuth('classifyAdmin:update')">修改</el-button>
                                <el-button size="small" type="danger" class="specification-page__action-btn"
                                    @click="handleDelete(scope.row)"
                                    :disabled="!isAuth('classifyAdmin:delete')">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="specification-page__pagination">
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
            <div class="specification-page__form-row">
                <span class="specification-page__form-label">规格标题：</span>
                <el-input v-model="dataContainer.ruleName" class="specification-page__form-input"
                    placeholder="请输入规格标题："></el-input>
            </div>
            <div class="specification-page__form-row">
                <div class="specification-page__rule-list">
                    <div class="ruleitem" v-for="(item, index) in dataContainer.ruleValue" :key="index">
                        <div>{{ item.value }}
                            <span @click="deleterule1(index)">
                                <Delete style="width: 1em; height: 1em; margin-right: 8px" />
                            </span>
                        </div>
                        <div>
                            <el-tag :key="tag" v-for="(tag, j) in item.detail.split(',')" closable
                                :disable-transitions="false" @close="handleClose(tag, j, item)">
                                {{ tag }}
                            </el-tag>
                            <el-input class="input-new-tag" v-model="dataContainer.inputValues[index]"
                                ref="saveTagInput" size="small" placeholder="请输入属性名"
                                @keyup.enter.native="handleInputConfirm(index, item)">
                            </el-input>
                            <el-button class="button-new-tag" size="small"
                                @click="handleInputConfirm(index, item)">添加</el-button>
                        </div>
                    </div>
                </div>
            </div>
            <div class="specification-page__form-row">
                <div class="specification-page__add-spec-wrap">
                    <div v-if="dataContainer.isshow" class="btn_specif">
                        <el-button size="small" type="primary" @click="btnToclick1()">添加新规格</el-button>
                    </div>
                </div>
                <div v-if="!dataContainer.isshow" class="specification-page__spec-inputs">
                    <div class="specification-page__spec-field">
                        <span class="specification-page__spec-label">规格：</span>
                        <el-input v-model="dataContainer.releobject.value" placeholder="请输入规格"></el-input>
                    </div>
                    <div class="specification-page__spec-field">
                        <span class="specification-page__spec-label">规格值：</span>
                        <el-input v-model="dataContainer.releobject.detail" placeholder="请输入规格值"></el-input>
                    </div>
                    <div class="specification-page__spec-actions">
                        <el-button type="primary" size="small" @click="speciTo1()">确 定</el-button>
                        <el-button size="small" @click="speciTotwo1()">取 消</el-button>
                    </div>
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
            routeUrl: '',
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
            nickNameT: '',
            phoneT: '',
            dialogFormVisibleSh: false,
            id: '',
            name: '',
            img: '',
            sort: '',
            parentId: 0,
            ruleName: '',
            releobject: {
                value: '',
                detail: '',
                id: '',
                ruleId: '',
            },
            ruleValue: [],
            isshow: false,
            inputVisible: false,
            inputValues: {
                inputValue0: '',
                inputValue1: '',
                inputValue2: '',
                inputValue3: '',
                inputValue4: '',
                inputValue5: ''
            },
            createTime: '',



        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '规格列表') {
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
            http.get('admin/rule/page', {
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

        /**
         * 添加、修改弹框
         */
        function addChangeU(row) {
            if (row) {

                dataContainer.titles = '修改'
                var rowsValue = JSON.parse(JSON.stringify(row));
                dataContainer.id = rowsValue.id
                dataContainer.ruleName = rowsValue.ruleName
                dataContainer.ruleValue = rowsValue.giftProductRuleValueList
                dataContainer.createTime = rowsValue.createTime



            } else {
                dataContainer.titles = '添加'
                dataContainer.id = ''
                dataContainer.ruleName = ''
                dataContainer.ruleValue = []
                dataContainer.createTime = ''
            }
            dataContainer.dialogFormVisibleU = true

        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.img = file.data.path
        }
        function StairNoticeToU() {
            if (dataContainer.ruleName === '') {
                ElNotification({
                    title: '提示',
                    message: '请输入规格标题',
                    type: 'warning',
                })
                return
            }

            //  if(dataContainer.img==''){
            //      ElNotification({
            //         title: '提示',
            //         message: '请上传图片',
            //         type: 'warning',
            //     })
            //     return
            // }


            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'admin/rule/save'
            } else {
                dataContainer.routeUrl = 'admin/rule/update'
            }
            http.post(dataContainer.routeUrl, {
                id: dataContainer.id,
                ruleName: dataContainer.ruleName,
                giftProductRuleValueList: dataContainer.ruleValue,

            }, {
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
                http.get('admin/rule/delete', {
                    params: {
                        id: data.id
                    }
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
        // 添加 新规格框
        function btnToclick1() {
            dataContainer.isshow = false
            dataContainer.releobject.value = ''
            dataContainer.releobject.detail = ''
            dataContainer.releobject.id = ''
            dataContainer.releobject.ruleId = dataContainer.id

        }
        // 添加属性 值
        function handleInputConfirm(index, item) {
            console.log(index, item);
            let inputValue = dataContainer.inputValues[index];
            if (inputValue) {
                var detailarr = item.detail.split(',')
                detailarr.push(inputValue)
                for (var i = 0; i < detailarr.length; i++) {
                    for (var j = 0; j < detailarr.length; j++) {
                        if (detailarr[i] == detailarr[j] && i != j) {
                            detailarr.splice(j, 1);
                        }
                    }
                }
                item.detail = String(detailarr)
                dataContainer.inputValues[index] = '';
            }
        }
        // 删除属性值
        function handleClose(tag, j, item) {
            var detailarr = item.detail.split(',')
            if (detailarr.length > 1) {
                detailarr.splice(detailarr.indexOf(tag), 1);
                item.detail = String(detailarr)
                console.log(item.detail)
            } else {
                console.log(item.detail)
                ElMessage({
                    message: '规格值至少要有一个',
                    type: 'warning',
                    onClose: () => {
                    }
                })

            }
        }
        // 确定 添加规格值
        function speciTo1() {




            dataContainer.ruleValue.push({
                value: dataContainer.releobject.value,
                detail: dataContainer.releobject.detail,
                id: dataContainer.releobject.id,
                ruleId: dataContainer.releobject.ruleId
            });
            dataContainer.isshow = true
            dataContainer.releobject.value = ''
            dataContainer.releobject.detail = ''
            dataContainer.releobject.id = ''
            dataContainer.releobject.ruleId = dataContainer.id
        }
        // 取消 添加规格值
        function speciTotwo1() {
            dataContainer.isshow = true
            dataContainer.releobject.value = ''
            dataContainer.releobject.detail = ''
            dataContainer.releobject.id = ''
            dataContainer.releobject.ruleId = dataContainer.id
        }
        // 删除规格
        function deleterule1(index) {
            dataContainer.ruleValue.splice(index, 1);
        }

        /** 获取列表数据 */

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
            StairNoticeToU,
            handleDelete,
            handleAvatarSuccess1,

            btnToclick1,
            handleInputConfirm,
            handleClose,
            speciTo1,
            speciTotwo1,
            deleterule1,


        };
    },
});
</script>



<style lang="scss" scoped>
.specification-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.specification-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.specification-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.specification-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.specification-page__tabs {
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

.specification-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.specification-page__action-btn {
    margin: 4px 0;
}

.specification-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.specification-page__form-row {
    margin-bottom: var(--nf-space-sm);
}

.specification-page__form-label {
    width: 200px;
    display: inline-block;
    text-align: right;
    color: var(--nf-text-primary);
    font-size: 14px;
}

.specification-page__form-input {
    width: 200px;
}

.specification-page__rule-list {
    margin-left: 200px;
}

.ruleitem {
    padding-left: var(--nf-space-sm);
    margin-top: var(--nf-space-sm);
}

.el-tag + .el-tag {
    margin-left: var(--nf-space-sm);
}

.button-new-tag {
    height: 32px;
    line-height: 31px;
    padding-top: 0;
    padding-bottom: 0;
    border-radius: 4px;
    border-top-left-radius: 0;
    border-bottom-left-radius: 0;
    background-color: var(--nf-sidebar-bg-alt);
    position: relative;
    left: -5px;
    border: 1px solid var(--nf-border);
}

.input-new-tag {
    width: 120px;
    margin-left: var(--nf-space-sm);
    vertical-align: bottom;
}

.specification-page__add-spec-wrap {
    margin-left: 200px;
}

.specification-page__spec-inputs {
    margin-top: var(--nf-space-md);
    margin-left: 200px;
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.specification-page__spec-field {
    display: flex;
    align-items: center;
    gap: var(--nf-space-xs);
}

.specification-page__spec-label {
    white-space: nowrap;
    color: var(--nf-text-primary);
    font-size: 14px;
}

.specification-page__spec-actions {
    display: flex;
    gap: var(--nf-space-xs);
}
</style>
