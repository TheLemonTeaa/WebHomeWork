<template>
  <div style="height: auto; width: auto;">
    <span style="font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">员工信息统计</span><br/><br/><br/>
    <div style="display: flex;">
      <div :style="{ height: height, width: width }" id="gender"></div>
      <div :style="{ height: height, width: width }" id="job"></div>
    </div>
  </div>
</template>
<script>
import axios from 'axios';
// 引入基本模板
import * as echarts from 'echarts/core';
import {TitleComponent} from 'echarts/components';
import { GridComponent } from 'echarts/components';
import { BarChart } from 'echarts/charts';
import { CanvasRenderer } from 'echarts/renderers';
import { TooltipComponent, LegendComponent } from 'echarts/components';
import { PieChart } from 'echarts/charts';
import { LabelLayout } from 'echarts/features';
echarts.use([GridComponent, BarChart, CanvasRenderer,TooltipComponent,LegendComponent,PieChart,LabelLayout,TitleComponent]);
export default {
  props: {
    height: {
      type: String,
      default: "500px",
    },
    width: {
      type: String,
      default: "500px",
    }
  },
  data() {
    return {
      job1:'',
      job2:'',
      job3:'',
      job4:'',
      job5:'',
      man:'',
      woman:'',
      empData:[]
    };
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
    axios.get("/api/emps",{
      params:{
        gender:1
      }
    }).then((result) => {
      if(result.data.code == 0) this.$message.error("请登录后操作!");
      else{
      this.man = result.data.data.total
      axios.get("/api/emps",{
      params:{
          gender:2
        }
      }).then((result) => {
        this.woman = result.data.data.total
        this.drawGender()
      })}
    })
    axios.get("/api/emps/job",{
      params:{
        job:1
      }
    }).then((result) =>{
      if(result.data.code == 1) {
      this.empData = result.data.data
      this.job1 = this.empData.length
      axios.get("/api/emps/job",{
        params:{
            job:2
          }
      }).then((result) =>{
        this.empData = result.data.data
        this.job2 = this.empData.length
        axios.get("/api/emps/job",{
          params:{
              job:3
            }
        }).then((result) =>{
          this.empData = result.data.data
          this.job3 = this.empData.length
          axios.get("/api/emps/job",{
            params:{
                job:4
              }
          }).then((result) =>{
            this.empData = result.data.data
            this.job4 = this.empData.length
            axios.get("/api/emps/job",{
              params:{
                  job:5
                }
            }).then((result) =>{
              this.empData = result.data.data
              this.job5 = this.empData.length
              this.drawLine()
            })
          })
        })
      })
    }
    })
  },
  methods: {
    drawGender() {
      let myChart = echarts.init(document.getElementById("gender"));
      myChart.setOption({
        title:{text:"员工性别统计"},
        tooltip: {
          trigger: 'item'
        },
        legend: {
          top: '5%',
          left: 'center'
        },
        series: [
          {
            name: '员工性别',
            type: 'pie',
            radius: ['40%', '70%'],
            avoidLabelOverlap: false,
            itemStyle: {
              borderRadius: 10,
              borderColor: '#fff',
              borderWidth: 2
            },
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: 40,
                fontWeight: 'bold'
              }
            },
            labelLine: {
              show: false
            },
            data: [
              { value: this.man, name: '男' },
              { value: this.woman, name: '女' },
            ]
          }
        ]
      })
    },
    drawLine() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(document.getElementById("job"));
      // 绘制图表
      myChart.setOption({
        title: { text: "员工职位统计"},
        tooltip: {},
        xAxis: {
          type: 'category',
          data: ['班主任','讲师','学工主管','教研主管','咨询师']
        },
        yAxis: {
          type:'value',
          name:'数量'
        },
        series: [
          {
            name: "职位",
            type: "bar",
            data: [this.job1,this.job2,this.job3,this.job4,this.job5],
          },
        ],
      });
    },
  },
  
};

</script>