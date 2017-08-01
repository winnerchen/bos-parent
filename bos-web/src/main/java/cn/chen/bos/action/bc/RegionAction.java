package cn.chen.bos.action.bc;

import cn.chen.bos.action.base.BaseAction;
import cn.chen.bos.domain.bc.Region;
import cn.chen.bos.service.impl.FacadeService;
import cn.chen.bos.utils.PinYin4jUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 2017/7/18.
 */
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("mavenbos")
public class RegionAction extends BaseAction<Region> {
    @Autowired
    private FacadeService facadeService;

    private File upload;

    public void setUpload(File upload) {
        this.upload = upload;
    }

    private String getHeadFromArray(String[] strings) {
        if (strings == null || strings.length == 0) {
            return null;
        } else {
            StringBuilder sb = new StringBuilder();
            for (String s : strings) {
                sb.append(s);
            }
            return sb.toString();
        }

    }


    @Action(value = "regionAction_oneClickUpload", results = { @Result(name = "oneClickUpload", type = "json") })
    public String oneClickUpload() {
        if (upload != null) {
            HSSFWorkbook workbook = null;
            try {
                workbook = new HSSFWorkbook(new FileInputStream(upload));
                HSSFSheet sheet = workbook.getSheetAt(0);
                ArrayList<Region> regions = new ArrayList<>();
                for (Row row : sheet) {
                    int rownum = row.getRowNum();
                    if (rownum == 0) {
                        continue;
                    }
                    Region region = new Region();
                    region.setId(row.getCell(0).getStringCellValue());
                    String province = row.getCell(1).getStringCellValue();
                    region.setProvince(province);
                    String city = row.getCell(2).getStringCellValue();
                    region.setCity(city);
                    String district = row.getCell(3).getStringCellValue();
                    region.setDistrict(district);
                    region.setPostcode(row.getCell(4).getStringCellValue());

                    city=city.substring(0, city.length() - 1);
                    region.setCitycode(PinYin4jUtils.hanziToPinyin(city,""));

                    province = province.substring(0, province.length() - 1);
                    district = district.substring(0, district.length() - 1);
                    String[] strArr;
                    if (province.equalsIgnoreCase(city)) {
                        strArr = PinYin4jUtils.getHeadByString(province + district);
                    } else {
                        strArr = PinYin4jUtils.getHeadByString(province + city + district);
                    }
                    String shortcode = getHeadFromArray(strArr);
                    region.setShortcode(shortcode);

                    regions.add(region);
                }
                facadeService.getRegionService().save(regions);
                push(true);
                return ("oneClickUpload");
            } catch (IOException e) {
                push(false);
                e.printStackTrace();
                throw new RuntimeException("文件解析异常" + e.getMessage());
            }
        }else{
            push(false);
            throw new RuntimeException("文件没有上传...");
        }
    }
    /*@Action(value = "regionAction_pageQuery", results = { @Result(name = "pageQuery", type="json") })
    public String pageQuery() {
        Map<String, Object> map = new HashMap<>();
        PageRequest pageRequest = new PageRequest(page - 1, rows);

        Page<Region> pageData = facadeService.getRegionService.pageQuery(pageRequest);

        map.put("total", pageData.getTotalElements());
        map.put("rows", pageData.getContent());
        push(map);
        return "pageQuery";
    }*/
    @Action(value = "regionAction_pageQuery")
    public String pageQuery() {
        Page<Region> pageData = facadeService.getRegionService().pageQuery(getPageRequest());
        setPageData(pageData);
        return "pageQuery";// 分页查询结果集名称
    }

    @Action(value = "regionAction_validRegionCode", results = { @Result(name = "validRegionCode", type="json") })
    public String validTelephone() {
        Region region = facadeService.getRegionService().validRegionCode(model.getId());
        boolean flag=false;
        if (region == null) {
            flag = true;
        }
        push(flag);
        return "validRegionCode";
    }
    @Action(value = "regionAction_validPostcode", results = { @Result(name = "validPostcode", type="json") })
    public String validPostcode() {
        Region region = facadeService.getRegionService().validPostcode(model.getPostcode());
        boolean flag=false;
        if (region == null) {
            flag = true;
        }
        push(flag);
        return "validPostcode";
    }
    @Action(value = "regionAction_save", results = { @Result(name = "save", location = "/WEB-INF/pages/base/region.jsp") })
    public String save() {
        facadeService.getRegionService().save(model);
        return "save";
    }
    @Action(value = "regionAction_ajaxList", results = { @Result(name = "ajaxList", type="fastJson", params = {"includeProperties","id,name"}) })
    public String ajaxList() {
        String params = getParameter("q");
        List<Region> regions = facadeService.getRegionService().findAll(params);
        push(regions);
        return "ajaxList";
    }




}
