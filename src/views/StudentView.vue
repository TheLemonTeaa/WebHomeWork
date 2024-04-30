<template>
  <div>
    <span style="font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">学员管理</span><br/><br/><br/>
    <el-form :inline="true" :model="formInline" class="demo-form-inline" style="width: 1300px;">
            <el-form-item label="学员姓名">
              <el-input v-model="formInline.studentName" placeholder="请输入学员姓名"></el-input>
            </el-form-item>
            <el-form-item label="学号">
              <el-input v-model="formInline.studentID" placeholder="请输入学号"></el-input>
            </el-form-item>
            <el-form-item label="最高学历">
              <el-select v-model="formInline.degree" placeholder="请选择">
                <el-option label="全部" value=""></el-option>
                <el-option label="初中" value="1"></el-option>
                <el-option label="高中" value="2"></el-option>
                <el-option label="大专" value="3"></el-option>
                <el-option label="本科" value="4"></el-option>
                <el-option label="硕士" value="5"></el-option>
                <el-option label="博士" value="6"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="所属班级">
              <el-select v-model="formInline.className" placeholder="请选择">
                <el-option label="全部" value=""></el-option>
                <el-option 
                  v-for="classname in classData"
                  :key="classname.id"
                  :label="classname.className"
                  :value="classname.id"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="onSubmit">查询</el-button>
            </el-form-item>
          </el-form>
          <p>
              <el-button type="primary" @click="dialogAddVisible = true">+添加学员</el-button>
              <el-button type="primary" @click="delMutiple()">-批量删除</el-button>
          </p>
          <el-dialog :visible.sync="dialogAddVisible" center>
          <span style="font-size: large;font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">添加学员</span><br/><br/><br/>
          <div>
          <el-form :model="form">
            <el-form-item label="姓名">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-input v-model="saveData.studentName" placeholder="请输入姓名" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="学号">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-input v-model="saveData.studentID" placeholder="请输入学号" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="性别">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-select v-model="saveData.gender" placeholder="请选择" style="width: 50%;">
                <el-option label="男" value="1"></el-option>
                <el-option label="女" value="2"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="手机号">&nbsp;&nbsp;&nbsp;
              <el-input v-model="saveData.phoneNum" placeholder="请输入手机号" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="最高学历">
              <el-select v-model="saveData.degree" placeholder="请选择">
                <el-option label="初中" value="1"></el-option>
                <el-option label="高中" value="2"></el-option>
                <el-option label="大专" value="3"></el-option>
                <el-option label="本科" value="4"></el-option>
                <el-option label="硕士" value="5"></el-option>
                <el-option label="博士" value="6"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="所属班级">
              <el-select v-model="saveData.className" placeholder="请选择">
                <el-option 
                  v-for="classname in classData"
                  :key="classname.id"
                  :label="classname.className"
                  :value="classname.id"></el-option>
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
            ref="multipleTable"
            border
            tooltip-effect="dark"
            style="width: 100%"
            @selection-change="handleSelectionChange">
            <el-table-column
              type="selection"
              width="62">
            </el-table-column>
            <el-table-column
              prop="studentName"
              label="姓名"
              width="80">
            </el-table-column>
            <el-table-column
              prop="studentID"
              label="学号"
              width="115">
            </el-table-column>
            <el-table-column
              prop="className"
              label="班级"
              width="180">
              <template slot-scope="scope">
                {{ classHandle(scope.row.className) }}
              </template>
            </el-table-column>
            <el-table-column
              prop="gender"
              label="性别"
              width="75">
              <template slot-scope="scope">
                {{ scope.row.gender == 1 ? "男" : "女" }}
              </template>
            </el-table-column>
            <el-table-column
              prop="phoneNum"
              label="手机号"
              width="120">
            </el-table-column>
            <el-table-column
              prop="degree"
              label="最高学历"
              width="100">
              <template slot-scope="scope">
                {{ degreeHandle(scope.row.degree) }}
              </template>
            </el-table-column>
            <el-table-column
              prop="count"
              label="违规次数"
              width="80">
            </el-table-column>
            <el-table-column
              prop="score"
              label="违规扣分"
              width="80">
            </el-table-column>
            <el-table-column
              prop="update_time"
              label="最后操作时间"
              width="140">
            </el-table-column>
            <el-table-column
              label="操作">
              <template slot-scope="scope">
                <el-button type="text" @click="edit(scope.row.id)">编辑</el-button>
                <el-button type="text" @click="volum(scope.row.id)">违纪</el-button>
                <el-button type="text" @click="del(scope.row.id)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-dialog :visible.sync="dialogEditVisible" center>
          <span style="font-size: large;font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">修改学员</span><br/><br/><br/>
          <div>
          <el-form :model="form">
            <el-form-item label="姓名">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-input v-model="updateData.studentName" placeholder="请输入姓名" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="学号">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-input v-model="updateData.studentID" placeholder="请输入学号" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="性别">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <el-select v-model="updateData.gender" placeholder="请选择" style="width: 50%;">
                <el-option label="男" value="1"></el-option>
                <el-option label="女" value="2"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="手机号">&nbsp;&nbsp;&nbsp;
              <el-input v-model="updateData.phoneNum" placeholder="请输入手机号" style="width: 50%;"></el-input>
            </el-form-item>
            <el-form-item label="最高学历">
              <el-select v-model="updateData.degree" placeholder="请选择">
                <el-option label="初中" value="1"></el-option>
                <el-option label="高中" value="2"></el-option>
                <el-option label="大专" value="3"></el-option>
                <el-option label="本科" value="4"></el-option>
                <el-option label="硕士" value="5"></el-option>
                <el-option label="博士" value="6"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="所属班级">
              <el-select v-model="updateData.className" placeholder="请选择">
                <el-option 
                  v-for="classname in classData"
                  :key="classname.id"
                  :label="classname.className"
                  :value="classname.id"></el-option>
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
              :current-page="page"
              :page-sizes="[10, 20, 50, 100]"
              :page-size="pageSize"
              layout="sizes, ->, total, prev, pager, next, jumper"
              :total=total>
            </el-pagination>
          </div>
  </div>
