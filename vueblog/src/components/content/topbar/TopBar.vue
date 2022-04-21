<template>
  <div id="csdn-toolbar">
    <div class="toolbar-inside">
      <div class="toolbar-container">
        <div class="toolbar-container-left">
          <div class="toolbar-logo toolbar-subMenu-box csdn-toolbar-fl"><a
                  data-report-click="{&quot;spm&quot;:&quot;3001.4476&quot;}" data-report-query="spm=3001.4476"
                  href="https://www.csdn.net/"><img title="bk首页" src="../../../assets/img/logo.png"></a>
          </div>
          <ul class="toolbar-menus csdn-toolbar-fl">
            <li class="" title="阅读深度、前沿文章">
              <a data-report-click="{&quot;mod&quot;:&quot;popu_336&quot;,&quot;dest&quot;:&quot;https://blog.csdn.net/nav/cloud-native&quot;,&quot;spm&quot;:&quot;3001.4477&quot;}"
                 data-report-query="spm=3001.4477" href="https://blog.csdn.net/nav/cloud-native">
                博客
              </a>
            </li>

            <li class="" title="马上开始系统学习">
              <a data-report-click="{&quot;mod&quot;:&quot;popu_336&quot;,&quot;dest&quot;:&quot;https://edu.csdn.net/&quot;,&quot;spm&quot;:&quot;3001.4482&quot;}"
                 data-report-query="spm=3001.4482" href="https://edu.csdn.net/">
                专题
              </a>
            </li>

            <li class="" title="热门文章">
              <a data-report-click="{&quot;mod&quot;:&quot;popu_336&quot;,&quot;dest&quot;:&quot;https://edu.csdn.net/&quot;,&quot;spm&quot;:&quot;3001.4482&quot;}"
                 data-report-query="spm=3001.4482" href="https://edu.csdn.net/">
                热榜
              </a>
            </li>

            <li class="" title="技术问题，有问必答">
              <a data-report-click="{&quot;mod&quot;:&quot;popu_336&quot;,&quot;dest&quot;:&quot;https://ask.csdn.net/&quot;,&quot;spm&quot;:&quot;3001.4492&quot;}"
                 data-report-query="spm=3001.4492" href="https://ask.csdn.net/">
                问答
              </a>
            </li>

            <li class="" title="找到志同道合的伙伴">
              <a data-report-click="{&quot;mod&quot;:&quot;popu_336&quot;,&quot;dest&quot;:&quot;https://bbs.csdn.net/&quot;,&quot;spm&quot;:&quot;3001.6068&quot;}"
                 data-report-query="spm=3001.6068" href="https://bbs.csdn.net/">
                社区
              </a>
            </li>

            <li class="" title="看看美丽的校园">
              <a data-report-click="{&quot;mod&quot;:&quot;popu_336&quot;,&quot;dest&quot;:&quot;https://edu.csdn.net/&quot;,&quot;spm&quot;:&quot;3001.4482&quot;}"
                 data-report-query="spm=3001.4482" href="https://edu.csdn.net/">
                校园风采
              </a>
            </li>

          </ul>
        </div>
        <div class="toolbar-container-middle">
          <div class="toolbar-search onlySearch">
            <div class="toolbar-search-container">
              <span class="icon-fire" style="display: none;"></span>
              <input id="toolbar-search-input" autocomplete="off" type="text" value="" placeholder="迁移学习分析"
                     style="text-indent: 12px;">
              <button id="toolbar-search-button"><i></i><span>搜索</span></button>
              <input type="password" autocomplete="new-password" readonly="" disabled="true"
                     style="display: none; position:absolute;left:-9999999px;width:0;height:0;">
            </div>
          </div>
        </div>
        <div class="toolbar-container-right">
          <div class="toolbar-btns onlyUser">
            <div class="toolbar-btn toolbar-btn-login csdn-toolbar-fl ">
<!--              <a data-report-click="{&quot;spm&quot;:&quot;3001.5105&quot;}" data-report-query="spm=3001.5105"-->
<!--                 href="https://passport.csdn.net/account/login">登录/注册</a>-->
              <router-link to="/login"><a>登录/注册</a></router-link>
            </div>

            <a class="test-div csdn-toolbar-fl" v-if="isShowAvatar">
              <img :src="avatarUrl" alt="" class="test-img">
            </a>
