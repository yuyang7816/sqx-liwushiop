<template>
	<view style="padding-bottom: 200rpx;">
		<view class="bgColor">
			<view class="tanbox">
				<scroll-view class="scroll-view_H" scroll-x="true" scroll-left="0">
					<view class="tanboxItem" v-for="(item,index) in cardType" :key="index"
						@click="changeCard(item,index)" :class="index==cardTypeIndex?'tanboxItemAct':''">
						{{item.cardCategoryName}}
					</view>
				</scroll-view>
			</view>
			<view class="listbox">
				<view class="listboxItem" v-for="(item,index) in cardlist" :key="index" @click="bindCard(item,index)">
					<image :src="item.cardImg" mode="aspectFill"></image>

					<view class="listboxItem_Icon" v-if="item.isCheck">
						<up-icon name="checkmark-circle-fill" color="#F6317A" size="20"></up-icon>
					</view>
				</view>
			</view>
			<!-- 加载数据 -->
			<up-loadmore :status="status" v-if="cardlist.length>0" />
			<!-- 暂无数据 -->
			<empty v-if="cardlist.length==0"></empty>
		</view>
		<view class="taber">
			<view class="taber_btn" :style="cardCategoryId?'background:#F6317A':''"
				@click="cardCategoryId?goCardDet():''">确认</view>
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
	const cardType = reactive([{
		cardCategoryName: '全部图案',
		cardCategoryId: ''
	}]);
	const cardTypeIndex = ref(0);
	const cardCategoryId = ref('');
	const cardlist = reactive([]);
	const page = ref(1);
	const pages = ref(1);
	const limit = ref(10);
	//加载状态
	const status = ref('loadmore');


	onLoad(option => {
		getCardType(); //卡片分类
		getCardlist();
	});
	onShow(() => {

	});
	const bindCard = (e, index) => {
		console.log(e)
		// e.isCheck = true
		cardlist.forEach((d, ind) => {
			if (ind == index) {
				d.isCheck = true
			} else {
				d.isCheck = false
			}
		})
		cardCategoryId.value = e.cardCategoryId
	};
	/* 跳转详情 */
	const goCardDet = (ite) => {
		uni.navigateTo({
			url: '/my/gift/index?cardCategoryId=' + cardCategoryId.value
		})
	};
	// 选择切换卡片分类
	const changeCard = (item, index) => {
		cardTypeIndex.value = index
		cardCategoryId.value = item.cardCategoryId
		page.value = 1
		getCardlist();
	};
	// 获取卡片分类
	const getCardType = () => {
		proxy.$http.getT('/app/cardCategory/listCardCategory').then(res => {
			if (res.code == 0) {
				cardType.splice(1, cardType.length, ...res.data.records)
			}
		})
	};
	// 获取卡片列表
	const getCardlist = () => {
		let data = {
			page: page.value,
			limit: limit.value,
			cardCategoryId: cardCategoryId.value
		}
		proxy.$http.getT('/app/card/listCard', data).then(res => {
			if (res.code == 0) {
				if (res.code == 0) {
					pages.value = res.data.pages
					if (page.value < pages.value) {
						status.value = 'loadmore';
					} else {
						status.value = 'nomore';
					}
					res.data.records.forEach(d => {
						d.isCheck = false
					})
					if (page.value === 1) {
						cardlist.splice(0, cardlist.length, ...res.data.records);
					} else {
						cardlist.push(...res.data.records);
					}
					status.value = 'nomore';
				}
				status.value = 'nomore';
				uni.stopPullDownRefresh();
			}
		})
	};
	onPullDownRefresh(() => {
		page.value = 1;
		getCardlist();
	});
	onReachBottom(() => {
		if (page.value < pages.value) {
			page.value += 1
			status.value = 'loading';
			getCardlist();
		}
	});
</script>

<style lang="scss">
	page {
		background: #F5F5F5;

	}

	.bgColor {
		background: linear-gradient(to bottom, #FFFFFF 0%, #F5F5F5 100%);
	}

	.tanbox {
		padding: 20rpx 30rpx;

		.scroll-view_H {
			white-space: nowrap;
			width: 100%;
		}

		.tanboxItem {
			height: 64rpx;
			background: #FFFFFF;
			border-radius: 32rpx;
			border: 1px solid #E6E6E6;
			font-family: Source Han Sans CN;
			font-weight: 400;
			font-size: 26rpx;
			color: #666666;
			padding: 0 25rpx;
			display: inline-flex;
			align-items: center;
			justify-content: center;
			margin-right: 20rpx;
		}

		.tanboxItemAct {
			height: 64rpx;
			background: #FFE9F1;
			border: 1px solid #FFE9F1;
			padding: 0 25rpx;
			color: #F6317A;
		}
	}



	.listbox {
		display: flex;
		flex-wrap: wrap;
		justify-content: space-between;
		margin: 20rpx 30rpx;

		.listboxItem {
			margin-bottom: 15rpx;
			position: relative;

			image {
				width: 333rpx;
				height: 200rpx;
				border-radius: 24rpx;
			}

			.listboxItem_Icon {
				position: absolute;
				top: 0;
				right: 0;
				background: #FFFFFF;
				border-radius: 55%;
				width: 30rpx;
				height: 30rpx;
				display: flex;
				align-items: center;
				justify-content: center;
			}
		}
	}

	.taber {
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		z-index: 99;
		padding: 15rpx 30rpx;
		background: #FFFFFF;

		.taber_btn {
			height: 80rpx;
			background: #E6E6E6;
			border-radius: 40rpx;
			font-family: Source Han Sans CN;
			font-weight: 500;
			font-size: 28rpx;
			color: #FFFFFF;
			display: flex;
			align-items: center;
			justify-content: center;
		}
	}
</style>