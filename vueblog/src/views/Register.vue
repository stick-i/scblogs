<template>
  <div>
    <el-container>
      <el-header>
        <img class="mlogo" src="../assets/img/logo.png" alt="">
      </el-header>
      <el-main>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
          <el-form-item label="用户名:" prop="username">
            <el-input v-model="ruleForm.username"></el-input>
          </el-form-item>

          <el-form-item label="密码:" prop="password">
            <el-input type="password" v-model="ruleForm.password"></el-input>
          </el-form-item>

          <el-form-item label="邮箱:" prop="mail">
            <el-input v-model="ruleForm.mail"></el-input>
          </el-form-item>

          <el-form-item label="手机号码:" prop="mobile">
            <el-input v-model.number="ruleForm.mobile"></el-input>
          </el-form-item>

          <el-form-item label="邮箱验证码:" prop="verifyCode">
            <el-input v-model="ruleForm.verifyCode"></el-input>

            <el-button type="primary" @click="getVerifyCode('ruleForm')">获取验证码</el-button>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
          </el-form-item>
        </el-form>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  export default {
    name: "",
    data() {
      return {
        ruleForm: {
          username: '',
          password: '',
          mail:'',
          verifyCode:'',
          mobile:'',

        },
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 15, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, message: '最少6个字符', trigger: 'blur' }
          ],
          mail:[
            { required: true, message: '请输入邮箱地址', trigger: 'blur' },
            { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
          ],
          verifyCode: [
            { required: true, message: '请输入邮箱验证码', trigger: 'blur' },
          ],
          mobile:[
            { required: true, message: '请输入手机号码', trigger: 'blur' },
            { type: 'number',message: '请输入正确的手机号码', trigger: 'blur'}
          ]
        }
      };
    },
    methods: {
      getVerifyCode(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('获取验证码成功');
            this.$axios.post('http://172.16.40.14/v1/api/register/send-mail-verify').then(res => {
              console.log(res)
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!');
            this.$axios.post('http://172.16.40.14/v1/api/register/send-mail-verify').then(res => {
              console.log(res)
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      }
    }
  }
</script>

<style scoped>
    .el-header, .el-footer {
        background-color: #B3C0D1;
        color: #333;
        text-align: center;
        line-height: 60px;
    }

    .el-aside {
        background-color: #D3DCE6;
        color: #333;
        text-align: center;
        line-height: 200px;
    }

    .el-main {
        /*background-color: #E9EEF3;*/
        color: #333;
        text-align: center;
        line-height: 160px;
    }

    body > .el-container {
        margin-bottom: 40px;
    }

    .el-container:nth-child(5) .el-aside,
    .el-container:nth-child(6) .el-aside {
        line-height: 260px;
    }

    .el-container:nth-child(7) .el-aside {
        line-height: 320px;
    }

    .mlogo {
        height: 60%;
        margin-top: 10px;
    }

    .demo-ruleForm {
        max-width: 500px;
        margin: 0 auto;
    }
</style>