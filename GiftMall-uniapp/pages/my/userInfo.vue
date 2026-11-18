<template>
	<view>
		<view class="usermain">
			<view class="usermain-item ">
				<view>头像</view>
				<view>
					<!-- #ifdef MP-WEIXIN -->
					<button open-type="chooseAvatar" @chooseavatar="onChooseAvatar"
						style="width: 111rpx;height: 111rpx;border-radius: 50%;">
						<image :src="avatar?avatar:'../../static/logo.png'" mode=""
							style="width: 111rpx;height: 111rpx;border-radius: 50%;">
						</image>
					</button>
					<!-- #endif -->
					<!-- #ifndef MP-WEIXIN -->
					<image @click="uploadImg()" :src="avatar?avatar:'../../static/logo.png'" mode=""
						style="width: 111rpx;height: 111rpx;border-radius: 50%;">
					</image>
					<!-- #endif -->
				</view>
			</view>
			<view class="usermain-item item-padding ">
				<view>用户名</view>
				<view>
					<view class="cu-form-group">
						<input v-model="userName" type="nickname" placeholder="请输入用户名" />
					</view>
				</view>
			</view>
			<view class="usermain-item item-padding ">
				<view>年龄</view>
				<view>
					<view class="cu-form-group">
						<input v-model="age" />
					</view>
				</view>
			</view>

		</view>
		<view class="footer-btn">
			<view class="usermain-btn" @click="messagebtn()">保存</view>
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
		onLoad
	} from '@dcloudio/uni-app';
	import config from '../../common/config.js'
	const {
		proxy
	} = getCurrentInstance();
	const avatar = ref(''); //头像
	const userName = ref(''); //用户名
	const age = ref(0); //年龄
	const phone = ref(''); //手机号
	const sex = ref(1); //性别
	const userId = ref('');


	onLoad(option => {
		userId.value = uni.getStorageSync('userId');
		getUserInfo();
	});
	const getUserInfo = () => {
		let data = {
			userId: userId.value
		}
		proxy.$http.getT("/app/user/info", data).then(res => {
			if (res.code == 0) {
				uni.setStorageSync('avatar', res.data.avatar);
				uni.setStorageSync('userId', res.data.userId);
				uni.setStorageSync('userName', res.data.userName);
				uni.setStorageSync('phone', res.data.phone);
				uni.setStorageSync('age', res.data.age);
				sex.value = res.data.sex
				age.value = res.data.age
				phone.value = res.data.phone;
				avatar.value = res.data.avatar;
				userName.value = res.data.userName;
				if (userName.value == null) {
					userName.value = res.data.nickName;
				} else {
					userName.value = res.data.userName;
				}
			}
			uni.hideLoading();
		});
	};
	const messagebtn = () => {
		if (!userName.value) {
			uni.showToast({
				title: "用户名不能为空",
				icon: "none"
			})
			return
		}
		if (!phone.value) {
			uni.showToast({
				title: "联系电话不能为空",
				icon: "none"
			})
			return
		}
		uni.showModal({
			title: '温馨提示',
			content: '确定保存信息',
			success: e => {
				if (e.confirm) {
					proxy.$http.postJson("/app/user/update", {
						userName: userName.value,
						avatar: avatar.value,
						phone: phone.value,
						sex: sex.value,
						age: age.value,
						userId: uni.getStorageSync('userId')
					}).then(res => {
						if (res.code === 0) {
							uni.showToast({
								title: '保存成功',
								icon: "none"
							})
							setTimeout(function() {
								uni.navigateBack()
							}, 1000)
						} else {
							uni.showToast({
								title: res.msg,
								icon: "none"
							})
						}
					})
				}
			}
		});
	};
	//上传头像
	const uploadImg = () => {
		var url = null;
		uni.showActionSheet({
			// itemList按钮的文字接受的是数组
			itemList: ["查看头像", "从相册选择图片"],
			success(e) {
				var index = e.tapIndex
				if (index === 0) {
					// 用户点击了预览当前图片
					// 可以自己实现当前头像链接的读取
					let url = avatar.value;
					let arr = []
					arr.push(url)
					uni.previewImage({
						// 预览功能图片也必须是数组的
						urls: arr
					})
				} else if (index === 1) {
					uni.chooseImage({
						count: 1, //默认9
						sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
						sourceType: ['album'], //从相册选择
						success: function(res) {
							uni.showLoading({
								title: '上传中...'
							});
							let token = uni.getStorageSync('token');
							let userId = uni.getStorageSync('userId');
							uni.uploadFile({
								// url: 'https://mydz.xianmxkj.com/sqx_fast/alioss/uploads', //真实的接口地址
								url: config.APIHOST + '/file/upload',
								filePath: res.tempFilePaths[0],
								header: {
									token: uni.getStorageSync('token')
								},
								name: 'file',
								success: uploadFileRes => {
									url = JSON.parse(uploadFileRes.data);
									console.log(url, '=========')
									avatar.value = url.data.path
									uni.hideLoading();
								}
							});
						}
					});
				}
			}
		})
	};
	// 微信头像能力
	const onChooseAvatar = (e) => {
		uni.showLoading({
			title: '上传中...'
		});
		uni.uploadFile({
			// url: config.APIHOST1 + '/alioss/upload', //仅为示例，非真实的接口地址
			url: config.APIHOST + '/file/upload', //仅为示例，非真实的接口地址
			filePath: e.detail.avatarUrl,
			header: {
				token: uni.getStorageSync('token')
			},
			name: 'file',
			success: uploadFileRes => {
				let url = JSON.parse(uploadFileRes.data).data.path;
				avatar.value = url
				uni.hideLoading();
				// proxy.$http.postJson('/app/user/update', {
				// 	avatar: url,
				// }).then(res => {
				// 	uni.hideLoading();
				// 	if (res.code === 0) {
				// 		uni.showToast({
				// 			title: '更新成功',
				// 			icon: 'none'
				// 		})
				// 		getUserInfo()
				// 	} else {
				// 		uni.showToast({
				// 			title: res.msg,
				// 			icon: 'none'
				// 		})
				// 	}
				// });
			}
		});
	};
</script>

<style>
	page {
		background-color: #ffffff;
	}

	button {
		padding: 0 !important;
	}

	button::after {
		border: none;
	}

	.usermain {
		background: #ffffff;
	}

	.usermain-item {
		display: flex;
		align-items: center;
		margin: 0 40rpx;
		padding: 10rpx 0;
		justify-content: space-between;
		border-bottom: 1rpx solid #e5e5e5;
	}

	.usermain-item.item-padding {
		padding: 30rpx 0;
	}

	.cu-form-group {
		padding: 0;
		background: #ffffff;
		text-align: right;
	}

	.cu-form-group input {
		background: #ffffff;
		font-size: 28rpx;
	}

	.footer-btn {
		margin-top: 150rpx;
	}

	.footer-btn .usermain-btn {
		color: #FFFFFF;
		background: #F6317A;
		text-align: center;
		width: 686rpx;
		height: 80rpx;
		font-size: 28rpx;
		line-height: 80rpx;
		margin: 0 auto;
		border-radius: 40rpx;
	}
</style>