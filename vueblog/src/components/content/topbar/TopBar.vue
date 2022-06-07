<template>
  <div id="csdn-toolbar">
    <div class="toolbar-inside">
      <div class="toolbar-container">
        <div class="toolbar-container-left">
          <div class="toolbar-logo toolbar-subMenu-box csdn-toolbar-fl">
            <a
              data-report-click='{"spm":"3001.4476"}'
              data-report-query="spm=3001.4476"
              href="https://www.csdn.net/"
              ><img title="bk首页" src="../../../assets/img/logo.png"
            /></a>
          </div>
          <ul class="toolbar-menus csdn-toolbar-fl">
<!--            <li class="" title="阅读深度、前沿文章">-->
<!--              <a-->
<!--                data-report-click='{"mod":"popu_336","dest":"https://blog.csdn.net/nav/cloud-native","spm":"3001.4477"}'-->
<!--                data-report-query="spm=3001.4477"-->
<!--                href="https://blog.csdn.net/nav/cloud-native"-->
<!--              >-->
<!--                博客-->
<!--              </a>-->
<!--            </li>-->

<!--            <li class="" title="马上开始系统学习">-->
<!--              <a-->
<!--                data-report-click='{"mod":"popu_336","dest":"https://edu.csdn.net/","spm":"3001.4482"}'-->
<!--                data-report-query="spm=3001.4482"-->
<!--                href="https://edu.csdn.net/"-->
<!--              >-->
<!--                专题-->
<!--                &lt;!&ndash; 学习分享 &ndash;&gt;-->
<!--              </a>-->
<!--            </li>-->

<!--            <li class="" title="热门文章">-->
<!--              <a-->
<!--                data-report-click='{"mod":"popu_336","dest":"https://edu.csdn.net/","spm":"3001.4482"}'-->
<!--                data-report-query="spm=3001.4482"-->
<!--                href="https://edu.csdn.net/"-->
<!--              >-->
<!--                热榜-->
<!--                &lt;!&ndash; 资源分享 &ndash;&gt;-->
<!--              </a>-->
<!--            </li>-->

            <li class="" title="技术问题，有问必答">
              <a
                data-report-click='{"mod":"popu_336","dest":"https://ask.csdn.net/","spm":"3001.4492"}'
                data-report-query="spm=3001.4492"
                href="https://ask.csdn.net/"
              >
                问答
                <!-- 校园风采 -->
              </a>
            </li>

            <li class="" title="找到志同道合的伙伴">
              <a
                @click="ToHeart()"
              >
                表白墙
                <!-- 关于我们 -->
              </a>
            </li>

            <li class="" title="看看美丽的校园">
              <a
                data-report-click='{"mod":"popu_336","dest":"https://edu.csdn.net/","spm":"3001.4482"}'
                data-report-query="spm=3001.4482"
                href="https://edu.csdn.net/"
              >
                校园风采
              </a>
            </li>

             <li class="" title="热门文章">
              <a
                data-report-click='{"mod":"popu_336","dest":"https://edu.csdn.net/","spm":"3001.4482"}'
                data-report-query="spm=3001.4482"
                href="https://edu.csdn.net/"
              >
                长沙学院<>
                <!-- 资源分享 -->
              </a>
            </li>

          </ul>
        </div>

        <!-- 搜索框 -->
        <div class="toolbar-container-middle" :style="{'display': isShowInput}">
          <div class="toolbar-search onlySearch">
            <div class="toolbar-search-container">
              <span class="icon-fire" style="display: none"></span>
              <!--  搜索博客  -->
              <input
                id="toolbar-search-input"
                autocomplete="off"
                type="text"
                v-model="key"
                value=""
                placeholder="迁移学习分析"
                style="text-indent: 12px"
              />
              <button id="toolbar-search-button" @click="goSearchBlogs()">
<!--                <button id="toolbar-search-button" @click="searchBlogs()">-->
                <i></i><span>搜索</span>
              </button>
              <input
                type="password"
                autocomplete="new-password"
                readonly=""
                disabled="true"
                style="
                  display: none;
                  position: absolute;
                  left: -9999999px;
                  width: 0;
                  height: 0;
                "
              />
            </div>
          </div>
        </div>

        <div class="toolbar-container-right">
          <div class="toolbar-btns onlyUser">


