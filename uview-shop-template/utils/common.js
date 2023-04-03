module.exports = {
	apiServer: 'http://mk.golvyou.cn/api/v1.', //正式
	hostUrl: 'http://mk.golvyou.cn/', //正式
	orderType:0,
	//路由跳转
	open: function(url) {
		uni.navigateTo({
			url: url
		})
	},
	//缓存
	value: function(name, value = "") {
		if (value == "") {
			return uni.getStorageSync(name)
		} else {
			return uni.setStorageSync(name, value)
		}
	},
	//删除url参数
	urlDelP: function(url, name) {
		var urlArr = url.split('?');
		if (urlArr.length > 1 && urlArr[1].indexOf(name) > -1) {
			var query = urlArr[1];
			var obj = {}
			var arr = query.split("&");
			for (var i = 0; i < arr.length; i++) {
				arr[i] = arr[i].split("=");
				obj[arr[i][0]] = arr[i][1];
			};
			delete obj[name];
			var urlte = urlArr[0] + '?' + JSON.stringify(obj).replace(/[\"\{\}]/g, "").replace(/\:/g, "=")
				.replace(/\,/g, "&");
			return urlte;
		} else {
			return url;
		};
	},
	//日期增加天数
	dateAdd: function(dateStr, dayCount) {
		var tempDate = new Date(dateStr.replace(/-/g, "/")); //把日期字符串转换成日期格式
		var resultDate = new Date((tempDate / 1000 + (86400 * dayCount)) * 1000); //增加n天后的日期
		var resultDateStr = resultDate.getFullYear() + "-" + (resultDate.getMonth() + 1) + "-" + (resultDate
			.getDate()); //将日期转化为字符串格式
		return resultDateStr;
	},
	// uni请求封装
	post: function(url, data = {}, callback, title = "", header = {}) {
		if (title != "") {
			uni.showLoading({
				title: title
			});
			
		}
		console.log(data.token = uni.getStorageSync('user'));
		if(uni.getStorageSync('user')){
			data.token = uni.getStorageSync('user').key;
		}else{
			data.token = '';
		}
		
		uni.request({
			url: this.apiServer + url, //仅为示例，并非真实接口地址。
			method: "POST",
			data: data,
			header: {
				'content-type': 'application/x-www-form-urlencoded',
			},

			success: (res) => {
				if (res.statusCode = 200) {
					if (res.data.code == 110) {
						uni.showToast({
							title: res.data.msg,
							icon: 'none'
						})
						uni.setStorageSync('user', null);
						console.log('重启');
						setTimeout(function() {
                       
						   
						   uni.reLaunch({
						   	url:'/pages/index/index.vue'
						   });
						}, 1000)

					}
					
					if (res.data.code == 111) {
						uni.showToast({
							title: res.data.msg,
							icon: 'none'
						})
						uni.setStorageSync('user', null);
						console.log('重启1');
						setTimeout(function() {
					   
						   // uni.switchTab({
						   // 	url:'/pages/index/index'
						   // })
						   uni.reLaunch({
						   	url:'/pages/index/index'
						   });
						}, 1000)
					     return;
					}
					return callback(res.data)
				} else {
					uni.showModal({
						content: res.errMsg
					})
				}

			},
			fail: (res) => {
				uni.showModal({
					content: res.errMsg
				})
			},
			complete() {
				setTimeout(function() {
					uni.hideLoading();
				}, 1000);
			}
		});
	},

	
	toast: function(title) {
		uni.showToast({
			icon: 'none',
			title: title,
			duration: 2000
		})
	},
	modal: function(title, content, callback, type = true) {
		uni.showModal({
			title: title,
			content: content,
			showCancel: type,
			success: function(res) {
				if (res.confirm) {
					return callback(true);
				} else if (res.cancel) {
					console.log('用户点击取消');
					return callback(false);
				}
			}
		});
	},
	isLogin: function(callback = "") {

		var token = uni.getStorageSync('token')
		if (token) {
			if (callback) {
				return callback(true);
			}


		} else {
			uni.redirectTo({
				url: '/pages/common/login/login.vue'
			})
		}

	},
	wxLogin: function(callback = "") {
		
		var that = this;
		uni.login({
			provider: 'weixin',
			success: function(loginRes) {
				console.log(loginRes);
				if (loginRes.errMsg == "login:ok") {
					that.http('wxlogin/login', {
						code: loginRes.code
					}, function(res) {
						// console.log(res);
						if (res.code !== 200) {
							if (res.code == 110) {
								uni.showToast({
									title: res.data.msg,
									icon: 'none'
								})
								
								this.value('user',res.data);
							}
							if (res.code == 222) {
						
								var openid = res.data;
								uni.showToast({
									title: res.msg,
									icon: 'none'
								})
								wx.showModal({
									title: '温馨提示',
									content: '亲，授权微信登录后才能正常使用小程序功能',
									success(res) {
										console.log(res)
										//如果用户点击了确定按钮
										if (res.confirm) {
											wx.getUserProfile({
												desc: '用于快捷注册', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
												success: (res) => {

													// console.log(res
													// 	.userInfo)
													var userInfo = res
														.userInfo;
													userInfo.openid =openid;
													userInfo.key = that.value('key');
													if (res.userInfo) {
														that.http(
															'wxlogin/reg',
															userInfo,
															callback
														);
													} else {
														wx.showToast({
															title: '获取微信用户信息失败',
															icon: 'error',
															duration: 2000
														});
													}
												},
												fail(res) {
													console.log(res)
												}
											})
										} else if (res.cancel) {
											//如果用户点击了取消按钮
											wx.showToast({
												title: '您拒绝了请求,不能正常使用小程序',
												icon: 'error',
												duration: 2000
											});
											return;
										}
									}
								})
							}

						} else {
							that.value('user',res.data);
							return callback(res);
						}

					});
			 }
			}
		});
	},
	wxOpenid: function() {
		var that = this;
		uni.login({
			provider: 'weixin',
			success: function(loginRes) {
				console.log(loginRes);
				if (loginRes.errMsg == "login:ok") {
					console.log(that.apiServer + 'wxlogin/getOpenid');

					uni.request({
						url: that.apiServer + 'wxlogin/getOpenid', //仅为示例，并非真实接口地址。
						method: "POST",
						data: {
							code: loginRes.code
						},
						header: {
							'content-type': 'application/x-www-form-urlencoded',
						},
						success: (res) => {
							console.log(res)
							if (res.statusCode = 200) {
								if (res.data.code == 110) {
									uni.showToast({
										title: res.data.msg,
										icon: 'none'
									})

								}
								return callback(res.data)
							} else {
								uni.showModal({
									content: res.errMsg
								})
							}

						},
						fail: (res) => {
							uni.showModal({
								content: res.errMsg
							})
						},
						complete() {
							setTimeout(function() {
								uni.hideLoading();
							}, 1000);
						}
					});
				}
			}
		});
	},
	share(){
		console.log(11);
		uni.share({
		    provider: 'weixin',
		    scene: "WXSceneSession",
		    type: 5,
		    imageUrl: 'https://bjetxgzv.cdn.bspapp.com/VKCEYUGU-uni-app-doc/962fc340-4f2c-11eb-bdc1-8bd33eb6adaa.png',
		    title: '欢迎体验uniapp',
		    miniProgram: {
		        id: 'gh_abcdefg',
		        path: 'pages/index/index',
		        type: 0,
		        webUrl: 'http://uniapp.dcloud.io'
		    },
		    success: ret => {
		        console.log(JSON.stringify(ret));
		    }
		});
	},
	//内部请求
	http(url, data, callback) {

		uni.request({
			url: this.apiServer + url, //仅为示例，并非真实接口地址。
			method: "POST",
			data: data,
			header: {
				'content-type': 'application/x-www-form-urlencoded',
			},
			success: (res) => {
				console.log(res)
				if (res.statusCode = 200) {
					if (res.data.code !== 200) {
						uni.showToast({
							title: res.data.msg,
							icon: 'none'
						})

					}
					return callback(res.data)
				} else {
					uni.showModal({
						content: res.errMsg
					})
				}

			},
			fail: (res) => {
				uni.showModal({
					content: res.errMsg
				})
			},
			complete() {
				setTimeout(function() {
					uni.hideLoading();
				}, 1000);
			}
		});

	},
	getWxInfo() {

		var _this = this
		wx.showModal({
			title: '温馨提示',
			content: '亲，授权微信登录后才能正常使用小程序功能',
			success(res) {
				console.log(res)
				//如果用户点击了确定按钮
				if (res.confirm) {
					wx.getUserProfile({
						desc: '展示用户信息', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
						success: (res) => {
							console.log(res)
							this.setData({
								userInfo: res.userInfo,
								hasUserInfo: true
							})
						},
						fail(res) {
							console.log(res)
						}
					})
				} else if (res.cancel) {
					//如果用户点击了取消按钮
					wx.showToast({
						title: '您拒绝了请求,不能正常使用小程序',
						icon: 'error',
						duration: 2000
					});
					return;
				}
			}
		})

	},
	//微信支付
	wxpay(data,callback=''){
		uni.showLoading({
			title: '正在支付'
		});
		
		// 仅作为示例，非真实参数信息。
		uni.requestPayment({
		    provider: 'wxpay',
			timeStamp: data.timeStamp,
			nonceStr: data.nonceStr,
			package: data.package,
			signType: 'MD5',
			paySign: data.paySign,
			success: function (res) {
				uni.hideLoading();
			    uni.showToast({
			    	title:"支付完成",
					icon:'none'
			    })
				console.log('success:' + JSON.stringify(res));
				return callback(1);
			},
			fail: function (err) {
				uni.hideLoading();
				// uni.showModal({
				// 	content:JSON.stringify(err),
				// 	showCancel:false,
				// 	success() {
						
				// 	}
				// })
				uni.showToast({
					title:"支付完成",
					icon:'none'
				})
				console.log('fail:' + JSON.stringify(err));
			}
		});
	}
	
}
