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

          <el-form-item label="密码" prop="password">
            <el-input
              type="password"
              show-password
              v-model="ruleForm.password"
            ></el-input>
          </el-form-item>

          <el-form-item label="手机号码" prop="mobile">
            <el-input v-model="ruleForm.mobile"></el-input>
          </el-form-item>

          <el-form-item label="所在学校" prop="school">
            <el-row>
              <el-col :span="8">
                <el-select
                  v-model="provinceId"
                  @focus="getProvinces"
                  @change="changeProvince"
                  placeholder="请选择省份"
                >
                  <el-option
                    v-for="item in provinceList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="8">
                <el-select
                  v-model="cityId"
                  @focus="getCities"
                  @change="changeCity"
                  placeholder="请选择城市"
                >
                  <el-option
                    v-for="item in cityList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  >
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="8">
                <el-select
                  v-model="ruleForm.schoolCode"
                  @focus="getSchools"
                  placeholder="请选择学校"
                >
                  <el-option
                    v-for="item in schoolList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.code"
                  >
                  </el-option>
                </el-select>
              </el-col>
            </el-row>
          </el-form-item>

          <el-form-item label="邮箱" prop="mail" ref="mailItem">
            <el-input v-model="ruleForm.mail" style="width: 348px"></el-input>
            <el-button
              type="danger"
              :disabled="!isShowTime"
              @click="obtainCode('mailItem')"
              style="width: 112px"
            >
              <span v-show="isShowTime">获取验证码</span>
              <span v-show="!isShowTime">{{ count }} s</span>
            </el-button>
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
          </el-form-item>
        </el-form>
      </div>
      <div class="denglu-p">
        <span class="span-before">已有账号？</span>
        <router-link to="/login"
          ><span class="span-after">登录</span></router-link
        >
      </div>
    </div>
  </div>
</template>

<script>
import qs from "qs";
const TIME_COUNT = 90; // 倒计时的时间
export default {
  name: "",
  data() {
    // 用户名验证
    let validateUserName = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("用户名不可为空"));
      } else {
        if (value !== "") {
          let reg = /^[a-zA-Z0-9_-]{5,16}$/;
          if (!reg.test(value)) {
            callback(
              new Error(
                "只能是字母数字下划线中划线，且必须在5-16位之间的用户名"
              )
            );
          }
        }
        callback();
      }
    };
    // 密码验证
    let validatePassWord = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("密码不可为空"));
      } else {
        if (value !== "") {
          let reg = /^[a-zA-Z0-9!@#$%^&*?_-]{5,20}$/;
          if (!reg.test(value)) {
            callback(new Error("只能是数字字母和特殊字符，长度为5-20位"));
          }
        }
        callback();
      }
    };
    // 手机号码验证
    let validateMobilePhone = (rule, value, callback) => {
      if (value === "") {
        callback(new Error("手机号不可为空"));
      } else {
        if (value !== "") {
          let reg = /^1[3456789]\d{9}$/;
          if (!reg.test(value)) {
            callback(new Error("请输入有效的手机号码"));
          }
        }
        callback();
      }
    };
    return {
      // 学校开始
      value: "",
      provinceList: [], // 省份列表
      cityList: [], // 城市列表
      schoolList: [], // 学校列表
      provinceId: "", // 省份编码
      cityId: "", // 城市编码
      schoolCode: "", // 学校编码
      // 学校结束
      isShowTime: true,
      count: "",
      timer: null,
      ruleForm: {
        username: "",
        password: "",
        mail: "",
        mailVerify: "",
        mobile: "",
        schoolCode: "",
      },
      rules: {
        username: [
          // { required: true, message: "请输入用户名", trigger: "blur" },
          { validator: validateUserName, trigger: "blur" },
        ],
        password: [
          // { required: true, message: "请输入密码", trigger: "blur" },
          { validator: validatePassWord, trigger: "blur" },
        ],
        mobile: [{ validator: validateMobilePhone, trigger: "blur" }],
        mail: [
          {
            required: true,
            message: "请输入邮箱地址",
            trigger: "blur",
          },
          {
            type: "email",
            message: "请输入正确的邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
        mailVerify: [
          { required: true, message: "请输入邮箱验证码", trigger: "blur" },
        ],
      },
    };
  },
	mounted(){
		//登录绑定事件
		window.addEventListener('keydown',this.keyDown);
	},
  methods: {
		keyDown(e){
			//如果是回车则执行登录方法
			if(e.keyCode == 13){
				this.submitForm('ruleForm');
			}
		},
    // 获取省份
    getProvinces() {
      this.$axios.get("/resource/province").then((res) => {
        this.provinceList = res.data.data;
      });
    },
    // 省份修改，拉取对应城市列表
    changeProvince() {
      this.$axios.get("/resource/province").then((res) => {
        this.provinceList = res.data.data;
      });
      this.cityId = "";
      this.ruleForm.schoolCode = "";
    },
    // 获取城市
    getCities() {
      if (this.provinceId == "") {
        this.$message({
          showClose: true,
          message: "请先选取省份",
          type: "warning",
        });
      } else {
        this.$axios
          .get("/resource/city?provinceId=" + this.provinceId)
          .then((res) => {
            this.cityList = res.data.data;
          });
      }
    },
    // 城市修改，拉取对应区域列表
    changeCity() {
      this.$axios
        .get("/resource/city?provinceId=" + this.provinceId)
        .then((res) => {
          this.cityList = res.data.data;
        });
      this.ruleForm.schoolCode = "";
    },
    // 获取学校
    getSchools() {
      if (this.cityId == "") {
        this.$message({
          showClose: true,
          message: "请先选取城市",
          type: "warning",
        });
      } else {
        this.$axios
          .get("/resource/university?cityId=" + this.cityId)
          .then((res) => {
            this.schoolList = res.data.data;
            // console.log(this.schoolCode)
          });
      }
    },

    obtainCode(mailItem) {
      if (this.ruleForm.mail == "") {
        this.$message({
          showClose: true,
          message: "请先输入邮箱再点击获取验证码哦~",
          type: "warning",
        });
      } else {
        this.$refs.ruleForm.validateField('mail', (error) => {
          console.log('侧睡')
          console.log(error)
          if (error) {
            return
          } else {
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
                if (res.data.code == 401 && res.data.status == false) {
                  console.log(res.data.message);
                  let message = res.data.message;
                  this.$message({
                    showClose: true,
                    message: message,
                    type: "error",
                  });
                }
              })
              .catch((error) => {
                console.log(error);
              });

            // 验证码倒计时
            if (!this.timer) {
              this.count = TIME_COUNT;
              this.isShowTime = false;
              this.timer = setInterval(() => {
                if (this.count > 0 && this.count <= TIME_COUNT) {
                  this.count--;
                } else {
                  this.isShowTime = true;
                  clearInterval(this.timer);
                  this.timer = null;
                }
              }, 1000);
            }
          }
        })
        
      }
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid&&this.ruleForm.schoolCode!='') {
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
                this.$router.push({ path: "login" });
              }
              if (res.data.code == 200 && res.data.status == false) {
                this.$message({
                  showClose: true,
                  message: "验证码错误",
                  type: "error",
                });
              }
              if (res.data.code == 401 && res.data.status == false) {
                console.log(res.data.message);
                let message = res.data.message;
                this.$message({
                  showClose: true,
                  message: message,
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
  },
	//登录销毁
	destroyed(){
		window.removeEventListener('keydown',this.keyDown,false);
	}
};
</script>

<style scoped>
/*注册*/
h3 {
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
