<template>
  <div class="body">
    <meta name="referrer" content="no-referrer" />
    <!-- 个人博客界面显示 -->
    <!-- 顶部导航栏部分 -->
    <TopBarA
      symbolImg="https://profile.csdnimg.cn/7/3/4/3_u011663865"
    ></TopBarA>
    <!-- 背景图片部分 -->
    <div class="bg"><img :src="bg_Img" alt="" /></div>
    <div class="content">
      <!-- 个人信息简述 -->
      <div class="content-intro1">
        <!-- 图片简介部分 -->
        <div class="intro1-left">
          <!-- 左部简介部分 -->
          <div class="intro1-left-img">
            <img :src="userMessage.avatarUrl" alt="" />
          </div>
          <div class="intro1-left-content">
            <div class="left-content-up">
              <div class="username">{{ userMessage.nickname }}</div>
              <div class="userage">
                <img :src="segPositionImg" alt="" />
                <span>码龄{{ codeAge }}年</span>
              </div>
              <div></div>
            </div>
            <div class="left-content-down">
              <ul>
                <li>
                  <a href="#">{{ visitTimes }}被访问<span>|</span></a>
                </li>
                <li>
                  <a href="#">原创{{ article }}篇<span>|</span></a>
                </li>
                <li>
                  <a href="#">排名{{ ranking }}<span>|</span></a>
                </li>
                <li>
                  <a href="#">{{ fans }}粉丝<span>|</span></a>
                </li>
              </ul>
            </div>
          </div>
        </div>
        <div class="intro1-right">
          <!-- 右部简介部分 -->
          <button @click="TurnToEditPage">
            <i class="iconfont icon-wenbenbianji"></i><span>编辑资料</span>
          </button>
          <button><i class="iconfont icon-shezhi"></i><span>设置</span></button>
        </div>
      </div>
      <div class="content-intro2">
        <!-- 下拉框部分 -->
        <el-collapse>
          <el-collapse-item>
            <template slot="title">
              查看详细资料<i class="iconfont icon-xiangxia"></i>
            </template>
            <div>加入CSDN时间:{{ userMessage.registerTime }}</div>
            <div>个人博客简介:{{ userMessage.nickname }}的博客</div>
          </el-collapse-item>
        </el-collapse>
      </div>
    </div>
    <div class="content-intro3">
      <leftcontent class="intro2-a"></leftcontent>
      <navigationbar class="intro2-b"></navigationbar>
    </div>
    <FixedRightA></FixedRightA>
    <ButtomA></ButtomA>
  </div>
</template>

<script>
import TopBarA from "@/components/content/topbar/TopBar";
import ButtomA from "@/components/P_user/ButtomView/ButtomView.vue";
import FixedRightA from "@/components/P_user/FixedRight/FixedRight";
import navigationbar from "@/components/P_user/NavigationBar/navigationbar";
// import leftcontent from "@/components/P_user/LeftContent/leftcontent.vue";
import qs from "qs";
export default {
  components: {
    TopBarA,
    ButtomA,
    FixedRightA,
    navigationbar,
    leftcontent,
  },

  data() {
    return {
      // 背景图片
      bg_Img:
        "https://up.enterdesk.com/edpic_360_360/9e/c5/b6/9ec5b6630fd0deac6c421e80d955f367.jpg",
      // 码龄段位标识图
      segPositionImg: "https://img-home.csdnimg.cn/images/20210108035944.gif",
      // 用户名
      userMessage: {
        username: "勇敢牛牛不怕困难",
        nickname: "超级飞侠",
        // 获取的个人头像照片地址
        avatarUrl: "https://profile.csdnimg.cn/2/8/8/1_qq_55817438",
        registerTime: "",
      },
      // 码龄
      codeAge: 0,
      // 个人博被访问次数
      visitTimes: 0,
      // 原创文章
      article: 0,
      ranking: 0,
      fans: 0,
      config: {
        headers: {
          token: localStorage.getItem("token"),
        },
      },
    };
  },
  async mounted() {
    await this.GetData();
    console.log("主页回去完成的数据",this.userMessage);
  },
  methods: {
   async GetData() {
      let params = {
        username: "123",
        password: "123456",
      };
    await this.$axios
        .post("/login/login", qs.stringify(params))
        .then((res) => {
          console.log("登录成功",res.data.data)
          // 将token设置到本地浏览器中
          window.localStorage.setItem("token", res.headers.token);
          window.localStorage.setItem(
            "userMessage",
            JSON.stringify(res.data.data)
          );
          this.userMessage = res.data.data;
        });

     await this.$axios
        .get("/user", this.config)
        .then((res) => {
          localStorage.setItem("userMessage", JSON.stringify(res.data.data));
          this.userMessage = res.data.data
        })
    },
    TurnToEditPage() {
      // 跳转至编辑页面
      console.log("触发了跳转函数");
      this.$router.push("/PersonalMessageEdit");
    },
  },
};
</script>
<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}
.body {
  background: rgb(243, 244, 246);
  width: 100%;
  height: 100%;
}

/* 背景图 */
.bg {
  width: 1280px;
  margin: 0 auto;
  height: 100px;
}
.bg img {
  width: inherit;
  height: 100%;
}
/* 中部介绍部分 */
.content {
  width: 1280px;
  background: white;
  padding: 0;
  height: 260px;
  margin: 30px auto;
}
.content .content-intro1 {
  height: 40%;
  width: 100%;
}
.content .content-intro2 {
  height: 40%;
  width: 100%;
}
.content .intro1-left {
  float: left;
}
.content .intro1-left-img {
  float: left;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  border: 2px solid rgb(240, 240, 242);
  margin: -15px 10px 0 20px;
  overflow: hidden;
}
.content .intro1-left-img img {
  width: 100px;
  height: 100px;
}
.intro1-left-content {
  float: left;
}
.intro1-left-content .left-content-up {
  margin-top: 20px;
}
.intro1-left-content .left-content-up .username {
  /* float: left; */
  font-size: 24px;
  margin-right: 10px;
  display: inline-block;
  vertical-align: middle;
}
.intro1-left-content .left-content-up .userage {
  display: inline-block;
  width: 80px;
  height: 18px;
  line-height: 18px;
  border-radius: 16px;
  background: rgb(209, 221, 241);
}
.userage img {
  width: 22px;
  margin-top: -2px;
}
.userage span {
  font-size: 12px;
  margin-left: 5px;
}
.intro1-left-content .left-content-down {
  /* float: left; */
  height: 30px;
  width: auto;
  margin-top: 10px;
}
.intro1-left-content .left-content-down li {
  float: left;
  list-style: none;
}
.intro1-left-content .left-content-down li a {
  color: black;
}
.intro1-left-content .left-content-down li span {
  margin: 0 10px;
  /* vertical-align: buttom; */
}
.intro1-right {
  float: right;
  margin-right: 20px;
}
.intro1-right button {
  margin: 20px;
  align-items: center;
  border: 1px solid rgb(204, 204, 216);
  background-color: white;
  width: 110px;
  height: 30px;
  border-radius: 20px;
  font-size: 14px;
}
.intro1-right button:hover {
  border: 1px solid black;
}
.intro1-right button i {
  color: rgb(204, 204, 216);
  vertical-align: middle;
  margin-right: 5px;
}
.intro1-right button span {
  vertical-align: middle;
}
.content-intro3 {
  width: 1280px;
  margin: 0 auto;
}
.intro2-a {
  width: 30%;
  float: left;
}
.intro2-b {
  width: 65%;
  float: right;
  background: white;
  padding: 0 20px;
}
</style>
