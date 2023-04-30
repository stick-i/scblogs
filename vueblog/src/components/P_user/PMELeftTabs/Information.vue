<template>
  <div class="rightContent">
    <div class="rightContentA">
        <div class="rightContentA-1">
            <div class="rightContentA-1-container">
              <div @click="ShowChose" class="img">
                <img :src="this.$parent.userMessage.avatarUrl" alt="">
                <img class="img-hover" :src="hoverCamera" alt="">
              </div>
              <div class="name">
                <span>{{this.$parent.userMessage.nickname}}</span>
              </div>
            </div>
        </div>
    </div>
    <div class="rightContentB">
        <div class="rightContentB-1">
            基本信息
            <button v-if="buttonSeen" class="button" @click="EditMessage()">编辑</button>
        </div>
        <div class="rightContentB-2">
          <div class="rightContentB-2-form">
            <ul>
              <li>
                <span v-if="formchange">用户昵称：{{this.$parent.userMessage.nickname}}</span>
                <!-- 编辑时产生对话框 -->
                <div  v-if="!formchange">
                  <el-form class="form" ref="form" :model="form" label-width="80px">
                      <el-form-item class="input" label="用户昵称">
                        <el-input v-model="form.nickname"></el-input>
                      </el-form-item>
                        <el-button class="b1" @click="submit">确定</el-button>
                        <el-button @click="cancel">取消</el-button>
                  </el-form>
                </div>
              </li>
              <li><span>用户ID：{{this.$parent.userMessage.username}}</span></li>
              <li><span>个人简介：这个人很懒，什么都没有留下</span></li>
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
        <uploadImg class="PickerImg"></uploadImg>
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="GetUserData">确 定</el-button>
    </el-dialog>
  </div>
</template>
<script>
import uploadImg from "@/components/P_user/UploadImg/uploadImg.vue"
import qs from "qs"
export default {
  name: "Information",
  components:{
    uploadImg,
  },
  data() {
    return {
      username:"",
      mymoney:0,
      buttonSeen:true,
      formchange:true,
      hoverCamera:"https://profile.csdnimg.cn/C/D/1/1_m0_46681545",
      // 获取图片弹窗显示
      dialogVisible: false,
      userMessage:{},
      form:{
          username:"",
          nickname:"",
          // 获取的个人头像照片地址
          avatarUrl:"https://profile.csdnimg.cn/2/8/8/1_qq_55817438",
          registerTime:""
      },
      config:{
        headers:{
          'token': localStorage.getItem('token')
        }
      }
    };
  },
  created() {
      let pMountedTimer = window.setInterval(() => {
          if (window.parentMounted ) {
            window.clearInterval(pMountedTimer)
            // 下面就可以写子组件想在mounted时执行代码（此时父组件的mounted已经执行完毕）
            this.GetData()
          }
      },100)
  },
  methods:{
    EditMessage(){
        console.log("对数据进行编辑")
        this.buttonSeen=false
        this.formchange=false
    },
    // 请求数据放入本地浏览器
    GetData(){
        this.form= JSON.parse(JSON.stringify(this.$parent.userMessage));
        },
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
      GetUserData(){
        // 使弹窗消失
          this.dialogVisible=false
          // this.GetData()
      },
      submit(){
        // 修改用户名称,需要添加ID元素
        let formdata = new FormData()
        formdata.append("nickname",this.form.nickname)
        this.$axios.put('/user/nickname',formdata,this.config).then(res=>{
          console.log("修改用户昵称接口返回数据",res)
          // 调用父组件中GetData函数获取新的用户信息
          if(res.data.status===true){
             this.$message({
              message: '用户昵称修改成功',
              type: 'success'
            });
            this.$parent.GetData()
            this.formchange=true
            this.buttonSeen=true
        //直接更新本地数据并保存至浏览器
            // this.userMessage.nickname=this.form.nickname
            // localStorage.setItem('userMessage',JSON.stringify(this.form))
          }else{
             this.$message({
              message: '修改失败',
              type: 'error'
            });
          }
        })
      },
      cancel(){
        // this.form.nickname=this.userMessage.nickname
        this.formchange=true
        this.buttonSeen=true
      }
  }
}
</script>
<style scoped lang="less">
.PickerImg{
  margin :0 20px 30px 10px;
}
.rightContent{
  width: 100%;
  background: transparent;
  }
.rightContentA{
  width: 100%;
  background: white;
}
.rightContentA .rightContentA-1{
  width: 100%;
  height: 75%;
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
  font-size: 1.2rem;
  font-weight:800 ;
  border-bottom: 1px solid rgb(245,246,247);
}
.rightContentB .rightContentB-1 .button{
  width: 70px;
  height: 1.7rem;
  cursor: pointer;
  margin: 0 100px;
  border-radius: 10px;
  font-size: 1rem;
  border: none;
  color: white;
  background: rgb(22,160,248);
	&:hover{
		box-shadow: 2px 0px 5px grey,-2px 0px 5px grey;
	}
}
.rightContentB .rightContentB-2{
  width: 100%;
  /* height: 480px; */
  padding: 10px 40px 40px 40px;
}
.rightContentB .rightContentB-2 .rightContentB-2-form{
  padding: 20px 0;
  height: 100%;
}
.rightContentB .rightContentB-2 .rightContentB-2-form ul{
  list-style: none;
}
.rightContentB .rightContentB-2 .rightContentB-2-form ul li{
  height: 24px;
  line-height:24px;
  margin-bottom:38px;
  font-size: 1rem;
}
.rightContentB .rightContentB-2 .rightContentB-2-form ul li form{
  width: 100%;
  height: 100%;
  display: flex;
}
.rightContentB .rightContentB-2 .rightContentB-2-form ul li form .input{
  width: 60%;
  height: 100%;
}
.rightContentB .rightContentB-2 .rightContentB-2-form ul li form button{
  width: 10%;
  height: 100%;
  padding: 8px 15px;
  margin: 0 10px;
  border-radius: 20px;
  border: none;
}
.rightContentB .rightContentB-2 .rightContentB-2-form ul li form .b1{
  background: rgb(255,80,27);
  color: white;
}
</style>
