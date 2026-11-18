<template>
    <div class="_div">
        <div style="display: inline-block;font-size:18px;margin: 10px;">
            <a href="#" @click="prev" style="text-decoration:none;font-size: 14px;">
                返回
            </a>
            <span style="display: inline-block;margin: 0 15px;color: #D9D9D9;">|</span>
            <span>线路规划</span>
        </div>
       <div>
			<div id="container" style="width:95%;height:80vh;margin-left: 2.5%;position: relative;"
				v-loading="dataContainer.tableDataLoadingDt"></div>
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
                polyline: [],
				qilng: 108.948024, //起点经度
				qilat: 34.263161, //起点纬度
				zhonglng: 108.948024, //终点经度
				zhonglat: 34.263161, //终点纬度
                // -------------
                





        

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
            if (route.query.item) {
				let item = JSON.parse(route.query.item)
                // if(item.polyline){
                //     dataContainer.polyline = item.polyline
                // }
                // if(item.shipAddressLongitude){
                //     dataContainer.qilng = item.shipAddressLongitude
                // }
                // if(item.shipAddressLatitude){
                //     dataContainer.qilat = item.shipAddressLatitude
                // }
                // if(item.deliveryAddressLongitude){
                //     dataContainer.zhonglng = item.deliveryAddressLongitude
                // }
                // if(item.deliveryAddressLatitude){
                //     dataContainer.zhonglat = item.deliveryAddressLatitude
                // }
				dataContainer.polyline = item.polyline
				dataContainer.qilng = item.shipAddressLongitude //起点经度
				dataContainer.qilat = item.shipAddressLatitude //起点纬度
				dataContainer.zhonglng = item.deliveryAddressLongitude //终点经度
				dataContainer.zhonglat = item.deliveryAddressLatitude //终点纬度
				console.log('item', dataContainer.qilng,dataContainer.qilat)
                // setMap();
                setTimeout(() =>{
                     setMap();
                },500)
                dataContainer.tableDataLoadingDt = false
            }
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
                        setMap()
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
            console.log('item---',typeof dataContainer.qilat)
            var center = new TMap.LatLng(dataContainer.qilat, dataContainer.qilng); 
            
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
                var startPosition = new TMap.LatLng(dataContainer.qilat, dataContainer.qilng); // 路线规划起点

				var endPosition = new TMap.LatLng(dataContainer.zhonglat, dataContainer.zhonglng); // 路线规划终点
				var marker = new TMap.MultiMarker({
					// 创造MultiMarker显示起终点标记
					id: 'marker-layer',
					map: map,
					styles: {
						start: new TMap.MarkerStyle({
							width: 25,
							height: 35,
							anchor: {
								x: 16,
								y: 32
							},
							src: 'https://mapapi.qq.com/web/lbs/javascriptGL/demo/img/start.png',
						}),
						end: new TMap.MarkerStyle({
							width: 25,
							height: 35,
							anchor: {
								x: 16,
								y: 32
							},
							src: 'https://mapapi.qq.com/web/lbs/javascriptGL/demo/img/end.png',
						}),
					},
					geometries: [{
							id: 'start',
							styleId: 'start',
							position: startPosition,
						},
						{
							id: 'end',
							styleId: 'end',
							position: endPosition,
						},
					],
				});
				console.log('startPosition', startPosition, endPosition)
				var driving = new TMap.service.Driving({
					// 新建一个驾车路线规划类
					mp: false, // 是否返回多方案
					policy: 'PICKUP,NAV_POINT_FIRST', // 规划策略
				});
				driving.search({
					from: startPosition,
					to: endPosition
				}).then((result) => {
					// 搜索路径
					// result.result.routes[0].steps.forEach((step, index) => {
					//   document.getElementById('instruction').innerHTML += `<p>${index + 1}. ${
					//     step.instruction
					//   }</p>`;
					//   // 展示路线引导
					// });
					display_polyline(result.result.routes[0].polyline); // 绘制路径折线
				});

			}
        function jsonp_request(url) {

				var script = document.createElement('script');
				script.src = url;
				document.body.appendChild(script);
			}
        function huidiao(ret) {
				console.log('获取回调···', ret.result.routes[0])
				console.log('回调经纬度---', ret.result.routes[0].polyline)
				var coords = ret.result.routes[0].polyline,
					pl = [];
				// var coords = this.polyline, pl = [];
				//坐标解压（返回的点串坐标，通过前向差分进行压缩）
				var kr = 1000000;
				for (var i = 2; i < coords.length; i++) {
					coords[i] = Number(coords[i - 2]) + Number(coords[i]) / kr;
				}
				//将解压后的坐标放入点串数组pl中
				for (var i = 0; i < coords.length; i += 2) {
					pl.push(new TMap.LatLng(coords[i], coords[i + 1]));
				}

				display_polyline(pl) //显示路线

				//标记起终点marker
				console.log('new TMap.LatLng(this.qilat,this.qilng)', new TMap.LatLng(this.qilat, this.qilng))
				var marker = new TMap.MultiMarker({
					id: 'marker-layer',
					map: map,
					styles: {
						"start": new TMap.MarkerStyle({
							"width": 25,
							"height": 35,
							"anchor": {
								x: 16,
								y: 32
							},
							"src": 'https://mapapi.qq.com/web/lbs/javascriptGL/demo/img/start.png'
						}),
						"end": new TMap.MarkerStyle({
							"width": 25,
							"height": 35,
							"anchor": {
								x: 16,
								y: 32
							},
							"src": 'https://mapapi.qq.com/web/lbs/javascriptGL/demo/img/end.png'
						})
					},
					geometries: [{
						"id": 'start',
						"styleId": 'start',
						"position": new TMap.LatLng(dataContainer.qilat, dataContainer.qilng)
					}, {
						"id": 'end',
						"styleId": 'end',
						"position": new TMap.LatLng(dataContainer.zhonglat, dataContainer.zhonglng)
					}]
				});

			}
        function display_polyline(pl) {

				//创建 MultiPolyline显示折线
				var polylineLayer = new TMap.MultiPolyline({
					id: 'polyline-layer', //图层唯一标识
					map: map, //绘制到目标地图
					//折线样式定义
					styles: {
						'style_blue': new TMap.PolylineStyle({
							'color': '#3777FF', //线填充色
							'width': 8, //折线宽度
							'borderWidth': 5, //边线宽度
							'borderColor': '#FFF', //边线颜色
							'lineCap': 'round', //线端头方式
						})
					},
					//折线数据定义
					geometries: [{
						'id': 'pl_1', //折线唯一标识，删除时使用
						'styleId': 'style_blue', //绑定样式名
						'paths': pl
					}]
				});
			}
           
        return {
            dataContainer,
            initData,
            prev,
            dataSelect,
            getMyLocation,
            setMap,
            showPosition,
            showErr,
            jsonp_request,
            huidiao,
            display_polyline,


            
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
