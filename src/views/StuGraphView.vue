<template>
    <div style="height: auto; width: auto;">
      <span style="font-family: 'Arial Normal',Arial; color: #02A7F0; text-align: left;">学员信息统计</span><br/><br/><br/>
      <div style="display: flex;">
        <div :style="{ height: height, width: width }" id="gender"></div>
        <div :style="{ height: height, width: width }" id="degree"></div>
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
        degree1:'',
        degree2:'',
        degree3:'',
        degree4:'',
        degree5:'',
        degree6:'',
        man:'',
        woman:'',
        stuData:[]
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
      axios.get("/api/stu/gender",{
        params:{
          gender:1
        }
      }).then((result) => {
        if(result.data.code == 0) this.$message.error("请登录后操作!");
        else{
        this.stuData = result.data.data
        this.man = this.stuData.length
        axios.get("/api/stu/gender",{
        params:{
            gender:2
          }
        }).then((result) => {
          this.stuData = result.data.data
          this.woman = this.stuData.length
          this.drawGender()
        })}
      })
      axios.get("/api/stu",{
        params:{
          degree:1
        }
      }).then((result) =>{
        if(result.data.code == 1) {
        this.degree1 = result.data.data.total
        axios.get("/api/stu",{
          params:{
              degree:2
            }
        }).then((result) =>{
          this.degree2 = result.data.data.total
          axios.get("/api/stu",{
            params:{
                degree:3
              }
          }).then((result) =>{
            this.degree3 = result.data.data.total
            axios.get("/api/stu",{
              params:{
                  degree:4
                }
            }).then((result) =>{
              this.degree4 = result.data.data.total
              axios.get("/api/stu",{
                params:{
                    degree:5
                  }
              }).then((result) =>{
                this.degree5 = result.data.data.total
                axios.get("/api/stu",{
                params:{
                    degree:6
                  }
              }).then((result) =>{
                this.degree6 = result.data.data.total
                this.drawLine()
              })
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
          title:{text:"学员性别统计"},
          tooltip: {
            trigger: 'item'
          },
          legend: {
            top: '5%',
            left: 'center'
          },
          series: [
            {
              name: '学员性别',
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
        let myChart = echarts.init(document.getElementById("degree"));
        // 绘制图表
        myChart.setOption({
          title: { text: "学员学历统计"},
          tooltip: {},
          xAxis: {
            type: 'category',
            data: ['初中','高中','大专','本科','硕士','博士']
          },
          yAxis: {
            type:'value',
            name:'数量'
          },
          series: [
            {
              name: "最高学历",
              type: "bar",
              data: [this.degree1,this.degree2,this.degree3,this.degree4,this.degree5],
            },
          ],
        });
      },
    },
    
  };
  
  </script>