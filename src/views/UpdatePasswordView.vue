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
                        <el-button type="primary" native-type="submit" style="width: 100px;">更新密码</el-button>
                    </el-form-item>
                </el-col>
                <el-col :span="5" :push="4">
                    <el-form-item>
                        <el-button @click="cancel()" type="default" native-type="reset" style="width: 100px;">取消</el-button>
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
            oldPassword: '',
            newPassword: '',
        };
    },
    methods: {
        updatePassword() {
            // 在这里添加密码更新的逻辑
            // 例如，你可以调用一个API来更新密码
        },
        cancel() {
            this.$router.push('/');
        }
    },
    mounted() {
        // 在这里添加mounted逻辑
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