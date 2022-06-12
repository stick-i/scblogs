<template>
  <div>
    <div class="comment-container">
<!--			{{this.commentList}}-->
      <div class="comment-title">
        评论 <span>{{ commentList.allCount }}</span>
      </div>
      <div class="comment-edit-box">
        <div class="user-img">
          <img :src="avatarUrl" alt="" />
        </div>
        <div class="comment-form">
          <input
            class="comment-content"
            name="comment_content"
            id="comment_content"
            type="text"
            v-model="comment.content"
            placeholder="请发表有价值的评论，博客评论不欢迎灌水，良好的社区氛围需大家一起维护。"
          />
          <div class="comment-operate-box" @click="firstComment()">评论</div>
        </div>
      </div>
      <div class="comment-list-container">
        <div class="comment-list-box">
          <ul
            class="comment-list"
            v-for="(item, index) in commentList.records"
            :key="index"
          >
            <!-- 父评论 -->
            <li class="comment-line-box">
              <div class="comment-list-item">
                <img :src="item.info.avatarUrl" alt="" class="avatar" />
                <div class="right-box">
                  <div class="new-info-box">
                    <div class="comment-top">
                      <div class="user-box">
                        <span class="name">{{ item.info.nickname }}</span>
                        <span class="date">{{ item.info.createTime }}</span>
                      </div>
                      <div class="comment-btn">
                        <div
                          class="delete"
                          @click="deleteComment(item.info.id)"
                        >
                          删除
                        </div>
                        <div @click="showFirstEditBox(item.info.id)">
                          <img
                            src="../../../assets/img/blogDetail/blogComment/commentReply.png"
                            alt=""
                          />
                          <span class="btn-reply">回复</span>
                        </div>
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
                      <div class="new-comment">{{ item.info.content }}</div>
                    </div>
                  </div>
                </div>
              </div>
              <!-- 评论input开始 -->
              <div class="first-edit-box" v-if="item.info.id == firstId">
                <div class="comment-edit-box">
                  <div class="user-img">
                    <img :src="avatarUrl" alt="" />
                  </div>
                  <div class="comment-form">
                    <input
                      class="comment-content"
                      name="comment_content"
                      type="text"
                      placeholder="输入评论..."
                      v-model="childComment.content"
                    />
                    <div
                      class="comment-operate-box"
                      @click="secondComment(item.info.id)"
                    >
                      评论
                    </div>
                  </div>
                </div>
              </div>
              <!-- 评论input结束 -->
            </li>
            <!-- 子评论 -->
            <li
              class="replay-box"
              v-for="(child, index) in item.sub"
              :key="index"
            >
              <ul class="comment-list">
                <li class="comment-line-box">
                  <div class="comment-list-item">
                    <img :src="child.avatarUrl" alt="" class="avatar" />
                    <div class="right-box">
                      <div class="new-info-box">
                        <div class="comment-top">
                          <div class="user-box">
                            <span class="name">{{ child.nickname }}</span>
                            <span class="text">回复</span>
                            <span class="nick-name">{{
                              child.parentNickname
                            }}</span>
                            <span class="date">{{ child.createTime }}</span>
                          </div>
                          <div class="comment-btn">
                            <div
                              class="delete"
                              @click="deleteComment(child.id)"
                            >
                              删除
                            </div>
                            <div @click="showSecondEditBox(child.id)">
                              <img
                                src="../../../assets/img/blogDetail/blogComment/commentReply.png"
                                alt=""
                              />
                              <span class="btn-reply">回复</span>
                            </div>
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
                          <div class="new-comment">{{ child.content }}</div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- 评论input开始 -->
                  <div class="first-edit-box" v-if="child.id == secondId">
                    <div class="comment-edit-box">
                      <div class="user-img">
                        <img :src="avatarUrl" alt="" />
                      </div>
                      <div class="comment-form">
                        <input
                          class="comment-content"
                          name="comment_content"
                          type="text"
                          placeholder="输入评论..."
                          v-model="childComment2.content"
                        />
                        <div
                          class="comment-operate-box"
                          @click="thirdComment(child.id)"
                        >
                          评论
                        </div>
                      </div>
                    </div>
                  </div>
                  <!-- 评论input结束 -->
                </li>
              </ul>
            </li>
          </ul>
        </div>
        <!-- 分页开始 -->
        <div class="pagination-box" v-if="commentList.records != null">
          <el-pagination
            background
            layout="prev, pager, next"
            :current-page="commentList.current"
            :page-size="commentList.size"
            :total="commentList.total"
            @current-change="page1"
          >
          </el-pagination>
        </div>
        <!-- 分页结束 -->
      </div>
    </div>
