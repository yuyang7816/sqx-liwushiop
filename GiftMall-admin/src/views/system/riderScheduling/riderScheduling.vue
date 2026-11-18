<template>
    <div class="_div">
        <div>
            <div style="margin-bottom: 10px;display: inline-block;width: 100%;">
                <div style="position: relative;display: inline-block;">
                    <span>师傅昵称：</span>
                    <el-input style="width: 200px;" @keydown.enter.native="phoneSelect" placeholder="请输入师傅昵称"
                        v-model="dataContainer.nickNameT">
                    </el-input>&nbsp;&nbsp;
                </div>
                <div style="position: relative;display: inline-block;">
                    <span style="display: inline-block;text-align: right;">城市：</span>
                    <el-cascader style="width:200px" :options="dataContainer.options" ref="cascaderAddr"
                        v-model="dataContainer.storeAddress" :placeholder="dataContainer.storeAddre" @change="handleChange5" :value="dataContainer.storeAddre">
                        <template #default="{ data }">
                            <span>{{ data.label }}</span>
                        </template>
                    </el-cascader>
                </div>
                <el-button size="small" plain type="primary"
                    @click="phoneSelect">刷新</el-button>
                <el-button size="small" type="primary" @click="cleans">重置
                </el-button>
            </div>
		</div>
       <div>
			<div id="container" style="width:95%;height:80vh;margin-left: 2.5%;position: relative;"
				v-loading="dataContainer.tableDataLoadingDt"></div>
		</div>
        
        <!-- 师傅列表 弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisible2" title="师傅列表" width="80%" center>
            <div style="display: inline-block;margin: 5px;">
                <span>昵称：</span>
                <el-input v-model="dataContainer.campus" placeholder="请输入昵称"
                    @keydown.enter.native="dataContainer.selectU" style="width: 150px;" />
            </div>
            <div style="display: inline-block;margin: 5px;">
                <span>电话:</span>
                <el-input v-model="dataContainer.phoneU" placeholder="请输入电话"
                    @keydown.enter.native="selectU" style="width: 150px;" />
            </div>
            <el-button size="small" type="primary" plain @click="selectU()">刷新</el-button>
            <el-button size="small" type="primary" @click="cleansU()">重置</el-button>
            <el-table v-loading="dataContainer.tableDataLoadingU" :data="dataContainer.userData.list">
					<el-table-column prop="userId" label="编号" width="80">
					</el-table-column>
					<el-table-column prop="nickName" label="用户昵称" width="120">
						<template #default="scope">
							　<span style="color: var(--nf-brand);cursor: pointer;" @click="updates(scope.row.userId)"
								v-text="scope.row.nickName"></span>
						</template>
					</el-table-column>
					<el-table-column prop="avatar" label="用户头像" width="100">
						<template #default="scope">
                            <img style="width: 50px; height: 50px;"  v-if="scope.row.avatar && scope.row.avatar!=''" :src="scope.row.avatar" alt="">
							<span v-else>暂无图片</span>
						</template>
					</el-table-column>
					<el-table-column prop="phone" label="手机号">
					</el-table-column>
					<el-table-column prop="createTime" label="创建时间" width="180">
					</el-table-column>
					<el-table-column label="操作" width="100" fixed='right' align="center">
						<template #default="scope">
							<el-button size="mini" type="primary" @click="userBtns(scope.row)">确认
							</el-button>
						</template>
					</el-table-column>
				</el-table>
                <div style="text-align: center;margin-top: 10px;">
                    <el-pagination @size-change="handleSizeChangeU" @current-change="handleCurrentChangeU"
                        :page-sizes="[10, 20, 30, 50, 100]" :page-size="dataContainer.limit1"
                        :current-page="dataContainer.page1" layout="total, sizes, prev, pager, next, jumper"
                        :total="dataContainer.userData.totalCount">
                    </el-pagination>
                </div>
        </el-dialog>
        <!-- 订单列表 弹框 -->
        <el-dialog v-model="dataContainer.dialogFormVisible" title="订单列表" width="80%" center>
            <el-table v-loading="dataContainer.tableDataLoadingU" :data="dataContainer.userData.records">
					<el-table-column fixed prop="indentId" label="编号" width="80">
					</el-table-column>
					<el-table-column prop="indentId" label="下单人昵称" width="120">
						<template slot-scope="scope">
							<div style="color: var(--nf-brand);cursor: pointer;" @click="updates(scope.row.userId)">
								{{scope.row.shipUserName}}
							</div>
						</template>
					</el-table-column>
					<el-table-column prop="shipUserPhone" label="下单手机号"></el-table-column>
					<el-table-column prop="indentType" label="订单类型">
						<template slot-scope="scope">
							<div v-if="scope.row.indentType==1">即时代驾</div>
							<div v-if="scope.row.indentType==2">朋友代叫</div>
							<div v-if="scope.row.indentType==3">预约代驾</div>
						</template>
					</el-table-column>
					<el-table-column prop="indentNumber" label="订单号"></el-table-column>
					<el-table-column prop="shipAddressDetail" label="乘客信息" width="200">
						<template slot-scope="scope">
							<div>
								<div>乘客姓名:{{scope.row.customName}} </div>
								<div>乘客电话: {{scope.row.customPhone}}</div>
							</div>
						</template>
					</el-table-column>
					<el-table-column prop="shipAddressDetail" label="下单人信息" width="200">
						<template slot-scope="scope">
							<div>
								<div>下单人姓名:{{scope.row.shipUserName}} </div>
								<div>下单人电话: {{scope.row.shipUserPhone}}</div>
							</div>
						</template>
					</el-table-column>
					<el-table-column prop="deilveryAddressDetail" label="下单地址" width="200">
						<template slot-scope="scope">
							<div>{{scope.row.shipAddress}}</div>
							<div>目的经纬度：{{scope.row.shipAddressLatitude}} {{scope.row.shipAddressLongitude}}</div>
						</template>
					</el-table-column>
					<el-table-column prop="deilveryAddressDetail" label="目的地址" width="200">
						<template slot-scope="scope">
							<div>{{scope.row.deliveryAddress}}</div>
							<div>目的经纬度：{{scope.row.deliveryAddressLatitude}} {{scope.row.deliveryAddressLongitude}}
							</div>
						</template>
					</el-table-column>
					<el-table-column prop="shipAddressDetail" label="师傅信息" width="200">
						<template slot-scope="scope">
							<div>
								<div>师傅姓名:{{scope.row.riderNickName}} </div>
								<div>师傅电话: {{scope.row.riderPhone}}</div>
							</div>
						</template>
					</el-table-column>
					<el-table-column prop="riderDistance" label="师傅距离起始/终点位置距离(米)" width="140">
					</el-table-column>

					<el-table-column prop="distance" label="预估总路程(公里)"></el-table-column>
					<el-table-column prop="indentBasicsMoney" label="基础价格">
					</el-table-column>
					<el-table-column prop="userScaleMoney" label="上级抽佣金额">
						<template slot-scope="scope">
							<div style="color: #f56c6c;cursor: pointer;" @click="updates(scope.row.userScaleUserId)">
								{{scope.row.userScaleMoney?scope.row.userScaleMoney:'0'}}
							</div>
						</template>
					</el-table-column>
					<el-table-column prop="userTwoMoney" label="上上级抽佣金额">
						<template slot-scope="scope">
							<div style="color: #f56c6c;cursor: pointer;" @click="updates(scope.row.userTwoUserId)">
								{{scope.row.userTwoMoney?scope.row.userTwoMoney:'0'}}
							</div>
						</template>
					</el-table-column>
					<el-table-column prop="platformMoney" label="平台收入">
					</el-table-column>
					<el-table-column prop="riderRewardMoney" label="师傅奖励金额">
					</el-table-column>
					<el-table-column prop="riderMoney" label="师傅收入">
					</el-table-column>
					<el-table-column prop="couponMoney" label="优惠券抵扣金额">
					</el-table-column>
					<el-table-column prop="indentMoney" label="支付金额">
						<template slot-scope="scope">
							<div style="color: #f56c6c;">
								{{scope.row.indentMoney?scope.row.indentMoney:'0'}}
							</div>
						</template>
					</el-table-column>
					<el-table-column prop="userFine" label="用户取消订单罚金">
					</el-table-column>
					<el-table-column prop="riderFine" label="师傅取消订单罚金">
					</el-table-column>
					<!-- <el-table-column prop="itemCodeFlag" label="是否有确认码">
						<template slot-scope="scope">
							<div v-if="scope.row.itemCodeFlag==1">没有收货码</div>
							<div v-if="scope.row.itemCodeFlag==0">有收货码</div>
						</template>
					</el-table-column> -->
					<el-table-column prop="remarks" label="备注">
					</el-table-column>
					<!-- <el-table-column prop="itemCode" label="确认码">
						<template slot-scope="scope">
							<div v-if="scope.row.itemCodeFlag==1">-</div>
							<div v-else>{{scope.row.itemCode}}</div>
						</template>
					</el-table-column> -->
					<el-table-column prop="modeOfPayment" label="支付方式">
						<template slot-scope="scope">
							<div v-if="scope.row.modeOfPayment==1">微信</div>
							<div v-if="scope.row.modeOfPayment==2">支付宝</div>
							<div v-if="scope.row.modeOfPayment==3">余额</div>
						</template>
					</el-table-column>
					<el-table-column prop="receivingTime" label="接单时间" width="160">
					</el-table-column>
					<el-table-column prop="finishTime" label="完成时间" width="160">
					</el-table-column>
					<el-table-column prop="createTime" label="创建时间" width="160">
					</el-table-column>

					<el-table-column prop="indentState" label="状态" fixed="right">
						<template slot-scope="scope">
							<div v-if="scope.row.indentState==0">待接单</div>
							<div v-if="scope.row.indentState==1">已接单</div>
							<div v-if="scope.row.indentState==2">进行中</div>
							<div v-if="scope.row.indentState==3">待支付</div>
							<div v-if="scope.row.indentState==4">已完成</div>
							<div v-if="scope.row.indentState==5">已取消</div>
						</template>
					</el-table-column>
				</el-table>
        </el-dialog>
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
    onMounted,
} from 'vue';
import { useRouter, useRoute } from 'vue-router';
import SvgIcon from '@/components/svgIcon/index.vue';
import { Delete } from '@element-plus/icons-vue';
import http from '@/http/request.js';
import userApi from '@/http/user.js';
import { jsonp } from 'vue-jsonp';
import {
		// provinceAndCityData,
		regionData,
        codeToText,
		// provinceAndCityDataPlus,
		// regionDataPlus
		
	} from 'element-china-area-data'
