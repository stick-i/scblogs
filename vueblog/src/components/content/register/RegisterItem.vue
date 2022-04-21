<template>
  <div class="register">
    <div class="register-container">
      <h3>欢迎注册</h3>
      <div class="register-form">
        <el-form
                :model="ruleForm"
                :rules="rules"
                ref="ruleForm"
                label-width="80px"
                class="demo-ruleForm"
        >
          <el-form-item label="用户名" prop="username">
            <el-input v-model="ruleForm.username" clearable></el-input>
          </el-form-item>

          <el-form-item label="密码:" prop="password">
            <el-input
                    type="password"
                    show-password
                    v-model="ruleForm.password"
            ></el-input>
          </el-form-item>

          <el-form-item label="手机号码" prop="mobile">
            <el-input v-model="ruleForm.mobile"></el-input>
          </el-form-item>

          <el-form-item label="所在学校">
            <el-input></el-input>
          </el-form-item>

          <el-form-item
                  prop="mail"
                  label="邮箱"
                  :rules="[
                  {
                    required: true,
                    message: '请输入邮箱地址',
                    trigger: 'blur',
                  },
                  {
                    type: 'email',
                    message: '请输入正确的邮箱地址',
                    trigger: ['blur', 'change'],
                  },
                ]"
          >
            <el-input
                    v-model="ruleForm.mail"
                    @change="isClick()"
                    style="width: 348px"
            ></el-input>
            <el-button
                    type="danger"
                    :disabled="isClickCodeBtn"
                    @click="obtainCode()"
            >
              <span v-show="isShowTime">获取验证码</span>
<!--              <span v-show="!isShowTime">{{ count }} s</span>-->
            </el-button
            >
          </el-form-item>

          <el-form-item label="验证码" prop="mailVerify">
            <el-input v-model="ruleForm.mailVerify"></el-input>
          </el-form-item>

          <el-form-item>
            <el-button
                    type="danger"
                    round
                    @click="submitForm('ruleForm')"
                    style="width: 420px"
            >立即注册</el-button
            >
            <!--              <el-button @click="resetForm('ruleForm')">重置</el-button>-->
          </el-form-item>
        </el-form>
      </div>
      <div class="denglu-p">
        <span class="span-before">已有账号？</span>
        <router-link to="/login"><span class="span-after">登录</span></router-link>
      </div>
    </div>
  </div>
</template>

<script>
  import qs from "qs";
  const TIME_COUNT = 60; // 倒计时的时间
  export default {
    name: "",
    data() {
      return {
        isClickCodeBtn: true,
        isShowTime:true,
        count: '',
        timer: null,
        ruleForm: {
          username: "",
          password: "",
          mail: "",
          mailVerify: "",
          mobile: "",
        },
        rules: {
          username: [
            { required: true, message: "请输入用户名", trigger: "blur" },
            { min: 3, max: 25, message: "长度在 3 到 5 个字符", trigger: "blur" },
          ],
          password: [
            { required: true, message: "请输入密码", trigger: "blur" },
            { min: 6, message: "最少6个字符", trigger: "blur" },
          ],
          mobile: [
            { required: true, message: "请输入手机号码", trigger: "blur" },
          ],
          mailVerify: [
            { required: true, message: "请输入邮箱验证码", trigger: "blur" },
          ],
        },
      };
    },
    methods: {
      isClick() {
        if (this.ruleForm.mail == "") {
          this.isClickCodeBtn = true;
        } else {
          this.isClickCodeBtn = false;
        }
      },
      obtainCode() {
        if (this.ruleForm.mail == "") {
          this.$message({
            showClose: true,
            message: "请先输入邮箱",
            type: "warning",
          });
        } else {
          // console.log("00")
          this.$axios
              .post("/register/send-mail-verify", qs.stringify(this.ruleForm))
              .then((res) => {
                console.log(res);
                if (res.data.code == 200 && res.data.status == true) {
                  this.$message({
                    showClose: true,
                    message: "验证码发送成功，请注意查收哦~",
                    type: "success",
                  });
                }
                if (res.data.code == 500) {
                  this.$message({
                    showClose: true,
                    message: "输入的邮箱格式不正确",
                    type: "warning",
                  });
                }
                if(res.data.code == 400 && res.data.status == false) {
                  this.$message({
                    showClose: true,
                    message: "该邮箱已被注册",
                    type: "error",
                  });
                }
              })
              .catch((error) => {
                console.log(error);
              });

          // 验证码倒计时
          // if (!this.timer) {
          //   this.count = TIME_COUNT
          //   this.isShowTime = false
          //   this.timer = setInterval(() => {
          //     if (this.count > 0 && this.count <= TIME_COUNT) {
          //       this.count--
          //     } else {
          //       this.show = true
          //       clearInterval(this.timer)
          //       this.timer = null
          //     }
          //   }, 1000)
          // }


        };



      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios
                .post("/register/register", qs.stringify(this.ruleForm))
                .then((res) => {
                  console.log(res);
                  if (res.data.code == 200 && res.data.status == true) {
                    this.$message({
                      showClose: true,
                      message: "恭喜您，注册成功~",
                      type: "success",
                    });
                    this.$router.push({path:'login'});
                  }
                  if(res.data.code == 200 && res.data.status == false) {
                    this.$message({
                      showClose: true,
                      message: "验证码错误",
                      type: "error",
                    });
                  }
                })
                .catch((error) => {
                  console.log(error);
                });
          } else {
            console.log("error submit!!");
            this.$message({
              showClose: true,
              message: "请注意填写正确",
              type: "warning",
            });
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
    },
  }
</script>

<style scoped>

    /*注册*/
    h3{
        text-align: center;
    }
    .register-container .register-form {
        margin-top: 20px;
    }

    .denglu-p {
        display: flex;
        justify-content: center;
        margin-top: 10px;
        font-size: 14px;
        /*padding-left: 50px;*/
    }
    .denglu-p .span-after {
        color: #fc5531;
        cursor: pointer;
    }

</style>