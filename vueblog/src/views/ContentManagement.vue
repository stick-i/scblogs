<template>
  <div class="body-content">
        <div class="top">
            <div class="content">
              <div class="left">
                <div class="leftcontent">
                  <button class="release">发布</button>
                  <div class="collapse">
                      <el-collapse v-model="activeNames" @change="handleChange">
                          <el-collapse-item  title="首页" name="1">

                          </el-collapse-item>
                          <el-collapse-item title="管理" name="2">
                            <ul >
                              <li @click="ChoseModel(item)" v-for="(item,index) in manageList" 
                              :style="{'color':item.chose==true?'red':''}" :key="index">{{item.title}}</li>
                            </ul>
                          </el-collapse-item>
                          <el-collapse-item title="数据" name="3">
                            <ul >
                              <li @click="ChoseModel(item)" v-for="(item,index) in dataList" :style="{'color':item.chose==true?'red':''}" :key="index">{{item.title}}</li>
                            </ul>
                          </el-collapse-item>
                          <el-collapse-item title="设置" name="4">

                          </el-collapse-item>
                      </el-collapse>
                    </div>
                </div>
              </div>
              <div class="right">
                <div class="rightcontent">
                  <ManageContent></ManageContent>
                </div>
              </div>
            </div>
        </div>
  </div>
</template>

<script>
import ManageContent from "@/components/P_user/Manage/managecontent.vue"
export default {
    components:{
      ManageContent,
    },
    data(){
        return{
            // 折叠面板手动选择元素
             activeNames: ['1'],
            //  选择列表
            manageList:[{title:"内容管理",chose:true},{title:"评论管理",chose:false},{title:"专栏管理",chose:false}],
            dataList:[{title:"作品数据",chose:false},{title:"收益数据",chose:false},{title:"粉丝数据",chose:false}]
        }
    },
     methods: {
      //  折叠面板手动选择
      handleChange(val) {
        console.log(val);
      },
      // 模块选择部分
      ChoseModel(item){
          for(let i=0 ;i<this.dataList.length;i++){
            if(item.title==this.dataList[i].title){
              this.dataList[i].chose=true
            }else{
              this.dataList[i].chose=false
            }
          }
          for(let i=0 ;i<this.manageList.length;i++){
                if(item.title==this.manageList[i].title){
                  this.manageList[i].chose=true
                }else{
                  this.manageList[i].chose=false
                }
              }
      }
    }
}
</script>

<style scoped>

.top{
    width: 100%;
    /* height: 50px; */
    background: rgb(255, 255, 255);
}
.body-content{
  width: 100%;
  min-height: 100vh;
  background: rgb(75, 84, 102);
}
.top-mid{
    margin: 0 auto;
    width: 1280px ;
    height: 100%;
    /* line-height: 50px; */
    background: rgb(242, 133, 24);
    text-align: center;
    color: white;
    font-size: 26px;
    font-weight: 800;
}
.el-carousel__item h3 {
  color: #fc622b;
  font-size: 14px;
  opacity: 1;
  line-height: 200px;
  margin: 0;
}
.h3  img{
  width: 400px;
  height: 300px;
}
.content{
  width:1280px;
  margin: 0 auto;
}
.left{
  margin-top:20px;
  width: 23%;
  height: 800px;
  background: rgb(255, 255, 255);
}
.left .leftcontent{
  width: calc(100%- 40px);
  height: calc(100%- 40px);
  margin:20px;
  text-align: center;
}
.left .leftcontent .release{
  width: 150px;
  height: 50px;
  border: none;
  color: white;
  font-size: 20px;
  font-weight: 500;
  border-radius: 30px;
  background: rgb(248, 156, 36);
  margin:20px auto;
}
.left .collapse{
  width: 80%;
  margin:0 auto;
}
.left .collapse ul li{
  width: 100%;
  height: 50px;
  text-align: center;
  line-height: 50px;
  vertical-align: middle;
  font-size: 16px;
  font-weight: 400;
}
.left .collapse ul li:hover{
  background: rgb(248, 246, 246);
  color: rgb(106, 213, 243);
}
.left .collapse ul li:active{
  background: rgb(232, 227, 227);
  color: rgb(61, 244, 85);
}
.right{
  width: 75%;
  height: 100%;
  margin-top:20px;
  background: rgb(255, 255, 255);
}
.right .rightcontent{
  width: 100%;
  height: 100%;
}
</style>
