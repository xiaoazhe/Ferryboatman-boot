package com.ferry.blog.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ferry.admin.config.HunterConfigTemplate;
import com.ferry.admin.util.ImageDownloadUtil;
import com.ferry.blog.dto.ReptileRequest;
import com.ferry.blog.service.BlogService;
import com.ferry.blog.service.ReptileService;
import com.ferry.core.file.util.IdWorker;
import com.ferry.server.blog.entity.BlBlog;
import com.ferry.server.blog.entity.BlType;
import com.ferry.server.blog.mapper.BlTypeMapper;
import me.zhyd.hunter.config.HunterConfig;

import me.zhyd.hunter.entity.ImageLink;
import me.zhyd.hunter.entity.VirtualArticle;
import me.zhyd.hunter.processor.BlogHunterProcessor;
import me.zhyd.hunter.processor.HunterProcessor;
import me.zhyd.hunter.util.HunterPrintWriter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @Author: 摆渡人
 * @Date: 2022/10/29
 */
@Service
public class ReptileServiceImpl implements ReptileService {

    @Resource
    private BlogService blogService;

    @Resource
    private BlTypeMapper typeMapper;

    @Resource
    private IdWorker idWorker;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void crawlSingle(Long typeId, String url, boolean convertImg, PrintWriter writer) {
        HunterPrintWriter writerUtil = new HunterPrintWriter(writer);

        HunterProcessor hunter = new BlogHunterProcessor(url, convertImg, writerUtil);
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        this.saveReptileBlog(typeId, hunter.getConfig(), writerUtil, list);

        writerUtil.shutdown();
    }

    @Override
    public void bringBackList(ReptileRequest request, PrintWriter writer) {

        if (Objects.isNull(request.getUserId()) || Objects.isNull(request.getTitle()) || Objects.isNull(request.getTypeId())) {
            return;
        }

        HunterConfig config = buildConfig(request);

        if (Objects.isNull(config)) {
            throw new RuntimeException("该地址未解析");
        }


        HunterPrintWriter writerUtil = new HunterPrintWriter(writer);
        long start = System.currentTimeMillis();
        HunterProcessor hunter = new BlogHunterProcessor(config, writerUtil, UUID.randomUUID().toString());
        CopyOnWriteArrayList<VirtualArticle> list = hunter.execute();
        if (CollectionUtils.isEmpty(list)) {
            writerUtil.print(String.format("未抓取到任何内容，请确保连接[<a href=\"%s\" target=\"_blank\">%s</a>]是否正确并能正常访问！共耗时 %s ms.", config.getEntryUrls().get(0), config.getEntryUrls().get(0), (System.currentTimeMillis() - start))).shutdown();
            return;
        }
        writerUtil.print("Congratulation ! 此次共整理到" + list.size() + "篇文章");
        saveReptileBlog(request.getTypeId(), config, writerUtil, list);

        writerUtil.print(String.format("搬家完成！耗时 %s ms.", (System.currentTimeMillis() - start)));
        writerUtil.shutdown();
    }


    private HunterConfig buildConfig(ReptileRequest request) {

        HunterConfig config = HunterConfigTemplate.getHunterConfig(request.getTitle());
//        String conf = platformConfig.replaceAll("\\{uid}", request.getUserId());
////        Map<String, HunterConfig> configMap = JSONObject.parseObject(conf, Map.class);
//
//        HunterConfig config = JSONObject.parseObject(conf, HunterConfig.class);
//        if (Objects.isNull(config)) {
//            return null;
//        }
//
//        Map<String, Map<String, String>> headerMap = JSONObject.parseObject(HEADERS.replaceAll("\\{uid}", request.getUserId()), Map.class);
//        Map<String, String> configHeader = JSONObject.parseObject(String.valueOf(headerMap.get(request.getTitle())), Map.class);
//
//        Map<String, String> headers = new HashMap<>();
//        configHeader.forEach((k,v)-> {
//            headers.put(k, v);
//        });
//
//        config.setHeaders(headers);
//
//        if (Objects.nonNull(config.getEntryUrls())) {
//            config.setEntryUrls(String.valueOf(config.getEntryUrls()));
//        }
        config.setUid(request.getUserId());
        config.setCount(request.getCount());
        return config;
    }


