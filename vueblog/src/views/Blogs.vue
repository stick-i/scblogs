<template>
  <div>
    <top-bar></top-bar>
    <div class="container">
      <div class="main">
        <div class="main-lt">
          <div class="list-container">
            <div class="list-item">
              <div class="item">
                <div class="item-hd">
                  <h3 class="title">
                    <a target="_blank" href="#">早睡早起身体好</a>
                  </h3>
                </div>
                <div class="item-bd">
                  <div class="item-bd_cont">
                    <div class="bgc-lt">
                      <p>饭后走一走活到九十九</p>
                      <div>浏览</div>
                    </div>
                    <div class="bgc-rt"></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="main-rt">rigth</div>
      </div>
    </div>
  </div>
</template>

<script>
import TopBar from "@/components/content/topbar/TopBar";

export default {
  components: {
    TopBar,
  },
};
</script>

<style scoped>
.container {
  width: 100%;
  height: 100%;
  background-color: #f2f2f2;
}
.main {
  width: 1382px;
  height: 500px;
  background-color: pink;
  margin: 0 auto;
}

/* 左边博客开始 */
.main-lt {
  width: 1034px;
  float: left;
  background-color: yellow;
}
/* item开始 */
.list-item .item {
  padding: 16px 24px;
}
.item .item-hd {
  width: 100%;
  line-height: 22px;
  margin-bottom: 4px;
}
.item-hd .title {
  font-size: 18px;
  vertical-align: top;
}
.item-hd .title a {
  color: #222226;
}
.item-bd .item-bd_cont {
  display: flex;
  align-items: flex-start;
  flex-shrink: 0;
  color: #555666;
  line-height: 24px;
  overflow-x: auto;
}
/* item结束 */

/* 左边博客结束 */

/* 右边推荐 */
.main-rt {
  width: 338px;
  float: right;
  background-color: red;
}
</style>
