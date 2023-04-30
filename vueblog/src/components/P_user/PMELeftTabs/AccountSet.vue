<template>
<div>
  <div v-if="contentSeen" class="rightContent">
<!--    <div class="rightContentA">-->
<!--    </div>-->
    <div class="rightContentB">
        <div class="rightContentB-1">
            账号设置
        </div>
        <div class="rightContentB-2">
            <ul>
                <li>
                    <div class="password">
                        <div class="text">密码</div>
                        <div class="right">
                            <span></span>
                            <span @click="ChangePassword">修改密码</span>
                        </div>
                    </div>
                </li>
                <li>
                    <div class="e-mail">
                        <div class="text">邮箱</div>
                        <div class="right">
                            <span>{{}}</span>
                            <span>绑定邮箱</span>
                        </div>
                    </div>
                </li>
                <li></li>
                <li><div class="deleteUser">
                        <div class="text">账号注销</div>
                        <div class="delete">
                            <span @click="DeleteAccount">立即注销</span>
                        </div>
                    </div></li>
            </ul>
        </div>
    </div>
  </div>
  <!-- 修改密码模块 -->
  <div v-if="changepassword" class="changepass">
      <div class="changepasscontent">
          <div class="changepassB">
              <span @click="Back()" >账号设置</span>/
              <span @click="changePassSeen=true">修改密码</span>
          </div>
          <div v-if="!changePassSeen" class="changepassC">
            账号修改完成！
          </div>
          <div v-if="changePassSeen" class="changepassA">
              <el-form :model="ruleForm2"
              status-icon
              :rules="rules2"
              ref="ruleForm2"
              label-width="100px"
              class="demo-ruleForm">
                <el-form-item label="输入旧密码" prop="oldpass">
                    <el-input type="password" v-model="ruleForm2.oldpass" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="输入新密码" prop="pass">
                    <el-input type="password" v-model="ruleForm2.pass" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="确认新密码" prop="checkPass">
                    <el-input type="password" v-model="ruleForm2.checkPass" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm2')">确认修改</el-button>
                    <el-button @click="resetForm('ruleForm2')">重置</el-button>
                </el-form-item>
               </el-form>
               <div class="errorTip" v-if="tipSeen">你小子原密码输入错了,是不是想盗号,报警抓你丫的</div>
          </div>
      </div>
  </div>
  <!-- 注销账户模块 -->
  <div v-if="deleteaccount" class="deleteAccount">
      <div class="deletecontent">
          <div class="changepassB">
              <span @click="Back()" >账号设置</span>/
              <span  >修改密码</span>
          </div>
          <div class="deleteaccountA">
                你惨了放学别走！有种填密码
          </div>

          <div class="button">
              <el-form :model="ruleForm2"
              status-icon
              :rules="rules2"
              ref="ruleForm2"
              label-width="100px"
              class="demo-ruleForm">
                <el-form-item label="输入密码" prop="pass">
                    <el-input type="password" v-model="ruleForm2.pass" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="确认密码" prop="checkPass">
                    <el-input type="password" v-model="ruleForm2.checkPass" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="DeleteTrue('ruleForm2')">我就删,咋滴</el-button>
                    <el-button @click="resetForm('ruleForm2')">大哥我错了</el-button>
                </el-form-item>
               </el-form>
          </div>
      </div>
  </div>
