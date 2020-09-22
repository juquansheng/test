package com.malaxiaoyugan.test.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.malaxiaoyugan.test.vo.QueryEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * description: ESClientService
 * date: 2020/9/17 10:19
 * author: juquansheng
 * version: 1.0 <br>
 */
@Service
@Slf4j
public class ESClientService {

    @Resource
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ObjectMapper mapper;

    /**
     * 创建索引
     * @param indexName
     * @param settings
     * @param mapping
     * @return
     * @throws IOException
     */
    public CreateIndexResponse createIndex(String indexName, String settings, String mapping) throws IOException {
        CreateIndexRequest request = new CreateIndexRequest(indexName);
        if (null != settings && !"".equals(settings)) {
            request.settings(settings, XContentType.JSON);
        }
        if (null != mapping && !"".equals(mapping)) {
            request.mapping(mapping, XContentType.JSON);
        }
        if (null != indexName && !"".equals(indexName)) {//别名
            request.alias(new Alias(indexName));
        }
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);

        // 6、处理响应
        boolean acknowledged = createIndexResponse.isAcknowledged();
        boolean shardsAcknowledged = createIndexResponse
                .isShardsAcknowledged();
        System.out.println("acknowledged = " + acknowledged);
        System.out.println("shardsAcknowledged = " + shardsAcknowledged);

        return createIndexResponse;

    }

    /**
     * 判断 index 是否存在
     */
    public boolean indexExists(String indexName) throws IOException {
        GetIndexRequest request = new GetIndexRequest(indexName);
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 搜索
     */
    public SearchResponse search(String field, String key, String rangeField, String
            from, String to, String termField, String termVal,
                                 String ... indexNames) throws IOException{
        //创建搜索请求对象SearchRequest，设置查询的指定索引和类型
        SearchRequest request = new SearchRequest(indexNames);
        //request.types("type");


        // 设置高亮,使用默认的highlighter高亮器
        HighlightBuilder highlightBuilder = createHighlightBuilder(key);
        //创建搜索内容对象SearchSourceBuilder
        SearchSourceBuilder builder = new SearchSourceBuilder();
        /*//创建查询对象MatchQueryBuilder，以及MatchQueryBuilder对象的配置
        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(field, key);
        //启动模糊查询
        matchQueryBuilder.fuzziness(Fuzziness.AUTO);
        //设置最大扩展选项以控制查询的模糊过程
        matchQueryBuilder.maxExpansions(10);
        //将查询对象MatchQueryBuilder添加到搜索内容对象SearchSourceBuilder中，以及SearchSourceBuilder对象的配置
        builder.query(matchQueryBuilder);
        //高亮
        builder.highlighter(highlightBuilder);
        //设置查询的起始索引位置
        builder.from(0);
        //设置查询的数量
        builder.size(5);
        //设置超时时间
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //将搜索内容对象SearchSourceBuilder添加到搜索请求对象SearchRequest中
        request.source(builder);
        log.info("[搜索语句为:{}]",request.source().toString());
        //发送搜索请求
        SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
        return searchResponse;*/
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        //boolQueryBuilder.must(new MatchQueryBuilder(field, key)).must(new RangeQueryBuilder(rangeField).from(from).to(to)).must(new TermQueryBuilder(termField, termVal));
        boolQueryBuilder.must(new MatchQueryBuilder(field, key)).must(new RangeQueryBuilder(rangeField).from(from).to(to));
        //boolQueryBuilder.must(QueryBuilders.moreLikeThisQuery(new String[]{field}, new String[]{key}, null));
        //boolQueryBuilder.must(new MatchQueryBuilder(field, key));
        builder.query(boolQueryBuilder).highlighter(highlightBuilder);
        request.source(builder);
        log.info("[搜索语句为:{}]",request.source().toString());
        return restHighLevelClient.search(request, RequestOptions.DEFAULT);
    }


    /**
     * 拼装查询条件
     * @param query
     * @return
     */
    public static BoolQueryBuilder buildEsParam(QueryEntity query) {

        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();

        //filter 效率比 must高
        if (StringUtils.isNotBlank(query.getName())) {
            queryBuilder.filter(QueryBuilders.termQuery("name", query.getName()));
        }

        //时间段要有头有尾 不然会出现慢查询
        if (null != query.getStartTime() && null != query.getEndTime()) {
            queryBuilder.filter(QueryBuilders.rangeQuery("createTime").from( query.getStartTime()).to(query.getEndTime()));
        }

        return queryBuilder;
    }

    /**
     * 批量导入
     * @param indexName
     * @param isAutoId 使用自动id 还是使用传入对象的id
     * @param source
     * @return
     * @throws IOException
     */
    public BulkResponse importAll(String indexName, boolean isAutoId, String  source) throws IOException{
        if (0 == source.length()){
            //todo 抛出异常 导入数据为空
        }
        BulkRequest request = new BulkRequest();
        JsonNode jsonNode = mapper.readTree(source);

        if (jsonNode.isArray()) {
            for (JsonNode node : jsonNode) {
                if (isAutoId) {
                    request.add(new IndexRequest(indexName).source(node.asText(), XContentType.JSON));
                } else {
                    request.add(new IndexRequest(indexName)
                            .id(node.get("id").asText())
                            .source(node.asText(), XContentType.JSON));
                }
            }
        }
        return restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
    }


    /**
     * 构造查询条件
     * @auther: zhoudong
     * @date: 2018/12/18 10:42
     */
    private QueryBuilder createQueryBuilder(String keyword, String... fieldNames){
        // 构造查询条件,使用标准分词器.
        return QueryBuilders.multiMatchQuery(keyword,fieldNames)   // matchQuery(),单字段搜索
                .analyzer("ik_max_word")
                .operator(Operator.OR);
    }
    /**
     * 构造高亮器
     * @auther: zhoudong
     * @date: 2018/12/18 10:44
     */
    private HighlightBuilder createHighlightBuilder(String... fieldNames){
        // 设置高亮,使用默认的highlighter高亮器
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                // .field("productName")
                .preTags("<span style='color:red'>")
                .postTags("</span>");

        // 设置高亮字段
        for (String fieldName: fieldNames) highlightBuilder.field(fieldName);

        return highlightBuilder;
    }

    /**
     * 处理高亮结果
     * @auther: zhoudong
     * @date: 2018/12/18 10:48
     */
    private List<Map<String,Object>> getHitList(SearchHits hits){
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map;
        for(SearchHit searchHit : hits){
            map = new HashMap<>();
            // 处理源数据
            map.put("source",searchHit.getSourceAsMap());
            // 处理高亮数据
            Map<String,Object> hitMap = new HashMap<>();
            searchHit.getHighlightFields().forEach((k,v) -> {
                String hight = "";
                for(Text text : v.getFragments()) hight += text.string();
                hitMap.put(v.getName(),hight);
            });
            map.put("highlight",hitMap);
            list.add(map);
        }
        return list;
    }


}