<!--            <div class="toolbar-btn toolbar-btn-vip csdn-toolbar-fl">-->
<!--              <a-->
<!--                data-report-click='{"mod":"popu_336","dest":"https://mall.csdn.net/vip","spm":"3001.4496"}'-->
<!--                data-report-query="spm=3001.4496"-->
<!--                href="https://mall.csdn.net/vip"-->
<!--                >积分中心-->
<!--              </a>-->
<!--            </div>-->
<!--            <div class="toolbar-btn toolbar-btn-collect csdn-toolbar-fl">-->
<!--              <a-->
<!--                data-report-click='{"spm":"3001.7480"}'-->
<!--                data-report-query="spm=3001.7480"-->
<!--                href="https://i.csdn.net/#/user-center/collection-list?type=1"-->
<!--                >足迹</a-->
<!--              >-->
<!--              <div-->
<!--                id="csdn-toolbar-collection-nologin"-->
<!--                class="csdn-toolbar-plugin"-->
<!--                style="display: none"-->
<!--              >-->
<!--                <div class="csdn-toolbar-plugin-triangle"></div>-->
<!--                <div class="csdn-toolbar-collection-top">-->
<!--                  登录即可查看浏览历史和收藏-->
<!--                </div>-->
<!--                <a-->
<!--                  href="https://passport.csdn.net/account/login"-->
<!--                  class="csdn-toolbar-loginbtn"-->
<!--                  >立即登录</a-->
<!--                >-->
<!--              </div>-->
<!--            </div>-->
<!--            <div class="toolbar-btn toolbar-btn-dynamic csdn-toolbar-fl">-->
<!--              <a-->
<!--                data-report-click='{"spm":"3001.4507"}'-->
<!--                data-report-query="spm=3001.4507"-->
<!--                href="https://blink.csdn.net"-->
<!--                >动态</a-->
<!--              >-->
<!--            </div>-->

            <div
              class="toolbar-btn toolbar-btn-login csdn-toolbar-fl"
              v-if="!isShowAvatar"
            >
              <router-link to="/login"><a>登录/注册</a></router-link>
            </div>

            <!-- 显示头像 下拉菜单开始 -->
            <div class="test-div csdn-toolbar-fl">
              <el-dropdown @command="handleCommand">
                <span class="el-dropdown-link" >
                  <a class="test-div csdn-toolbar-fl" v-if="isShowAvatar">
                    <img :src="avatarUrl" alt="" class="test-img" style="border: 1px solid #e8e8ed;margin-right: 10px;" />
                  </a>
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="a">个人中心</el-dropdown-item>
                  <el-dropdown-item command="b" divided
                  >退出登录</el-dropdown-item
                  >
                </el-dropdown-menu>
              </el-dropdown>
            </div>
            <!-- 显示头像 下拉菜单结束 -->


            <div class="toolbar-btn toolbar-btn-write csdn-toolbar-fl">
              <a
                data-report-click='{"spm":"3001.7765"}'
                data-report-query="spm=3001.7765"
                class="btn-write-new"
                @click="goBlogEdit()"
                style="cursor: pointer"
              ></a>
              <!-- <div
                id="csdn-toolbar-write"
                class="csdn-toolbar-plugin"
                style="display: none; opacity: 1"
              >
                <div class="csdn-toolbar-plugin-triangle"></div>
                <ul class="csdn-toolbar-write-box">
                  <li class="csdn-toolbar-write-box-blog">
                    <a
                      href="https://mp.csdn.net/edit"
                      target="_blank"
                      data-report-click='{"spm":"3001.5352"}'
                      data-report-query="spm=3001.5352"
                    >
                      <i class="csdn-toolbar-write-icon"></i>
                      <span>写文章</span>
                    </a>
                  </li>
                  <li class="csdn-toolbar-write-box-blink">
                    <a
                      href="https://blink.csdn.net"
                      target="_blank"
                      data-report-click='{"spm":"3001.5353"}'
                      data-report-query="spm=3001.5353"
                    >
                      <i class="csdn-toolbar-write-icon"></i>
                      <span>发Blink</span>
                    </a>
                  </li>
                  <li class="csdn-toolbar-write-box-ask">
                    <a
                      href="https://ask.csdn.net/new?word="
                      target="_blank"
                      data-report-click='{"spm":"3001.5354"}'
                      data-report-query="spm=3001.5354"
                    >
                      <i class="csdn-toolbar-write-icon"></i>
                      <span>提问题</span>
                    </a>
                  </li>
                  <li class="csdn-toolbar-write-box-upload">
                    <a
                      href="https://mp.csdn.net/mp_download/creation/uploadResources"
                      target="_blank"
                      data-report-click='{"spm":"3001.5355"}'
                      data-report-query="spm=3001.5355"
                    >
                      <i class="csdn-toolbar-write-icon"></i>
                      <span>传资源</span>
                    </a>
                  </li>
                  <li class="csdn-toolbar-write-box-code">
                    <a
                      href="https://codechina.csdn.net/explore"
                      target="_blank"
                      data-report-click='{"spm":"3001.5356"}'
                      data-report-query="spm=3001.5356"
                    >
                      <i class="csdn-toolbar-write-icon"></i>
                      <span>建项目</span>
                    </a>
                  </li>
                </ul>
              </div> -->
            </div>



          </div>
        </div>
