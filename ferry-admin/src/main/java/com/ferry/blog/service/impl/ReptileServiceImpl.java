package com.ferry.blog.service.impl;

import com.ferry.admin.util.ImageDownloadUtil;
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
}
