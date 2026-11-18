<template>
	<view>
		<view style="line-height: 26px;padding: 32rpx;" class="home1">
			<view style="font-size: 28rpx;" v-html="content"></view>
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
	const content = ref('');

	onLoad(option => {
		proxy.$http.getT('/app/common/type/319').then(res => {
			if (res.code == 0) {
				if (res.data && res.data.value) {
					content.value = res.data.value;
				}
			}
		});
	});
	onShow(() => {

	});
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">
page{
	background: #FFFFFF;
}
</style>