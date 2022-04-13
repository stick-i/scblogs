<template>
  <div>
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="ruleForm.username"></el-input>
      </el-form-item>

      <el-form-item label="密码:" prop="password">
        <el-input type="password" v-model="ruleForm.password"></el-input>
      </el-form-item>

      <el-form-item label="手机号码" prop="mobile">
        <el-input v-model="ruleForm.mobile"></el-input>
      </el-form-item>

      <el-form-item prop="mail" label="邮箱" :rules="[
      { required: true, message: '请输入邮箱地址', trigger: 'blur' },
      { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }]">
        <el-input v-model="ruleForm.mail"></el-input>
        <el-button type="primary" @click="obtainCode()">获取验证码</el-button>
      </el-form-item>

      <el-form-item label="验证码" prop="mailVerify">
        <el-input v-model="ruleForm.mailVerify"></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="submitForm('ruleForm')">立即创建</el-button>
        <el-button @click="resetForm('ruleForm')">重置</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>

<script>
  import qs from "qs";
  export default {
    data() {
      return {
        ruleForm: {
          username:'',
          password:'',
          mail:'',
          mailVerify:'',
          mobile:'',
        },
        rules: {
          username: [
            { required: true, message: '请输入活动名称', trigger: 'blur' },
            { min: 3, max: 25, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' },
            { min: 6, message: '最少6个字符', trigger: 'blur' }
          ],
          mobile:[
            { required: true, message: '请输入手机号码', trigger: 'blur' },
          ],
          mailVerify:[
            { required: true, message: '请输入邮箱验证码', trigger: 'blur' },
          ]
        }
      };
    },
    methods: {
      obtainCode(){
        if(this.ruleForm.mail=='') {
          this.$message({
            showClose: true,
            message: '请先输入邮箱',
            type: 'warning'
          });
        } else {
          // console.log("00")
          this.$axios.post('/register/send-mail-verify',qs.stringify(this.ruleForm)).then(res => {
            console.log(res);
            if(res.data.code==200) {
              this.$message({
                showClose: true,
                message: '验证码发送成功，请注意查收哦~',
                type: 'success'
              });
            }
            if(res.data.code==500) {
              this.$message({
                showClose: true,
                message: '输入的邮箱格式不正确',
                type: 'warning'
              });
            }
          }).catch((error)=> {
            console.log(error)
          })
        }
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$axios.post('/register/register',qs.stringify(this.ruleForm)).then(res => {
              console.log(res);
              if(res.data.status==true) {
                this.$message({
                  showClose: true,
                  message: '恭喜您，注册成功~',
                  type: 'success'
                });
              } else {
                this.$message({
                  showClose: true,
                  message: '验证码错误',
                  type: 'error'
                });
              }

            }).catch((error)=> {
              console.log(error)
            })

          } else {
            console.log('error submit!!');
            this.$message({
              showClose: true,
              message: '请注意填写正确',
              type: 'warning'
            });
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

</style>