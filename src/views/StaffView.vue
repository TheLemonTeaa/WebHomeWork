<template>
  <div>
    <span style="font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">员工管理</span><br/><br/><br/>
    <el-form :inline="true" :model="searchForm" class="demo-form-inline">
      <el-form-item label="姓名">
        <el-input v-model="searchForm.name" placeholder="请输入员工姓名"></el-input>
      </el-form-item>
      <el-form-item label="性别">
        <el-select v-model="searchForm.gender" placeholder="请选择">
          <el-option label="全部" value=""></el-option>
          <el-option label="男" value="1"></el-option>
          <el-option label="女" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="入职日期">
        <el-date-picker
          unlink-panels
          value-format="yyyy-MM-dd"
          v-model="searchForm.entrydate"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>        
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">查询</el-button>
      </el-form-item>
    </el-form>
    <p>
      <el-button type="primary" @click="dialogAddVisible = true">+添加员工</el-button>
      <el-button type="primary" @click="delMutiple()">-批量删除</el-button>
    </p>
    <el-dialog :visible.sync="dialogAddVisible" center>
          <span style="font-size: large;font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">添加员工</span><br/><br/><br/>
          <div>
          <el-form :model="form">
            <el-form-item label="用户名">&nbsp;&nbsp;&nbsp;
              <el-input v-model="saveData.username" placeholder="请输入用户名, 2-20字符, 不可重复" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="员工姓名">
              <el-input v-model="saveData.name" placeholder="请输入员工姓名, 2-10个字" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="性别">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-select v-model="saveData.gender" placeholder="请选择" style="width: 50%;">
                <el-option label="男" value=1></el-option>
                <el-option label="女" value=2></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <span id="text">图像</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-upload
                id="upload"
                class="avatar-uploader"
                action="/api/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccessA"
                :before-upload="beforeAvatarUpload">
                <img v-if="saveData.image" :src="saveData.image" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="职位">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-select v-model="saveData.job" placeholder="请选择">
                <el-option label="班主任" value=1></el-option>
                <el-option label="讲师" value=2></el-option>
                <el-option label="学工主管" value=3></el-option>
                <el-option label="教研主管" value=4></el-option>
                <el-option label="咨询师" value=5></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="入职日期">
              <el-date-picker value-format="yyyy-MM-dd" v-model="saveData.entrydate" placeholder="请选择入职日期" style="width: 50%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="归属部门">
              <el-select v-model="saveData.dept_id" placeholder="请选择">
                <el-option 
                  v-for = "dept in deptData" 
                  :key="dept.id"
                  :label="dept.name"
                  :value="dept.id"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="add()">确 定</el-button>
            <el-button @click="cancel()">取 消</el-button>
          </span>
        </el-dialog>
    <el-table 
      :data="tableData"
      :header-cell-style="cellStyle"
      :cell-style="cellStyle"
      tooltip-effect="dark"
      ref="mutipleTable"
      @selection-change="handleSelectionChange"
      border>
      <el-table-column type="selection" width="62px"></el-table-column>
      <el-table-column prop="name" label="姓名" width="120px"></el-table-column>
      <el-table-column prop="image" label="图像" width="180px">
        <template slot-scope="scope">
          <img :src="scope.row.image" width="100" height="100">
        </template>
      </el-table-column>
      <el-table-column prop="gender" label="性别" width="140px">
        <template slot-scope="scope">
          {{ scope.row.gender == 1 ? "男" : "女" }}
        </template>
      </el-table-column>
      <el-table-column prop="job" label="职位" width="140px">
        <template slot-scope="scope">
          {{ jobHandle(scope.row.job) }}
        </template>
      </el-table-column>
      <el-table-column prop="entrydate" label="入职日期" width="180px"></el-table-column>
      <el-table-column prop="update_time" label="最后操作时间" width="230px"></el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button type="text" @click="edit(scope.row.id)">编辑</el-button>
          <el-button type="text" @click="del(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialogEditVisible" center>
          <span style="font-size: large;font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">修改员工</span><br/><br/><br/>
          <div>
          <el-form :model="form">
            <el-form-item label="用户名">&nbsp;&nbsp;&nbsp;
              <el-input v-model="updateData.username" placeholder="请输入用户名, 2-20字符, 不可重复" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="员工姓名">
              <el-input v-model="updateData.name" placeholder="请输入员工姓名, 2-10个字" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="性别">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-select v-model="updateData.gender" placeholder="请选择" style="width: 50%;">
                <el-option label="男" value=1></el-option>
                <el-option label="女" value=2></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <span id="text">图像</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-upload
                id="upload"
                class="avatar-uploader"
                action="/api/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccessB"
                :before-upload="beforeAvatarUpload">
                <img v-if="updateData.image" :src="updateData.image" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
            <el-form-item label="职位">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-select v-model="updateData.job" placeholder="请选择">
                <el-option label="班主任" value="1"></el-option>
                <el-option label="讲师" value="2"></el-option>
                <el-option label="学工主管" value="3"></el-option>
                <el-option label="教研主管" value="4"></el-option>
                <el-option label="咨询师" value="5"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="入职日期">
              <el-date-picker  value-format="yyyy-MM-dd" v-model="updateData.entrydate" placeholder="请选择入职日期" style="width: 50%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="归属部门">
              <el-select v-model="updateData.dept_id" placeholder="请选择">
                <el-option 
                  v-for = "dept in deptData" 
                  :key="dept.id"
                  :label="dept.name"
                  :value="dept.id"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          </div>
          <span slot="footer" class="dialog-footer">
            <el-button type="primary" @click="update()">确 定</el-button>
            <el-button @click="cancel()">取 消</el-button>
          </span>
        </el-dialog>
    <div class="block">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :page-sizes="[10, 20, 50, 100]"
        :page-size="pageSize"
        :current-page="page"
        layout="sizes, ->, total, prev, pager, next, jumper"
        :total=total>
      </el-pagination>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data(){
    return{
      tableData:[],
      page:'',
      pageSize:'',
      tatal:'',
      searchForm:{
        name:'',
        gender:'',
        entrydate:[]
      },
      saveData:{
        username:'',
        name:'',
        gender:'',
        dept_id:'',
        job:'',
        image:'',
        entrydate:null
      },
      updateData:{
        id:'',
        username:'',
        name:'',
        gender:'',
        dept_id:'',
        job:'',
        image:'',
        entrydate:null
      },
      deptData:[],
      mutipleSelection:[],
      dialogAddVisible: false,
      dialogEditVisible: false
    }
  },
  methods: {
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.pageSize = val;
        this.page = "1";
        axios.get("/api/emps",{
          params:{
            page:this.page,
            pageSize:this.pageSize
          }
        }).then((result) => {
          this.tableData=result.data.data.result;
        })
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.page = val;
        axios.get("/api/emps",{
          params:{
            page:this.page,
            pageSize:this.pageSize
          }
        }).then((result) => {
          this.tableData=result.data.data.result;
        })
      },
      cellStyle(){
        return 'text-align:center'
      },
      add(){
        const emptyReg = /^$/;
        const nameReg = /^[\u4e00-\u9fa5]{2,10}$/;
        const usernameReg = /^.{2,20}$/;
        if(emptyReg.test(this.saveData.username)) {
          this.$message.error("用户名不能为空!");
          return;
        }
        else if(!usernameReg.test(this.saveData.username)){
          this.$message.error("用户名输入不正确!");
          return;
        }
        else if(emptyReg.test(this.saveData.name)) {
          this.$message.error("员工姓名不能为空!");
          return;
        }
        else if(!nameReg.test(this.saveData.name)){
          this.$message.error("员工姓名输入不正确!");
          return;
        }
        else if(emptyReg.test(this.saveData.gender)){
          this.$message.error("请选择性别!")
          return;
        }
        axios.post("/api/emps",this.saveData).then(() => {
          this.dialogAddVisible = false;
          axios.get("/api/emps",{
            params:{
              page:this.page,
              pageSize:this.pageSize
            }
          }).then((result) => {
            this.tableData=result.data.data.result;
            this.total=result.data.data.total
          })
          this.saveData={
            username:'',
            name:'',
            gender:'',
            dept_id:'',
            job:'',
            image:'',
            entrydate:null
          }
          this.$message.success("添加成功!")
        }).catch(() => {
          this.$message.error("添加失败!")
        })
      },
      edit(id) {
        axios.get("/api/emps/" + id).then((result) => {
          this.updateData = result.data.data;
          this.updateData.job = this.jobHandle(result.data.data.job)
          this.updateData.gender = result.data.data.gender == 1 ? "男" : "女"
          this.updateData.dept_id = this.deptHandle(result.data.data.dept_id)
          this.dialogEditVisible = true;
        }).catch(() => {
          this.$message.error("获取员工信息失败!")
        })
      },
      update() {
        const emptyReg = /^$/;
        const nameReg = /^[\u4e00-\u9fa5]{2,10}$/;
        const usernameReg = /^.{2,20}$/;
        if(emptyReg.test(this.updateData.username)) {
          this.$message.error("用户名不能为空!");
          return;
        }
        else if(!usernameReg.test(this.updateData.username)){
          this.$message.error("用户名输入不正确!");
          return;
        }
        else if(emptyReg.test(this.updateData.name)) {
          this.$message.error("员工姓名不能为空!");
          return;
        }
        else if(!nameReg.test(this.updateData.name)){
          this.$message.error("员工姓名输入不正确!");
          return;
        }
        else if(emptyReg.test(this.updateData.gender)){
          this.$message.error("请选择性别!")
          return;
        }
        this.toGender()
        this.toDept()
        this.toJob()
        axios.put("/api/emps",this.updateData).then(() => {
          this.$message({
            type:'success',
            message:'保存成功！'
          });
          this.dialogEditVisible = false;
          axios.get("/api/emps",{
            params:{
              page:this.page,
              pageSize:this.pageSize
            }
          }).then((result) => {
            this.tableData=result.data.data.result;
          })
          this.updateData={
            username:'',
            name:'',
            gender:'',
            dept_id:'',
            job:'',
            image:'',
            entrydate:null
          }
        }).catch(() => {
          this.$message.error("编辑失败!")
        })
      },
      cancel() {
        if(this.dialogEditVisible == true) {
          this.dialogEditVisible = false;
          this.updateData={
          username:'',
          name:'',
          gender:'',
          dept_id:'',
          job:'',
          image:'',
          entrydate:null
          }
        }
        else if(this.dialogAddVisible == true) {
          this.dialogAddVisible = false;
          this.saveData={
            username:'',
            name:'',
            gender:'',
            dept_id:'',
            job:'',
            image:'',
            entrydate:null
          }
        }
      },
      handleSelectionChange(selection) {
        this.mutipleSelection=[]
        selection.forEach(element => {
          this.mutipleSelection.push(element.id);
        });
      },
      delMutiple() {
        const apiUrl = "/api/emps/"
        console.log(apiUrl + this.mutipleSelection)
        axios.delete(apiUrl + this.mutipleSelection).then(() => {
          axios.get("/api/emps",{
            params:{
              page:this.page,
              pageSize:this.pageSize
            }
          }).then((result) => {
            this.tableData=result.data.data.result;
          })
          this.$message.success("删除成功！");
        }).catch(() => {
          this.$message.error("删除失败！");
        })
      },
      onSubmit() {
        if(this.searchForm.entrydate == null) {
          axios.get("/api/emps",{
          params:{
            page:this.page,
            pageSize:this.pageSize,
            name:this.searchForm.name,
            gender:this.searchForm.gender,
          }
        }).then((result) => {
          this.tableData=result.data.data.result;
        })
        }
        else axios.get("/api/emps",{
          params:{
            page:this.page,
            pageSize:this.pageSize,
            name:this.searchForm.name,
            gender:this.searchForm.gender,
            begin:this.searchForm.entrydate[0],
            end:this.searchForm.entrydate[1]
          }
        }).then((result) => {
          this.tableData=result.data.data.result;
        })
      },
      jobHandle(job) {
        if(job == "1") return "班主任";
        else if(job == "2") return "讲师";
        else if(job == "3") return "学工主管";
        else if(job == "4") return "教研主管";
        else if(job == "5") return "咨询师"
      },
      toJob() {
        if(this.updateData.job == "班主任") this.updateData.job = "1";
        else if(this.updateData.job == "讲师") this.updateData.job = "2";
        else if(this.updateData.job == "学工主管") this.updateData.job = "3";
        else if(this.updateData.job == "教研主管") this.updateData.job = "4";
        else if(this.updateData.job == "咨询师") this.updateData.job = "5";
      },
      deptHandle(dept) {
        if(dept != null){
          const department = this.deptData.find(dep => dep.id == dept);
          return department ? department.name : '';
        }
        return '';
      },
      toDept() {
        if(this.updateData.dept_id != null) {
          const deptId = this.deptData.find(dep => dep.name == this.updateData.dept_id);
          if(deptId) this.updateData.dept_id = deptId;
        }
      },
      toGender() {
        if(this.updateData.gender == "男") this.updateData.gender = "1";
        else if(this.updateData.gender == "女") this.updateData.gender = "2"
      },
      del(id){
        this.$confirm('您确定要删除该员工吗?', '删除员工', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.delete("/api/emps/" + id).then(() => {
            axios.get("/api/emps",{
              params:{
                page:this.page,
                pageSize:this.pageSize
              }
            }).then((result) => {
              this.tableData=result.data.data.result;
              this.total=result.data.data.total
            })
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
          }).catch(() => {
            this.$message.error("删除失败!");
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
      },
      handleAvatarSuccessA(res) {
        this.saveData.image=res.data;
      },
      handleAvatarSuccessB(res) {
        this.updateData.image=res.data;
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
      }
    },
  mounted (){
    axios.get("/api/emps",{
          params:{
            page:this.page,
            pageSize:this.pageSize
          }
        }).then((result) => {
          this.tableData=result.data.data.result;
          this.total=result.data.data.total
        })
    axios.get("/api/depts").then(result =>{
      this.deptData = result.data.data;
    })
  }
}
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position:relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 60px;
    height: 60px;
    line-height: 60px;
    text-align: center;
  }
  .avatar {
    width: 100px;
    height: 100px;
    display: block;
  }
  #text, #upload{
    display: inline-block;
  }
</style>