    private void saveReptileBlog(Long typeId, HunterConfig config, HunterPrintWriter writerUtil, CopyOnWriteArrayList<VirtualArticle> list) {

        for (VirtualArticle spiderVirtualArticle : list) {
            this.buildBlog(typeId, config.isConvertImg(), writerUtil, spiderVirtualArticle);
        }
    }

    private void buildBlog(Long typeId, boolean isConvertImg, HunterPrintWriter writerUtil, VirtualArticle virtualArticle) {
        BlBlog blog = new BlBlog();
        blog.setTitle(virtualArticle.getTitle());
        blog.setContent(isConvertImg ? parseImgForHtml(virtualArticle, writerUtil) : virtualArticle.getContent());
        blog.setFileUid(CollectionUtils.isEmpty(virtualArticle.getImageLinks()) ? null : new ArrayList<>(virtualArticle.getImageLinks()).get(0).getSrcLink());

        blog.setTypeId(String.valueOf(typeId));
        BlType type = typeMapper.selectById(typeId);
        blog.setTypeName(type.getName());
        blog.setTypeId(String.valueOf(type.getId()));
        blog.setId(idWorker.nextId() + "");
        blog.setCreateTime(new Date());
        blog.setAuthor(virtualArticle.getAuthor());
        blog.setSummary(virtualArticle.getDescription());
        blogService.save(blog);
        writerUtil.print(String.format("[ save ] Succeed! <a href=\"%s\" target=\"_blank\">%s</a>", virtualArticle.getSource(), blog.getTitle()));
    }

    private String parseImgForHtml(VirtualArticle spiderVirtualArticle, HunterPrintWriter writerUtil) {
        if (null == spiderVirtualArticle) {
            return null;
        }
        String source = spiderVirtualArticle.getSource();
        Set<ImageLink> imageLinks = spiderVirtualArticle.getImageLinks();
        String html = spiderVirtualArticle.getContent();
        if (!CollectionUtils.isEmpty(imageLinks)) {
            writerUtil.print(String.format("检测到存在%s张图片，即将转存图片到云存储服务器中...", imageLinks.size()));
            Iterator<ImageLink> it = imageLinks.iterator();
            while (it.hasNext()) {
                ImageLink imageLink = it.next();
                String resImgPath = null;
                try {
                    resImgPath = ImageDownloadUtil.saveToCloudStorage(imageLink.getSrcLink(), source);
                    if (StringUtils.isEmpty(resImgPath)) {
                        writerUtil.print("图片转存失败，请确保云存储已经配置完毕！请查看控制台详细错误信息...");
                        continue;
                    }
                } catch (Exception e) {
                    writerUtil.print(e.getMessage());
                    it.remove();
                    continue;
                }
                html = html.replace(imageLink.getSrcLink(), resImgPath);
                writerUtil.print(String.format("<a href=\"%s\" target=\"_blank\">原图片</a> 已经转存到 <a href=\"%s\" target=\"_blank\">云存储</a>...", imageLink.getSrcLink(), resImgPath));
            }
        }
        return html;
    }

