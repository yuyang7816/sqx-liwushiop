<template>
	<view>
		<view class="swiper_box">
			<swiper class="screen-swiper" :circular="true" :autoplay="true" interval="2500" duration="800">
				<swiper-item v-for="(item,index) in swiperList" :key="index" @tap="goNav(item.url)">
					<image :src="item.imageUrl" class="screen-swiper" mode="aspectFill"></image>
				</swiper-item>
			</swiper>
		</view>
		<view class="contont">
			<view class="contont_seach">
				<image src="/static/image/index/icon-search.png"></image>
				<up-input placeholder="输入关键字搜索商品" border="none" v-model="keyword" @change="change"></up-input>
			</view>
		</view>

		<view class="typebox">
			<view class="typebox_Item">
				<scroll-view scroll-y="true" :style="{height:windowHeight+'rpx'}">
					<view v-for="(item,index) in listType" :key="index" @click="clickType(item,index)"
						class="typebox_Itembox" :class="typeIndex==index?'typebox_ItemboxAct':''">
						{{item.name}}
					</view>
				</scroll-view>
			</view>
			<view class="typebox_list">
				<scroll-view  scroll-y="true" :style="{height:windowHeight+'rpx'}"
					:refresher-enabled="true" refresher-background="#FFFFFF" :refresher-triggered="refresherTriggered"
					@refresherrefresh="upper" @scrolltolower="lower">
					<view v-for="(item,index) in list" :key="index" class="scrollItem" @click="goDetail(item)">
						<image :src="item.coverImg?item.coverImg:'/static/logo.png'" mode="aspectFill">
						</image>
						<view class="scrollItemInfo">
							<view class="sscrollItem_title ">{{item.name}}</view>
							<view class="sscrollItem_tit">已售{{item.sales}}</view>
							<view class="sscrollItem_bot">
								<view class="sscrollItem_botprice">¥{{item.price}}</view>
								<view class="sscrollItem_botAdd" >
									<up-icon name="plus-circle-fill" color="#F6317A" size="28"></up-icon>
								</view>
							</view>
						</view>
					</view>
					<!-- 加载数据 -->
					<up-loadmore :status="status" v-if="list.length>0" />
					<!-- 暂无数据 -->
					<empty v-if="list.length==0"></empty>
				</scroll-view>
			</view>
		</view>
	</view>
</template>