let map = null;
export default defineComponent({
    components: {
        SvgIcon,
        Delete,
    },
    setup() {
        
        const router = useRouter();
        const route = useRoute();
        const Jsonp = jsonp;
        const geocoder= [];
        const mapr= [];
        const markersArrayr= [];
        const infoWindow = [];
        
        const dataContainer = reactive({
           tableDataLoadingDt:false,
           // 地图相关
				longitude: '', //经度
				latitude: '', //维度
				loadingMap: true,
				BMap: '',
				map: [],
				geoc: '',
				keyword: '',
				showMap: false,
				pointLngLat: '',
				center: {
					lng: 108.948024,
					lat: 34.263161
				},
				lng: 108.948024,
				lat: 34.263161,
				storeAddress: [],
				storeAddre: '请选择城市',
                province: '',
                city: '', //市
                district: '', //区
                nickNameT:'',
                geometries: [],
                lists: [],
                marker: '',
                options: regionData,
                tableDataLoadingU:false,
                dialogFormVisible:false,
                dialogFormVisible2:false,

                userData:{},
                page1:1,
                limit1:10,
                phoneU:'',
                campus:'',
                ordersId:'',





        

        });
        /**
         * 数据初始化
         */

        function initData() {
            let params = route.params;
            if (!params.sign) return;
            // dataContainer.form = {
            //     id: params.sign,
            // };
        }
        initData();

        function prev() {
            router.back()
        }
        
        
        /** 获取用户详情数据 */
        function dataSelect() {
            dataContainer.tableDataLoadingDt = true
            http.get('timedtask/selectAllRiderLocation', { 
                params: {
                    province: dataContainer.province,
                    city: dataContainer.city,
                    district: dataContainer.district,
                    nickName: dataContainer.nickNameT
                }
            }, {
            }).then((data) => {
                console.log('============',data)
                console.log(data)
					var arr = []
					var arr1 = []
					if (data.data.riderList.length > 0) {

						for (var i in data.data.riderList) {
							if (data.data.riderList[i].userEntity) {
								if (data.data.riderList[i].lat && data.data.riderList[i].lng) {
									var obj = {}
									obj.position = new TMap.LatLng(data.data.riderList[i].lat, data.data.riderList[
										i].lng)
									obj.datas = data.data.riderList[i]
									obj.img = data.data.riderList[i].artificerImg
									obj.content = data.data.riderList[i].userEntity.nickName
									arr.push(obj)
									dataContainer.longitude = data.data.riderList[0].lng
									dataContainer.latitude = data.data.riderList[0].lat
								}
							}


						}

						dataContainer.geometries = arr
						dataContainer.tableDataLoadingDt = false
						setMap();

					}
					if (data.data.indentList.length > 0) {

						for (var i in data.data.indentList) {
							if (data.data.indentList[i].shipAddressLatitude && data.data.indentList[i]
								.shipAddressLongitude) {
								var obj = {}
								obj.position = new TMap.LatLng(data.data.indentList[i].shipAddressLatitude, data
									.data.indentList[
										i].shipAddressLongitude)
								obj.datas = data.data.indentList[i]
								obj.img = data.data.indentList[i].artificerImg
								obj.content = data.data.indentList[i].shipUserName
								arr1.push(obj)
								dataContainer.longitude = data.data.indentList[0].shipAddressLongitude
								dataContainer.latitude = data.data.indentList[0].shipAddressLatitude
							}
						}
						dataContainer.lists = arr1
						dataContainer.tableDataLoadingDt = false
						setMap();
					} else {
						dataContainer.lists = []
					}
					if (data.data.indentList.length == 0 && data.data.riderList.length == 0) {
                         messageError('暂无数据');
						// this.$message({
						// 	message: '暂无数据',
						// 	type: 'warning',
						// 	duration: 1500,
						// 	onClose: () => {}
						// })
						dataContainer.tableDataLoadingDt = false
						setMap();
					}
            })
        }
        function phoneSelect() {
            dataSelect()
        }
        function cleans(){
            dataContainer.province = ''
            dataContainer.city = ''
            dataContainer.district = ''
            dataContainer.nickNameT = ''
            dataSelect()
        }
        // 获取省市区
		function handleChange5(value) {
            const value0 = value[0]
            const value1 = value[1]
            const value2 = value[2]
            console.log(codeToText[value0],'====',value)
            dataContainer.province = codeToText[value0]
            dataContainer.city = codeToText[value1]
            dataContainer.district = codeToText[value2]
            if (dataContainer.city == '市辖区') {
                dataContainer.city = dataContainer.province
            }
            dataSelect()
        }
        if(dataContainer.longitude==''||dataContainer.latitude==''){
                 let data = {
                        key: "ZBABZ-ZWECU-UQTVV-4LYDR-COK3F-5SF75", //申请的密钥
                        output: "jsonp",
                    };
                    let url = "https://apis.map.qq.com/ws/location/v1/ip"
                    Jsonp(url, data).then(res => {
                        dataContainer.longitude = res.result.location.lng
                        dataContainer.latitude = res.result.location.lat
                        console.log('获取Ip定位成功------',dataContainer.latitude,dataContainer.latitude)
                        // setMap()
                        // var center = new TMap.LatLng(dataContainer.latitude, dataContainer.longitude); 
                    }).catch(err => {
                        console.log('------定位失败',err)
                       
                    })
            }else{
               console.log('地图定位：', dataContainer.longitude, dataContainer.latitude, )
				var center = new TMap.LatLng(dataContainer.latitude, dataContainer.longitude); 
            }
            dataSelect();
        
       //定位获得当前位置信息
		function getMyLocation() {
				var geolocation = new qq.maps.Geolocation("ZBABZ-ZWECU-UQTVV-4LYDR-COK3F-5SF75", "来点小收入");
				geolocation.getIpLocation(showPosition(), showErr());
			}
		function showPosition(position) {
				setMap();
			}
		function showErr(e) {
				getMyLocation(); //定位失败再请求定位，测试使用
			}
			//位置信息在地图上展示
		function setMap() {
            
            var center = new TMap.LatLng(dataContainer.latitude, dataContainer.longitude); 
            
				if (map) {
					map.destroy();
				}
                
				 map = []
				var infoWindow = []
				// 初始化地图
				map = new TMap.Map('container', {
					zoom: 13, // 设置地图缩放级别
					pitch: 30,
					center: center, // 设置地图中心点坐标
					// mapStyleId: "style1", //个性化样式
					baseMap: {
						type: 'vector',
						// features: ['base', 'building3d'], // 隐藏矢量文字
					},
				});
                
				// 师傅标记
				dataContainer.marker = new TMap.MultiMarker({
					map: map,
					styles: {
						default: new TMap.MarkerStyle({
							width: 66, // 宽度
							height: 66, // 高度
							anchor: {
								x: 46,
								y: 46
							}, // 锚点位置
							src: 'https://mapapi.qq.com/web/lbs/visualizationApi/demo/img/circle.png', // 标注点图片url或base64地址
							color: '#000', // 标注点文本颜色
							size: 14, // 标注点文本文字大小
							offset: {
								x: 0,
								y: 16
							}, // 标注点文本文字基于direction方位的偏移属性
						}),
					},
					geometries: dataContainer.geometries
				});
				/* if(this.infoWindowList && this.infoWindowList.length>0){
					for(var i in this.infoWindowList){
						var infoWindow=this.infoWindowList[i];
						infoWindow.close()
					}
				} */


				//初始化infoWindow
				console.log('this.geometries', dataContainer.geometries)
				// infoWindow.clear();
				for (var i in dataContainer.geometries) {
					infoWindow = new TMap.InfoWindow({
						map: map,
						position: new TMap.LatLng(dataContainer.geometries[i].datas.lat, dataContainer.geometries[i].datas.lng),
						offset: {
							x: 0,
							y: -32
						} //设置信息窗相对position偏移像素
					});
					// infoWindow.clears();//关闭信息窗
					// infoWindow.setContent('待服务数量：1'); //设置信息窗内容
					infoWindow.setContent('未完成订单：' + dataContainer.geometries[i].datas.indentCount); //设置信息窗内容
					infoWindow.open(); //初始信息窗开启
					//this.infoWindowList.push(infoWindow);
					// infoWindow.close(); //关闭信息窗
				}
				let that = this
				dataContainer.marker.on("click", function(evt) {
					console.log(evt.geometry)
					zhuandan(evt.geometry.datas)
					dataContainer.dialogFormVisible = true
				})
				// 订单标记
				dataContainer.marker = new TMap.MultiMarker({
					map: map,
					styles: {
						default: new TMap.MarkerStyle({
							width: 25, // 宽度
							height: 35, // 高度
							anchor: {
								x: 16,
								y: 32
							}, // 锚点位置
							src: 'https://mapapi.qq.com/web/lbs/javascriptGL/demo/img/markerDefault.png', // 标注点图片url或base64地址
							color: '#ff4949', // 标注点文本颜色
							size: 14, // 标注点文本文字大小
							offset: {
								x: 0,
								y: 16
							}, // 标注点文本文字基于direction方位的偏移属性
						}),
					},
					geometries: dataContainer.lists
				});
				for (var i in dataContainer.lists) {
					infoWindow = new TMap.InfoWindow({
						map: map,
						position: new TMap.LatLng(dataContainer.lists[i].datas.shipAddressLatitude, dataContainer.lists[i].datas
							.shipAddressLongitude),
						offset: {
							x: 0,
							y: -32
						} //设置信息窗相对position偏移像素
					});
					// infoWindow.clears();//关闭信息窗
					// infoWindow.setContent('待服务数量：1'); //设置信息窗内容
					infoWindow.setContent('下单用户：' + dataContainer.lists[i].datas.shipUserName); //设置信息窗内容
					infoWindow.open(); //初始信息窗开启
					//this.infoWindowList.push(infoWindow);
					// infoWindow.close(); //关闭信息窗
				}

				// let that = this
				dataContainer.marker.on("click", function(evt) {
					console.log(evt.geometry)
					zhuandan(evt.geometry.datas)
					dataContainer.dialogFormVisible2 = true
				})

			}
        // 地图定位
		function select() {
				
				// this.address = dataContainer.province+ dataContainer.city+this.district+dataContainer.address
				var addr = dataContainer.province + dataContainer.city + dataContainer.district + dataContainer.address
				
				jsonp('https://apis.map.qq.com/ws/geocoder/v1/?address==' + addr +
					'&key=ZBABZ-ZWECU-UQTVV-4LYDR-COK3F-5SF75&get_poi=1&output=jsonp', {
						myCustomUrlParam: 'veryNice'
					}).then(response => {
					// handle success
					console.log('response', response)
					dataContainer.longitude = response.result.location.lng; // 经度
					dataContainer.latitude = response.result.location.lat; // 纬度
					setMap()
				}).catch(error => {
					// handle error
				}).then(() => {
					// always executed
				});
			}
        /** 跳转用户详情 */
        function updates(userId, querys) {
            router.push({
                name: 'userDetail',
                query:{
                    userId:userId,
                },
                params: {
                    sign: userId || new Date().getTime(),
                    querys,
                },
            });
        }
        function handleSizeChangeU(val) {
				dataContainer.limit1 = val;
				dataSelectUser()
			}
		function handleCurrentChangeU(val) {
				dataContainer.page1 = val;
				dataSelectUser()
			}
        // 转单弹框
		function zhuandan(row) {
				if (row.indentId) {
					dataContainer.page1 = 1
					dataContainer.ordersId = row.indentId
					dataSelectUser()
					dataContainer.dialogFormVisible2 = true
				} else {
					dataContainer.page1 = 1
					dataSelectOrder(row.userId)
					dataContainer.dialogFormVisible = true
				}

			}
			// 确认选择
		function userBtns(row) {
            http.post('adminerrand/distributionOrder', { 
                
                userId: row.userId,
                indentId: dataContainer.ordersId,
            }, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            }).then((res) => {
                console.log(res)
                if(res.code==0){
                     ElMessage({
                            message: '修改成功',
                            type: 'success',
                            onClose: () => {
                                dataSelect()
								dataContainer.dialogFormVisible2 = false
                            }
                        })
                }else{
                    ElMessage({
                            message: res.msg,
                            type: 'warning',
                            onClose: () => {
                            }
                        })
                }
                
            })
		}
			// 获取数据列表
		function dataSelectUser() {
			dataContainer.tableDataLoadingU = true
            http.get('adminerrand/findAllUser', { 
                params: {
                    page: dataContainer.page1,
                    limit: dataContainer.limit1,
                    phone: dataContainer.phoneU,
                    nickName:dataContainer.campus,
                    userType: 2,
                    invitationCode: '',
                    platform: ''
                }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.tableDataLoadingU = false;
                dataContainer.tableData = {}
                let returnData = res.data
                dataContainer.userData = returnData
            })
		}
			// 查询
		function selectU() {
				dataContainer.page = 1
				dataSelectUser();
		}
			// 重置
		function cleansU() {
				dataContainer.page1 = 1
				dataContainer.campus = ''
				dataContainer.phoneU = ''
				dataSelectUser();
		}
        // 获取师傅订单数据
		function dataSelectOrder(userId) {
            dataContainer.tableDataLoadingU = true
            http.get('adminerrand/findWaitIndent', { 
                params: {
                    page: dataContainer.page1,
                    limit: dataContainer.limit1,
                    userId: userId,
                }
            }, {
            }).then((res) => {
                console.log(res)
                dataContainer.tableDataLoadingU = false;
                dataContainer.tableData = {}
                let returnData = res.data
                dataContainer.userData = returnData
            })
		}
       
           
        return {
            dataContainer,
            initData,
            prev,
            dataSelect,
            getMyLocation,
            setMap,
            select,
            showPosition,
            showErr,
            phoneSelect,
            cleans,
            handleChange5,
            updates,
            dataSelectUser,
            selectU,
            cleansU,
            handleSizeChangeU,
            handleCurrentChangeU,
            zhuandan,
            userBtns,
            dataSelectOrder,


            
        };
    },
});
</script>

