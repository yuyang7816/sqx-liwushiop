<template>
	<view>

		<view class="fpList" v-for="(item,index) in list" :key="index">
			<view class="fpList_Top" :style="item.status==3?'color:red;':'color:green;'">
				{{item.status==1?'处理中':(item.status==2?'开票完成':'开票失败')}}
				<text style="color: black;">{{item.invoiceMoney}}元</text>
			</view>
			<view class="fpList_tits">发票抬头：{{item.invoiceHeader}}</view>
			<view class="fpList_tits">纳税人识别号：{{item.taxPayerNumber}}</view>
			<view class="fpList_tits">发票金额：{{item.invoiceMoney}}	</view>
			<view class="fpList_tits" v-if="item.bankDeposit">开户行：{{item.bankDeposit}}</view>
			<view class="fpList_tits" v-if="item.bankNumber">银行卡号：{{item.bankNumber}}</view>
			<view class="fpList_tits" v-if="item.bankPhone">开户手机号：{{item.bankPhone}}</view>
			<view class="fpList_tits" v-if="item.invoiceTitle">开票内容：{{item.invoiceTitle}}</view>
			<view class="fpList_tits">接收邮箱：{{item.userEmail}}</view>
			<view class="fpList_tits">接收手机号：{{item.userPhone}}</view>
			<view class="fpList_titsTime">{{item.createTime}}</view>
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
	const list = reactive([]);
	const page = ref(1);
	const pages = ref(1);
	const limit = ref(10);
	//加载状态
	const status = ref('loadmore');


	onLoad(option => {

	});
	onShow(() => {
		page.value = 1
		getlist();
	});
	//订单数据
	const getlist = () => {
		let data = {
			page: page.value,
			limit: limit.value,
		}
		proxy.$http.getT("/app/invoice/getMyInvoiceList", data).then(res => {
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
		});
	};
	onPullDownRefresh(() => {
		page.value = 1;
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
	.fpList {
		margin: 20rpx 30rpx;
		background: #FFFFFF;
		border-radius: 24rpx;
		padding: 30rpx;

		.fpList_Top {
			display: flex;
			align-items: center;
			justify-content: space-between;
			font-weight: bold;
			font-size: 30rpx;
			margin-bottom: 20rpx;
		}


		.fpList_tits {
			color: #999999;
			margin-top: 10rpx;
		}

		.fpList_titsTime {
			color: #999999;
			margin-top: 10rpx;
			display: flex;
			align-items: center;
			justify-content: flex-end;
		}
	}
</style>