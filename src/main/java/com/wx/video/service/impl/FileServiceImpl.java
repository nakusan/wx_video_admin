package com.wx.video.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;

import com.aliyun.oss.model.PutObjectRequest;
import com.wx.video.enums.VideoStatusEnum;
import com.wx.video.model.Video;
import com.wx.video.service.FileService;
import com.wx.video.utils.ConstantPropertiesUtil;
import com.wx.video.utils.PutObjectProgressListener;
import com.wx.video.utils.VideoUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;

import org.n3r.idworker.Sid;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    /**
     * 输出文件到oss
     *
     * @param file
     * @return
     */
    @Override
    public Video upload(MultipartFile file) throws Exception {

        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String fileHost = ConstantPropertiesUtil.FILE_HOST;

        Video video = null;
        OSS ossClient = null;
        try {
            //生产新的文件名
            String id = Sid.nextShort();
            Map<String, String> videoInfo = VideoUtil.getVideoDurationAndCover(file, id);
            Date currentDate = VideoUtil.getCurrentDate();

            // 创建OSSClient实例
            ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            if (!ossClient.doesBucketExist(bucketName)) {
                //创建bucket
                ossClient.createBucket(bucketName);
                //设置oss实例的访问权限，
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            //构建文件日期
            String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
            //文件扩展名
            String newFileName = id + "." + FilenameUtils.getExtension(file.getOriginalFilename());
            // URL
            String fileUrl = fileHost + "/" + date + "/" + newFileName;
            //获取上传文件流
            InputStream inputStream = file.getInputStream();
            long contentLength = file.getSize();

            //上传到阿里云oss中
            ossClient.putObject(new PutObjectRequest(bucketName,fileUrl, inputStream).
                    <PutObjectRequest>withProgressListener(new PutObjectProgressListener(contentLength)));
            //获取视频url地址
            String url = "https://" + bucketName + "." + endPoint + "/" + fileUrl;

            ///////////////////////////////////////////////////
            // 封面处理
            ///////////////////////////////////////////////////
            String outputImagePath = videoInfo.get("outputImagePath");
            String imageName = videoInfo.get("imageName");
            File tempImagePath = new File(outputImagePath);
            String thumbUrl = null;
            if (tempImagePath.exists()) {
                // 封面URL
                String imageUrl = fileHost + "/" + date + "/" + imageName;
                ossClient.putObject(bucketName, imageUrl, new FileInputStream(tempImagePath));
                // 获取视频封面url地址
                thumbUrl = "https://" + bucketName + "." + endPoint + "/" + imageUrl;

                // 删除临时文件
                tempImagePath.delete();
            }

            video = new Video();
            video.setVideoId(id);                                 // VideoId
            video.setDuration(videoInfo.get("duration"));         // 视频时长
            video.setVideoPath(url);                              // 视频存放地址
            video.setThumbUrl(thumbUrl);                          // 封面存放地址
            video.setStatus(VideoStatusEnum.FORBID.getValue());   // 状态初始化
            video.setIsDelete(VideoStatusEnum.ACTIVE.getValue()); // 删除标记
            video.setCreateTime(currentDate);                     // 创建时间
            video.setUpdateTime(currentDate);                     // 更新时间

        } catch (Exception e) {
//            log.error(e.getMessage());
            throw e;
        } finally {
            // 关闭OSSClient
            if (null != ossClient) {
                ossClient.shutdown();
            }
        }
        return video;
    }

//    /**
//     * @desc 查看文件列表
//     */
//    @Override
//    public List<OSSObjectSummary> getObjectList() {
//        OSS ossClient = new OSSClient(endPoint, accessKeyId, accessKeySecret);
//        // 设置最大个数。
//        final int maxKeys = 200;
//        // 列举文件。
//        ListObjectsRequest listObjectsRequest = new ListObjectsRequest(bucketName);
//        listObjectsRequest.setPrefix(fileHost + "/");
//        ObjectListing objectListing = ossClient.listObjects(listObjectsRequest.withMaxKeys(maxKeys));
//        List<OSSObjectSummary> sums = objectListing.getObjectSummaries();
//        return sums;
//    }

//    /**
//     * 获取文件临时url
//     *
//     * @param objectName    oss中的文件名
//     * @param
//     */
//    @Override
//    public String getUrl(String objectName ,long effectiveTime) {
//        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
//        // 设置URL过期时间
//        Date expiration = new Date(new Date().getTime() + effectiveTime);
//        GeneratePresignedUrlRequest generatePresignedUrlRequest ;
//        generatePresignedUrlRequest =new GeneratePresignedUrlRequest(bucketName, objectName);
//        generatePresignedUrlRequest.setExpiration(expiration);
//        URL url = ossClient.generatePresignedUrl(generatePresignedUrlRequest);
//        return url.toString();
//    }


    /**
     * 删除OSS中的单个文件
     *
     * @param objectName 唯一objectName（在oss中的文件名字）
     */
    @Override
    public void delete(String objectName) {
        String endPoint = ConstantPropertiesUtil.END_POINT;
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtil.BUCKET_NAME;
        OSS ossClient = null;
        try {
            // 创建OSSClient实例
            ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
            // 删除文件
            ossClient.deleteObject(bucketName, objectName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭OSSClient
            if (null != ossClient) {
                ossClient.shutdown();
            }
        }
    }



//    /**
//     * 输出文件到fastdfs
//     *
//     * @param fileStr
//     * @return
//     */
//    @Override
//    public String uploadToFastDfs(String fileStr) {
//
//        FastDFSFile fastdfsfile = null;
//        File file1 = null;
//        try {
//            file1 = new File(fileStr);
//            MultipartFile file = getMultipartFile(file1);
//            fastdfsfile = new FastDFSFile(
//                    file.getOriginalFilename(),//原来的文件名  1234.jpg
//                    file.getBytes(),//文件本身的字节数组
//                    StringUtils.getFilenameExtension(file.getOriginalFilename())
//            );
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            file1.delete();
//        }
//
//
//        String[] upload = FastDFSClient.upload(fastdfsfile);
//
//
//        return FastDFSClient.getTrackerUrl() + "/" + upload[0] + "/" + upload[1];
//    }
//
//    private static MultipartFile getMultipartFile(File file) {
//        FileInputStream fileInputStream = null;
//        MultipartFile multipartFile = null;
//        try {
//            fileInputStream = new FileInputStream(file);
//            multipartFile = new MockMultipartFile(file.getName(), file.getName(),
//                    ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return multipartFile;
//    }
}
