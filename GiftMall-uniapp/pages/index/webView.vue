<template>
	<view>
		<web-view :webview-styles="webviewStyles" :src="urls"></web-view>
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
	const urls = ref('https://www.xiansqx.com');
	const webviewStyles = ref({
		progress: {
			color: '#FF3333'
		}
	})

	onLoad(option => {
		if (option.url) {
			urls.value = option.url;
		} else {
			uni.navigateBack();
		}
	});
	onShow(() => {

	});
	onPullDownRefresh(() => {

	});
	onReachBottom(() => {

	});
</script>

<style lang="scss">

</style>