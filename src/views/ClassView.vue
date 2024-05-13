<template>
    <div>
        <span style="font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">班级管理</span><br/><br/><br/>
        <el-form :inline="true" :model="searchForm" class="demo-form-inline">
            <el-form-item label="班级名称">
                <el-input v-model="searchForm.className" placeholder="请输入班级名称"></el-input>
            </el-form-item>
            <el-form-item label="结课时间">
                <el-date-picker
                    unlink-panels
                    value-format="yyyy-MM-dd"
                    v-model="searchForm.date"
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
        <el-button type="primary" @click="dialogAddVisible = true">+新增班级</el-button>
        </p>
        <el-dialog :visible.sync="dialogAddVisible" center>
          <span style="font-size: large;font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">新增班级</span><br/><br/><br/>
          <div>
          <el-form :model="form">
            <el-form-item label="班级名称">
              <el-input v-model="saveData.className" placeholder="请输入班级名称, 如: 2024第01期10班" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="班级教室">
              <el-input v-model="saveData.classroom" placeholder="请填写班级教室" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="开课时间">
              <el-date-picker value-format="yyyy-MM-dd" v-model="saveData.startTime" placeholder="请选择开课时间" style="width: 50%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="结课时间">
              <el-date-picker value-format="yyyy-MM-dd" v-model="saveData.endTime" placeholder="请选择结课时间" style="width: 50%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="班主任">&nbsp;&nbsp;&nbsp;
              <el-select v-model="saveData.teacher" placeholder="请选择" style="width: 50%;">
                <el-option 
                  v-for="teacher in teacherData"
                  :key="teacher.id"
                  :label="teacher.name"
                  :value="teacher.id"></el-option>
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
        border>
        <el-table-column label="序号" width="62">
          <template slot-scope="scope">
            {{ scope.$index + 1 + (page - 1) * pageSize}}
          </template>
        </el-table-column>
        <el-table-column prop="className" label="班级名称" width="186"></el-table-column>
        <el-table-column prop="classroom" label="班级教室" width="127"></el-table-column>
        <el-table-column prop="startTime" label="开课时间" width="153"></el-table-column>
        <el-table-column prop="endTime" label="结课时间" width="172"></el-table-column>
        <el-table-column prop="teacher" label="班主任" width="130">
          <template slot-scope="scope">
            {{ teacherHandle(scope.row.teacher) }}
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="edit(scope.row.id)">编辑</el-button>
            <el-button type="text" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
        </el-table>
        <el-dialog :visible.sync="dialogEditVisible" center>
          <span style="font-size: large;font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">修改班级</span><br/><br/><br/>
          <div>
          <el-form :model="form">
            <el-form-item label="班级名称">
              <el-input v-model="updateData.className" placeholder="请输入班级名称, 如: 2024第01期10班" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="班级教室">
              <el-input v-model="updateData.classroom" placeholder="请填写班级教室" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="开课时间">
              <el-date-picker value-format="yyyy-MM-dd" v-model="updateData.startTime" placeholder="请选择开课时间" style="width: 50%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="结课时间">
              <el-date-picker value-format="yyyy-MM-dd" v-model="updateData.endTime" placeholder="请选择结课时间" style="width: 50%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="班主任">&nbsp;&nbsp;&nbsp;
              <el-select v-model="updateData.teacher" placeholder="请选择" style="width: 50%;">
                <el-option 
                  v-for="teacher in teacherData"
                  :key="teacher.id"
                  :label="teacher.name"
                  :value="teacher.id"></el-option>
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
      page:'1',
      pageSize:'10',
      total:'',
      searchForm:{
        className:'',
        date:[]
      },
      saveData:{
        className:'',
        classroom:'',
        startTime:'',
        endTime:'',
        teacher:''
      },
      updateData:{
        id:'',
        className:'',
        classroom:'',
        startTime:'',
        endTime:'',
        teacher:''
      },
      teacherData:[],
      dialogAddVisible: false,
      dialogEditVisible: false,
      teacher:{
        name:''
      }
    }
  },
  methods: {
      teacherHandle(id) {
        if(id != null) {
          const teacherName = this.teacherData.find(teach => teach.id == id);
          if(teacherName) return teacherName.name;
          else return "未知";
        }
      },
      toTeacher(){
        if(this.updateData.teacher != ''){
          const teacherId = this.teacherData.find(teach => teach.name == this.updateData.teacher);
          if(teacherId) this.updateData.teacher = teacherId.id;
        }
      },
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.pageSize = val;
        this.page = "1";
        axios.get("/api/class",{
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
        axios.get("/api/class",{
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
      onSubmit() {
        if(this.searchForm.date == null) {
          axios.get("/api/class",{
          params:{
            pageSize:this.pageSize,
            className:this.searchForm.className,
          }
        }).then((result) => {
          this.page = "1";
          if(result.data.code == 0) this.$message.error("请登录后操作!");
          else {
            this.tableData=result.data.data.result;
            this.total = result.data.data.total
          }
        })
        }
        else axios.get("/api/class",{
          params:{
            pageSize:this.pageSize,
            className:this.searchForm.className,
            begin:this.searchForm.date[0],
            end:this.searchForm.date[1]
          }
        }).then((result) => {
          this.page = "1";
          if(result.data.code == 0) this.$message.error("请登录后操作!");
          else {
            this.tableData=result.data.data.result;
            this.total = result.data.data.total
          }
        })
      },
      add() {
        console.log(this.teacherData)
        const emptyReg = /^$/;
        const classNameReg = /^[\u4e00-\u9fa50-9a-zA-Z]{4,30}$/;
        const classroomReg = /^[\u4e00-\u9fa50-9a-zA-Z]{1,20}$|^$/;
        if(emptyReg.test(this.saveData.className)) {
          this.$message.error("班级名称不能为空!");
          return;
        }
        else if(!classNameReg.test(this.saveData.className)){
          this.$message.error("班级名称输入不正确!");
          return;
        }
        else if(!classroomReg.test(this.saveData.classroom)){
          this.$message.error("班级教室输入不正确!");
          return;
        }
        else if(emptyReg.test(this.saveData.startTime)) {
          this.$message.error("请选择开课时间!");
          return;
        }
        else if(emptyReg.test(this.saveData.endTime)){
          this.$message.error("请选择结课时间!");
          return;
        }
        else if(emptyReg.test(this.saveData.teacher)){
          this.$message.error("请选择班主任!")
          return;
        }
        else if (this.saveData.startTime > this.saveData.endTime) {
          this.$message.error("开课时间不能晚于结课时间!");
          return;
        }
        axios.post("/api/class",this.saveData).then(() =>{
          this.$message.success("添加成功!");
          axios.get("/api/class",{
            params:{
                page:this.page,
                pageSize:this.pageSize
              }
          }).then((result) => {
            if(result.data.code == 0) this.$message.error("请登录后操作!");
            else this.tableData = result.data.data.result;
          })
          this.dialogAddVisible = false;
        }).catch(() => {
          this.$message.error("添加失败!")
        })
      },
      edit(id) {
        axios.get("/api/class/" + id).then((result) => {
          this.updateData = result.data.data;
          this.updateData.teacher = this.teacherHandle(this.updateData.teacher);
          this.dialogEditVisible = true;
        }).catch(() => {
          this.$message.error("获取班级信息失败!")
        })
      },
      update(){
        const emptyReg = /^$/;
        const classNameReg = /^[\u4e00-\u9fa50-9a-zA-Z]{4,30}$/;
        const classroomReg = /^[\u4e00-\u9fa50-9a-zA-Z]{1,20}$|^$/;
        if(emptyReg.test(this.updateData.className)) {
          this.$message.error("班级名称不能为空!");
          return;
        }
        else if(!classNameReg.test(this.updateData.className)){
          this.$message.error("班级名称输入不正确!");
          return;
        }
        else if(!classroomReg.test(this.updateData.classroom)){
          this.$message.error("班级教室输入不正确!");
          return;
        }
        else if(emptyReg.test(this.updateData.startTime)) {
          this.$message.error("请选择开课时间!");
          return;
        }
        else if(emptyReg.test(this.updateData.endTime)){
          this.$message.error("请选择结课时间!");
          return;
        }
        else if(emptyReg.test(this.updateData.teacher)){
          this.$message.error("请选择班主任!")
          return;
        }
        else if (this.saveData.startTime > this.saveData.endTime) {
          this.$message.error("开课时间不能晚于结课时间!");
          return;
        }
        this.toTeacher();
        axios.put("/api/class",this.updateData).then(() =>{
          this.$message.success("保存成功!"),
          this.dialogEditVisible = false,
          axios.get("/api/class",{
            params:{
                page:this.page,
                pageSize:this.pageSize
              }
          }).then((result) => {
            this.tableData = result.data.data.result;
            this.updateData = {
              className:'',
              classroom:'',
              startTime:'',
              endTime:'',
              teacher:''
            }
          })
        }).catch(() => {
          this.$message.error("编辑失败!")
        })
        
      },
      cancel() {
        if(this.dialogEditVisible == true) {
          this.dialogEditVisible = false;
          this.updateData={
            className:'',
            classroom:'',
            startTime:'',
            endTime:'',
            teacher:''
          }
        }
        else if(this.dialogAddVisible == true) {
          this.dialogAddVisible = false;
          this.saveData={
            className:'',
            classroom:'',
            startTime:'',
            endTime:'',
            teacher:''
          }
        }
      },
      del(id){
        this.$confirm('您确定要删除该班级吗?', '删除班级', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.delete("/api/class/" + id).then(() => {
            this.$message({
            type: 'success',
            message: '删除成功!'
            })
            axios.get("/api/class",{
              params:{
                  page:this.page,
                  pageSize:this.pageSize
                }
            }).then((result) => {
              this.tableData = result.data.data.result;
            })
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
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
      axios.get("/api/class",{
        params:{
            page:this.page,
            pageSize:this.pageSize
          }
      }).then((result) => {
        if(result.data.code == 0) this.$message.error("请登录后操作!");
        else {
          this.tableData = result.data.data.result;
          this.total = result.data.data.total}
      }),
      axios.get("/api/emps/job",{
        params:{
          job:1
        }
      }).then((result) => {
          this.teacherData=result.data.data;
        })
    }
  }
</script>
    
<style>
</style>