</div>
</template>
<script>
export default {
  name: "TabsContent2",
  components:{
  },
  data() {
      var checkOldpass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入旧密码'));
        } else {
          callback();
        }
      };
      var validatePass = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请输入新密码'));
        } else {
          if (this.ruleForm2.checkPass !== '') {
            this.$refs.ruleForm2.validateField('checkPass');
          }
          callback();
        }
      };
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.ruleForm2.pass) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
    return {
    //   修改密码
    changepassword:false,
    deleteaccount:false,
    contentSeen:true,
    // 用户修改完成没有
    changePassSeen:true,
    // 改昵称时返回错误弹出提示
    tipSeen:false,
    ruleForm2: {
        oldpass: '',
        pass: '',
        checkPass: '',
    },
    rules2: {
        oldpass: [
        { validator: checkOldpass, trigger: 'blur' }
        ],
        pass: [
        { validator: validatePass, trigger: 'blur' }
        ],
        checkPass: [
        { validator: validatePass2, trigger: 'blur' }
        ],

    },
    config:{headers:{'Content-Type': 'multipart/form-data','token':localStorage.getItem('token')}},
    };
  },
  mounted(){
    this.GetData()
  },
  methods:{
    //   提交修改密码
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
              console.log(valid,formName)
            // alert('submit!');
            // 开始调用接口修改用户密码
            let formdata = new FormData()
            formdata.append("oldPassword",this.ruleForm2.oldpass)
            formdata.append("newPassword",this.ruleForm2.checkPass)
            console.log("formdata",formdata)
            this.$axios.put('/user/password',formdata,this.config).then(res=>{
              console.log("修改昵称接口的返回值",res)
                if(res.data.status===true){
                     this.$message({
                            message: '密码修改成功',
                            type: 'success'
                        });
                        // 重置表单
                      this.resetForm('ruleForm2')
                      this.changePassSeen=false
                      this.tipSeen=false
                }else{
                      this.$message({
                            message: '密码修改失败',
                            type:'error'
                        });
                      this.tipSeen=true
                      this.resetForm('ruleForm2')
                }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    // 请求数据放入本地浏览器
    GetData(){
        this.form=this.$parent.userMessage
        // console.log("获取浏览器全局数据",this.$parent.userMessage)
        console.log("获取浏览器全局数据",this.form)
        },
    Back(){
        this.changepassword=false
        this.contentSeen=true
        },
    ChangePassword(){
        this.contentSeen=false
        this.changepassword=true
        // 点击成功后的页面隐藏
        this.changePassSeen=true
        // 出错后的提示语隐藏
        this.tipSeen=false
    },
    // 跳转至注销账号
    DeleteAccount(){
        this.contentSeen=false
        this.deleteaccount=true
    },
    // 确认开始注销账号
    DeleteTrue(formName){
        // 调用接口开始注销账号
        this.$refs[formName].validate((valid) => {
          if (valid) {
              console.log(valid,formName)
            // alert('submit!');
            // 开始调用接口修改用户密码
             let formdata=new FormData()
             formdata.append("password",this.ruleForm2.pass)
             console.log("标头headerstoken",this.config)
             this.$axios.delete('/user/user',{data:formdata,headers:{'token':localStorage.getItem('token')}}).then(res=>{
                if(res.data.data.status===true){
                     this.$message({
                            message: '你号没了',
                            type: 'success'
                        });
                }else{
                      this.$message({
                            message: '注销失败',
                            type:'error'
                        });
                        // 注销之后退出账号
                }
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });

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
  background:  linear-gradient(to bottom right, rgb(233, 189, 189), rgb(255, 255, 255));;
  height: 230px;
}

.rightContentB{
  width: 100%;
  height: 600px;
  background: white;
  /* box-sizing: border-box; */
}
.rightContentB-1{
    width: calc(100%- 30px);
    height: 100px;
    line-height: 100px;
    font-size: 30px;
    font-weight: 800;
    margin-left:30px;
}
.rightContentB-2{
    width: calc(100%- 60px);
    margin:0px 30px;
}
.rightContentB-2 ul{
    width: 100%;
    list-style: none;
}
.rightContentB-2 ul li{
    width:100%;
    height: 80px;
    line-height: 80px;
    font-size: 20px ;
    font-weight: 600;
}
.rightContentB-2 ul li .text{
    width: 80px;
    float: left;
}
.rightContentB-2 ul li .right{
    float: right;
}
.rightContentB-2 ul li .delete{
    float: right;
    color:red;
    cursor: pointer;
}
.rightContentB-2 ul li .right span:nth-child(1){
    margin-right: 50px;
}
.rightContentB-2 ul li .right span:nth-child(2){
    cursor: pointer;
    color: rgb(21, 123, 201);
}
.changepass{
    width: 100%;
    height: 500px;
    background: white;
}
.changepass .changepasscontent{
    width: calc(100%- 40px);
    height: 100%;
    margin:0 20px;
}
.changepassB{
    width: 100%;
    height: 50px;
    line-height: 50px;
    font-size: 18px;
    color: grey;
}
.changepassB span:hover{
    color: rgb(252,85,49);
}
.changepass .changepasscontent .changepassA{
    width: 50%;
    height: calc(100%- 50px);
    margin:100px auto;
}
.changepass .changepasscontent .changepassA .errorTip{
    width: 100%;
    height: 20%;
    font-size: 18px;
    font-weight: 600;
    text-align: center;
    color: rgb(234, 50, 50);
}
.changepass .changepasscontent .changepassC{
    width: 50%;
    height: calc(100%- 50px);
    margin:100px auto;
    text-align: center;
    font-size: 40px;
    font-weight: 600;
}
.deleteAccount{
    width: 100%;
    height: 500px;
    background: white;
}
.deletecontent{
    width: calc(100%- 40px);
    height: 100%;
    margin:0 20px;
}
.deleteaccountA{
    width: 100%;
    height: 200px;
    line-height: 200px;
    text-align: center;
    font-size: 40px;
    font-weight: 800;
}
.deletecontent .button{
    width: 40%;
    height: 200px;
    margin:0 auto;
    line-height: 200px;
    text-align: center;
    /* display: flex; */
    /* justify-content: center; */
}

/* .rightContentB-2 ul li span:nth-child(1) */
</style>
