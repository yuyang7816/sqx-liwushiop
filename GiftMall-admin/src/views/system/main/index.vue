<template>
    <DefinScrollbar height="100%" :showUpBt="true">
        <div class="index-page">
            <!-- hero + 筛选 -->
            <div class="index-page__top">
                <div class="nf-page-hero index-page__hero">
                    <span class="nf-page-hero__badge">数据中心</span>
                    <h2 class="nf-page-hero__title">用户与支付概览</h2>
                    <p class="nf-page-hero__desc">查看用户增长趋势与支付统计数据</p>
                </div>
                <div class="nf-filter-card index-page__filter">
                    <div class="nf-filter-card__head">
                        <el-icon><Filter /></el-icon>
                        <span>筛选条件</span>
                    </div>
                    <div class="nf-filter-card__body index-page__filter-body">
                        <el-select
                            v-model="dataContainer.flag"
                            class="index-page__select"
                            @change="orderfenxi()"
                        >
                            <el-option
                                v-for="(item, index) in dataContainer.flags"
                                :key="index"
                                :label="item.label"
                                :value="item.value"
                            />
                        </el-select>
                        <el-date-picker
                            v-model="dataContainer.info"
                            type="date"
                            placeholder="选择时间"
                            format="YYYY-MM-DD"
                            value-format="YYYY-MM-DD"
                            @change="animeOrder()"
                        />
                    </div>
                </div>
            </div>

            <!-- KPI 四列 · 用户指标 -->
            <div class="nf-kpi-grid">
                <div class="nf-kpi-card index-kpi">
                    <div class="index-kpi__icon index-kpi__icon--brand">
                        <el-icon><User /></el-icon>
                    </div>
                    <div class="index-kpi__body">
                        <div class="index-kpi__label">总用户数</div>
                        <div class="index-kpi__value">
                            <span>{{ dataContainer.tableData.userCount ? dataContainer.tableData.userCount : 0 }}</span>人
                        </div>
                    </div>
                </div>
                <div class="nf-kpi-card index-kpi">
                    <div class="index-kpi__icon index-kpi__icon--green">
                        <el-icon><Plus /></el-icon>
                    </div>
                    <div class="index-kpi__body">
                        <div class="index-kpi__label">今日新增</div>
                        <div class="index-kpi__value">
                            <span>{{ dataContainer.tableData.dayUserCount ? dataContainer.tableData.dayUserCount : 0 }}</span>人
                        </div>
                    </div>
                </div>
                <div class="nf-kpi-card index-kpi">
                    <div class="index-kpi__icon index-kpi__icon--blue">
                        <el-icon><Calendar /></el-icon>
                    </div>
                    <div class="index-kpi__body">
                        <div class="index-kpi__label">本月新增</div>
                        <div class="index-kpi__value">
                            <span>{{ dataContainer.tableData.monthUserCount ? dataContainer.tableData.monthUserCount : 0 }}</span>人
                        </div>
                    </div>
                </div>
                <div class="nf-kpi-card index-kpi">
                    <div class="index-kpi__icon index-kpi__icon--purple">
                        <el-icon><TrendCharts /></el-icon>
                    </div>
                    <div class="index-kpi__body">
                        <div class="index-kpi__label">本年新增</div>
                        <div class="index-kpi__value">
                            <span>{{ dataContainer.tableData.yearUserCount ? dataContainer.tableData.yearUserCount : 0 }}</span>人
                        </div>
                    </div>
                </div>
            </div>

            <!-- tabs + 明细白卡 -->
            <el-tabs
                v-model="dataContainer.activeName"
                class="index-page__tabs"
                @tab-click="handleClick"
            >
                <el-tab-pane label="用户分析" name="first">
                    <div class="nf-table-card index-detail">
                        <div class="index-detail__head">
                            <el-icon><UserFilled /></el-icon>
                            <span>用户分析</span>
                        </div>
                        <div class="nf-stat-tile-grid">
                            <div class="nf-stat-tile index-stat-tile">
                                <div class="index-stat-tile__label">总用户统计</div>
                                <div class="index-stat-tile__value">
                                    <span>{{ dataContainer.userStat.totalNumber ? dataContainer.userStat.totalNumber : 0 }}</span>人
                                </div>
                            </div>
                            <div class="nf-stat-tile index-stat-tile">
                                <div class="index-stat-tile__label">普通用户</div>
                                <div class="index-stat-tile__value">
                                    <span>{{ dataContainer.userStat.userNumber ? dataContainer.userStat.userNumber : 0 }}</span>人
                                </div>
                            </div>
                            <div class="nf-stat-tile index-stat-tile">
                                <div class="index-stat-tile__label">vip用户</div>
                                <div class="index-stat-tile__value">
                                    <span>{{ dataContainer.userStat.vipUserNumber ? dataContainer.userStat.vipUserNumber : 0 }}</span>人
                                </div>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="支付统计" name="sixth">
                    <div class="nf-table-card index-detail">
                        <div class="index-detail__head">
                            <el-icon><Money /></el-icon>
                            <span>支付统计</span>
                        </div>
                        <div class="nf-stat-tile-grid">
                            <div class="nf-stat-tile index-stat-tile">
                                <div class="index-stat-tile__label">支付总金额</div>
                                <div class="index-stat-tile__value">
                                    <span>{{ dataContainer.zongMoney ? dataContainer.zongMoney : 0 }}</span>元
                                </div>
                            </div>
                            <div
                                class="nf-stat-tile index-stat-tile"
                                v-for="(item, index) in dataContainer.withdrawDataZf"
                                :key="index"
                            >
                                <div class="index-stat-tile__label">{{ item.name }}</div>
                                <div class="index-stat-tile__value">
                                    <span>{{ item.amount ? item.amount : '0' }}</span>元
                                </div>
                            </div>
                        </div>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </div>
    </DefinScrollbar>