<!--    <div>123</div>-->
  </div>
</template>

<script>
import qs from "qs";

export default {
  name: "",
  props: ["facomment"],
  data() {
    return {
      // 头像
      avatarUrl: "",
      // 父评论
      comment: {
        blogId: this.$route.params.blogId,
        content: "",
        parentId: "",
      },
      // 子评论
      childComment: {
        blogId: this.$route.params.blogId,
        content: "",
        parentId: "",
      },
      childComment2: {
        blogId: this.$route.params.blogId,
        content: "",
        parentId: "",
      },
      firstId: null,
      secondId: null,
      // 分页
      blogId: this.$route.params.blogId,
      page: "2",
      pageSize: "3",
      // 评论列表
      commentList:{},
    };
  },
	mounted() {
	},
	created() {
    this.avatarUrl = window.localStorage.avatarUrl;
    this.getCommentList();
  },
  methods: {
    showFirstEditBox(id) {
      this.firstId = id;
    },
    showSecondEditBox(id) {
      this.secondId = id;
    },
		// 展示评论列表
		getCommentList() {
			this.$axios
				.get("/comment/list", {
					params: {
						blogId: this.blogId,
						page: 1,
						pageSize: this.pageSize,
					},
				})
				.then((res) => {
					// console.log(res.data.data)
					this.commentList = res.data.data
				});
		},
    // 发布一级评论
    firstComment() {
      this.$axios
        .post("/comment", this.comment, {
          headers: { token: localStorage.getItem("token") },
        })
        .then((res) => {
          console.log(res);
          this.comment.content = "";
          // 更新评论列表
          // this.$emit("func");
					this.getCommentList()
        });

    },
    // 发布二级评论
    secondComment(parentId) {
      this.childComment.parentId = parentId;
      this.$axios
        .post("/comment", this.childComment, {
          headers: { token: localStorage.getItem("token") },
        })
        .then((res) => {
          console.log(res);
          this.childComment.content = "";
          // 更新评论列表
          // this.$emit("func");
					this.getCommentList()
        });
    },
    // 发布三级评论
    thirdComment(parentId) {
      this.childComment2.parentId = parentId;
      this.$axios
        .post("/comment", this.childComment2, {
          headers: { token: localStorage.getItem("token") },
        })
        .then((res) => {
          console.log(res);
          this.childComment2.content = "";
          // 更新评论列表
          // this.$emit("func");
					this.getCommentList()
        });
    },
    // 删除评论
    deleteComment(id) {
      this.$axios
        .delete("/comment", {
          params: { id: id },
          headers: { token: localStorage.getItem("token") },
        })
        .then((res) => {
          console.log("删除", res);
					this.getCommentList()
          // this.$emit("func");
        });
    },
    // 分页
    page1(page) {
      this.$axios
        .get("/comment/list", {
          params: {
            blogId: this.blogId,
            page: page,
            pageSize: this.pageSize,
          },
        })
        .then((res) => {
					this.commentList = res.data.data
          // console.log(res)
					// this.getCommentList()
          // this.$emit("recordsChange", res.data.data);
        });
    },
  },
};
</script>

<style scoped>
/*评论盒子开始*/
.comment-container {
  width: 1070px;
  /*height: 100px;*/
  /*background-color: skyblue;*/
  margin-top: 10px;
  margin-bottom: 10px;
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

/* 回复一级评论开始 */
.first-edit-box {
  padding-left: 40px;
  padding-bottom: 14px;
}
/* 回复一级评论结束 */

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
  border-radius: 6px;
  font-size: 14px;
  text-align: center;
  line-height: 32px;
  cursor: pointer;
}
.comment-form:focus-within .comment-operate-box {
  color: #ffffff;
  background: #16a0f8;
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
.right-box .comment-top .user-box .date {
  font-size: 14px;
  color: #777888;
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

.comment-list-item .comment-btn .delete {
  color: #999aaa;
  margin-right: 10px;
}
.comment-list-item .comment-btn {
  display: none;
}
.comment-list-item:hover .comment-btn {
  display: flex;
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
.replay-box .right-box .comment-top .user-box .name {
  margin-right: 0;
}
.replay-box .right-box .comment-top .user-box .text {
  color: #999aaa;
  margin: 0 6px;
}
.replay-box .right-box .comment-top .user-box .nick-name {
  color: #777888;
  margin-right: 10px;
}
/* 评论显示结束 */

/* 分页开始 */
.pagination-box {
  text-align: center;
}
.comment-list-container
  .pagination-box
  .el-pagination
  .is-background
  .el-pager
  li:not(.disabled).active {
  background-color: #555666;
}

/* 分页结束 */

/*评论盒子结束*/
</style>
