<template>
    <div>
        <span style="font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">班级管理</span><br/><br/><br/>
        <el-form :inline="true" :model="searchForm" plan="demo-form-inline">
            <el-form-item label="课程名称">
                <el-input v-model="searchForm.courseName" placeholder="请输入课程名称"></el-input>
            </el-form-item>
            <el-form-item label="教师名称">
                <el-input v-model="searchForm.teacher" placeholder="请输入教师名称"></el-input>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
        </el-form>
        <p>
        <el-button type="primary" @click="dialogAddVisible = true">+新增安排</el-button>
        </p>
        <el-dialog :visible.sync="dialogAddVisible" center>
          <span style="font-size: large;font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">新增安排</span><br/><br/><br/>
          <div>
          <el-form :model="form">
            <el-form-item label="课程名称">
              <el-select v-model="saveData.courseName" placeholder="请选择" style="width: 50%;">
                <el-option 
                  v-for="course in courseData"
                  :key="course.id"
                  :label="course.courseName"
                  :value="course.courseName"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="课程教室">
              <el-input v-model="saveData.courseRoom" placeholder="请填写课程教室" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="任课教师">
              <el-select v-model="saveData.teacher" placeholder="请选择" style="width: 50%;">
                <el-option 
                  v-for="teacher in teacherData"
                  :key="teacher.id"
                  :label="teacher.name"
                  :value="teacher.name">
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>
          </div>
          <span slot="footer" plan="dialog-footer">
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
        <el-table-column prop="courseName" label="课程名称" width="186"></el-table-column>
        <el-table-column prop="courseRoom" label="课程教室" width="127"></el-table-column>
        <el-table-column prop="teacher" label="任课教师" width="130"></el-table-column>
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
            <el-form-item label="课程名称">
              <el-select v-model="updateData.courseName" placeholder="请选择" style="width: 50%;">
                <el-option 
                  v-for="course in courseData"
                  :key="course.id"
                  :label="course.courseName"
                  :value="course.courseName"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="课程教室">
              <el-input v-model="updateData.courseRoom" placeholder="请填写课程教室" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="任课教师">
              <el-select v-model="updateData.teacher" placeholder="请选择" style="width: 50%;">
                <el-option 
                  v-for="teacher in teacherData"
                  :key="teacher.id"
                  :label="teacher.name"
                  :value="teacher.name"></el-option>
              </el-select>
            </el-form-item>
          </el-form>
          </div>
          <span slot="footer" plan="dialog-footer">
            <el-button type="primary" @click="update()">确 定</el-button>
            <el-button @click="cancel()">取 消</el-button>
          </span>
        </el-dialog>
        <div plan="block">
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
        courseName:'',
        teacher:''
      },
      saveData:{
        courseName:'',
        teacher:'',
        courseRoom:''
      },
      updateData:{
        id:'',
        courseName:'',
        teacher:'',
        courseRoom:''
      },
      teacherData:[],
      courseData:[],
      dialogAddVisible: false,
      dialogEditVisible: false,
      teacher:{
        name:''
      }
    }
  },
  methods: {
    handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.pageSize = val;
        this.page = "1";
        axios.get("/api/plan",{
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
        axios.get("/api/plan",{
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
          axios.get("/api/plan",{
          params:{
            page:this.page,
            pageSize:this.pageSize,
            courseName:this.searchForm.courseName,
            teacher:this.searchForm.teacher
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
        if(emptyReg.test(this.saveData.courseName)){
          this.$message.error("请选择课程!")
          return;
        }
        else if(emptyReg.test(this.saveData.teacher)){
          this.$message.error("请选择任课教师!")
          return;
        }
        axios.post("/api/plan",this.saveData).then(() =>{
          this.$message.success("添加成功!");
          axios.get("/api/plan",{
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
        axios.get("/api/plan/" + id).then((result) => {
          this.updateData = result.data.data;
          this.dialogEditVisible = true;
        }).catch(() => {
          this.$message.error("获取安排信息失败!")
        })
      },
      update(){
        const emptyReg = /^$/;
        if(emptyReg.test(this.updateData.courseName)) {
          this.$message.error("请选择课程!");
          return;
        }
        else if(emptyReg.test(this.updateData.teacher)){
          this.$message.error("请选择任课教师!")
          return;
        }
        axios.put("/api/plan",this.updateData).then(() =>{
          this.$message.success("保存成功!"),
          this.dialogEditVisible = false,
          axios.get("/api/plan",{
            params:{
                page:this.page,
                pageSize:this.pageSize
              }
          }).then((result) => {
            this.tableData = result.data.data.result;
          })
          this.updateData = {
            courseName:'',
            teacher:''
          }
        }).catch(() => {
          this.$message.error("编辑失败!")
        })
        
      },
      cancel() {
        if(this.dialogEditVisible == true) {
          this.dialogEditVisible = false;
          this.updateData={
            courseName:'',
            courseRoom:'',
            teacher:''
          }
        }
        else if(this.dialogAddVisible == true) {
          this.dialogAddVisible = false;
          this.saveData={
            courseName:'',
            courseRoom:'',
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
          axios.delete("/api/plan/" + id).then(() => {
            this.$message({
            type: 'success',
            message: '删除成功!'
            })
            axios.get("/api/plan",{
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
      axios.get("/api/plan",{
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
          job:2
        }
      }).then((result) => {
          this.teacherData=result.data.data;
        }),
        axios.get("/api/course/all").then((result) => {
          this.courseData=result.data.data;
        })
    }
  }
</script>
    
<style>
</style>