<style lang="scss" scoped>
._div{
    padding: 20px;
}
.main-view {
    display: flex;
    flex-direction: column;
    width: 100%;

    .container {
        // background-color: white;
        width: 100%;
        // min-height: 800px;
        height: fit-content;
        border-radius: 5px;
        padding: 30px;
        box-sizing: border-box;

        >* {
            margin: 0 0 30px 0;

            &:last-child {
                margin: 0;
            }
        }
    }
}
.detailtabel h2 {
		margin-top: 0;
	}

	.detailtabel .table_main table {
		width: 100%;
	}

	.detailtabel .table_main {
		border: 1px solid #e8e8e8;
	}

	.detailtabel table tr {
		border-bottom: 1px solid #e8e8e8;
	}

	.detailtabel table tr th {
		background-color: #fafafa;
		padding: 16px 24px;
		border-right: 1px solid #e8e8e8;
		border-bottom: 1px solid #e8e8e8;
	}

	.detailtabel table tr td {
		padding: 16px 24px;
		border-right: 1px solid #e8e8e8;
		border-bottom: 1px solid #e8e8e8;
	}

	.detailtabel table th {
		color: rgba(0, 0, 0, .85);
		font-weight: 400;
		font-size: 14px;
		line-height: 1.5;
	}

	.border-rt {
		border-right: none !important;
	}

	.border-bt {
		border-bottom: none !important;
	}
	.el-upload {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
	}
	
	.avatar-uploader {
		border: 1px dashed #d9d9d9;
		border-radius: 6px;
		cursor: pointer;
		position: relative;
		overflow: hidden;
		border-color: var(--nf-brand);
		width: 148px;
		height: 148px;
	}
	
	.el-upload:hover {
		border-color: var(--nf-brand);
	}
	
	.avatar-uploader-icon {
		font-size: 28px;
		color: #8c939d;
		width: 148px;
		height: 148px;
		line-height: 148px;
		text-align: center;
	}
	
	.avatar {
		width: 148px;
		height: 148px;
		display: block;
	}
	
	
</style>