<!--            <img :src="avatarUrl" alt="" class="test-img">-->
<!--            <img src="/assets/img/home/003.jpg" alt="" class="test-img">-->

            <div class="toolbar-btn toolbar-btn-vip csdn-toolbar-fl">
              <a data-report-click="{&quot;mod&quot;:&quot;popu_336&quot;,&quot;dest&quot;:&quot;https://mall.csdn.net/vip&quot;,&quot;spm&quot;:&quot;3001.4496&quot;}"
                 data-report-query="spm=3001.4496" href="https://mall.csdn.net/vip">积分中心
                </a>
            </div>
            <div class="toolbar-btn toolbar-btn-collect csdn-toolbar-fl">
              <a data-report-click="{&quot;spm&quot;:&quot;3001.7480&quot;}" data-report-query="spm=3001.7480"
                 href="https://i.csdn.net/#/user-center/collection-list?type=1">足迹</a>
              <div id="csdn-toolbar-collection-nologin" class="csdn-toolbar-plugin" style="display: none;">
                <div class="csdn-toolbar-plugin-triangle"></div>
                <div class="csdn-toolbar-collection-top">登录即可查看浏览历史和收藏</div>
                <a href="https://passport.csdn.net/account/login" class="csdn-toolbar-loginbtn">立即登录</a>
              </div>
            </div>
            <div class="toolbar-btn toolbar-btn-dynamic csdn-toolbar-fl"><a
                    data-report-click="{&quot;spm&quot;:&quot;3001.4507&quot;}" data-report-query="spm=3001.4507"
                    href="https://blink.csdn.net">动态</a></div>

            <div class="toolbar-btn toolbar-btn-write csdn-toolbar-fl ">
              <a data-report-click="{&quot;spm&quot;:&quot;3001.7765&quot;}" data-report-query="spm=3001.7765"
                 href="https://mp.csdn.net" class="btn-write-new"></a>
              <div id="csdn-toolbar-write" class="csdn-toolbar-plugin" style="display: none; opacity: 1;">
                <div class="csdn-toolbar-plugin-triangle"></div>
                <ul class="csdn-toolbar-write-box">
                  <li class="csdn-toolbar-write-box-blog">
                    <a href="https://mp.csdn.net/edit" target="_blank"
                       data-report-click="{&quot;spm&quot;:&quot;3001.5352&quot;}" data-report-query="spm=3001.5352">
                      <i class="csdn-toolbar-write-icon"></i>
                      <span>写文章</span>
                    </a>
                  </li>
                  <li class="csdn-toolbar-write-box-blink">
                    <a href="https://blink.csdn.net" target="_blank"
                       data-report-click="{&quot;spm&quot;:&quot;3001.5353&quot;}" data-report-query="spm=3001.5353">
                      <i class="csdn-toolbar-write-icon"></i>
                      <span>发Blink</span>
                    </a>
                  </li>
                  <li class="csdn-toolbar-write-box-ask">
                    <a href="https://ask.csdn.net/new?word=" target="_blank"
                       data-report-click="{&quot;spm&quot;:&quot;3001.5354&quot;}" data-report-query="spm=3001.5354">
                      <i class="csdn-toolbar-write-icon"></i>
                      <span>提问题</span>
                    </a>
                  </li>
                  <li class="csdn-toolbar-write-box-upload">
                    <a href="https://mp.csdn.net/mp_download/creation/uploadResources" target="_blank"
                       data-report-click="{&quot;spm&quot;:&quot;3001.5355&quot;}" data-report-query="spm=3001.5355">
                      <i class="csdn-toolbar-write-icon"></i>
                      <span>传资源</span>
                    </a>
                  </li>
                  <li class="csdn-toolbar-write-box-code">
                    <a href="https://codechina.csdn.net/explore" target="_blank"
                       data-report-click="{&quot;spm&quot;:&quot;3001.5356&quot;}" data-report-query="spm=3001.5356">
                      <i class="csdn-toolbar-write-icon"></i>
                      <span>建项目</span>
                    </a>
                  </li>
                </ul>

              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: "TopBar",
    data(){
      return{
        avatarUrl1:'',
        isShowAvatar1:false,
        avatarUrl:require('../../../assets/img/home/003.jpg'),
        isShowAvatar:false,

      }
    },

    components: {},
    mounted(){
      this.$nextTick(function () {
        this.bus.$on('avatar',(res) => {
          console.log(res) // 输出的是LoginItem组件传过来的头像链接
          this.avatarUrl = res;
          this.isShowAvatar = true;
          console.log(this.avatarUrl)
          this.avatarUrl1=res;
          this.isShowAvatar1 = true;
        })

        console.log(this.avatarUrl)
        console.log(this.avatarUrl1)
        console.log(this.isShowAvatar1)


      })
    }
  };
</script>

<style>
    @import "../../../assets/css/content/topbar/topbar.css";
    .test-div {
        height: 48px;
    }
    #csdn-toolbar .toolbar-inside .toolbar-container .toolbar-container-right .onlyUser .test-img {
        display: block;
        width: 32px;
        height: 32px;
        border-radius: 50%;
        margin-top:  8px;
    }



</style>