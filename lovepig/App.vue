<script>
	import store from '@/store/index.js';
	import {
		mapGetters,
		mapMutations
	} from 'vuex';
	export default {
		onLaunch: async function() {
			// console.log(uni.$u.config.v);
			let token = uni.getStorageSync('token');
			let loginData = uni.getStorageSync('userInfo');
			if (token && loginData) {
				console.info('查找到用户信息直接登录');
				store.commit('modifyToken', token);
				store.dispatch('updateLoginUser', loginData);
				this.navigatePath(loginData);
			} else {
				console.info('用户没有登录');

				/*#ifdef H5*/
				this.h5DevLogin();
				/*#endif*/

				/*#ifdef MP*/
				uni.reLaunch({
					url: '/pages/loveStart/loveStart'
				});
				/*#endif*/

			}
		},
		onShow: function() {
			// console.log('App Show')
		},
		onHide: function() {
			// console.log('App Hide')
		},
		computed: {
			...mapGetters(['getLoginUser'])
		},
		methods: {
			wxlogin(userInfo) {
				let that = this;
				uni.login({
					provider: 'weixin',
					success: function(res) {
						if (res.code) {
							that.wechatLogin(res.code);
						} else {
							console.log('获取登录code失败!!');
						}
					}
				});
			},
			//2.预登录-正常登录
			wechatLogin(code) {
				this.$u.api
					.wechatLogin({
						appid: this.appid,
						code: code
					})
					.then(res => {
						// console.info(res)
						if (res.newUser) {
							store.commit('modifyIsNewUser', true);
							console.info('该用户是新用户需要获取信息登录');
						} else {
							//把token放到缓存中
							store.commit('modifyToken', res.token);
							store.commit('modifyLoginUser', res.loginUser);
							// this.navigatePath();
						}
					});
			},
			navigatePath(loginData) {
				// console.info(loginData);
				if (loginData.loveId === 0) {
					console.info('如果没有创建空间跳转到loveStart');
					uni.reLaunch({
						url: '/pages/loveStart/loveStart'
					});
				} else if (loginData.spouseId === 0) {
					console.info('如果已经创建跳转到邀请页面');
					uni.reLaunch({
						url: '/pages/loveStart/sendLove'
					});
				}
			},
			h5DevLogin() {
				if (process.env.NODE_ENV === 'development') {
					let tokentemp =
						"wx eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyVVVJRCI6ImU1NzUzMzUwLTZhNmQtNGQyMi05NmRmLTFiNzBjMjVlYWYzOCJ9.1muLZfeQKsax3-pKASKYmtk2m_S9ZUxXjNYGJCUd43I";
					let usertemp =
						'{"id":25,"username":"pig_xy79e91ntf","gender":0,"nickname":"joke","avatar":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLLnsXvmY93UaEB9M1JIaNNjdI243VX5v5ekpuiaNzBDTzJKL4KGLoLR4nKkwf39v2XChWD0JMc0eA/132","signature":"","city":"","loveId":536,"spouseId":255,"state":0,"createTime":1648290437000,"modifyTime":1653230616000}';
					console.info('h5开发者登录');
					uni.setStorageSync('token', tokentemp);
					uni.setStorageSync('userInfo', JSON.parse(usertemp));
					store.commit('modifyToken', tokentemp);
					store.dispatch('updateLoginUser', JSON.parse(usertemp));
					this.navigatePath(JSON.parse(usertemp));
				}
			}

		}
	};
</script>

<style lang="scss">
	@import 'uview-ui/index.scss';

	/*每个页面公共css */
	::-webkit-scrollbar {
		display: none;
		width: 0 !important;
		height: 0 !important;
		color: transparent !important;
	}

	scroll-view ::-webkit-scrollbar {
		display: none;
		width: 0;
		height: 0;
		color: transparent;
	}
</style>