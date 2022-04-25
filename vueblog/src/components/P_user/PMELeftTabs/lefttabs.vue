<template>
  <div class="rightContent">
    <div class="rightContentA">
        <div class="rightContentA-1">
            <div class="rightContentA-1-container">
              <div @click="ShowChose" class="img">
                <img :src="userImg" alt="">
                <img class="img-hover" :src="hoverCamera" alt="">
              </div>
              <div class="name">
                <span>{{username}}</span>
              </div>
            </div>
        </div>
        <div class="rightContentA-2">
            <div class="rightContentA-2-container">
                <div class="myMoney">
                  <i></i>
                  <span>我的余额{{mymoney}}</span>
                </div>
                <div class="click">
                  <button>去充值</button>|
                  <button>个人主页</button>|
                  <button>我的勋章</button>
                </div>
            </div>
        </div>
    </div>
    <div class="rightContentB">
        <div class="rightContentB-1">
            基本信息
        </div>
        <div class="rightContentB-2">
          <div class="rightContentB-2-form">
            <ul>
              <li><span>用户昵称</span></li>
              <li><span>用户ID</span></li>
              <li><span>性别</span></li>
              <li><span>个人简介</span></li>
              <li><span>所在地区</span></li>
              <li><span>地址管理</span></li>
              <li><span>出生日期</span></li>
            </ul>
          </div>
        </div>
    </div>
    <div class="rightContentC">
        <div class="rightContentC-1"></div>
        <div class="rightContentC-2"></div>
    </div>
    <!-- 弹窗选择用户头像 -->
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <!-- 上传头像部分 -->
      <uploadImg></uploadImg>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="GetUserImg">确 定</el-button>
    </el-dialog>
  </div>
</template>
<script>
import uploadImg from "@/components/P_user/UploadImg/uploadImg.vue"
import qs from "qs"
export default {
  name: "TabsContent",
  components:{
    uploadImg,
  },
  data() {
    return {
      userImg:this.$global.userMessage.userImg,
      username:"默认值没有",
      mymoney:0,
      hoverCamera:"https://profile.csdnimg.cn/C/D/1/1_m0_46681545",
      // 获取图片弹窗显示
      dialogVisible: false,
      config:{headers:{'token':localStorage.getItem('token')}}
    };
  },
  mounted(){
    // this.GetData()
  },
  methods:{
    ShowChose(){
      //弹出选项框
        console.log("触发了弹窗显示部分")
        this.dialogVisible=true
      },
      // 右上角关闭对话框
    handleClose(done) {
        this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
      },
      // 在用户修改完成之后重新调取用户头像
      GetUserImg(){
          this.dialogVisible=false
          this.$axios.get("/user",{headers:{'token':localStorage.getItem('token')}}).then(res=>{
            this.$global.userMessage.userImg=res.data.data.avatarUrl
          console.log("此时的全局头像",this.$global.userMessage.userImg)
          })
      }
  }
}
</script>
<style scoped>

.rightContent{
  width: 100%;
  background: transparent;
  }
.rightContentA{
  width: 100%;
  background: white;
  height: 230px;
}
.rightContentA .rightContentA-1{
  width: 100%;
  height: 75%;
  border-bottom: 3px solid rgb(245,246,247);
}
.rightContentA .rightContentA-1-container{
  padding: 24px 0 40px 30px;
  height: 100%;
  display: flex;
  align-items: center;
  position: relative;
}
.rightContentA .rightContentA-1-container .img{
  width: 100px;
  height: 100px;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 30px;
  vertical-align: top;
  cursor: pointer;
}
.rightContentA .rightContentA-1-container .img .img-hover{
  position: absolute;
  top: 20px;
  left: 30px;
  width: 110px;
  height: 110px;
  display:none;
}
.rightContentA .rightContentA-1-container .img:hover .img-hover{
  display:block;
}
.rightContentA .rightContentA-1-container img{
  height: 100%;
}
.rightContentA .rightContentA-1-container span{
  height: 100%;
  vertical-align: top;
  font-size: 20px;
}
.rightContentA .rightContentA-2{
  width: 100%;
  height: 25%;
}
.rightContentA .rightContentA-2-container{
  width: 100%;
  height: 100%;
  display: flex;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  padding-left: 180px;
  font-size: 18px;
  color: black;
}
.rightContentA .rightContentA-2-container .myMoney{
  width: 100px;
  height: 100%;
  line-height:100%;
  float: left;
  display: flex;
  align-items: center;
  text-align: center;
  color: #fc5531;
}
.rightContentA .rightContentA-2-container .click{
  height: 100%;
  display: flex;
  align-items: center;
  margin-left: 70px;
}
.rightContentA .rightContentA-2-container .click button{
  width: 150px;
  height: 70%;
  margin :0 20px;
  border-radius:20px;
  background:rgb(239,239,239);
  border: none;
}
.rightContentA .rightContentA-2-container .click button:nth-child(1){
  background: rgb(255,80,27);
  color: white;
}
.rightContentB{
  width: 100%;
  /* height: 600px; */
  background: white;
  margin: 20px 0;
}
.rightContentB .rightContentB-1{
  width: 100%;
  height: 80px;
  line-height: 80px;
  padding-left: 40px;
  font-size: 24px;
  font-weight:800 ;
  border-bottom: 1px solid rgb(245,246,247);
}
.rightContentB .rightContentB-2{
  width: 100%;
  height: 480px;
  padding: 10px 40px 40px 40px;
}
.rightContentB .rightContentB-2 .rightContentB-2-form{
  padding: 20px 0;
  height: 100%;
}
.rightContentB .rightContentB-2 .rightContentB-2-form ul{
  list-style: none;
}.rightContentB .rightContentB-2 .rightContentB-2-form ul li{
  height: 24px;
  line-height:24px;
  margin-bottom:38px;
  font-size: 16px;
}
</style>