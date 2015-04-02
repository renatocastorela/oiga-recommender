curl -XDELETE 'http://localhost:9200/events/event/_query' -d '
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
