<template>
    <div>
      <Table :data="table"></Table>
    </div>
  </template>
  
  <script>
import Table from '@/components/common/table/Table.vue';
  export default {
    data() {
        return {
            table: {
                column: [
                    {
                        prop: "date",
                        label: "日期",
                        width: 180,
                    },
                    {
                        prop: "name",
                        label: "姓名",
                        width: 180,
                    },
                    {
                        prop: "address",
                        label: "地址",
                    },
                ],
                pageNum: 1,
                pageSize: 9,
                total: 6,
                data: [
                    {
                        ID: 1,
                        date: "2016-05-02",
                        name: "王小虎",
                        address: "上海市普陀区金沙江路 1518 弄",
                        tag: "家",
                    },
                    {
                        ID: 2,
                        date: "2016-05-04",
                        name: "王小虎",
                        address: "上海市普陀区金沙江路 1517 弄",
                        tag: "家",
                    },
                    {
                        ID: 3,
                        date: "2016-05-01",
                        name: "王小虎",
                        address: "上海市普陀区金沙江路 1519 弄",
                        tag: "家",
                    },
                    {
                        ID: 4,
                        date: "2016-05-03",
                        name: "王小虎",
                        address: "上海市普陀区金沙江路 1516 弄",
                        tag: "学校",
                    },
                    {
                        ID: 5,
                        date: "2016-05-03",
                        name: "王小虎",
                        address: "上海市普陀区金沙江路 1516 弄",
                        tag: "学校",
                    },
                    {
                        ID: 6,
                        date: "2016-05-03",
                        name: "王小虎",
                        address: "上海市普陀区金沙江路 1516 弄",
                        tag: "学校",
                    },
                ],
            },
        };
    },
    components: { Table }
}
  </script>
  
  <style scoped>
  
  </style>