<!--        <div>{{key}}</div>-->
<!--        <div>|</div>-->
<!--        <div>{{$store.state.searchKey}}</div>-->
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "TopBar",
  data() {
    return {
    	// 信息
			userMessage:{},
      // 头像
      avatarUrl: require("../../../assets/img/home/default_avatar.jpg"),
      isShowAvatar: false,
      // 搜索内容
      key: "",
      // 是否显示搜索框
      isShowInput: "block",
      path:this.$route.fullPath
    };
  },

  components: {},
  created() {
    this.avatarUrl = window.localStorage.avatarUrl;
    this.isShowAvatar = window.localStorage.isShowAvatar;
    console.log(this.$route.fullPath)
    this.showInput();
  },
  mounted() {
    this.bus.$on("userMessage", (data) => {
      this.avatarUrl = JSON.parse(data).avatarUrl;
      this.isShowAvatar = true;
      this.userMessage = data;
      window.localStorage.avatarUrl = this.avatarUrl;
      window.localStorage.isShowAvatar = this.isShowAvatar;
      window.localStorage.userMessage = this.userMessage;
    });
  },
  // 监听
  watch:{
    '$route'(to, from) {
      this.path = to.fullPath;
      console.log(this.path)
      this.showInput();
    }
  },
  methods: {
    // 显示搜索框
    showInput() {
      if(this.path == '/blog/search') {
        this.isShowInput = "none";
      } else {
        this.isShowInput = "block";
      }
    },
    // 跳转表白墙页面
    ToHeart(){
      this.$router.push('/HeartSay')
    },
    // 跳转搜索博客
    goSearchBlogs() {
      this.$store.commit('copySearchKey',this.key)
      this.$router.push('/blog/search')
    },

    // 退出登录
    handleCommand(command) {
      if (command == "a") {
        console.log("个人中心");
        this.$router.push('/NewPersonBlog')
      }
      if (command == "b") {
        console.log("退出登录");
        this.$message({
          showClose: true,
          message: "已退出登录",
          type: "success",
        });
        // 清除状态保持
        window.localStorage.clear();
        window.sessionStorage.clear();
        // 状态保持清除后刷新页面
        window.location.reload();

        // this.$axios.get("/login/logout").then((res) => {
        //   console.log(res);
        //   this.$message({
        //     showClose: true,
        //     message: "已退出登录",
        //     type: "success",
        //   });
        //
        //
        // });
      }
    },
    // 点击跳转发布文章页面
    goBlogEdit() {
      // console.log(window.localStorage.isShowAvatar)
      if (window.localStorage.isShowAvatar == undefined) {
        this.$message({
          showClose: true,
          message: "请先登录哦~",
          type: "warning",
        });
      } else {
        this.$router.push({ path: "/blog/add" });
        // window.open("/blog/add", "_blank");
      }
    },
  },
};
</script>

<style>
@import "../../../assets/css/content/topbar/topbar.css";
.test-div {
  height: 48px;
}
#csdn-toolbar{
  /*position: fixed;*/
  top: 0px;
  left: 0px;
  z-index: 100;
  width: 100%;
}
#csdn-toolbar
  .toolbar-inside
  .toolbar-container
  .toolbar-container-right
  .onlyUser
  .test-img {
  display: block;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  margin-top: 8px;
}
.el-dropdown-link {
  cursor: pointer;
}
</style>
