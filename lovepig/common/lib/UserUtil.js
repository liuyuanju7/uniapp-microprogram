import store from '@/store/index.js' //需要引入store

function isLogin() {
	if (!store.getters.getIsLogin) {
		// this.$refs.uToast.show({
		// 	title: '请登录后操作',
		// 	type: 'warning',
		// 	url: '/pages/user/index'
		// })
		uni.showToast({
			title: '请登录后操作',
			duration: 1500,
			icon: 'none',
			success() {
				setTimeout(function() {
					uni.navigateTo({
						url: '/pages/user/login'
					});
				}, 1400);
			}
		});
		return false;
	} else {
		return true;
	}
};
function toLogin() {
	uni.navigateTo({
		url: '/pages/loveStart/loveStart'
	});
};
function test() {
	console.info("2222222test")
};
export default {
	isLogin,
	toLogin,test
}
