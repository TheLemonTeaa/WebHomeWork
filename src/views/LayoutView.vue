<template>
  <div>
    <el-container>
        <el-header style="text-align: right; font-size: 12px;">
          <img src="../assets/logo.svg" alt="Logo" style="position: absolute; left: 10px; top: -10px; height: 120px; width: auto;">
          <el-button @click="updatePassword()" icon="el-icon-edit" style="background-color: rgba(70, 76, 91, 1); color: #fff;">修改密码</el-button>
          <el-button @click="quit()" icon="el-icon-switch-button" style="background-color: rgba(70, 76, 91, 1); color: #fff;">退出登陆</el-button>
        </el-header>
      <el-container>
        <el-aside width="200px" style="background-color: #fff">
          <el-menu router unique-opened>
            <el-submenu index="1">
              <template slot="title"><i class="el-icon-user"></i>班级学员管理</template>
              <el-menu-item-group>
                <el-menu-item index="class">班级管理</el-menu-item>
                <el-menu-item index="student">学员管理</el-menu-item>
              </el-menu-item-group>
            </el-submenu>

            <el-submenu index="2">
              <template slot="title"><i class="el-icon-setting"></i>系统信息管理</template>
              <el-menu-item-group>
                <el-menu-item index="dept">部门管理</el-menu-item>
                <el-menu-item index="staff">员工管理</el-menu-item>
              </el-menu-item-group>
            </el-submenu>

            <el-submenu index="3">
              <template slot="title"><i class="el-icon-notebook-1"></i>课程教务管理</template>
              <el-menu-item-group>
                <el-menu-item index="course">课程管理</el-menu-item>
                <el-menu-item index="plan">课程安排</el-menu-item>
              </el-menu-item-group>
            </el-submenu>
            
            <el-submenu index="4">
              <template slot="title"><i class="el-icon-data-analysis"></i>数据统计管理</template>
              <el-menu-item-group>
                <el-menu-item index="empGraph">员工信息统计</el-menu-item>
              </el-menu-item-group>
              <el-menu-item-group>
                <el-menu-item index="stuGraph">学员信息统计</el-menu-item>
              </el-menu-item-group>
            </el-submenu>

          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default {
  methods: {
    updatePassword() {
      this.$router.push('/updatePassword')
    },
    quit() {
      this.$confirm('确认退出吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(() => {
        this.$message({
          type: 'success',
          message: '退出成功!'
        });
        localStorage.removeItem('token')
        this.$router.push('/login')
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消退出'
        });
      });
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
    }
    else {
      this.$router.push('/login')
    }
  },
}
</script>

<style>

  .el-header {
    background-color:rgba(70, 76, 91, 1);
    color: #ffffff;
    line-height: 60px;
  }
  
  .el-aside {
    color: #333;
  }
</style>