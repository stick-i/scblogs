<template>
  <div>
    <!--页面效果 -->
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="demo-ruleForm">
      <el-form-item label="邮箱" prop="mail">
        <el-input v-model="ruleForm.mail" class="mail" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item label="验证码" prop="code" class="pr">
        <el-input prop="code" v-model="ruleForm.code" placeholder="验证码"></el-input>
        <button @click="getCode()" class="code-btn" :disabled="!show">
          <span v-show="show">发送验证码</span>
          <span v-show="!show" class="count">{{count}} s</span>
        </button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  const TIME_COUNT = 60 // 设置一个全局的倒计时的时间
  export default {
    name: "",
    data () {
      // 邮箱校验
      let validateMobile = (rule, value, callback) => {
        if (!value) {
          callback(new Error('邮箱不可为空'))
        } else {
          if (value !== '') {
            let reg = /^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
            if (!reg.test(value)) {
              callback(new Error('请输入格式正确有效的邮箱号'))
            }
          }
          callback()
        }
      }
      // 验证码校验
      let validateCheckCode = (rule, value, callback) => {
        if (!value) {
          callback(new Error('验证码不可为空'))
        } else {
          if (value !== '') {
            let reg = /^[0-9]{6}$/
            if (!reg.test(value)) {
              callback(new Error('请输入收到的6位随机验证码'))
            }
          }
          callback()
        }
      }
      return {
        ruleForm: {
          mail: '',
          code: ''
        },
        show: true,
        count: '',
        timer: null,
        rules: {
          email: [
            { validator: validateMobile, trigger: 'blur' }
          ],
          code: [
            { validator: validateCheckCode, trigger: 'blur' }
          ]
        }
      }
    },
    methods:{
      // 向后端发请求的点击事件
      getCode () {
        let _this = this
        if (this.ruleForm.mail === '') {
          _this.$message.error('请先输入邮箱再点击获取验证码')
        } else {
          this.$axios({
            method: 'post',
            url: 'http://172.16.40.14/v1/api/register/send-mail-verify',
            data: {
              'mail': this.ruleForm.mail
            }
          }).then(function (res) {
            sessionStorage.setItem('checkCode', md5(res.data.data))  // 这里我没用redis做缓存，用的浏览器sessionStorage+md5加密存下来的
          })
          // 验证码倒计时
          if (!this.timer) {
            this.count = TIME_COUNT
            this.show = false
            this.timer = setInterval(() => {
              if (this.count > 0 && this.count <= TIME_COUNT) {
                this.count--
              } else {
                this.show = true
                clearInterval(this.timer)
                this.timer = null
              }
            }, 1000)
          }
        }
      },
    }

  }
</script>

<style scoped>

</style>