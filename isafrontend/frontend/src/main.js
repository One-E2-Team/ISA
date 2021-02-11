import Vue from 'vue'
import App from './App.vue'
import router from './router'
import vSelect from 'vue-select'
import Calendar from 'v-calendar/lib/components/calendar.umd'
import DatePicker from 'v-calendar/lib/components/date-picker.umd'
import QrcodeCapture from "vue-qrcode-reader";

Vue.use(QrcodeCapture);
Vue.component('v-select', vSelect)
Vue.component('calendar', Calendar)
Vue.component('date-picker', DatePicker)
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
