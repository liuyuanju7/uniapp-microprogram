//引入常用方法

//用户
import user from './lib/UserUtil.js'
//常量
import constData from './lib/constant.js'
import baseUtil from './lib/baseUtil.js'
import promise from './lib/promiseUtil.js'

const $pig = {
	base:baseUtil,
	user,
	promise,
	constData
}

// $u挂载到uni对象上
uni.$pig = $pig

const install = Vue => {
	// 将多久以前的方法，注入到全局过滤器
	// Vue.filter('timeFrom', (timestamp, format) => {
	// 	return timeFrom(timestamp, format)
	// })
	Vue.prototype.$pig = $pig
}

export default {
	install
}