    /**
     * csdn cnblogs oschina可以
     * imooc 无图片 有查看更多拦截
     * iteye 批量不行 单个能解析 貌似有问题
     * juejin 批量 单个支持不了了
     * v2ex 打不开
     * jianshu 滑块拦截
     */
    private final static String HEADERS = "{\n" +
            "    \"imooc\":{\n" +
            "        \"Host\":\"www.imooc.com\",\n" +
            "        \"Referer\":\"https://www.imooc.com\"\n" +
            "    },\n" +
            "    \"csdn\":{\n" +
            "        \"Host\":\"blog.csdn.net\",\n" +
            "        \"Referer\":\"https://blog.csdn.net/{uid}/article/list/1\"\n" +
            "    },\n" +
            "    \"iteye\":{\n" +
            "        \"Host\":\"www.iteye.com/blog/user/{uid}\",\n" +
            "        \"Referer\":\"https://www.iteye.com/blog/user/{uid}\"\n" +
            "    },\n" +
            "    \"cnblogs\":{\n" +
            "        \"Host\":\"www.cnblogs.com\",\n" +
            "        \"Referer\":\"https://www.cnblogs.com/\"\n" +
            "    },\n" +
            "    \"juejin\":{\n" +
            "        \"Host\":\"juejin.im\",\n" +
            "        \"Referer\":\"https://juejin.im\"\n" +
            "    },\n" +
            "    \"v2ex\":{\n" +
            "        \"Host\":\"www.v2ex.com\",\n" +
            "        \"Referer\":\"https://www.v2ex.com\"\n" +
            "    }\n" +
            "    \"v2ex\":{\n" +
            "        \"Host\":\"my.oschina.net\",\n" +
            "        \"Referer\":\"https://my.oschina.net\"\n" +
            "    }\n" +
            "    \"jianshu\":{\n" +
            "        \"Host\":\"www.jianshu.com\",\n" +
            "        \"Referer\":\"https://www.jianshu.com/p/{uid}\"\n" +
            "    }\n" +
            "}";
    private final static String CONFIG = "{\n" +
            "  \"imooc\": {\n" +
            "    \"domain\": \"www.imooc.com\",\n" +
            "    \"titleRegex\": \"//span[@class=js-title]/html()\",\n" +
            "    \"authorRegex\": \"//div[@class=name_con]/p[@class=name]/a[@class=nick]/html()\",\n" +
            "    \"releaseDateRegex\": \"//div[@class='dc-profile']/div[@class='l']/span[@class='spacer']/text()\",\n" +
            "    \"contentRegex\": \"//div[@class=detail-content]/html()\",\n" +
            "    \"tagRegex\": \"//div[@class=cat-box]/div[@class=cat-wrap]/a[@class=cat]/html()\",\n" +
            "    \"descriptionRegex\": \"//meta[@name=Description]/@content\",\n" +
            "    \"targetLinksRegex\": \"/article/[0-9]{1,10}\",\n" +
            "    \"header\": [\n" +
            "      \"Host=www.imooc.com\",\n" +
            "      \"Referer=https://www.imooc.com\"\n" +
            "    ],\n" +
            "    \"entryUrls\": [\n" +
            "      \"https://www.imooc.com/u/{uid}/articles?page=1\"\n" +
            "    ]\n" +
            "  },\n" +
            "  \"csdn\": {\n" +
            "    \"domain\": \"blog.csdn.net\",\n" +
            "    \"titleRegex\": \"//h1[@class=title-article]/html()\",\n" +
            "    \"authorRegex\": \"//a[@class=follow-nickName]/html()\",\n" +
            "    \"releaseDateRegex\": \"//div[@class=article-bar-top]/span[@class=time]/html()\",\n" +
            "    \"contentRegex\": \"//div[@id=content_views]/html()\",\n" +
            "    \"tagRegex\": \"//span[@class=artic-tag-box]/a[@class=tag-link]/html()\",\n" +
            "    \"targetLinksRegex\": \".*blog\\\\.csdn\\\\.net/{uid}/article/details/[0-9a-zA-Z]{1,15}\",\n" +
            "    \"header\": [\n" +
            "      \"Host=blog.csdn.net\",\n" +
            "      \"Referer=https://blog.csdn.net/{uid}/article/list/1\"\n" +
            "    ],\n" +
            "    \"cookie\": \"uuid_tt_dd=10_10331769530-1547536548454-504065; __yadk_uid=eg5NQPFTcIj2VFX6xv3ZJR5C8Q6PVnhm; smidV2=201901161027267de8378708fa178ab707894a70a126f100f32016b8489dd20; UN=u011197448; _ga=GA1.2.1772643969.1548209590; UM_distinctid=16882db136258b-0ce8092de75b71-6655742e-13c680-16882db1363437; gr_user_id=adf433f5-b683-45d5-80dd-caf4b7110360; _dg_id.40e39cb6d36d5282.c482=d846039aa9f3ff23%7C%7C%7C1548746364%7C%7C%7C1%7C%7C%7C1548746364%7C%7C%7C1548746364%7C%7C%7C%7C%7C%7Cf00a97bae3087c52%7C%7C%7Chttps%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DvtlNP9hvgRV6hAa9E1qaewZSIyCGR03ISdsDpTn-zP6muC9Cyop5IucCWeBKKtany7DrcUXgLmy83PTI98aNbSymNKzXgMUYl_c8xbxdt_W%26wd%3D%26eqid%3D85e43a6f000de5ab000000025c4ffe71%7C%7C%7Chttps%3A%2F%2Fwww.baidu.com%2Flink%3Furl%3DvtlNP9hvgRV6hAa9E1qaewZSIyCGR03ISdsDpTn-zP6muC9Cyop5IucCWeBKKtany7DrcUXgLmy83PTI98aNbSymNKzXgMUYl_c8xbxdt_W%26wd%3D%26eqid%3D85e43a6f000de5ab000000025c4ffe71%7C%7C%7C1%7C%7C%7Cundefined; pt_7cd998c4=uid=XsOJJs2ynt2SEUray9/meA&nid=1&vid=skmHxYQg4a0C8dk8c5hQuA&vn=1&pvn=1&sact=1548746403744&to_flag=0&pl=lW7Wzh7yRjgqeNTTgGYbFw*pt*1548746363475; ADHOC_MEMBERSHIP_CLIENT_ID1.0=c85c851e-bf2b-2e69-9332-5ef1b69d869d; BT=1551156352906; Hm_ct_6bcd52f51e9b3dce32bec4a3997715ac=1788*1*PC_VC!5744*1*u011197448!6525*1*10_10331769530-1547536548454-504065; dc_session_id=10_1553784106452.549598; bdshare_firstime=1555313012400; acw_tc=2760823b15578883310602927e32b811a6a04228c70de517201efc5c6a91ba; SESSION=a5fb6772-59f5-441a-9728-9d485c859155; dc_tos=prksrn; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1557888343,1557888344,1557910597,1557974435; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1557974435; c-login-auto=5; acw_sc__v3=5cdcd164b6b2dd555c665f17642d63ea924e82cf; acw_sc__v2=5cdcd15e0d1889a5308a8f22e3c72110f788dbce\",\n" +
            "    \"entryUrls\": [\n" +
            "      \"https://blog.csdn.net/{uid}/article/list/1\"\n" +
            "    ]\n" +
            "  },\n" +
            "  \"iteye\": {\n" +
            "    \"domain\": \"{uid}.iteye.com\",\n" +
            "    \"titleRegex\": \"//div[@class=blog_title]/h3/a/html()\",\n" +
            "    \"authorRegex\": \"//div[@id=blog_owner_name]/html()\",\n" +
            "    \"releaseDateRegex\": \"//div[@class=blog_bottom]/ul/li[1]/html()\",\n" +
            "    \"contentRegex\": \"//div[@class=iteye-blog-content-contain]/html()\",\n" +
            "    \"tagRegex\": \"//div[@class=news_tag]/a/html()\",\n" +
            "    \"targetLinksRegex\": \".*{uid}\\\\.iteye\\\\.com/blog/[0-9]+\",\n" +
            "    \"header\": [\n" +
            "      \"Host={uid}.iteye.com\",\n" +
            "      \"Referer=http://{uid}.iteye.com/\"\n" +
            "    ],\n" +
            "    \"entryUrls\": [\n" +
            "      \"http://{uid}.iteye.com/?page=1\"\n" +
            "    ]\n" +
            "  },\n" +
            "  \"cnblogs\": {\n" +
            "    \"domain\": \"www.cnblogs.com\",\n" +
            "    \"titleRegex\": \"//a[@id=cb_post_title_url]/html()\",\n" +
            "    \"authorRegex\": \"//div[@class=postDesc]/a[1]/html()\",\n" +
            "    \"releaseDateRegex\": \"//span[@id=post-date]/html()\",\n" +
            "    \"contentRegex\": \"//div[@id=cnblogs_post_body]/html()\",\n" +
            "    \"tagRegex\": \"//div[@id=EntryTag]/a/html()\",\n" +
            "    \"descriptionRegex\": \"//meta[@property=\\\"og:description\\\"]/@content\",\n" +
            "    \"targetLinksRegex\": \".*www\\\\.cnblogs\\\\.com/{uid}/p/[\\\\w\\\\d]+\\\\.html\",\n" +
            "    \"header\": [\n" +
            "      \"Host=www.cnblogs.com\",\n" +
            "      \"Referer=https://www.cnblogs.com/\"\n" +
            "    ],\n" +
            "    \"entryUrls\": [\n" +
            "      \"https://www.cnblogs.com/{uid}/default.html?page=1\"\n" +
            "    ]\n" +
            "  },\n" +
            "  \"juejin\": {\n" +
            "    \"domain\": \"juejin.im\",\n" +
            "    \"titleRegex\": \"//h1[@class=article-title]/html()\",\n" +
            "    \"authorRegex\": \"//div[@itemprop=author]/meta[@itemprop=\\\"name\\\"]/@content\",\n" +
            "    \"releaseDateRegex\": \"//meta[@itemprop=\\\"datePublished\\\"]/@content\",\n" +
            "    \"contentRegex\": \"//div[@class=article-content]/html()\",\n" +
            "    \"tagRegex\": \"//div[@class=tag-title]/html()\",\n" +
            "    \"cookie\": \"_ga=GA1.2.595705133.1611237484; __tea_cookie_tokens_2608=%257B%2522web_id%2522%253A%25226925428205755418116%2522%252C%2522ssid%2522%253A%2522e15927b6-bc34-474e-898f-2cf37b7d2727%2522%252C%2522user_unique_id%2522%253A%25226925428205755418116%2522%252C%2522timestamp%2522%253A1612451906024%257D; odin_tt=e282efb04e421733fe060bba9bffae7021aecf09ec862da28298897bb57590397f632b3aebd42f146dd10e2f69f96d1429cf8267324a94489e7e4a237b32aa16; n_mh=1ilNECBRd0xBAR4VMYD7xEwcwBEdWggC2XsK-DLcC3U; MONITOR_WEB_ID=5040c351-1ff1-405f-82e1-d22331e3eaf3; _gid=GA1.2.1270309245.1667057045; _tea_utm_cache_6587={%22utm_source%22:%22jj_nav%22}; _tea_utm_cache_2608={%22utm_source%22:%22timeline%22%2C%22utm_medium%22:%22banner%22%2C%22utm_campaign%22:%22xiaoce_electron_20221026%22}; passport_csrf_token=50677f180c6a6419819d808b76834f2a; passport_csrf_token_default=50677f180c6a6419819d808b76834f2a; sid_guard=ba5bec6fa9e890f3d7f2cf1c90648fd6%7C1667059079%7C21600%7CSat%2C+29-Oct-2022+21%3A57%3A59+GMT; uid_tt=4d7801d239b5ee8f420b995f789c812d; uid_tt_ss=4d7801d239b5ee8f420b995f789c812d; sid_tt=ba5bec6fa9e890f3d7f2cf1c90648fd6; sessionid=ba5bec6fa9e890f3d7f2cf1c90648fd6; sessionid_ss=ba5bec6fa9e890f3d7f2cf1c90648fd6; sid_ucp_v1=1.0.0-KDcyNmIyYjAzZGQ2YzcyMDY0Njg4MmQ5MTQ5Yzc1OTZlMDgzYjI3YTQKCBCHm_WaBhgNGgJsZiIgYmE1YmVjNmZhOWU4OTBmM2Q3ZjJjZjFjOTA2NDhmZDY; ssid_ucp_v1=1.0.0-KDcyNmIyYjAzZGQ2YzcyMDY0Njg4MmQ5MTQ5Yzc1OTZlMDgzYjI3YTQKCBCHm_WaBhgNGgJsZiIgYmE1YmVjNmZhOWU4OTBmM2Q3ZjJjZjFjOTA2NDhmZDY\",\n" +