</template>
<script>
/**
 * 首页
 */
import { defineComponent, reactive, ref } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import SvgIcon from '@/components/svgIcon/index.vue';
import DefinScrollbar from '@/components/definScrollbar.vue';
import http from '@/http/request.js';
import userApi from '@/http/user.js';
import {
    User,
    UserFilled,
    Plus,
    Calendar,
    TrendCharts,
    Money,
    Filter,
} from '@element-plus/icons-vue';

export default defineComponent({
    components: {
        SvgIcon,
        DefinScrollbar,
        User,
        UserFilled,
        Plus,
        Calendar,
        TrendCharts,
        Money,
        Filter,
    },
    setup() {
        const dataContainer = reactive({
            name: import.meta.env.VITE_APP_name,
            loading: false,
            activeName:'first',
            totalUsers:'',
            tableData:{},
            userStat:{},
            chongzhiData:{},
            flag:'day',
            flags:[
            {
                value: 'day',
                label: '按天查询'
            }, {
                value: 'month',
                label: '按月查询'
            }, {
                value: 'year',
                label: '按年查询'
            }
        ],
        info:getNowTime(),
        withdrawDataZf:[],
        zongMoney:0,


        });
        
        // 获取时间
        function getNowTime() {
            const now = new Date();
            const year = now.getFullYear(); //得到年份
            const month = now.getMonth(); //得到月份
            const date = now.getDate(); //得到日期
            const hh = now.getHours() < 10 ? "0" + now.getHours() : now.getHours();
            const mm = now.getMinutes() < 10 ? "0" + now.getMinutes() : now.getMinutes();
            const ss = now.getSeconds() < 10 ? "0" + now.getSeconds() : now.getSeconds();
            console.log(month)
            const month1 = month + 1;
            const month2 = month1.toString().padStart(2, "0");
            const date1 = date.toString().padStart(2, "0");
            var defaultDate = `${year}-${month2}-${date1}`;
            // var defaultDate = `${year}-${month}-${date} ${hh}:${mm}:${ss}`;

            return defaultDate;
        }
        getNowTime();
        // 选择日期类型
        function orderfenxi() {
            userData()
            rechSelect()
        }
        // 选择日期
        function animeOrder() {
            userData()
            rechSelect()
        }
        // 用户统计
        function _getData() {
            http.get('admin/index/homeStatistics', {}).then((res) => {
                console.log(res.data)
                if (res.code == 0) {
                    dataContainer.tableData = res.data;
                    
                }
            })
        };
        // 用户分析
        function userData() {
            const datas = {
                findDate:dataContainer.info,
                findType:dataContainer.flag
            }
            http.get('admin/index/userAnalysis', {
                params: datas
            },{
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then((res) => {
                console.log(res.data)
                if (res.code == 0) {
                    dataContainer.userStat = res.data;
                    console.log(userStat)
                }
            })
        };
        //支付统计
        function rechSelect() {
             const datas = {
                data:dataContainer.info,
                type:dataContainer.flag
            }
               http.post('admin/payDetails/payStatistics', {

                findDate: dataContainer.info,
                findType: dataContainer.flag,
            },{
                headers: {
                    'Content-Type': 'application/json'
                }
            }).then((res) => {
                console.log(res.data)
                dataContainer.withdrawDataZf = []
                dataContainer.zongMoney = 0
                for(const i in dataContainer.withdrawDataZf){
                    if(Number(dataContainer.withdrawDataZf[i].amount)>0){
                        dataContainer.zongMoney = dataContainer.zongMoney+Number(dataContainer.withdrawDataZf[i].amount)
                    }
                }
                let returnData = res.data
                dataContainer.withdrawDataZf = returnData
            })
			};

        function handleClick(tab, event){
            console.log(event.srcElement.innerText)
				if (event.srcElement.innerText == '用户分析') {
					dataContainer.info = getNowTime()
					userData()
				}
				if (event.srcElement.innerText == '支付统计') {
					dataContainer.info = getNowTime()
					rechSelect()
				}
        };
        console.log('测试权限',userApi.isAuth('userList:delete'))
        _getData();
        userData();
        return {
            dataContainer,
            _getData,
            userData,
            handleClick,
            getNowTime,
            orderfenxi,
            animeOrder,

        };
    },
});


</script>



<style lang="scss" scoped>
.index-page {
    padding: var(--nf-space-md);
    box-sizing: border-box;
    min-height: 100%;
}

.index-page__top {
    display: grid;
    grid-template-columns: 1fr 320px;
    gap: var(--nf-space-sm);
    margin-bottom: var(--nf-space-md);
    align-items: stretch;
}

.index-page__hero {
    margin-bottom: 0;
}

.index-page__filter {
    margin-bottom: 0;
    display: flex;
    flex-direction: column;
}

.index-page__filter-body {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    gap: var(--nf-space-sm);
}

.index-page__select {
    width: 140px;
}

.index-page__tabs {
    margin-bottom: 0;

    :deep(.el-tabs__header) {
        margin-bottom: 0;
    }
}

/* KPI card internals */
.index-kpi {
    display: flex;
    align-items: center;
    gap: var(--nf-space-md);
}

.index-kpi__icon {
    flex-shrink: 0;
    width: 44px;
    height: 44px;
    border-radius: var(--nf-radius-sm);
    display: flex;
    align-items: center;
    justify-content: center;
    font-size: 20px;

    &--brand {
        background: var(--nf-brand-lite);
        color: var(--nf-brand);
    }

    &--green {
        background: #e8f8ef;
        color: #27ae60;
    }

    &--blue {
        background: #e8f0fe;
        color: #3b82f6;
    }

    &--purple {
        background: #f0e8fe;
        color: #8b5cf6;
    }
}

.index-kpi__body {
    flex: 1;
    min-width: 0;
}

.index-kpi__label {
    font-size: 13px;
    color: var(--nf-text-secondary);
    margin-bottom: 4px;
}

.index-kpi__value {
    font-size: 13px;
    color: var(--nf-text-regular);

    span {
        font-size: 22px;
        font-weight: 600;
        color: var(--nf-brand);
        margin-right: 2px;
    }
}

/* Detail card */
.index-detail {
    margin-top: 0;
}

.index-detail__head {
    display: flex;
    align-items: center;
    gap: var(--nf-space-xs);
    font-size: 14px;
    font-weight: 600;
    color: var(--nf-text-primary);
    margin-bottom: var(--nf-space-md);
    padding-bottom: var(--nf-space-sm);
    border-bottom: 1px solid var(--nf-border);

    .el-icon {
        color: var(--nf-brand);
        font-size: 16px;
    }
}

.index-stat-tile__label {
    font-size: 12px;
    color: var(--nf-text-secondary);
    margin-bottom: 6px;
}

.index-stat-tile__value {
    font-size: 12px;
    color: var(--nf-text-regular);

    span {
        font-size: 18px;
        font-weight: 600;
        color: var(--nf-text-primary);
        margin-right: 2px;
    }
}

@media (max-width: 1200px) {
    .index-page__top {
        grid-template-columns: 1fr;
    }
}

@media (max-width: 768px) {
    .nf-kpi-grid {
        grid-template-columns: repeat(2, 1fr);
    }
}
</style>
