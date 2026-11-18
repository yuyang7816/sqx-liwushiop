<script setup>
	import {
		getCurrentInstance
	} from 'vue';
	const {
		proxy
	} = getCurrentInstance();
	import {
		onLaunch,
		onShow,
		onHide
	} from '@dcloudio/uni-app';

	onLaunch(() => {
		// #ifdef MP-WEIXIN
		//是否开启小程序自动登录	326
		proxy.$http.get('/app/common/type/326').then(rep => {
			if (rep.code == 0 && rep.data) {
				if (rep.data.value == '是') {
					//没有登录的时候开始自动登录
					if (!uni.getStorageSync('token')) {
						uni.login({
							provider: 'weixin',
							success: function(loginRes) {
								console.log(loginRes, 'code')

								let data = {
									code: loginRes.code
								}
								proxy.$http.get('/app/auth/wxLogin', data).then(res => {
									if (res.code == 0 && res.data) {
										uni.setStorageSync('openId', res.data.openid)
										uni.setStorageSync('unionId', res.data.unionid)
										uni.setStorageSync('flag', res.data.bindPhone)
										uni.setStorageSync('sessionkey', res.data
											.sessionKey)
										
										if (res.data.bindPhone) {
											uni.setStorageSync('token', res.data.loginVO.token)
											
											uni.setStorageSync('userName', res.data.userEntity.userName)
											uni.setStorageSync('avatar', res.data.userEntity.avatar)
											uni.setStorageSync('phone', res.data.userEntity.phone)
											uni.setStorageSync('invitationCode', res.data.userEntity.invitationCode)
											uni.setStorageSync('userId', res.data.userEntity.userId)
											uni.setStorageSync('openId', res.data.userEntity.openId)
										}

									}
								})

							}
						});
					}
				}
			}
		});
		// #endif


		// 企业微信客服链接	320
		proxy.$http.get('/app/common/type/320').then(res => {
			if (res.code == 0 && res.data.value) {
				uni.setStorageSync('SelKeFuLink', res.data.value)
			}
		})
		//企业微信客服appid
		proxy.$http.get('/app/common/type/321').then(res => {
			if (res.code == 0 && res.data.value) {
				uni.setStorageSync('SelKeFuAppId', res.data.value)
			}
		})
		//客服类型  322 客服类型(1:电话，2:企业微信，3:客服二维码)
		proxy.$http.get('/app/common/type/322').then(res => {
			if (res.code == 0 && res.data.value) {
				uni.setStorageSync('SelKeFu', res.data.value)
			}
		})
		//客服电话  323
		proxy.$http.get('/app/common/type/323').then(res => {
			if (res.code == 0 && res.data.value) {
				uni.setStorageSync('kefuPhone', res.data.value)
			}
		})
	});
	onShow(() => {
		console.log('onShow')
	});
	onHide(() => {
		console.log('onHide')
	});
</script>

<style lang="scss">
	/*每个页面公共css */
	//uview-plus公共样式
	@import "@/uni_modules/uview-plus/index.scss";
	//自定义公共样式
	@import "@/static/css/main.css";



	page {
		background-color: #F5F7FF;
	}
</style>