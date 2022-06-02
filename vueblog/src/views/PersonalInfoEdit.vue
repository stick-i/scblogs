<template>
  <div class="body">
    <div class="ContentA">
      <!-- 顶部导航栏部分 -->
      <TopBarA></TopBarA>
      <!-- 中间部分内容区域 -->
      <div class="centerContent">
        <div class="contentMiddle">
          <div class="contentleft">
            <ul class="contentleftA">
              <li :style="{'background-color': item.chose ? 'rgb(240,240,245)': ''}"
                  v-for="(item,index) in leftNavigation" @click="ChoseModel(index)" :key="item.id">
                <a href="#">{{ item.name }}</a>
              </li>
            </ul>
            <!-- <div class="contentleftB">
              <div class="contentleftB-1">
                <div class="contentleftB-1-a">
                  对我们的服务满意吗？
                </div>
                <div class="contentleftB-1-b">
                  <ul>
                    <li>
                      <i class="iconfont "></i><span>非常不满意</span>
                    </li>
                    <li>
                      <i></i><span>不满意</span>
                    </li>
                    <li>
                      <i></i><span>一般</span>
                    </li>
                    <li>
                      <i></i><span>满意</span>
                    </li>
                    <li>
                      <i></i><span>非常满意</span>
                    </li>
                  </ul>
                </div>
              </div>
            </div> -->
          </div>
          <div class="contentright">
              <div v-if="leftNavigation[0].chose">
                <Information></Information>
              </div>
              <div v-if="leftNavigation[1].chose">
                <AccountSet></AccountSet>
              </div>
              <div v-if="leftNavigation[2].chose">
                <MyCollect></MyCollect>
              </div>
              <div v-if="leftNavigation[5].chose">
                <MyThumbs></MyThumbs>
              </div>
          </div>
        </div>
      </div>
      <!-- 页面底部视图 -->
      <div class="contentbottom">
        <ButtomView></ButtomView>
      </div>
    </div>

  </div>
</template>
<script>
import TopBarA from "@/components/content/topbar/TopBar";
import ButtomView from "@/components/P_user/ButtomView/ButtomView.vue"
import Information from "@/components/P_user/PMELeftTabs/Information.vue";
import AccountSet from "@/components/P_user/PMELeftTabs/AccountSet.vue"
import MyCollect from "@/components/P_user/PMELeftTabs/MyCollect.vue"
import MyThumbs from "@/components/P_user/PMELeftTabs/MyThumbs_up.vue"

export default {
  components: {
    TopBarA,
    ButtomView,
	  Information,
	  AccountSet,
	  MyCollect,
	  MyThumbs,
  },
  data() {
    return {
      leftNavigation: [{name: "个人资料", chose: true,id:1}, {name: "账号设置", chose: false,id:2}
        , {name: "我的收藏", chose: false,id:3}, {name: "浏览历史", chose: false,id:4},
        {name: "内容管理", chose: false,id:5}, {name: "我的点赞", chose: false,id:6}],
      userMessage: {
        username: "默认参数P",
        nickname: "默认参数P",
        // 默认获取的个人头像照片地址
        avatarUrl: "https://profile.csdnimg.cn/2/8/8/1_qq_55817438",
        registerTime: ""
      },
      config: {
        headers: {
          'token': localStorage.getItem('token')
        }
      },
      // 用户数据加载完成之后显示
      content1Seen: false,
    }
  },
  async created() {
    await this.GetData()
    window.parentMounted = true	// _isMounted是当前实例mouned()是否执行 此时为true
    // 使得账号设置模块消失,模块选择可以变动
  },
  beforeCreate() {
    window.parentMounted = this._isMounted  // _isMounted是当前实例mouned()是否执行 此时为false
  },
  methods: {
    async GetData() {
      await this.$axios.get('/user', this.config).then((res) => {
        localStorage.setItem('userMessage', JSON.stringify(res.data.data))
      }).then(res => {
        this.userMessage = JSON.parse(localStorage.getItem('userMessage'))
        this.content1Seen = true
      })
    },
    ChoseModel(index) {
      // 选择模块
      for (let i = 0; i < this.leftNavigation.length; i++) {
        this.leftNavigation[i].chose = false
      }
      this.leftNavigation[index].chose = true

      if (index == 4) {
        this.$router.push('/ContentManagement')
      }
    }
  }
}
</script>

<style scoped>
* {
  padding: 0;
  margin: 0;
}
.body {
  width: 100%;
  min-height: 100vh;
  background: rgb(243, 244, 246);
  display: flex;
  justify-content: center;
}
.ContentA {
  width: 1280px;
  height: 100%;
  margin: 0 auto;
}
.centerContent {
  width: 100%;
  padding-top: 70px;
}
.contentbottom{
  margin-top: auto;
}
.contentMiddle {
  width: 1280px;
  overflow: hidden;
  background: transparent;
  margin: 10px auto 0;
}
.contentleft {
  width: 200px;
  float: left;
  /* background: white; */
}

.contentleftA {
  list-style: none;
  width: 100%;
  background: white;
  border-radius: 5px;
}

.contentleftA li {
  width: 100%;
  height: 50px;
  line-height: 50px;
  /* padding: 20px; */
  text-align: center;
  margin: 5px 0;
}

.contentleftA li a {
  font-size: 16px;
  color: black;
}

.contentleftA li:hover {
  background: rgb(240, 240, 245);
}

.contentleftB {
  width: 100%;

  padding-top: 15px;
  /* background: #000; */
}

.contentleftB .contentleftB-1 {
  width: 100%;
  background: white;
  padding-top: 20px;
  border-radius: 5px;
}

.contentleftB-1 .contentleftB-1-a {
  width: 100%;
  height: 40px;
  text-align: center;
  line-height: 40px;
}

.contentleftB-1 .contentleftB-1-b {
  width: 100%;
  margin-top: 10px;
  margin-bottom: 10px;
}

.contentleftB-1 .contentleftB-1-b ul {
  list-style: none;
}

.contentleftB-1 .contentleftB-1-b ul li {
  width: 100%;
  height: 60px;
  line-height: 60px;
  text-align: center;
  font-size: 16px;
  color: rgb(153, 154, 170);
}

.contentright {
  width: 83%;
  min-height: 800px;
  float: right;
}
</style>
