<template>
    <div class="form-container">
        <el-form @submit.native.prevent="updatePassword" class="update-password-form">
            <el-form-item label="旧密码:">
                <el-input v-model="oldPassword" type="password" required></el-input>
            </el-form-item>
            <el-form-item label="新密码:">
                <el-input v-model="newPassword" type="password" required></el-input>
            </el-form-item>
            <el-row>
                <el-col :span="12">
                    <el-form-item>
                        <el-button @click="updatePassword()" type="primary" style="width: 100px;">更新密码</el-button>
                    </el-form-item>
                </el-col>
                <el-col :span="5" :push="4">
                    <el-form-item>
                        <el-button @click="cancel()" type="default" style="width: 100px;">取消</el-button>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
    </div>
</template>

<script>
import axios from 'axios';
export default {
    data() {
        return {
            userDate:[
                {
                    id: '',
                    password: ''
                }
            ],
            oldPassword: '',
            newPassword: ''
        };
    },
    methods: {
        updatePassword() {
            if(this.oldPassword === '' || this.newPassword === ''){
                this.$message.error("密码不能为空!");
                return;
            }
            else if(this.oldPassword != this.userDate.password){
                this.$message.error("旧密码错误!")
                return;
            }
            else if(this.oldPassword === this.newPassword){
                this.$message.error("新密码不能与旧密码相同!");
                return;
            }
            this.userDate.password = this.newPassword;
            axios.put("/api/updatePassword", this.userDate).then(res => {
                if (res.data.code === 1) {
                    this.$message.success("密码更新成功，请重新登录!");
                    this.$router.push('/login');
                    localStorage.removeItem('token');
                } else {
                    this.$message.error("密码更新失败，请重试!");
                }
            }).catch(() => {
                this.$message.error("密码更新失败，请重试!")
            })
        },
        cancel() {
            this.$router.push('/');
            this.userDate = [];
            this.oldPassword = '';
            this.newPassword = '';
        }
    },
    mounted() {
        axios.get("/api/updatePassword",{
            params:{
                token: localStorage.getItem('token')
            }
        }).then(res => {
            if (res.data.code === 1) {
                this.userDate = res.data.data;
            } else {
                // 如果token无效，跳转到登录页面
                this.$router.push('/login');
                this.$message.info("请先登录!")
            }
        }).catch(() => {
            this.$message.error("获取登陆信息失败，请重试!")
        })
    },
};
</script>

<style scoped>
.form-container {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

.update-password-form {
    width: 300px;
    padding: 50px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    background: #fff;
    border-radius: 5px;
}
</style>