<template>
  <div class="picList">
    <div class="masonry" ref="pictest">
      <div class="item" v-for="(picture,index) in piclist" :key="index">
        <!-- 删掉一个ifload@load="ifLoad" -->
        <!--判断图片是否加载好-->
        <img :src="picture.pic" :alt="index" @click="showBigPic(index)" class="picture" @load="ifLoad">
        <el-dialog
          title="大图"
          :visible.sync="centerDialogVisible"
          width="50%"
          center>
          <img :src="bigPic" alt="" style="width:96%">
          <span slot="footer" class="dialog-footer">
              <el-button @click="centerDialogVisible = false">取 消</el-button>
              <el-button type="primary" @click="centerDialogVisible = false">确 定</el-button>
            </span>
        </el-dialog>
        <div class="picName">{{picture.name}}</div>
        <div class="icon">
          <!-- 点赞 -->
          <i class="iconfont like" :ref="'picLike'+index" @click="like(index)">&#xe622;</i>
          <!-- 收藏 -->
          <i class="iconfont collect" :ref="'picCollect'+index" @click="collect(index)">&#xe616;</i>
          <!-- 评论可以不用变色  <i class="iconfont comment" :ref="'picComment'+index" @click="comment(index)">&#xe615;</i> -->
          <i class="iconfont comment">&#xe615;</i>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "",
  data() {
    return {
      scrollHeight: 0, // 滚动的高度
      itemWidth: 0 + 'px', // 每个item 的宽度
      showPicList: [], // 获取全部图片的src
      piclist: [], // 获取showPicList中部分图片进行显示，之后再从loadmore()中获取更多(下拉加载功能)
      bigPic: '',
      centerDialogVisible: false,
      loadCount: 0
      // width: this.$refs.pictest.clientWidth
    }
  },
  mounted() {
    // console.log(document.getElementsByClassName('masonry'))
    window.addEventListener('resize', () => {
      this.waterfall()
    })
    window.addEventListener('scroll', () => {
      console.log(window.scrollY)
      var that = this
      if (window.scrollY > that.scrollHeight) {
        if (window.scrollY - that.scrollHeight >= 100) {
          this.loadMore()
          this.scrollHeight = window.scrollY
        }
      }
    })
  },
  methods:{
    waterfall() {
      var items = this.$refs.pictest.children
      // var itemWidth = 270
      var columns1 = 4
      var arr = []
      // 距离左右边缘的距离固定，item的缝隙固定
      var gapToTop = 10 // 与顶部之间的距离，避免盒子重合
      var gap = 20 // 上下间距
      var gapBetween = 10 // 左右item之间的距离
      var gapToSide = 10 // 到masonry左、右边缘的距离
      // var calcItemWidth = (this.$refs.pictest.clientWidth - 2 * gapToSide - (columns1 - 1) * gapBetween) / 4
      console.log(this.$refs.pictest.clientWidth)
      // 实现左右对称
      // items[0].style.left = gapToSide + 'px'
      // items[columns1 - 1].style.right = gapToSide + 'px'
      var calcItemWidth = (this.$refs.pictest.clientWidth - 2 * gapToSide - (columns1 - 1) * gapBetween) / 4
      this.itemWidth = calcItemWidth + 'px'
      for (var i = 0; i < items.length; i++) {
        this.$refs.pictest.children[i].style.width = calcItemWidth + 'px'
        console.log(calcItemWidth)
        // console.log(items[i].offsetHeight, items[i].clientHeight, items[i].offsetWidth, items[i].clientWidth)
        if (i < columns1) {
          items[i].style.top = gapToTop + 'px'
          // 调节第一行偏移量，让整个瀑布流区域居中
          // items[i].style.left = (calcItemWidth + gapBetween) * i + gapToSide + 'px'
          items[i].style.left = (calcItemWidth + gapBetween) * i + gapToSide + 'px'
          // console.log(items[i].style.top)
          arr.push(items[i].offsetHeight + gap)
          // console.log(items[i].offsetHeight)
        } else {
          var minHeight = arr[0]
          var index = 0
          for (var j = 0; j < arr.length; j++) {
            if (minHeight > arr[j]) {
              minHeight = arr[j]
              index = j
            }
          }
          items[i].style.top = arr[index] + gapToTop + 'px'
          // console.log(arr[0])
          items[i].style.left = items[index].offsetLeft + 'px'
          arr[index] = arr[index] + items[i].offsetHeight + gap
        }
      }
      // console.log(Math.max(...arr))
      this.$refs.pictest.style.height = Math.max(...arr) + 25 + 'px'
    },
    // 等图片加载完后执行瀑布流函数
    ifLoad() {
      this.loadCount++
      if (this.loadCount === this.$refs.pictest.children.length) {
        console.log(this.loadCount)
        this.waterfall()
      }
    },
    // 下拉加载更多
    loadMore() {
      var piclistlen = this.piclist.length
      var showPicListlen = this.showPicList.length
      if (showPicListlen - piclistlen >= 4) {
        this.piclist.push(...(this.showPicList.slice(piclistlen, piclistlen + 4)))
        // this.$refs.pictest.children.map(child => { child.style.width = that.itemWidth })
      } else {
        this.piclist.push(...(this.showPicList.slice(piclistlen, showPicListlen)))
      }
    },
  }
};
</script>

<style scoped>

  .picList {
    padding: 20px;
    width: 95%;
    margin: 0px auto;
    box-sizing: border-box;
    list-style: none;
    background-color: #fff;
    border-radius: 10px;
  }
  .picList .masonry {
    position: relative;
    overflow: hidden;
    border-radius: 10px;
    margin: 0;
    background-color: #e9e9e9;
  }
  .picList .masonry .item {
    position: absolute;
    max-width: 260px;
    background-color: #fff;
    border-radius: 10px;
  }
  .picList .masonry .item img {
    display: block;
    text-align: center;
    width: 95%;
    border-radius: 10px;
    margin: 10px auto;
    cursor: pointer;
  }
  .picList .masonry .item .picName {
    padding-left: 10px;
    padding-bottom: 5px;
  }

</style>