            "    \"targetLinksRegex\": \".*juejin\\\\.im/post/[\\\\w\\\\d]+\",\n" +
            "    \"header\": [\n" +
            "      \"Host=juejin.im\",\n" +
            "      \"Referer=https://juejin.im\"\n" +
            "    ],\n" +
            "    \"entryUrls\": [\n" +
            "      \"https://juejin.im/user/{uid}/posts\"\n" +
            "    ]\n" +
            "  },\n" +
            "  \"v2ex\": {\n" +
            "    \"domain\": \"v2ex.com\",\n" +
            "    \"titleRegex\": \"//*[@id=Main]/div[@class=box]/div[@class=header]/h1/html()\",\n" +
            "    \"authorRegex\": \"//*[@id=Main]/div[@class=box]/div[@class=header]/small/a/html()\",\n" +
            "    \"releaseDateRegex\": \"//meta[@property=\\\"article:published_time\\\"]/@content\",\n" +
            "    \"contentRegex\": \"//div[@class=markdown_body]/html()\",\n" +
            "    \"tagRegex\": \"//*[@id=\\\"Main\\\"]/div[6]/div/a/html()\",\n" +
            "    \"descriptionRegex\": \"//meta[@property=\\\"og:description\\\"]/@content\",\n" +
            "    \"targetLinksRegex\": \".*www\\\\.v2ex\\\\.com/t/[\\\\w\\\\d]+\",\n" +
            "    \"header\": [\n" +
            "      \"Host=www.v2ex.com\",\n" +
            "      \"Referer=https://www.v2ex.com\"\n" +
            "    ],\n" +
            "    \"entryUrls\": [\n" +
            "      \"https://www.v2ex.com/member/{uid}\"\n" +
            "    ]\n" +
            "  }\n" +
            "}," +
            "\"oschina\": {\n" +
            "    \"domain\": \"oschina.net\",\n" +
            "    \"titleRegex\": \"//h1[@class=article-box__title]/a/text()\",\n" +
            "    \"authorRegex\": \"//div[@class=article-box__meta]/div[@class=item-list]/div[2]/a/html()\",\n" +
            "    \"releaseDateRegex\": \"//div[@class=article-box__meta]/div[@class=item-list]/div[4]/html()\",\n" +
            "    \"contentRegex\": \"//div[@class=content]/html()\",\n" +
            "    \"tagRegex\": \"//div[@class=tags-box]/div[@class=tags-box__inner]/a/html()\",\n" +
            "    \"targetLinksRegex\": \"https://my.oschina.net/.*/blog/[0-9]{1,10}\",\n" +
            "    \"header\": [\n" +
            "      \"Host=my.oschina.net\",\n" +
            "      \"Referer=https://my.oschina.net\"\n" +
            "    ],\n" +
            "    \"entryUrls\": [\n" +
            "      \"https://my.oschina.net/{uid}\",\n" +
            "      \"https://my.oschina.net/u/{uid}\"\n" +
            "    ]\n" +
            "  },\n" +
            "  \"jianshu\": {\n" +
            "    \"resolver\": {\n" +
            "      \"releaseDate\": {\n" +
            "        \"type\": \"regex\",\n" +
            "        \"clazz\": \"java.lang.Long\",\n" +
            "        \"operator\": \"* 1000\"\n" +
            "      }\n" +
            "    },\n" +
            "    \"domain\": \"jianshu.com\",\n" +
            "    \"titleRegex\": \"//h1[@class=_1RuRku]/text()\",\n" +
            "    \"authorRegex\": \"//span[@class=_22gUMi]/html()\",\n" +
            "    \"releaseDateRegex\": \".*\\\"first_shared_at\\\":([0-9]+),.*\",\n" +
            "    \"contentRegex\": \"//article[@class=_2rhmJa]/html()\",\n" +
            "    \"tagRegex\": \"//div\",\n" +
            "    \"targetLinksRegex\": \"/p/[0-9a-zA-Z]{1,15}\",\n" +
            "    \"header\": [\n" +
            "      \"Host=www.jianshu.com\",\n" +
            "      \"Referer=https://www.jianshu.com/p/{uid}\"\n" +
            "    ],\n" +
            "    \"entryUrls\": [\n" +
            "      \"https://www.jianshu.com/p/{uid}\",\n" +
            "      \"https://www.jianshu.com/u/{uid}\"\n" +
            "    ]\n" +
            "  }";
}
