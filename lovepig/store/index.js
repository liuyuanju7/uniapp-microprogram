import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

import user from '@/store/modules/user.js'
import loveStart from '@/store/modules/loveStart.js'
import message from '@/store/modules/message.js'

const store = new Vuex.Store({
	//定义全局的属性
	state: {
		loveStartId: 0
	},
	//获取全局属性state,也可以使用mapState获取属性
	getters: {
		getLoveStartId: state => {
			return state.loveStartId
		},
	},
	//修改全局state数量
	mutations: {
		modifyLoveStartId(state, payload) {
			state.loveStartId = payload;
		}
	},

	//模块缓存，拥有自己的state和mutations等
	modules: {
		user,
		message,
		loveStart
	}
})

export default store
