package org.ssh.app.common.service;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.memcached.SpyMemcachedClient;
import org.springside.modules.utils.encode.JsonBinder;
import org.ssh.app.cache.MemcachedObjectType;
import org.ssh.app.common.dao.HzDao;
import org.ssh.app.common.entity.Hz;

@Service("hzService")
@Transactional
public class HzService {
    protected Logger logger = LoggerFactory.getLogger(getClass());
    private HzDao hzDao;

    private SpyMemcachedClient spyMemcachedClient;
    private JsonBinder jsonBinder = JsonBinder.buildNonDefaultBinder();

    @Autowired(required = false)
    public void setSpyMemcachedClient(SpyMemcachedClient spyMemcachedClient) {
        this.spyMemcachedClient = spyMemcachedClient;
    }

    @Autowired
    public void setHzDao(HzDao hzDao) {
        this.hzDao = hzDao;
    }

    /**
     * 获取字符串的五笔助记符和拼音助记符,其中的英文字母和阿拉伯数字保持不变
     */
    @Transactional(readOnly = true)
    public Map<String, String> getMemo(String hzStr) {
        Map<String, String> map = new HashMap<String, String>();
        String oneS = null;
        StringBuffer bfWb = new StringBuffer();
        StringBuffer bfPy = new StringBuffer();
        Hz hz = null;
        for (int i = 0; i < hzStr.length(); i++) {
            oneS = hzStr.substring(i, i + 1);
            if (spyMemcachedClient != null) {
                logger.debug("get hz use memecache!!!" + oneS);
                hz = getHzFromMemcached(oneS);
            } else {
                hz = hzDao.findOne(oneS);

            }
            if (hz != null) {
                bfWb.append(hz.getWb().substring(0, 1));
                bfPy.append(hz.getPy().substring(0, 1));
            }
        }

        String py = bfPy.toString();
        String wb = bfWb.toString();

        if (py.length() > 10) {
            py = py.substring(0, 10);
        }

        if (wb.length() > 10) {
            wb = wb.substring(0, 10);
        }

        map.put("py", py);
        map.put("wb", wb);

        return map;
    }

    /**
     * 访问Memcached, 使用JSON字符串存放对象以节约空间.
     */
    private Hz getHzFromMemcached(String one) {
        //TODO
        //不能直接用汉字作为spyMemcachedClient的key
        String one_code = one;
        try{
            one_code=URLEncoder.encode(one,"UTF-8");
        }catch (Exception e) {
            // TODO: handle exception
        }

        String key = MemcachedObjectType.HZK.getPrefix() + one_code;

        Hz hz = null;
        String jsonString = spyMemcachedClient.get(key);
        logger.debug("get key:" + key + ",jsonString:" + jsonString);
        if (jsonString == null) {
            //hz = hzDao.findOne(one);
            hz = hzDao.findOneBy("hz", one);
            if (hz != null) {
                jsonString = jsonBinder.toJson(hz);
                spyMemcachedClient.set(key, MemcachedObjectType.HZK.getExpiredTime(), jsonString);
            }
        } else {
            hz = jsonBinder.fromJson(jsonString, Hz.class);
        }
        return hz;
    }

    public void initData() {
        if (this.hzDao.getHzCount() != 0)
            return;

        logger.debug("开始装载汉字库数据");

        File resourcetxt = new File(this.getClass().getResource("/data/hzk.txt").getFile());

        try {
            FileInputStream fis = new FileInputStream(resourcetxt);
            String thisLine;

            DataInputStream myInput = new DataInputStream(fis);
            BufferedReader br = new BufferedReader(new InputStreamReader(myInput, "UTF-8"));

            Hz re = new Hz();
            //this.hzDao.batchExecute("delete from " + Hz.class.getName());
            int line = 1;
            while ((thisLine = br.readLine()) != null) {
                if (line == 1) {
                    line++;
                    continue;
                }
                String star[] = thisLine.split(",");
                //for(int j=0;j<star.length;j++){
                //    System.out.println(star[j]);
                //}

                if (star[1].trim().equals(""))
                    continue;

                re = new Hz();
                re.setOid(new Long(star[0]));
                re.setHz(star[1]);
                re.setPy(star[2]);
                re.setWb(star[3]);
                this.hzDao.save(re);
            }
            //
            //            CSVReader reader = new CSVReader(new InputStreamReader(resourcetxt,"UTF-8"),',');
            //
            //            String[] nextLine;
            //            while ((nextLine = reader.readNext()) != null) {
            //                System.out.println("Name: [" + nextLine[1] + "]\npy: [" + nextLine[2]
            //                        + "]\nwb: [" + nextLine[3] + "]");
            //            }

        } catch (Exception e) {
            logger.error("装载汉字数据出错:" + e);
            throw new ServiceException("导入汉字库时，服务器发生异常");
        } finally {

        }
    }
}