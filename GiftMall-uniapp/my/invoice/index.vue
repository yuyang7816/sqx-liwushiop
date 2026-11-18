<template>
	<view>
		<view @click="goNav('./Invoicing')" class="invoice-box " style="border: none;">
			<view class="invoice-boxInfo">
				<image src="../static/rider/kp.png" mode="widthFix"></image>
				<text>申请开票</text>
			</view>
			<up-icon name="arrow-right" color="#000000" size="18"></up-icon>
		</view>

		<view class="invoice-box margin-top-sm" @click="goNav('./kpjl')">
			<view class="invoice-boxInfo">
				<image src="../static/rider/kpjl.png" mode="widthFix"></image>
				<text>开票记录</text>
			</view>
			<up-icon name="arrow-right" color="#000000" size="18"></up-icon>
		</view>
		<view style="border: none;" class="invoice-box" @click="goNav('/my/invoice/kpsm')">
			<view class="invoice-boxInfo">
				<image src="../static/rider/kpsm.png" mode="widthFix"></image>
				<text>开票说明</text>
			</view>
			<up-icon name="arrow-right" color="#000000" size="18"></up-icon>
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

	onLoad(option => {

	});
	onShow(() => {

	});
	// 跳转路由
	const goNav = (url) => {
		uni.navigateTo({
			url
		})
	};
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	.invoice-box {
		background: #FFFFFF;
		padding: 20rpx 30rpx;
		border-bottom: 1rpx solid #f2f2f2;
		display: flex;
		align-items: center;
		justify-content: space-between;

		.invoice-boxInfo {
			display: flex;
			align-items: center;

			image {
				width: 40rpx;
				height: 45rpx;
			}

			text {
				margin-left: 10rpx;

			}
		}
	}
</style>