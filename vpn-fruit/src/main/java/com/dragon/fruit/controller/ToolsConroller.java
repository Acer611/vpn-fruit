package com.dragon.fruit.controller;

import com.dragon.fruit.dao.uuid.UUIDDao;
import com.dragon.fruit.entity.po.user.Vmess;
import com.dragon.fruit.entity.po.uuid.UUID;
import com.dragon.fruit.service.IVmessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "工具类接口")
@RestController
@RequestMapping("/tools")
public class ToolsConroller {
    @Autowired
    private IVmessService vmessService;
    @Autowired
    private UUIDDao uuidDao;

    /**
     * 生成UUID的工具类
     * @return
     */

    @ApiOperation(value = "生成VIP UUID")
    @ResponseBody
    @RequestMapping(value = "/generateVIPUUID", method = RequestMethod.GET)
    public Map generateUUID(){

        Map resultMap = new HashMap();

        List<String> uuidList = readFileByLines("E:\\vpnuuid\\vip.txt");
        List<Vmess> vmessList = vmessService.listVIPVmess();

        for (Vmess vmess:vmessList) {
            for (String uuid:uuidList) {
                UUID uuidEntity = new UUID();
                uuidEntity.setUuid(uuid);
                uuidEntity.setVmessID(vmess.getId());
                uuidEntity.setIsVip(1);
                uuidEntity.setIsEnable(0);
                uuidDao.saveUUID(uuidEntity);
            }
        }

        return  resultMap;

    }


    @ApiOperation(value = "生成免费 UUID")
    @ResponseBody
    @RequestMapping(value = "/generateFreeUUID", method = RequestMethod.GET)
    public Map generateFreeUUID(){

        Map resultMap = new HashMap();

        List<String> uuidList = readFileByLines("E:\\vpnuuid\\free.txt");
        List<Vmess> freeVmessList = vmessService.listFreeVmess();

        for (Vmess vmess:freeVmessList) {
            for (String uuid:uuidList) {
                UUID uuidEntity = new UUID();
                uuidEntity.setUuid(uuid);
                uuidEntity.setVmessID(vmess.getId());
                uuidEntity.setIsVip(0);
                uuidEntity.setIsEnable(0);
                uuidDao.saveUUID(uuidEntity);
            }
        }




        return  resultMap;

    }

    /**
     * 读取文件
     * @param fileName
     * @return
     */
    public static List<String> readFileByLines(String fileName) {
        List<String> uuidList = new ArrayList<>();
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读一行，读入null时文件结束
            while ((tempString = reader.readLine()) != null) {
                //Thread.sleep(500);
                System.out.println(tempString);
                uuidList.add(tempString);
                line++;
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return uuidList;

    }

}
