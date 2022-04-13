<template>
  <div>
    <top-bar></top-bar>
    <div class="login-container">
      <div class="passport-container">
        <div class="passport-container-inner">
          <div class="passport-notes">
            <div class="notes">
              <h4>CC University</h4>
              <p style="-webkit-box-orient: vertical">
                长沙学院（Changsha
                University），简称“长大”，位于湖南省长沙市，是经中华人民共和国教育部批准的全日制普通高等本科院校，实行省市共建共管、以长沙市为主管理的体制，属于湖南省“双一流”高水平应用特色学院，入选教育部首批新工科研究与实践项目、新文科研究与改革实践项目、湖南省2011计划，是国家“十三五”应用型本科产教融合发展工程规划高校、全国高校实践育人创新创业基地、CDIO工程教育联盟、长沙市与高等院校（科研院所）创新发展联盟成员单位。
              </p>
              <span></span>
            </div>
          </div>
          <div class="passport-main">
            <div class="login-box">
              <div class="logo">
                <div class="login-icon">
                  <img src="../../assets/img/logo.png" alt="" />
                  <p>博客</p>
                </div>
                <div>
                  <span>登录博客账号</span>
                </div>
              </div>
              <div class="login-form">
                <el-input
                        placeholder="电子邮箱"
                        v-model="email"
                        clearable
                ></el-input>
                <el-input
                        placeholder="输入密码"
                        v-model="password"
                        show-password
                ></el-input>
                <p style="text-align: right">忘记密码</p>
              </div>
              <div class="login-btn">
                <el-button type="danger" round style="width: 370px"
                >登录</el-button
                >
              </div>
              <div class="zhuce-p">
                <span class="span-before">没有账号？</span>
                <span class="span-after">注册</span>
              </div>
            </div>
            <!--          <div class="zhuce-box">-->
            <!--            <div class="zhuce-box-top">顶部</div>-->
            <!--            <div class="zhuce-box-bottom">底部</div>-->
            <!--          </div>-->
          </div>
        </div>
      </div>
      <div class="login-footer">
        <div>@2022 长沙大学 版权所有</div>
        <div>关于我们</div>
      </div>
    </div>
  </div>
</template>

<script>
  import TopBar from "../../components/content/topbar/TopBar"
export default {
  name: "Login",
  components: {
    TopBar,
  },
  data() {
    return {
      email: "",
      password: "",
    };
  },
};
</script>

<style>
.passport-container {
  background: url("../../assets/img/login/015.jpg");
  background-size: cover;
  width: 100%;
  height: calc(100vh - 48px - 104px);
  position: relative;
}

.login-footer {
  width: 100%;
  height: 104px;
  display: flex;
  /*-webkit-box-align: center;*/
  align-items: center;
  /*-webkit-box-pack: center;*/
  justify-content: center;
  background-color: rgb(0, 0, 0);
  color: rgba(255, 255, 255, 0.7);
}

.passport-container .passport-container-inner {
  background: transparent;
  position: absolute;
  top: 50%;
  left: 50%;
  height: 520px;
  transform: translate(-50%, -55%);
  clear: both;
  max-width: 1520px;
}

@media screen and (min-width: 1920px) {
  .passport-container .passport-container-inner {
    width: calc(100% - 400px);
  }
}

@media screen and (min-width: 1440px) and (max-width: 1920px) {
  .passport-container .passport-container-inner {
    width: calc(100% - 240px);
  }
}

@media screen and (min-width: 1366px) and (max-width: 1440px) {
  .passport-container .passport-container-inner {
    width: calc(100% - 240px);
  }
}

@media screen and (min-width: 1280px) and (max-width: 1366px) {
  .passport-container .passport-container-inner {
    width: calc(100% - 120px);
  }
}

@media screen and (max-width: 1280px) {
  .passport-container .passport-container-inner {
    width: 1000px;
  }
}

@media screen and (max-width: 500px) {
  .passport-container .passport-container-inner {
    width: 410px;
  }

  .passport-container .passport-container-inner .passport-notes {
    display: none;
  }
}

.passport-container .passport-container-inner .passport-notes {
  width: 419px;
  height: 100%;
  float: left;
}

.passport-container .passport-container-inner .passport-notes .notes {
  width: 100%;
  height: 100%;
  padding: 0;
}

.passport-container .passport-container-inner .passport-notes .notes h4 {
  margin: 130px 0 8px;
}

.passport-container .passport-container-inner .passport-notes .notes span {
  left: 0;
}

.passport-container .passport-container-inner .passport-main {
  position: relative;
  width: 410px;
  height: 468px;
  margin-top: 52px;
  float: right;
  background-color: #ffffff;
}

.login-box {
  width: 100%;
  height: 100%;
  border-radius: 2px 2px;
  background: #fff;
  box-sizing: border-box;
}

.notes h4 {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  /*font-size: 16px;*/
  line-height: 34px;
  height: 34px;
  font-size: 28px;
  color: #fff !important;
  font-weight: 400;
  margin-bottom: 24px;
}

.notes p {
  color: #fff !important;
  line-height: 34px;
  height: 246px;
  overflow-y: auto;
  box-sizing: border-box;
}

.login-container .passport-container .passport-container-inner .passport-main {
  padding: 20px;
}

.login-container
  .passport-container
  .passport-container-inner
  .passport-main
  .login-box
  .logo
  img {
  width: 25px;
  height: 25px;
}

.login-container
  .passport-container
  .passport-container-inner
  .passport-main
  .login-box
  .logo
  .login-icon {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-bottom: 10px;
}

.login-container
  .passport-container
  .passport-container-inner
  .passport-main
  .login-box
  .logo
  p {
  font-weight: 700;
  margin-left: 5px;
}

.login-container
  .passport-container
  .passport-container-inner
  .passport-main
  .login-box
  .logo
  span {
  font-size: 20px;
}

.login-container
  .passport-container
  .passport-container-inner
  .passport-main
  .login-box
  .login-form {
  margin-top: 50px;
}

.login-container
  .passport-container
  .passport-container-inner
  .passport-main
  .login-box
  .login-form
  .el-input {
  margin-top: 15px;
}

.login-container
  .passport-container
  .passport-container-inner
  .passport-main
  .login-box
  .login-form
  p {
  margin-top: 5px;
  font-size: 13px;
  color: #858585;
  cursor: pointer;
}

.login-container
  .passport-container
  .passport-container-inner
  .passport-main
  .login-box
  .login-btn {
  margin-top: 30px;
}

.login-container
  .passport-container
  .passport-container-inner
  .passport-main
  .login-box
  .login-btn {
  margin-top: 30px;
}

.login-container
  .passport-container
  .passport-container-inner
  .passport-main
  .login-box
  .zhuce-p {
  display: flex;
  justify-content: center;
  margin-top: 10px;
  font-size: 14px;
}
.login-container
  .passport-container
  .passport-container-inner
  .passport-main
  .login-box
  .zhuce-p
  .span-after {
  color: #fc5531;
  cursor: pointer;
}
</style>