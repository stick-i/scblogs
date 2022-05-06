<template>
  <div>
    <div class="comment-container">
      <div class="comment-title">评论 <span>3</span></div>
      <div class="comment-edit-box">
        <div class="user-img">
          <img
            src="https://local.sticki.cn/api/v1/resource/avatar/1_stick"
            alt=""
          />
        </div>
        <div class="comment-form">
          <input
            class="comment-content"
            name="comment_content"
            id="comment_content"
            type="text"
            placeholder="请发表有价值的评论， 博客评论不欢迎灌水，良好的社区氛围需大家一起维护。"
          />
          <div class="comment-operate-box">评论</div>
        </div>
      </div>
      <div class="comment-list-container">
        <div class="comment-list-box">
          <ul class="comment-list">
            <!-- 父评论 -->
            <li class="comment-line-box">
              <div class="comment-list-item">
                <img
                  src="https://local.sticki.cn/api/v1/resource/avatar/1_stick"
                  alt=""
                  class="avatar"
                />
                <div class="right-box">
                  <div class="new-info-box">
                    <div class="comment-top">
                      <div class="user-box">
                        <span class="name">取了个名字</span>
                        <span class="date">2022.05.06</span>
                      </div>
                      <div class="comment-btn">
                        <img
                          src="../../../assets/img/blogDetail/blogComment/commentReply.png"
                          alt=""
                        />
                        <span class="btn-reply">回复</span>
                      </div>
                      <div class="comment-like">
                        <img
                          src="../../../assets/img/blogDetail/blogComment/commentLike.png"
                          alt=""
                        />
                        <span class="like-num">1</span>
                      </div>
                    </div>
                    <div class="comment-center">
                      <div class="new-comment">为你点赞！！！</div>
                    </div>
                  </div>
                </div>
              </div>
            </li>
            <!-- 子评论 -->
            <li class="replay-box">
              <ul class="comment-list">
                <li class="comment-line-box">
                  <div class="comment-list-item">
                    <img
                      src="https://local.sticki.cn/api/v1/resource/avatar/1_stick"
                      alt=""
                      class="avatar"
                    />
                    <div class="right-box">
                      <div class="new-info-box">
                        <div class="comment-top">
                          <div class="user-box">
                            <span class="name">取了个名字</span>
                            <span class="date">2022.05.06</span>
                          </div>
                          <div class="comment-btn">
                            <img
                              src="../../../assets/img/blogDetail/blogComment/commentReply.png"
                              alt=""
                            />
                            <span class="btn-reply">回复</span>
                          </div>
                          <div class="comment-like">
                            <img
                              src="../../../assets/img/blogDetail/blogComment/commentLike.png"
                              alt=""
                            />
                            <!--                            <span class="like-num">1</span>-->
                          </div>
                        </div>
                        <div class="comment-center">
                          <div class="new-comment">为你点赞！！！</div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
                <li class="comment-line-box">
                  <div class="comment-list-item">
                    <img
                      src="https://local.sticki.cn/api/v1/resource/avatar/1_stick"
                      alt=""
                      class="avatar"
                    />
                    <div class="right-box">
                      <div class="new-info-box">
                        <div class="comment-top">
                          <div class="user-box">
                            <span class="name">取了个名字</span>
                            <span class="date">2022.05.06</span>
                          </div>
                          <div class="comment-btn">
                            <img
                              src="../../../assets/img/blogDetail/blogComment/commentReply.png"
                              alt=""
                            />
                            <span class="btn-reply">回复</span>
                          </div>
                          <div class="comment-like">
                            <img
                              src="../../../assets/img/blogDetail/blogComment/commentLike.png"
                              alt=""
                            />
                            <!--                            <span class="like-num">1</span>-->
                          </div>
                        </div>
                        <div class="comment-center">
                          <div class="new-comment">为你点赞！！！</div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "",
};
</script>

<style scoped>
/*评论盒子开始*/
.comment-container {
  width: 1070px;
  /*height: 100px;*/
  /*background-color: skyblue;*/
  margin-top: 10px;
  padding: 16px 24px 24px 24px;
  border-radius: 2px;
  background: #fff;
}

/*评论标题开始*/
.comment-title {
  font-size: 18px;
  font-weight: 600;
  color: #222226;
  line-height: 32px;
  margin-bottom: 8px;
}

/*评论标题结束*/

/*写评论开始*/
.comment-edit-box {
  display: flex;
  align-items: center;
}

.comment-edit-box .user-img {
  margin-right: 8px;
  /*padding-top: 9px;*/
}

.comment-edit-box .user-img img {
  display: block;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  border: 1px solid #e8e8ed;
}

.comment-form {
  width: 100%;
  position: relative;
  background: rgba(245, 246, 247, 0.8);
  border-radius: 8px;
  padding: 8px 0;
  display: flex;
}
.comment-form .comment-content {
  width: 100%;
  border: none;
  outline: none;
  padding: 4px 12px;
  height: 30px;
  font-size: 14px;
  line-height: 22px;
  /* -webkit-box-sizing: border-box; */
  box-sizing: border-box;
  background: rgba(245, 246, 247, 0.8);
}
.comment-form .comment-operate-box {
  width: 72px;
  height: 30px;
  margin: 0 10px;
  background: #e1e2e6;
  color: #222226;
  border-radius: 16px;
  font-size: 14px;
  text-align: center;
  line-height: 32px;
  cursor: pointer;
}
.comment-form:focus-within .comment-operate-box {
  color: #ffffff;
  background: #fc5531;
}

/*写评论结束*/

/* 评论显示开始 */
.comment-list-container {
  padding-top: 16px;
}
/* 父评论 */
.comment-list-item {
  display: flex;
  width: 100%;
  /* background-color: pink; */
}
.comment-list-item .avatar {
  display: block;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  border: 1px solid #e8e8ed;
  margin-top: 15px;
  /* padding-top: 15px; */
}
.comment-list-item .right-box {
  border-top: 1px solid #e8e8ed;
  padding-top: 14px;
  padding-bottom: 14px;
  width: 100%;
  margin-left: 8px;
}
.right-box .comment-top {
  display: flex;
  margin-bottom: 4px;
  line-height: 20px;
  font-size: 14px;
}
.right-box .comment-top .user-box {
  display: flex;
  flex: 1;
  align-items: center;
}
.right-box .comment-top .user-box .name {
  display: inline-block;
  max-width: 300px;
  color: #777888;
  margin-right: 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.right-box .comment-top img {
  width: 16px;
  height: 16px;
  vertical-align: middle;
  margin-right: 2px;
}
.right-box .comment-top .comment-btn .btn-reply {
  font-size: 14px;
  color: #555666;
  margin-right: 16px;
}
.right-box .comment-top .comment-like .like-num {
  color: #999aaa;
  vertical-align: middle;
}
.right-box .comment-top .comment-btn,
.right-box .comment-top .comment-like {
  cursor: pointer;
}

.right-box .comment-center {
  font-size: 14px;
  color: #222226;
  line-height: 22px;
  word-break: break-word;
}
.comment-list-item .comment-btn {
  display: none;
}
.comment-list-item:hover .comment-btn {
  display: block;
}

/* 子评论 */
.replay-box {
  padding-left: 32px;
  /* background-color: aqua; */
}
.replay-box .avatar {
  display: block;
  width: 24px;
  height: 24px;
  border-radius: 50%;
  margin-top: 0;
}
.replay-box .comment-list-item .right-box {
  border-top: 0;
  padding-top: 0;
}
/* 评论显示结束 */
/*评论盒子结束*/
</style>
