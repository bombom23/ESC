<template>
  <div>
    <!--  <v-row>
  <v-card
    class="mx-auto my-12"
    width="400"
    v-for="i in paginatedData" :key="i.boardNo"
  >
    <v-card-title>{{i.memberId}}</v-card-title>
    <v-card-title>{{i.title}}</v-card-title>
    <v-card-subtitle>[{{$moment(i.createDate).format('YYYY-MM-DD/hh:mm')}} 조회{{i.viewcount}}]<v-icon small>mdi-thumb-up</v-icon>:{{i.good}},<v-icon small>mdi-thumb-down</v-icon>:{{i.bad}}</v-card-subtitle>
    <v-img v-if="i.img != ''" width="400px" height="350" :src="require(`@/assets/게시판/${i.img}`)"></v-img>
    <v-img v-else-if="i.img == ''" width="400px" height="350" :src="require('@/assets/게시판/사진없음.jpg')"></v-img>
    

    <v-card-text>
    </v-card-text>

    <v-divider class="mx-4"></v-divider>

    <v-card-actions>
      <v-btn
        color="deep-purple lighten-2"
        text
        @click="goDetail(i.boardNo)"
      >
        보기
      </v-btn>
    </v-card-actions>
  </v-card>
  </v-row>
  <div class="btn-cover">
      <button :disabled="pageNum === 0" @click="prevPage" class="page-btn">
        이전
      </button>
      <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }} 페이지</span>
      <button :disabled="pageNum >= pageCount - 1" @click="nextPage" class="page-btn">
        다음
      </button>
    </div> -->
    <div v-if="coin == 0 && this.tableMode == 1">
      <v-btn @click="ModeChange()"><v-icon>mdi-table</v-icon></v-btn>
      <v-container>
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr style="text-align: center;">
                <td>번호</td>
                <td>제목</td>
                <td>글쓴이</td>
                <td><v-icon>mdi-clock-outline</v-icon></td>
                <td><v-icon>mdi-eye</v-icon></td>
                <td><v-icon color="blue">mdi-thumb-up</v-icon></td>
                <td><v-icon color="red">mdi-thumb-down</v-icon></td>
              </tr>
            </thead>
            <tbody>
              <tr
                style="text-align: center;"
                v-for="i in paginatedData"
                :key="i.boardNo"
              >
                <td style="margin-right:50px">{{ i.boardNo }}</td>
                <td @click="goDetail(i.boardNo)">{{ i.title }}</td>
                <td>{{ i.memberId }}</td>
                <td>{{ $moment(i.createDate).format("YYYY-MM-DD/hh") }}</td>
                <td>{{ i.viewcount }}</td>
                <td style="width:70px">{{ i.good }}</td>
                <td style="width:70px">{{ i.bad }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
        <!--페이지네이션 버튼 -->
        <div class="btn-cover">
          <button :disabled="pageNum === 0" @click="prevPage" class="page-btn">
            <v-icon>mdi-arrow-left-bold</v-icon>
          </button>
          <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }}</span>
          <button
            :disabled="pageNum >= pageCount - 1"
            @click="nextPage"
            class="page-btn"
          >
            <v-icon>mdi-arrow-right-bold</v-icon>
          </button>
          <form @keyup.enter="searching(searchMenus, search)">
            <!--  <v-btn style="margin-right: 100%" @click="Write()"><v-icon>mdi-pen-plus</v-icon></v-btn> -->
            <v-row style="margin-left: 80%">
              <v-select
                style="max-width: 100px"
                :items="searchMenu"
                label="검색"
                v-model="searchMenus"
              />
              <v-text-field
                style="max-width: 300px"
                v-model="search"
                label="검색란"
              ></v-text-field>
            </v-row>
          </form>
        </div>
      </v-container>
    </div>

    <div v-else-if="coin == 1 && tableMode == 1">
      <v-btn @click="ModeChange()"><v-icon>mdi-table</v-icon></v-btn>
      <v-container>
        <v-simple-table>
          <template v-slot:default>
            <thead>
              <tr style="text-align: center;">
                <td>번호</td>
                <td>제목</td>
                <td>글쓴이</td>
                <td><v-icon>mdi-clock-outline</v-icon></td>
                <td><v-icon>mdi-eye</v-icon></td>
                <td><v-icon color="blue">mdi-thumb-up</v-icon></td>
                <td><v-icon color="red">mdi-thumb-down</v-icon></td>
              </tr>
            </thead>
            <tbody>
              <tr
                style="text-align: center;"
                v-for="i in searchpaginatedData"
                :key="i.boardNo"
              >
                <td style="margin-right:50px">{{ i.boardNo }}</td>
                <td @click="goDetail(i.boardNo)">{{ i.title }}</td>
                <td>{{ i.memberId }}</td>
                <td>{{ $moment(i.createDate).format("YYYY-MM-DD/hh") }}</td>
                <td>{{ i.viewcount }}</td>
                <td style="width:70px">{{ i.good }}</td>
                <td style="width:70px">{{ i.bad }}</td>
              </tr>
            </tbody>
          </template>
        </v-simple-table>
        <!-- 페이지네이션 버튼 -->
        <div class="btn-cover">
          <button
            :disabled="searchpageNum === 0"
            @click="searchPrevPage"
            class="page-btn"
          >
            <v-icon>mdi-arrow-left-bold</v-icon>
          </button>
          <span class="page-count"
            >{{ searchpageNum + 1 }} / {{ searchpageCount }}</span
          >
          <button
            :disabled="searchpageNum >= searchpageCount - 1"
            @click="searchNextPage"
            class="page-btn"
          >
            <v-icon>mdi-arrow-right-bold</v-icon>
          </button>
          <form @keyup.enter="searching(searchMenus, search)">
            <!--        <v-btn style="margin-right: 100%" @click="Write()"><v-icon>mdi-pen-plus</v-icon></v-btn> -->
            <v-row style="margin-left: 80%">
              <v-select
                style="max-width: 100px"
                :items="searchMenu"
                label="검색"
                v-model="searchMenus"
              />
              <v-text-field
                style="max-width: 300px"
                v-model="search"
                label="검색란"
              ></v-text-field>
            </v-row>
            <v-btn style="margin-left: 90%" @click="showAllBoard()"
              >검색해제</v-btn
            >
          </form>
        </div>
      </v-container>
    </div>

    <div v-else-if="coin == 0 && cardMode == 1">
      <v-btn @click="ModeChange()"
        ><v-icon>mdi-format-list-bulleted</v-icon></v-btn
      >
      <v-row>
        <v-card
          class="mx-auto my-12"
          width="400"
          v-for="i in paginatedData"
          :key="i.boardNo"
          @click="goDetail(i.boardNo)"
          outlined
          hover
        >
          <v-card-title>{{ i.memberId }}</v-card-title>
          <v-divider class="mx-4"></v-divider>
          <v-card-title>{{ i.title }}</v-card-title>
          <v-card-subtitle
            >[{{ $moment(i.createDate).format("YYYY-MM-DD/hh:mm") }} 조회{{
              i.viewcount
            }}]</v-card-subtitle
          >
          <v-img
            v-if="i.img != ''"
            width="400px"
            height="350"
            :src="require(`@/assets/게시판/${i.img}`)"
          ></v-img>
          <v-img
            v-else-if="i.img == ''"
            width="400px"
            height="350"
            :src="require('@/assets/게시판/사진없음.jpg')"
          ></v-img>
          <v-divider class="mx-4"></v-divider>
          <v-card-text>
            <v-icon color="blue" style="margin-left: 77%">mdi-thumb-up</v-icon
            >{{ i.good }}<v-icon color="red">mdi-thumb-down</v-icon>{{ i.bad }}
          </v-card-text>
        </v-card>
      </v-row>
      <!--페이지네이션 버튼 -->
      <div class="btn-cover">
        <button :disabled="pageNum === 0" @click="prevPage" class="page-btn">
          <v-icon>mdi-arrow-left-bold</v-icon>
        </button>
        <span class="page-count">{{ pageNum + 1 }} / {{ pageCount }}</span>
        <button
          :disabled="pageNum >= pageCount - 1"
          @click="nextPage"
          class="page-btn"
        >
          <v-icon>mdi-arrow-right-bold</v-icon>
        </button>
        <form @keyup.enter="searching(searchMenus, search)">
          <!--         <v-btn style="margin-right: 100%" @click="Write()"><v-icon>mdi-pen-plus</v-icon></v-btn> -->
          <v-row style="margin-left: 80%">
            <v-select
              style="max-width: 100px"
              :items="searchMenu"
              label="검색"
              v-model="searchMenus"
            />
            <v-text-field
              style="max-width: 300px"
              v-model="search"
              label="검색란"
            ></v-text-field>
          </v-row>
        </form>
      </div>
    </div>

    <div v-else-if="coin == 1 && cardMode == 1">
      <v-btn @click="ModeChange()"
        ><v-icon>mdi-format-list-bulleted</v-icon></v-btn
      >
      <v-row>
        <v-card
          class="mx-auto my-12"
          width="400"
          v-for="i in searchpaginatedData"
          :key="i.boardNo"
          @click="goDetail(i.boardNo)"
          outlined
          hover
        >
          <v-card-title>{{ i.memberId }}</v-card-title>
          <v-card-title>{{ i.title }}</v-card-title>
          <v-card-subtitle
            >[{{ $moment(i.createDate).format("YYYY-MM-DD/hh:mm") }} 조회{{
              i.viewcount
            }}]</v-card-subtitle
          >
          <v-img
            v-if="i.img != ''"
            width="400px"
            height="350"
            :src="require(`@/assets/게시판/${i.img}`)"
          ></v-img>
          <v-img
            v-else-if="i.img == ''"
            width="400px"
            height="350"
            :src="require('@/assets/게시판/사진없음.jpg')"
          ></v-img>
          <v-divider class="mx-4"></v-divider>
          <v-card-text>
            <v-icon color="blue" style="margin-left: 77%">mdi-thumb-up</v-icon
            >{{ i.good }}<v-icon color="red">mdi-thumb-down</v-icon>{{ i.bad }}
          </v-card-text>
        </v-card>
      </v-row>
      <!--페이지 네이션 버튼 -->
      <div class="btn-cover">
        <button
          :disabled="searchpageNum === 0"
          @click="searchPrevPage"
          class="page-btn"
        >
          <v-icon>mdi-arrow-left-bold</v-icon>
        </button>
        <span class="page-count"
          >{{ searchpageNum + 1 }} / {{ searchpageCount }}</span
        >
        <button
          :disabled="searchpageNum >= searchpageCount - 1"
          @click="searchNextPage"
          class="page-btn"
        >
          <v-icon>mdi-arrow-right-bold</v-icon>
        </button>
        <form @keyup.enter="searching(searchMenus, search)">
          <!--         <v-btn style="margin-right: 100%" @click="Write()"><v-icon>mdi-pen-plus</v-icon></v-btn> -->
          <v-row style="margin-left: 80%">
            <v-select
              style="max-width: 100px"
              :items="searchMenu"
              label="검색"
              v-model="searchMenus"
            />
            <v-text-field
              style="max-width: 300px"
              v-model="search"
              label="검색란"
            ></v-text-field>
          </v-row>
          <v-btn style="margin-left: 90%" @click="showAllBoard()"
            >검색해제</v-btn
          >
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import Vue from "vue";
import VueMoment from "vue-moment";
import axios from "axios";
import { mapActions, mapState } from "vuex";
Vue.use(VueMoment);
export default {
  name: "paginated-list",
  data() {
    return {
      //tableMode: 1,
      //cardMode: 0,
      searchMenus: "",
      pageNum: 0,
      searchpageNum: 0,
      search: "",
      ip: "",
      coin: 0,
      searchList: [],
      searchMenu: [{ text: "글제목", value: "글제목" }],
      selectedItem: 1,
    };
  },
  props: {
    myBoardList: {
      type: Array,
      required: true,
    },
    pageSize: {
      type: Number,
      required: false,
      default: 10,
    },
  },
  methods: {
    ...mapActions(["SetTableMode", "SetCardMode"]),
    nextPage() {
      this.pageNum += 1;
    },
    prevPage() {
      this.pageNum -= 1;
    },
    goDetail(boardNo) {
      this.$router.push({ name: "BoardReadPage", params: { boardNo } });

      axios.post(`https://evsbackend.herokuapp.com/board/${boardNo}`).then(() => {});
    },
    reserve() {
      this.loading = true;
      setTimeout(() => (this.loading = false), 2000);
    },
    searching(searchMenus, search) {
      if (searchMenus == "") {
        alert("카테고리를 선택해주세요");
      }
      if (searchMenus == "글제목") {
        this.searchList.length = 0;
        console.log("동작");
        axios
          .post(`https://evsbackend.herokuapp.com/board/titleSearchList/${search}`)
          .then((res) => {
            if (res.data == "") {
              alert("해당검색어로 검색되는 글이 존재하지않습니다.");
            } else {
              (this.searchList = res.data), (this.coin = 1);
            }
          });
      }
    },
    showAllBoard() {
      this.coin = 0;
    },
    ModeChange() {
      /*
     if(this.cardMode == 0){
       this.cardMode =1;
     }else{
       this.cardMode = 0
     }
     if(this.tableMode == 1){
       this.tableMode =0;
     }else{
       this.tableMode = 1
     }*/
      if (this.$store.state.tableMode == 1) {
        Vue.$cookies.remove("TableMode");
        Vue.$cookies.remove("CardMode");
        this.SetTableMode(2);
        this.SetCardMode(1);
        Vue.$cookies.set("TableMode", 2, "1h");
        Vue.$cookies.set("CardMode", 1, "1h");
        console.log(this.tableMode);
        console.log(this.cardMode);
      } else {
        Vue.$cookies.remove("TableMode");
        Vue.$cookies.remove("CardMode");
        this.SetTableMode(1);
        this.SetCardMode(2);
        Vue.$cookies.set("TableMode", 1, "1h");
        Vue.$cookies.set("CardMode", 2, "1h");
      }
    },
  },

  computed: {
    ...mapState(["tableMode", "cardMode"]),
    pageCount() {
      let listLeng = this.myBoardList.length,
        listSize = this.pageSize,
        page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;

      return page;
    },
    searchpageCount() {
      let listLeng = this.searchList.length,
        listSize = this.pageSize,
        page = Math.floor(listLeng / listSize);
      if (listLeng % listSize > 0) page += 1;

      return page;
    },
    paginatedData() {
      const start = this.pageNum * this.pageSize,
        end = start + this.pageSize;
      return this.myBoardList.slice(start, end);
    },
    searchpaginatedData() {
      const start = this.searchpageNum * this.pageSize,
        end = start + this.pageSize;
      return this.searchList.slice(start, end);
    },
  },
  created() {
    if (this.$store.state.tableMode == null) {
      this.SetTableMode();
      this.SetCardMode();
    } else {
      this.SetTableMode(Vue.$cookies.get("TableMode"));
      this.SetCardMode(Vue.$cookies.get("CardMode"));
    }
  },
};
</script>

<style>
table {
  width: 100%;
  border-collapse: collapse;
}
table th {
  font-size: 1.2rem;
}
table tr {
  height: 2rem;
  text-align: center;
  border-bottom: 1px solid #505050;
}
table tr:first-of-type {
  border-top: 2px solid #404040;
}
table tr td {
  padding: 1rem 0;
  font-size: 1.1rem;
}
.btn-cover {
  margin-top: 1.5rem;
  text-align: center;
}
.btn-cover .page-btn {
  width: 5rem;
  height: 2rem;
  letter-spacing: 0.5px;
}
.btn-cover .page-count {
  padding: 0 1rem;
}
</style>