</template>
<script>
import axios from 'axios';
  export default {
    data() {
      return {
        tableData: [],
        classData:[],
        page:'',
        pageSize:'',
        total:'',
        formInline: {
          studentName: '',
          studentID: '',
          degree: '',
          className: ''
        },
        dialogAddVisible: false,
        dialogDeleteVisible: false,
        dialogEditVisible: false,
        saveData:{
          studentName:'',
          studentID:'',
          gender:'',  
          phoneNum:'',
          degree:'',
          className:''
        },
        updateData:{
          id:'',
          studentName:'',
          studentID:'',
          gender:'',  
          phoneNum:'',
          degree:'',
          className:'',
          count:'',
          score:''
        },
        volumData:{
          id:'',
          count:'',
          score:''
        },
        mutipleSelection:[]
      }
    },
    methods: {
      handleSizeChange(val) {
        console.log(`每页 ${val} 条`);
        this.pageSize = val,
        this.page = "1",
        axios.get("/api/stu",{
          params:{
              page:this.page,
              pageSize:this.pageSize
            }
        }).then((result) => {
          this.tableData = result.data.data.result
        })
      },
      handleCurrentChange(val) {
        console.log(`当前页: ${val}`);
        this.page = val,
        axios.get("/api/stu",{
          params:{
              page:this.page,
              pageSize:this.pageSize
            }
        }).then((result) => {
          this.tableData = result.data.data.result
        })
      },
      degreeHandle(degree) {
        if(degree == "1") return "初中";
        else if(degree == "2") return "高中";
        else if(degree == "3") return "大专";
        else if(degree == "4") return "本科";
        else if(degree == "5") return "硕士";
        else if(degree == "6") return "博士"
      },
      toDegree(){
        if(this.updateData.degree == "初中") this.updateData.degree = "1";
        else if(this.updateData.degree == "高中") this.updateData.degree = "2";
        else if(this.updateData.degree == "大专") this.updateData.degree = "3";
        else if(this.updateData.degree == "本科") this.updateData.degree = "4";
        else if(this.updateData.degree == "硕士") this.updateData.degree = "5";
        else if(this.updateData.degree == "博士") this.updateData.degree = "6";
      },
      classHandle(classname){
        if(classname != null){
          const cls = this.classData.find(cl => cl.id == classname);
          return cls ? cls.className : ''
        }
        return ''
      },
      toClass(){
        if(this.updateData.className != null){
          const cls = this.classData.find(cl => cl.className == this.updateData.className);
          return cls ? cls.id : ''
        }
        return ''
      },
      toGender() {
        if(this.updateData.gender == "男") this.updateData.gender = "1";
        else if(this.updateData.gender == "女") this.updateData.gender = "2"
      },
      cellStyle(){
        return 'text-align:center'
      },
      onSubmit() {
        console.log('submit!');
        axios.get("/api/stu",{
          params:{
            page:this.page,
            pageSize:this.pageSize,
            studentName:this.formInline.studentName,
            studentID:this.formInline.studentID,
            degree:this.formInline.degree,
            className:this.formInline.className
          }
        }).then((result) =>{
          this.tableData = result.data.data.result
        })
      },
      handleSelectionChange(selection) {
        this.mutipleSelection=[]
        selection.forEach(element => {
          this.mutipleSelection.push(element.id);
        });
      },
      delMutiple() {
        const apiUrl = "/api/stu/"
        console.log(apiUrl + this.mutipleSelection)
        axios.delete(apiUrl + this.mutipleSelection).then(() => {
          axios.get("/api/stu",{
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
      del(id){
        this.$confirm('您确定要删除该学员的信息吗?', '删除学员', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          axios.delete("/api/stu/" + id).then(()=>{
            this.$message({
              type: 'success',
              message: '删除成功!'
            });
            axios.get("/api/stu",{
              params:{
                  page:this.page,
                  pageSize:this.pageSize
                }
            }).then((result) => {
              this.tableData = result.data.data.result,
              this.total = result.data.data.total
            })
          }).catch(()=>{
            this.$message.error("删除失败!")
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
      },
      add(){
        const emptyReg = /^$/;
        const studentNameReg = /^[\u4e00-\u9fa50-9a-zA-Z]{2,10}$/;
        const studentIDReg = /^[a-zA-Z0-9]{10}$/;
        const phoneNumReg = /^[0-9]{11}$/;
        if(emptyReg.test(this.saveData.studentName)) {
          this.$message.error("姓名不能为空!");
          return;
        }
        else if(!studentNameReg.test(this.saveData.studentName)){
          this.$message.error("姓名输入不正确!");
          return;
        }
        else if(emptyReg.test(this.saveData.studentID)) {
          this.$message.error("学号不能为空!");
          return;
        }
        else if(!studentIDReg.test(this.saveData.studentID)){
          this.$message.error("学号输入不正确!");
          return;
        }
        else if(emptyReg.test(this.saveData.gender)){
          this.$message.error("请选择性别!")
          return;
        }
        else if(emptyReg.test(this.saveData.phoneNum)){
          this.$message.error("手机号不能为空!")
          return;
        }
        else if(!phoneNumReg.test(this.saveData.phoneNum)){
          this.$message.error("手机号输入不正确!")
          return;
        }
        else if(emptyReg.test(this.saveData.className)){
          this.$message.error("请选择所属班级!")
          return;
        }
        axios.post("/api/stu",this.saveData).then(() => {
          this.dialogAddVisible = false;
          axios.get("/api/stu",{
            params:{
              page:this.page,
              pageSize:this.pageSize
            }
          }).then((result) => {
            this.tableData=result.data.data.result,
            this.total = result.data.data.total
          })
          this.saveData={
            studentName:'',
            studentID:'',
            gender:'',  
            phoneNum:'',
            degree:'',
            className:''
          }
          this.$message.success("添加成功!")
        }).catch(() => {
          this.$message.error("添加失败!")
        })
      },
      edit(id) {
        axios.get("/api/stu/" + id).then((result) => {
          this.updateData = result.data.data;
          this.updateData.gender = result.data.data.gender == 1 ? "男" : "女"
          this.updateData.degree = this.degreeHandle(result.data.data.degree)
          this.updateData.className = this.classHandle(result.data.data.className)
          this.dialogEditVisible = true;
        }).catch(() => {
          this.$message.error("获取学员信息失败!")
        })
      },
      update(){
        const emptyReg = /^$/;
        const studentNameReg = /^[\u4e00-\u9fa50-9a-zA-Z]{2,10}$/;
        const studentIDReg = /^[a-zA-Z0-9]{10}$/;
        const phoneNumReg = /^[0-9]{11}$/;
        if(emptyReg.test(this.updateData.studentName)) {
          this.$message.error("姓名不能为空!");
          return;
        }
        else if(!studentNameReg.test(this.updateData.studentName)){
          this.$message.error("姓名输入不正确!");
          return;
        }
        else if(emptyReg.test(this.updateData.studentID)) {
          this.$message.error("学号不能为空!");
          return;
        }
        else if(!studentIDReg.test(this.updateData.studentID)){
          this.$message.error("学号输入不正确!");
          return;
        }
        else if(emptyReg.test(this.updateData.gender)){
          this.$message.error("请选择性别!")
          return;
        }
        else if(emptyReg.test(this.updateData.phoneNum)){
          this.$message.error("手机号不能为空!")
          return;
        }
        else if(!phoneNumReg.test(this.updateData.phoneNum)){
          this.$message.error("手机号输入不正确!")
          return;
        }
        else if(emptyReg.test(this.updateData.className)){
          this.$message.error("请选择所属班级!")
          return;
        }
        this.toDegree()
        this.toGender()
        this.toClass()
        axios.put("/api/stu",this.updateData).then(() => {
          this.$message({
            type:'success',
            message:'保存成功！'
          });
          this.dialogEditVisible = false;
          axios.get("/api/stu",{
            params:{
              page:this.page,
              pageSize:this.pageSize
            }
          }).then((result) => {
            this.tableData=result.data.data.result;
          })
          this.updateData={
            studentName:'',
            studentID:'',
            gender:'',  
            phoneNum:'',
            degree:'',
            className:''
          }
        }).catch(() => {
          this.$message.error("编辑失败!")
        })
      },
      cancel() {
        if(this.dialogEditVisible == true) {
          this.dialogEditVisible = false;
          this.updateData={
            studentName:'',
            studentID:'',
            gender:'',  
            phoneNum:'',
            degree:'',
            className:''
          }
        }
        else if(this.dialogAddVisible == true) {
          this.dialogAddVisible = false;
          this.saveData={
            studentName:'',
            studentID:'',
            gender:'',  
            phoneNum:'',
            degree:'',
            className:''
          }
        }
      },
      volum(id) {
        this.$prompt('违纪扣分', '学员违纪处理', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /^(?:[1-9]|10)$/,
          inputErrorMessage: '输入分值不正确',
        }).then(({value}) => {
          console.log(value)
          axios.get("/api/stu/" + id).then((result) => {
            this.volumData.id = id
            this.volumData.score = result.data.data.score + parseInt(value);
            this.volumData.count = result.data.data.count + 1
            console.log(this.volumData)
            axios.put("/api/stu",this.volumData).then(()=> {
              this.$message({
                type: 'success',
                message: '处理成功'
              }),
              axios.get("/api/stu",{
                params:{
                    page:this.page,
                    pageSize:this.pageSize
                  }
              }).then((result) => {
                this.tableData = result.data.data.result
              })
            })
          }).catch(()=>{
            this.$message.error("获取学员信息失败!")
            return;
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消处理'
          });       
        });
      }
      
    },
    mounted() {
      axios.get("/api/stu",{
        params:{
          page:this.page,
          pageSize:this.pageSize
        }
      }).then((result) => {
        this.tableData = result.data.data.result,
        this.total = result.data.data.total
      }),
      axios.get("/api/class/all").then((result) => {
        this.classData = result.data.data;
      })
    }
  }

</script>

<style>

</style>