<script setup>
	import {
		ref,
		reactive,
		getCurrentInstance
	} from 'vue';
	import {
		onLoad,
		onShow,
		onPullDownRefresh,
		onReachBottom
	} from '@dcloudio/uni-app';
	const {
		proxy
	} = getCurrentInstance();
	const swiperList = reactive([{
		imageUrl: 'https://pt2.0.xianmxkj.com/file/uploadPath/2025/06/26/3f25bdd1c7b0df7b5fbd4c4b9888dd9a.png'
	}]);
	const keyword = ref('');
	const typeIndex = ref(0);
	const windowHeight = ref(0); //分类高度
	const refresherTriggered = ref(false); //设置当前下拉刷新状态，true 表示下拉刷新已经被触发，false 表示下拉刷新未被触发
	const listType = reactive([{
		id: '',
		name: '全部'
	}]);
	const list = reactive([]);
	const page = ref(1);
	const pages = ref(1);
	const limit = ref(10);
	//加载状态
	const status = ref('loadmore');
	const typeId = ref('');

	onLoad(option => {
		let systemInfo = uni.getSystemInfoSync();
		console.log(systemInfo)
		windowHeight.value = systemInfo.screenHeight + 120
		console.log(windowHeight.value)
	});
	onShow(() => {
		getBanner(); //获取轮播图
		getGoodType(); //获取分类
		getGoodlist(); //获取商品
	});
	const goLoginInfo=()=>{
		uni.navigateTo({
			url:'/pages/public/login'
		})
	};
	// 跳转商品详情
	const goDetail = (item) => {
		let token = uni.getStorageSync('token');
		if (!token) {
			goLoginInfo();
			return;
		}
		uni.navigateTo({
			url: '/my/Commodity/detail?id=' + item.id
		})
	};
	// 获取轮播图
	const getBanner = () => {
		proxy.$http.postJson('/app/banner/page', {
			classify: 3, // 分类1banner图 2金刚区分类	 3商城轮播图
			state: 1, //状态1正常 2隐藏
		}).then(res => {
			if (res.code == 0) {
				swiperList.splice(0, swiperList.length, ...res.data.records)
			}
		})
	};
	const clickType = (item, index) => {
		typeIndex.value = index
		typeId.value = item.id
		page.value = 1
		getGoodlist();
	};
	// 获取商品分类
	const getGoodType = () => {
		proxy.$http.getT('/app/productType/list').then(res => {
			if (res.code == 0) {
				listType.splice(1, listType.length, ...res.data.records)
			}
		})
	};
	// 搜索
	const change=(e)=>{
		// console.log(e)
		keyword.value = e
		page.value = 1
		getGoodlist();
	};
	// 获取列表
	const getGoodlist = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			status: 0, //商品状态(默认0正常 1下架)
			typeId: typeId.value,
			name:keyword.value
		}
		proxy.$http.getT('/app/product/page', data).then(res => {
			if (res.code == 0) {
				pages.value = res.data.pages
				if (page.value < pages.value) {
					status.value = 'loadmore';
				} else {
					status.value = 'nomore';
				}
				if (page.value === 1) {
					list.splice(0, list.length, ...res.data.records);
				} else {
					list.push(...res.data.records);
				}
				status.value = 'nomore';
			}
			status.value = 'nomore';
			refresherTriggered.value = false
			uni.stopPullDownRefresh();
		})
	};
	// 触发自定义底部加载
	const lower = () => {
		console.log('触发自定义底部加载')
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getGoodlist();
		} else {
			uni.showToast({
				title: '已经到底了~',
				icon: 'none'
			})
		}
	};
	// 触发自定义下拉刷新
	const upper = () => {
		console.log('触发自定义下拉刷新')
		refresherTriggered.value = true
		status.value = 'loading';
		page.value = 1
		getGoodlist()

	};
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	page {
		background: #FFFFFF;
	}

	.swiper_box {
		width: 100%;


		.screen-swiper {
			width: 100%;
			height: 235rpx;
		}
	}

	.contont {
		padding: 20rpx 30rpx;
		background: #FFFFFF;

		.contont_seach {
			padding: 0 30rpx;
			height: 80rpx;
			background: #F7F7F7;
			border-radius: 40rpx;
			border: 1px solid #E6E6E6;
			display: flex;
			align-items: center;

			image {
				width: 34rpx;
				height: 34rpx;
				margin-right: 10rpx;
			}
		}
	}

	.input-placeholder {
		font-family: PingFang SC;
		font-weight: 400;
		font-size: 24rpx;
		color: #CCCCCC;
	}

	.typebox {
		width: 100%;
		display: flex;


		.typebox_Item {
			width: 220rpx;
			background: #FFFFFF;
			border-right: 1px solid #F2F2F2;

			.typebox_Itembox {
				width: 220rpx;
				height: 107rpx;
				background: #FFFFFF;
				display: flex;
				align-items: center;
				justify-content: center;
				font-family: Source Han Sans CN;
				font-weight: 500;
				font-size: 28rpx;
				color: #1A1A1A;
			}

			.typebox_ItemboxAct {
				background: #F7F7F7 !important;
				color: #EF276A !important;
			}
		}

		.typebox_list {
			flex: 1;

			.scrollItem {
				width: 100%;
				display: inline-flex;
				background: #FFFFFF;
				border-bottom: 1px solid #F2F2F2;
				padding: 30rpx 10rpx 30rpx 20rpx;

				image {
					width: 180rpx;
					height: 180rpx;
					border-radius: 24rpx;
				}

				.scrollItemInfo {
					flex: 1;
					padding-left: 20rpx;
					display: flex;
					justify-content: space-between;
					flex-direction: column;

					.sscrollItem_title {
						font-family: Source Han Sans CN;
						font-weight: 500;
						font-size: 32rpx;
						color: #1A1A1A;
						width: 280rpx;
						white-space: nowrap;
						/*防止文本焕行*/
						overflow: hidden;
						/*隐藏溢出的内容*/
						text-overflow: ellipsis;
						/*显示省略号 */
					}

					.sscrollItem_tit {
						font-family: PingFang SC;
						font-weight: 500;
						font-size: 24rpx;
						color: #CCCCCC;
					}

					.sscrollItem_bot {
						display: flex;
						align-items: center;
						justify-content: space-between;
						padding-right: 40rpx;

						.sscrollItem_botprice {
							font-family: DIN;
							font-weight: bold;
							font-size: 38rpx;
							color: #EF276A;
						}

						.sscrollItem_botAdd {}
					}
				}
			}
		}

	}
</style>