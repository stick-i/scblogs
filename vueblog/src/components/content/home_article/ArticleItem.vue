<template>
  <div>
    <div class="article-main">
      <div class="article-item">
        <div class="article-title">
          <a target="_blank" href="#">
            <span>【JS】质数求和</span>
          </a>
        </div>
        <div class="article-content-item">
          <div class="article-img-left">
            <a target="_blank" href="#">
              <img src="../../../assets/img/home/003.jpg" alt="">
            </a>
          </div>
          <div class="article-content-right">
            <a target="_blank" href="#">
              <div class="article-content">
                JS-质数求和：质数（prime number）是大于 1 且仅可以被 1 和自己整除的数。 比如，2 就是一个质数，因为它只可以被 1 和 2（它本身）整除。 相反，4 不是质数，因为它可以被 1, 2 和
                4 整除。
              </div>
            </a>
            <div class="article-evaluation">
              <div class="article-good"><img src="../../../assets/img/home/good.png" alt=""> 666 赞</div>
              <div class="article-author">作者：<span>啦啦啦</span></div>
              <div class="article-date">发布时间：<span>2022年4月9日</span></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: ""
  }
</script>

<style scoped>
    .article-main {
        border-radius: 5px;
        margin-right: 15px;
        border-bottom: 1px solid #f0f0f2;
        /*padding: 15px 10px 0;*/
        padding: 15px 0px 0;
    }

    .article-main:hover {
        background-color: #fafafa;
    }


    .article-title a {
        margin-bottom: 10px;
        font-size: 18px;
        font-weight: 700;
        color: #222226;
        overflow: hidden;
        white-space: normal;
        word-break: break-word;
        display: -webkit-box;
        -webkit-box-orient: vertical;
        -webkit-line-clamp: 1;
        line-height: 25px;
    }
    .article-title a span:hover {
        /*color: pink;*/
        text-decoration: underline;
    }

    .article-img-left {
        margin-right: 16px;
    }
    .article-img-left a {
        position: relative;
        border-radius: 2px;
        width: 134px;
        height: 84px;
        display: inline-block;
        background-size: cover;
        background-position: 50%;
        border: 1px solid #f5f6f7;
        overflow: hidden;
        background: #f5f6f7;
    }

    .article-img-left img {
        /*width: 132px;*/
        /*height: 100px;*/
        width: 100%;
        left: 50%;
        top: 50%;
        -webkit-transform: translate(-50%,-50%);
        transform: translate(-50%,-50%);
        height: auto;
        position: absolute;
    }

    /*图片文章并排显示*/
    .article-content-item {
        display: flex;
        padding-bottom: 15px;
    }
    .article-content-right {
        display: flex;
        flex-direction: column;
        justify-content: space-between;
        /*margin-top: 4px;*/
        font-size: 14px;
        font-weight: 400;
        min-height: 6px;
        min-height: 40px;
        color: #555666;
    }
    .article-content-right .article-content {
        color: #555666;
        font-size: 14px;
        font-weight: 400;
        line-height: 22px;
    }

    /*评价*/
    .article-evaluation {
        display: flex;
        align-items: center;
    }
    .article-evaluation img {
        width: 16px;
        height: 16px;
        margin-right: 3px;
    }
    .article-evaluation .article-good {
        display: flex;
        align-items: center;
        margin-right: 30px;
    }
    .article-evaluation .article-author {
        margin-right: 30px;
    }
</style>