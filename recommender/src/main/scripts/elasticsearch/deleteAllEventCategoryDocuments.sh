curl -XDELETE 'http://localhost:9200/events/category/_query' -d '
{
  "query": {
    "bool": {
      "must": [
        {
          "match_all": {}
        }
      ]
    }
  }
}
'
