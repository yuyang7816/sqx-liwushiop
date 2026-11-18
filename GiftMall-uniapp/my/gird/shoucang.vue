<template>
	<view>
		<view class="listbox" v-for="(item,index) in list" :key="index" @click="goDetail(item)" >
			<image :src="item.giftProduct?item.giftProduct.coverImg:'/static/logo.png'" mode="aspectFill"></image>
			<view class="listbox_Cot" v-if="item.giftProduct">
				<view class="listbox_CotTitle">{{item.giftProduct.name}}</view>
				<view class="listbox_Cotbot">
					<view class="listbox_Cotbot_price">
						¥<text>{{item.giftProduct.price}}</text>
					</view>
					<view class="listbox_Cotbot_btn">立即购买</view>
				</view>
			</view>
		</view>

		<!-- 加载数据 -->
		<up-loadmore :status="status" v-if="list.length>0" />
		<!-- 暂无数据 -->
		<empty v-if="list.length==0"></empty>
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
	const page = ref(1);
	const pages = ref(1);
	const limit = ref(10);
	const list = reactive([]);
	const status = ref('loadmore'); //加载状态

	onLoad(option => {

	});
	onShow(() => {
		page.value = 1
		getlist();
	});
	// 跳转商品详情
	const goDetail = (item) => {
		uni.navigateTo({
			url: '/my/Commodity/detail?id=' + item.productId
		})
	};
	// 获取列表
	const getlist = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			userId:uni.getStorageSync('userId')
		}

		proxy.$http.getT('/app/userCollect/page', data).then(res => {
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
			uni.stopPullDownRefresh();
		})
	};
	onPullDownRefresh(() => {
		page.value = 1
		getlist();
	});
	onReachBottom(() => {
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getlist();
		}
	});
</script>

<style lang="scss">
	page {
		background: #F5F5F5;
	}

	.listbox {
		margin: 20rpx;
		padding: 30rpx;
		background: #FFFFFF;
		border-radius: 24rpx;
		display: flex;

		image {
			width: 163rpx;
			height: 163rpx;
			border-radius: 12rpx;
		}

		.listbox_Cot {
			height: 163rpx;
			flex: 1;
			margin-left: 10rpx;
			display: flex;
			justify-content: space-between;
			flex-direction: column;

			.listbox_CotTitle {
				font-family: Source Han Sans CN;
				font-weight: 500;
				font-size: 28rpx;
				color: #333333;
				display: -webkit-box;
				-webkit-line-clamp: 2;
				/*显示3行*/
				-webkit-box-orient: vertical;
				overflow: hidden;
			}

			.listbox_Cotbot {
				display: flex;
				align-items: flex-end;
				justify-content: space-between;

				.listbox_Cotbot_price {
					font-family: PingFang SC;
					font-weight: bold;
					font-size: 24rpx;
					color: #F6317A;

					text {
						font-size: 40rpx;
					}
				}

				.listbox_Cotbot_btn {
					width: 150rpx;
					height: 56rpx;
					background: #EF276A;
					border-radius: 28rpx;

					font-size: 26rpx;
					color: #FFFFFF;
					display: flex;
					align-items: center;
					justify-content: center;
				}
			}
		}
	}
</style>