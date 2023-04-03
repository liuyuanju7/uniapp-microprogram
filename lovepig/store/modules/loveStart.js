export default {
	state: {
		loveStartInfo: {
			"loveId": 536,
			"loveName": "爱猪猪",
			"loveMessage": "我的小猪姑娘快来",
			"inviteUser": 25,
			"acceptedUser": 255,
			"inviteTime": 1650802829000,
			"acceptedTime": 1650802982000,
			"loveTime": 1539388800000,
			"separatedTime": null,
			"allowAccess": 1,
			"loveState": 1,
			"inviteUserInfo": {
				"id": 25,
				"username": "pig_xy79e91ntf",
				"gender": 0,
				"nickname": "joke",
				"avatar": "https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLLnsXvmY93UaEB9M1JIaNNjdI243VX5v5ekpuiaNzBDTzJKL4KGLoLR4nKkwf39v2XChWD0JMc0eA/132",
				"signature": "",
				"city": "",
				"loveId": 536,
				"spouseId": 255,
				"state": 0,
				"createTime": 1648290437000,
				"modifyTime": 1653230616000
			},
			"acceptedUserInfo": {
				"id": 255,
				"username": "pig_9lh2o13kuo",
				"gender": 0,
				"nickname": "Moon\uD83D\uDC2F",
				"avatar": "https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTK3dIMexufib0XVZ0Hg8iaXfxSlp1ZqU01AvfAgEnjYjLnFuLKwVkwX67BhR6gDKWJC0zFIo7Gw5A2w/132",
				"signature": "",
				"city": "",
				"loveId": 536,
				"spouseId": 25,
				"state": 0,
				"createTime": 1649738126000,
				"modifyTime": 1650802982000
			},
			"loveDay": 1625,
			"banners": [{
				"resourceId": 240,
				"resourceUrl": ""
			}]
		},
		loveStartBanners:""	},
	getters: {
		getLoveStartInfo: state => {
			return state.loveStartInfo
		},
		getLoveStartBanners: state => {
			return state.loveStartBanners
		}
	},
	mutations: {
		modifyLoveStartInfo(state, payload) {
			state.loveStartInfo = payload;
			state.loveStartBanners = payload.banners[0].resourceUrl;
		}
	},
	actions: {

	}
}
