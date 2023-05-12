ES创建index脚本：
```es
PUT /blog
{
  "mappings": {
    "properties": {
      "id": {
        "type": "integer"
      },
      "authorId": {
        "type": "integer"
      },
      "title": {
        "type": "text",
        "analyzer": "ik_max_word",
        "copy_to": "descriptiveContent"
      },
      "description": {
        "type": "text",
        "analyzer": "ik_max_word",
        "copy_to": "descriptiveContent"
      },
      "schoolCode": {
        "type": "integer"
      },
      "coverImage": {
        "type": "keyword",
        "index": false
      },
      "createTime": {
        "type": "date",
        "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis||strict_date_optional_time"
      },
      "releaseTime": {
        "type": "date",
        "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis||strict_date_optional_time"
      },
      "modifiedTime": {
        "type": "date",
        "format": "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis||strict_date_optional_time"
      },
      "status": {
        "type": "integer"
      },
      "writeType":{
          "type":"integer"
       },
       "descriptiveContent":{
          "type":"text",
          "analyzer":"ik_max_word"
       }
    }
  }
}
```