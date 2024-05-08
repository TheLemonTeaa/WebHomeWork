<template>
    <div>
        <span style="font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">课程管理</span><br/><br/><br/>
        <el-form :inline="true" :model="searchForm" course="demo-form-inline">
            <el-form-item label="课程名称">
                <el-input v-model="searchForm.courseName" placeholder="请输入名称"></el-input>
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
        <el-button type="primary" @click="dialogAddVisible = true">+新增课程</el-button>
        </p>
        <el-dialog :visible.sync="dialogAddVisible" center>
          <span style="font-size: large;font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">新增课程</span><br/><br/><br/>
          <div>
          <el-form :model="form">
            <el-form-item label="课程名称">
              <el-input v-model="saveData.courseName" placeholder="请输入课程名称" style="width: 50%;"></el-input>
              </el-form-item>
            <el-form-item label="开课时间">
              <el-date-picker value-format="yyyy-MM-dd" v-model="saveData.startTime" placeholder="请选择开课时间" style="width: 50%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="结课时间">
              <el-date-picker value-format="yyyy-MM-dd" v-model="saveData.endTime" placeholder="请选择结课时间" style="width: 50%;"></el-date-picker>
            </el-form-item>
          </el-form>
          </div>
          <span slot="footer" course="dialog-footer">
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
        <el-table-column prop="startTime" label="开课时间" width="153"></el-table-column>
        <el-table-column prop="endTime" label="结课时间" width="172"></el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" @click="edit(scope.row.id)">编辑</el-button>
            <el-button type="text" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>
        </el-table>
        <el-dialog :visible.sync="dialogEditVisible" center>
          <span style="font-size: large;font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">修改课程</span><br/><br/><br/>
          <div>
          <el-form :model="form">
            <el-form-item label="课程名称">
              <el-input v-model="updateData.courseName" placeholder="请输入课程名称" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="开课时间">
              <el-date-picker value-format="yyyy-MM-dd" v-model="updateData.startTime" placeholder="请选择开课时间" style="width: 50%;"></el-date-picker>
            </el-form-item>
            <el-form-item label="结课时间">
              <el-date-picker value-format="yyyy-MM-dd" v-model="updateData.endTime" placeholder="请选择结课时间" style="width: 50%;"></el-date-picker>
            </el-form-item>
          </el-form>
          </div>
          <span slot="footer" course="dialog-footer">
            <el-button type="primary" @click="update()">确 定</el-button>
            <el-button @click="cancel()">取 消</el-button>
          </span>
        </el-dialog>
        <div course="block">
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
        date:[]
      },
      saveData:{
        courseName:'',
        startTime:'',
        endTime:''
      },
      updateData:{
        id:'',
        courseName:'',
        startTime:'',
        endTime:''
      },
      dialogAddVisible: false,
      dialogEditVisible: false,
    }
  },
  methods: {
    handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.pageSize = val;
        this.page = "1";
        axios.get("/api/course",{
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
        axios.get("/api/course",{
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
          axios.get("/api/course",{
          params:{
            page:this.page,
            pageSize:this.pageSize,
            courseName:this.searchForm.courseName,
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
        else axios.get("/api/course",{
          params:{
            page:this.page,
            pageSize:this.pageSize,
            courseName:this.searchForm.courseName,
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
        const courseNameReg = /^[\u4e00-\u9fa50-9a-zA-Z]{2,10}$/;
        if(emptyReg.test(this.saveData.courseName)) {
          this.$message.error("课程名称不能为空!");
          return;
        }
        else if(!courseNameReg.test(this.saveData.courseName)){
          this.$message.error("课程名称输入不正确!");
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
        axios.post("/api/course",this.saveData).then(() =>{
          this.$message.success("添加成功!");
          axios.get("/api/course",{
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
        axios.get("/api/course/" + id).then((result) => {
          this.updateData = result.data.data;
          this.dialogEditVisible = true;
        }).catch(() => {
          this.$message.error("获取课程信息失败!")
        })
      },
      update(){
        const emptyReg = /^$/;
        const courseNameReg = /^[\u4e00-\u9fa50-9a-zA-Z]{2,10}$/;
        if(emptyReg.test(this.updateData.courseName)) {
          this.$message.error("课程名称不能为空!");
          return;
        }
        else if(!courseNameReg.test(this.updateData.courseName)){
          this.$message.error("课程名称输入不正确!");
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
        axios.put("/api/course",this.updateData).then(() =>{
          this.$message.success("保存成功!"),
          this.dialogEditVisible = false,
          axios.get("/api/course",{
            params:{
                page:this.page,
                pageSize:this.pageSize
              }
          }).then((result) => {
            this.tableData = result.data.data.result;
          })
          this.updateData = {
            courseName:'',
            startTime:'',
            endTime:''
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
            startTime:'',
            endTime:''
          }
        }
        else if(this.dialogAddVisible == true) {
          this.dialogAddVisible = false;
          this.saveData={
            courseName:'',
            startTime:'',
            endTime:''
          }
        }
      },
      del(id){
        this.$confirm('您确定要删除该课程吗?', '删除课程', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.delete("/api/course/" + id).then(() => {
            this.$message({
            type: 'success',
            message: '删除成功!'
            })
            axios.get("/api/course",{
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
      axios.get("/api/course",{
        params:{
            page:this.page,
            pageSize:this.pageSize
          }
      }).then((result) => {
        if(result.data.code == 0) this.$message.error("请登录后操作!");
        else {
          this.tableData = result.data.data.result;
          this.total = result.data.data.total}
      })
    }
  }
</script>
    
<style>
</style>