<template>
  <div class="login">
    <div class="login-content">
      <img src="../assets/login.png" class="login-image">
      <el-form @submit.prevent="login" class="login-form">
        <el-row>
          <el-col :span="24" :push="2">
            <h1>培训管理系统</h1>
          </el-col>
        </el-row>
        <el-row style="align-items: center;">
            <el-col :span="6">
              <el-form-item style="display: flex; flex-direction: row;">
                <strong style="font-weight: bolder;">用户名:</strong>
              </el-form-item>
            </el-col>
          <el-col :span="18">
            <el-input v-model="loginForm.username" placeholder="Username" class="input-login"/>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item style="display: flex; flex-direction: row;">
              <strong style="font-weight: bolder;">密码:</strong>
            </el-form-item>
          </el-col>
          <el-col :span="18">
            <el-input type="password" v-model="loginForm.password" placeholder="Password" class="input-login"/>
          </el-col>
        </el-row>
        <el-button type="primary" @click="login()">Login</el-button>
      </el-form>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data() {
    return {
      loginForm: {
        username: '',
        password: ''
      }
    }
  },
  methods: {
    login() {
      axios.post('/api/login',this.loginForm).then(res => {
        if (res.data.code === 1) {
          const token = res.data.data;
          // 将token存储到localStorage
          localStorage.setItem('token', token);
          // 添加请求拦截器
          axios.interceptors.request.use(config => {
            // 在发送请求之前做些什么
            config.headers['token'] = localStorage.getItem('token');
            return config;
          }, error => {
            // 对请求错误做些什么
            return Promise.reject(error);
          });
          this.$router.push('/')
        } else {
          this.$message.error('用户名或密码错误！')
        }
      })
    }
  }
}
</script>

<style>
.login {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.login-content {
  display: flex;
  align-items: center;
}

.login-image {
  margin-right: 50px;
}

.login-form {
  display: flex;
  width: 50%;
  flex-direction: column;
}

</style>