<template>
  <div>
    <span style="font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">部门管理</span><br/><br/><br/>
        <p>
            <el-button type="primary" @click="dialogAddVisible = true">+新增部门</el-button>
        </p>
          <el-table 
            :data="tableData"
            :header-cell-style="cellStyle"
            :cell-style="cellStyle"
            border
            style="width: 100%">
            <el-table-column
              label="序号"
              width="62">
              <template slot-scope="scope">
                {{ scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column
              prop="name"
              label="部门名称"
              width="356"  
            >
            </el-table-column>
            <el-table-column
              prop="update_time"
              label="最后操作时间"
              width="244">
            </el-table-column>
            <el-table-column
              label="操作">
              <template slot-scope="scope">
                <el-button type="text" @click="edit(scope.row.id)">编辑</el-button>
                <el-button type="text" @click="del(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        <el-dialog :visible.sync="dialogAddVisible" center>
          <span style="font-size: large;font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">新增部门</span><br/><br/><br/>
          <div>
          <el-form>
            <el-form-item label="部门名称" style="font-weight: bold;">
              <el-input v-model="saveData.name" @input="checkDepartmentName" placeholder="请输入部门名称, 长度为2-10位" style="width: 50%;"></el-input>
            </el-form-item>
          </el-form>
          <span class="error-prompt" style="font-size: small;font-family: 'Arial Normal',Arial;color: red;">&nbsp;&nbsp;&nbsp;{{addErrorPrompt}}</span>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="add()">确 定</el-button>
            <el-button @click="dialogAddVisible = false, addErrorPrompt='', saveData.name=''">取 消</el-button>
          </span>
        </el-dialog>
        <el-dialog :visible.sync="dialogEditVisible" center>
          <span style="font-size: large;font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">编辑部门</span><br/><br/><br/>
          <div>
          <el-form>
            <el-form-item label="部门名称">
              <el-input v-model="updateData.name" @input="editDepartmentName" placeholder="请输入部门名称, 长度为2-10位" style="width: 50%;"></el-input>
            </el-form-item>
          </el-form>
          <span class="error-prompt" style="font-size: small;font-family: 'Arial Normal',Arial;color: red;">&nbsp;&nbsp;&nbsp;{{editErrorPrompt}}</span>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="save()">确 定</el-button>
            <el-button @click="dialogEditVisible = false, editErrorPrompt=''">取 消</el-button>
          </span>
        </el-dialog>
  </div>
</template>

<script>
import axios from 'axios'
export default {
    data(){
        return{
          tableData:[],
          saveData:{
            name:''
          },
          updateData:{
            id:'',
            name:''
          },
          dialogEditVisible: false,
          dialogAddVisible: false,
          addErrorPrompt:'',
          editErrorPrompt:''
        }
    },
    methods: {
        cellStyle(){
        return 'text-align:center'
      },
      checkDepartmentName() {
        const reg = /^[\u4e00-\u9fa5]{2,10}$|^$/;
        if(!reg.test(this.saveData.name)) {
          this.addErrorPrompt = "部门名称格式不正确，请输入2-10位汉字！";
        }else {this.addErrorPrompt = '';}
      },
      editDepartmentName() {
        const reg = /^[\u4e00-\u9fa5]{2,10}$|^$/;
        if(!reg.test(this.updateData.name)) {
          this.editErrorPrompt = "部门名称格式不正确，请输入2-10位汉字！";
        }else {this.editErrorPrompt = '';}
      },
      add() {
        const reg = /^[\u4e00-\u9fa5]{2,10}$/;
        if(!reg.test(this.saveData.name)) {
          return;
        }
        axios.post("/api/depts",this.saveData)
        .then(() => {
          this.$message({
            type:'success',
            message:'添加成功！'
          });
          this.dialogAddVisible = false;
          axios.get("/api/depts").then((result) => {
            this.tableData = result.data.data;
          })
          this.saveData.name = '';
        }).catch(() => {
          this.$message.error('部门添加失败，请重试！');
          this.dialogAddVisible = false;
        });
      },
      edit(id) {
        this.dialogEditVisible = true;
        axios.get("/api/depts/" + id)
        .then((result) =>{
          this.updateData = result.data.data;
        }).catch(() =>{
          this.$message.error('获取部门信息失败！')
        })
      },
      save() {
        const reg = /^[\u4e00-\u9fa5]{2,10}$/;
        if(!reg.test(this.updateData.name)) {
          return;
        }
        axios.put("/api/depts",this.updateData)
        .then(() => {
          this.$message({
            type:'success',
            message:'保存成功！'
          });
          this.dialogEditVisible = false;
          axios.get("/api/depts").then((result) => {
            this.tableData = result.data.data;
          })
          this.updateData.name = '';
          this.updateData.id = ''; 
        }).catch(() => {
          this.$message.error('部门编辑失败，请重试！');
          this.dialogEditVisible = false;
        });
      },
      del(row){
        this.$confirm('您确定要删除该部门吗?', '删除部门', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.delete("/api/depts/" + row.id)
          .then(() => {
            this.$message({
            type: 'success',
            message: '删除成功！'
          });
            axios.get("/api/depts").then((result) => {
              this.tableData = result.data.data;
            })
          })
          .catch(() => {
            this.$message.error('删除失败，请重试！');
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
      },
    },
    mounted() {
      axios.get("/api/depts").then((result) => {
        this.tableData = result.data.data;
      })
    },
}
</script>

<style>

</style>