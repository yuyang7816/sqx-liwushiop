<template>
	<view style="padding-bottom: 200rpx;">
		<!-- 发票信息 -->
		<view class="box ">
			<view class="boxItem ">
				<view class="boxIte_Title">发票抬头
					<text style="">*</text>
				</view>
				<view class="boxIte_Input">
					<up-input placeholder="例:西安XXX有限公司" border="none" v-model="taitou" input-align="right"></up-input>
				</view>
			</view>
			<view class="boxItem ">
				<view class="boxIte_Title">纳税人识别号
					<text style="">*</text>
				</view>
				<view class="boxIte_Input">
					<up-input placeholder="请填写纳税人识别号" border="none" v-model="shibiehao" input-align="right"></up-input>
				</view>
			</view>
			<view class="boxItem ">
				<view class="boxIte_Title">发票类型</view>
				<view class="boxIte_Input">
					<up-input placeholder="请填写发票类型" :readonly="true" border="none" v-model="types"
						input-align="right"></up-input>
				</view>
			</view>
			<view class="boxItem ">
				<view class="boxIte_Title">发票内容</view>
				<view class="boxIte_Input">
					<up-input placeholder="请填写发票内容" border="none" v-model="contents" input-align="right"></up-input>
				</view>
			</view>
			<view class="boxItem ">
				<view class="boxIte_Title">发票金额
					<text style="">*</text>
				</view>
				<view class="boxIte_Input">
					<text style="color: #02B595;">{{price}}元</text>
				</view>
			</view>
			<view class="boxItem ">
				<view class="boxIte_Title">注册地址</view>
				<view class="boxIte_Input">
					<up-input placeholder="请填写注册地址" border="none" v-model="address" input-align="right"></up-input>
				</view>
			</view>
			<view class="boxItem ">
				<view class="boxIte_Title">注册电话</view>
				<view class="boxIte_Input">
					<up-input placeholder="请填写注册电话" border="none" v-model="zcphone" input-align="right"></up-input>
				</view>
			</view>
			<view class="boxItem ">
				<view class="boxIte_Title">开户银行</view>
				<view class="boxIte_Input">
					<up-input placeholder="请填写开户银行" border="none" v-model="yh" input-align="right"></up-input>
				</view>
			</view>
			<view class="boxItem ">
				<view class="boxIte_Title">银行账号</view>
				<view class="boxIte_Input">
					<up-input placeholder="请填写银行账号" border="none" v-model="yhzh" input-align="right"></up-input>
				</view>
			</view>
		</view>
		<view class="info ">收件人信息</view>
		<view class="box ">
			<view class="boxItem ">
				<view class="boxIte_Title">接受电子邮件
					<text style="">*</text>
				</view>
				<view class="boxIte_Input">
					<up-input placeholder="用于向你发送电子发票" border="none" v-model="email" input-align="right"></up-input>
				</view>
			</view>
			<view class="boxItem ">
				<view class="boxIte_Title">接收手机号
					<text style="">*</text>
				</view>
				<view class="boxIte_Input">
					<up-input placeholder="订单记录通过短信发送" border="none" v-model="phone" type="number" maxlength="11"
						input-align="right"></up-input>
				</view>
			</view>
		</view>

		<view class="taber">
			<view class="btn" @click="submit">提交</view>
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

	const taitou = ref(''); //发票抬头
	const shibiehao = ref(''); //纳税人识别号
	const types = ref('电子发票');
	const contents = ref(''); //发票内容
	const price = ref(0); //发票金额
	const indentNumber = ref(''); //订单号
	const address = ref(''); //注册地址
	const zcphone = ref(''); //注册电话
	const yh = ref(''); //开户银行
	const yhzh = ref(''); //银行账号
	const email = ref(''); //电子发票邮箱
	const phone = ref(''); //联系电话

	onLoad(option => {
		if (option.price) {
			price.value = option.price
		}
		if (option.indentNumber) {
			indentNumber.value = option.indentNumber
		}

	});
	onShow(() => {

	});
	const submit = () => {
		if (!taitou.value) {
			uni.showToast({
				title: '请输入发票抬头',
				icon: 'none'
			})
			return
		}
		if (!shibiehao.value) {
			uni.showToast({
				title: '请输入纳税人识别号',
				icon: 'none'
			})
			return
		}
		if (!email.value) {
			uni.showToast({
				title: '请输入接收电子邮件',
				icon: 'none'
			})
			return
		}
		if (!phone.value) {
			uni.showToast({
				title: '请输入接收手机号',
				icon: 'none'
			})
			return
		}
		let data = {
			indentNumber: indentNumber.value,
			userType: 1,
			invoiceType: 1,
			invoiceTitle: contents.value,
			invoiceMoney: price.value,
			userEmail: email.value,
			userPhone: phone.value,
			invoiceHeader: taitou.value,
			taxPayerNumber: shibiehao.value,
			bankAddress: address.value,
			bankPhone: zcphone.value,
			bankDeposit: yh.value,
			bankNumber: yhzh.value
		}
		proxy.$http.postJson("/app/invoice/applyInvoice", data).then(res => {
			if (res.code == 0) {
				uni.showToast({
					title: '提交成功!'
				})
				setTimeout(() => {
					uni.navigateBack()
				}, 1000)
			} else {
				uni.showToast({
					title: res.msg,
					icon: 'none'
				})
			}
		});
	};
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
	.box {
		margin: 20rpx 30rpx;
		background: #FFFFFF;
		padding: 0 30rpx;
		border-radius: 24rpx;

		.boxItem {
			display: flex;
			align-items: center;
			justify-content: space-between;
			border-bottom: 1px solid #f2f2f2;
			padding: 25rpx 0;

			.boxIte_Title {
				display: flex;
				align-items: center;
				font-size: 30rpx;

				text {
					color: red;
					font-size: 32rpx;
					margin-left: 10rpx;
				}
			}
		}

		.box-con {
			width: 686rpx;
			height: 100%;
			background-color: #ffffff;
			border-radius: 16rpx;

			.box-con-c {
				width: 646rpx;
				height: 100%;
			}

			.box-con-c-item {
				width: 100%;
				height: 100rpx;
				border-bottom: 1rpx solid #f2f2f2;

				.box-con-c-item-l {
					width: 240rpx;
					height: 100%;
				}
			}
		}
	}

	.info {
		margin: 20rpx 30rpx;
		color: #999;
	}

	.taber {

		background: #FFFFFF;
		padding: 20rpx 30rpx;
		position: fixed;
		bottom: 0;
		left: 0;
		right: 0;
		z-index: 1;

		.btn {
			background: #F6317A;
			height: 100rpx;
			display: flex;
			align-items: center;
			justify-content: center;
			border-radius: 55rpx;
			color: #FFFFFF;
			font-size: 32rpx;
		}
	}
</style>