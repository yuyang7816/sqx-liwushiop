<template>
    <div class="shop-admin-page">
        <div class="nf-page-hero shop-admin-page__hero">
            <span class="nf-page-hero__badge">商品管理</span>
            <h2 class="nf-page-hero__title">商品列表</h2>
            <p class="nf-page-hero__desc">管理团购商品信息，支持按名称、状态、类型与送礼场景筛选</p>
        </div>

        <div class="nf-filter-card shop-admin-page__filter">
            <div class="nf-filter-card__head">
                <el-icon><Filter /></el-icon>
                <span>筛选条件</span>
            </div>
            <div class="nf-filter-card__body shop-admin-page__filter-body">
                <div class="shop-admin-page__filter-item">
                    <span class="shop-admin-page__filter-label">商品名称：</span>
                    <el-input v-model="dataContainer.nameT" placeholder="请输入商品名称"
                        @keydown.enter.native="handleSearch" class="shop-admin-page__filter-input" />
                </div>
                <div class="shop-admin-page__filter-item">
                    <span class="shop-admin-page__filter-label">状态：</span>
                    <el-select v-model="dataContainer.statusT" class="shop-admin-page__filter-input"
                        placeholder="请选择状态" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.statesnum" :key="index"
                            :label="item.label" :value="item.value" />
                    </el-select>
                </div>
                <div class="shop-admin-page__filter-item">
                    <span class="shop-admin-page__filter-label">是否精选好物：</span>
                    <el-select v-model="dataContainer.goodPickT" class="shop-admin-page__filter-input"
                        placeholder="请选择是否精选好物" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.classnum" :key="index"
                            :label="item.label" :value="item.value" />
                    </el-select>
                </div>
                <div class="shop-admin-page__filter-item">
                    <span class="shop-admin-page__filter-label">是否特价商品：</span>
                    <el-select v-model="dataContainer.specialOfferT" class="shop-admin-page__filter-input"
                        placeholder="请选择是否特价商品" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.classnum" :key="index"
                            :label="item.label" :value="item.value" />
                    </el-select>
                </div>
                <div class="shop-admin-page__filter-item">
                    <span class="shop-admin-page__filter-label">商品类型：</span>
                    <el-select v-model="dataContainer.typeIdT" class="shop-admin-page__filter-input"
                        placeholder="请选择商品类型" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.typeList" :key="index"
                            :label="item.name" :value="item.id" />
                    </el-select>
                </div>
                <div class="shop-admin-page__filter-item">
                    <span class="shop-admin-page__filter-label">送礼场景：</span>
                    <el-select v-model="dataContainer.sceneIdT" class="shop-admin-page__filter-input"
                        placeholder="请选择送礼场景" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.sceneList" :key="index"
                            :label="item.name" :value="item.id" />
                    </el-select>
                </div>
                <div class="shop-admin-page__filter-item">
                    <span class="shop-admin-page__filter-label">送礼对象：</span>
                    <el-select v-model="dataContainer.targetIdT" class="shop-admin-page__filter-input"
                        placeholder="请选择送礼对象" @change="handleSearch">
                        <el-option v-for="(item, index) in dataContainer.targetList" :key="index"
                            :label="item.name" :value="item.id" />
                    </el-select>
                </div>
                <div class="shop-admin-page__filter-actions">
                    <el-button type="primary" @click="handleSearch()">刷新</el-button>
                    <el-button @click="clearSearch()">重置</el-button>
                    <el-button type="primary" @click="addChangeU()"
                        :disabled="!isAuth('classifyAdmin:add')">新增</el-button>
                </div>
            </div>
        </div>

        <el-tabs v-model="dataContainer.activeName" class="shop-admin-page__tabs" @tab-click="handleClick">
            <el-tab-pane label="商品列表" name="first">
                <div class="nf-table-card shop-admin-page__table">
                    <el-table :data="dataContainer.tableData.records" v-loading="dataContainer.loading"
                        style="width: 100%">
                        <el-table-column prop="id" label="编号" width="80" fixed="left">
                        </el-table-column>
                        <el-table-column prop="giftProductType.name" label="商品类型">
                        </el-table-column>
                        <el-table-column prop="sceneName" label="送礼场景">
                        </el-table-column>
                        <el-table-column prop="targetName" label="送礼对象">
                        </el-table-column>
                        <el-table-column prop="name" label="商品名称" fixed="left">
                        </el-table-column>
                        <el-table-column prop="coverImg" label="商品封面图片">
                            <template #default="scope">
                                <div v-if="scope.row.coverImg == null || scope.row.coverImg == ''">
                                    暂无图片
                                </div>
                                <div v-else>
                                    <el-popover placement="top-start" title="" trigger="hover"
                                        popper-style="width:420px;">
                                        <template #reference>
                                            <img class="shop-admin-page__thumb" :src="scope.row.coverImg" alt="">
                                        </template>
                                        <template #default>
                                            <img class="shop-admin-page__preview" :src="scope.row.coverImg" alt="">
                                        </template>
                                    </el-popover>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="img" label="商品图片">
                            <template #default="scope">
                                <div v-if="scope.row.img == null || scope.row.img == ''">
                                    暂无图片
                                </div>
                                <div v-else style="display:flex;flex-wrap: wrap;">
                                    <el-popover placement="top-start" title="" trigger="hover"
                                        popper-style="width:420px;"
                                        v-for="(item, index) in scope.row.img.split(',')" :key="index">
                                        <template #reference>
                                            <img class="shop-admin-page__thumb" :src="item" alt="">
                                        </template>
                                        <template #default>
                                            <img class="shop-admin-page__preview" :src="item" alt="">
                                        </template>
                                    </el-popover>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column prop="description" label="商品描述" width="150">
                        </el-table-column>
                        <el-table-column prop="originalPrice" label="原价">
                        </el-table-column>
                        <el-table-column prop="price" label="商品售价">
                        </el-table-column>
                        <el-table-column prop="discountRatio" label="折扣比例">
                        </el-table-column>
                        <el-table-column prop="status" label="商品状态">
                            <template #default="scope">
                                <span v-if="scope.row.status === 0">正常</span>
                                <span v-else>下架</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="goodPick" label="是否精选好物">
                            <template #default="scope">
                                <span v-if="scope.row.goodPick === 0">是</span>
                                <span v-else>否</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="specialOffer" label="是否特价商品">
                            <template #default="scope">
                                <span v-if="scope.row.specialOffer === 0">是</span>
                                <span v-else>否</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="createTime" label="创建时间" width="170">
                        </el-table-column>
                        <el-table-column label="操作" width="160" fixed="right">
                            <template #default="scope">
                                <el-button size="small" type="primary" class="shop-admin-page__action-btn"
                                    @click="addChangeU(scope.row)"
                                    :disabled="!isAuth('classifyAdmin:update')">修改</el-button>
                                <el-button size="small" type="danger" class="shop-admin-page__action-btn"
                                    @click="handleDelete(scope.row)"
                                    :disabled="!isAuth('classifyAdmin:delete')">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <div class="shop-admin-page__pagination">
                        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                            :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit"
                            :current-page="dataContainer.page" layout="total, sizes, prev, pager, next, jumper"
                            :total="dataContainer.tableData.total">
                        </el-pagination>
                    </div>
                </div>
            </el-tab-pane>

            <!-- 添加/修改弹框 -->
            <el-dialog v-model="dataContainer.dialogFormVisibleU" :title="dataContainer.titles" width="80%" center>
                <div class="shop-admin-page__form-row">
                    <span class="shop-admin-page__form-label">分类名称:</span>
                    <el-input class="shop-admin-page__form-input" v-model="dataContainer.name" type="text"
                        placeholder="请输入分类名称"></el-input>
                </div>
                <div class="shop-admin-page__form-row">
                    <span class="shop-admin-page__form-label">图片：</span>
                    <div class="shop-admin-page__upload-wrap">
                        <el-upload class="avatar-uploader shop-admin-page__uploader" v-model="dataContainer.img"
                            action="https://lwscadmin.xianmxkj.com/sqx_fast/file/upload" :show-file-list="false"
                            :on-success="handleAvatarSuccess1">
                            <img v-if="dataContainer.img" :src="dataContainer.img"
                                class="shop-admin-page__avatar" />
                            <SvgIcon v-else class="shop-admin-page__upload-icon" name="svg:plus.svg">
                            </SvgIcon>
                        </el-upload>
                    </div>
                </div>
                <div class="shop-admin-page__form-row">
                    <span class="shop-admin-page__form-label">排序：</span>
                    <el-input class="shop-admin-page__form-input" v-model="dataContainer.sort" type="text"
                        placeholder="请输入排序"></el-input>
                </div>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dataContainer.dialogFormVisibleU = false">取消</el-button>
                        <el-button type="primary" @click="StairNoticeToU">确认</el-button>
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
            nameT: '', //商品名称
            statusT: '', //商品状态(默认0正常 1下架)
            goodPickT: '', //是否精选好物 0是 1不是
            specialOfferT: '', //是否特价商品 0是 1不是
            typeIdT: '', //商品类型id
            sceneIdT: '', //送礼场景id
            targetIdT: '', //送礼对象id
            startBudgetT: '', //预算起始金额
            endBudgetT: '', //预算截止金额

            classify: '',
            dialogFormVisibleU: false,
            titles: '添加',
            routeUrl: '',
            statesnum: [{
                value: '',
                label: '全部'
            },
            {
                value: 0,
                label: '正常'
            }, {
                value: 1,
                label: '下架'
            }
            ],
            classnum: [
                {
                    value: '',
                    label: '全部'
                },
                {
                    value: 0,
                    label: '是'
                }, {
                    value: 1,
                    label: '不是'
                }
            ],
            typeList: [],
            sceneList: [],
            targetList: [],
            nickNameT: '',
            phoneT: '',
            dialogFormVisibleSh: false,
            id: '',
            name: "", //商品名称
            description: "", //商品描述
            coverImg: "", //商品封面图片
            img: "", //商品图片
            price: 1, //商品价格(售价)
            status: 1, //商品状态(默认0正常 1下架)
            goodPick: 1, //是否精选好物 0是 1不是
            specialOffer: 1, //是否特价商品 0是 1不是
            originalPrice: 1, //原价
            discountRatio: 1, //折扣比例 0.1表示10%1折
            typeId: 1, //商品类型id
            sceneId: 1, //送礼场景id
            targetId: 1, //送礼对象id
            giftProductAttrList: [], //商品规格
            giftProductSkuList: [] //商品sku



        });

        /** 跳转相应Tab */
        function handleClick(tab, event) {
            console.log(event.srcElement.innerText)

            if (event.srcElement.innerText == '商品列表') {
                dataSelect();
                typeSearch()
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
            dataContainer.nameT = ''
            dataContainer.statusT = ''
            dataContainer.goodPickT = ''
            dataContainer.specialOfferT = ''
            dataContainer.typeIdT = ''
            dataContainer.sceneIdT = ''
            dataContainer.targetIdT = ''
            dataContainer.startBudgetT = ''
            dataContainer.endBudgetT = ''
            dataContainer.page = 1
            dataSelect();
        }

        /** 获取列表数据 */
        function dataSelect() {
            dataContainer.loading = true
            http.get('admin/product/page?page=' + dataContainer.page + '&limit=' + dataContainer.limit, {
                params: {
                    // page: dataContainer.page,
                    // limit: dataContainer.limit,
                    name: dataContainer.nameT,
                    status: dataContainer.statusT,
                    goodPick: dataContainer.goodPickT,
                    specialOffer: dataContainer.specialOfferT,
                    typeId: dataContainer.typeIdT,
                    sceneId: dataContainer.sceneIdT,
                    targetId: dataContainer.targetIdT,
                    startBudget: dataContainer.startBudgetT,
                    endBudget: dataContainer.endBudgetT,

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
        typeSearch()
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
                router.push({
                    name: 'shopAmend',
                    query: {
                        id: row.id,
                    },
                });
            } else {
                router.push({
                    name: 'shopPublish',
                    query: {
                    },
                });
            }
        }
        function handleAvatarSuccess1(file, fileList) {
            dataContainer.img = file.data.path
        }
        function StairNoticeToU() {
            if (dataContainer.name === '') {
                ElNotification({
                    title: '提示',
                    message: '请输入分类名称',
                    type: 'warning',
                })
                return
            }

            if (dataContainer.img == '') {
                ElNotification({
                    title: '提示',
                    message: '请上传图片',
                    type: 'warning',
                })
                return
            }


            if (dataContainer.titles == '添加') {
                dataContainer.routeUrl = 'admin/productType/save'
            } else {
                dataContainer.routeUrl = 'admin/productType/update'
            }
            http.post(dataContainer.routeUrl, {
                id: dataContainer.id,
                img: dataContainer.img,
                name: dataContainer.name,
                sort: dataContainer.sort,
                parentId: dataContainer.parentId,
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
                http.get('admin/product/delete', {
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
        function typeSearch() {
            // 商品分类
            http.get('admin/productType/listNoPage', {
                params: {}
            }, {
            }).then((res) => {
                console.log(res)

                dataContainer.typeList = []
                let returnData = res.data
                dataContainer.typeList = returnData
            })
            // 送礼场景
            http.get('admin/scene/list', {
                params: {
                    page: 1,
                    limit: 50,
                }
            }, {
            }).then((res) => {
                console.log(res)

                dataContainer.sceneList = []
                let returnData = res.data
                dataContainer.sceneList = returnData.records
            })
            // 送礼对象
            http.get('admin/target/list', {
                params: {
                    page: 1,
                    limit: 50,
                }
            }, {
            }).then((res) => {
                console.log(res)

                dataContainer.targetList = []
                let returnData = res.data
                dataContainer.targetList = returnData.records
            })

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
            typeSearch,
            clearSearch,

        };
    },
});
</script>



<style lang="scss" scoped>
.shop-admin-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.shop-admin-page__hero {
    margin-bottom: var(--nf-space-sm);
}

.shop-admin-page__filter {
    margin-bottom: var(--nf-space-sm);
}

.shop-admin-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.shop-admin-page__filter-item {
    display: flex;
    align-items: center;
    gap: 8px;
}

.shop-admin-page__filter-label {
    flex-shrink: 0;
    font-size: 14px;
    color: var(--nf-text-primary);
    white-space: nowrap;
}

.shop-admin-page__filter-input {
    width: 150px;
}

.shop-admin-page__filter-actions {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.shop-admin-page__tabs {
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

.shop-admin-page__table {
    margin-top: 0;
    margin-bottom: 0;
}

.shop-admin-page__action-btn {
    margin: 4px 0;
}

.shop-admin-page__pagination {
    display: flex;
    justify-content: center;
    margin-top: var(--nf-space-sm);
    padding-top: var(--nf-space-sm);
    border-top: 1px solid var(--nf-border);
}

.shop-admin-page__thumb {
    width: 50px;
    height: 50px;
    margin: 3px;
    object-fit: cover;
    border-radius: var(--nf-radius-sm);
}

.shop-admin-page__preview {
    width: 400px;
    height: auto;
    display: block;
}

.shop-admin-page__form-row {
    margin-bottom: var(--nf-space-sm);
}

.shop-admin-page__form-label {
    width: 200px;
    display: inline-block;
    text-align: right;
    color: var(--nf-text-primary);
    font-size: 14px;
}

.shop-admin-page__form-input {
    width: 50%;
}

.shop-admin-page__upload-wrap {
    display: inline-block;
    vertical-align: top;
}

.shop-admin-page__uploader {
    width: 148px;
    height: 148px;
    border: 1px dashed var(--nf-border);
    border-radius: var(--nf-radius-sm);
    text-align: center;
    line-height: 148px;
    color: var(--nf-text-placeholder);
}

.shop-admin-page__avatar {
    width: 148px;
    height: 148px;
    object-fit: cover;
    display: block;
}

.shop-admin-page__upload-icon {
    width: 23px;
    height: 23px;
    color: var(--nf-text-placeholder);
}
</style>
