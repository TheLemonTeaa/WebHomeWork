<template>
  <div>
    <el-container>
        <el-header style="text-align: right; font-size: 12px;">
          <el-button icon="el-icon-edit" style="background-color: rgba(70, 76, 91, 1); color: #fff;">修改密码</el-button>
          <el-button icon="el-icon-switch-button" style="background-color: rgba(70, 76, 91, 1); color: #fff;">退出登陆</el-button>
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
              <template slot="title"><i class="el-icon-data-analysis"></i>数据统计管理</template>
              <el-menu-item-group>
                <el-menu-item index="graph">员工信息统计</el-menu-item>
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