package com.yiranpay.web.controller.paychannel;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.yiranpay.common.annotation.Log;
import com.yiranpay.common.enums.BusinessType;
import com.yiranpay.paychannel.domain.TmFundChannelExt;
import com.yiranpay.paychannel.service.ITmFundChannelExtService;
import com.yiranpay.common.config.Global;
import com.yiranpay.common.core.controller.BaseController;
import com.yiranpay.common.core.domain.AjaxResult;
import com.yiranpay.common.core.page.TableDataInfo;
import com.yiranpay.common.utils.poi.ExcelUtil;
import com.yiranpay.framework.util.ShiroUtils;
/**
 * 资金渠道特性 信息操作处理
 * 
 * @author yiran
 * @date 2019-04-19
 */
@Controller
@RequestMapping("/paychannel/tmFundChannelExt")
public class TmFundChannelExtController extends BaseController
{
	private Logger        logger = LoggerFactory.getLogger(TmFundChannelExtController.class);
    private String prefix = "paychannel/tmFundChannelExt";
	
	@Autowired
	private ITmFundChannelExtService tmFundChannelExtService;
	
	@RequiresPermissions("paychannel:tmFundChannelExt:view")
	@GetMapping()
	public String tmFundChannelExt()
	{
	    return prefix + "/tmFundChannelExt";
	}
	
	/**
	 * 查询资金渠道特性列表
	 */
	@RequiresPermissions("paychannel:tmFundChannelExt:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(TmFundChannelExt tmFundChannelExt)
	{
		startPage();
        List<TmFundChannelExt> list = tmFundChannelExtService.selectTmFundChannelExtList(tmFundChannelExt);
		return getDataTable(list);
	}
	
	/**
	 * 资金渠道特性
	 */
	@GetMapping("/setFundChannelExt/{id}")
	public String setFundChannelExt(@PathVariable("id") String id, ModelMap mmap)
	{
		logger.info("获取资金渠道特性->渠道编号：{}",id);
		TmFundChannelExt tmFundChannelExt = new TmFundChannelExt();
		tmFundChannelExt.setFundChannelCode(id);
		List<TmFundChannelExt> list = tmFundChannelExtService.selectTmFundChannelExtList(tmFundChannelExt);
		mmap.put("list", list);
		mmap.put("fundChannelCode", id);
	    return prefix + "/fundChannelExt";
	}
	
	
	/**
	 * 导出资金渠道特性列表
	 */
	@RequiresPermissions("paychannel:tmFundChannelExt:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(TmFundChannelExt tmFundChannelExt)
    {
    	List<TmFundChannelExt> list = tmFundChannelExtService.selectTmFundChannelExtList(tmFundChannelExt);
        ExcelUtil<TmFundChannelExt> util = new ExcelUtil<TmFundChannelExt>(TmFundChannelExt.class);
        return util.exportExcel(list, "tmFundChannelExt");
    }
	
	/**
	 * 新增资金渠道特性
	 */
	@GetMapping("/add/{id}")
	public String add(@PathVariable("id") String id, ModelMap mmap)
	{
		mmap.put("fundChannelCode", id);
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存资金渠道特性
	 */
	@RequiresPermissions("paychannel:tmFundChannelExt:add")
	@Log(title = "资金渠道特性", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(TmFundChannelExt tmFundChannelExt)
	{	
		
		tmFundChannelExt.setAttrKey(tmFundChannelExt.getAttrKey().trim());
		tmFundChannelExt.setAttrValue(tmFundChannelExt.getAttrValue().trim());
		tmFundChannelExt.setValueSplit(tmFundChannelExt.getValueSplit().trim());
		tmFundChannelExt.setApiCode(tmFundChannelExt.getApiCode().trim());
		return toAjax(tmFundChannelExtService.insertTmFundChannelExt(tmFundChannelExt));
	}

	/**
	 * 修改资金渠道特性
	 */
	@GetMapping("/edit/{extId}")
	public String edit(@PathVariable("extId") Integer extId, ModelMap mmap)
	{
		TmFundChannelExt tmFundChannelExt = tmFundChannelExtService.selectTmFundChannelExtById(extId);
		mmap.put("tmFundChannelExt", tmFundChannelExt);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存资金渠道特性
	 */
	@RequiresPermissions("paychannel:tmFundChannelExt:edit")
	@Log(title = "资金渠道特性", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(TmFundChannelExt tmFundChannelExt)
	{		
		return toAjax(tmFundChannelExtService.updateTmFundChannelExt(tmFundChannelExt));
	}
	
	/**
	 * 删除资金渠道特性
	 */
	@RequiresPermissions("paychannel:tmFundChannelExt:remove")
	@Log(title = "资金渠道特性", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(tmFundChannelExtService.deleteTmFundChannelExtByIds(ids));
	}
	
}
