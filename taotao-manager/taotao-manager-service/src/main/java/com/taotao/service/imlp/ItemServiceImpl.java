package com.taotao.service.imlp;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.util.IDUtils;
import com.taotao.common.util.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
import com.taotao.manager.jedis.*;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper mapper;
	@Autowired
	private TbItemDescMapper descmapper;
	@Autowired
	private JmsTemplate Jmstemplate;
	@Autowired
	private JedisClient client;
	@Resource(name = "topicDestination")
	private Destination destination;
	@Value("${ITEM_INFO_KEY}")
	private String ITEM_INFO_KEY;
	@Value("${ITEM_INFO_KEY_EXPIRE}")
	private Integer ITEM_INFO_KEY_EXPIRE;
	@Override
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		// 1.设置分页的信息 使用pagehelper
		if (page == null)
			page = 1;
		if (rows == null)
			rows = 30;
		PageHelper.startPage(page, rows);
		// 2.注入mapper
		// 3.创建example 对象 不需要设置查询条件
		TbItemExample example = new TbItemExample();
		// 4.根据mapper调用查询所有数据的方法
		List<TbItem> list = mapper.selectByExample(example);
		// 5.获取分页的信息
		PageInfo<TbItem> info = new PageInfo<>(list);
		// 6.封装到EasyUIDataGridResult
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal((int) info.getTotal());
		result.setRows(info.getList());
		// 7.返回
		return result;
	}

	@Override
	public TaotaoResult saveItem(TbItem item, String desc) {
		// 生成商品的id
		final long itemId = IDUtils.genItemId();
		// 1.补全item 的其他属性
		item.setId(itemId);
		item.setCreated(new Date());
		// 1-正常，2-下架，3-删除',
		item.setStatus((byte) 1);
		item.setUpdated(item.getCreated());
		// 2.插入到item表 商品的基本信息表
		mapper.insertSelective(item);
		// 3.补全商品描述中的属性
		TbItemDesc desc2 = new TbItemDesc();
		desc2.setItemDesc(desc);
		desc2.setItemId(itemId);
		desc2.setCreated(item.getCreated());
		desc2.setUpdated(item.getCreated());
		// 4.插入商品描述数据
		// 注入tbitemdesc的mapper
		descmapper.insertSelective(desc2);
		// 发送消息
		Jmstemplate.send(destination, new MessageCreator() {

			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub

				return session.createTextMessage(itemId + "");
			}
		});
		// 5.返回taotaoresult
		return TaotaoResult.ok();
	}

	@Override
	public TbItem getItemById(long itemId) {
		// 添加缓存的原则是，不能够影响现在有的业务逻辑
		// 查询缓存
		try {
			//if (itemId != null) {
			System.out.println("有缓存");
				// 从缓存中查询
				String jsonstring = client.get(ITEM_INFO_KEY + ":" + itemId + ":BASE");
				if (StringUtils.isNotBlank(jsonstring)) {// 不为空则直接返回
					client.expire(ITEM_INFO_KEY + ":" + itemId + ":BASE", ITEM_INFO_KEY_EXPIRE);
					return JsonUtils.jsonToPojo(jsonstring, TbItem.class);
				}
		//	}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbItem tbItem = mapper.selectByPrimaryKey(itemId);

		// 添加缓存
		try {
			// 注入redisclient
			if (tbItem != null) {
				client.set(ITEM_INFO_KEY + ":" + itemId + ":BASE", JsonUtils.objectToJson(tbItem));
				client.expire(ITEM_INFO_KEY + ":" + itemId + ":BASE", ITEM_INFO_KEY_EXPIRE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tbItem;

	}

	@Override
	public TbItemDesc getItemDescById(long itemId) {
		// 查询缓存
		try {
			//if (itemId != null) {
				// 从缓存中查询
				System.out.println("有缓存");
				String jsonstring = client.get(ITEM_INFO_KEY + ":" + itemId + ":DESC");
				if (StringUtils.isNotBlank(jsonstring)) {// 不为空则直接返回
					client.expire(ITEM_INFO_KEY + ":" + itemId + ":DESC", ITEM_INFO_KEY_EXPIRE);
					return JsonUtils.jsonToPojo(jsonstring, TbItemDesc.class);
				}
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}

		TbItemDesc itemDesc = descmapper.selectByPrimaryKey(itemId);

		// 添加缓存
		try {
			// 注入redisclient
			if (itemDesc != null) {
				client.set(ITEM_INFO_KEY + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc));
				client.expire(ITEM_INFO_KEY + ":" + itemId + ":DESC", ITEM_INFO_KEY_EXPIRE);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;

	}

}
