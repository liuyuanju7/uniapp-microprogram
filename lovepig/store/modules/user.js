import Vue from 'vue'
export default {
	state: {
		token: '',
		isLogin: false,
		isNewUser: false,
		isOnwer: false,
		loginUser: {
			gender: 1,
			nickname: '未登录',
			motto: '点击快速登录~',
			avatar: 'https://lovestatic.jokeo.cn/202106/222.jpg',
		}
	},
	getters: {
		getToken: state => {
			return state.token
		},
		getLoginUser: state => {
			return state.loginUser
		},
		getLoginUserId: state => {
			return state.loginUser.id
		},
		getIsLogin: state => {
			return state.isLogin
		},
		getIsNewUser: state => {
			return state.isNewUser
		}
	},
	mutations: {
		modifyToken(state, payload) {
			state.token = payload;
			uni.setStorageSync('token', payload)
		},
		modifyLoginUser(state, payload) {
			state.isLogin = true;
			state.loginUser = payload;
			uni.setStorageSync('userInfo', payload)
		},
		modifyIsNewUser(state, payload) {
			state.isNewUser = payload;
		}
	},
	actions: {
		updateLoginUser(context, userInfo) {
			context.commit('modifyLoginUser', userInfo);
			context.commit('modifyLoveStartId', userInfo.loveId);
		},
		//实现登录和注册功能
		login(context) {
			return new Promise((resolve, reject) => {
				wx.getUserProfile({
					desc: '用于完善您的登录信息',
					success: userRes => {
						console.log(userRes);
						uni.login({
							provider: 'weixin',
							success: function(res) {
								if (res.code) {
									let param = {
										appid: Vue.prototype.appid,
										code: res.code,
										nickname: userRes.userInfo.nickName,
										avatar: userRes.userInfo.avatarUrl
									};
									Vue.prototype.$u.api.wechatRegister(param).then(
										res => {
											console.info(res);
											resolve("login执行完毕");
											context.commit('modifyToken', res
												.token);
											context.commit('modifyLoginUser', res
												.loginUser);
										});
								} else {
									Vue.prototype.$u.toast("登录失败");
									reject("获取code失败");
								}
							}
						});
					},
					fail: res => {
						Vue.prototype.$u.toast("登录失败");
						console.log(res);
						reject("获取code失败");
					}
				});

				// resolve("login执行完毕");
				// Vue.prototype.$u.toast("获取code失败");
				// reject("获取code失败");
			})
		},

		// 1.获取微信用户信息
		getWxUserInfo() {
			wx.getUserProfile({
				desc: '用于完善您的登录信息',
				success: res => {
					// console.log(res);
					this.wxlogin(res.userInfo);
				},
				fail: res => {
					console.log(res);
				}
			});
		},
		//2.获取code用于注册登录
		wxlogin(userInfo) {
			let that = this;
			uni.login({
				provider: 'weixin',
				success: function(res) {
					if (res.code) {
						that.wechatRegistered(res.code, userInfo);
					} else {
						console.log('获取登录code失败!!');
					}
				}
			});
		},
		//3.注册登录
		wechatRegistered(code, userInfo) {
			let that = this;
			let param = {
				appid: this.appid,
				code: code,
				nickname: userInfo.nickName,
				gender: userInfo.gender,
				avatar: userInfo.avatarUrl
			};
			this.$u.api.wechatRegister(param).then(res => {
				console.info(res);
				context.commit('modifyToken', res.token);
				context.commit('modifyLoginUser', res.loginUser);
			});
		},
	}
}
