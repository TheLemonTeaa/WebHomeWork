<template>
  <div class="home" style="text-align: center;">
      <h2 style="font-size: 24px;">欢迎使用培训管理系统!</h2>
      <p style="font-size: 20px;">欢迎您&nbsp;&nbsp;{{ username }}!</p>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      username: ''
    }
  },
  mounted() {
    if(localStorage.getItem('token') != null) {
        axios.interceptors.request.use(config => {
            // 在发送请求之前做些什么
            config.headers['token'] = localStorage.getItem('token');
            return config;
          }, error => {
            // 对请求错误做些什么
            return Promise.reject(error);
          });
          axios.get("/api/updatePassword",{
            params:{
                token: localStorage.getItem('token')
            }
        }).then((result) => {
          if(result.data.code == 0){
            this.$message.error("请登录后操作!");
            this.$router.push('/login');
          }
          else this.username = result.data.data.username;
        })
    }
  }
}
</script>

<style>

</style>