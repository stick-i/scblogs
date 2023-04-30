<template>
  <div class="body">
	<!-- 顶部导航栏部分 -->
	<TopBarA></TopBarA>
    <div class="ContentA">
      <!-- 中间部分内容区域 -->
      <div class="centerContent">
        <div class="contentMiddle">
          <div class="contentleft">
            <ul class="contentleftA">
              <li :style="{'background-color': item.chose ? 'rgb(240,240,245)': ''}"
                  v-for="(item,index) in leftNavigation" @click="new ChoseModel(index)" :key="item.id">
                <a href="#">{{ item.name }}</a>
              </li>
            </ul>
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
        username: "",
        nickname: "",
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
		this.bus.$on('refreshUserInfo', () => {
			this.GetData()
		});
  },
  beforeCreate() {
    window.parentMounted = this._isMounted  // _isMounted是当前实例mouned()是否执行 此时为false
  },
	beforeDestroy() {
		this.bus.$off('refreshUserInfo')
	},
  methods: {
    async GetData() {
      await this.$axios.get('/user', this.config).then((res) => {
				// 避免浏览器缓存 每次获取个人信息时 给头像url添加时间戳, 同时通知头部导航栏
				res.data.data.avatarUrl += `?timestamp=${new Date().getTime()}`
				this.bus.$emit('userMessage', JSON.stringify(res.data.data))
        localStorage.setItem('userMessage', JSON.stringify(res.data.data))
      }).then(() => {
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

      if (index === 4) {
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
}
.ContentA {
  width: 1280px;
  height: 100%;
  margin: 0 auto;
}
.centerContent {
  width: 100%;
  padding-top: 20px;
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
  font-size: 0.8rem;
  color: black;
}
.contentleftA li:hover {
  background: rgb(240, 240, 245);
}
.contentright {
  width: 83%;
  min-height: 800px;
  float: right;
}
</style>
